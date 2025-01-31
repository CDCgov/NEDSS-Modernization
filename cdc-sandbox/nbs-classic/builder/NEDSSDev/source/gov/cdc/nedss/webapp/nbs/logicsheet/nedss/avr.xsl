<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsp="http://apache.org/xsp" xmlns:avr="http://www.cdc.gov/nedss/logicsheet/nedss/avr" version="1.0">
	<!-- Strip all whitespace. -->
	<xsl:strip-space elements="*"/>
	<!-- Just call this one.  It aggregates the others so you don't have to call them. -->
	<xsl:template match="avr:import">
		<xsl:call-template name="avr:isOwner"/>
		<xsl:call-template name="avr:getReportTitle"/>
		<xsl:call-template name="avr:isCustom"/>
		<xsl:call-template name="avr:isPublic"/>
		<xsl:call-template name="avr:isReportingFacility"/>
		<xsl:call-template name="avr:startsWithFilter"/>
		<xsl:call-template name="avr:hasFilter"/>
		<xsl:call-template name="avr:isSingle"/>
	</xsl:template>
	<!-- This is the rule-based security engine that reads and caches all static permissions needed for reporting. -->
	<xsl:template match="avr:security">
		<xsp:logic><![CDATA[

            if(so == null)
            {
                httpResponse.sendRedirect("/nbs/login");
            }

            //  Get base permissions from security object.
            boolean booCREATEREPORTPRIVATE          = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPRIVATE);
            boolean booCREATEREPORTPUBLIC           = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTPUBLIC);            
            boolean booCREATEREPORTREPORTINGFACILITY   = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.CREATEREPORTREPORTINGFACILITY);
            boolean booDELETEREPORTPRIVATE          = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPRIVATE);
            boolean booDELETEREPORTPUBLIC           = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPUBLIC);
            boolean booDELETEREPORTREPORTINGFACILITY  = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTREPORTINGFACILITY);
            boolean booEDITREPORTPRIVATE            = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPRIVATE);
            boolean booEDITREPORTPUBLIC             = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTPUBLIC);
            boolean booEDITREPORTREPORTINGFACILITY = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.EDITREPORTREPORTINGFACILITY);
            boolean booEXPORTREPORT                 = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.EXPORTREPORT);
            boolean booRUNREPORT                    = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.RUNREPORT);
            boolean booSELECTFILTERCRITERIAPRIVATE  = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPRIVATE);
            boolean booSELECTFILTERCRITERIAPUBLIC   = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPUBLIC);
            boolean booSELECTFILTERCRITERIATEMPLATE = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIATEMPLATE);
            boolean booSELECTFILTERCRITERIAREPORTINGFACILITY = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAREPORTINGFACILITY);            
            boolean booVIEWREPORTPRIVATE            = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPRIVATE);
            boolean booVIEWREPORTPUBLIC             = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPUBLIC);
            boolean booVIEWREPORTTEMPLATE           = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTTEMPLATE);
            boolean booVIEWREPORTREPORTINGFACILITY  = so.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTREPORTINGFACILITY);

            //  Combine some permissions for convenience.
            boolean booVIEWPRIVATEORPUBLIC          = (booVIEWREPORTPRIVATE || booVIEWREPORTPUBLIC);
            boolean booVIEWREPORT                   = (booVIEWREPORTPRIVATE || booVIEWREPORTPUBLIC || booVIEWREPORTTEMPLATE || booVIEWREPORTREPORTINGFACILITY);
            boolean booSELECTFILTERCRITERIA         = (booSELECTFILTERCRITERIAPRIVATE || booSELECTFILTERCRITERIAPUBLIC || booSELECTFILTERCRITERIATEMPLATE || booSELECTFILTERCRITERIAREPORTINGFACILITY);
            boolean booCREATEREPORT                 = (booCREATEREPORTPUBLIC || booCREATEREPORTPRIVATE || booCREATEREPORTREPORTINGFACILITY);

            //  Create page-level secuirty options.
            boolean booSECreports = booVIEWREPORT;
            boolean booSECbasic = booSELECTFILTERCRITERIA;
            boolean booSECadvanced = booSELECTFILTERCRITERIA;
            boolean booSECcolumn = booSELECTFILTERCRITERIA;
            boolean booSECrun = booRUNREPORT;
            boolean booSECsave = booCREATEREPORT;

            boolean booSEC241 = booCREATEREPORTREPORTINGFACILITY;
            boolean booSEC242 = booDELETEREPORTREPORTINGFACILITY;
            boolean booSEC243 = booEDITREPORTREPORTINGFACILITY;
            boolean booSEC247 = booSELECTFILTERCRITERIAREPORTINGFACILITY;
            boolean booSEC248 = booVIEWREPORTREPORTINGFACILITY;
            
            //  Create element-level security options.
            boolean booSEC141 = booCREATEREPORTPUBLIC;
            boolean booSEC142 = booDELETEREPORTPUBLIC;
            boolean booSEC143 = booEDITREPORTPUBLIC;
            boolean booSEC147 = booSELECTFILTERCRITERIAPUBLIC;
            boolean booSEC148 = booVIEWREPORTPUBLIC;
            boolean booSEC149 = booCREATEREPORTPRIVATE;
            boolean booSEC150 = booDELETEREPORTPRIVATE;
            boolean booSEC151 = booEDITREPORTPRIVATE;
            boolean booSEC155 = booSELECTFILTERCRITERIAPRIVATE;
            boolean booSEC156 = booVIEWREPORTPRIVATE;
            boolean booSEC169 = booSELECTFILTERCRITERIATEMPLATE;
            boolean booSEC170 = booVIEWREPORTTEMPLATE;
            boolean booSEC171 = booRUNREPORT;
            boolean booSEC172 = booEXPORTREPORT;
            boolean booSEC173 = (booSEC148 || booSEC156);   //  This depends on the shared code, so it will need to be recalculated for each element.
            boolean booSEC175 = (booSEC147 || booSEC155 || booSEC169 || booSEC247);
            boolean booSEC176 = (booSEC142 || booSEC150);   //  This depends on the shared code, so it will need to be recalculated for each element.
            boolean booSEC177 = (booSEC148 || booSEC156);
            boolean booSEC178 = booSEC148;
            boolean booSEC179 = booSEC170;
            boolean booSEC180 = (booSEC143 || booSEC151);
            boolean booSEC181 = (booSEC141 || booSEC149 || booSEC241);
            boolean booSEC183 = (booSEC148 || booSEC156);
            boolean booSEC184 = booSEC148;
            boolean booSEC185 = booSEC170;
            boolean booSEC186 = (booSEC147 || booSEC155);   //  This depends on the shared code, so it will need to be recalculated for each element.
            boolean booSEC187 = (booSEC148 || booSEC156 || booSEC170 || booSEC248);
            
            //  Create special logic for radio buttons on save page, since they share the same UID.
            //  If true, select public, otherwise select private.
            boolean booSEC182_public = booCREATEREPORTPUBLIC;
            boolean booSEC182_private = booCREATEREPORTPRIVATE;
            boolean booSEC182_reportingFacility = booCREATEREPORTREPORTINGFACILITY;
            boolean booSEC182 = ( (booSEC182_public) && (booSEC182_reportingFacility) && (!booSEC182_private) ) ? true : false;

        ]]></xsp:logic>
	</xsl:template>
	<xsl:template name="avr:isOwner">
		<xsp:logic><![CDATA[
            /**
             *  Determines whether the user is the owner of the report.
             *  @return true if the user owns the report or false if not.
             */
            private boolean isOwner()
            {
                //  Get data from the server.
                String strPrivateReports = (String)getData(ReportConstantUtil.PRIVATE_REPORT_LIST, true);
                String strPublicReports = (String)getData(ReportConstantUtil.PUBLIC_REPORT_LIST, true);
                String strReportTemplates = (String)getData(ReportConstantUtil.TEMPLATE_REPORT_LIST, true);
                String strReportingFacility = (String)getData(ReportConstantUtil.REPORTING_FACILITY_REPORT_LIST, true);

                NBSSecurityObj sec = (NBSSecurityObj)getData("NBSSecurityObject", true);
                //  Get UID data from the server.
                String strDataSourceUID = (String)getData(ReportConstantUtil.DATASOURCE_UID, true);
                String strReportUID = (String)getData(ReportConstantUtil.REPORT_UID, true);
                //  Parse the XML data into object trees.
                ArrayList alPrivateReports = XMLRequestHelper.getTable(strPrivateReports);
                ArrayList alPublicReports = XMLRequestHelper.getTable(strPublicReports);
                ArrayList alReportTemplates = XMLRequestHelper.getTable(strReportTemplates);
                ArrayList alReportingFacility  = XMLRequestHelper.getTable(strReportingFacility);

                //  Create return value.
                boolean b = false;
                //  Get user ID.
                if(sec == null)
                {
                    return false;
                }
                String strUserID = sec.getEntryID();
                if(strUserID == null)
                {
                    return false;
                }
                //  Create temp variables for looping.
                int x = 0;
                int y = 0;
                String strDSUID = null;
                String strRUID = null;
                String strOwnerID = null;
                ArrayList al = null;
                //  Loop throught all private reports.
                if(b == false)
                {
                    y = alPrivateReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPrivateReports.get(x);
                        //  Get its IDs.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strOwnerID = (String)al.get(7);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strOwnerID.equalsIgnoreCase(strUserID))
                        )
                        {
                            b = true;
                            break;
                        }
                    }
                }
                //  Loop throught all public reports.
                if(b == false)
                {
                    y = alPublicReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPublicReports.get(x);
                        //  Get its IDs.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strOwnerID = (String)al.get(7);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strOwnerID.equalsIgnoreCase(strUserID))
                        )
                        {
                            b = true;
                            break;
                        }
                    }
                }
                //  Loop throught all report templates.
                if(b == false)
                {
                    y = alReportTemplates.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportTemplates.get(x);
                        //  Get its IDs.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strOwnerID = (String)al.get(7);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strOwnerID.equalsIgnoreCase(strUserID))
                        )
                        {
                            b = true;
                            break;
                        }
                    }
                }
                //  Loop throught all reporting facility reports.
                if(b == false)
                {
                    y = alReportingFacility.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportingFacility.get(x);
                        //  Get its IDs.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strOwnerID = (String)al.get(7);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strOwnerID.equalsIgnoreCase(strUserID))
                        )
                        {
                            b = true;
                            break;
                        }
                    }
                }
                
                //  Return the output.
                return b;
            }
        ]]></xsp:logic>
	</xsl:template>
	<xsl:template name="avr:getReportTitle">
		<xsp:logic><![CDATA[
            /**
             *  Gets the title of the current report.
             *  @return the title of the current report.
             */
            private String getReportTitle()
            {
                //  Get data from the server.
                String strPrivateReports = (String)getData(ReportConstantUtil.PRIVATE_REPORT_LIST, true);
                String strPublicReports = (String)getData(ReportConstantUtil.PUBLIC_REPORT_LIST, true);
                String strReportTemplates = (String)getData(ReportConstantUtil.TEMPLATE_REPORT_LIST, true);
                String strReportingFacility = (String)getData(ReportConstantUtil.REPORTING_FACILITY_REPORT_LIST, true);
                //  Get UID data from the server.
                String strDataSourceUID = (String)getData(ReportConstantUtil.DATASOURCE_UID, true);
                String strReportUID = (String)getData(ReportConstantUtil.REPORT_UID, true);
                //  Parse the XML data into object trees.
                ArrayList alPrivateReports = XMLRequestHelper.getTable(strPrivateReports);
                ArrayList alPublicReports = XMLRequestHelper.getTable(strPublicReports);
                ArrayList alReportTemplates = XMLRequestHelper.getTable(strReportTemplates);
                ArrayList alReportingFacility = XMLRequestHelper.getTable(strReportingFacility);
                //  Create return value.
                String strReportTitle = "Report";
                //  Create temp variables for looping.
                int x = 0;
                int y = 0;
                String strDSUID = null;
                String strRUID = null;
                ArrayList al = null;
                boolean booFound = false;
                String strTemp = null;
                //  Loop throught all private reports.
                if(booFound == false)
                {
                    y = alPrivateReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPrivateReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strTemp = (String)al.get(2);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        )
                        {
                            strReportTitle = strTemp;
                            booFound = true;
                            break;
                        }
                    }
                }
                //  Loop throught all public reports.
                if(booFound == false)
                {
                    y = alPublicReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPublicReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strTemp = (String)al.get(2);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        )
                        {
                            strReportTitle = strTemp;
                            booFound = true;
                            break;
                        }
                    }
                }
                //  Loop throught all report templates.
                if(booFound == false)
                {
                    y = alReportTemplates.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportTemplates.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strTemp = (String)al.get(2);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        )
                        {
                            strReportTitle = strTemp;
                            booFound = true;
                            break;
                        }
                    }
                }
                //  Loop throught all Reporting Facility Reports.
                if(booFound == false)
                {
                    y = alReportingFacility.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportingFacility.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strTemp = (String)al.get(2);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        )
                        {
                            strReportTitle = strTemp;
                            booFound = true;
                            break;
                        }
                    }
                }
                
                //  Return the output.
                return strReportTitle;
            }
        ]]></xsp:logic>
	</xsl:template>
	<xsl:template name="avr:isCustom">
		<xsp:logic><![CDATA[
            /**
             *  Determines whether the current report is a custom report or not.
             *  @return true if the current report is of type SAS_CUSTOM or false if not.
             */
            private boolean isCustom()
            {
                //  Get data from the server.
                String strPrivateReports = (String)getData(ReportConstantUtil.PRIVATE_REPORT_LIST, true);
                String strPublicReports = (String)getData(ReportConstantUtil.PUBLIC_REPORT_LIST, true);
                String strReportTemplates = (String)getData(ReportConstantUtil.TEMPLATE_REPORT_LIST, true);
                String strReportingFacilityReports = (String)getData(ReportConstantUtil.REPORTING_FACILITY_REPORT_LIST, true);
                //  Get UID data from the server.
                String strDataSourceUID = (String)getData(ReportConstantUtil.DATASOURCE_UID, true);
                String strReportUID = (String)getData(ReportConstantUtil.REPORT_UID, true);
                //  Parse the XML data into object trees.
                ArrayList alPrivateReports = XMLRequestHelper.getTable(strPrivateReports);
                ArrayList alPublicReports = XMLRequestHelper.getTable(strPublicReports);
                ArrayList alReportTemplates = XMLRequestHelper.getTable(strReportTemplates);
                ArrayList alReportingFacilityReports = XMLRequestHelper.getTable(strReportingFacilityReports);

                //  Create return value.
                boolean booCustom = false;
                //  Create temp variables for looping.
                int x = 0;
                int y = 0;
                String strDSUID = null;
                String strRUID = null;
                String strReportType = null;
                ArrayList al = null;
                //  Loop throught all private reports.
                if(booCustom == false)
                {
                    y = alPrivateReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPrivateReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strReportType = (String)al.get(6);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strReportType.equalsIgnoreCase(ReportConstantUtil.SAS_CUSTOM))
                        )
                        {
                            booCustom = true;
                            break;
                        }
                    }
                }
                //  Loop throught all public reports.
                if(booCustom == false)
                {
                    y = alPublicReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPublicReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strReportType = (String)al.get(6);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strReportType.equalsIgnoreCase(ReportConstantUtil.SAS_CUSTOM))
                        )
                        {
                            booCustom = true;
                            break;
                        }
                    }
                }
                //  Loop throught all report templates.
                if(booCustom == false)
                {
                    y = alReportTemplates.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportTemplates.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strReportType = (String)al.get(6);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strReportType.equalsIgnoreCase(ReportConstantUtil.SAS_CUSTOM))
                        )
                        {
                            booCustom = true;
                            break;
                        }
                    }
                }
                //  Loop throught all Reporting Facility reports.
                if(booCustom == false)
                {
                    y = alReportingFacilityReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportingFacilityReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strReportType = (String)al.get(6);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strReportType.equalsIgnoreCase(ReportConstantUtil.SAS_CUSTOM))
                        )
                        {
                            booCustom = true;
                            break;
                        }
                    }
                }
                //  Return the output.
                return booCustom;
            }
        ]]></xsp:logic>
	</xsl:template>
	<xsl:template name="avr:isPublic">
		<xsp:logic><![CDATA[
		   private boolean isPublic()
            {
                //  Get data from the server.
                String strPrivateReports = (String)getData(ReportConstantUtil.PRIVATE_REPORT_LIST, true);
                String strPublicReports = (String)getData(ReportConstantUtil.PUBLIC_REPORT_LIST, true);
                String strReportTemplates = (String)getData(ReportConstantUtil.TEMPLATE_REPORT_LIST, true);
                String strReportingFacility = (String)getData(ReportConstantUtil.REPORTING_FACILITY_REPORT_LIST, true);

                //  Get UID data from the server.
                String strDataSourceUID = (String)getData(ReportConstantUtil.DATASOURCE_UID, true);
                String strReportUID = (String)getData(ReportConstantUtil.REPORT_UID, true);
                //  Parse the XML data into object trees.
                ArrayList alPrivateReports = XMLRequestHelper.getTable(strPrivateReports);
                ArrayList alPublicReports = XMLRequestHelper.getTable(strPublicReports);
                ArrayList alReportTemplates = XMLRequestHelper.getTable(strReportTemplates);
                ArrayList alReportingFacility = XMLRequestHelper.getTable(strReportingFacility);

                //  Create return value.
                boolean booPublic = false;
                //  Create temp variables for looping.
                int x = 0;
                int y = 0;
                String strDSUID = null;
                String strRUID = null;
                String strShared = null;
                ArrayList al = null;
                //  Loop throught all private reports.
                if(booPublic == false)
                {
                    y = alPrivateReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPrivateReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strShared = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared.equalsIgnoreCase("S"))
                        )
                        {
                            booPublic = true;
                            break;
                        }
                    }
                }
                //  Loop throught all public reports.
                if(booPublic == false)
                {
                    y = alPublicReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPublicReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                       strShared  = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared .equalsIgnoreCase("S"))
                        )
                        {
                            booPublic = true;
                            break;
                        }
                    }
                }
                //  Loop throught all report templates.
                if(booPublic == false)
                {
                    y = alReportTemplates.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportTemplates.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strShared = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared .equalsIgnoreCase("S"))
                        )
                        {
                            booPublic = true;
                            break;
                        }
                    }
                }
                //  Loop throught all Reporting Facility Reports.
                if(booPublic == false)
                {
                    y = alReportingFacility.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportingFacility.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strShared = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared .equalsIgnoreCase("S"))
                        )
                        {
                            booPublic = true;
                            break;
                        }
                    }
                }
                
                //  Return the output.               
                return booPublic;
            }

    
    ]]></xsp:logic>
	</xsl:template>
	
