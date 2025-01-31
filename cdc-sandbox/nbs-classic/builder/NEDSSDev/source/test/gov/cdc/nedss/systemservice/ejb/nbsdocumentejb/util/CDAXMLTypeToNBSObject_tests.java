package test.gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.phdc.cda.II;
import gov.cdc.nedss.phdc.cda.ON;
import gov.cdc.nedss.phdc.cda.POCDMT000040AssignedCustodian;
import gov.cdc.nedss.phdc.cda.POCDMT000040ClinicalDocument1;
import gov.cdc.nedss.phdc.cda.POCDMT000040Custodian;
import gov.cdc.nedss.phdc.cda.POCDMT000040CustodianOrganization;
import gov.cdc.nedss.phdc.cda.POCDMT000040RecordTarget;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAEntityProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPatientMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.Jurisdiction;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.JurisdictionHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;

import org.apache.xmlbeans.XmlCursor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;



@SuppressStaticInitializationFor ({ "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(Enclosed.class)
@PrepareForTest({CDAXMLTypeToNBSObject.class})
@PowerMockIgnore("javax.management.*")
public class CDAXMLTypeToNBSObject_tests {
	
	

/**
 * CreateNBSDocumentVO_test: this test case is focused on the new part added at the end of the method to prepare the VO calling the actual method pre.prepareVO.
 * However, it will still make sure it executes successfully and the values we are expected to be set are set accordingly.
 * To summarize, will verify:
 * 
 * - the method executes successfully
 * - record status cd is the expected
 * - Program area is the expected
 * - Jurisdiction is the expected
 * - Version control number is the expected
 * - Last Change user is the expected
 * - Condition is the expected
 * - Doc name is the expected
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.dt.NbsInterfaceDT", "gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO","gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({POCDMT000040ClinicalDocument1.class,ProgramAreaJurisdictionUtil.class, Coded.class, CDAXMLTypeToNBSObject.class, NBSDocumentVO.class, ClinicalDocumentDocument1.class, PortableRemoteObject.class, CDAEntityProcessor.class, CachedDropDowns.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class CreateNBSDocumentVO_test {
	
	@Mock
	LogUtils logger;
	
	@Mock
	NbsDocumentDAOImpl nbsDocumentDAOImpl;
	
	@Mock
	NBSSecurityObj nbsSecurityObj;
	
	@Mock
	UserProfile userProfile;
	
	@Mock
	User user;
	
	@Mock
	NbsInterfaceDT nbsInterfaceDT;
	
	
	@Mock
	EdxPatientMatchingCriteriaUtil edxPatientMatchingCriteriaUtil;
	
	@Mock
	EntityController entityController=Mockito.mock(EntityController.class);
	
	@Mock
	ClinicalDocumentDocument1 clinicalDocumentRoot;
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	POCDMT000040ClinicalDocument1 clinicalDoc;

	@Mock
	NedssUtils nedssUtils;
	
	@Mock
	Jurisdiction jurisdiction;
	
	@Mock
	JurisdictionHome jurHome;
	
	@Mock
	PersonVO patientVO;
	
	@Mock
	Coded conditionCodeObj;
	
	@Mock
	PrepareVOUtils pre = Mockito.mock(PrepareVOUtils.class);
	
	@Mock
	POCDMT000040Custodian custodian;
	
	@Mock
	POCDMT000040AssignedCustodian assignedCustodian;
	
	@Mock
	POCDMT000040CustodianOrganization representedCustodianOrg;
	
	@Mock
	ON name;
	
	@Mock
	XmlCursor xmlCursor;
	
	@Mock
	XmlCursor xmlCursor2;
	
	@Mock
	II id;
	
	@InjectMocks
	CDAXMLTypeToNBSObject cdaXMLTypeToNBSObject=Mockito.mock(CDAXMLTypeToNBSObject.class);
	
	
	
	private String iteration;
	private String progArea;
	private String conditionCode;
	private String skipDRRQ;
	private String extension;
	private String textValue;
	private String jurisdictionCd;
	private String previousRecordStatusCd;
	private Long userId; 
	
		 	

	
	public CreateNBSDocumentVO_test(String it, String progArea, String conditionCode, String skipDRRQ, String extension, String textValue, String jurisdictionCd, String previousRecordStatusCd, Long userId){
	
		super();
		
		this.iteration = it;
		this.progArea = progArea;
		this.conditionCode = conditionCode;
		this.skipDRRQ = skipDRRQ;
		this.extension = extension;
		this.textValue = textValue;
		this.jurisdictionCd = jurisdictionCd;
		this.previousRecordStatusCd = previousRecordStatusCd;
		this.userId = userId;
	}
		
	
	

	   @Parameterized.Parameters
	   public static Collection input() {
		   

		  int it = 0;

		   
	      return Arrays.asList(new Object[][]{
	    		  
	    
	    		  // progArea, String conditionCode, String skipDRRQ, String extension, String textValue, String jurisdictionCd
	    			
	    		  {"createNBSDocumentVO_test"+"_"+it++, "TB","10220","N","Extension1","SENDINGTest1","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "TB","10220","N","Extension1","SENDINGTest1","30309", "PROCESSED", 3333332L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "TB","10220","Y","Extension1","SENDINGTest1","30000", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "TB","10220","Y","Extension1","SENDINGTest1","30309", "PROCESSED", 3333332L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "TB","10220","N","Extension1","SENDINGTest1","31111", "PROCESSED", 44444444L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "TB","10220","Y","Extension1","SENDINGTest1","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"10220","Y","Extension1","SENDINGTest1","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"10220","Y","Extension1","SENDINGTest1",null, "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"10220","N","Extension1","SENDINGTest1","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"10220","N","Extension1","SENDINGTest1",null, "UNPROCESSED", 2222222L},

	    
	    		  {"createNBSDocumentVO_test"+"_"+it++, "GCD","1001","N","Extension2","SENDINGTest2","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "GCD","1001","N","Extension2","SENDINGTest2","30309", "PROCESSED", 3333332L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "GCD","1001","Y","Extension2","SENDINGTest2","30000", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "GCD","1001","Y","Extension2","SENDINGTest2","30309", "PROCESSED", 3333332L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "GCD","1001","N","Extension2","SENDINGTest2","31111", "PROCESSED", 44444444L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, "GCD","1001","Y","Extension2","SENDINGTest2","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"1001","Y","Extension2","SENDINGTest2","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"1001","Y","Extension2","SENDINGTest2",null, "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"1001","N","Extension2","SENDINGTest2","30309", "UNPROCESSED", 2222222L},
	    		  {"createNBSDocumentVO_test"+"_"+it++, null,"1001","N","Extension2","SENDINGTest2",null, "UNPROCESSED", 2222222L},
	    			    		  
	    		 
		  });
	   }
	 
	   
	   
	   
	 @Before
	 public void initMocks() throws Exception {
		 
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 clinicalDocumentRoot = Mockito.mock(ClinicalDocumentDocument1.class);
		 clinicalDoc = Mockito.mock(POCDMT000040ClinicalDocument1.class);
		 userProfile = Mockito.mock(UserProfile.class);
		 user = Mockito.mock(User.class);
		 jurHome = Mockito.mock(JurisdictionHome.class);
		 jurisdiction = Mockito.mock(Jurisdiction.class);
		 nedssUtils = Mockito.mock(NedssUtils.class);
		 patientVO = Mockito.mock(PersonVO.class);
		 pre =  Mockito.mock(PrepareVOUtils.class);
		 xmlCursor = Mockito.mock(XmlCursor.class);
		 xmlCursor2 = Mockito.mock(XmlCursor.class);
		 id = Mockito.mock(II.class);
		 custodian =  Mockito.mock(POCDMT000040Custodian.class);
		 assignedCustodian =  Mockito.mock(POCDMT000040AssignedCustodian.class);
		 representedCustodianOrg =  Mockito.mock(POCDMT000040CustodianOrganization.class);
		 name =  Mockito.mock(ON.class);

		 }
	
	@Test
	public void createNBSDocumentVO_test() throws Exception {

		PowerMockito.mockStatic(PropertyUtil.class);
		Mockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
		cdaXMLTypeToNBSObject = PowerMockito.spy(new CDAXMLTypeToNBSObject());
		
		Whitebox.setInternalState(CDAXMLTypeToNBSObject.class, "logger", logger);
		
 
		NbsInterfaceDT nbsInterfaceDT = new NbsInterfaceDT();
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		NBSDocumentMetadataDT nbsDocMDT = new NBSDocumentMetadataDT();
		Object payload = new Object();

		PowerMockito.doReturn(clinicalDocumentRoot).when(cdaXMLTypeToNBSObject, "parseCaseTypeXml", payload);
		
		Mockito.when(xmlCursor.getTextValue()).thenReturn(textValue);
		Mockito.when(name.newCursor()).thenReturn(xmlCursor);
		Mockito.when(representedCustodianOrg.getName()).thenReturn(name);
		Mockito.when(assignedCustodian.getRepresentedCustodianOrganization()).thenReturn(representedCustodianOrg);
		Mockito.when(custodian.getAssignedCustodian()).thenReturn(assignedCustodian);
		Mockito.when(clinicalDoc.getCustodian()).thenReturn(custodian);
		Mockito.when(clinicalDocumentRoot.getClinicalDocument()).thenReturn(clinicalDoc);
		
		
		
		
		Mockito.when(xmlCursor2.getTextValue()).thenReturn(textValue);
		Mockito.when(id.getExtension()).thenReturn(extension);
		Mockito.when(id.newCursor()).thenReturn(xmlCursor2);
		Mockito.when(clinicalDoc.getId()).thenReturn(id);

		PowerMockito.spy(CDAXMLTypeToNBSObject.class);
		PowerMockito.doNothing().when(CDAXMLTypeToNBSObject.class, "setupDocumentVOWithEventTypeInfoWithInDocument", any(NBSDocumentVO.class),any(ClinicalDocumentDocument1.class));
			//clinicalDocumentRoot.getClinicalDocument().getId() != null){
		 
		 ArrayList<String>  conditions = new ArrayList<String>();

		 conditions.add(conditionCode);
		 PowerMockito.doReturn(conditions).when(cdaXMLTypeToNBSObject, "getConditionCodeFromDocument", any(POCDMT000040ClinicalDocument1.class), any(Map.class));
		 
		 when(nbsSecurityObj.getTheUserProfile()).thenReturn(userProfile);
		 when(userProfile.getTheUser()).thenReturn(user);
		 when(user.getEntryID()).thenReturn("111111111");
		
		 
		 PowerMockito.mockStatic(PortableRemoteObject.class);
		 PowerMockito.doReturn(jurHome).when(PortableRemoteObject.class, "narrow",any(Object.class), any(String.class));
	       
	
		 PowerMockito.mockStatic(CDAEntityProcessor.class);
		 PowerMockito.doReturn(patientVO).when(CDAEntityProcessor.class, "getPatient",any(POCDMT000040RecordTarget[].class), any(EdxCDAInformationDT.class), any(POCDMT000040Section.class));
	  
		 ProgramAreaVO programAreaVO = new ProgramAreaVO();

		 programAreaVO.setStateProgAreaCode(progArea);
		 PowerMockito.mockStatic(CachedDropDowns.class);
		 PowerMockito.when(CachedDropDowns.getProgramAreaForCondition(conditionCode)).thenReturn(programAreaVO);
		
		 TreeMap<Object, Object> condAndFormCdTreeMap = new TreeMap<Object, Object> ();
			
		 condAndFormCdTreeMap.put(conditionCode, "form");
			
		 PowerMockito.when(CachedDropDowns.getConditionCdAndInvFormCd()).thenReturn(condAndFormCdTreeMap);
			
	
	     PowerMockito.whenNew(Coded.class).withArguments(conditionCode,"v_Condition_code","PHC_TYPE").thenReturn(conditionCodeObj);
	     Mockito.when(conditionCodeObj.getCode()).thenReturn(conditionCode);
	     Mockito.when(conditionCodeObj.isFlagNotFound()).thenReturn(false);

         PowerMockito.whenNew(NedssUtils.class).withNoArguments().thenReturn(nedssUtils);
         Mockito.when(nedssUtils.lookupBean(any(String.class))).thenReturn(new Object());
         Mockito.when(jurHome.create()).thenReturn(jurisdiction);
         
         Collection<Object> jurColl = new ArrayList<Object>();
        
         jurColl.add(jurisdictionCd);
         Mockito.when(jurisdiction.findJurisdictionForPatient(patientVO)).thenReturn(jurColl);
         
        
        PowerMockito.whenNew(PrepareVOUtils.class).withNoArguments().thenReturn(pre);
		NBSDocumentDT nbsDocumentDTAfterPrep = new NBSDocumentDT();//to simulate the values returned by the prepareVO
		//It is not important to check the next status returned is the expected one as it is mocked anyways and that would be testing a different method

		
		String expectedRecordStatusCd = "";
		if(skipDRRQ!=null && skipDRRQ.equalsIgnoreCase("Y") && jurisdictionCd!=null && progArea!=null){
			//SkipDRRQ = Y and Jurisdiction and program area are not null, then it should be automatically processed.
			expectedRecordStatusCd="PROCESSED";
			
		}else{
			//this is to assume the prep call is doing what's supposed to do:
			if(previousRecordStatusCd=="PROCESSED")
				expectedRecordStatusCd="PROCESSED";
			if(previousRecordStatusCd=="UNPROCESSED")
				expectedRecordStatusCd="PROCESSED";
			if(previousRecordStatusCd=="LOG_DEL")
				expectedRecordStatusCd="PROCESSED";
			if(previousRecordStatusCd=="START")
					expectedRecordStatusCd="UNPROCESSED";

		}
		nbsDocumentDTAfterPrep.setRecordStatusCd(expectedRecordStatusCd);	
		nbsDocumentDTAfterPrep.setLastChgUserId(userId);

		Mockito.when(propertyUtil.getPHDCSkipDRRQ()).thenReturn(skipDRRQ);
		//NBSBOLookup.DOCUMENT, previosRecordStatusCd, "NBS_DOCUMENT", BASE, 
		Mockito.when(pre.prepareVO(any(NBSDocumentDT.class), any(String.class), any(String.class),any(String.class),any(String.class), any(NBSSecurityObj.class))).thenReturn(nbsDocumentDTAfterPrep);
		

		Long pajHash = 111112L;
		PowerMockito.mockStatic(ProgramAreaJurisdictionUtil.class);
		PowerMockito.doReturn(pajHash).when(ProgramAreaJurisdictionUtil.class, "getPAJHash",progArea, jurisdictionCd);
	      

		 	 
		 PowerMockito.doNothing().when(cdaXMLTypeToNBSObject, "extractEventIds", any(NBSDocumentDT.class),any(POCDMT000040ClinicalDocument1.class));
			


		NBSDocumentVO nbsDocumentVO=Whitebox.invokeMethod(cdaXMLTypeToNBSObject,"createNBSDocumentVO", nbsInterfaceDT, time, nbsDocMDT, payload, nbsSecurityObj);


		
		
		System.out.println("Iteration: "+iteration+" Method Name: createNBSDocumentVO_test, Method executed sucessfully RESULT::::::PASSED");
		
		//Record status cd
		String actualRecordStatusCd = nbsDocumentVO.getNbsDocumentDT().getRecordStatusCd();
		System.out.println("\n Method Name: createNBSDocumentVO_test, Expected Record status Cd: "+expectedRecordStatusCd+", actual record status Cd: "+actualRecordStatusCd+". PASSED.");
		Assert.assertEquals(expectedRecordStatusCd,actualRecordStatusCd);
		System.out.println("PASSED");
		
		
		//Program area
		String programAreaCdActual = nbsDocumentVO.getNbsDocumentDT().getProgAreaCd();
		System.out.println("\n Method Name: createNBSDocumentVO_test, Expected Program Area cd: "+progArea+", actual Program Area cd: "+programAreaCdActual+". PASSED.");
		Assert.assertEquals(progArea,programAreaCdActual);
		System.out.println("PASSED"); 
			
		
		//Jurisdiction
		String jurisdictionCdActual = nbsDocumentVO.getNbsDocumentDT().getJurisdictionCd();
		System.out.println("\n Method Name: createNBSDocumentVO_test, Expected Jurisdiction cd: "+jurisdictionCd+", actual Jurisdiction cd: "+jurisdictionCdActual+". PASSED.");
		Assert.assertEquals(jurisdictionCd,jurisdictionCdActual);
		System.out.println("PASSED");			
				
				
		//Version Control Number
		int versionControlNbr = nbsDocumentVO.getNbsDocumentDT().getVersionCtrlNbr();
		System.out.println("\n Method Name: createNBSDocumentVO_test, Expected Version control number: "+"1"+", actual Version control number: "+versionControlNbr+". PASSED.");
		Assert.assertEquals(1,versionControlNbr);
		System.out.println("PASSED");			
				
			
		//Last change user id
		Long lastChangeUserId = nbsDocumentVO.getNbsDocumentDT().getLastChgUserId();
		System.out.println("\n Method Name: createNBSDocumentVO_test, Expected Last change user id: "+userId+", actual Last change user id: "+lastChangeUserId+". PASSED.");
		Assert.assertEquals(userId,lastChangeUserId);
		System.out.println("PASSED");		
		
		//Condition
		String conditionCdActual = nbsDocumentVO.getNbsDocumentDT().getCd();
		System.out.println("\n Method Name: createNBSDocumentVO_test, Expected Condition: "+conditionCode+", actual Condition: "+conditionCdActual+". PASSED.");
		Assert.assertEquals(userId,lastChangeUserId);
		System.out.println("PASSED");		
		
		

		//Doc Name
		String docNameActual = nbsDocumentVO.getDocName();
		System.out.println("\n Method Name: createNBSDocumentVO_test, Expected Doc Name: "+textValue+", actual Doc name: "+docNameActual+". PASSED.");
		Assert.assertEquals(userId,lastChangeUserId);
		System.out.println("PASSED");		
		

	}
	


}
}
