package test.gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.isNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationPersonInfoVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(Enclosed.class)
@PrepareForTest({SearchResultDAOImpl.class})
@PowerMockIgnore("javax.management.*")
public class FindInvestigationDAOImpl_tests  {
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
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
	
	public FindPatientsByQuery_test(int cacheNumber,int fromIndex,String finalQuery, String whereClause, int customVoCollection,int expectedValue,int iteration) {
		this.cacheNumber=cacheNumber;
		this.fromIndex=fromIndex;
		this.finalQuery=finalQuery;
		this.whereClause=whereClause;
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
	CachedDropDownValues cache;
	
	@Mock 
	HashMap investigationPersonInfoVOMap;
	
	@Mock
	PersonSearchResultTmp curInvestigation;
    
	@InjectMocks
	FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Before
	 public void initMocks() throws Exception {
		 propertyUtil= Mockito.mock(PropertyUtil.class);
		 logger=Mockito.mock(LogUtils.class);
		 nbsSecurityObj=Mockito.mock(NBSSecurityObj.class);
		 patientSearchVO=Mockito.mock(PatientSearchVO.class);
		 
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
	     Whitebox.setInternalState(FindInvestigationDAOImpl.class,"logger", logger);
     }
	
	/**
     * Investigation custom  queues validated By this testcase.
     * @throws Exception
     */
	
	@Test
	 public void findPatientsByQuery_test() throws Exception{
		 findInvestigationDAOImpl = PowerMockito.spy(new FindInvestigationDAOImpl());
		 PowerMockito.whenNew(PatientSearchVO.class).withNoArguments().thenReturn(patientSearchVO);
		 when(propertyUtil.getMyProgramAreaSecurity()).thenReturn("VIEW");
         when(nbsSecurityObj.getDataAccessWhereClause(any(String.class),any(String.class), any(String.class))).thenReturn("where");
         ArrayList<Object> queueList=retriveCustomQueueVOList();
         when(patientSearchVO.getReportType()).thenReturn("I");
         Mockito.doReturn(new Integer(3)).doReturn(queueList).when(findInvestigationDAOImpl).preparedStmtMethod(any(PersonSearchResultTmp.class),any(ArrayList.class),any(String.class),any(String.class));
         PowerMockito.doReturn(returnAssembleData()).when(findInvestigationDAOImpl, "assembleInvestigationData", any(ArrayList.class),any(String.class));
         Object[] oParams=new Object[] {patientSearchVO,this.finalQuery,this.cacheNumber,this.fromIndex,nbsSecurityObj};
         ArrayList<Object> arr= Whitebox.invokeMethod(findInvestigationDAOImpl, "findPatientsByQuery", oParams);
		 DisplayPersonList dpl=(DisplayPersonList)arr.get(0);
		 Assert.assertEquals(this.expectedValue,dpl.getList().size());
		 System.out.println("Method Name: findPatientsByQuery,  Iteration: "+iteration+". Expected value: "+this.expectedValue+" Actual value: "+dpl.getList().size()+", RESULT::::::PASSED");
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
				{ 100,0,"select","where",3,3, 1 },
				{ 100,0,"select","where",3,3, 2 },
				{ 100,0,"select","where",4,4, 3 },
				{ 100,0,"select","where",4,4, 4 },
				{ 100,0,"select","where",5,5, 5 },
				{ 100,0,"select","where",3,3, 6 }
							
			});
				
			}
				
	
}



