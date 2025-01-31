package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;



import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
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

- processDrugsPhenotypic
- processDrugsMDR

 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class CommonPDFPrintForm_tests {


/**
 * ProcessDrugsPhenotypic_test: this method will verify 2 things:
 * 
 * - it will validate the number of values in the map after reordering is the expected one
 * - the value sent as a parameter is in the expected position (also sent as a parameter) after reordering it.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessDrugsPhenotypic_test{
	

	private String iteration;
	private int numberOfRows;//this represents the number of rows (drugs) entered in the table
	private String drugToTest;//this is the specific drug we will make sure it has been inserted in the expected position
	private int positionToTest;//the position where we expect the previous drug to be interted
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessDrugsPhenotypic_test(String it, int numberOfRows, String drugToTest, int positionToTest,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.numberOfRows = numberOfRows;//this represents the number of rows (drugs) entered in the table
	 this.drugToTest = drugToTest;//this is the specific drug we will make sure it has been inserted in the expected position
	 this.positionToTest = positionToTest;//the position where we expect the previous drug to be interted
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	 //Used for test1, Linezolid with 1 row in the table
	    Map<String, String> answerMap = new HashMap<String, String>();
		int i =1;
		
		answerMap.put("LABAST6_R"+i+"_CDT","Linezolid");
		answerMap.put("LABAST5_R"+i,"dateCollected1");
		answerMap.put("LABAST14_R"+i,"dateReported1");
		answerMap.put("LABAST3_R"+i+"_CDT","specimenSource1");
		answerMap.put("LABAST8_R"+i+"_CD","result1");
		answerMap.put("LABAST7_R"+i+"_CDT","testMethod1");
		
		//Used for test2, 	//Isoniazid 1 with 1 rows
		
		  Map<String, String> answerMap2 = new HashMap<String, String>();
		  i =1;
		
		answerMap2.put("LABAST6_R"+i+"_CDT","Isoniazid");
		answerMap2.put("LABAST5_R"+i,"dateCollected1");
		answerMap2.put("LABAST14_R"+i,"dateReported1");
		answerMap2.put("LABAST3_R"+i+"_CDT","specimenSource1");
		answerMap2.put("LABAST8_R"+i+"_CD","result1");
		answerMap2.put("LABAST7_R"+i+"_CDT","testMethod1");
			
		
		
		//Used for test3, 	//Streptomycin position 5 with 3 rows
		
		Map<String, String> answerMap3 = new HashMap<String, String>();
		i =1;
		answerMap3.put("LABAST6_R"+i+"_CDT","Isoniazid");
		answerMap3.put("LABAST5_R"+i,"dateCollected1");
		answerMap3.put("LABAST14_R"+i,"dateReported1");
		answerMap3.put("LABAST3_R"+i+"_CDT","specimenSource1");
		answerMap3.put("LABAST8_R"+i+"_CD","result1");
		answerMap3.put("LABAST7_R"+i+"_CDT","testMethod1");
			
		i++;
		answerMap3.put("LABAST6_R"+i+"_CDT","Linezolid");
		answerMap3.put("LABAST5_R"+i,"dateCollected1");
		answerMap3.put("LABAST14_R"+i,"dateReported1");
		answerMap3.put("LABAST3_R"+i+"_CDT","specimenSource1");
		answerMap3.put("LABAST8_R"+i+"_CD","result1");
		answerMap3.put("LABAST7_R"+i+"_CDT","testMethod1");
			
		i++;
		answerMap3.put("LABAST6_R"+i+"_CDT","Streptomycin");
		answerMap3.put("LABAST5_R"+i,"dateCollected1");
		answerMap3.put("LABAST14_R"+i,"dateReported1");
		answerMap3.put("LABAST3_R"+i+"_CDT","specimenSource1");
		answerMap3.put("LABAST8_R"+i+"_CD","result1");
		answerMap3.put("LABAST7_R"+i+"_CDT","testMethod1");
			
		
		
		//Used for test4, 	//Streptomycin position 5 with 0 rows
		
		Map<String, String> answerMap4 = new HashMap<String, String>();
		
		
		//Used for test5, 	//Isoniazid position 1 with 2 rows
		
		 Map<String, String> answerMap5 = new HashMap<String, String>();
			i =1;
			
			answerMap5.put("LABAST6_R"+i+"_CDT","Linezolid");
			answerMap5.put("LABAST5_R"+i,"dateCollected1");
			answerMap5.put("LABAST14_R"+i,"dateReported1");
			answerMap5.put("LABAST3_R"+i+"_CDT","specimenSource1");
			answerMap5.put("LABAST8_R"+i+"_CD","result1");
			answerMap5.put("LABAST7_R"+i+"_CDT","testMethod1");
			
			
			  i++;
			
			  answerMap5.put("LABAST6_R"+i+"_CDT","Isoniazid");
			  answerMap5.put("LABAST5_R"+i,"dateCollected1");
			  answerMap5.put("LABAST14_R"+i,"dateReported1");
			  answerMap5.put("LABAST3_R"+i+"_CDT","specimenSource1");
			  answerMap5.put("LABAST8_R"+i+"_CD","result1");
			  answerMap5.put("LABAST7_R"+i+"_CDT","testMethod1");
				
			
			
		
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    //test name, number of rows in the table, drug name to test, position expected of the drug indicated, the map with all the answers not sorted
    		  {"processDrugsPhenotypic_test"+"_"+it++,1,"Linezolid",19, answerMap},
    		  {"processDrugsPhenotypic_test"+"_"+it++,1,"Isoniazid",1, answerMap2},
    		  {"processDrugsPhenotypic_test"+"_"+it++,3,"Streptomycin",5, answerMap3},
    		  {"processDrugsPhenotypic_test"+"_"+it++,0,null,1, answerMap4},
    		  {"processDrugsPhenotypic_test"+"_"+it++,2,"Isoniazid",1, answerMap5},
    		  
    		  
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	
	Map<String, String> LABAST6Drugs= new HashMap<String, String>();
	Map<String, String> LABAST6DrugName = new HashMap<String, String>();
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		// Whitebox.setInternalState(CDCRVCTForm.class, "logger", loggerMock);
		// Whitebox.setInternalState(CDCRVCTForm.class, "pageForm", form);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LABAST6Drugs", LABAST6Drugs);
		 
		
		 
 		 
	 }
	 
	

	@Test
	public void processDrugsPhenotypic_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processDrugsPhenotypic_test *******************");
	
				
				

				if(LABAST6DrugName.size()==0){
					LABAST6DrugName.put("18860-7","Amikacin");
					LABAST6DrugName.put("LAB675","Bedaquiline");
					LABAST6DrugName.put("18872-2","Capreomycin");
					LABAST6DrugName.put("18906-8","Ciprofloxacin");
					LABAST6DrugName.put("23627-3","Clofazimine");
					LABAST6DrugName.put("18914-2","Cycloserine");
					LABAST6DrugName.put("LAB676","Delamanid");
					LABAST6DrugName.put("18921-7","Ethambutol");
					LABAST6DrugName.put("18922-5","Ethionamide");
					LABAST6DrugName.put("18934-0","Isoniazid");
					LABAST6DrugName.put("18935-7","Kanamycin");
					LABAST6DrugName.put("20629-2","Levofloxacin");
					LABAST6DrugName.put("29258-1","Linezolid");
					LABAST6DrugName.put("31039-1","Moxifloxacin");
					LABAST6DrugName.put("18959-7","Ofloxacin");
					LABAST6DrugName.put("LAB674","Other Quinolones");
					LABAST6DrugName.put("OTH","Other Test Type");
					LABAST6DrugName.put("23629-9","Para-Aminesalicylicacid");
					LABAST6DrugName.put("93850-6","Pretomanid");
					LABAST6DrugName.put("18973-8","Pyrazinamide");
					LABAST6DrugName.put("19149-4","Rifabutin");
					LABAST6DrugName.put("18974-6","Rifampin");
					LABAST6DrugName.put("76627-9","Rifapentine");
					LABAST6DrugName.put("18982-9","Streptomycin");

				}
				
				


				if(LABAST6Drugs.size()==0){
					//reading from the map to make sure the names are consistent
					LABAST6Drugs.put(LABAST6DrugName.get("18934-0"), "1");//Isoniazid
					LABAST6Drugs.put(LABAST6DrugName.get("18974-6"), "2");//Rifampin
					LABAST6Drugs.put(LABAST6DrugName.get("18973-8"), "3");//Pyrazinamide
					LABAST6Drugs.put(LABAST6DrugName.get("18921-7"), "4");//Ethambutol
					LABAST6Drugs.put(LABAST6DrugName.get("18982-9"), "5");//Streptomycin		
					LABAST6Drugs.put(LABAST6DrugName.get("19149-4"), "6");//Rifabutin		
					LABAST6Drugs.put(LABAST6DrugName.get("76627-9"),"7");	//Rifapentine	
					LABAST6Drugs.put(LABAST6DrugName.get("18922-5"), "8");	//Ethionamide	
					LABAST6Drugs.put(LABAST6DrugName.get("18860-7"), "9");		//Amikacin
					LABAST6Drugs.put(LABAST6DrugName.get("18935-7"), "10");//Kanamycin		
					LABAST6Drugs.put(LABAST6DrugName.get("18872-2"), "11");//Capreomycin		
					LABAST6Drugs.put(LABAST6DrugName.get("18906-8"), "12");//Ciprofloxacin		
					LABAST6Drugs.put(LABAST6DrugName.get("20629-2"), "13");//Levofloxacin		
					LABAST6Drugs.put(LABAST6DrugName.get("18959-7"), "14");//Ofloxacin	
					LABAST6Drugs.put(LABAST6DrugName.get("31039-1"), "15");//Moxifloxacin	
					LABAST6Drugs.put(LABAST6DrugName.get("LAB674"), "16");//Other Quinolones	
					LABAST6Drugs.put(LABAST6DrugName.get("18914-2"), "17");//Cycloserine	
					LABAST6Drugs.put(LABAST6DrugName.get("23629-9"), "18");//Para-Aminesalicylicacid	
					LABAST6Drugs.put(LABAST6DrugName.get("29258-1"), "19");	//Linezolid
					LABAST6Drugs.put(LABAST6DrugName.get("LAB675"), "20");//Bedaquiline		
					LABAST6Drugs.put(LABAST6DrugName.get("LAB676"), "21");	//Delamanid
					LABAST6Drugs.put(LABAST6DrugName.get("23627-3"), "22");//Clofazimine	
					LABAST6Drugs.put(LABAST6DrugName.get("93850-6"), "23");		//Pretomanid
				//	LABAST6Drugs.put(LABAST6DrugName.get("OTH"), "24");		//Other Test Type	
					
				}
				
		
	
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processDrugsPhenotypic", formSpecificQuestionAnswerMap);
		
				int totalNumberValues = numberOfRows*6;
				int actualTotalNumberValues = 	formSpecificQuestionAnswerMap.size();
				
				System.out.println("Number of expected values in the sorted map: "+totalNumberValues+", actual total number: "+actualTotalNumberValues);
				Assert.assertEquals(totalNumberValues, actualTotalNumberValues);
				String drugInPosition = formSpecificQuestionAnswerMap.get("LABAST6_R"+positionToTest+"_CDT");
				
				System.out.println("Drug: "+drugToTest+" expected to be found in position "+positionToTest+". Drug found in position "+positionToTest +" is: "+drugInPosition);
				Assert.assertEquals(drugToTest, drugInPosition);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processDrugsPhenotypic_test *******************");
			
		}	
	
}
	





/**
 * ProcessDrugsMDR_test: this method will verify 2 things:
 * 
 * - it will validate the number of values in the map after reordering is the expected one
 * - the value sent as a parameter is in the expected position (also sent as a parameter) after reordering it.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessDrugsMDR_test{
	

	private String iteration;
	private int numberOfRows;//this represents the number of rows (drugs) entered in the table
	private String drugToTest;//this is the specific drug we will make sure it has been inserted in the expected position
	private int positionToTest;//the position where we expect the previous drug to be interted
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessDrugsMDR_test(String it, int numberOfRows, String drugToTest, int positionToTest,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.numberOfRows = numberOfRows;//this represents the number of rows (drugs) entered in the table
	 this.drugToTest = drugToTest;//this is the specific drug we will make sure it has been inserted in the expected position
	 this.positionToTest = positionToTest;//the position where we expect the previous drug to be interted
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	 //Used for test1, Linezolid with 1 row in the table
	    Map<String, String> answerMap = new HashMap<String, String>();
			int i =1;
			
			answerMap.put("INV1158_R"+i+"_CDT","Linezolid");
			answerMap.put("INV1159_R"+i+"_CD","length1");
			
		
			
		    Map<String, String> answerMap2 = new HashMap<String, String>();
			i =1;
			
			answerMap2.put("INV1158_R"+i+"_CDT","Bedaquiline");
			answerMap2.put("INV1159_R"+i+"_CD","length1");

			
		    Map<String, String> answerMap3 = new HashMap<String, String>();
			 i =1;
			
			answerMap3.put("INV1158_R"+i+"_CDT","Rifapentine");
			answerMap3.put("INV1159_R"+i+"_CD","length1");

			
		    Map<String, String> answerMap4 = new HashMap<String, String>();
			 i =1;
			
			 answerMap4.put("INV1158_R"+i+"_CDT","Ethambutol");
			 answerMap4.put("INV1159_R"+i+"_CD","length1");
					
		    Map<String, String> answerMap5 = new HashMap<String, String>();
		    
			 i =1;
				
			 answerMap5.put("INV1158_R"+i+"_CDT","Ethambutol");
			 answerMap5.put("INV1159_R"+i+"_CD","length1");
			 i ++;
				
			 answerMap5.put("INV1158_R"+i+"_CDT","Rifapentine");
			 answerMap5.put("INV1159_R"+i+"_CD","length1");
						
								
			i++;
			answerMap5.put("INV1158_R"+i+"_CDT","Bedaquiline");
			answerMap5.put("INV1159_R"+i+"_CD","length1");


		    Map<String, String> answerMap6 = new HashMap<String, String>();
		    
		    
			
		    Map<String, String> answerMap7 = new HashMap<String, String>();
			 i =1;
				
			 answerMap7.put("INV1158_R"+i+"_CDT","Linezolid");
			 answerMap7.put("INV1159_R"+i+"_CD","length1");
			 i ++;
				
			 answerMap7.put("INV1158_R"+i+"_CDT","Linezolid");
			 answerMap7.put("INV1159_R"+i+"_CD","length1");
						
				
			 
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    //test name, number of rows in the table, drug name to test, position expected of the drug indicated, the map with all the answers not sorted
    		  {"processDrugsPhenotypic_test"+"_"+it++,1,"Linezolid",19, answerMap},
    		  {"processDrugsPhenotypic_test"+"_"+it++,1,"Bedaquiline",20, answerMap2},
    		  {"processDrugsPhenotypic_test"+"_"+it++,1,"Rifapentine",7, answerMap3},
    		  {"processDrugsPhenotypic_test"+"_"+it++,1,"Ethambutol",4, answerMap4},
    		  {"processDrugsPhenotypic_test"+"_"+it++,3,"Ethambutol",4, answerMap5},
    		  {"processDrugsPhenotypic_test"+"_"+it++,3,"Rifapentine",7, answerMap5},
    		  {"processDrugsPhenotypic_test"+"_"+it++,0,null,20, answerMap6},
    		  {"processDrugsPhenotypic_test"+"_"+it++,2,"Linezolid",24, answerMap7},//2 Linezolid, the second one would be inserted in the first empty row

    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	
	Map<String, String> LABAST6Drugs= new HashMap<String, String>();
	Map<String, String> LABAST6DrugName = new HashMap<String, String>();
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LABAST6Drugs", LABAST6Drugs);
	 }
	 
	

	@Test
	public void processDrugsMDR_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processDrugsMDR_test *******************");

				if(LABAST6DrugName.size()==0){
					LABAST6DrugName.put("18860-7","Amikacin");
					LABAST6DrugName.put("LAB675","Bedaquiline");
					LABAST6DrugName.put("18872-2","Capreomycin");
					LABAST6DrugName.put("18906-8","Ciprofloxacin");
					LABAST6DrugName.put("23627-3","Clofazimine");
					LABAST6DrugName.put("18914-2","Cycloserine");
					LABAST6DrugName.put("LAB676","Delamanid");
					LABAST6DrugName.put("18921-7","Ethambutol");
					LABAST6DrugName.put("18922-5","Ethionamide");
					LABAST6DrugName.put("18934-0","Isoniazid");
					LABAST6DrugName.put("18935-7","Kanamycin");
					LABAST6DrugName.put("20629-2","Levofloxacin");
					LABAST6DrugName.put("29258-1","Linezolid");
					LABAST6DrugName.put("31039-1","Moxifloxacin");
					LABAST6DrugName.put("18959-7","Ofloxacin");
					LABAST6DrugName.put("LAB674","Other Quinolones");
					LABAST6DrugName.put("OTH","Other Test Type");
					LABAST6DrugName.put("23629-9","Para-Aminesalicylicacid");
					LABAST6DrugName.put("93850-6","Pretomanid");
					LABAST6DrugName.put("18973-8","Pyrazinamide");
					LABAST6DrugName.put("19149-4","Rifabutin");
					LABAST6DrugName.put("18974-6","Rifampin");
					LABAST6DrugName.put("76627-9","Rifapentine");
					LABAST6DrugName.put("18982-9","Streptomycin");

				}
				
				


				if(LABAST6Drugs.size()==0){
					//reading from the map to make sure the names are consistent
					LABAST6Drugs.put(LABAST6DrugName.get("18934-0"), "1");//Isoniazid
					LABAST6Drugs.put(LABAST6DrugName.get("18974-6"), "2");//Rifampin
					LABAST6Drugs.put(LABAST6DrugName.get("18973-8"), "3");//Pyrazinamide
					LABAST6Drugs.put(LABAST6DrugName.get("18921-7"), "4");//Ethambutol
					LABAST6Drugs.put(LABAST6DrugName.get("18982-9"), "5");//Streptomycin		
					LABAST6Drugs.put(LABAST6DrugName.get("19149-4"), "6");//Rifabutin		
					LABAST6Drugs.put(LABAST6DrugName.get("76627-9"),"7");	//Rifapentine	
					LABAST6Drugs.put(LABAST6DrugName.get("18922-5"), "8");	//Ethionamide	
					LABAST6Drugs.put(LABAST6DrugName.get("18860-7"), "9");		//Amikacin
					LABAST6Drugs.put(LABAST6DrugName.get("18935-7"), "10");//Kanamycin		
					LABAST6Drugs.put(LABAST6DrugName.get("18872-2"), "11");//Capreomycin		
					LABAST6Drugs.put(LABAST6DrugName.get("18906-8"), "12");//Ciprofloxacin		
					LABAST6Drugs.put(LABAST6DrugName.get("20629-2"), "13");//Levofloxacin		
					LABAST6Drugs.put(LABAST6DrugName.get("18959-7"), "14");//Ofloxacin	
					LABAST6Drugs.put(LABAST6DrugName.get("31039-1"), "15");//Moxifloxacin	
					LABAST6Drugs.put(LABAST6DrugName.get("LAB674"), "16");//Other Quinolones	
					LABAST6Drugs.put(LABAST6DrugName.get("18914-2"), "17");//Cycloserine	
					LABAST6Drugs.put(LABAST6DrugName.get("23629-9"), "18");//Para-Aminesalicylicacid	
					LABAST6Drugs.put(LABAST6DrugName.get("29258-1"), "19");	//Linezolid
					LABAST6Drugs.put(LABAST6DrugName.get("LAB675"), "20");//Bedaquiline		
					LABAST6Drugs.put(LABAST6DrugName.get("LAB676"), "21");	//Delamanid
					LABAST6Drugs.put(LABAST6DrugName.get("23627-3"), "22");//Clofazimine	
					LABAST6Drugs.put(LABAST6DrugName.get("93850-6"), "23");		//Pretomanid
				//	LABAST6Drugs.put(LABAST6DrugName.get("OTH"), "24");		//Other Test Type	
					
				}
				
		
	
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processDrugsMDR", formSpecificQuestionAnswerMap);
		
				int totalNumberValues = numberOfRows*2;
				int actualTotalNumberValues = 	formSpecificQuestionAnswerMap.size();
				
				System.out.println("Number of expected values in the sorted map: "+totalNumberValues+", actual total number: "+actualTotalNumberValues);
				Assert.assertEquals(totalNumberValues, actualTotalNumberValues);
				String drugInPosition = formSpecificQuestionAnswerMap.get("INV1158_R"+positionToTest+"_CDT");
			
				System.out.println("Drug: "+drugToTest+" expected to be found in position "+positionToTest+". Drug found in position "+positionToTest +" is: "+drugInPosition);
				Assert.assertEquals(drugToTest, drugInPosition);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processDrugsMDR_test *******************");
			
		}	
	
}
	




/**
 * ProcessSideEffects_test: this method will verify 2 things:
 * 
 * - it will validate the number of values in the map after reordering is the expected one
 * - the value sent as a parameter is in the expected position (also sent as a parameter) after reordering it.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessSideEffects_test{
	

	private String iteration;
	private int numberOfRows;//this represents the number of rows (drugs) entered in the table
	private String drugToTest;//this is the specific drug we will make sure it has been inserted in the expected position
	private int positionToTest;//the position where we expect the previous drug to be interted
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessSideEffects_test(String it, int numberOfRows, String drugToTest, int positionToTest,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.numberOfRows = numberOfRows;//this represents the number of rows (drugs) entered in the table
	 this.drugToTest = drugToTest;//this is the specific drug we will make sure it has been inserted in the expected position
	 this.positionToTest = positionToTest;//the position where we expect the previous drug to be interted
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	 //Used for test1, Linezolid with 1 row in the table
	    Map<String, String> answerMap = new HashMap<String, String>();
			int i =1;
			
			answerMap.put("42563_7_CD_R"+i+"_CDT","Depression");
			answerMap.put("INV1164_R"+i+"_CD","experienced1");
			
		
			
		 Map<String, String> answerMap1 = new HashMap<String, String>();
			 i =1;
			
			answerMap1.put("42563_7_CD_R"+i+"_CDT","Tinnitus");
			answerMap1.put("INV1164_R"+i+"_CD","experienced1");
			
			Map<String, String> answerMap2 = new HashMap<String, String>();
			 i =1;
			
			 answerMap2.put("42563_7_CD_R"+i+"_CDT","Peripheral Neuropathy");
			 answerMap2.put("INV1164_R"+i+"_CD","experienced1");
			
			
			 Map<String, String> answerMap3 = new HashMap<String, String>();
			 i =1;
			
			 answerMap3.put("42563_7_CD_R"+i+"_CDT","Peripheral Neuropathy");
			 answerMap3.put("INV1164_R"+i+"_CD","experienced1");
			 i++;
			 answerMap3.put("42563_7_CD_R"+i+"_CDT","Tinnitus");
			 answerMap3.put("INV1164_R"+i+"_CD","experienced1");
				
			
			 Map<String, String> answerMap4 = new HashMap<String, String>();
			 i = 1;
			
			 answerMap4.put("42563_7_CD_R"+i+"_CDT","Peripheral Neuropathy");
			 answerMap4.put("INV1164_R"+i+"_CD","experienced1");
			 i++;
			 
			 answerMap4.put("42563_7_CD_R"+i+"_CDT","Tinnitus");
			 answerMap4.put("INV1164_R"+i+"_CD","experienced1");
			 i++;
			 
			 answerMap4.put("42563_7_CD_R"+i+"_CDT","Depression");
			 answerMap4.put("INV1164_R"+i+"_CD","experienced1");
				
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    //test name, number of rows in the table, drug name to test, position expected of the drug indicated, the map with all the answers not sorted
    		  {"processSideEffects_test"+"_"+it++,1,"Depression",5, answerMap},
    		  {"processSideEffects_test"+"_"+it++,1,"Tinnitus",8, answerMap1},
    		  {"processSideEffects_test"+"_"+it++,1,"Peripheral Neuropathy",4, answerMap2},
    		  {"processSideEffects_test"+"_"+it++,2,"Peripheral Neuropathy",4, answerMap3},
    		  {"processSideEffects_test"+"_"+it++,3,"Depression",5, answerMap4},

    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> code42563_7_CDSideEffect= new HashMap<String, String>();
	Map<String, String> code42563_7sideEffects = new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code42563_7sideEffects", code42563_7sideEffects);
		 
	 }
	 
	

	@Test
	public void processSideEffects_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processSideEffects_test *******************");

				
				if(code42563_7_CDSideEffect.size()==0){
					
					code42563_7_CDSideEffect.put("57676002","Arthralgia");
					code42563_7_CDSideEffect.put("36358004","Cardiac Abnormalities");
					code42563_7_CDSideEffect.put("35489007","Depression");
					code42563_7_CDSideEffect.put("15188001","Hearing Loss");
					code42563_7_CDSideEffect.put("197354009","Liver Toxicity");
					code42563_7_CDSideEffect.put("68962001","Myalgia");
					code42563_7_CDSideEffect.put("OTH","Other (specify)");
					code42563_7_CDSideEffect.put("302226006","Peripheral Neuropathy");
					code42563_7_CDSideEffect.put("236423003","Renal Dysfunction");
					code42563_7_CDSideEffect.put("82313006","Suicide Attempt or Ideation");
					code42563_7_CDSideEffect.put("60862001","Tinnitus");
					code42563_7_CDSideEffect.put("445053006","Vestibular Dysfunction");
					code42563_7_CDSideEffect.put("PHC1920","Vision Change/Loss");


				}
				if(code42563_7sideEffects.size()==0){
					//reading from the map to make sure the names are consistent
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("15188001"), "1");//Hearing loss
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("197354009"), "2");//Liver Toxicity
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("236423003"), "3");//Renal Dysfunction
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("302226006"), "4");//Peripheral Neuropathy
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("35489007"), "5");//Depression		
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("36358004"), "6");//Cardiac Abnormalities		
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("445053006"),"7");	//Vestibular Dysfunction	
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("60862001"), "8");	//Tinnitus	
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("68962001"), "9");		//Myalgia
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("82313006"), "10");//Suicide Attempt or Ideation		
					code42563_7sideEffects.put(code42563_7_CDSideEffect.get("PHC1920"), "11");//Vision Change/Loss		
				//	code42563_7sideEffects.put(code42563_7_CDSideEffect.get("OTH"), "12");//Other (Specify)	

					
				}
				
	
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processSideEffects", formSpecificQuestionAnswerMap);
		
				int totalNumberValues = numberOfRows*3;
				int actualTotalNumberValues = 	formSpecificQuestionAnswerMap.size();
				
				System.out.println("Number of expected values in the sorted map: "+totalNumberValues+", actual total number: "+actualTotalNumberValues);
				Assert.assertEquals(totalNumberValues, actualTotalNumberValues);
				String drugInPosition = formSpecificQuestionAnswerMap.get("42563_7_CD_R"+positionToTest+"_CDT");
			
				System.out.println("Drug: "+drugToTest+" expected to be found in position "+positionToTest+". Drug found in position "+positionToTest +" is: "+drugInPosition);
				Assert.assertEquals(drugToTest, drugInPosition);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processSideEffects_test *******************");
			
		}	
	
}
	

















/**
 * ProcessEverWorkedAs_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessEverWorkedAs_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessEverWorkedAs_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("INV1276_CD","C0682244, 223366009, PHC2121, 260413007, UNK");


		
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processEverWorkedAs_test"+"_"+it++,"INV1276_H_CD","223366009", answerMap},
    		  {"processEverWorkedAs_test"+"_"+it++,"INV1276_M_CD","PHC2121", answerMap},
    		  {"processEverWorkedAs_test"+"_"+it++,"INV1276_U_CD","UNK", answerMap},
    		  {"processEverWorkedAs_test"+"_"+it++,"INV1276_N_CD","260413007", answerMap},
    		  {"processEverWorkedAs_test"+"_"+it++,"INV1276_X_CD",null, answerMap},
    		  
    		  
			  

	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> INV1276EverWorkedAs= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String INV1276_CD = "INV1276_CD";
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1276_CD", INV1276_CD);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1276EverWorkedAs", INV1276EverWorkedAs);
		 
	 }
	 
	

	@Test
	public void processEverWorkedAs_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processEverWorkedAs_test *******************");

				if(INV1276EverWorkedAs.size()==0){
					INV1276EverWorkedAs.put("1002-5", "C");
					INV1276EverWorkedAs.put("223366009", "H");
					INV1276EverWorkedAs.put("PHC2121", "M");
					INV1276EverWorkedAs.put("UNK", "U");
					INV1276EverWorkedAs.put("260413007", "N");
				}
				
				
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("INV1276_H_CD","INV1276_CD__everworkedas_INV1276_H_CD");
				formSpecificQuestionMap.put("INV1276_C_CD","INV1276_CD__everworkedas_INV1276_C_CD");
				formSpecificQuestionMap.put("INV1276_M_CD","INV1276_CD__everworkedas_INV1276_M_CD");
				formSpecificQuestionMap.put("INV1276_U_CD","INV1276_CD__everworkedas_INV1276_U_CD");
				formSpecificQuestionMap.put("INV1276_N_CD","INV1276_CD__everworkedas_INV1276_N_CD");
				
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processEverWorkedAs", formSpecificQuestionMap,formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processEverWorkedAs_test *******************");
			
		}	
	
}
	













/**
 * ProcessWhatCriteriaMet_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessWhatCriteriaMet_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessWhatCriteriaMet_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("INV515_CD","PHC1140, PHC1139, PHC1138, PHC1137, PHC1215, PHC1141");

		
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processWhatCriteriaMet_test"+"_"+it++,"INV515_S_CD","PHC1140", answerMap},
    		  {"processWhatCriteriaMet_test"+"_"+it++,"INV515_C_CD","PHC1139", answerMap},
    		  {"processWhatCriteriaMet_test"+"_"+it++,"INV515_R_CD","PHC1138", answerMap},
    		  {"processWhatCriteriaMet_test"+"_"+it++,"INV515_W_CD","PHC1137", answerMap},
    		  {"processWhatCriteriaMet_test"+"_"+it++,"INV515_B_CD","PHC1215", answerMap},
    		  {"processWhatCriteriaMet_test"+"_"+it++,"INV515_O_CD","PHC1141", answerMap},
    		  {"processWhatCriteriaMet_test"+"_"+it++,"INV515_X_CD",null, answerMap},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> INV515Criteria= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String INV515_CD = "INV515_CD";
		 String INV515 = "INV515";
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV515_CD", INV515_CD);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV515", INV515);
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV515Criteria", INV515Criteria);
		 
	 }
	 
	

	@Test
	public void processWhatCriteriaMet_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processWhatCriteriaMet_test *******************");

				
				
				

				
				if(INV515Criteria.size()==0){
					
					INV515Criteria.put("PHC1140","S");
					INV515Criteria.put("PHC1139","C");
					INV515Criteria.put("PHC1138","R");
					INV515Criteria.put("PHC1137","W");
					INV515Criteria.put("PHC1215","B");
					INV515Criteria.put("PHC1141","O");
					
					
				}
				
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("INV515_S_CD","INV515_CD__whatCriteriaMet_INV515_S_CD");
				formSpecificQuestionMap.put("INV515_C_CD","INV515_CD__whatCriteriaMet_INV515_C_CD");
				formSpecificQuestionMap.put("INV515_R_CD","INV515_CD__whatCriteriaMet_INV515_R_CD");
				formSpecificQuestionMap.put("INV515_W_CD","INV515_CD__whatCriteriaMet_INV515_W_CD");
				formSpecificQuestionMap.put("INV515_B_CD","INV515_CD__whatCriteriaMet_INV515_B_CD");
				formSpecificQuestionMap.put("INV515_O_CD","INV515_CD__whatCriteriaMet_INV515_O_CD");
				
				
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processWhatCriteriaMet", formSpecificQuestionMap,formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processWhatCriteriaMet_test *******************");
			
		}	
	
}
	









/**
 * ProcessPrimarySites_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessPrimarySites_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessPrimarySites_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("INV1133_CD","39607008, 3120008, PHC5, 69831007, 281778006, 281777001, PHC2, PHC3, 110547006, 110522009, 21514008, 1231004, 83670000, OTH");

		
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_PU_CD","39607008", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_PL_CD","3120008", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_SI_CD","PHC5", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_LC_CD","69831007", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_LI_CD","281778006", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_LA_CD","281777001", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_LO_CD","PHC2", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_LU_CD","PHC3", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_L_CD","110547006", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_B_CD","110522009", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_G_CD","21514008", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_M_CD","1231004", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_P_CD","83670000", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_O_CD","OTH", answerMap},
    		  {"processPrimarySites_test"+"_"+it++,"INV1133_XX_CD",null, answerMap},
    		  
    		  
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> INV1133PrimarySites= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String INV1133_CD = "INV1133_CD";
		 String INV1133 = "INV1133";
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1133_CD", INV1133_CD);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1133", INV1133);
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1133PrimarySites", INV1133PrimarySites);
		 
	 }
	 
	

	@Test
	public void processPrimarySites_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processPrimarySites_test *******************");

				
				
				

				if(INV1133PrimarySites.size()==0){
					INV1133PrimarySites.put("39607008","PU");
					INV1133PrimarySites.put("3120008","PL");
					INV1133PrimarySites.put("PHC5","SI");
					INV1133PrimarySites.put("69831007","LC");
					INV1133PrimarySites.put("281778006","LI");
					INV1133PrimarySites.put("281777001","LA");
					INV1133PrimarySites.put("PHC2","LO");
					INV1133PrimarySites.put("PHC3","LU");
					INV1133PrimarySites.put("110547006","L");
					INV1133PrimarySites.put("110522009","B");
					INV1133PrimarySites.put("21514008","G");
					INV1133PrimarySites.put("1231004","M");
					INV1133PrimarySites.put("83670000","P");
					INV1133PrimarySites.put("OTH","O");			
					
				}
				
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("INV1133_PU_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_PL_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_SI_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_LC_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_LI_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_LA_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_LO_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_LU_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_L_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_B_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_G_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_M_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_P_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1133_O_CD","INV1133_CD__primarySites_INV1133_PU_CD");
				
				
				
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processPrimarySites", formSpecificQuestionMap,formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processPrimarySites_test *******************");
			
		}	
	
}
	







/**
 * ProcessMovedWhere_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessMovedWhere_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessMovedWhere_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("INV1152_CD","PHC246, PHC1911");

		
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processMovedWhere_test"+"_"+it++,"INV1152_OS_CD","PHC246", answerMap},
    		  {"processMovedWhere_test"+"_"+it++,"INV1152_OUS_CD","PHC1911", answerMap},
    		  
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> INV1152MovedToWhere= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String INV1152 = "INV1152";
		 String INV1152_CD = "INV1152_CD";
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1152", INV1152);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1152_CD", INV1152_CD);
		 
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1152MovedToWhere", INV1152MovedToWhere);
		 
	 }
	 
	

	@Test
	public void processMovedWhere_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processMovedWhere_test *******************");

				
				
				if(INV1152MovedToWhere.size()==0){
					INV1152MovedToWhere.put("PHC246","OS");
					INV1152MovedToWhere.put("PHC1911","OUS");
				}
				
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("INV1152_OS_CD","INV1133_CD__movedToWhere_INV1133_PU_CD");
				formSpecificQuestionMap.put("INV1152_OUS_CD","INV1133_CD__movedToWhere_INV1133_PU_CD");

				
				
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processMovedWhere", formSpecificQuestionMap,formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processMovedWhere_test *******************");
			
		}	
	
}





/**
 * ProcessReasonTbTherapyExtended_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessReasonTbTherapyExtended_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessReasonTbTherapyExtended_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("INV1141_CD","62014003, PHC701, 76797004, PHC700, 258143003, OTH, UNK");

	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processReasonTbTherapyExtended_test"+"_"+it++,"INV1141_A_CD","62014003", answerMap},
    		  {"processReasonTbTherapyExtended_test"+"_"+it++,"INV1141_C_CD","PHC701", answerMap},
    		  {"processReasonTbTherapyExtended_test"+"_"+it++,"INV1141_F_CD","76797004", answerMap},
    		  {"processReasonTbTherapyExtended_test"+"_"+it++,"INV1141_I_CD","PHC700", answerMap},
    		  {"processReasonTbTherapyExtended_test"+"_"+it++,"INV1141_N_CD","258143003", answerMap},
    		  {"processReasonTbTherapyExtended_test"+"_"+it++,"INV1141_O_CD","OTH", answerMap},
    		  {"processReasonTbTherapyExtended_test"+"_"+it++,"INV1141_U_CD","UNK", answerMap},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> INV1141ReasonTherapyExtended= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String INV1141_CD = "INV1141_CD";
		 String INV1141 = "INV1141";
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1141_CD", INV1141_CD);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1141", INV1141);
		 
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1141ReasonTherapyExtended", INV1141ReasonTherapyExtended);
		 
	 }
	 
	

	@Test
	public void processReasonTbTherapyExtended_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processReasonTbTherapyExtended_test *******************");

				
				
				if(INV1141ReasonTherapyExtended.size()==0){
					INV1141ReasonTherapyExtended.put("62014003","A");
					INV1141ReasonTherapyExtended.put("PHC701","C");
					INV1141ReasonTherapyExtended.put("76797004","F");
					INV1141ReasonTherapyExtended.put("PHC700","I");
					INV1141ReasonTherapyExtended.put("258143003","N");
					INV1141ReasonTherapyExtended.put("OTH","O");
					INV1141ReasonTherapyExtended.put("UNK","U");
				}
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("INV1141_A_CD","INV1141_CD__ReasonTherapyExtended_INV1141_A_CD");
				formSpecificQuestionMap.put("INV1141_C_CD","INV1141_CD__ReasonTherapyExtended_INV1141_C_CD");
				formSpecificQuestionMap.put("INV1141_F_CD","INV1141_CD__ReasonTherapyExtended_INV1141_F_CD");
				formSpecificQuestionMap.put("INV1141_I_CD","INV1141_CD__ReasonTherapyExtended_INV1141_I_CD");
				formSpecificQuestionMap.put("INV1141_N_CD","INV1141_CD__ReasonTherapyExtended_INV1141_N_CD");
				formSpecificQuestionMap.put("INV1141_O_CD","INV1141_CD__ReasonTherapyExtended_INV1141_O_CD");
				formSpecificQuestionMap.put("INV1141_U_CD","INV1141_CD__ReasonTherapyExtended_INV1141_U_CD");
				

				
				
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processReasonTbTherapyExtended", formSpecificQuestionMap,formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processReasonTbTherapyExtended_test *******************");
			
		}	
	
}







/**
 * ProcessTreatmentAdministration_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessTreatmentAdministration_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessTreatmentAdministration_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("code55753_8_CD","435891000124101, PHC1881, 225425006");

	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processTreatmentAdministration_test"+"_"+it++,"code55753_8_D_CD","435891000124101", answerMap},
    		  {"processTreatmentAdministration_test"+"_"+it++,"code55753_8_E_CD","PHC1881", answerMap},
    		  {"processTreatmentAdministration_test"+"_"+it++,"code55753_8_S_CD","225425006", answerMap},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> code55753TreatmentAdministration= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String code55753_8_CD = "code55753_8_CD";
		 String code55753_8 = "code55753_8";
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code55753_8_CD", code55753_8_CD);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code55753_8", code55753_8);
		 
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code55753TreatmentAdministration", code55753TreatmentAdministration);
		 
	 }
	 
	

	@Test
	public void processTreatmentAdministration_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processTreatmentAdministration_test *******************");

				
				

				if(code55753TreatmentAdministration.size()==0){
				
				code55753TreatmentAdministration.put("435891000124101","D");
				code55753TreatmentAdministration.put("PHC1881","E");
				code55753TreatmentAdministration.put("225425006","S");			
				}
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("code55753_8_D_CD","code55753_8_CD__TreatmentAdministration_code55753_8_D_CD");
				formSpecificQuestionMap.put("code55753_8_E_CD","code55753_8_CD__TreatmentAdministration_code55753_8_E_CD");
				formSpecificQuestionMap.put("code55753_8_S_CD","code55753_8_CD__TreatmentAdministration_code55753_8_S_CD");
				

				
				
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processTreatmentAdministration", formSpecificQuestionMap,formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processTreatmentAdministration_test *******************");
			
		}	
	
}








/**
 * ProcessTreatmentAdministration_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessTreatmentAdministrationLTBI_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessTreatmentAdministrationLTBI_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("code55753_8B_CD","435891000124101, PHC1881, 225425006");

	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processTreatmentAdministration_test"+"_"+it++,"code55753_8B_D_CD","435891000124101", answerMap},
    		  {"processTreatmentAdministration_test"+"_"+it++,"code55753_8B_E_CD","PHC1881", answerMap},
    		  {"processTreatmentAdministration_test"+"_"+it++,"code55753_8B_S_CD","225425006", answerMap},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> code55753TreatmentAdministration= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String code55753_8B_CD = "code55753_8B_CD";
		 String code55753_8B = "code55753_8B";
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code55753_8B_CD", code55753_8B_CD);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code55753_8B", code55753_8B);
		 
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code55753TreatmentAdministration", code55753TreatmentAdministration);
		 
	 }
	 
	

	@Test
	public void processTreatmentAdministrationLTBI_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processTreatmentAdministrationLTBI_test *******************");

				
				

				if(code55753TreatmentAdministration.size()==0){
				
				code55753TreatmentAdministration.put("435891000124101","D");
				code55753TreatmentAdministration.put("PHC1881","E");
				code55753TreatmentAdministration.put("225425006","S");			
				}
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("code55753_8B_D_CD","code55753_8_CD__TreatmentAdministration_code55753_8B_D_CD");
				formSpecificQuestionMap.put("code55753_8B_E_CD","code55753_8_CD__TreatmentAdministration_code55753_8B_E_CD");
				formSpecificQuestionMap.put("code55753_8B_S_CD","code55753_8_CD__TreatmentAdministration_code55753_8B_S_CD");
				

				
				
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processTreatmentAdministrationLTBI", formSpecificQuestionMap,formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processTreatmentAdministrationLTBI_test *******************");
			
		}	
	
}








/**
 * ProcessSevereAdverseEvent_test: this method will make sure than for the specific question, the answer is correct. This method handles multi answers to be displayed in individual fields, so each of the questions is split in subquestions by a code, like _U_.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class ProcessSevereAdverseEvent_test{
	

	private String iteration;
	//Answer to validate
	private String code;
	private String answer;
	private Map<String,String> formSpecificQuestionAnswerMap = new HashMap<String, String>();	
	
 public ProcessSevereAdverseEvent_test(String it, String code, String answer,  Map<String,String> formSpecificQuestionAnswerMap){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.code = code;
	 this.answer=answer;
	 this.formSpecificQuestionAnswerMap = formSpecificQuestionAnswerMap;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	    Map<String, String> answerMap = new HashMap<String, String>();
	
			//Answers selected from the UI
			answerMap.put("code64750_3_CD","399166001, 434081000124108");
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"processSevereAdverseEvent_test"+"_"+it++,"code64750_3_D_CD","399166001", answerMap},
    		  {"processSevereAdverseEvent_test"+"_"+it++,"code64750_3_H_CD","434081000124108", answerMap},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);

	

	Map<String, String> code64750_3SevereAdverse= new HashMap<String, String>();
	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		 String code64750_3_CD = "code64750_3_CD";
		 String code64750_3 = "code64750_3";
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code64750_3_CD", code64750_3_CD);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code64750_3", code64750_3);
		 
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code64750_3SevereAdverse", code64750_3SevereAdverse);
		 
	 }
	 
	

	@Test
	public void processSevereAdverseEvent_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: processSevereAdverseEvent_test *******************");

				
				
				if(code64750_3SevereAdverse.size()==0){
					
					code64750_3SevereAdverse.put("399166001","D");
					code64750_3SevereAdverse.put("434081000124108","H");
					
					
				}
				
				Map<String,String> formSpecificQuestionMap = new HashMap<String, String>();
				
				//Questions on the form
				formSpecificQuestionMap.put("code64750_3_D_CD","code64750_CD_3__SevereAdverse_code64750_CD_D_3");
				formSpecificQuestionMap.put("code64750_3_H_CD","code64750_CD_3__SevereAdverse_code64750_CD_H_3");
				

		
				
				
		
				
				Whitebox.invokeMethod(commonPDFPrintForm, "processSevereAdverseEvent", formSpecificQuestionMap, formSpecificQuestionAnswerMap);
				
				String actualAnswer = formSpecificQuestionAnswerMap.get(code);
			
				
				System.out.println("Iteration: "+iteration+" Question: "+code+"\nExpected answer: "+answer+"\nActual answer: "+answer);
		
				Assert.assertEquals(answer, actualAnswer);
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: processSevereAdverseEvent_test *******************");
			
		}	
	
}






/**
 * InitializeDemographics_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeDemographics_test{
	

	private String iteration;

	
 public InitializeDemographics_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeDemographics_test"+"_"+it++},
    		  
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> PregnancyMap= new HashMap<String, String>();
	private static Map<String, String> BirthSexMap= new HashMap<String, String>();
	private static Map<String, String> INV178YnuMap= new HashMap<String, String>();
	private static Map<String, String> drugHistory= new HashMap<String, String>();
	private static Map<String, String> CurrentSexMap= new HashMap<String, String>();
	private static Map<String, String> PreferredSexMap= new HashMap<String, String>();
	private static Map<String, String> SexUnknownReasonMap= new HashMap<String, String>();
	private static Map<String, String> DEM140PMaritalMap= new HashMap<String, String>();
	private static Map<String, String> DEM152RaceCodePRaceCatMap= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapAmericanIndian= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapAsian= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapAfricanAmerican= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapNativeHawaiian= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapWhite= new HashMap<String, String>();

	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "PregnancyMap", PregnancyMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "BirthSexMap", BirthSexMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV178YnuMap", INV178YnuMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "drugHistory", drugHistory);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "CurrentSexMap", CurrentSexMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "PreferredSexMap", PreferredSexMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "SexUnknownReasonMap", SexUnknownReasonMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DEM140PMaritalMap", DEM140PMaritalMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DEM152RaceCodePRaceCatMap", DEM152RaceCodePRaceCatMap);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DetailedRaceCodePRaceCatMapAmericanIndian", DetailedRaceCodePRaceCatMapAmericanIndian);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DetailedRaceCodePRaceCatMapAsian", DetailedRaceCodePRaceCatMapAsian);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DetailedRaceCodePRaceCatMapAfricanAmerican", DetailedRaceCodePRaceCatMapAfricanAmerican);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DetailedRaceCodePRaceCatMapNativeHawaiian", DetailedRaceCodePRaceCatMapNativeHawaiian);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DetailedRaceCodePRaceCatMapWhite", DetailedRaceCodePRaceCatMapWhite);

	 }
	 
	

	@Test
	public void initializeDemographics_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeDemographics_test *******************");

				Whitebox.invokeMethod(commonPDFPrintForm, "initializeDemographics");
				
				//Pregnancy
				int pregnancySizeActual =  PregnancyMap.size();
				int pregnancySizeExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nPregnancy Size:\nActual Size: "+pregnancySizeActual+"\nExpected size: "+pregnancySizeExpected);
		
				Assert.assertEquals(pregnancySizeExpected, pregnancySizeActual);
				
				//BirthSex
				int birthSexSizeActual =  BirthSexMap.size();
				int birthSexSizeExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nBirth Sex:\nActual Size: "+birthSexSizeActual+"\nExpected size: "+birthSexSizeExpected);
		
				Assert.assertEquals(birthSexSizeExpected, birthSexSizeActual);
				
				
				//INV178Ynu
				int INV178YnuSizeActual =  INV178YnuMap.size();
				int INV178YnuSizeExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nINV178Ynu:\nActual Size: "+INV178YnuSizeActual+"\nExpected size: "+INV178YnuSizeExpected);
		
				Assert.assertEquals(INV178YnuSizeExpected, INV178YnuSizeActual);
				
				
				//drugHistory
				int drugHistorySizeActual =  drugHistory.size();
				int drugHistorySizeExpected = 5;
	
				System.out.println("Iteration: "+iteration+"\nDrug History:\nActual Size: "+drugHistorySizeActual+"\nExpected size: "+drugHistorySizeExpected);
		
				Assert.assertEquals(drugHistorySizeExpected, drugHistorySizeActual);
				
				
				//CurrentSex
				int currentSexSizeActual =  CurrentSexMap.size();
				int currentSexSizeExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nCurrent Sex:\nActual Size: "+currentSexSizeActual+"\nExpected size: "+currentSexSizeExpected);
		
				Assert.assertEquals(currentSexSizeExpected, currentSexSizeActual);
				
				
				//Preferred Sex
				int preferredSexSizeActual =  PreferredSexMap.size();
				int preferredSexSizeExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nPreferred Sex:\nActual Size: "+preferredSexSizeActual+"\nExpected size: "+preferredSexSizeExpected);
		
				Assert.assertEquals(preferredSexSizeExpected, preferredSexSizeActual);
				
			

				//SexUnknownReason
				int sexUnknownReasonSizeActual =  SexUnknownReasonMap.size();
				int sexUnknownReasonSizeExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nSex Unknown Reason:\nActual Size: "+sexUnknownReasonSizeActual+"\nExpected size: "+sexUnknownReasonSizeExpected);
		
				Assert.assertEquals(sexUnknownReasonSizeExpected, sexUnknownReasonSizeActual);
				
			
				
				
				//DEM140 Marital
				int DEM140MaritalSizeActual =  DEM140PMaritalMap.size();
				int DEM140MaritalSizeExpected = 8;
	
				System.out.println("- Iteration: "+iteration+"\nDEM140 Marital:\nActual Size: "+DEM140MaritalSizeActual+"\nExpected size: "+DEM140MaritalSizeExpected);
		
				Assert.assertEquals(DEM140MaritalSizeExpected, DEM140MaritalSizeActual);
				
			
				//DEM152RaceCodeCodePRaceCat
				int DEM152RaceCodeSizeActual =  DEM152RaceCodePRaceCatMap.size();
				int DEM152RaceCodeSizeExpected = 9;
	
				System.out.println("- Iteration: "+iteration+"\nDEM140 Marital:\nActual Size: "+DEM152RaceCodeSizeActual+"\nExpected size: "+DEM152RaceCodeSizeExpected);
		
				Assert.assertEquals(DEM152RaceCodeSizeExpected, DEM152RaceCodeSizeActual);
			
				
				
				
				//DetailedRaceCodePRaceCatMapAmericanIndian
				
				int detailedRaceCodePRaceCatMapAmericanIndianSizeActual =  DetailedRaceCodePRaceCatMapAmericanIndian.size();
				int detailedRaceCodePRaceCatMapAmericanIndianSizeExpected = 2;
	
				System.out.println("- Iteration: "+iteration+"\nDEM140 Marital:\nActual Size: "+detailedRaceCodePRaceCatMapAmericanIndianSizeActual+"\nExpected size: "+detailedRaceCodePRaceCatMapAmericanIndianSizeExpected);
		
				Assert.assertEquals(detailedRaceCodePRaceCatMapAmericanIndianSizeExpected, detailedRaceCodePRaceCatMapAmericanIndianSizeActual);
			
				
				//DetailedRaceCodePRaceCatMapAsian
				int detailedRaceCodePRaceCatMapAsianSizeActual =  DetailedRaceCodePRaceCatMapAsian.size();
				int detailedRaceCodePRaceCatMapAsianSizeExpected = 24;
	
				System.out.println("- Iteration: "+iteration+"\nDEM140 Marital:\nActual Size: "+detailedRaceCodePRaceCatMapAsianSizeActual+"\nExpected size: "+detailedRaceCodePRaceCatMapAsianSizeExpected);
		
				Assert.assertEquals(detailedRaceCodePRaceCatMapAsianSizeExpected, detailedRaceCodePRaceCatMapAsianSizeActual);
			
				//DetailedRaceCodePRaceCatMapAfricanAmerican
				int detailedRaceCodePRaceCatMapAfricanAmericanSizeActual =  DetailedRaceCodePRaceCatMapAfricanAmerican.size();
				int detailedRaceCodePRaceCatMapAfricanAmericanSizeExpected = 12;
	
				System.out.println("- Iteration: "+iteration+"\nDEM140 Marital:\nActual Size: "+detailedRaceCodePRaceCatMapAfricanAmericanSizeActual+"\nExpected size: "+detailedRaceCodePRaceCatMapAfricanAmericanSizeExpected);
		
				Assert.assertEquals(detailedRaceCodePRaceCatMapAfricanAmericanSizeExpected, detailedRaceCodePRaceCatMapAfricanAmericanSizeActual);
			
				//DetailedRaceCodePRaceCatMapNativeHawaiian
				int detailedRaceCodePRaceCatMapNativeHawaiianSizeActual =  DetailedRaceCodePRaceCatMapNativeHawaiian.size();
				int detailedRaceCodePRaceCatMapNativeHawaiianSizeExpected = 4;
	
				System.out.println("- Iteration: "+iteration+"\nDEM140 Marital:\nActual Size: "+detailedRaceCodePRaceCatMapNativeHawaiianSizeActual+"\nExpected size: "+detailedRaceCodePRaceCatMapNativeHawaiianSizeExpected);
		
				Assert.assertEquals(detailedRaceCodePRaceCatMapNativeHawaiianSizeExpected, detailedRaceCodePRaceCatMapNativeHawaiianSizeActual);
			
				//DetailedRaceCodePRaceCatMapWhite
				int detailedRaceCodePRaceCatMapWhiteSizeActual =  DetailedRaceCodePRaceCatMapWhite.size();
				int detailedRaceCodePRaceCatMapWhiteExpected = 3;
	
				System.out.println("- Iteration: "+iteration+"\nDEM140 Marital:\nActual Size: "+detailedRaceCodePRaceCatMapWhiteSizeActual+"\nExpected size: "+detailedRaceCodePRaceCatMapWhiteExpected);
		
				Assert.assertEquals(detailedRaceCodePRaceCatMapWhiteExpected, detailedRaceCodePRaceCatMapWhiteSizeActual);
			
				
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeDemographics_test *******************");
			
		}	
	
}










/**
 * InitializeCaseStatus_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCaseStatus_test{
	

	private String iteration;

	
 public InitializeCaseStatus_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeCaseStatus_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV163caseStatusMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV163caseStatusMap", INV163caseStatusMap);

	 }
	 
	

	@Test
	public void initializeCaseStatus_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCaseStatus_test *******************");

				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCaseStatus");
				
				//Case Status
				int caseStatusSizeActual =  INV163caseStatusMap.size();
				int caseStatusExpected = 5;
	
				System.out.println("Iteration: "+iteration+"\nINV163caseStatus Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCaseStatus_test *******************");
			
		}	
	
}






/**
 * InitializeCaseVerification_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCaseVerification_test{
	

	private String iteration;

	
 public InitializeCaseVerification_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeCaseVerification_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1115CaseVerificationMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1115CaseVerificationMap", INV1115CaseVerificationMap);

	 }
	 
	

	@Test
	public void initializeCaseVerification_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCaseVerification_test *******************");

				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCaseVerification");
				
				//Case Status
				int caseStatusSizeActual =  INV1115CaseVerificationMap.size();
				int caseStatusExpected = 7;
	
				System.out.println("Iteration: "+iteration+"\nINV1115CaseVerification Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCaseVerification_test *******************");
			
		}	
	
}









/**
 * InitializeCaseCounted_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCaseCounted_test{
	

	private String iteration;

	
 public InitializeCaseCounted_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeCaseCounted_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1109CaseCountedMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1109CaseCountedMap", INV1109CaseCountedMap);

	 }
	 
	

	@Test
	public void initializeCaseCounted_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCaseCounted_test *******************");

				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCaseCounted");
				
				//Case Status
				int caseStatusSizeActual =  INV1109CaseCountedMap.size();
				int caseStatusExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nINV1109CaseCounted Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCaseCounted_test *******************");
			
		}	
	
}







/**
 * InitializeCountryOfVerifiedCase_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCountryOfVerifiedCase_test{
	

	private String iteration;

	
 public InitializeCountryOfVerifiedCase_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeCountryOfVerifiedCase_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1111CountryOfVerifiedCase= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1111CountryOfVerifiedCase", INV1111CountryOfVerifiedCase);

	 }
	 
	

	@Test
	public void initializeCountryOfVerifiedCase_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCountryOfVerifiedCase_test *******************");

				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCountryOfVerifiedCase");
				
				//Case Status
				int caseStatusSizeActual =  INV1111CountryOfVerifiedCase.size();
				int caseStatusExpected = 258;
	
				System.out.println("Iteration: "+iteration+"\nINV1111CountryOfVerifiedCase Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCountryOfVerifiedCase_test *******************");
			
		}	
	
}











/**
 * InitializeCountryOfUsualresidency_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCountryOfUsualresidency_test{
	

	private String iteration;

	
 public InitializeCountryOfUsualresidency_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeCountryOfUsualresidency_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV501CountryOfUsualResidency= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV501CountryOfUsualResidency", INV501CountryOfUsualResidency);

	 }
	 
	

	@Test
	public void initializeCountryOfUsualresidency_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCountryOfUsualresidency_test *******************");

				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCountryOfUsualresidency");
				
				//Case Status
				int caseStatusSizeActual =  INV501CountryOfUsualResidency.size();
				int caseStatusExpected = 276;
	
				System.out.println("Iteration: "+iteration+"\nINV501CountryOfUsualResidency Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCountryOfUsualresidency_test *******************");
			
		}	
	
}







/**
 * InitializeEverWorkedAs_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeEverWorkedAs_test{
	

	private String iteration;

	
 public InitializeEverWorkedAs_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeEverWorkedAs_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1276EverWorkedAs= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCRVCTForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1276EverWorkedAs", INV1276EverWorkedAs);

	 }
	 
	

	@Test
	public void initializeEverWorkedAs_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeEverWorkedAs_test *******************");

				Whitebox.invokeMethod(commonPDFPrintForm, "initializeEverWorkedAs");
				
				//Case Status
				int caseStatusSizeActual =  INV1276EverWorkedAs.size();
				int caseStatusExpected = 5;
	
				System.out.println("Iteration: "+iteration+"\nINV1276EverWorkedAs Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeEverWorkedAs_test *******************");
			
		}	
	
}






/**
 * InitializeOccupation_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeOccupation_test{
	

	private String iteration;

	
 public InitializeOccupation_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeOccupation_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code85659_1Occupation= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code85659_1Occupation", code85659_1Occupation);

	 }
	 
	

	@Test
	public void initializeOccupation_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeOccupation_test *******************");

	
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeOccupation2");
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeOccupation");
				
				//Case Status
				int caseStatusSizeActual =  code85659_1Occupation.size();
				int caseStatusExpected = 381;
	
				System.out.println("Iteration: "+iteration+"\ncode85659_1Occupation:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeOccupation_test *******************");
			
		}	
	
}




/**
 * InitializeOccupation2_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeOccupation2_test{
	

	private String iteration;

	
 public InitializeOccupation2_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeOccupation2_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code85659_1Occupation= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code85659_1Occupation", code85659_1Occupation);

	 }
	 
	

	@Test
	public void initializeOccupation2_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeOccupation2_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeOccupation2");
				
				//Case Status
				int caseStatusSizeActual =  code85659_1Occupation.size();
				int caseStatusExpected = 163;
	
				System.out.println("Iteration: "+iteration+"\ncode85659_1Occupation Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeOccupation2_test *******************");
			
		}	
	
}








/**
 * InitializeIndustry_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeIndustry_test{
	

	private String iteration;

	
 public InitializeIndustry_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeIndustry_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code85657_5Industry= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code85657_5Industry", code85657_5Industry);

	 }
	 
	

	@Test
	public void initializeIndustry_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeIndustry_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeIndustry");
				
				//Case Status
				int caseStatusSizeActual =  code85657_5Industry.size();
				int caseStatusExpected = 272;
	
				System.out.println("Iteration: "+iteration+"\nPregnancy Size:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeIndustry_test *******************");
			
		}	
	
}







/**
 * InitializeDEM152MarginalRace_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeDEM152MarginalRace_test{
	

	private String iteration;

	
 public InitializeDEM152MarginalRace_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeDEM152MarginalRace_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> DEM152MarginalRaceMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DEM152MarginalRaceMap", DEM152MarginalRaceMap);

	 }
	 
	

	@Test
	public void initializeDEM152MarginalRace_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeDEM152MarginalRace_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeDEM152MarginalRace");
				
				//Case Status
				int caseStatusSizeActual =  DEM152MarginalRaceMap.size();
				int caseStatusExpected = 9;
	
				System.out.println("Iteration: "+iteration+"\nDEM152MarginalRaceMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeDEM152MarginalRace_test *******************");
			
		}	
	
}







/**
 * InitializeDEM155PhvsEthnicitygroupCdcUnkMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeDEM155PhvsEthnicitygroupCdcUnkMap_test{
	

	private String iteration;

	
 public InitializeDEM155PhvsEthnicitygroupCdcUnkMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeDEM155PhvsEthnicitygroupCdcUnkMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> DEM155PhvsEthnicitygroupCdcUnkMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "DEM155PhvsEthnicitygroupCdcUnkMap", DEM155PhvsEthnicitygroupCdcUnkMap);

	 }
	 
	

	@Test
	public void initializeDEM155PhvsEthnicitygroupCdcUnkMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeDEM155PhvsEthnicitygroupCdcUnkMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeDEM155PhvsEthnicitygroupCdcUnkMap");
				
				//Case Status
				int caseStatusSizeActual =  DEM155PhvsEthnicitygroupCdcUnkMap.size();
				int caseStatusExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nDEM152MarginalRaceMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeDEM155PhvsEthnicitygroupCdcUnkMap_test *******************");
			
		}	
	
}






/**
 * InitializeIXS105NbsInterviewTypeMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeIXS105NbsInterviewTypeMap_test{
	

	private String iteration;

	
 public InitializeIXS105NbsInterviewTypeMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeIXS105NbsInterviewTypeMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> IXS105NbsInterviewTypeMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "IXS105NbsInterviewTypeMap", IXS105NbsInterviewTypeMap);

	 }
	 
	

	@Test
	public void initializeIXS105NbsInterviewTypeMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeIXS105NbsInterviewTypeMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeIXS105NbsInterviewTypeMap");
				
				//Case Status
				int caseStatusSizeActual =  IXS105NbsInterviewTypeMap.size();
				int caseStatusExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nDEM152MarginalRaceMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeIXS105NbsInterviewTypeMap_test *******************");
			
		}	
	
}










/**
 * InitializeNBS151SurveillancePatientFollowupMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS151SurveillancePatientFollowupMap_test{
	

	private String iteration;

	
 public InitializeNBS151SurveillancePatientFollowupMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeNBS151SurveillancePatientFollowupMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS151SurveillancePatientFollowupMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS151SurveillancePatientFollowupMap", NBS151SurveillancePatientFollowupMap);

	 }
	 
	

	@Test
	public void initializeNBS151SurveillancePatientFollowupMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS151SurveillancePatientFollowupMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS151SurveillancePatientFollowupMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS151SurveillancePatientFollowupMap.size();
				int caseStatusExpected = 7;
	
				System.out.println("Iteration: "+iteration+"\nDEM152MarginalRaceMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS151SurveillancePatientFollowupMap_test *******************");
			
		}	
	
}







/**
 * InitializeNBS273PEthnUnkReasonMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS273PEthnUnkReasonMap_test{
	

	private String iteration;

	
 public InitializeNBS273PEthnUnkReasonMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS273PEthnUnkReasonMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS273PEthnUnkReasonMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS273PEthnUnkReasonMap", NBS273PEthnUnkReasonMap);

	 }
	 
	

	@Test
	public void initializeNBS151SurveillancePatientFollowupMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS273PEthnUnkReasonMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS273PEthnUnkReasonMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS273PEthnUnkReasonMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nDEM152MarginalRaceMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS273PEthnUnkReasonMap_test *******************");
			
		}	
	
}







/**
 * initializeSTD121PhvsClinicianObservedLesionsStdMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeSTD121PhvsClinicianObservedLesionsStdMap_test{
	

	private String iteration;

	
 public InitializeSTD121PhvsClinicianObservedLesionsStdMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeSTD121PhvsClinicianObservedLesionsStdMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> STD121PhvsClinicianObservedLesionsStdMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "STD121PhvsClinicianObservedLesionsStdMap", STD121PhvsClinicianObservedLesionsStdMap);

	 }
	 
	

	@Test
	public void initializeSTD121PhvsClinicianObservedLesionsStdMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeSTD121PhvsClinicianObservedLesionsStdMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeSTD121PhvsClinicianObservedLesionsStdMap");
				
				//Case Status
				int caseStatusSizeActual =  STD121PhvsClinicianObservedLesionsStdMap.size();
				int caseStatusExpected = 14;
	
				System.out.println("Iteration: "+iteration+"\nDEM152MarginalRaceMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeSTD121PhvsClinicianObservedLesionsStdMap_test *******************");
			
		}	
	
}
















/**
 * InitializeNBS150CaseDiagnosisMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS150CaseDiagnosisMap_test{
	

	private String iteration;

	
 public InitializeNBS150CaseDiagnosisMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS150CaseDiagnosisMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS150CaseDiagnosisMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS150CaseDiagnosisMap", NBS150CaseDiagnosisMap);

	 }
	 
	

	@Test
	public void initializeNBS150CaseDiagnosisMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS150CaseDiagnosisMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS150CaseDiagnosisMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS150CaseDiagnosisMap.size();
				int caseStatusExpected = 23;
	
				System.out.println("Iteration: "+iteration+"\nNBS150CaseDiagnosisMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS150CaseDiagnosisMap_test *******************");
			
		}	
	
}














/**
 * InitializeNBS149ExamReasonMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS149ExamReasonMap_test{
	

	private String iteration;

	
 public InitializeNBS149ExamReasonMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeNBS149ExamReasonMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS149ExamReasonMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS149ExamReasonMap", NBS149ExamReasonMap);

	 }
	 
	

	@Test
	public void initializeNBS149ExamReasonMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS149ExamReasonMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS149ExamReasonMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS149ExamReasonMap.size();
				int caseStatusExpected = 9;
	
				System.out.println("Iteration: "+iteration+"\nNBS149ExamReasonMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS149ExamReasonMap_test *******************");
			
		}	
	
}












/**
 * InitializeSTD119PartnerInternet_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeSTD119PartnerInternet_test{
	

	private String iteration;

	
 public InitializeSTD119PartnerInternet_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeSTD119PartnerInternet_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> STD119PartnerInternet= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "STD119PartnerInternet", STD119PartnerInternet);

	 }
	 
	

	@Test
	public void initializeSTD119PartnerInternet_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeSTD119PartnerInternet_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeSTD119PartnerInternet");
				
				//Case Status
				int caseStatusSizeActual =  STD119PartnerInternet.size();
				int caseStatusExpected = 5;
	
				System.out.println("Iteration: "+iteration+"\nSTD119PartnerInternet:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeSTD119PartnerInternet_test *******************");
			
		}	
	
}














/**
 * InitializeYNRUDStdMisMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeYNRUDStdMisMap_test{
	

	private String iteration;

	
 public InitializeYNRUDStdMisMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeYNRUDStdMisMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> YNRUDStdMisMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "YNRUDStdMisMap", YNRUDStdMisMap);

	 }
	 
	

	@Test
	public void initializeYNRUDStdMisMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeYNRUDStdMisMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeYNRUDStdMisMap");
				
				//Case Status
				int caseStatusSizeActual =  YNRUDStdMisMap.size();
				int caseStatusExpected = 6;
	
				System.out.println("Iteration: "+iteration+"\nYNRUDStdMisMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeYNRUDStdMisMap_test *******************");
			
		}	
	
}











/**
 * InitializeYNStdMisMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeYNStdMisMap_test{
	

	private String iteration;

	
 public InitializeYNStdMisMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeYNStdMisMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> YNStdMisMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "YNStdMisMap", YNStdMisMap);

	 }
	 
	

	@Test
	public void initializeYNStdMisMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeYNStdMisMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeYNStdMisMap");
				
				//Case Status
				int caseStatusSizeActual =  YNStdMisMap.size();
				int caseStatusExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\nYNStdMisMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeYNStdMisMap_test *******************");
			
		}	
	
}










/**
 * InitializeNBS263_HIVTestResult_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS263_HIVTestResult_test{
	

	private String iteration;

	
 public InitializeNBS263_HIVTestResult_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS263_HIVTestResult_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS263_HIVTestResult= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS263_HIVTestResult", NBS263_HIVTestResult);

	 }
	 
	

	@Test
	public void initializeNBS263_HIVTestResult_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS263_HIVTestResult_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS263_HIVTestResult");
				
				//Case Status
				int caseStatusSizeActual =  NBS263_HIVTestResult.size();
				int caseStatusExpected = 15;
	
				System.out.println("Iteration: "+iteration+"\nNBS263_HIVTestResult:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS263_HIVTestResult_test *******************");
			
		}	
	
}












/**
 * InitializeYNStdReverseMisMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeYNStdReverseMisMap_test{
	

	private String iteration;

	
 public InitializeYNStdReverseMisMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeYNStdReverseMisMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> YNStdReverseMisMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "YNStdReverseMisMap", YNStdReverseMisMap);

	 }
	 
	

	@Test
	public void initializeYNStdReverseMisMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeYNStdReverseMisMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeYNStdReverseMisMap");
				
				//Case Status
				int caseStatusSizeActual =  YNStdReverseMisMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nYNStdReverseMisMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeYNStdReverseMisMap_test *******************");
			
		}	
	
}






/**
 * InitializeNBS192_PatientInterviewStatusMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS192_PatientInterviewStatusMap_test{
	

	private String iteration;

	
 public InitializeNBS192_PatientInterviewStatusMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS192_PatientInterviewStatusMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS192_PatientInterviewStatusMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS192_PatientInterviewStatusMap", NBS192_PatientInterviewStatusMap);

	 }
	 
	

	@Test
	public void initializeNBS192_PatientInterviewStatusMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS192_PatientInterviewStatusMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS192_PatientInterviewStatusMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS192_PatientInterviewStatusMap.size();
				int caseStatusExpected = 10;
	
				System.out.println("Iteration: "+iteration+"\nNBS192_PatientInterviewStatusMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS192_PatientInterviewStatusMap_test *******************");
			
		}	
	
}






  



/**
 * InitializeNBS192_R_PatientInterviewStatusMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS192_R_PatientInterviewStatusMap_test{
	

	private String iteration;

	
 public InitializeNBS192_R_PatientInterviewStatusMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS192_R_PatientInterviewStatusMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS192_R_PatientInterviewStatusMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS192_R_PatientInterviewStatusMap", NBS192_R_PatientInterviewStatusMap);

	 }
	 
	

	@Test
	public void initializeNBS192_R_PatientInterviewStatusMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS192_R_PatientInterviewStatusMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS192_R_PatientInterviewStatusMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS192_R_PatientInterviewStatusMap.size();
				int caseStatusExpected = 8;
	
				System.out.println("Iteration: "+iteration+"\nNBS192_R_PatientInterviewStatusMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS192_R_PatientInterviewStatusMap_test *******************");
			
		}	
	
}











/**
 * InitializeNBS242PlacesToMeetPartnerMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS242PlacesToMeetPartnerMap_test{
	

	private String iteration;

	
 public InitializeNBS242PlacesToMeetPartnerMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS242PlacesToMeetPartnerMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS242PlacesToMeetPartnerMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS242PlacesToMeetPartnerMap", NBS242PlacesToMeetPartnerMap);

	 }
	 
	

	@Test
	public void initializeNBS242PlacesToMeetPartnerMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS242PlacesToMeetPartnerMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS242PlacesToMeetPartnerMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS242PlacesToMeetPartnerMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS242PlacesToMeetPartnerMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS242PlacesToMeetPartnerMap_test *******************");
			
		}	
	
}












/**
 * InitializeNBS244PlacesToHaveSexMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS244PlacesToHaveSexMap_test{
	

	private String iteration;

	
 public InitializeNBS244PlacesToHaveSexMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS244PlacesToHaveSexMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS244PlacesToHaveSexMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS244PlacesToHaveSexMap", NBS244PlacesToHaveSexMap);

	 }
	 
	

	@Test
	public void initializeNBS244PlacesToHaveSexMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS244PlacesToHaveSexMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS244PlacesToHaveSexMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS244PlacesToHaveSexMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS244PlacesToHaveSexMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS244PlacesToHaveSexMap_test *******************");
			
		}	
	
}




/**
 * InitializeNBS223FemalePartnersPastYearMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS223FemalePartnersPastYearMap_test{
	

	private String iteration;

	
 public InitializeNBS223FemalePartnersPastYearMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS223FemalePartnersPastYearMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS223FemalePartnersPastYearMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS223FemalePartnersPastYearMap", NBS223FemalePartnersPastYearMap);

	 }
	 
	

	@Test
	public void initializeNBS223FemalePartnersPastYearMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS223FemalePartnersPastYearMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS223FemalePartnersPastYearMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS223FemalePartnersPastYearMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS244PlacesToHaveSexMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS223FemalePartnersPastYearMap_test *******************");
			
		}	
	
}






/**
 * InitializeNBS225MalePartnersPastYearMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS225MalePartnersPastYearMap_test{
	

	private String iteration;

	
 public InitializeNBS225MalePartnersPastYearMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeNBS225MalePartnersPastYearMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS225MalePartnersPastYearMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS225MalePartnersPastYearMap", NBS225MalePartnersPastYearMap);

	 }
	 
	

	@Test
	public void initializeNBS225MalePartnersPastYearMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS225MalePartnersPastYearMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS225MalePartnersPastYearMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS225MalePartnersPastYearMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS225MalePartnersPastYearMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS225MalePartnersPastYearMap_test *******************");
			
		}	
	
}











/**
 * InitializeNBS227TransgenderPartnersPastYearMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS227TransgenderPartnersPastYearMap_test{
	

	private String iteration;

	
 public InitializeNBS227TransgenderPartnersPastYearMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"InitializeNBS227TransgenderPartnersPastYearMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS227TransgenderPartnersPastYearMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS227TransgenderPartnersPastYearMap", NBS227TransgenderPartnersPastYearMap);

	 }
	 
	

	@Test
	public void initializeNBS225MalePartnersPastYearMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS227TransgenderPartnersPastYearMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS227TransgenderPartnersPastYearMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS227TransgenderPartnersPastYearMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS227TransgenderPartnersPastYearMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS227TransgenderPartnersPastYearMap_test *******************");
			
		}	
	
}









/**
 * InitializeNBS129FemalePartnersInterviewPeriodMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS129FemalePartnersInterviewPeriodMap_test{
	

	private String iteration;

	
 public InitializeNBS129FemalePartnersInterviewPeriodMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		  {"initializeNBS129FemalePartnersInterviewPeriodMap_test"+"_"+it++},
    		  
    		  
    		  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS129FemalePartnersInterviewPeriodMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS129FemalePartnersInterviewPeriodMap", NBS129FemalePartnersInterviewPeriodMap);

	 }
	 
	

	@Test
	public void initializeNBS129FemalePartnersInterviewPeriodMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS129FemalePartnersInterviewPeriodMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS129FemalePartnersInterviewPeriodMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS129FemalePartnersInterviewPeriodMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS129FemalePartnersInterviewPeriodMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS129FemalePartnersInterviewPeriodMap_test *******************");
			
		}	
	
}









/**
 * InitializeNBS131MalePartnersInterviewPeriodMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS131MalePartnersInterviewPeriodMap_test{
	

	private String iteration;

	
 public InitializeNBS131MalePartnersInterviewPeriodMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeNBS131MalePartnersInterviewPeriodMap_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS131MalePartnersInterviewPeriodMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS131MalePartnersInterviewPeriodMap", NBS131MalePartnersInterviewPeriodMap);

	 }
	 
	

	@Test
	public void initializeNBS131MalePartnersInterviewPeriodMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS131MalePartnersInterviewPeriodMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS131MalePartnersInterviewPeriodMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS131MalePartnersInterviewPeriodMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS131MalePartnersInterviewPeriodMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS131MalePartnersInterviewPeriodMap_test *******************");
			
		}	
	
}









/**
 * InitializeNBS133TransgenderPartnersInterviewPeriodMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS133TransgenderPartnersInterviewPeriodMap_test{
	

	private String iteration;

	
 public InitializeNBS133TransgenderPartnersInterviewPeriodMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeNBS133TransgenderPartnersInterviewPeriodMap_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS133TransgenderPartnersInterviewPeriodMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS133TransgenderPartnersInterviewPeriodMap", NBS133TransgenderPartnersInterviewPeriodMap);

	 }
	 
	

	@Test
	public void initializeNBS133TransgenderPartnersInterviewPeriodMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS133TransgenderPartnersInterviewPeriodMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS133TransgenderPartnersInterviewPeriodMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS133TransgenderPartnersInterviewPeriodMap.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nNBS133TransgenderPartnersInterviewPeriodMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS133TransgenderPartnersInterviewPeriodMap_test *******************");
			
		}	
	
}











/**
 * InitializeNBS125OPSpouseMap_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS125OPSpouseMap_test{
	

	private String iteration;

	
 public InitializeNBS125OPSpouseMap_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeNBS125OPSpouseMap_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS125OPSpouseMap= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS125OPSpouseMap", NBS125OPSpouseMap);

	 }
	 
	

	@Test
	public void initializeNBS125OPSpouseMap_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS125OPSpouseMap_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS125OPSpouseMap");
				
				//Case Status
				int caseStatusSizeActual =  NBS125OPSpouseMap.size();
				int caseStatusExpected = 4;
	
				System.out.println("Iteration: "+iteration+"\nNBS125OPSpouseMap:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS125OPSpouseMap_test *******************");
			
		}	
	
}








/**
 * InitializeNBS872_QuantitativeResultsUnits_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeNBS872_QuantitativeResultsUnits_test{
	

	private String iteration;

	
 public InitializeNBS872_QuantitativeResultsUnits_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeNBS872_QuantitativeResultsUnits_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> NBS872_QuantitativeResultsUnits= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "NBS872_QuantitativeResultsUnits", NBS872_QuantitativeResultsUnits);

	 }
	 
	

	@Test
	public void initializeNBS872_QuantitativeResultsUnits_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeNBS872_QuantitativeResultsUnits_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeNBS872_QuantitativeResultsUnits");
				
				//Case Status
				int caseStatusSizeActual =  NBS872_QuantitativeResultsUnits.size();
				int caseStatusExpected = 31;
	
				System.out.println("Iteration: "+iteration+"\nNBS872_QuantitativeResultsUnits:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeNBS872_QuantitativeResultsUnits_test *******************");
			
		}	
	
}









/**
 * InitializeTUB128_TUB132_TUB865_SpecimenSource_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeTUB128_TUB132_TUB865_SpecimenSource_test{
	

	private String iteration;

	
 public InitializeTUB128_TUB132_TUB865_SpecimenSource_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeTUB128_TUB132_TUB865_SpecimenSource_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> TUB128_TUB132_TUB865_SpecimenSource= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "TUB128_TUB132_TUB865_SpecimenSource", TUB128_TUB132_TUB865_SpecimenSource);

	 }
	 
	

	@Test
	public void initializeTUB128_TUB132_TUB865_SpecimenSource_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeTUB128_TUB132_TUB865_SpecimenSource_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeTUB128_TUB132_TUB865_SpecimenSource");
				
				//Case Status
				int caseStatusSizeActual =  TUB128_TUB132_TUB865_SpecimenSource.size();
				int caseStatusExpected = 97;
	
				System.out.println("Iteration: "+iteration+"\nTUB128_TUB132_TUB865_SpecimenSource:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeTUB128_TUB132_TUB865_SpecimenSource_test *******************");
			
		}	
	
}











/**
 * InitializeINV290TestType_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV290TestType_test{
	

	private String iteration;

	
 public InitializeINV290TestType_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV290TestType_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV290TestType= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV290TestType", INV290TestType);

	 }
	 
	

	@Test
	public void initializeINV290TestType_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV290TestType_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV290TestType");
				
				//Case Status
				int caseStatusSizeActual =  INV290TestType.size();
				int caseStatusExpected = 16;
	
				System.out.println("Iteration: "+iteration+"\nINV290TestType:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV290TestType_test *******************");
			
		}	
	
}










/**
 * InitializeLAB165SpecimenSource_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeLAB165SpecimenSource_test{
	

	private String iteration;

	
 public InitializeLAB165SpecimenSource_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeLAB165SpecimenSource_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> LAB165SpecimenSource= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LAB165SpecimenSource", LAB165SpecimenSource);

	 }
	 
	

	@Test
	public void initializeLAB165SpecimenSource_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeLAB165SpecimenSource_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeLAB165SpecimenSource");
				
				//Case Status
				int caseStatusSizeActual =  LAB165SpecimenSource.size();
				int caseStatusExpected = 98;
	
				System.out.println("Iteration: "+iteration+"\nLAB165SpecimenSource:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeLAB165SpecimenSource_test *******************");
			
		}	
	
}









/**
 * InitializeINV515Criteria_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV515Criteria_test{
	

	private String iteration;

	
 public InitializeINV515Criteria_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV515Criteria_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV515Criteria= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV515Criteria", INV515Criteria);

	 }
	 
	

	@Test
	public void initializeINV515Criteria_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV515Criteria_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV515Criteria");
				
				//Case Status
				int caseStatusSizeActual =  INV515Criteria.size();
				int caseStatusExpected = 6;
	
				System.out.println("Iteration: "+iteration+"\nINV515Criteria:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV515Criteria_test *******************");
			
		}	
	
}










/**
 * InitializeINV1133PrimarySites_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV1133PrimarySites_test{
	

	private String iteration;

	
 public InitializeINV1133PrimarySites_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV1133PrimarySites_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1133PrimarySites= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1133PrimarySites", INV1133PrimarySites);

	 }
	 
	

	@Test
	public void initializeINV1133PrimarySites_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV1133PrimarySites_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV1133PrimarySites");
				
				//Case Status
				int caseStatusSizeActual =  INV1133PrimarySites.size();
				int caseStatusExpected = 14;
	
				System.out.println("Iteration: "+iteration+"\nINV1133PrimarySites:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV1133PrimarySites_test *******************");
			
		}	
	
}









/**
 * InitializeINV1152MovedToWhere_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV1152MovedToWhere_test{
	

	private String iteration;

	
 public InitializeINV1152MovedToWhere_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV1152MovedToWhere_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1152MovedToWhere= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1152MovedToWhere", INV1152MovedToWhere);

	 }
	 
	

	@Test
	public void initializeINV1152MovedToWhere_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV1152MovedToWhere_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV1152MovedToWhere");
				
				//Case Status
				int caseStatusSizeActual =  INV1152MovedToWhere.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\nINV1152MovedToWhere:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV1152MovedToWhere_test *******************");
			
		}	
	
}









/**
 * InitializeINV1141ReasonTherapyExtended_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV1141ReasonTherapyExtended_test{
	

	private String iteration;

	
 public InitializeINV1141ReasonTherapyExtended_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV1141ReasonTherapyExtended_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1141ReasonTherapyExtended= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1141ReasonTherapyExtended", INV1141ReasonTherapyExtended);

	 }
	 
	

	@Test
	public void initializeINV1141ReasonTherapyExtended_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV1141ReasonTherapyExtended_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV1141ReasonTherapyExtended");
				
				//Case Status
				int caseStatusSizeActual =  INV1141ReasonTherapyExtended.size();
				int caseStatusExpected = 7;
	
				System.out.println("Iteration: "+iteration+"\nINV1141ReasonTherapyExtended:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV1141ReasonTherapyExtended_test *******************");
			
		}	
	
}









/**
 * InitializeCode55753TreatmentAdministration_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCode55753TreatmentAdministration_test{
	

	private String iteration;

	
 public InitializeCode55753TreatmentAdministration_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeCode55753TreatmentAdministration_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code55753TreatmentAdministration= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code55753TreatmentAdministration", code55753TreatmentAdministration);

	 }
	 
	

	@Test
	public void initializeCode55753TreatmentAdministration_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCode55753TreatmentAdministration_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCode55753TreatmentAdministration");
				
				//Case Status
				int caseStatusSizeActual =  code55753TreatmentAdministration.size();
				int caseStatusExpected = 3;
	
				System.out.println("Iteration: "+iteration+"\ncode55753TreatmentAdministration:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCode55753TreatmentAdministration_test *******************");
			
		}	
	
}







/**
 * InitializeCode64750_3SevereAdverse_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCode64750_3SevereAdverse_test{
	

	private String iteration;

	
 public InitializeCode64750_3SevereAdverse_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeCode64750_3SevereAdverse_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code64750_3SevereAdverse= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code64750_3SevereAdverse", code64750_3SevereAdverse);

	 }
	 
	

	@Test
	public void initializeCode64750_3SevereAdverse_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCode64750_3SevereAdverse_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCode64750_3SevereAdverse");
				
				//Case Status
				int caseStatusSizeActual =  code64750_3SevereAdverse.size();
				int caseStatusExpected = 2;
	
				System.out.println("Iteration: "+iteration+"\ncode64750_3SevereAdverse:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCode64750_3SevereAdverse_test *******************");
			
		}	
	
}











/**
 * InitializeINV1133SecondarySites_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV1133SecondarySites_test{
	

	private String iteration;

	
 public InitializeINV1133SecondarySites_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV1133SecondarySites_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1133SecondarySites= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1133SecondarySites", INV1133SecondarySites);

	 }
	 
	

	@Test
	public void initializeINV1133SecondarySites_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV1133SecondarySites_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV1133SecondarySites");
				
				//Case Status
				int caseStatusSizeActual =  INV1133SecondarySites.size();
				int caseStatusExpected = 58;
	
				System.out.println("Iteration: "+iteration+"\nINV1133SecondarySites:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV1133SecondarySites_test *******************");
			
		}	
	
}









/**
 * InitializeLABAST6Drugs_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeLABAST6Drugs_test{
	

	private String iteration;

	
 public InitializeLABAST6Drugs_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeLABAST6Drugs_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> LABAST6Drugs= new HashMap<String, String>();
	private static Map<String, String> LABAST6DrugName= new HashMap<String, String>();

	
	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LABAST6Drugs", LABAST6Drugs);
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LABAST6DrugName", LABAST6DrugName);

		 
	 }
	 
	

	@Test
	public void initializeLABAST6Drugs_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeLABAST6Drugs_test *******************");

	
				
				if(LABAST6DrugName.size()==0){
					LABAST6DrugName.put("18860-7","Amikacin");
					LABAST6DrugName.put("LAB675","Bedaquiline");
					LABAST6DrugName.put("18872-2","Capreomycin");
					LABAST6DrugName.put("18906-8","Ciprofloxacin");
					LABAST6DrugName.put("23627-3","Clofazimine");
					LABAST6DrugName.put("18914-2","Cycloserine");
					LABAST6DrugName.put("LAB676","Delamanid");
					LABAST6DrugName.put("18921-7","Ethambutol");
					LABAST6DrugName.put("18922-5","Ethionamide");
					LABAST6DrugName.put("18934-0","Isoniazid");
					LABAST6DrugName.put("18935-7","Kanamycin");
					LABAST6DrugName.put("20629-2","Levofloxacin");
					LABAST6DrugName.put("29258-1","Linezolid");
					LABAST6DrugName.put("31039-1","Moxifloxacin");
					LABAST6DrugName.put("18959-7","Ofloxacin");
					LABAST6DrugName.put("LAB674","Other Quinolones");
					LABAST6DrugName.put("OTH","Other Test Type");
					LABAST6DrugName.put("23629-9","Para-Aminesalicylicacid");
					LABAST6DrugName.put("93850-6","Pretomanid");
					LABAST6DrugName.put("18973-8","Pyrazinamide");
					LABAST6DrugName.put("19149-4","Rifabutin");
					LABAST6DrugName.put("18974-6","Rifampin");
					LABAST6DrugName.put("76627-9","Rifapentine");
					LABAST6DrugName.put("18982-9","Streptomycin");

				}
				
				
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeLABAST6Drugs");
				
				//Case Status
				int caseStatusSizeActual =  LABAST6Drugs.size();
				int caseStatusExpected = 23;
	
				System.out.println("Iteration: "+iteration+"\nLABAST6Drugs:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeLABAST6Drugs_test *******************");
			
		}	
	
}







/**
 * InitializeLABAST6DrugName_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeLABAST6DrugName_test{
	

	private String iteration;

	
 public InitializeLABAST6DrugName_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeLABAST6DrugName_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> LABAST6DrugName= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LABAST6DrugName", LABAST6DrugName);

	 }
	 
	

	@Test
	public void initializeLABAST6DrugName_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeLABAST6DrugName_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeLABAST6DrugName");
				
				//Case Status
				int caseStatusSizeActual =  LABAST6DrugName.size();
				int caseStatusExpected = 24;
	
				System.out.println("Iteration: "+iteration+"\nLABAST6DrugName:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeLABAST6DrugName_test *******************");
			
		}	
	
}












/**
 * InitializeLABAST3SpecimenSource_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeLABAST3SpecimenSource_test{
	

	private String iteration;

	
 public InitializeLABAST3SpecimenSource_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeLABAST3SpecimenSource_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> LABAST3SpecimenSource= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LABAST3SpecimenSource", LABAST3SpecimenSource);

	 }
	 
	

	@Test
	public void initializeLABAST3SpecimenSource_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeLABAST3SpecimenSource_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeLABAST3SpecimenSource");
				
				//Case Status
				int caseStatusSizeActual =  LABAST3SpecimenSource.size();
				int caseStatusExpected = 98;
	
				System.out.println("Iteration: "+iteration+"\nLABAST3SpecimenSource:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeLABAST3SpecimenSource_test *******************");
			
		}	
	
}









/**
 * InitializeLABAST7TestMethod_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeLABAST7TestMethod_test{
	

	private String iteration;

	
 public InitializeLABAST7TestMethod_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeLABAST7TestMethod_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> LABAST7TestMethod= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LABAST7TestMethod", LABAST7TestMethod);

	 }
	 
	

	@Test
	public void initializeLABAST7TestMethod_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeLABAST7TestMethod_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeLABAST7TestMethod");
				
				//Case Status
				int caseStatusSizeActual =  LABAST7TestMethod.size();
				int caseStatusExpected = 5;
	
				System.out.println("Iteration: "+iteration+"\nLABAST7TestMethod:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeLABAST7TestMethod_test *******************");
			
		}	
	
}








/**
 * InitializeLAB684SpecimenSource_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeLAB684SpecimenSource_test{
	

	private String iteration;

	
 public InitializeLAB684SpecimenSource_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeLAB684SpecimenSource_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> LAB684SpecimenSource= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "LAB684SpecimenSource", LAB684SpecimenSource);

	 }
	 
	

	@Test
	public void initializeLAB684SpecimenSource_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeLAB684SpecimenSource_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeLAB684SpecimenSource");
				
				//Case Status
				int caseStatusSizeActual =  LAB684SpecimenSource.size();
				int caseStatusExpected = 98;
	
				System.out.println("Iteration: "+iteration+"\nLAB684SpecimenSource:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeLAB684SpecimenSource_test *******************");
			
		}	
	
}










/**
 * Initializecode48018_6Gene_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class Initializecode48018_6Gene_test{
	

	private String iteration;

	
 public Initializecode48018_6Gene_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializecode48018_6Gene_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code48018_6Gene= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code48018_6Gene", code48018_6Gene);

	 }
	 
	

	@Test
	public void initializecode48018_6Gene_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializecode48018_6Gene_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializecode48018_6Gene");
				
				//Case Status
				int caseStatusSizeActual =  code48018_6Gene.size();
				int caseStatusExpected = 20;
	
				System.out.println("Iteration: "+iteration+"\ncode48018_6Gene:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializecode48018_6Gene_test *******************");
			
		}	
	
}







/**
 * InitializeINV1153OutOfState_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV1153OutOfState_test{
	

	private String iteration;

	
 public InitializeINV1153OutOfState_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV1153OutOfState_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1153OutOfState= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1153OutOfState", INV1153OutOfState);

	 }
	 
	

	@Test
	public void initializeINV1153OutOfState_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV1153OutOfState_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV1153OutOfState");
				
				//Case Status
				int caseStatusSizeActual =  INV1153OutOfState.size();
				int caseStatusExpected = 59;
	
				System.out.println("Iteration: "+iteration+"\nINV1153OutOfState:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV1153OutOfState_test *******************");
			
		}	
	
}










/**
 * InitializeINV1154OutOfCountry_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV1154OutOfCountry_test{
	

	private String iteration;

	
 public InitializeINV1154OutOfCountry_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV1154OutOfCountry_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1154OutOfCountry= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1154OutOfCountry", INV1154OutOfCountry);

	 }
	 
	

	@Test
	public void initializeINV1154OutOfCountry_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV1154OutOfCountry_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV1154OutOfCountry");
				
				//Case Status
				int caseStatusSizeActual =  INV1154OutOfCountry.size();
				int caseStatusExpected = 246;
	
				System.out.println("Iteration: "+iteration+"\nINV1154OutOfCountry:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV1154OutOfCountry_test *******************");
			
		}	
	
}









/**
 * InitializeINV1158Drug_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeINV1158Drug_test{
	

	private String iteration;

	
 public InitializeINV1158Drug_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeINV1158Drug_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> INV1158Drug= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "INV1158Drug", INV1158Drug);

	 }
	 
	

	@Test
	public void initializeINV1158Drug_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeINV1158Drug_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeINV1158Drug");
				
				//Case Status
				int caseStatusSizeActual =  INV1158Drug.size();
				int caseStatusExpected = 24;
	
				System.out.println("Iteration: "+iteration+"\nINV1158Drug:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeINV1158Drug_test *******************");
			
		}	
	
}









/**
 * InitializeCode42563_7_CDSideEffect_test: this method will make sure that each specific map is initialized with the right number of values.
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeCode42563_7_CDSideEffect_test{
	

	private String iteration;

	
 public InitializeCode42563_7_CDSideEffect_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;

		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"initializeCode42563_7_CDSideEffect_test"+"_"+it++},
			  
			  
			  
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   
   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code42563_7_CDSideEffect= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code42563_7_CDSideEffect", code42563_7_CDSideEffect);

	 }
	 
	

	@Test
	public void initializeCode42563_7_CDSideEffect_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeCode42563_7_CDSideEffect_test *******************");

	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeCode42563_7_CDSideEffect");
				
				//Case Status
				int caseStatusSizeActual =  code42563_7_CDSideEffect.size();
				int caseStatusExpected = 13;
	
				System.out.println("Iteration: "+iteration+"\ncode42563_7_CDSideEffect:\nActual Size: "+caseStatusSizeActual+"\nExpected size: "+caseStatusExpected);
		
				Assert.assertEquals(caseStatusExpected, caseStatusSizeActual);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeCode42563_7_CDSideEffect_test *******************");
			
		}	
	
}











/**
 * InitializeValues_test:  this method will test InitializeValues method. It will pass if the last method is called once, meaning all the methods have been called.
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCRVCTForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeValues_test{
	

 private String iteration;
	
 public InitializeValues_test(String it){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
		
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   

	
			  
	
			
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
	  return Arrays.asList(new Object[][]{
			  
			  {"InitializeValues_test"+"_"+it++}
 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
 
	@Mock
	PropertyUtil propertyUtilMocked;
	

	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CommonPDFPrintForm commonPDFPrintForm =  Mockito.mock(CommonPDFPrintForm.class);


	
	
	private static Map<String, String> code42563_7_CDSideEffect= new HashMap<String, String>();

	
	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CommonPDFPrintForm.class);
		
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		
		
		 
		 Whitebox.setInternalState(CommonPDFPrintForm.class, "code42563_7_CDSideEffect", code42563_7_CDSideEffect);
	 }
	 
	

	@Test
	public void initializeValues_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: initializeValues_test *******************");
				
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeDemographics");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCaseStatus");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCaseVerification");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCaseCounted");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCountryOfVerifiedCase");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCountryOfUsualresidency");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeEverWorkedAs");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeOccupation");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeIndustry");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeDEM152MarginalRace");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeDEM155PhvsEthnicitygroupCdcUnkMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeIXS105NbsInterviewTypeMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS151SurveillancePatientFollowupMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS273PEthnUnkReasonMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeSTD121PhvsClinicianObservedLesionsStdMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS150CaseDiagnosisMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS149ExamReasonMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeSTD119PartnerInternet");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeYNRUDStdMisMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeYNStdMisMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS263_HIVTestResult");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeYNStdReverseMisMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS192_PatientInterviewStatusMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS192_R_PatientInterviewStatusMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS242PlacesToMeetPartnerMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS244PlacesToHaveSexMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS223FemalePartnersPastYearMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS225MalePartnersPastYearMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS227TransgenderPartnersPastYearMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS129FemalePartnersInterviewPeriodMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS131MalePartnersInterviewPeriodMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS133TransgenderPartnersInterviewPeriodMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS125OPSpouseMap");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeNBS872_QuantitativeResultsUnits");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeTUB128_TUB132_TUB865_SpecimenSource");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV290TestType");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeLAB165SpecimenSource");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV515Criteria");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV1133PrimarySites");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV1152MovedToWhere");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV1141ReasonTherapyExtended");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCode55753TreatmentAdministration");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCode64750_3SevereAdverse");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV1133SecondarySites");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeLABAST6DrugName");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeLABAST6Drugs");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeLABAST3SpecimenSource");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeLABAST7TestMethod");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeLAB684SpecimenSource");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializecode48018_6Gene");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV1153OutOfState");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV1154OutOfCountry");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeINV1158Drug");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCode42563_7_CDSideEffect");
				PowerMockito.doNothing().when(CommonPDFPrintForm.class	,"initializeCode42563_7sideEffects");
				
				
				
				
				
								
			
	
				Whitebox.invokeMethod(commonPDFPrintForm, "initializeValues");
				
			   PowerMockito.verifyStatic(Mockito.times(1));
			   CommonPDFPrintForm.initializeCode42563_7sideEffects();        
					
	
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeValues_test *******************");
			
		}	
	
}






}
