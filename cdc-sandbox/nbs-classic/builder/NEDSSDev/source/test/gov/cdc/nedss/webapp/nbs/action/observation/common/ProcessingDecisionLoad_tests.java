package test.gov.cdc.nedss.webapp.nbs.action.observation.common;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
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

import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.observation.common.ProcessingDecisionLoad;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.form.observation.ObservationForm;

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.observation.ObservationForm","gov.cdc.nedss.webapp.nbs.action.observation.common.ProcessingDecisionLoad","gov.cdc.nedss.util.LogUtils", "gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil", "gov.cdc.nedss.webapp.nbs.action.util.CommonAction", "gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf"})
@PrepareForTest({ProcessingDecisionLoad.class,LogUtils.class, PageLoadUtil.class, CommonAction.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class ProcessingDecisionLoad_tests {

	@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.observation.common.ProcessingDecisionLoad", "gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil", "gov.cdc.nedss.webapp.nbs.action.util.CommonAction", "gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf"})
	@RunWith(PowerMockRunner.class)
	@PrepareForTest({ObservationForm.class, ProcessingDecisionLoad.class, LogUtils.class, PageLoadUtil.class, CommonAction.class})
	@PowerMockRunnerDelegate(Parameterized.class)
	@PowerMockIgnore("javax.management.*")
	public static class PDLoad_test{

		 private String iteration;
		 private String event;
		 private String context;
		 private String isCreateInvFromViewDocument;
		 private String referralBasisDocument;
		 private String MprUid;
		 
		 public PDLoad_test(String it, String event, String context, String isCreateInvFromViewDocument, String referralBasisDocument, String MprUid){
			super();
			this.iteration = it;
			this.event = event;
			this.context = context;
			this.isCreateInvFromViewDocument = isCreateInvFromViewDocument;
			this.referralBasisDocument = referralBasisDocument;
			this.MprUid = MprUid;
		 }

	 @Parameterized.Parameters
	   public static Collection input() {

		  int it = 0;
		   
	      return Arrays.asList(new Object[][]{
	    		  
	    		  {"PDLoad_test"+"_"+it++,"INV","INV","true","T1","123456789"},

		  });
	   }
	 
	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	@Mock
	NBSSecurityObj secObj;
	 
	@Mock
	LogUtils loggerMock;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	ObservationForm form;

	@Mock
	ActionMapping mapping;

	@Mock
	HttpSession session;
	
	@Mock
	PageLoadUtil pageLoadUtil;
	
	@Mock
	CommonAction commonAction;
	
	@Spy
	@InjectMocks
	ProcessingDecisionLoad processingDecisionLoad = Mockito.spy(new ProcessingDecisionLoad());

	 @Before
	 public void initMocks() throws Exception {

		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 response = Mockito.mock(HttpServletResponse.class);
		 form = new ObservationForm();
		 mapping = Mockito.mock(ActionMapping.class);
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 session = Mockito.mock(HttpSession.class);
		 pageLoadUtil = Mockito.mock(PageLoadUtil.class);
		 commonAction = Mockito.mock(CommonAction.class);
		 
		 Whitebox.setInternalState(ProcessingDecisionLoad.class, "logger", loggerMock);
		 
	 }
	 
	@Test
	public void PDLoad_test() throws Exception{
			
		System.out.println("******************* Starting test case named: ProcessingDecisionLoad_tests *******************");
		
		when((String) request.getParameter("event")).thenReturn(event);
		when((String) request.getParameter("context")).thenReturn(context);
		when((String) request.getParameter("isCreateInvFromViewDocument")).thenReturn(isCreateInvFromViewDocument);
		when((String) request.getParameter("referralBasisDocument")).thenReturn(referralBasisDocument);
		when((String) request.getParameter("MprUid")).thenReturn(MprUid);
		
		when(request.getSession(false)).thenReturn(session);
		when(request.getSession()).thenReturn(session);
		when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
		Whitebox.setInternalState(ProcessingDecisionLoad.class, "EVENT", "event");
		PowerMockito.whenNew(PageLoadUtil.class).withNoArguments().thenReturn(pageLoadUtil);
		PowerMockito.whenNew(CommonAction.class).withNoArguments().thenReturn(commonAction);
		ActionForward action = new ActionForward();
		
		when(mapping.findForward("default")).thenReturn(action);
		
		ActionForward actionForwardActual= Whitebox.invokeMethod(new ProcessingDecisionLoad(), "processingDecisionLoad", mapping, form, request, response);
		
		Assert.assertEquals(form.getProcessingDecisionLogic(), "STD_CR_PROC_DECISION_NOINV");

		System.out.println("PASSED");
		System.out.println("******************* End test case named: ProcessingDecisionLoad_tests *******************");
			
		}		
	}
}