<xsl:template name="avr:isReportingFacility">
		<xsp:logic><![CDATA[
		   private boolean isReportingFacility()
            {
                //  Get data from the server.
                String strPrivateReports = (String)getData(ReportConstantUtil.PRIVATE_REPORT_LIST, true);
                String strPublicReports = (String)getData(ReportConstantUtil.PUBLIC_REPORT_LIST, true);
                String strReportTemplates = (String)getData(ReportConstantUtil.TEMPLATE_REPORT_LIST, true);
                String strReportingFacility = (String)getData(ReportConstantUtil.REPORTING_FACILITY_REPORT_LIST, true);

                //  Get UID data from the server.
                String strDataSourceUID = (String)getData(ReportConstantUtil.DATASOURCE_UID, true);
                String strReportUID = (String)getData(ReportConstantUtil.REPORT_UID, true);
                //  Parse the XML data into object trees.
                ArrayList alPrivateReports = XMLRequestHelper.getTable(strPrivateReports);
                ArrayList alPublicReports = XMLRequestHelper.getTable(strPublicReports);
                ArrayList alReportTemplates = XMLRequestHelper.getTable(strReportTemplates);
                ArrayList alReportingFacility = XMLRequestHelper.getTable(strReportingFacility);

                //  Create return value.
                boolean booReportingFacility = false;
                //  Create temp variables for looping.
                int x = 0;
                int y = 0;
                String strDSUID = null;
                String strRUID = null;
                String strShared = null;
                ArrayList al = null;
                //  Loop throught all private reports.
                if(booReportingFacility == false)
                {
                    y = alPrivateReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPrivateReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strShared = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared.equalsIgnoreCase("R"))
                        )
                        {
                            booReportingFacility = true;
                            break;
                        }
                    }
                }
                //  Loop throught all public reports.
                if(booReportingFacility == false)
                {
                    y = alPublicReports.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alPublicReports.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                       strShared  = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared .equalsIgnoreCase("R"))
                        )
                        {
                            booReportingFacility = true;
                            break;
                        }
                    }
                }
                //  Loop throught all report templates.
                if(booReportingFacility == false)
                {
                    y = alReportTemplates.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportTemplates.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strShared = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared .equalsIgnoreCase("R"))
                        )
                        {
                            booReportingFacility = true;
                            break;
                        }
                    }
                }
                //  Loop throught all Reporting Facility Reports.
                if(booReportingFacility == false)
                {
                    y = alReportingFacility.size();
                    for(x=0; x<y; x++)
                    {
                        //  Get a report.
                        al = (ArrayList)alReportingFacility.get(x);
                        //  Get it's UIDs and report type.
                        strDSUID = (String)al.get(0);
                        strRUID = (String)al.get(1);
                        strShared = (String)al.get(5);
                        //  See if it's the one we're looking for.
                        if
                        (
                            (strDSUID.equalsIgnoreCase(strDataSourceUID))
                        &&  (strRUID.equalsIgnoreCase(strReportUID))
                        &&  (strShared .equalsIgnoreCase("R"))
                        )
                        {
                            booReportingFacility = true;
                            break;
                        }
                    }
                }
                
                //  Return the output.               
                return booReportingFacility;
            }

    
    ]]></xsp:logic>
	</xsl:template>	
	<xsl:template name="avr:startsWithFilter">
		<xsp:logic><![CDATA[
            /**
             *  Determines whether the report has a certain filter.
             *  @param pFilters the list of filters for the current report.
             *  @param pFilterCode the filter code to search for.
             *  @return true if found or false if not.
             */
            private boolean startsWithFilter(ArrayList pFilters, String pFilterCode)
            {
                //  Create return value.
                boolean booFound = false;
                //  Verify parameters.
                if(pFilters == null)
                {
                    return booFound;
                }
                if(pFilterCode == null)
                {
                    return booFound;
                }
                //  Loop throught all filters.
                int x = 0;
                int y = pFilters.size();
                for(x=0; x<y; x++)
                {
                    //  Get a filter.
                    ArrayList al = (ArrayList)pFilters.get(x);
                    //  Get it's code.
                    String strFilterCode = (String)al.get(1);
                    //  See if it's the one we're looking for.
                    if(strFilterCode.startsWith(pFilterCode))
                    {
                        //  If it is, set the Found flag and stop looking.
                        booFound = true;
                        break;
                    }
                }
                //  Return the output.
                return booFound;
            }
        ]]></xsp:logic>
	</xsl:template>
	<xsl:template name="avr:hasFilter">
		<xsp:logic><![CDATA[
            /**
             *  Determines whether the report has a certain filter.
             *  @param pFilters the list of filters for the current report.
             *  @param pFilterCode the filter code to search for.
             *  @return true if found or false if not.
             */
            private boolean hasFilter(ArrayList pFilters, String pFilterCode)
            {
                //  Create return value.
                boolean booFound = false;
                //  Verify parameters.
                if(pFilters == null)
                {
                    return booFound;
                }
                if(pFilterCode == null)
                {
                    return booFound;
                }
                //  Loop throught all filters.
                int x = 0;
                int y = pFilters.size();
                for(x=0; x<y; x++)
                {
                    //  Get a filter.
                    ArrayList al = (ArrayList)pFilters.get(x);
                    //  Get it's code.
                    String strFilterCode = (String)al.get(1);
                    //  See if it's the one we're looking for.
                    if(strFilterCode.startsWith(pFilterCode))
                    {
                        //  If it is, set the Found flag and stop looking.
                        booFound = true;
                        break;
                    }
                }
                //  Return the output.
                return booFound;
            }
        ]]></xsp:logic>
	</xsl:template>
	<xsl:template name="avr:isSingle">
		<xsp:logic><![CDATA[
            /**
             *  Determines whether the specified filter should be displayed
             *  as a dropdown listbox or as a multiselect listbox.
             *  A filter should be displayed as a dropdown listbox if
             *  the max and min are both set to 1.  Otherwise, use a multiselect.
             *  @param pFilters the list of filters for the current report.
             *  @param pFilterCode the filter code to search for.
             *  @return true if dropdown or false if not.
             *  Also returns false if the filter code is not found
             *  in the list of filters for the current report.
             */
            private boolean isSingle(ArrayList pFilters, String pFilterCode)
            {
                //  Create return value.
                boolean booSingle = false;
                //  Verify parameters.
                if(pFilters == null)
                {
                    return booSingle;
                }
                if(pFilterCode == null)
                {
                    return booSingle;
                }
                //  Loop throught all filters.
                int x = 0;
                int y = pFilters.size();
                for(x=0; x<y; x++)
                {
                    //  Get a filter.
                    ArrayList al = (ArrayList)pFilters.get(x);
                    //  Get it's code.
                    String strFilterCode = (String)al.get(1);
                    //  See if it's the one we're looking for.
                    if(strFilterCode.equals(pFilterCode))
                    {
                        //  Check min and max.
                        String strMin = (String)al.get(4);
                        String strMax = (String)al.get(5);
                        //  If they are both set to 1, then
                        //  it's a dropdown, otherwise, it's a multiselect.
                        if
                        (
                            (strMin.equals("1"))
                        &&	  (strMax.equals("1"))
                        )
                        {
                            booSingle = true;
                        }
                        break;
                    } else if(strFilterCode.equals(pFilterCode + "_N"))
                    {
                        //  Check min and max.
                        String strMin = (String)al.get(4);
                        String strMax = (String)al.get(5);
                        //  If they are both set to 1, then
                        //  it's a dropdown, otherwise, it's a multiselect.
                        if
                        (
                            (strMin.equals("1"))
                        &&	  (strMax.equals("1"))
                        )
                        {
                            booSingle = true;
                        }
                        break;
                    }
                }
                //  Return the output.
                return booSingle;
            }
        ]]></xsp:logic>
	</xsl:template>
	<!-- Define root template. -->
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	<!-- Keep all undefined nodes. -->
	<xsl:template match="*|@*|text()|processing-instruction()">
		<xsl:copy>
			<xsl:apply-templates select="*|@*|text()|processing-instruction()"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
