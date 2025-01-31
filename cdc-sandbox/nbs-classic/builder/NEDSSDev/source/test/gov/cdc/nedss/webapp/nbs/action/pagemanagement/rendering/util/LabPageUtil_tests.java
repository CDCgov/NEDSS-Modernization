package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import org.mockito.internal.matchers.Any;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;




/**
Methods that have been unit tested so far:


 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabPageUtil","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({LabPageUtil.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class LabPageUtil_tests {






/**
 * setParticipationUidFromClientVO_test:  the changes to this method consists of changing the type of tempID from int to long. Due to the simplicity of this method (calling another method),
 * we are going to verify that this second static method is called once, meaning the method passed for the parameters sent.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabPageUtil","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({LabPageUtil.class, LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetParticipationUidFromClientVO_test{
	

	private String iteration;
	private String userId;
	private Long tempID;
	private Long actUID;
	private String programAreaCode;

	
	

	
 public SetParticipationUidFromClientVO_test(String it, String userId, Long tempID, Long actUID, String programAreaCode){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.userId = userId;
	 this.tempID = tempID;
	 this.actUID = actUID;
	 this.programAreaCode = programAreaCode;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setParticipationUidFromClientVO_test"+"_"+it++,"111111111", 2L, 2L, "STD"},
    		  {"setParticipationUidFromClientVO_test"+"_"+it++,"111111111", 1000000L, 2L, "STD"},
    		  {"setParticipationUidFromClientVO_test"+"_"+it++,"111111111", 99999999L, 2L, "STD"},
    		  {"setParticipationUidFromClientVO_test"+"_"+it++,"111111111", 88888887L, 2L, "STD"},
    		  {"setParticipationUidFromClientVO_test"+"_"+it++,"111111111", 1L, 2L, "STD"},
    		  {"setParticipationUidFromClientVO_test"+"_"+it++,"111111111", 0L, 2L, "STD"},
    		  
    		
    		  
			  
        

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
LabPageUtil labPageUtil=Mockito.spy(new LabPageUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 participationDT = Mockito.mock(ParticipationDT.class);
		 oldDT = Mockito.mock(NbsActEntityDT.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setParticipationUidFromClientVO_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setParticipationUidFromClientVO_test *******************");

				PageForm form = new PageForm();

				LabResultProxyVO labResultProxyVO = new LabResultProxyVO();
			
			    PowerMockito.mockStatic(PageStoreUtil.class);
			  	PowerMockito.doNothing().when(PageStoreUtil.class, "setEntitiesForCreate", labResultProxyVO,form, tempID, userId,actUID, programAreaCode,NEDSSConstants.CLASS_CD_OBS, request);

				Whitebox.invokeMethod(new LabPageUtil(), "setParticipationUidFromClientVO", labResultProxyVO, tempID, userId, actUID, programAreaCode, form, request);
			
				
				PowerMockito.verifyStatic(Mockito.times(1)); // Verify that the following mock method was called exactly once
				PageStoreUtil.setEntitiesForCreate(labResultProxyVO,form, tempID, userId,actUID, programAreaCode,NEDSSConstants.CLASS_CD_OBS, request);
		
				System.out.println("Iteration: #"+iteration+"\nThe static method setEntitiesForCreate called from setParticipationUidFromClientVO has been called once as expected");
				System.out.println("PASSED");
				System.out.println("******************* End test case named: setParticipationUidFromClientVO_test *******************");
			
		}		
	
}








/**
 * createObservationVO_test:  the changes that were done to this method consisted of changing the type of tempID from int to long. What we are verifying as part of this JUnit testing
 * is to make sure all the values set in the ObservationDT (including the new Long variable) are set correctly in the Object, by comparing the values sent as parameter to the method
 * with the values in the ObservationDT.
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabPageUtil","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({LabPageUtil.class, LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class CreateObservationVO_test{
	

	private String iteration;
	private Long tempID;
	private String code;
	private String codeDescTxt;
	private String codeSystemCode;
	private String codeSystemDescTxt;
	private String statusCode;
	private String recordStatusCode;
	private String obsDomainCodeSt1; 
    
    
	
	

	
 public CreateObservationVO_test(String it, Long tempID, String code, String codeDescTxt, String codeSystemCode, String codeSystemDescTxt, String statusCode, String recordStatusCode, String obsDomainCodeSt1){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.tempID = tempID;
	 this.code = code;
	 this.codeDescTxt = codeDescTxt;
	 this.codeSystemCode = codeSystemCode;
	 this.codeSystemDescTxt = codeSystemDescTxt;
	 this.statusCode = statusCode;
	 this.recordStatusCode = recordStatusCode;
	 this.obsDomainCodeSt1 = obsDomainCodeSt1; 

 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"createObservationVO_test"+"_"+it++, -5L, "LAB222",null,"No Information Given",null, "A", null, "R_Order"},
    		  {"createObservationVO_test"+"_"+it++, -8L, "LAB329"," 2.16.840.1.114222.4.5.1","Isolate Tracking","NEDSS Base System", "D", "ACTIVE", "I_Order"},
    		  

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



@Mock
LogUtils loggerMock;



@Mock
HttpServletRequest request;

@Spy
@InjectMocks
LabPageUtil labPageUtil=Mockito.spy(new LabPageUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageCreateHelper.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 Whitebox.setInternalState(PageCreateHelper.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void createObservationVO_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: createObservationVO_test *******************");
				
	

		 	    Timestamp effectiveFromTime = new Timestamp(System.currentTimeMillis());
		  	    Timestamp effectiveToTime = new Timestamp(System.currentTimeMillis());	 
					 
				ObservationVO observationVO = Whitebox.invokeMethod(new LabPageUtil(), "createObservationVO", tempID,code, codeDescTxt, codeSystemCode, codeSystemDescTxt,
				effectiveFromTime, effectiveToTime, statusCode, recordStatusCode, obsDomainCodeSt1);
			
				ObservationDT observationDT = observationVO.getTheObservationDT();



				System.out.println("Iteration: #"+iteration+"\nTempID sent as parameter:" +tempID+"\nTempID set in the ObservationVO.ObservationDT: "+ observationDT.getObservationUid());
				System.out.println("\ncode sent as parameter:" +code+"\ncode set in the ObservationVO.ObservationDT: "+observationDT.getCd());
				System.out.println("\ncodeDescTxt sent as parameter:" +codeDescTxt+"\ncode set in the ObservationVO.ObservationDT: "+observationDT.getCdDescTxt());
				System.out.println("\ncodeSystemCode sent as parameter:" +codeSystemCode+"\ncodeSystemCode set in the ObservationVO.ObservationDT: "+observationDT.getCdSystemCd());
				System.out.println("\ncodeSystemDescTxt sent as parameter:" +codeSystemDescTxt+"\ncodeSystemDescTxt set in the ObservationVO.ObservationDT: "+codeSystemDescTxt);
				System.out.println("\nstatusCode sent as parameter:" +statusCode+"\nstatusCode set in the ObservationVO.ObservationDT: "+observationDT.getStatusCd());
				System.out.println("\nrecordStatusCode sent as parameter:" +recordStatusCode+"\nrecordStatusCode set in the ObservationVO.ObservationDT: "+ observationDT.getRecordStatusCd());
				System.out.println("\nobsDomainCodeSt1 sent as parameter:" +obsDomainCodeSt1+"\nobsDomainCodeSt1 set in the ObservationVO.ObservationDT: "+observationDT.getObsDomainCdSt1());
				System.out.println("\neffectiveFromTime sent as parameter:" +effectiveFromTime+"\neffectiveFromTime set in the ObservationVO.ObservationDT: "+observationDT.getEffectiveFromTime());
				System.out.println("\neffectiveToTime sent as parameter:" +effectiveToTime+"\neffectiveToTime set in the ObservationVO.ObservationDT: "+observationDT.getEffectiveToTime());


				 
				 Assert.assertEquals(tempID, observationDT.getObservationUid());
				 Assert.assertEquals(code, observationDT.getCd());
				 Assert.assertEquals(codeDescTxt, observationDT.getCdDescTxt());
				 Assert.assertEquals(codeSystemCode, observationDT.getCdSystemCd());
				 Assert.assertEquals(codeSystemDescTxt, codeSystemDescTxt);
				 Assert.assertEquals(statusCode, observationDT.getStatusCd());
				 Assert.assertEquals(recordStatusCode, observationDT.getRecordStatusCd());
				 Assert.assertEquals(obsDomainCodeSt1, observationDT.getObsDomainCdSt1());
				 Assert.assertEquals(effectiveFromTime, observationDT.getEffectiveFromTime());
				 Assert.assertEquals(effectiveToTime, observationDT.getEffectiveToTime());
				 
				System.out.println("PASSED");
				System.out.println("******************* End test case named: createObservationVO_test *******************");
			
		}		
	
}









}
	
