package test.gov.cdc.nedss.proxy.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;

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
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindOrganizationDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindPersonDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;




@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.util.EntityProxyHelper","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl"})
@RunWith(Enclosed.class)
@PrepareForTest({EntityProxyHelper.class})
@PowerMockIgnore("javax.management.*")
public class EntityProxyHelper_tests {
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.util.EntityProxyHelper","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityProxyHelper.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class FindOrganizationsByKeyWords_test {
	private int cacheNumber;
	private int fromIndex;
	private int expectedValueForLab;
	private int expectedValueForInvestgation;
	private int expectedValueForProvider;
	private int expectedValueForOrg;
	private int iteration;
	private String finalQuery;
	
	
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	
	@Mock
	 FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl;
	
	@Mock
	 FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Mock
	 PatientSearchVO patientSearchVO;
	@Mock
	ProviderSearchVO theProviderSearchVO;
	@Mock
	OrganizationSearchVO theOrganizationSearchVO;
	@Mock
	 FindPersonDAOImpl findPersonDAOImpl ;
	
	
	@Mock
	FindOrganizationDAOImpl findOrganizationDAOImpl ;
	
	
	@Mock
	 SearchResultDAOImpl searchResultDAOImpl;

	@Spy 
	@InjectMocks	
	EntityProxyHelper entityProxyHelper = Mockito.spy(EntityProxyHelper.class);

	@Mock
	LogUtils logger;
	
	public FindOrganizationsByKeyWords_test(String finalQuery,int cacheNumber, int fromIndex, int expectedValueForLab,
			int expectedValueForInvestgation, int expectedValueForProvider, int expectedValueForOrg, int it) {
		this.cacheNumber = cacheNumber;
		this.fromIndex = fromIndex;
		this.expectedValueForLab = expectedValueForLab;
		this.expectedValueForInvestgation = expectedValueForInvestgation;
		this.expectedValueForProvider = expectedValueForProvider;
		this.expectedValueForOrg = expectedValueForOrg;
		this.finalQuery=finalQuery;
		this.iteration=it;
	}
	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 findOrganizationDAOImpl = Mockito.mock(FindOrganizationDAOImpl.class);
			
		 
		 Whitebox.setInternalState(EntityProxyHelper.class, "logger", logger);
	 }
		 
		
	 private ArrayList<Object> retrieveOrganizations(){
		 ArrayList<Object>   displayOrganizationList  = new ArrayList<Object> ();
		 displayOrganizationList.add( new DisplayOrganizationList(1, new ArrayList<Object>(), 0,0));
		 displayOrganizationList.add(new DisplayOrganizationList(1, new ArrayList<Object>(), 0,0));
		 displayOrganizationList.add( new DisplayOrganizationList(1, new ArrayList<Object>(), 0,0));
		 displayOrganizationList.add(new DisplayOrganizationList(1, new ArrayList<Object>(), 0,0));
		 return displayOrganizationList;
	  }
	 
	 
	 @Test
	 public void findOrganizationsByKeyWords_test() throws Exception {
		 PowerMockito.whenNew(OrganizationSearchVO.class).withNoArguments().thenReturn(theOrganizationSearchVO);
		 PowerMockito.whenNew(FindOrganizationDAOImpl.class).withNoArguments().thenReturn(findOrganizationDAOImpl);
		 ArrayList<Object>   list=retrieveOrganizations();
		 when(findOrganizationDAOImpl.findOrganizationsByKeyWords(any(OrganizationSearchVO.class),anyInt(),  anyInt())).thenReturn(list);
		 ArrayList<Object> ar=entityProxyHelper.findOrganizationsByKeyWords(theOrganizationSearchVO, this.cacheNumber, this.fromIndex);
		 Assert.assertEquals(expectedValueForOrg, ar.size());
		 System.out.println("Method:findOrganization, iteration::::"+this.iteration+", Expected Result:"+this.expectedValueForOrg+", Actual Result:"+ar.size()+", RESULT::::PASSED");  
	 }

	 

	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  {"select",100,0,2,4,4,4,1}
	  
	       });
	   }
		
}


