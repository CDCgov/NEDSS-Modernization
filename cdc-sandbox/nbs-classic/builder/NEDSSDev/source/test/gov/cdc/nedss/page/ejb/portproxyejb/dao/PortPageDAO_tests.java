package test.gov.cdc.nedss.page.ejb.portproxyejb.dao;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.page.ejb.portproxyejb.dao.PortPageDAO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

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





/**
Methods that have been unit tested so far:

- CreateNBSConversionCondition_test
- ...
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.page.ejb.portproxyejb.dao.PortPageDAO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PortPageDAO.class, LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class PortPageDAO_tests {



/**
 * CreateNBSConversionCondition_test: the change on this method consisted of updating from long maxConditionCDGroupId = resultSet.getInt(1) to long maxConditionCDGroupId = resultSet.getLong(1).
 * In order to test this method we will make sure that the method behaves the way that is expected by comparing maxConditionCDGroupId with 100000 and if it is less, it returns 100000, otherwise, maxConditionCDGroupId + 1
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.page.ejb.portproxyejb.dao.PortPageDAO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PortPageDAO.class, PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class CreateNBSConversionCondition_test{
	

	private String iteration;
	private String conditionCd;
	private Long nbsConversionPageMgmtUid;
	private Long resultSetLong1;
	private Long resultSetLong2;
	private Long expectedConditionCDGroupId;
	
	

	
 public CreateNBSConversionCondition_test(String it, String conditionCd, Long nbsConversionPageMgmtUid, Long resultSetLong1, Long resultSetLong2, Long expectedConditionCDGroupId){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.conditionCd = conditionCd;
	 this.nbsConversionPageMgmtUid = nbsConversionPageMgmtUid;
	 this.resultSetLong1 = resultSetLong1;
	 this.resultSetLong2 = resultSetLong2;
	 this.expectedConditionCDGroupId = expectedConditionCDGroupId;
	
		
		
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 1L, 2L, 100000L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 3L, 2L, 100000L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 352L, 2L, 100000L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 1000L, 2L, 100000L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 1234L, 2L, 100000L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 100000L, 2L, 100001L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 2222222L, 2L, 2222223L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 123456789L, 2L, 123456790L},
    		  {"createNBSConversionCondition_test"+"_"+it++,"111111111", 2L, 0L, 2L, 100000L},
    		  
    		  
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */


 //For the database connection under the method we are testing
@Mock
Connection c;
@Mock
PreparedStatement stmt;
//@org.mockito.Mock
//  DataSource ds;
@Mock
ResultSet resultSet;
 	
@Mock
LogUtils loggerMock;



@Mock
HttpServletRequest request;

@Spy
@InjectMocks
PortPageDAO portPage=Mockito.spy(new PortPageDAO());

	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PortPageDAO.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(PortPageDAO.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void createNBSConversionCondition_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: createNBSConversionCondition_test *******************");
				
				
				Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
				when(c.prepareStatement(any(String.class))).thenReturn(stmt);
				Mockito.doReturn(c).when(portPage).getConnection();
				Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
				Mockito.when(resultSet.getLong(1)).thenReturn(resultSetLong1);
				Mockito.when(resultSet.getLong(2)).thenReturn(resultSetLong2);
				
				
				
				/*Long phcUid = 111111L;
				
				PortPageDAO portPageDAO = new PortPageDAO();

				PageActProxyVO proxyVO = new PageActProxyVO();
				PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
				PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
				publicHealthCaseDT.setPublicHealthCaseUid(phcUid);
				proxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				
				PowerMockito.doReturn(participationDT).when(PageCreateHelper.class, "createParticipation",  any(Long.class), any(Timestamp.class),  any(String.class), any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class));

			*/
				Long conditionCDGroupId = Whitebox.invokeMethod(portPage, "createNBSConversionCondition", conditionCd, nbsConversionPageMgmtUid);
			
				/*int actualSize = proxyVO.getTheParticipationDTCollection().size();
				int expectedSize = 1;
	
			
				*/
				System.out.println("Iteration: #"+iteration+"\nExpected returned conditionCDGroupId: "+expectedConditionCDGroupId+"\nActual conditionCDGroupId returned: "+conditionCDGroupId);
				
				
				
				Assert.assertEquals(expectedConditionCDGroupId, conditionCDGroupId);
				
				System.out.println("PASSED");
				System.out.println("******************* End test case named: createNBSConversionCondition_test *******************");
			
		}		
	
}










}
	
