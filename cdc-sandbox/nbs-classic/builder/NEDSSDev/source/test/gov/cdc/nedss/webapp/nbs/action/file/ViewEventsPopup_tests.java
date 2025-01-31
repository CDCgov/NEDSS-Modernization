package test.gov.cdc.nedss.webapp.nbs.action.file;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.entity.person.dt.PersonReportsSummaryDT;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.file.ViewEventsPopup;
import gov.cdc.nedss.webapp.nbs.action.file.WorkupLoad;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.file.ViewEventsPopup",
	"gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(Enclosed.class)
@PrepareForTest({ViewEventsPopup.class,NBSContext.class,LogUtils.class,PropertyUtil.class,NEDSSConstants.class})
@PowerMockIgnore("javax.management.*")
public class ViewEventsPopup_tests {
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.file.ViewEventsPopup",
	"gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({ViewEventsPopup.class,NBSContext.class,LogUtils.class,PropertyUtil.class,NEDSSConstants.class})
@PowerMockIgnore("javax.management.*")
public static class GetLabReportsDisplayList_test {
	 
	@Mock
	PropertyUtil propertyUtil;
	@Mock
	HttpServletRequest request;
	@Mock
	LogUtils logger;
	
	 public GetLabReportsDisplayList_test() {
	 
	}

	@Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		  Whitebox.setInternalState(ViewEventsPopup.class, "logger", logger);
		 Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		 PowerMockito.mockStatic(CachedDropDowns.class);
	 }
		 
	 
	 @Test
	 public void getLabReportsDisplayList_test() throws Exception {
		 List<LabReportSummaryVO> labReportSummaryVOList =populateLabSummaryList();
		 PowerMockito.when(CachedDropDowns.getCodeDescTxtForCd(any(String.class),any(String.class))).thenReturn("Test_Processing_Decision_RSN");
		 PowerMockito.when(CachedDropDowns.getCodeDescTxtForCd(any(String.class),any(String.class))).thenReturn("Test_Processing_Decision_SYPHILIS");
		 	
		 
 		
 		TreeMap<String,String> tm = new TreeMap<String,String>();
 		tm.put("ObservationLabIDOnSummary","ObservationLabIDOnSummary");
 		String sCurrTask="sCurrTask";
 		
 		
		 Object[] oParams1 = new Object[] {labReportSummaryVOList, null,request,NEDSSConstants.SUMMARY,tm,sCurrTask};
		 
		 List<PersonReportsSummaryDT> summaryLabReportList =Whitebox.invokeMethod(new ViewEventsPopup(), "getLabReportsDisplayList", oParams1);
		 assertTrue("getLabReportsDisplayList_test Success . ", "<b>Date Collected:</b><br>12/31/1969".equalsIgnoreCase(summaryLabReportList.get(0).getDateCollected()));
		 assertTrue("getMorbReportsDisplayList_test Failed . ", "<b>Reporting Facility:</b><br>ReportingFecility<br><b>Ordering Provider:</b><br>TestPrefix FirstName LastName TestSuffix <br><b>Ordering Facility:</b><br>OrderingFecility".equalsIgnoreCase(summaryLabReportList.get(0).getProviderFacility().trim()));
		 assertTrue("getMorbReportsDisplayList_test Failed . ", "jurisdiction".equalsIgnoreCase(summaryLabReportList.get(0).getJurisdiction().trim()));
			
		 oParams1 = new Object[] {null, null,request,NEDSSConstants.SUMMARY,tm,sCurrTask};
		 summaryLabReportList =Whitebox.invokeMethod(new WorkupLoad(), "getLabReportsDisplayList", oParams1);
		 assertTrue("getLabReportsDisplayList_test Success . ", summaryLabReportList.isEmpty());
		 
	 } 
	
	 private List<LabReportSummaryVO> populateLabSummaryList(){
		 List<LabReportSummaryVO> labReportSummaryVOList = new ArrayList<LabReportSummaryVO>();
		 LabReportSummaryVO labReportSummaryVO = new LabReportSummaryVO();
		 labReportSummaryVO.setProcessingDecisionCd("TEST");
		 labReportSummaryVO.setProgramArea("programArea");
		 labReportSummaryVO.setJurisdiction("jurisdiction");
		 labReportSummaryVO.setObservationUid(012L); 
		 labReportSummaryVO.setUid(12345L) ;
		 labReportSummaryVO.setLabFromDoc(true);
		 labReportSummaryVO.setDateReceived(new Timestamp(1233333));
		 labReportSummaryVO.setElectronicInd("electronicInd");
		 labReportSummaryVO.setProviderPrefix("TestPrefix"); 
		 labReportSummaryVO.setProviderFirstName("FirstName");
		 labReportSummaryVO.setProviderLastName("LastName");
		 labReportSummaryVO.setProviderSuffix("TestSuffix");
		 labReportSummaryVO.setOrderingFacility("OrderingFecility"); 
		 labReportSummaryVO.setReportingFacility("ReportingFecility");
		 labReportSummaryVO.setDateCollected(new Timestamp(1233333));
		 labReportSummaryVO.setResultedTestString("Result");
		 labReportSummaryVO.setAssociationsMap(null);
		 labReportSummaryVO.setLocalId("123");
		 labReportSummaryVOList.add(labReportSummaryVO);
	      
	      return labReportSummaryVOList;
}
	 
}