@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.util.EntityProxyHelper","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityProxyHelper.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class FindProvidersByKeyWords_test {
	private int cacheNumber;
	private int fromIndex;
	private int expectedValueForLab;
	private int expectedValueForInvestgation;
	private int expectedValueForProvider;
	private int expectedValueForOrg;
	private int iteration;
	private String finalQuery;
	
	
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	
	@Mock
	 FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl;
	
	@Mock
	 FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Mock
	 PatientSearchVO patientSearchVO;
	@Mock
	ProviderSearchVO theProviderSearchVO;
	@Mock
	OrganizationSearchVO theOrganizationSearchVO;
	@Mock
	 FindPersonDAOImpl findPersonDAOImpl ;
	
	
	@Mock
	FindOrganizationDAOImpl findOrganizationDAOImpl ;
	
	
	@Mock
	 SearchResultDAOImpl searchResultDAOImpl;


	@Spy 
	@InjectMocks	
	EntityProxyHelper entityProxyHelper = Mockito.spy(EntityProxyHelper.class);
	
	@Mock
	LogUtils logger;
	
	public FindProvidersByKeyWords_test(String finalQuery,int cacheNumber, int fromIndex, int expectedValueForLab,
			int expectedValueForInvestgation, int expectedValueForProvider, int expectedValueForOrg, int it) {
		this.cacheNumber = cacheNumber;
		this.fromIndex = fromIndex;
		this.expectedValueForLab = expectedValueForLab;
		this.expectedValueForInvestgation = expectedValueForInvestgation;
		this.expectedValueForProvider = expectedValueForProvider;
		this.expectedValueForOrg = expectedValueForOrg;
		this.finalQuery=finalQuery;
		this.iteration=it;
	}
	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 patientSearchVO = Mockito.mock(PatientSearchVO.class);
		 findPersonDAOImpl = Mockito.mock(FindPersonDAOImpl.class);
			
		 
		 nbsSecurityObjMock = Mockito.mock(NBSSecurityObj.class);
		 findLaboratoryReportDAOImpl = Mockito.mock(FindLaboratoryReportDAOImpl.class);
		 findInvestigationDAOImpl = Mockito.mock(FindInvestigationDAOImpl.class);
		// patientSearchVO = Mockito.mock(PatientSearchVO.class);
		 theProviderSearchVO = Mockito.mock(ProviderSearchVO.class);
		 theOrganizationSearchVO = Mockito.mock(OrganizationSearchVO.class);
		// findPersonDAOImpl = Mockito.mock(FindPersonDAOImpl.class);
		 findOrganizationDAOImpl = Mockito.mock(FindOrganizationDAOImpl.class);
		 searchResultDAOImpl = Mockito.mock(SearchResultDAOImpl.class);
				
		
			
			
		 Whitebox.setInternalState(EntityProxyHelper.class, "logger", logger);
	 }
		 

	 
	 @Test
	 public void findProvidersByKeyWords_test() throws Exception {
		 PowerMockito.whenNew(ProviderSearchVO.class).withNoArguments().thenReturn(theProviderSearchVO);
		 PowerMockito.whenNew(FindPersonDAOImpl.class).withNoArguments().thenReturn(findPersonDAOImpl);
		 when(patientSearchVO.getReportType()).thenReturn("LR");
		 ArrayList<Object>   list=retriveRecordsPerQueueForInvest();
		 when(findPersonDAOImpl.findProvidersByKeyWords(any(ProviderSearchVO.class),anyInt(),  anyInt() )).thenReturn(list);
		 ArrayList<Object> ar=entityProxyHelper.findProvidersByKeyWords(theProviderSearchVO, this.cacheNumber, this.fromIndex);
		 Assert.assertEquals(this.expectedValueForProvider, ar.size());
		 System.out.println("Method:findProvider, iteration::::"+this.iteration+", Expected Result:"+this.expectedValueForProvider+", Actual Result:"+ar.size()+", RESULT::::PASSED"); 
	 }
	 

	 private ArrayList<Object> retriveRecordsPerQueueForInvest(){
		 ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
		 reportCustomQueues.add( new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 reportCustomQueues.add(new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 reportCustomQueues.add( new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 reportCustomQueues.add(new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 return reportCustomQueues;
	  }
	 
	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  {"select",100,0,2,4,4,4,1}
	  
	       });
	   }
		
}
		

