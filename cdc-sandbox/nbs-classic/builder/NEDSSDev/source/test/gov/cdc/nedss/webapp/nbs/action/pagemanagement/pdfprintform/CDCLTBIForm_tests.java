package test.gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;



import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
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
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;




/**
Methods that have been unit tested so far:

- fillPDForm
- printForm
 */

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class})
@RunWith(Enclosed.class)
@PowerMockIgnore("javax.management.*")
public class CDCLTBIForm_tests {


/**
 * FillPDForm_test: this method will test fillPDForm method. As long as it is able to execute all the methods, this test is considered pass. It doesn"t return any value.
 * The reason why we are not verifying any method inside the method that we are testing has been executed n times is because the one that we could use, processPDFFIelds, is protected and extended from super class, so it is not visible.
 * @author Fatima.Lopezcalzado
 *
 */
	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class, PDAcroForm.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class FillPDForm_test{
	

	private String iteration;
	private String invFormCd;
	
 public FillPDForm_test(String it, String invFormCd){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.invFormCd = invFormCd;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"fillPDForm_test"+"_"+it++,"PG_TB_LTBI_Investigation"},
    		  {"fillPDForm_test"+"_"+it++,"PG_TB_LTBI_Investigation_2"}
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
   Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   
   @Mock
   PageForm form;
   
   @Mock
   PageClientVO pageClientVO;
   

   @Mock
	PDDocument pdfDocument;

	@Mock
	PDDocumentCatalog docCatalog;
	

	@Mock
	HttpServletResponse res;


	@Mock
	 ServletOutputStream os;
	
	@Mock
	HttpServletRequest req;
	
	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	PDAcroForm acroForm;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLTBIForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 req = Mockito.mock(HttpServletRequest.class);
		 form = Mockito.mock(PageForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 pageClientVO = Mockito.mock(PageClientVO.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 res = 	Mockito.mock(HttpServletResponse.class);
		 docCatalog=Mockito.mock(PDDocumentCatalog.class);
		 pdfDocument = Mockito.mock(PDDocument.class);
		 acroForm = Mockito.mock(PDAcroForm.class);
		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "pageForm", form);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		 
 		 
	 }
	 
	

	@Test
	public void fillPDForm_test() throws Exception{
			
			
				System.out.println("******************* Starting test case named: fillPDForm_test *******************");
	
				
				
				
				Map<Object, Object>  answerMap =new HashMap<Object, Object>();
				Mockito.when(form.getPageClientVO()).thenReturn(pageClientVO);
				Mockito.when(pageClientVO.getAnswerMap()).thenReturn(answerMap);
				PowerMockito.suppress(MemberMatcher.methodsDeclaredIn(CommonPDFPrintForm.class));
				Mockito.when(res.getOutputStream()).thenReturn(os);
			    PowerMockito.mockStatic(PDDocument.class);
				PowerMockito.doReturn(pdfDocument).when(PDDocument.class, "load",Matchers.any(File.class));
				Mockito.doNothing().when(pdfDocument).save(Matchers.any(ServletOutputStream.class));
				Mockito.doReturn(docCatalog).when(pdfDocument).getDocumentCatalog();
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeLTBIFormValues");
			//	PDAcroForm acroForm = new PDAcroForm(pdfDocument);
				
				PowerMockito.whenNew(PDAcroForm.class).withArguments(pdfDocument).thenReturn(acroForm);
				
				
				//if processPDFFIelds is not protected, we can run following code and also check how many times it has been executed.
				//otherwise, we will just make sure there"s no errors while running fillPDForm method.
			//	PowerMockito.doNothing().when(CDCLTBIForm.class	,"processPDFFIelds", Matchers.any(PDField.class), Matchers.any(Integer.class));
				
				
				Whitebox.invokeMethod(cdcLTBIForm, "fillPDForm", acroForm,invFormCd, req);
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: fillPDForm_test *******************");
			
		}		
	
}
	


/**
 * PrintForm_test: this method will test printForm method. this method will pass if save is called once.
 * pdfDocument.save(res.getOutputStream());, is not a mock and it doesn"t change any specific value that we can compare against.
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class PrintForm_test{
	

	private String iteration;
	private String formType;
	
 public PrintForm_test(String it, String formType){
	 
	 super();
	 this.iteration = it;
	 this.formType = formType;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"printForm_test"+"_"+it++,"NoBlank"},
    		  {"printForm_test"+"_"+it++,"Blank"}
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   
   @Mock
   PageForm form;
   
   @Mock
   PageClientVO pageClientVO;
   

	@Mock
	HttpServletRequest req;
	
	@Mock
	 ServletOutputStream os;
	
	@Mock
	HttpServletResponse res;
	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	PDDocument pdfDocument;
	
	@Mock
	LogUtils loggerMock;
	
	@Mock
	PDDocumentCatalog docCatalog;
	

	
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

		// form = Mockito.mock(PageForm.class);
		 form = Mockito.mock(PageForm.class);
		 req = Mockito.mock(HttpServletRequest.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 res = 	Mockito.mock(HttpServletResponse.class);
		 PowerMockito.spy(CDCLTBIForm.class);
		 docCatalog=Mockito.mock(PDDocumentCatalog.class);
	//	 acroForm=Mockito.mock(PDAcroForm.class);
		 pdfDocument = Mockito.mock(PDDocument.class);
		 pageClientVO = Mockito.mock(PageClientVO.class);
		 os = Mockito.mock(ServletOutputStream.class);
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "pageForm", form);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
 		 
	 }
	 

	@Test
	public void printForm_test() throws Exception{
			
				System.out.println("******************* Starting test case named: printForm_test *******************");
			
				Map<Object, Object>  answerMap =new HashMap<Object, Object>();
				
				Mockito.when(form.getPageClientVO()).thenReturn(pageClientVO);
				Mockito.when(pageClientVO.getAnswerMap()).thenReturn(answerMap);
				PowerMockito.suppress(MemberMatcher.methodsDeclaredIn(CommonPDFPrintForm.class));

				PowerMockito.doNothing().when(CDCLTBIForm.class	,"fillPDForm",Matchers.any(PDAcroForm.class), Matchers.any(String.class), Matchers.any(HttpServletRequest.class));
				
				 
	
				Mockito.when(res.getOutputStream()).thenReturn(os);
			    PowerMockito.mockStatic(PDDocument.class);

				PowerMockito.doReturn(pdfDocument).when(PDDocument.class, "load",Matchers.any(File.class));
				Mockito.doNothing().when(pdfDocument).save(Matchers.any(ServletOutputStream.class));
				Mockito.doNothing().when(pdfDocument).close();
		
				
				Mockito.doReturn(docCatalog).when(pdfDocument).getDocumentCatalog();
 
				Whitebox.invokeMethod(cdcLtbiForm, "printForm", form,req, res, formType);

				
				Mockito.verify(pdfDocument, Mockito.times(1)).save(Matchers.any(ServletOutputStream.class));
			   
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: printForm_test *******************");
			
		}		
	
}
	


/**
 * initializeLTBIFormValues_test: this method will test initializeLTBIFormValues method.It will pass if the array size is 0 and the methods are called once, or if the array size if different than 0, then it won"t call any of the methods.
 * As a parameter we are sending the size of the Map.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeLTBIFormValues_test{
	

	private String iteration;
	private int size;
	
 public InitializeLTBIFormValues_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeLTBIFormValues_test"+"_"+it++,0},
    		  {"initializeLTBIFormValues_test"+"_"+it++,1},
    		  {"initializeLTBIFormValues_test"+"_"+it++,2},
    		  {"initializeLTBIFormValues_test"+"_"+it++,3},
    		  {"initializeLTBIFormValues_test"+"_"+it++,4},
    		  {"initializeLTBIFormValues_test"+"_"+it++,5},
    		  {"initializeLTBIFormValues_test"+"_"+it++,6}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   
   @Mock
   PageForm form;
   
   @Mock
   PageClientVO pageClientVO;
   

	@Mock
	HttpServletRequest req;
	@Mock
	 ServletOutputStream os;
	
	@Mock
	HttpServletResponse res;
	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

		// form = Mockito.mock(PageForm.class);
		 form = Mockito.mock(PageForm.class);
		 req = Mockito.mock(HttpServletRequest.class);
		 res = 	Mockito.mock(HttpServletResponse.class);
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "pageForm", form);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		 
 		 
	 }
	 
	

	@Test
	public void initializeLTBIFormValues_test() throws Exception{
			
			//PDDocument doc = new PDDocument();
			//PDAcroForm acroForm = new PDAcroForm(doc);
		
				System.out.println("******************* Starting test case named: initializeLTBIFormValues_test *******************");
		
			
				
				
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializePatientDemographics");
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeAdministrative");
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeInitialEvaluation");
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeRisks");
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeDiagnosisTesting");
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeChest");
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeEpidemiologic");
				PowerMockito.doNothing().when(CDCLTBIForm.class	,"initializeTreatmentAndOutcome");
	
				for (int i=0; i<size; i++)
					LTBIFieldsMap.put("","");
								
				Whitebox.invokeMethod(CDCLTBIForm.class, "initializeLTBIFormValues");

				if(size==0){
				   PowerMockito.verifyStatic(Mockito.times(1));
				   CDCLTBIForm.initializeTreatmentAndOutcome();        
				}else{
					PowerMockito.verifyStatic(Mockito.times(0));
					CDCLTBIForm.initializeTreatmentAndOutcome();
				}
				
				
				
				
				
				System.out.println(iteration+ ": PASSED");
				System.out.println("******************* End test case named: initializeLTBIFormValues_test *******************");
			
		}		
	
}
	




/**
 * initializePatientDemographics: this method will test initializePatientDemographics. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializePatientDemographics_test{
	

	private String iteration;
	private int size;
	
 public InitializePatientDemographics_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializePatientDemographics_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		String delimiter1 = "__";

		String DEM165	= "DEM165"; //County
		String DEM167	= "DEM167"; //Country
		
		String DEM102 = "DEM102";
		String DEM102__PatientLastNameDEM102= DEM102 + delimiter1+ "PatientLastNameDEM102";
		 String DEM104_FN = "DEM104_FN";
		String DEM104_FN__PatientFirstNameDEM104= DEM104_FN + delimiter1+ "PatientFirstNameDEM104";
		 String DEM105 = "DEM105";
		String DEM105__PatientMiddleNameDEM105= DEM105 + delimiter1+ "PatientMiddleNameDEM105";
		 String DEM250 = "DEM250";
		String DEM250__PatientAliasNameDEM250= DEM250 + delimiter1+ "PatientAliasNameDEM250";	
		 String FullName = "Full_Name";
		String FullName__Full_Name= FullName + delimiter1+ "FullNameFull_Name";	
		 String DEM159_DEM160= "DEM159_DEM160";//street1+street2 - there is a space in form
		String DEM159_160__PatientStreetDEM159= DEM159_DEM160 + delimiter1+ "PatientLastNameDEM159DEM160";
		 String DEM161 = "DEM161";
		String DEM161__PatientCityDEM161= DEM161 + delimiter1+ "PatientCityDEM161";
		 String DEM162 = "DEM162";
		String DEM162__PatientStateDEM162= DEM162 + delimiter1+ "PatientStateDEM162";
		 String DEM163 = "DEM163";
		String DEM163__PatientZipDEM163= DEM163 + delimiter1+ "PatientZipDEM163";
		String DEM165__PatientCountyDEM165= DEM165 + delimiter1+ "PatientCountyDEM165";
		String DEM167__PatientCountryDEM167= DEM167 + delimiter1+ "PatientCountryDEM167";
		 String DEM168 = "DEM168";
		String DEM168__CensusTracDEM168= DEM168 + delimiter1+ "PatientCensusTrackDEM168";
		String City_Limits_CD = "INV1112_CD";
		String CityLimitsINV1112= City_Limits_CD + delimiter1+ "CityLimitsINV1112";
		 String NBS006 = "NBS006";
		String NBS006__PatientCellNBS006_UseCd = NBS006 + delimiter1+ "PatientCellNBS006";
		 String DEM177 = "DEM177";
		String DEM177__PatientHmPhDEM177_UseCd = DEM177 + delimiter1+ "PatientHmPhDEM177";
		 String NBS002 = "NBS002";
		 String INV2001 = "INV2001";
		String INV2001__PatientReportedAge = INV2001 + delimiter1+ "PatientReportedAgeINV2001";
		 String DEM115 = "DEM115";
		String DEM115__PatientDOB = DEM115 + delimiter1+ "PatientDOBDEM115";
		 String INV178_CD = "INV178_CD";
		String INV178__PatientPregnantINV178 = INV178_CD + delimiter1+ "PatientPregnantINV178";
		 String NBS128 = "NBS128";
		String NBS128__PatientPregnantWeeksNBS128 = NBS128 + delimiter1+ "PatientPregnantNBS128";
		 String DEM152_AI_CDT = "DEM152_AI_CDT";
		String DEM152_AI_CDT__PatientRaceDEM152 = DEM152_AI_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_A_CDT = "DEM152_A_CDT";
		String DEM152_A_CDT__PatientRaceDEM152 = DEM152_A_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_B_CDT = "DEM152_B_CDT";
		String DEM152_B_CDT__PatientRaceDEM152 = DEM152_B_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_W_CDT = "DEM152_W_CDT";
		String DEM152_W_CDT__PatientRaceDEM152 = DEM152_W_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_O_CDT = "DEM152_O_CDT";
		String DEM152_O_CDT__PatientRaceDEM152 = DEM152_O_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_NH_CDT = "DEM152_NH_CDT";
		String DEM152_NH_CDT__PatientRaceDEM152 = DEM152_NH_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_U_CDT = "DEM152_U_CDT";
		String DEM152_U_CDT__PatientRaceDEM152 = DEM152_U_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_R_CDT = "DEM152_R_CDT";
		String DEM152_R_CDT__PatientRaceDEM152 = DEM152_R_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_D_CDT = "DEM152_D_CDT";
		String DEM152_D_CDT__PatientRaceDEM152 = DEM152_D_CDT + delimiter1+ "PatientRaceDEM152";	
		 String DEM152_NA_CDT = "DEM152_NA_CDT";
		String DEM152_NA_CDT__PatientRaceDEM152 = DEM152_NA_CDT + delimiter1+ "PatientRaceDEM152";	
		String DEM242 = "DEM242";
		String DEM242__AmericanIndianAlaskanNative = DEM242 + delimiter1 +"SpecifyAmericanIndianAlaskanNativeDEM242";
		String DEM243 = "DEM243";
		String DEM243_Asian= DEM243 + delimiter1 +"SpecifyAsianDEM243";
		String DEM244 = "DEM244";
		String DEM244_BlackAfricanAmerican= DEM244 + delimiter1 +"DEM244_BlackAfricanAmerican";
		String DEM245 = "DEM245";
		String DEM245_Hawaiian = DEM245 + delimiter1 +"DEM245_Hawaiian";
		String DEM246 = "DEM246";
		String DEM246_White = DEM246 + delimiter1 +"DEM246_White";
		String DEM155_CDT = "DEM155_CDT";
		String DEM155_CDT__PatientEthnicityDEM155 = DEM155_CDT + delimiter1+ "EthnicityDEM155";
		String NBS273_CDT = "NBS273_CDT";
		String NBS273_CDT__PatientEthnicityUnkownReasonNBS273 = NBS273_CDT + delimiter1+ "PatientEthnicityUnkownReasonNBS273";
		String DEM113_CD = "DEM113_CD";
		String DEM113_CD__PatientCurrentSexDEM113 = DEM113_CD + delimiter1+ "PatientCurrentSexDEM113";	 
		String NBS274_CD = "NBS274_CD";
		String  NBS274_CD__PatientTransgenderNBS274 =  NBS274_CD + delimiter1+ "PatientTransgenderNBS274";	
		String NBS272_CD = "NBS272_CD";
		String NBS272_CD__PatientSexUnkownReasonNBS272 = NBS272_CD + delimiter1+ "PatientSexUnkownReasonNBS272";
		String CURRENT_SEX = "Current_Sex_CDT";
		String CURRENT_SEX_currentSex = CURRENT_SEX+delimiter1+"CurrentSexCDT";
		String Employment_Phone = "Employment_Phone";//Field Follow Up form constant
		String EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003 = Employment_Phone+delimiter1+"PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003";
			
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM102",DEM102 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM102__PatientLastNameDEM102",DEM102__PatientLastNameDEM102 );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM104_FN",DEM104_FN  );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM104_FN__PatientFirstNameDEM104",DEM104_FN__PatientFirstNameDEM104 );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM105",DEM105  );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM105__PatientMiddleNameDEM105",DEM105__PatientMiddleNameDEM105 );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM250",DEM250  );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM250__PatientAliasNameDEM250",DEM250__PatientAliasNameDEM250 );
		Whitebox.setInternalState(CDCLTBIForm.class,"FullName",FullName  );
		Whitebox.setInternalState(CDCLTBIForm.class,"FullName__Full_Name",FullName__Full_Name );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM159_DEM160",DEM159_DEM160 );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM159_160__PatientStreetDEM159",DEM159_160__PatientStreetDEM159 );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM161",DEM161  );
		Whitebox.setInternalState(CDCLTBIForm.class,"DEM161__PatientCityDEM161",DEM161__PatientCityDEM161 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM162",DEM162  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM162__PatientStateDEM162",DEM162__PatientStateDEM162 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM163",DEM163  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM163__PatientZipDEM163",DEM163__PatientZipDEM163 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM165__PatientCountyDEM165",DEM165__PatientCountyDEM165 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM167__PatientCountryDEM167",DEM167__PatientCountryDEM167 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM168",DEM168  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM168__CensusTracDEM168",DEM168__CensusTracDEM168 );
		Whitebox.setInternalState(CDCLTBIForm.class, "City_Limits_CD",City_Limits_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "CityLimitsINV1112",CityLimitsINV1112 );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS006",NBS006  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS006__PatientCellNBS006_UseCd",NBS006__PatientCellNBS006_UseCd  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM177",DEM177  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM177__PatientHmPhDEM177_UseCd",DEM177__PatientHmPhDEM177_UseCd  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS002",NBS002  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV2001",INV2001  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV2001__PatientReportedAge",INV2001__PatientReportedAge  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM115",DEM115  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM115__PatientDOB",DEM115__PatientDOB  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV178_CD",INV178_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV178__PatientPregnantINV178",INV178__PatientPregnantINV178  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS128",NBS128  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS128__PatientPregnantWeeksNBS128",NBS128__PatientPregnantWeeksNBS128  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_AI_CDT",DEM152_AI_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_AI_CDT__PatientRaceDEM152",DEM152_AI_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_A_CDT",DEM152_A_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_A_CDT__PatientRaceDEM152",DEM152_A_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_B_CDT",DEM152_B_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_B_CDT__PatientRaceDEM152",DEM152_B_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_W_CDT",DEM152_W_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_W_CDT__PatientRaceDEM152",DEM152_W_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_O_CDT",DEM152_O_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_O_CDT__PatientRaceDEM152",DEM152_O_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_NH_CDT",DEM152_NH_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_NH_CDT__PatientRaceDEM152",DEM152_NH_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_U_CDT",DEM152_U_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_U_CDT__PatientRaceDEM152",DEM152_U_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_R_CDT",DEM152_R_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_R_CDT__PatientRaceDEM152",DEM152_R_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_D_CDT",DEM152_D_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_D_CDT__PatientRaceDEM152",DEM152_D_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_NA_CDT",DEM152_NA_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM152_NA_CDT__PatientRaceDEM152",DEM152_NA_CDT__PatientRaceDEM152  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM242",DEM242  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM242__AmericanIndianAlaskanNative",DEM242__AmericanIndianAlaskanNative  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM243",DEM243  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM243_Asian",DEM243_Asian );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM244",DEM244  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM244_BlackAfricanAmerican",DEM244_BlackAfricanAmerican );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM245",DEM245  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM245_Hawaiian",DEM245_Hawaiian  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM246",DEM246  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM246_White",DEM246_White  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM155_CDT",DEM155_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM155_CDT__PatientEthnicityDEM155",DEM155_CDT__PatientEthnicityDEM155  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS273_CDT",NBS273_CDT  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS273_CDT__PatientEthnicityUnkownReasonNBS273",NBS273_CDT__PatientEthnicityUnkownReasonNBS273  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM113_CD",DEM113_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM113_CD__PatientCurrentSexDEM113",DEM113_CD__PatientCurrentSexDEM113  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS274_CD",NBS274_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS274_CD__PatientTransgenderNBS274", NBS274_CD__PatientTransgenderNBS274  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS272_CD",NBS272_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS272_CD__PatientSexUnkownReasonNBS272",NBS272_CD__PatientSexUnkownReasonNBS272  );
		Whitebox.setInternalState(CDCLTBIForm.class, "CURRENT_SEX",CURRENT_SEX  );
		Whitebox.setInternalState(CDCLTBIForm.class, "CURRENT_SEX_currentSex",CURRENT_SEX_currentSex  );
		Whitebox.setInternalState(CDCLTBIForm.class, "Employment_Phone",Employment_Phone  );
		Whitebox.setInternalState(CDCLTBIForm.class, "EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003",EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003  );

 		 
	 }
	 
	

	@Test
	public void initializePatientDemographics_test() throws Exception{

		
			System.out.println("******************* Starting test case named: initializePatientDemographics_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializePatientDemographics");
		
			
			int sizeMap = 0;
			int expectedSize = 42;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializePatientDemographics_test *******************");
			
		}		
	
}
	


/**
 * initializeAdministrative_test: this method will test InitializeAdministrative_test. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeAdministrative_test{
	

	private String iteration;
	private int size;
	
 public InitializeAdministrative_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeAdministrative_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);	 
		
		String delimiter1 = "__";
	
		

			String date_reported = "INV111";
			String INV111_date_reported = date_reported+delimiter1+"DateReportedINV111";
			Whitebox.setInternalState(CDCLTBIForm.class, "date_reported", date_reported);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV111_date_reported", INV111_date_reported);

			String mmwr_week = "INV165";
			String INV165_mmwrweek = mmwr_week+delimiter1+"mmwrweekINV165";
			Whitebox.setInternalState(CDCLTBIForm.class, "mmwr_week", mmwr_week);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV165_mmwrweek", INV165_mmwrweek);

			String mmwr_year = "INV166";
			String INV166_mmwryear = mmwr_year+delimiter1+"mmwryearINV166";
			Whitebox.setInternalState(CDCLTBIForm.class, "mmwr_year", mmwr_year);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV166_mmwryear", INV166_mmwryear);

			String case_verification = "INV1115_CDT";
			String INV1115_caseverfication = case_verification+delimiter1+"caseVerificationINV1115";
			Whitebox.setInternalState(CDCLTBIForm.class, "case_verification", case_verification);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1115_caseverfication", INV1115_caseverfication);

			String INV163_CDT = "INV163_CDT";
			String INV163_CDT_casestatus = INV163_CDT+delimiter1+"casestatusINV163";
			Whitebox.setInternalState(CDCLTBIForm.class, "INV163_CDT", INV163_CDT);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV163_CDT_casestatus", INV163_CDT_casestatus);

			String INV173 = "INV173";
			String INV173_casenumber= INV173+delimiter1+"tbStateCaseNumberINV173";	
			Whitebox.setInternalState(CDCLTBIForm.class, "INV173", INV173);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV173_casenumber", INV173_casenumber);

			String INV1109_CD= "INV1109_CD";
			String INV1109_CD_casecounted = INV1109_CD+delimiter1+"caseCountedINV1109_CD";																																														
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1109_CD", INV1109_CD);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1109_CD_casecounted", INV1109_CD_casecounted);

			String INV1111_CDT= "INV1111_CDT";
			String INV1111_CDT_countryofverifiedcase = INV1111_CDT+delimiter1+"countryofverifiedcaseINV1111";																																														
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1111_CDT", INV1111_CDT);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1111_CDT_countryofverifiedcase", INV1111_CDT_countryofverifiedcase);

			String INV1108= "INV1108";
			String INV1108_localcasenumber = INV1108+delimiter1+"localcasenumberINV1108";																																														
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1108", INV1108);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1108_localcasenumber", INV1108_localcasenumber);

			String INV1110= "INV1110";
			String INV1110_previouslyreportedstatecase = INV1110+delimiter1+"previouslyreportedstatecaseINV1110";																																														
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1110", INV1110);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1110_previouslyreportedstatecase", INV1110_previouslyreportedstatecase);
			
			String INV1111_Oth= "INV1111_Oth";
			String INV1111_Oth_othercountryofverifiedcase = INV1111_Oth+delimiter1+"othercountryofverifiedcaseINV1111_Oth";																																														
			

			Whitebox.setInternalState(CDCLTBIForm.class, "INV1111_Oth", INV1111_Oth);
			Whitebox.setInternalState(CDCLTBIForm.class, "INV1111_Oth_othercountryofverifiedcase", INV1111_Oth_othercountryofverifiedcase);

 		 
	 }
	 
	

	@Test
	public void initializeAdministrative_test() throws Exception{
			
		
		
			System.out.println("******************* Starting test case named: initializeAdministrative_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeAdministrative");
		
			
			int sizeMap = 0;
			int expectedSize = 11;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializeAdministrative_test *******************");
			
		}		
	
}
	











/**
 * InitializeInitialEvaluation_test: this method will test InitializeInitialEvaluation. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeInitialEvaluation_test{
	

	private String iteration;
	private int size;
	
 public InitializeInitialEvaluation_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeInitialEvaluation_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		String delimiter1 = "__";
	
		
		

		String DEM126= "DEM126";
		String DEM126_countryofbirth = DEM126+delimiter1+"countryofbirthDEM126";																																										
		String DEM2005= "DEM2005";
		String DEM2005_dateoffirstusarrival = DEM2005+delimiter1+"dateoffirstusarrivalDEM2005";	
		String DEM2003_CD= "DEM2003_CD";
		String DEM2003_CD_eligibleforuscitizenship = DEM2003_CD+delimiter1+"eligibleforuscitizenship_DEM2003_CD";																																														
		String INV1113_CDT= "INV1113_CDT";
		String INV1113_CDT_countriesofbirthforprimaryguardians = INV1113_CDT+delimiter1+"countriesofbirthforprimaryguardians_INV1113";																																														
		String INV501_CDT= "INV501_CDT";
		String INV501_CDT_countryofusualresidency = INV501_CDT+delimiter1+"countryofusualresdency_INV501_CDT";
		String INV1114_CD= "INV1114_CD";
		String INV1114_CD_outsideusfor90daysormore = INV1114_CD+delimiter1+"outsideusfor90daysormore_INV1114_CD";																																														
		String TB101_CD= "TB101_CD";
		String TB101_CD_statusattbdiagnosis = TB101_CD+delimiter1+"statusattbdiagnosis_TB101_CD";																																														
		String INV1116_CD= "INV1116_CD";
		String INV1116_CD_initialreasonevaluatedfortb = INV1116_CD+delimiter1+"initialreasonevaluatedfortb_INV1116_CD";																																														
		String INV1116_OTH= "INV1116_OTH";
		String INV1116_OTH_initialreasonevaluatedfortb = INV1116_OTH+delimiter1+"initialreasonevaluatedfortb_INV1116_OTH";																																														
		String INV1276_CD= "INV1276_CD";
		String INV1276_CD_everworkedas = INV1276_CD+delimiter1+"everworkedas_INV1276_CD";																																														
		String INV1276_C_CD= "INV1276_C_CD";
		String INV1276_C_CD_everworkedas = INV1276_C_CD+delimiter1+"everworkedas_INV1276_C_CD";																																														
		String INV1276_H_CD= "INV1276_H_CD";
		String INV1276_H_CD_everworkedas = INV1276_H_CD+delimiter1+"everworkedas_INV1276_H_CD";																																														
		String INV1276_M_CD= "INV1276_M_CD";
		String INV1276_M_CD_everworkedas = INV1276_M_CD+delimiter1+"everworkedas_INV1276_M_CD";																																														
		String INV1276_U_CD= "INV1276_U_CD";
		String INV1276_U_CD_everworkedas = INV1276_U_CD+delimiter1+"everworkedas_INV1276_U_CD";																																														
		String INV1276_N_CD= "INV1276_N_CD";
		String INV1276_N_CD_everworkedas = INV1276_N_CD+delimiter1+"everworkedas_INV1276_N_CD";																																														
		String code85659_1_R1= "85659_1_R1";
		String code85659_1_R1_currentOccupationStandarized = code85659_1_R1+delimiter1+"currentOccupationStandarized85659_1";																																														
		String code85659_1_R2= "85659_1_R2";
		String code85659_1_R2_currentOccupationStandarized = code85659_1_R2+delimiter1+"currentOccupationStandarized85659_1";																																														
		String code85659_1_R3= "85659_1_R3";
		String code85659_1_R3_currentOccupationStandarized = code85659_1_R3+delimiter1+"currentOccupationStandarized85659_1";																																														
		String code85658_3_R1= "85658_3_R1";
		String code85658_3_R1_currentOccupation = code85658_3_R1+delimiter1+"currentOccupation85658_3";																																														
		String code85658_3_R2= "85658_3_R2";
		String code85658_3_R2_currentOccupation = code85658_3_R2+delimiter1+"currentOccupation85658_3";																																														
		String code85658_3_R3= "85658_3_R3";
		String code85658_3_R3_currentOccupation = code85658_3_R3+delimiter1+"currentOccupation85658_3";																																														
		String code85657_5_R1= "85657_5_R1";
		String code85657_5_R1_currentIndustryStandarized = code85657_5_R1+delimiter1+"currentIndustryStandarized85657_5";																																														
		String code85657_5_R2= "85657_5_R2";
		String code85657_5_R2_currentIndustryStandarized = code85657_5_R2+delimiter1+"currentIndustryStandarized85657_5";																																														
		String code85657_5_R3= "85657_5_R3";
		String code85657_5_R3_currentIndustryStandarized = code85657_5_R3+delimiter1+"currentIndustryStandarized85657_5";																																														
		String code85078_4_R1= "85078_4_R1";
		String code85078_4_R1_currentIndustry = code85078_4_R1+delimiter1+"currentIndustry85078_4";																																														
		String code85078_4_R2= "85078_4_R2";
		String code85078_4_R2_currentIndustry = code85078_4_R2+delimiter1+"currentIndustry85078_4";																																														
		String code85078_4_R3= "85078_4_R3";
		String code85078_4_R3_currentIndustry = code85078_4_R3+delimiter1+"currentIndustry85078_4";																																														
		
		
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM126",DEM126 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM126_countryofbirth",DEM126_countryofbirth  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM2005",DEM2005 );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM2005_dateoffirstusarrival",DEM2005_dateoffirstusarrival  );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM2003_CD",DEM2003_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "DEM2003_CD_eligibleforuscitizenship",DEM2003_CD_eligibleforuscitizenship  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1113_CDT",INV1113_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1113_CDT_countriesofbirthforprimaryguardians",INV1113_CDT_countriesofbirthforprimaryguardians  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV501_CDT",INV501_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV501_CDT_countryofusualresidency",INV501_CDT_countryofusualresidency  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1114_CD",INV1114_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1114_CD_outsideusfor90daysormore",INV1114_CD_outsideusfor90daysormore  );
		Whitebox.setInternalState(CDCLTBIForm.class, "TB101_CD",TB101_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TB101_CD_statusattbdiagnosis",TB101_CD_statusattbdiagnosis  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1116_CD",INV1116_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1116_CD_initialreasonevaluatedfortb",INV1116_CD_initialreasonevaluatedfortb  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1116_OTH",INV1116_OTH );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1116_OTH_initialreasonevaluatedfortb",INV1116_OTH_initialreasonevaluatedfortb  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_CD",INV1276_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_CD_everworkedas",INV1276_CD_everworkedas  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_C_CD",INV1276_C_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_C_CD_everworkedas",INV1276_C_CD_everworkedas  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_H_CD",INV1276_H_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_H_CD_everworkedas",INV1276_H_CD_everworkedas  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_M_CD",INV1276_M_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_M_CD_everworkedas",INV1276_M_CD_everworkedas  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_U_CD",INV1276_U_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_U_CD_everworkedas",INV1276_U_CD_everworkedas  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_N_CD",INV1276_N_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1276_N_CD_everworkedas",INV1276_N_CD_everworkedas  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85659_1_R1",code85659_1_R1 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85659_1_R1_currentOccupationStandarized",code85659_1_R1_currentOccupationStandarized  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85659_1_R2",code85659_1_R2 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85659_1_R2_currentOccupationStandarized",code85659_1_R2_currentOccupationStandarized  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85659_1_R3",code85659_1_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85659_1_R3_currentOccupationStandarized",code85659_1_R3_currentOccupationStandarized  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85658_3_R1",code85658_3_R1 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85658_3_R1_currentOccupation",code85658_3_R1_currentOccupation  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85658_3_R2",code85658_3_R2 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85658_3_R2_currentOccupation",code85658_3_R2_currentOccupation  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85658_3_R3",code85658_3_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85658_3_R3_currentOccupation",code85658_3_R3_currentOccupation  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85657_5_R1",code85657_5_R1 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85657_5_R1_currentIndustryStandarized",code85657_5_R1_currentIndustryStandarized  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85657_5_R2",code85657_5_R2 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85657_5_R2_currentIndustryStandarized",code85657_5_R2_currentIndustryStandarized  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85657_5_R3",code85657_5_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85657_5_R3_currentIndustryStandarized",code85657_5_R3_currentIndustryStandarized  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85078_4_R1",code85078_4_R1 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85078_4_R1_currentIndustry",code85078_4_R1_currentIndustry  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85078_4_R2",code85078_4_R2 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85078_4_R2_currentIndustry",code85078_4_R2_currentIndustry  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85078_4_R3",code85078_4_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "code85078_4_R3_currentIndustry",code85078_4_R3_currentIndustry  );


		
		
		
		
		
		
	 }
	 
	

	@Test
	public void initializeInitialEvaluation_test() throws Exception{
			
		
		
			System.out.println("******************* Starting test case named: initializeInitialEvaluation_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeInitialEvaluation");
		
			
			int sizeMap = 0;
			int expectedSize = 27;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializeInitialEvaluation_test *******************");
			
		}		
	
}
	













/**
 * initializeRisks_test: this method will test initializeRisks. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeRisks_test{
	

	private String iteration;
	private int size;
	
 public InitializeRisks_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeRisks_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		String delimiter1 = "__";

		
		
		
		String ARB016_CD= "ARB016_CD";
		String ARB016_CD_diabeticAtDiagnosisEvaluation = ARB016_CD+delimiter1+"diabeticAtDiagnosisEvaluation_ARB016";																																														
		String TB127_CD= "TB127_CD";
		String TB127_CD_homelessInThePast12Months = TB127_CD+delimiter1+"homelessInThePast12Months_TB127";																																														
		String Code32911000_CD= "32911000_CD";
		String Code32911000_CD_homelessever = Code32911000_CD+delimiter1+"homelessever_32911000";																																														
		String NBS689_CD= "NBS689_CD";
		String NBS689_CD_correctionalFacility = NBS689_CD+delimiter1+"correctionalFacility_NBS689";																																														
		String INV1119_CD= "INV1119_CD";
		String INV1119_CD_typeoffacility = INV1119_CD+delimiter1+"typeoffacility_INV1119_CD";																																														
		String INV1119_OTH= "INV1119_OTH";
		String INV1119_OTH_typeoffacility = INV1119_OTH+delimiter1+"typeoffacility_INV1119_CD";																																														
		String INV649_CD= "INV649_CD";
		String INV649_CD_correctionalfacilityever = INV649_CD+delimiter1+"correctionalfacilityever_INV649_CD";																																														
		String INV636_CD= "INV636_CD";
		String INV636_CD_residentoflongtermcarefacility = INV636_CD+delimiter1+"residentoflongtermcarefacility_INV636_CD";																																														
		String INV1120_CD= "INV1120_CD";
		String INV1120_CD_typeoffacility = INV1120_CD+delimiter1+"typeoffacility_INV1120_CD";																																														
		String INV1120_OTH= "INV1120_OTH";
		String INV1120_OTH_typeoffacility = INV1120_OTH+delimiter1+"typeoffacility_INV1120_OTH";																																														
		String INV607_CD= "INV607_CD";
		String INV607_CD_injectingdruguseinthepast12months = INV607_CD+delimiter1+"injectingdruguseinthepast12months_INV607_CD";																																														
		String INV608_CD= "INV608_CD";
		String INV608_CD_injectingdruguseinthepast12months = INV608_CD+delimiter1+"noninjectingdruguseinthepast12months_INV608_CD";																																														
		String ARB025_CD= "ARB025_CD";
		String ARB025_CD_HeavyAlcoholUseInThePast12Months = ARB025_CD+delimiter1+"HeavyAlcoholUseInThePast12Months_ARB025_CD";																																														
		String PHC690_CD= "PHC690_CD";
		String PHC690_CD_TNFAntagonistTherapy = PHC690_CD+delimiter1+"TNFAntagonistTherapy_PHC690_CD";																																														
		String Code161663000_CD= "161663000_CD";
		String Code161663000_CD_PostOrganTransplantation = Code161663000_CD+delimiter1+"PostOrganTransplantation_161663000_CD";																																														
		String ARB024_CD= "ARB024_CD";
		String ARB024_CD_EndStageRenalDisease = ARB024_CD+delimiter1+"EndStageRenalDisease_ARB024_CD";																																														
		String PHC2236_CD= "PHC2236_CD";
		String PHC2236_CD_ViralHepatitisBorCOnly = PHC2236_CD+delimiter1+"ViralHepatitisBorCOnly_PHC2236_CD";																																														
		String VAR126_CD= "VAR126_CD";
		String VAR126_CD_OtherImmunocompromise = VAR126_CD+delimiter1+"OtherImmunocompromise_VAR126_CD";																																														
		String Code72166_2_CD= "72166_2_CD";
		String Code72166_2_CD_CurrentSmokingStatusAtDiagnosticEvaluation = Code72166_2_CD+delimiter1+"CurrentSmokingStatusAtDiagnosticEvaluation_72166_2_CD";																																														
		String TRAVEL10_CD= "TRAVEL10_CD";
		String TRAVEL10_CD_LivedOutsideOfUSForMoreThan2Months = TRAVEL10_CD+delimiter1+"LivedOutsideOfUSForMoreThan2Months_TRAVEL10_CD";																																														
		String NBS561= "NBS561";
		String NBS561_SpecifyOtherRiskFactor = NBS561+delimiter1+"SpecifyOtherRiskFactor_NBS561";																																														
		String NBS560_CD= "NBS560_CD";
		String NBS560_CD_OtherRiskFactor = NBS560_CD+delimiter1+"OtherRiskFactor_NBS560_CD";
		
		Whitebox.setInternalState(CDCLTBIForm.class, "ARB016_CD",ARB016_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "ARB016_CD_diabeticAtDiagnosisEvaluation",ARB016_CD_diabeticAtDiagnosisEvaluation  );
		Whitebox.setInternalState(CDCLTBIForm.class, "TB127_CD",TB127_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TB127_CD_homelessInThePast12Months",TB127_CD_homelessInThePast12Months  );
		Whitebox.setInternalState(CDCLTBIForm.class, "Code32911000_CD",Code32911000_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "Code32911000_CD_homelessever",Code32911000_CD_homelessever  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS689_CD",NBS689_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS689_CD_correctionalFacility",NBS689_CD_correctionalFacility  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1119_CD",INV1119_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1119_CD_typeoffacility",INV1119_CD_typeoffacility  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1119_OTH",INV1119_OTH );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1119_OTH_typeoffacility",INV1119_OTH_typeoffacility  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV649_CD",INV649_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV649_CD_correctionalfacilityever",INV649_CD_correctionalfacilityever  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV636_CD",INV636_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV636_CD_residentoflongtermcarefacility",INV636_CD_residentoflongtermcarefacility  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1120_CD",INV1120_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1120_CD_typeoffacility",INV1120_CD_typeoffacility  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1120_OTH",INV1120_OTH );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1120_OTH_typeoffacility",INV1120_OTH_typeoffacility  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV607_CD",INV607_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV607_CD_injectingdruguseinthepast12months",INV607_CD_injectingdruguseinthepast12months  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV608_CD",INV608_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV608_CD_injectingdruguseinthepast12months",INV608_CD_injectingdruguseinthepast12months  );
		Whitebox.setInternalState(CDCLTBIForm.class, "ARB025_CD",ARB025_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "ARB025_CD_HeavyAlcoholUseInThePast12Months",ARB025_CD_HeavyAlcoholUseInThePast12Months  );
		Whitebox.setInternalState(CDCLTBIForm.class, "PHC690_CD",PHC690_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "PHC690_CD_TNFAntagonistTherapy",PHC690_CD_TNFAntagonistTherapy  );
		Whitebox.setInternalState(CDCLTBIForm.class, "Code161663000_CD",Code161663000_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "Code161663000_CD_PostOrganTransplantation",Code161663000_CD_PostOrganTransplantation  );
		Whitebox.setInternalState(CDCLTBIForm.class, "ARB024_CD",ARB024_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "ARB024_CD_EndStageRenalDisease",ARB024_CD_EndStageRenalDisease  );
		Whitebox.setInternalState(CDCLTBIForm.class, "PHC2236_CD",PHC2236_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "PHC2236_CD_ViralHepatitisBorCOnly",PHC2236_CD_ViralHepatitisBorCOnly  );
		Whitebox.setInternalState(CDCLTBIForm.class, "VAR126_CD",VAR126_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "VAR126_CD_OtherImmunocompromise",VAR126_CD_OtherImmunocompromise  );
		Whitebox.setInternalState(CDCLTBIForm.class, "Code72166_2_CD",Code72166_2_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "Code72166_2_CD_CurrentSmokingStatusAtDiagnosticEvaluation",Code72166_2_CD_CurrentSmokingStatusAtDiagnosticEvaluation  );
		Whitebox.setInternalState(CDCLTBIForm.class, "TRAVEL10_CD",TRAVEL10_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TRAVEL10_CD_LivedOutsideOfUSForMoreThan2Months",TRAVEL10_CD_LivedOutsideOfUSForMoreThan2Months  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS561",NBS561 );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS561_SpecifyOtherRiskFactor",NBS561_SpecifyOtherRiskFactor  );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS560_CD",NBS560_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS560_CD_OtherRiskFactor",NBS560_CD_OtherRiskFactor  );


	 }
	 
	

	@Test
	public void initializeRisks_test() throws Exception{
			
		
		
			System.out.println("******************* Starting test case named: initializeRisks_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeRisks");
		
			
			int sizeMap = 0;
			int expectedSize = 22;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializeRisks_test *******************");
			
		}		
	
}
	


//
//



/**
 * initializeDiagnosisTesting_test: this method will test initializeDiagnosisTesting_test. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeDiagnosisTesting_test{
	

	private String iteration;
	private int size;
	
 public InitializeDiagnosisTesting_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeDiagnosisTesting_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		String delimiter1 = "__";

		

		//HIV Status
		String NBS859_CD= "NBS859_CD";
		String NBS859_CD_HivStatus = NBS859_CD+delimiter1+"HivStatus_NBS859_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS859_CD",NBS859_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS859_CD_HivStatus",NBS859_CD_HivStatus );
		
		String NBS450= "NBS450";
		String NBS450_Collection_Date = NBS450+delimiter1+"HivStatusCollectionDate_NBS450";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS450", NBS450);
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS450_Collection_Date",NBS450_Collection_Date );
		
		String NBS870= "NBS870";
		String NBS870_Date_Reported = NBS870+delimiter1+"HivStatusDateReported_NBS870";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS870", NBS870);
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS870_Date_Reported",NBS870_Date_Reported );
		
		//Tuberculin
		
		String TUB147_CD= "TUB147_CD";
		String TUB147_CD_Tuberculin = TUB147_CD+delimiter1+"Tuberculin_TUB147_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB147_CD",TUB147_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB147_CD_Tuberculin",TUB147_CD_Tuberculin );
		
		String TUB148= "TUB148";
		String TUB148_DatePlaced = TUB148+delimiter1+"TuberculinDatePlaced_TUB148";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB148", TUB148);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB148_DatePlaced",TUB148_DatePlaced );
		
		String NBS866= "NBS866";
		String NBS866_DateRead = NBS866+delimiter1+"TuberculinDateRead_NBS866";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS866",NBS866 );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS866_DateRead", NBS866_DateRead);
		
		String TUB149= "TUB149";
		String TUB149_MMOfInduration = TUB149+delimiter1+"TuberculinMMOfInduration_TUB149";																																														
		
		
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB149",TUB149 );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB149_MMOfInduration",TUB149_MMOfInduration );
		
		//Interferon
		
		String TUB150_CD= "TUB150_CD";
		String TUB150_CD_Interferon= TUB150_CD+delimiter1+"Interferon_TUB150_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB150_CD",TUB150_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB150_CD_Interferon",TUB150_CD_Interferon );
		
		String NBS868_CD= "NBS868_CD";
		String NBS868_CD_TestType = NBS868_CD+delimiter1+"TestType_NBS868_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS868_CD",NBS868_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS868_CD_TestType",NBS868_CD_TestType );
		
		String TUB151= "TUB151";
		String TUB151_CollectionDate = TUB151+delimiter1+"InterferonCollectionDate_TUB151";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB151",TUB151 );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB151_CollectionDate",TUB151_CollectionDate );
		
		String NBS869= "NBS869";
		String NBS869_DateReported = NBS869+delimiter1+"InterferonDateReported_NBS869";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS869", NBS869);
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS869_DateReported",NBS869_DateReported );
		
		String NBS871= "NBS871";
		String NBS871_QuantitativeResult= NBS871+delimiter1+"InterferonQuantitativeResult_NBS871";	
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS871", NBS871);
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS871_QuantitativeResult", NBS871_QuantitativeResult);
		
		String NBS872_CDT= "NBS872_CDT";
		String NBS872_CDT_QuantitativeResultUnits= NBS872_CDT+delimiter1+"InterferonQuantitativeResultUnits_NBS872_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS872_CDT", NBS872_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS872_CDT_QuantitativeResultUnits",NBS872_CDT_QuantitativeResultUnits );
			
				
		//Sputum Smear
		String TUB120_CD= "TUB120_CD";
		String TUB120_CD_SputumSmear = TUB120_CD+delimiter1+"SputumSmear_TUB120_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB120_CD",TUB120_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB120_CD_SputumSmear", TUB120_CD_SputumSmear);
		
		String TUB121= "TUB121";
		String TUB121_CollectionDate = TUB121+delimiter1+"SputumSmearCollectionDate_TUB121";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB121", TUB121);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB121_CollectionDate",TUB121_CollectionDate );
		
		String NBS863= "NBS863";
		String NBS863_DateReported = NBS863+delimiter1+"DateReported_NBS863";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS863", NBS863);
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS863_DateReported",NBS863_DateReported );
		
		
		//Sputum Culture
		String TUB122_CD= "TUB122_CD";
		String TUB122_CD_SputumCulture = TUB122_CD+delimiter1+"SputumCulture_TUB122_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB122_CD",TUB122_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB122_CD_SputumCulture",TUB122_CD_SputumCulture );
		
		String TUB123= "TUB123";
		String TUB123_CollectionDate = TUB123+delimiter1+"SputumCultureCollectionDate_TUB123";			
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB123", TUB123);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB123_CollectionDate", TUB123_CollectionDate);
		
		String TUB124= "TUB124";
		String TUB124_DateReported = TUB124+delimiter1+"SputumCultureDateReported_TUB124";																																														
		
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB124", TUB124);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB124_DateReported",TUB124_DateReported );
		
		//Smear/Pathology/Cytology
		String TUB126_CD= "TUB126_CD";
		String TUB126_CD_SmearPathology = TUB126_CD+delimiter1+"SmearPathology_TUB126_CD";	
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB126_CD", TUB126_CD);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB126_CD_SmearPathology",TUB126_CD_SmearPathology );
		
		String TUB129_CD= "TUB129_CD";
		String TUB129_CD_TestType = TUB129_CD+delimiter1+"TestType_TUB129_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB129_CD", TUB129_CD);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB129_CD_TestType",TUB129_CD_TestType );
		
		
		String TUB127= "TUB127";
		String TUB127_CollectionDate = TUB127+delimiter1+"SmearPathologyCollectionDate_TUB127";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB127",TUB127 );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB127_CollectionDate",TUB127_CollectionDate );
		
		
		String NBS864= "NBS864";
		String NBS864_DateReported = NBS864+delimiter1+"SmearPathologyDateReported_NBS864";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS864",NBS864 );
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS864_DateReported",NBS864_DateReported );
		
		String TUB128_CDT= "TUB128_CDT";
		String TUB128_CDT_SpecimenSource = TUB128_CDT+delimiter1+"SmearPathologySpecimenSource_TUB128_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB128_CDT", TUB128_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB128_CDT_SpecimenSource", TUB128_CDT_SpecimenSource);
		
		//Culture of tissue
		String TUB130_CD= "TUB130_CD";
		String TUB130_CD_CultureOfTissue = TUB130_CD+delimiter1+"CultureOfTissue_TUB130_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB130_CD", TUB130_CD);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB130_CD_CultureOfTissue",TUB130_CD_CultureOfTissue );
		
		String TUB131= "TUB131";
		String TUB131_CollectionDate = TUB131+delimiter1+"CultureOfTissueCollectionDate_TUB131";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB131", TUB131);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB131_CollectionDate", TUB131_CollectionDate);
		
		String TUB132_CDT= "TUB132_CDT";
		String TUB132_CDT_SpecimenSource = TUB132_CDT+delimiter1+"CultureOfTissueSpecimenSource_TUB132_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB132_CDT",TUB132_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB132_CDT_SpecimenSource", TUB132_CDT_SpecimenSource);
		
		String TUB133= "TUB133";
		String TUB133_DateReported = TUB133+delimiter1+"CultureOfTissueSpecimenSource_TUB133";																																														
		
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB133", TUB133);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB133_DateReported", TUB133_DateReported);
		
		//Nucleic Acid Amplification
		String TUB135_CD= "TUB135_CD";
		String TUB135_CD_NucleicAcid = TUB135_CD+delimiter1+"NucleicAcid_TUB135_CD";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB135_CD",TUB135_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB135_CD_NucleicAcid",TUB135_CD_NucleicAcid );
		
		String TUB136= "TUB136";
		String TUB136_CollectionDate = TUB136+delimiter1+"NucleicAcidCollectionDate_TUB136";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB136", TUB136);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB136_CollectionDate", TUB136_CollectionDate);
		
		String TUB139= "TUB139";
		String TUB139_DateReported = TUB139+delimiter1+"NucleicAcidDateReported_TUB139";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB139", TUB139);
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB139_DateReported", TUB139_DateReported);
		
		String NBS865_CDT= "NBS865_CDT";
		String NBS865_CDT_SpecimenSource = NBS865_CDT+delimiter1+"NucleicAcidSpecimenSource_NBS865_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS865_CDT", NBS865_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "NBS865_CDT_SpecimenSource", NBS865_CDT_SpecimenSource);
		
		//Diagnosis Testing Repeating
		String INV290_R1_CDT = "INV290_R1_CDT";
		String INV290_R1_CDT_TestType = INV290_R1_CDT+delimiter1+"TestType_INV290_R1_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R1_CDT",INV290_R1_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R1_CDT_TestType", INV290_R1_CDT_TestType);
		
		String LAB165_R1_CDT = "LAB165_R1_CDT";
		String LAB165_R1_CDT_SpecimenSource = LAB165_R1_CDT+delimiter1+"SpecimenSource_LAB165_R1_CDT";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R1_CDT", LAB165_R1_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R1_CDT_SpecimenSource", LAB165_R1_CDT_SpecimenSource);
		
		String LAB163_R1 = "LAB163_R1";
		String LAB163_R1_SpecimenSource = LAB163_R1+delimiter1+"DateCollectedPlaced_LAB163_R1";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R1", LAB163_R1);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R1_SpecimenSource", LAB163_R1_SpecimenSource);
		
		String LAB167_R1 = "LAB167_R1";
		String LAB167_R1_DateReported = LAB167_R1+delimiter1+"DateReported_LAB167_R1";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R1", LAB167_R1);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R1_DateReported",LAB167_R1_DateReported );
		
		String INV291_R1_CD = "INV291_R1_CD";
		String INV291_R1_CD_testResultQualitative = INV291_R1_CD+delimiter1+"testResultQualitative_INV291_R1_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R1_CD", INV291_R1_CD);
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R1_CD_testResultQualitative", INV291_R1_CD_testResultQualitative);
		
		String LAB628_R1 = "LAB628_R1";
		String LAB628_R1_QuantitativeResult = LAB628_R1+delimiter1+"QuantitativeResult_LAB628_R1";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R1", LAB628_R1);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R1_QuantitativeResult", LAB628_R1_QuantitativeResult);
		
		String LAB115_R1_CD = "LAB115_R1_CD";
		String LAB115_R1_CD_resultsUnits = LAB115_R1_CD+delimiter1+"resultsUnits_LAB115_R1_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R1_CD", LAB115_R1_CD);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R1_CD_resultsUnits", LAB115_R1_CD_resultsUnits);
		
		//R2
		String INV290_R2_CDT = "INV290_R2_CDT";
		String INV290_R2_CDT_TestType = INV290_R2_CDT+delimiter1+"TestType_INV290_R2_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R2_CDT", INV290_R2_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R2_CDT_TestType", INV290_R2_CDT_TestType);
		
		String LAB165_R2_CDT = "LAB165_R2_CDT";
		String LAB165_R2_CDT_SpecimenSource = LAB165_R2_CDT+delimiter1+"SpecimenSource_LAB165_R2_CDT";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R2_CDT", LAB165_R2_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R2_CDT_SpecimenSource", LAB165_R2_CDT_SpecimenSource);
		
		String LAB163_R2 = "LAB163_R2";
		String LAB163_R2_SpecimenSource = LAB163_R2+delimiter1+"DateCollectedPlaced_LAB163_R2";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R2", LAB163_R2);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R2_SpecimenSource", LAB163_R2_SpecimenSource);
		
		String LAB167_R2 = "LAB167_R2";
		String LAB167_R2_DateReported = LAB167_R2+delimiter1+"DateReported_LAB167_R2";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R2",LAB167_R2 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R2_DateReported", LAB167_R2_DateReported);
		
		String INV291_R2_CD = "INV291_R2_CD";
		String INV291_R2_CD_testResultQualitative = INV291_R2_CD+delimiter1+"testResultQualitative_INV291_R2_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R2_CD", INV291_R2_CD);
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R2_CD_testResultQualitative", INV291_R2_CD_testResultQualitative);
		
		String LAB628_R2 = "LAB628_R2";
		String LAB628_R2_QuantitativeResult = LAB628_R2+delimiter1+"QuantitativeResult_LAB628_R2";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R2", LAB628_R2);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R2_QuantitativeResult", LAB628_R2_QuantitativeResult);
		
		String LAB115_R2_CD = "LAB115_R2_CD";
		String LAB115_R2_CD_resultsUnits = LAB115_R2_CD+delimiter1+"resultsUnits_LAB115_R2_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R2_CD", LAB115_R2_CD);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R2_CD_resultsUnits",LAB115_R2_CD_resultsUnits );
		
		//R3
		String INV290_R3_CDT = "INV290_R3_CDT";
		String INV290_R3_CDT_TestType = INV290_R3_CDT+delimiter1+"TestType_INV290_R3_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R3_CDT",INV290_R3_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R3_CDT_TestType",INV290_R3_CDT_TestType );
		
		String LAB165_R3_CDT = "LAB165_R3_CDT";
		String LAB165_R3_CDT_SpecimenSource = LAB165_R3_CDT+delimiter1+"SpecimenSource_LAB165_R3_CDT";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R3_CDT", LAB165_R3_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R3_CDT_SpecimenSource", LAB165_R3_CDT_SpecimenSource);
		
		String LAB163_R3 = "LAB163_R3";
		String LAB163_R3_SpecimenSource = LAB163_R3+delimiter1+"DateCollectedPlaced_LAB163_R3";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R3", LAB163_R3);
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R3_SpecimenSource", LAB163_R3_SpecimenSource);
		
		String LAB167_R3 = "LAB167_R3";
		String LAB167_R3_DateReported = LAB167_R3+delimiter1+"DateReported_LAB167_R3";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R3",LAB167_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R3_DateReported",LAB167_R3_DateReported );
		
		String INV291_R3_CD = "INV291_R3_CD";
		String INV291_R3_CD_testResultQualitative = INV291_R3_CD+delimiter1+"testResultQualitative_INV291_R3_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R3_CD",INV291_R3_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R3_CD_testResultQualitative",INV291_R3_CD_testResultQualitative );
		
		String LAB628_R3 = "LAB628_R3";
		String LAB628_R3_QuantitativeResult = LAB628_R3+delimiter1+"QuantitativeResult_LAB628_R3";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R3",LAB628_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R3_QuantitativeResult",LAB628_R3_QuantitativeResult );
		
		String LAB115_R3_CD = "LAB115_R3_CD";
		String LAB115_R3_CD_resultsUnits = LAB115_R3_CD+delimiter1+"resultsUnits_LAB115_R3_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R3_CD",LAB115_R3_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R3_CD_resultsUnits",LAB115_R3_CD_resultsUnits );
		
		//R4
		String INV290_R4_CDT = "INV290_R4_CDT";
		String INV290_R4_CDT_TestType = INV290_R4_CDT+delimiter1+"TestType_INV290_R4_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R4_CDT", INV290_R4_CDT);
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R4_CDT_TestType",INV290_R4_CDT_TestType );
		
		String LAB165_R4_CDT = "LAB165_R4_CDT";
		String LAB165_R4_CDT_SpecimenSource = LAB165_R4_CDT+delimiter1+"SpecimenSource_LAB165_R4_CDT";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R4_CDT",LAB165_R4_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R4_CDT_SpecimenSource",LAB165_R4_CDT_SpecimenSource );
		
		String LAB163_R4 = "LAB163_R4";
		String LAB163_R4_SpecimenSource = LAB163_R4+delimiter1+"DateCollectedPlaced_LAB163_R4";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R4",LAB163_R4 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R4_SpecimenSource",LAB163_R4_SpecimenSource );
		
		String LAB167_R4 = "LAB167_R4";
		String LAB167_R4_DateReported = LAB167_R4+delimiter1+"DateReported_LAB167_R4";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R4",LAB167_R4 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R4_DateReported",LAB167_R4_DateReported );
		
		String INV291_R4_CD = "INV291_R4_CD";
		String INV291_R4_CD_testResultQualitative = INV291_R4_CD+delimiter1+"testResultQualitative_INV291_R4_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R4_CD",INV291_R4_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R4_CD_testResultQualitative",INV291_R4_CD_testResultQualitative );
		
		String LAB628_R4 = "LAB628_R4";
		String LAB628_R4_QuantitativeResult = LAB628_R4+delimiter1+"QuantitativeResult_LAB628_R4";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R4",LAB628_R4 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R4_QuantitativeResult",LAB628_R4_QuantitativeResult );
		
		String LAB115_R4_CD = "LAB115_R4_CD";
		String LAB115_R4_CD_resultsUnits = LAB115_R4_CD+delimiter1+"resultsUnits_LAB115_R4_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R4_CD",LAB115_R4_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R4_CD_resultsUnits",LAB115_R4_CD_resultsUnits );
		
		//R5
		String INV290_R5_CDT = "INV290_R5_CDT";
		String INV290_R5_CDT_TestType = INV290_R5_CDT+delimiter1+"TestType_INV290_R5_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R5_CDT",INV290_R5_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R5_CDT_TestType",INV290_R5_CDT_TestType );
		
		String LAB165_R5_CDT = "LAB165_R5_CDT";
		String LAB165_R5_CDT_SpecimenSource = LAB165_R5_CDT+delimiter1+"SpecimenSource_LAB165_R5_CDT";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R5_CDT",LAB165_R5_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R5_CDT_SpecimenSource",LAB165_R5_CDT_SpecimenSource );
		
		String LAB163_R5 = "LAB163_R5";
		String LAB163_R5_SpecimenSource = LAB163_R5+delimiter1+"DateCollectedPlaced_LAB163_R5";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R5",LAB163_R5 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R5_SpecimenSource",LAB163_R5_SpecimenSource );
		
		String LAB167_R5 = "LAB167_R5";
		String LAB167_R5_DateReported = LAB167_R5+delimiter1+"DateReported_LAB167_R5";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R5",LAB167_R5 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R5_DateReported",LAB167_R5_DateReported );
		
		String INV291_R5_CD = "INV291_R5_CD";
		String INV291_R5_CD_testResultQualitative = INV291_R5_CD+delimiter1+"testResultQualitative_INV291_R5_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R5_CD",INV291_R5_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R5_CD_testResultQualitative",INV291_R5_CD_testResultQualitative );
		
		String LAB628_R5 = "LAB628_R5";
		String LAB628_R5_QuantitativeResult = LAB628_R5+delimiter1+"QuantitativeResult_LAB628_R5";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R5",LAB628_R5 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R5_QuantitativeResult", LAB628_R5_QuantitativeResult);
		
		String LAB115_R5_CD = "LAB115_R5_CD";
		String LAB115_R5_CD_resultsUnits = LAB115_R5_CD+delimiter1+"resultsUnits_LAB115_R5_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R5_CD",LAB115_R5_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R5_CD_resultsUnits",LAB115_R5_CD_resultsUnits );
		
		//R6
		String INV290_R6_CDT = "INV290_R6_CDT";
		String INV290_R6_CDT_TestType = INV290_R6_CDT+delimiter1+"TestType_INV290_R6_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R6_CDT",INV290_R6_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R6_CDT_TestType",INV290_R6_CDT_TestType );
		
		String LAB165_R6_CDT = "LAB165_R6_CDT";
		String LAB165_R6_CDT_SpecimenSource = LAB165_R6_CDT+delimiter1+"SpecimenSource_LAB165_R6_CDT";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R6_CDT",LAB165_R6_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R6_CDT_SpecimenSource",LAB165_R6_CDT_SpecimenSource );
		
		String LAB163_R6 = "LAB163_R6";
		String LAB163_R6_SpecimenSource = LAB163_R6+delimiter1+"DateCollectedPlaced_LAB163_R6";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R6",LAB163_R6 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R6_SpecimenSource",LAB163_R6_SpecimenSource );
		
		String LAB167_R6 = "LAB167_R6";
		String LAB167_R6_DateReported = LAB167_R6+delimiter1+"DateReported_LAB167_R6";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R6",LAB167_R6 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R6_DateReported",LAB167_R6_DateReported );
		
		String INV291_R6_CD = "INV291_R6_CD";
		String INV291_R6_CD_testResultQualitative = INV291_R6_CD+delimiter1+"testResultQualitative_INV291_R6_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R6_CD",INV291_R6_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R6_CD_testResultQualitative",INV291_R6_CD_testResultQualitative );
		
		String LAB628_R6 = "LAB628_R6";
		String LAB628_R6_QuantitativeResult = LAB628_R6+delimiter1+"QuantitativeResult_LAB628_R6";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R6",LAB628_R6 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R6_QuantitativeResult",LAB628_R6_QuantitativeResult );
		
		String LAB115_R6_CD = "LAB115_R6_CD";
		String LAB115_R6_CD_resultsUnits = LAB115_R6_CD+delimiter1+"resultsUnits_LAB115_R6_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R6_CD",LAB115_R6_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R6_CD_resultsUnits",LAB115_R6_CD_resultsUnits );
		
		//R7
		String INV290_R7_CDT = "INV290_R7_CDT";
		String INV290_R7_CDT_TestType = INV290_R7_CDT+delimiter1+"TestType_INV290_R7_CDT";																																														
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R7_CDT",INV290_R7_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV290_R7_CDT_TestType",INV290_R7_CDT_TestType );
		
		String LAB165_R7_CDT = "LAB165_R7_CDT";
		String LAB165_R7_CDT_SpecimenSource = LAB165_R7_CDT+delimiter1+"SpecimenSource_LAB165_R7_CDT";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R7_CDT",LAB165_R7_CDT );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB165_R7_CDT_SpecimenSource",LAB165_R7_CDT_SpecimenSource );
		
		String LAB163_R7 = "LAB163_R7";
		String LAB163_R7_SpecimenSource = LAB163_R7+delimiter1+"DateCollectedPlaced_LAB163_R7";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R7",LAB163_R7 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB163_R7_SpecimenSource", LAB163_R7_SpecimenSource);
		
		String LAB167_R7 = "LAB167_R7";
		String LAB167_R7_DateReported = LAB167_R7+delimiter1+"DateReported_LAB167_R7";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R7",LAB167_R7 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB167_R7_DateReported", LAB167_R7_DateReported);
		
		String INV291_R7_CD = "INV291_R7_CD";
		String INV291_R7_CD_testResultQualitative = INV291_R7_CD+delimiter1+"testResultQualitative_INV291_R7_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R7_CD",INV291_R7_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV291_R7_CD_testResultQualitative",INV291_R7_CD_testResultQualitative );
		
		String LAB628_R7 = "LAB628_R7";
		String LAB628_R7_QuantitativeResult = LAB628_R7+delimiter1+"QuantitativeResult_LAB628_R7";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R7",LAB628_R7 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB628_R7_QuantitativeResult",LAB628_R7_QuantitativeResult );
		
		String LAB115_R7_CD = "LAB115_R7_CD";
		String LAB115_R7_CD_resultsUnits = LAB115_R7_CD+delimiter1+"resultsUnits_LAB115_R7_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R7_CD",LAB115_R7_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB115_R7_CD_resultsUnits",LAB115_R7_CD_resultsUnits );
		

	 }
	 
	

	@Test
	public void initializeDiagnosisTesting_test() throws Exception{
			
		
		
			System.out.println("******************* Starting test case named: initializeDiagnosisTesting_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeDiagnosisTesting");
		
			
			int sizeMap = 0;
			int expectedSize = 81;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializeDiagnosisTesting_test *******************");
			
		}		
	
}
	




/**
 * InitializeChest_test: this method will test InitializeChest. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeChest_test{
	

	private String iteration;
	private int size;
	
 public InitializeChest_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeChest_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		String delimiter1 = "__";


		
		
		

		String TUB141_CD = "TUB141_CD";
		String TUB141_CD_InitialChestXRay = TUB141_CD+delimiter1+"initialChestXRay_TUB141_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB141_CD",TUB141_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB141_CD_InitialChestXRay",TUB141_CD_InitialChestXRay );
		
		String LAB681A = "LAB681A";
		String LAB681A_XRayDate = LAB681A+delimiter1+"XRayDate_LAB681A";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681A",LAB681A );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681A_XRayDate",LAB681A_XRayDate );
		
		String TUB142_CD = "TUB142_CD";
		String TUB142_CD_evidenceOfCavityXRay = TUB142_CD+delimiter1+"evidenceOfCavityXRay_TUB142_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB142_CD",TUB142_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB142_CD_evidenceOfCavityXRay",TUB142_CD_evidenceOfCavityXRay );
		
		String TUB143_CD = "TUB143_CD";
		String TUB143_CD_evidenceOfMiliaryTBXRay = TUB143_CD+delimiter1+"evidenceOfMiliaryTBXRay_TUB143_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB143_CD",TUB143_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB143_CD_evidenceOfMiliaryTBXRay",TUB143_CD_evidenceOfMiliaryTBXRay );
		
		String TUB144_CD = "TUB144_CD";
		String TUB144_CD_InitialChestCTScan = TUB144_CD+delimiter1+"initialChestCTScan_TUB144_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB144_CD",TUB144_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB144_CD_InitialChestCTScan",TUB144_CD_InitialChestCTScan );
		
		String LAB681B = "LAB681B";
		String LAB681B_CTScanDate = LAB681B+delimiter1+"CTScanDate_LAB681B";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681B",LAB681B );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681B_CTScanDate",LAB681B_CTScanDate );
		
		String TUB145_CD = "TUB145_CD";
		String TUB145_CD_evidenceOfCavityCTScan = TUB145_CD+delimiter1+"evidenceOfCavityCTScan_TUB145_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB145_CD",TUB145_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB145_CD_evidenceOfCavityCTScan",TUB145_CD_evidenceOfCavityCTScan );
		
		String TUB146_CD = "TUB146_CD";
		String TUB146_CD_evidenceOfMiliaryTBCTScan = TUB146_CD+delimiter1+"evidenceOfMiliaryTBCTScan_TUB146_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB146_CD",TUB146_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "TUB146_CD_evidenceOfMiliaryTBCTScan",TUB146_CD_evidenceOfMiliaryTBCTScan );
		
		
		//Chest repeating
		String LAB677_R1_CD = "LAB677_R1_CD";
		String LAB677_R1_CD_ChestTestType = LAB677_R1_CD+delimiter1+"chestTestType_LAB677_R1_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R1_CD",LAB677_R1_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R1_CD",LAB677_R1_CD );
		
		String LAB677_R1_OTH = "LAB677_R1_OTH";
		String LAB677_R1_OTH_ChestTestType = LAB677_R1_OTH+delimiter1+"chestTestType_LAB677_R1_OTH";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R1_OTH",LAB677_R1_OTH );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R1_OTH_ChestTestType",LAB677_R1_OTH_ChestTestType );
		
		String LAB681_R1 = "LAB681_R1";
		String LAB681_R1_ChestDateOfStudy = LAB681_R1+delimiter1+"chestDateOfStudy_LAB681_R1";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681_R1",LAB681_R1 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681_R1_ChestDateOfStudy",LAB681_R1_ChestDateOfStudy );
		
		String LAB678_R1_CD = "LAB678_R1_CD";
		String LAB678_R1_CD_ChestResultOfStudy = LAB678_R1_CD+delimiter1+"chestResultOfStudy_LAB678_R1_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB678_R1_CD",LAB678_R1_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB678_R1_CD_ChestResultOfStudy",LAB678_R1_CD_ChestResultOfStudy );
		
		String LAB679_R1_CD = "LAB679_R1_CD";
		String LAB679_R1_CD_ChestEvidenceOfCavity = LAB679_R1_CD+delimiter1+"chestEvidenceOfCavity_LAB679_R1_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB679_R1_CD",LAB679_R1_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB679_R1_CD_ChestEvidenceOfCavity",LAB679_R1_CD_ChestEvidenceOfCavity );
		
		String LAB680_R1_CD = "LAB680_R1_CD";
		String LAB680_R1_CD_chestEvidenceOfMiliary = LAB680_R1_CD+delimiter1+"chestEvidenceOfMiliary_LAB680_R1_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB680_R1_CD",LAB680_R1_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB680_R1_CD_chestEvidenceOfMiliary",LAB680_R1_CD_chestEvidenceOfMiliary );
		
		String LAB677_R2_CD = "LAB677_R2_CD";
		String LAB677_R2_CD_ChestTestType = LAB677_R2_CD+delimiter1+"chestTestType_LAB677_R2_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R2_CD",LAB677_R2_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R2_CD_ChestTestType",LAB677_R2_CD_ChestTestType );
		
		String LAB677_R2_OTH = "LAB677_R2_OTH";
		String LAB677_R2_OTH_ChestTestType = LAB677_R2_OTH+delimiter1+"chestTestType_LAB677_R2_OTH";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R2_OTH",LAB677_R2_OTH );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R2_OTH_ChestTestType",LAB677_R2_OTH_ChestTestType );
		
		String LAB681_R2 = "LAB681_R2";
		String LAB681_R2_ChestDateOfStudy = LAB681_R2+delimiter1+"chestDateOfStudy_LAB681_R2";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681_R2",LAB681_R2 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681_R2_ChestDateOfStudy",LAB681_R2_ChestDateOfStudy );
		
		String LAB678_R2_CD = "LAB678_R2_CD";
		String LAB678_R2_CD_ChestResultOfStudy = LAB678_R2_CD+delimiter1+"chestResultOfStudy_LAB678_R2_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB678_R2_CD",LAB678_R2_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB678_R2_CD_ChestResultOfStudy",LAB678_R2_CD_ChestResultOfStudy );
		
		String LAB679_R2_CD = "LAB679_R2_CD";
		String LAB679_R2_CD_ChestEvidenceOfCavity = LAB679_R2_CD+delimiter1+"chestEvidenceOfCavity_LAB679_R2_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB679_R2_CD",LAB679_R2_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB679_R2_CD_ChestEvidenceOfCavity",LAB679_R2_CD_ChestEvidenceOfCavity );
		
		String LAB680_R2_CD = "LAB680_R2_CD";
		String LAB680_R2_CD_chestEvidenceOfMiliary = LAB680_R2_CD+delimiter1+"chestEvidenceOfMiliary_LAB680_R2_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB680_R2_CD",LAB680_R2_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB680_R2_CD_chestEvidenceOfMiliary",LAB680_R2_CD_chestEvidenceOfMiliary );
		
		String LAB677_R3_CD = "LAB677_R3_CD";
		String LAB677_R3_CD_ChestTestType = LAB677_R3_CD+delimiter1+"chestTestType_LAB677_R3_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R3_CD",LAB677_R3_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R3_CD_ChestTestType",LAB677_R3_CD_ChestTestType );
		
		String LAB677_R3_OTH = "LAB677_R3_OTH";
		String LAB677_R3_OTH_ChestTestType = LAB677_R3_OTH+delimiter1+"chestTestType_LAB677_R3_OTH";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R3_OTH",LAB677_R3_OTH );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB677_R3_OTH_ChestTestType",LAB677_R3_OTH_ChestTestType );
		
		String LAB681_R3 = "LAB681_R3";
		String LAB681_R3_ChestDateOfStudy = LAB681_R3+delimiter1+"chestDateOfStudy_LAB681_R3";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681_R3",LAB681_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB681_R3_ChestDateOfStudy",LAB681_R3_ChestDateOfStudy );
		
		String LAB678_R3_CD = "LAB678_R3_CD";
		String LAB678_R3_CD_ChestResultOfStudy = LAB678_R3_CD+delimiter1+"chestResultOfStudy_LAB678_R3_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB678_R3_CD",LAB678_R3_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB678_R3_CD_ChestResultOfStudy",LAB678_R3_CD_ChestResultOfStudy );
		
		String LAB679_R3_CD = "LAB679_R3_CD";
		String LAB679_R3_CD_ChestEvidenceOfCavity = LAB679_R3_CD+delimiter1+"chestEvidenceOfCavity_LAB679_R3_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB679_R3_CD",LAB679_R3_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB679_R3_CD_ChestEvidenceOfCavity",LAB679_R3_CD_ChestEvidenceOfCavity );
		
		String LAB680_R3_CD = "LAB680_R3_CD";
		String LAB680_R3_CD_chestEvidenceOfMiliary = LAB680_R3_CD+delimiter1+"chestEvidenceOfMiliary_LAB680_R3_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB680_R3_CD",LAB680_R3_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "LAB680_R3_CD_chestEvidenceOfMiliary",LAB680_R3_CD_chestEvidenceOfMiliary );
		
		

		
	 }
	 
	

	@Test
	public void initializeChest_test() throws Exception{
			
		
		
			System.out.println("******************* Starting test case named: initializeChest_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeChest");
		
			
			int sizeMap = 0;
			int expectedSize = 26;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializeChest_test *******************");
			
		}	
	
	
}














/**
 * initializeEpidemiologic_test: this method will test initializeEpidemiologic_test. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeEpidemiologic_test{
	

	private String iteration;
	private int size;
	
 public InitializeEpidemiologic_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeEpidemiologic_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		String delimiter1 = "__";

		
		
		

		
		String INV1274_CD = "INV1274_CD";
		String INV1274_CD_caseMeetBinational = INV1274_CD+delimiter1+"caseMeetBinational_INV1274_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1274_CD",INV1274_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1274_CD_caseMeetBinational",INV1274_CD_caseMeetBinational );
		
		String INV515_CD = "INV515_CD";
		String INV515_CD_whichCriteriaWereMet = INV515_CD+delimiter1+"whichCriteriaWereMet_INV515_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_CD",INV515_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_CD_whichCriteriaWereMet",INV515_CD_whichCriteriaWereMet );
		
		
		String INV515_S_CD = "INV515_S_CD";
		String INV515_S_CD_whichCriteriaWereMet = INV515_S_CD+delimiter1+"whichCriteriaWereMet_INV515_S_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_S_CD",INV515_S_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_S_CD_whichCriteriaWereMet",INV515_S_CD_whichCriteriaWereMet );
		
		
		String INV515_C_CD = "INV515_C_CD";
		String INV515_C_CD_whichCriteriaWereMet = INV515_C_CD+delimiter1+"whichCriteriaWereMet_INV515_C_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_C_CD",INV515_C_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_C_CD_whichCriteriaWereMet",INV515_C_CD_whichCriteriaWereMet );
		
		
		String INV515_R_CD = "INV515_R_CD";
		String INV515_R_CD_whichCriteriaWereMet = INV515_R_CD+delimiter1+"whichCriteriaWereMet_INV515_R_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_R_CD",INV515_R_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_R_CD_whichCriteriaWereMet",INV515_R_CD_whichCriteriaWereMet );
		
		
		String INV515_W_CD = "INV515_W_CD";
		String INV515_W_CD_whichCriteriaWereMet = INV515_W_CD+delimiter1+"whichCriteriaWereMet_INV515_W_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_W_CD",INV515_W_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_W_CD_whichCriteriaWereMet",INV515_W_CD_whichCriteriaWereMet );
		
		
		String INV515_B_CD = "INV515_B_CD";
		String INV515_B_CD_whichCriteriaWereMet = INV515_B_CD+delimiter1+"whichCriteriaWereMet_INV515_B_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_B_CD",INV515_B_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_B_CD_whichCriteriaWereMet",INV515_B_CD_whichCriteriaWereMet );
		
		
		String INV515_O_CD = "INV515_O_CD";
		String INV515_O_CD_whichCriteriaWereMet = INV515_O_CD+delimiter1+"whichCriteriaWereMet_INV515_O_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_O_CD",INV515_O_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV515_O_CD_whichCriteriaWereMet",INV515_O_CD_whichCriteriaWereMet );
		
		

		String INV1122_CD = "INV1122_CD";
		String INV1122_CD_caseIdentifiedDuringTheContactInvestigation = INV1122_CD+delimiter1+"caseIdentifiedDuringTheContactInvestigation_INV1122_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1122_CD",INV1122_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1122_CD_caseIdentifiedDuringTheContactInvestigation",INV1122_CD_caseIdentifiedDuringTheContactInvestigation );
		
		

		String INV1123_CD = "INV1123_CD";
		String INV1123_CD_evaluatedForTB = INV1123_CD+delimiter1+"evaluatedForTB_INV1123_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1123_CD",INV1123_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1123_CD_evaluatedForTB",INV1123_CD_evaluatedForTB );
		
		

		String INV1134_CD = "INV1134_CD";
		String INV1134_CD_contactInvestigationConducted = INV1134_CD+delimiter1+"contactInvestigationConducted_INV1134_CD";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1134_CD",INV1134_CD );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1134_CD_contactInvestigationConducted",INV1134_CD_contactInvestigationConducted );
		
		

		String INV1124_R1 = "INV1124_R1";
		String INV1124_R1_linkedStateCaseNumber = INV1124_R1+delimiter1+"linkedStateCaseNumber_INV1124_R1";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R1",INV1124_R1 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R1_linkedStateCaseNumber",INV1124_R1_linkedStateCaseNumber );
		
		

		String INV1124_R2 = "INV1124_R2";
		String INV1124_R2_linkedStateCaseNumber = INV1124_R2+delimiter1+"linkedStateCaseNumber_INV1124_R2";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R2",INV1124_R2 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R2_linkedStateCaseNumber",INV1124_R2_linkedStateCaseNumber );
		
		

		String INV1124_R3 = "INV1124_R3";
		String INV1124_R3_linkedStateCaseNumber = INV1124_R3+delimiter1+"linkedStateCaseNumber_INV1124_R3";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R3",INV1124_R3 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R3_linkedStateCaseNumber",INV1124_R3_linkedStateCaseNumber );
		
		

		String INV1124_R4 = "INV1124_R4";
		String INV1124_R4_linkedStateCaseNumber = INV1124_R4+delimiter1+"linkedStateCaseNumber_INV1124_R4";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R4",INV1124_R4 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R4_linkedStateCaseNumber",INV1124_R4_linkedStateCaseNumber );
		
		

		String INV1124_R5 = "INV1124_R5";
		String INV1124_R5_linkedStateCaseNumber = INV1124_R5+delimiter1+"linkedStateCaseNumber_INV1124_R5";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R5",INV1124_R5 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R5_linkedStateCaseNumber",INV1124_R5_linkedStateCaseNumber );
		
		

		String INV1124_R6 = "INV1124_R6";
		String INV1124_R6_linkedStateCaseNumber = INV1124_R6+delimiter1+"linkedStateCaseNumber_INV1124_R6";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R6",INV1124_R6 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R6_linkedStateCaseNumber",INV1124_R6_linkedStateCaseNumber );
		
		

		String INV1124_R7 = "INV1124_R7";
		String INV1124_R7_linkedStateCaseNumber = INV1124_R7+delimiter1+"linkedStateCaseNumber_INV1124_R7";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R7",INV1124_R7 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R7_linkedStateCaseNumber",INV1124_R7_linkedStateCaseNumber );
		
		

		String INV1124_R8 = "INV1124_R8";
		String INV1124_R8_linkedStateCaseNumber = INV1124_R8+delimiter1+"linkedStateCaseNumber_INV1124_R8";
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R8",INV1124_R8 );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1124_R8_linkedStateCaseNumber",INV1124_R8_linkedStateCaseNumber );
		
		

		
		
		
		
		
		
	 }
	 
	

	@Test
	public void initializeEpidemiologic_test() throws Exception{
			
		
		
			System.out.println("******************* Starting test case named: initializeEpidemiologic_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeEpidemiologic");
		
			
			int sizeMap = 0;
			int expectedSize = 19;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializeEpidemiologic_test *******************");
			
		}	
	
	
}





/**
 * initializeClinicalHistory_test: this method will test initializeClinicalHistory_test. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
 * 
 * @author Fatima.Lopezcalzado
 *
 */
@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class InitializeTreatmentAndOutcome_test{
	

	private String iteration;
	private int size;
	
 public InitializeTreatmentAndOutcome_test(String it, int size){
	 
	 super();
	 //Common Parameters
	// this.type = type;
	 this.iteration = it;
	 this.size = size;
	
 }

 
   @Parameterized.Parameters
   public static Collection input() {
	   /*Parameters for validateRule1_test*/
	   
	   int it = 0;
	   
	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
	   //The areas are the lines where the new method escapeCharacterSequence method is called
      return Arrays.asList(new Object[][]{
    		  
    		    
    		  {"initializeClinicalHistory_test"+"_"+it++,0}
    		  
    		  
    		 
	  });
   }
 
 
 
/*
 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
 * */

   
    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
   

	@Mock
	PropertyUtil propertyUtilMocked;
	
	@Mock
	LogUtils loggerMock;
	
	@Spy
	@InjectMocks
	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());

