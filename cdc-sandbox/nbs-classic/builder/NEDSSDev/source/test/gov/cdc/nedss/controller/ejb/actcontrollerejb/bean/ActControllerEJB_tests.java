package test.gov.cdc.nedss.controller.ejb.actcontrollerejb.bean;

import static org.mockito.Matchers.any;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerEJB;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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


- ...
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({NEDSSDAOFactory.class,ActControllerEJB.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class ActControllerEJB_tests {



/**
 * GetActRelationships_test: this method was updated to remove .intValue() from actRelationshipDaoImpl.load(actUid). So this method will pass as long as no exceptions are shown and the load method is called once suscesfully.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.util.NEDSSDAOFactory","gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerEJB","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({NEDSSDAOFactory.class,ActRelationshipDAOImpl.class, PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,ActControllerEJB.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class GetActRelationships_test{
	

	private String iteration;
	private String personUidList;
	

	
 public GetActRelationships_test(String it, String personUidList){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.personUidList = personUidList;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"getActRelationships_test"+"_"+it++, "11111|99999999"}, 
    		  {"getActRelationships_test"+"_"+it++, "11111"}, 
    		  {"getActRelationships_test"+"_"+it++, "99999999"}, 
    		  {"getActRelationships_test"+"_"+it++, "11111|99999999|11232141241"}, 
    		  {"getActRelationships_test"+"_"+it++, ""}, 
    		  
    		
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   
@Mock
LogUtils loggerMock;

@Mock
ActRelationshipDAOImpl actRelationshipDaoImpl;

@Mock
NBSSecurityObj secObj;

@Mock
ActControllerEJB actController;

@Spy
@InjectMocks
ActControllerEJB actControllerEJB=Mockito.spy(new ActControllerEJB());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(ActControllerEJB.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 actController = Mockito.mock(ActControllerEJB.class);
		 actRelationshipDaoImpl = Mockito.mock(ActRelationshipDAOImpl.class);
		 Whitebox.setInternalState(ActControllerEJB.class, "logger", loggerMock);
		 
	 }
	 
	

	@Test
	public void getActRelationships_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: getActRelationships_test *******************");
				
			Long actUid =11111L;
			

			 Collection<Object> actRelationshipDTs = new ArrayList<Object> ();
			 
			 actController = PowerMockito.spy(new ActControllerEJB());
		
		
			 	Mockito.when(actRelationshipDaoImpl.load(actUid)).thenReturn(actRelationshipDTs);
			 	PowerMockito.mockStatic(NEDSSDAOFactory.class);
				PowerMockito.doReturn(actRelationshipDaoImpl).when(NEDSSDAOFactory.class, "getDAO",any(String.class));


				 actController.getActRelationships( actUid, secObj);
			
				 
				 System.out.println("Iteration: #"+iteration+" getActRelationships was successfully executed, and load was executed 1 times.");

				 Mockito.verify(actRelationshipDaoImpl, Mockito.times(1)).load(actUid);

				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: getActRelationships_test *******************");
			
		}		
	
}
















}
	
