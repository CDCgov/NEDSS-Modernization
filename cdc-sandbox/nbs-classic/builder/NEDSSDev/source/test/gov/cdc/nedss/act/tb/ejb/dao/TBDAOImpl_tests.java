package test.gov.cdc.nedss.act.tb.ejb.dao;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.tb.ejb.dao.TBDAOImpl;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.act.tb.ejb.dao.TBDAOImpl","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({LogUtils.class,TBDAOImpl.class, PreparedStatement.class,ResultSetUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class TBDAOImpl_tests {



@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.act.tb.ejb.dao.TBDAOImpl","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({LogUtils.class,TBDAOImpl.class, PreparedStatement.class,ResultSetUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SelectCaseNumber_test{
	

	private String iteration;
	private String publicHealthCaseUid;
	private String caseNumber;
	private String selectedPhcUid;
	private String expectedResult;
	private String tbConditions;//conditions considered TB case, they are the ones from nbs_configuration.config_key = TB_CONDITION_CODES plus an additional one
	
	
 public SelectCaseNumber_test(String it, String publicHealthCaseUid, String caseNumber, String selectedPhcUid, String expectedResult, String tbConditions){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.publicHealthCaseUid = publicHealthCaseUid;
	 this.caseNumber = caseNumber;
	 this.selectedPhcUid = selectedPhcUid;
	 this.expectedResult = expectedResult;
	 this.tbConditions = tbConditions;

	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   

	   
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  //validateRule1_test
    		//    it,                 phc of the current investigation,     case number,  public health case uid found (different than current), result from method
    		  {"selectCaseNumber_test"+"_"+it++,"1015570004","1212","1015573002","1015573002",""},
    		  {"selectCaseNumber_test"+"_"+it++,"10032318","2008-OK-ABCD56789","","","102201"},
    		  {"selectCaseNumber_test"+"_"+it++,"1015570004","1212","1015573001","1015573001", "102201,502582"},
    		  {"selectCaseNumber_test"+"_"+it++,"1015588004","2222-AA-222222222","","","102201,502582,10220"},
    		  {"selectCaseNumber_test"+"_"+it++,"1015576008","123123131321312","","","102201,10220"},
    		  {"selectCaseNumber_test"+"_"+it++,"1015576008","","","","502582,10220"},
    		  
    		   
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

	//For the database connection under the method we are testing
	@org.mockito.Mock
    Connection c;
	@org.mockito.Mock
    PreparedStatement stmt;
	//@org.mockito.Mock
  //  DataSource ds;
	@org.mockito.Mock
	ResultSet resultSet;
	
    @org.mockito.Mock
    ResultSetUtils resultSetUtils;

	@Mock
	PageClientVO pageClientVO;
	
	
	@org.mockito.Mock
	LogUtils loggerMock;
	
	
	@Spy
	@InjectMocks
	TBDAOImpl tbDAOImpl = Mockito.spy(TBDAOImpl.class);

	 @Before
	 public void initMocks() throws Exception {

		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(TBDAOImpl.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void selectCaseNumber_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: selectCaseNumber_test *******************");
			
				

				Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
				when(c.prepareStatement(any(String.class))).thenReturn(stmt);
				Mockito.doReturn(c).when(tbDAOImpl).getConnection();
				Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
				Mockito.when(resultSet.getString(1)).thenReturn(this.selectedPhcUid);
				

				String actualResult = Whitebox.invokeMethod(tbDAOImpl,"selectCaseNumber", caseNumber, publicHealthCaseUid, tbConditions);
				
				
				
				System.out.println("Iteration: #"+iteration+"\nExpected value: "+expectedResult+"\nActual value: "+actualResult);
				Assert.assertEquals(expectedResult, actualResult);

				System.out.println("PASSED");
				System.out.println("******************* End test case named: selectCaseNumber_test *******************");
			
		}		
	
}
	




}
	
