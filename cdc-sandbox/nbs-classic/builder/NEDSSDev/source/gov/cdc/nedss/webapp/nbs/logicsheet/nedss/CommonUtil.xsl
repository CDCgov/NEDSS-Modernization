<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:session="http://apache.org/xsp/session/2.0"
 xmlns:xsp="http://apache.org/xsp" xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss" xmlns:call="http://xml.apache.org/xalan/java/gov.cdc.nedss.util.Utility"
 exclude-result-prefixes="call" version="1.0" >
   
    <xsl:template match="nedss:Include">
          <xsp:include>gov.cdc.nedss.util.*</xsp:include>
		<xsp:include>gov.cdc.nedss.webapp.nbs.diseaseform.util.*</xsp:include>
		<xsp:include>java.util.*</xsp:include>
		<xsp:include>java.sql.Timestamp</xsp:include>
		<xsp:include>gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO</xsp:include>
		<xsp:include>gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO</xsp:include>
		<xsp:include>gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO</xsp:include>
    </xsl:template>
    <xsl:template match="nedss:GetJurisdictionConditions">
          <xsp:logic>
             if(sCurrentTask.startsWith("EditInvestigation")
			|| sCurrentTask.equals("CreateInvestigation2")
			|| sCurrentTask.equals("CreateInvestigation3")
			|| sCurrentTask.equals("CreateInvestigation4")
			|| sCurrentTask.equals("CreateInvestigation5")
			|| sCurrentTask.equals("CreateInvestigation6")
			|| sCurrentTask.equals("CreateInvestigation7")
			|| sCurrentTask.equals("CreateInvestigation8")
			|| sCurrentTask.equals("CreateInvestigation9")	)
		  {
		</xsp:logic>
	      	<xsl:apply-templates/>
		<xsp:logic>
		 }
		</xsp:logic>
	</xsl:template>
	<xsl:template match="nedss:IfMultiple">
		<xsp:logic>
		if (sCurrentTask.startsWith("<xsl:value-of select="@task1"/>") || sCurrentTask.startsWith("<xsl:value-of select="@task2"/>"))
		{
		</xsp:logic>
              <xsl:apply-templates/>
		<xsp:logic>
		}
		</xsp:logic>
	</xsl:template>
     <xsl:template match="nedss:If">
		<xsp:logic>
		if (sCurrentTask.startsWith("<xsl:value-of select="@task"/>"))
		{
		</xsp:logic>
              <xsl:apply-templates/>
		<xsp:logic>
		}
		</xsp:logic>
	</xsl:template>
	<xsl:template match="nedss:ElseIf">
		<xsp:logic>
		else if (sCurrentTask.startsWith("<xsl:value-of select="@task"/>"))
		{
		</xsp:logic>
              <xsl:apply-templates/>
		<xsp:logic>
		}
		</xsp:logic>
	</xsl:template>
	<xsl:template match="nedss:hello">
		  <xsl:variable name="rightNow" select="call:new()" />
		  <xsl:variable name="type">
		        	<xsl:value-of select="./argument"/>
	      </xsl:variable>
	      <xsl:variable name="type1">
		        	<xsl:value-of select="./argument1"/>
	      </xsl:variable>
		 <xsl:value-of select= "call:hello($rightNow,string($type),string($type1))"/>
	</xsl:template>
	<xsl:template match="nedss:FormatDate1" >
	<xsl:variable name="rightNow1" select="call:new()" />
		  <xsl:variable name="type">
		        	<xsl:value-of select="./argument1"/>
	      </xsl:variable>
		 <xsl:value-of select= "call:formatDate($rightNow1,number($type))"/>
       </xsl:template>
       <xsl:template match="nedss:FormatDate" >
		<xsp:logic><![CDATA[
		    private String formatDate(java.sql.Timestamp timestamp)
		    {
				Date date = null;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				if(timestamp != null) date = new Date(timestamp.getTime());
				if(date == null)
				return "";
				else
				return 	formatter.format(date);
		    }
	 	]]></xsp:logic>	
	</xsl:template>
       <xsl:template match="nedss:DateLink" >
		<xsp:logic><![CDATA[
		    private String DateLink(java.sql.Timestamp timestamp)
		    {
				Date date = null;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				if(timestamp != null) date = new Date(timestamp.getTime());
				if(date == null)
				return "No Date";
				else
				return 	formatter.format(date);
		    }
	 	]]></xsp:logic>	
	</xsl:template>	
	<xsl:template match="nedss:CurrentTask">
		<xsp:logic><![CDATA[
             int i = 0;
			String sCurrentTask= request.getParameter("CurrentTask");
			if (sCurrentTask == null || sCurrentTask.trim().length() == 0)
				sCurrentTask =(String) request.getAttribute("CurrentTask");
			if(sCurrentTask == null)
				sCurrentTask="";
				
			String sContextAction= (String)request.getAttribute("ContextAction");
			if(sContextAction==null)
				sContextAction="";
				
			String conditionCdDescTxt = (String)request.getAttribute("conditionCdDescTxt");
			if(conditionCdDescTxt == null)
				conditionCdDescTxt = "";
				
			conditionCdDescTxt += " Investigation";

		
			
      	]]></xsp:logic>
	</xsl:template>
	<xsl:template match="nedss:SortDirections">
		<xsp:logic><![CDATA[
			
	       	Boolean sortDirection = (Boolean) ]]><session:get-attribute name="sortDirection"/><![CDATA[;
		  	String direction = null;
		  	if(sortDirection!=null)
				direction = sortDirection.toString();
		  	else
				direction = "true";
		]]></xsp:logic>
	</xsl:template>
	<xsl:template match="nedss:Index">
		<xsp:logic><![CDATA[
			int currentIndex = 0;
			int maxRowCount = 50;  // number of records to be shown per page
			int numberOfRecords =5;
			String temp = request.getParameter("currentIndex");
			if (temp!=null) 
			{
				currentIndex = Integer.parseInt(temp);
			}
		]]></xsp:logic>		
    </xsl:template>
    <xsl:template match="nedss:NotificationList">
		<xsp:logic><![CDATA[
			ArrayList notificationList = new ArrayList();
			try
			{
				numberOfRecords = 0;
				notificationList= (ArrayList) ]]><session:get-attribute name="notificationSummaryList"/><![CDATA[;
				if(notificationList != null)
				{
				   numberOfRecords = notificationList.size();
				}
		
			}
			catch (Exception e) 
			{
				logger.info("Error is: " + e.toString());
			}
		]]></xsp:logic>
	</xsl:template>
	<xsl:template match ="nedss:LessThanFifteenYears">
	<xsp:logic><![CDATA[	
	
	
		String age15 = "F";	
		Timestamp time = null;
		int age[] = {0,0,0};
		String patientDateOfBirth = (String)request.getAttribute("birthTime");
		if( (patientDateOfBirth != null) && (patientDateOfBirth.trim().equals("")) )
		{
			time = StringUtils.stringToStrutsTimestamp(patientDateOfBirth);
			age = DateUtil.ageInYears(time);
			if(age[0] < 15)
			age15 = "T";
		}	

      	]]></xsp:logic>

	</xsl:template>
	<xsl:template match="nedss:ZeroToSixDays">
	<xsp:logic><![CDATA[
				// display all the Group B Strep only when age 0-6 days abcInd="T"
				String displayAll = "F";
				String sCurrentTask1= request.getParameter("CurrentTask");
				Object abcsObj = request.getAttribute("abcsInd");
				String ageSixDays= (String)request.getAttribute("DOBValidation");
				if(abcsObj != null && abcsObj.equals("T") )
				displayAll = "T";
				
				if(ageSixDays!= null && !ageSixDays.equals("T") && sCurrentTask1!= null && sCurrentTask1.startsWith("ViewInvestigation"))
				displayAll = "F";

		]]></xsp:logic>
	</xsl:template>
	<xsl:template match="nedss:HepSpecific">
	<xsp:logic>
			String ObsIndex = (String)request.getAttribute("ObsIndex");
			if(ObsIndex==null)
			ObsIndex="0";
			int i=27;
			int j=0;
			String sCurrentTask= request.getParameter("CurrentTask");
	</xsp:logic>
	</xsl:template>
</xsl:stylesheet>
