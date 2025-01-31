package gov.cdc.nedss.webapp.nbs.action.iisquery.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.immregistries.smm.tester.connectors.Connector;
import org.immregistries.smm.tester.manager.query.QueryResponse;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.v251.message.QBP_Q11;
import ca.uhn.hl7v2.model.v251.message.RSP_K11;
import ca.uhn.hl7v2.model.v251.segment.ERR;
import ca.uhn.hl7v2.model.v251.segment.MSA;
import ca.uhn.hl7v2.model.v251.segment.MSH;
import ca.uhn.hl7v2.model.v251.segment.QAK;
import ca.uhn.hl7v2.model.v251.segment.QPD;
import ca.uhn.hl7v2.model.v251.segment.RCP;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.parser.XMLParser;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.validation.impl.NoValidation;
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.MessageBuilderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.Hl7ToNBSObjectConverter;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.vaccination.iis.dt.PatientSearchResultDT;
import gov.cdc.nedss.vaccination.iis.dt.VaccinationSearchResultDT;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.iisquery.IISQueryForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

public class IISQueryUtil {
	static final LogUtils logger = new LogUtils(IISQueryUtil.class.getName());
	
	private static Map conceptCdAndCVGMap = new HashMap<String, CodeValueGeneralDT>();
	private static Map conceptCdAndCodeMap = new HashMap<String, String>(); //Key: codeSetNm+conceptCode value: code
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	public static String IIS_SOURCE_TYPE_LOCAL = "LOCAL";
	public static String IIS_SOURCE_TYPE_SYSTEM = "SYSTEM";
	public static String PATIENT = "Patient";
	public static String VACCINE = "Vaccination";
	public static final String NULL = "NULL";
	public static final String DOC_TYPE_CD = "VAC";
	public static final Long VAC_NBS_DOC_META_UID = new Long(1006);
	public static final String ORIGINAL_DOC_TYPE_CD = "Z32";
	public static final String IIS_DOC_TYPE = "Z32";
	public static final String IIS_TARGET_TYPE_CD="VAC";
	public static final String ASSIGNING_AUTH_CD_SSA="SSA";
	public static final String DOC_EVENT_TYPE_CD_VAC = "VAC";
	public static final String NO_ERROR = "NO_ERROR";
	public static final String ERROR_WITHOUT_QUERY_RESPONSE_STATUS = "ERROR_WITHOUT_QUERY_RESPONSE_STATUS";
	public static final String ERROR_RSP_DOC_NM = "ERR_RSP_DOC_NM";
	public static final String ERROR_RSP_DOC_TYPE = "ERR_RSP_DOC_TYPE";
	public static final String ERROR_RSP_SOURCE_NM = "ERR_RSP_SRC_NM";
	public static final String ERROR_RSP_MESSAGE_ID = "ERR_RSP_MSG_ID";
	public static final String ERROR_RSP_ERROR_CODE = "ERR_RSP_ERR_3";
	public static final String ERROR_RSP_USER_MESSAGE = "ERR_RSP_ERR_8";
	public static final String ERROR_SEGMENT = "ERROR_SEGMENT";
	public static final String GENERIC_ERROR_MSG_IN_CASE_OF_EXCEPTION = "<br/><span style=\"font-weight:bold;color:red;\">An issue has been encountered when querying the registry. Please try again, and if the issue persists, please contact your system administrator.</span><br/>";
	public static CachedDropDownValues cdv = new CachedDropDownValues();
	static PortPageUtil portPageUtil = new PortPageUtil();
	
	public static void populatePatientIISQueryPopUp(PatientSearchVO patientSearch, HttpSession session) throws ServletException{
		try{
			Map<Object, Object> personDetailMap = (HashMap<Object, Object>) NBSContext.retrieve(session, "PersonDetails");
			
			if(personDetailMap!=null){
				String firstName = "";
				if(personDetailMap.get("FirstName")!=null)
					firstName = (String) personDetailMap.get("FirstName");
				
				String lastName = "";
				if(personDetailMap.get("LastName")!=null)
					lastName= (String) personDetailMap.get("LastName");
				
				Timestamp dobTimestamp = null;
				if(personDetailMap.get("DOB")!=null)
					dobTimestamp = (Timestamp) personDetailMap.get("DOB");
				   
				patientSearch.setFirstName(firstName);
				patientSearch.setLastName(lastName);
				
				if(dobTimestamp!=null){
			    	Date dob = new Date(dobTimestamp.getTime());
			    	
			    	SimpleDateFormat formatNowDay = new SimpleDateFormat("dd");
				    SimpleDateFormat formatNowMonth = new SimpleDateFormat("MM");
				    SimpleDateFormat formatNowYear = new SimpleDateFormat("YYYY");
				    
				    String day = formatNowDay.format(dob);
				    String month = formatNowMonth.format(dob);
				    String year = formatNowYear.format(dob);
					
					patientSearch.setBirthTimeDay(day);
					patientSearch.setBirthTimeMonth(month);
					patientSearch.setBirthTimeYear(year);
			    }
				
				if(personDetailMap.get("Sex")!=null){
					patientSearch.setCurrentSex((String) personDetailMap.get("Sex"));
				}
				
				if(personDetailMap.get("StreetAddress1")!=null){
					patientSearch.setStreetAddr1((String)personDetailMap.get("StreetAddress1"));
				}
				
				if(personDetailMap.get("City")!=null){
					patientSearch.setCityDescTxt((String)personDetailMap.get("City"));
				}
				
				if(personDetailMap.get("State")!=null){
					patientSearch.setState((String)personDetailMap.get("State"));
				}
				
				if(personDetailMap.get("Zip")!=null){
					patientSearch.setZipCd((String)personDetailMap.get("Zip"));
				}
			}
			
		}catch(Exception ex){
			logger.fatal("Exception: "+ex.getMessage(), ex);
    		throw new ServletException(ex.getMessage());
		}
	}
	
	public static GenericForm translateFromPatientSearchResultDTToGenericSummaryDisplayVO(IISQueryForm iisQueryForm){
		
		GenericForm genericForm = new GenericForm();
		try{
			genericForm.setSearchCriteriaArrayMap(iisQueryForm.getSearchCriteriaArrayMap());
			genericForm.setAttributeMap(iisQueryForm.getAttributeMap());
			genericForm.setQueueDT(iisQueryForm.getQueueDT());
			
			genericForm.setElementColl(iisQueryForm.getPatientSearchList());
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
		}
		return genericForm;
	}
	
