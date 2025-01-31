package test.gov.cdc.nedss.webapp.nbs.action.person;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.person.PersonSearchSubmit;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.person.PersonSearchSubmit","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({PersonSearchSubmit.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public class PersonSearchSubmit_tests {
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.person.PersonSearchSubmit","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({PersonSearchSubmit.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class FindPatientsCustomQuery_test {
	private String finalQuery="select";
    private int expectedListSize=3;
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	PatientSearchVO patientSearchVO;
	
	@Mock
	MainSessionHolder holder;
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpSession httpSession;
	
	@Mock
	MainSessionCommand msCommand;
	
	@Mock
	NBSSecurityObj secObjMock;
	
	@Mock
	PersonSearchForm psForm;

	@Mock
	LogUtils logger;
	
	@Mock
	SearchResultPersonUtil srpUtil;
	
	@InjectMocks
	PersonSearchSubmit personSearchSubmit;
	
	public FindPatientsCustomQuery_test(){}
	

	
	
	
	 @Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(PersonSearchSubmit.class, "propertyUtil", propertyUtil);
		 Whitebox.setInternalState(PersonSearchSubmit.class, "logger", logger);
	 }
		 
			
	@Test
	 public void findPatientsCustomQuery_test() throws Exception {
		 PowerMockito.whenNew(PatientSearchVO.class).withNoArguments().thenReturn(patientSearchVO);
		 PowerMockito.whenNew(SearchResultPersonUtil.class).withNoArguments().thenReturn(srpUtil);
		 PowerMockito.mockStatic(NBSContext.class);
		 msCommand = PowerMockito.mock(MainSessionCommand.class);
		 when(propertyUtil.getLabNumberOfRows()).thenReturn(10);
		 when(psForm.getPersonSearch()).thenReturn(patientSearchVO);
		 when(request.getSession()).thenReturn(httpSession);
		 when(holder.getMainSessionCommand(any(HttpSession.class))).thenReturn(msCommand);
		 when((MainSessionCommand)httpSession.getAttribute("msCommand")).thenReturn(msCommand);
		// Object[] oParams = new Object[]{patientSearchVO, new Integer(10),new Integer(0), finalQuery};
		 ArrayList<?> ar=retriveRecordsPerQueueForLab();
		 Mockito.doReturn(ar).when(msCommand).processRequest(any(String.class),any(String.class),any(Object[].class));
		 when(request.getSession().getAttribute("NBSSecurityObject")).thenReturn(secObjMock);
		 when(secObjMock.getPermission(NBSBOLookup.PATIENT,NBSOperationLookup.VIEWWORKUP)).thenReturn(true);
   	     Mockito.doReturn("LR").when(patientSearchVO).getReportType();
   	     Mockito.doNothing().when(srpUtil).setDisplayInfoLaboratoryReport(any(ArrayList.class),any(Boolean.class), any(HttpServletRequest.class));
		 Mockito.doNothing().when(srpUtil).sortObservations(any(PersonSearchForm.class),any(ArrayList.class),any(Boolean.class), any(HttpServletRequest.class));
		 Mockito.doNothing().when(srpUtil).updateMarkAsReviewdPermission(any(NBSSecurityObj.class),any(PersonSearchForm.class), any(ArrayList.class), any(HttpServletRequest.class));
		 NBSContext.store(any(HttpSession.class), any(String.class), any(ArrayList.class));
		 Object[] oParams1 = new Object[] {psForm, finalQuery,request};
	     Whitebox.invokeMethod(new PersonSearchSubmit(), "findPatientsCustomQuery", oParams1);
	     ArrayList<?> personList=(ArrayList<?> )ar.get(0);
	     int actualSize=((DisplayPersonList)personList.get(0)).getList().size();
	     System.out.println("Method Name: findPatientsCustomQuery,  List From EjB Call (actual populate size):::"+actualSize+"    expected populate data size::"+this.expectedListSize);
	     Assert.assertEquals(actualSize,this.expectedListSize);
		 System.out.println("PASSED");
	 }
			
	 
	 private ArrayList<?> retriveRecordsPerQueueForLab(){
		 ArrayList<ArrayList<Object>> main = new ArrayList<ArrayList<Object>>();
		 ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
		 reportCustomQueues.add( new DisplayPersonList(1, retriveResultsPerQueue(), 0,0));
		 ArrayList<Object>   reportCustomQueues1  = new ArrayList<Object> ();
		 reportCustomQueues1.add( new DisplayPersonList(1, retriveResultsPerQueue(), 0,0));
		 main.add(reportCustomQueues);
		 main.add(reportCustomQueues1);
		 return main;
	  }
	 
	 
	 ArrayList<Object> personVOs = null;
	 
	 private ArrayList<Object> retriveResultsPerQueue(){
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
	      
	      PatientSrchResultVO labReport2= new PatientSrchResultVO();
	      labReport.setDocumentType("Lab Report");
	     // labReport.setStartDate((Timestamp) new Date());
	      labReport.setReportingFacilityProvider("");
	      labReport.setPersonUID(1111L);
	      labReport.setPersonFirstName("hello");
	      labReport.setPersonLastName("first");
	      labReport.setDescription("");
	      labReport.setLocalId("12345678");
	      labReport.setObservationUid(76543L);
	      labReport.setMPRUid(6L);
	      labReport.setPersonParentUid(10L);
	      labReport.setElectronicInd("0987654");
	      labReport.setProgramAreaCode("56789012");
	      labReport.setRecordStatusCd("sdfghjkl");	
	      
	      personVOs.add(labReport);
	      personVOs.add(labReport1);
	      personVOs.add(labReport2);
	      
	      return personVOs;
}
}

}
		
	
