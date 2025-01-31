package test.gov.cdc.nedss.pagemanagement.wa.xml;


import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML;
import gov.cdc.nedss.pagemanagement.wa.xml.util.HeaderType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.MarshallPageRules;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageElementType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageInfoDocument;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageInfoDocument.Factory;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageInfoType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.SectionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.SetValuesButtonType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.SubSectionType;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

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
For this test case, only 1 method is tested so far:
- splitVerCtrlNbr
 */




/**
Description:

This method will check if the number of times the internal method setAnswer is called is the same one as expected.
Since the method we are testing is a void method, this is the only way we have to know if it is really working fine.


Lessons learnt:

- ...



*/

@SuppressStaticInitializationFor ({"gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML","gov.cdc.nedss.util.LogUtils"})
@RunWith(Enclosed.class)//powermock-module-junit4.jar\
@PrepareForTest({MarshallPageXML.class,LogUtils.class,PageInfoDocument.class})
@PowerMockIgnore("javax.management.*")
public class MarshallPageXML_tests {

	
@SuppressStaticInitializationFor ({"gov.cdc.nedss.pagemanagement.wa.xml.MarshallPageXML","gov.cdc.nedss.util.LogUtils"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({MarshallPageXML.class,LogUtils.class,PageInfoDocument.class})
@PowerMockRunnerDelegate(Parameterized.class)//Added on release of PowerMock 1.6, to allow to use @RunWith(PowerMockRunner.class) and @RunWith(Parametizer) at the same time
@PowerMockIgnore("javax.management.*")
public static class GeneratePageXMLFile_test {


	private String blockName;
	private String defaultValue; 
	private String questionLabel;
	private int iteration;
	private String discreteOrRepeating;//D or R

	 
	 public GeneratePageXMLFile_test(String blockName, String defaultValue, String questionLabel, String discreteOrRepeating, int it){
		 super();

		 this.blockName = blockName;
		 this.defaultValue = defaultValue;
		 this.questionLabel = questionLabel;
		 this.discreteOrRepeating = discreteOrRepeating;
		 this.iteration = it;
		 }

	 
	   @Parameterized.Parameters
	   public static Collection input() {
		   
			
			

		  //With the following data sent as a parameter, we will cover 2 of the three areas modified under the test method to fix the defect.
		   //The areas are the lines where the new method escapeCharacterSequence method is called
	      return Arrays.asList(new Object[][]{
	    		  //ContextAction, expected value, iteration
	    		  {"PHENO_DRG_SUSC_TST","LABAST6^18921-7~LABAST8^131196009|LABAST6^18934-0~LABAST8^131196009|LABAST6^18973-8~LABAST8^131196009|LABAST6^18974-6~LABAST8^131196009|","Standard Susceptibilities (4)","R",1},//PASS - repeating
	    		  {"PHENO_DRG_SUSC_TST","LABAST6^18982-9~LABAST8^385660001|LABAST6^19149-4~LABAST8^385660001|LABAST6^76627-9~LABAST8^385660001|LABAST6^18860-7~LABAST8^385660001| LABAST6^18935-7~LABAST8^385660001|LABAST6^18872-2~LABAST8^385660001|LABAST6^18906-8~LABAST8^385660001|LABAST6^20629-2~LABAST8^385660001| LABAST6^18959-7~LABAST8^385660001|LABAST6^LAB674~LABAST8^385660001|LABAST6^18914-2~LABAST8^385660001|LABAST6^23629-9~LABAST8^385660001|LABAST6^LAB675~LABAST8^385660001|LABAST6^23627-3~LABAST8^385660001|LABAST6^LAB676~LABAST8^385660001|LABAST6^18922-5~LABAST8^385660001|LABAST6^29258-1~LABAST8^385660001| LABAST6^31039-1~LABAST8^385660001|LABAST6^93850-6~LABAST8^385660001|LABAST6^OTH~LABAST8^385660001|","Mark Rest 'Not Done'","R",2},//PASS - repeating
	    		  {"PHENO_DRG_SUSC_TST","CLEAR","Clear","R",3},//PASS - repeating
	    		  {"","6038^Y~9384^Y~8987^Y~4110^Y","Standard Susceptibilities (4)","D",4},//PASS - discrete
	    		  {"","10109^N~55672^N~35617^N~4127^N~641^N~6099^N~78903^N~2551^N~82122^N~7623^N~139462^N~PHC1888^N~3007^N~7833^N~190376^N~1364504^N~PHC1889^N~2592^N~2198359^N~NBS456^N","Mark Rest 'Not Done'","D",5},//PASS - discrete
	    		  {"","6038^[CLEAR]~9384^[CLEAR]~8987^[CLEAR]~4110^[CLEAR]~10109^[CLEAR]~55672^[CLEAR]~35617^[CLEAR]~4127^[CLEAR]~641^[CLEAR]~6099^[CLEAR]~78903^[CLEAR]~2551^[CLEAR]~82122^[CLEAR]~7623^[CLEAR]~139462^[CLEAR]~PHC1888^[CLEAR]~3007^[CLEAR]~7833^[CLEAR]~190376^[CLEAR]~1364504^[CLEAR]~PHC1889^[CLEAR]~2592^[CLEAR]~2198359^[CLEAR]~NBS456^[CLEAR]~NBS456_OTH^[CLEAR]","Clear","D",6}, //PASS - discrete 		  
	    		/*  {"PHENO_DRG_SUSC_TST","LABAST6^18921-7~LABAST8^131196009|LABAST6^18934-0~LABAST8^131196009|LABAST6^18973-8~LABAST8^131196009|LABAST6^18974-6~LABAST8^131196009|","Standard Susceptibilities (4)","D",7},//FAIL - It has a block name but I am indicating D
	    		  {"PHENO_DRG_SUSC_TST","LABAST6^18982-9~LABAST8^385660001|LABAST6^19149-4~LABAST8^385660001|LABAST6^76627-9~LABAST8^385660001|LABAST6^18860-7~LABAST8^385660001| LABAST6^18935-7~LABAST8^385660001|LABAST6^18872-2~LABAST8^385660001|LABAST6^18906-8~LABAST8^385660001|LABAST6^20629-2~LABAST8^385660001| LABAST6^18959-7~LABAST8^385660001|LABAST6^LAB674~LABAST8^385660001|LABAST6^18914-2~LABAST8^385660001|LABAST6^23629-9~LABAST8^385660001|LABAST6^LAB675~LABAST8^385660001|LABAST6^23627-3~LABAST8^385660001|LABAST6^LAB676~LABAST8^385660001|LABAST6^18922-5~LABAST8^385660001|LABAST6^29258-1~LABAST8^385660001| LABAST6^31039-1~LABAST8^385660001|LABAST6^93850-6~LABAST8^385660001|LABAST6^OTH~LABAST8^385660001|","Mark Rest 'Not Done'","D",8},//FAIL - It has a block name but I am indicating D
	    		  {"PHENO_DRG_SUSC_TST","CLEAR","Clear","D",9},//FAIL - It has a block name but I am indicating D
	    		  {"","6038^Y~9384^Y~8987^Y~4110^Y","Standard Susceptibilities (4)","R",10}, //FAIL - it doesn't have block name but I am indicating R
	    		  {"","10109^N~55672^N~35617^N~4127^N~641^N~6099^N~78903^N~2551^N~82122^N~7623^N~139462^N~PHC1888^N~3007^N~7833^N~190376^N~1364504^N~PHC1889^N~2592^N~2198359^N~NBS456^N","Mark Rest 'Not Done'","R",11},//FAIL - it doesn't have block name but I am indicating R
	    		  {"","6038^[CLEAR]~9384^[CLEAR]~8987^[CLEAR]~4110^[CLEAR]~10109^[CLEAR]~55672^[CLEAR]~35617^[CLEAR]~4127^[CLEAR]~641^[CLEAR]~6099^[CLEAR]~78903^[CLEAR]~2551^[CLEAR]~82122^[CLEAR]~7623^[CLEAR]~139462^[CLEAR]~PHC1888^[CLEAR]~3007^[CLEAR]~7833^[CLEAR]~190376^[CLEAR]~1364504^[CLEAR]~PHC1889^[CLEAR]~2592^[CLEAR]~2198359^[CLEAR]~NBS456^[CLEAR]~NBS456_OTH^[CLEAR]","Clear","R",12}   //FAIL - it doesn't have block name but I am indicating R		  
	    		*/
	    		  
	    		  {null,"LABAST6^18921-7~LABAST8^131196009|LABAST6^18934-0~LABAST8^131196009|LABAST6^18973-8~LABAST8^131196009|LABAST6^18974-6~LABAST8^131196009|","Standard Susceptibilities (4)","D",13},//PASS - discreate, null block name.
	    		  // {null,null,"D",14},//FAIL, this scenario doesn't make sense because default values will have a value, unless there's bad data in the database.
	    		  {"PHENO_DRG_SUSC_TST","","Standard Susceptibilities (4)","R",14},//PASS - repeating with empty default values, which doesn't make sense, but still works as expected.
		    		 
	      		});
	      
	  				
	  	
	  	
	  	
	   }
	 
	 
	 
	/*
	 * To process all above annotations, MockitoAnnotations.initMocks(testClass); must be used mat least once. 
	 * */

	@org.mockito.Mock
	LogUtils logger;

	@Mock
	PageInfoDocument pageInfoDoc;
	
	@Mock
	HttpSession httpSession;

	@Mock
	PageManagementProxyVO pmProxyVO;
	
	@Mock
	SubSectionType subSection = null;
	
	@Mock
	PageElementType element;
	
	@Mock
	MarshallPageRules ruleMarshaller;

	
	@Mock
	String fileToMake;
	@Mock
	PageInfoType pageInfo;
	@Mock
	gov.cdc.nedss.pagemanagement.wa.xml.util.PageType page;
	@Mock
	gov.cdc.nedss.pagemanagement.wa.xml.util.TabType tab;
	
	@Mock
	HeaderType header;
	
	@Mock
	SectionType section;
	
	@InjectMocks
	@Spy
	MarshallPageXML marshallPageXML = Mockito.spy(new MarshallPageXML());
	
	
		 @Before
		 public void initMocks() throws Exception {

			 logger = Mockito.mock(LogUtils.class);
			 pageInfoDoc = Mockito.mock(PageInfoDocument.class);
			 Whitebox.setInternalState(MarshallPageXML.class, "logger", logger);
			 
		 }
		 
		 
		 		@Test
				public void generatePageXMLFile_test() throws Exception{
				
		

				System.out.println("******************* Starting test case named: generatePageXMLFile_test *******************");
				
			
				
				PageInfoDocument pageInfoDoc2 = PageInfoDocument.Factory.newInstance();
				PageInfoType pageInfo2 = pageInfoDoc2.addNewPageInfo();
				gov.cdc.nedss.pagemanagement.wa.xml.util.PageType page2 = pageInfo2.addNewPage();
				gov.cdc.nedss.pagemanagement.wa.xml.util.TabType tab2 = page2.addNewPageTab();
				SectionType section2 = tab2.addNewSection();
				SubSectionType subSection2 = section2.addNewSubSection();
				PageElementType element2 = subSection2.addNewPageElement();
				SetValuesButtonType setValuesButton2 = element2.addNewSetValuesButton(); 
				
				
				
				String fileToMake = "C:\\wildfly-10.0.0.Final\\bin\\..\nedssdomain\\Nedss\\Properties\\xmltojsp\\pageOut.xml";
				
				//Mocking methods.
				PowerMockito.when(pageInfoDoc.addNewPageInfo()).thenReturn(pageInfo);
				PowerMockito.mockStatic(Factory.class);
				PowerMockito.when(Factory.newInstance()).thenReturn(pageInfoDoc);
				PowerMockito.when(pageInfo.addNewHeader()).thenReturn(header);
				PowerMockito.when(pageInfo.addNewPage()).thenReturn(page);
				PowerMockito.when(page.addNewPageTab()).thenReturn(tab);
				PowerMockito.when(tab.addNewSection()).thenReturn(section);
				PowerMockito.when(section.addNewSubSection()).thenReturn(subSection);
				PowerMockito.when(subSection.addNewPageElement()).thenReturn(element);
				PowerMockito.when(element.addNewSetValuesButton()).thenReturn(setValuesButton2);
				PowerMockito.whenNew(MarshallPageRules.class) .withNoArguments().thenReturn(ruleMarshaller);
				PowerMockito.doNothing().when(ruleMarshaller, "addRulesToXML",Mockito.any(Collection.class), Mockito.any(HashMap.class),  Mockito.any(HashMap.class),  Mockito.any(HashMap.class), Mockito.any(PageInfoType.class));
				PowerMockito.doNothing().when(marshallPageXML, "addUIMetadataToXML",Mockito.any(PageInfoType.class),Mockito.any(WaUiMetadataDT.class));
	 			

				PageElementVO pageElementVO = new PageElementVO();
				PageElementVO pageElementVO1 = new PageElementVO();
				PageElementVO pageElementVO2 = new PageElementVO();
				PageElementVO pageElementVO3 = new PageElementVO();
				
				Collection<Object> peVoColl = new ArrayList<Object>();
				/*******************************************************************************************/
				//Creating minimum elements to be able to test the new element, Default Values buttons.
				
				//Tab
				WaTemplateDT waTempDT = new WaTemplateDT();
				
				 WaUiMetadataDT uiMeta  = new WaUiMetadataDT();
				 Long NbsUiComponentUid = 1010L;
				 String standardNndInd = "N";
				 uiMeta.setNbsUiComponentUid(NbsUiComponentUid);
				 uiMeta.setStandardNndIndCd(standardNndInd); 
				 pageElementVO.setWaUiMetadataDT(uiMeta);
				 
				 Mockito.when(pmProxyVO.getWaTemplateDT()).thenReturn(waTempDT);
				 
				
				//Section
				 WaUiMetadataDT uiMeta1  = new WaUiMetadataDT();
				 Long NbsUiComponentUid1 = 1015L;
				 String standardNndInd1 = "N";
				 uiMeta1.setNbsUiComponentUid(NbsUiComponentUid1);
				 uiMeta1.setStandardNndIndCd(standardNndInd1); 
				 pageElementVO1.setWaUiMetadataDT(uiMeta1);
				 
				 
				//Subsection
				 WaUiMetadataDT uiMeta2  = new WaUiMetadataDT();
				 Long NbsUiComponentUid2 = 1016L;
				 String standardNndInd2 = "N";
				 uiMeta2.setNbsUiComponentUid(NbsUiComponentUid2);
				 uiMeta2.setStandardNndIndCd(standardNndInd2); 
				 pageElementVO2.setWaUiMetadataDT(uiMeta2);
				 
				 /*******************************************************************************************/
				 
				 
				 
				//Default value
				 
				 // We will provide different values for Default Values buttons from the aprameters so we can test different scenarios.
				 //We don't need to used this as parameters, since it is not part of the new changes. We will be providing the same value through all the scenarios.
				
				 WaUiMetadataDT uiMeta3  = new WaUiMetadataDT();
				 Long NbsUiComponentUid3 = 1034L;
				 String standardNndInd3 = "N";
				 uiMeta3.setNbsUiComponentUid(NbsUiComponentUid3);
				 uiMeta3.setStandardNndIndCd(standardNndInd3); 
				 String adminComment="Added in NBS 6.0.13 to support TB/LTBI in Page Builder for RVCT 2020.";
				 //String questionLabel="DefaultQuestionLabel";//We will be providing this as a parameter to be more clear
				 String questionIdentifier="question_unique_name";//Just as an example, it doesn't matter for the test
				 
				 
				 uiMeta3.setAdminComment(adminComment);
				 uiMeta3.setQuestionLabel(questionLabel);
				 uiMeta3.setQuestionIdentifier(questionIdentifier);
				 uiMeta3.setBlockName(blockName);
				 uiMeta3.setDefaultValue(defaultValue);
				 pageElementVO3.setWaUiMetadataDT(uiMeta3);

				 peVoColl.add(pageElementVO);
				 peVoColl.add(pageElementVO1);
				 peVoColl.add(pageElementVO2);
				 peVoColl.add(pageElementVO3);
					
				Mockito.when(pmProxyVO.getThePageElementVOCollection()).thenReturn(peVoColl);

				marshallPageXML.GeneratePageXMLFile(pmProxyVO, fileToMake);
				
				String expectedValue = discreteOrRepeating+"=="+this.defaultValue;
				String actualValue = setValuesButton2.getDefaultValue();
				
				System.out.println("Iteration: #"+iteration+"\nExpected default values: "+expectedValue+"\nActual default values:   "+actualValue);
				
				Assert.assertEquals(expectedValue, actualValue);

				System.out.println("PASSED");
				System.out.println("******************* End test case named: generatePageXMLFile_test *******************");
			}

}
}