	 @Before
	 public void initMocks() throws Exception {

	
		 PowerMockito.spy(CDCLTBIForm.class);
		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
		 
		 loggerMock = Mockito.mock(LogUtils.class);

		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
		 
		
		String delimiter1 = "__";

		

		String INV1128 = "INV1128";
		String INV1128_LTBITherapyStarted = INV1128+delimiter1+"LTBITherapyStarted_INV1128";
		String INV924 = "INV924";
		String INV924_TreatmentStartDate = INV924+delimiter1+"TreatmentStartDate_INV924";
		String code63939_3 = "63939_3";
		String code63939_3_DateTherapyStopped = code63939_3+delimiter1+"DateTherapyStopped_63939_3";
		String INV1129_CD = "INV1129_CD";
		String INV1129_CD_SpecifyInitialRegimen= INV1129_CD+delimiter1+"SpecifyInitialRegimen_INV1129_CD";
		String INV1129_OTH = "INV1129_OTH";
		String INV1129_OTH_SpecifyInitialRegimenOther= INV1129_OTH+delimiter1+"SpecifyInitialRegimenOther_INV1129_OTH";
		String INV1130_CD = "INV1130_CD";
		String INV1130_CD_WhyLTBITreatmentWasNotStarted= INV1130_CD+delimiter1+"WhyLTBITreatmentWasNotStarted_INV1130_CD";
		String INV1130_OTH = "INV1130_OTH";
		String INV1130_OTH_WhyLTBITreatmentWasNotStartedOther= INV1130_OTH+delimiter1+"WhyLTBITreatmentWasNotStartedOther_INV1130_OTH";
		String code55753_8B_CD = "55753_8B_CD";
		String code55753_8B_CD_treatmentAdministration= code55753_8B_CD+delimiter1+"treatmentAdministration_55753_8B_CD";
		String code55753_8B_D_CD = "55753_8B_D_CD";
		String code55753_8B_D_CD_treatmentAdministration= code55753_8B_D_CD+delimiter1+"treatmentAdministration_55753_8B_D_CD";
		String code55753_8B_E_CD = "55753_8B_E_CD";
		String code55753_8B_E_CD_treatmentAdministration= code55753_8B_E_CD+delimiter1+"treatmentAdministration_55753_8B_E_CD";
		String code55753_8B_S_CD = "55753_8B_S_CD";
		String code55753_8B_S_CD_treatmentAdministration= code55753_8B_S_CD+delimiter1+"treatmentAdministration_55753_8B_S_CD";
		String INV1131_CD = "INV1131_CD";
		String INV1131_CD_ReasonTherapyStopped= INV1131_CD+delimiter1+"ReasonTherapyStopped_INV1131_CD";
		String INV1131_OTH = "INV1131_OTH";
		String INV1131_OTH_ReasonTherapyStoppedOther= INV1131_OTH+delimiter1+"ReasonTherapyStoppedOther_INV1131_OTH";
		String INV1132 = "INV1132";
		String INV1132_NTSSCaseNumber= INV1132+delimiter1+"NTSSCaseNumber_INV1132";
		String code64750_3_CD = "64750_3_CD";
		String code64750_3_CD_SevereAdverseEvent= code64750_3_CD+delimiter1+"SevereAdverseEvent_64750_3_CD";
		String code64750_3_D_CD = "64750_3_D_CD";
		String code64750_3_D_CD_SevereAdverseEvent= code64750_3_D_CD+delimiter1+"SevereAdverseEvent_64750_3_D_CD";
		String code64750_3_H_CD = "64750_3_H_CD";
		String code64750_3_H_CD_SevereAdverseEvent= code64750_3_H_CD+delimiter1+"SevereAdverseEvent_64750_3_H_CD";
		String INV167 = "INV167";
		String INV167_InvestigationComments = INV167+delimiter1+"InvestigationComments_INV167";
		
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1128",INV1128  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1128_LTBITherapyStarted",INV1128_LTBITherapyStarted  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV924",INV924  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV924_TreatmentStartDate",INV924_TreatmentStartDate  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code63939_3",code63939_3  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code63939_3_DateTherapyStopped",code63939_3_DateTherapyStopped  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1129_CD",INV1129_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1129_CD_SpecifyInitialRegimen",INV1129_CD_SpecifyInitialRegimen );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1129_OTH",INV1129_OTH  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1129_OTH_SpecifyInitialRegimenOther",INV1129_OTH_SpecifyInitialRegimenOther );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1130_CD",INV1130_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1130_CD_WhyLTBITreatmentWasNotStarted",INV1130_CD_WhyLTBITreatmentWasNotStarted );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1130_OTH",INV1130_OTH  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1130_OTH_WhyLTBITreatmentWasNotStartedOther",INV1130_OTH_WhyLTBITreatmentWasNotStartedOther );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_CD",code55753_8B_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_CD_treatmentAdministration",code55753_8B_CD_treatmentAdministration );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_D_CD",code55753_8B_D_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_D_CD_treatmentAdministration",code55753_8B_D_CD_treatmentAdministration );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_E_CD",code55753_8B_E_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_E_CD_treatmentAdministration",code55753_8B_E_CD_treatmentAdministration );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_S_CD",code55753_8B_S_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8B_S_CD_treatmentAdministration",code55753_8B_S_CD_treatmentAdministration );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1131_CD",INV1131_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1131_CD_ReasonTherapyStopped",INV1131_CD_ReasonTherapyStopped );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1131_OTH",INV1131_OTH  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1131_OTH_ReasonTherapyStoppedOther",INV1131_OTH_ReasonTherapyStoppedOther );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1132",INV1132  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV1132_NTSSCaseNumber",INV1132_NTSSCaseNumber );
		Whitebox.setInternalState(CDCLTBIForm.class, "code64750_3_CD",code64750_3_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code64750_3_CD_SevereAdverseEvent",code64750_3_CD_SevereAdverseEvent );
		Whitebox.setInternalState(CDCLTBIForm.class, "code64750_3_D_CD",code64750_3_D_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code64750_3_D_CD_SevereAdverseEvent",code64750_3_D_CD_SevereAdverseEvent );
		Whitebox.setInternalState(CDCLTBIForm.class, "code64750_3_H_CD",code64750_3_H_CD  );
		Whitebox.setInternalState(CDCLTBIForm.class, "code64750_3_H_CD_SevereAdverseEvent",code64750_3_H_CD_SevereAdverseEvent );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV167",INV167  );
		Whitebox.setInternalState(CDCLTBIForm.class, "INV167_InvestigationComments",INV167_InvestigationComments  );

		
	 }
	 
	

	@Test
	public void initializeTreatmentAndOutcome_test() throws Exception{
			
		
		
			System.out.println("******************* Starting test case named: initializeTreatmentAndOutcome_test *******************");
				
			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeTreatmentAndOutcome");
		
			
			int sizeMap = 0;
			int expectedSize = 18;
			
			if(LTBIFieldsMap!=null)
				sizeMap = LTBIFieldsMap.size();
			
			
			
			Assert.assertEquals(expectedSize, sizeMap);
			

			
			System.out.println(iteration+ ": PASSED");
			System.out.println("******************* End test case named: initializeTreatmentAndOutcome_test *******************");
			
		}	
	
	
}


