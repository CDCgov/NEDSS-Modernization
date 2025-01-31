package test.gov.cdc.nedss.webapp.nbs.action.localfields;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.localfields.LocalFieldsAction;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations.util.CaseCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.form.localfields.LocalFieldsForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.matchers.Any;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;




/**
Methods that have been unit tested so far:


 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.localfields.LocalFieldsForm","gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil","gov.cdc.nedss.webapp.nbs.action.localfields.LocalFieldsAction","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({LocalFieldUtil.class,LocalFieldsAction.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class LocalFieldsAction_tests {






/**
 * EditLDF: the update to this method was replacing uiMetadataUid.intValue() > 0 with uiMetadataUid > 0. The way we are verifying this method is working as expected is,
 * after making the change, the method still executes completely successfully returning the expection action forward. We can't check if the UID has been set correctly
 * as it is used to be sent to a different method. 
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil","gov.cdc.nedss.webapp.nbs.action.localfields.LocalFieldsAction","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({LocalFieldsForm.class,LocalFieldUtil.class, LocalFieldsAction.class, LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class EditLDF_test{
	

	private String iteration;
	private String uiMetadataUid;
	
	

	
 public EditLDF_test(String it, String uiMetadataUid){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.uiMetadataUid = uiMetadataUid;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"editLDF_test"+"_"+it++,"11111"},
    		  {"editLDF_test"+"_"+it++,"0"},
    		  {"editLDF_test"+"_"+it++,"1"},
    		  {"editLDF_test"+"_"+it++,"222222"},
    		  {"editLDF_test"+"_"+it++,"24214124"},
    		  {"editLDF_test"+"_"+it++,"35325252"},
    		  {"editLDF_test"+"_"+it++,"99999999"},
    		  {"editLDF_test"+"_"+it++,"-1"},
    		  {"editLDF_test"+"_"+it++,null},
    		  
    		  
    		  
    		  
    		
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */




@Mock
PageActProxyVO pageActProxyVO;


@Mock
LogUtils loggerMock;

@Mock
NbsActEntityDT oldDT;

@Mock
ParticipationDT participationDT;

@Mock
HttpServletRequest request;

@Mock
HttpServletResponse response;

@Mock
LocalFieldsForm form;

@Mock
ActionMapping mapping;

@Spy
@InjectMocks
LocalFieldsAction localFieldsAction=Mockito.spy(new LocalFieldsAction());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 participationDT = Mockito.mock(ParticipationDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 response = Mockito.mock(HttpServletResponse.class);
		 form = Mockito.mock(LocalFieldsForm.class);
		 mapping = Mockito.mock(ActionMapping.class);
		 Whitebox.setInternalState(LocalFieldsAction.class, "logger", loggerMock);
		 
	 }
	 
	

	@Test
	public void editLDF_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: editLDF_test *******************");
				
				
				
	
				 when(request.getParameter("uiMetadataUid")).thenReturn(uiMetadataUid);
				 ArrayList<Object> dtList = new ArrayList<Object>();
				 
				PowerMockito.mockStatic(LocalFieldUtil.class);
				PowerMockito.doReturn(dtList).when(LocalFieldUtil.class,"processRequest",Matchers.any(Object[].class),any(HttpSession.class));
				//manageForm.setPageName(request);
				
			//	when(c.prepareStatement(any(String.class))).thenReturn(stmt);
				
				ActionForward action = new ActionForward();
				
				when(mapping.findForward("default")).thenReturn(action);
				
				Mockito.doNothing().when(form).setPageName(request);
				ActionForward actionForwardActual= Whitebox.invokeMethod(new LocalFieldsAction(), "editLDF", mapping, form, request, response);
			
				System.out.println("Iteration: #"+iteration+"\n The method has executed successfully returning the expected action forward.");
				Assert.assertEquals(actionForwardActual,action);
				System.out.println("PASSED");
				System.out.println("******************* End test case named: editLDF_test *******************");
			
		}		
	
}









}
	
