package test.gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.UpdateCaseSummaryVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
import javax.transaction.UserTransaction;

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


@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants" })
//@RunWith(PowerMockRunner.class)
@PrepareForTest({ EdxPHCRHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,
NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class EdxPHCRHelper_tests { 
	
	
	/**
	 * CreateDocumentInvestigation_test: this is a pre-existing method that has been updated to exclude certain messages while printing the information
	 * of the processed PHDC if it is an update or if it is an update and skip DRRQ = Y. We will be verifying:
	 * 
	 * - the existing method still executes successfully.
	 * - if it is an update, the number of times the following  method is called is 0: addMultiplePatientDetailMsg, addPatientNewDetailMsg, addPatientOldDetailMsg, addDocumentSuccessDetailMessage.
	 * - if it is an update and skipDRRQ = Y, the number of times the following method is called is 0: addExistingInvMessage, addAlgorithmFailDetailMsg.
	 * 
	 * @author Fatima.Lopezcalzado
	 *
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants" })
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({ EdxPHCRHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,SessionContext.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,
	NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class, PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
	@PowerMockIgnore("javax.management.*")
	public static class CreateDocumentInvestigation_test { 
		
		private static String iteration;
		private static boolean documentUpdate;
		private static String skipDRRQ;

		 public CreateDocumentInvestigation_test(String it, boolean documentUpdate, String skipDRRQ){
			 
			 super();
			 //Common Parameters
			// this.type = type;
			 this.iteration = it;
			 this.documentUpdate = documentUpdate;
			 this.skipDRRQ = skipDRRQ;
		 }

	
	@Mock
	PropertyUtil propertyUtil;
	@Mock
	NBSSecurityObj nbsSecurityObjMock;
	@Mock
	NbsInterfaceDT nbsInterfaceDT;
	@Mock
	SessionContext sessionContext;
	@Mock
	UserTransaction userTransaction;
	@Mock
	LogUtils logger;
	@Mock
	NedssUtils nedssUtilsMock;
	@Mock
	NbsDocumentHome nbsDocumentHomeMock;
	@Mock
	NbsDocument nbsDocumentMock;
	@InjectMocks
	EdxPHCRHelper edxPHCRHelper;
	 
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(EdxPHCRHelper.class, "logger", logger);
		 propertyUtil = Mockito.mock(PropertyUtil.class); 
		 Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		 sessionContext = Mockito.mock(SessionContext.class); 
		 userTransaction= Mockito.mock(UserTransaction.class); ;
		 nbsDocumentHomeMock = Mockito.mock(NbsDocumentHome.class);
		 nbsDocumentMock=Mockito.mock(NbsDocument.class);
		 nedssUtilsMock=Mockito.mock(NedssUtils.class);
	 }
	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
		   ArrayList<String> createDocumentInvestigation_test_1= new ArrayList<String>();
			   
		   int it = 0;
		   
		  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
		   //The areas are the lines where the new method escapeCharacterSequence method is called
	      return Arrays.asList(new Object[][]{
	    		  
	    		  {"CreateDocumentInvestigation_test"+"_"+it++,true,"Y"},
	    		  {"CreateDocumentInvestigation_test"+"_"+it++,true,"N"},
	    		  {"CreateDocumentInvestigation_test"+"_"+it++,false,"Y"},
	    		  {"CreateDocumentInvestigation_test"+"_"+it++,false,"N"},
	    		  
	    		  
	    		 
	    		  
	    		 
		  });
	   }
	 

	  
	   
	   @Test
	 public void createDocumentInvestigation_test() throws Exception {
		   
		 edxPHCRHelper=PowerMockito.spy(new EdxPHCRHelper());
		 
		 PowerMockito.whenNew(PropertyUtil.class).withNoArguments().thenReturn(propertyUtil);
			
		 Mockito.when(propertyUtil.getPHDCSkipDRRQ()).thenReturn(skipDRRQ);
		
		 
		 Object[] oParams1 = new Object[] {"", nbsInterfaceDT,nbsSecurityObjMock,sessionContext};
		 when(nbsSecurityObjMock.getPermission(any(String.class),any(String.class))).thenReturn(true); 
		 when(sessionContext.getUserTransaction()).thenReturn(userTransaction);
		  
		 PowerMockito.mockStatic(PortableRemoteObject.class);
         PowerMockito.whenNew(NedssUtils.class).withNoArguments().thenReturn(nedssUtilsMock);
         Mockito.when(nedssUtilsMock.lookupBean(any(String.class))).thenReturn(new Object());
         Mockito.when(PortableRemoteObject.narrow(any(Object.class), any(Class.class))).thenReturn(nbsDocumentHomeMock);
         Mockito.when(nbsDocumentHomeMock.create()).thenReturn(nbsDocumentMock);
         Mockito.when(nbsDocumentMock.createNBSDocument(any(NbsInterfaceDT.class), any(NBSSecurityObj.class))).thenReturn(getNbsDOcumentVO(documentUpdate));
         EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT =Whitebox.invokeMethod(edxPHCRHelper, "createDocumentInvestigation", oParams1);
		 assertTrue(edxRuleAlgorothmManagerDT.getMPRUid()==12345); 
		 
		 //ND-27000- Test case for identifyCaseToBeUpdated method .
		 Mockito.when(nbsDocumentMock.createNBSDocument(any(NbsInterfaceDT.class), any(NBSSecurityObj.class))).thenReturn(getNbsDOcumentVO1());
		 edxRuleAlgorothmManagerDT =Whitebox.invokeMethod(edxPHCRHelper, "createDocumentInvestigation", oParams1);
		 assertTrue("12".equalsIgnoreCase( edxRuleAlgorothmManagerDT.getDocumentDT().getJurisdictionCd() ) ); 
		// assertTrue("At least one existing closed investigation for TEST was found. Document marked as reviewed".equalsIgnoreCase( edxRuleAlgorothmManagerDT.getErrorText() ) );
		  
		 //After the changes for Updates to PHDC and skipDRRQ to now show patient matching messages and / or algorithm match, we can test if it is working fine
		 //by making sure that the number of times called the methods below are 0 if any of those variables, respectively, are not true:
		 


		 if(documentUpdate){
			 
			 System.out.println("Iteration: #"+iteration+"If it is an update, the number of times addMultiplePatientDetailMsg, addPatientNewDetailMsg,"+
			 "addPatientOldDetailMsg, addDocumentSuccessDetailMessage methods are called should be 0.");
			 
			 
			 PowerMockito.verifyPrivate(edxPHCRHelper, Mockito.times(0)).invoke("addMultiplePatientDetailMsg", any(EdxRuleAlgorothmManagerDT.class),
					 any(NBSDocumentVO.class), any(Long.class));
			 PowerMockito.verifyPrivate(edxPHCRHelper, Mockito.times(0)).invoke("addPatientNewDetailMsg", any(EdxRuleAlgorothmManagerDT.class),
					 any(NBSDocumentVO.class), any(Long.class));
			 PowerMockito.verifyPrivate(edxPHCRHelper, Mockito.times(0)).invoke("addPatientOldDetailMsg", any(EdxRuleAlgorothmManagerDT.class),
					 any(NBSDocumentVO.class), any(Long.class));
			 PowerMockito.verifyPrivate(edxPHCRHelper, Mockito.times(0)).invoke("addDocumentSuccessDetailMessage", any(EdxRuleAlgorothmManagerDT.class),
					 any(NBSDocumentVO.class));
			 
			 System.out.println("PASSED");
		 }
		 
		 if(documentUpdate && skipDRRQ!=null && skipDRRQ.equalsIgnoreCase("Y")){
			 
			 System.out.println("Iteration: #"+iteration+"If it is an update and skipDRRQ = Y, the number of times addExistingInvMessage, addAlgorithmFailDetailMsg,"+
					 "methods are called should be 0.");
					 
			 
			 
			 PowerMockito.verifyPrivate(edxPHCRHelper, Mockito.times(0)).invoke("addExistingInvMessage", any(EdxRuleAlgorothmManagerDT.class),
					 any(NBSDocumentVO.class));
			 
			 PowerMockito.verifyPrivate(edxPHCRHelper, Mockito.times(0)).invoke("addAlgorithmFailDetailMsg", any(EdxRuleAlgorothmManagerDT.class),
					 any(NBSDocumentVO.class));
			 
			 
			 System.out.println("PASSED");
			 
		 }
		 
	 }

	 
	 private NBSDocumentVO getNbsDOcumentVO(boolean documentUpdate) {
         NBSDocumentVO ndvo= new NBSDocumentVO();
         ndvo.setConditionName("TEST");
         PersonVO patientVO = new PersonVO();
         PersonDT personDT = new PersonDT();
         personDT.setPersonParentUid(12345L);
         patientVO.setThePersonDT(personDT);
         ndvo.setPatientVO(patientVO);
         ndvo.setDocumentUpdate(documentUpdate);
         
         return ndvo;
    }
 
	 private NBSDocumentVO getNbsDOcumentVO1() {
         NBSDocumentVO ndvo= new NBSDocumentVO();
         ndvo.setConditionName("TEST");
         PersonVO patientVO = new PersonVO();
         PersonDT personDT = new PersonDT();
         personDT.setPersonParentUid(12345L);
         patientVO.setThePersonDT(personDT);
         ndvo.setPatientVO(patientVO);
         
         NBSDocumentDT nbsDocumentDT = new NBSDocumentDT();
         nbsDocumentDT.setJurisdictionCd("12");
         ndvo.setNbsDocumentDT(nbsDocumentDT);
         ndvo.setAssociatedInv(true);
        		 
         UpdateCaseSummaryVO summaryVO = new UpdateCaseSummaryVO();
         ArrayList<Object> assoSummaryCaseList = new ArrayList<Object>();
		 assoSummaryCaseList.add(summaryVO);

		 ndvo.setAssoSummaryCaseList(assoSummaryCaseList);	
		 ndvo.setDocumentUpdate(documentUpdate);
		
		 
         return ndvo;
    }
	 
	}
	
	/**
	 * CreateDocumentInvestigation_not_autorized_test: this method will verify that after executing the method that we are testing,
	 * the user is not authorized, meaning the error message returned is: User  is not authorized to autocreate Document.
	 * @author Fatima.Lopezcalzado
	 *
	 */

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants" })
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({ EdxPHCRHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,SessionContext.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,
	NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class})
	@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
	@PowerMockIgnore("javax.management.*")
	public static class CreateDocumentInvestigation_not_autorized_test { 
		
		private String iteration;
		ArrayList<String> parameters;
		
		 

		 public CreateDocumentInvestigation_not_autorized_test(String it, ArrayList<String> parameters){
			 
			 super();
			 //Common Parameters
			// this.type = type;
			 this.iteration = it;
			 this.parameters = parameters;
			
		 }

	
	@Mock
	PropertyUtil propertyUtil;
	@Mock
	NBSSecurityObj nbsSecurityObjMock;
	@Mock
	NbsInterfaceDT nbsInterfaceDT;
	@Mock
	SessionContext sessionContext;
	@Mock
	UserTransaction userTransaction;
	@Mock
	LogUtils logger;
	@Mock
	NedssUtils nedssUtilsMock;
	@Mock
	NbsDocumentHome nbsDocumentHomeMock;
	@Mock
	NbsDocument nbsDocumentMock;
	@InjectMocks
	EdxPHCRHelper edxPHCRHelper;
	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(EdxPHCRHelper.class, "logger", logger);	 		 
		 nbsInterfaceDT =  Mockito.mock(NbsInterfaceDT.class);
		 nbsSecurityObjMock =  Mockito.mock(NBSSecurityObj.class);
		 propertyUtil = Mockito.mock(PropertyUtil.class); 
		 Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil); 
		 sessionContext = Mockito.mock(SessionContext.class); 
		 userTransaction= Mockito.mock(UserTransaction.class);
		 nbsDocumentHomeMock = Mockito.mock(NbsDocumentHome.class);
		 nbsDocumentMock=Mockito.mock(NbsDocument.class);
		 nedssUtilsMock=Mockito.mock(NedssUtils.class);
	 }
		   
	 
	 

	 
	   @Parameterized.Parameters
	   public static Collection input() {		   
		  ArrayList<String> createDocumentInvestigation_test_1= new ArrayList<String>();
			   
		  int it = 0;

	      return Arrays.asList(new Object[][]{
	    		  
	    {"CreateDocumentInvestigation_not_autorized_test"+"_"+it++,createDocumentInvestigation_test_1},
	    		 
	    		  
	    		 
		  });
	   }
	 

	   
	   @Test
	 public void createDocumentInvestigation_not_autorized_test() throws Exception {
		 
		 edxPHCRHelper=PowerMockito.spy(new EdxPHCRHelper());
		 Object[] oParams1 = new Object[] {"", nbsInterfaceDT,nbsSecurityObjMock,sessionContext};
		 
		 EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT =Whitebox.invokeMethod(new EdxPHCRHelper(), "createDocumentInvestigation", oParams1);
		 assertTrue("createDocumentInvestigation_not_autorized_test failed", "User  is not authorized to autocreate Document".equalsIgnoreCase(edxRuleAlgorothmManagerDT.getErrorText()));

		 
	 }

	 
	}
	
	
	
	/**
	 * AddDocumentMatchDetailMessage_test: this test is very simple, so, as long as we are able to make the call to addDocumentMatchDetailMessage, the test will pass.
	 * @author Fatima.Lopezcalzado
	 *
	 */

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({EdxPHCRConstants.class,EdxRuleAlgorothmManagerDT.class, EdxPHCRHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,SessionContext.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,EdxRuleAlgorothmManagerDT.class,
		NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class})
	@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
	@PowerMockIgnore("javax.management.*")
	public static class AddDocumentMatchDetailMessage_test { 
		
	private String iteration;
	ArrayList<String> parameters;
	
	 

	 public AddDocumentMatchDetailMessage_test(String it, ArrayList<String> parameters){
		 
		 super();
		 //Common Parameters
		// this.type = type;
		 this.iteration = it;
		 this.parameters = parameters;
		
	 }

	
	@Mock
	PropertyUtil propertyUtil;
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	
	
	@Mock
	LogUtils logger;
	
	@Mock
	NedssUtils nedssUtilsMock;
	
	@Mock
	NbsDocumentHome nbsDocumentHomeMock;
	@Mock
	NbsDocument nbsDocumentMock;
	
	
	//@Spy
	@InjectMocks
	EdxPHCRHelper edxPHCRHelper;
	
	 
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(EdxPHCRHelper.class, "logger", logger);
		 propertyUtil = Mockito.mock(PropertyUtil.class); 
		 Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		 nbsDocumentHomeMock = Mockito.mock(NbsDocumentHome.class);
		 nbsDocumentMock=Mockito.mock(NbsDocument.class);
		 nedssUtilsMock=Mockito.mock(NedssUtils.class);
		   
	
	 }
	 
	   @Parameterized.Parameters
	   public static Collection input() {

		  ArrayList<String> createDocumentInvestigation_test_1= new ArrayList<String>();  
		  int it = 0;
	      return Arrays.asList(new Object[][]{
	    		  
	    		  {"addDocumentMatchDetailMessage_test"+"_"+it++,createDocumentInvestigation_test_1},
	 
		  });
	   }
	 

	   
	     @Test
		 public void addDocumentMatchDetailMessage_test() throws Exception {
				
	    	System.out.println("******************* Starting test case named: addDocumentMatchDetailMessage_test *******************");
				
	    	 edxPHCRHelper=PowerMockito.spy(new EdxPHCRHelper());
	    	 
			 EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT = new EdxRuleAlgorothmManagerDT();
			 
			 NBSDocumentVO nbsDocumentVO = new  NBSDocumentVO();
	 
			 PowerMockito.doNothing().when(edxPHCRHelper,"addToActivityDetailLog",any(EdxRuleAlgorothmManagerDT.class), any(String.class), any(EdxPHCRConstants.MSG_TYPE.class), any(String.class), any(EdxRuleAlgorothmManagerDT.STATUS_VAL.class));
			 Whitebox.invokeMethod(new EdxPHCRHelper(), "addDocumentMatchDetailMessage", edxRuleAlgorithmManagerDT, nbsDocumentVO); 
	
			 System.out.println("Iteration: #"+iteration+" PASSED");
			System.out.println("******************* End test case named: addDocumentMatchDetailMessage_test *******************");
				
			 
		 }
	 
	 	}
}
		
	
