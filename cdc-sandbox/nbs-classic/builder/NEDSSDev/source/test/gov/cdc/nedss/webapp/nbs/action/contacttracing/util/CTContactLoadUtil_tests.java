package test.gov.cdc.nedss.webapp.nbs.action.contacttracing.util;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.CtContactClientVO.CTContactClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTContactLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
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

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CTContactForm.class,CTContactLoadUtil.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class CTContactLoadUtil_tests {



/**
 * SetPatientForEventCreate_test: the change on this method consisted of updating patientUid and tempId type from int to Long. As part of this test, we are making sure
 * whatever values we pass to the method as tempId and patientUid, are set in the personVO correctly.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm","gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTContactLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CTContactForm.class,PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,CTContactLoadUtil.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetPatientForEventCreate_test{
	

	private String iteration;
	private Long patientUid;
	private Long tempID;
	private String userId;

	
	

	
 public SetPatientForEventCreate_test(String it, Long patientUid, Long tempId, String userId){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.patientUid = patientUid;
	 this.tempID = tempId;
	 this.userId = userId;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setPatientForEventCreate_test"+"_"+it++, 1L, 2L, "111111111"}, 
    		  {"setPatientForEventCreate_test"+"_"+it++, 2L, 3L, "111111111"}, 
    		  {"setPatientForEventCreate_test"+"_"+it++, 4444444L, 111111L, "111111111"}, 
    		  {"setPatientForEventCreate_test"+"_"+it++, 5555L, 6666666L, "111111111"},
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   @Mock
   HttpServletRequest request;
   @Mock
   HttpSession session;


/*
@Mock
PamProxyVO pamProxyVO;*/


@Mock
LogUtils loggerMock;


@Mock
CTContactForm form;

@Mock
NBSSecurityObj secObj;


@Spy
@InjectMocks
CTContactLoadUtil cTContactLoadUtil=Mockito.spy(new CTContactLoadUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(CTContactLoadUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 session = Mockito.mock(HttpSession.class);
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 //pamProxyVO = Mockito.mock(PamProxyVO.class);
		 Whitebox.setInternalState(CTContactLoadUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setPatientForEventCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setPatientForEventCreate_test *******************");

				CTContactProxyVO pageProxyVO =  new CTContactProxyVO();
		
				Whitebox.invokeMethod(new CTContactLoadUtil(), "setPatientForEventCreate", patientUid, tempID, pageProxyVO, form, request, userId);
				
				PersonVO personVO = pageProxyVO.getContactPersonVO();
				Long personUIDactual = null;
				Long personParentUIDactual = null;
				
				if(personVO!=null){
					
						personUIDactual = personVO.getThePersonDT().getPersonUid();
						personParentUIDactual = personVO.getThePersonDT().getPersonParentUid();
					
				}

				
				System.out.println("Iteration: #"+iteration);
				System.out.println("\nPersonUID sent: "+tempID+"\nPersonUID set in PersonVO: "+personUIDactual);
				
				Assert.assertEquals(tempID, personUIDactual);
				System.out.println("\nPersonParentUID sent: "+patientUid+"\nPersonUID set in PersonVO: "+personParentUIDactual);
				
				Assert.assertEquals(patientUid, personParentUIDactual);
				
				
				
				System.out.println("PASSED");
				System.out.println("******************* End test case named: setPatientForEventCreate_test *******************");
			
		}		
	
}







/**
 * SetPatientForEventPageCreate_test: the change on this method consisted of updating patientUid and tempId type from int to Long. As part of this test, we are making sure
 * whatever values we pass to the method as tempId and patientUid, are set in the personVO correctly.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm","gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTContactLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageCreateHelper.class,CTContactForm.class,PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,CTContactLoadUtil.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetPatientForEventPageCreate_test{
	

	private String iteration;
	private Long patientUid;
	private Long tempID;
	private String userId;

	
	

	
 public SetPatientForEventPageCreate_test(String it, Long patientUid, Long tempId, String userId){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.patientUid = patientUid;
	 this.tempID = tempId;
	 this.userId = userId;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setPatientForEventPageCreate_test"+"_"+it++, 1L, 2L, "111111111"}, 
    		  {"setPatientForEventPageCreate_test"+"_"+it++, 2L, 3L, "111111111"}, 
    		  {"setPatientForEventPageCreate_test"+"_"+it++, 4444444L, 111111L, "111111111"}, 
    		  {"setPatientForEventPageCreate_test"+"_"+it++, 5555L, 6666666L, "111111111"},
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   @Mock
   HttpServletRequest request;
   @Mock
   HttpSession session;


/*
@Mock
PamProxyVO pamProxyVO;*/


@Mock
LogUtils loggerMock;



@Mock
NBSSecurityObj secObj;


@Spy
@InjectMocks
CTContactLoadUtil cTContactLoadUtil=Mockito.spy(new CTContactLoadUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(CTContactLoadUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 session = Mockito.mock(HttpSession.class);
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 //pamProxyVO = Mockito.mock(PamProxyVO.class);
		 Whitebox.setInternalState(CTContactLoadUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setPatientForEventPageCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setPatientForEventPageCreate_test *******************");
				
				CTContactForm form = new CTContactForm();
				CTContactProxyVO pageProxyVO =  new CTContactProxyVO();
				CTContactProxyVO CTCntactProxyVO = new CTContactProxyVO();
				form.setcTContactProxyVO(CTCntactProxyVO);
				
				PowerMockito.mockStatic(PageCreateHelper.class);
				PowerMockito.doNothing().when(PageCreateHelper.class, "setPatientForEventCreate", Matchers.any(PersonVO.class), Matchers.any(ClientVO.class), Matchers.any(String.class));
					
				Whitebox.invokeMethod(new CTContactLoadUtil(), "setPatientForEventPageCreate", patientUid, tempID, pageProxyVO, form, request, userId);

			
				PersonVO personVO = pageProxyVO.getContactPersonVO();
				Long personUIDactual = null;
				Long personParentUIDactual = null;
				
				if(personVO!=null){
					
						personUIDactual = personVO.getThePersonDT().getPersonUid();
						personParentUIDactual = personVO.getThePersonDT().getPersonParentUid();
				
				}

				
				System.out.println("Iteration: #"+iteration);
				System.out.println("\nPersonUID sent: "+tempID+"\nPersonUID set in PersonVO: "+personUIDactual);
				
				Assert.assertEquals(tempID, personUIDactual);
				System.out.println("\nPersonParentUID sent: "+patientUid+"\nPersonUID set in PersonVO: "+personParentUIDactual);
				
				Assert.assertEquals(patientUid, personParentUIDactual);
				
				
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setPatientForEventPageCreate_test *******************");
			
		}		
	
}









/**
 * SetPatientForEventCreate_test: the change on this method consisted of updating revisionPatientUID type from int to Long. As part of this test, we are making sure
 * whatever value we pass to the method as tempId, the method still works as expected and ActEntityDTCollection contains one DT created within that method.
 * We can't validate the expected value has been set in the DT because the DT is returned by other method that returns a Mock.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm","gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTContactLoadUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTContactLoadUtil","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,CTContactLoadUtil.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetEntitiesForCreateEdit_test{
	

	private String iteration;
	private String userId;
	private Long revisionPatientUID;
	private String versionCtrlNbr;
	private Long actUid;
	 

	
	

	
 public SetEntitiesForCreateEdit_test(String it, String userId, Long revisionPatientUID, String versionCtrlNbr, Long actUid){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.userId = userId;
	 this.revisionPatientUID=revisionPatientUID;
	 this.versionCtrlNbr=versionCtrlNbr;
	this.actUid = actUid;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 2L, "1", 2L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 1111111L, "1", 1L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 99999999L, "1", 3L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 88888888L, "1", 4L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 13242352L, "1", 5L},
    		  {"setEntitiesForCreateEdit_test"+"_"+it++,"111111111", 13242352L, "1", 0L},
    		  
    		  
			  
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */




@Mock
PageActProxyVO pageActProxyVO;


@Mock
LogUtils loggerMock;

@Mock
NbsActEntityDT oldDT;

@Mock
NbsActEntityDT entityDT;

@Spy
@InjectMocks
CTContactLoadUtil cTContactLoadUtil=Mockito.spy(new CTContactLoadUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(CTContactLoadUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 entityDT = Mockito.mock(NbsActEntityDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 Whitebox.setInternalState(CTContactLoadUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setEntitiesForCreateEdit_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setEntitiesForCreateEdit_test *******************");
				
		
				CTContactForm form = new CTContactForm();
				Collection<Object> actEntityCollection = new ArrayList<Object>();
				CTContactVO ctContactVO = new CTContactVO();
				CTContactClientVO ctContactClientVO = new CTContactClientVO();
				CTContactProxyVO ctContactProxyVO = new CTContactProxyVO();
				
				ctContactVO.setActEntityDTCollection(actEntityCollection);
				ctContactProxyVO.setcTContactVO(ctContactVO);
				ctContactClientVO.setOldCtContactProxyVO(ctContactProxyVO);
				form.setcTContactClientVO(ctContactClientVO);
				
				
				
				CTContactProxyVO proxyVO = new CTContactProxyVO();
				NbsActEntityDT entityDT = new NbsActEntityDT();
				entityDT.setActUid(actUid);
				NbsActEntityDT oldDT = new NbsActEntityDT();
				PowerMockito.doReturn(entityDT).when(CTContactLoadUtil.class, "createContactEntity", any(Long.class), any(String.class), any(String.class), any(String.class), any(String.class));
				PowerMockito.doReturn(oldDT).when(CTContactLoadUtil.class, "getNbsCaseEntity",any(String.class), any(Collection.class));

				
				Whitebox.invokeMethod(new CTContactLoadUtil(), "setEntitiesForCreateEdit", form, proxyVO, revisionPatientUID, versionCtrlNbr, userId);
			
				int actualSize = proxyVO.getcTContactVO().getActEntityDTCollection().size();
				int expectedSize = 1;
				
	
				System.out.println("Iteration: #"+iteration+"\nExpected size of ActEntityDTCollection: "+expectedSize+"\nActual size of ActEntityDTCollection: "+actualSize);
				
				Assert.assertEquals(expectedSize, actualSize);
				
				
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setEntitiesForCreateEdit_test *******************");
			
		}		
	
}



}
	
