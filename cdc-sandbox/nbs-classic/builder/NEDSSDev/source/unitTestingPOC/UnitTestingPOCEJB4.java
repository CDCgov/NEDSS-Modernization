package unitTestingPOC;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.ResourceBundle;

import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyEJB;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;



/*
 * Mover to powermockito to being able to mock static elements
 * Powermockito 1.6.6 gives this type of error: No methods matching the name(s) prepareStatement were found in the class hierarchy of class java.lang.Object.
 * 
 *
 * */

/*
 * IMPORTANT NOTE:
 * 
 * I HAD TO COMMENT OUT THE TEST METHOD BECAUSE THE ACTUAL METHOD WE WERE TESTING LOOKS LIKE DOESN'T EXIST IN 6.0.12.
 * 
 * */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageProxyEJB.class,NNDMessageSenderHelper.class})
public class UnitTestingPOCEJB4 {

	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */


	
	@Mock
	  NBSSecurityObj nbsSecurityObj;
	@Mock
	NNDMessageSenderHelper nndMessageSenderHelper;
	
	@Mock
	LogUtils logger;
	 
	@InjectMocks
	PageProxyEJB pageProxyEJB;
	 
	
		 @Before
		 public void initMocks() throws Exception {

			 MockitoAnnotations.initMocks(this);

			 nbsSecurityObj = Mockito.mock(NBSSecurityObj.class);
			//	nndMessageSenderHelper= Mockito.mock(NNDMessageSenderHelper.class);
				
			 logger = new LogUtils("PageProxyEJB");
			 
			
			 

		 }
		 
		 
//			@Test
//				public void testCaseEJB() throws Exception{
//
//				System.out.println("******************* Starting test case named: testCaseEJB *******************");
//
//				Long longUid = 100000L;
//				
//				//nndMessageSenderHelper = Mockito.mock(NNDMessageSenderHelper.class);
//				when(nndMessageSenderHelper.checkForExistingNotificationsByUid(any(Long.class))).thenReturn(false);   
//				/*
//				 * mocking static methods from the test method
//				 * */
//				PowerMockito.mockStatic(NNDMessageSenderHelper.class);
//
//			    when(NNDMessageSenderHelper.getInstance()).thenReturn(nndMessageSenderHelper);
//			
//			 /*    PowerMockito.whenNew(NNDMessageSenderHelper.class)
//	              .withNoArguments()
//	              .thenReturn(nndMessageSenderHelper);
//			     
//		*/
//				 
//				boolean actualResult = pageProxyEJB.checkForExistingNotificationsByPublicHealthUid(longUid, nbsSecurityObj);
//				
//				boolean expectedResult = false;
//				System.out.println("Expected result:" +expectedResult);
//				System.out.println("Actual result:" +actualResult);
//				
//				Assert.assertEquals(actualResult,expectedResult);
//			
//				System.out.println("******************* End test case named: testCaseEJB *******************");
//				}
//			
			
			

			
			
	/*We should have these 3 sets on each test*/
	//Arrange
	
	//Act
	
	//Assert
	
	
	
}
	
