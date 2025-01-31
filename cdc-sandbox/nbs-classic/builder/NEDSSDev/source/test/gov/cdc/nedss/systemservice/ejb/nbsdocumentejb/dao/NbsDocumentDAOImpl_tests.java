package test.gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.reflect.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewDAOImpl;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.DSMUpdateAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import org.mockito.Mockito;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil"})
//@RunWith(PowerMockRunner.class)
@PrepareForTest({NbsDocumentDAOImpl.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class NbsDocumentDAOImpl_tests {
	
	
/**
 * GetDSMUpdateAlgorithmList_test: this test method will pass if the map returned by the method we are testing matches the expected condition value for
 * the combination of condition plus sending system. The map returned is the one we have mocked to be returned from one of the internal method calls.
 * 
 * @author Fatima.Lopezcalzado
 *
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.act.sqlscript.WumSqlQuery"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({NbsDocumentDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class GetDSMUpdateAlgorithmList_test {

	int iteration;
	String conditionCode;
	String sendingSystem;
	String expectedValue;
	
	@Mock
	LogUtils logger;
	
	@Mock
	PropertyUtil propertyUtil;
	
	@InjectMocks
	NbsDocumentDAOImpl nbsDocumentDAOImpl;
	
	public GetDSMUpdateAlgorithmList_test(String conditionCode, String sendingSystem, String expectedValue,int iteration) {
		super();
		this.conditionCode = conditionCode;
		this.sendingSystem = sendingSystem;
		this.expectedValue = expectedValue;
		this.iteration = iteration;
	}
	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 Whitebox.setInternalState(NbsDocumentDAOImpl.class, "logger", logger);
	 }
	
	@Test
	public void getDSMUpdateAlgorithmList_test() throws Exception{
	    nbsDocumentDAOImpl = PowerMockito.spy(new NbsDocumentDAOImpl());
	    Mockito.doReturn(buildUpdateMap()).when(nbsDocumentDAOImpl).preparedStmtMethodForMap(any(DSMUpdateAlgorithmDT.class),any(ArrayList.class),any(String.class),any(String.class),any(String.class));
	    Map<String, DSMUpdateAlgorithmDT> uMap=Whitebox.invokeMethod(nbsDocumentDAOImpl, "getDSMUpdateAlgorithmList");
	    Assert.assertEquals(this.expectedValue,(uMap.get(this.conditionCode+this.sendingSystem)).getConditionCd());
		System.out.println("Class Name: NbsDocumentDAOImpl.java, Method:getDSMUpdateAlgorithmList, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+(uMap.get(this.conditionCode+this.sendingSystem)).getConditionCd()+", RESULT::::PASSED");
	}
	    
	    
	private Map<String, DSMUpdateAlgorithmDT> buildUpdateMap(){
		Long AlogUid=100L;
	    DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT = new DSMUpdateAlgorithmDT();
	    Map<String, DSMUpdateAlgorithmDT> updateMap = new HashMap<String, DSMUpdateAlgorithmDT>();
	    dsmUpdateAlgorithmDT.setDsmUpdateAlgorithmUid(AlogUid++);
	    dsmUpdateAlgorithmDT.setConditionCd(this.conditionCode);
	    dsmUpdateAlgorithmDT.setSendingSystemNm(this.sendingSystem);
	    updateMap.put(this.conditionCode+this.sendingSystem, dsmUpdateAlgorithmDT);
	    return updateMap;
	}
	
	
	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  {"11065","ALL","11065",1},{"11066","CDC","11066",2},{"11067","NBS","11067",3}
	  
	       });
	   }

}





/**
 * The changes done to the getNbsDocument were related to setting additional values in the nbsDocumentDT after reading from the DB.
 * From this test we are not actually quering the DB, as agreed in the past, but, we are passing values as parameter to simulate they are read from the DB.
 * This test will pass if calling the getNbsDocument method with different NbsDocumentUid, doesn't fail and each of the values expected to be read "from the DB" and set in the NBSDocumentVO are set successfully.
 * Some of the values that we are testing are one of the latest that were modified: payload, sending app event, sending user event, etc.
 * @author Fatima.Lopezcalzado
 *
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.act.sqlscript.WumSqlQuery"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({NbsDocumentDAOImpl.class, WumSqlQuery.class, EDXEventProcessDT.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class GetNBSDocument_test {

	int iteration;
	Long nbsDocUid;
	String payload;
	String appEventId;
	String appPatientId;
	
	@Mock
	LogUtils logger;
	
	@Mock
	PropertyUtil propertyUtil;
	
	@org.mockito.Mock
    Connection c;
	@org.mockito.Mock
    PreparedStatement stmt;
	@org.mockito.Mock
	ResultSet resultSet;
	
	
	@InjectMocks
	@Spy
	NbsDocumentDAOImpl nbsDocumentDAOImpl =  Mockito.spy(NbsDocumentDAOImpl.class);
	
	public GetNBSDocument_test(int iteration, Long nbsDocUid, String payload, String appEventId, String appPatientId) {
		super();
		this.nbsDocUid = nbsDocUid;
		this.iteration = iteration;
		this.payload = payload;
		this.appEventId = appEventId;
		this.appPatientId = appPatientId;
		
	}
	

	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  {1, 111111L, "payloaaaaaaaaad1", "CAS1015501000GA01","PSN1015501000GA01"},{2, 2222222L, "payloaaaaaaaaad2", "CAS1015501002GA01","PSN1015501002GA01"},{3, 333333L, "payloaaaaaaaaad3", "CAS1015501003GA01","PSN1015501003GA01"}
	  
	       });
	   }
	 
	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 c = Mockito.mock(Connection.class);
		 stmt = Mockito.mock(PreparedStatement.class);
		 resultSet = Mockito.mock(ResultSet.class);
		 
		Whitebox.setInternalState(WumSqlQuery.class, "GET_NBS_DOCUMENT", "select * from nbs_document");//just as an example
		 Whitebox.setInternalState(NbsDocumentDAOImpl.class, "logger", logger);
	

			
			
			
			
			
	 }
	
	@Test
	public void getNBSDocument_test() throws Exception{
		
		System.out.println("******************* Starting test case named: getNBSDocument_test *******************");
		Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
		when(c.prepareStatement(any(String.class))).thenReturn(stmt);
		Mockito.doReturn(c).when(nbsDocumentDAOImpl).getConnection();
		
		Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(resultSet.getLong("nbsDocumentUid")).thenReturn(nbsDocUid);
		Mockito.when(resultSet.getString("docPayload")).thenReturn(payload);
		Mockito.when(resultSet.getString("sendingAppEventId")).thenReturn(appEventId);
		Mockito.when(resultSet.getString("sendingAppPatientId")).thenReturn(appPatientId);
		
		//and so on
		
		
		//.getEDXEventProcessMap(nbsDocumentDT.getNbsDocumentUid())
		Map<String, EDXEventProcessDT> eDXEventProcessDTMap = new HashMap<String, EDXEventProcessDT>();
		Mockito.doReturn(eDXEventProcessDTMap).when(nbsDocumentDAOImpl).getEDXEventProcessMap(nbsDocUid);
//	    
		NBSDocumentVO nbsDocumentVO = nbsDocumentDAOImpl.getNBSDocument(nbsDocUid);
		
		

		
		System.out.println("Iteration: #"+iteration+"\nExpected payload: "+payload+"\nActual payload: "+ nbsDocumentVO.getNbsDocumentDT().getPayLoadTxt()+
				"\nExpected NbsDocumentUid: "+nbsDocUid+"\nActual NbsDocumentUid: "+ nbsDocumentVO.getNbsDocumentDT().getNbsDocumentUid()+
				"\nExpected SendingAppEventId: "+appEventId+"\nActual SendingAppEventId: "+ nbsDocumentVO.getNbsDocumentDT().getSendingAppEventId()+
				"\nExpected SendingAppPatientId: "+appPatientId+"\nActual SendingAppPatientId: "+ nbsDocumentVO.getNbsDocumentDT().getSendingAppPatientId());
		
		Assert.assertEquals(payload, nbsDocumentVO.getNbsDocumentDT().getPayLoadTxt());
		Assert.assertEquals(nbsDocUid, nbsDocumentVO.getNbsDocumentDT().getNbsDocumentUid());
		Assert.assertEquals(appEventId, nbsDocumentVO.getNbsDocumentDT().getSendingAppEventId());
		Assert.assertEquals(appPatientId, nbsDocumentVO.getNbsDocumentDT().getSendingAppPatientId());
		
		
		System.out.println("PASSED");
		System.out.println("******************* End test case named: getNBSDocument_test *******************");
	
		
		
	}
	    
	    
	

}


}
		
	
