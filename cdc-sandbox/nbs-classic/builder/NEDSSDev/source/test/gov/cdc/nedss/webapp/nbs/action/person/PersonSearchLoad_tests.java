
package test.gov.cdc.nedss.webapp.nbs.action.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockHttpSession;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.person.PersonSearchLoad;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm; 


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.servlet",
	"gov.cdc.nedss.webapp.nbs.action.person.PersonSearchLoad","gov.cdc.nedss.util.LogUtils",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj","gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper"})
@RunWith(Enclosed.class)
@PrepareForTest({ PersonSearchLoad.class,LogUtils.class,PropertyUtil.class, NEDSSConstants.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public class PersonSearchLoad_tests {
	


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.servlet",
	"gov.cdc.nedss.webapp.nbs.action.person.PersonSearchLoad","gov.cdc.nedss.util.LogUtils",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj","gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper"})
@RunWith(PowerMockRunner.class) 
@PrepareForTest({ PersonSearchLoad.class,LogUtils.class,PropertyUtil.class, NEDSSConstants.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class Execute_test {
	
	
	@Mock
	PropertyUtil propertyUtil;
	@org.mockito.Mock
	LogUtils loggerMock;

	//@org.mockito.Mock
	//HttpServletRequest request;
	
	@InjectMocks
	@Spy //@Spy is needed to mock methods inside the class that we are testing.
	PersonSearchLoad personSearchLoad=  Mockito.spy(PersonSearchLoad.class);
	
	
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	//@Mock
	//HttpSession httpSession;
	

	@Mock
	PersonSearchForm psForm;
	
	@Mock
	ErrorMessageHelper errorMessageHelperMock;
	 
	
	MockHttpServletRequest mockRequest = new MockHttpServletRequest();
	MockHttpSession mockHttpSession = new MockHttpSession();
	MockHttpServletResponse mockResponse = new MockHttpServletResponse();
	ActionMapping mockMapping = new ActionMapping();
	
	
	
	 @Before
	    public void initMocks() throws Exception {
		 
		 propertyUtil= Mockito.mock(PropertyUtil.class);
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		//searchResultPersonUtil = PowerMockito.spy(new SearchResultPersonUtil());
	     personSearchLoad =PowerMockito.spy(new PersonSearchLoad());
		 loggerMock = Mockito.mock(LogUtils.class);
		 
		 Whitebox.setInternalState(PersonSearchLoad.class, "logger", loggerMock);
		 Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		 
		 nbsSecurityObjMock = Mockito.mock(NBSSecurityObj.class);
		 //Whitebox.setInternalState(SearchResultPersonUtil.class, "instance", nbsSecurityObjMock);
		 errorMessageHelperMock = Mockito.mock(ErrorMessageHelper.class);
	    }
	  
	 

		@Test
		public void execute_test()  throws Exception{
			mockHttpSession.setAttribute("NBSSecurityObject", nbsSecurityObjMock);
			mockHttpSession.setAttribute("confirmationMessage", "SUCCESS");
			mockRequest.setHttpSession(mockHttpSession);
			mockRequest.setAttribute("ContextAction", "ReturnToFindPatient");
			
			PowerMockito.mockStatic(ErrorMessageHelper.class);
			PowerMockito.doNothing().when(ErrorMessageHelper.class, "setErrMsgToRequest", any(MockHttpServletRequest.class), any(String.class));
			
			Mockito.doReturn(null).when(personSearchLoad).chooseInvestigation(any(String.class));
			
			TreeMap<Object,Object> pageElements = new TreeMap<Object,Object> ();
			pageElements.put("Submit", "Submit");
			
			PowerMockito.mockStatic(NBSContext.class);
			NBSContext.store(any(HttpSession.class), any(String.class), any(ArrayList.class));
			when(NBSContext.getPageContext(any(HttpSession.class), any(String.class), any(String.class))).thenReturn (pageElements);
			when(NBSContext.getCurrentTask(mockRequest.getSession())).thenReturn("FindPatient1");
			Mockito.doReturn("TestUsers").when(personSearchLoad).getusers(any(NBSSecurityObj.class));
			ActionForward expectedForward = new ActionForward();
			
			expectedForward.setName("XSP");
			expectedForward.setPath("XSP");
			mockMapping.addForwardConfig(expectedForward);
			
			mockRequest.setAttribute("MergePatient", "true");
			
			ActionForward actualForward=personSearchLoad.execute(mockMapping, psForm, mockRequest, mockResponse);
			assertEquals("/nbs/FindPatient1.do?Mode1=ManualMerge", mockRequest.getAttribute("formHref"));
			assertEquals(expectedForward.getPath(), actualForward.getPath());
			assertEquals("SUCCESS", mockRequest.getAttribute("confirmationMergeMessage"));
		}
		
		 
}

}