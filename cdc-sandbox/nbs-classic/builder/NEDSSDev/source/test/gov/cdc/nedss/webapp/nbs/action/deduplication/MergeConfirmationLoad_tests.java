package test.gov.cdc.nedss.webapp.nbs.action.deduplication;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
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

import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.action.deduplication.MergeConfirmationLoad;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({MergeConfirmationLoad.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public class MergeConfirmationLoad_tests { 
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({MergeConfirmationLoad.class,NBSContext.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class Execute_test { 
 	String contextAction;
 	String forwardAction;
	String expectedValue;
	int iteration;
		 
	public Execute_test(String contextAction,String forwardAction,String expectedValue, int iteration) {
		this.contextAction = contextAction;
		this.forwardAction=forwardAction;
		this.expectedValue = expectedValue;
		this.iteration = iteration;
	}

	@Mock
	HttpServletRequest request;
	 
	@Mock
	HttpSession session;
	 	 
	@Mock
	NBSSecurityObj secObj;
	
	@Mock
	TreeMap<Object,Object> tm;
	
	@Mock
	ActionMapping mapping;
	
	@Mock
	ActionForm form;
	
	@Mock
	HttpServletResponse response;
	 
	
	@InjectMocks
	@Spy 
	MergeConfirmationLoad mergeConfirmationLoad=  Mockito.spy(MergeConfirmationLoad.class);


	@SuppressWarnings("unchecked")
	@Test
	public void execute_test() throws Exception{
		 PowerMockito.mockStatic(NBSContext.class);
		 when(request.getSession(false)).thenReturn(session);
		 when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
		 when(request.getParameter("ContextAction")).thenReturn(contextAction);
		 when(NBSContext.getPageContext(any(HttpSession.class), any(String.class), any(String.class))).thenReturn(tm);
		 when(NBSContext.getCurrentTask(any(HttpSession.class))).thenReturn("LoadFindPatient4");
		 NBSContext.lookInsideTreeMap(any(TreeMap.class));
		 when(tm.get("ReturnToFindPatient")).thenReturn("ReturnToFindPatient");
		 when(tm.get("ReturnToHomePage")).thenReturn("ReturnToHomePage");
		 when(tm.get("ReturnToMergeCandidateList")).thenReturn("ReturnToMergeCandidateList");
		 when(NBSContext.retrieve(any(HttpSession.class), any(String.class))).thenReturn("retMergeConfirmationVO");
		 when(mapping.findForward(forwardAction)).thenReturn(getActionForwardPath(forwardAction));
		 Object[] oParams = new Object[] {mapping, form, request,response};
		 ActionForward actionForward=Whitebox.invokeMethod(mergeConfirmationLoad, "execute", oParams);
		 String actualResult = actionForward.getPath();
		 Assert.assertEquals(this.expectedValue,actualResult);
		 System.out.println("Method Name: execute, ContextAction:"+contextAction+", forwardAction::"+forwardAction+", Iteration: "+this.iteration+". Expected value:"+this.expectedValue+" Actual value: "+actualResult);
		 System.out.println("RESULTS::::::PASSED");
	}
		 
		 
	@Parameterized.Parameters
	public static Collection<Object[]> input() {
	    return Arrays.asList(new Object[][]{
	  {"Merge","submit","/LoadFindPatient4.do",1},
	  {"ReturnToHomePage","ReturnToHomePage","/LoadMyTaskList1.do",2},
	  {"ReturnToFindPatient","ReturnToFindPatient","/LoadFindPatient4.do",3},
	  {"ReturnToMergeCandidateList","ReturnToMergeCandidateList","/LoadMergeCandidateList2.do",4},
	 });
	}
	
	 private ActionForward getActionForwardPath(String forwardAction) {
		 ActionForward actionForward = new ActionForward();
		 String path="";
		 if("submit".equals(forwardAction) ) {
			path="/LoadFindPatient4.do";
		 } else if("ReturnToHomePage".equals(forwardAction)) {
			path="/LoadMyTaskList1.do";
		 }else if("ReturnToFindPatient".equals(forwardAction)) {
			path="/LoadFindPatient4.do";
		 }else if("ReturnToMergeCandidateList".equals(forwardAction)) {
			path="/LoadMergeCandidateList2.do";
		 }
		 actionForward.setPath(path);
		 return actionForward;
	 }
	
}
}
	
