package unitTestingPOC;

import static org.mockito.Mockito.when;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.PageAction;
import gov.cdc.nedss.webapp.nbs.action.share.ShareCaseUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import sun.security.validator.ValidatorException;



/*
 * Mover to powermockito to being able to mock static elements
 * Powermockito 1.6.6 gives this type of error: No methods matching the name(s) prepareStatement were found in the class hierarchy of class java.lang.Object.
 * 
 *
 * */




@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.PageAction","gov.cdc.nedss.webapp.nbs.action.share.ShareCaseUtil","gov.cdc.nedss.util.PropertyUtil",})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageAction.class})
public class UnitTestingPOCAction3 {

	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */


	
//	@Mock
	//PropertyUtil propertyUtilMocked;
	@Mock
	ShareCaseUtil util;
	
	@Mock
	LogUtils logger;
	

	@Mock
	ActionMapping mapping;
	
	/*Mocking the PageForm was giving unexpected exceptions, like Class not found.*/
	
	@Mock
	PageForm form;
	
	@Mock
	HttpServletRequest request;
	
	@Mock
    HttpServletResponse response;
	
	@InjectMocks
	PageAction pageAction = Mockito.spy(new PageAction());

		 @Before
		 public void initMocks() throws Exception {

			 MockitoAnnotations.initMocks(this);
			 pageAction = new PageAction();
		//	 form = Mockito.mock(PageForm.class);
			// propertyUtilMocked = Mockito.mock(PropertyUtil.class);
			
		
			 logger = new LogUtils("PageProxyEJB");
			 
			
			// PageForm partiallyMockedFoo = PowerMockito.spy(new PageForm());


		 }
		 
			
			

			@Test
				public void testCaseAction() throws Exception{

				System.out.println("******************* Starting test case named: testCaseAction *******************");

				ActionForward aa = new ActionForward();
				aa.setPath("aaaa");
				
				
			
				 
				/*Powermockito to mock objects created from the testing method:*/
				 ShareCaseUtil shareCaseUtil = PowerMockito.mock(ShareCaseUtil.class);
				 PowerMockito.whenNew(ShareCaseUtil.class).withNoArguments().thenReturn(shareCaseUtil);
				 when(shareCaseUtil.shareCasePageSubmit(mapping, form, request, response)).thenReturn(aa);
				 
			
				ActionForward actionForward = pageAction.sharePageCaseSubmit(mapping, form, request, response);
				String actualPath = actionForward.getPath();
				String expectedPath = aa.getPath();
				
				
				System.out.println("expected path: "+expectedPath);
				System.out.println("actual path: "+actualPath);
				
				
				Assert.assertEquals(actualPath,expectedPath);
				
			
				System.out.println("******************* End test case named: testCaseAction *******************");
				}
			
			
			

			
			
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
	
	
	
}
	
