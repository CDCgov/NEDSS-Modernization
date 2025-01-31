package test.gov.cdc.nedss.entity.entityid.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({EntityIdDAOImpl.class})
@PowerMockIgnore("javax.management.*")
public class EntityIdDAOImpl_tests {
	
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityIdDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class GetNextMaxEntityIdSeq_test {
	int expectedValue;
	long personUid;
	int iteration;
		
	public GetNextMaxEntityIdSeq_test(int expectedValue,long personUid,int iteration){
	    this.expectedValue=expectedValue;
	    this.personUid=personUid;
		this.iteration=iteration;
	}
	
	@Mock
	PropertyUtil propertyUtilMocked;
	@Mock
    Connection conn;
	@Mock
    PreparedStatement stmt;
	@Mock
	ResultSet resultSet;
    @Mock
    LogUtils logger;
    @Mock
    ResultSetUtils resultSetUtils;
	@Mock
	BMPBase bmpBase;
	
	@InjectMocks
	@Spy
	EntityIdDAOImpl entityIdDAOImpl=  Mockito.spy(EntityIdDAOImpl.class);
   
	@Before
	public void initMocks() throws Exception {
		propertyUtilMocked = Mockito.mock(PropertyUtil.class);
 		logger = Mockito.mock(LogUtils.class);
		conn = Mockito.mock(Connection.class);
		stmt = Mockito.mock(PreparedStatement.class);
		resultSet = Mockito.mock(ResultSet.class);
 		Whitebox.setInternalState(EntityIdDAOImpl.class, "logger", logger);
	}
		 
		 
	@Test
	public void getNextMaxEntityIdSeq_test() throws Exception{
		Mockito.doReturn(conn).when(entityIdDAOImpl).getConnection();
		when(conn.prepareStatement(any(String.class))).thenReturn(stmt);
		Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
		Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(resultSet.getInt(1)).thenReturn(this.expectedValue);
	//	PowerMockito.doNothing().when(bmpBase, "closeResultSet", any(ResultSet.class));
	//	PowerMockito.doNothing().when(bmpBase, "closeStatement", any(PreparedStatement.class));
	//	PowerMockito.doNothing().when(bmpBase, "releaseConnection", any(Connection.class));
		int actualResult=Whitebox.invokeMethod(entityIdDAOImpl,"getNextMaxEntityIdSeq", this.personUid);
		Assert.assertEquals(actualResult,this.expectedValue);
		System.out.println("Method:getNextMaxEntityIdSeq, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED");
	}
		

	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][]{
			{100,100000,1},
			{101,100001,2}, 
			{102,100003,3}, 
			{103,100004,4}, 
			{104,100005,5}, 
			{105,100006,6}  
		   });
	     }

	

}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityIdDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class InsertEntityIDs_test {
	int expectedValue;
	long personUid;
	int iteration;
		
	public InsertEntityIDs_test(int expectedValue,long personUid,int iteration){
	    this.expectedValue=expectedValue;
	    this.personUid=personUid;
		this.iteration=iteration;
	}
	
	@Mock
	PropertyUtil propertyUtilMocked;
	@Mock
    Connection conn;
	@Mock
    PreparedStatement stmt;
	@Mock
	ResultSet resultSet;
    @Mock
    LogUtils logger;
    @Mock
    ResultSetUtils resultSetUtils;
	@Mock
	BMPBase bmpBase;
	
	@InjectMocks
	@Spy
	EntityIdDAOImpl entityIdDAOImpl=  PowerMockito.spy(new EntityIdDAOImpl());
   
	@Before
	public void initMocks() throws Exception {
		propertyUtilMocked = Mockito.mock(PropertyUtil.class);
 		logger = Mockito.mock(LogUtils.class);
 		conn = Mockito.mock(Connection.class);
 		stmt = Mockito.mock(PreparedStatement.class);
 		resultSet = Mockito.mock(ResultSet.class);
 		resultSetUtils = Mockito.mock(ResultSetUtils.class);
 		
 
 		Whitebox.setInternalState(EntityIdDAOImpl.class, "logger", logger);
	}
		 
		 
	@Test
	public void insertEntityIDs_test() throws Exception{
		PowerMockito.doReturn(this.expectedValue).when(entityIdDAOImpl, "getNextMaxEntityIdSeq", any(Long.class));
/*		PowerMockito.doNothing().when(entityIdDAOImpl, "insertEntityID", anyLong(),any(EntityIdDT.class),anyInt());
		Whitebox.invokeMethod(entityIdDAOImpl,"insertEntityIDs", this.personUid,getEntityIdsList());
		System.out.println("Method:insertEntityIDs, iteration::::"+this.iteration+",  RESULT::::PASSED");*/
	}

	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][]{
			{100,100000,1},
			{101,100001,2}, 
			{102,100003,3}, 
			{103,100004,4}, 
			{104,100005,5}, 
			{105,100006,6}  
		   });
	     }
				 

	private Collection<Object> getEntityIdsList(){
		Collection<Object> entityIDs= new ArrayList<Object>();
		EntityIdDT entityID = new EntityIdDT();
		entityID.setAddUserId(12345L);
		entityID.setEntityUid(123456L);
		entityID.setEntityIdSeq(1);
		entityID.setAssigningAuthorityCd("SSA");
		entityIDs.add(entityID);

		
		return entityIDs;
	}
	

}





