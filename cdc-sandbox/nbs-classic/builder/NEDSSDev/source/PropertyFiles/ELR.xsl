<?xml version="1.0"?>
<!DOCTYPE stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.cdc.gov/NEDSS" xmlns:xs="http://www.cdc.gov/NEDSS">
	<xsl:template match="/">
		<html>
			<head>
				<STYLE><![CDATA[
		TABLE {	border-collapse: collapse;	border: 0px;}
		body {    background-color: #FFFFFF;    color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: normal;     margin-top: 0px;}
		label {    color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: bold;    vertical-align: top;}
		td {  color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: normal;  padding-left:5px;}
		th { color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: bold;    text-align: left;}
		div.sect table.sectHeader {width:100%; border-width:0px 0px 1px 0px; border-color:#5F8DBF; 
			   margin-top:1em; border-style:solid; padding:0.20em 0.20em 0.20em 0.25em;}
		div.sect table.sectHeader tr td.sectName {color:#CC5200; font-size: 110%; font-weight:bold; text-transform:capitalize;}
		div.sect table.sectHeader tr td.sectName a.anchor {text-decoration:none; /*color:#5F8DBF;*/ color:#a46322}
		div.sect div.sectBody {text-align:center; margin-left:1em;}

		table.sectionsToggler, table.subSectionsToggler, table.subSect1 {width:98%; margin:0 auto; margin-top:1em; }

		div.bluebarsect table.bluebarsectHeader {width:100%;  margin-top:0em; margin-bottom:0em;  background:#003470; padding:0px 0px; border-style:solid; border-bottom: 1px gray;}
		div.graybarsect table {width:100%;  margin-top:0em; margin-bottom:0em;  padding:0px 0px;}
		div.bluebarsect table.bluebarsectHeaderWhite {width:100%;  margin-top:0em; margin-bottom:0em;  background:"white"; padding:0px 0px; border-style:solid;}
		div.bluebarsect table.bluebarsectHeader tr td.bluebarsectName {color:"white"; font-size: 13px; font-weight:bold; text-transform:capitalize; height:25px;}
		div.bluebarsect table.bluebarsectHeader tr td.bluebarsectNameNoColor {border-style:none solid none solid; border-color:gray; background:white; height:25px;}
		div.bluebarsect table.bluebarsectHeader tr td.bluebarsectName a.anchor {text-decoration:none; /*color:#5F8DBF;*/ color:"white"}
		div.bluebarsect table.bluebarsectHeader tr td a.anchorBack {text-decoration:underline; /*color:#5F8DBF;*/ color:"white"}
		div.graybarsect table tbody tr.odd1 td{text-align:left; background:#EFEFEF; font-weight:bold; border: 1px solid gray; height:22px;}
		div.graybarsect table tbody tr.even1 td{text-align:left; border: 1px solid gray; height:22px;}
		div.graybarsect table tbody tr.odd1 td.odd1blank{text-align:left; background:white; border-style:none solid none solid; font-weight:bold; height:22px;}
		div.graybarsect table tbody tr.odd1 td.odd1blanktop{text-align:left; background:white; border-style: none solid none solid ; font-weight:bold; height:22px;}
		div.graybarsect table tbody tr.even1 td.even1blank{text-align:left; border-style:solid; border-top:0px gray; border-bottom:0px gray; height:22px;}
		div.graybarsect table tbody tr.even1 td.even1blankbottom{text-align:left; border-style:none solid none solid; height:22px;}
		
		span.valueTopLine {font:16px Arial; font-weight:bold; margin-left:0.2em;}
		table.style{width:100%; margin:0 auto; margin-top:1em;border:1px solid #AFAFAF;}
		table tr.cellColor {background:#FEF2BC;}
		table.Summary {width:100%; margin:0 auto; margin-top:1em;border:1px solid #AFAFAF;}
		table td.border3 {padding:0.15em;width:24%; border-style:solid; border-width:1px 1px 1px 1px; border-color:#AFAFAF;}
		table td.border4{text-align:right;padding:0.15em;width:24%; border-style:solid; border-width:1px 1px 1px 1px; border-color:#AFAFAF;}

      ]]></STYLE>
				<!--<STYLE><![CDATA[
		TABLE {	border-collapse: collapse;	border: 0px;}
		body {    background-color: #FFFFFF;    color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: normal;     margin-top: 0px;}
		label {    color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: bold;    vertical-align: top;}
		td {  color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: normal;  padding-left:5px;}
		th { color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: bold;    text-align: left;}
		div.sect table.sectHeader {width:100%; border-width:0px 0px 1px 0px; border-color:#5F8DBF; 
			   margin-top:1em; border-style:solid; padding:0.20em 0.20em 0.20em 0.25em;}
		div.sect table.sectHeader tr td.sectName {color:#CC5200; font-size: 110%; font-weight:bold; text-transform:capitalize;}
		div.sect table.sectHeader tr td.sectName a.anchor {text-decoration:none; /*color:#5F8DBF;*/ color:#a46322}
		div.sect div.sectBody {text-align:center; margin-left:1em;}

		table.sectionsToggler, table.subSectionsToggler, table.subSect1 {width:98%; margin:0 auto; margin-top:1em; }

		div.bluebarsect table.bluebarsectHeader {width:100%; margin-bottom:2px;}
		div.graybarsect table {width:100%;  margin-top:0em; margin-bottom:0em;  padding:0px 0px;}
		div.bluebarsect table.bluebarsectHeaderWhite {margin-bottom:2px;}
		div.bluebarsect table.bluebarsectHeader tr td.bluebarsectName {color:#CC5200;font-size:13px;font-weight:bold;}
		div.bluebarsect table.bluebarsectHeader tr td.bluebarsectNameNoColor {font-size:13px;font-weight:bold;}
		div.bluebarsect table.bluebarsectHeader tr td.bluebarsectName a.anchor {color:#CC5200;font-size:13px;}
		div.bluebarsect table.bluebarsectHeader tr td a.anchorBack {display:none}
		div.graybarsect table tbody tr.odd1 td{border-width:1px;border-style:solid; background:#EFEFEF; padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px; font-weight:bold;}
		div.graybarsect table tbody tr.even1 td{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px; border-width:1px;border-style:solid; }
		div.graybarsect table tbody tr.odd1 td.odd1blank{background:#FFF;font-size:11px; border-width:1px;border-style:none none none none;}
		div.graybarsect table tbody tr.odd1 td.odd1blanktop{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px; border-width:1px;border-style: none none none none ; }
		div.graybarsect table tbody tr.even1 td.even1blank{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px;border-width:1px;border-style:none none none none; }
		div.graybarsect table tbody tr.even1 td.even1blankbottom{border-width:1px;border-style:none none none none; padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px;}
		
		span.valueTopLine {font:13px Arial; font-weight:bold; margin-left:0.2em;}
		span.valueTopLine1 {font:13px Arial; margin-left:0.2em;}
		table.style{width:100%; margin:0 auto; margin-top:1em;border:1px solid #AFAFAF;}
		table tr.cellColor {background:#FEF2BC;}
		table.Summary {width:100%; margin:0 auto; margin-top:1em;border:1px solid #AFAFAF;}
		table td.border3 {padding:0.15em;width:24%; border-style:solid; border-width:1px 1px 1px 1px; border-color:#AFAFAF;}
		table td.border4{text-align:right;padding:0.15em;width:24%; border-style:solid; border-width:1px 1px 1px 1px; border-color:#AFAFAF;}

      ]]></STYLE>-->
				<xsl:text disable-output-escaping="yes"><![CDATA[ 
	<script type =	"text/javascript"> 
		function gotoSection(sectionId)
		{
			var sectionId = "#" + sectionId;
		      window.location = sectionId;
		}	
		function toggleDisplay(Obj, imagen) {
			if (document.getElementById(Obj).style.display == 'none') {
				document.getElementById(Obj).style.display = "";
				document.getElementById(imagen).src = "section_collapse.gif";
		
			} else {
				document.getElementById(Obj).style.display = "none";
				document.getElementById(imagen).src = "section_expand.gif";
		
			}
		
			return false;
		}
		function colapseRawXML()
		{
		    if(document.getElementById('sec_rawXMLDiv')!=null){
				document.getElementById('sec_rawXMLDiv').style.display = "none";
			}
		}
		function populateDateReceived()
		{
		    //alert("Here");
		    if(document.getElementById('dateReceived')!=null && document.getElementById('dateReceivedHidden')!=null){
		    //alert("Here 1 " +document.getElementById('dateReceived').innerHTML);
		    //alert("Here 2 " +document.getElementById('dateReceivedHidden').value);
				document.getElementById('dateReceived').innerHTML = document.getElementById('dateReceivedHidden').value;
				//alert("Here 3 " +document.getElementById('dateReceived').innerHTML);
			}
		}
		function toggleAllSectionsDisplay(viewId)
		{
			var divCollection = document.getElementsByTagName("div");
			var imgCollection = document.getElementsByTagName("img");
			var expColl = document.getElementById('expcoll').innerHTML;
			if(expColl=="Collapse Sections"){
			for (var i=0; i<divCollection.length; i++) {
			    document.getElementById('expcoll').innerHTML="Expand Sections";
				if(divCollection[i].getAttributeNode('id')!=null && divCollection[i].getAttributeNode('id').nodeValue.substr(0,3) == "sec") {
					document.getElementById(divCollection[i].getAttributeNode("id").nodeValue).style.display = "none";
				}
			}
			//alert(imgCollection);
			for (var i=0; i<imgCollection.length; i++) {
				if(imgCollection[i].getAttributeNode('class')!=null && imgCollection[i].getAttributeNode('class').nodeValue == "img") {
					document.getElementById(imgCollection[i].getAttributeNode("id").nodeValue).src = "section_expand.gif";
				}
			}
			}
			else{
			document.getElementById('expcoll').innerHTML="Collapse Sections";
			for (var i=0; i<divCollection.length; i++) {
				if(divCollection[i].getAttributeNode('id')!=null && divCollection[i].getAttributeNode('id').nodeValue.substr(0,3) == "sec") {
					document.getElementById(divCollection[i].getAttributeNode("id").nodeValue).style.display = "";
				}
			}
			//alert(imgCollection);
			for (var i=0; i<imgCollection.length; i++) {
				if(imgCollection[i].getAttributeNode('class')!=null && imgCollection[i].getAttributeNode('class').nodeValue == "img") {
					document.getElementById(imgCollection[i].getAttributeNode("id").nodeValue).src = "section_collapse.gif";
				}
			}
			}
		}
	</script>
	]]></xsl:text>
			</head>
			<body>
				<xsl:for-each select="xs:Container">
					<xsl:for-each select="xs:HL7LabReport">
						<xsl:for-each select="xs:HL7PATIENT_RESULT/xs:PATIENT">
							<div>
								<a name="pageTop"/>
								<table class="style">
									<tr class="cellColor">
										<td class="border3" colspan="2">
											<span class="valueTopLine">
												<xsl:for-each select="xs:PatientIdentification/xs:PatientName">
													<xsl:call-template name="NameFormat"/>
												</xsl:for-each>
											</span>
											<xsl:if test="xs:PatientIdentification/xs:AdministrativeSex !=''">
												<span class="valueTopLine">|</span>
												<span class="valueTopLine">
													<xsl:value-of select="xs:PatientIdentification/xs:AdministrativeSex"/>
												</span>
											</xsl:if>
											<xsl:if test="xs:PatientIdentification/xs:DateTimeOfBirth">
												<span class="valueTopLine">|</span>
												<span class="valueTopLine">
													<xsl:for-each select="xs:PatientIdentification/xs:DateTimeOfBirth">
														<xsl:call-template name="dateFormatter"/>
													</xsl:for-each>
												</span>
											</xsl:if>
										</td>
									</tr>
									<tr class="cellColor">
										<td class="border3">
											<span class="valueTopLine"> Date Received: </span>
											<span class="valueTopLine1" id="dateReceived">
												<!--populated by body onload function-->
											</span>
										</td>
										<td class="border4">
											<span class="valueTopLine"> Accession Number: </span>
											<span class="valueTopLine1">
												<xsl:value-of select="../xs:ORDER_OBSERVATION/xs:ObservationRequest/xs:FillerOrderNumber/xs:HL7EntityIdentifier"/>
											</span>
											<span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
										</td>
									</tr>
								</table>
							</div>
						</xsl:for-each>
						<div id="view_ELR">
							<div class="sect" id="sect_HL7LabHeader">
								<table class="sectHeader">
									<tr>
										<td class="sectName">Lab Report(ELR)</td>
									</tr>
								</table>
							</div>
							<div class="view" id="viewELR" style="text-align:center;">
								<table class="sectionsToggler" style="width:100%;">
									<tr>
										<td>
											<ul class="horizontalList">
												<b>Go to: </b>
												<a href="javascript:gotoSection('patientInfo')">Patient Information</a> | <a href="javascript:gotoSection('ReportingInfo')">Reporting Information</a> | <a href="javascript:gotoSection('orderTest_1')">Ordered Test</a> | <a href="javascript:gotoSection('rawXML')">ELR XML Message</a>
											</ul>
										</td>
									</tr>
								</table>
							</div>
							<div id="CollapseExpand">
								<table>
									<tr>
										<td style="padding-top:1em;">
											<a id="expcoll" class="toggleHref" href="javascript:toggleAllSectionsDisplay('view_ELR')">Collapse Sections</a>
										</td>
									</tr>
									<tr>
										<td>
										&nbsp;
									</td>
									</tr>
								</table>
							</div>
							<xsl:call-template name="ELRPatient"/>
							<!-- Reporting Information section-->
							<div class="bluebarsect" id="ReportingInfo">
								<table class="bluebarsectHeader" role="presentation">
									<tr>
										<td class="bluebarsectName">
											<img src="section_collapse.gif" class="img" id="reportingInfo_gif{position()}" onclick="toggleDisplay('sec_report_patientInfo','reportingInfo_gif{position()}')" align="left"/>
											<a class="anchor" name="apatientInfo">Reporting Information</a>
										</td>
										<td style="text-align:right;">
											<a class="anchorBack" href="#pageTop">Back to top</a>&nbsp;
										</td>
									</tr>
								</table>
							</div>
							<div class="graybarsect" id="sec_report_patientInfo">
								<table id="report_patientTable" width="100%">
									<tbody>
										<tr class="odd1">
											<td width="50%">Lab Report Date</td>
											<td width="50%">Reporting Facility</td>
										</tr>
										<tr class="even1">
											<td>
												<xsl:for-each select="xs:HL7PATIENT_RESULT/xs:ORDER_OBSERVATION">
													<xsl:if test="xs:CommonOrder">
														<xsl:for-each select="xs:ObservationRequest/xs:ResultsRptStatusChngDateTime">
															<xsl:call-template name="dateFormatter"/>
														</xsl:for-each>
													</xsl:if>
												</xsl:for-each>
											</td>
											<td>
												<xsl:value-of select="xs:HL7MSH/xs:SendingFacility/xs:HL7NamespaceID"/>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<xsl:for-each select="xs:HL7PATIENT_RESULT">
								<xsl:for-each select="xs:ORDER_OBSERVATION">
									<!-- Ordered Test section-->
									<xsl:variable name="parentPosition">
										<xsl:value-of select="position()"/>
									</xsl:variable>
									<xsl:if test="xs:CommonOrder">
										<div class="bluebarsect" id="orderTest_{position()}">
											<table class="bluebarsectHeader" role="presentation" id="orderTestTable_{position()}">
												<tr>
													<td class="bluebarsectName">
														<img src="section_collapse.gif" class="img" id="{$parentPosition}_orderTest_gif{position()}" onclick="
											toggleDisplay('sec_{$parentPosition}_orderTestDiv_{position()}','{$parentPosition}_orderTest_gif{position()}')" align="left"/>
														<a class="anchor" tabindex="-1" name="apatientInfo">Ordered Test</a>
													</td>
													<td style="text-align:right;">
														<a class="anchorBack" href="#pageTop" tabindex="-1">Back to top</a>&nbsp;
											</td>
												</tr>
											</table>
										</div>
										<div id="sec_{$parentPosition}_orderTestDiv_{position()}">
											<div class="graybarsect" id="orderTestInfo_{position()}">
												<table id="orderTestInfoTable_{position()}">
													<tbody>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="50%" colspan="2">Ordered Test</td>
															<td width="50%" colspan="2">Accession Number</td>
														</tr>
														<tr class="even1">
															<td class="even1blank" width="2%">&nbsp;</td>
															<td colspan="2">
																<xsl:for-each select="xs:ObservationRequest/xs:UniversalServiceIdentifier">
																	<xsl:call-template name="CodedDescriptionWithCode"/>
																</xsl:for-each>
															</td>
															<td colspan="2">
																<xsl:value-of select="xs:ObservationRequest/xs:FillerOrderNumber/xs:HL7EntityIdentifier"/>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="25%">Date Collected</td>
															<td width="25%">Order Status</td>
															<td width="50%" colspan="2">Ordering Facility</td>
														</tr>
														<tr class="even1">
															<td class="even1blank" width="2%">&nbsp;</td>
															<td>
																<xsl:choose>
																	<xsl:when test="xs:ObservationRequest/xs:ObservationDateTime">
																		<xsl:for-each select="xs:ObservationRequest/xs:ObservationDateTime">
																			<xsl:call-template name="dateFormatter"/>
																		</xsl:for-each>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:for-each select="xs:PatientResultOrderSPMObservation/xs:SPECIMEN/xs:SPECIMEN/xs:SpecimenCollectionDateTime/xs:HL7RangeStartDateTime">
																			<xsl:call-template name="dateFormatter"/>
																		</xsl:for-each>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td>
																<xsl:value-of select="xs:ObservationRequest/xs:ResultStatus"/>
															</td>
															<td colspan="2">
																<xsl:value-of select="xs:CommonOrder/xs:OrderingFacilityName/xs:HL7OrganizationName"/>
																<br/>
																<xsl:for-each select="xs:CommonOrder/xs:OrderingFacilityAddress">
																	<xsl:if test="position() = 1">
																		<xsl:call-template name="AddressFormat"/>
																	</xsl:if>
																</xsl:for-each>
																<br/>
																<xsl:for-each select="xs:CommonOrder/xs:OrderingFacilityPhoneNumber">
																	<xsl:if test="position() = 1">
																		<xsl:call-template name="TelephoneFormat"/>
																	</xsl:if>
																</xsl:for-each>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="25%">Specimen Source</td>
															<td width="25%">Specimen Site</td>
															<td width="50%" colspan="2">Ordering Provider</td>
														</tr>
														<tr class="even1">
															<td class="even1blank" width="2%">&nbsp;</td>
															<td>
																<xsl:choose>
																	<xsl:when test="xs:ObservationRequest/xs:SpecimenSource/xs:HL7SpecimenSourceNameOrCode">
																		<xsl:for-each select="xs:ObservationRequest/xs:SpecimenSource/xs:HL7SpecimenSourceNameOrCode">
																			<xsl:call-template name="CodedDescriptionOrCode"/>
																		</xsl:for-each>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:for-each select="xs:PatientResultOrderSPMObservation/xs:SPECIMEN/xs:SPECIMEN/xs:SpecimenType">
																			<xsl:call-template name="CodedDescriptionOrCode"/>
																		</xsl:for-each>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td>
																<xsl:choose>
																	<xsl:when test="xs:ObservationRequest/xs:SpecimenSource/xs:HL7BodySite">
																		<xsl:for-each select="xs:ObservationRequest/xs:SpecimenSource/xs:HL7BodySite">
																			<xsl:call-template name="CodedDescriptionOrCode"/>
																		</xsl:for-each>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:for-each select="xs:PatientResultOrderSPMObservation/xs:SPECIMEN/xs:SPECIMEN/xs:SpecimenSourceSite">
																			<xsl:call-template name="CodedDescriptionOrCode"/>
																		</xsl:for-each>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td colspan="2">
																<xsl:for-each select="xs:ObservationRequest/xs:OrderingProvider">
																	<xsl:if test="position() = 1">
																		<xsl:call-template name="NameFormat"/>
																	</xsl:if>
																</xsl:for-each>
																<br/>
																<xsl:for-each select="xs:CommonOrder/xs:OrderingProviderAddress">
																	<xsl:if test="position() = 1">
																		<xsl:call-template name="AddressFormat"/>
																	</xsl:if>
																</xsl:for-each>
																<br/>
																<xsl:for-each select="xs:ObservationRequest/xs:OrderCallbackPhoneNumber">
																	<xsl:if test="position() = 1">
																		<xsl:call-template name="TelephoneFormat"/>
																	</xsl:if>
																</xsl:for-each>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="50%" colspan="2">Specimen Details</td>
															<td width="50%" colspan="2">Reason for Study</td>
														</tr>
														<tr class="even1">
															<td class="even1blank" width="2%">&nbsp;</td>
															<td colspan="2">
																<xsl:choose>
																	<xsl:when test="xs:ObservationRequest/xs:SpecimenSource/xs:HL7SpecimenCollectionMethod/xs:HL7String">
																		<xsl:value-of select="xs:ObservationRequest/xs:SpecimenSource/xs:HL7SpecimenCollectionMethod/xs:HL7String"/>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:value-of select="xs:PatientResultOrderSPMObservation/xs:SPECIMEN/xs:SPECIMEN/xs:SpecimenDescription"/>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td colspan="2">
																<xsl:for-each select="xs:ObservationRequest/xs:ReasonforStudy">
																	<xsl:call-template name="CodedDescriptionWithCode"/>
																	<xsl:if test="position() != last()">
																		<br/>
																	</xsl:if>
																</xsl:for-each>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="100%" colspan="4">Clinical Information</td>
														</tr>
														<tr class="even1">
															<td class="even1blank" width="2%">&nbsp;</td>
															<td colspan="4">
																<xsl:value-of select="xs:ObservationRequest/xs:RelevantClinicalInformation"/>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
											<xsl:for-each select="xs:PatientResultOrderObservation/xs:OBSERVATION">
												<!-- Resulted Test section-->
												<div class="bluebarsect" id="{$parentPosition}_ResultedTest-{position()}">
													<table class="bluebarsectHeader" role="presentation">
														<tr>
															<td width="2%" class="bluebarsectNameNoColor">&nbsp;</td>
															<td class="bluebarsectName">
																<img src="section_collapse.gif" class="img" id="{$parentPosition}_ResultedTest_gif{position()}" onclick="toggleDisplay('{$parentPosition}_ResultedTestDiv-{position()}','{$parentPosition}_ResultedTest_gif{position()}');" align="left"/>
																<a class="anchor" tabindex="-1" name="apatientInfo">Resulted Test</a>
															</td>
														</tr>
													</table>
												</div>
												<div id="{$parentPosition}_ResultedTestDiv-{position()}">
													<div class="graybarsect" id="ResultedTestDiv-{position()}">
														<table id="ResultedTestTable-{position()}">
															<tbody>
																<tr class="odd1">
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td width="33%">Test/Drug Name</td>
																	<td width="33%">Result/Organism</td>
																	<td width="33%">Status</td>
																</tr>
																<tr class="even1">
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td>
																		<xsl:for-each select="xs:ObservationResult/xs:ObservationIdentifier">
																			<xsl:call-template name="CodedDescriptionWithCode"/>
																		</xsl:for-each>
																	</td>
																	<td>
																		<xsl:for-each select="xs:ObservationResult/xs:ObservationValue">
																			<xsl:call-template name="do-not-escape-markup-characters">
																				<xsl:with-param name="text">
																					<xsl:value-of select="normalize-space(.)"/>
																				</xsl:with-param>
																			</xsl:call-template>&nbsp;<xsl:value-of select="../xs:Units/xs:HL7Identifier"/>
																			<br/>
																		</xsl:for-each>
																	</td>
																	<td>
																		<xsl:value-of select="xs:ObservationResult/xs:ObservationResultStatus"/>
																	</td>
																</tr>
																<tr class="odd1">
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td width="33%">Performed Date/Time</td>
																	<td width="33%">Test Result Method</td>
																	<td width="33%">Performing Facility</td>
																</tr>
																<tr class="even1">
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td>
																		<xsl:choose>
																			<xsl:when test="xs:ObservationResult/xs:DateTimeOftheObservation">
																				<xsl:for-each select="xs:ObservationResult/xs:DateTimeOftheObservation">
																					<xsl:call-template name="dateTimeFormatter"/>
																				</xsl:for-each>
																			</xsl:when>
																			<xsl:otherwise>
																				<xsl:for-each select="xs:ObservationResult/xs:DateTimeOftheAnalysis">
																					<xsl:call-template name="dateTimeFormatter"/>
																				</xsl:for-each>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td>
																		<xsl:for-each select="xs:ObservationResult/xs:ObservationMethod">
																			<xsl:call-template name="CodedDescriptionOrCode"/>
																			<xsl:if test="position() != last()">
																				<br/>
																			</xsl:if>
																		</xsl:for-each>
																	</td>
																	<td>
																		<xsl:choose>
																			<xsl:when  test="xs:ObservationResult/xs:PerformingOrganizationName">
																				<xsl:if test="normalize-space(xs:ObservationResult/xs:PerformingOrganizationName/xs:HL7OrganizationName)!=''">
																					<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationName/xs:HL7OrganizationName)"/>
																				</xsl:if>
																				<xsl:if test="normalize-space(xs:ObservationResult/xs:PerformingOrganizationName/xs:HL7OrganizationIdentifier)!=''">
																					(<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationName/xs:HL7OrganizationIdentifier)"/>)</xsl:if>
																				<br/>
																					<xsl:if test="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7StreetAddress/xs:HL7StreetOrMailingAddress)!='' and normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7StreetAddress/xs:HL7StreetOrMailingAddress)!='Not present in v2.3.1 message'">
																						<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7StreetAddress/xs:HL7StreetOrMailingAddress)"/>
																					</xsl:if>
																					<xsl:if test="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7OtherDesignation)!=''">,&nbsp;<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7OtherDesignation)"/>
																					</xsl:if>
																					<xsl:if test="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7City)!='' or normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/HL7StateOrProvince)!='' or normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7ZipOrPostalCode)!=''">
																						<br/>
																						<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7City)"/>
																						<xsl:if test="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7StateOrProvince)!=''">
				,&nbsp;<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7StateOrProvince)"/>
																						</xsl:if>
			&nbsp;<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7ZipOrPostalCode)"/>
																					</xsl:if>
																					<xsl:if test="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7CountyParishCode)!=''">
			,&nbsp;<xsl:value-of select="normalize-space(xs:ObservationResult/xs:PerformingOrganizationAddress/xs:HL7CountyParishCode)"/>
																					</xsl:if>
																			</xsl:when>
																			<xsl:otherwise>
																				<xsl:if test="normalize-space(xs:ObservationResult/xs:ProducersReference/xs:HL7Text)!=''">
																					<xsl:value-of select="xs:ObservationResult/xs:ProducersReference/xs:HL7Text"/>
																				</xsl:if>
																				<xsl:if test="normalize-space(xs:ObservationResult/xs:ProducersReference/xs:HL7Identifier)!=''">
																					(<xsl:value-of select="xs:ObservationResult/xs:ProducersReference/xs:HL7Identifier"/>
																					<xsl:if test="normalize-space(xs:ObservationResult/xs:ProducersReference/xs:HL7NameofCodingSystem)!=''">
																						[<xsl:value-of select="xs:ObservationResult/xs:ProducersReference/xs:HL7NameofCodingSystem"/>]
																					</xsl:if>)
																				</xsl:if>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																</tr>
																<tr class="odd1">
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td width="35%">Ref. Range</td>
																	<td width="65%" colspan="2">Abnormal Flag</td>
																</tr>
																<tr class="even1">
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td>
																		<xsl:value-of select="xs:ObservationResult/xs:ReferencesRange"/>
																	</td>
																	<td colspan="2">
																		<xsl:for-each select="xs:ObservationResult/xs:AbnormalFlags">
																			<xsl:call-template name="CodedDescriptionOrCode"/>
																			<br/>
																		</xsl:for-each>
																	</td>
																</tr>
																<tr class="odd1">
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td class="odd1blank" width="2%">&nbsp;</td>
																	<td width="100%" colspan="3">Comments</td>
																</tr>
																<tr class="even1">
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td class="even1blank" width="2%">&nbsp;</td>
																	<td colspan="5">
																		<xsl:for-each select="xs:NotesAndComments">
																			<xsl:choose>
																				<xsl:when test="xs:HL7Comment">
																					<xsl:for-each select="xs:HL7Comment">
																						<xsl:value-of select="../xs:HL7Comment"/>
																						<br/>
																					</xsl:for-each>
																				</xsl:when>
																				<xsl:otherwise>
																					<br/>
																				</xsl:otherwise>
																			</xsl:choose>
																		</xsl:for-each>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
													<xsl:variable name="resultParentPosition">
														<xsl:value-of select="position()"/>
													</xsl:variable>
													<xsl:call-template name="Susceptibilities">
														<xsl:with-param name="subID">
															<xsl:value-of select="xs:ObservationResult/xs:ObservationSubID"/>
														</xsl:with-param>
														<xsl:with-param name="testCode">
															<xsl:value-of select="xs:ObservationResult/xs:ObservationIdentifier/xs:HL7Identifier"/>
														</xsl:with-param>
														<xsl:with-param name="parentpos">
															<xsl:value-of select="$parentPosition"/>-<xsl:value-of select="$resultParentPosition"/>
														</xsl:with-param>
													</xsl:call-template>
												</div>
												<!-- Susceptibility section-->
											</xsl:for-each>
											<!--End loop for Resulted Test section-->
											<!-- Other section-->
											<div class="bluebarsect" id="{$parentPosition}_otherInfo-{position()}">
												<table class="bluebarsectHeader" role="presentation">
													<tr>
														<td width="2%" class="bluebarsectNameNoColor">&nbsp;</td>
														<td class="bluebarsectName">
															<img src="section_collapse.gif" class="img" id="{$parentPosition}_otherInfo_gif{position()}" onclick="toggleDisplay('{$parentPosition}_otherInfoDiv-{position()}','{$parentPosition}_otherInfo_gif{position()}');" align="left"/>
															<a class="anchor" tabindex="-1" name="apatientInfo">More Information</a>
														</td>
													</tr>
												</table>
											</div>
											<div class="graybarsect" id="{$parentPosition}_otherInfoDiv-{position()}">
												<table id="otherInfoTable-{position()}">
													<tbody>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="33%">Patient's birth place</td>
															<td width="33%">Collection Volume</td>
															<td width="33%">Priority Code</td>
														</tr>
														<tr class="even1">
															<td class="even1blankbottom" width="2%">&nbsp;</td>
															<td>
																<xsl:value-of select="../xs:PATIENT/xs:PatientIdentification/xs:BirthPlace"/>
															</td>
															<td>
																<xsl:choose>
																	<xsl:when test="xs:ObservationRequest/xs:CollectionVolume/xs:HL7Quantity/xs:HL7Numeric">
																		<xsl:value-of select="xs:ObservationRequest/xs:CollectionVolume/xs:HL7Quantity/xs:HL7Numeric"/>&nbsp;<xsl:value-of select="xs:ObservationRequest/xs:CollectionVolume/xs:HL7Units/xs:HL7Identifier"/>
																	</xsl:when>
																	<xsl:otherwise>
																		<xsl:value-of select="xs:PatientResultOrderSPMObservation/xs:SPECIMEN/xs:SPECIMEN/xs:SpecimenCollectionAmount/xs:HL7Quantity/xs:HL7Numeric"/>&nbsp;<xsl:value-of select="xs:PatientResultOrderSPMObservation/xs:SPECIMEN/xs:SPECIMEN/xs:SpecimenCollectionAmount/xs:HL7Units/xs:HL7Identifier"/>
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td>
																<xsl:value-of select="xs:ObservationRequest/xs:PriorityOBR"/>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="33%">Multiple Birth/Order</td>
															<td width="33%">Danger Code</td>
															<td width="33%">Message Control ID</td>
														</tr>
														<tr class="even1">
															<td class="even1blankbottom" width="2%">&nbsp;</td>
															<td>
																<xsl:value-of select="../xs:PATIENT/xs:PatientIdentification/xs:MultipleBirthIndicator"/>
																<xsl:if test="../xs:PATIENT/xs:PatientIdentification/xs:BirthOrder!=''">
																	<xsl:if test="../xs:PATIENT/xs:PatientIdentification/xs:MultipleBirthIndicator!=''">/</xsl:if>
																	<xsl:value-of select="../xs:PATIENT/xs:PatientIdentification/xs:BirthOrder/xs:HL7Numeric"/>
																</xsl:if>
															</td>
															<td>
																<xsl:for-each select="xs:ObservationRequest/xs:DangerCode">
																	<xsl:call-template name="CodedDescriptionOrCode"/>
																</xsl:for-each>
															</td>
															<td>
																<xsl:value-of select="../../xs:HL7MSH/xs:MessageControlID"/>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="33%">Alternate Contact</td>
															<td width="66%" colspan="2">Receiving Facility</td>
														</tr>
														<tr class="even1">
															<td class="even1blankbottom" width="2%">&nbsp;</td>
															<td>
																<xsl:if test="../xs:PATIENT/xs:NextofKinAssociatedParties">
																	<xsl:for-each select="../xs:PATIENT/xs:NextofKinAssociatedParties/xs:Name">
																		<xsl:if test="position() = 1">
																			<xsl:call-template name="NameFormat"/>
																			<br/>
																		</xsl:if>
																	</xsl:for-each>
																</xsl:if>
															</td>
															<td colspan="2">
																<xsl:value-of select="../../xs:HL7MSH/xs:ReceivingFacility/xs:HL7NamespaceID"/>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="33%">Alternate Contact Phone</td>
															<td width="66%" colspan="2">Participants</td>
														</tr>
														<tr class="even1">
															<td class="even1blankbottom" width="2%">&nbsp;</td>
															<td>
																<xsl:for-each select="../xs:PATIENT/xs:NextofKinAssociatedParties">
																	<xsl:if test="position() = 1">
																		<xsl:for-each select="xs:PhoneNumber">
																			<xsl:if test="position() = 1">
																				<xsl:call-template name="TelephoneFormat">
																					<xsl:with-param name="type">(H)</xsl:with-param>
																				</xsl:call-template>
																			</xsl:if>
																		</xsl:for-each>
																		<xsl:for-each select="xs:BusinessPhoneNumber">
																			<xsl:if test="position() = 1">
																				<xsl:call-template name="TelephoneFormat">
																					<xsl:with-param name="type">(W)</xsl:with-param>
																				</xsl:call-template>
																			</xsl:if>
																		</xsl:for-each>
																	</xsl:if>
																</xsl:for-each>
															</td>
															<td colspan="2">
																<xsl:if test="xs:ObservationRequest/xs:CollectorIdentifier">
																	<xsl:for-each select="xs:ObservationRequest/xs:CollectorIdentifier">
																		<b>Collector:</b>&nbsp;<xsl:call-template name="NameFormat"/>
																		<br/>
																	</xsl:for-each>
																</xsl:if>
																<xsl:if test="xs:ObservationRequest/xs:PrincipalResultInterpreter">
																	<xsl:for-each select="xs:ObservationRequest/xs:PrincipalResultInterpreter/xs:HL7Name">
																		<b>Principal Interpreter:</b>&nbsp;<xsl:call-template name="NameFormat1"/>
																		<br/>
																	</xsl:for-each>
																</xsl:if>
																<xsl:if test="xs:ObservationRequest/xs:AssistantResultInterpreter">
																	<xsl:for-each select="xs:ObservationRequest/xs:AssistantResultInterpreter/xs:HL7Name">
																		<b>Assistant Interpreter:</b>&nbsp;<xsl:call-template name="NameFormat1"/>
																		<br/>
																	</xsl:for-each>
																</xsl:if>
																<xsl:if test="xs:ObservationRequest/xs:Technician">
																	<xsl:for-each select="xs:ObservationRequest/xs:Technician/xs:HL7Name">
																		<b>Technecian:</b>&nbsp;<xsl:call-template name="NameFormat1"/>
																		<br/>
																	</xsl:for-each>
																</xsl:if>
																<xsl:if test="xs:ObservationRequest/xs:Transcriptionist">
																	<xsl:for-each select="xs:ObservationRequest/xs:Transcriptionist/xs:HL7Name">
																		<b>Transcriptionist:</b>&nbsp;<xsl:call-template name="NameFormat1"/>
																		<br/>
																	</xsl:for-each>
																</xsl:if>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="33%">Alternate Contact Relationship</td>
															<td width="66%" colspan="2">Copy to Providers</td>
														</tr>
														<tr class="even1">
															<td class="even1blankbottom" width="2%">&nbsp;</td>
															<td>
																<xsl:value-of select="../xs:PATIENT/xs:NextofKinAssociatedParties/xs:Relationship/xs:HL7Identifier"/>
															</td>
															<td colspan="2">
																<xsl:for-each select="xs:ObservationRequest/xs:ResultCopiesTo">
																	<xsl:call-template name="NameFormat"/>
																	<br/>
																</xsl:for-each>
															</td>
														</tr>
														<tr class="odd1">
															<td class="odd1blank" width="2%">&nbsp;</td>
															<td width="100%" colspan="3">Order Comments</td>
														</tr>
														<tr class="even1">
															<td class="even1blankbottom" width="2%">&nbsp;</td>
															<td colspan="3">
																<xsl:for-each select="xs:NotesAndComments">
																	<xsl:for-each select="xs:HL7Comment">
																		<xsl:value-of select="../xs:HL7Comment"/>
																		<br/>
																	</xsl:for-each>
																</xsl:for-each>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</xsl:if>
								</xsl:for-each>
								<!--End loop for Ordered Test section-->
							</xsl:for-each>
							<!--End loop for Reporting Information section-->
							<div class="bluebarsect" id="rawXML">
								<table class="bluebarsectHeader" role="presentation">
									<tr>
										<td class="bluebarsectName">
											<img src="section_expand.gif" id="rawXML_gif" onclick="toggleDisplay('sec_rawXMLDiv','rawXML_gif');" align="left"/>
											<a class="anchor" tabindex="-1" name="apatientInfo">ELR XML Message</a>
										</td>
									</tr>
								</table>
							</div>
							<div class="graybarsect" id="sec_rawXMLDiv">
								<table id="rawXMLTable">
									<tbody>
										<tr>
											<td style="background:#EFEFEF;">
												<xsl:call-template name="xml-to-string"/>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</xsl:for-each>
					<!--End loop for xs:HL7LabReport-->
				</xsl:for-each>
				<!--End loop for xs:Container-->
			</body>
		</html>
	</xsl:template>
	<!-- Template for displaying Patient and Section Header Information-->
	<xsl:template name="ELRPatient">
		<xsl:param name="label"/>
		<xsl:param name="value"/>
		<xsl:for-each select="xs:HL7PATIENT_RESULT/xs:PATIENT">
			<div class="bluebarsect" id="patientInfo">
				<table class="bluebarsectHeader" role="presentation" id="patientInfoHeaderTable">
					<tr class="headerRow">
						<td class="bluebarsectName">
							<img src="section_collapse.gif" class="img" id="patientInfo_gif{position()}" onclick="toggleDisplay('sec_patientInfo-{position()}','patientInfo_gif{position()}')" align="left"/>
							<a class="anchor" tabindex="-1" name="apatientInfo">Patient Information</a>
						</td>
						<td style="text-align:right;">
							<a class="anchorBack" href="#pageTop" tabindex="-1">Back to top</a>&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div class="graybarsect" id="sec_patientInfo-{position()}">
				<table id="patientBodyTable-{position()}">
					<tr class="odd1">
						<td>Name</td>
						<td>Sex</td>
						<td>Date of Birth</td>
						<td>Age at Observation</td>
					</tr>
					<tr class="even1">
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:PatientName">
								<xsl:call-template name="NameFormat">
									<xsl:with-param name="type">
										<xsl:choose>
											<xsl:when test="xs:HL7NameTypeCode='A'">
												(Alias)
											</xsl:when>
											<xsl:when test="xs:HL7NameTypeCode">
												(<xsl:value-of select="xs:HL7NameTypeCode"/>)
											</xsl:when>
											<xsl:otherwise>
												(Legal)
											</xsl:otherwise>
										</xsl:choose>
									</xsl:with-param>
								</xsl:call-template>
								<xsl:if test="position() != last()">
									<br/>
								</xsl:if>
							</xsl:for-each>
						</td>
						<td>
							<xsl:value-of select="xs:PatientIdentification/xs:AdministrativeSex"/>
						</td>
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:DateTimeOfBirth">
								<xsl:call-template name="dateFormatter"/>
							</xsl:for-each>
						</td>
						<td>
							<xsl:for-each select="xs:ORDER_OBSERVATION/xs:PatientResultOrderSPMObservation/xs:SPECIMEN/xs:ObservationResult">
								<xsl:if test="xs:ObservationIdentifier/xs:HL7Identifier='35659-2'">
									<xsl:value-of select="xs:ObservationValue"/>&nbsp;<xsl:value-of select="xs:Units/xs:HL7Text"/>
								</xsl:if>
							</xsl:for-each>
						</td>
					</tr>
					<tr class="odd1">
						<td>Race</td>
						<td>Ethnicity</td>
						<td>Deceased</td>
						<td>Deceased Time</td>
					</tr>
					<tr class="even1">
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:Race">
								<xsl:call-template name="CodedDescriptionOrCode"/>
								<xsl:if test="position() != last()">
									<br/>
								</xsl:if>
							</xsl:for-each>
						</td>
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:EthnicGroup">
								<xsl:call-template name="CodedDescriptionOrCode"/>
								<xsl:if test="position() != last()">
									<br/>
								</xsl:if>
							</xsl:for-each>
						</td>
						<td>
							<xsl:value-of select="xs:PatientIdentification/xs:PatientDeathIndicator"/>
						</td>
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:PatientDeathDateAndTime">
								<xsl:call-template name="dateTimeFormatter"/>
							</xsl:for-each>
						</td>
					</tr>
					<tr class="odd1">
						<td>Marital Status</td>
						<td>Patient ID</td>
						<td>Address</td>
						<td>Phone</td>
					</tr>
					<tr class="even1">
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:MaritalStatus">
								<xsl:call-template name="CodedDescriptionOrCode"/>
								<xsl:if test="position() != last()">
									<br/>
								</xsl:if>
							</xsl:for-each>
						</td>
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:PatientIdentifierList">
								<xsl:value-of select="xs:HL7IDNumber"/>
								<xsl:if test="xs:HL7AssigningAuthority/xs:HL7NamespaceID!= '' or xs:HL7IdentifierTypeCode!= ''">
									(<xsl:value-of select="xs:HL7IdentifierTypeCode"/>
									<xsl:if test="xs:HL7AssigningAuthority/xs:HL7NamespaceID!= ''">
										<xsl:if test="xs:HL7IdentifierTypeCode!= ''">,</xsl:if>
										<xsl:value-of select="xs:HL7AssigningAuthority/xs:HL7NamespaceID"/>
									</xsl:if>)
									</xsl:if>
								<xsl:if test="position() != last()">
									<br/>
								</xsl:if>
							</xsl:for-each>
							<xsl:if test="xs:PatientIdentification/xs:PatientAccountNumber/xs:HL7IDNumber!=''">
								<br/>
								<xsl:value-of select="xs:PatientIdentification/xs:PatientAccountNumber/xs:HL7IDNumber"/>&nbsp;(<xsl:value-of select="xs:PatientIdentification/xs:PatientAccountNumber/xs:HL7IdentifierTypeCode"/>)
							</xsl:if>
							<xsl:if test="xs:PatientIdentification/xs:MothersIdentifier/xs:HL7IDNumber!=''">
								<br/>
								<xsl:value-of select="xs:PatientIdentification/xs:MothersIdentifier/xs:HL7IDNumber"/>&nbsp;(MO)
							</xsl:if>
						</td>
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:PatientAddress">
								<xsl:if test="position() = 1">
									<xsl:call-template name="AddressFormat"/>
								</xsl:if>
							</xsl:for-each>
						</td>
						<td>
							<xsl:for-each select="xs:PatientIdentification/xs:PhoneNumberHome">
								<xsl:if test="position() = 1">
									<xsl:call-template name="TelephoneFormat">
										<xsl:with-param name="type">(H)</xsl:with-param>
									</xsl:call-template>
								</xsl:if>
							</xsl:for-each>
							<xsl:for-each select="xs:PatientIdentification/xs:PhoneNumberBusiness">
								<xsl:if test="position() = 1">
									<xsl:call-template name="TelephoneFormat">
										<xsl:with-param name="type">(W)</xsl:with-param>
									</xsl:call-template>
								</xsl:if>
							</xsl:for-each>
						</td>
					</tr>
					<tr class="odd1">
						<td colspan="4">Patient Comments</td>
					</tr>
					<tr class="even1">
						<td colspan="4">
							<xsl:for-each select="xs:NotesAndComments">
								<xsl:for-each select="xs:HL7Comment">
									<xsl:value-of select="../xs:HL7Comment"/>
									<br/>
									<br/>
								</xsl:for-each>
							</xsl:for-each>&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</xsl:for-each>
	</xsl:template>
	<!-- Formatting date to mm/dd/yyyy format-->
	<xsl:template name="dateFormatter">
		<xsl:if test="xs:year!=''">
			<xsl:copy>
				<xsl:if test="xs:month &lt; '10'">0</xsl:if>
				<xsl:value-of select="xs:month"/>/<xsl:if test="xs:day &lt; '10'">0</xsl:if>
				<xsl:value-of select="xs:day"/>/<xsl:value-of select="xs:year"/>
			</xsl:copy>
		</xsl:if>
	</xsl:template>
	<!-- Formatting datetime to mm/dd/yyyy HH:MM:SS format-->
	<xsl:template name="dateTimeFormatter">
		<xsl:copy>
			<xsl:if test="xs:month &lt; '10'">0</xsl:if>
			<xsl:value-of select="xs:month"/>/<xsl:if test="xs:day &lt; '10'">0</xsl:if>
			<xsl:value-of select="xs:day"/>/<xsl:value-of select="xs:year"/>&nbsp;<xsl:if test="xs:hours!=''">
				<xsl:if test="xs:hours &lt; '10'">0</xsl:if>
				<xsl:value-of select="xs:hours"/>:<xsl:if test="xs:minutes &lt; '10'">0</xsl:if>
				<xsl:value-of select="xs:minutes"/>
			</xsl:if>
		</xsl:copy>
	</xsl:template>
	<xsl:template name="CodedDescriptionWithCode">
		<xsl:if test="normalize-space(xs:HL7Identifier)!=''">
			<xsl:value-of select="xs:HL7Text"/>
		(<xsl:value-of select="xs:HL7Identifier"/>
		[<xsl:value-of select="xs:HL7NameofCodingSystem"/>])
	</xsl:if>
		<xsl:if test="normalize-space(xs:HL7AlternateIdentifier)!=''">
			<xsl:if test="normalize-space(xs:HL7Text)!=''">/</xsl:if>
			<xsl:value-of select="xs:HL7AlternateText"/>
		(<xsl:value-of select="xs:HL7AlternateIdentifier"/>
		[<xsl:value-of select="xs:HL7NameofAlternateCodingSystem"/>])
	</xsl:if>
	</xsl:template>
	<xsl:template name="CodedDescriptionOrCode">
		<xsl:choose>
			<xsl:when test="normalize-space(xs:HL7Text)!='' or normalize-space(xs:HL7AlternateText)!=''">
				<xsl:value-of select="xs:HL7Text"/>
				<xsl:if test="normalize-space(xs:HL7AlternateText)!=''">
					<xsl:if test="normalize-space(xs:HL7Text)!=''">/</xsl:if>
					<xsl:value-of select="xs:HL7AlternateText"/>
				</xsl:if>
			</xsl:when>
			<xsl:when test="normalize-space(xs:HL7Identifier)!='' or normalize-space(xs:HL7AlternateIdentifier)!=''">
				<xsl:value-of select="xs:HL7Identifier"/>
				<xsl:if test="normalize-space(xs:HL7AlternateIdentifier)!=''">
					<xsl:if test="normalize-space(xs:HL7Identifier)!=''">/</xsl:if>
					<xsl:value-of select="xs:HL7AlternateIdentifier"/>
				</xsl:if>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="NameFormat">
		<xsl:param name="type"/>
		<xsl:value-of select="xs:HL7GivenName"/>
		<xsl:if test="normalize-space(xs:HL7SecondAndFurtherGivenNamesOrInitialsThereof)!=''">
			&nbsp;<xsl:value-of select="xs:HL7SecondAndFurtherGivenNamesOrInitialsThereof"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:HL7FamilyName/xs:HL7Surname)!=''">&nbsp;<xsl:value-of select="xs:HL7FamilyName/xs:HL7Surname"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:HL7Suffix)!=''">,&nbsp;<xsl:value-of select="xs:HL7Suffix"/>
		</xsl:if>
		<xsl:value-of select="$type"/>
	</xsl:template>
	<xsl:template name="NameFormat1">
		<xsl:param name="type"/>
		<xsl:value-of select="xs:HL7GivenName"/>
		<xsl:if test="normalize-space(xs:HL7SecondAndFurtherGivenNamesOrInitialsThereof)!=''">
			&nbsp;<xsl:value-of select="xs:HL7SecondAndFurtherGivenNamesOrInitialsThereof"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:HL7FamilyName)!=''">&nbsp;<xsl:value-of select="xs:HL7FamilyName"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:HL7Suffix)!=''">,&nbsp;<xsl:value-of select="xs:HL7Suffix"/>
		</xsl:if>
		<xsl:value-of select="$type"/>
	</xsl:template>
	<xsl:template name="TelephoneFormat">
		<xsl:param name="type"/>
		<xsl:if test="normalize-space(xs:HL7LocalNumber)!=''">
			<xsl:variable name="localNumber">
				<xsl:value-of select="normalize-space(xs:HL7LocalNumber)"/>
			</xsl:variable>
			(<xsl:value-of select="normalize-space(xs:HL7AreaCityCode)"/>)&nbsp;<xsl:value-of select="normalize-space(concat(substring($localNumber,1,3),'-',substring($localNumber,4,7)))"/>
			<xsl:if test="normalize-space(xs:HL7Extension)!=''">&nbsp;ext.&nbsp;<xsl:value-of select="normalize-space(xs:HL7Extension)"/>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="$type!=''">
					&nbsp;<xsl:value-of select="$type"/>
					<br/>
				</xsl:when>
				<xsl:when test="normalize-space(xs:HL7TelecommunicationUseCode)!=''">
					&nbsp;(<xsl:value-of select="xs:HL7TelecommunicationUseCode"/>)<br/>
				</xsl:when>
				<xsl:otherwise>
					
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		
		
		<xsl:if test="normalize-space(xs:HL7LocalNumber)=''">
			<xsl:if test="normalize-space(xs:HL7AreaCityCode)!=''">
		
				(<xsl:value-of select="normalize-space(xs:HL7AreaCityCode)"/>)&nbsp;<xsl:if test="normalize-space(xs:HL7Extension)!=''">&nbsp;ext.&nbsp;<xsl:value-of select="normalize-space(xs:HL7Extension)"/>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="$type!=''">
						&nbsp;<xsl:value-of select="$type"/>
						<br/>
					</xsl:when>
					<xsl:when test="normalize-space(xs:HL7TelecommunicationUseCode)!=''">
						&nbsp;(<xsl:value-of select="xs:HL7TelecommunicationUseCode"/>)<br/>
					</xsl:when>
					<xsl:otherwise>
						
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>	
		</xsl:if>
	</xsl:template>
	<xsl:template name="AddressFormat">
		<xsl:param name="type"/>
		<xsl:if test="normalize-space(xs:HL7StreetAddress/xs:HL7StreetOrMailingAddress)!=''">
			<xsl:value-of select="xs:HL7StreetAddress/xs:HL7StreetOrMailingAddress"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:HL7OtherDesignation)!=''">,&nbsp;<xsl:value-of select="xs:HL7OtherDesignation"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:HL7City)!='' or normalize-space(HL7StateOrProvince)!='' or normalize-space(xs:HL7ZipOrPostalCode)!=''">
			<br/>
			<xsl:value-of select="xs:HL7City"/>
			<xsl:if test="normalize-space(xs:HL7StateOrProvince)!=''">
				,&nbsp;<xsl:value-of select="xs:HL7StateOrProvince"/>
			</xsl:if>
			&nbsp;<xsl:value-of select="xs:HL7ZipOrPostalCode"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:HL7CountyParishCode)!=''">
			,&nbsp;<xsl:value-of select="xs:HL7CountyParishCode"/>
		</xsl:if>
	</xsl:template>
	<xsl:template name="Susceptibilities">
		<xsl:param name="subID"/>
		<xsl:param name="testCode"/>
		<xsl:param name="parentpos"/>
		<xsl:for-each select="../../../xs:ORDER_OBSERVATION">
			<xsl:variable name="susPos">
				<xsl:value-of select="position()"/>
			</xsl:variable>
			<!--<tr>
		<td><xsl:value-of select="$subID"></xsl:value-of>&nbsp;<xsl:value-of select="$testCode"></xsl:value-of></td>
	</tr>
	<tr>
		<td><xsl:value-of select="xs:ObservationRequest/xs:ParentResult/xs:ParentObservationSubidentifier"></xsl:value-of>&nbsp;<xsl:value-of select="xs:ObservationRequest/xs:ParentResult/xs:ParentObservationIdentifier/xs:HL7Identifier"></xsl:value-of></td>
	</tr>-->
			<xsl:if test="xs:ObservationRequest/xs:ParentResult/xs:ParentObservationSubidentifier=$subID and xs:ObservationRequest/xs:ParentResult/xs:ParentObservationIdentifier/xs:HL7Identifier=$testCode">
				<xsl:for-each select="xs:PatientResultOrderObservation/xs:OBSERVATION">
					<div class="bluebarsect" id="{$parentpos}_susceptibility-{position()}-{$susPos}">
						<table class="bluebarsectHeader" role="presentation">
							<tr>
								<td width="2%" class="bluebarsectNameNoColor">&nbsp;</td>
								<td width="2%" class="bluebarsectNameNoColor">&nbsp;</td>
								<td class="bluebarsectName">
									<img src="section_collapse.gif" class="img" id="{$parentpos}_susceptibility_gif{position()}-{$susPos}" onclick="toggleDisplay('{$parentpos}_susceptibilityDiv-{position()}-{$susPos}','{$parentpos}_susceptibility_gif{position()}-{$susPos}')" align="left"/>
									<a class="anchor" tabindex="-1" name="apatientInfo">Susceptibility Testing</a>
								</td>
							</tr>
						</table>
					</div>
					<div class="graybarsect" id="{$parentpos}_susceptibilityDiv-{position()}-{$susPos}">
						<table id="susceptibilityTable-{position()}-{$susPos}">
							<tbody>
								<tr class="odd1">
									<td class="odd1blanktop" width="2%">&nbsp;</td>
									<td class="odd1blanktop" width="2%">&nbsp;</td>
									<td class="odd1blanktop" width="2%">&nbsp;</td>
									<td width="22%">Method</td>
									<td width="28%">Drug Name</td>
									<td width="15%">Result(s)</td>
									<td width="15%">Interpretive Flag</td>
									<td width="15%">Status</td>
								</tr>
								<tr class="even1">
									<td class="even1blankbottom" width="2%">&nbsp;</td>
									<td class="even1blankbottom" width="2%">&nbsp;</td>
									<td class="even1blankbottom" width="2%">&nbsp;</td>
									<td>
										<xsl:for-each select="xs:ObservationResult/xs:ObservationMethod">
											<xsl:call-template name="CodedDescriptionOrCode"/>
										</xsl:for-each>
									</td>
									<td>
										<xsl:for-each select="xs:ObservationResult/xs:ObservationIdentifier">
											<xsl:call-template name="CodedDescriptionWithCode"/>
										</xsl:for-each>
									</td>
									<td>
										<xsl:value-of select="normalize-space(xs:ObservationResult/xs:ObservationValue)"/>&nbsp;<xsl:value-of select="xs:Units/xs:HL7Text"/>
									</td>
									<td>
										<xsl:for-each select="xs:ObservationResult/xs:AbnormalFlags">
											<xsl:call-template name="CodedDescriptionOrCode"/>
										</xsl:for-each>
									</td>
									<td>
										<xsl:value-of select="xs:ObservationResult/xs:ObservationResultStatus"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</xsl:for-each>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="RenderCodedNumericTextValue">
		<xsl:choose>
			<xsl:when test="normalize-space(xs:Values/xs:Coded/xs:Code)!=''">
				<xsl:value-of select="xs:Values/xs:Coded/xs:CodeDescTxt"/>
			</xsl:when>
			<xsl:when test="normalize-space(xs:HL7Numeric/xs:Value1)!=''">
				<xsl:value-of select="xs:HL7Numeric/xs:ComparatorCode"/>&nbsp;
				<xsl:value-of select="xs:HL7Numeric/xs:Value1"/>&nbsp;
				<xsl:value-of select="xs:HL7Numeric/xs:SeperatorCode"/>&nbsp;
				<xsl:value-of select="xs:HL7Numeric/xs:Value2"/>&nbsp;
				<xsl:value-of select="xs:HL7Numeric/xs:Unit/xs:Code"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="xs:Values/xs:Text"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--===========================================================================================-->
	<xsl:param name="use-empty-syntax" select="true()"/>
	<xsl:param name="exclude-unused-prefixes" select="true()"/>
	<xsl:param name="force-exclude-all-namespaces" select="false()"/>
	<!-- a node-set; each node's string-value
       will be interpreted as a namespace URI to be
       excluded from the serialization. -->
	<xsl:param name="namespaces-to-exclude" select="/.."/>
	<!-- initialized to empty node-set -->
	<xsl:param name="start-tag-start" select="'&lt;'"/>
	<xsl:param name="start-tag-end" select="'>'"/>
	<xsl:param name="empty-tag-end" select="'/>'"/>
	<xsl:param name="end-tag-start" select="'&lt;/'"/>
	<xsl:param name="end-tag-end" select="'>'"/>
	<xsl:param name="space" select="' '"/>
	<xsl:param name="ns-decl" select="'xmlns'"/>
	<xsl:param name="colon" select="':'"/>
	<xsl:param name="equals" select="'='"/>
	<xsl:param name="attribute-delimiter" select="'&quot;'"/>
	<xsl:param name="comment-start" select="'&lt;!--'"/>
	<xsl:param name="comment-end" select="'-->'"/>
	<xsl:param name="pi-start" select="'&lt;?'"/>
	<xsl:param name="pi-end" select="'?>'"/>
	<xsl:template name="xml-to-string">
		<xsl:param name="node-set" select="."/>
		<xsl:apply-templates select="$node-set" mode="xml-to-string">
			<xsl:with-param name="depth" select="1"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="HL7LabReport" name="xml-to-string-root-rule">
		<xsl:call-template name="xml-to-string"/>
	</xsl:template>
	<xsl:template match="HL7LabReportF" mode="xml-to-string">
		<xsl:param name="depth"/>
		<xsl:apply-templates mode="xml-to-string">
			<xsl:with-param name="depth" select="$depth"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="*" mode="xml-to-string">
		<xsl:param name="depth"/>
		<xsl:variable name="element" select="."/>
		<br/>
		<xsl:value-of select="$start-tag-start"/>
		<xsl:call-template name="element-name">
			<xsl:with-param name="text" select="name()"/>
		</xsl:call-template>
		<xsl:apply-templates select="@*" mode="xml-to-string"/>
		<xsl:if test="not($force-exclude-all-namespaces)">
			<xsl:for-each select="namespace::*">
				<xsl:call-template name="process-namespace-node">
					<xsl:with-param name="element" select="$element"/>
					<xsl:with-param name="depth" select="$depth"/>
				</xsl:call-template>
			</xsl:for-each>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="node() or not($use-empty-syntax)">
				<xsl:value-of select="$start-tag-end"/>
				<xsl:apply-templates mode="xml-to-string">
					<xsl:with-param name="depth" select="$depth + 1"/>
				</xsl:apply-templates>
				<xsl:value-of select="$end-tag-start"/>
				<xsl:call-template name="element-name">
					<xsl:with-param name="text" select="name()"/>
				</xsl:call-template>
				<xsl:value-of select="$end-tag-end"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$empty-tag-end"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="process-namespace-node">
		<xsl:param name="element"/>
		<xsl:param name="depth"/>
		<xsl:variable name="declaredAbove">
			<xsl:call-template name="isDeclaredAbove">
				<xsl:with-param name="depth" select="$depth - 1"/>
				<xsl:with-param name="element" select="$element/.."/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="is-used-on-this-element" select="($element    | $element/@*) [namespace-uri() = current()]"/>
		<xsl:variable name="is-used-on-a-descendant" select="($element//* | $element//@*)[namespace-uri() = current()]"/>
		<xsl:variable name="is-unused" select="not($is-used-on-this-element) and
                                           not($is-used-on-a-descendant)"/>
		<xsl:variable name="exclude-ns" select="($is-unused and $exclude-unused-prefixes) or
                                            (. = $namespaces-to-exclude)"/>
		<xsl:variable name="force-include" select="$is-used-on-this-element and (. = $namespaces-to-exclude)"/>
		<xsl:if test="(name() != 'xml') and ($force-include or (not($exclude-ns) and not(string($declaredAbove))))">
			<xsl:value-of select="$space"/>
			<xsl:value-of select="$ns-decl"/>
			<xsl:if test="name()">
				<xsl:value-of select="$colon"/>
				<xsl:call-template name="ns-prefix">
					<xsl:with-param name="text" select="name()"/>
				</xsl:call-template>
			</xsl:if>
			<xsl:value-of select="$equals"/>
			<xsl:value-of select="$attribute-delimiter"/>
			<xsl:call-template name="ns-uri">
				<xsl:with-param name="text" select="string(.)"/>
			</xsl:call-template>
			<xsl:value-of select="$attribute-delimiter"/>
		</xsl:if>
	</xsl:template>
	<xsl:template name="isDeclaredAbove">
		<xsl:param name="element"/>
		<xsl:param name="depth"/>
		<xsl:if test="$depth > 0">
			<xsl:choose>
				<xsl:when test="$element/namespace::*[name(.)=name(current()) and .=current()]">1</xsl:when>
				<xsl:when test="$element/namespace::*[name(.)=name(current())]"/>
				<xsl:otherwise>
					<xsl:call-template name="isDeclaredAbove">
						<xsl:with-param name="depth" select="$depth - 1"/>
						<xsl:with-param name="element" select="$element/.."/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	<xsl:template match="@*" mode="xml-to-string" name="serialize-attribute">
		<xsl:param name="att-value" select="string(.)"/>
		<xsl:value-of select="$space"/>
		<xsl:call-template name="attribute-name">
			<xsl:with-param name="text" select="name()"/>
		</xsl:call-template>
		<xsl:value-of select="$equals"/>
		<xsl:value-of select="$attribute-delimiter"/>
		<xsl:call-template name="attribute-value">
			<xsl:with-param name="text" select="$att-value"/>
		</xsl:call-template>
		<xsl:value-of select="$attribute-delimiter"/>
	</xsl:template>
	<xsl:template match="comment()" mode="xml-to-string">
		<xsl:value-of select="$comment-start"/>
		<xsl:call-template name="comment-text">
			<xsl:with-param name="text" select="string(.)"/>
		</xsl:call-template>
		<xsl:value-of select="$comment-end"/>
	</xsl:template>
	<xsl:template match="processing-instruction()" mode="xml-to-string">
		<xsl:value-of select="$pi-start"/>
		<xsl:call-template name="pi-target">
			<xsl:with-param name="text" select="name()"/>
		</xsl:call-template>
		<xsl:value-of select="$space"/>
		<xsl:call-template name="pi-text">
			<xsl:with-param name="text" select="string(.)"/>
		</xsl:call-template>
		<xsl:value-of select="$pi-end"/>
	</xsl:template>
	<xsl:template match="text()" mode="xml-to-string">
		<xsl:call-template name="text-content">
			<xsl:with-param name="text" select="string(.)"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="element-name">
		<xsl:param name="text"/>
		<xsl:value-of select="$text"/>
	</xsl:template>
	<xsl:template name="attribute-name">
		<xsl:param name="text"/>
		<xsl:value-of select="$text"/>
	</xsl:template>
	<xsl:template name="attribute-value">
		<xsl:param name="text"/>
		<xsl:variable name="escaped-markup">
			<xsl:call-template name="escape-markup-characters">
				<xsl:with-param name="text" select="$text"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$attribute-delimiter = &quot;'&quot;">
				<xsl:call-template name="replace-string">
					<xsl:with-param name="text" select="$escaped-markup"/>
					<xsl:with-param name="replace" select="&quot;'&quot;"/>
					<xsl:with-param name="with" select="'&amp;apos;'"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$attribute-delimiter = '&quot;'">
				<xsl:call-template name="replace-string">
					<xsl:with-param name="text" select="$escaped-markup"/>
					<xsl:with-param name="replace" select="'&quot;'"/>
					<xsl:with-param name="with" select="'&amp;quot;'"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="replace-string">
					<xsl:with-param name="text" select="$escaped-markup"/>
					<xsl:with-param name="replace" select="$attribute-delimiter"/>
					<xsl:with-param name="with" select="''"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="ns-prefix">
		<xsl:param name="text"/>
		<xsl:value-of select="$text"/>
	</xsl:template>
	<xsl:template name="ns-uri">
		<xsl:param name="text"/>
		<xsl:call-template name="attribute-value">
			<xsl:with-param name="text" select="$text"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="text-content">
		<xsl:param name="text"/>
		<xsl:call-template name="escape-markup-characters">
			<xsl:with-param name="text" select="$text"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="pi-target">
		<xsl:param name="text"/>
		<xsl:value-of select="$text"/>
	</xsl:template>
	<xsl:template name="pi-text">
		<xsl:param name="text"/>
		<xsl:value-of select="$text"/>
	</xsl:template>
	<xsl:template name="comment-text">
		<xsl:param name="text"/>
		<xsl:value-of select="$text"/>
	</xsl:template>
	<xsl:template name="escape-markup-characters">
		<xsl:param name="text"/>
		<xsl:variable name="ampEscaped">
			<xsl:call-template name="replace-string">
				<xsl:with-param name="text" select="$text"/>
				<xsl:with-param name="replace" select="'&amp;'"/>
				<xsl:with-param name="with" select="'&amp;amp;'"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="ltEscaped">
			<xsl:call-template name="replace-string">
				<xsl:with-param name="text" select="$ampEscaped"/>
				<xsl:with-param name="replace" select="'&lt;'"/>
				<xsl:with-param name="with" select="'&amp;lt;'"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:call-template name="replace-string">
			<xsl:with-param name="text" select="$ltEscaped"/>
			<xsl:with-param name="replace" select="']]>'"/>
			<xsl:with-param name="with" select="']]&amp;gt;'"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="do-not-escape-markup-characters">
		<xsl:param name="text"/>
		<xsl:call-template name="replace-string">
			<xsl:with-param name="text" select="$text"/>
			<xsl:with-param name="replace" select="'&amp;lt;'"/>
			<xsl:with-param name="with" select="'&lt;'"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="replace-string">
		<xsl:param name="text"/>
		<xsl:param name="replace"/>
		<xsl:param name="with"/>
		<xsl:variable name="stringText" select="string($text)"/>
		<xsl:choose>
			<xsl:when test="contains($stringText,$replace)">
				<xsl:value-of select="substring-before($stringText,$replace)"/>
				<xsl:value-of select="$with"/>
				<xsl:call-template name="replace-string">
					<xsl:with-param name="text" select="substring-after($stringText,$replace)"/>
					<xsl:with-param name="replace" select="$replace"/>
					<xsl:with-param name="with" select="$with"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$stringText"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