///**
// * initializeDrugRegimen_test: this method will test initializeDrugRegimen. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
// * 
// * @author Fatima.Lopezcalzado
// *
// */
//@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
//@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
//@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
//@PowerMockIgnore("javax.management.*")
//public static class InitializeDrugRegimen_test{
//	
//
//	private String iteration;
//	private int size;
//	
// public InitializeDrugRegimen_test(String it, int size){
//	 
//	 super();
//	 //Common Parameters
//	// this.type = type;
//	 this.iteration = it;
//	 this.size = size;
//	
// }
//
// 
//   @Parameterized.Parameters
//   public static Collection input() {
//	   /*Parameters for validateRule1_test*/
//	   
//	   int it = 0;
//	   
//	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
//	   //The areas are the lines where the new method escapeCharacterSequence method is called
//      return Arrays.asList(new Object[][]{
//    		  
//    		    
//    		  {"initializeDrugRegimen_test"+"_"+it++,0}
//    		  
//    		  
//    		 
//	  });
//   }
// 
// 
// 
///*
// * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
// * */
//
//   
//    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
//   
//
//	@Mock
//	PropertyUtil propertyUtilMocked;
//	
//	@Mock
//	LogUtils loggerMock;
//	
//	@Spy
//	@InjectMocks
//	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());
//
//	 @Before
//	 public void initMocks() throws Exception {
//
//	
//		 PowerMockito.spy(CDCLTBIForm.class);
//		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
//		 
//		 loggerMock = Mockito.mock(LogUtils.class);
//
//		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
//		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
//		 
//		
//		String delimiter1 = "__";
//
//		
//
//		
//		
//
//		String VAR141 = "VAR141";
//		String VAR141_DateTherapyStarted = VAR141+delimiter1+"DateTherapyStarted_VAR141";	
//		Whitebox.setInternalState(CDCLTBIForm.class, "VAR141",VAR141 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "VAR141_DateTherapyStarted",VAR141_DateTherapyStarted );
//
//		
//		
//		String INV1139_CD = "INV1139_CD";
//		String INV1139_CD_InitialDrugRegimenNOTRIPEHRZE = INV1139_CD+delimiter1+"InitialDrugRegimenNOTRIPEHRZE_INV1139_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1139_CD",INV1139_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1139_CD_InitialDrugRegimenNOTRIPEHRZE",INV1139_CD_InitialDrugRegimenNOTRIPEHRZE );
//		String INV1139_OTH = "INV1139_OTH";
//		String INV1139_OTH_InitialDrugRegimenNOTRIPEHRZE = INV1139_OTH+delimiter1+"InitialDrugRegimenNOTRIPEHRZE_INV1139_OTH";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1139_OTH",INV1139_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1139_OTH_InitialDrugRegimenNOTRIPEHRZE",INV1139_OTH_InitialDrugRegimenNOTRIPEHRZE );
//		String code6038_CD = "6038_CD";
//		String code6038_CD_Isoniazid = code6038_CD+delimiter1+"Isoniazid_6038_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code6038_CD",code6038_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code6038_CD_Isoniazid",code6038_CD_Isoniazid );
//		String code9384_CD = "9384_CD";
//		String code9384_CD_Rifampin = code9384_CD+delimiter1+"Rifampin_9384_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code9384_CD",code9384_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code9384_CD_Rifampin",code9384_CD_Rifampin );
//		String code8987_CD = "8987_CD";
//		String code8987_CD_Pyrazinamide = code8987_CD+delimiter1+"Pyrazinamide_8987_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code8987_CD",code8987_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code8987_CD_Pyrazinamide",code8987_CD_Pyrazinamide );
//		String code4110_CD = "4110_CD";
//		String code4110_CD_Ethambutol = code4110_CD+delimiter1+"Ethambutol_4110_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code4110_CD",code4110_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code4110_CD_Ethambutol",code4110_CD_Ethambutol );
//		String code10109_CD = "10109_CD";
//		String code10109_CD_Streptomycin = code10109_CD+delimiter1+"Streptomycin_10109_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code10109_CD",code10109_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code10109_CD_Streptomycin",code10109_CD_Streptomycin );
//		String code55672_CD = "55672_CD";
//		String code55672_CD_Rifabutin = code55672_CD+delimiter1+"Rifabutin_55672_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55672_CD",code55672_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55672_CD_Rifabutin",code55672_CD_Rifabutin );
//		String code35617_CD = "35617_CD";
//		String code35617_CD_Rifapentine = code35617_CD+delimiter1+"Rifapentine_35617_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code35617_CD",code35617_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code35617_CD_Rifapentine",code35617_CD_Rifapentine );
//		String code4127_CD = "4127_CD";
//		String code4127_CD_Ethionamide = code4127_CD+delimiter1+"Ethionamide_4127_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code4127_CD",code4127_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code4127_CD_Ethionamide",code4127_CD_Ethionamide );
//		String code641_CD = "641_CD";
//		String code641_CD_Amikacin = code641_CD+delimiter1+"Amikacin_641_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code641_CD",code641_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code641_CD_Amikacin",code641_CD_Amikacin );
//		String code6099_CD = "6099_CD";
//		String code6099_CD_Kanamycin = code6099_CD+delimiter1+"Kanamycin_6099_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code6099_CD",code6099_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code6099_CD_Kanamycin",code6099_CD_Kanamycin );
//		String code78903_CD = "78903_CD";
//		String code78903_CD_Capreomycin = code78903_CD+delimiter1+"Capreomycin_78903_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code78903_CD",code78903_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code78903_CD_Capreomycin",code78903_CD_Capreomycin );
//		String code2551_CD = "2551_CD";
//		String code2551_CD_Ciprofloxacin = code2551_CD+delimiter1+"Ciprofloxacin_2551_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code2551_CD",code2551_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code2551_CD_Ciprofloxacin",code2551_CD_Ciprofloxacin );
//		String code82122_CD = "82122_CD";
//		String code82122_CD_Levofloxacin = code82122_CD+delimiter1+"Levofloxacin_82122_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code82122_CD",code82122_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code82122_CD_Levofloxacin",code82122_CD_Levofloxacin );
//		String code7623_CD = "7623_CD";
//		String code7623_CD_Ofloxacin = code7623_CD+delimiter1+"Ofloxacin_7623_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code7623_CD",code7623_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code7623_CD_Ofloxacin",code7623_CD_Ofloxacin );
//		String code139462_CD = "139462_CD";
//		String code139462_CD_Moxifloxacin = code139462_CD+delimiter1+"Moxifloxacin_139462_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code139462_CD",code139462_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code139462_CD_Moxifloxacin",code139462_CD_Moxifloxacin );
//		String PHC1888_CD = "PHC1888_CD";
//		String PHC1888_CD_OtherQuinolones = PHC1888_CD+delimiter1+"Other Quinolones_PHC1888_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "PHC1888_CD",PHC1888_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "PHC1888_CD_OtherQuinolones",PHC1888_CD_OtherQuinolones );
//			String code3007_CD = "3007_CD";
//		String code3007_CD_Cycloserine = code3007_CD+delimiter1+"Cycloserine_3007_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code3007_CD",code3007_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code3007_CD_Cycloserine",code3007_CD_Cycloserine );
//				String code7833_CD = "7833_CD";
//		String code7833_CD_ParaAminesalicylicacid = code7833_CD+delimiter1+"Para-Aminesalicylicacid_7833_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code7833_CD",code7833_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code7833_CD_ParaAminesalicylicacid",code7833_CD_ParaAminesalicylicacid );
//		String code190376_CD = "190376_CD";
//		String code190376_CD_Linezolid = code190376_CD+delimiter1+"Linezolid_190376_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code190376_CD",code190376_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code190376_CD_Linezolid",code190376_CD_Linezolid );
//		String code1364504_CD = "1364504_CD";
//		String code1364504_CD_Bedaquiline = code1364504_CD+delimiter1+"Bedaquiline_1364504_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code1364504_CD",code1364504_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code1364504_CD_Bedaquiline",code1364504_CD_Bedaquiline );
//		String PHC1889_CD = "PHC1889_CD";
//		String PHC1889_CD_Delamanid = PHC1889_CD+delimiter1+"Delamanid_PHC1889_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "PHC1889_CD",PHC1889_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "PHC1889_CD_Delamanid",PHC1889_CD_Delamanid );
//			String code2592_CD = "2592_CD";
//		String code2592_CD_Clofazimine = code2592_CD+delimiter1+"Clofazimine_2592_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code2592_CD",code2592_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code2592_CD_Clofazimine",code2592_CD_Clofazimine );
//		String code2198359_CD = "2198359_CD";
//		String code2198359_CD_Pretomanid = code2198359_CD+delimiter1+"Pretomanid_2198359_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code2198359_CD",code2198359_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code2198359_CD_Pretomanid",code2198359_CD_Pretomanid );
//		String NBS456_CD = "NBS456_CD";
//		String NBS456_CD_OtherDrugRegimen = NBS456_CD+delimiter1+"Other Drug Regimen_NBS456_CD";
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "NBS456_CD",NBS456_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "NBS456_CD_OtherDrugRegimen",NBS456_CD_OtherDrugRegimen );
//		String NBS456_OTH = "NBS456_OTH";
//		String NBS456_OTH_Specify = NBS456_OTH+delimiter1+"Specify:_NBS456_OTH";
//
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "NBS456_OTH",NBS456_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "NBS456_OTH_Specify",NBS456_OTH_Specify );
//		
//		
//
//		
//	 }
//	 
//	
//
//	@Test
//	public void initializeDrugRegimen_test() throws Exception{
//			
//		
//		
//			System.out.println("******************* Starting test case named: initializeDrugRegimen_test *******************");
//				
//			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeDrugRegimen");
//		
//			
//			int sizeMap = 0;
//			int expectedSize = 28;
//			
//			if(LTBIFieldsMap!=null)
//				sizeMap = LTBIFieldsMap.size();
//			
//			
//			
//			Assert.assertEquals(expectedSize, sizeMap);
//			
//
//			
//			System.out.println(iteration+ ": PASSED");
//			System.out.println("******************* End test case named: initializeDrugRegimen_test *******************");
//			
//		}	
//	
//	
//}
//
//
//
//
//
//
///**
// * initializeGenotypicPhenotypic_part1_test: this method will test initializeGenotypicPhenotypic_part1. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
// * 
// * @author Fatima.Lopezcalzado
// *
// */
//@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
//@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
//@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
//@PowerMockIgnore("javax.management.*")
//public static class InitializeGenotypicPhenotypic_part1_test{
//	
//
//	private String iteration;
//	private int size;
//	
// public InitializeGenotypicPhenotypic_part1_test(String it, int size){
//	 
//	 super();
//	 //Common Parameters
//	// this.type = type;
//	 this.iteration = it;
//	 this.size = size;
//	
// }
//
// 
//   @Parameterized.Parameters
//   public static Collection input() {
//	   /*Parameters for validateRule1_test*/
//	   
//	   int it = 0;
//	   
//	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
//	   //The areas are the lines where the new method escapeCharacterSequence method is called
//      return Arrays.asList(new Object[][]{
//    		  
//    		    
//    		  {"initializeGenotypicPhenotypic_part1_test"+"_"+it++,0}
//    		  
//    		  
//    		 
//	  });
//   }
// 
// 
// 
///*
// * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
// * */
//
//   
//    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
//   
//
//	@Mock
//	PropertyUtil propertyUtilMocked;
//	
//	@Mock
//	LogUtils loggerMock;
//	
//	@Spy
//	@InjectMocks
//	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());
//
//	 @Before
//	 public void initMocks() throws Exception {
//
//	
//		 PowerMockito.spy(CDCLTBIForm.class);
//		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
//		 
//		 loggerMock = Mockito.mock(LogUtils.class);
//
//		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
//		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
//		 
//		
//		String delimiter1 = "__";
//
//		
//
//		
//		
//
//		String VAR141 = "VAR141";
//		String VAR141_DateTherapyStarted = VAR141+delimiter1+"DateTherapyStarted_VAR141";	
//		Whitebox.setInternalState(CDCLTBIForm.class, "VAR141",VAR141 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "VAR141_DateTherapyStarted",VAR141_DateTherapyStarted );
//
//		///////
//		
//		
//		String INV1145_CD = "INV1145_CD";
//		String INV1145_CD_IsolateSubmittedForGenotyping = INV1145_CD+delimiter1+"IsolateSubmittedForGenotyping_INV1145_CD";
//		String LAB125 = "LAB125";
//		String LAB125_AccessionNumberGen = LAB125+delimiter1+"AccessionNumberGen_LAB125";
//		String INV1147_CD = "INV1147_CD";
//		String INV1147_CD_WasPhenotypic = INV1147_CD+delimiter1+"WasPhenotypic_INV1147_CD";
//		
//		String LABAST6_R1_CDT = "LABAST6_R1_CDT";
//		String LABAST6_R1_CDT_DrugName = LABAST6_R1_CDT+delimiter1+"DrugName_LABAST6_R1_CDT";
//		String LABAST5_R1 = "LABAST5_R1";
//		String LABAST5_R1_DateCollected = LABAST5_R1+delimiter1+"DateCollected_LABAST5_R1";
//		String LABAST14_R1 = "LABAST14_R1";
//		String LABAST14_R1_DateReported = LABAST14_R1+delimiter1+"DateReported_LABAST14_R1";
//		String LABAST3_R1_CDT = "LABAST3_R1_CDT";
//		String LABAST3_R1_CDT_SpecimenSource = LABAST3_R1_CDT+delimiter1+"SpecimenSource_LABAST3_R1_CDT";
//		String LABAST8_R1_CD = "LABAST8_R1_CD";
//		String LABAST8_R1_CD_Result = LABAST8_R1_CD+delimiter1+"Result_LABAST8_R1_CD";
//		String LABAST7_R1_CDT = "LABAST7_R1_CDT";
//		String LABAST7_R1_CDT_TestMethodOptional = LABAST7_R1_CDT+delimiter1+"TestMethodOptional_LABAST7_R1_CDT";
//		
//		String LABAST6_R2_CDT = "LABAST6_R2_CDT";
//		String LABAST6_R2_CDT_DrugName = LABAST6_R2_CDT+delimiter1+"DrugName_LABAST6_R2_CDT";
//		String LABAST5_R2 = "LABAST5_R2";
//		String LABAST5_R2_DateCollected = LABAST5_R2+delimiter1+"DateCollected_LABAST5_R2";
//		String LABAST14_R2 = "LABAST14_R2";
//		String LABAST14_R2_DateReported = LABAST14_R2+delimiter1+"DateReported_LABAST14_R2";
//		String LABAST3_R2_CDT = "LABAST3_R2_CDT";
//		String LABAST3_R2_CDT_SpecimenSource = LABAST3_R2_CDT+delimiter1+"SpecimenSource_LABAST3_R2_CDT";
//		String LABAST8_R2_CD = "LABAST8_R2_CD";
//		String LABAST8_R2_CD_Result = LABAST8_R2_CD+delimiter1+"Result_LABAST8_R2_CD";
//		String LABAST7_R2_CDT = "LABAST7_R2_CDT";
//		String LABAST7_R2_CDT_TestMethodOptional = LABAST7_R2_CDT+delimiter1+"TestMethodOptional_LABAST7_R2_CDT";
//
//		String LABAST6_R3_CDT = "LABAST6_R3_CDT";
//		String LABAST6_R3_CDT_DrugName = LABAST6_R3_CDT+delimiter1+"DrugName_LABAST6_R3_CDT";
//		String LABAST5_R3 = "LABAST5_R3";
//		String LABAST5_R3_DateCollected = LABAST5_R3+delimiter1+"DateCollected_LABAST5_R3";
//		String LABAST14_R3 = "LABAST14_R3";
//		String LABAST14_R3_DateReported = LABAST14_R3+delimiter1+"DateReported_LABAST14_R3";
//		String LABAST3_R3_CDT = "LABAST3_R3_CDT";
//		String LABAST3_R3_CDT_SpecimenSource = LABAST3_R3_CDT+delimiter1+"SpecimenSource_LABAST3_R3_CDT";
//		String LABAST8_R3_CD = "LABAST8_R3_CD";
//		String LABAST8_R3_CD_Result = LABAST8_R3_CD+delimiter1+"Result_LABAST8_R3_CD";
//		String LABAST7_R3_CDT = "LABAST7_R3_CDT";
//		String LABAST7_R3_CDT_TestMethodOptional = LABAST7_R3_CDT+delimiter1+"TestMethodOptional_LABAST7_R3_CDT";
//
//		String LABAST6_R4_CDT = "LABAST6_R4_CDT";
//		String LABAST6_R4_CDT_DrugName = LABAST6_R4_CDT+delimiter1+"DrugName_LABAST6_R4_CDT";
//		String LABAST5_R4 = "LABAST5_R4";
//		String LABAST5_R4_DateCollected = LABAST5_R4+delimiter1+"DateCollected_LABAST5_R4";
//		String LABAST14_R4 = "LABAST14_R4";
//		String LABAST14_R4_DateReported = LABAST14_R4+delimiter1+"DateReported_LABAST14_R4";
//		String LABAST3_R4_CDT = "LABAST3_R4_CDT";
//		String LABAST3_R4_CDT_SpecimenSource = LABAST3_R4_CDT+delimiter1+"SpecimenSource_LABAST3_R4_CDT";
//		String LABAST8_R4_CD = "LABAST8_R4_CD";
//		String LABAST8_R4_CD_Result = LABAST8_R4_CD+delimiter1+"Result_LABAST8_R4_CD";
//		String LABAST7_R4_CDT = "LABAST7_R4_CDT";
//		String LABAST7_R4_CDT_TestMethodOptional = LABAST7_R4_CDT+delimiter1+"TestMethodOptional_LABAST7_R4_CDT";
//
//		String LABAST6_R5_CDT = "LABAST6_R5_CDT";
//		String LABAST6_R5_CDT_DrugName = LABAST6_R5_CDT+delimiter1+"DrugName_LABAST6_R5_CDT";
//		String LABAST5_R5 = "LABAST5_R5";
//		String LABAST5_R5_DateCollected = LABAST5_R5+delimiter1+"DateCollected_LABAST5_R5";
//		String LABAST14_R5 = "LABAST14_R5";
//		String LABAST14_R5_DateReported = LABAST14_R5+delimiter1+"DateReported_LABAST14_R5";
//		String LABAST3_R5_CDT = "LABAST3_R5_CDT";
//		String LABAST3_R5_CDT_SpecimenSource = LABAST3_R5_CDT+delimiter1+"SpecimenSource_LABAST3_R5_CDT";
//		String LABAST8_R5_CD = "LABAST8_R5_CD";
//		String LABAST8_R5_CD_Result = LABAST8_R5_CD+delimiter1+"Result_LABAST8_R5_CD";
//		String LABAST7_R5_CDT = "LABAST7_R5_CDT";
//		String LABAST7_R5_CDT_TestMethodOptional = LABAST7_R5_CDT+delimiter1+"TestMethodOptional_LABAST7_R5_CDT";
//
//		String LABAST6_R6_CDT = "LABAST6_R6_CDT";
//		String LABAST6_R6_CDT_DrugName = LABAST6_R6_CDT+delimiter1+"DrugName_LABAST6_R6_CDT";
//		String LABAST5_R6 = "LABAST5_R6";
//		String LABAST5_R6_DateCollected = LABAST5_R6+delimiter1+"DateCollected_LABAST5_R6";
//		String LABAST14_R6 = "LABAST14_R6";
//		String LABAST14_R6_DateReported = LABAST14_R6+delimiter1+"DateReported_LABAST14_R6";
//		String LABAST3_R6_CDT = "LABAST3_R6_CDT";
//		String LABAST3_R6_CDT_SpecimenSource = LABAST3_R6_CDT+delimiter1+"SpecimenSource_LABAST3_R6_CDT";
//		String LABAST8_R6_CD = "LABAST8_R6_CD";
//		String LABAST8_R6_CD_Result = LABAST8_R6_CD+delimiter1+"Result_LABAST8_R6_CD";
//		String LABAST7_R6_CDT = "LABAST7_R6_CDT";
//		String LABAST7_R6_CDT_TestMethodOptional = LABAST7_R6_CDT+delimiter1+"TestMethodOptional_LABAST7_R6_CDT";
//
//		String LABAST6_R7_CDT = "LABAST6_R7_CDT";
//		String LABAST6_R7_CDT_DrugName = LABAST6_R7_CDT+delimiter1+"DrugName_LABAST6_R7_CDT";
//		String LABAST5_R7 = "LABAST5_R7";
//		String LABAST5_R7_DateCollected = LABAST5_R7+delimiter1+"DateCollected_LABAST5_R7";
//		String LABAST14_R7 = "LABAST14_R7";
//		String LABAST14_R7_DateReported = LABAST14_R7+delimiter1+"DateReported_LABAST14_R7";
//		String LABAST3_R7_CDT = "LABAST3_R7_CDT";
//		String LABAST3_R7_CDT_SpecimenSource = LABAST3_R7_CDT+delimiter1+"SpecimenSource_LABAST3_R7_CDT";
//		String LABAST8_R7_CD = "LABAST8_R7_CD";
//		String LABAST8_R7_CD_Result = LABAST8_R7_CD+delimiter1+"Result_LABAST8_R7_CD";
//		String LABAST7_R7_CDT = "LABAST7_R7_CDT";
//		String LABAST7_R7_CDT_TestMethodOptional = LABAST7_R7_CDT+delimiter1+"TestMethodOptional_LABAST7_R7_CDT";
//		
//		String LABAST6_R8_CDT = "LABAST6_R8_CDT";
//		String LABAST6_R8_CDT_DrugName = LABAST6_R8_CDT+delimiter1+"DrugName_LABAST6_R8_CDT";
//		String LABAST5_R8 = "LABAST5_R8";
//		String LABAST5_R8_DateCollected = LABAST5_R8+delimiter1+"DateCollected_LABAST5_R8";
//		String LABAST14_R8 = "LABAST14_R8";
//		String LABAST14_R8_DateReported = LABAST14_R8+delimiter1+"DateReported_LABAST14_R8";
//		String LABAST3_R8_CDT = "LABAST3_R8_CDT";
//		String LABAST3_R8_CDT_SpecimenSource = LABAST3_R8_CDT+delimiter1+"SpecimenSource_LABAST3_R8_CDT";
//		String LABAST8_R8_CD = "LABAST8_R8_CD";
//		String LABAST8_R8_CD_Result = LABAST8_R8_CD+delimiter1+"Result_LABAST8_R8_CD";
//		String LABAST7_R8_CDT = "LABAST7_R8_CDT";
//		String LABAST7_R8_CDT_TestMethodOptional = LABAST7_R8_CDT+delimiter1+"TestMethodOptional_LABAST7_R8_CDT";
//
//		String LABAST6_R9_CDT = "LABAST6_R9_CDT";
//		String LABAST6_R9_CDT_DrugName = LABAST6_R9_CDT+delimiter1+"DrugName_LABAST6_R9_CDT";
//		String LABAST5_R9 = "LABAST5_R9";
//		String LABAST5_R9_DateCollected = LABAST5_R9+delimiter1+"DateCollected_LABAST5_R9";
//		String LABAST14_R9 = "LABAST14_R9";
//		String LABAST14_R9_DateReported = LABAST14_R9+delimiter1+"DateReported_LABAST14_R9";
//		String LABAST3_R9_CDT = "LABAST3_R9_CDT";
//		String LABAST3_R9_CDT_SpecimenSource = LABAST3_R9_CDT+delimiter1+"SpecimenSource_LABAST3_R9_CDT";
//		String LABAST8_R9_CD = "LABAST8_R9_CD";
//		String LABAST8_R9_CD_Result = LABAST8_R9_CD+delimiter1+"Result_LABAST8_R9_CD";
//		String LABAST7_R9_CDT = "LABAST7_R9_CDT";
//		String LABAST7_R9_CDT_TestMethodOptional = LABAST7_R9_CDT+delimiter1+"TestMethodOptional_LABAST7_R9_CDT";
//
//		String LABAST6_R10_CDT = "LABAST6_R10_CDT";
//		String LABAST6_R10_CDT_DrugName = LABAST6_R10_CDT+delimiter1+"DrugName_LABAST6_R10_CDT";
//		String LABAST5_R10 = "LABAST5_R10";
//		String LABAST5_R10_DateCollected = LABAST5_R10+delimiter1+"DateCollected_LABAST5_R10";
//		String LABAST14_R10 = "LABAST14_R10";
//		String LABAST14_R10_DateReported = LABAST14_R10+delimiter1+"DateReported_LABAST14_R10";
//		String LABAST3_R10_CDT = "LABAST3_R10_CDT";
//		String LABAST3_R10_CDT_SpecimenSource = LABAST3_R10_CDT+delimiter1+"SpecimenSource_LABAST3_R10_CDT";
//		String LABAST8_R10_CD = "LABAST8_R10_CD";
//		String LABAST8_R10_CD_Result = LABAST8_R10_CD+delimiter1+"Result_LABAST8_R10_CD";
//		String LABAST7_R10_CDT = "LABAST7_R10_CDT";
//		String LABAST7_R10_CDT_TestMethodOptional = LABAST7_R10_CDT+delimiter1+"TestMethodOptional_LABAST7_R10_CDT";
//
//		String LABAST6_R11_CDT = "LABAST6_R11_CDT";
//		String LABAST6_R11_CDT_DrugName = LABAST6_R11_CDT+delimiter1+"DrugName_LABAST6_R11_CDT";
//		String LABAST5_R11 = "LABAST5_R11";
//		String LABAST5_R11_DateCollected = LABAST5_R11+delimiter1+"DateCollected_LABAST5_R11";
//		String LABAST14_R11 = "LABAST14_R11";
//		String LABAST14_R11_DateReported = LABAST14_R11+delimiter1+"DateReported_LABAST14_R11";
//		String LABAST3_R11_CDT = "LABAST3_R11_CDT";
//		String LABAST3_R11_CDT_SpecimenSource = LABAST3_R11_CDT+delimiter1+"SpecimenSource_LABAST3_R11_CDT";
//		String LABAST8_R11_CD = "LABAST8_R11_CD";
//		String LABAST8_R11_CD_Result = LABAST8_R11_CD+delimiter1+"Result_LABAST8_R11_CD";
//		String LABAST7_R11_CDT = "LABAST7_R11_CDT";
//		String LABAST7_R11_CDT_TestMethodOptional = LABAST7_R11_CDT+delimiter1+"TestMethodOptional_LABAST7_R11_CDT";
//		
//		String LABAST6_R12_CDT = "LABAST6_R12_CDT";
//		String LABAST6_R12_CDT_DrugName = LABAST6_R12_CDT+delimiter1+"DrugName_LABAST6_R12_CDT";
//		String LABAST5_R12 = "LABAST5_R12";
//		String LABAST5_R12_DateCollected = LABAST5_R12+delimiter1+"DateCollected_LABAST5_R12";
//		String LABAST14_R12 = "LABAST14_R12";
//		String LABAST14_R12_DateReported = LABAST14_R12+delimiter1+"DateReported_LABAST14_R12";
//		String LABAST3_R12_CDT = "LABAST3_R12_CDT";
//		String LABAST3_R12_CDT_SpecimenSource = LABAST3_R12_CDT+delimiter1+"SpecimenSource_LABAST3_R12_CDT";
//		String LABAST8_R12_CD = "LABAST8_R12_CD";
//		String LABAST8_R12_CD_Result = LABAST8_R12_CD+delimiter1+"Result_LABAST8_R12_CD";
//		String LABAST7_R12_CDT = "LABAST7_R12_CDT";
//		String LABAST7_R12_CDT_TestMethodOptional = LABAST7_R12_CDT+delimiter1+"TestMethodOptional_LABAST7_R12_CDT";
//
//		String LABAST6_R13_CDT = "LABAST6_R13_CDT";
//		String LABAST6_R13_CDT_DrugName = LABAST6_R13_CDT+delimiter1+"DrugName_LABAST6_R13_CDT";
//		String LABAST5_R13 = "LABAST5_R13";
//		String LABAST5_R13_DateCollected = LABAST5_R13+delimiter1+"DateCollected_LABAST5_R13";
//		String LABAST14_R13 = "LABAST14_R13";
//		String LABAST14_R13_DateReported = LABAST14_R13+delimiter1+"DateReported_LABAST14_R13";
//		String LABAST3_R13_CDT = "LABAST3_R13_CDT";
//		String LABAST3_R13_CDT_SpecimenSource = LABAST3_R13_CDT+delimiter1+"SpecimenSource_LABAST3_R13_CDT";
//		String LABAST8_R13_CD = "LABAST8_R13_CD";
//		String LABAST8_R13_CD_Result = LABAST8_R13_CD+delimiter1+"Result_LABAST8_R13_CD";
//		String LABAST7_R13_CDT = "LABAST7_R13_CDT";
//		String LABAST7_R13_CDT_TestMethodOptional = LABAST7_R13_CDT+delimiter1+"TestMethodOptional_LABAST7_R13_CDT";
//
//		String LABAST6_R14_CDT = "LABAST6_R14_CDT";
//		String LABAST6_R14_CDT_DrugName = LABAST6_R14_CDT+delimiter1+"DrugName_LABAST6_R14_CDT";
//		String LABAST5_R14 = "LABAST5_R14";
//		String LABAST5_R14_DateCollected = LABAST5_R14+delimiter1+"DateCollected_LABAST5_R14";
//		String LABAST14_R14 = "LABAST14_R14";
//		String LABAST14_R14_DateReported = LABAST14_R14+delimiter1+"DateReported_LABAST14_R14";
//		String LABAST3_R14_CDT = "LABAST3_R14_CDT";
//		String LABAST3_R14_CDT_SpecimenSource = LABAST3_R14_CDT+delimiter1+"SpecimenSource_LABAST3_R14_CDT";
//		String LABAST8_R14_CD = "LABAST8_R14_CD";
//		String LABAST8_R14_CD_Result = LABAST8_R14_CD+delimiter1+"Result_LABAST8_R14_CD";
//		String LABAST7_R14_CDT = "LABAST7_R14_CDT";
//		String LABAST7_R14_CDT_TestMethodOptional = LABAST7_R14_CDT+delimiter1+"TestMethodOptional_LABAST7_R14_CDT";
//
//		
//		Whitebox.setInternalState(CDCLTBIForm.class,"INV1145_CD",INV1145_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class,"INV1145_CD_IsolateSubmittedForGenotyping",INV1145_CD_IsolateSubmittedForGenotyping );
//		Whitebox.setInternalState(CDCLTBIForm.class,"LAB125",LAB125 );
//		Whitebox.setInternalState(CDCLTBIForm.class,"LAB125_AccessionNumberGen",LAB125_AccessionNumberGen );
//		Whitebox.setInternalState(CDCLTBIForm.class,"INV1147_CD",INV1147_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1147_CD_WasPhenotypic",INV1147_CD_WasPhenotypic );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R1_CDT",LABAST6_R1_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R1_CDT_DrugName",LABAST6_R1_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R1",LABAST5_R1 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R1_DateCollected",LABAST5_R1_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R1",LABAST14_R1 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R1_DateReported",LABAST14_R1_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R1_CDT",LABAST3_R1_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R1_CDT_SpecimenSource",LABAST3_R1_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R1_CD",LABAST8_R1_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R1_CD_Result",LABAST8_R1_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R1_CDT",LABAST7_R1_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R1_CDT_TestMethodOptional",LABAST7_R1_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R2_CDT",LABAST6_R2_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R2_CDT_DrugName",LABAST6_R2_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R2",LABAST5_R2 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R2_DateCollected",LABAST5_R2_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R2",LABAST14_R2 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R2_DateReported",LABAST14_R2_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R2_CDT",LABAST3_R2_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R2_CDT_SpecimenSource",LABAST3_R2_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R2_CD",LABAST8_R2_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R2_CD_Result",LABAST8_R2_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R2_CDT",LABAST7_R2_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R2_CDT_TestMethodOptional",LABAST7_R2_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R3_CDT",LABAST6_R3_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R3_CDT_DrugName",LABAST6_R3_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R3",LABAST5_R3 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R3_DateCollected",LABAST5_R3_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R3",LABAST14_R3 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R3_DateReported",LABAST14_R3_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R3_CDT",LABAST3_R3_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R3_CDT_SpecimenSource",LABAST3_R3_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R3_CD",LABAST8_R3_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R3_CD_Result",LABAST8_R3_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R3_CDT",LABAST7_R3_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R3_CDT_TestMethodOptional",LABAST7_R3_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R4_CDT",LABAST6_R4_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R4_CDT_DrugName",LABAST6_R4_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R4",LABAST5_R4 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R4_DateCollected",LABAST5_R4_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R4",LABAST14_R4 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R4_DateReported",LABAST14_R4_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R4_CDT",LABAST3_R4_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R4_CDT_SpecimenSource",LABAST3_R4_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R4_CD",LABAST8_R4_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R4_CD_Result",LABAST8_R4_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R4_CDT",LABAST7_R4_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R4_CDT_TestMethodOptional",LABAST7_R4_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R5_CDT",LABAST6_R5_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R5_CDT_DrugName",LABAST6_R5_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R5",LABAST5_R5 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R5_DateCollected",LABAST5_R5_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R5",LABAST14_R5 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R5_DateReported",LABAST14_R5_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R5_CDT",LABAST3_R5_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R5_CDT_SpecimenSource",LABAST3_R5_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R5_CD",LABAST8_R5_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R5_CD_Result",LABAST8_R5_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R5_CDT",LABAST7_R5_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R5_CDT_TestMethodOptional",LABAST7_R5_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R6_CDT",LABAST6_R6_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R6_CDT_DrugName",LABAST6_R6_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R6",LABAST5_R6 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R6_DateCollected",LABAST5_R6_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R6",LABAST14_R6 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R6_DateReported",LABAST14_R6_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R6_CDT",LABAST3_R6_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R6_CDT_SpecimenSource",LABAST3_R6_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R6_CD",LABAST8_R6_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R6_CD_Result",LABAST8_R6_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R6_CDT",LABAST7_R6_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R6_CDT_TestMethodOptional",LABAST7_R6_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R7_CDT",LABAST6_R7_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R7_CDT_DrugName",LABAST6_R7_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R7",LABAST5_R7 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R7_DateCollected",LABAST5_R7_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R7",LABAST14_R7 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R7_DateReported",LABAST14_R7_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R7_CDT",LABAST3_R7_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R7_CDT_SpecimenSource",LABAST3_R7_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R7_CD",LABAST8_R7_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R7_CD_Result",LABAST8_R7_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R7_CDT",LABAST7_R7_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R7_CDT_TestMethodOptional",LABAST7_R7_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R8_CDT",LABAST6_R8_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R8_CDT_DrugName",LABAST6_R8_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R8",LABAST5_R8 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R8_DateCollected",LABAST5_R8_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R8",LABAST14_R8 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R8_DateReported",LABAST14_R8_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R8_CDT",LABAST3_R8_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R8_CDT_SpecimenSource",LABAST3_R8_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R8_CD",LABAST8_R8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R8_CD_Result",LABAST8_R8_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R8_CDT",LABAST7_R8_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R8_CDT_TestMethodOptional",LABAST7_R8_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R9_CDT",LABAST6_R9_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R9_CDT_DrugName",LABAST6_R9_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R9",LABAST5_R9 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R9_DateCollected",LABAST5_R9_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R9",LABAST14_R9 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R9_DateReported",LABAST14_R9_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R9_CDT",LABAST3_R9_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R9_CDT_SpecimenSource",LABAST3_R9_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R9_CD",LABAST8_R9_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R9_CD_Result",LABAST8_R9_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R9_CDT",LABAST7_R9_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R9_CDT_TestMethodOptional",LABAST7_R9_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R10_CDT",LABAST6_R10_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R10_CDT_DrugName",LABAST6_R10_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R10",LABAST5_R10 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R10_DateCollected",LABAST5_R10_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R10",LABAST14_R10 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R10_DateReported",LABAST14_R10_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R10_CDT",LABAST3_R10_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R10_CDT_SpecimenSource",LABAST3_R10_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R10_CD",LABAST8_R10_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R10_CD_Result",LABAST8_R10_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R10_CDT",LABAST7_R10_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R10_CDT_TestMethodOptional",LABAST7_R10_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R11_CDT",LABAST6_R11_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R11_CDT_DrugName",LABAST6_R11_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R11",LABAST5_R11 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R11_DateCollected",LABAST5_R11_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R11",LABAST14_R11 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R11_DateReported",LABAST14_R11_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R11_CDT",LABAST3_R11_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R11_CDT_SpecimenSource",LABAST3_R11_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R11_CD",LABAST8_R11_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R11_CD_Result",LABAST8_R11_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R11_CDT",LABAST7_R11_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R11_CDT_TestMethodOptional",LABAST7_R11_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R12_CDT",LABAST6_R12_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R12_CDT_DrugName",LABAST6_R12_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R12",LABAST5_R12 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R12_DateCollected",LABAST5_R12_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R12",LABAST14_R12 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R12_DateReported",LABAST14_R12_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R12_CDT",LABAST3_R12_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R12_CDT_SpecimenSource",LABAST3_R12_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R12_CD",LABAST8_R12_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R12_CD_Result",LABAST8_R12_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R12_CDT",LABAST7_R12_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R12_CDT_TestMethodOptional",LABAST7_R12_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R13_CDT",LABAST6_R13_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R13_CDT_DrugName",LABAST6_R13_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R13",LABAST5_R13 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R13_DateCollected",LABAST5_R13_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R13",LABAST14_R13 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R13_DateReported",LABAST14_R13_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R13_CDT",LABAST3_R13_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R13_CDT_SpecimenSource",LABAST3_R13_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R13_CD",LABAST8_R13_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R13_CD_Result",LABAST8_R13_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R13_CDT",LABAST7_R13_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R13_CDT_TestMethodOptional",LABAST7_R13_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R14_CDT",LABAST6_R14_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R14_CDT_DrugName",LABAST6_R14_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R14",LABAST5_R14 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R14_DateCollected",LABAST5_R14_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R14",LABAST14_R14 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R14_DateReported",LABAST14_R14_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R14_CDT",LABAST3_R14_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R14_CDT_SpecimenSource",LABAST3_R14_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R14_CD",LABAST8_R14_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R14_CD_Result",LABAST8_R14_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R14_CDT",LABAST7_R14_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R14_CDT_TestMethodOptional",LABAST7_R14_CDT_TestMethodOptional );
//		
//		
//	 }
//	 
//	
//
//	@Test
//	public void initializeGenotypicPhenotypic_part1_test() throws Exception{
//			
//		
//		
//			System.out.println("******************* Starting test case named: initializeGenotypicPhenotypic_part1_test *******************");
//				
//			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeGenotypicPhenotypic_part1");
//		
//			
//			int sizeMap = 0;
//			int expectedSize = 87;
//			
//			if(LTBIFieldsMap!=null)
//				sizeMap = LTBIFieldsMap.size();
//			
//			
//		//	Assert.assertNotEquals(0, sizeMap);//At least one initialized
//			Assert.assertEquals(expectedSize, sizeMap);
//			
//
//			
//			System.out.println(iteration+ ": PASSED");
//			System.out.println("******************* End test case named: initializeGenotypicPhenotypic_part1_test *******************");
//			
//		}	
//	
//	
//}
//
//
//
//
//
//
//
///**
// * initializeGenotypicPhenotypic_part2_test: this method will test InitializeGenotypicPhenotypic_part2. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
// * 
// * @author Fatima.Lopezcalzado
// *
// */
//@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
//@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
//@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
//@PowerMockIgnore("javax.management.*")
//public static class InitializeGenotypicPhenotypic_part2_test{
//	
//
//	private String iteration;
//	private int size;
//	
// public InitializeGenotypicPhenotypic_part2_test(String it, int size){
//	 
//	 super();
//	 //Common Parameters
//	// this.type = type;
//	 this.iteration = it;
//	 this.size = size;
//	
// }
//
// 
//   @Parameterized.Parameters
//   public static Collection input() {
//	   /*Parameters for validateRule1_test*/
//	   
//	   int it = 0;
//	   
//	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
//	   //The areas are the lines where the new method escapeCharacterSequence method is called
//      return Arrays.asList(new Object[][]{
//    		  
//    		    
//    		  {"InitializeGenotypicPhenotypic_part2_test"+"_"+it++,0}
//    		  
//    		  
//    		 
//	  });
//   }
// 
// 
// 
///*
// * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
// * */
//
//   
//    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
//   
//
//	@Mock
//	PropertyUtil propertyUtilMocked;
//	
//	@Mock
//	LogUtils loggerMock;
//	
//	@Spy
//	@InjectMocks
//	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());
//
//	 @Before
//	 public void initMocks() throws Exception {
//
//	
//		 PowerMockito.spy(CDCLTBIForm.class);
//		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
//		 
//		 loggerMock = Mockito.mock(LogUtils.class);
//
//		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
//		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
//		 
//		
//		String delimiter1 = "__";
//
//		
//
//		
//		
//
//		String VAR141 = "VAR141";
//		String VAR141_DateTherapyStarted = VAR141+delimiter1+"DateTherapyStarted_VAR141";	
//		Whitebox.setInternalState(CDCLTBIForm.class, "VAR141",VAR141 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "VAR141_DateTherapyStarted",VAR141_DateTherapyStarted );
//
//		///////
//		
//	
//		String LABAST6_R15_CDT = "LABAST6_R15_CDT";
//		String LABAST6_R15_CDT_DrugName = LABAST6_R15_CDT+delimiter1+"DrugName_LABAST6_R15_CDT";
//		String LABAST5_R15 = "LABAST5_R15";
//		String LABAST5_R15_DateCollected = LABAST5_R15+delimiter1+"DateCollected_LABAST5_R15";
//		String LABAST14_R15 = "LABAST14_R15";
//		String LABAST14_R15_DateReported = LABAST14_R15+delimiter1+"DateReported_LABAST14_R15";
//		String LABAST3_R15_CDT = "LABAST3_R15_CDT";
//		String LABAST3_R15_CDT_SpecimenSource = LABAST3_R15_CDT+delimiter1+"SpecimenSource_LABAST3_R15_CDT";
//		String LABAST8_R15_CD = "LABAST8_R15_CD";
//		String LABAST8_R15_CD_Result = LABAST8_R15_CD+delimiter1+"Result_LABAST8_R15_CD";
//		String LABAST7_R15_CDT = "LABAST7_R15_CDT";
//		String LABAST7_R15_CDT_TestMethodOptional = LABAST7_R15_CDT+delimiter1+"TestMethodOptional_LABAST7_R15_CDT";
//
//		String LABAST6_R16_CDT = "LABAST6_R16_CDT";
//		String LABAST6_R16_CDT_DrugName = LABAST6_R16_CDT+delimiter1+"DrugName_LABAST6_R16_CDT";
//		String LABAST5_R16 = "LABAST5_R16";
//		String LABAST5_R16_DateCollected = LABAST5_R16+delimiter1+"DateCollected_LABAST5_R16";
//		String LABAST14_R16 = "LABAST14_R16";
//		String LABAST14_R16_DateReported = LABAST14_R16+delimiter1+"DateReported_LABAST14_R16";
//		String LABAST3_R16_CDT = "LABAST3_R16_CDT";
//		String LABAST3_R16_CDT_SpecimenSource = LABAST3_R16_CDT+delimiter1+"SpecimenSource_LABAST3_R16_CDT";
//		String LABAST8_R16_CD = "LABAST8_R16_CD";
//		String LABAST8_R16_CD_Result = LABAST8_R16_CD+delimiter1+"Result_LABAST8_R16_CD";
//		String LABAST7_R16_CDT = "LABAST7_R16_CDT";
//		String LABAST7_R16_CDT_TestMethodOptional = LABAST7_R16_CDT+delimiter1+"TestMethodOptional_LABAST7_R16_CDT";
//
//		String LABAST6_R17_CDT = "LABAST6_R17_CDT";
//		String LABAST6_R17_CDT_DrugName = LABAST6_R17_CDT+delimiter1+"DrugName_LABAST6_R17_CDT";
//		String LABAST5_R17 = "LABAST5_R17";
//		String LABAST5_R17_DateCollected = LABAST5_R17+delimiter1+"DateCollected_LABAST5_R17";
//		String LABAST14_R17 = "LABAST14_R17";
//		String LABAST14_R17_DateReported = LABAST14_R17+delimiter1+"DateReported_LABAST14_R17";
//		String LABAST3_R17_CDT = "LABAST3_R17_CDT";
//		String LABAST3_R17_CDT_SpecimenSource = LABAST3_R17_CDT+delimiter1+"SpecimenSource_LABAST3_R17_CDT";
//		String LABAST8_R17_CD = "LABAST8_R17_CD";
//		String LABAST8_R17_CD_Result = LABAST8_R17_CD+delimiter1+"Result_LABAST8_R17_CD";
//		String LABAST7_R17_CDT = "LABAST7_R17_CDT";
//		String LABAST7_R17_CDT_TestMethodOptional = LABAST7_R17_CDT+delimiter1+"TestMethodOptional_LABAST7_R17_CDT";
//
//		String LABAST6_R18_CDT = "LABAST6_R18_CDT";
//		String LABAST6_R18_CDT_DrugName = LABAST6_R18_CDT+delimiter1+"DrugName_LABAST6_R18_CDT";
//		String LABAST5_R18 = "LABAST5_R18";
//		String LABAST5_R18_DateCollected = LABAST5_R18+delimiter1+"DateCollected_LABAST5_R18";
//		String LABAST14_R18 = "LABAST14_R18";
//		String LABAST14_R18_DateReported = LABAST14_R18+delimiter1+"DateReported_LABAST14_R18";
//		String LABAST3_R18_CDT = "LABAST3_R18_CDT";
//		String LABAST3_R18_CDT_SpecimenSource = LABAST3_R18_CDT+delimiter1+"SpecimenSource_LABAST3_R18_CDT";
//		String LABAST8_R18_CD = "LABAST8_R18_CD";
//		String LABAST8_R18_CD_Result = LABAST8_R18_CD+delimiter1+"Result_LABAST8_R18_CD";
//		String LABAST7_R18_CDT = "LABAST7_R18_CDT";
//		String LABAST7_R18_CDT_TestMethodOptional = LABAST7_R18_CDT+delimiter1+"TestMethodOptional_LABAST7_R18_CDT";
//
//		String LABAST6_R19_CDT = "LABAST6_R19_CDT";
//		String LABAST6_R19_CDT_DrugName = LABAST6_R19_CDT+delimiter1+"DrugName_LABAST6_R19_CDT";
//		String LABAST5_R19 = "LABAST5_R19";
//		String LABAST5_R19_DateCollected = LABAST5_R19+delimiter1+"DateCollected_LABAST5_R19";
//		String LABAST14_R19 = "LABAST14_R19";
//		String LABAST14_R19_DateReported = LABAST14_R19+delimiter1+"DateReported_LABAST14_R19";
//		String LABAST3_R19_CDT = "LABAST3_R19_CDT";
//		String LABAST3_R19_CDT_SpecimenSource = LABAST3_R19_CDT+delimiter1+"SpecimenSource_LABAST3_R19_CDT";
//		String LABAST8_R19_CD = "LABAST8_R19_CD";
//		String LABAST8_R19_CD_Result = LABAST8_R19_CD+delimiter1+"Result_LABAST8_R19_CD";
//		String LABAST7_R19_CDT = "LABAST7_R19_CDT";
//		String LABAST7_R19_CDT_TestMethodOptional = LABAST7_R19_CDT+delimiter1+"TestMethodOptional_LABAST7_R19_CDT";
//
//		String LABAST6_R20_CDT = "LABAST6_R20_CDT";
//		String LABAST6_R20_CDT_DrugName = LABAST6_R20_CDT+delimiter1+"DrugName_LABAST6_R20_CDT";
//		String LABAST5_R20 = "LABAST5_R20";
//		String LABAST5_R20_DateCollected = LABAST5_R20+delimiter1+"DateCollected_LABAST5_R20";
//		String LABAST14_R20 = "LABAST14_R20";
//		String LABAST14_R20_DateReported = LABAST14_R20+delimiter1+"DateReported_LABAST14_R20";
//		String LABAST3_R20_CDT = "LABAST3_R20_CDT";
//		String LABAST3_R20_CDT_SpecimenSource = LABAST3_R20_CDT+delimiter1+"SpecimenSource_LABAST3_R20_CDT";
//		String LABAST8_R20_CD = "LABAST8_R20_CD";
//		String LABAST8_R20_CD_Result = LABAST8_R20_CD+delimiter1+"Result_LABAST8_R20_CD";
//		String LABAST7_R20_CDT = "LABAST7_R20_CDT";
//		String LABAST7_R20_CDT_TestMethodOptional = LABAST7_R20_CDT+delimiter1+"TestMethodOptional_LABAST7_R20_CDT";
//
//		String LABAST6_R21_CDT = "LABAST6_R21_CDT";
//		String LABAST6_R21_CDT_DrugName = LABAST6_R21_CDT+delimiter1+"DrugName_LABAST6_R21_CDT";
//		String LABAST5_R21 = "LABAST5_R21";
//		String LABAST5_R21_DateCollected = LABAST5_R21+delimiter1+"DateCollected_LABAST5_R21";
//		String LABAST14_R21 = "LABAST14_R21";
//		String LABAST14_R21_DateReported = LABAST14_R21+delimiter1+"DateReported_LABAST14_R21";
//		String LABAST3_R21_CDT = "LABAST3_R21_CDT";
//		String LABAST3_R21_CDT_SpecimenSource = LABAST3_R21_CDT+delimiter1+"SpecimenSource_LABAST3_R21_CDT";
//		String LABAST8_R21_CD = "LABAST8_R21_CD";
//		String LABAST8_R21_CD_Result = LABAST8_R21_CD+delimiter1+"Result_LABAST8_R21_CD";
//		String LABAST7_R21_CDT = "LABAST7_R21_CDT";
//		String LABAST7_R21_CDT_TestMethodOptional = LABAST7_R21_CDT+delimiter1+"TestMethodOptional_LABAST7_R21_CDT";
//		
//		String LABAST6_R22_CDT = "LABAST6_R22_CDT";
//		String LABAST6_R22_CDT_DrugName = LABAST6_R22_CDT+delimiter1+"DrugName_LABAST6_R22_CDT";
//		String LABAST5_R22 = "LABAST5_R22";
//		String LABAST5_R22_DateCollected = LABAST5_R22+delimiter1+"DateCollected_LABAST5_R22";
//		String LABAST14_R22 = "LABAST14_R22";
//		String LABAST14_R22_DateReported = LABAST14_R22+delimiter1+"DateReported_LABAST14_R22";
//		String LABAST3_R22_CDT = "LABAST3_R22_CDT";
//		String LABAST3_R22_CDT_SpecimenSource = LABAST3_R22_CDT+delimiter1+"SpecimenSource_LABAST3_R22_CDT";
//		String LABAST8_R22_CD = "LABAST8_R22_CD";
//		String LABAST8_R22_CD_Result = LABAST8_R22_CD+delimiter1+"Result_LABAST8_R22_CD";
//		String LABAST7_R22_CDT = "LABAST7_R22_CDT";
//		String LABAST7_R22_CDT_TestMethodOptional = LABAST7_R22_CDT+delimiter1+"TestMethodOptional_LABAST7_R22_CDT";
//
//		String LABAST6_R23_CDT = "LABAST6_R23_CDT";
//		String LABAST6_R23_CDT_DrugName = LABAST6_R23_CDT+delimiter1+"DrugName_LABAST6_R23_CDT";
//		String LABAST5_R23 = "LABAST5_R23";
//		String LABAST5_R23_DateCollected = LABAST5_R23+delimiter1+"DateCollected_LABAST5_R23";
//		String LABAST14_R23 = "LABAST14_R23";
//		String LABAST14_R23_DateReported = LABAST14_R23+delimiter1+"DateReported_LABAST14_R23";
//		String LABAST3_R23_CDT = "LABAST3_R23_CDT";
//		String LABAST3_R23_CDT_SpecimenSource = LABAST3_R23_CDT+delimiter1+"SpecimenSource_LABAST3_R23_CDT";
//		String LABAST8_R23_CD = "LABAST8_R23_CD";
//		String LABAST8_R23_CD_Result = LABAST8_R23_CD+delimiter1+"Result_LABAST8_R23_CD";
//		String LABAST7_R23_CDT = "LABAST7_R23_CDT";
//		String LABAST7_R23_CDT_TestMethodOptional = LABAST7_R23_CDT+delimiter1+"TestMethodOptional_LABAST7_R23_CDT";
//		
//		String LABAST6_R24_CDT = "LABAST6_R24_CDT";
//		String LABAST6_R24_CDT_DrugName = LABAST6_R24_CDT+delimiter1+"DrugName_LABAST6_R24_CDT";
//		String LABAST5_R24 = "LABAST5_R24";
//		String LABAST5_R24_DateCollected = LABAST5_R24+delimiter1+"DateCollected_LABAST5_R24";
//		String LABAST14_R24 = "LABAST14_R24";
//		String LABAST14_R24_DateReported = LABAST14_R24+delimiter1+"DateReported_LABAST14_R24";
//		String LABAST3_R24_CDT = "LABAST3_R24_CDT";
//		String LABAST3_R24_CDT_SpecimenSource = LABAST3_R24_CDT+delimiter1+"SpecimenSource_LABAST3_R24_CDT";
//		String LABAST8_R24_CD = "LABAST8_R24_CD";
//		String LABAST8_R24_CD_Result = LABAST8_R24_CD+delimiter1+"Result_LABAST8_R24_CD";
//		String LABAST7_R24_CDT = "LABAST7_R24_CDT";
//		String LABAST7_R24_CDT_TestMethodOptional = LABAST7_R24_CDT+delimiter1+"TestMethodOptional_LABAST7_R24_CDT";
//		
//		String LABAST6_R25_CDT = "LABAST6_R25_CDT";
//		String LABAST6_R25_CDT_DrugName = LABAST6_R25_CDT+delimiter1+"DrugName_LABAST6_R25_CDT";
//		String LABAST5_R25 = "LABAST5_R25";
//		String LABAST5_R25_DateCollected = LABAST5_R25+delimiter1+"DateCollected_LABAST5_R25";
//		String LABAST14_R25 = "LABAST14_R25";
//		String LABAST14_R25_DateReported = LABAST14_R25+delimiter1+"DateReported_LABAST14_R25";
//		String LABAST3_R25_CDT = "LABAST3_R25_CDT";
//		String LABAST3_R25_CDT_SpecimenSource = LABAST3_R25_CDT+delimiter1+"SpecimenSource_LABAST3_R25_CDT";
//		String LABAST8_R25_CD = "LABAST8_R25_CD";
//		String LABAST8_R25_CD_Result = LABAST8_R25_CD+delimiter1+"Result_LABAST8_R25_CD";
//		String LABAST7_R25_CDT = "LABAST7_R25_CDT";
//		String LABAST7_R25_CDT_TestMethodOptional = LABAST7_R25_CDT+delimiter1+"TestMethodOptional_LABAST7_R25_CDT";
//		
//		String LABAST6_R26_CDT = "LABAST6_R26_CDT";
//		String LABAST6_R26_CDT_DrugName = LABAST6_R26_CDT+delimiter1+"DrugName_LABAST6_R26_CDT";
//		String LABAST5_R26 = "LABAST5_R26";
//		String LABAST5_R26_DateCollected = LABAST5_R26+delimiter1+"DateCollected_LABAST5_R26";
//		String LABAST14_R26 = "LABAST14_R26";
//		String LABAST14_R26_DateReported = LABAST14_R26+delimiter1+"DateReported_LABAST14_R26";
//		String LABAST3_R26_CDT = "LABAST3_R26_CDT";
//		String LABAST3_R26_CDT_SpecimenSource = LABAST3_R26_CDT+delimiter1+"SpecimenSource_LABAST3_R26_CDT";
//		String LABAST8_R26_CD = "LABAST8_R26_CD";
//		String LABAST8_R26_CD_Result = LABAST8_R26_CD+delimiter1+"Result_LABAST8_R26_CD";
//		String LABAST7_R26_CDT = "LABAST7_R26_CDT";
//		String LABAST7_R26_CDT_TestMethodOptional = LABAST7_R26_CDT+delimiter1+"TestMethodOptional_LABAST7_R26_CDT";
//		
//		String LABAST6_R27_CDT = "LABAST6_R27_CDT";
//		String LABAST6_R27_CDT_DrugName = LABAST6_R27_CDT+delimiter1+"DrugName_LABAST6_R27_CDT";
//		String LABAST5_R27 = "LABAST5_R27";
//		String LABAST5_R27_DateCollected = LABAST5_R27+delimiter1+"DateCollected_LABAST5_R27";
//		String LABAST14_R27 = "LABAST14_R27";
//		String LABAST14_R27_DateReported = LABAST14_R27+delimiter1+"DateReported_LABAST14_R27";
//		String LABAST3_R27_CDT = "LABAST3_R27_CDT";
//		String LABAST3_R27_CDT_SpecimenSource = LABAST3_R27_CDT+delimiter1+"SpecimenSource_LABAST3_R27_CDT";
//		String LABAST8_R27_CD = "LABAST8_R27_CD";
//		String LABAST8_R27_CD_Result = LABAST8_R27_CD+delimiter1+"Result_LABAST8_R27_CD";
//		String LABAST7_R27_CDT = "LABAST7_R27_CDT";
//		String LABAST7_R27_CDT_TestMethodOptional = LABAST7_R27_CDT+delimiter1+"TestMethodOptional_LABAST7_R27_CDT";
//		
//		String LABAST6_R28_CDT = "LABAST6_R28_CDT";
//		String LABAST6_R28_CDT_DrugName = LABAST6_R28_CDT+delimiter1+"DrugName_LABAST6_R28_CDT";
//		String LABAST5_R28 = "LABAST5_R28";
//		String LABAST5_R28_DateCollected = LABAST5_R28+delimiter1+"DateCollected_LABAST5_R28";
//		String LABAST14_R28 = "LABAST14_R28";
//		String LABAST14_R28_DateReported = LABAST14_R28+delimiter1+"DateReported_LABAST14_R28";
//		String LABAST3_R28_CDT = "LABAST3_R28_CDT";
//		String LABAST3_R28_CDT_SpecimenSource = LABAST3_R28_CDT+delimiter1+"SpecimenSource_LABAST3_R28_CDT";
//		String LABAST8_R28_CD = "LABAST8_R28_CD";
//		String LABAST8_R28_CD_Result = LABAST8_R28_CD+delimiter1+"Result_LABAST8_R28_CD";
//		String LABAST7_R28_CDT = "LABAST7_R28_CDT";
//		String LABAST7_R28_CDT_TestMethodOptional = LABAST7_R28_CDT+delimiter1+"TestMethodOptional_LABAST7_R28_CDT";
//		
//		String LABAST6_R29_CDT = "LABAST6_R29_CDT";
//		String LABAST6_R29_CDT_DrugName = LABAST6_R29_CDT+delimiter1+"DrugName_LABAST6_R29_CDT";
//		String LABAST5_R29 = "LABAST5_R29";
//		String LABAST5_R29_DateCollected = LABAST5_R29+delimiter1+"DateCollected_LABAST5_R29";
//		String LABAST14_R29 = "LABAST14_R29";
//		String LABAST14_R29_DateReported = LABAST14_R29+delimiter1+"DateReported_LABAST14_R29";
//		String LABAST3_R29_CDT = "LABAST3_R29_CDT";
//		String LABAST3_R29_CDT_SpecimenSource = LABAST3_R29_CDT+delimiter1+"SpecimenSource_LABAST3_R29_CDT";
//		String LABAST8_R29_CD = "LABAST8_R29_CD";
//		String LABAST8_R29_CD_Result = LABAST8_R29_CD+delimiter1+"Result_LABAST8_R29_CD";
//		String LABAST7_R29_CDT = "LABAST7_R29_CDT";
//		String LABAST7_R29_CDT_TestMethodOptional = LABAST7_R29_CDT+delimiter1+"TestMethodOptional_LABAST7_R29_CDT";
//		
//		String LABAST6_R30_CDT = "LABAST6_R30_CDT";
//		String LABAST6_R30_CDT_DrugName = LABAST6_R30_CDT+delimiter1+"DrugName_LABAST6_R30_CDT";
//		String LABAST5_R30 = "LABAST5_R30";
//		String LABAST5_R30_DateCollected = LABAST5_R30+delimiter1+"DateCollected_LABAST5_R30";
//		String LABAST14_R30 = "LABAST14_R30";
//		String LABAST14_R30_DateReported = LABAST14_R30+delimiter1+"DateReported_LABAST14_R30";
//		String LABAST3_R30_CDT = "LABAST3_R30_CDT";
//		String LABAST3_R30_CDT_SpecimenSource = LABAST3_R30_CDT+delimiter1+"SpecimenSource_LABAST3_R30_CDT";
//		String LABAST8_R30_CD = "LABAST8_R30_CD";
//		String LABAST8_R30_CD_Result = LABAST8_R30_CD+delimiter1+"Result_LABAST8_R30_CD";
//		String LABAST7_R30_CDT = "LABAST7_R30_CDT";
//		String LABAST7_R30_CDT_TestMethodOptional = LABAST7_R30_CDT+delimiter1+"TestMethodOptional_LABAST7_R30_CDT";
//		
//		String LABAST6_R31_CDT = "LABAST6_R31_CDT";
//		String LABAST6_R31_CDT_DrugName = LABAST6_R31_CDT+delimiter1+"DrugName_LABAST6_R31_CDT";
//		String LABAST5_R31 = "LABAST5_R31";
//		String LABAST5_R31_DateCollected = LABAST5_R31+delimiter1+"DateCollected_LABAST5_R31";
//		String LABAST14_R31 = "LABAST14_R31";
//		String LABAST14_R31_DateReported = LABAST14_R31+delimiter1+"DateReported_LABAST14_R31";
//		String LABAST3_R31_CDT = "LABAST3_R31_CDT";
//		String LABAST3_R31_CDT_SpecimenSource = LABAST3_R31_CDT+delimiter1+"SpecimenSource_LABAST3_R31_CDT";
//		String LABAST8_R31_CD = "LABAST8_R31_CD";
//		String LABAST8_R31_CD_Result = LABAST8_R31_CD+delimiter1+"Result_LABAST8_R31_CD";
//		String LABAST7_R31_CDT = "LABAST7_R31_CDT";
//		String LABAST7_R31_CDT_TestMethodOptional = LABAST7_R31_CDT+delimiter1+"TestMethodOptional_LABAST7_R31_CDT";
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R15_CDT",LABAST6_R15_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R15_CDT_DrugName",LABAST6_R15_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R15",LABAST5_R15 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R15_DateCollected",LABAST5_R15_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R15",LABAST14_R15 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R15_DateReported",LABAST14_R15_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R15_CDT",LABAST3_R15_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R15_CDT_SpecimenSource",LABAST3_R15_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R15_CD",LABAST8_R15_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R15_CD_Result",LABAST8_R15_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R15_CDT",LABAST7_R15_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R15_CDT_TestMethodOptional",LABAST7_R15_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R16_CDT",LABAST6_R16_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R16_CDT_DrugName",LABAST6_R16_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R16",LABAST5_R16 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R16_DateCollected",LABAST5_R16_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R16",LABAST14_R16 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R16_DateReported",LABAST14_R16_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R16_CDT",LABAST3_R16_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R16_CDT_SpecimenSource",LABAST3_R16_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R16_CD",LABAST8_R16_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R16_CD_Result",LABAST8_R16_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R16_CDT",LABAST7_R16_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R16_CDT_TestMethodOptional",LABAST7_R16_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R17_CDT",LABAST6_R17_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R17_CDT_DrugName",LABAST6_R17_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R17",LABAST5_R17 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R17_DateCollected",LABAST5_R17_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R17",LABAST14_R17 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R17_DateReported",LABAST14_R17_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R17_CDT",LABAST3_R17_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R17_CDT_SpecimenSource",LABAST3_R17_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R17_CD",LABAST8_R17_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R17_CD_Result",LABAST8_R17_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R17_CDT",LABAST7_R17_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R17_CDT_TestMethodOptional",LABAST7_R17_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R18_CDT",LABAST6_R18_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R18_CDT_DrugName",LABAST6_R18_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R18",LABAST5_R18 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R18_DateCollected",LABAST5_R18_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R18",LABAST14_R18 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R18_DateReported",LABAST14_R18_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R18_CDT",LABAST3_R18_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R18_CDT_SpecimenSource",LABAST3_R18_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R18_CD",LABAST8_R18_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R18_CD_Result",LABAST8_R18_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R18_CDT",LABAST7_R18_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R18_CDT_TestMethodOptional",LABAST7_R18_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R19_CDT",LABAST6_R19_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R19_CDT_DrugName",LABAST6_R19_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R19",LABAST5_R19 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R19_DateCollected",LABAST5_R19_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R19",LABAST14_R19 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R19_DateReported",LABAST14_R19_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R19_CDT",LABAST3_R19_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R19_CDT_SpecimenSource",LABAST3_R19_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R19_CD",LABAST8_R19_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R19_CD_Result",LABAST8_R19_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R19_CDT",LABAST7_R19_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R19_CDT_TestMethodOptional",LABAST7_R19_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R20_CDT",LABAST6_R20_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R20_CDT_DrugName",LABAST6_R20_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R20",LABAST5_R20 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R20_DateCollected",LABAST5_R20_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R20",LABAST14_R20 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R20_DateReported",LABAST14_R20_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R20_CDT",LABAST3_R20_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R20_CDT_SpecimenSource",LABAST3_R20_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R20_CD",LABAST8_R20_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R20_CD_Result",LABAST8_R20_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R20_CDT",LABAST7_R20_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R20_CDT_TestMethodOptional",LABAST7_R20_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R21_CDT",LABAST6_R21_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R21_CDT_DrugName",LABAST6_R21_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R21",LABAST5_R21 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R21_DateCollected",LABAST5_R21_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R21",LABAST14_R21 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R21_DateReported",LABAST14_R21_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R21_CDT",LABAST3_R21_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R21_CDT_SpecimenSource",LABAST3_R21_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R21_CD",LABAST8_R21_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R21_CD_Result",LABAST8_R21_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R21_CDT",LABAST7_R21_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R21_CDT_TestMethodOptional",LABAST7_R21_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R22_CDT",LABAST6_R22_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R22_CDT_DrugName",LABAST6_R22_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R22",LABAST5_R22 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R22_DateCollected",LABAST5_R22_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R22",LABAST14_R22 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R22_DateReported",LABAST14_R22_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R22_CDT",LABAST3_R22_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R22_CDT_SpecimenSource",LABAST3_R22_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R22_CD",LABAST8_R22_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R22_CD_Result",LABAST8_R22_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R22_CDT",LABAST7_R22_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R22_CDT_TestMethodOptional",LABAST7_R22_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R23_CDT",LABAST6_R23_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R23_CDT_DrugName",LABAST6_R23_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R23",LABAST5_R23 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R23_DateCollected",LABAST5_R23_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R23",LABAST14_R23 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R23_DateReported",LABAST14_R23_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R23_CDT",LABAST3_R23_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R23_CDT_SpecimenSource",LABAST3_R23_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R23_CD",LABAST8_R23_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R23_CD_Result",LABAST8_R23_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R23_CDT",LABAST7_R23_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R23_CDT_TestMethodOptional",LABAST7_R23_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R24_CDT",LABAST6_R24_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R24_CDT_DrugName",LABAST6_R24_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R24",LABAST5_R24 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R24_DateCollected",LABAST5_R24_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R24",LABAST14_R24 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R24_DateReported",LABAST14_R24_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R24_CDT",LABAST3_R24_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R24_CDT_SpecimenSource",LABAST3_R24_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R24_CD",LABAST8_R24_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R24_CD_Result",LABAST8_R24_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R24_CDT",LABAST7_R24_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R24_CDT_TestMethodOptional",LABAST7_R24_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R25_CDT",LABAST6_R25_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R25_CDT_DrugName",LABAST6_R25_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R25",LABAST5_R25 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R25_DateCollected",LABAST5_R25_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R25",LABAST14_R25 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R25_DateReported",LABAST14_R25_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R25_CDT",LABAST3_R25_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R25_CDT_SpecimenSource",LABAST3_R25_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R25_CD",LABAST8_R25_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R25_CD_Result",LABAST8_R25_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R25_CDT",LABAST7_R25_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R25_CDT_TestMethodOptional",LABAST7_R25_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R26_CDT",LABAST6_R26_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R26_CDT_DrugName",LABAST6_R26_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R26",LABAST5_R26 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R26_DateCollected",LABAST5_R26_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R26",LABAST14_R26 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R26_DateReported",LABAST14_R26_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R26_CDT",LABAST3_R26_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R26_CDT_SpecimenSource",LABAST3_R26_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R26_CD",LABAST8_R26_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R26_CD_Result",LABAST8_R26_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R26_CDT",LABAST7_R26_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R26_CDT_TestMethodOptional",LABAST7_R26_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R27_CDT",LABAST6_R27_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R27_CDT_DrugName",LABAST6_R27_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R27",LABAST5_R27 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R27_DateCollected",LABAST5_R27_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R27",LABAST14_R27 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R27_DateReported",LABAST14_R27_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R27_CDT",LABAST3_R27_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R27_CDT_SpecimenSource",LABAST3_R27_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R27_CD",LABAST8_R27_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R27_CD_Result",LABAST8_R27_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R27_CDT",LABAST7_R27_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R27_CDT_TestMethodOptional",LABAST7_R27_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R28_CDT",LABAST6_R28_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R28_CDT_DrugName",LABAST6_R28_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R28",LABAST5_R28 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R28_DateCollected",LABAST5_R28_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R28",LABAST14_R28 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R28_DateReported",LABAST14_R28_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R28_CDT",LABAST3_R28_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R28_CDT_SpecimenSource",LABAST3_R28_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R28_CD",LABAST8_R28_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R28_CD_Result",LABAST8_R28_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R28_CDT",LABAST7_R28_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R28_CDT_TestMethodOptional",LABAST7_R28_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R29_CDT",LABAST6_R29_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R29_CDT_DrugName",LABAST6_R29_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R29",LABAST5_R29 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R29_DateCollected",LABAST5_R29_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R29",LABAST14_R29 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R29_DateReported",LABAST14_R29_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R29_CDT",LABAST3_R29_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R29_CDT_SpecimenSource",LABAST3_R29_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R29_CD",LABAST8_R29_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R29_CD_Result",LABAST8_R29_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R29_CDT",LABAST7_R29_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R29_CDT_TestMethodOptional",LABAST7_R29_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R30_CDT",LABAST6_R30_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R30_CDT_DrugName",LABAST6_R30_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R30",LABAST5_R30 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R30_DateCollected",LABAST5_R30_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R30",LABAST14_R30 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R30_DateReported",LABAST14_R30_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R30_CDT",LABAST3_R30_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R30_CDT_SpecimenSource",LABAST3_R30_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R30_CD",LABAST8_R30_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R30_CD_Result",LABAST8_R30_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R30_CDT",LABAST7_R30_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R30_CDT_TestMethodOptional",LABAST7_R30_CDT_TestMethodOptional );
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R31_CDT",LABAST6_R31_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST6_R31_CDT_DrugName",LABAST6_R31_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R31",LABAST5_R31 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST5_R31_DateCollected",LABAST5_R31_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R31",LABAST14_R31 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST14_R31_DateReported",LABAST14_R31_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R31_CDT",LABAST3_R31_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST3_R31_CDT_SpecimenSource",LABAST3_R31_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R31_CD",LABAST8_R31_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST8_R31_CD_Result",LABAST8_R31_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R31_CDT",LABAST7_R31_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LABAST7_R31_CDT_TestMethodOptional",LABAST7_R31_CDT_TestMethodOptional );
//
//		
//	 }
//	 
//	
//
//	@Test
//	public void initializeGenotypicPhenotypic_part2_test() throws Exception{
//			
//		
//		
//			System.out.println("******************* Starting test case named: InitializeGenotypicPhenotypic_part2_test *******************");
//				
//			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeGenotypicPhenotypic_part2");
//		
//			
//			int sizeMap = 0;
//			int expectedSize = 102;
//			
//			if(LTBIFieldsMap!=null)
//				sizeMap = LTBIFieldsMap.size();
//			
//			
//		//	Assert.assertNotEquals(0, sizeMap);//At least one initialized
//			Assert.assertEquals(expectedSize, sizeMap);
//			
//
//			
//			System.out.println(iteration+ ": PASSED");
//			System.out.println("******************* End test case named: InitializeGenotypicPhenotypic_part2_test *******************");
//			
//		}	
//	
//	
//}
//
//
//
//
//
////initializeGenotypicMolecular();
//
//
//
//
//
//
///**
// * InitializeGenotypicMolecular_test: this method will test InitializeGenotypicMolecular. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
// * 
// * @author Fatima.Lopezcalzado
// *
// */
//@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
//@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
//@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
//@PowerMockIgnore("javax.management.*")
//public static class InitializeGenotypicMolecular_test{
//	
//
//	private String iteration;
//	private int size;
//	
// public InitializeGenotypicMolecular_test(String it, int size){
//	 
//	 super();
//	 //Common Parameters
//	// this.type = type;
//	 this.iteration = it;
//	 this.size = size;
//	
// }
//
// 
//   @Parameterized.Parameters
//   public static Collection input() {
//	   /*Parameters for validateRule1_test*/
//	   
//	   int it = 0;
//	   
//	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
//	   //The areas are the lines where the new method escapeCharacterSequence method is called
//      return Arrays.asList(new Object[][]{
//    		  
//    		    
//    		  {"initializeGenotypicMolecular_test"+"_"+it++,0}
//    		  
//    		  
//    		 
//	  });
//   }
// 
// 
// 
///*
// * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
// * */
//
//   
//    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
//   
//
//	@Mock
//	PropertyUtil propertyUtilMocked;
//	
//	@Mock
//	LogUtils loggerMock;
//	
//	@Spy
//	@InjectMocks
//	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());
//
//	 @Before
//	 public void initMocks() throws Exception {
//
//	
//		 PowerMockito.spy(CDCLTBIForm.class);
//		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
//		 
//		 loggerMock = Mockito.mock(LogUtils.class);
//
//		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
//		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
//		 
//		
//		String delimiter1 = "__";
//
//		String INV1148_CD = "INV1148_CD";
//		String INV1148_CD_WasGenOrMoleDrugSuscepTest = INV1148_CD+delimiter1+"WasGenOrMoleDrugSuscepTest_INV1148_CD";
//		String code48018_6_R1_CDT = "48018_6_R1_CDT";
//		String code48018_6_R1_CDT_GeneName = code48018_6_R1_CDT+delimiter1+"GeneName_48018_6_R1_CDT";
//		String LAB682_R1 = "LAB682_R1";
//		String LAB682_R1_DateCollected = LAB682_R1+delimiter1+"DateCollected_LAB682_R1";
//		String LAB683_R1 = "LAB683_R1";
//		String LAB683_R1_DateReported = LAB683_R1+delimiter1+"DateReported_LAB683_R1";
//		String LAB684_R1_CDT = "LAB684_R1_CDT";
//		String LAB684_R1_CDT_SpecimenSource = LAB684_R1_CDT+delimiter1+"SpecimenSource_LAB684_R1_CDT";
//		String LAB685_R1_CD = "LAB685_R1_CD";
//		String LAB685_R1_CD_Result = LAB685_R1_CD+delimiter1+"Result_LAB685_R1_CD";
//		String LAB686_R1 = "LAB686_R1";
//		String LAB686_R1_NucleicAcidChange = LAB686_R1+delimiter1+"NucleicAcidChange_LAB686_R1";
//		String LAB687_R1 = "LAB687_R1";
//		String LAB687_R1_AminoAcidChange = LAB687_R1+delimiter1+"AminoAcidChange_LAB687_R1";
//		String LAB688_R1_CD = "LAB688_R1_CD";
//		String LAB688_R1_CD_Indel = LAB688_R1_CD+delimiter1+"Indel_LAB688_R1_CD";
//		String LAB689_R1_CD = "LAB689_R1_CD";
//		String LAB689_R1_CD_TestType = LAB689_R1_CD+delimiter1+"TestType_LAB689_R1_CD";
//		String LAB689_R1_OTH = "LAB689_R1_OTH";
//		String LAB689_R1_OTH_TestType = LAB689_R1_OTH+delimiter1+"TestType_LAB689_R1_OTH";
//		String code48018_6_R2_CDT = "48018_6_R2_CDT";
//		String code48018_6_R2_CDT_GeneName = code48018_6_R2_CDT+delimiter1+"GeneName_48018_6_R2_CDT";
//		String LAB682_R2 = "LAB682_R2";
//		String LAB682_R2_DateCollected = LAB682_R2+delimiter1+"DateCollected_LAB682_R2";
//		String LAB683_R2 = "LAB683_R2";
//		String LAB683_R2_DateReported = LAB683_R2+delimiter1+"DateReported_LAB683_R2";
//		String LAB684_R2_CDT = "LAB684_R2_CDT";
//		String LAB684_R2_CDT_SpecimenSource = LAB684_R2_CDT+delimiter1+"SpecimenSource_LAB684_R2_CDT";
//		String LAB685_R2_CD = "LAB685_R2_CD";
//		String LAB685_R2_CD_Result = LAB685_R2_CD+delimiter1+"Result_LAB685_R2_CD";
//		String LAB686_R2 = "LAB686_R2";
//		String LAB686_R2_NucleicAcidChange = LAB686_R2+delimiter1+"NucleicAcidChange_LAB686_R2";
//		String LAB687_R2 = "LAB687_R2";
//		String LAB687_R2_AminoAcidChange = LAB687_R2+delimiter1+"AminoAcidChange_LAB687_R2";
//		String LAB688_R2_CD = "LAB688_R2_CD";
//		String LAB688_R2_CD_Indel = LAB688_R2_CD+delimiter1+"Indel_LAB688_R2_CD";
//		String LAB689_R2_CD = "LAB689_R2_CD";
//		String LAB689_R2_CD_TestType = LAB689_R2_CD+delimiter1+"TestType_LAB689_R2_CD";
//		String LAB689_R2_OTH = "LAB689_R2_OTH";
//		String LAB689_R2_OTH_TestType = LAB689_R2_OTH+delimiter1+"TestType_LAB689_R2_OTH";
//		String code48018_6_R3_CDT = "48018_6_R3_CDT";
//		String code48018_6_R3_CDT_GeneName = code48018_6_R3_CDT+delimiter1+"GeneName_48018_6_R3_CDT";
//		String LAB682_R3 = "LAB682_R3";
//		String LAB682_R3_DateCollected = LAB682_R3+delimiter1+"DateCollected_LAB682_R3";
//		String LAB683_R3 = "LAB683_R3";
//		String LAB683_R3_DateReported = LAB683_R3+delimiter1+"DateReported_LAB683_R3";
//		String LAB684_R3_CDT = "LAB684_R3_CDT";
//		String LAB684_R3_CDT_SpecimenSource = LAB684_R3_CDT+delimiter1+"SpecimenSource_LAB684_R3_CDT";
//		String LAB685_R3_CD = "LAB685_R3_CD";
//		String LAB685_R3_CD_Result = LAB685_R3_CD+delimiter1+"Result_LAB685_R3_CD";
//		String LAB686_R3 = "LAB686_R3";
//		String LAB686_R3_NucleicAcidChange = LAB686_R3+delimiter1+"NucleicAcidChange_LAB686_R3";
//		String LAB687_R3 = "LAB687_R3";
//		String LAB687_R3_AminoAcidChange = LAB687_R3+delimiter1+"AminoAcidChange_LAB687_R3";
//		String LAB688_R3_CD = "LAB688_R3_CD";
//		String LAB688_R3_CD_Indel = LAB688_R3_CD+delimiter1+"Indel_LAB688_R3_CD";
//		String LAB689_R3_CD = "LAB689_R3_CD";
//		String LAB689_R3_CD_TestType = LAB689_R3_CD+delimiter1+"TestType_LAB689_R3_CD";
//		String LAB689_R3_OTH = "LAB689_R3_OTH";
//		String LAB689_R3_OTH_TestType = LAB689_R3_OTH+delimiter1+"TestType_LAB689_R3_OTH";
//		String code48018_6_R4_CDT = "48018_6_R4_CDT";
//		String code48018_6_R4_CDT_GeneName = code48018_6_R4_CDT+delimiter1+"GeneName_48018_6_R4_CDT";
//		String LAB682_R4 = "LAB682_R4";
//		String LAB682_R4_DateCollected = LAB682_R4+delimiter1+"DateCollected_LAB682_R4";
//		String LAB683_R4 = "LAB683_R4";
//		String LAB683_R4_DateReported = LAB683_R4+delimiter1+"DateReported_LAB683_R4";
//		String LAB684_R4_CDT = "LAB684_R4_CDT";
//		String LAB684_R4_CDT_SpecimenSource = LAB684_R4_CDT+delimiter1+"SpecimenSource_LAB684_R4_CDT";
//		String LAB685_R4_CD = "LAB685_R4_CD";
//		String LAB685_R4_CD_Result = LAB685_R4_CD+delimiter1+"Result_LAB685_R4_CD";
//		String LAB686_R4 = "LAB686_R4";
//		String LAB686_R4_NucleicAcidChange = LAB686_R4+delimiter1+"NucleicAcidChange_LAB686_R4";
//		String LAB687_R4 = "LAB687_R4";
//		String LAB687_R4_AminoAcidChange = LAB687_R4+delimiter1+"AminoAcidChange_LAB687_R4";
//		String LAB688_R4_CD = "LAB688_R4_CD";
//		String LAB688_R4_CD_Indel = LAB688_R4_CD+delimiter1+"Indel_LAB688_R4_CD";
//		String LAB689_R4_CD = "LAB689_R4_CD";
//		String LAB689_R4_CD_TestType = LAB689_R4_CD+delimiter1+"TestType_LAB689_R4_CD";
//		String LAB689_R4_OTH = "LAB689_R4_OTH";
//		String LAB689_R4_OTH_TestType = LAB689_R4_OTH+delimiter1+"TestType_LAB689_R4_OTH";
//		String code48018_6_R5_CDT = "48018_6_R5_CDT";
//		String code48018_6_R5_CDT_GeneName = code48018_6_R5_CDT+delimiter1+"GeneName_48018_6_R5_CDT";
//		String LAB682_R5 = "LAB682_R5";
//		String LAB682_R5_DateCollected = LAB682_R5+delimiter1+"DateCollected_LAB682_R5";
//		String LAB683_R5 = "LAB683_R5";
//		String LAB683_R5_DateReported = LAB683_R5+delimiter1+"DateReported_LAB683_R5";
//		String LAB684_R5_CDT = "LAB684_R5_CDT";
//		String LAB684_R5_CDT_SpecimenSource = LAB684_R5_CDT+delimiter1+"SpecimenSource_LAB684_R5_CDT";
//		String LAB685_R5_CD = "LAB685_R5_CD";
//		String LAB685_R5_CD_Result = LAB685_R5_CD+delimiter1+"Result_LAB685_R5_CD";
//		String LAB686_R5 = "LAB686_R5";
//		String LAB686_R5_NucleicAcidChange = LAB686_R5+delimiter1+"NucleicAcidChange_LAB686_R5";
//		String LAB687_R5 = "LAB687_R5";
//		String LAB687_R5_AminoAcidChange = LAB687_R5+delimiter1+"AminoAcidChange_LAB687_R5";
//		String LAB688_R5_CD = "LAB688_R5_CD";
//		String LAB688_R5_CD_Indel = LAB688_R5_CD+delimiter1+"Indel_LAB688_R5_CD";
//		String LAB689_R5_CD = "LAB689_R5_CD";
//		String LAB689_R5_CD_TestType = LAB689_R5_CD+delimiter1+"TestType_LAB689_R5_CD";
//		String LAB689_R5_OTH = "LAB689_R5_OTH";
//		String LAB689_R5_OTH_TestType = LAB689_R5_OTH+delimiter1+"TestType_LAB689_R5_OTH";
//		String code48018_6_R6_CDT = "48018_6_R6_CDT";
//		String code48018_6_R6_CDT_GeneName = code48018_6_R6_CDT+delimiter1+"GeneName_48018_6_R6_CDT";
//		String LAB682_R6 = "LAB682_R6";
//		String LAB682_R6_DateCollected = LAB682_R6+delimiter1+"DateCollected_LAB682_R6";
//		String LAB683_R6 = "LAB683_R6";
//		String LAB683_R6_DateReported = LAB683_R6+delimiter1+"DateReported_LAB683_R6";
//		String LAB684_R6_CDT = "LAB684_R6_CDT";
//		String LAB684_R6_CDT_SpecimenSource = LAB684_R6_CDT+delimiter1+"SpecimenSource_LAB684_R6_CDT";
//		String LAB685_R6_CD = "LAB685_R6_CD";
//		String LAB685_R6_CD_Result = LAB685_R6_CD+delimiter1+"Result_LAB685_R6_CD";
//		String LAB686_R6 = "LAB686_R6";
//		String LAB686_R6_NucleicAcidChange = LAB686_R6+delimiter1+"NucleicAcidChange_LAB686_R6";
//		String LAB687_R6 = "LAB687_R6";
//		String LAB687_R6_AminoAcidChange = LAB687_R6+delimiter1+"AminoAcidChange_LAB687_R6";
//		String LAB688_R6_CD = "LAB688_R6_CD";
//		String LAB688_R6_CD_Indel = LAB688_R6_CD+delimiter1+"Indel_LAB688_R6_CD";
//		String LAB689_R6_CD = "LAB689_R6_CD";
//		String LAB689_R6_CD_TestType = LAB689_R6_CD+delimiter1+"TestType_LAB689_R6_CD";
//		String LAB689_R6_OTH = "LAB689_R6_OTH";
//		String LAB689_R6_OTH_TestType = LAB689_R6_OTH+delimiter1+"TestType_LAB689_R6_OTH";
//		String code48018_6_R7_CDT = "48018_6_R7_CDT";
//		String code48018_6_R7_CDT_GeneName = code48018_6_R7_CDT+delimiter1+"GeneName_48018_6_R7_CDT";
//		String LAB682_R7 = "LAB682_R7";
//		String LAB682_R7_DateCollected = LAB682_R7+delimiter1+"DateCollected_LAB682_R7";
//		String LAB683_R7 = "LAB683_R7";
//		String LAB683_R7_DateReported = LAB683_R7+delimiter1+"DateReported_LAB683_R7";
//		String LAB684_R7_CDT = "LAB684_R7_CDT";
//		String LAB684_R7_CDT_SpecimenSource = LAB684_R7_CDT+delimiter1+"SpecimenSource_LAB684_R7_CDT";
//		String LAB685_R7_CD = "LAB685_R7_CD";
//		String LAB685_R7_CD_Result = LAB685_R7_CD+delimiter1+"Result_LAB685_R7_CD";
//		String LAB686_R7 = "LAB686_R7";
//		String LAB686_R7_NucleicAcidChange = LAB686_R7+delimiter1+"NucleicAcidChange_LAB686_R7";
//		String LAB687_R7 = "LAB687_R7";
//		String LAB687_R7_AminoAcidChange = LAB687_R7+delimiter1+"AminoAcidChange_LAB687_R7";
//		String LAB688_R7_CD = "LAB688_R7_CD";
//		String LAB688_R7_CD_Indel = LAB688_R7_CD+delimiter1+"Indel_LAB688_R7_CD";
//		String LAB689_R7_CD = "LAB689_R7_CD";
//		String LAB689_R7_CD_TestType = LAB689_R7_CD+delimiter1+"TestType_LAB689_R7_CD";
//		String LAB689_R7_OTH = "LAB689_R7_OTH";
//		String LAB689_R7_OTH_TestType = LAB689_R7_OTH+delimiter1+"TestType_LAB689_R7_OTH";
//		String code48018_6_R8_CDT = "48018_6_R8_CDT";
//		String code48018_6_R8_CDT_GeneName = code48018_6_R8_CDT+delimiter1+"GeneName_48018_6_R8_CDT";
//		String LAB682_R8 = "LAB682_R8";
//		String LAB682_R8_DateCollected = LAB682_R8+delimiter1+"DateCollected_LAB682_R8";
//		String LAB683_R8 = "LAB683_R8";
//		String LAB683_R8_DateReported = LAB683_R8+delimiter1+"DateReported_LAB683_R8";
//		String LAB684_R8_CDT = "LAB684_R8_CDT";
//		String LAB684_R8_CDT_SpecimenSource = LAB684_R8_CDT+delimiter1+"SpecimenSource_LAB684_R8_CDT";
//		String LAB685_R8_CD = "LAB685_R8_CD";
//		String LAB685_R8_CD_Result = LAB685_R8_CD+delimiter1+"Result_LAB685_R8_CD";
//		String LAB686_R8 = "LAB686_R8";
//		String LAB686_R8_NucleicAcidChange = LAB686_R8+delimiter1+"NucleicAcidChange_LAB686_R8";
//		String LAB687_R8 = "LAB687_R8";
//		String LAB687_R8_AminoAcidChange = LAB687_R8+delimiter1+"AminoAcidChange_LAB687_R8";
//		String LAB688_R8_CD = "LAB688_R8_CD";
//		String LAB688_R8_CD_Indel = LAB688_R8_CD+delimiter1+"Indel_LAB688_R8_CD";
//		String LAB689_R8_CD = "LAB689_R8_CD";
//		String LAB689_R8_CD_TestType = LAB689_R8_CD+delimiter1+"TestType_LAB689_R8_CD";
//		String LAB689_R8_OTH = "LAB689_R8_OTH";
//		String LAB689_R8_OTH_TestType = LAB689_R8_OTH+delimiter1+"TestType_LAB689_R8_OTH";
//		//MDR TB Case
//		String INV1275_CD = "INV1275_CD";
//		String INV1275_CD_TreatedAsMDR = INV1275_CD+delimiter1+"TreatedAsMDR_INV1275_CD";
//
//		
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1148_CD",INV1148_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1148_CD_WasGenOrMoleDrugSuscepTest",INV1148_CD_WasGenOrMoleDrugSuscepTest );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R1_CDT",code48018_6_R1_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R1_CDT_GeneName",code48018_6_R1_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R1",LAB682_R1 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R1_DateCollected",LAB682_R1_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R1",LAB683_R1 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R1_DateReported",LAB683_R1_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R1_CDT",LAB684_R1_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R1_CDT_SpecimenSource",LAB684_R1_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R1_CD",LAB685_R1_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R1_CD_Result",LAB685_R1_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R1",LAB686_R1 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R1_NucleicAcidChange",LAB686_R1_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R1",LAB687_R1 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R1_AminoAcidChange",LAB687_R1_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R1_CD",LAB688_R1_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R1_CD_Indel",LAB688_R1_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R1_CD",LAB689_R1_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R1_CD_TestType",LAB689_R1_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R1_OTH",LAB689_R1_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R1_OTH_TestType",LAB689_R1_OTH_TestType );
//
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R2_CDT",code48018_6_R2_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R2_CDT_GeneName",code48018_6_R2_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R2",LAB682_R2 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R2_DateCollected",LAB682_R2_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R2",LAB683_R2 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R2_DateReported",LAB683_R2_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R2_CDT",LAB684_R2_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R2_CDT_SpecimenSource",LAB684_R2_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R2_CD",LAB685_R2_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R2_CD_Result",LAB685_R2_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R2",LAB686_R2 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R2_NucleicAcidChange",LAB686_R2_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R2",LAB687_R2 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R2_AminoAcidChange",LAB687_R2_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R2_CD",LAB688_R2_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R2_CD_Indel",LAB688_R2_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R2_CD",LAB689_R2_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R2_CD_TestType",LAB689_R2_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R2_OTH",LAB689_R2_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R2_OTH_TestType",LAB689_R2_OTH_TestType );
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R3_CDT",code48018_6_R3_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R3_CDT_GeneName",code48018_6_R3_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R3",LAB682_R3 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R3_DateCollected",LAB682_R3_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R3",LAB683_R3 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R3_DateReported",LAB683_R3_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R3_CDT",LAB684_R3_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R3_CDT_SpecimenSource",LAB684_R3_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R3_CD",LAB685_R3_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R3_CD_Result",LAB685_R3_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R3",LAB686_R3 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R3_NucleicAcidChange",LAB686_R3_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R3",LAB687_R3 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R3_AminoAcidChange",LAB687_R3_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R3_CD",LAB688_R3_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R3_CD_Indel",LAB688_R3_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R3_CD",LAB689_R3_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R3_CD_TestType",LAB689_R3_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R3_OTH",LAB689_R3_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R3_OTH_TestType",LAB689_R3_OTH_TestType );
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R4_CDT",code48018_6_R4_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R4_CDT_GeneName",code48018_6_R4_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R4",LAB682_R4 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R4_DateCollected",LAB682_R4_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R4",LAB683_R4 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R4_DateReported",LAB683_R4_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R4_CDT",LAB684_R4_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R4_CDT_SpecimenSource",LAB684_R4_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R4_CD",LAB685_R4_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R4_CD_Result",LAB685_R4_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R4",LAB686_R4 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R4_NucleicAcidChange",LAB686_R4_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R4",LAB687_R4 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R4_AminoAcidChange",LAB687_R4_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R4_CD",LAB688_R4_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R4_CD_Indel",LAB688_R4_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R4_CD",LAB689_R4_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R4_CD_TestType",LAB689_R4_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R4_OTH",LAB689_R4_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R4_OTH_TestType",LAB689_R4_OTH_TestType );
//
//
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R5_CDT",code48018_6_R5_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R5_CDT_GeneName",code48018_6_R5_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R5",LAB682_R5 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R5_DateCollected",LAB682_R5_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R5",LAB683_R5 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R5_DateReported",LAB683_R5_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R5_CDT",LAB684_R5_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R5_CDT_SpecimenSource",LAB684_R5_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R5_CD",LAB685_R5_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R5_CD_Result",LAB685_R5_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R5",LAB686_R5 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R5_NucleicAcidChange",LAB686_R5_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R5",LAB687_R5 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R5_AminoAcidChange",LAB687_R5_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R5_CD",LAB688_R5_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R5_CD_Indel",LAB688_R5_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R5_CD",LAB689_R5_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R5_CD_TestType",LAB689_R5_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R5_OTH",LAB689_R5_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R5_OTH_TestType",LAB689_R5_OTH_TestType );
//
//
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R6_CDT",code48018_6_R6_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R6_CDT_GeneName",code48018_6_R6_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R6",LAB682_R6 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R6_DateCollected",LAB682_R6_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R6",LAB683_R6 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R6_DateReported",LAB683_R6_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R6_CDT",LAB684_R6_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R6_CDT_SpecimenSource",LAB684_R6_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R6_CD",LAB685_R6_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R6_CD_Result",LAB685_R6_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R6",LAB686_R6 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R6_NucleicAcidChange",LAB686_R6_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R6",LAB687_R6 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R6_AminoAcidChange",LAB687_R6_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R6_CD",LAB688_R6_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R6_CD_Indel",LAB688_R6_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R6_CD",LAB689_R6_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R6_CD_TestType",LAB689_R6_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R6_OTH",LAB689_R6_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R6_OTH_TestType",LAB689_R6_OTH_TestType );
//
//
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R7_CDT",code48018_6_R7_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R7_CDT_GeneName",code48018_6_R7_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R7",LAB682_R7 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R7_DateCollected",LAB682_R7_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R7",LAB683_R7 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R7_DateReported",LAB683_R7_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R7_CDT",LAB684_R7_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R7_CDT_SpecimenSource",LAB684_R7_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R7_CD",LAB685_R7_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R7_CD_Result",LAB685_R7_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R7",LAB686_R7 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R7_NucleicAcidChange",LAB686_R7_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R7",LAB687_R7 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R7_AminoAcidChange",LAB687_R7_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R7_CD",LAB688_R7_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R7_CD_Indel",LAB688_R7_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R7_CD",LAB689_R7_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R7_CD_TestType",LAB689_R7_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R7_OTH",LAB689_R7_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R7_OTH_TestType",LAB689_R7_OTH_TestType );
//
//
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R8_CDT",code48018_6_R8_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code48018_6_R8_CDT_GeneName",code48018_6_R8_CDT_GeneName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R8",LAB682_R8 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB682_R8_DateCollected",LAB682_R8_DateCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R8",LAB683_R8 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB683_R8_DateReported",LAB683_R8_DateReported );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R8_CDT",LAB684_R8_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB684_R8_CDT_SpecimenSource",LAB684_R8_CDT_SpecimenSource );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R8_CD",LAB685_R8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB685_R8_CD_Result",LAB685_R8_CD_Result );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R8",LAB686_R8 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB686_R8_NucleicAcidChange",LAB686_R8_NucleicAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R8",LAB687_R8 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB687_R8_AminoAcidChange",LAB687_R8_AminoAcidChange );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R8_CD",LAB688_R8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB688_R8_CD_Indel",LAB688_R8_CD_Indel );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R8_CD",LAB689_R8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R8_CD_TestType",LAB689_R8_CD_TestType );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R8_OTH",LAB689_R8_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "LAB689_R8_OTH_TestType",LAB689_R8_OTH_TestType );
//
//
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1275_CD",INV1275_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1275_CD_TreatedAsMDR",INV1275_CD_TreatedAsMDR );
//
//		///////
//		
//
//	 }
//	 
//	
//
//	@Test
//	public void initializeGenotypicMolecular_test() throws Exception{
//			
//		
//		
//			System.out.println("******************* Starting test case named: initializeGenotypicMolecular_test *******************");
//				
//			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeGenotypicMolecular");
//		
//			
//			int sizeMap = 0;
//			int expectedSize = 82;
//			
//			if(LTBIFieldsMap!=null)
//				sizeMap = LTBIFieldsMap.size();
//			
//			
//		//	Assert.assertNotEquals(0, sizeMap);//At least one initialized
//			Assert.assertEquals(expectedSize, sizeMap);
//			
//
//			
//			System.out.println(iteration+ ": PASSED");
//			System.out.println("******************* End test case named: initializeGenotypicMolecular_test *******************");
//			
//		}	
//	
//	
//}
//
//
//
///**
// * InitializeCaseOutcome_test: this method will test InitializeCaseOutcome. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
// * 
// * @author Fatima.Lopezcalzado
// *
// */
//@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
//@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
//@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
//@PowerMockIgnore("javax.management.*")
//public static class InitializeCaseOutcome_test{
//	
//
//	private String iteration;
//	private int size;
//	
// public InitializeCaseOutcome_test(String it, int size){
//	 
//	 super();
//	 //Common Parameters
//	// this.type = type;
//	 this.iteration = it;
//	 this.size = size;
//	
// }
//
// 
//   @Parameterized.Parameters
//   public static Collection input() {
//	   /*Parameters for validateRule1_test*/
//	   
//	   int it = 0;
//	   
//	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
//	   //The areas are the lines where the new method escapeCharacterSequence method is called
//      return Arrays.asList(new Object[][]{
//    		  
//    		    
//    		  {"initializeCaseOutcome_test"+"_"+it++,0}
//    		  
//    		  
//    		 
//	  });
//   }
// 
// 
// 
///*
// * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
// * */
//
//   
//    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
//   
//
//	@Mock
//	PropertyUtil propertyUtilMocked;
//	
//	@Mock
//	LogUtils loggerMock;
//	
//	@Spy
//	@InjectMocks
//	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());
//
//	 @Before
//	 public void initMocks() throws Exception {
//
//	
//		 PowerMockito.spy(CDCLTBIForm.class);
//		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
//		 
//		 loggerMock = Mockito.mock(LogUtils.class);
//
//		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
//		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
//		 
//		
//		String delimiter1 = "__";
//		String INV1149_CD = "INV1149_CD";
//		String INV1149_CD_SputumCultureConversionDocumented = INV1149_CD+delimiter1+"SputumCultureConversionDocumented_INV1149_CD";
//		String INV1150 = "INV1150";
//		String INV1150_dateSpecimenCollected = INV1150+delimiter1+" dateSpecimenCollected_INV1150";
//		String INV1151_CD = "INV1151_CD";
//		String INV1151_CD_reasonForNotDocumenting = INV1151_CD+delimiter1+"reasonForNotDocumenting_INV1151_CD";
//		String INV1151Oth = "INV1151_OTH";
//		String INV1151Oth_OtherSpecify = INV1151Oth+delimiter1+"OtherSpecify_INV1151_OTH";
//		String TB279_CD = "TB279_CD";
//		String TB279_CD_MovedDuringTherapy = TB279_CD+delimiter1+"MovedDuringTherapy_TB279_CD";
//		String INV1152_CD = "INV1152_CD";
//		String INV1152_CD_IfYesMovedToWhere = INV1152_CD+delimiter1+"IfYesMovedToWhere_INV1152_CD";
//		String INV1152_OS_CD = "INV1152_OS_CD";
//		String INV1152_OS_CD_IfYesMovedToWhere = INV1152_OS_CD+delimiter1+"IfYesMovedToWhere_INV1152_OS_CD";
//		String INV1152_OUS_CD = "INV1152_OUS_CD";
//		String INV1152_OUS_CD_IfYesMovedToWhere = INV1152_OUS_CD+delimiter1+"IfYesMovedToWhere_INV1152_OUS_CD";
//		String INV1153_CDT = "INV1153_CDT";
//		String INV1153_CDT_IfOutOfStateSpecifyDestination = INV1153_CDT+delimiter1+"IfOutOfStateSpecifyDestination_INV1153_CDT";
//		String INV1155_CD = "INV1155_CD";
//		String INV1155_CD_TransnationalReferralMade = INV1155_CD+delimiter1+"TransnationalReferralMade_INV1155_CD";
//		String INV1154_CDT = "INV1154_CDT";
//		String INV1154_CDT_IfOutOfCountrySpecifyDestination = INV1154_CDT+delimiter1+"IfOutOfCountrySpecifyDestination_INV1154_CDT";
//		String code413947000 = "413947000";
//		String code413947000_DateTherapyStopped = 413947000+delimiter1+"DateTherapyStopped_413947000";
//		String INV1140_CD = "INV1140_CD";
//		String INV1140_CD_ReasonTherapyStoppedOrNeverStarted = INV1140_CD+delimiter1+"ReasonTherapyStoppedOrNeverStarted_INV1140_CD";
//		String INV1140_OTH = "INV1140_OTH";
//		String INV1140_OTH_ReasonTherapyStoppedOrNeverStarted = INV1140_OTH+delimiter1+"ReasonTherapyStoppedOrNeverStartedOtherSpecify_INV1140_OTH";
//		String INV1141_CD = "INV1141_CD";
//		String INV1141_CD_ReasonTbTherapyExtended = INV1141_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_CD";
//		String INV1141_I_CD = "INV1141_I_CD";
//		String INV1141_I_CD_ReasonTbTherapyExtended = INV1141_I_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_I_CD";
//		String INV1141_A_CD = "INV1141_A_CD";
//		String INV1141_A_CD_ReasonTbTherapyExtended = INV1141_A_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_A_CD";
//		String INV1141_C_CD = "INV1141_C_CD";
//		String INV1141_C_CD_ReasonTbTherapyExtended = INV1141_C_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_C_CD";
//		String INV1141_F_CD = "INV1141_F_CD";
//		String INV1141_F_CD_ReasonTbTherapyExtended = INV1141_F_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_F_CD";
//		String INV1141_N_CD = "INV1141_N_CD";
//		String INV1141_N_CD_ReasonTbTherapyExtended = INV1141_N_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_N_CD";
//		String INV1141_U_CD = "INV1141_U_CD";
//		String INV1141_U_CD_ReasonTbTherapyExtended = INV1141_U_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_U_CD";
//		String INV1141_O_CD = "INV1141_O_CD";
//		String INV1141_O_CD_ReasonTbTherapyExtended = INV1141_O_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_O_CD";
//		String INV1141_OTH = "INV1141_OTH";
//		String INV1141_OTH_ReasonTbTherapyExtended = INV1141_OTH+delimiter1+"ReasonTbTherapyExtended_INV1141_OTH";
//		String code55753_8_CD = "55753_8_CD";
//		String code55753_8_CD_TreatmentAdministration = code55753_8_CD+delimiter1+"TreatmentAdministration_55753_8_CD";
//		String code55753_8_D_CD = "55753_8_D_CD";
//		String code55753_8_D_CD_TreatmentAdministration = code55753_8_D_CD+delimiter1+"TreatmentAdministration_55753_8_D_CD";
//		String code55753_8_E_CD = "55753_8_E_CD";
//		String code55753_8_E_CD_TreatmentAdministration = code55753_8_E_CD+delimiter1+"TreatmentAdministration_55753_8_E_CD";
//		String code55753_8_S_CD = "55753_8_S_CD";
//		String code55753_8_S_CD_TreatmentAdministration = code55753_8_S_CD+delimiter1+"TreatmentAdministration_55753_8_S_CD";
//		String DEM127_CD = "DEM127_CD";
//		String DEM127_CD_DidThePatientDied = DEM127_CD+delimiter1+"DidThePatientDied_DEM127_CD";
//		String DEM128 = "DEM128";
//		String DEM128_DateOfDeath = DEM128+delimiter1+"DateOfDeath_DEM128";
//		String INV145_CD = "INV145_CD";
//		String INV145_CD_DidTBOrComplicationsOfTBTreatmentContributeToDeath = INV145_CD+delimiter1+"DidTBOrComplicationsOfTBTreatmentContributeToDeath_INV145_CD";
//		String INV167 = "INV167";
//		String INV167_InvestigationComments = INV167+delimiter1+"InvestigationComments_INV167";
//
//		
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1149_CD",INV1149_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1149_CD_SputumCultureConversionDocumented",INV1149_CD_SputumCultureConversionDocumented );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1150",INV1150 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1150_dateSpecimenCollected",INV1150_dateSpecimenCollected );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1151_CD",INV1151_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1151_CD_reasonForNotDocumenting",INV1151_CD_reasonForNotDocumenting );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1151Oth",INV1151Oth );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1151Oth_OtherSpecify",INV1151Oth_OtherSpecify );
//		Whitebox.setInternalState(CDCLTBIForm.class, "TB279_CD",TB279_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "TB279_CD_MovedDuringTherapy",TB279_CD_MovedDuringTherapy );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1152_CD",INV1152_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1152_CD_IfYesMovedToWhere",INV1152_CD_IfYesMovedToWhere );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1152_OS_CD",INV1152_OS_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1152_OS_CD_IfYesMovedToWhere",INV1152_OS_CD_IfYesMovedToWhere );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1152_OUS_CD",INV1152_OUS_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1152_OUS_CD_IfYesMovedToWhere",INV1152_OUS_CD_IfYesMovedToWhere );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1153_CDT",INV1153_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1153_CDT_IfOutOfStateSpecifyDestination",INV1153_CDT_IfOutOfStateSpecifyDestination );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1155_CD",INV1155_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1155_CD_TransnationalReferralMade",INV1155_CD_TransnationalReferralMade );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1154_CDT",INV1154_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1154_CDT_IfOutOfCountrySpecifyDestination",INV1154_CDT_IfOutOfCountrySpecifyDestination );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code413947000",code413947000 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code413947000_DateTherapyStopped",code413947000_DateTherapyStopped );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1140_CD",INV1140_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1140_CD_ReasonTherapyStoppedOrNeverStarted",INV1140_CD_ReasonTherapyStoppedOrNeverStarted );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1140_OTH",INV1140_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1140_OTH_ReasonTherapyStoppedOrNeverStarted",INV1140_OTH_ReasonTherapyStoppedOrNeverStarted );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_CD",INV1141_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_CD_ReasonTbTherapyExtended",INV1141_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_I_CD",INV1141_I_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_I_CD_ReasonTbTherapyExtended",INV1141_I_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_A_CD",INV1141_A_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_A_CD_ReasonTbTherapyExtended",INV1141_A_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_C_CD",INV1141_C_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_C_CD_ReasonTbTherapyExtended",INV1141_C_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_F_CD",INV1141_F_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_F_CD_ReasonTbTherapyExtended",INV1141_F_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_N_CD",INV1141_N_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_N_CD_ReasonTbTherapyExtended",INV1141_N_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_U_CD",INV1141_U_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_U_CD_ReasonTbTherapyExtended",INV1141_U_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_O_CD",INV1141_O_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_O_CD_ReasonTbTherapyExtended",INV1141_O_CD_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_OTH",INV1141_OTH );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1141_OTH_ReasonTbTherapyExtended",INV1141_OTH_ReasonTbTherapyExtended );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_CD",code55753_8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_CD_TreatmentAdministration",code55753_8_CD_TreatmentAdministration );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_D_CD",code55753_8_D_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_D_CD_TreatmentAdministration",code55753_8_D_CD_TreatmentAdministration );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_E_CD",code55753_8_E_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_E_CD_TreatmentAdministration",code55753_8_E_CD_TreatmentAdministration );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_S_CD",code55753_8_S_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code55753_8_S_CD_TreatmentAdministration",code55753_8_S_CD_TreatmentAdministration );
//		Whitebox.setInternalState(CDCLTBIForm.class, "DEM127_CD",DEM127_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "DEM127_CD_DidThePatientDied",DEM127_CD_DidThePatientDied );
//		Whitebox.setInternalState(CDCLTBIForm.class, "DEM128",DEM128 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "DEM128_DateOfDeath",DEM128_DateOfDeath );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV145_CD",INV145_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV145_CD_DidTBOrComplicationsOfTBTreatmentContributeToDeath",INV145_CD_DidTBOrComplicationsOfTBTreatmentContributeToDeath );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV167",INV167 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV167_InvestigationComments",INV167_InvestigationComments );
//
//		///////
//		
//
//	 }
//	 
//	
//
//	@Test
//	public void initializeCaseOutcome_test() throws Exception{
//			
//		
//		
//			System.out.println("******************* Starting test case named: initializeCaseOutcome_test *******************");
//				
//			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeCaseOutcome");
//		
//			
//			int sizeMap = 0;
//			int expectedSize = 31;
//			
//			if(LTBIFieldsMap!=null)
//				sizeMap = LTBIFieldsMap.size();
//			
//			
//		//	Assert.assertNotEquals(0, sizeMap);//At least one initialized
//			Assert.assertEquals(expectedSize, sizeMap);
//			
//			System.out.println(iteration+ ": PASSED");
//			System.out.println("******************* End test case named: initializeCaseOutcome_test *******************");
//			
//		}	
//	
//	
//}
//
//
//
//
//
///**
// * initializeMDRTBSupplemental_test: this method will test initializeMDRTBSupplemental. It will pass if the size of the map is the expected, meaning, the number of pairs inserted in the map is the expected one.
// * 
// * @author Fatima.Lopezcalzado
// *
// */
//@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm","gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm","gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.webapp.nbs.form.page.PageForm"})
//@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
//@PrepareForTest({CDCLTBIForm.class,LogUtils.class, PDDocument.class})
//@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
//@PowerMockIgnore("javax.management.*")
//public static class InitializeMDRTBSupplemental_test{
//	
//
//	private String iteration;
//	private int size;
//	
// public InitializeMDRTBSupplemental_test(String it, int size){
//	 
//	 super();
//	 //Common Parameters
//	// this.type = type;
//	 this.iteration = it;
//	 this.size = size;
//	
// }
//
// 
//   @Parameterized.Parameters
//   public static Collection input() {
//	   /*Parameters for validateRule1_test*/
//	   
//	   int it = 0;
//	   
//	  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
//	   //The areas are the lines where the new method escapeCharacterSequence method is called
//      return Arrays.asList(new Object[][]{
//    		  
//    		    
//    		  {"initializeMDRTBSupplemental_test"+"_"+it++,0}
//    		  
//    		  
//    		 
//	  });
//   }
// 
// 
// 
///*
// * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
// * */
//
//   
//    Map<String, String> LTBIFieldsMap= new HashMap<String, String>();
//   
//
//	@Mock
//	PropertyUtil propertyUtilMocked;
//	
//	@Mock
//	LogUtils loggerMock;
//	
//	@Spy
//	@InjectMocks
//	CDCLTBIForm cdcLtbiForm = Mockito.spy(new CDCLTBIForm());
//
//	 @Before
//	 public void initMocks() throws Exception {
//
//	
//		 PowerMockito.spy(CDCLTBIForm.class);
//		 propertyUtilMocked = Mockito.mock(PropertyUtil.class);
//		 
//		 loggerMock = Mockito.mock(LogUtils.class);
//
//		 Whitebox.setInternalState(CDCLTBIForm.class, "logger", loggerMock);
//		 Whitebox.setInternalState(CDCLTBIForm.class, "LTBIFieldsMap", LTBIFieldsMap);
//		 
//		
//		String delimiter1 = "__";
//
//		
//		String INV1156_CD = "INV1156_CD";
//		String INV1156_CD_HistoryTreatmentBeforeCurrentEpisode = INV1156_CD+delimiter1+"HistoryTreatmentBeforeCurrentEpisode_INV1156_CD";
//		String INV1157 = "INV1157";
//		String INV1157_DateMDRTBTherapyStartedForCurrentEpisode = INV1157+delimiter1+"DateMDRTBTherapyStartedForCurrentEpisode_INV1157";
//		String INV1158_R1_CDT = "INV1158_R1_CDT";
//		String INV1158_R1_CDT_DrugName = INV1158_R1_CDT+delimiter1+"DrugName_INV1158_R1_CDT";
//		String INV1159_R1_CD = "INV1159_R1_CD";
//		String INV1159_R1_CD_LengthTimAdministered = INV1159_R1_CD+delimiter1+"LengthTimAdministered_INV1159_R1_CD";
//		String INV1158_R2_CDT = "INV1158_R2_CDT";
//		String INV1158_R2_CDT_DrugName = INV1158_R2_CDT+delimiter1+"DrugName_INV1158_R2_CDT";
//		String INV1159_R2_CD = "INV1159_R2_CD";
//		String INV1159_R2_CD_LengthTimAdministered = INV1159_R2_CD+delimiter1+"LengthTimAdministered_INV1159_R2_CD";
//		String INV1158_R3_CDT = "INV1158_R3_CDT";
//		String INV1158_R3_CDT_DrugName = INV1158_R3_CDT+delimiter1+"DrugName_INV1158_R3_CDT";
//		String INV1159_R3_CD = "INV1159_R3_CD";
//		String INV1159_R3_CD_LengthTimAdministered = INV1159_R3_CD+delimiter1+"LengthTimAdministered_INV1159_R3_CD";
//		String INV1158_R4_CDT = "INV1158_R4_CDT";
//		String INV1158_R4_CDT_DrugName = INV1158_R4_CDT+delimiter1+"DrugName_INV1158_R4_CDT";
//		String INV1159_R4_CD = "INV1159_R4_CD";
//		String INV1159_R4_CD_LengthTimAdministered = INV1159_R4_CD+delimiter1+"LengthTimAdministered_INV1159_R4_CD";
//		String INV1158_R5_CDT = "INV1158_R5_CDT";
//		String INV1158_R5_CDT_DrugName = INV1158_R5_CDT+delimiter1+"DrugName_INV1158_R5_CDT";
//		String INV1159_R5_CD = "INV1159_R5_CD";
//		String INV1159_R5_CD_LengthTimAdministered = INV1159_R5_CD+delimiter1+"LengthTimAdministered_INV1159_R5_CD";
//		String INV1158_R6_CDT = "INV1158_R6_CDT";
//		String INV1158_R6_CDT_DrugName = INV1158_R6_CDT+delimiter1+"DrugName_INV1158_R6_CDT";
//		String INV1159_R6_CD = "INV1159_R6_CD";
//		String INV1159_R6_CD_LengthTimAdministered = INV1159_R6_CD+delimiter1+"LengthTimAdministered_INV1159_R6_CD";
//		String INV1158_R7_CDT = "INV1158_R7_CDT";
//		String INV1158_R7_CDT_DrugName = INV1158_R7_CDT+delimiter1+"DrugName_INV1158_R7_CDT";
//		String INV1159_R7_CD = "INV1159_R7_CD";
//		String INV1159_R7_CD_LengthTimAdministered = INV1159_R7_CD+delimiter1+"LengthTimAdministered_INV1159_R7_CD";
//		String INV1158_R8_CDT = "INV1158_R8_CDT";
//		String INV1158_R8_CDT_DrugName = INV1158_R8_CDT+delimiter1+"DrugName_INV1158_R8_CDT";
//		String INV1159_R8_CD = "INV1159_R8_CD";
//		String INV1159_R8_CD_LengthTimAdministered = INV1159_R8_CD+delimiter1+"LengthTimAdministered_INV1159_R8_CD";
//		String INV1158_R9_CDT = "INV1158_R9_CDT";
//		String INV1158_R9_CDT_DrugName = INV1158_R9_CDT+delimiter1+"DrugName_INV1158_R9_CDT";
//		String INV1159_R9_CD = "INV1159_R9_CD";
//		String INV1159_R9_CD_LengthTimAdministered = INV1159_R9_CD+delimiter1+"LengthTimAdministered_INV1159_R9_CD";
//		String INV1158_R10_CDT = "INV1158_R10_CDT";
//		String INV1158_R10_CDT_DrugName = INV1158_R10_CDT+delimiter1+"DrugName_INV1158_R10_CDT";
//		String INV1159_R10_CD = "INV1159_R10_CD";
//		String INV1159_R10_CD_LengthTimAdministered = INV1159_R10_CD+delimiter1+"LengthTimAdministered_INV1159_R10_CD";
//		String INV1158_R11_CDT = "INV1158_R11_CDT";
//		String INV1158_R11_CDT_DrugName = INV1158_R11_CDT+delimiter1+"DrugName_INV1158_R11_CDT";
//		String INV1159_R11_CD = "INV1159_R11_CD";
//		String INV1159_R11_CD_LengthTimAdministered = INV1159_R11_CD+delimiter1+"LengthTimAdministered_INV1159_R11_CD";
//		String INV1158_R12_CDT = "INV1158_R12_CDT";
//		String INV1158_R12_CDT_DrugName = INV1158_R12_CDT+delimiter1+"DrugName_INV1158_R12_CDT";
//		String INV1159_R12_CD = "INV1159_R12_CD";
//		String INV1159_R12_CD_LengthTimAdministered = INV1159_R12_CD+delimiter1+"LengthTimAdministered_INV1159_R12_CD";
//		String INV1158_R13_CDT = "INV1158_R13_CDT";
//		String INV1158_R13_CDT_DrugName = INV1158_R13_CDT+delimiter1+"DrugName_INV1158_R13_CDT";
//		String INV1159_R13_CD = "INV1159_R13_CD";
//		String INV1159_R13_CD_LengthTimAdministered = INV1159_R13_CD+delimiter1+"LengthTimAdministered_INV1159_R13_CD";
//		String INV1158_R14_CDT = "INV1158_R14_CDT";
//		String INV1158_R14_CDT_DrugName = INV1158_R14_CDT+delimiter1+"DrugName_INV1158_R14_CDT";
//		String INV1159_R14_CD = "INV1159_R14_CD";
//		String INV1159_R14_CD_LengthTimAdministered = INV1159_R14_CD+delimiter1+"LengthTimAdministered_INV1159_R14_CD";
//		String INV1158_R15_CDT = "INV1158_R15_CDT";
//		String INV1158_R15_CDT_DrugName = INV1158_R15_CDT+delimiter1+"DrugName_INV1158_R15_CDT";
//		String INV1159_R15_CD = "INV1159_R15_CD";
//		String INV1159_R15_CD_LengthTimAdministered = INV1159_R15_CD+delimiter1+"LengthTimAdministered_INV1159_R15_CD";
//		String INV1158_R16_CDT = "INV1158_R16_CDT";
//		String INV1158_R16_CDT_DrugName = INV1158_R16_CDT+delimiter1+"DrugName_INV1158_R16_CDT";
//		String INV1159_R16_CD = "INV1159_R16_CD";
//		String INV1159_R16_CD_LengthTimAdministered = INV1159_R16_CD+delimiter1+"LengthTimAdministered_INV1159_R16_CD";
//		String INV1158_R17_CDT = "INV1158_R17_CDT";
//		String INV1158_R17_CDT_DrugName = INV1158_R17_CDT+delimiter1+"DrugName_INV1158_R17_CDT";
//		String INV1159_R17_CD = "INV1159_R17_CD";
//		String INV1159_R17_CD_LengthTimAdministered = INV1159_R17_CD+delimiter1+"LengthTimAdministered_INV1159_R17_CD";
//		String INV1158_R18_CDT = "INV1158_R18_CDT";
//		String INV1158_R18_CDT_DrugName = INV1158_R18_CDT+delimiter1+"DrugName_INV1158_R18_CDT";
//		String INV1159_R18_CD = "INV1159_R18_CD";
//		String INV1159_R18_CD_LengthTimAdministered = INV1159_R18_CD+delimiter1+"LengthTimAdministered_INV1159_R18_CD";
//		String INV1158_R19_CDT = "INV1158_R19_CDT";
//		String INV1158_R19_CDT_DrugName = INV1158_R19_CDT+delimiter1+"DrugName_INV1158_R19_CDT";
//		String INV1159_R19_CD = "INV1159_R19_CD";
//		String INV1159_R19_CD_LengthTimAdministered = INV1159_R19_CD+delimiter1+"LengthTimAdministered_INV1159_R19_CD";
//		String INV1158_R20_CDT = "INV1158_R20_CDT";
//		String INV1158_R20_CDT_DrugName = INV1158_R20_CDT+delimiter1+"DrugName_INV1158_R20_CDT";
//		String INV1159_R20_CD = "INV1159_R20_CD";
//		String INV1159_R20_CD_LengthTimAdministered = INV1159_R20_CD+delimiter1+"LengthTimAdministered_INV1159_R20_CD";
//		String INV1158_R21_CDT = "INV1158_R21_CDT";
//		String INV1158_R21_CDT_DrugName = INV1158_R21_CDT+delimiter1+"DrugName_INV1158_R21_CDT";
//		String INV1159_R21_CD = "INV1159_R21_CD";
//		String INV1159_R21_CD_LengthTimAdministered = INV1159_R21_CD+delimiter1+"LengthTimAdministered_INV1159_R21_CD";
//		String INV1158_R22_CDT = "INV1158_R22_CDT";
//		String INV1158_R22_CDT_DrugName = INV1158_R22_CDT+delimiter1+"DrugName_INV1158_R22_CDT";
//		String INV1159_R22_CD = "INV1159_R22_CD";
//		String INV1159_R22_CD_LengthTimAdministered = INV1159_R22_CD+delimiter1+"LengthTimAdministered_INV1159_R22_CD";
//		String INV1158_R23_CDT = "INV1158_R23_CDT";
//		String INV1158_R23_CDT_DrugName = INV1158_R23_CDT+delimiter1+"DrugName_INV1158_R23_CDT";
//		String INV1159_R23_CD = "INV1159_R23_CD";
//		String INV1159_R23_CD_LengthTimAdministered = INV1159_R23_CD+delimiter1+"LengthTimAdministered_INV1159_R23_CD";
//		String INV1158_R24_CDT = "INV1158_R24_CDT";
//		String INV1158_R24_CDT_DrugName = INV1158_R24_CDT+delimiter1+"DrugName_INV1158_R24_CDT";
//		String INV1159_R24_CD = "INV1159_R24_CD";
//		String INV1159_R24_CD_LengthTimAdministered = INV1159_R24_CD+delimiter1+"LengthTimAdministered_INV1159_R24_CD";
//		String INV1158_R25_CDT = "INV1158_R25_CDT";
//		String INV1158_R25_CDT_DrugName = INV1158_R25_CDT+delimiter1+"DrugName_INV1158_R25_CDT";
//		String INV1159_R25_CD = "INV1159_R25_CD";
//		String INV1159_R25_CD_LengthTimAdministered = INV1159_R25_CD+delimiter1+"LengthTimAdministered_INV1159_R25_CD";
//		String INV1158_R26_CDT = "INV1158_R26_CDT";
//		String INV1158_R26_CDT_DrugName = INV1158_R26_CDT+delimiter1+"DrugName_INV1158_R26_CDT";
//		String INV1159_R26_CD = "INV1159_R26_CD";
//		String INV1159_R26_CD_LengthTimAdministered = INV1159_R26_CD+delimiter1+"LengthTimAdministered_INV1159_R26_CD";
//		String INV1158_R27_CDT = "INV1158_R27_CDT";
//		String INV1158_R27_CDT_DrugName = INV1158_R27_CDT+delimiter1+"DrugName_INV1158_R27_CDT";
//		String INV1159_R27_CD = "INV1159_R27_CD";
//		String INV1159_R27_CD_LengthTimAdministered = INV1159_R27_CD+delimiter1+"LengthTimAdministered_INV1159_R27_CD";
//		String INV1158_R28_CDT = "INV1158_R28_CDT";
//		String INV1158_R28_CDT_DrugName = INV1158_R28_CDT+delimiter1+"DrugName_INV1158_R28_CDT";
//		String INV1159_R28_CD = "INV1159_R28_CD";
//		String INV1159_R28_CD_LengthTimAdministered = INV1159_R28_CD+delimiter1+"LengthTimAdministered_INV1159_R28_CD";
//		String INV1158_R29_CDT = "INV1158_R29_CDT";
//		String INV1158_R29_CDT_DrugName = INV1158_R29_CDT+delimiter1+"DrugName_INV1158_R29_CDT";
//		String INV1159_R29_CD = "INV1159_R29_CD";
//		String INV1159_R29_CD_LengthTimAdministered = INV1159_R29_CD+delimiter1+"LengthTimAdministered_INV1159_R29_CD";
//		String INV1158_R30_CDT = "INV1158_R30_CDT";
//		String INV1158_R30_CDT_DrugName = INV1158_R30_CDT+delimiter1+"DrugName_INV1158_R30_CDT";
//		String INV1159_R30_CD = "INV1159_R30_CD";
//		String INV1159_R30_CD_LengthTimAdministered = INV1159_R30_CD+delimiter1+"LengthTimAdministered_INV1159_R30_CD";
//		String INV1160 = "INV1160";
//		String INV1160_DateInjectableMedicationWasStopped = INV1160+delimiter1+"DateInjectableMedicationWasStopped_INV1160";
//		String INV1161_CD = "INV1161_CD";
//		String INV1161_CD_WasSurgeryPerformedToTreatMDRTB = INV1161_CD+delimiter1+"WasSurgeryPerformedToTreatMDRTB_INV1161_CD";
//		String INV1162 = "INV1162";
//		String INV1162_DateOfSurgery = INV1162+delimiter1+"DateOfSurgery_INV1162";
//		String code42563_7_CD_R1_CDT = "42563_7_CD_R1_CDT";
//		String code42563_7_CD_R1_CDT_SideEffect = code42563_7_CD_R1_CDT+delimiter1+"SideEffect_42563_7_CD_R1_CDT";
//		String INV1164_R1_CD = "INV1164_R1_CD";
//		String INV1164_R1_CD_SideEffectExperienced = INV1164_R1_CD+delimiter1+"SideEffectExperienced_INV1164_R1_CD";
//		String INV1163_R1_CD = "INV1163_R1_CD";
//		String INV1163_R1_CD_When = INV1163_R1_CD+delimiter1+"When_INV1163_R1_CD";
//		String code42563_7_CD_R2_CDT = "42563_7_CD_R2_CDT";
//		String code42563_7_CD_R2_CDT_SideEffect = code42563_7_CD_R2_CDT+delimiter1+"SideEffect_42563_7_CD_R2_CDT";
//		String INV1164_R2_CD = "INV1164_R2_CD";
//		String INV1164_R2_CD_SideEffectExperienced = INV1164_R2_CD+delimiter1+"SideEffectExperienced_INV1164_R2_CD";
//		String INV1163_R2_CD = "INV1163_R2_CD";
//		String INV1163_R2_CD_When = INV1163_R2_CD+delimiter1+"When_INV1163_R2_CD";
//		String code42563_7_CD_R3_CDT = "42563_7_CD_R3_CDT";
//		String code42563_7_CD_R3_CDT_SideEffect = code42563_7_CD_R3_CDT+delimiter1+"SideEffect_42563_7_CD_R3_CDT";
//		String INV1164_R3_CD = "INV1164_R3_CD";
//		String INV1164_R3_CD_SideEffectExperienced = INV1164_R3_CD+delimiter1+"SideEffectExperienced_INV1164_R3_CD";
//		String INV1163_R3_CD = "INV1163_R3_CD";
//		String INV1163_R3_CD_When = INV1163_R3_CD+delimiter1+"When_INV1163_R3_CD";
//		String code42563_7_CD_R4_CDT = "42563_7_CD_R4_CDT";
//		String code42563_7_CD_R4_CDT_SideEffect = code42563_7_CD_R4_CDT+delimiter1+"SideEffect_42563_7_CD_R4_CDT";
//		String INV1164_R4_CD = "INV1164_R4_CD";
//		String INV1164_R4_CD_SideEffectExperienced = INV1164_R4_CD+delimiter1+"SideEffectExperienced_INV1164_R4_CD";
//		String INV1163_R4_CD = "INV1163_R4_CD";
//		String INV1163_R4_CD_When = INV1163_R4_CD+delimiter1+"When_INV1163_R4_CD";
//		String code42563_7_CD_R5_CDT = "42563_7_CD_R5_CDT";
//		String code42563_7_CD_R5_CDT_SideEffect = code42563_7_CD_R5_CDT+delimiter1+"SideEffect_42563_7_CD_R5_CDT";
//		String INV1164_R5_CD = "INV1164_R5_CD";
//		String INV1164_R5_CD_SideEffectExperienced = INV1164_R5_CD+delimiter1+"SideEffectExperienced_INV1164_R5_CD";
//		String INV1163_R5_CD = "INV1163_R5_CD";
//		String INV1163_R5_CD_When = INV1163_R5_CD+delimiter1+"When_INV1163_R5_CD";
//		String code42563_7_CD_R6_CDT = "42563_7_CD_R6_CDT";
//		String code42563_7_CD_R6_CDT_SideEffect = code42563_7_CD_R6_CDT+delimiter1+"SideEffect_42563_7_CD_R6_CDT";
//		String INV1164_R6_CD = "INV1164_R6_CD";
//		String INV1164_R6_CD_SideEffectExperienced = INV1164_R6_CD+delimiter1+"SideEffectExperienced_INV1164_R6_CD";
//		String INV1163_R6_CD = "INV1163_R6_CD";
//		String INV1163_R6_CD_When = INV1163_R6_CD+delimiter1+"When_INV1163_R6_CD";
//		String code42563_7_CD_R7_CDT = "42563_7_CD_R7_CDT";
//		String code42563_7_CD_R7_CDT_SideEffect = code42563_7_CD_R7_CDT+delimiter1+"SideEffect_42563_7_CD_R7_CDT";
//		String INV1164_R7_CD = "INV1164_R7_CD";
//		String INV1164_R7_CD_SideEffectExperienced = INV1164_R7_CD+delimiter1+"SideEffectExperienced_INV1164_R7_CD";
//		String INV1163_R7_CD = "INV1163_R7_CD";
//		String INV1163_R7_CD_When = INV1163_R7_CD+delimiter1+"When_INV1163_R7_CD";
//		String code42563_7_CD_R8_CDT = "42563_7_CD_R8_CDT";
//		String code42563_7_CD_R8_CDT_SideEffect = code42563_7_CD_R8_CDT+delimiter1+"SideEffect_42563_7_CD_R8_CDT";
//		String INV1164_R8_CD = "INV1164_R8_CD";
//		String INV1164_R8_CD_SideEffectExperienced = INV1164_R8_CD+delimiter1+"SideEffectExperienced_INV1164_R8_CD";
//		String INV1163_R8_CD = "INV1163_R8_CD";
//		String INV1163_R8_CD_When = INV1163_R8_CD+delimiter1+"When_INV1163_R8_CD";
//		String code42563_7_CD_R9_CDT = "42563_7_CD_R9_CDT";
//		String code42563_7_CD_R9_CDT_SideEffect = code42563_7_CD_R9_CDT+delimiter1+"SideEffect_42563_7_CD_R9_CDT";
//		String INV1164_R9_CD = "INV1164_R9_CD";
//		String INV1164_R9_CD_SideEffectExperienced = INV1164_R9_CD+delimiter1+"SideEffectExperienced_INV1164_R9_CD";
//		String INV1163_R9_CD = "INV1163_R9_CD";
//		String INV1163_R9_CD_When = INV1163_R9_CD+delimiter1+"When_INV1163_R9_CD";
//		String code42563_7_CD_R10_CDT = "42563_7_CD_R10_CDT";
//		String code42563_7_CD_R10_CDT_SideEffect = code42563_7_CD_R10_CDT+delimiter1+"SideEffect_42563_7_CD_R10_CDT";
//		String INV1164_R10_CD = "INV1164_R10_CD";
//		String INV1164_R10_CD_SideEffectExperienced = INV1164_R10_CD+delimiter1+"SideEffectExperienced_INV1164_R10_CD";
//		String INV1163_R10_CD = "INV1163_R10_CD";
//		String INV1163_R10_CD_When = INV1163_R10_CD+delimiter1+"When_INV1163_R10_CD";
//		String code42563_7_CD_R11_CDT = "42563_7_CD_R11_CDT";
//		String code42563_7_CD_R11_CDT_SideEffect = code42563_7_CD_R11_CDT+delimiter1+"SideEffect_42563_7_CD_R11_CDT";
//		String INV1164_R11_CD = "INV1164_R11_CD";
//		String INV1164_R11_CD_SideEffectExperienced = INV1164_R11_CD+delimiter1+"SideEffectExperienced_INV1164_R11_CD";
//		String INV1163_R11_CD = "INV1163_R11_CD";
//		String INV1163_R11_CD_When = INV1163_R11_CD+delimiter1+"When_INV1163_R11_CD";
//		String code42563_7_CD_R12_CDT = "42563_7_CD_R12_CDT";
//		String code42563_7_CD_R12_CDT_SideEffect = code42563_7_CD_R12_CDT+delimiter1+"SideEffect_42563_7_CD_R12_CDT";
//		String INV1164_R12_CD = "INV1164_R12_CD";
//		String INV1164_R12_CD_SideEffectExperienced = INV1164_R12_CD+delimiter1+"SideEffectExperienced_INV1164_R12_CD";
//		String INV1163_R12_CD = "INV1163_R12_CD";
//		String INV1163_R12_CD_When = INV1163_R12_CD+delimiter1+"When_INV1163_R12_CD";
//		String code42563_7_CD_R13_CDT = "42563_7_CD_R13_CDT";
//		String code42563_7_CD_R13_CDT_SideEffect = code42563_7_CD_R13_CDT+delimiter1+"SideEffect_42563_7_CD_R13_CDT";
//		String INV1164_R13_CD = "INV1164_R13_CD";
//		String INV1164_R13_CD_SideEffectExperienced = INV1164_R13_CD+delimiter1+"SideEffectExperienced_INV1164_R13_CD";
//		String INV1163_R13_CD = "INV1163_R13_CD";
//		String INV1163_R13_CD_When = INV1163_R13_CD+delimiter1+"When_INV1163_R13_CD";
//		String code42563_7_CD_R14_CDT = "42563_7_CD_R14_CDT";
//		String code42563_7_CD_R14_CDT_SideEffect = code42563_7_CD_R14_CDT+delimiter1+"SideEffect_42563_7_CD_R14_CDT";
//		String INV1164_R14_CD = "INV1164_R14_CD";
//		String INV1164_R14_CD_SideEffectExperienced = INV1164_R14_CD+delimiter1+"SideEffectExperienced_INV1164_R14_CD";
//		String INV1163_R14_CD = "INV1163_R14_CD";
//		String INV1163_R14_CD_When = INV1163_R14_CD+delimiter1+"When_INV1163_R14_CD";
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1156_CD",INV1156_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1156_CD_HistoryTreatmentBeforeCurrentEpisode",INV1156_CD_HistoryTreatmentBeforeCurrentEpisode );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1157",INV1157 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1157_DateMDRTBTherapyStartedForCurrentEpisode",INV1157_DateMDRTBTherapyStartedForCurrentEpisode );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R1_CDT",INV1158_R1_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R1_CDT_DrugName",INV1158_R1_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R1_CD",INV1159_R1_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R1_CD_LengthTimAdministered",INV1159_R1_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R2_CDT",INV1158_R2_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R2_CDT_DrugName",INV1158_R2_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R2_CD",INV1159_R2_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R2_CD_LengthTimAdministered",INV1159_R2_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R3_CDT",INV1158_R3_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R3_CDT_DrugName",INV1158_R3_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R3_CD",INV1159_R3_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R3_CD_LengthTimAdministered",INV1159_R3_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R4_CDT",INV1158_R4_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R4_CDT_DrugName",INV1158_R4_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R4_CD",INV1159_R4_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R4_CD_LengthTimAdministered",INV1159_R4_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R5_CDT",INV1158_R5_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R5_CDT_DrugName",INV1158_R5_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R5_CD",INV1159_R5_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R5_CD_LengthTimAdministered",INV1159_R5_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R6_CDT",INV1158_R6_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R6_CDT_DrugName",INV1158_R6_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R6_CD",INV1159_R6_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R6_CD_LengthTimAdministered",INV1159_R6_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R7_CDT",INV1158_R7_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R7_CDT_DrugName",INV1158_R7_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R7_CD",INV1159_R7_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R7_CD_LengthTimAdministered",INV1159_R7_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R8_CDT",INV1158_R8_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R8_CDT_DrugName",INV1158_R8_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R8_CD",INV1159_R8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R8_CD_LengthTimAdministered",INV1159_R8_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R9_CDT",INV1158_R9_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R9_CDT_DrugName",INV1158_R9_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R9_CD",INV1159_R9_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R9_CD_LengthTimAdministered",INV1159_R9_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R10_CDT",INV1158_R10_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R10_CDT_DrugName",INV1158_R10_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R10_CD",INV1159_R10_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R10_CD_LengthTimAdministered",INV1159_R10_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R11_CDT",INV1158_R11_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R11_CDT_DrugName",INV1158_R11_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R11_CD",INV1159_R11_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R11_CD_LengthTimAdministered",INV1159_R11_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R12_CDT",INV1158_R12_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R12_CDT_DrugName",INV1158_R12_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R12_CD",INV1159_R12_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R12_CD_LengthTimAdministered",INV1159_R12_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R13_CDT",INV1158_R13_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R13_CDT_DrugName",INV1158_R13_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R13_CD",INV1159_R13_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R13_CD_LengthTimAdministered",INV1159_R13_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R14_CDT",INV1158_R14_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R14_CDT_DrugName",INV1158_R14_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R14_CD",INV1159_R14_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R14_CD_LengthTimAdministered",INV1159_R14_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R15_CDT",INV1158_R15_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R15_CDT_DrugName",INV1158_R15_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R15_CD",INV1159_R15_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R15_CD_LengthTimAdministered",INV1159_R15_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R16_CDT",INV1158_R16_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R16_CDT_DrugName",INV1158_R16_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R16_CD",INV1159_R16_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R16_CD_LengthTimAdministered",INV1159_R16_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R17_CDT",INV1158_R17_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R17_CDT_DrugName",INV1158_R17_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R17_CD",INV1159_R17_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R17_CD_LengthTimAdministered",INV1159_R17_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R18_CDT",INV1158_R18_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R18_CDT_DrugName",INV1158_R18_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R18_CD",INV1159_R18_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R18_CD_LengthTimAdministered",INV1159_R18_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R19_CDT",INV1158_R19_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R19_CDT_DrugName",INV1158_R19_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R19_CD",INV1159_R19_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R19_CD_LengthTimAdministered",INV1159_R19_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R20_CDT",INV1158_R20_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R20_CDT_DrugName",INV1158_R20_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R20_CD",INV1159_R20_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R20_CD_LengthTimAdministered",INV1159_R20_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R21_CDT",INV1158_R21_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R21_CDT_DrugName",INV1158_R21_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R21_CD",INV1159_R21_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R21_CD_LengthTimAdministered",INV1159_R21_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R22_CDT",INV1158_R22_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R22_CDT_DrugName",INV1158_R22_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R22_CD",INV1159_R22_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R22_CD_LengthTimAdministered",INV1159_R22_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R23_CDT",INV1158_R23_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R23_CDT_DrugName",INV1158_R23_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R23_CD",INV1159_R23_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R23_CD_LengthTimAdministered",INV1159_R23_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R24_CDT",INV1158_R24_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R24_CDT_DrugName",INV1158_R24_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R24_CD",INV1159_R24_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R24_CD_LengthTimAdministered",INV1159_R24_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R25_CDT",INV1158_R25_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R25_CDT_DrugName",INV1158_R25_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R25_CD",INV1159_R25_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R25_CD_LengthTimAdministered",INV1159_R25_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R26_CDT",INV1158_R26_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R26_CDT_DrugName",INV1158_R26_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R26_CD",INV1159_R26_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R26_CD_LengthTimAdministered",INV1159_R26_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R27_CDT",INV1158_R27_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R27_CDT_DrugName",INV1158_R27_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R27_CD",INV1159_R27_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R27_CD_LengthTimAdministered",INV1159_R27_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R28_CDT",INV1158_R28_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R28_CDT_DrugName",INV1158_R28_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R28_CD",INV1159_R28_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R28_CD_LengthTimAdministered",INV1159_R28_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R29_CDT",INV1158_R29_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R29_CDT_DrugName",INV1158_R29_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R29_CD",INV1159_R29_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R29_CD_LengthTimAdministered",INV1159_R29_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R30_CDT",INV1158_R30_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1158_R30_CDT_DrugName",INV1158_R30_CDT_DrugName );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R30_CD",INV1159_R30_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1159_R30_CD_LengthTimAdministered",INV1159_R30_CD_LengthTimAdministered );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1160",INV1160 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1160_DateInjectableMedicationWasStopped",INV1160_DateInjectableMedicationWasStopped );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1161_CD",INV1161_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1161_CD_WasSurgeryPerformedToTreatMDRTB",INV1161_CD_WasSurgeryPerformedToTreatMDRTB );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1162",INV1162 );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1162_DateOfSurgery",INV1162_DateOfSurgery );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R1_CDT",code42563_7_CD_R1_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R1_CDT_SideEffect",code42563_7_CD_R1_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R1_CD",INV1164_R1_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R1_CD_SideEffectExperienced",INV1164_R1_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R1_CD",INV1163_R1_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R1_CD_When",INV1163_R1_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R2_CDT",code42563_7_CD_R2_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R2_CDT_SideEffect",code42563_7_CD_R2_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R2_CD",INV1164_R2_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R2_CD_SideEffectExperienced",INV1164_R2_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R2_CD",INV1163_R2_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R2_CD_When",INV1163_R2_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R3_CDT",code42563_7_CD_R3_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R3_CDT_SideEffect",code42563_7_CD_R3_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R3_CD",INV1164_R3_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R3_CD_SideEffectExperienced",INV1164_R3_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R3_CD",INV1163_R3_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R3_CD_When",INV1163_R3_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R4_CDT",code42563_7_CD_R4_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R4_CDT_SideEffect",code42563_7_CD_R4_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R4_CD",INV1164_R4_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R4_CD_SideEffectExperienced",INV1164_R4_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R4_CD",INV1163_R4_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R4_CD_When",INV1163_R4_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R5_CDT",code42563_7_CD_R5_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R5_CDT_SideEffect",code42563_7_CD_R5_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R5_CD",INV1164_R5_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R5_CD_SideEffectExperienced",INV1164_R5_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R5_CD",INV1163_R5_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R5_CD_When",INV1163_R5_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R6_CDT",code42563_7_CD_R6_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R6_CDT_SideEffect",code42563_7_CD_R6_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R6_CD",INV1164_R6_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R6_CD_SideEffectExperienced",INV1164_R6_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R6_CD",INV1163_R6_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R6_CD_When",INV1163_R6_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R7_CDT",code42563_7_CD_R7_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R7_CDT_SideEffect",code42563_7_CD_R7_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R7_CD",INV1164_R7_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R7_CD_SideEffectExperienced",INV1164_R7_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R7_CD",INV1163_R7_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R7_CD_When",INV1163_R7_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R8_CDT",code42563_7_CD_R8_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R8_CDT_SideEffect",code42563_7_CD_R8_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R8_CD",INV1164_R8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R8_CD_SideEffectExperienced",INV1164_R8_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R8_CD",INV1163_R8_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R8_CD_When",INV1163_R8_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R9_CDT",code42563_7_CD_R9_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R9_CDT_SideEffect",code42563_7_CD_R9_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R9_CD",INV1164_R9_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R9_CD_SideEffectExperienced",INV1164_R9_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R9_CD",INV1163_R9_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R9_CD_When",INV1163_R9_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R10_CDT",code42563_7_CD_R10_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R10_CDT_SideEffect",code42563_7_CD_R10_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R10_CD",INV1164_R10_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R10_CD_SideEffectExperienced",INV1164_R10_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R10_CD",INV1163_R10_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R10_CD_When",INV1163_R10_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R11_CDT",code42563_7_CD_R11_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R11_CDT_SideEffect",code42563_7_CD_R11_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R11_CD",INV1164_R11_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R11_CD_SideEffectExperienced",INV1164_R11_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R11_CD",INV1163_R11_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R11_CD_When",INV1163_R11_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R12_CDT",code42563_7_CD_R12_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R12_CDT_SideEffect",code42563_7_CD_R12_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R12_CD",INV1164_R12_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R12_CD_SideEffectExperienced",INV1164_R12_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R12_CD",INV1163_R12_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R12_CD_When",INV1163_R12_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R13_CDT",code42563_7_CD_R13_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R13_CDT_SideEffect",code42563_7_CD_R13_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R13_CD",INV1164_R13_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R13_CD_SideEffectExperienced",INV1164_R13_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R13_CD",INV1163_R13_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R13_CD_When",INV1163_R13_CD_When );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R14_CDT",code42563_7_CD_R14_CDT );
//		Whitebox.setInternalState(CDCLTBIForm.class, "code42563_7_CD_R14_CDT_SideEffect",code42563_7_CD_R14_CDT_SideEffect );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R14_CD",INV1164_R14_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1164_R14_CD_SideEffectExperienced",INV1164_R14_CD_SideEffectExperienced );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R14_CD",INV1163_R14_CD );
//		Whitebox.setInternalState(CDCLTBIForm.class, "INV1163_R14_CD_When",INV1163_R14_CD_When );
//
//		
//		
//
//
//		///////
//		
//
//	 }
//	 
//	
//
//	@Test
//	public void initializeMDRTBSupplemental_test() throws Exception{
//			
//		
//		
//			System.out.println("******************* Starting test case named: initializeMDRTBSupplemental_test *******************");
//				
//			Whitebox.invokeMethod(CDCLTBIForm.class, "initializeMDRTBSupplemental");
//		
//			
//			int sizeMap = 0;
//			int expectedSize = 107;
//			
//			if(LTBIFieldsMap!=null)
//				sizeMap = LTBIFieldsMap.size();
//			
//			
//		//	Assert.assertNotEquals(0, sizeMap);//At least one initialized
//			Assert.assertEquals(expectedSize, sizeMap);
//			
//
//			
//			System.out.println(iteration+ ": PASSED");
//			System.out.println("******************* End test case named: initializeMDRTBSupplemental_test *******************");
//			
//		}	
//	
//	
//}






}
