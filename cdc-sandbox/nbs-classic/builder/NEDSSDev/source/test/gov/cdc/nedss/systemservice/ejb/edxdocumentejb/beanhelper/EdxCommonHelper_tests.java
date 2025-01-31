package test.gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import static org.mockito.Matchers.any;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PhysicalLocatorDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxCommonHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

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


@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants" })
//@RunWith(PowerMockRunner.class)
@PrepareForTest({ EdxPHCRHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,
NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class EdxCommonHelper_tests { 
	
	
	
	
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
	public static class SetPersonUIDOnUpdate_test { 
		
	private String iteration;
	private Long aPersonUid;
	private PersonVO personVO;
	 

	 public SetPersonUIDOnUpdate_test(String it, Long aPersonUid, PersonVO personVO){
		 
		 super();
		 //Common Parameters
		// this.type = type;
		 this.iteration = it;
		 this.aPersonUid = aPersonUid;
		 this.personVO = personVO;
		
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

			  PersonVO personVO1 = new PersonVO();
    		  PersonVO personVO2 = new PersonVO();
			  
		  int it = 0;
	      return Arrays.asList(new Object[][]{
	    		

	    	
	    		  
	    		  {"setPersonUIDOnUpdate_test"+"_"+it++,1L,personVO1},
	    		  {"setPersonUIDOnUpdate_test"+"_"+it++,11111L,personVO1},
	    		  {"setPersonUIDOnUpdate_test"+"_"+it++,999999L,personVO1},
	    		  {"setPersonUIDOnUpdate_test"+"_"+it++,0L,personVO1},
	    		  {"setPersonUIDOnUpdate_test"+"_"+it++,0L,personVO2},
	    		  {"setPersonUIDOnUpdate_test"+"_"+it++,-1L,personVO1},
	    		  {"setPersonUIDOnUpdate_test"+"_"+it++,null,personVO1},
	    		  
	    		  
	    		  
	    		  
	    		  
	 
		  });
	   }
	 

	   
	     @Test
		 public void setPersonUIDOnUpdate_test() throws Exception {
				
	    	System.out.println("******************* Starting test case named: setPersonUIDOnUpdate_test *******************");
	    	
	    	
	    	
	    	System.out.println("Person Uid ="+aPersonUid);
	    	
	    	edxPHCRHelper=PowerMockito.spy(new EdxPHCRHelper());
	    	Whitebox.invokeMethod(new EdxCommonHelper(), "setPersonUIDOnUpdate", aPersonUid, personVO); 
	
			System.out.println("Iteration: #"+iteration+" PASSED");
			System.out.println("******************* End test case named: setPersonUIDOnUpdate_test *******************");
				
			 
		 }
	 
	 	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	/**
	 * UpdatePersonELRPHDCUpdate_test: this method will make sure that the current person vo, the one received from the Lab Report, gets updated as expected
	 * with information from the matched person vo existing in the system. It validates:
	 * 
	 * - Number of person names in the collection of the final VO is the expected
	 * - Number of entity ids in the collection of the final VO is the expected
	 * - The personDT.localId in the final VO is the correct one
	 * - The personDT.personParentUid in the final VO is the correct one
	 * - The VO itDirty attribute in the final VO is the correct one

	 * - The personNameDT.personUid in the final VO is the correct one
	 * - The PersonRaceDT.personuid in the final VO is the correct one
	 * - The personEthnicityDT.personuid in the final VO is the correct one
	 * - The entityIdDT.entityUid in the final VO is the correct one
	 * - The locatorParticipationDT.entityUid in the final VO is the correct one
	 * - The Postal Locator New attribute in the final VO is the correct one
	 * - The Postal Locator Dirty attribute in the final VO is the correct one
	 * - The Postal Locator Delete attribute in the final VO is the correct one
	 * - The Tele Locator New attribute in the final VO is the correct one
	 * - The Tele Locator Dirty attribute in the final VO is the correct one
	 * - The Tele Locator Delete attribute in the final VO is the correct one
	 * - The Physical Locator New attribute in the final VO is the correct one
	 * - The Physical Locator Dirty attribute in the final VO is the correct one
	 * - The Physical Locator Delete attribute in the final VO is the correct one
	 * 
	 * 
	 * 
	 * @author Fatima.Lopezcalzado
	 *
	 */

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRHelper" , "gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NedssUtils","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT","gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({EdxPHCRConstants.class,EdxRuleAlgorothmManagerDT.class, EdxPHCRHelper.class , NBSContext.class,PropertyUtil.class,NEDSSConstants.class,SessionContext.class,LogUtils.class,NbsDocumentHome.class,NbsDocument.class,EdxRuleAlgorothmManagerDT.class,
		NedssUtils.class,PortableRemoteObject.class,NbsDocumentHome.class})
	@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
	@PowerMockIgnore("javax.management.*")
	public static class UpdatePersonELRPHDCUpdate_test { 
		
	private String iteration;
	boolean personMatchedFound;
	private	Long personUid; 
	private	Long personParentUid; 
	private	String personLocalId; 
	private	int versionControlNumber;
	
	//Current Person VO
	
	private Long currentPersonUid;
		
	 

	 public UpdatePersonELRPHDCUpdate_test(String it, boolean personMatchedFound, Long personUid, 	Long personParentUid, 	String personLocalId, int versionControlNumber, Long currentPersonUid){
		 
		 super();
		 //Common Parameters
		// this.type = type;
		 this.iteration = it;
		 this.personMatchedFound = personMatchedFound;
		this.personUid = personUid;
		this.personParentUid = personParentUid;
		this.personLocalId=personLocalId;
		this.versionControlNumber=versionControlNumber;
		this.currentPersonUid = currentPersonUid;
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

			  PersonVO personVO1 = new PersonVO();
    		  PersonVO personVO2 = new PersonVO();
			  
		  int it = 0;
	      return Arrays.asList(new Object[][]{
	    		

	    	
	    		 //personmatchedFound, personUid,personParentUid,personLocalId, versionControlNumber, currentPersonUid
	    		  {"updatePersonELRPHDCUpdate_test"+"_"+it++,true,111111111L,111111112L,"PSN11111111GA01",1,22222222222L},
	    		  {"updatePersonELRPHDCUpdate_test"+"_"+it++,true,2222L,33333L,"PSN111111567GA01",1,22222222222L},
	    		  {"updatePersonELRPHDCUpdate_test"+"_"+it++,false,null,null,null,1,22222222222L},//if person matched not found, then the rest of values needs to be null
	    		  {"updatePersonELRPHDCUpdate_test"+"_"+it++,true,111111111L,null,"PSN11111891GA01",1,22222222222L},
	    		  {"updatePersonELRPHDCUpdate_test"+"_"+it++,true,null,33333L,"PSN11112222GA01",1,22222222222L},
	    		  {"updatePersonELRPHDCUpdate_test"+"_"+it++,true,2222L,2222L,"PSN111111567GA01",1,22222222222L},
 
	 
		  });
	   }
	 

	   
	     @Test
		 public void updatePersonELRPHDCUpdate_test() throws Exception {
				
	    	System.out.println("******************* Starting test case named: updatePersonELRPHDCUpdate_test *******************");
	    	
	    	PersonVO personPatientMatchedVO=null;
	    	
	    	if(personMatchedFound)
	    		personPatientMatchedVO= createPersonMatchedData(	personUid, personParentUid, personLocalId,  versionControlNumber);
	  
	    	
	    	PersonVO currentPersonPatientVO = createCurrentPersonData(currentPersonUid);//From Lab
	    	
	    	int personNameCollectionCurrentPersonSize = 0;
	    	
	    	if(currentPersonPatientVO.getThePersonNameDTCollection()!=null)
	    		personNameCollectionCurrentPersonSize=currentPersonPatientVO.getThePersonNameDTCollection().size();
	    	
	    	int entityCollectionSize = 0;
	    	
	    	if(currentPersonPatientVO.getTheEntityIdDTCollection()!=null)
	    		entityCollectionSize=currentPersonPatientVO.getTheEntityIdDTCollection().size();
	    	
	    	
	    	edxPHCRHelper=PowerMockito.spy(new EdxPHCRHelper());
	    	Whitebox.invokeMethod(new EdxCommonHelper(), "updatePersonELRPHDCUpdate", currentPersonPatientVO, personPatientMatchedVO); 
	
	    	System.out.println("Iteration: #"+iteration+"\n");
			
	    	//Validate the PersonDT

	    	System.out.println("Person local Id. Expected: "+personLocalId+" Actual: "+currentPersonPatientVO.getThePersonDT().getLocalId());
	    	Assert.assertEquals(personLocalId,currentPersonPatientVO.getThePersonDT().getLocalId());
	    	System.out.println("PASSED");
			
	    	System.out.println("Person parent uid. Expected: "+personParentUid+" Actual: "+currentPersonPatientVO.getThePersonDT().getPersonParentUid());
	    	Assert.assertEquals(personParentUid,currentPersonPatientVO.getThePersonDT().getPersonParentUid());
	    	System.out.println("PASSED");
			
	    	boolean dirtyExpected = true;
	    	
	    	System.out.println("Dirty. Expected: "+dirtyExpected+" Actual: "+currentPersonPatientVO.isItDirty());
	    	Assert.assertEquals(dirtyExpected,currentPersonPatientVO.isItDirty());
	    	System.out.println("PASSED");
			
	    	
	    	//Validate person name collection
	    	
	    	if(currentPersonPatientVO.getThePersonNameDTCollection()!=null){
	    		if(currentPersonPatientVO.getThePersonNameDTCollection()!=null && currentPersonPatientVO.getThePersonNameDTCollection().size()>0){
					for (Iterator<Object> it = currentPersonPatientVO.getThePersonNameDTCollection().iterator(); it.hasNext();){
						
						PersonNameDT personNameDT = (PersonNameDT)it.next();
						Long actualPersonuid = personNameDT.getPersonUid();
					
						System.out.println("Expected PersonNameDT.personuid: "+personUid+" Actual: "+actualPersonuid);
				    	Assert.assertEquals(personUid,actualPersonuid);
				    	System.out.println("PASSED");
					
				    	
				    	//The length of the person name collection needs to be the sum of both person name collections
					}
						
	    		}
	    		
	    		
	    		
	    	}
	    	
	    	
	    	//Validate the number of Person names in the collection
	    	
	    	int totalPersonSize = personNameCollectionCurrentPersonSize;
	    	
	    	if(personPatientMatchedVO!=null && personPatientMatchedVO.getThePersonNameDTCollection()!=null)
	    		totalPersonSize += personPatientMatchedVO.getThePersonNameDTCollection().size();
	    	
	    	
	    	int actualPersonNameCollection = 0;

	    	if(currentPersonPatientVO.getThePersonNameDTCollection()!=null)
	    		actualPersonNameCollection = currentPersonPatientVO.getThePersonNameDTCollection().size();
	    	
	    	System.out.println("Expected number of person name collection: "+totalPersonSize+". Actual: "+actualPersonNameCollection);
	    	
	    	Assert.assertEquals(totalPersonSize,actualPersonNameCollection);
	    	System.out.println("PASSED");
	    	
	    	
	    	
	    	
	    	//Validate person race collection
	    	
	    	if(currentPersonPatientVO.getThePersonRaceDTCollection()!=null){
	    		if(currentPersonPatientVO.getThePersonRaceDTCollection()!=null && currentPersonPatientVO.getThePersonRaceDTCollection().size()>0){
					for (Iterator<Object> it = currentPersonPatientVO.getThePersonRaceDTCollection().iterator(); it.hasNext();){
						
						PersonRaceDT personRaceDT = (PersonRaceDT)it.next();
						Long actualPersonuid = personRaceDT.getPersonUid();
					
						System.out.println("Expected PersonRaceDT.personuid: "+personUid+" Actual: "+actualPersonuid);
				    	Assert.assertEquals(personUid,actualPersonuid);
				    	System.out.println("PASSED");
					
					}
						
	    		}
	    		
	    		
	    		
	    	}
	    	
	    	
	    	

	    	//Validate Ethnicity collection
	    	
	    	if(currentPersonPatientVO.getThePersonEthnicGroupDTCollection()!=null){
	    		if(currentPersonPatientVO.getThePersonEthnicGroupDTCollection()!=null && currentPersonPatientVO.getThePersonEthnicGroupDTCollection().size()>0){
					for (Iterator<Object> it = currentPersonPatientVO.getThePersonEthnicGroupDTCollection().iterator(); it.hasNext();){
						
						PersonEthnicGroupDT personEthnicityDT = (PersonEthnicGroupDT)it.next();
						Long actualPersonuid = personEthnicityDT.getPersonUid();
					
						System.out.println("Expected personEthnicityDT.personuid: "+personUid+" Actual: "+actualPersonuid);
				    	Assert.assertEquals(personUid,actualPersonuid);
				    	System.out.println("PASSED");
					
					}
						
	    		}
	    		
	    		
	    		
	    	}
	    	
	    	
	    	//Validate Entity ID collection
	    	
	    	if(currentPersonPatientVO.getTheEntityIdDTCollection()!=null){
	    		if(currentPersonPatientVO.getTheEntityIdDTCollection()!=null && currentPersonPatientVO.getTheEntityIdDTCollection().size()>0){
					for (Iterator<Object> it = currentPersonPatientVO.getTheEntityIdDTCollection().iterator(); it.hasNext();){
						
						EntityIdDT entityIdDT = (EntityIdDT)it.next();
						Long entityUid = entityIdDT.getEntityUid();
					
						System.out.println("Expected entityIdDT.entityUid: "+personUid+" Actual: "+entityUid);
				    	Assert.assertEquals(personUid,entityUid);
				    	System.out.println("PASSED");
					
					}
						
	    		}
	    		
	    		
	    		
	    	}
	    	
	    	
	    	//Validate the number of Entity Uids in the collection
	    	
	    	int totalEntitiesSize = entityCollectionSize;
	    	
	    	if(personPatientMatchedVO!=null && personPatientMatchedVO.getTheEntityIdDTCollection()!=null)
	    		totalEntitiesSize += personPatientMatchedVO.getTheEntityIdDTCollection().size();
	    	
	    	
	    	int actualtotalEntitiesSize = 0;

	    	if(currentPersonPatientVO.getTheEntityIdDTCollection()!=null)
	    		actualtotalEntitiesSize = currentPersonPatientVO.getTheEntityIdDTCollection().size();
	    	
	    	
	    	System.out.println("Expected number of Entity Ids collection: "+actualtotalEntitiesSize+". Actual: "+currentPersonPatientVO.getTheEntityIdDTCollection().size());
	    	
	    	Assert.assertEquals(totalEntitiesSize,actualtotalEntitiesSize);
	    	System.out.println("PASSED");
	    	
	    			
	    			
	    	//Validate Locator Participator collection
	    	
	    	if(currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection()!=null){
	    		if(currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection()!=null && currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection().size()>0){
					for (Iterator<Object> it = currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection().iterator(); it.hasNext();){
						
						EntityLocatorParticipationDT locatorParticipationDT = (EntityLocatorParticipationDT)it.next();
						Long entityUid = locatorParticipationDT.getEntityUid();
					
						System.out.println("Expected locatorParticipationDT.entityUid: "+personUid+" Actual: "+entityUid);
				    	Assert.assertEquals(personUid,entityUid);
				    	System.out.println("PASSED");
				    	
				    	boolean postalNew = locatorParticipationDT.getThePostalLocatorDT().isItNew();
				    	boolean postalDelete = locatorParticipationDT.getThePostalLocatorDT().isItDelete();
				    	boolean postalDirty = locatorParticipationDT.getThePostalLocatorDT().isItDirty();
				       	
				    	System.out.println("Expected Postal Locator New: true. Actual: "+postalNew);
				    	Assert.assertEquals(true,postalNew);
				    	System.out.println("PASSED");
				       	
				    	System.out.println("Expected Postal Locator Delete: false. Actual: "+postalDelete);
				    	Assert.assertEquals(false,postalDelete);
				    	System.out.println("PASSED");
				       	
				    	System.out.println("Expected Postal Locator Dirty: false. Actual: "+postalDirty);
				    	Assert.assertEquals(false,postalDirty);
				    	System.out.println("PASSED");
				    	
				    	
				    	
				    	
				    	
				    	boolean teleNew = locatorParticipationDT.getTheTeleLocatorDT().isItNew();
				    	boolean teleDelete = locatorParticipationDT.getTheTeleLocatorDT().isItDelete();
				    	boolean teleDirty = locatorParticipationDT.getTheTeleLocatorDT().isItDirty();
				       	
				    	System.out.println("Expected Tele Locator New: true. Actual: "+teleNew);
				    	Assert.assertEquals(true,postalNew);
				    	System.out.println("PASSED");
				       	
				    	System.out.println("Expected Tele Locator Delete: false. Actual: "+teleDelete);
				    	Assert.assertEquals(false,postalDelete);
				    	System.out.println("PASSED");
				       	
				    	System.out.println("Expected Tele Locator Dirty: false. Actual: "+teleDirty);
				    	Assert.assertEquals(false,postalDirty);
				    	System.out.println("PASSED");
				    	
				    	
				    	
				    	
				    	
				    	boolean physicalNew = locatorParticipationDT.getThePhysicalLocatorDT().isItNew();
				    	boolean physicalDelete = locatorParticipationDT.getThePostalLocatorDT().isItDelete();
				    	boolean physicalDirty = locatorParticipationDT.getThePostalLocatorDT().isItDirty();
				       	
				    	System.out.println("Expected Physical Locator New: true. Actual: "+physicalNew);
				    	Assert.assertEquals(true,physicalNew);
				    	System.out.println("PASSED");
				       	
				    	System.out.println("Expected Physical Locator Delete: false. Actual: "+physicalDelete);
				    	Assert.assertEquals(false,physicalDelete);
				    	System.out.println("PASSED");
				       	
				    	System.out.println("Expected Physical Locator Dirty: false. Actual: "+physicalDirty);
				    	Assert.assertEquals(false,physicalDirty);
				    	System.out.println("PASSED");
				    	
				    	
				    	
					
					}
						
	    		}
	    		
	    		
	    		
	    	}
	    	
	    	
	    
	    	
	    	
	    	
			System.out.println("******************* End test case named: updatePersonELRPHDCUpdate_test *******************");
				
			 
		 }
	 
	 	}

	public static PersonVO createPersonMatchedData(Long personUid, Long personParentUid, String personLocalId, int versionControlNumber){
		
		

    	PersonVO personPatientMatchedVO = new PersonVO();//Matched patient found
    	
    	
    	//Data for Patient Matched
    	//PersonDT
    	PersonDT personDT = new PersonDT();
    	
    	personDT.setPersonUid(personUid);
    	personDT.setPersonParentUid(personParentUid);
    	personDT.setLocalId(personLocalId);
    	personDT.setVersionCtrlNbr(versionControlNumber);

    	personPatientMatchedVO.setThePersonDT(personDT);
		
    	//Person name collection
    	
		
    	Collection<Object> personNameCollection = new ArrayList<Object>();
    	
    	PersonNameDT personNameDT = new PersonNameDT();
    	personNameDT.setPersonNameSeq(1);
    	personNameDT.setFirstNm("Name1");
    	personNameDT.setPersonUid(personUid);
    	
    	personNameCollection.add(personNameDT);
    	
    	PersonNameDT personNameDT2 = new PersonNameDT();
    	personNameDT2.setPersonNameSeq(1);
    	personNameDT2.setFirstNm("Name2");
    	personNameDT2.setPersonUid(personUid);
    	personNameCollection.add(personNameDT2);
    	
    	personPatientMatchedVO.setThePersonNameDTCollection(personNameCollection);
    	
    	//Person race
    	
    	Collection<Object> personRaceCollection = new ArrayList<Object>();
    	
    	PersonRaceDT personRaceDT = new PersonRaceDT();
    	personRaceDT.setRaceCd("1002-5");
    	personRaceDT.setPersonUid(personUid);
    	personRaceCollection.add(personRaceDT);
    	
    	PersonRaceDT personRaceDT2 = new PersonRaceDT();
    	personRaceDT2.setRaceCd("1004-1");
    	personRaceDT2.setPersonUid(personUid);
    	
    	personRaceCollection.add(personRaceDT2);
    	
    	personPatientMatchedVO.setThePersonRaceDTCollection(personRaceCollection);
    	
    	//Person Ethnicity
    	
    	Collection<Object> personEthnicityCollection = new ArrayList<Object>();
    	
    	PersonEthnicGroupDT personEthnicity1 = new PersonEthnicGroupDT();
    	personEthnicity1.setEthnicGroupCd("1002-5");
    	personEthnicity1.setPersonUid(personUid);
    	
    	personEthnicityCollection.add(personEthnicity1);
    	
    	PersonEthnicGroupDT personEthnicity2 = new PersonEthnicGroupDT();
    	personEthnicity2.setEthnicGroupCd("1004-1");
    	personEthnicity2.setPersonUid(personUid);
    	personEthnicityCollection.add(personEthnicity2);
    	
    	personPatientMatchedVO.setThePersonEthnicGroupDTCollection(personEthnicityCollection);
    	
    	
    	
    	
    	Collection<Object> entityDTCollection = new ArrayList<Object>();
    	
    	EntityIdDT entityId1 = new EntityIdDT();
    	entityId1.setEntityIdSeq(123);
    	entityId1.setEntityUid(personUid);
    	EntityIdDT entityId2 = new EntityIdDT();
    	entityId2.setEntityIdSeq(456);
    	entityId2.setEntityUid(personUid);
    	
    	entityDTCollection.add(entityId1);
    	entityDTCollection.add(entityId2);
    	
    	
    	personPatientMatchedVO.setTheEntityIdDTCollection(entityDTCollection);
    	
    	//Locator participator
    	
    	
    	Collection<Object> locatorParticipatorCollection = new ArrayList<Object>();
    	
    	EntityLocatorParticipationDT locatorParticipator1 = new EntityLocatorParticipationDT();
    	
    	PostalLocatorDT postalDT = new PostalLocatorDT();
    	postalDT.setZipCd("12345");
    	
    	TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
    	teleLocatorDT.setPhoneNbrTxt("1231231212");
    	
    	PhysicalLocatorDT physicalLocatorDT = new PhysicalLocatorDT();
    	physicalLocatorDT.setPhysicalLocatorUid(999L);
    	
    	locatorParticipator1.setEntityUid(personUid);
    	locatorParticipator1.setThePostalLocatorDT(postalDT);
    	locatorParticipator1.setTheTeleLocatorDT(teleLocatorDT);
    	locatorParticipator1.setThePhysicalLocatorDT(physicalLocatorDT);
    	
    	
   
    	locatorParticipatorCollection.add(locatorParticipator1);
    
    	
    	
    	return personPatientMatchedVO;
    	
    	
		
		
		
		
	}
	
	
	public static PersonVO createCurrentPersonData(Long currentPersonUid){
		
		

    	PersonVO currentPersonVO = new PersonVO();
    	
    	
    
    	//PersonDT
    	PersonDT personDT = new PersonDT();
    	
    	personDT.setPersonUid(currentPersonUid);
    	personDT.setPersonParentUid(111111113L);
    	personDT.setLocalId("PSN11111113GA01");
    	personDT.setVersionCtrlNbr(1);

    	currentPersonVO.setThePersonDT(personDT);
		
    	//Person name collection
    	
		
    	Collection<Object> personNameCollection = new ArrayList<Object>();
    	
    	PersonNameDT personNameDT = new PersonNameDT();
    	personNameDT.setPersonNameSeq(1);
    	personNameDT.setFirstNm("Name1");
    	personNameCollection.add(personNameDT);
    	
    	PersonNameDT personNameDT2 = new PersonNameDT();
    	personNameDT2.setPersonNameSeq(1);
    	personNameDT2.setFirstNm("Name2");
    	personNameCollection.add(personNameDT2);
    	
    	currentPersonVO.setThePersonNameDTCollection(personNameCollection);
    	
    	//Person race
    	
    	Collection<Object> personRaceCollection = new ArrayList<Object>();
    	
    	PersonRaceDT personRaceDT = new PersonRaceDT();
    	personRaceDT.setRaceCd("1031-4");
    	personRaceCollection.add(personRaceDT);
    	
    	PersonRaceDT personRaceDT2 = new PersonRaceDT();
    	personRaceDT2.setRaceCd("1004-1");
    	personRaceCollection.add(personRaceDT2);
    	
    	currentPersonVO.setThePersonRaceDTCollection(personRaceCollection);
    	
    	
    	
    	//Person Ethnicity
    	
    	Collection<Object> personEthnicityCollection = new ArrayList<Object>();
    	
    	PersonEthnicGroupDT personEthnicity1 = new PersonEthnicGroupDT();
    	personEthnicity1.setEthnicGroupCd("1002-5");
    	personEthnicityCollection.add(personEthnicity1);
    	
    	PersonEthnicGroupDT personEthnicity2 = new PersonEthnicGroupDT();
    	personEthnicity2.setEthnicGroupCd("1004-1");
    	personEthnicityCollection.add(personEthnicity2);
    	
    	currentPersonVO.setThePersonEthnicGroupDTCollection(personEthnicityCollection);
    	
    	
    	
    	//EntityDT

    	
    	Collection<Object> entityDTCollection = new ArrayList<Object>();
    	
    	EntityIdDT entityId1 = new EntityIdDT();
    	entityId1.setEntityIdSeq(123);
    	EntityIdDT entityId2 = new EntityIdDT();
    	entityId2.setEntityIdSeq(456);
    
    	entityDTCollection.add(entityId1);
    	entityDTCollection.add(entityId2);
    	
    	
    	currentPersonVO.setTheEntityIdDTCollection(entityDTCollection);
    	
    	//Locator participator
    	
    	
    	Collection<Object> locatorParticipatorCollection = new ArrayList<Object>();
    	
    	EntityLocatorParticipationDT locatorParticipator1 = new EntityLocatorParticipationDT();
    	
    	PostalLocatorDT postalDT = new PostalLocatorDT();
    	postalDT.setZipCd("12345");
    	
    	TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
    	teleLocatorDT.setPhoneNbrTxt("1231231212");
    	
    	PhysicalLocatorDT physicalLocatorDT = new PhysicalLocatorDT();
    	physicalLocatorDT.setPhysicalLocatorUid(999L);
    	
    	
    	locatorParticipator1.setThePostalLocatorDT(postalDT);
    	locatorParticipator1.setTheTeleLocatorDT(teleLocatorDT);
    	locatorParticipator1.setThePhysicalLocatorDT(physicalLocatorDT);
    	
    	
   
    	locatorParticipatorCollection.add(locatorParticipator1);
    	currentPersonVO.setTheEntityLocatorParticipationDTCollection(locatorParticipatorCollection);
    	
    	
    	return currentPersonVO;
    	
    	
		
		
		
		
	}


	
}
		
	