	public static QueueDT fillPatientSearchResultQueueDT(){
    	
    	QueueDT queueDT = new QueueDT();

    	//First column: Event Type
    	QueueColumnDT queue = new QueueColumnDT();
    	queue.setColumnId("column1");
    	queue.setColumnName("Registry Patient ID");
    	queue.setBackendId("REGISTRY_PATIENT_ID");
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getRegistryPatientID");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("registryPatientIDLink");
    	queue.setMediaPdfProperty("registryPatientID");
    	queue.setMediaCsvProperty("registryPatientID");
    	queue.setColumnStyle("width:10%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("REGISTRYPATIENTID");
    	queue.setDropdownStyleId("registryPatientID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getRegistryPatientID");
    	queueDT.setColumn1(queue);
    	
    	//Second column: Map Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column2");
    	queue.setColumnName("Patient Name");
    	queue.setBackendId("PATIENT_NAME");
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getPatientName");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("patientNameWithPrefix");
    	queue.setMediaPdfProperty("patientNameWithPrefix");
    	queue.setMediaCsvProperty("patientNameWithPrefix");
    	queue.setColumnStyle("width:10%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("PATIENTNAME");
    	queue.setDropdownStyleId("patientNameID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getPatientName");
    	queueDT.setColumn2(queue);

    	//Third column: From Page Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column3");
    	queue.setColumnName("Age/DOB/Sex");
    	queue.setBackendId("AGE_DBO_SEX");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getAgeDobSex");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("ageDobSex");
    	queue.setMediaPdfProperty("ageDobSex");
    	queue.setMediaCsvProperty("ageDobSex");
    	
    	queue.setColumnStyle("width:10%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("AGEDOBSEX");
    	queue.setDropdownStyleId("ageDobSexID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getAgeDobSex");
    	queueDT.setColumn3(queue);

    	//Four column: To Page Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column4");
    	queue.setColumnName("Address");
    	queue.setBackendId("PATIENT_ADDRESS");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getAddress");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("address");
    	queue.setMediaPdfProperty("address");
    	queue.setMediaCsvProperty("address");
    	queue.setColumnStyle("width:11%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("PATIENTADDRESS");
    	queue.setDropdownStyleId("addressID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getAddress");
    	queueDT.setColumn4(queue);
    	
    	
    	//Five column: To Page Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column5");
    	queue.setColumnName("Phone");
    	queue.setBackendId("PATIENT_PHONE");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getPhone");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("phone");
    	queue.setMediaPdfProperty("phone");
    	queue.setMediaCsvProperty("phone");
    	queue.setColumnStyle("width:9%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("PATIENTPHONE");
    	queue.setDropdownStyleId("phoneID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getPhone");
    	queueDT.setColumn5(queue);
    	
    	//Sixth column: To Page Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column6");
    	queue.setColumnName("Mother's Name");
    	queue.setBackendId("MOTHER_NAME");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getMotherName");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("motherName");
    	queue.setMediaPdfProperty("motherName");
    	queue.setMediaCsvProperty("motherName");
    	queue.setColumnStyle("width:11%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("MOTHERNAME");
    	queue.setDropdownStyleId("motherNameID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getMotherName");
    	queueDT.setColumn6(queue); 
    	
    	return queueDT;
    	
    }
	
	public static void extractPIDandProcess(String queryResponseStr, Collection<Object> patientSearchList) throws NEDSSAppException{
		try{
			
			//If response has an XML, then correct it.
			if(queryResponseStr.indexOf("<PID.11>")>0){
				queryResponseStr = replaceXMLwithHL7(queryResponseStr);
			}
			
			if(queryResponseStr.indexOf("PID|")>0){
				String mshSegment = queryResponseStr.substring(0,queryResponseStr.indexOf("PID|"));
				logger.info("mshSegment:\n"+mshSegment);
				String pids = queryResponseStr.substring(queryResponseStr.indexOf("PID|"));
				logger.info("Extracted pid from original message:\n"+pids);
				
				while(pids.indexOf("PID|",10)>=0){
					String singlePID = pids.substring(pids.indexOf("PID|"), pids.indexOf("PID|",10));
					logger.info("singlePID: "+singlePID);
					parseAndPopulatePatientSearchList(mshSegment+singlePID,patientSearchList);
					pids = pids.substring(pids.indexOf("PID|",10));
				}
				
				logger.info("Last pid: "+pids);
				
				parseAndPopulatePatientSearchList(mshSegment+pids,patientSearchList);
			}
			
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * 
	 * <PID.11><PID.11.1>1326 S 400 E</PID.11.1><PID.11.2/><PID.11.3>SPRINGVILLE</PID.11.3><PID.11.4>UT</PID.11.4><PID.11.5>84663</PID.11.5><PID.11.6/><PID.11.7/><PID.11.8/><PID.11.9/><PID.11.10/><PID.11.11/></PID.11>
	 * to
	 * 1326 S 400 E^^SPRINGVILLE^UT^84663
	 * @param queryResponseStr
	 * @return
	 * @throws NEDSSAppException
	 */
	
	private static String replaceXMLwithHL7(String queryResponseStr) throws NEDSSAppException{
		try{
			//For Utah address returns in <PID.11><PID.11.1>738 E 300 S</PID.11.1><PID.11.2>Apt 1111</PID.11.2><PID.11.3>SPANISH FORK</PID.11.3><PID.11.4>UT</PID.11.4><PID.11.5>84660</PID.11.5><PID.11.6/><PID.11.7/><PID.11.8/><PID.11.9/><PID.11.10/><PID.11.11/></PID.11> format.
			//Converting it to regular HL7 format.
			
			String updatedMessage = queryResponseStr.replaceAll("<PID.11><PID.11.1>", "");
			updatedMessage = updatedMessage.replaceAll("</PID.11.1><PID.11.2/><PID.11.3>","^^"); //If streetAddress2 does not exist
			updatedMessage = updatedMessage.replaceAll("</PID.11.1><PID.11.2>","^");
			updatedMessage = updatedMessage.replaceAll("</PID.11.2><PID.11.3>","^");
			updatedMessage = updatedMessage.replaceAll("</PID.11.3><PID.11.4>","^");
			updatedMessage = updatedMessage.replaceAll("</PID.11.4><PID.11.5>","^");
			updatedMessage = updatedMessage.replaceAll("</PID.11.5><PID.11.6/><PID.11.7/><PID.11.8/><PID.11.9/><PID.11.10/><PID.11.11/></PID.11>", ""); //If country does not exist
			updatedMessage = updatedMessage.replaceAll("</PID.11.5><PID.11.6>","^");
			updatedMessage = updatedMessage.replaceAll("</PID.11.6><PID.11.7/><PID.11.8/><PID.11.9/><PID.11.10/><PID.11.11/></PID.11>", "");
			
			return updatedMessage;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static void parseAndPopulatePatientSearchList(String queryResponseStr, Collection<Object> patientSearchList) throws NEDSSAppException{
		try{
			String lastName="";
			String firstName="";
			String middleName="";
			
			Parser p = new PipeParser();
	     	p.setValidationContext(new NoValidation());
	     	String responseMsgToParse = queryResponseStr.replaceAll("\n", "\r\n");
	     	logger.info("responseMsgToParse: "+responseMsgToParse);
	     	Message hapiMsg = (RSP_K11) p.parse(responseMsgToParse);
	        
	     	Terser t = new Terser(hapiMsg);
	     	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	     	DateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
	     	
     		if(t.get("/PID-5-1")!=null){
	     		PatientSearchResultDT patient = new PatientSearchResultDT();
	     		patient.setRegistryPatientID(t.get("/PID-3-1"));
	     		
	     		lastName = t.get("/PID-5-1")!=null?t.get("/PID-5-1"):"";
	     		firstName = t.get("/PID-5-2")!=null?t.get("/PID-5-2"):"";
	     		middleName= t.get("/PID-5-3")!=null?t.get("/PID-5-3"):"";
	     		
	     		patient.setPatientName(t.get("/PID-5-2")+" "+t.get("/PID-5-1"));
	     		patient.setPatientNameWithPrefix("<b>Legal</b></br>"+lastName+", "+firstName+" "+middleName);
	     		
	     		String motherLastName = t.get("/PID-6-1")!=null?t.get("/PID-6-1"):"";
	     		String motherFirstName = t.get("/PID-6-2")!=null?t.get("/PID-6-2"):"";
	     		if(motherLastName.length()>0 || motherFirstName.length()>0){
	     			patient.setMotherName("<b>Legal</b></br>"+motherFirstName+" "+motherLastName);
	     			patient.setMotherMaidenLastName(motherLastName);
	     			patient.setMotherMaidenFirstName(motherFirstName);
	     		}
	     		
	     		
	     		String phoneTypeCd = t.get("/PID-13-3")!=null?t.get("/PID-13-3"):"";
	     		patient.setPhoneTypeCd(phoneTypeCd);
	     		
	     		String phoneNbr = "";
	     		if(t.get("/PID-13-6")!=null){
	     			phoneNbr = t.get("/PID-13-6");
	     		}
	     		if(t.get("/PID-13-7")!=null){
	     			phoneNbr = phoneNbr + t.get("/PID-13-7");
	     		}
	     		if(phoneNbr.length()>0){
	     			patient.setPhone("<b>Home</b></br>"+phoneNbr);
	     			patient.setPhoneNbr(phoneNbr);
	     		}
	     		
	     		String workPhoneNbr = "";
	     		if(t.get("/PID-14-6")!=null){
	     			workPhoneNbr = t.get("/PID-14-6");
	     		}
	     		if(t.get("/PID-14-7")!=null){
	     			workPhoneNbr = workPhoneNbr + t.get("/PID-14-7");
	     		}
	     		if(workPhoneNbr.length()>0){
	     			patient.setWorkPhoneNbr(workPhoneNbr);
	     		}
	     		
	     		patient.setWorkPhoneExt(t.get("/PID-14-8"));
	     		
	     		patient.setMaritalStatus(t.get("/PID-16-1"));
	     		
				patient.setDob(t.get("/PID-7-1"));
				
				String streetAddress = t.get("/PID-11-1")!=null?t.get("/PID-11-1"):"";
				String streetAddress2 = t.get("/PID-11-2")!=null?t.get("/PID-11-2"):"";
				String city = t.get("/PID-11-3")!=null?t.get("/PID-11-3"):"";
				String state = t.get("/PID-11-4")!=null?t.get("/PID-11-4"):"";
				String zip = t.get("/PID-11-5")!=null?t.get("/PID-11-5"):"";
				String countryCd = t.get("/PID-11-6")!=null?t.get("/PID-11-6"):"";
				
				if(streetAddress.length()>0 || city.length()>0 || state.length()>0 || zip.length()>0)
					patient.setAddress("<b>Home</b></br>"+streetAddress+" "+streetAddress2+" "+city+" "+state+" "+zip);
				
				patient.setStreetAddress(streetAddress);
				patient.setStreetAddress2(streetAddress2);
				patient.setCity(city);
				patient.setState(state);
				patient.setZip(zip);
				patient.setCountryCd(countryCd);
				
				String sex = t.get("/PID-8-1")!=null?t.get("/PID-8-1"):"";
				patient.setSexCd(sex);
				
				if(sex!=null){
					String sexDesc = getSexDesc(sex);
					patient.setSex(sexDesc);
				}
				
				String patientDOB = t.get("/PID-7-1");
				String formattedDate = "";
				String formattedDOB = "";
				if(patientDOB!=null){
					if(patientDOB.length()>8)
						patientDOB = patientDOB.substring(0, 8);
					Date inputDate = inputDateFormat.parse(patientDOB);
					formattedDOB = formatter.format(inputDate);
					String currentDate = formatter.format(new Date()); 
					Map ageMap = PersonUtil.calculateAge(formattedDOB, currentDate);
					
			    	patient.setDob(formattedDOB);
			    	patient.setDobYYYYMMDD(patientDOB);
			    	
			        if(ageMap!=null && !ageMap.isEmpty())
			        {
			          String currentAge = "";
			          String currentAgeUnits = "";
			          if(ageMap.get("age")!=null){
			        	  Integer age = (Integer) ageMap.get("age");
			        	  currentAge = String.valueOf(age);
			          }
			          if(ageMap.get("unit")!=null){
			        	  currentAgeUnits = (String) ageMap.get("unit");
			          }
			          String ageUnitsToDisplay = CachedDropDowns.getCodeDescTxtForCd(currentAgeUnits,"P_AGE_UNIT");
			          patient.setAgeDobSex(currentAge+" "+ageUnitsToDisplay+"<br/>"+formattedDOB+"<br/>"+patient.getSex());
			          patient.setAgeAtVaccination(currentAge);
			          patient.setAgeAtVaccinationUnit(currentAgeUnits);
			        }
		    	}
				
				String registryPatientIDs ="";
				String assigningAuthorities = "";
				String identifierTypeCodes = "";
				String assigningFacilities = "";
				String effectiveDates = "";
				String expirationDates = "";
				
				int i=0;
				while(t.get("/PID-3("+i+")-1")!=null){
					registryPatientIDs = registryPatientIDs+"|"+t.get("/PID-3("+i+")-1");
					assigningAuthorities = assigningAuthorities+"|"+t.get("/PID-3("+i+")-4");
					identifierTypeCodes = identifierTypeCodes+"|"+t.get("/PID-3("+i+")-5");
					if(t.get("/PID-3("+i+")-6")!=null)
						assigningFacilities = assigningFacilities+"|"+t.get("/PID-3("+i+")-6");
					else
						assigningFacilities = assigningFacilities +"|" + NULL;
					
					if(t.get("/PID-3("+i+")-7")!=null){
						String effectiveDate = t.get("/PID-3("+i+")-7");
						if(effectiveDate!=null){
							if(effectiveDate.length()>8)
								effectiveDate = effectiveDate.substring(0, 8);
							Date inputDate = inputDateFormat.parse(effectiveDate);
							formattedDate = formatter.format(inputDate);
							effectiveDates = effectiveDates +"|"+formattedDate;
						}
					}else{
						effectiveDates = effectiveDates +"|"+ NULL;
					}
					
					if(t.get("/PID-3("+i+")-8")!=null){
						String expirationDate = t.get("/PID-3("+i+")-8");
						if(expirationDate!=null){
							if(expirationDate.length()>8)
								expirationDate = expirationDate.substring(0, 8);
							Date inputDate = inputDateFormat.parse(expirationDate);
							formattedDate = formatter.format(inputDate);
							expirationDates = expirationDates +"|"+formattedDate;
						}
						
					}else{
						expirationDates = expirationDates +"|"+ NULL;
					}
					
					i++;
				}
				
				patient.setListOfregistryPatientID(registryPatientIDs);
				patient.setAssigningAuthorities(assigningAuthorities);
				patient.setIdentifierTypeCodes(identifierTypeCodes);
				patient.setAssigningFacilities(assigningFacilities);
				patient.setEffectiveDates(effectiveDates);
				patient.setExpirationDates(expirationDates);
				
				String cntyCd = t.get("/PID-11-9")!=null?t.get("/PID-11-9"):"";
				patient.setCntyCd(cntyCd);
				i=0;
				ArrayList<String> raceList = new ArrayList<String>();
				
				while(t.get("/PID-10("+i+")-1")!=null){
					raceList.add(t.get("/PID-10("+i+")-1"));
					i++;
				}
				patient.setRaceList(raceList);
				
				patient.setEthnicity(t.get("/PID-22-1"));
				patient.setRegistryPatientIDLink("<a href=/nbs/IISQuery.do?method=searchVaccinationsForPatientFromIIS&registryPatientIDStr="+patient.getRegistryPatientID()+"&patientName="+java.net.URLEncoder.encode(patient.getPatientName())+"&lastName="+java.net.URLEncoder.encode(lastName)+"&firstName="+java.net.URLEncoder.encode(firstName)+"&ri="+registryPatientIDs+"&aa="+assigningAuthorities+"&itc="+identifierTypeCodes+"&af="+assigningFacilities+"&sex="+patient.getSex()+"+&dob="+formattedDOB+"&initLoad=true"+">"+patient.getRegistryPatientID()+"</a>");
				patient.setFirstName(firstName);
				patient.setLastName(lastName);
				patient.setMiddleName(middleName);
				
				patient.setDeceasedInd(t.get("/PID-30-1"));
				String deceasedDateStr = t.get("/PID-29-1");
				if(deceasedDateStr!=null && deceasedDateStr.length()>8){
					deceasedDateStr = deceasedDateStr.substring(0, 8);
				 	Date inputDate = inputDateFormat.parse(deceasedDateStr);
				 	patient.setDeceasedTime(new Timestamp(inputDate.getTime()));
				}
				
				patient.setSuffix(t.get("/PID-5-4"));
				patient.setPrefix(t.get("/PID-5-5"));
				patient.setDegree(t.get("/PID-5-6"));
				
				String personNameEffectiveDateStr = t.get("/PID-5-12")!=null?t.get("/PID-5-12"):"";
				String personNameExpirationDateStr = t.get("/PID-5-13")!=null?t.get("/PID-5-13"):"";
				if(personNameEffectiveDateStr!=null && personNameEffectiveDateStr.length()>=8){
					personNameEffectiveDateStr = personNameEffectiveDateStr.substring(0, 8);
				 	Date inputDate = inputDateFormat.parse(personNameEffectiveDateStr);
				 	patient.setPersonNameEffectiveDate(new Timestamp(inputDate.getTime()));
				}
				
				if(personNameExpirationDateStr!=null && personNameExpirationDateStr.length()>=8){
					personNameExpirationDateStr = personNameExpirationDateStr.substring(0, 8);
				 	Date inputDate = inputDateFormat.parse(personNameExpirationDateStr);
				 	patient.setPersonNameExpirationDate(new Timestamp(inputDate.getTime()));
				}
				
				String addressFromDate = t.get("/PID-11-13")!=null?t.get("/PID-11-13"):"";
				String addressToDate = t.get("/PID-11-14")!=null?t.get("/PID-11-14"):"";
				
				if(addressFromDate!=null && addressFromDate.length()>=8){
					addressFromDate = addressFromDate.substring(0, 8);
				 	Date inputDate = inputDateFormat.parse(addressFromDate);
				 	patient.setAddressFromDate(new Timestamp(inputDate.getTime()));
				}
				
				if(addressToDate!=null && addressToDate.length()>=8){
					addressToDate = addressToDate.substring(0, 8);
				 	Date inputDate = inputDateFormat.parse(addressToDate);
				 	patient.setAddressToDate(new Timestamp(inputDate.getTime()));
				}
				
				String personAsDate = t.get("/PID-33-1")!=null?t.get("/PID-33-1"):"";
				if(personAsDate!=null && personAsDate.length()>=8){
					personAsDate = personAsDate.substring(0, 8);
				 	Date inputDate = inputDateFormat.parse(personAsDate);
				 	patient.setPersonAsOfDate(new Timestamp(inputDate.getTime()));
				}
				
				patientSearchList.add(patient);
     		}
	     		
	     	
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		
	}
	
	public static String buildSearchCriteriaString(PatientSearchVO psVO) throws ServletException {
	    //  build the criteria string
		StringBuffer sQuery = new StringBuffer("");
		String sQuerynew="";
		try {
			CachedDropDownValues cache = new CachedDropDownValues();
	
			// Demographics
			
			if (psVO.getLastName() != null && !psVO.getLastName().equals(""))
			    sQuery.append("Last Name Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getLastName()) + "'").append(", ");
	
			if (psVO.getFirstName() != null && !psVO.getFirstName().equals(""))
			    sQuery.append("First Name Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getFirstName()) + "'").append(", ");
			
			if (psVO.getMiddleName() != null && !psVO.getMiddleName().equals(""))
			    sQuery.append("Middle Name Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getMiddleName()) + "'").append(", ");
			
			if(psVO.getBirthTimeDay()!=null && psVO.getBirthTimeMonth()!=null && psVO.getBirthTimeYear()!=null)
				psVO.setBirthTime(PersonUtil.getBirthDate(psVO.getBirthTimeMonth(), psVO.getBirthTimeDay(), psVO.getBirthTimeYear()));
			
			if (psVO.getBirthTime() != null && !psVO.getBirthTime().equals("") && !"../../....".equals(psVO.getBirthTime()))
			    sQuery.append("Date of Birth Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getBirthTime()) + "'").append(", ");
			
			if (psVO.getCurrentSex() != null && !psVO.getCurrentSex().equals(""))
			    sQuery.append("Current Sex Equal ").append("'" + HTMLEncoder.encodeHtml(getSexDesc(psVO.getCurrentSex())) + "'").append(", ");
			
			if (psVO.getStreetAddr1() != null && !psVO.getStreetAddr1().equals(""))
			    sQuery.append("Street Address Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getStreetAddr1()) + "'").append(", ");
			
			if (psVO.getCityDescTxt() != null && !psVO.getCityDescTxt().equals(""))
			    sQuery.append("City Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getCityDescTxt()) + "'").append(", ");
			
			if (psVO.getState() != null && !psVO.getState().equals(""))
			    sQuery.append("State Equal ").append("'" + HTMLEncoder.encodeHtml(getStateDescTxt(psVO.getState())) + "'").append(", ");
			
			if (psVO.getZipCd() != null && !psVO.getZipCd().equals(""))
			    sQuery.append("Zip Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getZipCd()) + "'").append(", ");
			
			if (psVO.getPhoneNbrTxt() != null && !psVO.getPhoneNbrTxt().equals(""))
			    sQuery.append("Phone Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getPhoneNbrTxt()) + "'").append(", ");
			
			// Mother's details
			
			if (psVO.getMotherLastName() != null && !psVO.getMotherLastName().equals(""))
			    sQuery.append("Mother's Last Name Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getMotherLastName()) + "'").append(", ");
			
			if (psVO.getMotherFirstName() != null && !psVO.getMotherFirstName().equals(""))
			    sQuery.append("Mother's First Name Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getMotherFirstName()) + "'").append(", ");
			
			if (psVO.getMotherMaidenName() != null && !psVO.getMotherMaidenName().equals(""))
			    sQuery.append("Mother's Maiden Name Equal ").append("'" + HTMLEncoder.encodeHtml(psVO.getMotherMaidenName()) + "'").append(", ");
			
			if (psVO.getMultipleBirthIndicator() != null && !psVO.getMultipleBirthIndicator().equals("")){
			    sQuery.append("Multiple Birth Indicator Equal ").append("'" + HTMLEncoder.encodeHtml(cache.getDescForCode("YN",psVO.getMultipleBirthIndicator())) + "'").append(", ");
			
				if ("Y".equals(psVO.getMultipleBirthIndicator()) && psVO.getBirthOrder() > 0)
				    sQuery.append("Birth Order Equal ").append("'" + psVO.getBirthOrder() + "'").append(", ");
			}
			
			if(sQuery.length()>=2)
				sQuerynew = sQuery.substring(0, sQuery.length()-2);
		} catch (Exception e) {
			logger.error("Exception:"+e.getMessage(), e);
			throw new ServletException("Exception while buildSearchCriteriaString: "+e.getMessage(),e);
		}
	    
		return sQuerynew;

	}
	
	private static String getSexDesc(String sex) throws ServletException
    {
		try{
	      String desc="";
		  if(sex.equals("M"))
			  desc = "Male";
		  if(sex.equals("F"))
			  desc = "Female";
		  if(sex.equals("U"))
			  desc = "Unknown";

		  return desc;
		  
		} catch (Exception e) {
			logger.error("Exception:"+e.getMessage(),e);
			throw new ServletException("Exception: "+e.getMessage(),e);
		}
   }
	
	private static String getStateDescTxt(String sStateCd) throws ServletException {
		try{
	      CachedDropDownValues srtValues = new CachedDropDownValues();
	      TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
	      String desc = "";
	
	      if (sStateCd != null && treemap.get(sStateCd) != null) {
	         desc = (String) treemap.get(sStateCd);
	
	      }
	      return desc;
		} catch (Exception e) {
			logger.error("Exception:"+e.getMessage(), e);
			throw new ServletException("Exception: "+e.getMessage(),e);
		}
   }
	
	
	
	public static QueueDT fillVaccinationSearchResultQueueDT(){
    	
    	QueueDT queueDT = new QueueDT();

    	//First column: Provider
    	QueueColumnDT queue = new QueueColumnDT();
    	queue.setColumnId("column1");
    	queue.setColumnName("Provider");
    	queue.setBackendId("VACC_PROVIDER");
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getProvider");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("provider");
    	queue.setMediaPdfProperty("provider");
    	queue.setMediaCsvProperty("provider");
    	
    	queue.setColumnStyle("width:12%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("VACCPROVIDER");
    	queue.setDropdownStyleId("providerID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getProvider");
    	queueDT.setColumn1(queue);
    	
    	//Second column: Administration Date
    	queue = new QueueColumnDT();
    	queue.setColumnId("column2");
    	queue.setColumnName("Date Administered");
    	queue.setBackendId("ADMINISTRATION_DATE");
    	queue.setDefaultOrder("descending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getVaccAdminDt");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("vaccAdminDtS");
    	queue.setMediaPdfProperty("vaccAdminDt");
    	queue.setMediaCsvProperty("vaccAdminDt");
    	
    	queue.setColumnStyle("width:15%");
    	queue.setFilterType("0");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("ADMINISTRATIONDATE");
    	queue.setDropdownStyleId("vaccAdminDateID");
    	queue.setDropdownsValues("vaccAdminDate");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	//queue.setConstantCount("??");
    	queue.setMethodFromElement("getVaccAdminDt");
    	queue.setConstantCount("DATE_FILTER_COUNT");
    	queue.setMethodFromElement("getVaccAdminDt");
    	queue.setMethodGeneralFromForm("getColumn1List");//Only for date/multiselect
    	queue.setFilterByConstant(NEDSSConstants.FILTERBYDATE);
    	queueDT.setColumn2(queue);

    	//Third column: From Page Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column3");
    	queue.setColumnName("Vaccine Administered");
    	queue.setBackendId("VACCINE_ADMINISTERED");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getVaccineAdministered");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("vaccineAdministered");
    	queue.setMediaPdfProperty("vaccineAdministered");
    	queue.setMediaCsvProperty("vaccineAdministered");
    	
    	queue.setColumnStyle("width:22%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("VACCINEADMINISTERED");
    	queue.setDropdownStyleId("vaccineAdministeredID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getVaccineAdministered");
    	queueDT.setColumn3(queue);

    	//Fourth column: To Page Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column4");
    	queue.setColumnName("Lot Information");
    	queue.setBackendId("LOT_INFORMATION");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getLotInformation");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("lotInformation");
    	queue.setMediaPdfProperty("lotInformation");
    	queue.setMediaCsvProperty("lotInformation");
    	queue.setColumnStyle("width:23%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("LOTINFORMATION");
    	queue.setDropdownStyleId("lotInformation");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getLotInformation");
    	queueDT.setColumn4(queue);

    	//Fifth column: To Page Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column5");
    	queue.setColumnName("Information Source");
    	queue.setBackendId("INFORMATION_SOURCE");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getVaccInfoSource");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("vaccInfoSource");
    	queue.setMediaPdfProperty("vaccInfoSource");
    	queue.setMediaCsvProperty("vaccInfoSource");
    	queue.setColumnStyle("width:15%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("INFORMATIONSOURCE");
    	queue.setDropdownStyleId("vaccInfoSourceID");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	queue.setMethodFromElement("getVaccInfoSource");
    	queueDT.setColumn5(queue);
    	
    	return queueDT;
    	
    }

	public static GenericForm translateFromVaccineSearchResultDTToGenericSummaryDisplayVO(IISQueryForm iisQueryForm){
		
		GenericForm genericForm = new GenericForm();
		try{
			genericForm.setSearchCriteriaArrayMap(iisQueryForm.getSearchCriteriaArrayMap());
			genericForm.setAttributeMap(iisQueryForm.getAttributeMap());
			genericForm.setQueueDT(iisQueryForm.getQueueDT());
			
			genericForm.setColumn1List(iisQueryForm.getVaccAdminDate());
			
			genericForm.setElementColl(iisQueryForm.getVaccinationSearchList());
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
		}
		return genericForm;
	}
	
	
	public static void setPatientDetailsForVaccinationResultHeader(HttpServletRequest request){
		try{
			String registryPatientIDStr = request.getParameter("registryPatientIDStr");
	    	String patientName = request.getParameter("patientName");
	    	String patientSex = request.getParameter("sex");
	    	String patientDOB = request.getParameter("dob");
	    	String currentAge = "";
	    	String currentAgeUnits="";
	    	
	    	if(patientDOB!=null){
		    	String currentAgeAndUnits = PersonUtil.displayAge(patientDOB);
	
		        if(currentAgeAndUnits!=null ||currentAgeAndUnits!="")
		        {
		          int pipe = currentAgeAndUnits.indexOf('|');
		          currentAge = currentAgeAndUnits.substring(0, pipe);
		          currentAgeUnits = currentAgeAndUnits.substring(pipe + 1);
		        }
	    	}
	    	
	    	if(patientName!=null){
	    		Map personDetailsMap = new HashMap<Object, Object>();
	    		personDetailsMap.put("registryPatientIDStr",registryPatientIDStr);
	    		personDetailsMap.put("patientName",patientName);
	    		personDetailsMap.put("patientSex",patientSex);
	    		personDetailsMap.put("patientDOB",patientDOB);
	    		personDetailsMap.put("currentAge", currentAge);
	    		personDetailsMap.put("currentAgeUnitCd",currentAgeUnits);
	    		
	    		NBSContext.store(request.getSession(), "PersonDetailsMap", personDetailsMap);
	    	}else{
	    		Map<Object, Object> personDetailsMap = (HashMap<Object, Object>) NBSContext.retrieve(request.getSession(), "PersonDetailsMap");
	    		if(personDetailsMap.get("registryPatientIDStr")!=null)
	    			registryPatientIDStr = (String) personDetailsMap.get("registryPatientIDStr");
				
				if(personDetailsMap.get("patientName")!=null)
					patientName= (String) personDetailsMap.get("patientName");

				if(personDetailsMap.get("patientSex")!=null)
					patientSex= (String) personDetailsMap.get("patientSex");
				
				if(personDetailsMap.get("currentAge")!=null)
					currentAge= (String) personDetailsMap.get("currentAge");
				
				if(personDetailsMap.get("currentAgeUnitCd")!=null)
					currentAgeUnits= (String) personDetailsMap.get("currentAgeUnitCd");
				
				Timestamp dobTimestamp = null;
				if(personDetailsMap.get("patientDOB")!=null)
					patientDOB = (String) personDetailsMap.get("patientDOB");
	    	}
	    	
	    	request.setAttribute("registryPatientIDStr",registryPatientIDStr);
	    	request.setAttribute("patientName",patientName);
	    	request.setAttribute("patientSex",patientSex);
	    	request.setAttribute("patientDOB",patientDOB);
	    	request.setAttribute("currentAge", currentAge);
	        request.setAttribute("currentAgeUnitCd", currentAgeUnits);
	        
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
		}
	}
	
	
	public static String searchPatientFromIIS(PatientSearchResultDT patient, String registryPatientIDs, String assigningAuthorities, String identifierTypeCodes, String assigningFacilities, String requestFor, String qtyLimitedRequest) throws NEDSSAppException{
		try{ 
			String responseMessage = "";
			if(IIS_SOURCE_TYPE_LOCAL.equals(propertyUtil.getIISSourceType())){
				//For testing, reads patient's response file from IIS_LOCAL_PATH setting + "Patient" folder
				//For vaccination response reads from IIS_LOCAL_PATH setting + "Vaccination" folder
				
				logger.info("Reading response from local file systems...........");
				
				responseMessage = readResponseFromLocalFileSystem(requestFor);
			}else{
				Connector connector = IISQueryConnectionUtil.getIISConnection();
				
				QueryResponse queryResponse = new QueryResponse();
				String requestHL7Msg = buildQueryToSearchPatient(patient, registryPatientIDs, assigningAuthorities, identifierTypeCodes, assigningFacilities, connector, qtyLimitedRequest);
				logger.info("requestHL7Msg for IIS ------------------------------------------"+requestHL7Msg);
				
				
				responseMessage = connector.submitMessage(requestHL7Msg, false);
				
				logger.info("responseMessage from IIS ----------------------------------"+responseMessage);
				
			}
			return responseMessage;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	
	/**
	 * Used for testing HL7 patient and vaccination response
	 * Reads response txt from path specified at NBS_Configuration table's IIS_LOCAL_PATH config_key. That is root directory
	 * Underneath it has Patient and Vaccination directory for respective responses.
	 *  
	 * @param requestFor - is folder name (Patient / Vaccination) from where to read response
	 * @return
	 * @throws NEDSSAppException
	 */
	private static String readResponseFromLocalFileSystem(String requestFor) throws NEDSSAppException{
		try{
			String responseMessage = "";
			String filePath = propertyUtil.getIISLocalPath()+"/"+requestFor;
			
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        System.out.println(file.getName());
			        responseMessage = FileUtils.readFileToString(file);
			    }
			}
			
			return responseMessage;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	private static String buildQueryToSearchPatient(PatientSearchResultDT patient, String registryPatientIDs, String assigningAuthorities, String identifierTypeCodes, String assigningFacilities, Connector connector, String qtyLimitedRequest) throws NEDSSAppException{
		String encodedMessage = null;
		try{
			String messageId = System.currentTimeMillis() + "." + getCount();
			
			if(patient == null)
				throw new NEDSSAppException("Missing patient search criteria, PatientSearchResultDT is null");
			
			QBP_Q11 qbp = new QBP_Q11();
			qbp.initQuickstart("QBP", "Q11", "P");
			
			// Populate MSH
			MSH mshSegment1 = qbp.getMSH();
			
			String customTransforms = connector.getCustomTransformations();
			Terser t = new Terser(qbp);
			
			if(customTransforms!=null && customTransforms.length()>0){
				String[] transforms = customTransforms.split("\n");
				for(int i=0;i<transforms.length;i++){
					String segment = transforms[i].substring(0,transforms[i].indexOf("="));
					String value = transforms[i].substring(transforms[i].indexOf("=")+1);
					if("[OTHERID]".equals(value))
						value = connector.getOtherid();
					
					if("MSH-3".equals(segment))
						mshSegment1.getSendingApplication().getNamespaceID().setValue(value);
					else if("MSH-4".equals(segment))
						mshSegment1.getSendingFacility().getNamespaceID().setValue(value);
					else if("MSH-5".equals(segment))
						mshSegment1.getReceivingApplication().getNamespaceID().setValue(value);
					else if("MSH-6".equals(segment))
						mshSegment1.getMsh6_ReceivingFacility().getNamespaceID().setValue(value);
					else if("MSH-8".equals(segment))
						mshSegment1.getSecurity().setValue(value);
					else if(segment!=null && segment.startsWith("MSH")) {
						t.set(segment.trim(),value);
					}
				}
			}else{
				mshSegment1.getSendingApplication().getNamespaceID().setValue("Test EHR Application");
				mshSegment1.getSendingFacility().getNamespaceID().setValue("");
			}
			if(mshSegment1.getReceivingFacility().getNamespaceID().getValue()==null)
				mshSegment1.getReceivingFacility().getNamespaceID().setValue("NIST Test Iz Reg");
			mshSegment1.getDateTimeOfMessage().getTime().setValue(new Date());
			
			//TODO generate below MessageControlID dynamically
			mshSegment1.getMessageControlID().setValue(messageId);
			mshSegment1.getProcessingID().getProcessingID().setValue("P");
			mshSegment1.getVersionID().getVersionID().setValue("2.5.1");
			mshSegment1.getAcceptAcknowledgmentType().setValue("ER");
			mshSegment1.getApplicationAcknowledgmentType().setValue("AL");
			mshSegment1.getMsh21_MessageProfileIdentifier(0).getEi1_EntityIdentifier().setValue("Z34");
			mshSegment1.getMsh21_MessageProfileIdentifier(0).getEi2_NamespaceID().setValue("CDCPHINVS");
			
			// Populate QPD
			QPD qpd = qbp.getQPD();
			qpd.getQpd1_MessageQueryName().getCe1_Identifier().setValue("Z34");
			qpd.getQpd1_MessageQueryName().getCe2_Text().setValue("Request Immunization History");
			qpd.getQpd1_MessageQueryName().getCe3_NameOfCodingSystem().setValue("CDCPHINVS");
			//TODO generate below value dynamically
			qpd.getQpd2_QueryTag().setValue(messageId);

			
			if(registryPatientIDs!=null){
				//Check if we need to get below value dynamically.
				StringTokenizer registryIDST = new StringTokenizer(registryPatientIDs,"|");
				StringTokenizer assigningAuthoritiesST = new StringTokenizer(assigningAuthorities,"|");
				StringTokenizer identifierTypeCodesST = new StringTokenizer(identifierTypeCodes,"|");
				StringTokenizer assigningFacilitiesST = new StringTokenizer(assigningFacilities,"|");
				
				/*if(patientRegistryId!=null)
					qpd.getQpd3_UserParametersInsuccessivefields().parse(patientRegistryId);
				if(assigningAuthority!=null)
					qpd.getQpd3_UserParametersInsuccessivefields().getExtraComponents().getComponent(2).parse(assigningAuthority);
				if(identifierTypeCode!=null)
					qpd.getQpd3_UserParametersInsuccessivefields().getExtraComponents().getComponent(3).parse(identifierTypeCode);*/
				
				
				int i=0;
				while (registryIDST.hasMoreElements()) {
					t.set("QPD-3("+i+")-1",registryIDST.nextToken());
			    	t.set("QPD-3("+i+")-4",assigningAuthoritiesST.nextToken());
			    	t.set("QPD-3("+i+")-5",identifierTypeCodesST.nextToken());
			    	if(assigningFacilitiesST.hasMoreElements())
			    		t.set("QPD-3("+i+")-6",assigningFacilitiesST.nextToken());
			    	i++;
				}
			
			}
			
			boolean isNameEmpty = true;
			
			if(patient.getLastName()!=null && patient.getLastName().length()>0){
				t.set("QPD-4-1", patient.getLastName());
				isNameEmpty = false;
			}
			if(patient.getFirstName()!=null && patient.getFirstName().length()>0){
				t.set("QPD-4-2", patient.getFirstName());
				isNameEmpty = false;
			}
			if(patient.getMiddleName()!=null && patient.getMiddleName().length()>0){
				t.set("QPD-4-3", patient.getMiddleName());
				isNameEmpty = false;
				
			}
			
			if(!isNameEmpty){
				t.set("QPD-4-7", "L");
			}
	        
	        if(patient.getMotherMaidenLastName()!=null && patient.getMotherMaidenLastName().length()>0){
	        	t.set("QPD-5-1", patient.getMotherMaidenLastName());
	        	//t.set("QPD-5-2", patientSearchVO.getMotherFirstName());
	        	t.set("QPD-5-7", "M");
	        }
	        
	        t.set("QPD-6", patient.getDobYYYYMMDD());
	        t.set("QPD-7", patient.getSexCd());
	        
	        boolean isAddressEmpty = true;
	        
	        if(patient.getStreetAddress()!=null && patient.getStreetAddress().length()>0){
	        	t.set("QPD-8-1", patient.getStreetAddress());
	        	isAddressEmpty = false;
	        }
	        
	        if(patient.getCity()!=null && patient.getCity().length()>0){
	        	t.set("QPD-8-3", patient.getCity());
	        	isAddressEmpty = false;
	        }
	        
            if(patient.getState()!=null && patient.getState().length()>0){
            	CachedDropDownValues cdv = new CachedDropDownValues();
                TreeMap<Object,Object> tm = cdv.getCachedStateCodeList();
	            String stateDesc = (String)tm.get(patient.getState());
		        t.set("QPD-8-4", stateDesc);
		        isAddressEmpty = false;
            }

            if(patient.getZip()!=null && patient.getZip().length()>0){
            	t.set("QPD-8-5", patient.getZip());
            	isAddressEmpty = false;
            }
            
            if(!isAddressEmpty){
		        t.set("QPD-8-6", "USA");
		        t.set("QPD-8-7", "L");
            }
	        
	        String phoneNbr=patient.getPhoneNbr();
	        if(phoneNbr!=null && phoneNbr.length()>=3){
	        	t.set("QPD-9-2", "PRN");
		        t.set("QPD-9-3", "PH");
		        
	        	String areaCd = phoneNbr.substring(0, 3);
	        	t.set("QPD-9-6", areaCd);
	        	if(phoneNbr.length()>3){
		        	String phone = phoneNbr.substring(3);
			        if(phone!=null && phone.length()>0){
			        	phone = phone.replace("-", "");
			        	t.set("QPD-9-7", phone);
			        }
	        	}
	        }
	        
	        if("Y".equals(patient.getMultipleBirthIndicator())){
	        	t.set("QPD-10-1", "Y");
	        	if(patient.getBirthOrder()!=null)
	        		t.set("QPD-11-1", patient.getBirthOrder().toString());
	        }else if("N".equals(patient.getMultipleBirthIndicator())){
	        	t.set("QPD-10-1", "N");
	        }
	        
			RCP rcp = qbp.getRCP();
			rcp.getRcp1_QueryPriority().setValue("I");
			rcp.getRcp2_QuantityLimitedRequest().getCq1_Quantity().setValue(qtyLimitedRequest);
			rcp.getRcp2_QuantityLimitedRequest().getCq2_Units().getCe1_Identifier().setValue("RD");
			rcp.getRcp2_QuantityLimitedRequest().getCq2_Units().getCe2_Text().setValue("Records");;
			rcp.getRcp2_QuantityLimitedRequest().getCq2_Units().getCe3_NameOfCodingSystem().setValue("HL70126");

			HapiContext context = new DefaultHapiContext();
			Parser parser = context.getPipeParser();
			encodedMessage = parser.encode(qbp);
			logger.info("QBP Message ******************************* : "+encodedMessage);
			
			return encodedMessage;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	
	public static void extractRXAandProcess(String vaccinationResponse, Collection<Object> vaccinationSearchList, IISQueryForm iisQueryForm, HttpServletRequest request) throws NEDSSAppException{
		try{
			Map<String, VaccinationSearchResultDT> vaccMap = new HashMap<String, VaccinationSearchResultDT>();
			
			//If response has an XML, then correct it.
			if(vaccinationResponse.indexOf("<PID.11>")>0){
				vaccinationResponse = replaceXMLwithHL7(vaccinationResponse);
			}
			//ORC, RXA and RXR are group of vaccinations, extract MSH up to first ORC.
			if(vaccinationResponse.indexOf("ORC|")>0){
				String mshSegment = vaccinationResponse.substring(0,vaccinationResponse.indexOf("ORC|"));
				
				//Extracting PID to patientSearchDT to match with submitted request
				
				Collection<Object> patientSearchList = new ArrayList<Object>();
				parseAndPopulatePatientSearchList(mshSegment, patientSearchList);
				if(patientSearchList.size()>0){
					ArrayList<Object> patientSearchList1 = (ArrayList<Object> ) patientSearchList;
					iisQueryForm.setPatientFromVaccinationResponse((PatientSearchResultDT)patientSearchList1.get(0));
					
				}
				
				if(comparePatientFromPatientSearchAndVaccineSearchResponse(iisQueryForm.getSelectedPatient(), iisQueryForm.getPatientFromVaccinationResponse()))
				{	
					logger.info("mshSegment:\n"+mshSegment);
					//Extract group of vaccinations
					String rxas = vaccinationResponse.substring(vaccinationResponse.indexOf("ORC|"));
					logger.info("rxas:\n"+rxas);
					String sigleRXA = "";
					int i=1;
					while(rxas.indexOf("ORC|",5)>=0){
						sigleRXA = rxas.substring(rxas.indexOf("ORC|"), rxas.indexOf("ORC|",5));
						logger.info("sigleRXA: "+sigleRXA);
						VaccinationSearchResultDT vaccination = parseAndPopulateVaccinationSearchList(mshSegment+sigleRXA,vaccinationSearchList);
						vaccination.setVaccSeqNbr(String.valueOf(i));
						vaccMap.put(String.valueOf(i), vaccination);
						rxas = rxas.substring(rxas.indexOf("ORC|",5));
						i++;
					}
					
					logger.info("Last RXA: "+rxas);
					
					VaccinationSearchResultDT vaccination = parseAndPopulateVaccinationSearchList(mshSegment+rxas,vaccinationSearchList);
					vaccination.setVaccSeqNbr(String.valueOf(i));
					vaccMap.put(String.valueOf(i), vaccination);
					
					iisQueryForm.setVaccinationMap(vaccMap);
				}else{
					//If PID mismatch then display error message.
					request.setAttribute("feedbackErroMessage","The following issues were encountered when querying the registry: Patient ID Mismatch. Please try submitting the request again. If issues persist, please contact your system administrator.");
					request.setAttribute("isError",true);
					request.setAttribute("infoBox","info");
					
					EDXActivityLogDT edxActivityLogDT = IISQueryUtil.populateEDXActivityLogDT(new Long(-5), new Long(-5), "Failure", "Patient ID Mismatch (Patient details mismatch between patient search and vaccination search).", null, null, null, iisQueryForm.getPersonSearch().getLastName()+" "+iisQueryForm.getPersonSearch().getFirstName(), null, IISQueryUtil.IIS_DOC_TYPE, IISQueryUtil.IIS_DOC_TYPE);
					portPageUtil.createEdxActivityLog(edxActivityLogDT, request);
				}
			}
			
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	
	public static VaccinationSearchResultDT parseAndPopulateVaccinationSearchList(String queryResponseStr, Collection<Object> vaccinationSearchList) throws NEDSSAppException{
		try{
			String lastName="";
			String firstName="";
			
			Parser p = new PipeParser();
	     	p.setValidationContext(new NoValidation());
	     	Message hapiMsg = (RSP_K11) p.parse(queryResponseStr.replaceAll("\n", "\r\n"));
	        
	     	Terser t = new Terser(hapiMsg);
	     	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	     	DateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
	     	String formattedDate = "";
	     	
     		if(t.get("/RXA-3-1")!=null){
     			
     			 String firstLastName = t.get("/RXA-10-2")!=null?t.get("/RXA-10-2"):"";
     			 String givenName = t.get("/RXA-10-3")!=null?t.get("/RXA-10-3"):"";
     			
    	         VaccinationSearchResultDT vaccination = new VaccinationSearchResultDT();
    	         vaccination.setHl7msg(queryResponseStr);
    	         vaccination.setProvider(givenName+" "+firstLastName);
				 vaccination.setProviderFirstName(givenName);
				 vaccination.setProviderLastName(firstLastName);
				 vaccination.setProviderId(t.get("/RXA-10-1"));
				 vaccination.setProviderMiddleInitial(t.get("/RXA-10-4"));
				 vaccination.setProviderPrefix(t.get("/RXA-10-5"));
				 vaccination.setProviderSuffix(t.get("/RXA-10-6"));
				 vaccination.setProviderDegree(t.get("/RXA-10-7"));
				 
				 vaccination.setProviderAssigningAuthority(t.get("/RXA-10-9"));
				 vaccination.setIdTypeCd(t.get("/RXA-10-13"));
				 
				 //Populate Organization
				 vaccination.setOrgName(t.get("/RXA-11-4-1"));
				 vaccination.setOrgAddress1(t.get("/RXA-11-9"));
				 vaccination.setOrgAddress2(t.get("/RXA-11-10"));
				 vaccination.setOrgCity(t.get("/RXA-11-11"));
				 vaccination.setOrgState(t.get("/RXA-11-12"));
				 vaccination.setOrgZip(t.get("/RXA-11-13"));
				 vaccination.setOrgCountry(t.get("/RXA-11-14"));
				 vaccination.setOrgLocatorDescTxt(t.get("/RXA-11-16"));
				 
    	         String vaccAdminDt = t.get("/RXA-3-1");
    	         if(vaccAdminDt.length()>8)
    	        	 vaccAdminDt = vaccAdminDt.substring(0, 8);
				 Date inputDate = inputDateFormat.parse(vaccAdminDt);
				 formattedDate = formatter.format(inputDate);
					
				 vaccination.setVaccAdminDt(formattedDate);
				 vaccination.setVaccAdminDt(new Timestamp(inputDate.getTime()));
				 
				 String vaccType = t.get("/RXA-5-2")!=null?t.get("/RXA-5-2"):"";
				 vaccination.setVaccType(vaccType);

				 String vaccTypeCd = t.get("/RXA-5-1")!=null?t.get("/RXA-5-1"):"";
				 String codeForVaccType = getCodeForConceptCode("VAC_NM",vaccTypeCd);
				 if(codeForVaccType!=null)
					 vaccination.setVaccTypeCd(codeForVaccType);
				 else
					 vaccination.setVaccTypeCd(vaccTypeCd);
				 
				 //Populate dose number
				 
				 try{
					 int j=0;
			         while(t.get("/OBX("+j+")-3-1")!=null){
			        	 // Extract the value of the dose number from OBX-5 where OBX-3 is 30973-2.
			        	 if("30973-2".equals(t.get("/OBX("+j+")-3-1"))){
			        		 if(t.get("/OBX("+j+")-5-1")!=null)
			        			 vaccination.setVaccDoseNbr(Integer.parseInt(t.get("/OBX("+j+")-5-1")));
			        	 }
			        	 j++;
			         }
				 }catch(Exception e){
					 //If OBX not found in response then it will throw exception.
					 //Do not throw it back.
				 }
				 
				 String doseNbrStr = vaccination.getVaccDoseNbr()!=null?String.valueOf(vaccination.getVaccDoseNbr()):"";
				 
				 String vaccineAdministered = "";
				 if(vaccination.getVaccType()!=null && vaccination.getVaccType().length()>0)
					 vaccineAdministered = "<b>Vaccine Type:</b><br/>"+vaccination.getVaccType();
				 if(doseNbrStr!=null && doseNbrStr.length()>0)
					 vaccineAdministered = vaccineAdministered +"<br/>"+"<b>Dose Number:</b><br/>"+doseNbrStr;
				
				 vaccination.setVaccineAdministered(vaccineAdministered);
				
				 String vaccMfgr = "";
				 try{
					 if(t.get("/RXA-17-1")!=null){
						 vaccination.setVaccMfgr(t.get("/RXA-17-2"));
						 vaccination.setVaccMfgrCd(t.get("/RXA-17-1"));
						 vaccMfgr = t.get("/RXA-17-2");
					 }
				 }catch(Exception e){
					 //Don't throw exception, its expected for AL
				 }
				 
				 String lotNbr = t.get("/RXA-15-1")!=null?t.get("/RXA-15-1"):"";
				 vaccination.setVaccLotNbr(lotNbr);
				 
				 String lotExpirationDate = "";
				 if(t.get("/RXA-16-1")!=null){
					 String lotExpDateStr = t.get("/RXA-16-1");
					 if(lotExpDateStr.length()>8)
						 lotExpDateStr = lotExpDateStr.substring(0, 8);
					 Date date = (Date) inputDateFormat.parse(lotExpDateStr);
					 Timestamp timeStampDate = new Timestamp(date.getTime());
					 vaccination.setVaccExpDt(timeStampDate);
					 lotExpirationDate = formatter.format(vaccination.getVaccExpDt());
				 }
				 
				 String lotInformation = "";
				 if(vaccMfgr!=null && vaccMfgr.length()>0)
					 lotInformation = "<b>Manufacturer:</b><br/>"+vaccMfgr+"<br/>";
				 if(vaccination.getVaccLotNbr()!=null && vaccination.getVaccLotNbr().length()>0)
					 lotInformation = lotInformation +"<b>Lot #:</b><br/>"+vaccination.getVaccLotNbr()+"<br/>";
				 if(lotExpirationDate!=null && lotExpirationDate.length()>0)
					 lotInformation = lotInformation+"<b>Lot Expiration:</b><br/>"+lotExpirationDate;
				 vaccination.setLotInformation(lotInformation);
				 
				 if(t.get("/RXA-9-1")!=null){
					 CodeValueGeneralDT codeValueGeneralDT = getCodeValueGeneralDTbyConceptCd("PHVS_VACCINEEVENTINFORMATIONSOURCE_NND",t.get("/RXA-9-1"));
					 if(codeValueGeneralDT!=null){
						 String infoSource = codeValueGeneralDT.getConceptNm();
						 if(infoSource.length()>0){
							 vaccination.setVaccInfoSourceCd(codeValueGeneralDT.getCode());
							 vaccination.setVaccInfoSource(infoSource);
						 }
					 }else{
						 vaccination.setVaccInfoSourceCd(t.get("/RXA-9-1"));
					 }
				 }else if(t.get("/RXA-9-2")!=null){
					 vaccination.setVaccInfoSource(t.get("/RXA-9-2"));
				 }
				 
				 //Expected exception, if RXR not found in response.
				 try{
					 if(t.get("/RXR-2-1")!=null){
						 String vaccAnatomicSite = getCodeForConceptCode("NIP_ANATOMIC_ST", t.get("/RXR-2-1"));
						 if(vaccAnatomicSite!=null)
							 vaccination.setVaccBodySiteCd(vaccAnatomicSite);
						 else
							 vaccination.setVaccBodySiteCd(t.get("/RXR-2-1"));
					 }
				 }catch(Exception e){
					 //If RXR not found in response then it will throw exception.
					 //Do not throw it back.
				 }
				 
				 try{
					 String vaccinationUniqeId = t.get("/ORC-3-1");
					 vaccination.setVaccinationIdentifier(vaccinationUniqeId);
				 }catch(Exception e){
					 //If ORC-3 not found in response then it will throw exception.
					 //Do not throw it back.
				 }
				 
				 vaccination.setVaccSourceNm(t.get("/MSH-3"));
				 vaccination.setVaccDocNm(t.get("/MSH-9-1")+"^"+t.get("/MSH-9-2")+"^"+t.get("/MSH-9-3"));
				 vaccination.setVaccMessageId(t.get("/MSH-10"));
				 
				 vaccinationSearchList.add(vaccination);
				 return vaccination;
     		}
	     		
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return null;
		
	}
	
	private static int count = 0;

	private static void incCount() {
	    count++;
	    if (count > 99999) {
	      count = 0;
	    }
	}

	protected static synchronized int getCount() {
	    incCount();
	    return count;
	}
	
	
	/**
	 * populates and creates vaccination
	 * 
	 * @param iisQueryForm
	 * @param vaccination
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 */
	public static Long populateAndCreatePageActProxyVOForIntervention(IISQueryForm iisQueryForm, VaccinationSearchResultDT vaccination, HttpServletRequest request) throws NEDSSAppException{
		try{
			
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			Timestamp currentTS = new Timestamp(new Date().getTime());
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			String asOfDate = null;
			
			PageActProxyVO proxyVO = new PageActProxyVO();
			PatientSearchResultDT selectedPatient = iisQueryForm.getSelectedPatient();
			
			InterventionVO vacVO = new InterventionVO();
            InterventionDT vacDT = new InterventionDT();
            vacVO.setTheInterventionDT(vacDT);
            
            int tempInterventionId = -1;
            vacVO.getTheInterventionDT().setInterventionUid(new Long(tempInterventionId));
        	vacVO.getTheInterventionDT().setVersionCtrlNbr(1);
        	vacVO.getTheInterventionDT().setAddUserId(Long.valueOf(userId));
        	vacVO.getTheInterventionDT().setAddTime(currentTS);
        	
        	vacVO.getTheInterventionDT().setCd(NEDSSConstants.VACADM_CD);
        	vacVO.getTheInterventionDT().setCdDescTxt("Vaccine Administration");
        	vacVO.getTheInterventionDT().setCdSystemCd(NEDSSConstants.NBS);
        	vacVO.getTheInterventionDT().setCdSystemDescTxt("NEDSS Base System");
        	vacVO.getTheInterventionDT().setItNew(true);
        	vacVO.getTheInterventionDT().setItDirty(false);
        	
        	//Check if vaccination created electronically, the set electronic_ind = 'Y' otherwise 'N'
        	
        	vacVO.getTheInterventionDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND_VACCINATION);
        	
        	vacVO.getTheInterventionDT().setVaccInfoSourceCd(vaccination.getVaccInfoSourceCd());
        	
        	
        	
        	if(vaccination.getVaccAdminDtS()!=null){
        		Timestamp adminDTTimestamp = new Timestamp(formatter.parse(vaccination.getVaccAdminDtS()).getTime());
        		vacVO.getTheInterventionDT().setActivityFromTime(adminDTTimestamp);
        		asOfDate = vaccination.getVaccAdminDtS();
        		Map ageMap = PersonUtil.calculateAge(selectedPatient.getDob(), vaccination.getVaccAdminDtS());
        		if(ageMap!=null){
        			if(ageMap.get("age")!=null){
        				Integer age = (Integer) ageMap.get("age");
        				vacVO.getTheInterventionDT().setAgeAtVacc(age);
        			}
        			if(ageMap.get("unit")!=null){
        				String ageUnit = (String) ageMap.get("unit");
        				vacVO.getTheInterventionDT().setAgeAtVaccUnitCd(ageUnit);
        			}
        		}
        	}
        	
        	if(asOfDate == null){
        		asOfDate = formatter.format(new Date());
        	}
        	
        	vacVO.getTheInterventionDT().setVaccMfgrCd(vaccination.getVaccMfgrCd());
        	vacVO.getTheInterventionDT().setVaccDoseNbr(vaccination.getVaccDoseNbr());
        	vacVO.getTheInterventionDT().setTargetSiteCd(vaccination.getVaccBodySiteCd());
        	vacVO.getTheInterventionDT().setMaterialCd(vaccination.getVaccTypeCd());
        	vacVO.getTheInterventionDT().setMaterialLotNm(vaccination.getVaccLotNbr());
        	vacVO.getTheInterventionDT().setMaterialExpirationTime(vaccination.getVaccExpDt());
        	
        	vacVO.setItNew(true);
        	vacVO.setItDirty(false);
        	
             //Setting TargetSiteDescription from targetSiteCd
             if(vacVO.getTheInterventionDT().getTargetSiteCd()!=null)
             	vacVO.getTheInterventionDT().setTargetSiteDescTxt(CachedDropDowns.getCodeDescTxtForCd(vacVO.getTheInterventionDT().getTargetSiteCd(), "NIP_ANATOMIC_ST"));
             //Setting effectiveFromTime same as activityFromTime
             if(vacVO.getTheInterventionDT().getActivityFromTime()!=null)
             	vacVO.getTheInterventionDT().setEffectiveFromTime(vacVO.getTheInterventionDT().getActivityFromTime());
             
             vacVO.getTheInterventionDT().setLastChgUserId(Long.valueOf(userId));
             vacVO.getTheInterventionDT().setLastChgTime(currentTS);
             vacVO.getTheInterventionDT().setRecordStatusCd(NEDSSConstants.ACTIVE);
             vacVO.getTheInterventionDT().setRecordStatusTime(currentTS);
             proxyVO.setInterventionVO(vacVO); // set the interview VO into the
             
             int tempID = -1;
        	 Long patientUid = (Long) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPersonSummary);
        	 PersonVO personVO = new PersonVO();
     		 Collection<Object>  patientColl = new ArrayList<Object> ();
     		 
     		 personVO.setItNew(true);
     		 personVO.getThePersonDT().setItNew(true);
     		 personVO.getThePersonDT().setPersonParentUid(patientUid);
     		 personVO.getThePersonDT().setPersonUid(new Long(tempID));
     		 personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
     		 personVO.getThePersonDT().setStatusTime(currentTS);
     		 personVO.getThePersonDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
     		 personVO.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND_VACCINATION);	
     		 personVO.getThePersonDT().setLastChgUserId(Long.valueOf(userId));
     		 personVO.getThePersonDT().setLastChgTime(currentTS);
     		 personVO.getThePersonDT().setCurrSexCd(selectedPatient.getSexCd());
     		 personVO.getThePersonDT().setAddUserId(Long.valueOf(userId));
     		 personVO.getThePersonDT().setAddTime(currentTS);
     		 
     		 if(selectedPatient.getEthnicity()!=null && selectedPatient.getEthnicity().length()>0){
     			 
	     		 ArrayList<Object> ethnicityList = new ArrayList<Object> ();
	     		 
	     		 PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
	     		 ethnicDT.setItNew(true);
	     		 ethnicDT.setItDirty(false);
	     		 ethnicDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
	     		 ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	     		 
	     		 String ethnicityCode = getCodeForConceptCode("PHVS_ETHNICITYGROUP_CDC_UNK", selectedPatient.getEthnicity());
    			 
	     		 if(ethnicityCode!=null){
	     			 ethnicDT.setEthnicGroupCd(ethnicityCode);
	     			 personVO.getThePersonDT().setEthnicGroupInd(ethnicityCode);
	     		 }else{
	     			 ethnicDT.setEthnicGroupCd(selectedPatient.getEthnicity());
		     		 personVO.getThePersonDT().setEthnicGroupInd(selectedPatient.getEthnicity());
	     		 }
	     		 ethnicDT.setAddTime(currentTS);
	     		 ethnicDT.setRecordStatusTime(currentTS);
	     		 ethnicDT.setAddUserId(Long.valueOf(userId));
	     		 ethnicDT.setLastChgUserId(Long.valueOf(userId));
	     		 ethnicDT.setLastChgTime(currentTS);
	     		 ethnicityList.add(ethnicDT);
	     		 personVO.getThePersonDT().setAsOfDateEthnicity_s(asOfDate);
	     		 
	     		 personVO.setThePersonEthnicGroupDTCollection(ethnicityList);
     			 
     		 }
     		 
     		 if(selectedPatient.getDob()!=null){
	     		 personVO.getThePersonDT().setBirthTime(StringUtils.stringToStrutsTimestamp(selectedPatient.getDob()));
	    		 if(personVO.getThePersonDT().getBirthTime() != null) {
	    			 personVO.getThePersonDT().setBirthTimeCalc(personVO.getThePersonDT().getBirthTime());
	    		 }
     		 }
     		 
     		 personVO.getThePersonDT().setAsOfDateSex_s(asOfDate);
     		 
     		 if(selectedPatient.getPersonAsOfDate()!=null){
     			 personVO.getThePersonDT().setAsOfDateAdmin(selectedPatient.getPersonAsOfDate());
     		 }else{
     			 personVO.getThePersonDT().setAsOfDateAdmin_s(asOfDate);
     		 }
     		 
     		 if(selectedPatient.getMaritalStatus()!=null && selectedPatient.getMaritalStatus().length()>0){
	     		 personVO.getThePersonDT().setMaritalStatusCd(selectedPatient.getMaritalStatus());
	     		 personVO.getThePersonDT().setAsOfDateGeneral_s(asOfDate);
     		 }
     		 
     		 if(selectedPatient.getDeceasedInd()!=null && selectedPatient.getDeceasedInd().length()>0){
	     		 personVO.getThePersonDT().setDeceasedIndCd(selectedPatient.getDeceasedInd());
	     		 personVO.getThePersonDT().setDeceasedTime(selectedPatient.getDeceasedTime());
	     		 personVO.getThePersonDT().setAsOfDateMorbidity_s(asOfDate);
     		 }
     		 
     		 Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();
     		 if (arrELP == null) {
     			 arrELP = new ArrayList<Object> ();
     		 }
    		
     		 
    		if(selectedPatient.getStreetAddress().length()>0 || selectedPatient.getZip().length()>0 || selectedPatient.getState().length()>0 ||
    				selectedPatient.getCity().length()>0 || selectedPatient.getCntyCd().length()>0) {
	     		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
				elp.setItNew(true);
				elp.setItDirty(false);
				elp.setAddTime(currentTS);
				elp.setAddUserId(Long.valueOf(userId));
				elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elp.setEntityUid(patientUid);
				elp.setCd(NEDSSConstants.HOME);
				elp.setCdDescTxt(NEDSSConstants.HOUSE);
				elp.setClassCd(NEDSSConstants.POSTAL);
				elp.setUseCd(NEDSSConstants.HOME);
				elp.setAsOfDate_s(asOfDate);
				elp.setFromTime(selectedPatient.getAddressFromDate());
				elp.setToTime(selectedPatient.getAddressToDate());
	
				PostalLocatorDT pl = new PostalLocatorDT();
				pl.setItNew(true);
				pl.setItDirty(false);
				pl.setAddTime(currentTS);
				pl.setAddUserId(Long.valueOf(userId));
				pl.setRecordStatusTime(currentTS);
				
				pl.setStreetAddr1(selectedPatient.getStreetAddress());
				pl.setStreetAddr2(selectedPatient.getStreetAddress2());
				pl.setZipCd(selectedPatient.getZip());
				String nbsStateCd = hl7ToNBSObjectConverter.translateStateCd(selectedPatient.getState());
				nbsStateCd = nbsStateCd!=null ? nbsStateCd: selectedPatient.getState();
				pl.setStateCd(nbsStateCd);
				pl.setCityDescTxt(selectedPatient.getCity());
				pl.setCntyCd(selectedPatient.getCntyCd());
				if(selectedPatient.getCountryCd()!=null){
					String countryCd = XMLTypeToNBSObject.getNBSCodeFromPHINCodes("PSL_CNTRY", selectedPatient.getCountryCd(),NEDSSConstants.CODE_VALUE_GENERAL);
					countryCd = countryCd != null ? countryCd : selectedPatient.getCountryCd();
					
					pl.setCntryCd(countryCd);
				}
				
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	
				elp.setThePostalLocatorDT(pl);
				arrELP.add(elp);
    		}
    		
			if(selectedPatient.getPhoneNbr()!=null && selectedPatient.getPhoneNbr().length()>0){
				EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
				TeleLocatorDT teleDTHome = new TeleLocatorDT();
				elpHome.setItNew(true);
				elpHome.setItDirty(false);
				elpHome.setAddTime(currentTS);
				elpHome.setAddUserId(Long.valueOf(userId));
				elpHome.setEntityUid(patientUid);
				elpHome.setClassCd(NEDSSConstants.TELE);
				if(NEDSSConstants.CELL.equals(selectedPatient.getPhoneTypeCd())){
					elpHome.setCd(NEDSSConstants.CELL);
					elpHome.setUseCd(NEDSSConstants.MOBILE);
				}else{
					elpHome.setCd(NEDSSConstants.PHONE);
					elpHome.setUseCd(NEDSSConstants.HOME);
				}
				
				elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elpHome.setAsOfDate_s(asOfDate);
				teleDTHome.setPhoneNbrTxt(selectedPatient.getPhoneNbr());
				teleDTHome.setItNew(true);
				teleDTHome.setItDirty(false);
				teleDTHome.setAddTime(currentTS);
				teleDTHome.setAddUserId(Long.valueOf(userId));
				teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elpHome.setTheTeleLocatorDT(teleDTHome);
	
				arrELP.add(elpHome);
			}
			
			if(selectedPatient.getWorkPhoneNbr()!=null && selectedPatient.getWorkPhoneNbr().length()>0){
				EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
				TeleLocatorDT teleDTHome = new TeleLocatorDT();
				elpHome.setItNew(true);
				elpHome.setItDirty(false);
				elpHome.setAddTime(currentTS);
				elpHome.setAddUserId(Long.valueOf(userId));
				elpHome.setEntityUid(patientUid);
				elpHome.setClassCd(NEDSSConstants.TELE);
				elpHome.setCd(NEDSSConstants.PHONE);
				elpHome.setUseCd(NEDSSConstants.WORK_PHONE);
				elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elpHome.setAsOfDate_s(asOfDate);
				teleDTHome.setPhoneNbrTxt(selectedPatient.getWorkPhoneNbr());
				teleDTHome.setExtensionTxt(selectedPatient.getWorkPhoneExt());
				teleDTHome.setItNew(true);
				teleDTHome.setItDirty(false);
				teleDTHome.setAddTime(currentTS);
				teleDTHome.setAddUserId(Long.valueOf(userId));
				teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elpHome.setTheTeleLocatorDT(teleDTHome);
	
				arrELP.add(elpHome);
			}
			
			personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
			
     		 Collection<Object>  pdts = new ArrayList<Object> ();
	   		 PersonNameDT pdt = new PersonNameDT();
	   		 pdt.setItNew(true);
			 pdt.setItDirty(false);
			 pdt.setAddTime(currentTS);
			 pdt.setAddUserId(Long.valueOf(userId));
			 pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
			 pdt.setPersonNameSeq(new Integer(1));
			 pdt.setStatusTime(currentTS);
			 pdt.setRecordStatusTime(currentTS);
			 pdt.setPersonUid(personVO.getThePersonDT().getPersonUid());
			 pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
			 pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
	   		 pdt.setLastNm(selectedPatient.getLastName());
			 pdt.setFirstNm(selectedPatient.getFirstName());
			 pdt.setMiddleNm(selectedPatient.getMiddleName());
			 pdt.setAsOfDate_s(asOfDate);
			 
			 pdt.setNmSuffix(selectedPatient.getSuffix());
			 pdt.setNmPrefix(selectedPatient.getPrefix());
			 pdt.setNmDegree(selectedPatient.getDegree());
			 
			 pdt.setFromTime(selectedPatient.getPersonNameEffectiveDate());
			 pdt.setToTime(selectedPatient.getPersonNameExpirationDate());
			 
			 pdts.add(pdt);
	   		 personVO.setThePersonNameDTCollection(pdts);
   		 
     		 patientColl.add(personVO);

     		Collection<Object>  raceColl = new ArrayList<Object> ();
     		 for(int i=0;i<selectedPatient.getRaceList().size();i++){
	     		PersonRaceDT raceDT= new PersonRaceDT();
	     		raceDT.setItNew(true);
	     		raceDT.setItDelete(false);
	     		raceDT.setItDirty(false);
	     		raceDT.setAddTime(currentTS);
	     		raceDT.setAddUserId(Long.valueOf(userId));
	     		raceDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
	     		String raceCd = getCodeForConceptCode("RACE_CALCULATED", selectedPatient.getRaceList().get(i));
	     		if(raceCd!=null){
		     		raceDT.setRaceCategoryCd(raceCd);
		     		raceDT.setRaceCd(raceCd);
	     		}else{
	     			raceDT.setRaceCategoryCd(selectedPatient.getRaceList().get(i));
		     		raceDT.setRaceCd(selectedPatient.getRaceList().get(i));
	     		}
	     		
	     		raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	     		raceDT.setAsOfDate_s(asOfDate);
	     		raceColl.add(raceDT);
     		 }
     		 
     		 if (raceColl.size() > 0) {
     			 personVO.setThePersonRaceDTCollection(raceColl);
     		 }
     		
    		 if (patientColl.size() > 0) {
    			 proxyVO.setThePersonVOCollection(patientColl);
    		 }
    		 
    		 //Add EntityIDs
    		 
    		 Collection<Object> entityIdColl = new ArrayList<Object>();
 			
 			 StringTokenizer registryIDST = new StringTokenizer(selectedPatient.getListOfregistryPatientID(),"|");
 			 StringTokenizer assigningAuthoritiesST = new StringTokenizer(selectedPatient.getAssigningAuthorities(),"|");
 			 StringTokenizer identifierTypeCodesST = new StringTokenizer(selectedPatient.getIdentifierTypeCodes(),"|");
 			 StringTokenizer effectiveDatesST = new StringTokenizer(selectedPatient.getEffectiveDates(),"|");
 			 StringTokenizer expirationDatesST = new StringTokenizer(selectedPatient.getExpirationDates(),"|");
 				
 			 int i=1;
 			 while (registryIDST.hasMoreElements()) {
 				String registryId = registryIDST.nextToken();
 				String assigningAuthority = assigningAuthoritiesST.nextToken();
 				String idetnifierTypeCode = identifierTypeCodesST.nextToken();
 				String effectiveDate = effectiveDatesST.nextToken();
 				String expirationDate = expirationDatesST.nextToken();
 				
 				if(NULL.equals(registryId)){
 					registryId = null;
 				}
 				if(NULL.equals(assigningAuthority)){
 					assigningAuthority = null;
 				}
 				if(NULL.equals(idetnifierTypeCode)){
 					idetnifierTypeCode = null;
 				}
 				if(NULL.equals(effectiveDate)){
 					effectiveDate = null;
 				}
 				if(NULL.equals(expirationDate)){
 					expirationDate = null;
 				}
 				
 				EntityIdDT entityIdDT = createEntityID(personVO.getThePersonDT(), registryId, assigningAuthority, idetnifierTypeCode, i, effectiveDate, expirationDate);
 				i++;
 				entityIdColl.add(entityIdDT);
 			 }
 			 
    		 if(entityIdColl.size()>0)
    			 personVO.setTheEntityIdDTCollection(entityIdColl);
    		 
    		 String newUidSt=personVO.getThePersonDT().getPersonUid().toString();
    		 
    		 if (proxyVO.getTheParticipationDTCollection() == null)
	         {
	             Collection<Object> participationDTCollection = new ArrayList<Object>();
	             Collection<Object> nbsActEntityDT = new ArrayList<Object>();
	             proxyVO.setTheParticipationDTCollection(participationDTCollection);
	             proxyVO.getPageVO().setActEntityDTCollection(nbsActEntityDT);
	         }
    		 
             PageManagementCommonActionUtil.processGenericParticipation(null, null, newUidSt, proxyVO.getInterventionVO().getTheInterventionDT().getInterventionUid(), (PageActProxyVO) proxyVO, null,
            		 NEDSSConstants.SUBJECT_OF_VACCINE, NEDSSConstants.CLASS_CD_PAT, userId, null, NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE);
    		
             //Create act relationship
             HttpSession session = request.getSession();
 			 String DSInvestigationUid = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
 			 
 			 createActRelationship(proxyVO, new Long(tempInterventionId), new Long(DSInvestigationUid));
 			 
			 //Create Provider
			 if((vaccination.getProviderFirstName()!=null && vaccination.getProviderFirstName().length()>0) || (vaccination.getProviderLastName()!=null && vaccination.getProviderLastName().length()>0)){
				 PersonVO providerPersonVO = getProvider(vaccination, proxyVO, Long.valueOf(userId), new Long(DSInvestigationUid), asOfDate, tempID--, nbsSecurityObj);
				 proxyVO.getThePersonVOCollection().add(providerPersonVO);
			 }
			 
			 //Create Organization
			 if(vaccination.getOrgName()!=null && vaccination.getOrgName().length()>0){
				 OrganizationVO orgVO = getOrganization(vaccination, proxyVO, Long.valueOf(userId), new Long(DSInvestigationUid), tempID--, nbsSecurityObj);
				 Collection<Object> orgVOColl = new ArrayList<Object> ();
				 orgVOColl.add(orgVO);
				 proxyVO.setTheOrganizationVOCollection(orgVOColl);
			 }
			 
             //Store pageProxyVO
    		 proxyVO.setItNew(true);
    		 proxyVO.setItDirty(false);
             Long interventionUid = PageStoreUtil.sendProxyToPageEJB(proxyVO, request, NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE);
             
             return interventionUid;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	
	/**
	 * @param proxyVO
	 * @param interventionUid
	 * @param investigationUid
	 * @throws NEDSSAppException 
	 */
	private static void createActRelationship(PageActProxyVO proxyVO, long interventionUid, long investigationUid) throws NEDSSAppException{
		try{
			ActRelationshipDT actRelDT = new ActRelationshipDT();
			actRelDT.setItNew(true);
			actRelDT.setSourceActUid(interventionUid);
			actRelDT.setSourceClassCd(NEDSSConstants.INTERVENTION_CLASS_CODE);
			actRelDT.setTypeCd(NEDSSConstants.AR_TYPE_CODE);
			actRelDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			actRelDT.setTargetActUid(investigationUid);
			actRelDT.setTargetClassCd(NEDSSConstants.CASE);
			actRelDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			
			if(proxyVO.getTheActRelationshipDTCollection()==null || proxyVO.getTheActRelationshipDTCollection().size()==0){
				Collection<Object>  coll = new ArrayList<Object> ();
				coll.add(actRelDT);
				proxyVO.setTheActRelationshipDTCollection(coll);
			} else {
				for (Iterator<Object> itr1 = proxyVO.getTheActRelationshipDTCollection().iterator(); itr1.hasNext();)
	            {
					ActRelationshipDT actRelationshipDT = (ActRelationshipDT)itr1.next();
					if(actRelationshipDT!=null && actRelationshipDT.getTargetActUid().longValue() != investigationUid 
							&& actRelationshipDT.getSourceActUid().longValue() == interventionUid){
						//Executed in case of vaccination associated for conditionA and user is trying to import for conditionB, 
						//in this case vaccination will be updated and associated with conditionB
						proxyVO.getTheActRelationshipDTCollection().add(actRelDT);
						break;
					}
	            }
				 
			}
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	
	/**
	 * Creates provider for vaccination - IIS
	 * @param vaccination
	 * @param proxyVO
	 * @param userId
	 * @param publicHealthCaseUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSAppException
	 */
	public static PersonVO getProvider(VaccinationSearchResultDT vaccination, PageActProxyVO proxyVO, long userId, long publicHealthCaseUid, String asOfDate, int tempID, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		PersonVO personVO = new PersonVO();
		try{
			Timestamp currentTS = new Timestamp(new Date().getTime());
			 Collection<Object>  pdts = new ArrayList<Object> ();
	   		 PersonNameDT pdt = new PersonNameDT();
	   		 pdt.setItNew(true);
			 pdt.setItDirty(false);
			 pdt.setAddTime(currentTS);
			 pdt.setAddUserId(Long.valueOf(userId));
			 pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
			 pdt.setPersonNameSeq(new Integer(1));
			 pdt.setStatusTime(currentTS);
			 pdt.setRecordStatusTime(currentTS);
			 pdt.setPersonUid(new Long(tempID));
			 pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
			 pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
	   		 pdt.setLastNm(vaccination.getProviderLastName());
			 pdt.setFirstNm(vaccination.getProviderFirstName());
			 pdt.setMiddleNm(vaccination.getProviderMiddleInitial());
			 pdt.setNmDegree(vaccination.getProviderDegree());
			 pdt.setNmPrefix(vaccination.getProviderPrefix());
			 pdt.setNmSuffix(vaccination.getProviderSuffix());
			 pdt.setAsOfDate_s(asOfDate);
			 pdts.add(pdt);
	   		 personVO.setThePersonNameDTCollection(pdts);
	   		 
	   		PersonDT personDT = personVO.getThePersonDT();

			personDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
			personDT.setEdxInd(NEDSSConstants.EDX_TYPE_ENTITY);
			Timestamp ts = new Timestamp(new Date().getTime());
			personDT.setCd(NEDSSConstants.PRV);
			personDT.setPersonUid(pdt.getPersonUid());
			personDT.setItNew(true);
			personDT.setItDirty(false);
			personVO.setItNew(true);
			personVO.setItDirty(false);
			personDT.setVersionCtrlNbr(new Integer(1));
			personDT.setItNew(true);
			personDT.setLastChgTime(ts);
			personDT.setAddTime(ts);
			personDT.setLastChgUserId(userId);
			personDT.setAddUserId(userId);
			personDT.setStatusCd(NEDSSConstants.A);
			personDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			personDT.setRecordStatusTime(ts);
			personDT.setStatusTime(ts);
			
			if(personVO!=null){
				
				 int sizeOfEntityIdDTColl = 0;
				 if(personVO.getTheEntityIdDTCollection()!=null)
					sizeOfEntityIdDTColl = personVO.getTheEntityIdDTCollection().size();
			
				 EntityIdDT entityIdDT = createEntityID(personVO.getThePersonDT(), vaccination.getProviderId(), vaccination.getProviderAssigningAuthority(), vaccination.getIdTypeCd(), sizeOfEntityIdDTColl+1, null, null);
				 if(entityIdDT!=null){
					 Collection<Object> coll = new ArrayList<Object>();
					 coll.add(entityIdDT);
					 personVO.setTheEntityIdDTCollection(coll);
				 }
			 
				 EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
				 EDXActivityDetailLogDT eDXActivityDetailLogDT = util.getMatchingProvider(personVO, nbsSecurityObj);
				 if(!checkIfEntityAssociated(proxyVO, NEDSSConstants.PERSON, new Long(eDXActivityDetailLogDT.getRecordId()))){
					 removeExistingParticipationAndActEntity(proxyVO, NEDSSConstants.PERSON, new Long(eDXActivityDetailLogDT.getRecordId()));
					 setActEntityForCreate(proxyVO,NEDSSConstants.PERFORMER_OF_VACCINE, new Long(eDXActivityDetailLogDT.getRecordId()),NEDSSConstants.PERSON,proxyVO.getInterventionVO().getTheInterventionDT().getInterventionUid(),userId);
				 }else{
					 //use this flag to check if personVO need to add in collection
					 personVO.setItNew(false);
					 personVO.setItDirty(true);
				 }
			}
			
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return personVO;
	}
				
	
	private static boolean checkIfEntityAssociated(PageActProxyVO pageActProxyVO, String subjectClassCd, long recordId) throws NEDSSAppException{
		boolean isExist = false;
		try{
			for (Iterator<Object> itr1 = pageActProxyVO.getTheParticipationDTCollection().iterator(); itr1.hasNext();)
            {
                ParticipationDT participationDT = (ParticipationDT)itr1.next();
                
                if(subjectClassCd.equals(participationDT.getSubjectClassCd()) && participationDT.getSubjectEntityUid().longValue() == recordId){
                	isExist = true;
                	break;
                }
    		}
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return isExist;
	}
	
	/**
	 * @param personDT
	 * @param id
	 * @param assigningAuthority
	 * @param identifierTypeCd
	 * @param seqNbr
	 * @param effectiveTime
	 * @param expirationTime
	 * @return
	 * @throws NEDSSAppException
	 */
	private static EntityIdDT createEntityID(PersonDT personDT, String id, String assigningAuthority, String identifierTypeCd, int seqNbr, String effectiveTime, String expirationTime) throws NEDSSAppException{
		try{
			if(identifierTypeCd!=null && identifierTypeCd.length()>0){
		    	EntityIdDT entityIdDT = new EntityIdDT();
				entityIdDT.setEntityUid(personDT.getPersonUid());
				entityIdDT.setAddTime(personDT.getAddTime());
				entityIdDT.setEntityIdSeq(seqNbr);
				
				Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
				//converts incoming conceptCode to code from code_value_general table before storing in Database.
				CodeValueGeneralDT cvgDT = getCodeValueGeneralDTbyConceptCd("EI_AUTH_PAT",assigningAuthority);
				if(cvgDT!=null){
					entityIdDT.setAssigningAuthorityCd(cvgDT.getCode());
					entityIdDT.setAssigningAuthorityDescTxt(cvgDT.getCodeDescTxt());
				}else{
					assigningAuthority = assigningAuthority != null ? assigningAuthority : "";
					entityIdDT.setAssigningAuthorityCd(assigningAuthority);
				}
				
				if(ASSIGNING_AUTH_CD_SSA.equalsIgnoreCase(entityIdDT.getAssigningAuthorityCd())){
					entityIdDT.setRootExtensionTxt(hl7ToNBSObjectConverter.formatSSN(id));
				}else{
					entityIdDT.setRootExtensionTxt(id);
				}
				
				CodeValueGeneralDT cvgDT1 = getCodeValueGeneralDTbyConceptCd("EI_TYPE_PAT",identifierTypeCd);
				if(cvgDT1!=null){
					entityIdDT.setTypeCd(cvgDT1.getCode());
					entityIdDT.setTypeDescTxt(cvgDT1.getCodeDescTxt());
				}else{
					identifierTypeCd = identifierTypeCd != null ? identifierTypeCd : "";
					entityIdDT.setTypeCd(identifierTypeCd);
				}
				entityIdDT.setAddUserId(personDT.getAddUserId());
				entityIdDT.setAddTime(personDT.getAddTime());
				entityIdDT.setLastChgUserId(personDT.getLastChgUserId());
				entityIdDT.setStatusCd(NEDSSConstants.A);
				entityIdDT.setStatusTime(personDT.getAddTime());
				entityIdDT.setRecordStatusTime(personDT.getAddTime());
				entityIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				entityIdDT.setAsOfDate(personDT.getAsOfDateAdmin());
				entityIdDT.setLastChgTime(personDT.getLastChgTime());
				entityIdDT.setLastChgUserId(personDT.getLastChgUserId());
				
				if(effectiveTime!=null)
					entityIdDT.setValidFromTime_s(effectiveTime);
				if(expirationTime!=null)
					entityIdDT.setValidToTime_s(expirationTime);
				
				entityIdDT.setItNew(true);
				entityIdDT.setItDirty(false);
				
				return entityIdDT;
			}
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return null;
	}
	
	/**
	 * Creates organization for vaccination - IIS
	 * @param vaccination
	 * @param proxyVO
	 * @param userId
	 * @param publicHealthCaseUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSAppException
	 */
	public static OrganizationVO getOrganization(VaccinationSearchResultDT vaccination, PageActProxyVO proxyVO, long userId, long publicHealthCaseUid, int tempID, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		OrganizationVO orgVO = new OrganizationVO();
		try{
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			OrganizationDT orgDT = orgVO.getTheOrganizationDT();
			orgDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
			orgDT.setEdxInd(NEDSSConstants.EDX_TYPE_ENTITY);
			
			Timestamp ts = new Timestamp(new Date().getTime());
			Timestamp asOfTs = new Timestamp(new Date().getTime());
			if(vaccination.getVaccAdminDtS()!=null){
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				asOfTs = new Timestamp(formatter.parse(vaccination.getVaccAdminDtS()).getTime());
			}
					
			orgDT.setOrganizationUid(new Long(tempID));
			orgVO.setItDirty(false);
			orgVO.setItNew(true);
			orgDT.setVersionCtrlNbr(new Integer(1));
			orgDT.setItNew(true);
			orgDT.setLastChgTime(ts);
			orgDT.setAddTime(ts);
			orgDT.setLastChgUserId(userId);
			orgDT.setAddUserId(userId);
			orgDT.setStatusCd(NEDSSConstants.A);
			orgDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			orgDT.setRecordStatusTime(ts);
			orgDT.setStatusTime(ts);
			
			if(vaccination.getOrgAddress1()!=null || vaccination.getOrgCity()!=null 
					|| vaccination.getOrgState()!=null || vaccination.getOrgZip()!=null) {
				EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
				elp.setItNew(true);
				elp.setItDirty(false);
				elp.setAsOfDate(asOfTs);
				elp.setAddTime(new Timestamp(new Date().getTime()));
				elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elp.setStatusTime(ts);
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elp.setRecordStatusTime(ts);
				elp.setCd(NEDSSConstants.OFFICE_CD);
				elp.setCdDescTxt(NEDSSConstants.OFFICE);
				elp.setUseCd(NEDSSConstants.WORK_PLACE);
				elp.setClassCd(NEDSSConstants.POSTAL);
				elp.setLocatorDescTxt(vaccination.getOrgLocatorDescTxt());
				
				PostalLocatorDT pl = new PostalLocatorDT();
				pl.setItNew(true);
				pl.setItDirty(false);
				pl.setAddTime(ts);
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				pl.setRecordStatusTime(ts);
				pl.setStreetAddr1(vaccination.getOrgAddress1());
				pl.setStreetAddr2(vaccination.getOrgAddress2());
				pl.setCityDescTxt(vaccination.getOrgCity());
				String nbsStateCd = hl7ToNBSObjectConverter.translateStateCd(vaccination.getOrgState());
				pl.setStateCd(nbsStateCd);
				pl.setZipCd(vaccination.getOrgZip());
				if(vaccination.getOrgCountry()!=null){
					String countryCd = XMLTypeToNBSObject.getNBSCodeFromPHINCodes("PSL_CNTRY", vaccination.getOrgCountry(),NEDSSConstants.CODE_VALUE_GENERAL);
					countryCd = countryCd != null ? countryCd : vaccination.getOrgCountry();
					pl.setCntryCd(countryCd);
				}
				
				elp.setThePostalLocatorDT(pl);
				elp.setEntityUid(orgDT.getOrganizationUid());
				Collection<Object> elpCollection = new ArrayList<Object>();
				elpCollection.add(elp);
				
				orgVO.setTheEntityLocatorParticipationDTCollection(elpCollection);
			}
			
			Collection<Object> nameCollection = new ArrayList<Object>();
			OrganizationNameDT orgNameDT = new OrganizationNameDT();
			orgNameDT.setNmUseCd(NEDSSConstants.LEGAL);
			orgNameDT.setNmTxt(vaccination.getOrgName());
			orgNameDT.setAddTime(ts);
			orgNameDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			orgNameDT.setRecordStatusTime(ts);
			orgNameDT.setStatusCd(NEDSSConstants.A);
			orgNameDT.setStatusTime(ts);
			orgNameDT.setAddUserId(userId);
			orgNameDT.setItNew(true);
			orgNameDT.setItDirty(false);
			orgNameDT.setLastChgTime(ts);
			orgNameDT.setLastChgUserId(userId);
			orgNameDT.setOrganizationUid(orgDT.getOrganizationUid());
			orgNameDT.setOrganizationNameSeq(1);
			nameCollection.add(orgNameDT);
			
			orgVO.setTheOrganizationNameDTCollection(nameCollection);
			
			if(orgVO!=null){
				EDXActivityDetailLogDT eDXActivityDetailLogDT = new EDXActivityDetailLogDT();
				EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
				eDXActivityDetailLogDT = util.getMatchingOrganization(orgVO, nbsSecurityObj);
				
				if(!checkIfEntityAssociated(proxyVO, NEDSSConstants.ORGANIZATION, new Long(eDXActivityDetailLogDT.getRecordId()))){
					removeExistingParticipationAndActEntity(proxyVO, NEDSSConstants.ORGANIZATION, new Long(eDXActivityDetailLogDT.getRecordId()));
					setActEntityForCreate(proxyVO,NEDSSConstants.PERFORMER_OF_VACCINE, new Long(eDXActivityDetailLogDT.getRecordId()),NEDSSConstants.ORGANIZATION,proxyVO.getInterventionVO().getTheInterventionDT().getInterventionUid(),userId);
				}else{
					//use this flag to check if orgVO need to add in collection
					orgVO.setItDirty(true);
					orgVO.setItNew(false);
				}
			}
			
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return orgVO;
	}
	

	/**
	 * @param codeSetNm
	 * @return
	 * @throws NEDSSAppException
	 */
	private static ArrayList<Object> getCodeList(String codeSetNm) throws NEDSSAppException{
		try{
			MessageBuilderHelper helper = new MessageBuilderHelper();
			ArrayList codeList = helper.getCodeListByCodeSetNm(codeSetNm);
			return codeList;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * @param codeSetNm
	 * @param conceptCd
	 * @return
	 * @throws NEDSSAppException
	 */
	private static CodeValueGeneralDT getCodeValueGeneralDTbyConceptCd(String codeSetNm, String conceptCd) throws NEDSSAppException{
		try{
			if (conceptCdAndCVGMap.get(codeSetNm+conceptCd)==null) {
				ArrayList<Object> codeList = new ArrayList<Object>();

				codeList = getCodeList(codeSetNm);
				
				for (int i=0; i<codeList.size(); ++i) {
					CodeValueGeneralDT codeValueGeneralDT = (CodeValueGeneralDT) codeList.get(i);
					conceptCdAndCVGMap.put(codeSetNm+codeValueGeneralDT.getConceptCode(), codeValueGeneralDT);
				}
			}
			
			return (CodeValueGeneralDT) conceptCdAndCVGMap.get(codeSetNm+conceptCd);
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		
	}
	
	/**
	 * @param codeSetNm
	 * @param conceptCode
	 * @return
	 * @throws NEDSSAppException
	 */
	private static String getCodeForConceptCode(String codeSetNm, String conceptCode) throws NEDSSAppException{
		String code="";
		try{
			if(conceptCdAndCodeMap.get(codeSetNm+conceptCode)==null){
				ArrayList<Object> codeList = getCodeList(codeSetNm);
				for (int i=0; i<codeList.size(); ++i) {
					CodeValueGeneralDT codeValueGeneralDT = (CodeValueGeneralDT) codeList.get(i);
					conceptCdAndCodeMap.put(codeSetNm+codeValueGeneralDT.getConceptCode(), codeValueGeneralDT.getCode());
				}
			}
			code = (String) conceptCdAndCodeMap.get(codeSetNm+conceptCode);
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return code;
	}
	
	/**
	 * @param patientSearchVO
	 * @return
	 * @throws NEDSSAppException
	 */
	public static PatientSearchResultDT getPatientSearchResultDTForIISQuery(PatientSearchVO patientSearchVO) throws NEDSSAppException{
		PatientSearchResultDT patient = new PatientSearchResultDT();
		try{
			patient.setFirstName(patientSearchVO.getFirstName());
			patient.setLastName(patientSearchVO.getLastName());
			patient.setMiddleName(patientSearchVO.getMiddleName());
			String dob = patientSearchVO.getBirthTimeYear()+patientSearchVO.getBirthTimeMonth()+patientSearchVO.getBirthTimeDay();
			patient.setDob(dob);
			patient.setDobYYYYMMDD(dob);
			patient.setMotherMaidenLastName(patientSearchVO.getMotherMaidenName());
			patient.setStreetAddress(patientSearchVO.getStreetAddr1());
			patient.setCity(patientSearchVO.getCityDescTxt());
			patient.setState(patientSearchVO.getState());
			patient.setZip(patientSearchVO.getZipCd());
			patient.setPhoneNbr(patientSearchVO.getPhoneNbrTxt());
			patient.setMultipleBirthIndicator(patientSearchVO.getMultipleBirthIndicator());
			patient.setBirthOrder(patientSearchVO.getBirthOrder());
			patient.setSexCd(patientSearchVO.getCurrentSex());
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return patient;
	}
	
	/**
	 * Compares both patientsearch response and vaccine search response PID. If matches then renders list of vaccinations. Otherwise not to display.
	 * @param patientResponse
	 * @param vaccineResponse
	 * @return
	 * @throws NEDSSAppException
	 */
	public static boolean comparePatientFromPatientSearchAndVaccineSearchResponse(PatientSearchResultDT patientResponse, PatientSearchResultDT vaccineResponse) throws NEDSSAppException{
		boolean isMatch = false;
		try{
			if((patientResponse.getFirstName()!=null && patientResponse.getFirstName().equalsIgnoreCase(vaccineResponse.getFirstName())) && (patientResponse.getLastName()!=null && patientResponse.getLastName().equalsIgnoreCase(vaccineResponse.getLastName()))
					&& (patientResponse.getDobYYYYMMDD()!=null && patientResponse.getDobYYYYMMDD().equalsIgnoreCase(vaccineResponse.getDobYYYYMMDD()))
					&& (patientResponse.getListOfregistryPatientID()!=null && patientResponse.getListOfregistryPatientID().equalsIgnoreCase(vaccineResponse.getListOfregistryPatientID()))
					&& ((patientResponse.getStreetAddress()==null && vaccineResponse.getStreetAddress()==null) || (patientResponse.getStreetAddress()!=null && patientResponse.getStreetAddress().equalsIgnoreCase(vaccineResponse.getStreetAddress()))) && ((patientResponse.getCity()==null && vaccineResponse.getCity()==null) || (patientResponse.getCity()!=null && patientResponse.getCity().equalsIgnoreCase(vaccineResponse.getCity())))
					&& ((patientResponse.getState()==null) || (patientResponse.getState()!=null && patientResponse.getState().equalsIgnoreCase(vaccineResponse.getState()))) && ((patientResponse.getZip()==null && vaccineResponse.getZip()==null) || (patientResponse.getZip()!=null && patientResponse.getZip().equalsIgnoreCase(vaccineResponse.getZip()))))
					/*&& (patientResponse.getMiddleName()!=null && patientResponse.getMiddleName().equalsIgnoreCase(vaccineResponse.getMiddleName()))
					&& ((patientResponse.getMotherMaidenName()== null && vaccineResponse.getMotherMaidenName()==null) || (patientResponse.getMotherMaidenName()!=null && patientResponse.getMotherMaidenName().equalsIgnoreCase(vaccineResponse.getMotherMaidenName()))) && ((patientResponse.getSexCd()==null && vaccineResponse.getSexCd()==null) || (patientResponse.getSexCd()!=null && patientResponse.getSexCd().equals(vaccineResponse.getSexCd()))))*/{
				isMatch=true;
			}
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return isMatch;
	}
	
	
	/**
	 * @param hl7MsgStr
	 * @return
	 * @throws NEDSSAppException
	 */
	private static String convertHL7toXML(String hl7MsgStr) throws NEDSSAppException{
		try{
			Parser p = new PipeParser();
	     	p.setValidationContext(new NoValidation());
	     	String msgToParse = hl7MsgStr.replaceAll("\n", "\r\n");
	     	Message hl7msg = (RSP_K11) p.parse(msgToParse);
			XMLParser xmlParser = new DefaultXMLParser();
			String generatedXML = xmlParser.encode(hl7msg).substring(39);
            return generatedXML;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * @param hl7msg
	 * @param actUid
	 * @param edxDocumentParentUid
	 * @return
	 * @throws NEDSSAppException
	 */
	public static EDXDocumentDT populateEDXDocument(String hl7msg, Long actUid, Long edxDocumentParentUid) throws NEDSSAppException{
		try{
			EDXDocumentDT edxDocumentDT = new EDXDocumentDT();
			Timestamp time = new Timestamp(new Date().getTime());
			edxDocumentDT.setAddTime(time);
			edxDocumentDT.setActUid(actUid);
			edxDocumentDT.setDocTypeCd(DOC_TYPE_CD);
			edxDocumentDT.setPayload(convertHL7toXML(hl7msg));
			edxDocumentDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			edxDocumentDT.setRecordStatusTime(time);
			edxDocumentDT.setNbsDocumentMetadataUid(VAC_NBS_DOC_META_UID);
			edxDocumentDT.setOriginalDocTypeCd(ORIGINAL_DOC_TYPE_CD);
			edxDocumentDT.setOriginalPayload(hl7msg);
			edxDocumentDT.setEdxDocumentParentUid(edxDocumentParentUid);
			edxDocumentDT.setItDirty(false);
			edxDocumentDT.setItNew(true);
			return edxDocumentDT;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * @param edxDocumentDT
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 */
	public static Long createEDXDocument(EDXDocumentDT edxDocumentDT, HttpServletRequest request) throws NEDSSAppException{
		try{
			String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
	    	String sMethod = "createEDXDocument";
			Object[] sParams = new Object[] {edxDocumentDT};
			Object object = CallProxyEJB.callProxyEJB(sParams, sBeanJndiName, sMethod, request.getSession());
			Long edxDocumentUid = (Long) object;
			return edxDocumentUid;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * @param sourceUid
	 * @param interventionUid
	 * @param recordStatusCd
	 * @param exceptionTxt
	 * @param docName
	 * @param sourceName
	 * @param messageId
	 * @param entityNm
	 * @param accessionNbr
	 * @return
	 * @throws NEDSSAppException
	 */
	public static EDXActivityLogDT populateEDXActivityLogDT(Long sourceUid, Long interventionUid,String recordStatusCd, String exceptionTxt, String docName, String sourceName, String messageId, String entityNm, String accessionNbr, String docType, String sourceTypeCd) throws NEDSSAppException{
		try{
			EDXActivityLogDT dt = new EDXActivityLogDT();
			dt.setSourceUid(sourceUid); //EdxDocumentUid
		   	dt.setTargetUid(interventionUid);
		   	dt.setDocType(docType);
		   	dt.setRecordStatusCd(recordStatusCd);
		   	dt.setTargetTypeCd(IIS_TARGET_TYPE_CD);
		   	dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
		   	dt.setException(exceptionTxt);
		   	dt.setSourceTypeCd(sourceTypeCd);
		   	dt.setDocName(docName);
		   	dt.setSrcName(sourceName);
		   	dt.setMessageId(messageId);
		   	dt.setEntityNm(entityNm);
		   	dt.setAccessionNbr(accessionNbr);
		   	dt.setBusinessObjLocalId(null);	
		   	dt.setImpExpIndCd("I");
		   	return dt;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static EDXEventProcessDT populateEDXEventProcessDT(long edxDocumentUid, long interventionUid, String sourceEventId, long userId) throws NEDSSAppException{
		try{
			EDXEventProcessDT dt = new EDXEventProcessDT();
			dt.setNbsDocumentUid(null);
			dt.setEdxDocumentUid(edxDocumentUid);
			dt.setNbsEventUid(interventionUid);
			dt.setSourceEventId(sourceEventId);
			dt.setDocEventTypeCd(DOC_EVENT_TYPE_CD_VAC);
			dt.setAddUserId(userId);
			dt.setAddTime(new Timestamp(new Date().getTime()));
			dt.setParsedInd("Y");
			return dt;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static void createEDXEventProcess(EDXEventProcessDT edxEventProcessDT, HttpServletRequest request) throws NEDSSAppException{
		try{
			String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
	    	String sMethod = "createEDXEventProcess";
			Object[] sParams = new Object[] {edxEventProcessDT};
			Object object = CallProxyEJB.callProxyEJB(sParams, sBeanJndiName, sMethod, request.getSession());
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static EDXEventProcessDT getEDXEventProcessDTBySourceIdandEventType(String sourceId, String eventType, HttpServletRequest request) throws NEDSSAppException{
		try{
			String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
	    	String sMethod = "getEDXEventProcessDTBySourceIdandEventType";
			Object[] sParams = new Object[] {sourceId, eventType};
			Object object = CallProxyEJB.callProxyEJB(sParams, sBeanJndiName, sMethod, request.getSession());
			EDXEventProcessDT edxEventProcessDT = (EDXEventProcessDT) object;
			return edxEventProcessDT;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static void populateAndUpdatePageActProxyVO(PageActProxyVO pageActProxyVO, IISQueryForm iisQueryForm, VaccinationSearchResultDT vaccination, HttpServletRequest request) throws NEDSSAppException{
		try{
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter =new Hl7ToNBSObjectConverter();
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String asOfDate = null;
			Timestamp currentTS = new Timestamp(new Date().getTime());
			
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			
			PatientSearchResultDT selectedPatient = iisQueryForm.getSelectedPatient();
			
			InterventionDT interventionDT = pageActProxyVO.getInterventionVO().getTheInterventionDT();
			pageActProxyVO.getInterventionVO().setItDirty(true);
			pageActProxyVO.getInterventionVO().setItNew(false);
			
			//Set intervention for update
			interventionDT.setVaccInfoSourceCd(vaccination.getVaccInfoSourceCd());
        	
        	if(vaccination.getVaccAdminDtS()!=null){
        		Timestamp adminDTTimestamp = new Timestamp(formatter.parse(vaccination.getVaccAdminDtS()).getTime());
        		interventionDT.setActivityFromTime(adminDTTimestamp);
        		asOfDate = vaccination.getVaccAdminDtS();
        		Map ageMap = PersonUtil.calculateAge(selectedPatient.getDob(), vaccination.getVaccAdminDtS());
        		if(ageMap!=null){
        			if(ageMap.get("age")!=null){
        				Integer age = (Integer) ageMap.get("age");
        				interventionDT.setAgeAtVacc(age);
        			}
        			if(ageMap.get("unit")!=null){
        				String ageUnit = (String) ageMap.get("unit");
        				interventionDT.setAgeAtVaccUnitCd(ageUnit);
        			}
        		}
        	}
        	
        	if(asOfDate == null){
        		asOfDate = formatter.format(new Date());
        	}
        	
        	interventionDT.setVaccMfgrCd(vaccination.getVaccMfgrCd());
        	interventionDT.setVaccDoseNbr(vaccination.getVaccDoseNbr());
        	interventionDT.setTargetSiteCd(vaccination.getVaccBodySiteCd());
        	interventionDT.setMaterialCd(vaccination.getVaccTypeCd());
        	interventionDT.setMaterialLotNm(vaccination.getVaccLotNbr());
        	interventionDT.setMaterialExpirationTime(vaccination.getVaccExpDt());
        	
            //Setting TargetSiteDescription from targetSiteCd
            if(interventionDT.getTargetSiteCd()!=null)
            	interventionDT.setTargetSiteDescTxt(CachedDropDowns.getCodeDescTxtForCd(interventionDT.getTargetSiteCd(), "NIP_ANATOMIC_ST"));
            //Setting effectiveFromTime same as activityFromTime
            if(interventionDT.getActivityFromTime()!=null)
            	interventionDT.setEffectiveFromTime(interventionDT.getActivityFromTime());
             
            interventionDT.setLastChgUserId(Long.valueOf(userId));
            interventionDT.setLastChgTime(currentTS);
            
            interventionDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
            interventionDT.setRecordStatusTime(currentTS);
            
			interventionDT.setItNew(false);
			interventionDT.setItDirty(true);
        	
			Collection<Object> updatedPersonNameCollection = new ArrayList<Object>();
			
			// Update Person
			Iterator<Object> anIterator = null;
			for (anIterator = pageActProxyVO.getThePersonVOCollection().iterator(); anIterator.hasNext();) {
				PersonVO personVO = (PersonVO) anIterator.next();
				if (NEDSSConstants.PAT.equals(personVO.getThePersonDT().getCd())) {
					
					// Update Person
					
		     		personVO.setItNew(false);
		     		personVO.setItDirty(true);
		     		personVO.getThePersonDT().setItNew(false);
		     		personVO.getThePersonDT().setItDirty(true);
		     		personVO.getThePersonDT().setLastChgUserId(Long.valueOf(userId));
		     		personVO.getThePersonDT().setLastChgTime(currentTS);
		     		personVO.getThePersonDT().setCurrSexCd(selectedPatient.getSexCd());
		     		personVO.getThePersonDT().setAddUserId(Long.valueOf(userId));
		     		
		     		if(selectedPatient.getDob()!=null){
			     		personVO.getThePersonDT().setBirthTime(StringUtils.stringToStrutsTimestamp(selectedPatient.getDob()));
			    		if(personVO.getThePersonDT().getBirthTime() != null) {
			    			personVO.getThePersonDT().setBirthTimeCalc(personVO.getThePersonDT().getBirthTime());
			    		}
		     		}
					
		     		personVO.getThePersonDT().setAsOfDateSex_s(asOfDate);
					
		     		
		     		if(selectedPatient.getPersonAsOfDate()!=null){
		     			personVO.getThePersonDT().setAsOfDateAdmin(selectedPatient.getPersonAsOfDate());
		     		}else{
		     			personVO.getThePersonDT().setAsOfDateAdmin_s(asOfDate);
		     		}
		     		 
		     		if(selectedPatient.getMaritalStatus()!=null && selectedPatient.getMaritalStatus().length()>0){
		     			personVO.getThePersonDT().setMaritalStatusCd(selectedPatient.getMaritalStatus());
			     		personVO.getThePersonDT().setAsOfDateGeneral_s(asOfDate);
		     		}
		     		 
		     		if(selectedPatient.getDeceasedInd()!=null && selectedPatient.getDeceasedInd().length()>0){
		     			personVO.getThePersonDT().setDeceasedIndCd(selectedPatient.getDeceasedInd());
			     		personVO.getThePersonDT().setDeceasedTime(selectedPatient.getDeceasedTime());
			     		personVO.getThePersonDT().setAsOfDateMorbidity_s(asOfDate);
		     		}
		     		 		     		
		     		if(selectedPatient.getEthnicity()!=null && selectedPatient.getEthnicity().length()>0){
		     			 String ethnicityCode = getCodeForConceptCode("PHVS_ETHNICITYGROUP_CDC_UNK", selectedPatient.getEthnicity());
		     			 if(ethnicityCode!=null){
				     		 personVO.getThePersonDT().setEthnicGroupInd(ethnicityCode);
				     		 personVO.getThePersonDT().setAsOfDateEthnicity_s(asOfDate);
				     		 
				     		 ArrayList<Object> ethnicityList = new ArrayList<Object>();
				     		 PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
				     		 if(personVO.getThePersonEthnicGroupDTCollection()!=null)
				     			 ethnicityList = (ArrayList<Object>) personVO.getThePersonEthnicGroupDTCollection();
				     		 
				     		 for(int i=0;i<ethnicityList.size();i++){
				     			 ethnicDT = (PersonEthnicGroupDT) ethnicityList.get(0);
					     		 if(ethnicDT!=null && ethnicDT.getEthnicGroupCd().equals(ethnicityCode)){
					     			 ethnicDT.setItNew(false);
						     		 ethnicDT.setItDirty(true);
						     		 ethnicDT.setItDelete(false);
					     		 }
					     		 if(ethnicDT!=null && !ethnicDT.getEthnicGroupCd().equals(ethnicityCode)){
					     			 ethnicDT.setItNew(true);
						     		 ethnicDT.setItDirty(false);
						     		 ethnicDT.setItDelete(false);
						     		 ethnicDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
						     		 ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						     		 ethnicDT.setAddTime(currentTS);
						     		 ethnicDT.setRecordStatusTime(currentTS);
						     		 ethnicityList.add(ethnicDT);
						     		 personVO.setThePersonEthnicGroupDTCollection(ethnicityList);
					     		 }
					     		 
					     		 ethnicDT.setEthnicGroupCd(ethnicityCode);
					     		 ethnicDT.setAddUserId(Long.valueOf(userId));
					     		 ethnicDT.setLastChgUserId(Long.valueOf(userId));
					     		 ethnicDT.setLastChgTime(currentTS);
				     		 }
		     			 }
		     		 }else{
		     			for (Iterator<Object> it = personVO.getThePersonEthnicGroupDTCollection().iterator(); it.hasNext();) {
							PersonEthnicGroupDT personEthnicGroupDT = (PersonEthnicGroupDT) it.next();
							personEthnicGroupDT.setItDelete(true);
							personEthnicGroupDT.setItDirty(false);
							personEthnicGroupDT.setItNew(false);
						}
		     		 }
		     		
		     		ArrayList<Object> existingRaceList = new ArrayList<Object>();
		     		ArrayList<Object> newRaceList = new ArrayList<Object>();
		     		
		     		HashMap<String,PersonRaceDT> existingRaceMap = new HashMap<String,PersonRaceDT>();
		     		
		     		if(personVO.getThePersonRaceDTCollection()!=null)
		     			existingRaceList = (ArrayList<Object>) personVO.getThePersonRaceDTCollection();

		     		for(int i=0;i<existingRaceList.size();i++){
		     			PersonRaceDT raceDT1 = (PersonRaceDT) existingRaceList.get(i);
		     			existingRaceMap.put(raceDT1.getRaceCd(), raceDT1);
		     		}
		     			
		     		if(selectedPatient.getRaceList().size()>0){
			     		 for(int i=0;i<selectedPatient.getRaceList().size();i++){
			     			String raceCd = getCodeForConceptCode("RACE_CALCULATED", selectedPatient.getRaceList().get(i));
			     			
			     			if(raceCd == null)
			     				raceCd = selectedPatient.getRaceList().get(i);
			     			
				     		PersonRaceDT raceDT= new PersonRaceDT();
				     		
				     		if(existingRaceMap.get(raceCd)!=null){
				     			raceDT = existingRaceMap.remove(raceCd);
				     			raceDT.setItNew(false);
					     		raceDT.setItDelete(false);
					     		raceDT.setItDirty(true);
				     		}else{
				     			raceDT.setItNew(true);
					     		raceDT.setItDelete(false);
					     		raceDT.setItDirty(false);
					     		raceDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
					     		raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					     		raceDT.setAsOfDate_s(asOfDate);
					     		raceDT.setAddTime(currentTS);
					     		raceDT.setAddUserId(Long.valueOf(userId));
					     		newRaceList.add(raceDT);
				     		}
				     		
				     		if(raceCd!=null){
					     		raceDT.setRaceCategoryCd(raceCd);
					     		raceDT.setRaceCd(raceCd);
				     		}else{
				     			raceDT.setRaceCategoryCd(selectedPatient.getRaceList().get(i));
					     		raceDT.setRaceCd(selectedPatient.getRaceList().get(i));
				     		}
				     		
			     		 }
			     		 
			     		//Delete if race does not exist in selectedPatient.getRaceList(), existingRaceMap has only unmatched races.
			     		for (String key : existingRaceMap.keySet()){
			     			PersonRaceDT raceDT = existingRaceMap.get(key);
			     			raceDT.setItNew(false);
				     		raceDT.setItDelete(true);
				     		raceDT.setItDirty(false);
			     	    }
			     		
			     		 if (newRaceList.size() > 0) {
			     			 personVO.getThePersonRaceDTCollection().addAll(newRaceList);
			     		 }
		     		}else{
		     			for (Iterator<Object> it = personVO.getThePersonRaceDTCollection().iterator(); it.hasNext();)
				        {
							PersonRaceDT personRaceDT = (PersonRaceDT)it.next();
							personRaceDT.setItDelete(true);
							personRaceDT.setItDirty(false);
							personRaceDT.setItNew(false);
				        }
		     		}
		     		 
		     		// Update Person_name
		     		
		     		// Delete Person_name
		     		 
		     		int nameSeq=0;
		     		if(personVO.getThePersonNameDTCollection()!=null && personVO.getThePersonNameDTCollection().size()>0){
						for (Iterator<Object> it = personVO.getThePersonNameDTCollection().iterator(); it.hasNext();)
				        {
							PersonNameDT personNameDT = (PersonNameDT)it.next();
							personNameDT.setItDelete(true);
							personNameDT.setItDirty(false);
							personNameDT.setItNew(false);
							if(personNameDT.getPersonNameSeq()>nameSeq)
								nameSeq = personNameDT.getPersonNameSeq();
							updatedPersonNameCollection.add(personNameDT);
				        }
					}
		     		
		     		// Insert Person_name
		     		
		     		Collection<Object>  pdts = new ArrayList<Object> ();
			   		PersonNameDT pdt = new PersonNameDT();
			   		pdt.setItNew(true);
					pdt.setItDirty(false);
					pdt.setAddTime(currentTS);
					pdt.setAddUserId(Long.valueOf(userId));
					pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
					pdt.setPersonNameSeq(new Integer(nameSeq+1));
					pdt.setStatusTime(currentTS);
					pdt.setRecordStatusTime(currentTS);
					pdt.setPersonUid(personVO.getThePersonDT().getPersonUid());
					pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
					pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			   		pdt.setLastNm(selectedPatient.getLastName());
					pdt.setFirstNm(selectedPatient.getFirstName());
					pdt.setMiddleNm(selectedPatient.getMiddleName());
					pdt.setAsOfDate_s(asOfDate);
					
					pdt.setNmSuffix(selectedPatient.getSuffix());
					pdt.setNmPrefix(selectedPatient.getPrefix());
					pdt.setNmDegree(selectedPatient.getDegree());
					 
					pdt.setFromTime(selectedPatient.getPersonNameEffectiveDate());
					pdt.setToTime(selectedPatient.getPersonNameExpirationDate());
					 
					personVO.setThePersonNameDTCollection(new ArrayList());
			   		personVO.getThePersonNameDTCollection().add(pdt); 
			   		personVO.getThePersonNameDTCollection().addAll(updatedPersonNameCollection);
			   		
					
		     		// Update locator
			   		
			   		Long patientUid = personVO.getThePersonDT().getPersonUid();
					
					ArrayList<Object>  arrELP = (ArrayList<Object>) personVO.getTheEntityLocatorParticipationDTCollection();
		     		if (arrELP == null) {
		     			arrELP = new ArrayList<Object> ();
		     			personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
		     		}
		    		 
		    		if(selectedPatient.getStreetAddress().length()>0 || selectedPatient.getZip().length()>0 || selectedPatient.getState().length()>0 ||
		    				selectedPatient.getCity().length()>0 || selectedPatient.getCntyCd().length()>0) {
			     		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
			     		PostalLocatorDT pl = new PostalLocatorDT();
			     		if(arrELP.size()>0){
			     			for(int i=0;i<arrELP.size();i++){
			     				elp = (EntityLocatorParticipationDT) arrELP.get(i);
			     				//Find existing for update
			     				if(NEDSSConstants.HOME.equals(elp.getCd()) && NEDSSConstants.POSTAL.equals(elp.getClassCd())){
			     					elp.setItNew(false);
									elp.setItDirty(true);
									pl = elp.getThePostalLocatorDT();
			     					break;
			     				}
			     			}
			     			
			     		}
			     		
			     		if(!elp.isItDirty()){//create new if not exist{
			     			elp.setItNew(true);
							elp.setItDirty(false);
							elp.setAddTime(currentTS);
							elp.setAddUserId(Long.valueOf(userId));
							elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
							elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							elp.setEntityUid(patientUid);
							elp.setCd(NEDSSConstants.HOME);
							elp.setCdDescTxt(NEDSSConstants.HOUSE);
							elp.setClassCd(NEDSSConstants.POSTAL);
							elp.setUseCd(NEDSSConstants.HOME);
							elp.setAsOfDate_s(asOfDate);
							
							pl.setItNew(true);
							pl.setItDirty(false);
							pl.setAddTime(currentTS);
							pl.setAddUserId(Long.valueOf(userId));
							pl.setRecordStatusTime(currentTS);
							pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			     		}
						
						elp.setFromTime(selectedPatient.getAddressFromDate());
						elp.setToTime(selectedPatient.getAddressToDate());
						
						pl.setStreetAddr1(selectedPatient.getStreetAddress());
						pl.setStreetAddr2(selectedPatient.getStreetAddress2());
						pl.setZipCd(selectedPatient.getZip());
						String nbsStateCd = hl7ToNBSObjectConverter.translateStateCd(selectedPatient.getState());
						nbsStateCd = nbsStateCd!=null ? nbsStateCd: selectedPatient.getState();
						pl.setStateCd(nbsStateCd);
						pl.setCityDescTxt(selectedPatient.getCity());
						pl.setCntyCd(selectedPatient.getCntyCd());
						if(selectedPatient.getCountryCd()!=null){
							String countryCd = XMLTypeToNBSObject.getNBSCodeFromPHINCodes("PSL_CNTRY", selectedPatient.getCountryCd(),NEDSSConstants.CODE_VALUE_GENERAL);
							countryCd = countryCd != null ? countryCd : selectedPatient.getCountryCd();
							
							pl.setCntryCd(countryCd);
						}
						
						elp.setThePostalLocatorDT(pl);
						if(elp.isItNew())
							arrELP.add(elp);
		    		}
		    		
					if(selectedPatient.getPhoneNbr()!=null && selectedPatient.getPhoneNbr().length()>0){
						EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
						TeleLocatorDT teleDTHome = new TeleLocatorDT();
						if(arrELP.size()>0){
			     			for(int i=0;i<arrELP.size();i++){
			     				elpHome = (EntityLocatorParticipationDT) arrELP.get(i);
			     				//Find existing for update
			     				if((NEDSSConstants.PHONE.equals(elpHome.getCd()) && NEDSSConstants.HOME.equals(elpHome.getUseCd())) ||
			     						(NEDSSConstants.CELL.equals(elpHome.getCd()) && NEDSSConstants.MOBILE.equals(elpHome.getUseCd()))){
			     					elpHome.setItNew(false);
			     					elpHome.setItDirty(true);
			     					teleDTHome = elpHome.getTheTeleLocatorDT();
			     					teleDTHome.setItNew(false);
									teleDTHome.setItDirty(true);
									teleDTHome.setLastChgTime(currentTS);
									teleDTHome.setLastChgUserId(Long.valueOf(userId));
			     					break;
			     				}
			     			}
			     		}
						
						if(!elpHome.isItDirty()){//create new if not exist
							elpHome.setItNew(true);
							elpHome.setItDirty(false);
							elpHome.setAddTime(currentTS);
							elpHome.setAddUserId(Long.valueOf(userId));
							elpHome.setEntityUid(patientUid);
							elpHome.setClassCd(NEDSSConstants.TELE);
							elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
							elpHome.setAsOfDate_s(asOfDate);
							teleDTHome.setItNew(true);
							teleDTHome.setItDirty(false);
							teleDTHome.setAddTime(currentTS);
							teleDTHome.setAddUserId(Long.valueOf(userId));
							teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						}
						
						if(NEDSSConstants.CELL.equals(selectedPatient.getPhoneTypeCd())){
							elpHome.setCd(NEDSSConstants.CELL);
							elpHome.setUseCd(NEDSSConstants.MOBILE);
						}else{
							elpHome.setCd(NEDSSConstants.PHONE);
							elpHome.setUseCd(NEDSSConstants.HOME);
						}
						
						teleDTHome.setPhoneNbrTxt(selectedPatient.getPhoneNbr());
						
						elpHome.setTheTeleLocatorDT(teleDTHome);
			
						if(elpHome.isItNew())
							arrELP.add(elpHome);
					}
					
					if(selectedPatient.getWorkPhoneNbr()!=null && selectedPatient.getWorkPhoneNbr().length()>0){
						EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
						TeleLocatorDT teleDTHome = new TeleLocatorDT();
						
						if(arrELP.size()>0){
			     			for(int i=0;i<arrELP.size();i++){
			     				elpHome = (EntityLocatorParticipationDT) arrELP.get(i);
			     				//Find existing for update
			     				if(NEDSSConstants.PHONE.equals(elpHome.getCd()) && NEDSSConstants.WORK_PHONE.equals(elpHome.getUseCd())){
			     					elpHome.setItNew(false);
									elpHome.setItDirty(true);
									teleDTHome = elpHome.getTheTeleLocatorDT();
									teleDTHome.setItNew(false);
									teleDTHome.setItDirty(true);
									teleDTHome.setLastChgTime(currentTS);
									teleDTHome.setLastChgUserId(Long.valueOf(userId));
									break;
			     				}
			     			}
						}
						
						if(!elpHome.isItDirty()){//create new if not exist
							elpHome.setItNew(true);
							elpHome.setItDirty(false);
							elpHome.setAddTime(currentTS);
							elpHome.setAddUserId(Long.valueOf(userId));
							elpHome.setEntityUid(patientUid);
							elpHome.setClassCd(NEDSSConstants.TELE);
							elpHome.setCd(NEDSSConstants.PHONE);
							elpHome.setUseCd(NEDSSConstants.WORK_PHONE);
							elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
							elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
							elpHome.setAsOfDate_s(asOfDate);
							teleDTHome.setItNew(true);
							teleDTHome.setItDirty(false);
							teleDTHome.setAddTime(currentTS);
							teleDTHome.setAddUserId(Long.valueOf(userId));
							teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						}
						
						teleDTHome.setPhoneNbrTxt(selectedPatient.getWorkPhoneNbr());
						teleDTHome.setExtensionTxt(selectedPatient.getWorkPhoneExt());
						
						elpHome.setTheTeleLocatorDT(teleDTHome);
			
						if(elpHome.isItNew())
							arrELP.add(elpHome);
					}
					
					// Update entity_id
					
					ArrayList<Object> entityIdList = (ArrayList<Object>) personVO.getTheEntityIdDTCollection();
					if(entityIdList == null){
						Collection<Object> entityIdColl = new ArrayList<Object>();
						personVO.setTheEntityIdDTCollection(entityIdColl);
					}
					
					int entityIdSeq=0;
					Collection<Object> updatedtheEntityIdDTCollection = new ArrayList<Object>();
	 				if(personVO.getTheEntityIdDTCollection()!=null && personVO.getTheEntityIdDTCollection().size()>0){
						for (Iterator<Object> it = personVO.getTheEntityIdDTCollection().iterator(); it.hasNext();)
				        {
							EntityIdDT entityIDDT = (EntityIdDT)it.next();
						
							entityIDDT.setItDelete(true);
							entityIDDT.setItDirty(false);
							entityIDDT.setItNew(false);
							if(entityIDDT.getEntityIdSeq()>entityIdSeq)
								entityIdSeq = entityIDDT.getEntityIdSeq();
							updatedtheEntityIdDTCollection.add(entityIDDT);
							
				        }
					}
	 				
					StringTokenizer registryIDST = new StringTokenizer(selectedPatient.getListOfregistryPatientID(),"|");
		 			StringTokenizer assigningAuthoritiesST = new StringTokenizer(selectedPatient.getAssigningAuthorities(),"|");
		 			StringTokenizer identifierTypeCodesST = new StringTokenizer(selectedPatient.getIdentifierTypeCodes(),"|");
		 			StringTokenizer effectiveDatesST = new StringTokenizer(selectedPatient.getEffectiveDates(),"|");
		 			StringTokenizer expirationDatesST = new StringTokenizer(selectedPatient.getExpirationDates(),"|");
		 				
		 			int i=1;
		 			while (registryIDST.hasMoreElements()) {
		 				String registryId = registryIDST.nextToken();
		 				String assigningAuthority = assigningAuthoritiesST.nextToken();
		 				String identifierTypeCode = identifierTypeCodesST.nextToken();
		 				String effectiveDate = effectiveDatesST.nextToken();
		 				String expirationDate = expirationDatesST.nextToken();
		 				
		 				if(NULL.equals(registryId)){
		 					registryId = null;
		 				}
		 				if(NULL.equals(assigningAuthority)){
		 					assigningAuthority = null;
		 				}
		 				if(NULL.equals(identifierTypeCode)){
		 					identifierTypeCode = null;
		 				}
		 				if(NULL.equals(effectiveDate)){
		 					effectiveDate = null;
		 				}
		 				if(NULL.equals(expirationDate)){
		 					expirationDate = null;
		 				}
		 				
						EntityIdDT entityIdDT = createEntityID(personVO.getThePersonDT(), registryId, assigningAuthority, identifierTypeCode, ++entityIdSeq, effectiveDate, expirationDate);
		 				updatedtheEntityIdDTCollection.add(entityIdDT);
		 				
		 			}
		 			 
		 			personVO.setTheEntityIdDTCollection(updatedtheEntityIdDTCollection);
					
		 			
		 			//Create act relationship
		 			HttpSession session = request.getSession();
		 			String DSInvestigationUid = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
		 			
		 			createActRelationship(pageActProxyVO, interventionDT.getInterventionUid(), new Long(DSInvestigationUid));
		 			 
					// Check if provider exist then update otherwise create it
					
		 			int tempID = -1;
		 			
		 			if((vaccination.getProviderFirstName()!=null && vaccination.getProviderFirstName().length()>0) || (vaccination.getProviderLastName()!=null && vaccination.getProviderLastName().length()>0)){
						PersonVO providerPersonVO = getProvider(vaccination, pageActProxyVO, Long.valueOf(userId), new Long(DSInvestigationUid), asOfDate, tempID--, nbsSecurityObj);
						if(providerPersonVO.isItNew())
							pageActProxyVO.getThePersonVOCollection().add(providerPersonVO);
					}
		 			
					// Check if organization exist then update otherwise create it
					
					if(vaccination.getOrgName()!=null && vaccination.getOrgName().length()>0){
						OrganizationVO orgVO = getOrganization(vaccination, pageActProxyVO, Long.valueOf(userId), new Long(DSInvestigationUid), tempID--, nbsSecurityObj);
						if(orgVO.isItNew()){
							Collection<Object> orgVOColl = new ArrayList<Object> ();
							orgVOColl.add(orgVO);
							pageActProxyVO.setTheOrganizationVOCollection(orgVOColl);
						}
					}
					 
					 pageActProxyVO.setItNew(false);
					 pageActProxyVO.setItDirty(true);
		             PageStoreUtil.sendProxyToPageEJB(pageActProxyVO, request, NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE);
		             break;
				}
			}
			
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static void setActEntityForCreate(PageActProxyVO proxyVO, String entityType, Long subjectEntityUID, String entityClassCd, Long actUid, Long addUserId) {
		int vcNum = 1;

		NbsActEntityDT nbsActEntityDT = new NbsActEntityDT();
		nbsActEntityDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		nbsActEntityDT.setAddUserId(addUserId);
		nbsActEntityDT.setEntityUid(subjectEntityUID);
		nbsActEntityDT.setEntityVersionCtrlNbr(new Integer(vcNum));
		nbsActEntityDT.setActUid(actUid);
		nbsActEntityDT.setTypeCd(entityType);
		createParticipation(proxyVO, subjectEntityUID, entityClassCd, entityType, actUid);
		if (proxyVO != null) {
			if (proxyVO.getPageVO().getActEntityDTCollection().size() == 0) {
				Collection<Object> coll = new ArrayList<Object>();
				coll.add(nbsActEntityDT);
				proxyVO.getPageVO().setActEntityDTCollection(coll);
			} else {
				proxyVO.getPageVO().getActEntityDTCollection().add(nbsActEntityDT);
			}
		} 
	}
	
	/**
	 * In case of setting new provider/organization, it removes the old one from theParticipationDTCollection
	 * 
	 * @param proxyVO
	 * @param entityClassCd
	 * @param recordId
	 * @throws NEDSSAppException
	 */
	public static void removeExistingParticipationAndActEntity(PageActProxyVO proxyVO, String entityClassCd, long recordId) throws NEDSSAppException {
		try{
			for (Iterator<Object> itr1 = proxyVO.getTheParticipationDTCollection().iterator(); itr1.hasNext();)
            {
                ParticipationDT participationDT = (ParticipationDT)itr1.next();
                if(entityClassCd.equals(participationDT.getSubjectClassCd()) && participationDT.getSubjectEntityUid().longValue() != recordId){
                	itr1.remove();
                	for(Iterator<Object> itr2 = proxyVO.getPageVO().getActEntityDTCollection().iterator(); itr2.hasNext();){
                		NbsActEntityDT nbsActEntityDT1 = (NbsActEntityDT) itr2.next();
                		if(participationDT.getSubjectEntityUid().longValue() == nbsActEntityDT1.getEntityUid().longValue()){
                			itr2.remove();
                		}
                	}
                }
    		}
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	public static ParticipationDT createParticipation(PageActProxyVO pageActProxyVO, Long subjectEntityUid, String subjectClassCd, String typeCd, Long actUid) {

		ParticipationDT participationDT = new ParticipationDT();
		participationDT.setActClassCd(NEDSSConstants.CLASS_CD_INTV);
		participationDT.setActUid(actUid);
		participationDT.setSubjectClassCd(subjectClassCd);
		participationDT.setSubjectEntityUid(subjectEntityUid);
		participationDT.setTypeCd(typeCd.trim());
		participationDT.setTypeDescTxt(cdv.getDescForCode("PAR_TYPE", typeCd.trim()));
		participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		participationDT.setItNew(true);
		participationDT.setItDirty(false);
		
		if(pageActProxyVO!=null){
			if(pageActProxyVO.getTheParticipationDTCollection()==null)
				pageActProxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
			pageActProxyVO.getTheParticipationDTCollection().add(participationDT);
		}
		return participationDT;
	}
	
	/**
	 * Before parsing response from IIS, check for error condition.
	 * 
	 * @param queryResponseStr
	 * @param iisQueryForm
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String checkForErrorResponse(String queryResponseStr, IISQueryForm iisQueryForm) throws NEDSSAppException{
		String errorResponse = NO_ERROR;
		try{
			Parser p = new PipeParser();
	     	p.setValidationContext(new NoValidation());
	     	String responseMsgToParse = queryResponseStr.replaceAll("\n", "\r\n");
	     	RSP_K11 rsp = (RSP_K11) p.parse(responseMsgToParse);
	     	
	     	ERR err = rsp.getERR();
	     	QAK qak = rsp.getQAK();
	     	MSH msh = rsp.getMSH();
	     	MSA msa = rsp.getMSA();
	     	
	     	if(msh!=null){
		     	if(msh.getMessageType()!=null){
		     		iisQueryForm.getErrorAttributeMap().put(ERROR_RSP_DOC_NM, msh.getMessageType().getMsg1_MessageCode()+"^"+msh.getMessageType().getMsg2_TriggerEvent()+"^"+msh.getMessageType().getMsg3_MessageStructure());
		     	}
		     	if(msh.getMessageControlID()!=null){
		     		iisQueryForm.getErrorAttributeMap().put(ERROR_RSP_MESSAGE_ID, msh.getMessageControlID().getValue());
		     	}
		     	if(msh.getMsh21_MessageProfileIdentifier(0)!=null && msh.getMsh21_MessageProfileIdentifier(0).getEi1_EntityIdentifier()!=null){
		     		iisQueryForm.getErrorAttributeMap().put(ERROR_RSP_DOC_TYPE, msh.getMsh21_MessageProfileIdentifier(0).getEi1_EntityIdentifier().getValue());
		     	}
		     	if(msh.getMsh3_SendingApplication()!=null && msh.getMsh3_SendingApplication().getHd1_NamespaceID()!=null){
		     		iisQueryForm.getErrorAttributeMap().put(ERROR_RSP_SOURCE_NM,msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		     	}
	     	}
	     	
	     	if(err!=null){
	     		if(err.getErr3_HL7ErrorCode()!=null && err.getErr3_HL7ErrorCode().getComponents()!=null){
	     			String err3 = "";
	     			Type[] types = err.getErr3_HL7ErrorCode().getComponents();
	     			for(int j=0;j<types.length;j++){
	     				if(types[j]!=null && types[j].toString()!=null){
	     					err3 = err3+types[j].toString()+"^";
	     				}
	     			}
	     			if(err3.length()>0)
	     				err3 = err3.substring(0, err3.length()-1);
		     		iisQueryForm.getErrorAttributeMap().put(ERROR_RSP_ERROR_CODE,err3);
		     		
		     	}
		     	if(err.getErr8_UserMessage()!=null){
		     		iisQueryForm.getErrorAttributeMap().put(ERROR_RSP_USER_MESSAGE,err.getErr8_UserMessage().getValue());
		     	}
	     	}
	     	
	     	
	     	if(err!=null){
	     		errorResponse = ERROR_WITHOUT_QUERY_RESPONSE_STATUS;
	     		iisQueryForm.getErrorAttributeMap().put(ERROR_SEGMENT,"ERR");
	     	}
	     	
	     	if(msa!=null && msa.getMsa1_AcknowledgmentCode()!=null){
	     		//error values :  AE, AR, CE, CR
	     		errorResponse = msa.getMsa1_AcknowledgmentCode().getValue();
	     		iisQueryForm.getErrorAttributeMap().put(ERROR_SEGMENT,"MSA");
	     	}
	     	
	     	if(qak!=null && qak.getQak2_QueryResponseStatus()!=null && qak.getQak2_QueryResponseStatus().getValueOrEmpty().length()>0){
	     		errorResponse = qak.getQak2_QueryResponseStatus().getValue();
	     		iisQueryForm.getErrorAttributeMap().put(ERROR_SEGMENT,"QAK");
	     	}
	     	
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return errorResponse;
	}
	
	/**
	 * Translate standard error message received from IIS to render on screen.
	 * 
	 * @param errorResponse
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String tralateErrorResponse(String errorResponse, Map<String, String> errorAttributeMap, String qtyLimitedRequest) throws NEDSSAppException{
		String traslatedErrorResponse = "";
		try{
			if("NF".equals(errorResponse))
				traslatedErrorResponse = "No records were found.";
			else if("TM".equals(errorResponse))
				traslatedErrorResponse = "The search criteria submitted resulted in more than "+qtyLimitedRequest+" records found in the registry. Any records that were returned by the registry are displayed below, but this may not include all potential records. If no records are displayed or the desired record is not included in this result set, please refine the search and resubmit.";
			else if("AR".equals(errorResponse) || "AE".equals(errorResponse) || "CE".equals(errorResponse) || "CR".equals(errorResponse)){
				String err3 = errorAttributeMap.get(ERROR_RSP_ERROR_CODE)!=null?errorAttributeMap.get(ERROR_RSP_ERROR_CODE):"";
				String err8 = errorAttributeMap.get(ERROR_RSP_USER_MESSAGE)!=null?errorAttributeMap.get(ERROR_RSP_USER_MESSAGE):"";
				String errorMsg = err3;
				if(!"".equals(err8))
					errorMsg = err3+": "+err8;
				
				if(errorMsg==null || errorMsg.length()==0){
					String errorDesc =errorResponse;
					if("AR".equals(errorResponse)){
						errorDesc="AR: Original mode: Application Reject - Enhanced mode: Application acknowledgment: Reject";
					}else if("AE".equals(errorResponse)){
						errorDesc = "AE: Original mode: Application Error - Enhanced mode: Application acknowledgment: Error";
					}else if("CE".equals(errorResponse)){
						errorDesc = "CE: Enhanced mode: Accept acknowledgment: Commit Error";
					}else if("CR".equals(errorResponse)){
						errorDesc = "CR: Enhanced mode: Accept acknowledgment: Commit Reject";
					}
					
					traslatedErrorResponse = "The following issues were encountered when querying the registry: "+errorDesc+". Please try submitting the request again. If issues persist, please contact your system administrator.";
					
				}else{
					traslatedErrorResponse = "The following issues were encountered when querying the registry: ["+errorMsg+"]. Please try submitting the request again. If issues persist, please contact your system administrator.";
				}
			}else if("OK".equals(errorResponse))
				traslatedErrorResponse = "";
			else if(ERROR_WITHOUT_QUERY_RESPONSE_STATUS.equals(errorResponse))
				traslatedErrorResponse = "Error occured. Please try submitting the request again. If issues persist, please contact your system administrator.";
			else if("MSA".equals(errorAttributeMap.get(ERROR_SEGMENT))){
				traslatedErrorResponse = "The following issues were encountered when querying the registry: "+errorResponse+". Please try submitting the request again. If issues persist, please contact your system administrator.";
			}
			
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return traslatedErrorResponse;
	}
	
	public static String convertExceptionStackTraceToString(Exception e) throws NEDSSAppException{
		try{
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			String exceptionMessage = errors.toString();
			String ErrorMsgToWrite = exceptionMessage.toString();
			return ErrorMsgToWrite;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
	
	/**
	 * Compare existing file's patient local Id with imported vaccination's patient's local Id.
	 * 
	 * @param selectedCheckBoxes
	 * @param iisQueryForm
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 */
	public static ArrayList checkIfVaccinationAssociatedWithOtherPatient(String[] selectedCheckBoxes, IISQueryForm iisQueryForm, HttpServletRequest request) throws NEDSSAppException{
		ArrayList <VaccinationSearchResultDT> existingAssociatedVaccList = new ArrayList <VaccinationSearchResultDT>();
		try{
			HttpSession session = request.getSession(false);
			String strPersonLocalId  = (String)NBSContext.retrieve(session, NBSConstantUtil.DSPatientPersonLocalID);
			
			for(int i=0;i<selectedCheckBoxes.length;i++){
				VaccinationSearchResultDT vaccination = iisQueryForm.getVaccinationMap().get(selectedCheckBoxes[i]);
				String personLocalIdForImportedVac = getPersonLocalIdBySourceIdandEventType(vaccination.getVaccinationIdentifier(),IISQueryUtil.DOC_EVENT_TYPE_CD_VAC, request);
				
				if(personLocalIdForImportedVac!=null && !personLocalIdForImportedVac.equals(strPersonLocalId)){
					existingAssociatedVaccList.add(vaccination);
				}
			}
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
		return existingAssociatedVaccList;
	}
	
	/**
	 * @param sourceId
	 * @param eventType
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String getPersonLocalIdBySourceIdandEventType(String sourceId, String eventType, HttpServletRequest request) throws NEDSSAppException{
		try{
			String personLocalId = null;
			String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
	    	String sMethod = "getPersonLocalIdBySourceIdandEventType";
			Object[] sParams = new Object[] {sourceId, eventType};
			Object object = CallProxyEJB.callProxyEJB(sParams, sBeanJndiName, sMethod, request.getSession());
			if(object != null){
				personLocalId = (String) object;
			}
			return personLocalId;
		}catch(Exception ex){
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
	}
}
