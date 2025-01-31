<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsp="http://apache.org/xsp"
xmlns:nedss="http://www.cdc.gov/nedss/logicsheet/nedss"
version="1.0"
>
<!--  Storing the necessary request parameter to variables -->
   <xsl:template match="nedss:RequestValues">
    	<xsp:logic><![CDATA[

			String typeOfAction = request.getParameter("ContextAction");
			String isThisWorkup = (String)request.getAttribute("workupPage");
			String sCurrTask= request.getAttribute("sCurrTask") == null ? null : (String)request.getAttribute("sCurrTask");
			//System.out.println("Brent's current task is : " + sCurrTask);

		]]></xsp:logic>
    </xsl:template>
  	<xsl:template match="nedss:ViewPerson">
  	<xsp:logic>		
	if(sCurrTask != null) {
	if (sCurrTask.startsWith("<xsl:value-of select="@task"/>"))
     {
   </xsp:logic>
      <xsl:apply-templates/>
 	<xsp:logic>			
	}}
	</xsp:logic>
   </xsl:template>
    <xsl:template match="nedss:SpecifyEntityButtons">
   	<xsp:logic><![CDATA[
				if (typeOfAction != null && typeOfAction.equals("entity")) { ]]></xsp:logic>
          <xsl:apply-templates/>		
       <xsp:logic><![CDATA[
                        } 
                       ]]></xsp:logic>
         </xsl:template>
         <xsl:template match="nedss:SpecifyWorkupButtons">
		<xsp:logic><![CDATA[
	                else { if(isThisWorkup!=null && isThisWorkup.equals("true")){
	                      ]]></xsp:logic>
           <xsl:apply-templates/>		
               <xsp:logic>
			     	}
		      </xsp:logic>
		 </xsl:template>
         <xsl:template match="nedss:SpecifyButtons">
          <xsp:logic>
			  else {
			</xsp:logic>
         <xsl:apply-templates/>		
    	<xsp:logic>}</xsp:logic>
    </xsl:template>
    <xsl:template match="nedss:CloseParanthesis">
          <xsp:logic>
			  }
		</xsp:logic>
      </xsl:template>

    
 <!--Specific for********************Simple_demographic.xsp -->
     <xsl:template match="nedss:FileInstances">
		<xsp:logic><![CDATA[
            LogUtils logger = new LogUtils((this.getClass()).getName());
            NEDSSConstants ncu = new NEDSSConstants();
        ]]></xsp:logic>
	 </xsl:template>
    <xsl:template match="nedss:SpecifyIfAddorCancelButtons">
  	   <xsp:logic><![CDATA[
	         if (typeOfAction != null && ( typeOfAction.equals("Add") || typeOfAction.trim().equals("Cancel"))) { ]]></xsp:logic>
	      <xsl:apply-templates/>		
		<xsp:logic><![CDATA[
                     } 
             ]]></xsp:logic>
         </xsl:template>
          <xsl:template match="nedss:SpecifyWorkupButtonsForSimple">
		<xsp:logic><![CDATA[
	                else { if(isThisWorkup!=null && isThisWorkup.equals("true")){
	                      ]]></xsp:logic>
           <xsl:apply-templates/>		
               <xsp:logic>
			     	}}
		      </xsp:logic>
		 </xsl:template>
	
		<!-- Specific for ***********Person Search************ -->
		<xsl:template match="nedss:getTitle">
		<xsp:logic>
			private String getTitle(){
				String PersonName = (String)request.getAttribute("PersonName");
				//##!! System.out.println("PersonName in Search.xsp = " + PersonName);
				if(PersonName  != null)  {
					request.setAttribute("PersonName", PersonName);
					String displayName = PersonName + " Search Criteria";
					return displayName;
				}	else
					return "Patient Search Criteria";
			}
		</xsp:logic>
		</xsl:template>
		<xsl:template match="nedss:getTitleForResults">
		<xsp:logic>
			private String getTitleForResults(){
				String PersonName = (String)request.getAttribute("PersonName");
				//##!! System.out.println("PersonName in Search.xsp = " + PersonName);
				if(PersonName  != null)  {
					request.setAttribute("PersonName", PersonName);
					String displayName = PersonName + " Search Results";
					return displayName;
				}	else
					return "Patient Search Results";
			}
		</xsp:logic>
		</xsl:template>
		<xsl:template match="nedss:getProviderTitle">
		<xsp:logic>
			private String getProviderTitle(){
				String PersonName = (String)request.getAttribute("PersonName");
				//##!! System.out.println("PersonName in Search.xsp = " + PersonName);
				if(PersonName  != null)  {
					request.setAttribute("PersonName", PersonName);
					String displayName = PersonName + " Search Criteria";
					return displayName;
				}	else
					return "Provider Search Criteria";
			}
		</xsp:logic>
		</xsl:template>
		<xsl:template match="nedss:getProviderTitleForResults">
		<xsp:logic>
			private String getProviderTitleForResults(){
				String PersonName = (String)request.getAttribute("PersonName");
				//##!! System.out.println("PersonName in Search.xsp = " + PersonName);
				if(PersonName  != null)  {
					request.setAttribute("PersonName", PersonName);
					String displayName = PersonName + " Search Results";
					return displayName;
				}	else
					return "Provider Search Results";
			}
		</xsp:logic>
		</xsl:template>


		<xsl:template match="nedss:getName">
		<xsp:logic>
			private String getName() {
			String oT = (String)request.getAttribute("ContextAction");
			if(!oT.equals("EntitySearch"))
				return "Simple Search";
			else
				return "";
		}
		</xsp:logic>
         </xsl:template>
         <xsl:template match="nedss:getNameProvider">
    		<xsp:logic>
			private String getNameProvider() {
			String oT = (String)request.getAttribute("ContextAction");
			if(!oT.equals("EntitySearch"))
				return "Search Criteria";
			else
				return "";
		}
	</xsp:logic>
  </xsl:template>
      <xsl:template match="nedss:storePerName">
         <xsp:logic>
		private String storePerName() {
			//##!! System.out.println("What is the PersonName = " +request.getAttribute("PersonName"));
			return (String)request.getAttribute("PersonName");
		}
         </xsp:logic>
         </xsl:template>
	 
     <xsl:template match="nedss:RequestvaluesFind">
     	<xsp:logic><![CDATA[
				String sDebug = "";

				PatientSearchVO personFind = null;

				String sOperationType=(String)request.getAttribute("ContextAction");
				if(sOperationType==null)
					sOperationType=(String)request.getParameter("ContextAction");
				 personFind = (PatientSearchVO) request.getAttribute("DSSearchCriteria");
		]]></xsp:logic>
     </xsl:template>
     <xsl:template match="nedss:ifEntitySearch">
     	<xsp:logic>if(sOperationType.equals("EntitySearch")) {
		</xsp:logic>
           <xsl:apply-templates/>		
           <xsp:logic> } </xsp:logic>
     </xsl:template>
     <xsl:template match="nedss:PersonFindNotNull">
     <xsp:logic>if (personFind != null) {</xsp:logic>
       <xsl:apply-templates/>		
           <xsp:logic> } </xsp:logic>
     </xsl:template>
     <xsl:template match="nedss:StatusCodeActiveNotNull">
        <xsp:logic>
		if (personFind.getStatusCodeActive() != null)
		{
		</xsp:logic>
		<xsl:apply-templates/>
		<xsp:logic>}</xsp:logic>
     </xsl:template>
     <xsl:template match="nedss:StatusCodeInActiveNotNull">
        <xsp:logic>
		if (personFind.getStatusCodeInActive() != null)
		{
		</xsp:logic>
		<xsl:apply-templates/>
		<xsp:logic>}</xsp:logic>
     </xsl:template>
     <xsl:template match="nedss:StatusCodeSuperCededNotNull">
     <xsp:logic>
		if (personFind.getStatusCodeSuperCeded() != null) 
		{
		</xsp:logic>
		<xsl:apply-templates/>
		<xsp:logic>
		}
	  </xsp:logic>	
     </xsl:template>
     <xsl:template match="nedss:StatusCodeActiveNull">
      <xsp:logic>
		if (personFind.getStatusCodeActive() == null) 
		{
		</xsp:logic>
		<xsl:apply-templates/>
		<xsp:logic>
		}
	  </xsp:logic>	
     </xsl:template>
     <xsl:template match="nedss:StatusCodeSuperCededNull">
      <xsp:logic>
		if (personFind.getStatusCodeSuperCeded() == null) 
		{
		</xsp:logic>
		<xsl:apply-templates/>
		<xsp:logic>
		}
	  </xsp:logic>	
     </xsl:template>
     <xsl:template match="nedss:StatusCodeInActiveNull">
      <xsp:logic>
		if (personFind.getStatusCodeInActive() == null) 
		{
		</xsp:logic>
		<xsl:apply-templates/>
		<xsp:logic>
		}
	  </xsp:logic>	
     </xsl:template>
     <xsl:template match="nedss:ElseClause">
	  <xsp:logic>
		else {
	  </xsp:logic>
		<xsl:apply-templates/>
	  <xsp:logic>
		}
	  </xsp:logic>	
     </xsl:template>
     <xsl:template match="nedss:NotEntitySearch">
	<xsp:logic><![CDATA[if(sOperationType!=null&&!sOperationType.equals("EntitySearch")){]]></xsp:logic>
		<xsl:apply-templates/>
	  <xsp:logic>
		}
	  </xsp:logic>	
     </xsl:template>
    <xsl:template match="nedss:RoleSecurity">
  	<xsp:logic>
		String roleSecurity = "false";
		if (request.getAttribute("roleSecurity") != null)
			roleSecurity = (String)request.getAttribute("roleSecurity");
		if (roleSecurity.equalsIgnoreCase("true")) {
	</xsp:logic>
       <xsl:apply-templates/>
       <xsp:logic>}</xsp:logic>
      </xsl:template>
      <!--*******Specific for SearchResult******** -->
        <xsl:template match="nedss:Declarations">
	   <xsp:logic><![CDATA[

		   	private CachedDropDownValues srtValues = new CachedDropDownValues();
               private TreeMap treemap = srtValues.getStateCodes2("USA");

         	    String entityFirstNm = "";
		    String entityLastNm = "";
		    String entitySuffix = "";
		    String entityDegree = "";

		    String entityEmail = "";
		    String entityPhoneNbr = "";
		    String entityPhoneExt = "";
		    String homePhoneNbr ="";
		    String entityPersonUID = "";
		    String entityPersonParentUID = "";
		    String entityLocalID = "";
		    String entityMiddle ="";
		    String entityCity = "";
		    String entityState = "";
		    String entitystateValue ="";
		    String entityZip = "";
		    String entityAddress1 = "";
		    String entityAddress2 = "";
		    String personSearchResult="";
		    String reporterPersonSearchResult="";
		    
		    String entityCounty ="";
		    String countyList="";
		    
		    String entityEthnicity="";
		    String entityDOB="";
		    String entityAsOfDate="";
		    String entityAge="";
		    String entityAgeUnit="";
		    String entitySex="";
		    String entityDateOfDeath="";

		    String entityMaritalStatus="";
		    String entitySSN="";

		    String entityUnknown="";
		    String entityAmerican ="";
		    String entityAsian="";
		    String entityBlack="";
		    String entityNative="";
		    String entityWhite="";
		    String entityOther="";
		    String entityOtherDesc="";
		    String entityIds="";


         ]]></xsp:logic>
	   </xsl:template>
       <xsl:template match="nedss:StateDescTxt">
	  <xsp:logic>    
		   private String getStateDescTxt(String sStateCd) {

		   		String desc="";
		                  	if (treemap != null){

		                      if (sStateCd!=null){
		                        if (treemap.get(sStateCd)!=null)
		                      	desc = (String)treemap.get(sStateCd);
                                  } 
		                     }
		      			return desc;
		    }
	  </xsp:logic>
	  </xsl:template>
	  <xsl:template match="nedss:SearchResultRequestValues">
	  		<xsp:logic><![CDATA[
				
				
				String sOperationType = request.getParameter("ContextAction");
				if(sOperationType == null)
					sOperationType = request.getAttribute("ContextAction") == null ? "" : (String) request.getAttribute("ContextAction");

				int currentIndex = 0;
				int maxRowCount = 10;
				int numberOfRecords = 0;
				int totalCount = 0;
				//String temp = request.getParameter("currentIndex");


				String temp = (String) request.getAttribute("DSFromIndex");

				String sSearchCriteria = (String) request.getAttribute("DSSearchCriteriaString");

				if (temp!=null) {
					currentIndex = Integer.parseInt(temp);
				}
				ArrayList personList = new ArrayList();
				ArrayList  person = new ArrayList();

				PatientSearchVO personFind = null;

			try {
				numberOfRecords = 0;
				//personList = (ArrayList) request.getSession().getAttribute("DSSearchResults");
				personList = (ArrayList) request.getAttribute("DSSearchResults");

				if(personList != null){				
			
					DisplayPersonList  list = (DisplayPersonList)personList.get(0);
					if (list != null) {
						person = list.getList();
						numberOfRecords = person.size();
						totalCount = list.getTotalCounts();
					}
						//System.out.println("numberOfRecords = " + numberOfRecords);
						//System.out.println("totalCount = " + totalCount);


				}
			}
			catch (Exception e) {
				//##!! System.out.println("error occurred in person_search_results.xsp");
			}


			]]></xsp:logic>
	  </xsl:template>
	   <xsl:template match="nedss:SearchResultForMerge">
	  		<xsp:logic><![CDATA[
				
				
				String sOperationType = request.getParameter("ContextAction");


				int currentIndex = 0;
				int maxRowCount = 10;
				int numberOfRecords = 0;
				int totalCount = 0;

				//String temp = request.getParameter("currentIndex");


				String temp = (String) request.getAttribute("DSFromIndex");

				String sSearchCriteria = (String) request.getAttribute("DSSearchCriteriaString");

				if (temp!=null) {
					currentIndex = Integer.parseInt(temp);
				}
				ArrayList personList = new ArrayList();
				ArrayList  person = new ArrayList();

				PatientSearchVO personFind = null;

			try {
				numberOfRecords = 0;
				//personList = (ArrayList) request.getSession().getAttribute("DSSearchResults");
				personList = (ArrayList) request.getAttribute("DSSearchResults");
                    if(request.getAttribute("ContextTaskName")!=null && request.getAttribute("ContextTaskName").equals("MergeCandidateList1") )
                    {
				if(personList != null){
					DisplayPersonList  list = (DisplayPersonList)personList.get(0);

					if (list != null) {
						person = list.getList();
						numberOfRecords = person.size() ;
						totalCount = list.getTotalCounts();
					}
				}
				}
				else
				{
				   if(personList != null)
				   {
				       person = (ArrayList)personList;
				       numberOfRecords = person.size() ;
				   }
				}
			}
			catch (Exception e) {
				//##!! System.out.println("error occurred in person_search_results.xsp");
			}


			]]></xsp:logic>
	  </xsl:template>
	  <xsl:template match="nedss:DisplayPersonNameArray">
       <xsp:logic><![CDATA[
       
       		ArrayList raceNames = (ArrayList) pvo.getRaceCdColl();	
       			//System.out.println("\n\n\n size of raceNames list " + raceNames.size());
	
			  if( raceNames != null){
			    
			   	for (int j =0; j < raceNames .size(); j++)	{
			   	  PersonRaceDT raceDT = (PersonRaceDT)raceNames .get(j);			   	  
			   	if(raceDT  != null && raceDT .getRaceCd() != null){  
				       if(raceDT.getRaceCd() .equals("U" )){
				  		 entityUnknown="Y" ;			  		
				      }		 
					if(raceDT.getRaceCd() .equals("1002-5" )){
						   entityAmerican ="Y";
					}  
					if(raceDT.getRaceCd().equals("2028-9" )){
						   entityAsian="Y";					
					}
					if(raceDT.getRaceCd() .equals("2054-5" ))		{
						   entityBlack="Y";					
					}
					if(raceDT.getRaceCd().equals("2076-8")){
				   		entityNative="Y";			   		
					}
					if(raceDT.getRaceCd().equals("2106-3"))			   {
					   entityWhite="Y";				   
					}
					if(raceDT.getRaceCd().equals("2131-1")){
					   entityOther="Y";
					   entityOtherDesc=raceDT.getRaceDescTxt();
					  

					}   
					   //System.out.println("new changes now%%%%%%%%%%%%%%%%%%%%%%% " + raceDT.getRaceCd());
			   	  }
				}	
			  }
			ArrayList personNames = (ArrayList) pvo.getPersonNameColl();
	      if(!(personNames == null)) {
		  for (int j =0; j < personNames.size(); j++)	{
			PersonNameDT name = (PersonNameDT) personNames.get(j);
			//	for entity search


			if (name.getNmUseCd()!= null && name.getNmUseCd().trim().equals("Legal")){
			  entityLastNm = name.getLastNm();
			  entityFirstNm = name.getFirstNm();
			  entitySuffix = name.getNmSuffix();
			  entityDegree = name.getNmDegree();
			  entityMiddle = name.getMiddleNm();
			} else {
			  entityLastNm = "";
			  entityFirstNm = "";
			  entitySuffix = "";
			  entityDegree = "";

			}
				]]></xsp:logic>
  <xsl:apply-templates/>				
  	<xsp:logic><![CDATA[
			}}
	]]></xsp:logic>
  </xsl:template>
  <xsl:template match="nedss:PersonRecords">
	<xsp:logic><![CDATA[
		//System.out.println("\n\n\n calling  this is from entityDOB "  );

		for( int i=currentIndex; (i >= currentIndex) && (i < currentIndex + maxRowCount) && (i < numberOfRecords); i++ ) {
			PatientSrchResultVO pvo = (PatientSrchResultVO) person.get(i);

			entityPersonUID = String.valueOf(pvo.getPersonUID());
			entityPersonParentUID = String.valueOf(pvo.getPersonParentUid());
			entityLocalID = pvo.getPersonLocalID();
			
			entityDOB = pvo.getPersonDOB();
			entityEthnicity = pvo.getEthnicGroupInd();
			entityAsOfDate=pvo.getAsOfDateAdmin();
			
		     entityAge=pvo.getAgeReported();
		     entityAgeUnit = pvo.getAgeUnit();
		     
		     
		     entitySex=pvo.getSex();
		     entityDateOfDeath = pvo.getDateOfDeath();
		     entityMaritalStatus=pvo.getMaritalStatusCd();
		     entitySSN=pvo.getSsn();
			//System.out.println("this is from entityEthnicity   "  + entityEthnicity );
   ]]></xsp:logic>
  <xsl:apply-templates/>
  <xsp:logic><![CDATA[
	}
 ]]></xsp:logic>
  </xsl:template>
  
   <xsl:template match="nedss:ProviderRecords">
	<xsp:logic><![CDATA[
		//System.out.println("\n\n\n calling  this is from entityDOB "  );

		for( int i=currentIndex; (i >= currentIndex) && (i < currentIndex + maxRowCount) && (i < numberOfRecords); i++ ) {
			PersonSrchResultVO pvo = (PersonSrchResultVO) person.get(i);

			entityPersonUID = String.valueOf(pvo.getPersonUID());
			entityLocalID = pvo.getPersonLocalID();
			
			//entityDOB = pvo.getPersonDOB();
			//entityEthnicity = pvo.getEthnicGroupInd();
			//System.out.println("this is from entityDOB "  + entityDOB  );
			//System.out.println("this is from entityEthnicity   "  + entityEthnicity );
   ]]></xsp:logic>
  <xsl:apply-templates/>
  <xsp:logic><![CDATA[
	}
 ]]></xsp:logic>
  </xsl:template>
  
  <xsl:template match="nedss:DisplayRevisions">
  	<xsp:logic><![CDATA[

	ArrayList revisions = (ArrayList) pvo.getRevisionColl();
	      if(!(revisions == null)) {
		  for (int r =0; r < revisions.size(); r++)	{
			PatientSrchResultVO revisionPatient = (PatientSrchResultVO) revisions.get(r);
			  	
  	
  	
  	]]></xsp:logic>
  </xsl:template>
  
  <xsl:template match="nedss:MergeList">
	<xsp:logic><![CDATA[
		for( int i=0;i < numberOfRecords; i++ ) {
			PatientSrchResultVO pvo = (PatientSrchResultVO) person.get(i);
                int[] age = DateUtil.ageInYears(StringUtils.stringToStrutsTimestamp(pvo.getPersonDOB()));
                String patientAge  = "";
                if(age[0]!=0) patientAge  = new Integer(age[0]).toString()+" Years         ";
         
          
                else if(age[1]!=0) patientAge  = new Integer(age[1]).toString()+" Months         ";
             
                else if(age[2]!=0) patientAge  = new Integer(age[2]).toString()+" Days        ";
                entityPersonUID = String.valueOf(pvo.getPersonUID());
			entityLocalID = pvo.getPersonLocalID();
		    ]]></xsp:logic>
  <xsl:apply-templates/>
  <xsp:logic><![CDATA[
	}
 ]]></xsp:logic>
  </xsl:template>

  <xsl:template match="nedss:PersonPostalLocators">
  <xsp:logic><![CDATA[

   if(locator.getClassCd()!=null && locator.getClassCd().equals("PST")) {
	   if (locator.getThePostalLocatorDT() != null)	{
		  PostalLocatorDT postal = locator.getThePostalLocatorDT();
		  if (postal != null) { 
		   if (postal.getStreetAddr1() != null || postal.getCityCd() != null || postal.getStateCd() != null || postal.getZipCd() != null)
		   { 
			   entityAddress1 = postal.getStreetAddr1();
			   entityCity = postal.getCityCd();
			   entityState = getStateDescTxt(postal.getStateCd());
			   entityCounty =  postal.getCntyCd();
			   //System.out.println("\n\nlab report entityCounty Tthis is from xsl " + entityCounty );
			   entitystateValue = postal.getStateCd();
			   entityZip = postal.getZipCd();
			   countyList = locator.getLocatorDescTxt();
			 
			   if (postal.getStreetAddr2() != null)
				   entityAddress2 = postal.getStreetAddr2();


  ]]></xsp:logic>
  <xsl:apply-templates/>
  <xsp:logic>
  } }}}
  </xsp:logic>
   </xsl:template>
     <xsl:template match="nedss:PersonTeleLocators">
	<xsp:logic><![CDATA[			
	if(locator!=null && locator.getClassCd()!=null && locator.getClassCd().equals("TELE")) {
		if (locator.getTheTeleLocatorDT() != null)	{
			TeleLocatorDT tele = locator.getTheTeleLocatorDT();
				if( locator.getUseCd() != null && !locator.getUseCd().trim().equals("") && locator.getUseCd().equalsIgnoreCase("Primary Work Place")  && locator.getCd()!=null && !locator.getCd().trim().equals("") && locator.getCd().equalsIgnoreCase("PH") ) {
							entityPhoneNbr = tele.getPhoneNbrTxt();
							entityPhoneExt = tele.getExtensionTxt();
							entityEmail = tele.getEmailAddress();
		
				}
				if( locator.getUseCd() != null && locator.getUseCd().equalsIgnoreCase("Home")  && locator.getCd()!=null && locator.getCd().equalsIgnoreCase("PH") ) {
							homePhoneNbr = tele.getPhoneNbrTxt();
		
				}				
						]]></xsp:logic>
 <xsl:apply-templates/>
 <xsp:logic>
    } }
  </xsp:logic>
  </xsl:template>
  <xsl:template match="nedss:PersonEntityLocators">
	<xsp:logic><![CDATA[	
	ArrayList personLocators = (ArrayList) pvo.getPersonLocatorsColl();
		if(!(personLocators == null)) {
			for (int k =0; k < personLocators.size(); k++)	{
				EntityLocatorParticipationDT locator = (EntityLocatorParticipationDT) personLocators.get(k);
	
	]]></xsp:logic>
	<xsl:apply-templates/>
     <xsp:logic><![CDATA[
		}}
     ]]></xsp:logic>
  </xsl:template>
  <xsl:template match="nedss:PersonIds">
    <xsp:logic><![CDATA[
	
			Collection personIds =  pvo.getPersonIdColl();
			//##!! System.out.println("10001: "+ pvo.getPersonIdColl());
			if(!(personIds == null)) {
				StringBuffer entityIdString = new StringBuffer("");
				for( Iterator anIterator = personIds .iterator(); anIterator.hasNext(); )
				{
					EntityIdDT ids  = (EntityIdDT) anIterator.next();
					if(ids != null  &&  ids.getRootExtensionTxt() != null && ids.getRootExtensionTxt().trim() != null && !(ids.getRootExtensionTxt().trim().equals("")))
				{
	
	]]></xsp:logic>
   <xsl:apply-templates/>
   	<xsp:logic><![CDATA[
				}}}
	]]></xsp:logic>
  </xsl:template>
  <xsl:template match="nedss:IfTypeCdSSN">
  <xsp:logic>
	if (ids.getTypeCd() != null)
	{	
	   // System.out.println("ssn: " +ids.getTypeCd()+ "   " +  tele.getPhoneNbrTxt() + "   " +  ids.getRootExtensionTxt() );
		if((ids.getTypeCd()).equals("Social Security"))
		{
  </xsp:logic>
  <xsl:apply-templates/>
  <xsp:logic>
  }
   	 }
  </xsp:logic>
  </xsl:template>
  <xsl:template match="nedss:RootExtensionTxtIndexOf">
  <xsp:logic>
  if((ids.getRootExtensionTxt())!= null &amp;&amp; ((ids.getRootExtensionTxt()).indexOf("-")!= -1))
   {
     entitySSN=ids.getRootExtensionTxt();
  </xsp:logic>
  <xsl:apply-templates/>
  <xsp:logic>}</xsp:logic>
  </xsl:template>
  <xsl:template match="nedss:RootExtensionTxtSubString">
  <xsp:logic>
  else{
	String newSSN = ids.getRootExtensionTxt().substring(0, 3);
	newSSN = newSSN.concat("-");
	newSSN = newSSN.concat(ids.getRootExtensionTxt().substring(3, 5));
	newSSN = newSSN.concat("-");
	newSSN = newSSN.concat(ids.getRootExtensionTxt().substring(5));
	//System.out.println("The newSSN is "+ newSSN );
	//ids.getTypeCd()= newSSN;
  </xsp:logic>
  <xsl:apply-templates/>
  <xsp:logic>
    }
  </xsp:logic>
  </xsl:template>

	<xsl:template match="nedss:inputEthnicity">
		<xsp:logic>
		if(typeOfAction.startsWith("Add") || typeOfAction.startsWith("Edit")) {
		</xsp:logic>
		<xsl:apply-templates/>							
	</xsl:template>
  	
  	<xsl:template match="nedss:displayEthnicity">
		<xsp:logic><![CDATA[		   	

			} else {			
			CachedDropDownValues srtValues = new CachedDropDownValues();
			String spanishOrigin = (String)request.getAttribute("CDM-spanishOrigin");
			String ethnicity= (String)request.getAttribute("parsedEthnicity");
			if(ethnicity != null)
			{
			StringTokenizer st = new StringTokenizer(ethnicity, "|");
			StringBuffer sb = new StringBuffer("");
			int count = st.countTokens();
			for(int cnt = 0; cnt<count; cnt++) {
				if(cnt == (count-1))
					sb.append(srtValues.getDescForCode("P_ETHN",st.nextToken()));
				else
					sb.append(srtValues.getDescForCode("P_ETHN",st.nextToken())).append(",");
			}		
						
		]]></xsp:logic>
		
		<xsl:apply-templates/>
		<xsp:logic>
		}}
		</xsp:logic>				  
  	</xsl:template>

 </xsl:stylesheet>
