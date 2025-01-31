package test.gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao.DeDuplicationDAO;
import gov.cdc.nedss.util.BMPBase;

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({DeDuplicationDAO.class})
@PowerMockIgnore("javax.management.*")
public class DeDuplicationDAO_tests {
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({DeDuplicationDAO.class})
@PowerMockIgnore("javax.management.*")
public static class RemovePatientFromMergeForDedup_test {
	
	public RemovePatientFromMergeForDedup_test(){
	}
	
	@Mock
    Connection conn;
	@Mock
    PreparedStatement stmt;
	@Mock
	ResultSet resultSet;
	
	@Mock
	BMPBase bmpBase;
	
	@InjectMocks
	@Spy 
	DeDuplicationDAO deDuplicationDAO=  Mockito.spy(DeDuplicationDAO.class);
	

	
	@Before
	public void initMocks() throws Exception {
	}
	
	@Test
	public void removePatientFromMergeForDedup_test() throws Exception {
		Mockito.doReturn(conn).when(deDuplicationDAO).getConnection();
		when(conn.prepareStatement(any(String.class))).thenReturn(stmt);
		PowerMockito.doNothing().when(stmt, "setString", anyInt(),any(String.class));
		PowerMockito.doReturn(1).when(stmt).executeUpdate();
		PowerMockito.doNothing().when(bmpBase, "closeResultSet", any(ResultSet.class));
		PowerMockito.doNothing().when(bmpBase, "closeStatement", any(PreparedStatement.class));
		PowerMockito.doNothing().when(bmpBase, "releaseConnection", any(Connection.class));
		Whitebox.invokeMethod(deDuplicationDAO, "removePatientFromMergeForDedup", any(String.class));
		System.out.println("Method Name: removePatientFromMergeForDedup, Iteration: 1, RESULTS::::::PASSED");
	}
}
}
	
