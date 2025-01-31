package test.gov.cdc.nedss.webapp.nbs.action.client;


import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

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

@RunWith(Enclosed.class)
public class ClientUtil_tests {



/**
 * SetRaceInfoToClientVO_test: this unit test method consists of making sure the number of categories stored in clientVO.AnswerArray is the expected one.
 * For each category, we store one pair of category/race under clientVO. The unit test method will receive a String[] with format category|race that it will be parsed
 * to initialize the collection personVO.ThePersonRaceDTCollection. Then, after calling the method that we are testing, those values will be set in the ClientVO.
 * The test will pass if the number of races stored in the ClientVO is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns","gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBiding","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({PageLoadUtil.class, CachedDropDowns.class,NBSContext.class, DynamicBeanBinding.class,ClientUtil.class,LogUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class SetRaceInfoToClientVO_test{
	

	private String iteration;
	private String[] categoryRace = {};
	private int expectedRaceAnswerMapSize;
	
 public SetRaceInfoToClientVO_test(String it, String[] categoryRace,
		 int expectedRaceAnswerMapSize){
	 
	 super();

	 this.iteration = it;
	 this.categoryRace = categoryRace;
	 this.expectedRaceAnswerMapSize = expectedRaceAnswerMapSize;
 }

 
   @Parameterized.Parameters
   public static Collection input() {

	  int it = 0;
      return Arrays.asList(new Object[][]{
    		  
	  
    			  {"setRaceInfoToClientVO_test"+"_"+it++,
    				  new String[]{"U|U",
    				 "2131-1|2131-1",//Other
    				 "NASK|NASK",
    				 "PHC1175|PHC1175",//Refused
    				 "2054-5|2054-5",//Afrikaan
    				 "1002-5|1002-5",//aiian 
    				  "2106-3|2106-3",//white 
    				 "2028-9|2028-9",//asian 
    				  "2076-8|2076-8|",//hawaiian 
    				//Categories
    				  "2028-9|2029-7",//asian 
        			 "2076-8|2078-4",//hawaaian 
        			 "2106-3|2108-9",//white 
        			 "1002-5|1004-1",//indian 
        			 "2054-5|2056-0"},//african 
        			 5},
        
        	
        			 
        			 
        			 {"setRaceInfoToClientVO_test"+"_"+it++,
       				  new String[]{
       				//Categories
       				  "2028-9|2029-7",//asian 
           			 "2076-8|2078-4",//hawaaian 
           			 "2106-3|2108-9",//white 
           			 "1002-5|1004-1",//indian 
           			 "2054-5|2056-0"},//african 
           			 5},
           
           			 
           			 
           			{"setRaceInfoToClientVO_test"+"_"+it++,
       				  new String[]{"U|U",
       				 "2131-1|2131-1",//Other
       				 "NASK|NASK",
       				 "PHC1175|PHC1175",//Refused
       				 "2054-5|2054-5",//Afrikaan
       				 
       				//Categories
       				  "2028-9|2029-7",//asian 
           			 "2076-8|2078-4",//hawaaian 
           			},//african 
           			 2},
           
           			 
        			{"setRaceInfoToClientVO_test"+"_"+it++,
    				  new String[]{"U|U",
    				 "2131-1|2131-1",//Other
    				 "NASK|NASK",
    				 "PHC1175|PHC1175",//Refused
    				 "2054-5|2054-5"//Afrikaan
    				 
    				//Categories
    				
        			},//african 

        			 0},
            			 
            			 
            			 
    			    {"setRaceInfoToClientVO_test"+"_"+it++,
      				  new String[]{
      				//Categories
      				  "2028-9|2029-7",//asian 
      				"2028-9|2030-5",//asian 
      				"2028-9|2031-3",//asian 
    				  
          			 "2076-8|2078-4",//hawaaian 
          			"2106-3|2108-9",//white 
          			"2106-3|2108-9",//white 
         			 
          			 "1002-5|1004-1",//indian 
          			 "2054-5|2056-0"},//african 
          			 5},
                  			 
          			 
          			 
          			 {"setRaceInfoToClientVO_test"+"_"+it++,
         				  new String[]{
         				//Categories
         				  "2028-9|2029-7",//asian 
         				"2028-9|2030-5",//asian 
         				"2028-9|2031-3"//asian 
       				  },//african 
             		1},
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */



@org.mockito.Mock
LogUtils loggerMock;

@Mock
PersonVO personVO;

@Mock
PersonDT personDT;

@Spy
@InjectMocks
ClientUtil clientUtil=Mockito.spy(new ClientUtil());


	 @Before
	 public void initMocks() throws Exception {

		 
		 PowerMockito.spy(ClientUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
	     personVO = Mockito.mock(PersonVO.class);
	     personDT = Mockito.mock(PersonDT.class);
		 Whitebox.setInternalState(ClientUtil.class, "logger", loggerMock);
	 }
	 
	

	@Test
	public void setRaceInfoToClientVO_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: setRaceInfoToClientVO_test *******************");
				ClientVO clientVO = new ClientVO();
				String formCd = "PG_TB_LTBI_GA_Version_Page";//This is just an example, it could a different form, but it shouldn't affect this testing
				
				
				Collection<Object> personRaceDTColleciton = new ArrayList<Object>();
				
				for (int i = 0; i<categoryRace.length; i++){
					String catRace = categoryRace[i];
					String[] catRaceArray = catRace.split("\\|");
					
					PersonRaceDT raceDT = new PersonRaceDT();
					raceDT.setRaceCategoryCd(catRaceArray[0]);
					raceDT.setRaceCd(catRaceArray[1]);
					raceDT.setRecordStatusCd("ACTIVE");
					personRaceDTColleciton.add(raceDT);
				}
				
				
				Mockito.when(personVO.getThePersonRaceDTCollection()).thenReturn(personRaceDTColleciton);  
				
				
				Whitebox.invokeMethod(new ClientUtil(), "setRaceInfoToClientVO", personVO, clientVO, formCd);
			

				int actualRaceAnswerArrayMapSize = clientVO.getAnswerArrayMap().size();
			
				System.out.println("Iteration: #"+iteration+"\n");
				System.out.println("Expected number of detailed races in ClientVO.AnserArrayMap: "+expectedRaceAnswerMapSize+"\nActual number of detailed raced in ClientVO.AnserArrayMap: "+actualRaceAnswerArrayMapSize);

				Assert.assertEquals(expectedRaceAnswerMapSize, actualRaceAnswerArrayMapSize);
				
				
				 System.out.println("PASSED");
				 System.out.println("******************* End test case named: setRaceInfoToClientVO_test *******************");
			
		}		
	
}

}
	
