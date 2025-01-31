package test.gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerEJB;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageChangeConditionUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.mockito.internal.verification.Times;
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
@PrepareForTest({EntityControllerEJB.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class EntityControllerEJB_tests {



/**
 * SetPersonNames_test: this method was updated to remove .intValue from the logger. So this method will pass as long as no exceptions are shown.
 * Also, depending on the collection (either empty or with at least one value - since there's no loop to iterate through the collection from the original method) we will make sure the setPerson and logger.debug methods are called 0 or 1 times.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerEJB","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,EntityControllerEJB.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetPersonNames_test{
	

	private String iteration;
	private String personUidList;
	

	
 public SetPersonNames_test(String it, String personUidList){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.personUidList = personUidList;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setPersonNames_test"+"_"+it++, "11111|99999999"}, 
    		  {"setPersonNames_test"+"_"+it++, "11111"}, 
    		  {"setPersonNames_test"+"_"+it++, "99999999"}, 
    		  {"setPersonNames_test"+"_"+it++, "11111|99999999|11232141241"}, 
    		  {"setPersonNames_test"+"_"+it++, ""}, 
    		  
    		
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   
@Mock
LogUtils loggerMock;



@Mock
NBSSecurityObj secObj;

@Mock
EntityControllerEJB entity;

@Spy
@InjectMocks
EntityControllerEJB entityControllerEJB=Mockito.spy(new EntityControllerEJB());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(EntityControllerEJB.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 entity = Mockito.mock(EntityControllerEJB.class);
		 
		 Whitebox.setInternalState(EntityControllerEJB.class, "logger", loggerMock);
		 
	 }
	 
	

	@Test
	public void setPersonNames_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setPersonNames_test *******************");
				
	
				 
				 Collection<Object> personNames = new ArrayList<Object>();
				 
				
				 
				 String[] listPersonUid = personUidList.split("\\|");
				 int times = 1;
				 if(personUidList!=null && personUidList.isEmpty())
					 times=0;
				 else{
					 for(int i = 0; i<listPersonUid.length ; i++){
						Long personUid =  Long.parseLong(listPersonUid[i]);
						 
						
						 PersonNameDT personNameDT = new PersonNameDT();
						 personNameDT.setPersonUid(personUid);
						 personNames.add(personNameDT);
	
					
						 
					 }
				 }
					
				 PersonVO personVO = new PersonVO();
				 Long personUidExample = 111111L;
				 entity = PowerMockito.spy(new EntityControllerEJB());
				 PowerMockito.doReturn(personVO).when(entity, "getPerson", any(Long.class),any(NBSSecurityObj.class));
				 PowerMockito.doReturn(personUidExample).when(entity, "setPerson",any(PersonVO.class),any(NBSSecurityObj.class));
					 
			
				 entity.setPersonNames( personNames, secObj);
			
				 //Verifying the last method was called once (there are not loop to iterate through all the persons)
				 Mockito.verify(entity, Mockito.times(times)).setPerson(Mockito.any(PersonVO.class),Mockito.any(NBSSecurityObj.class));
				 Mockito.verify(loggerMock, Mockito.times(times)).debug(any(String.class));
						
				System.out.println("Iteration: #"+iteration+" setPersonNames was successfully executed, and setPerson was executed "+times+" times");
				
		
			
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setPersonNames_test *******************");
			
		}		
	
}












/**
 * SetPersonRaces_test: this method was updated to remove .intValue from the logger. So this method will pass as long as no exceptions are shown.
 * Also, depending on the collection (either empty or with at least one value - since there's no loop to iterate through the collection from the original method) we will make sure the setPerson and logger.debug methods are called 0 or 1 times.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerEJB","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,EntityControllerEJB.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetPersonRaces_test{
	

	private String iteration;
	private String personUidList;
	

	
 public SetPersonRaces_test(String it, String personUidList){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.personUidList = personUidList;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setPersonRaces_test"+"_"+it++, "11111|99999999"}, 
    		  {"setPersonRaces_test"+"_"+it++, "11111"}, 
    		  {"setPersonRaces_test"+"_"+it++, "99999999"}, 
    		  {"setPersonRaces_test"+"_"+it++, "11111|99999999|11232141241"}, 
    		  {"setPersonRaces_test"+"_"+it++, ""}, 
    		  
    		
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   
@Mock
LogUtils loggerMock;



@Mock
NBSSecurityObj secObj;

@Mock
EntityControllerEJB entity;

@Spy
@InjectMocks
EntityControllerEJB entityControllerEJB=Mockito.spy(new EntityControllerEJB());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(EntityControllerEJB.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 entity = Mockito.mock(EntityControllerEJB.class);
		 
		 Whitebox.setInternalState(EntityControllerEJB.class, "logger", loggerMock);
		 
	 }
	 
	

	@Test
	public void setPersonRaces_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setPersonRaces_test *******************");

				 Collection<Object> personRaces = new ArrayList<Object>();

				 String[] listPersonUid = personUidList.split("\\|");
				 int times = 1;
				 if(personUidList!=null && personUidList.isEmpty())
					 times=0;
				 else{
					 for(int i = 0; i<listPersonUid.length ; i++){
						Long personUid =  Long.parseLong(listPersonUid[i]);
						 
						
						PersonRaceDT personRaceDT = new PersonRaceDT();
						personRaceDT.setPersonUid(personUid);
						personRaces.add(personRaceDT);
	
					
						 
					 }
				 }
					
				 PersonVO personVO = new PersonVO();
				 Long personUidExample = 111111L;
				 entity = PowerMockito.spy(new EntityControllerEJB());
				 PowerMockito.doReturn(personVO).when(entity, "getPerson", any(Long.class),any(NBSSecurityObj.class));
				 PowerMockito.doReturn(personUidExample).when(entity, "setPerson",any(PersonVO.class),any(NBSSecurityObj.class));
					 
			
				 entity.setPersonRaces( personRaces, secObj);
			
				 //Verifying the last method was called once (there are not loop to iterate through all the persons)
				 Mockito.verify(entity, Mockito.times(times)).setPerson(Mockito.any(PersonVO.class),Mockito.any(NBSSecurityObj.class));
				 Mockito.verify(loggerMock, Mockito.times(times)).debug(any(String.class));
						
				System.out.println("Iteration: #"+iteration+" setPersonRaces was successfully executed, and setPerson was executed "+times+" times");
				
		
			
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setPersonRaces_test *******************");
			
		}		
	
}














/**
 * setPersonEthnicGroups_test: this method was updated to remove .intValue from the logger. So this method will pass as long as no exceptions are shown.
 * Also, depending on the collection (either empty or with at least one value - since there's no loop to iterate through the collection from the original method) we will make sure the setPerson and logger.debug methods are called 0 or 1 times.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerEJB","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,EntityControllerEJB.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetPersonEthnicGroups_test{
	

	private String iteration;
	private String personUidList;
	

	
 public SetPersonEthnicGroups_test(String it, String personUidList){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.personUidList = personUidList;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setPersonEthnicGroups_test"+"_"+it++, "11111|99999999"}, 
    		  {"setPersonEthnicGroups_test"+"_"+it++, "11111"}, 
    		  {"setPersonEthnicGroups_test"+"_"+it++, "99999999"}, 
    		  {"setPersonEthnicGroups_test"+"_"+it++, "11111|99999999|11232141241"}, 
    		  {"setPersonEthnicGroups_test"+"_"+it++, ""}, 
    		  
    		
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   
@Mock
LogUtils loggerMock;



@Mock
NBSSecurityObj secObj;

@Mock
EntityControllerEJB entity;

@Spy
@InjectMocks
EntityControllerEJB entityControllerEJB=Mockito.spy(new EntityControllerEJB());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(EntityControllerEJB.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 entity = Mockito.mock(EntityControllerEJB.class);
		 
		 Whitebox.setInternalState(EntityControllerEJB.class, "logger", loggerMock);
		 
	 }
	 
	

	@Test
	public void setPersonEthnicGroups_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setPersonEthnicGroups_test *******************");

				 Collection<Object> personEthnicGroupDTs = new ArrayList<Object>();

				 String[] listPersonUid = personUidList.split("\\|");
				 int times = 1;
				 if(personUidList!=null && personUidList.isEmpty())
					 times=0;
				 else{
					 for(int i = 0; i<listPersonUid.length ; i++){
						Long personUid =  Long.parseLong(listPersonUid[i]);
						 
						
						PersonEthnicGroupDT personEthnicGroupDT = new PersonEthnicGroupDT();
						personEthnicGroupDT.setPersonUid(personUid);
						personEthnicGroupDTs.add(personEthnicGroupDT);
	
					
						 
					 }
				 }
					
				 PersonVO personVO = new PersonVO();
				 Long personUidExample = 111111L;
				 entity = PowerMockito.spy(new EntityControllerEJB());
				 PowerMockito.doReturn(personVO).when(entity, "getPerson", any(Long.class),any(NBSSecurityObj.class));
				 PowerMockito.doReturn(personUidExample).when(entity, "setPerson",any(PersonVO.class),any(NBSSecurityObj.class));
					 
			
				 entity.setPersonEthnicGroups( personEthnicGroupDTs, secObj);
			
				 //Verifying the last method was called once (there are not loop to iterate through all the persons)
				 Mockito.verify(entity, Mockito.times(times)).setPerson(Mockito.any(PersonVO.class),Mockito.any(NBSSecurityObj.class));
				 Mockito.verify(loggerMock, Mockito.times(times)).debug(any(String.class));
						
				System.out.println("Iteration: #"+iteration+" setPersonEthnicGroups_test was successfully executed, and setPerson was executed "+times+" times");
				
		
			
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setPersonEthnicGroups_test *******************");
			
		}		
	
}














}
	
