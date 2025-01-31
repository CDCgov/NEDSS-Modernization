package test.gov.cdc.nedss.webapp.nbs.action.deduplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.deduplication.vo.DeduplicationPatientMergeVO;
import gov.cdc.nedss.deduplication.vo.MergeConfirmationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.deduplication.MergeCandidateListLoad;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.util.DeDuplicationQueueHelper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.junit.Assert;
import org.junit.Before;
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


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper"})
@PrepareForTest({MergeCandidateListLoad.class,NBSContext.class,PropertyUtil.class,PaginationUtil.class,DeDuplicationQueueHelper.class, ErrorMessageHelper.class,PersonSearchForm.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
@RunWith(Enclosed.class)
public class MergeCandidateListLoad_tests {

	
/*
	 @SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper"})
	 @RunWith(PowerMockRunner.class)
	 @PrepareForTest({MergeCandidateListLoad.class,NBSContext.class,PropertyUtil.class,PaginationUtil.class,DeDuplicationQueueHelper.class, ErrorMessageHelper.class,})
	 @PowerMockRunnerDelegate(Parameterized.class)
	 @PowerMockIgnore("javax.management.*")
	 */
	 @SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.deduplication.MergeCandidateListLoad",
			"gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
		@RunWith(PowerMockRunner.class)
		@PrepareForTest({PropertyUtil.class, MergeCandidateListLoad.class,PersonUtil.class,DeDuplicationQueueHelper.class,CachedDropDownValues.class})
		@PowerMockIgnore("javax.management.*")
	 @PowerMockRunnerDelegate(Parameterized.class)
	 public static class BuildPersonSerachResultVO_test {

		 String code1;
		 String code2;
		 int iteration;
		 
		@Before
		 public void initMocks() throws Exception {
			
			logger=Mockito.mock(LogUtils.class);
			mergeCandidateListLoad =PowerMockito.spy(new MergeCandidateListLoad());
		    cache = Mockito.mock(CachedDropDownValues.class);
		  }
			
			
		 public BuildPersonSerachResultVO_test(String code1, String code2, int it){
			 
			 super();
			 
			 this.code1 = code1;
			 this.code2=code2;
			 this.iteration = it;
		 }
	
		 
			
		 @Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    		  //ContextAction, taskname, expected value, iteration
		    		  {"anotherCode1","anotherCode1",1}
		    		  
		    		 
		    		  });
		   }
		 
		 
		 
		 
	@Mock
	LogUtils logger;
	
	@Mock
	MergeConfirmationVO survivorVO;

	@Mock
	NBSSecurityObj secObj;
	@Mock
	MainSessionCommand msCommand;
	
	@Mock
	MainSessionHolder holder;
	
	
	@Mock
	PersonVO  personVo;
	
	@Mock
	PersonDT personDt;

	@Mock
	CachedDropDownValues cache;
	
	@InjectMocks
	MergeCandidateListLoad mergeCandidateListLoad ;
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void buildPersonSerachResultVO_test() throws Exception {

		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockRequest.setHttpSession(mockHttpSession);
		PowerMockito.mockStatic(DeDuplicationQueueHelper.class);
		PowerMockito.whenNew(CachedDropDownValues.class).withNoArguments().thenReturn(cache);
		TreeMap<String, String> mapReturnByQuery = new TreeMap<String, String>();
		mapReturnByQuery.put(code1, code2);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("23/09/2007");
		long time = date.getTime();
		when(DeDuplicationQueueHelper.getLastBatchProcessTime()).thenReturn(new Timestamp(time));

		Object[] oParams = new Object[] { getDeduplicationPatientMergeVO().get(0), mockRequest };
		PatientSrchResultVO patientSrchResultVO = Whitebox.invokeMethod(mergeCandidateListLoad,
				"buildPersonSerachResultVO", oParams);
		System.out.println("TEST=" + mockRequest.getSession().getAttribute("candidateQueueCreationDate"));
		assertNotNull(mockRequest.getSession().getAttribute("candidateQueueCreationDate"));
		assertEquals("09/23/2007", mockRequest.getSession().getAttribute("candidateQueueCreationDate"));
		assertNotNull(patientSrchResultVO);

	}
	 

	 private ArrayList<Object> getDeduplicationPatientMergeVO() {
		 ArrayList<Object> dePatientMergeList= new ArrayList<Object>();
		 DeduplicationPatientMergeVO deduplicationPatientMergeVO = new DeduplicationPatientMergeVO();
		 PersonVO personVo = new PersonVO();
		 PersonDT personDT = new PersonDT();
		 personDT.setGroupNbr(null);
		 personDT.setGroupTime(null);
		 personDT.setDedupMatchInd("R");
		 personVo.setThePersonDT(personDT);
		 deduplicationPatientMergeVO.setMPR(personVo);
		 dePatientMergeList.add(deduplicationPatientMergeVO);
		 return dePatientMergeList;
	 }
	 
	 
	 
}
	 
	 
	
	
	
	
	
	
	
	
	
	
	
	 @SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.deduplication.MergeCandidateListLoad","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper"})
	 @RunWith(PowerMockRunner.class)
	 @PrepareForTest({MergeCandidateListLoad.class,NBSContext.class,PropertyUtil.class,PaginationUtil.class,DeDuplicationQueueHelper.class, ErrorMessageHelper.class,PersonSearchForm.class})
	 @PowerMockRunnerDelegate(Parameterized.class)
	 @PowerMockIgnore("javax.management.*")
	 public static class Execute_test { 
		 String contextAction;
		 String contextTaskName;
		 String expectedValue;
		 int iteration;
		 String sortStString;
		 
		 
		 
		 @Mock
			PropertyUtil propertyUtil;
		 @Mock
		 SearchResultPersonUtil srpUtil;
		 
		@Mock
		 HttpServletRequest request;
		 
		
		 @Mock
		 HttpSession session;
		 
		 @Mock
		 NBSSecurityObj secObj;
		 
		 @Mock
		 DeDuplicationQueueHelper deDuplicationQueueHelper;
		 
		 @Mock
		 PersonSearchForm pageForm;
		 
		 @Mock
		  ActionMapping mapping;
		 
		 @Mock
		 HttpServletResponse response;
		 
		 @Mock
		 NBSContext nbsContext;
			 
		 @InjectMocks
		 MergeCandidateListLoad mergeCandidateListLoad;

		 
		 
		 @Before
		 public void initMocks() throws Exception {

			 
			// PowerMockito.spy(MergeCandidateListLoad.class);
			 request = Mockito.mock(HttpServletRequest.class);
			 session = Mockito.mock(HttpSession.class);
			 mapping = Mockito.mock(ActionMapping.class);
			// pageForm = Mockito.mock(PersonSearchForm.class);
			 request = Mockito.mock(HttpServletRequest.class);
			 response = Mockito.mock(HttpServletResponse.class);
			 pageForm = Mockito.mock(PersonSearchForm.class);
			 srpUtil = Mockito.mock(SearchResultPersonUtil.class);
			 secObj = Mockito.mock(NBSSecurityObj.class);
			 deDuplicationQueueHelper = Mockito.mock(DeDuplicationQueueHelper.class);
		
			 //propertyUtil
		 }
		 
		 
		 
		 public Execute_test(String contextAction, String contextTaskName, String expectedValue, String sortStString, int it){
			 super();
			 this.contextAction = contextAction;
			 this.contextTaskName=contextTaskName;
			 this.expectedValue=expectedValue;
			 this.iteration = it;
			 this.sortStString = sortStString;
		 }
		 
		 @SuppressWarnings("unchecked")
		@Test
		 public void execute_test() throws Exception{
			 PowerMockito.mockStatic(NBSContext.class);
			 PowerMockito.mockStatic(PropertyUtil.class);
			 PowerMockito.mockStatic(PaginationUtil.class);
			 PowerMockito.mockStatic(ErrorMessageHelper.class);
			    
			 PowerMockito.whenNew(SearchResultPersonUtil.class).withNoArguments().thenReturn(srpUtil);
			// ContentAction:Merge    contextTaskName:MergeCandidateList2
			 when(request.getParameter("ContextAction")).thenReturn(contextAction);
			 when(request.getSession(false)).thenReturn(session);
			 when(request.getSession()).thenReturn(session);
			 when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
			 when((String)request.getAttribute("ContextTaskName")).thenReturn(contextTaskName);
			 when((String)NBSContext.getCurrentTask(any(HttpSession.class))).thenReturn(contextTaskName);
//			 NBSContext.retrieve(session, NBSConstantUtil.DSAttributeMap)
		
			 when((String) request.getAttribute("ContextAction")).thenReturn(contextAction);
			 when((String)request.getParameter(Character.toLowerCase(contextAction.charAt(0)) + contextAction.substring(1))).thenReturn("1234,3456,4567,6789,7890");
			 when((String)request.getParameter("survivor")).thenReturn("");
			 when(PropertyUtil.isMergeCandidateDefaultSurvivorOldest()).thenReturn(true);
			 
			 
			 PowerMockito.mockStatic(DeDuplicationQueueHelper.class);
			 PowerMockito.whenNew(DeDuplicationQueueHelper.class).withNoArguments().thenReturn(deDuplicationQueueHelper);
				
				
				
				
				
				
				
			//	HashMap<Object,Object> dedupAvailableQueue = deDuplicationQueueHelper.getDedupAvailableQueue();

		     HashMap<Object,Object> dedupAvailableQueue = new HashMap<Object, Object>();
		     //Some random values so the queue has elements, and we can go through some workflows.
		     dedupAvailableQueue.put("1","1");
		     dedupAvailableQueue.put("2","2");
		     dedupAvailableQueue.put("3","3");
		     
		     when(deDuplicationQueueHelper.getDedupAvailableQueue()).thenReturn(dedupAvailableQueue);

				//deDuplicationQueueHelper.getDedupTakenQueue().put(key, ((ArrayList<?> )collection).get(0));
		     HashMap<Object,Object> dedupTaken = new HashMap<Object,Object>();
		     when(deDuplicationQueueHelper.getDedupTakenQueue()).thenReturn(dedupTaken);
		     
		     
				//deDuplicationQueueHelper.getDedupSessionQueue().put(session.getId(),
		     HashMap<Object,Object> sessionQueue = new HashMap<Object, Object>();
		     when(deDuplicationQueueHelper.getDedupSessionQueue()).thenReturn(sessionQueue);
				
		     
		     //	Collection<?> collection = getPersonVOsGroupedSimilar(session,Long.valueOf(key.toString()));
		     Collection<DeduplicationPatientMergeVO> collection = new ArrayList<>();
		     DeduplicationPatientMergeVO deduplicationPatientMergeVO = new DeduplicationPatientMergeVO();
		     collection.add(deduplicationPatientMergeVO);
		     mergeCandidateListLoad=PowerMockito.spy(new MergeCandidateListLoad());
		     PowerMockito.doReturn(collection).when(mergeCandidateListLoad, "getPersonVOsGroupedSimilar", any(HttpSession.class), any(Long.class));
		    
		     //	patientSrchResultVO = buildPersonSummaryVOCollection(collection,request);
		     ArrayList<Object> patientSrchResultVO = new ArrayList<Object>();
		   
		     PowerMockito.doReturn(patientSrchResultVO).when(mergeCandidateListLoad, "buildPersonSummaryVOCollection", collection, request);
			  //spersonList = sortPersonListOnPersonLocalId(personList);
		     ArrayList<Object> spersonList = new ArrayList<Object>(); 
		     
		     PowerMockito.doReturn(spersonList).when(mergeCandidateListLoad, "sortPersonListOnPersonLocalId", any(ArrayList.class));
			 

		 
			 propertyUtil = PowerMockito.mock(PropertyUtil.class);
			 PowerMockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
			 when(propertyUtil.getNumberOfRows()).thenReturn(2);
			 
			 NBSContext.store(any(HttpSession.class),any(String.class),any(ArrayList.class));
			 when(mapping.findForward(contextAction)).thenReturn(getActionForwardPath(contextAction,contextTaskName));
			 
			  Mockito.doNothing().when(pageForm).setSearchCriteriaArrayMap(any(HashMap.class));
			  
		//	 PowerMockito.doNothing().when(pageForm, "setSearchCriteriaArrayMap", any(HashMap.class));
			
			  Mockito.doNothing().when(srpUtil).showButton(any(HttpServletRequest.class),any(String.class));
				
			 // PowerMockito.doNothing().when(srpUtil, "showButton", any(HttpServletRequest.class),any(String.class));
			 when(srpUtil.filterPatientSubmit(any(ActionMapping.class),any(PersonSearchForm.class),any(HttpServletRequest.class),any(HttpServletResponse.class))).thenReturn(getActionForwardPath(contextAction,contextTaskName));
			 when(session.getAttribute("attributeMap")).thenReturn(new HashMap<Object,Object>());
			 // session.getAttribute("DQH");//
			 when(session.getAttribute("DQH")).thenReturn(true);
			// PowerMockito.doNothing().when(pageForm, "clearAll");
			  Mockito.doNothing().when(pageForm).clearAll();
				
			 //DSSearchResults
			 when((ArrayList<Object>)NBSContext.retrieve(session,"DSSearchResults")).thenReturn(getDisplayPersonList());
			 
			 HashMap<String, String> attributeMap2 = new HashMap<String, String>();
			 when(NBSContext.retrieve(session, "DSAttributeMap")).thenReturn(attributeMap2);
			 
			 
			 
			 when(srpUtil.filterPatient(any(PersonSearchForm.class), any(HttpServletRequest.class))).thenReturn(getDisplayPersonList());
			 Mockito.doNothing().when(srpUtil).sortPatientLibarary(any(PersonSearchForm.class),any(Collection.class),any(Boolean.class),any(HttpServletRequest.class));
			 when(PaginationUtil.personPaginate(any(PersonSearchForm.class),any(HttpServletRequest.class),any(String.class),any(ActionMapping.class))).thenReturn(getActionForwardPath(contextAction,contextTaskName));
			 when((String)request.getParameter("uid")).thenReturn("8907654");
			 
			 
			 //personSearchForm.getAttributeMap().get("searchCriteria")
			 Map<Object,Object> attributeMap = new HashMap <Object, Object>();
			 attributeMap.put("sortSt",sortStString);
			 HashMap<String, String> searchCriteriaColl = new HashMap<String, String>();
			 searchCriteriaColl.put("sortSt", sortStString);
			 attributeMap.put("searchCriteria",searchCriteriaColl);
			 
			 
			 Mockito.doReturn(attributeMap).when(pageForm).getAttributeMap();
			 //    	ErrorMessageHelper.setErrMsgToRequest(request, "PS209");
			
			 PowerMockito.doNothing().when(ErrorMessageHelper.class, "setErrMsgToRequest",request, "PS209");

			 //	PatientSearchVO psVO = (PatientSearchVO) NBSContext.retrieve(session,"DSSearchCriteria");
		//	 PowerMockito.mockStatic(NBSContext.class);
			 PatientSearchVO psVO = new PatientSearchVO();
			 PowerMockito.doReturn(psVO).when(NBSContext.class, "retrieve",session, "DSSearchCriteria");
			 
			 //	String scString = this.buildSearchCriteriaString(psVO);
			 PowerMockito.doReturn("mySearchCriteria").when(mergeCandidateListLoad, "buildSearchCriteriaString", psVO);
			 
			 
			 Object[] oParams = new Object[] {mapping, pageForm, request,response};
			 
			 ActionForward actionForward=mergeCandidateListLoad.execute(mapping, pageForm, request,response);
			// ActionForward actionForward=Whitebox.invokeMethod(mergeCandidateListLoad, "execute", oParams);
			 String actualResult = actionForward.getPath();
			 Assert.assertEquals(this.expectedValue,actualResult);
			 System.out.println("Iteration: "+iteration+" Method Name: execute, ContextAction:"+contextAction+", ContextTaskName:"+contextTaskName+", Iteration: "+this.iteration+". Expected value:"+this.expectedValue+" Actual value: "+actualResult);
			 System.out.println("RESULTS::::::PASSED");
		 
		 }
		 
		 @Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    		  //ContextAction, taskname, expected value, iteration
		    		  {"Submit","MergeCandidateList1","/MergeCandidateListLoad1.do","Patient ID @ in ascending order",1},
		    		  {"Submit","MergeCandidateList2","/MergeCandidateListLoad2.do","Patient ID @ in ascending order",2},
		    		  {"Submit","MergeCandidateList1","/MergeCandidateListLoad1.do","",3},
		    		  {"Submit","MergeCandidateList2","/MergeCandidateListLoad2.do","",4},
		    		  {"Submit","MergeCandidateList1","/MergeCandidateListLoad1.do",null,5},
		    		  {"Submit","MergeCandidateList2","/MergeCandidateListLoad2.do",null,6},
		    		  {"Submit","MergeCandidateList1","/MergeCandidateListLoad1.do","Patient ID @ in descending order",7},
		    		  {"Submit","MergeCandidateList2","/MergeCandidateListLoad2.do","Patient ID @ in descending order",8},
		    		  {"GlobalMP_SystemIndentified","MergeCandidateList2","/person/jsp/patient_search_results_merge_system_identified.jsp","Patient ID @ in descending order",9},
		    		  {"Skip","MergeCandidateList2","/person/jsp/patient_search_results_merge_system_identified.jsp","Patient ID @ in descending order",10},
		    		  
		    		  
		    		  
		    		  
		    		 
		    		  });
		   }
		 private ArrayList<Object> getDisplayPersonList() {
			 ArrayList<Object> list = new ArrayList<Object>();
			 DisplayPersonList displayPersonList = new DisplayPersonList(1, getPatientSrchResultVOCollectionList(), iteration, iteration);
			 displayPersonList.setTotalCountOfDistinctPersonLists(1);
			 list.add(displayPersonList);
			 return 	 list;
		 }
		 
 
 
		 
		 private ArrayList<Object> getPatientSrchResultVOCollectionList(){
			 ArrayList<Object> personVOs = new ArrayList<Object>();
		
		      PatientSrchResultVO labReport= new PatientSrchResultVO();
		      labReport.setDocumentType("Lab Report");
		      labReport.setReportingFacilityProvider("");
		      labReport.setPersonUID(1111L);
		      labReport.setPersonFirstName("hello");
		      labReport.setPersonLastName("second");
		      labReport.setDescription("");
		      labReport.setLocalId("12345678");
		      labReport.setObservationUid(76543L);
		      labReport.setMPRUid(6L);
		      labReport.setPersonParentUid(10L);
		      labReport.setElectronicInd("0987654");
		      labReport.setProgramAreaCode("56789012");
		      labReport.setRecordStatusCd("sdfghjkl");	
		      labReport.setPersonLocalID("12345678");
		      
		      PatientSrchResultVO labReport1= new PatientSrchResultVO();
		      labReport.setDocumentType("Lab Report");
		      //labReport.setStartDate((Timestamp) new Date());
		      labReport.setReportingFacilityProvider("");
		      labReport.setPersonUID(1111L);
		      labReport.setPersonFirstName("hello");
		      labReport.setPersonLastName("third");
		      labReport.setDescription("");
		      labReport.setLocalId("12345678");
		      labReport.setObservationUid(76543L);
		      labReport.setMPRUid(6L);
		      labReport.setPersonParentUid(10L);
		      labReport.setElectronicInd("0987654");
		      labReport.setProgramAreaCode("56789012");
		      labReport.setRecordStatusCd("sdfghjkl");	
		      labReport1.setPersonLocalID("12345678");
		      
		      PatientSrchResultVO labReport2= new PatientSrchResultVO();
		      labReport2.setDocumentType("Lab Report");
		     // labReport.setStartDate((Timestamp) new Date());
		      labReport2.setReportingFacilityProvider("");
		      labReport2.setPersonUID(1111L);
		      labReport2.setPersonFirstName("hello");
		      labReport2.setPersonLastName("first");
		      labReport2.setDescription("");
		      labReport2.setLocalId("12345678");
		      labReport2.setObservationUid(76543L);
		      labReport2.setMPRUid(6L);
		      labReport2.setPersonParentUid(10L);
		      labReport2.setElectronicInd("0987654");
		      labReport2.setProgramAreaCode("56789012");
		      labReport2.setRecordStatusCd("sdfghjkl");	
		      labReport2.setPersonLocalID("12345678");
		      
		      personVOs.add(labReport);
		      personVOs.add(labReport1);
		      personVOs.add(labReport2);
		      
		      return personVOs;
	}
		  
		 private ActionForward getActionForwardPath(String contextAction,String contextTaskName) {
			 ActionForward actionForward = new ActionForward();
			 String path="";
			 if("MergeCandidateList2".equals(contextTaskName)) {
				 if("Submit".equals(contextAction)) {
					 path="/MergeCandidateListLoad2.do";
				 } else if("NoMerge".equals(contextAction)) {
					 path="/MergeCandidateListLoad1.do";
				 }else if("Cancel".equals(contextAction)) {
					 path="/cancelMerge1.do";
				 }else if("Skip".equals(contextAction)) {
					 path="/person/jsp/patient_search_results_merge_system_identified.jsp";
				 }else if("RemoveFromMerge".equals(contextAction)) {
					 path="/MergeCandidateListLoad1.do";
				 }else if ("GlobalMP_SystemIndentified".equals(contextAction))
					 path="/person/jsp/patient_search_results_merge_system_identified.jsp";
				 
			 } else if("MergeCandidateList1".equals(contextTaskName)) {
				 if("Submit".equals(contextAction)) {
					 path="/MergeCandidateListLoad1.do";
				 }else if("NewSearch".equals(contextAction)) {
					 path="/LoadFindPatient4.do";
				 }else if("RefineSearch".equals(contextAction)) {
					 path="/LoadFindPatient5.do";
				 }else if("Cancel".equals(contextAction)) {
					 path="/cancelMerge1.do";
				 }
			 } else {
				 if("filterPatientSubmit".equals(contextAction)) {
					 path="/filterPatientSubmit.do";
				 }else if("removeFilter".equals(contextAction)) {
					 path="/removeFilterForPatientMerge.do";
				 }else if("sortingByColumn".equals(contextAction)) {
					 path="/sortByColumnPatientMerge.do";
				 }else if("ViewFile".equals(contextAction)) {
					 path="/ViewEventsPopup.do";
				 }
			 }
			 actionForward.setPath(path);
			 return actionForward;
		 }
		 
	 }
			

}
	

