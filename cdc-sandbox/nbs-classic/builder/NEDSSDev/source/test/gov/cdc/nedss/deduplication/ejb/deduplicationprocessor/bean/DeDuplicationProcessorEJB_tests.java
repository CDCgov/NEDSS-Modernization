package test.gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean;

import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean.DeDuplicationProcessorEJB;
import gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao.DeDuplicationDAO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.LogUtils;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean.DeDuplicationProcessorEJB","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({DeDuplicationProcessorEJB.class,NEDSSDAOFactory.class})
@PowerMockIgnore("javax.management.*")
public class DeDuplicationProcessorEJB_tests {
	
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean.DeDuplicationProcessorEJB","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({DeDuplicationProcessorEJB.class,NEDSSDAOFactory.class})
@PowerMockIgnore("javax.management.*")
public static class RemoveFromMergeForDedup_test {
	
	public RemoveFromMergeForDedup_test(){
	}
	
	@Mock
	DeDuplicationDAO deDuplicationDAO;


	@Mock
	LogUtils logger;
	
	@InjectMocks
	DeDuplicationProcessorEJB deDuplicationProcessorEJB;
	

	
	@Before
	public void initMocks() throws Exception {
	}
	
	
	@Test
	public void removeFromMergeForDedup_test() throws Exception {
		PowerMockito.mockStatic(NEDSSDAOFactory.class);
		PowerMockito.whenNew(DeDuplicationDAO.class).withNoArguments().thenReturn(deDuplicationDAO);
		deDuplicationProcessorEJB= Mockito.spy(DeDuplicationProcessorEJB.class);
		Mockito.when(NEDSSDAOFactory.getDAO(any(String.class))).thenReturn(deDuplicationDAO);
		PowerMockito.doNothing().when(deDuplicationDAO, "removePatientFromMergeForDedup", any(String.class));
		Whitebox.invokeMethod(deDuplicationProcessorEJB, "removeFromMergeForDedup", any(String.class), any(NBSSecurityObj.class));
		System.out.println("Method Name:removeFromMergeForDedup, Iteration: 1, RESULTS::::::PASSED");
	}
}
	
}
