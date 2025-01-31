package test.gov.cdc.nedss.webapp.nbs.action.person.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.verification.Times;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;




@RunWith(Enclosed.class)
public class SearchResultPersonUtil_tests {

	
	
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({SearchResultPersonUtil.class,NEDSSConstants.class,PersonUtil.class})
	@PowerMockIgnore("javax.management.*")
	public static class SetPatientFormat_test{
		

		
		@Mock
		PropertyUtil propertyUtil;
		
		@Mock
		LogUtils logger;
		
		@Mock
		PatientSrchResultVO patientSrchResultVO;
		
		@Mock
		PersonUtil  personUtil;
		
	
		@InjectMocks
		SearchResultPersonUtil searchResultPersonUtil;
	    
		
		public SetPatientFormat_test() {}
		
	
		@Before
		 public void initMocks() throws Exception {
			 propertyUtil = Mockito.mock(PropertyUtil.class);
			 Whitebox.setInternalState(SearchResultPersonUtil.class,"propertyUtil", propertyUtil);
			 Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
			 Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		}
		
		@Test
		 public void  setPatientFormat_test() throws Exception{
			 searchResultPersonUtil = PowerMockito.spy(new SearchResultPersonUtil());
			 PowerMockito.whenNew(PatientSrchResultVO.class).withNoArguments().thenReturn(patientSrchResultVO);
			 PowerMockito.whenNew(PropertyUtil.class).withNoArguments().thenReturn(propertyUtil);
			 PowerMockito.mockStatic(PersonUtil.class);
			 PowerMockito.mockStatic(NEDSSConstants.class);
	         when(patientSrchResultVO.getCurrentSex()).thenReturn("M");
			 when(patientSrchResultVO.getPersonDOB()).thenReturn("01/10/1960");
			 when(patientSrchResultVO.getPersonLocalID()).thenReturn("PSN10063000GA01");
			 when(PersonUtil.displayAgeForPatientResults(any(String.class))).thenReturn("60");
			 when(propertyUtil.getSeedValue()).thenReturn("10000000");
	         when(propertyUtil.getUidSufixCode()).thenReturn("GA01");
	         //when(NEDSSConstants.PERSON).thenReturn("PSN");
		     when(patientSrchResultVO.getPersonFullName()).thenReturn("James Bond");
	         when(patientSrchResultVO.getPersonFullNameNoLink()).thenReturn("James Bond No Links");
	       	 Object[] oParams = new Object[] {patientSrchResultVO};
			 Whitebox.invokeMethod(new SearchResultPersonUtil(), "setPatientFormat", oParams);
			 System.out.println("Full Name::::::"+patientSrchResultVO.getPersonFullName());
	    	 System.out.println("Method Name: setPatientFormat"+"Result::::PASSED");
		 }
		
	
		
	
		
	
					
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * This test case is verified by making sure that if Mode1 is SystemIdentified or ManualMerge and Report Type is not I, the method util.sortObjectByColumnGeneric
	 * is called once with sortMethod = getViewFile (which is the actual change that has been done to this method, using a different sorting column if the page is
	 * System Identified or Manual Merge. We need to have at least one patient in the collection to call the method util.sortObjectByColumnGeneric
	 * @author Fatima.Lopezcalzado
	 *
	 */
	
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({SearchResultPersonUtil.class,NEDSSConstants.class,PersonUtil.class, NedssUtils.class})
	@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
	@PowerMockIgnore("javax.management.*")
	public static class SortPatientLibarary_test{
		
		
		private String iteration;
		private String sortMethod;
		private String sortMethodUpdatedTo;
		private String direction;
		private String reportType;
		private String modeRequestAttribute;
		private String modeRequestParameter;
		
		private int timesCalled;
		
		
		@Mock
		 HttpServletRequest request;

		
		@Mock
		PropertyUtil propertyUtil;
		
		@Mock
		NedssUtils nedssUtil;
		
		@Mock
		LogUtils logger;
		
		@Mock
		PatientSrchResultVO patientSrchResultVO;
		
		@Mock
		PersonUtil  personUtil;
		
		@Mock
		PersonSearchForm psForm;
		
		
		@InjectMocks
		//@Spy
		SearchResultPersonUtil searchResultPersonUtil;// = PowerMockito.spy(new SearchResultPersonUtil());
	    
		

		 public SortPatientLibarary_test(String it, String sortMethod, String sortMethodUpdatedTo, String direction, String reportType, String modeRequestAttribute, String modeRequestParameter, int timesCalled){
			 
			 super();
			 
			 this.iteration = it;
			 this.sortMethod = sortMethod;
			 this.sortMethodUpdatedTo = sortMethodUpdatedTo;
			 this.direction = direction;
			 this.reportType = reportType;
			 this.modeRequestAttribute = modeRequestAttribute;
			 this.modeRequestParameter = modeRequestParameter;
			 this.timesCalled = timesCalled;
			
		 }

		 
	
		 

		 
		   @Parameterized.Parameters
		   public static Collection input() {

			  int it = 0;
			   

		      return Arrays.asList(new Object[][]{

		    		  //it,sortMethod, sortMethodUpdated, direction, report type, Mode1, number of times the method util.sortObjectByColumnGeneric is called
		    		  {"sortPatientLibarary_test"+"_"+it++, "none","getViewFile","", "N", "SystemIdentified","", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getViewFile","", "N", "SystemIdentified","", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getViewFile","", "N", "SystemIdentified","", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getPersonFullName","", "N", "SomethingElse","", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getPersonFullName","", "N", "","", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getPersonFullName","", "N", null,"", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, "anyOtherSortMethod","anyOtherSortMethod","", "N", null,"", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, "getStartDate","getStartDate","", "I", null,"", 1},
		    		  
		    		  {"sortPatientLibarary_test"+"_"+it++, "none","getViewFile","", "N", "","ManualMerge", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getViewFile","", "N", "", "ManualMerge",1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getViewFile","", "N", "","ManualMerge", 1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getPersonFullName","", "N", "", "SomethingElse",1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getPersonFullName","", "N", "", "",1},
		    		  {"sortPatientLibarary_test"+"_"+it++, null,"getPersonFullName","", "N", null, "",1},
		    		  {"sortPatientLibarary_test"+"_"+it++, "anyOtherSortMethod","anyOtherSortMethod","", "N", null, null,1},
		    		  {"sortPatientLibarary_test"+"_"+it++, "getStartDate","getStartDate","", "I", null,null, 1},
		    		  
		    		  
	    			  
			  });
		   }
		 
		 
		   
		   
		@Before
		 public void initMocks() throws Exception {
			propertyUtil = Mockito.mock(PropertyUtil.class);
			nedssUtil = Mockito.mock(NedssUtils.class);
			request = Mockito.mock(HttpServletRequest.class);
			 
			PowerMockito.spy(SearchResultPersonUtil.class);
			
			  
		    psForm = Mockito.mock(PersonSearchForm.class);
		    Whitebox.setInternalState(SearchResultPersonUtil.class,"propertyUtil", propertyUtil);
		    Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		    Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		}
		
		@Test
		 public void sortPatientLibarary_test() throws Exception{
			 
			System.out.println("******************* Starting test case named: sortPatientLibarary_test *******************");
			
			
		
			
			boolean existing = true;
			Collection<Object>  patientList = new ArrayList<Object>();
			
			PatientSrchResultVO vo = new PatientSrchResultVO();
			patientList.add(vo);//This is just an example
					
					
			//String sortMethod = getSortMethod(request, psForm);
			PowerMockito.doReturn(sortMethod).when(SearchResultPersonUtil.class, "getSortMethod",  request, psForm);
			
			
			
			//String direction = getSortDirection(request, psForm);
	
			PowerMockito.doReturn(direction).when(SearchResultPersonUtil.class, "getSortDirection",  request, psForm);
			
			
			
			//String reportType = (String) psForm.getAttributeMap().get("reportType");

			Map<Object, Object> attributeMap = new HashMap<Object, Object>();
			attributeMap.put("reportType", reportType);
			when(psForm.getAttributeMap()).thenReturn(attributeMap);
			
			

			
			when(request.getAttribute("Mode1")).thenReturn(modeRequestAttribute);
			when(request.getParameter("Mode1")).thenReturn(modeRequestParameter);
			
			
			
			
			PowerMockito.whenNew(NedssUtils.class).withNoArguments().thenReturn(nedssUtil);
			Mockito.doNothing().when(nedssUtil).sortObjectByColumnGeneric(sortMethodUpdatedTo,patientList,true);	
			
			
			Whitebox.invokeMethod(new SearchResultPersonUtil(), "sortPatientLibarary", psForm, patientList, existing, request);
			
			
			//This verification will validate the method sortObjectByColumnGeneric has been called once with those specific parameters
			//which tells us, the value for sortMethod has been correctly changed to getViewFile1 as expected.
		
			Mockito.verify(nedssUtil, Mockito.times(timesCalled)).sortObjectByColumnGeneric(sortMethodUpdatedTo,patientList,true);

			 System.out.println("PASSED");
			 System.out.println("******************* End test case named: sortPatientLibarary_test *******************");

		 }				
		
	}
	
	/**
	 * This unit test method was created to verify the changes to unit test the defect ND-27449. The test will pass if:
	 * 
	 * - the page title is the expected one: because is the real change that has been done, making sure the title of the page stays the same from Manual Merge and System identified, regardless of the action taken.
	 * - if the returned page is the expected one (because at the end of the day the method returns and action forward
	 * @author Fatima.Lopezcalzado
	 *
	 */
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({SearchResultPersonUtil.class,NEDSSConstants.class,PersonUtil.class, NedssUtils.class, NBSContext.class})
	@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
	@PowerMockIgnore("javax.management.*")
	public static class FilterPatientSubmit_test{
		
		
		private String iteration;
		private String expectedPath;
		private String pageTitle;
		private String reportType;
		private String custom;

		
		@Mock
		PropertyUtil propertyUtil;
		
		@Mock
		NedssUtils nedssUtil;
		
		@Mock
		LogUtils logger;
		
		@Mock
		PatientSrchResultVO patientSrchResultVO;
		
		@Mock
		PersonUtil  personUtil;
		
		@Mock
		PersonSearchForm psForm;
		
		@Mock
		ActionMapping mapping;
		
		@Mock
		HttpServletResponse response;
		 
		@InjectMocks
		@Spy
		SearchResultPersonUtil searchResultPersonUtil = PowerMockito.spy(new SearchResultPersonUtil());
	    
		

		 public FilterPatientSubmit_test(String it, String expectedPath, String pageTitle, String reportType, String custom){
			 
			 super();
			 
			 this.iteration = it;
			 this.expectedPath = expectedPath;
			 this.pageTitle = pageTitle;
			 this.reportType = reportType;
			 this.custom = custom;//true or false

			
		 }

		 
	
		 

		 
		   @Parameterized.Parameters
		   public static Collection input() {

			  int it = 0;
			   

		      return Arrays.asList(new Object[][]{

		    		//it, expectedPath, pageTitle (page title and expected page title), reportType, custom

		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Merge Candidate List","N", "false"},
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Merge Candidate List","", "false"},
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Merge Candidate List",null, "false"},
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Search Results","N", "false"},
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Search Results","", "false"},
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Search Results",null, "false"},
		    		  
		    		  //report type I, custom (true or false), if true, page title is the queue name, if not, page title is Event Search Results
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Queue name testing","I", "true"},
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Event Search Results","I", "false"},
			    		 
		    		  
		    		  //report type LR, custom (true or false), if true, page title is the queue name, if not, page title is Event Search Results
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Queue name testing","LR", "true"},
		    		  {"filterPatientSubmit_test"+"_"+it++,  "/person/jsp/patient_search_results_merge.jsp","Event Search Results","LR", "false"},
			    		
		    		  
	    			  
			  });
		   }
		 
		 
		   
		   
		@Before
		 public void initMocks() throws Exception {
			propertyUtil = Mockito.mock(PropertyUtil.class);
			nedssUtil = Mockito.mock(NedssUtils.class);
			//request = Mockito.mock(MockHttpServletRequest.class);
			 
			PowerMockito.spy(SearchResultPersonUtil.class);
			
			  
		    psForm = Mockito.mock(PersonSearchForm.class);
		    Whitebox.setInternalState(SearchResultPersonUtil.class,"propertyUtil", propertyUtil);
		    Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		    Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		}
		
		
		
		 
	 
		@Test
		 public void filterPatientSubmit_test() throws Exception{
		
			
			System.out.println("******************* Starting test case named: filterPatientSubmit_test *******************");
			
			MockHttpServletRequest request = new MockHttpServletRequest();
			
			String queueName = pageTitle;
			PowerMockito.mockStatic(NBSContext.class);
			NBSContext.store(any(HttpSession.class), any(String.class), any(ArrayList.class));
			
			//(String)psForm.getAttributeMap().get("reportType");
			 Map<Object,Object> attributeMap = new HashMap<Object, Object>();
			 attributeMap.put("reportType", reportType);
			 attributeMap.put("custom", custom);
			 attributeMap.put("queueName", queueName);
			 
			 Mockito.doReturn(attributeMap).when(psForm).getAttributeMap();
				
			 
			
			
			
			//	personVoColl = this.filterPatient(psForm, request);
			 Collection<Object> personVoColl = new ArrayList<Object>();
		
			//PowerMockito.doReturn(personVoColl).when(searchResultPersonUtil, "filterPatient",  psForm, request);
		//	when(searchResultPersonUtil.filterPatient(psForm, request)).thenReturn(personVoColl);
			PowerMockito.spy(SearchResultPersonUtil.class);
			Mockito.doReturn(personVoColl).when(searchResultPersonUtil).filterPatient(psForm, request);
			
			//personVoColl = this.filterInvs(psForm, request);
			Mockito.doReturn(personVoColl).when(searchResultPersonUtil).filterInvs(psForm, request);
			
			//	personVoColl = this.filterObservations(psForm, request);
			Mockito.doReturn(personVoColl).when(searchResultPersonUtil).filterObservations(psForm, request);
			
			//this.sortPatientLibarary(psForm, personVoColl, true, request);
			Mockito.doNothing().when(searchResultPersonUtil).sortPatientLibarary(psForm,personVoColl, true, request);

			
			//PaginationUtil.personPaginate(psForm, request, "searchResultLoad",mapping);
			
			ActionForward actionForward = new ActionForward();
		
			actionForward.setPath(expectedPath);
			
			 PowerMockito.mockStatic(PaginationUtil.class);
			when(PaginationUtil.personPaginate(any(PersonSearchForm.class),any(HttpServletRequest.class),any(String.class),any(ActionMapping.class))).thenReturn(actionForward);
				
			
		
			Mockito.doReturn(pageTitle).when(psForm).getPageTitle();
			
			ActionForward actual = Whitebox.invokeMethod(searchResultPersonUtil, "filterPatientSubmit", mapping, psForm, request, response);
			

			String actualPath = actual.getPath();
			
			//Expected path
		
			//Expected Page Title:
			String actualPageTitle = (String)request.getAttribute("PageTitle");
			String expectedPageTitle = pageTitle;
			
			System.out.println("Iteration: #"+iteration+"\n");
			System.out.println("Expected path: #"+expectedPath+"\n");
			System.out.println("Actual path: #"+actualPath+"\n");
			System.out.println("Expected Page title: #"+expectedPageTitle+"\n");
			System.out.println("Actual Page title: #"+actualPageTitle+"\n");
			

			Assert.assertEquals(expectedPath, actualPath);
			Assert.assertEquals(expectedPageTitle, actualPageTitle);
			
			
			 System.out.println("PASSED");
			 System.out.println("******************* End test case named: filterPatientSubmit_test *******************");

		 }				
		
		
		
		
	}

}

	 