@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.file.ViewEventsPopup",
	"gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({ViewEventsPopup.class,NBSContext.class,LogUtils.class,PropertyUtil.class,NEDSSConstants.class})
@PowerMockIgnore("javax.management.*")
public static class GetMorbReportsDisplayList_test {
	 
	@Mock
	PropertyUtil propertyUtil;
	@Mock
	HttpServletRequest request;
	@Mock
	LogUtils logger;
	
	 public GetMorbReportsDisplayList_test() {
	 
	}

	@Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		  Whitebox.setInternalState(ViewEventsPopup.class, "logger", logger);
		 Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		 PowerMockito.mockStatic(CachedDropDowns.class);
	 }
		
	 
	 @Test
	 public void getMorbReportsDisplayList_test() throws Exception {
		 List<MorbReportSummaryVO> morbReportSummaryVOList =populateMorbReportSummaryVOList();
		 PowerMockito.when(CachedDropDowns.getCodeDescTxtForCd(any(String.class),any(String.class))).thenReturn("Test_Processing_Decision_RSN");
		 PowerMockito.when(CachedDropDowns.getCodeDescTxtForCd(any(String.class),any(String.class))).thenReturn("Test_Processing_Decision_SYPHILIS");
		 	
		 
 		
 		TreeMap<String,String> tm = new TreeMap<String,String>();
 		tm.put("ObservationMorbIDOnSummary","ObservationMorbIDOnSummary");
 		String sCurrTask="sCurrTask";
 		
 		
		 Object[] oParams1 = new Object[] {morbReportSummaryVOList,request,NEDSSConstants.SUMMARY,tm,sCurrTask};
		 
		 List<PersonReportsSummaryDT> summaryMorbReportList =Whitebox.invokeMethod(new WorkupLoad(), "getMorbReportsDisplayList", oParams1);
		 assertTrue("getMorbReportsDisplayList_test Failed .",summaryMorbReportList.get(0).getDateReceived().contains("12/31/1969"));
		 //assertTrue("getMorbReportsDisplayList_test Failed . ", "12/31/1969<br>6:20 PM<br>Test_Processing_Decision_SYPHILIS".equalsIgnoreCase(summaryMorbReportList.get(0).getDateReceived()));
		 assertTrue("getMorbReportsDisplayList_test Failed . ", "<b>Reporting Facility:</b><br>ReportingFecility<br><b>Ordering Provider:</b><br>TestPrefix FirstName LastName TestSuffix".equalsIgnoreCase(summaryMorbReportList.get(0).getProviderFacility().trim()));
		 assertTrue("getMorbReportsDisplayList_test Failed . ", "jurisdiction".equalsIgnoreCase(summaryMorbReportList.get(0).getJurisdiction().trim()));
		 
		  
		 
		 oParams1 = new Object[] {null,request,NEDSSConstants.SUMMARY,tm,sCurrTask};
		 summaryMorbReportList =Whitebox.invokeMethod(new WorkupLoad(), "getMorbReportsDisplayList", oParams1);
		 assertTrue("getMorbReportsDisplayList_test Failed . ", summaryMorbReportList.isEmpty());
		 
	 } 
	 private List<MorbReportSummaryVO> populateMorbReportSummaryVOList(){
		 List<MorbReportSummaryVO> morbReportSummaryVOList = new ArrayList<MorbReportSummaryVO>();
		 MorbReportSummaryVO morbReportSummaryVO = new MorbReportSummaryVO();
		 morbReportSummaryVO.setProcessingDecisionCd("TEST");
		 morbReportSummaryVO.setProgramArea("programArea");
		 morbReportSummaryVO.setJurisdiction("jurisdiction");
		 morbReportSummaryVO.setObservationUid(012L); 
		 morbReportSummaryVO.setUid(12345L) ;
		 morbReportSummaryVO.setMorbFromDoc(true);
		 morbReportSummaryVO.setDateReceived(new Timestamp(1233333));
		 morbReportSummaryVO.setElectronicInd("electronicInd");
		 morbReportSummaryVO.setProviderPrefix("TestPrefix"); 
		 morbReportSummaryVO.setProviderFirstName("FirstName");
		 morbReportSummaryVO.setProviderLastName("LastName");
		 morbReportSummaryVO.setProviderSuffix("TestSuffix");
		 morbReportSummaryVO.setReportingFacility("ReportingFecility");
		 morbReportSummaryVO.setAssociationsMap(null);
		 morbReportSummaryVO.setLocalId("123");
		 morbReportSummaryVOList.add(morbReportSummaryVO);
	      
	      return morbReportSummaryVOList;
}
	 
	 
}

}
		
	
