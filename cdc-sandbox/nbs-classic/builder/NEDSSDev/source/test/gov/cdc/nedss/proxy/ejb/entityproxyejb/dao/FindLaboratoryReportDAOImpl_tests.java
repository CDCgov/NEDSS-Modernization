package test.gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchResultTmp;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(Enclosed.class)
@PrepareForTest({SearchResultDAOImpl.class})
@PowerMockIgnore("javax.management.*")
public class FindLaboratoryReportDAOImpl_tests  {
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchResultDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class FindPatientsByQuery_test  {

	private int cacheNumber;
	private int fromIndex;
	private String finalQuery;
    private String whereClause;
	private int expectedValue;
	private int iteration;
	private int customVoCollection;
	

	public FindPatientsByQuery_test(int cacheNumber, int fromIndex, String finalQuery, String whereClause,
			int customVoCollection,int expectedValue, int iteration) {
		this.cacheNumber = cacheNumber;
		this.fromIndex = fromIndex;
		this.finalQuery = finalQuery;
		this.whereClause = whereClause;
		this.customVoCollection=customVoCollection;
		this.expectedValue = expectedValue;
		this.iteration = iteration;
	
	}
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	NBSSecurityObj nbsSecurityObj;
	
	@Mock
	PatientSearchVO patientSearchVO;
	
	
	@Mock
	LogUtils logger;
    
	@Mock
	SearchResultDAOImpl searchResultDAOImpl;
	
	@Mock
	 RetrieveSummaryVO rsvo;
	
	@Mock
	CachedDropDownValues cache;
    
	@InjectMocks
	FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl;
	
	@Before
	 public void initMocks() throws Exception {
		 propertyUtil= Mockito.mock(PropertyUtil.class);
		 logger=Mockito.mock(LogUtils.class);
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
	     Whitebox.setInternalState(FindLaboratoryReportDAOImpl.class,"logger", logger);
     }
	
	@Test
	 public void  findPatientsByQuery_test() throws Exception{
		 findLaboratoryReportDAOImpl = PowerMockito.spy(new FindLaboratoryReportDAOImpl());
		 when(propertyUtil.getMyProgramAreaSecurity()).thenReturn("VIEW");
         when(propertyUtil.getLabNumberOfRows()).thenReturn(10);	
         when(nbsSecurityObj.getDataAccessWhereClause(any(String.class),any(String.class), any(String.class))).thenReturn("where");
         ArrayList<Object> queueList=retriveCustomQueueVOList();
         Mockito.doReturn(queueList).when(findLaboratoryReportDAOImpl).preparedStmtMethod(any(PersonSearchResultTmp.class),any(ArrayList.class),any(String.class),any(String.class));
         PowerMockito.doReturn(returnAssembleData()).when(findLaboratoryReportDAOImpl, "assembleLabReportData", any(ArrayList.class),any(NBSSecurityObj.class));
         Object[] oParams=new Object[] {patientSearchVO,this.finalQuery,this.cacheNumber,this.fromIndex,nbsSecurityObj};
         ArrayList<Object> arr= Whitebox.invokeMethod(findLaboratoryReportDAOImpl, "findPatientsByQuery", oParams);
		 DisplayPersonList dpl=(DisplayPersonList)arr.get(0);
		 Assert.assertEquals(this.expectedValue,dpl.getList().size());
		 System.out.println("Method Name: findPatientsByQuery,  Iteration: "+iteration+". Expected value: "+this.expectedValue+" Actual value: "+dpl.getList().size());
		 System.out.println("PASSED"); 

	 }
	
	
	
	private ArrayList<Object> retriveCustomQueueVOList(){
		ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
			PersonSearchResultTmp searchResultName= null;
			for(int i=0; i < this.customVoCollection; i++) {
				searchResultName=new PersonSearchResultTmp();
				searchResultName.setLocalId("1234"+i);
				searchResultName.setCaseClassCd("123"+i);
				searchResultName.setCity("Atl"+i);
				reportCustomQueues.add(searchResultName);
			}
			return reportCustomQueues;
	}
	
	
	private ArrayList<Object> returnAssembleData(){
		ArrayList<Object>   assembleList  = new ArrayList<Object> ();
		PatientSrchResultVO labReport=null;
		for(int i=0; i < this.customVoCollection; i++) {
			labReport=new PatientSrchResultVO();
			labReport.setLocalId("1223445"+i);
			labReport.setCaseStatusCd("098766"+i);
			labReport.setCondition("tytyu"+i);
			labReport.setJurisdiction("atl"+i);
			assembleList.add(labReport);
		}
		return assembleList;
	}
	
	 @SuppressWarnings("rawtypes")
	  @Parameterized.Parameters
	   public static Collection input() {
			return Arrays.asList(new Object[][] {
				{100,0,"select","where", 3,3, 1 },
				{100,0,"select","where", 3,3, 2 },
				{100,0,"select","where",4,4, 3 },
				{100,0,"select","where", 4,4, 4 },
				{100,0,"select","where", 5,5, 5 },
				{100,0,"select","where", 8,8, 6 },
				{100,0,"select","where",3,3, 7 },
				{100,0,"select","where", 4,4, 8 },
				{100,0,"select","where",3,3, 9 }
						
			});
				
			}
				
	
}



@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchResultDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class AssembleLabReportData_test  {

	private int cacheNumber;
	private int fromIndex;
	private String finalQuery;
    private String whereClause;
	private int expectedValue;
	private int iteration;
	private int customVoCollection;
	

	public AssembleLabReportData_test(int cacheNumber, int fromIndex, String finalQuery, String whereClause,
			int customVoCollection,int expectedValue, int iteration) {
		this.cacheNumber = cacheNumber;
		this.fromIndex = fromIndex;
		this.finalQuery = finalQuery;
		this.whereClause = whereClause;
		this.customVoCollection=customVoCollection;
		this.expectedValue = expectedValue;
		this.iteration = iteration;
	
	}
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	NBSSecurityObj nbsSecurityObj;
	
	@Mock
	PatientSearchVO patientSearchVO;
	
	
	@Mock
	LogUtils logger;
    
	@Mock
	SearchResultDAOImpl searchResultDAOImpl;
	
	@Mock
	 RetrieveSummaryVO rsvo;
	
	@Mock
	CachedDropDownValues cache;
    
	@InjectMocks
	FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl;
	
	@Before
	 public void initMocks() throws Exception {
		 propertyUtil= Mockito.mock(PropertyUtil.class);
		 logger=Mockito.mock(LogUtils.class);
		 rsvo=Mockito.mock(RetrieveSummaryVO.class);
		 cache=Mockito.mock(CachedDropDownValues.class);
			
		 

	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
	     Whitebox.setInternalState(FindLaboratoryReportDAOImpl.class,"logger", logger);
     }
	
	@Test
	 public void  assembleLabReportData_test() throws Exception{
		 findLaboratoryReportDAOImpl = PowerMockito.spy(new FindLaboratoryReportDAOImpl());
		 PowerMockito.whenNew(RetrieveSummaryVO.class).withNoArguments().thenReturn(rsvo);
		 PowerMockito.whenNew(CachedDropDownValues.class).withNoArguments().thenReturn(cache);
	     ArrayList<Object> queueList=retriveCustomQueueVOList();
  	     when(rsvo.getAssociatedInvListVersion2(any(Long.class), any(NBSSecurityObj.class), any(String.class))).thenReturn(null);
	     PowerMockito.doNothing().when(findLaboratoryReportDAOImpl,"getTestAndSusceptibilitiesDRRQ", any(ArrayList.class),any(PatientSrchResultVO.class),any(LabReportSummaryVO.class));
	     when(cache.getJurisdictionDesc(any(String.class))).thenReturn(null);
	     Object[] oParams=new Object[] {queueList,nbsSecurityObj};
         ArrayList<Object> arr= Whitebox.invokeMethod(findLaboratoryReportDAOImpl, "assembleLabReportData", oParams);
		 Assert.assertEquals(this.expectedValue,arr.size());
		 System.out.println("Method Name: assembleLabReportData,  iteration   "+iteration+". Expected value: "+this.expectedValue+" Actual value: "+arr.size());
		 System.out.println("PASSED");

	 }
	
	
	private ArrayList<Object> retriveCustomQueueVOList(){
		ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
			PersonSearchResultTmp searchResultName= null;
			for(int i=0; i < this.customVoCollection; i++) {
				searchResultName=new PersonSearchResultTmp();
				searchResultName.setLocalId("1234"+i);
				searchResultName.setCaseClassCd("123"+i);
				searchResultName.setCity("Atl"+i);
				reportCustomQueues.add(searchResultName);
			}
			return reportCustomQueues;
	}
	
	
	
	 @SuppressWarnings("rawtypes")
	  @Parameterized.Parameters
	   public static Collection input() {
			return Arrays.asList(new Object[][] {
				{100,0,"select","where", 3,3, 1 },
				{100,0,"select","where", 3,3, 2 },
				{100,0,"select","where",4,4, 3 },
				{100,0,"select","where", 4,4, 4 },
				{100,0,"select","where", 5,5, 5 },
				{100,0,"select","where", 8,8, 6 },
				{100,0,"select","where",3,3, 7 },
				{100,0,"select","where", 4,4, 8 },
				{100,0,"select","where",3,3, 9 }
						
			});
				
			}
				
	
}


}


	 