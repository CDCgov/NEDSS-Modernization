package test.gov.cdc.nedss.webapp.nbs.action.deduplication;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
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

import gov.cdc.nedss.deduplication.vo.DeduplicationPatientMergeVO;
import gov.cdc.nedss.deduplication.vo.MergeConfirmationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.deduplication.MergeCandidateListSubmit;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.util.DeDuplicationQueueHelper;

@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class MergeCandidateListSubmit_tests {
	
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({MergeCandidateListSubmit.class})
	@PowerMockIgnore("javax.management.*")
	public static class GetMaxGroupNumber_test { 
		@Mock
		LogUtils logger;
	
		@InjectMocks
		@Spy 
		MergeCandidateListSubmit mergeCandidateListSubmit=  Mockito.spy(MergeCandidateListSubmit.class);
	
		@Before
		public void initMocks() throws Exception {
			logger=Mockito.mock(LogUtils.class);
		}
	 
		@Test
		public void getMaxGroupNumber_test() throws Exception {
			int maxNumber=4;
			int actualResult=Whitebox.invokeMethod(mergeCandidateListSubmit, "getMaxGroupNumber", new MergeCandidateListSubmit_tests().getDeduplicationAvailableQueue());
			Assert.assertEquals(maxNumber,actualResult);
			System.out.println("Class Name:MergeCandidateListSubmit.java, Method Name: getMaxGroupNumber,  Iteration: "+1+". Expected value: "+maxNumber+" Actual value: "+actualResult+", RESULT:::::PASSED");
	    }
	}
	
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({MergeCandidateListSubmit.class,PersonUtil.class})
	@PowerMockIgnore("javax.management.*")
	public static class CreateConfirmationMessage_test { 
		@Mock
		PropertyUtil propertyUtil;
	
		@Mock
		LogUtils logger;
	
		@InjectMocks
		@Spy 
		MergeCandidateListSubmit mergeCandidateListSubmit=  Mockito.spy(MergeCandidateListSubmit.class);
	
	
		@Before
		public void initMocks() throws Exception {
			logger=Mockito.mock(LogUtils.class);
	     }
		
	 @Test
	 public void createConfirmationMessage_test() throws Exception{
		 String expectedMessage = "The following patients have been successfully merged into 10030023 FirstName1  LastName1, legal<ul><li>10030012      FirstName2            LastName2, legal</li></ul>"; 
		 PowerMockito.mockStatic(PersonUtil.class);
		 when(PersonUtil.getDisplayLocalID("OBS10030023GA01")).thenReturn("10030023");
		 when(PersonUtil.getDisplayLocalID("OBS10030012GA01")).thenReturn("10030012");
		 String actualMessage=Whitebox.invokeMethod(mergeCandidateListSubmit,"createConfirmationMessage",new MergeCandidateListSubmit_tests().getMergeConfirmationV());
		 Assert.assertEquals(expectedMessage,actualMessage);
		 System.out.println("Class Name:MergeCandidateListSubmit.java, Method Name: createConfirmationMessage,  Iteration: 1, RESULT:::::PASSED");

	 }
  }
	 
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({MergeCandidateListSubmit.class,DeDuplicationQueueHelper.class})
	@PowerMockIgnore("javax.management.*")
	public static class RemoveFromMergeForDedup_test { 
		
		@Mock
		PropertyUtil propertyUtil;
	
		@Mock
		LogUtils logger;
		
		@Mock
		NBSSecurityObj secObj;
	
		@Mock
		HttpSession session;
	
		@Mock
		HttpServletRequest request;
	
		
		@Mock
		MainSessionCommand msCommand;
		
		@Mock
		MainSessionHolder holder;
	
		
		@InjectMocks
		@Spy 
		MergeCandidateListSubmit mergeCandidateListSubmit=  Mockito.spy(MergeCandidateListSubmit.class);
	
	
		@Before
		public void initMocks() throws Exception {
			logger=Mockito.mock(LogUtils.class);
		}
	
		//@Ignore("not yet ready , Please ignore.")
		@Test
		public void removeFromMergeForDedup_test() throws Exception{
			BasicConfigurator.configure(); //remove log4j configure messages
			PowerMockito.mockStatic(DeDuplicationQueueHelper.class);
			msCommand = PowerMockito.mock(MainSessionCommand.class);
			PowerMockito.whenNew(MainSessionHolder.class).withNoArguments().thenReturn(holder);
			when(request.getSession()).thenReturn(session);
			ArrayList<Object> dedupPatientMergeVO=new MergeCandidateListSubmit_tests().getDeduplicationPatientMergeVO();
			when(DeDuplicationQueueHelper.getDedupTakenQueue()).thenReturn(new MergeCandidateListSubmit_tests().getDeduplicationAvailableQueue());
			PowerMockito.doNothing().when(mergeCandidateListSubmit, "updateDedupIndForRemovedPatientRecord", any(HashMap.class),any(String.class));
			when(holder.getMainSessionCommand(any(HttpSession.class))).thenReturn(msCommand);
			Mockito.doReturn(dedupPatientMergeVO).when(msCommand).processRequest(any(String.class),any(String.class),any(Object[].class));
			Object[] oParams = new Object[] {"01236689", session,secObj};
			Whitebox.invokeMethod(mergeCandidateListSubmit, "removeFromMergeForDedup", oParams);
			DeduplicationPatientMergeVO dmvo= (DeduplicationPatientMergeVO)dedupPatientMergeVO.get(0);
			Assert.assertEquals("R",dmvo.getMPR().getThePersonDT().getDedupMatchInd());
			System.out.println("Class Name:MergeCandidateListSubmit.java, Method Name: removeFromMergeForDedup,  Iteration: "+1+". Expected value: R  Actual value: "+dmvo.getMPR().getThePersonDT().getDedupMatchInd()+", RESULT:::::PASSED");

	 }
	 
	}
	
	 

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({MergeCandidateListSubmit.class,PersonUtil.class,DeDuplicationQueueHelper.class})
	@PowerMockIgnore("javax.management.*")
	public static class UpdateDedupIndForRemovedPatientRecord_test { 
		
		@Mock
		PropertyUtil propertyUtil;
	
		@Mock
		LogUtils logger;
		
		@Mock
		PersonVO  personVo;
	
		@Mock
		PersonDT personDt;
	
		@InjectMocks
		@Spy 
		MergeCandidateListSubmit mergeCandidateListSubmit=  Mockito.spy(MergeCandidateListSubmit.class);
	
	
		@Before
		public void initMocks() throws Exception {
			logger=Mockito.mock(LogUtils.class);
		}
 
		//@Ignore("not yet ready , Please ignore.")
		@Test
		public void updateDedupIndForRemovedPatientRecord_test() throws Exception{
			PowerMockito.whenNew(PersonVO.class).withNoArguments().thenReturn(personVo);
			PowerMockito.whenNew(PersonDT.class).withNoArguments().thenReturn(personDt);
			when(personVo.getThePersonDT()).thenReturn(personDt);
			when(personDt.getPersonUid()).thenReturn(2340L);
			Whitebox.invokeMethod(mergeCandidateListSubmit, "updateDedupIndForRemovedPatientRecord", new MergeCandidateListSubmit_tests().getPVOSPerGroupNumber(2), "2340");
			System.out.println("Class Name:MergeCandidateListSubmit.java,Method Name: updateDedupIndForRemovedPatientRecord,  Iteration: 1, RESULT:::::PASSED");

	 }
	 
	}
	
	
	@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({MergeCandidateListSubmit.class})
	@PowerMockIgnore("javax.management.*")
	public static class GetPersonVOs_test { 
		
		@Mock
		PropertyUtil propertyUtil;
	
		@Mock
		LogUtils logger;
	
		@Mock
		MergeConfirmationVO survivorVO;

		@Mock
		NBSSecurityObj secObj;
	
		@Mock
		HttpSession session;
	
		@Mock
		HttpServletRequest request;
	
		@Mock
		MainSessionCommand msCommand;
	
		@Mock
		MainSessionHolder holder;
		
		@InjectMocks
		@Spy 
		MergeCandidateListSubmit mergeCandidateListSubmit=  Mockito.spy(MergeCandidateListSubmit.class);
	
	
		@Before
		public void initMocks() throws Exception {
			logger=Mockito.mock(LogUtils.class);
		}
		//@Ignore("not yet ready , Please ignore.")
		@Test
		public void getPersonVOs_test() throws Exception {
			when((String)request.getParameter(any(String.class))).thenReturn("1234,3456,4567,6789,7890");
			PowerMockito.doReturn(new MergeCandidateListSubmit_tests().getPersonVOCollection()).when(mergeCandidateListSubmit, "sendProxyToGetPersons", any(Collection.class),any(HttpSession.class),any(NBSSecurityObj.class));
			Collection<PersonVO> personCollection= Whitebox.invokeMethod(mergeCandidateListSubmit, "getPersonVOs", request,request.getSession(),secObj);
			Assert.assertEquals(2,personCollection.size());
			System.out.println("Class Name:MergeCandidateListSubmit.java, Method Name: getPersonVOs,  Iteration: 1, Expected value:2  Actual value: "+personCollection.size()+", RESULT::::PASSED");
			
	 }
	 
	}
	 
 
	 @SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
	 @RunWith(PowerMockRunner.class)
	 @PrepareForTest({MergeCandidateListSubmit.class,DeDuplicationQueueHelper.class})
	 @PowerMockRunnerDelegate(Parameterized.class)
	 @PowerMockIgnore("javax.management.*")
	 public static class ProcessContextActionRemoveFromMerge_test { 
		 int iterator;
         int actualValue;
         String message;
         
		 public ProcessContextActionRemoveFromMerge_test(int actual, String message, int iterator) {
			 this.actualValue=actual;
			 this.iterator=iterator;
			 this.message=message;
		 }
		 @Mock
		 HttpServletRequest request;
		 
		 @Mock
		 HttpSession session;
		 
		 @InjectMocks
		 @Spy 
		 MergeCandidateListSubmit mergeCandidateListSubmit=  Mockito.spy(MergeCandidateListSubmit.class);
	
		 //@Ignore("not yet ready , Please ignore.")
		 @Test
		 public void processContextActionRemoveFromMerge_test() throws Exception{
			 request= Mockito.mock(HttpServletRequest.class);
			 session= Mockito.mock(HttpSession.class);
			 PowerMockito.mockStatic(DeDuplicationQueueHelper.class);
			 when(request.getSession()).thenReturn(session);
			 when(DeDuplicationQueueHelper.getDedupAvailableQueue()).thenReturn(new HashMap<Object,Object>());
			 when(DeDuplicationQueueHelper.getDedupAvailableQueueSkipped()).thenReturn(new MergeCandidateListSubmit_tests().getDeduplicationAvailableQueueSkipped());
			 when(DeDuplicationQueueHelper.getDedupTakenQueue()).thenReturn(new MergeCandidateListSubmit_tests().getPVOSPerGroupNumber(this.actualValue));
			 Object[] oParams1 = new Object[] {this.actualValue, session};
			 Whitebox.invokeMethod(mergeCandidateListSubmit, "processContextActionRemoveFromMerge", oParams1);
			 System.out.println("Class Name:MergeCandidateListSubmit.java, Method Name: processContextActionRemoveFromMerg,  Iteration: "+this.iterator+"  "+this.message+"RESULT::::PASSED");
		 }
		 
		 
		 @SuppressWarnings("rawtypes")
		@Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    	  {1,"Single Record",1},
		    	  {2,"Multiple Records",2}
		      });
		      }
		 
		
			 

	 }
	 
	 
	 
	 

	 
	 @SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
	 @RunWith(PowerMockRunner.class)
	 @PrepareForTest({MergeCandidateListSubmit.class,DeDuplicationQueueHelper.class})
	 @PowerMockRunnerDelegate(Parameterized.class)
	 @PowerMockIgnore("javax.management.*")
	 public static class ProcessContextActionCancel_test { 
		 int iterator;
         int groupId;
         int expectedFinalAvailableGroups;
	     String groupNumberSkipped;
	     String groupNumberTaken;
	     String groupNumberAvailable;
 
         
		 public ProcessContextActionCancel_test(int groupId, int expectedFinalAvailableGroups, String groupNumberSkipped, String groupNumberTaken, String groupNumberAvailable, int iterator) {
			 this.groupId=groupId;
			 this.iterator=iterator;
			 this.expectedFinalAvailableGroups = expectedFinalAvailableGroups;
		     this.groupNumberSkipped=groupNumberSkipped;
		     this. groupNumberTaken=groupNumberTaken;
		     this. groupNumberAvailable=groupNumberAvailable;
		       
		       
		 }
		 @Mock
		 HttpServletRequest request;
		 
		 @Mock
		 HttpSession session;
		 
		 @InjectMocks
		 @Spy 
		 MergeCandidateListSubmit mergeCandidateListSubmit=  Mockito.spy(MergeCandidateListSubmit.class);
	

		 @Test
		 public void processContextActionCancel_test() throws Exception{
			 
			 int numberOfSkipped = 0;
			 int numberOfTaken = 0;
			 int numberOfAvailable = 0;
			 
			  HashMap<Object,Object> deduplicationAvailableQueue = new HashMap<Object, Object>();
	          HashMap<Object,Object> dedupTakenQueue = new HashMap<Object, Object>();
	          HashMap<Object,Object> dedupSkippedQueue = new HashMap<Object, Object>();
	          DeduplicationPatientMergeVO dedupPatientMergeVO =  new DeduplicationPatientMergeVO();
	          Collection<Object> collection = new ArrayList<Object>();
	          
	       
	          if(groupNumberSkipped!=null && !groupNumberSkipped.isEmpty()){
	        		  
		         String[] skipped =  groupNumberSkipped.split("\\|");
		         if(skipped!=null && skipped.length>0)
		         for(int i=0; i<skipped.length; i++){
		        	 numberOfSkipped++;
		          dedupSkippedQueue.put(Integer.parseInt(skipped[i]), new Timestamp(System.currentTimeMillis()));
		          
		         }
	          }
	          
	          
	          if(groupNumberTaken!=null && !groupNumberTaken.isEmpty()){
	         String[] taken = groupNumberTaken.split("\\|");
	         
	         if(taken!=null && taken.length>0)
	         for(int i=0; i<taken.length; i++){
	        	 numberOfTaken++;
	          PersonVO personVO = new PersonVO();
	          PersonDT personDT = new PersonDT();
	          personDT.setGroupTime(new Timestamp(System.currentTimeMillis()));
	          personVO.setThePersonDT(personDT);
	          dedupPatientMergeVO.setMPR(personVO);
	          collection.add(dedupPatientMergeVO);
	          dedupTakenQueue.put(Integer.parseInt(taken[i]), collection);
	          
	         }
	         
	          }
	         
	          
	          if(groupNumberAvailable!=null && !groupNumberAvailable.isEmpty()){
		         String[] available = groupNumberAvailable.split("\\|");
		         
		         if(available!=null && available.length>0)
		         for(int i=0; i<available.length; i++){
		        	 numberOfAvailable++;
		          deduplicationAvailableQueue.put(Integer.parseInt(available[i]), new Timestamp(System.currentTimeMillis()));
		          
		         }
	          }
	          
	            
			 request= Mockito.mock(HttpServletRequest.class);
			 session= Mockito.mock(HttpSession.class);
			 PowerMockito.mockStatic(DeDuplicationQueueHelper.class);
			 when(request.getSession()).thenReturn(session);
			 when(DeDuplicationQueueHelper.getDedupAvailableQueue()).thenReturn(deduplicationAvailableQueue);
			 when(DeDuplicationQueueHelper.getDedupAvailableQueueSkipped()).thenReturn(dedupSkippedQueue);
			 when(DeDuplicationQueueHelper.getDedupTakenQueue()).thenReturn(dedupTakenQueue);
			 Object[] oParams1 = new Object[] {this.groupId, session};
			 Whitebox.invokeMethod(mergeCandidateListSubmit, "processContextActionCancel", oParams1);
			 
			 int actualAvailableGroups = deduplicationAvailableQueue.size();
			 
			 Assert.assertEquals(expectedFinalAvailableGroups,actualAvailableGroups);

			 
				System.out.println("Iteration: #"+iterator+"\nNumber of skipped groups:"+numberOfSkipped+"\nNumber of taken groups:"+numberOfTaken+"\nNumber of available groups:"+numberOfAvailable+"\nExpected number of groups in the available queue after executing the method: "+expectedFinalAvailableGroups+"\nActual number of groups in the available queue after executing the method: : "+actualAvailableGroups);
				junit.framework.Assert.assertEquals(expectedFinalAvailableGroups, actualAvailableGroups);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: processContextActionCancel_test *******************");
				
				
		 
		 }
		 
		 
		 @SuppressWarnings("rawtypes")
		@Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
  
	    		//Group id
	    		//expected final number of aailable groups
	    		//skipped
	    		//taken
	    		//availble
	    		//iteration
		    		  
		    	  {2,4,"1","2","3|4",1},
		    	  {3,7,"1|2","3","4|5|6|7",2},
		    	  {1,3,"","1","2|3",3},
		    	  {8,8,"1|2|3|4|5|6|7","8","",4},
		    	  {2,3,"1","2","3",5},
		    	  
		      });
		      }
		 
		
			 

	 }
	 
	 
	 
	 @SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
	 @RunWith(PowerMockRunner.class)
	 @PrepareForTest({MergeCandidateListSubmit.class,NBSContext.class,PropertyUtil.class,PaginationUtil.class})
	 @PowerMockRunnerDelegate(Parameterized.class)
	 @PowerMockIgnore("javax.management.*")
	 public static class Execute_test { 
		 String contextAction;
		 String contextTaskName;
		 String expectedValue;
		 int iteration;
		 
		 public Execute_test(String contextAction, String contextTaskName, String expectedValue, int it){
			 this.contextAction = contextAction;
			 this.contextTaskName=contextTaskName;
			 this.expectedValue=expectedValue;
			 this.iteration = it;
		 }
		 
		 @Mock
		 SearchResultPersonUtil srpUtil;
		 
		 @Mock
		 HttpServletRequest request;
		 
		 @Mock
		 HttpSession session;
		 
		 @Mock
		 NBSSecurityObj secObj;
		 
		 @Mock
		 PersonSearchForm pageForm;
		 
		 @Mock
		  ActionMapping mapping;
		 
		 @Mock
		 HttpServletResponse response;
		 
		 @Mock
		 NBSContext nbsContext;
			 
		 @InjectMocks
		 MergeCandidateListSubmit mergeCandidateListSubmit;
		
			
		@Before
		 public void initMocks() throws Exception {
			 request= Mockito.mock(HttpServletRequest.class);
			 session= Mockito.mock(HttpSession.class);
			 mapping= Mockito.mock(ActionMapping.class);
			}
		 
			
		 @SuppressWarnings("unchecked")
		@Test
		 public void execute_test() throws Exception{
			 mergeCandidateListSubmit=PowerMockito.spy(new MergeCandidateListSubmit());
			 pageForm=PowerMockito.mock(PersonSearchForm.class);
			 srpUtil=PowerMockito.mock(SearchResultPersonUtil.class);
			 PowerMockito.mockStatic(NBSContext.class);
			 PowerMockito.mockStatic(PropertyUtil.class);
			 PowerMockito.mockStatic(PaginationUtil.class);
			 PowerMockito.whenNew(SearchResultPersonUtil.class).withNoArguments().thenReturn(srpUtil);
			// ContentAction:Merge    contextTaskName:MergeCandidateList2
			 when(request.getParameter("ContextAction")).thenReturn(contextAction);
			 when(request.getSession(false)).thenReturn(session);
			 when(request.getSession()).thenReturn(session);
			 when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
			 when((String)request.getAttribute("ContextTaskName")).thenReturn(contextTaskName);
			 when((String)NBSContext.getCurrentTask(any(HttpSession.class))).thenReturn(contextTaskName);
			 when((String) request.getAttribute("ContextAction")).thenReturn(contextAction);
			 PowerMockito.doReturn(new Integer(2)).when(mergeCandidateListSubmit, "getGroupNbr", any(HttpSession.class));
			 when((String)request.getParameter(Character.toLowerCase(contextAction.charAt(0)) + contextAction.substring(1))).thenReturn("1234,3456,4567,6789,7890");
			 PowerMockito.doReturn(getPersonVOCollectionList()).when(mergeCandidateListSubmit, "getSimilarGroupToMerge", any(Integer.class),any(String[].class));
			 when((String)request.getParameter("survivor")).thenReturn("");
			 when(PropertyUtil.isMergeCandidateDefaultSurvivorOldest()).thenReturn(true);
			 PowerMockito.doReturn("23456").when(mergeCandidateListSubmit, "findOldestPatientId", any(ArrayList.class));
			 PowerMockito.doReturn(getPersonVOCollectionList()).when(mergeCandidateListSubmit, "sendProxyToMeregPersons", any(ArrayList.class), any(String.class),any(HttpSession.class),any(NBSSecurityObj.class));
			 PowerMockito.doReturn(getPersonVOCollectionList()).when(mergeCandidateListSubmit, "getNonSelectedGroupMembers", any(Integer.class), any(ArrayList.class));
			 PowerMockito.doNothing().when(mergeCandidateListSubmit, "updateNonSelectedGroups", any(ArrayList.class),any(HttpSession.class),any(NBSSecurityObj.class));
			 NBSContext.store(any(HttpSession.class),any(String.class),any(ArrayList.class));
			 when(mergeCandidateListSubmit.createConfirmationMessage(any(ArrayList.class))).thenReturn("Success");
			 PowerMockito.doNothing().when(mergeCandidateListSubmit, "processPostMergeOrNoMergeForSystemIdentified", any(Integer.class), any(HttpSession.class),any(String.class),any(NBSSecurityObj.class));
			 when(mapping.findForward(contextAction)).thenReturn(getActionForwardPath(contextAction,contextTaskName));
			// ContentAction:NoMerge    contextTaskName:MergeCandidateList2
			 PowerMockito.doNothing().when(mergeCandidateListSubmit, "processPostMergeOrNoMergeForSystemIdentified", any(Integer.class), any(HttpSession.class),any(String.class),any(NBSSecurityObj.class));
			// ContentAction:Cancel    contextTaskName:MergeCandidateList2
			 PowerMockito.doNothing().when(mergeCandidateListSubmit, "processContextActionCancel", any(Integer.class), any(HttpSession.class));
			// ContentAction:Skip    contextTaskName:MergeCandidateList2
			 PowerMockito.doNothing().when(mergeCandidateListSubmit, "processContextActionSkip", any(Integer.class), any(HttpSession.class));
			// ContentAction:RemoveFromMerge    contextTaskName:MergeCandidateList2
			 PowerMockito.doNothing().when(mergeCandidateListSubmit, "removeFromMergeForDedup", any(String.class), any(HttpSession.class),any(NBSSecurityObj.class));
			 PowerMockito.doNothing().when(mergeCandidateListSubmit, "processContextActionRemoveFromMerge", any(Integer.class), any(HttpSession.class));
			//ContentAction:Merge    contextTaskName:MergeCandidateList1
			 PowerMockito.doReturn(getPersonVOCollectionList()).when(mergeCandidateListSubmit, "getPersonVOs", any(HttpServletRequest.class),any(HttpSession.class),any(NBSSecurityObj.class));
			//ContentAction:NewSearch    contextTaskName:MergeCandidateList1 -- Nothing to Add
		    //ContentAction:RefineSearch    contextTaskName:MergeCandidateList1
			 PowerMockito.doNothing().when(pageForm, "setSearchCriteriaArrayMap", any(HashMap.class));
			//ContentAction:Cancel    contextTaskName:MergeCandidateList1 --Nothing to Add
			//ContentAction:filterPatientSubmit 
			 PowerMockito.doNothing().when(srpUtil, "showButton", any(HttpServletRequest.class),any(String.class));
			 when(srpUtil.filterPatientSubmit(any(ActionMapping.class),any(PersonSearchForm.class),any(HttpServletRequest.class),any(HttpServletResponse.class))).thenReturn(getActionForwardPath(contextAction,contextTaskName));
			//ContentAction:removeFilter
			 when(session.getAttribute("attributeMap")).thenReturn(new HashMap<Object,Object>());
			 PowerMockito.doNothing().when(pageForm, "clearAll");
			//ContentAction:sortByColumn
			 when((ArrayList<Object>)NBSContext.retrieve(any(HttpSession.class),any(String.class))).thenReturn(getPersonVOCollectionList());
			 when(srpUtil.filterPatient(any(PersonSearchForm.class), any(HttpServletRequest.class))).thenReturn(getPersonVOCollectionList());
			 PowerMockito.doNothing().when(srpUtil, "sortPatientLibarary", any(PersonSearchForm.class),any(Object[].class),any(Boolean.class),any(HttpServletRequest.class));
			 when(PaginationUtil.personPaginate(any(PersonSearchForm.class),any(HttpServletRequest.class),any(String.class),any(ActionMapping.class))).thenReturn(getActionForwardPath(contextAction,contextTaskName));
			 //Context Action: ViewFile
			 when((String)request.getParameter("uid")).thenReturn("8907654");
			 Object[] oParams = new Object[] {mapping, pageForm, request,response};
			 ActionForward actionForward=Whitebox.invokeMethod(mergeCandidateListSubmit, "execute", oParams);
			 String actualResult = actionForward.getPath();
			 Assert.assertEquals(this.expectedValue,actualResult);
			 System.out.println("Method Name: execute, ContextAction:"+contextAction+", ContextTaskName:"+contextTaskName+", Iteration: "+this.iteration+". Expected value:"+this.expectedValue+" Actual value: "+actualResult);
			 System.out.println("RESULTS::::::PASSED");
		 
		 }
		 
		 @SuppressWarnings("rawtypes")
		@Parameterized.Parameters
		   public static Collection input() {
		      return Arrays.asList(new Object[][]{
		    		  //ContextAction, taskname, expected value, iteration
		    		  {"Merge","MergeCandidateList2","/MergeCandidateListLoad1.do",1},
		    		  {"NoMerge","MergeCandidateList2","/MergeCandidateListLoad1.do",2},
		    		  {"Cancel","MergeCandidateList2","/cancelMerge1.do",3},
		    		  {"Skip","MergeCandidateList2","/MergeCandidateListLoad1.do",4},
		    		  {"RemoveFromMerge","MergeCandidateList2","/MergeCandidateListLoad1.do",5},
		    		  {"Merge","MergeCandidateList1","/LoadFindPatient4.do",6},
		    		  {"NewSearch","MergeCandidateList1","/LoadFindPatient4.do",7},
		    		  {"RefineSearch","MergeCandidateList1","/LoadFindPatient5.do",8},
		    		  {"Cancel","MergeCandidateList1","/cancelMerge1.do",9}, 
		    		  {"filterPatientSubmit","","/filterPatientSubmit.do",10}, 
		    		  {"removeFilter","","/removeFilterForPatientMerge.do",11},
		    		  {"sortingByColumn","","/sortByColumnPatientMerge.do",12},
		    		  {"ViewFile","","/ViewEventsPopup.do",13},
		    		  {"removeFilter","MergeCandidateList1","/person/jsp/patient_search_results_merge.jsp",14},
		    		  {"removeFilter","MergeCandidateList2","/person/jsp/patient_search_results_merge.jsp",15},
		    		  });
		   }
		 
		 
		 private ArrayList<Object> getPersonVOCollectionList() {
			 ArrayList<Object> personVOCollection= new ArrayList<Object>();
			 PersonVO personVo = new PersonVO();
			 PersonDT personDT = new PersonDT();
			 personDT.setGroupNbr(2);
			 personDT.setGroupTime(new Timestamp(System.currentTimeMillis()));
			 personDT.setDedupMatchInd("X");
			 personDT.setPersonUid(new Long(2340));
			 personDT.setRecordStatusCd("ACTIVE");
			 personVo.setThePersonDT(personDT);
			 personVOCollection.add(personVo);
			 PersonVO personVo1 = new PersonVO();
			 PersonDT personDT1 = new PersonDT();
			 personDT1.setGroupNbr(3);
			 personDT1.setGroupTime(new Timestamp(System.currentTimeMillis()));
			 personDT1.setDedupMatchInd("Y");
			 personDT1.setPersonUid(new Long(8945));
			 personDT1.setRecordStatusCd("ACTIVE");
			 personVo1.setThePersonDT(personDT1);
			 personVOCollection.add(personVo1);
			 return personVOCollection;
		 }
		 
		 private ActionForward getActionForwardPath(String contextAction,String contextTaskName) {
			 ActionForward actionForward = new ActionForward();
			 String path="";
			 if("MergeCandidateList2".equals(contextTaskName)) {
				 if("Merge".equals(contextAction)) {
					 path="/MergeCandidateListLoad1.do";
				 } else if("NoMerge".equals(contextAction)) {
					 path="/MergeCandidateListLoad1.do";
				 }else if("Cancel".equals(contextAction)) {
					 path="/cancelMerge1.do";
				 }else if("Skip".equals(contextAction)) {
					 path="/MergeCandidateListLoad1.do";
				 }else if("RemoveFromMerge".equals(contextAction)) {
					 path="/MergeCandidateListLoad1.do";
				 }
				 else if("removeFilter".equals(contextAction)) {
					 path="/person/jsp/patient_search_results_merge.jsp";
				 }
				 
			 } else if("MergeCandidateList1".equals(contextTaskName)) {
				 if("Merge".equals(contextAction)) {
					 path="/LoadFindPatient4.do";
				 }else if("NewSearch".equals(contextAction)) {
					 path="/LoadFindPatient4.do";
				 }else if("RefineSearch".equals(contextAction)) {
					 path="/LoadFindPatient5.do";
				 }else if("Cancel".equals(contextAction)) {
					 path="/cancelMerge1.do";
				 } else if("removeFilter".equals(contextAction)) {
					 path="/person/jsp/patient_search_results_merge.jsp";
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
	 
	 private HashMap<Object,Object> getDeduplicationAvailableQueueSkipped() {
		 HashMap<Object,Object> deduplicationAQ= new HashMap<Object,Object>();
		 deduplicationAQ.put(new Integer(5), new Timestamp(System.currentTimeMillis()));
		 deduplicationAQ.put(new Integer(6), new Timestamp(System.currentTimeMillis()+1000));
    	 return deduplicationAQ;
	 }
	 private HashMap<Object,Object> getDeduplicationAvailableQueue() {
		 HashMap<Object,Object> deduplicationAQ= new HashMap<Object,Object>();
		 deduplicationAQ.put(new Integer(1), new Timestamp(System.currentTimeMillis()));
		 deduplicationAQ.put(new Integer(2), new Timestamp(System.currentTimeMillis()+1000));
		 deduplicationAQ.put(new Integer(3), new Timestamp(System.currentTimeMillis()+10000));
		 deduplicationAQ.put(new Integer(4), new Timestamp(System.currentTimeMillis()+100000));
		 return deduplicationAQ;
	 }
		
	 private ArrayList<Object> getDeduplicationPatientMergeVOCollection() {
		 ArrayList<Object> dePatientMergeList= new ArrayList<Object>();
		 DeduplicationPatientMergeVO deduplicationPatientMergeVO = new DeduplicationPatientMergeVO();
		 PersonVO personVo = new PersonVO();
		 PersonDT personDT = new PersonDT();
		 personDT.setGroupNbr(2);
		 personDT.setGroupTime(new Timestamp(System.currentTimeMillis()));
		 personDT.setDedupMatchInd("X");
		 personDT.setPersonUid(new Long(2340));
		 personVo.setThePersonDT(personDT);
		 deduplicationPatientMergeVO.setMPR(personVo); 
		 dePatientMergeList.add(deduplicationPatientMergeVO);
		 DeduplicationPatientMergeVO deduplicationPatientMergeVO1 = new DeduplicationPatientMergeVO();
		 PersonVO personVo1 = new PersonVO();
		 PersonDT personDT1 = new PersonDT();
		 personDT1.setGroupNbr(3);
		 personDT1.setGroupTime(new Timestamp(System.currentTimeMillis()));
		 personDT1.setDedupMatchInd("Y");
		 personDT1.setPersonUid(new Long(8945));
		 personVo1.setThePersonDT(personDT1);
		 deduplicationPatientMergeVO1.setMPR(personVo1); 
		 dePatientMergeList.add(deduplicationPatientMergeVO1);
		 return dePatientMergeList;
	 }
	 
	 private ArrayList<Object> getDeduplicationPatientMergeVOSingle() {
		 ArrayList<Object> dePatientMergeList= new ArrayList<Object>();
		 DeduplicationPatientMergeVO deduplicationPatientMergeVO = new DeduplicationPatientMergeVO();
		 PersonVO personVo = new PersonVO();
		 PersonDT personDT = new PersonDT();
		 personDT.setGroupNbr(2);
		 personDT.setGroupTime(new Timestamp(System.currentTimeMillis()));
		 personDT.setDedupMatchInd("X");
		 personVo.setThePersonDT(personDT);
		 deduplicationPatientMergeVO.setMPR(personVo); 
		 dePatientMergeList.add(deduplicationPatientMergeVO);
		 return dePatientMergeList;
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
	 
	 private Collection<Object> getMergeConfirmationV() {
		 ArrayList<Object> mergeCol= new ArrayList<Object>();
		 MergeConfirmationVO mvo= new MergeConfirmationVO();
		 mvo.setSurvivor(true);
		 mvo.setFirstName("FirstName1");
		 mvo.setMiddleName("");
		 mvo.setLastName("LastName1");
		 mvo.setNmUseCdDesc("legal");
		 mvo.setLocalId("OBS10030023GA01");
		 mergeCol.add(mvo);
		 
		 MergeConfirmationVO mvo1= new MergeConfirmationVO();
		 mvo1.setSurvivor(false);
		 mvo1.setFirstName("FirstName2");
		 mvo1.setMiddleName("");
		 mvo1.setLastName("LastName2");
		 mvo1.setNmUseCdDesc("legal");
		 mvo1.setLocalId("OBS10030012GA01");
		 mergeCol.add(mvo1);
		 
		 return mergeCol;
	 }
	 
	 private HashMap<Object,Object> getPVOSPerGroupNumber(Integer groupNUmber) {
		 HashMap<Object,Object> collectionPVOS= new HashMap<Object,Object>();
		 if(groupNUmber == 1 ) {
			 collectionPVOS.put(new Integer(1), getDeduplicationPatientMergeVOSingle());
		 }else {
			 collectionPVOS.put(new Integer(2), getDeduplicationPatientMergeVOCollection());
		 }
		 return collectionPVOS;
	 }
	 
	 private ArrayList<Object> getPersonVOCollection() {
		 ArrayList<Object> personVOCollection= new ArrayList<Object>();
		 PersonVO personVo = new PersonVO();
		 PersonDT personDT = new PersonDT();
		 personDT.setGroupNbr(2);
		 personDT.setGroupTime(new Timestamp(System.currentTimeMillis()));
		 personDT.setDedupMatchInd("X");
		 personDT.setPersonUid(new Long(2340));
		 personDT.setRecordStatusCd("ACTIVE");
		 personVo.setThePersonDT(personDT);
		 personVOCollection.add(personVo);
		 PersonVO personVo1 = new PersonVO();
		 PersonDT personDT1 = new PersonDT();
		 personDT1.setGroupNbr(3);
		 personDT1.setGroupTime(new Timestamp(System.currentTimeMillis()));
		 personDT1.setDedupMatchInd("Y");
		 personDT1.setPersonUid(new Long(8945));
		 personDT1.setRecordStatusCd("ACTIVE");
		 personVo1.setThePersonDT(personDT1);
		 personVOCollection.add(personVo1);
		 return personVOCollection;
	 }

}
	