@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchResultDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class AssembleInvestigationData_test  {
	private int cacheNumber;
	private int fromIndex;
    private String finalQuery;
    private String whereClause;
    private int expectedValue;
	private int iteration;
	private int customVoCollection;
	
	public AssembleInvestigationData_test(int cacheNumber,int fromIndex,String finalQuery, String whereClause, int customVoCollection,int expectedValue,int iteration) {
		this.cacheNumber=cacheNumber;
		this.fromIndex=fromIndex;
		this.finalQuery=finalQuery;
		this.whereClause=whereClause;
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
	CachedDropDownValues cache;
	
	@Mock 
	HashMap investigationPersonInfoVOMap;
	
	@Mock
	PersonSearchResultTmp curInvestigation;
    
	@InjectMocks
	FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Before
	 public void initMocks() throws Exception {
		 propertyUtil= Mockito.mock(PropertyUtil.class);
		 logger=Mockito.mock(LogUtils.class);
		 curInvestigation=Mockito.mock(PersonSearchResultTmp.class);
		 cache=Mockito.mock(CachedDropDownValues.class);
			
			
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
	     Whitebox.setInternalState(FindInvestigationDAOImpl.class,"logger", logger);
     }
	
	
	@Test
	 public void assembleInvestigationData_test() throws Exception{
		 System.out.println("*****************************************************");
		 System.out.println("*********** Iteration"+iteration+"*******************");
		 System.out.println("*****************************************************");
		 findInvestigationDAOImpl = PowerMockito.spy(new FindInvestigationDAOImpl());
	     PowerMockito.whenNew(CachedDropDownValues.class).withNoArguments().thenReturn(cache);
	     PowerMockito.whenNew(PersonSearchResultTmp.class).withNoArguments().thenReturn(curInvestigation);
	     ArrayList<Object> queueList=retriveCustomQueueVOList();
  	     when(cache.getDescForCode(any(String.class),any(String.class))).thenReturn(null);
  	     when(cache.getJurisdictionDesc(any(String.class))).thenReturn(null);
  	     when(curInvestigation.getPublicHealthCaseUid()).thenReturn(new Long(01234567));
  	     PowerMockito.doReturn(buildInvestigationPersonInfoVOMap()).when(findInvestigationDAOImpl, "getPersonInfoForExistingQueues");
	     Object[] oParams=new Object[] {queueList,this.finalQuery};
         ArrayList<Object> arr= Whitebox.invokeMethod(findInvestigationDAOImpl, "assembleInvestigationData", oParams);
		 Assert.assertEquals(this.expectedValue,arr.size());
		 System.out.println("Method Name: assembleInvestigationData,  iteration   "+iteration+". Expected value: "+this.expectedValue+" Actual value: "+arr.size()+", RESULT::::::PASSED");
	 }
	

	private ArrayList<Object> retriveCustomQueueVOList(){
		ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
			//PersonSearchResultTmp searchResultName= null;
			for(int i=0; i < this.customVoCollection; i++) {
				PersonSearchResultTmp searchResultName=new PersonSearchResultTmp();
				searchResultName.setLocalId("1234"+i);
				searchResultName.setCaseClassCd("123"+i);
				searchResultName.setCity("Atl"+i);
				reportCustomQueues.add(searchResultName);
			}
			return reportCustomQueues;
	}
	
	
	private Map<Long, Object> buildInvestigationPersonInfoVOMap(){
		HashMap<Long, Object> investigationPersonInfoVOMap = new HashMap<Long, Object>();
		InvestigationPersonInfoVO investigationPersonInfoVO=null;
		investigationPersonInfoVO=new InvestigationPersonInfoVO();
		investigationPersonInfoVO.setPublicHealthCaseUid(new Long(01234567));
		investigationPersonInfoVO.setFirstNm("xyz");
		investigationPersonInfoVO.setLastNm("abc");
		investigationPersonInfoVO.setCurrSexCd("F");
		investigationPersonInfoVO.setPersonParentUid(new Long(78976));
		investigationPersonInfoVO.setPersonLocalId("78976");
		investigationPersonInfoVO.setBirthTime(new Timestamp(System.currentTimeMillis()));
		investigationPersonInfoVOMap.put(new Long(01234567), investigationPersonInfoVO);
		return investigationPersonInfoVOMap;
	}
	
	
	
	 @SuppressWarnings("rawtypes")
	  @Parameterized.Parameters
	   public static Collection input() {
			return Arrays.asList(new Object[][] {
				{ 100,0,"select","where",3,3, 1 },
				{ 100,0,"select","where",3,3, 2 },
				{ 100,0,"select","where",4,4, 3 },
				{ 100,0,"select","where",4,4, 4 },
				{ 100,0,"select","where",5,5, 5 },
				{ 100,0,"select","where",3,3, 6 }
							
			});
				
			}
				
	
}


@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchResultDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class GetPersonInfoForExistingQueues  {
	private int cacheNumber;
	private int fromIndex;
    private String finalQuery;
    private String whereClause;
    private int expectedValue;
	private int iteration;
	private int customVoCollection;
	
	public GetPersonInfoForExistingQueues(int cacheNumber,int fromIndex,String finalQuery, String whereClause, int customVoCollection,int expectedValue,int iteration) {
		this.cacheNumber=cacheNumber;
		this.fromIndex=fromIndex;
		this.finalQuery=finalQuery;
		this.whereClause=whereClause;
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
	CachedDropDownValues cache;
	
	@Mock 
	HashMap investigationPersonInfoVOMap;
	
	@Mock
	PersonSearchResultTmp curInvestigation;
    
	@InjectMocks
	FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Before
	 public void initMocks() throws Exception {
		 propertyUtil= Mockito.mock(PropertyUtil.class);
		 logger=Mockito.mock(LogUtils.class);
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
	     Whitebox.setInternalState(FindInvestigationDAOImpl.class,"logger", logger);
     }
	
	
	
	@SuppressWarnings({"unchecked","unused"})
	@Test
	public void getPersonInfoForExistingQueues() throws Exception{
		findInvestigationDAOImpl = PowerMockito.spy(new FindInvestigationDAOImpl());
     	Mockito.doReturn(buildInvestigationPersonInfoVOMapTest()).when(findInvestigationDAOImpl).preparedStmtMethodForMap(any(InvestigationPersonInfoVO.class), isNull(ArrayList.class),any(String.class),any(String.class),any(String.class));
		Map<Long, Object> personsMap= Whitebox.invokeMethod(findInvestigationDAOImpl, "getPersonInfoForExistingQueues");
		Collection<Object> personList = (Collection<Object>) personsMap.get(new Long(1111111));
		System.out.println("Method Name: getPersonInfoForExistingQueues,  iteration   "+iteration+". Expected value: "+this.expectedValue+" Actual value: "+personList.size());
		Assert.assertEquals(this.expectedValue,personList.size());
		System.out.println("PASSED");
	}
	
    
	private Map<Long, Object> buildInvestigationPersonInfoVOMapTest(){
		HashMap<Long, Object> investigationPersonInfoVOMap = new HashMap<Long, Object>();
		InvestigationPersonInfoVO investigationPersonInfoVO=null;
		ArrayList<Object>   assembleList  = new ArrayList<Object> ();
		for(int i=0; i< this.customVoCollection; i++) {
			investigationPersonInfoVO=new InvestigationPersonInfoVO();
			investigationPersonInfoVO.setPublicHealthCaseUid(new Long(01234567)+i);
			investigationPersonInfoVO.setFirstNm("xyz"+i);
			investigationPersonInfoVO.setLastNm("abc"+i);
			investigationPersonInfoVO.setCurrSexCd("F");
			investigationPersonInfoVO.setPersonParentUid(new Long(78976)+i);
			investigationPersonInfoVO.setPersonLocalId("78976"+i);
			investigationPersonInfoVO.setBirthTime(new Timestamp(System.currentTimeMillis()));
			assembleList.add(investigationPersonInfoVO);
		}
		investigationPersonInfoVOMap.put(new Long(1111111), assembleList);
		return investigationPersonInfoVOMap;
	}
	
	
	 @SuppressWarnings("rawtypes")
	  @Parameterized.Parameters
	   public static Collection input() {
			return Arrays.asList(new Object[][] {
				{ 100,0,"select","where",3,3, 1 },
				{ 100,0,"select","where",3,3, 2 },
				{ 100,0,"select","where",4,4, 3 },
				{ 100,0,"select","where",4,4, 4 },
				{ 100,0,"select","where",5,5, 5 },
				{ 100,0,"select","where",3,3, 6 }
							
			});
				
			}
				
	
}


@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchResultDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class FindPatientsByKeyWords_test  {
	private int cacheNumber;
	private int fromIndex;
    private String finalQuery;
    private String whereClause;
    private int expectedValue;
	private int iteration;
	private int customVoCollection;
	
	public FindPatientsByKeyWords_test(int cacheNumber,int fromIndex,String finalQuery, String whereClause, int customVoCollection,int expectedValue,int iteration) {
		this.cacheNumber=cacheNumber;
		this.fromIndex=fromIndex;
		this.finalQuery=finalQuery;
		this.whereClause=whereClause;
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
	CachedDropDownValues cache;
	
	@Mock 
	HashMap investigationPersonInfoVOMap;
	
	@Mock
	PersonSearchResultTmp curInvestigation;
    
	@InjectMocks
	FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Before
	 public void initMocks() throws Exception {
		 propertyUtil= Mockito.mock(PropertyUtil.class);
		 logger=Mockito.mock(LogUtils.class);
		 patientSearchVO=Mockito.mock(PatientSearchVO.class);
		 
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
	     Whitebox.setInternalState(FindInvestigationDAOImpl.class,"logger", logger);
     }
	
	
      /**
       * Advanced Search Validated By this testcase.
       * @throws Exception
       */
	@Test
	 public void  findPatientsByKeyWords_test() throws Exception{
		 findInvestigationDAOImpl = PowerMockito.spy(new FindInvestigationDAOImpl());
		 PowerMockito.whenNew(PatientSearchVO.class).withNoArguments().thenReturn(patientSearchVO);
		 PowerMockito.doReturn(queriesList()).when(findInvestigationDAOImpl, "buildSearchQueryInvestigation", any(PatientSearchVO.class),anyInt(),any(NBSSecurityObj.class));
		 when(patientSearchVO.getReportType()).thenReturn("I");
		 Mockito.doReturn(retriveCustomQueueVOList()).when(findInvestigationDAOImpl).preparedStmtMethod(any(PersonSearchResultTmp.class),isNull(ArrayList.class),any(String.class),any(String.class));
		 PowerMockito.doReturn(returnAssembleData()).when(findInvestigationDAOImpl, "assembleInvestigationData", any(ArrayList.class),any(String.class));
		 Object[] oParams=new Object[] {patientSearchVO,this.cacheNumber,this.fromIndex,nbsSecurityObj};
         ArrayList<Object> arr= Whitebox.invokeMethod(findInvestigationDAOImpl, "findPatientsByKeyWords", oParams);
         DisplayPersonList dpl=(DisplayPersonList)arr.get(0);
		 Assert.assertEquals(this.expectedValue,dpl.getList().size());
		 System.out.println("Method Name: findPatientsByKeyWords,  Iteration: "+iteration+". Expected value: "+this.expectedValue+" Actual value: "+dpl.getList().size()+", RESULT::::::PASSED");
	 
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
	
	private ArrayList<Object> queriesList(){
		 ArrayList<Object> queries = new ArrayList<Object>();
		 queries.add("select");
		 queries.add(finalQuery);
		 return queries;
	}
	
	 @SuppressWarnings("rawtypes")
	  @Parameterized.Parameters
	   public static Collection input() {
			return Arrays.asList(new Object[][] {
				{ 100,0,"select","where",3,3, 1 },
				{ 100,0,"select","where",3,3, 2 },
				{ 100,0,"select","where",4,4, 3 },
				{ 100,0,"select","where",4,4, 4 },
				{ 100,0,"select","where",5,5, 5 },
				{ 100,0,"select","where",3,3, 6 }
							
			});
				
			}
				
	
}

}


	 