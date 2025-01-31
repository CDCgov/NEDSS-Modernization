package test.gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.dao.NbsInterfaceDAOImpl;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxCommonHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxLabHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.EDXObservationDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.HL7CommonLabUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.HL7ELRValidateDecisionSupport;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
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


@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxLabHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants" })
//@RunWith(PowerMockRunner.class)
@PrepareForTest({ EdxLabHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,
NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class EdxLabHelper_tests { 
	
	
	/**
	 * GetUnProcessedELR_test: the updates to this method consist of calling the new methods shared by ELR and PHDC: updatePersonELRPHDCUpdate and setPersonUIDOnUpdate. The validations that are checked within this test method are:
	 * - the method executes successfully for the different parameters used.
	 * - if not observationDT matched has been found within ODS, then the patient matched will be set to false, otherwise to true.
	 * - if not observationDT matched has been found within ODS, then the lab create will be set to true, otherwise to false.
	 * - if matched observationDT has been found, the new method updatePersonELRPHDCUpdate added as part of latest PHDC modifications is called once.
	 * - if DRRQUpdate or DRSAQ update, knowing that there's a personVO in the correction of the Subject of observation type, the new method setPersonUIDOnUpdate is called once.
	 * 
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxCommonHelper","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.EDXObservationDAO","gov.cdc.nedss.act.observation.vo.ObservationVO","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxLabHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.dao.NbsInterfaceDAOImpl" })
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({ EdxCommonHelper.class, EDXObservationDAO.class,HL7ELRValidateDecisionSupport.class, ObservationVO.class, NbsInterfaceDAOImpl.class,EdxLabHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,SessionContext.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,
	NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class, PropertyUtil.class})
	@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
	@PowerMockIgnore("javax.management.*")
	public static class GetUnProcessedELR_test { 
		
		private static String iteration;

	    boolean labUpdateDRRQ;
	    boolean labUpdateDRSAQ;
	    boolean matchedObsODS;
	    
		private static boolean documentUpdate;
		private static String skipDRRQ;

		 public GetUnProcessedELR_test(String it, boolean labUpdateDRRQ, boolean labUpdateDRSAQ, boolean matchedObsODS){
			 
			 super();
			 //Common Parameters
			// this.type = type;
			 this.iteration = it;
			 this.labUpdateDRRQ = labUpdateDRRQ;
			 this.labUpdateDRSAQ = labUpdateDRSAQ;
			 this.matchedObsODS = matchedObsODS;
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
	UserProfile userProfile;
	
	@Mock
	User user;
	@Mock
	HL7CommonLabUtil hL7CommonLabUtil;
	
	@Mock
	NbsInterfaceDAOImpl nbsInterfaceDAOImpl;
	@Mock
	NbsDocument nbsDocumentMock;
	@Mock
	EdxCommonHelper edxCommonHelper;
	
    @Mock
    HL7ELRValidateDecisionSupport hL7ELRValidateDecisionSupport;
    
	@InjectMocks
	EdxLabHelper edxLabHelper;
	 
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(EdxLabHelper.class, "logger", logger);
		 propertyUtil = Mockito.mock(PropertyUtil.class); 
		 Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		 sessionContext = Mockito.mock(SessionContext.class); 
		 userTransaction= Mockito.mock(UserTransaction.class); ;
		 nbsDocumentHomeMock = Mockito.mock(NbsDocumentHome.class);
		 nbsDocumentMock=Mockito.mock(NbsDocument.class);
		 nedssUtilsMock=Mockito.mock(NedssUtils.class);
		 userProfile=Mockito.mock(UserProfile.class);
		 user=Mockito.mock(User.class);	
		 nbsInterfaceDAOImpl = Mockito.mock(NbsInterfaceDAOImpl.class);
		 hL7CommonLabUtil= Mockito.mock(HL7CommonLabUtil.class);
		 edxCommonHelper =  Mockito.mock(EdxCommonHelper.class);
	 }
	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
			   
		   int it = 0;
		   
		  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
		   //The areas are the lines where the new method escapeCharacterSequence method is called
	      return Arrays.asList(new Object[][]{
	    		  
	    		  {"GetUnProcessedELR_test"+"_"+it++,true,true,true},
	    		  {"GetUnProcessedELR_test"+"_"+it++,true,false,true},
	    		  {"GetUnProcessedELR_test"+"_"+it++,false,true,true},
	    		  {"GetUnProcessedELR_test"+"_"+it++,false,false,true},
	     		  {"GetUnProcessedELR_test"+"_"+it++,true,true,false},
	    		  {"GetUnProcessedELR_test"+"_"+it++,true,false,false},
	    		  {"GetUnProcessedELR_test"+"_"+it++,false,true,false},
	    		  {"GetUnProcessedELR_test"+"_"+it++,false,false,false},
	
		  });
	   }
	 

	  
	   
	   @Test
	 public void getUnProcessedELR_test() throws Exception {
		   
		 edxLabHelper=PowerMockito.spy(new EdxLabHelper());
		 
		 PowerMockito.whenNew(PropertyUtil.class).withNoArguments().thenReturn(propertyUtil);
		 PowerMockito.whenNew(EdxCommonHelper.class).withNoArguments().thenReturn(edxCommonHelper);
		 Mockito.doNothing().when(edxCommonHelper).updatePersonELRPHDCUpdate(any(PersonVO.class),any(PersonVO.class));
		 

		 Mockito.when(propertyUtil.getPHDCSkipDRRQ()).thenReturn(skipDRRQ);
		
		 String userIdString="10000000";//Move to parameter??
		 when(nbsSecurityObjMock.getTheUserProfile()).thenReturn(userProfile);
		 when(userProfile.getTheUser()).thenReturn(user);
		 when(user.getEntryID()).thenReturn(userIdString);
		 Mockito.doNothing().when(userTransaction).begin();
		 
		 Long nbsInterfaceUid = 11111L;//Move to parameters
		 
		 when(nbsInterfaceDAOImpl.selectNbsInterfaceDT(nbsInterfaceUid)).thenReturn(nbsInterfaceDT);
		 
		 PowerMockito.whenNew(NbsInterfaceDAOImpl.class).withNoArguments().thenReturn(nbsInterfaceDAOImpl);

		 when(nbsSecurityObjMock.getPermission(any(String.class),any(String.class))).thenReturn(true); 
		 when(sessionContext.getUserTransaction()).thenReturn(userTransaction);
 
		 PowerMockito.mockStatic(PortableRemoteObject.class);
         PowerMockito.whenNew(NedssUtils.class).withNoArguments().thenReturn(nedssUtilsMock);
         Mockito.when(nedssUtilsMock.lookupBean(any(String.class))).thenReturn(new Object());
         Mockito.when(PortableRemoteObject.narrow(any(Object.class), any(Class.class))).thenReturn(nbsDocumentHomeMock);
         Mockito.when(nbsDocumentHomeMock.create()).thenReturn(nbsDocumentMock);
         Mockito.when(nbsDocumentMock.createNBSDocument(any(NbsInterfaceDT.class), any(NBSSecurityObj.class))).thenReturn(getNbsDOcumentVO(documentUpdate));

         
         LabResultProxyVO labResultProxyVO = new LabResultProxyVO();
         
         Collection<Object> personVOCollection = new ArrayList<Object>();
         PersonVO personVO = new PersonVO();
         PersonDT personDT = new PersonDT();
         personDT.setCdDescTxt("Observation Subject");
         personVO.setThePersonDT(personDT);
         
         personVOCollection.add(personVO);
         labResultProxyVO.setThePersonVOCollection(personVOCollection);
         
         PowerMockito.whenNew(HL7CommonLabUtil.class).withNoArguments().thenReturn(hL7CommonLabUtil);
         Mockito.when(hL7CommonLabUtil.processELR(any(NbsInterfaceDT.class),any(EdxLabInformationDT.class), any(NBSSecurityObj.class))).thenReturn(labResultProxyVO);
         ObservationDT observationDT = new ObservationDT();
         
         
         Long observationUid = 2222222L;//Move to parameters??
         observationDT.setObservationUid(observationUid);
         observationDT.setJurisdictionCd("3001");
         observationDT.setProgAreaCd("GCD");
         Mockito.when(hL7CommonLabUtil.sendLabResultToProxy(labResultProxyVO, nbsSecurityObjMock)).thenReturn(observationDT);

         ObservationVO orderTest = new ObservationVO();
         PowerMockito.spy(EdxLabHelper.class);
         PowerMockito.doReturn(orderTest).when(edxLabHelper, "getOrderedTest",labResultProxyVO);

         PowerMockito.doNothing().when(edxLabHelper, "checkSecurity",any(NBSSecurityObj.class), any(EdxLabInformationDT.class), any(String.class), any(String.class), any(String.class), any(String.class));

         EdxLabInformationDT edxLabInformationDT = null;
         ObservationDT observationDTMatched = null;
         if(matchedObsODS){
        	 observationDTMatched = new ObservationDT();
         	 Long observationuid = 123456789L;
         	 observationDTMatched.setObservationUid(observationuid);
           	 LabResultProxyVO matchedlabResultProxyVO = new LabResultProxyVO();
         	 Mockito.when(hL7CommonLabUtil.getLabResultToProxy(observationDTMatched.getObservationUid(), nbsSecurityObjMock)).thenReturn(matchedlabResultProxyVO);
         	 
         	 
         }
         
         edxLabInformationDT= new EdxLabInformationDT();
         edxLabInformationDT.setLabIsUpdateDRRQ(labUpdateDRRQ);
         edxLabInformationDT.setLabIsUpdateDRSA(labUpdateDRSAQ);
     
         
         
         PowerMockito.whenNew(EdxLabInformationDT.class).withNoArguments().thenReturn(edxLabInformationDT);
         PowerMockito.whenNew(HL7ELRValidateDecisionSupport.class).withNoArguments().thenReturn(hL7ELRValidateDecisionSupport);

         EdxLabInformationDT edxLabInformationDT2 = new EdxLabInformationDT();
     	 Mockito.when(hL7ELRValidateDecisionSupport.validateProxyVO(labResultProxyVO, edxLabInformationDT, nbsSecurityObjMock)).thenReturn(edxLabInformationDT2);
     	

          
  	 	 PowerMockito.mockStatic(EDXObservationDAO.class);
     	 PowerMockito.doReturn(observationDTMatched).when(EDXObservationDAO.class,"matchingObservationInODS",edxLabInformationDT);


         
		 EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT =Whitebox.invokeMethod(edxLabHelper, "getUnProcessedELR", nbsInterfaceUid, sessionContext, nbsSecurityObjMock);

		 
		 
			boolean patientMatchedActual = edxLabInformationDT.isPatientMatch();
			boolean patientMatchedExpected = matchedObsODS;
			
			System.out.println("Iteration: #"+iteration+"\nPatient matched:\nExpected value: "+patientMatchedExpected+"\nActual value: "+patientMatchedActual);
			Assert.assertEquals(patientMatchedActual,patientMatchedExpected);
			 System.out.println("PASSED");
			 
			 
			if(matchedObsODS){
				
				System.out.println("Iteration: #"+iteration+"\nVerifying that Matched Observation from ODS is found, the method updatePersonELRPHDCUpdate is called once");
				
				 Mockito.verify(edxCommonHelper, Mockito.times(1)).updatePersonELRPHDCUpdate(any(PersonVO.class),any(PersonVO.class));

				 System.out.println("PASSED");
				 
				 
				 System.out.println("Iteration: #"+iteration+"\nVerifying that Matched Observation from ODS is not found, and it is isLabCreate is false");
					boolean actualLabCreate = edxLabInformationDT.isLabIsCreate();
					boolean expectedLabCreated = false;
					Assert.assertEquals(actualLabCreate,expectedLabCreated);
					
					 System.out.println("PASSED");
					 
					 
					if(labUpdateDRRQ || labUpdateDRSAQ){
						
						//since we are hardcoding 1 person in the collection for that type, it should call the method below once.
						System.out.println("Iteration: #"+iteration+"\nVerifying that Matched Observation from ODS is found, and it is labUpdateDRRQ o labUpdateDRSAQ and there's 1 personVO in the collection, the method setPersonUIDOnUpdate is called once");
						
						 Mockito.verify(edxCommonHelper, Mockito.times(1)).setPersonUIDOnUpdate(any(Long.class),any(PersonVO.class));

						 System.out.println("PASSED");
							
				
						
					}

						
						
			}else{
				
				
				System.out.println("Iteration: #"+iteration+"\nVerifying that Matched Observation from ODS is not found, and isLabCreate is true");
				boolean actualLabCreate = edxLabInformationDT.isLabIsCreate();
				boolean expectedLabCreated = true;
				Assert.assertEquals(actualLabCreate,expectedLabCreated);
				
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
 
	 
	}
	
}
		
	