@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityIdDAOImpl.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class InsertEntityID_test {
	int expectedValue;
	long personUid;
	int iteration;
		
	public InsertEntityID_test(int expectedValue,long personUid,int iteration){
	    this.expectedValue=expectedValue;
	    this.personUid=personUid;
		this.iteration=iteration;
	}
	
	@Mock
	PropertyUtil propertyUtilMocked;
	@Mock
    Connection conn;
	@Mock
    PreparedStatement stmt;
	@Mock
	ResultSet resultSet;
    @Mock
    LogUtils logger;
    @Mock
    ResultSetUtils resultSetUtils;
	@Mock
	BMPBase bmpBase;
	
	@InjectMocks
	@Spy
	EntityIdDAOImpl entityIdDAOImpl=  PowerMockito.spy(new EntityIdDAOImpl());
   
	@Before
	public void initMocks() throws Exception {
		propertyUtilMocked = Mockito.mock(PropertyUtil.class);
 		logger = Mockito.mock(LogUtils.class);
		conn = Mockito.mock(Connection.class);
		stmt = Mockito.mock(PreparedStatement.class);
		resultSet = Mockito.mock(ResultSet.class);
 		Whitebox.setInternalState(EntityIdDAOImpl.class, "logger", logger);
	}
		
		
	@Test
	public void insertEntityID_test() throws Exception{
		Mockito.doReturn(conn).when(entityIdDAOImpl).getConnection();
		when(conn.prepareStatement(any(String.class))).thenReturn(stmt);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);  
		//PowerMockito.doNothing().when(bmpBase, "closeResultSet", any(ResultSet.class));
		//PowerMockito.doNothing().when(bmpBase, "closeStatement", any(PreparedStatement.class));
		//PowerMockito.doNothing().when(bmpBase, "releaseConnection", any(Connection.class));
		Whitebox.invokeMethod(entityIdDAOImpl,"insertEntityID", this.personUid,getEntityDT(),this.expectedValue);
		System.out.println("Method:insertEntityID, iteration::::"+this.iteration+", RESULT::::PASSED");
	}
	

	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][]{
			{100,100000,1},
			{101,100001,2}, 
			{102,100003,3}, 
			{103,100004,4}, 
			{104,100005,5}, 
			{105,100006,6}  
		   });
	     }
				 

	
	private EntityIdDT getEntityDT() {
		EntityIdDT entityID = new EntityIdDT();
		entityID.setAddUserId(12345L);
		entityID.setEntityUid(123456L);
		entityID.setEntityIdSeq(1);
		entityID.setAssigningAuthorityCd("SSA");
		return entityID;
	}
}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.ResultSetUtils","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityIdDAOImpl.class,})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class CheckInOthers_test {
	int expectedValue;
	long personUid;
	int iteration;
		
	public CheckInOthers_test(int expectedValue,long personUid,int iteration){
	    this.expectedValue=expectedValue;
	    this.personUid=personUid;
		this.iteration=iteration;
	}
	
	@Mock
	PropertyUtil propertyUtilMocked;
	@Mock
    Connection conn;
	@Mock
    PreparedStatement stmt;
	@Mock
	ResultSet resultSet;
    @Mock
    LogUtils logger;
    @Mock
    ResultSetUtils resultSetUtils;
	
	@InjectMocks
	@Spy
	EntityIdDAOImpl entityIdDAOImpl=  PowerMockito.spy(new EntityIdDAOImpl());
   
	@Before
	public void initMocks() throws Exception {
		propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		logger = Mockito.mock(LogUtils.class);
		conn = Mockito.mock(Connection.class);
		stmt = Mockito.mock(PreparedStatement.class);
		resultSet = Mockito.mock(ResultSet.class);

 	}
		 
	
	
	@Test 
	public void checkInOthers_test() throws Exception{
		Mockito.doReturn(conn).when(entityIdDAOImpl).getConnection();
		when(conn.prepareStatement(any(String.class))).thenReturn(stmt);
		Mockito.when(stmt.executeQuery()).thenReturn(resultSet);  
		Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(resultSet.getInt(1)).thenReturn(this.expectedValue);
		
		 
		//Protected methods
	//	PowerMockito.doNothing().when(bmpBase, "closeStatement", any(PreparedStatement.class));
	//	PowerMockito.doNothing().when(bmpBase, "releaseConnection", any(Connection.class));
		boolean actualResult=Whitebox.invokeMethod(entityIdDAOImpl,"checkInOthers",getEntityDT());
		boolean expectedValue=this.expectedValue>0?true:false;
		Assert.assertEquals(actualResult,expectedValue);
		System.out.println("Method:checkInOthers, iteration::::"+this.iteration+", Expected Result:"+expectedValue+", Actual Result:"+actualResult+", RESULT::::PASSED");
	}
		

	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][]{
			{100,100000,1},
			{101,100001,2}, 
			{102,100003,3}, 
			{103,100004,4}, 
			{104,100005,5}, 
			{105,100006,6}  
		   });
	     }
				 


	private EntityIdDT getEntityDT() {
		EntityIdDT entityID = new EntityIdDT();
		entityID.setAddUserId(12345L);
		entityID.setEntityUid(123456L);
		entityID.setEntityIdSeq(1);
		entityID.setAssigningAuthorityCd("SSA");
		return entityID;
	}
}


}
	
