package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
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
@PrepareForTest({PageChangeConditionUtil.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class PageChangeConditionUtil_tests {



/**
 * SetParticipationsForChangeCondition_test: the change on this method consisted of updating phcUid and revisionUid type from int to Long. As part of this test, we need to make sure
 * that those long values were set correctly in the pageProxyVO.TheParticipationDTCollection() and the collection getTheParticipationDTCollection() from pageProxyVO.getPublicHealthCaseVO() is set to null.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageChangeConditionUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageChangeConditionUtil.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetParticipationsForChangeCondition_test{
	

	private String iteration;
	private Long revisionUid;
	private Long phcUid;
	private String typeCd;


	

	
 public SetParticipationsForChangeCondition_test(String it, Long revisionUid, Long phcUid, String typeCd){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.revisionUid = revisionUid;
	 this.phcUid = phcUid;
	 this.typeCd = typeCd;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setParticipationsForChangeCondition_test"+"_"+it++, 1L, 2L, "SubjOfPHC"}, 
    		  {"setParticipationsForChangeCondition_test"+"_"+it++, 2L, 3L, ""}, 
    		  {"setParticipationsForChangeCondition_test"+"_"+it++, 4444444L, 111111L, "SubjOfPHC"}, 
    		  {"setParticipationsForChangeCondition_test"+"_"+it++, 5555L, 6666666L, ""},
    		  
    
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   @Mock
   HttpServletRequest request;
   @Mock
   HttpSession session;






@Mock
LogUtils loggerMock;



@Mock
NBSSecurityObj secObj;


@Spy
@InjectMocks
PageChangeConditionUtil pageChangeConditionUtil=Mockito.spy(new PageChangeConditionUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageChangeConditionUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 session = Mockito.mock(HttpSession.class);
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 Whitebox.setInternalState(PageChangeConditionUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setParticipationsForChangeCondition_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setParticipationsForChangeCondition_test *******************");
				
			
				PageActProxyVO pageProxyVO = new PageActProxyVO();
				PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
				Collection<Object> participationDTCollection = new ArrayList<Object>();
				ParticipationDT participationDT = new ParticipationDT();
				participationDT.setTypeCd(typeCd);
				participationDTCollection.add(participationDT);
				pageProxyVO.getPublicHealthCaseVO().setTheParticipationDTCollection(participationDTCollection);
				
				
		
				
				
				PamForm form = new PamForm();

				 when((HttpSession) request.getSession()).thenReturn(session);
				 
				 when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
	
				 when(secObj.getPermission(any(String.class), any(String.class))).thenReturn(true);
				Whitebox.invokeMethod(new PageChangeConditionUtil(), "setParticipationsForChangeCondition", pageProxyVO, revisionUid, phcUid);
		
				Collection<Object> participationCollection1 = pageProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
				Collection<Object> participationCollection = pageProxyVO.getTheParticipationDTCollection();
				
				
				
				
				if(participationCollection!=null && participationCollection.size()>0){
					Iterator<Object> Ite = participationCollection.iterator();
					while(Ite.hasNext()){
						ParticipationDT pDT = (ParticipationDT)Ite.next(); 
						
						Long subjectEntityUidActual = pDT.getSubjectEntityUid();
						Long actUidActual = pDT.getActUid();
						
						System.out.println("Iteration: #"+iteration);
								
						if(typeCd!=null && typeCd.equalsIgnoreCase("SubjOfPHC")){
							pDT.setSubjectEntityUid(new Long(revisionUid));
							System.out.println("\nSubjectEntityUid sent: "+revisionUid+"\nSubjectEntityUid set in ParticipationDT: "+subjectEntityUidActual);
						
						}else{
							System.out.println("\nTypeCd is not SubjOfPHC, nothing set in the SubjectEntityUid");
							Assert.assertEquals(null, participationDT.getSubjectEntityUid());
						}
						
						pDT.setActUid(new Long(phcUid));
						System.out.println("\nActUid sent: "+phcUid+"\nActUid  set in ParticipationDT: "+actUidActual+"\n");
						
						
						Assert.assertEquals(null, participationCollection1);
						
						
						
					}
				}
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setParticipationsForChangeCondition_test *******************");
			
		}		
	
}













/**
 * SetEntitiesForChangeCondition_test: the change on this method consisted of updating actUid and revisionUid type from int to Long. As part of this test, we need to make sure
 * that those long values were set correctly in the pageProxyVO.pageVO.actEntityDTCollection.
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageChangeConditionUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,PageChangeConditionUtil.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetEntitiesForChangeCondition_test{
	

	private String iteration;
	private Long revisionUid;
	private Long phcUid;
	private String typeCd;


	

	
 public SetEntitiesForChangeCondition_test(String it, Long revisionUid, Long phcUid, String typeCd){
	 
	 super();
	 //Common Parameters
	 this.iteration = it;
	 this.revisionUid = revisionUid;
	 this.phcUid = phcUid;
	 this.typeCd = typeCd;
	

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
	   

      return Arrays.asList(new Object[][]{
    		  
    		  {"setEntitiesForChangeCondition_test"+"_"+it++, 1L, 2L, "SubjOfPHC"}, 
    		  {"setEntitiesForChangeCondition_test"+"_"+it++, 2L, 3L, ""}, 
    		  {"setEntitiesForChangeCondition_test"+"_"+it++, 4444444L, 111111L, "SubjOfPHC"}, 
    		  {"setEntitiesForChangeCondition_test"+"_"+it++, 5555L, 6666666L, ""},
    		  
    
        

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



   @Mock
   HttpServletRequest request;
   @Mock
   HttpSession session;






@Mock
LogUtils loggerMock;



@Mock
NBSSecurityObj secObj;


@Spy
@InjectMocks
PageChangeConditionUtil pageChangeConditionUtil=Mockito.spy(new PageChangeConditionUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(PageChangeConditionUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 request = Mockito.mock(HttpServletRequest.class);
		 session = Mockito.mock(HttpSession.class);
		 secObj = Mockito.mock(NBSSecurityObj.class);
		 Whitebox.setInternalState(PageChangeConditionUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setEntitiesForChangeCondition_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setEntitiesForChangeCondition_test *******************");
				
			//	Collection<Object> actEntityCollection  = pageProxyVO.getPageVO().getActEntityDTCollection();
				
				
				PageActProxyVO pageProxyVO = new PageActProxyVO();
				PageVO pageVO = new PageVO();
				Collection<Object> actEntityDTCollection = new ArrayList<Object>();
				NbsActEntityDT actEntityDT = new NbsActEntityDT();
				actEntityDT.setTypeCd(typeCd);
				actEntityDTCollection.add(actEntityDT);
				pageProxyVO.setPageVO(pageVO);
				pageProxyVO.getPageVO().setActEntityDTCollection(actEntityDTCollection);
				
			

				 when((HttpSession) request.getSession()).thenReturn(session);
				 
				 when((NBSSecurityObj) session.getAttribute("NBSSecurityObject")).thenReturn(secObj);
	
				 when(secObj.getPermission(any(String.class), any(String.class))).thenReturn(true);
				Whitebox.invokeMethod(new PageChangeConditionUtil(), "setEntitiesForChangeCondition", pageProxyVO, revisionUid, phcUid);
		
				//Collection<Object> participationCollection1 = pageProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
				Collection<Object> actEntityDTCollection1 = pageProxyVO.getPageVO().getActEntityDTCollection();
				
				
				
				
				if(actEntityDTCollection1!=null && actEntityDTCollection1.size()>0){
					Iterator<Object> Ite = actEntityDTCollection1.iterator();
					while(Ite.hasNext()){
						NbsActEntityDT actDT = (NbsActEntityDT)Ite.next(); 
						
						Long subjectEntityUidActual = actDT.getEntityUid();
						Long actUidActual = actDT.getActUid();
						
						System.out.println("Iteration: #"+iteration);
								
						if(typeCd!=null && typeCd.equalsIgnoreCase("SubjOfPHC")){
					
							System.out.println("\nSubjectEntityUid sent: "+revisionUid+"\nSubjectEntityUid set in NbsActEntityDT: "+subjectEntityUidActual);
							Assert.assertEquals(revisionUid, subjectEntityUidActual);

						}else{
							System.out.println("\nTypeCd is not SubjOfPHC, nothing set in the SubjectEntityUid");
							Assert.assertEquals(null,subjectEntityUidActual);
						}
						
					
						System.out.println("\nActUid sent: "+phcUid+"\nActUid  set in NbsActEntityDT: "+actUidActual+"\n");
						Assert.assertEquals(phcUid, actUidActual);
						
						
					}
				}
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setEntitiesForChangeCondition_test *******************");
			
		}		
	
}











}
	
