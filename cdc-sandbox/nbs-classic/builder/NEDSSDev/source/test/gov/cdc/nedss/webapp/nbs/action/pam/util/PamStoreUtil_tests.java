package test.gov.cdc.nedss.webapp.nbs.action.pam.util;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
@PrepareForTest({PamStoreUtil.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class PamStoreUtil_tests {



/**
 * SetPatientForEventCreate_test: the change on this method consisted of updating tempId type from int to Long. As part of this test, we are making sure
 * whatever value we pass to the method as tempId, is set in the personVO correctly.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PamStoreUtil.class,LogUtils.class})
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
NBSSecurityObj secObj;


@Spy
@InjectMocks
PamStoreUtil pamStoreUtil=Mockito.spy(new PamStoreUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PamStoreUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 session = Mockito.mock(HttpSession.class);
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 //pamProxyVO = Mockito.mock(PamProxyVO.class);
		 Whitebox.setInternalState(PamStoreUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setPatientForEventCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setPatientForEventCreate_test *******************");
				
			
				PamProxyVO pamProxyVO =  new PamProxyVO();

				
				PamForm form = new PamForm();

				 when((HttpSession) request.getSession()).thenReturn(session);
				 
				 when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
				 
				 when(secObj.getPermission(any(String.class), any(String.class))).thenReturn(true);
				Whitebox.invokeMethod(new PamStoreUtil(), "setPatientForEventCreate", patientUid, tempID, pamProxyVO, form, request, userId);
			
				Collection<Object> collection = pamProxyVO.getThePersonVOCollection();
				Long personUIDactual = null;
				
				if(collection!=null){
					
					Iterator<Object> it = collection.iterator();
					if(it.hasNext()){
						PersonVO personVO = (PersonVO)it.next();
						 personUIDactual = personVO.getThePersonDT().getPersonUid();
						
					}
					
				}
				    
	
			//	Long personUid = personVO.getThePersonDT().getPersonUid();
				
				
				System.out.println("Iteration: #"+iteration+"\nPersonUID sent: "+tempID+"\nPersonUID set in PersonVO: "+personUIDactual);
				
				Assert.assertEquals(tempID, personUIDactual);
				
				
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setPatientForEventCreate_test *******************");
			
		}		
	
}









/**
 * SetPatientForEventCreate_test: the change on this method consisted of updating revisionPatientUID type from int to Long. As part of this test, we are making sure
 * whatever value we pass to the method as tempId, the method still works as expected and ActEntityDTCollection contains one DT created within that method.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PamStoreUtil.class,LogUtils.class})
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
PamStoreUtil pamStoreUtil=Mockito.spy(new PamStoreUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PamStoreUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 entityDT = Mockito.mock(NbsActEntityDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 Whitebox.setInternalState(PamStoreUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setEntitiesForCreateEdit_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setEntitiesForCreateEdit_test *******************");
				
				Long phcUid = 111111L;
				
				PamForm form = new PamForm();

				PamProxyVO proxyVO = new PamProxyVO();
				PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
				PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
				publicHealthCaseDT.setPublicHealthCaseUid(phcUid);
				proxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				
				PamClientVO pamClientVO = new PamClientVO();
				PamProxyVO oldPageProxyVO = new PamProxyVO();
				PamVO pageVO = new PamVO();
				
				pageVO.setActEntityDTCollection(new ArrayList<Object>());
				oldPageProxyVO.setPamVO(pageVO);
				pamClientVO.setOldPamProxyVO(oldPageProxyVO);
				form.setPamClientVO(pamClientVO);
				((PamProxyVO)form.getPamClientVO().getOldPamProxyVO()).getPamVO();
				
				
		
				PowerMockito.doReturn(entityDT).when(PamStoreUtil.class, "createPamCaseEntity",  any(Long.class), any(String.class),  any(String.class), any(String.class), any(String.class));
	
			
				PowerMockito.doReturn(oldDT).when(PamStoreUtil.class, "getNbsCaseEntity",   any(String.class), any(Collection.class));
				
				
				when(entityDT.getActUid()).thenReturn(actUid);
					
	
				
				
				Whitebox.invokeMethod(new PamStoreUtil(), "setEntitiesForCreateEdit", form, proxyVO, revisionPatientUID, versionCtrlNbr, userId);
			
				int actualSize = proxyVO.getPamVO().getActEntityDTCollection().size();
				int expectedSize = 1;
	
				System.out.println("Iteration: #"+iteration+"\nExpected size of ActEntityDTCollection: "+expectedSize+"\nActual size of ActEntityDTCollection: "+actualSize);
				
				Assert.assertEquals(expectedSize, actualSize);
				
				
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setEntitiesForCreateEdit_test *******************");
			
		}		
	
}





/**
 * setParticipationsForCreate_test: the change on this method consisted of updating revisionPatientUID type from int to Long. As part of this test, we are making sure
 * whatever value we pass to the method as tempId, the method still works as expected and getTheParticipationDTCollection contains one DT created within that method.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PamStoreUtil.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetParticipationsForCreate_test{
	

	private String iteration;
	private Long revisionPatientUID;

	
	

	
 public SetParticipationsForCreate_test(String it, Long revisionPatientUID){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.revisionPatientUID=revisionPatientUID;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setParticipationsForCreate_test"+"_"+it++, 2L},
    		  {"setParticipationsForCreate_test"+"_"+it++, 1111111L},
    		  {"setParticipationsForCreate_test"+"_"+it++, 99999999L},
    		  {"setParticipationsForCreate_test"+"_"+it++, 88888888L},
    		  {"setParticipationsForCreate_test"+"_"+it++, 13242352L},
    		  {"setParticipationsForCreate_test"+"_"+it++, 13242352L},
    		  
    		  
			  
        

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
ParticipationDT participationDT;

@Mock
HttpServletRequest request;

@Spy
@InjectMocks
PamStoreUtil pamStoreUtil=Mockito.spy(new PamStoreUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PamStoreUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 participationDT = Mockito.mock(ParticipationDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(PamStoreUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setParticipationsForCreate_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setParticipationsForCreate_test *******************");
				
				Long phcUid = 111111L;
				
				PamForm form = new PamForm();

				PamProxyVO proxyVO = new PamProxyVO();
				PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
				PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
				publicHealthCaseDT.setPublicHealthCaseUid(phcUid);
				proxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				PowerMockito.doReturn(participationDT).when(PamStoreUtil.class, "createParticipation",  any(Long.class), any(String.class), any(String.class), any(String.class));


				Whitebox.invokeMethod(new PamStoreUtil(), "setParticipationsForCreate", proxyVO, form, revisionPatientUID, request);
			
				int actualSize = proxyVO.getTheParticipationDTCollection().size();
				int expectedSize = 1;
	
				System.out.println("Iteration: #"+iteration+"\nExpected size of getTheParticipationDTCollection: "+expectedSize+"\nActual size of getTheParticipationDTCollection: "+actualSize);
				
				Assert.assertEquals(expectedSize, actualSize);
				
				
				
				System.out.println("PASSED");
				System.out.println("******************* End test case named: setParticipationsForCreate_test *******************");
			
		}		
	
}







}
	