/**
 * 
 * Comments added by Fatima: while I was refactoring this existing test file to convert to inner classes,
 * somehow there are 2 different methods to test the same method, so I couldn't create 2 classes withthe same name.
 * Without going into more details to see why that was done that way in the past, I named them:
 *
 * - FindPatientsByQuery_test
 * - FindPatientsByQuery_LR_test
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.util.EntityProxyHelper","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityProxyHelper.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class FindPatientsByQuery_LR_test {
	private int cacheNumber;
	private int fromIndex;
	private int expectedValueForLab;
	private int expectedValueForInvestgation;
	private int expectedValueForProvider;
	private int expectedValueForOrg;
	private int iteration;
	private String finalQuery;
	
	
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	
	@Mock
	 FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl;
	
	@Mock
	 FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Mock
	 PatientSearchVO patientSearchVO;
	@Mock
	ProviderSearchVO theProviderSearchVO;
	@Mock
	OrganizationSearchVO theOrganizationSearchVO;
	@Mock
	 FindPersonDAOImpl findPersonDAOImpl ;
	
	
	@Mock
	FindOrganizationDAOImpl findOrganizationDAOImpl ;
	
	
	@Mock
	 SearchResultDAOImpl searchResultDAOImpl;

		 
	@Spy 
	@InjectMocks	
	EntityProxyHelper entityProxyHelper = Mockito.spy(EntityProxyHelper.class);

	
	@Mock
	LogUtils logger;
	
	public FindPatientsByQuery_LR_test(String finalQuery,int cacheNumber, int fromIndex, int expectedValueForLab,
			int expectedValueForInvestgation, int expectedValueForProvider, int expectedValueForOrg, int it) {
		this.cacheNumber = cacheNumber;
		this.fromIndex = fromIndex;
		this.expectedValueForLab = expectedValueForLab;
		this.expectedValueForInvestgation = expectedValueForInvestgation;
		this.expectedValueForProvider = expectedValueForProvider;
		this.expectedValueForOrg = expectedValueForOrg;
		this.finalQuery=finalQuery;
		this.iteration=it;
	}
	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 patientSearchVO = Mockito.mock(PatientSearchVO.class);
		 findLaboratoryReportDAOImpl = Mockito.mock(FindLaboratoryReportDAOImpl.class);
			 
			
		 Whitebox.setInternalState(EntityProxyHelper.class, "logger", logger);
	 }
		 

	 
	 
	 
	 @Test
	 public void findPatientsByQuery_LR_test() throws Exception {
		 PowerMockito.whenNew(PatientSearchVO.class).withNoArguments().thenReturn(patientSearchVO);
		 PowerMockito.whenNew(FindLaboratoryReportDAOImpl.class).withNoArguments().thenReturn(findLaboratoryReportDAOImpl);
		 PowerMockito.whenNew(FindInvestigationDAOImpl.class).withNoArguments().thenReturn(findInvestigationDAOImpl);
		 when(patientSearchVO.getReportType()).thenReturn("LR");
		 ArrayList<Object>   list=retriveRecordsPerQueueForLab();
		 when(findLaboratoryReportDAOImpl.findPatientsByQuery(any(PatientSearchVO.class),any(String.class),  anyInt(),  anyInt(), any(NBSSecurityObj.class))).thenReturn(list);
		 ArrayList<Object> ar=entityProxyHelper.findPatientsByQuery(patientSearchVO, this.finalQuery, this.cacheNumber, this.fromIndex, nbsSecurityObjMock);
		 Assert.assertEquals(this.expectedValueForLab, ar.size());
		 System.out.println("Method:findPatientsByQuery_LR, iteration::::"+this.iteration+", Expected Result:"+this.expectedValueForLab+", Actual Result:"+ar.size()+", RESULT::::PASSED"); 

	 } 
	 private ArrayList<Object> retriveRecordsPerQueueForLab(){
		 ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
		 reportCustomQueues.add( new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 reportCustomQueues.add(new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 return reportCustomQueues;
	  }
	 

	 
	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  {"select",100,0,2,4,4,4,1}
	  
	       });
	   }
		
}
		



@SuppressStaticInitializationFor ({"gov.cdc.nedss.proxy.util.EntityProxyHelper","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.SearchResultDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindInvestigationDAOImpl","gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindLaboratoryReportDAOImpl"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityProxyHelper.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class FindPatientsByQuery_test {
	private int cacheNumber;
	private int fromIndex;
	private int expectedValueForLab;
	private int expectedValueForInvestgation;
	private int expectedValueForProvider;
	private int expectedValueForOrg;
	private int iteration;
	private String finalQuery;
	
	
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	
	@Mock
	 FindLaboratoryReportDAOImpl findLaboratoryReportDAOImpl;
	
	@Mock
	 FindInvestigationDAOImpl findInvestigationDAOImpl;
	
	@Mock
	 PatientSearchVO patientSearchVO;
	@Mock
	ProviderSearchVO theProviderSearchVO;
	@Mock
	OrganizationSearchVO theOrganizationSearchVO;
	@Mock
	 FindPersonDAOImpl findPersonDAOImpl ;
	
	
	@Mock
	FindOrganizationDAOImpl findOrganizationDAOImpl ;
	
	
	@Mock
	 SearchResultDAOImpl searchResultDAOImpl;

		 
	@Spy 
	@InjectMocks	
	EntityProxyHelper entityProxyHelper = Mockito.spy(EntityProxyHelper.class);

	
	@Mock
	LogUtils logger;
	
	public FindPatientsByQuery_test(String finalQuery,int cacheNumber, int fromIndex, int expectedValueForLab,
			int expectedValueForInvestgation, int expectedValueForProvider, int expectedValueForOrg, int it) {
		this.cacheNumber = cacheNumber;
		this.fromIndex = fromIndex;
		this.expectedValueForLab = expectedValueForLab;
		this.expectedValueForInvestgation = expectedValueForInvestgation;
		this.expectedValueForProvider = expectedValueForProvider;
		this.expectedValueForOrg = expectedValueForOrg;
		this.finalQuery=finalQuery;
		this.iteration=it;
	}
	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 patientSearchVO = Mockito.mock(PatientSearchVO.class);
		 findInvestigationDAOImpl = Mockito.mock(FindInvestigationDAOImpl.class);
		 
			
		 Whitebox.setInternalState(EntityProxyHelper.class, "logger", logger);
	 }
		 
		
	 @Test
	 public void findPatientsByQuery_test() throws Exception {
		 PowerMockito.whenNew(PatientSearchVO.class).withNoArguments().thenReturn(patientSearchVO);
		 PowerMockito.whenNew(FindLaboratoryReportDAOImpl.class).withNoArguments().thenReturn(findLaboratoryReportDAOImpl);
		 PowerMockito.whenNew(FindInvestigationDAOImpl.class).withNoArguments().thenReturn(findInvestigationDAOImpl);
		 when(patientSearchVO.getReportType()).thenReturn("I");
		 ArrayList<Object>   list=retriveRecordsPerQueueForInvest();
		 when(findInvestigationDAOImpl.findPatientsByQuery(any(PatientSearchVO.class),any(String.class),  anyInt(),  anyInt(), any(NBSSecurityObj.class))).thenReturn(list);
		 ArrayList<Object> ar=entityProxyHelper.findPatientsByQuery(patientSearchVO, finalQuery, cacheNumber, fromIndex, nbsSecurityObjMock);
		 Assert.assertEquals(this.expectedValueForInvestgation, ar.size());
		 System.out.println("Method:findPatientsByQuery_I, iteration::::"+this.iteration+", Expected Result:"+this.expectedValueForInvestgation+", Actual Result:"+ar.size()+", RESULT::::PASSED"); 

	 
	 }		 

	 
	 private ArrayList<Object> retriveRecordsPerQueueForInvest(){
		 ArrayList<Object>   reportCustomQueues  = new ArrayList<Object> ();
		 reportCustomQueues.add( new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 reportCustomQueues.add(new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 reportCustomQueues.add( new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 reportCustomQueues.add(new DisplayPersonList(1, new ArrayList<Object>(), 0,0));
		 return reportCustomQueues;
	  }
	 
	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  {"select",100,0,2,4,4,4,1}
	  
	       });
	   }
		
}
		
		
	
}