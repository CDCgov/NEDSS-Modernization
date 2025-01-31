package test.gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util;

import static org.mockito.Matchers.any;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.ejb.dao.PersonRootDAOImpl;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPatientMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.DSMUpdateAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessCaseSummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.UpdateCaseSummaryVO;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.XMLTypeToNBSObject","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(Enclosed.class)
@PrepareForTest({XMLTypeToNBSObject.class})
@PowerMockIgnore("javax.management.*")
public class XMLTypeToNBSObject_tests {
	
	
/**
 * GetMatchedCaseForUpdate_test: this method will pass if the local id of the VO returned matches the expected one. All this information is hard coded, I think this test method
 * should have been created in a way that at least the local id that we are checking if exists in the array changes for each iteration.
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.XMLTypeToNBSObject","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({XMLTypeToNBSObject.class})
@PowerMockIgnore("javax.management.*")
public static class GetMatchedCaseForUpdate_test {
	String expectedValue="01234Local2";
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	LogUtils logger;
	
	@Mock
	NbsDocumentDAOImpl nbsDocumentDAOImpl;
	
	@Mock
	NBSSecurityObj nbsSecurityObj;
	
	@Mock
	NbsInterfaceDT nbsInterfaceDT;
	
	
	@Mock
	EdxPatientMatchingCriteriaUtil edxPatientMatchingCriteriaUtil;
	
	@Mock
	EntityController entityController=Mockito.mock(EntityController.class);
	

	
	@InjectMocks
	XMLTypeToNBSObject xmlTypeToNBSObject=Mockito.mock(XMLTypeToNBSObject.class);
	
	public GetMatchedCaseForUpdate_test(){
		super();
	}
		
	 @Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "logger", logger);
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "updateMap", new HashMap<String, DSMUpdateAlgorithmDT>());
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "ASSO_LIST", "ASSO_LIST");
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "WITH_IN_TIMEFRAME_LIST", "WITH_IN_TIMEFRAME_LIST");
	 }
	
	@Test
	public void getMatchedCaseForUpdate_test() throws Exception {
		 PowerMockito.spy(XMLTypeToNBSObject.class);
		 ArrayList<Object> matchCase=Whitebox.invokeMethod(xmlTypeToNBSObject,"getMatchedCaseForUpdate", getUpdateCaseSummaryVOList(), "01234Local2");
		 UpdateCaseSummaryVO ucsv=(UpdateCaseSummaryVO) matchCase.get(0);
		 Assert.assertEquals(this.expectedValue,ucsv.getLocalId());
		 System.out.println("Method Name: getMatchedCaseForUpdate,  iteration:::1. Expected value: "+this.expectedValue+" Actual value: "+ucsv.getLocalId()+", RESULT::::::PASSED");
			
	}
	

		
	
	private ArrayList<Object> getUpdateCaseSummaryVOList(){
		ArrayList<Object> list= new ArrayList<Object>();
		UpdateCaseSummaryVO ucsv1= new UpdateCaseSummaryVO();
		ucsv1.setLocalId("01234Local1");
		UpdateCaseSummaryVO ucsv2= new UpdateCaseSummaryVO();
		ucsv2.setLocalId("01234Local2");
		UpdateCaseSummaryVO ucsv3= new UpdateCaseSummaryVO();
		ucsv3.setLocalId("01234Local3");
		list.add(ucsv1);
		list.add(ucsv2);
		list.add(ucsv3);
		return list;
	}
	
}

/**
 * LoadDSMUpdateAlgorithmList_test: this test will pass if the condition we are looking for in the DSM map exists. 
 * @author Fatima.Lopezcalzado
 *
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.XMLTypeToNBSObject","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({XMLTypeToNBSObject.class})
@PowerMockIgnore("javax.management.*")
public static class LoadDSMUpdateAlgorithmList_test {
	String expectedValue="01234Local2";
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	LogUtils logger;
	
	@Mock
	NbsDocumentDAOImpl nbsDocumentDAOImpl;
	
	@Mock
	NBSSecurityObj nbsSecurityObj;
	
	@Mock
	NbsInterfaceDT nbsInterfaceDT;
	
	
	@Mock
	EdxPatientMatchingCriteriaUtil edxPatientMatchingCriteriaUtil;
	
	@Mock
	EntityController entityController=Mockito.mock(EntityController.class);
	

	
	@InjectMocks
	XMLTypeToNBSObject xmlTypeToNBSObject=Mockito.mock(XMLTypeToNBSObject.class);
	
	public LoadDSMUpdateAlgorithmList_test(){
		super();
	}
		
	 @Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "logger", logger);
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "updateMap", new HashMap<String, DSMUpdateAlgorithmDT>());
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "ASSO_LIST", "ASSO_LIST");
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "WITH_IN_TIMEFRAME_LIST", "WITH_IN_TIMEFRAME_LIST");
	 }
	

	
	@Test
	public void loadDSMUpdateAlgorithmList_test() throws Exception{
		PowerMockito.mockStatic(PropertyUtil.class);
		Mockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
		xmlTypeToNBSObject = PowerMockito.spy(new XMLTypeToNBSObject());
		PowerMockito.whenNew(NbsDocumentDAOImpl.class).withNoArguments().thenReturn(nbsDocumentDAOImpl);
		Mockito.when(nbsDocumentDAOImpl.getDSMUpdateAlgorithmList()).thenReturn(getUpdatedMap());
		Map<String, DSMUpdateAlgorithmDT> uMap=Whitebox.invokeMethod(xmlTypeToNBSObject,"loadDSMUpdateAlgorithmList");
		Assert.assertEquals((getUpdatedMap().get("11065ALL")).getConditionCd(),(uMap.get("11065ALL")).getConditionCd());
		System.out.println("Method:loadDSMUpdateAlgorithmList, iteration::::1, Expected Result:"+(getUpdatedMap().get("11065ALL")).getConditionCd()+", Actual Result:"+(uMap.get("11065ALL")).getConditionCd()+", RESULT::::PASSED");
	}
	
	
	private Map<String, DSMUpdateAlgorithmDT> getUpdatedMap() {
		 DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT = new DSMUpdateAlgorithmDT();
		 Map<String, DSMUpdateAlgorithmDT> updateMap = new HashMap<String, DSMUpdateAlgorithmDT>();
		 dsmUpdateAlgorithmDT.setDsmUpdateAlgorithmUid(1L);
		 dsmUpdateAlgorithmDT.setConditionCd("11065");
		 dsmUpdateAlgorithmDT.setSendingSystemNm("ALL");
		 updateMap.put("11065ALL", dsmUpdateAlgorithmDT);
		 return updateMap;
	}

}

/**
 * InsertNbsDocumentVO_test: this test method validates different things:
 * 
 * - Associated to Inv value is the expected one (done from first time the test case was created (Sai).
 * - The final document is recognized as an update or not depending on the old nbs document vo exists or not (Fatima).
 * - No exception happen and the method runs as expected with different parameters (Fatima).
 * - if it is an update document, checks if updateNbsDocument is called once when (Fatima).
 * 
 * @author Fatima.Lopezcalzado
 *
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.XMLTypeToNBSObject","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT", "gov.cdc.nedss.entity.person.ejb.dao.PersonRootDAOImpl"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({XMLTypeToNBSObject.class, NbsDocumentDAOImpl.class, NBSDocumentDT.class, PersonRootDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InsertNbsDocumentVO_test {
	
	private String iteration;
	private Long nbsDocumentUid;
	private Long personUid;
	private boolean previousDocExists;
	private String previosRecordStatusCd;
	private String skipDRRQ;
	
	
	public InsertNbsDocumentVO_test(String it, Long nbsDocumentUid, Long personUid, boolean previousDocExists, String previosRecordStatusCd, String skipDRRQ){
		super();
		
		 this.iteration = it;
		 this.nbsDocumentUid = nbsDocumentUid;
		 this.personUid = personUid;
		 this.previousDocExists = previousDocExists;
		 this.previosRecordStatusCd = previosRecordStatusCd;
		 this.skipDRRQ = skipDRRQ;
			
	}
		
	

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   

		  int it = 0;
		   
	      return Arrays.asList(new Object[][]{
	    		  
	    
	    		//nbsDocumentUid, personUid, previousDocExists, previosRecordStatusCd, skipDRRQ
	    			
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 333333L, 2222222L, true, "UNPROCESSED", "Y"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 1111111L, 4444444L, false, "UNPROCESSED", "Y"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 4444444L, 2222222L, true, "PROCESSED", "Y"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 333333L, 5555555L, false, "PROCESSED", "Y"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 333333L, 0L, false, "PROCESSED", "Y"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, null, 0L, false, "PROCESSED", "Y"},
	    		  
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 4444444L, 2222222L, true, "UNPROCESSED", "N"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 333333L, 5555555L, false, "UNPROCESSED", "N"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 333333L, 1111111L, true, "PROCESSED", "N"},
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 5555555L, 4444444L, false, "PROCESSED", "N"},
	    		  
	    		  
		    		 
	    		  {"insertNbsDocumentVO_test"+"_"+it++, 333333L, 2222222L, false, "UNPROCESSED","Y"},
	    		  
	    		  
	    		 
		  });
	   }
	 
	   
	   
	   
	   
	String expectedValue="01234Local2";
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	LogUtils logger;
	

	
	@Mock
	NBSSecurityObj nbsSecurityObj;
	
	@Mock
	NbsInterfaceDT nbsInterfaceDT;
	
	
	@Mock
	EdxPatientMatchingCriteriaUtil edxPatientMatchingCriteriaUtil;
	
	@Mock
	EntityController entityController=Mockito.mock(EntityController.class);
	
	@Mock
	PersonRootDAOImpl personRootDAO = Mockito.mock(PersonRootDAOImpl.class);
	@Mock
	NBSDocumentVO nbsDocVOOld = Mockito.mock(NBSDocumentVO.class);
	
	@Mock
	PrepareVOUtils pre = Mockito.mock(PrepareVOUtils.class);
	
	@Mock
	NbsDocumentDAOImpl nbsDocDao = Mockito.mock(NbsDocumentDAOImpl.class);
	
	@InjectMocks
	@Spy
	XMLTypeToNBSObject xmlTypeToNBSObject=Mockito.mock(XMLTypeToNBSObject.class);
	
	
	 

	   
	   
	 @Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 personRootDAO = Mockito.mock(PersonRootDAOImpl.class);
		 nbsDocVOOld = Mockito.mock(NBSDocumentVO.class);
		 pre = Mockito.mock(PrepareVOUtils.class);
		 edxPatientMatchingCriteriaUtil = Mockito.mock(EdxPatientMatchingCriteriaUtil.class);
		 nbsInterfaceDT = Mockito.mock(NbsInterfaceDT.class);
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "logger", logger);
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "updateMap", new HashMap<String, DSMUpdateAlgorithmDT>());
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "ASSO_LIST", "ASSO_LIST");
		 Whitebox.setInternalState(XMLTypeToNBSObject.class, "WITH_IN_TIMEFRAME_LIST", "WITH_IN_TIMEFRAME_LIST");
		
		 
	 }
	
	@Test
	public void insertNbsDocumentVO_test() throws Exception{

		NBSDocumentDT nbsDocDT = new NBSDocumentDT();
		
		PowerMockito.whenNew(NbsDocumentDAOImpl.class).withNoArguments().thenReturn(nbsDocDao);
		Mockito.when(nbsDocDao.insertNBSDocumentHist(any(NBSDocumentDT.class))).thenReturn(nbsDocDT);
			
		
		
	
		PowerMockito.mockStatic(PropertyUtil.class);
		Mockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
		xmlTypeToNBSObject = PowerMockito.spy(new XMLTypeToNBSObject());
		PowerMockito.whenNew(EdxPatientMatchingCriteriaUtil.class).withNoArguments().thenReturn(edxPatientMatchingCriteriaUtil);
		//PowerMockito.whenNew(NbsDocumentDAOImpl.class).withNoArguments().thenReturn(nbsDocumentDAOImpl);
		PowerMockito.whenNew(NbsInterfaceDT.class).withNoArguments().thenReturn(nbsInterfaceDT);
		Mockito.when(edxPatientMatchingCriteriaUtil.getEntityController()).thenReturn(entityController);
		Mockito.when(entityController.setPatientRevision(any(PersonVO.class),
					any(String.class), any(NBSSecurityObj.class))).thenReturn(98765L);
		Mockito.when(nbsDocDao.getDSMUpdateAlgorithmList()).thenReturn(getUpdatedMap());

		PersonVO personVO = new PersonVO();
		PersonDT personDT = new PersonDT();
		personDT.setPersonUid(personUid);
		personVO.setThePersonDT(personDT);

		//Getting old NBSDocumentVO
		
		Mockito.when(nbsDocVOOld.getPatientVO()).thenReturn(personVO);
		
	
		NBSDocumentDT oldNBSDocumentDT = new NBSDocumentDT();
		
		
		if(previousDocExists){
			oldNBSDocumentDT.setNbsDocumentUid(nbsDocumentUid);
			oldNBSDocumentDT.setLocalId("CAS1111111111GA");
			oldNBSDocumentDT.setExternalVersionCtrlNbr(1);
			oldNBSDocumentDT.setVersionCtrlNbr(1);
			
		}
		
		

		Mockito.when(nbsDocVOOld.getNbsDocumentDT()).thenReturn(oldNBSDocumentDT);
		
		Mockito.when(nbsDocDao.getNBSDocument(nbsDocumentUid)).thenReturn(nbsDocVOOld);
		
		
		
		
		//personUid
		 
		PowerMockito.whenNew(PersonRootDAOImpl.class).withNoArguments().thenReturn(personRootDAO);
		 
		Mockito.when(personRootDAO.loadObject(personUid)).thenReturn(personVO);//personUid is from the person of the NBSDocVOOld
		
			
	
		//Existing document
		Mockito.when(nbsDocDao.getLastDocument(any(NBSDocumentVO.class))).thenReturn(oldNBSDocumentDT);
		Mockito.when(nbsDocDao.insertNBSDocument(any(NBSDocumentDT.class))).thenReturn(getNBSDocumentDT());
		NBSDocumentVO nbsDocVO = getNBSDocumentVO();
		nbsDocVO.getNbsDocumentDT().setRecordStatusCd(previosRecordStatusCd);
	
		PowerMockito.whenNew(PrepareVOUtils.class).withNoArguments().thenReturn(pre);
		NBSDocumentDT nbsDocumentDTAfterPrep = new NBSDocumentDT();//to simulate the values returned by the prepareVO
		//It is not important to check the next status returned is the expected one as it is mocked anyways and that would be testing a different method
		Mockito.when(pre.prepareVO(nbsDocDT, NBSBOLookup.DOCUMENT, previosRecordStatusCd, "NBS_DOCUMENT", "BASE", nbsSecurityObj)).thenReturn(nbsDocumentDTAfterPrep);
		
	
		Mockito.when(propertyUtil.getPHDCSkipDRRQ()).thenReturn(skipDRRQ);
		Mockito.when(nbsDocDao.updateNbsDocument(any(NBSDocumentDT.class))).thenReturn(1L);
	
		//Mockito.when(actRelationshipDaoImpl.load(actUid)).thenReturn(actRelationshipDTs);
	 	
		
		
		NBSDocumentVO nbsDocumentVO=Whitebox.invokeMethod(xmlTypeToNBSObject,"insertNbsDocumentVO",nbsInterfaceDT,nbsDocVO,nbsSecurityObj);
		//Assert.assertFalse(nbsDocumentVO.isAssociatedInv());
		System.out.println("Iteration: "+iteration+" Method:insertNbsDocumentVO, Expected Value: false, Actual Value:"+nbsDocumentVO.isAssociatedInv());
		Assert.assertEquals(false, nbsDocumentVO.isAssociatedInv());
			
		System.out.println("PASSED"); 
		
		//Check if it is an update of the document or not in order to pass this new version of the test method
		//nBSDocumentVO.setDocumentUpdate(true);
		
		System.out.println("Iteration: "+iteration+" Method:insertNbsDocumentVO, Expected Value, document update = "+previousDocExists+", Actual Value, document update = "+nbsDocVO.isDocumentUpdate());
		
		if(previousDocExists)
			Assert.assertEquals(true, nbsDocVO.isDocumentUpdate());
		else
			Assert.assertEquals(false, nbsDocVO.isDocumentUpdate());
		
		
		System.out.println("PASSED"); 
	
		
		if(previousDocExists){
			System.out.println("Iteration: "+iteration+" Method:insertNbsDocumentVO, Expected updateNbsDocument method to be called once:");
			Mockito.verify(nbsDocDao, Mockito.times(1)).updateNbsDocument(any(NBSDocumentDT.class));
			System.out.println("PASSED"); 
		}		
	
	
	}
	
	
	private Map<String, DSMUpdateAlgorithmDT> getUpdatedMap() {
		 DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT = new DSMUpdateAlgorithmDT();
		 Map<String, DSMUpdateAlgorithmDT> updateMap = new HashMap<String, DSMUpdateAlgorithmDT>();
		 dsmUpdateAlgorithmDT.setDsmUpdateAlgorithmUid(1L);
		 dsmUpdateAlgorithmDT.setConditionCd("11065");
		 dsmUpdateAlgorithmDT.setSendingSystemNm("ALL");
		 updateMap.put("11065ALL", dsmUpdateAlgorithmDT);
		 return updateMap;
	}

	private NBSDocumentVO getNBSDocumentVO() {
		NBSDocumentVO nbdDocumentvo= new NBSDocumentVO();
		nbdDocumentvo.setContactRecordDoc(true);
		Map<String, EDXEventProcessCaseSummaryDT> caseSummaryMap= new HashMap<String, EDXEventProcessCaseSummaryDT>();
		EDXEventProcessCaseSummaryDT caseDT= new EDXEventProcessCaseSummaryDT();
		caseDT.setConditionCd("1234cond");
		caseDT.setPersonParentUid(12345L);
		caseSummaryMap.put("ContactInv", caseDT);
		nbdDocumentvo.seteDXEventProcessCaseSummaryDTMap(caseSummaryMap);
		return nbdDocumentvo;
	}
	
	
	private NBSDocumentDT getNBSDocumentDT() {
		NBSDocumentDT ndt= new NBSDocumentDT();
		ndt.setEffectiveTime(new Timestamp(System.currentTimeMillis()));
		ndt.setLocalId("local123");
		ndt.setExternalVersionCtrlNbr(1);
		return ndt;
	}
}
}
