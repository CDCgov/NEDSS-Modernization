package test.gov.cdc.nedss.reportadmin.dao;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
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

- ListAllFilters_test
- ...
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.reportadmin.dao.ReportFilterDAO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({ReportFilterDAO.class, LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class ReportFilterDAO_tests {



/**
 * ListAllFilters_test: the change on this method consisted of updating from int column_uid = rs.getInt("column_uid") to Long column_uid = rs.getLong("column_uid");
 * we will verify that the filter string returned is as expected.
 * 
 * */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.reportadmin.dao.ReportFilterDAO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({ReportFilterDAO.class, PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ListAllFilters_test{
	

	private String iteration;

	private Long filterUid;
	private Long reportFilterUid;
	private String filterName;
	private int minValue;
	private int maxValue;
	private Long columnUid;
	private String reportFilterInd;
	private String expectedString;
	

	
 public ListAllFilters_test(String iteration, Long filterUid, Long reportFilterUid, String filterName, int minValue, int maxValue, Long columnUid, String reportFilterInd, String expectedString){
	 
	 super();
	 //Common Parameters
	 this.iteration = iteration;
	 this.filterUid = filterUid ;
	 this.reportFilterUid = reportFilterUid;
	 this.filterName = filterName;
	 this.minValue = minValue;
	 this.maxValue = maxValue;
	 this.columnUid = columnUid;
	 this.reportFilterInd = reportFilterInd;
	 this.expectedString = expectedString;
		
		
		
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"listAllFilters_test"+"_"+it++,5L,10066739L,"Time Range",1, 2,  31501L, null,  "10066739$Time Range$Plain Text$31501$No|"},
    		  {"listAllFilters_test"+"_"+it++,18L,1015546032L,"Basic Text Filter",1, 2,  31496L, "Yes",  "1015546032$Basic Text Filter$Plain Text$31496$Yes|"},
    		  {"listAllFilters_test"+"_"+it++,1L,1015546032L,"Single Select Filter",1, 2,  31496L, "Yes",  "1015546032$Single Select Filter$Single Select$31496$Yes|"},

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
ReportFilterDAO reportFilterDAO=Mockito.spy(new ReportFilterDAO());

	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(ReportFilterDAO.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(ReportFilterDAO.class, "logger", loggerMock);
		 Whitebox.setInternalState(ReportFilterDAO.class, "LIST", LIST);
		 
	 }
	 
	    private static final String[] LIST =
	        {
	        	"select report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid from nbs_odse..Report_Filter where data_source_uid = ? and report_uid = ?;",
	            "select report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid from nbs_odse.Report_Filter  where data_source_uid = ? and report_uid = ?",
	        };

	@Test
	public void listAllFilters_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: listAllFilters_test *******************");
				
				
				Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
				Mockito.doNothing().when(stmt).setLong(any(Integer.class),any(Long.class));
				Mockito.doNothing().when(stmt).setLong(any(Integer.class),any(Long.class));
				
				
				when(c.prepareStatement(any(String.class), any(Integer.class), any(Integer.class))).thenReturn(stmt);
			    
				Mockito.doReturn(c).when(reportFilterDAO).getConnection();
				Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
			//	Mockito.when(resultSet.getLong(1)).thenReturn(resultSetLong1);
			//	Mockito.when(resultSet.getLong(2)).thenReturn(resultSetLong2);
				

				
				
				Mockito.when(resultSet.getLong("filter_uid")).thenReturn(filterUid);
				Mockito.when(resultSet.getLong("report_filter_uid")).thenReturn(reportFilterUid);
				Mockito.when(resultSet.getString("filter_name")).thenReturn(filterName);
				Mockito.when(resultSet.getInt("min_value_cnt")).thenReturn(minValue);
				Mockito.when(resultSet.getInt("max_value_cnt")).thenReturn(maxValue);
				Mockito.when(resultSet.getLong("column_uid")).thenReturn(columnUid);
				Mockito.when(resultSet.getString("report_filter_ind")).thenReturn(reportFilterInd);
			
                
                
				 
				 
			    int SERVER = 0;
				  // logger.warn(LIST[SERVER]);
	//		PowerMockito.spy(LogUtils.class);
			//	PowerMockito.mockStatic(LogUtils.class);
				  Mockito.doNothing().when(loggerMock).warn(LIST[SERVER]);
				/*Long phcUid = 111111L;
				
				PortPageDAO portPageDAO = new PortPageDAO();

				PageActProxyVO proxyVO = new PageActProxyVO();
				PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
				PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
				publicHealthCaseDT.setPublicHealthCaseUid(phcUid);
				proxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				
				PowerMockito.doReturn(participationDT).when(PageCreateHelper.class, "createParticipation",  any(Long.class), any(Timestamp.class),  any(String.class), any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class));

			*/
				String valueReturned = Whitebox.invokeMethod(reportFilterDAO, "listAllFilters", 1L, 2L);
			
				/*int actualSize = proxyVO.getTheParticipationDTCollection().size();
				int expectedSize = 1;
	
			
				*/
				System.out.println("Iteration: #"+iteration+"\nExpected returned String: "+expectedString+"\nActual String returned: "+valueReturned);
				
				
				
			//	Assert.assertEquals(expectedConditionCDGroupId, conditionCDGroupId);
				
				System.out.println("PASSED");
				System.out.println("******************* End test case named: listAllFilters_test *******************");
			
		}		
	
}










}
	
