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
   
       div.sect h2.sectHeader {border-width:0px 0px 1px 0px; border-color:#5F8DBF; border-style:solid; 
               padding:0.20em 0em 0.20em 0.25em; text-transform:capitalize; color:#5F8DBF; margin-top:1em;}
       div.sect h2.sectHeader a.anchor {text-decoration:none; /*color:#5F8DBF;*/ color:#a46322}
       div.sect h2.sectHeader span.displayToggleIcon {background:#FFF;}
       
       div.sect table.sectHeader {width:100%; border-width:0px 0px 1px 0px; border-color:#5F8DBF; 
               margin-top:1em; border-style:solid; padding:0.20em 0.20em 0.20em 0.25em;}
               
        div.sect table.sectHeader1 {width:100%; border-width:0px 0px 0px 0px; 
               margin-top:1em; padding:0.20em 0.20em 0.20em 0.25em;}
        div.sect table.sectHeader1 tr td.sectName {color:#151B8D; font-size: 110%; font-weight:bold; text-transform:capitalize;}
        div.sect table.sectHeader1thead tr th.sectName {color:#151B8D; font-size: 110%; font-weight:bold; text-transform:capitalize;}
               
       div.sect table.sectHeader tr td.sectName {color:#a46322; font-size: 110%; font-weight:bold; text-transform:capitalize;}
       div.sect table.sectHeader tr td.sectName a.anchor {text-decoration:none; /*color:#5F8DBF;*/ color:#a46322}
       div.sect div.sectBody {text-align:center; margin-left:1em;}
       table.sectionsToggler, table.subSectionsToggler, table.subSect {width:98%; border-width:0px; }
       table.subSect thead tr {background:#FFF;}
       table.subSect thead tr th {padding:0.15em; font-weight:bold; color:#185394; }
       table.subSect tbody tr {/*background:#F2F2F2;*/ background:#EFF7FF;}
       table.subSect tbody tr td { padding-top:5px; padding-bottom:5px;border-width:1px; }
       table.subSect tbody tr td.fieldName {text-align:left; width:40%; vertical-align:top; padding-right:0.20em; font-weight:bold; border-width:1px; }

       
       table.sectionsToggler, table.subSectionsToggler, table.subSect1 {width:98%; margin:0 auto; margin-top:1em; }
       table.subSect1 thead tr th {padding:0.15em; font-weight:bold; color:#185394; }
       
        table.subSect1 thead tr th.sectName  {padding:0.15em; color:#151B8D; font-size: 110%; font-weight:bold; text-transform:capitalize;}

       
       table.subSect1 tbody tr td { border-style:solid; border-width: 1px; padding-top:5px; padding-bottom:5px;vertical-align:top; border-color: gray; width:30%}
       table.subSect1 tbody tr td.fieldName {padding-left:5px; background:#EFF7FF; border-style:solid; border-width: 1px; text-align:left; width:25%; vertical-align:top; padding-right:0.20em; font-weight:bold; border-color: gray;}
        table.subSect1 tbody tr td.fieldName1 {padding-left:5px; background:#EFF7FF; border-style:solid; border-width: 1px; text-align:left; width:30%; vertical-align:top; padding-right:0.20em; font-weight:bold; border-color: gray;}
        
	table.dtTable, table.privateDtTable {width:100%; border:1px solid #BBB; padding:0.5em; margin:1em auto; margin-top:1em;}
	table.dtTable thead tr th,table.privateDtTable thead tr th {text-decoration:none; border:1px solid #BBB; 	font-weight:bold; background:#EFEFEF; padding:0em; text-align:center;}
	table.dtTable tbody tr td, table.privateDtTable tbody tr td {vertical-align:top;} 
	table.dtTable thead tr th, table.privateDtTable thead tr th {text-decoration:none; border:1px solid #BBB; 	font-weight:bold; background:#EFEFEF; padding:0.1em 0 0.1em 0.1em; text-align:left;}
	table.dtTable tbody tr.odd, table.privateDtTable tbody tr.odd, table.dtTable tbody tr.odd td table tr {background:#FFF;}
	table.dtTable tbody tr.even, table.privateDtTable tbody tr.even, table.dtTable tbody tr.even td table tr {background:#EFF8FF;}
	table.dtTable tbody tr td table tr td {border-width:0;}
	table.dtTable tbody tr td, table.privateDtTable tbody tr td {padding:2px; border-width:0px 1px 1px 0px; border-style:solid; border-color:#DDD;}
	table.dtTable tbody tr td.hoverDescLink a, table.privateDtTable tbody tr td.hoverDescLink a {text-decoration:none; color:#000; cursor:help;}
	table.dtTable tbody tr td.hoverDescLink a:hover, table.privateDtTable tbody tr td.hoverDescLink a:hover {background:#FFE2BF;}

      ]]></STYLE>
				<xsl:text disable-output-escaping="yes"><![CDATA[ 
	<script type =	"text/javascript"> 
		function gotoSection(sectionId)
			{
				var sectionId = "#" + sectionId;
			      window.location = sectionId;
			}	
	</script> 
	]]></xsl:text>
			</head>
			<body>
				<xsl:for-each select="xs:Container">
					<div class="sect" id="case_header">
						<table class="sectHeader1">
							<tr>
								<td class="sectName">Case Report:&nbsp;<xsl:value-of select="xs:Case/xs:Condition/xs:CodeDescTxt"/>
								</td>
								<td/>
							</tr>
						</table>
					</div>
					<div class="view" id="editTuberculosis" style="text-align:center;">
						<table class="sectionsToggler" style="width:100%;">
							<tr>
								<td>
									<ul class="horizontalList">
										<b>Go to: </b>
										<a href="javascript:gotoSection('sect_viewSendingSystemInformation')">Sending System Information</a> | <a href="javascript:gotoSection('sect_viewDocumentHeader')">Document Header</a> | <a href="javascript:gotoSection('sect_viewCommonQuestions')">Common Questions</a> | <a href="javascript:gotoSection('sect_diseaseSpecific')">Disease Specific Questions</a> | <a href="javascript:gotoSection('sect_associatedParticipants')">Associated Participants</a> | <a href="javascript:gotoSection('sect_associatedLabReports')">Associated Lab Reports</a> | <a href="javascript:gotoSection('sect_unstructuredData')">Additional Information</a>
									</ul>
								</td>
							</tr>
						</table>
					</div>
					<div class="sect" id="sect_viewSendingSystemInformation"> 
					      <table class="sectHeader">
								<tr>
									<td class="sectName">Sending System Information</td>
									<td style="text-align:right;">
										<a href="#pageTop" class="backToTopLink">Back to top </a>
									</td>
								</tr>
						</table>
                                 </div>
					<table class="subSect1" id="viewTopHeader">
						<tbody>
							<tr>
								<td class="fieldName">Message Type</td>
								<td>
									<xsl:value-of select="xs:Header/xs:MessageType/xs:CodeDescTxt"/>
								</td>
								<td class="fieldName">Sending System ID</td>
								<td>
									<xsl:value-of select="xs:Header/xs:MessageControlID"/>
								</td>
							</tr>
							<tr>
								<td class="fieldName">Sending Facility</td>
								<td>
									<xsl:value-of select="xs:Header/xs:SendingFacility/xs:NamespaceID"/>
								</td>
								<td class="fieldName">Sending System</td>
								<td>
									<xsl:value-of select="xs:Header/xs:SendingApplication/xs:NamespaceID"/>
								</td>
							</tr>
							<tr>
								<td class="fieldName">Message Creation Date</td>
								<td>
									<xsl:call-template name="dateFormatter">
										<xsl:with-param name="date">
											<xsl:value-of select="xs:Header/xs:CreationTime"/>
										</xsl:with-param>
									</xsl:call-template>
								</td>
								<td class="fieldName">Message Status</td>
								<td>
									<xsl:value-of select="xs:Header/xs:ResultStatus/xs:CodeDescTxt"/>
								</td>
							</tr>
						</tbody>
					</table>
					<xsl:for-each select="xs:Case">
					 <div class="sect" id="sect_viewDocumentHeader"> 
					      <table class="sectHeader">
								<tr>
									<td class="sectName">Document Header</td>
									<td style="text-align:right;">
										<a href="#pageTop" class="backToTopLink">Back to top </a>
									</td>
								</tr>
						</table>
                                 </div>
						<xsl:call-template name="patientAndSectionHeader"/>
						<!-- Starting Case/Common Questions/InvestigationSummary-->
						<div class="sect" id="sect_viewCommonQuestions">
							<table class="sectHeader">
								<tr>
									<td class="sectName">Common Questions</td>
									<td style="text-align:right;">
										<a href="#pageTop" class="backToTopLink">Back to top </a>
									</td>
								</tr>
							</table>
							<table class="subSect1" id="viewCase">
								<thead>
									<tr>
										<th colspan="2">Investigation Summary</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="fieldName1">Sending Facility Jurisdiction</td>
										<td>
											<xsl:value-of select="xs:CommonQuestions/xs:InvestigationInformation/xs:SendingApplicationJurisdiction/xs:CodeDescTxt"/>
										</td>
									</tr>
									<tr>
										<td class="fieldName1">State Case ID</td>
										<td>
											<xsl:value-of select="xs:CommonQuestions/xs:InvestigationInformation/xs:StateCaseID"/>
										</td>
									</tr>
									<tr>
										<td class="fieldName1">Investigation Status</td>
										<td>
											<xsl:value-of select="xs:CommonQuestions/xs:InvestigationInformation/xs:InvestigationStatus/xs:CodeDescTxt"/>
										</td>
									</tr>
									<tr>
										<td class="fieldName1">Investigation Start Date</td>
										<td>
											<xsl:call-template name="dateFormatter">
												<xsl:with-param name="date">
													<xsl:value-of select="xs:CommonQuestions/xs:InvestigationInformation/xs:InvestigationStartDate"/>												</xsl:with-param>
											</xsl:call-template>
										</td>
									</tr>
									<tr>
										<td class="fieldName1">Assigned Investigator</td>
										<td>
											<xsl:for-each select="xs:CommonQuestions/xs:InvestigationInformation/xs:Investigator">
												<xsl:call-template name="displayProvider"/>
											</xsl:for-each>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- Starting Case/Common Questions/ReportingSource-->
						<table class="subSect1" id="viewCase">
							<thead>
								<tr>
									<th colspan="2">Reporting Information</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="fieldName1">Date of Report</td>
									<td>
										<xsl:call-template name="dateFormatter">
												<xsl:with-param name="date">
													<xsl:value-of select="xs:CommonQuestions/xs:ReportingInformation/xs:DateOfReport"/>
												</xsl:with-param>
											</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Reporting Source</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:ReportingInformation/xs:ReportingSourceType/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Reporting Organization</td>
									<td>
										<xsl:for-each select="xs:CommonQuestions/xs:ReportingInformation/xs:ReportingOrganization">
											<xsl:call-template name="displayOrganization"/>
										</xsl:for-each>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Reporting Provider</td>
									<td>
										<xsl:for-each select="xs:CommonQuestions/xs:ReportingInformation/xs:ReportingProvider">
											<xsl:call-template name="displayProvider"/>
										</xsl:for-each>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Earliest Date Reported to County</td>
									<td>
									      <xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ReportingInformation/xs:EarliestDateReportedToCounty"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Earliest Date Reported to State</td>
									<td>
										<xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ReportingInformation/xs:EarliestDateReportedToState"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
							</tbody>
						</table>
						<!-- Starting Case/Common Questions/Clinical Information-->
						<table class="subSect1" id="viewCase">
							<thead>
								<tr>
									<th colspan="2">Clinical Information</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="fieldName1">Physician</td>
									<td>
										<xsl:for-each select="xs:CommonQuestions/xs:ClinicalInformation/xs:Physician">
											<xsl:call-template name="displayProvider"/>
										</xsl:for-each>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Was the Patient Hospitalized</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:WasThePatientHospitalized/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Admission Date</td>
									<td>
										<xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:AdmissionDate"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Discharge Date</td>
									<td>
									<xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:DischargeDate"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Duration of Stay in Days</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:DurationOfStay"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Hospital</td>
									<td>
										<xsl:for-each select="xs:CommonQuestions/xs:ClinicalInformation/xs:Hospital">
											<xsl:call-template name="displayOrganization"/>
										</xsl:for-each>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Diagnosis Date</td>
									<td>
										<xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:DiagnosisDate"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Illness Onset Date</td>
									<td>
									 	<xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:IllnessOnsetDate"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Illness End Date</td>
									<td>
										<xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:IllnessEndDate"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Illness Duration</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:IllnessDuration/xs:Value1"/>&nbsp;<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:IllnessDuration/xs:Unit/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Age at Illness Onset</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:AgeAtIllnessOnset/xs:Value1"/>&nbsp;<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:AgeAtIllnessOnset/xs:Unit/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Is the Patient Pregnant</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:PregnancyStatus/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Did the Patient Die from this Illness</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:DidThePatientDieFromIllness/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Date of Death</td>
									<td>
										<xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:ClinicalInformation/xs:DateOfDeath"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
							</tbody>
						</table>
						<!-- Starting Case/Common Questions/Epidemiologic Information-->
						<table class="subSect1" id="viewCase">
							<thead>
								<tr>
									<th colspan="2">Epidemiologic Information</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="fieldName1">Is this case part of an outbreak?</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:IsThisCasePartOfAnOutbreak/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Outbreak Name</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:OutbreakName/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Where was the disease acquired?</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:WhereWasTheDiseaseAcquired/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Imported Country</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:ImportedCountry/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Imported State</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:ImportedState/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Imported City</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:ImportedCity"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Imported County</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:ImportedCounty/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Transmission Mode</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:TransmissionMode/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Detection Method</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:DetectionMethod/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Confirmation Method</td>
									<td>
										<xsl:for-each select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:ConfirmationMethod">
											<xsl:value-of select="xs:CodeDescTxt"/><xsl:if test="position() != last()"><br/></xsl:if>
										</xsl:for-each>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Confirmation Date</td>
									<td>
									 <xsl:call-template name="dateFormatter">
											<xsl:with-param name="date">
												<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:ConfirmationDate"/></xsl:with-param>
										</xsl:call-template>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">Case Status</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:CaseStatus/xs:CodeDescTxt"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">MMWR Week</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:MMWRWeek"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">MMWR Year</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:MMWRYear"/>
									</td>
								</tr>
								<tr>
									<td class="fieldName1">General Comments</td>
									<td>
										<xsl:value-of select="xs:CommonQuestions/xs:EpidemiologicInformation/xs:GeneralComments"/>
									</td>
								</tr>
							</tbody>
						</table>
						<!-- Starting Case/Disease specific questions-->
						<div class="sect" id="sect_diseaseSpecific">
							<table class="sectHeader">
								<tr>
									<td class="sectName">Disease Specific Questions</td>
									<td style="text-align:right;">
										<a href="#pageTop" class="backToTopLink">Back to top </a>
									</td>
								</tr>
							</table>
							<table class="subSect1" id="viewCase">
								<tbody>
									<xsl:for-each select="xs:DiseaseSpecificQuestions/xs:Observation">
										<tr>
											<td class="fieldName1">
												<xsl:value-of select="xs:Question/xs:CodeDescTxt"/>
											</td>
											<td>
												<xsl:call-template name="AnswerTypes"/>
											</td>
										</tr>
									</xsl:for-each>
								</tbody>
							</table>
						</div>
						<!-- Begining of Participants Table-->
						<div class="sect" id="sect_associatedParticipants">
							<table class="sectHeader">
								<tr>
									<td class="sectName">Associated Participants</td>
									<td style="text-align:right;">
										<a href="#pageTop" class="backToTopLink">Back to top </a>
									</td>
								</tr>
							</table>
							<xsl:call-template name="AdditionalParticipants"/>
						</div>
						<!-- End of Participants Table-->
						<!-- Starting Lab Information-->
						<div class="sect" id="sect_associatedLabReports">
							<table class="sectHeader">
								<tr>
									<td class="sectName">Associated Lab Reports</td>
									<td style="text-align:right;">
										<a href="#pageTop" class="backToTopLink">Back to top </a>
									</td>
								</tr>
							</table>
							<xsl:for-each select="xs:LabReport">
								<xsl:call-template name="patientAndSectionHeader">
									<xsl:with-param name="label" select="'Lab Report'"/>
									<xsl:with-param name="value">
										<xsl:value-of select="xs:SectionHeader/xs:Description"/>
									</xsl:with-param>
								</xsl:call-template>
								<table class="subSect1" id="viewPatient">
									<tbody>
										<tr>
											<td class="fieldName">Order</td>
											<td colspan="3">
												<xsl:value-of select="xs:Tests/xs:RequestedObservation/xs:CodeDescTxt"/>
											</td>
										</tr>
										<tr>
											<td class="fieldName">Filler Order Number</td>
											<td>
												<xsl:value-of select="xs:Tests/xs:FillerOrderNumber"/>
											</td>
											<td class="fieldName">Laboratory Report Date</td>
											<td>
												<xsl:call-template name="dateFormatter">
													<xsl:with-param name="date">
														<xsl:value-of select="xs:Tests/xs:LaboratoryReportDate"/></xsl:with-param>
												</xsl:call-template>
											</td>
										</tr>
										<tr>
											<td class="fieldName">Specimen Collection Date Time</td>
											<td>
												<xsl:call-template name="dateTimeFormatter">
													<xsl:with-param name="datetime">
														<xsl:value-of select="xs:Tests/xs:ObservationDateTime"/></xsl:with-param>
												</xsl:call-template>
											</td>
											<td class="fieldName">Specimen</td>
											<td>
												<xsl:choose>
													<xsl:when test="normalize-space(xs:Tests/xs:Specimen/xs:SpecimenCode/xs:CodeDescTxt)!='' or normalize-space(xs:Tests/xs:Specimen/xs:SpecimenCode/xs:Code)!='' ">
														<xsl:if test="normalize-space(xs:Tests/xs:Specimen/xs:SpecimenCode/xs:CodeDescTxt)!=''">
															<xsl:value-of select="xs:Tests/xs:Specimen/xs:SpecimenCode/xs:CodeDescTxt"/>
															<xsl:if test="normalize-space(xs:Tests/xs:Specimen/xs:SpecimenCode/xs:Code)!=''">&nbsp;(<xsl:value-of select="xs:Tests/xs:Specimen/xs:SpecimenCode/xs:Code"/>)</xsl:if>
														</xsl:if>
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="xs:Tests/xs:Specimen/xs:Description"/>
													</xsl:otherwise>
												</xsl:choose>
											</td>
										</tr>
										<tr>
											<td class="fieldName">Reporting Facility</td>
											<td>
												<xsl:value-of select="xs:Tests/xs:ReportingFacility/xs:Name"/>
												<br/>
												<xsl:value-of select="xs:Tests/xs:ReportingFacility/xs:Telephone/xs:Number"/>
												<xsl:if test="normalize-space(xs:Tests/xs:ReportingFacility/xs:Telephone/xs:Extension)!=''">
													&nbsp;ext.&nbsp;<xsl:value-of select="xs:Tests/xs:ReportingFacility/xs:Telephone/xs:Extension"/>
												</xsl:if>
											</td>
											<td class="fieldName">Ordering Facility</td>
											<td>
												<xsl:value-of select="xs:Tests/xs:OrderingFacility/xs:Name"/>
												<br/>
												<xsl:value-of select="xs:Tests/xs:OrderingFacility/xs:Telephone/xs:Number"/>
												<xsl:if test="normalize-space(xs:Tests/xs:OrderingFacility/xs:Telephone/xs:Extension)!=''">
													&nbsp;ext.&nbsp;<xsl:value-of select="xs:Tests/xs:OrderingFacility/xs:Telephone/xs:Extension"/>
												</xsl:if>
											</td>
										</tr>
										<tr>
											<td class="fieldName">Ordering Physician</td>
											<td>
												<xsl:value-of select="xs:Tests/xs:OrderingProvider/xs:Name/xs:Last"/>&nbsp;<xsl:value-of select="xs:Tests/xs:OrderingProvider/xs:Name/xs:First"/>,&nbsp;<xsl:value-of select="xs:Tests/xs:OrderingProvider/xs:Name/xs:Degree/xs:Code"/>
												<br/>
												<xsl:value-of select="xs:Tests/xs:OrderingProvider/xs:Telephone/xs:Number"/>
												<xsl:if test="normalize-space(xs:Tests/xs:OrderingProvider/xs:Telephone/xs:Extension)!=''">
													&nbsp;ext.&nbsp;<xsl:value-of select="xs:Tests/xs:OrderingProvider/xs:Telephone/xs:Extension"/>
												</xsl:if>
											</td>
											<td class="fieldName">Status</td>
											<td>
												<xsl:value-of select="xs:Tests/xs:ResultStatus/xs:CodeDescTxt"/>
											</td>
										</tr>
										<tr>
											<td class="fieldName">Clinical Information</td>
											<td colspan="3">
												<xsl:value-of select="xs:Tests/xs:ClinicalInformation"/>
											</td>
										</tr>
									</tbody>
								</table>
								<!--Result Section -->
								<table class="subSect" id="viewCase">
									<tbody>
										<tr style="background:#FFF;">
											<td colspan="2">
												<table id="invHistoryTable" class="dtTable">
													<thead>
														<tr>
															<th width="40%">Procedure</th>
															<th>Result</th>
														</tr>
													</thead>
													<tbody>
														<xsl:for-each select="xs:Tests/xs:TestResult">
															<tr style="display:block;" class="odd">
																<td width="25%">
																	<xsl:value-of select="xs:TestResultCode/xs:CodeDescTxt"/>
																	<xsl:if test="normalize-space(xs:ObservationDateTime)!=''">
																		<br/>
																		<br/>
																		<b>Observation Date/Time: </b>&nbsp;<xsl:call-template name="dateTimeFormatter">
																									<xsl:with-param name="datetime">
																										<xsl:value-of select="xs:ObservationDateTime"/></xsl:with-param>
																								        </xsl:call-template>
																	</xsl:if>
																	<xsl:if test="normalize-space(xs:PerformingFacility/xs:Name)!=''">
																		<br/>
																		<br/>
																		<b>Performing Facility:</b> &nbsp;<xsl:value-of select="xs:PerformingFacility/xs:Name"/>
																	</xsl:if>
																</td>
																<td width="70%">
																	<xsl:call-template name="RenderCodedNumericTextValue"/>
																	<br/>
																	<xsl:if test="normalize-space(xs:ReferenceRange/xs:Low)!=''">
																		<br/>
																		<b>Refrence Range: </b>&nbsp;Low:&nbsp;<xsl:value-of select="xs:ReferenceRange/xs:Low"/>&nbsp;&nbsp;High:&nbsp;<xsl:value-of select="xs:ReferenceRange/xs:High"/>
																		<br/>
																	</xsl:if>
																	<xsl:if test="normalize-space(xs:Susceptibility/xs:Drug/xs:Code)!=''">
																		
																		<br/>
																		<b>Susceptibility:</b>
																		<table width="100%">
																			<tbody>
																				<xsl:for-each select="xs:Susceptibility">
																					<tr>
																						<td width="25%">
																							<xsl:value-of select="xs:Drug/xs:CodeDescTxt"/>
																						</td>
																						<td width="50%">
																							<xsl:choose>
																								<xsl:when test="normalize-space(xs:Coded/xs:Code)!=''">
																									<xsl:value-of select="xs:Coded/xs:CodeDescTxt"/>
																								</xsl:when>
																								<xsl:when test="normalize-space(xs:Numeric/xs:Value1)!=''">
																									<xsl:value-of select="xs:Numeric/xs:ComparatorCode"/>&nbsp;
																	 <xsl:value-of select="xs:Numeric/xs:Value1"/>&nbsp;
																	 <xsl:value-of select="xs:Numeric/xs:SeperatorCode"/>&nbsp;
																	 <xsl:value-of select="xs:Numeric/xs:Value2"/>&nbsp;
																	 <xsl:value-of select="xs:Numeric/xs:Unit/xs:Code"/>
																								</xsl:when>
																							</xsl:choose>
																						</td>
																						<td width="25%">&nbsp;<xsl:value-of select="xs:Interpretation/xs:CodeDescTxt"/>
																						</td>
																					</tr>
																				</xsl:for-each>
																			</tbody>
																		</table>
																	</xsl:if>
																	<xsl:if test="normalize-space(xs:Comments)!=''">
																		<br/>
																		<b>Notes:</b> &nbsp;<xsl:value-of select="xs:Comments"/>
																		<!--xsl:for-each select="./xs:Comments"><xsl:value-of select="xs:Comment"/><br/></xsl:for-each-->
																	</xsl:if>
																</td>
															</tr>
														</xsl:for-each>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
								<xsl:call-template name="AdditionalParticipants"/>
							</xsl:for-each>
						</div>
						<!--Unstructured Data Information -->
						<div class="sect" id="sect_unstructuredData">
							<table class="sectHeader">
								<tr>
									<td class="sectName">Additional Information</td>
									<td style="text-align:right;">
										<a href="#pageTop" class="backToTopLink">Back to top </a>
									</td>
								</tr>
							</table>
						</div>
						<xsl:for-each select="xs:Unstructured">
							<xsl:call-template name="patientAndSectionHeader">
								<xsl:with-param name="label">
									<xsl:value-of select="xs:SectionHeader/xs:DocumentType/xs:CodeDescTxt"/>
								</xsl:with-param>
								<xsl:with-param name="value">
									<xsl:value-of select="xs:SectionHeader/xs:Description"/>
								</xsl:with-param>
							</xsl:call-template>
							<!-- Unstructured Questions and Answers-->
							<table class="subSect1" id="viewCase">
								<tbody>
									<xsl:for-each select="xs:Observations/xs:Observation">
										<tr>
											<td class="fieldName1">
												<xsl:value-of select="xs:Question/xs:CodeDescTxt"/>
											</td>
											<td>
												<xsl:call-template name="AnswerTypes"/>
											</td>
										</tr>
									</xsl:for-each>
								</tbody>
							</table>
							<!-- Begining of Participants Table-->
							<xsl:call-template name="AdditionalParticipants"/>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
	<!-- Template for displaying provider Information-->
	<xsl:template name="displayProvider">
		<xsl:value-of select="xs:Name/xs:First"/>&nbsp;<xsl:value-of select="xs:Name/xs:Last"/>
		<xsl:if test="normalize-space(xs:Name/xs:Degree/xs:CodeDescTxt)!=''">,&nbsp;<xsl:value-of select="xs:Name/xs:Degree/xs:Code"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:PostalAddress/xs:StreetAddressOne)!=''">
			<br/>
			<xsl:value-of select="xs:PostalAddress/xs:StreetAddressOne"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:PostalAddress/xs:StreetAddressTwo)!=''">
			<br/>
			<xsl:value-of select="xs:PostalAddress/xs:StreetAddressTwo"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:PostalAddress/xs:City)!='' or normalize-space(xs:PostalAddress/xs:State/xs:CodeDescTxt)!='' or normalize-space(xs:PostalAddress/xs:ZipCode)!=''">
			<br/>
			<xsl:value-of select="xs:PostalAddress/xs:City"/>
			<xsl:if test="normalize-space(xs:PostalAddress/xs:State/xs:CodeDescTxt)!=''">,&nbsp;<xsl:value-of select="xs:PostalAddress/xs:State/xs:CodeDescTxt"/>
			</xsl:if>
			<xsl:if test="normalize-space(xs:PostalAddress/xs:ZipCode)!=''">
			  &nbsp;<xsl:value-of select="xs:PostalAddress/xs:ZipCode"/>
			  </xsl:if>		
		</xsl:if>
		<xsl:if test="normalize-space(xs:Telephone/xs:Number)!=''">
			<br/>
			<xsl:value-of select="xs:Telephone/xs:Number"/>
			<xsl:if test="normalize-space(xs:Telephone/xs:Extension)!=''">
				&nbsp;ext.&nbsp;<xsl:value-of select="xs:Telephone/xs:Extension"/>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<!-- Template for displaying Organization Information-->
	<xsl:template name="displayOrganization">
		<xsl:value-of select="xs:Name"/>
		<xsl:if test="normalize-space(xs:PostalAddress/xs:StreetAddressOne)!=''">
			<br/>
			<xsl:value-of select="xs:PostalAddress/xs:StreetAddressOne"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:PostalAddress/xs:StreetAddressTwo)!=''">
			<br/>
			<xsl:value-of select="xs:PostalAddress/xs:StreetAddressTwo"/>
		</xsl:if>
		<xsl:if test="normalize-space(xs:PostalAddress/xs:City)!='' or normalize-space(xs:PostalAddress/xs:State/xs:CodeDescTxt)!='' or normalize-space(xs:PostalAddress/xs:ZipCode)!=''">
			<br/>
			<xsl:value-of select="xs:PostalAddress/xs:City"/>
			<xsl:if test="normalize-space(xs:PostalAddress/xs:State/xs:CodeDescTxt)!=''">,&nbsp;<xsl:value-of select="xs:PostalAddress/xs:State/xs:CodeDescTxt"/>
			</xsl:if>
			<xsl:if test="normalize-space(xs:PostalAddress/xs:ZipCode)!=''">
			       &nbsp;<xsl:value-of select="xs:PostalAddress/xs:ZipCode"/>
			  </xsl:if>
		</xsl:if>
		<xsl:if test="normalize-space(xs:Telephone/xs:Number)!=''">
			<br/>
			<xsl:value-of select="xs:Telephone/xs:Number"/>
			<xsl:if test="normalize-space(xs:Telephone/xs:Extension)!=''">
				&nbsp;ext.&nbsp;<xsl:value-of select="xs:Telephone/xs:Extension"/>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<!-- Template for displaying Patient and Section Header Information-->
	<xsl:template name="patientAndSectionHeader">
		<xsl:param name="label"/>
		<xsl:param name="value"/>
		<table class="subSect1" id="viewCase">
			<thead>
				<tr>
					<th colspan="4" class="sectName">
						<xsl:value-of select="normalize-space($label)"/>
						<xsl:if test="string-length($label)>0">: &nbsp;</xsl:if>
						<xsl:value-of select="normalize-space($value)"/>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="fieldName">Document Type</td>
					<td>
						<xsl:value-of select="xs:SectionHeader/xs:DocumentType/xs:CodeDescTxt"/>
					</td>
					<td class="fieldName">Sending System Event ID</td>
					<td>
						<xsl:value-of select="xs:SectionHeader/xs:SendingApplicationEventIdentifier"/>
					</td>
				</tr>
				<tr>
					<td class="fieldName">Description</td>
					<td>
						<xsl:value-of select="xs:SectionHeader/xs:Description"/>
					</td>
					<td class="fieldName">Purpose</td>
					<td>
						<xsl:value-of select="xs:SectionHeader/xs:Purpose/xs:CodeDescTxt"/>
					</td>
        			</tr>
			</tbody>
		</table>
		<xsl:for-each select="xs:Patient">
			<table class="subSect1" id="viewPatient">
				<tbody>
					<tr>
						<td class="fieldName">Patient Name</td>
						<td>
							<xsl:value-of select="xs:Name/xs:First"/>&nbsp;<xsl:value-of select="xs:Name/xs:Last"/>
						</td>
						<td class="fieldName">Sending System Patient ID</td>
						<td>
							<xsl:value-of select="xs:SendingApplicationPatientIdentifier"/>
						</td>
					</tr>
					<tr>
						<td class="fieldName">Date of Birth</td>
						<td>
							<xsl:call-template name="dateFormatter">
								<xsl:with-param name="date">
									<xsl:value-of select="xs:DateOfBirth"/></xsl:with-param>
							</xsl:call-template>
						</td>
						<td class="fieldName">Reported Age</td>
						<td>
							<xsl:value-of select="xs:ReportedAge/xs:Value1"/>&nbsp;<xsl:value-of select="xs:ReportedAge/xs:Unit/xs:CodeDescTxt"/>
						</td>
					</tr>
					<tr>
						<td class="fieldName">Race</td>
						<td>
							<xsl:for-each select="xs:Race">
								<xsl:value-of select="xs:CodeDescTxt"/><xsl:if test="position() != last()"><xsl:text>,&nbsp;</xsl:text></xsl:if>
							</xsl:for-each>
						</td>
						<td class="fieldName">Ethnicity</td>
						<td>
							<xsl:value-of select="xs:Ethnicity/xs:CodeDescTxt"/>
						</td>
					</tr>
					<tr>
					      <td class="fieldName">Sex</td>
						<td>
							<xsl:value-of select="xs:Sex/xs:CodeDescTxt"/>
						</td>
						<td class="fieldName">Contact Info</td>
						<td>
							<xsl:if test="normalize-space(xs:PostalAddress/xs:StreetAddressOne)!=''">
								<xsl:value-of select="xs:PostalAddress/xs:StreetAddressOne"/>
							</xsl:if>
							<xsl:if test="normalize-space(xs:PostalAddress/xs:StreetAddressTwo)!=''">
								<br/>
								<xsl:value-of select="xs:PostalAddress/xs:StreetAddressTwo"/>
							</xsl:if>
							<xsl:if test="normalize-space(xs:PostalAddress/xs:City)!='' or normalize-space(xs:PostalAddress/xs:State/xs:CodeDescTxt)!='' or normalize-space(xs:PostalAddress/xs:ZipCode)!=''">
								<br/>
								<xsl:value-of select="xs:PostalAddress/xs:City"/>
								<xsl:if test="normalize-space(xs:PostalAddress/xs:State/xs:CodeDescTxt)!=''">,&nbsp;<xsl:value-of select="xs:PostalAddress/xs:State/xs:CodeDescTxt"/>
								</xsl:if>
								  &nbsp;<xsl:value-of select="xs:PostalAddress/xs:ZipCode"/>
							</xsl:if>
							<xsl:if test="normalize-space(xs:PostalAddress/xs:County/xs:CodeDescTxt)!=''">
									  	<br/><xsl:value-of select="xs:PostalAddress/xs:County/xs:CodeDescTxt"/>
								</xsl:if>
							<xsl:if test="normalize-space(xs:Telephone/xs:Number)!=''">
								<br/>
								<xsl:value-of select="xs:Telephone/xs:Number"/>
								<xsl:if test="normalize-space(xs:Telephone/xs:Extension)!=''">
									&nbsp;ext.&nbsp;<xsl:value-of select="xs:Telephone/xs:Extension"/>
								</xsl:if>
							</xsl:if>
						</td>
					</tr>
				</tbody>
			</table>
		</xsl:for-each>
	</xsl:template>
	<!-- Template for displaying different susceptibility type i.e; text, numeric, coded in LABs -->
	<xsl:template name="RenderCodedNumericTextValue">
		<xsl:choose>
			<xsl:when test="normalize-space(xs:Values/xs:Coded/xs:Code)!=''">
				<xsl:value-of select="xs:Values/xs:Coded/xs:CodeDescTxt"/>
			</xsl:when>
			<xsl:when test="normalize-space(xs:Values/xs:Numeric/xs:Value1)!=''">
				<xsl:value-of select="xs:Values/xs:Numeric/xs:ComparatorCode"/>&nbsp;
	 <xsl:value-of select="xs:Values/xs:Numeric/xs:Value1"/>&nbsp;
	 <xsl:value-of select="xs:Values/xs:Numeric/xs:SeperatorCode"/>&nbsp;
	 <xsl:value-of select="xs:Values/xs:Numeric/xs:Value2"/>&nbsp;
	 <xsl:value-of select="xs:Values/xs:Numeric/xs:Unit/xs:Code"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="xs:Values/xs:Text"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- Template for displaying different answer type i.e; text, numeric, coded, dates -->
	<xsl:template name="AnswerTypes">
		<xsl:if test="xs:Answer/xs:AnswerCode">
			<xsl:for-each select="xs:Answer/xs:AnswerCode">
				<xsl:value-of select="xs:CodeDescTxt"/><xsl:if test="position() != last()"><br/></xsl:if>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="xs:Answer/xs:AnswerNumeric">
			<xsl:value-of select="xs:Answer/xs:AnswerNumeric/xs:ComparatorCode"/>&nbsp;
		<xsl:value-of select="xs:Answer/xs:AnswerNumeric/xs:Value1"/>&nbsp;
		<xsl:value-of select="xs:Answer/xs:AnswerNumeric/xs:SeperatorCode"/>&nbsp;
		<xsl:value-of select="xs:Answer/xs:AnswerNumeric/xs:Value2"/>&nbsp;
		<xsl:value-of select="xs:Answer/xs:AnswerNumeric/xs:Unit/xs:CodeDescTxt"/>
		</xsl:if>
		<xsl:if test="xs:Answer/xs:AnswerDate">
		    <xsl:call-template name="dateFormatter">
				<xsl:with-param name="date">
					<xsl:value-of select="xs:Answer/xs:AnswerDate"/></xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="xs:Answer/xs:AnswerDateTime">
			<xsl:value-of select="xs:Answer/xs:AnswerDateTime"/>
		</xsl:if>
		<xsl:if test="xs:Answer/xs:AnswerText">
			<xsl:value-of select="xs:Answer/xs:AnswerText"/>
		</xsl:if>
	</xsl:template>
	<!-- Formatting date to mm/dd/yyyy format-->
	<xsl:template name="dateFormatter">
	   <xsl:param name="date"/>
		   <xsl:if test="$date!=''">
			    <xsl:copy>
			      <xsl:value-of select="concat(substring($date, 6, 2),'/',substring($date, 9, 2),'/',substring($date, 1, 4))"/>
			    </xsl:copy>
		    </xsl:if>
	  </xsl:template>
	  <!-- Formatting datetime to mm/dd/yyyy HH:MM:SS format-->
	<xsl:template name="dateTimeFormatter">
	   <xsl:param name="datetime"/>
		   <xsl:if test="$datetime!=''">
			    <xsl:copy>
			      <xsl:value-of select="concat(substring($datetime, 6, 2),'/',substring($datetime, 9, 2),'/',substring($datetime, 1, 4),' ',substring($datetime,12,8))"/>
			    </xsl:copy>
		    </xsl:if>
	  </xsl:template>

	<!-- Template for displaying associated participants in tabular form-->
	<xsl:template name="AdditionalParticipants">
		<table class="subSect" id="viewCase">
			<tbody>
				<tr style="background:#FFF;">
					<td colspan="2">
						<table id="invHistoryTable" class="dtTable">
							<thead>
								<tr>
									<th width="40%">Relationship</th>
									<th>Participant Information</th>
								</tr>
							</thead>
							<tbody>
								<xsl:choose>
									<xsl:when test="xs:Participants/xs:Provider or xs:Participants/xs:Organization">
										<xsl:for-each select="xs:Participants/xs:Provider">
											<xsl:if test="position()  mod 2 = 1">
												<tr style="display:block;" class="odd">
													<td>
														<xsl:value-of select="xs:TypeCd/xs:CodeDescTxt"/>
													</td>
													<td>
														<xsl:call-template name="displayProvider"/>
													</td>
												</tr>
											</xsl:if>
											<xsl:if test="position()  mod 2 = 0">
												<tr style="display:block;" class="even">
													<td>
														<xsl:value-of select="xs:TypeCd/xs:CodeDescTxt"/>
													</td>
													<td>
														<xsl:call-template name="displayProvider"/>
													</td>
												</tr>
											</xsl:if>
										</xsl:for-each>
										<xsl:for-each select="xs:Participants/xs:Organization">
											<xsl:if test="position()  mod 2 = 1">
												<tr style="display:block;" class="odd">
													<td>
														<xsl:value-of select="xs:TypeCd/xs:CodeDescTxt"/>
													</td>
													<td>
														<xsl:call-template name="displayOrganization"/>
													</td>
												</tr>
											</xsl:if>
											<xsl:if test="position()  mod 2 = 0">
												<tr style="display:block;" class="even">
													<td>
														<xsl:value-of select="xs:TypeCd/xs:CodeDescTxt"/>
													</td>
													<td>
														<xsl:call-template name="displayOrganization"/>
													</td>
												</tr>
											</xsl:if>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<tr style="display:block;" class="odd">
											<td colspan="2">
										  There are no additional participants provided.
										</td>
										</tr>
									</xsl:otherwise>
								</xsl:choose>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</xsl:template>
</xsl:stylesheet>
