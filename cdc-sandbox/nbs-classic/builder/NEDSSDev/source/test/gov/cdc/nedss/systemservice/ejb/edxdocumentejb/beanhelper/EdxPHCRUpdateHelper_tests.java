package test.gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRUpdateHelper;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.DSMUpdateAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NBSBeanUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRUpdateHelper","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(Enclosed.class)
@PrepareForTest({EdxPHCRUpdateHelper.class})
@PowerMockIgnore("javax.management.*")
public class EdxPHCRUpdateHelper_tests {
	
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRUpdateHelper","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EdxPHCRUpdateHelper.class})
@PowerMockIgnore("javax.management.*")
public static class LoadIgnoreQuestionMap_test {
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	LogUtils logger;
	
	@Mock
	DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT;
	
    @Mock
    CachedDropDownValues cdv;
    
    @Mock
    NBSBeanUtils nUtils;
    
  
	@InjectMocks
	@Spy
	EdxPHCRUpdateHelper edxPHCRUpdateHelper=Mockito.spy(EdxPHCRUpdateHelper.class);
	
	public LoadIgnoreQuestionMap_test(){}
		
	 @Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(EdxPHCRUpdateHelper.class, "logger", logger);
	 }
		
	 @Test
	 public void loadIgnoreQuestionMap_test() throws Exception{
		 PowerMockito.whenNew(DSMUpdateAlgorithmDT.class).withNoArguments().thenReturn(dsmUpdateAlgorithmDT);
		 Mockito.when(dsmUpdateAlgorithmDT.getUpdateIgnoreList()).thenReturn(getUpdateIgnoreList());
		 Mockito.doReturn(getQuestionMap()).when(edxPHCRUpdateHelper).getQuestionMap();
		 Mockito.doReturn(getIgnoreQuestionMap()).when(edxPHCRUpdateHelper).getIgnoreQuestionMap();
		 Mockito.doReturn(getIgnoreParticipantQuestionMap()).when(edxPHCRUpdateHelper).getIgnoreParticipantQuestionMap();
		 Mockito.doReturn(getIgnoreActIDQuestionMap()).when(edxPHCRUpdateHelper).getIgnoreActIDQuestionMap();
		 Whitebox.invokeMethod(edxPHCRUpdateHelper, "loadIgnoreQuestionMap", dsmUpdateAlgorithmDT);
		 System.out.println("Class Name: EdxPHCRUpdateHelper.java, Method:loadIgnoreQuestionMap, Iteration:1, Result:::PASSED");
	 }
	 
	 
	 private String getUpdateIgnoreList() {
		 return "question1,question2,question3,question4";
	 }
	 
	 private HashMap<Object, Object> getQuestionMap() {
		 Map<Object, Object> questionMap= new HashMap<Object,Object>();
		 NbsQuestionMetadata nbsqMetaData0=new NbsQuestionMetadata();
		 nbsqMetaData0.setDataCd("1234");
		 questionMap.put("question1", nbsqMetaData0);
		 NbsQuestionMetadata nbsqMetaData1=new NbsQuestionMetadata();
		 nbsqMetaData1.setPartTypeCd("PART11");
		 questionMap.put("question4", nbsqMetaData1);
		 NbsQuestionMetadata nbsqMetaData2=new NbsQuestionMetadata();
		 nbsqMetaData2.setPartTypeCd("PART1");
		 questionMap.put("question2", nbsqMetaData2);
		 NbsQuestionMetadata nbsqMetaData3=new NbsQuestionMetadata();
		 nbsqMetaData3.setDataLocation("ACT_ID_Atlanta");
		 questionMap.put("question3", nbsqMetaData3);
		 return (HashMap<Object, Object>) questionMap;
	 }
	 
	 private HashMap<Object, Object> getIgnoreQuestionMap() {
		 Map<Object, Object> ignoreQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) ignoreQuestionMap;
	 }
	 
	 private HashMap<Object, Object> getIgnoreParticipantQuestionMap(){
		 Map<Object, Object> IgnoreParticipantQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) IgnoreParticipantQuestionMap; 
	 }
	 
	 private HashMap<Object, Object> getIgnoreActIDQuestionMap(){
		 Map<Object, Object> IgnoreActIDQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) IgnoreActIDQuestionMap; 
	 }
	
	 private  PageActProxyVO getoldPhcVO() {
		 PageActProxyVO ppvo= new PageActProxyVO();
		 PublicHealthCaseVO phcv = new PublicHealthCaseVO();
		 PublicHealthCaseDT phct = new PublicHealthCaseDT();
		 PersonVO personVO = new PersonVO();
		 phct.setProgAreaCd("HEP");
		 phct.setPublicHealthCaseUid(1234567L);
		 phct.setLocalId("USP0987");
		 phct.setAddUserId(1111111L);
		 phct.setSharedInd("Ind");
		 phct.setAddTime(new Timestamp(System.currentTimeMillis()));
		 phct.setVersionCtrlNbr(1);
		 phcv.setThePublicHealthCaseDT(phct);
		 ppvo.setPublicHealthCaseVO(phcv);
		 
		 personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
	  	 personVO.setMPRUpdateValid(false);
 		 personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
		 personVO.getThePersonDT().setLastChgTime(new Timestamp(System.currentTimeMillis()));
		 personVO.getThePersonDT().setLastChgUserId(1000000L);
		 personVO.getThePersonDT().setFirstNm("pra");
		 personVO.getThePersonDT().setMiddleNm("Pra1");
		 personVO.getThePersonDT().setLastNm("Prab2");
		 Collection<Object> personColl = new ArrayList<Object>();
		 personColl.add(personVO);
		 ppvo.setThePersonVOCollection(personColl);
		 
		 return ppvo;
	 }
	 
	 private  PageActProxyVO getnewPhcVO() {
		 PageActProxyVO ppvo= new PageActProxyVO();
		 PublicHealthCaseVO phcv = new PublicHealthCaseVO();
		 ActRelationshipDT actDoc = new ActRelationshipDT();
		 actDoc.setSourceActUid(12345L);
		 actDoc.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
		 actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		 actDoc.setTargetActUid(98765L);
		 actDoc.setTargetClassCd(NEDSSConstants.CASE);
		 actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
		 actDoc.setTypeCd("DocToPHC");

		 Collection<Object> coll = new ArrayList<Object>();
		 coll.add(actDoc);
		 phcv.setTheActRelationshipDTCollection(coll);
		 
		 ActRelationshipDT actDoc1 = new ActRelationshipDT();
		 actDoc1.setSourceActUid(123456L);
		 actDoc1.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
		 actDoc1.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		 actDoc1.setTargetActUid(987656L);
		 actDoc1.setTargetClassCd(NEDSSConstants.CASE);
		 actDoc1.setRecordStatusCd(NEDSSConstants.ACTIVE);
		 actDoc1.setTypeCd("DocToCON");
		 phcv.getTheActRelationshipDTCollection().add(actDoc1);
		 
		 ArrayList<Object> confirmationlist = new ArrayList<Object>();
		 ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
		 confirmationMethodDT.setConfirmationMethodCd("NA");
		 confirmationMethodDT.setConfirmationMethodTime(new Timestamp(System.currentTimeMillis()));
		 confirmationlist.add(confirmationMethodDT);
		 phcv.setTheConfirmationMethodDTCollection(confirmationlist);
		 ppvo.setPublicHealthCaseVO(phcv);
		 
		 PersonVO personVO = new PersonVO();
		 personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
	  	 personVO.setMPRUpdateValid(false);
 		 personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
		 personVO.getThePersonDT().setLastChgTime(new Timestamp(System.currentTimeMillis()));
		 personVO.getThePersonDT().setLastChgUserId(1000000L);
		 personVO.getThePersonDT().setFirstNm("bha");
		 personVO.getThePersonDT().setMiddleNm("bha1");
		 personVO.getThePersonDT().setLastNm("bha2");
		 Collection<Object> personColl = new ArrayList<Object>();
		 personColl.add(personVO);
		 ppvo.setThePersonVOCollection(personColl);
		 return ppvo;
	 }
	 
	private ProgramAreaVO getProgramAreaVO() {
		ProgramAreaVO pavo= new ProgramAreaVO();
		pavo.setInvestigationFormCd("1234INV");
		return pavo;
	}
	
	 private HashMap<Object, Object> getIgnoredQuestionMap() {
		 Map<Object, Object> questionMap= new HashMap<Object,Object>();
		 NbsQuestionMetadata nbsqMetaData1=new NbsQuestionMetadata();
		 nbsqMetaData1.setDataLocation("PERSON.person_uid");
		 questionMap.put("question1", nbsqMetaData1);
		 NbsQuestionMetadata nbsqMetaData2=new NbsQuestionMetadata();
		 nbsqMetaData2.setDataLocation("PUBLIC_HEALTH_CASE.public_health_case_uid");
		 questionMap.put("question2", nbsqMetaData2);
		 NbsQuestionMetadata nbsqMetaData3=new NbsQuestionMetadata();
		 nbsqMetaData3.setDataLocation("CASE_MANAGEMENT.public_health_case_uid");
		 questionMap.put("question3", nbsqMetaData3);
		 return (HashMap<Object, Object>) questionMap;
	 }
}
		



@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRUpdateHelper","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EdxPHCRUpdateHelper.class})
@PowerMockIgnore("javax.management.*")
public static class PreparePageActProxyVOForUpdate_test {
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	LogUtils logger;
	
	@Mock
	DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT;
	
    @Mock
    CachedDropDownValues cdv;
    
    @Mock
    NBSBeanUtils nUtils;
    
  
	@InjectMocks
	@Spy
	EdxPHCRUpdateHelper edxPHCRUpdateHelper=Mockito.spy(EdxPHCRUpdateHelper.class);
	
	public PreparePageActProxyVOForUpdate_test(){}
		
	 @Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(EdxPHCRUpdateHelper.class, "logger", logger);
	 }
	 
	@SuppressWarnings("unchecked")
	@Test
	 public void preparePageActProxyVOForUpdate_test() throws Exception {
		 PowerMockito.mockStatic(EdxPHCRUpdateHelper.class);
		 PowerMockito.whenNew(CachedDropDownValues.class).withNoArguments().thenReturn(cdv);
		 Mockito.when(cdv.getProgramAreaCondition(any(String.class),any(String.class))).thenReturn(getProgramAreaVO());
		 Mockito.doNothing().doThrow(new RuntimeException()).when(edxPHCRUpdateHelper).loadQuestions(any(String.class));
		 Mockito.doNothing().doThrow(new RuntimeException()).when(edxPHCRUpdateHelper).loadQuestionKeys(any(String.class));
		 Mockito.doNothing().doThrow(new RuntimeException()).when(edxPHCRUpdateHelper).loadIgnoreQuestionMap(any(DSMUpdateAlgorithmDT.class));
		 Mockito.when(EdxPHCRUpdateHelper.safe(any(Collection.class))).thenReturn(getnewPhcVO().getPublicHealthCaseVO().getTheActRelationshipDTCollection());
		 PowerMockito.doNothing().when(edxPHCRUpdateHelper, "handleActIDUpdates", any(PageActProxyVO.class),any(PageActProxyVO.class));
		 Mockito.when(EdxPHCRUpdateHelper.updatePersonCRUpdate(any(PageActProxyVO.class),any(PageActProxyVO.class))).thenReturn(12345L);
		 PowerMockito.doNothing().when(edxPHCRUpdateHelper, "setEntitiesForEdit", any(PageActProxyVO.class),any(PageActProxyVO.class),any(Long.class));
		 Mockito.doNothing().when(edxPHCRUpdateHelper).handleUpdateIngoreScenarioForNBSCaseAnswer(any(PageActProxyVO.class),any(PageActProxyVO.class));
		 PowerMockito.doNothing().when(edxPHCRUpdateHelper, "updateNbsAnswersForDirty", any(PageActProxyVO.class),any(PageActProxyVO.class));
		 PowerMockito.doNothing().when(edxPHCRUpdateHelper, "updateNbsRepeatingAnswersForDirty", any(PageActProxyVO.class),any(PageActProxyVO.class));
		 PowerMockito.doNothing().when(edxPHCRUpdateHelper, "handleUpdateIngoreScenarioForCoreObjects", any(PageActProxyVO.class),any(PageActProxyVO.class));
		 PageActProxyVO ppvo= getnewPhcVO();
		 Whitebox.invokeMethod(edxPHCRUpdateHelper, "preparePageActProxyVOForUpdate", ppvo,getoldPhcVO(), new DSMUpdateAlgorithmDT());
		 assertTrue(ppvo.getPublicHealthCaseVO().getTheActRelationshipDTCollection()!=null);
		 assertTrue( ppvo.getPublicHealthCaseVO().getTheActRelationshipDTCollection().size() ==2);
		 System.out.println("Class Name: EdxPHCRUpdateHelper.java, Method:preparePageActProxyVOForUpdate, Iteration:1, Result::PASSED");
	 }
	
	

	 private String getUpdateIgnoreList() {
		 return "question1,question2,question3,question4";
	 }
	 
	 private HashMap<Object, Object> getQuestionMap() {
		 Map<Object, Object> questionMap= new HashMap<Object,Object>();
		 NbsQuestionMetadata nbsqMetaData0=new NbsQuestionMetadata();
		 nbsqMetaData0.setDataCd("1234");
		 questionMap.put("question1", nbsqMetaData0);
		 NbsQuestionMetadata nbsqMetaData1=new NbsQuestionMetadata();
		 nbsqMetaData1.setPartTypeCd("PART11");
		 questionMap.put("question4", nbsqMetaData1);
		 NbsQuestionMetadata nbsqMetaData2=new NbsQuestionMetadata();
		 nbsqMetaData2.setPartTypeCd("PART1");
		 questionMap.put("question2", nbsqMetaData2);
		 NbsQuestionMetadata nbsqMetaData3=new NbsQuestionMetadata();
		 nbsqMetaData3.setDataLocation("ACT_ID_Atlanta");
		 questionMap.put("question3", nbsqMetaData3);
		 return (HashMap<Object, Object>) questionMap;
	 }
	 
	 private HashMap<Object, Object> getIgnoreQuestionMap() {
		 Map<Object, Object> ignoreQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) ignoreQuestionMap;
	 }
	 
	 private HashMap<Object, Object> getIgnoreParticipantQuestionMap(){
		 Map<Object, Object> IgnoreParticipantQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) IgnoreParticipantQuestionMap; 
	 }
	 
	 private HashMap<Object, Object> getIgnoreActIDQuestionMap(){
		 Map<Object, Object> IgnoreActIDQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) IgnoreActIDQuestionMap; 
	 }
	
	 private  PageActProxyVO getoldPhcVO() {
		 PageActProxyVO ppvo= new PageActProxyVO();
		 PublicHealthCaseVO phcv = new PublicHealthCaseVO();
		 PublicHealthCaseDT phct = new PublicHealthCaseDT();
		 PersonVO personVO = new PersonVO();
		 phct.setProgAreaCd("HEP");
		 phct.setPublicHealthCaseUid(1234567L);
		 phct.setLocalId("USP0987");
		 phct.setAddUserId(1111111L);
		 phct.setSharedInd("Ind");
		 phct.setAddTime(new Timestamp(System.currentTimeMillis()));
		 phct.setVersionCtrlNbr(1);
		 phcv.setThePublicHealthCaseDT(phct);
		 ppvo.setPublicHealthCaseVO(phcv);
		 
		 personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
	  	 personVO.setMPRUpdateValid(false);
 		 personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
		 personVO.getThePersonDT().setLastChgTime(new Timestamp(System.currentTimeMillis()));
		 personVO.getThePersonDT().setLastChgUserId(1000000L);
		 personVO.getThePersonDT().setFirstNm("pra");
		 personVO.getThePersonDT().setMiddleNm("Pra1");
		 personVO.getThePersonDT().setLastNm("Prab2");
		 Collection<Object> personColl = new ArrayList<Object>();
		 personColl.add(personVO);
		 ppvo.setThePersonVOCollection(personColl);
		 
		 return ppvo;
	 }
	 
	 private  PageActProxyVO getnewPhcVO() {
		 PageActProxyVO ppvo= new PageActProxyVO();
		 PublicHealthCaseVO phcv = new PublicHealthCaseVO();
		 ActRelationshipDT actDoc = new ActRelationshipDT();
		 actDoc.setSourceActUid(12345L);
		 actDoc.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
		 actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		 actDoc.setTargetActUid(98765L);
		 actDoc.setTargetClassCd(NEDSSConstants.CASE);
		 actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
		 actDoc.setTypeCd("DocToPHC");

		 Collection<Object> coll = new ArrayList<Object>();
		 coll.add(actDoc);
		 phcv.setTheActRelationshipDTCollection(coll);
		 
		 ActRelationshipDT actDoc1 = new ActRelationshipDT();
		 actDoc1.setSourceActUid(123456L);
		 actDoc1.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
		 actDoc1.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		 actDoc1.setTargetActUid(987656L);
		 actDoc1.setTargetClassCd(NEDSSConstants.CASE);
		 actDoc1.setRecordStatusCd(NEDSSConstants.ACTIVE);
		 actDoc1.setTypeCd("DocToCON");
		 phcv.getTheActRelationshipDTCollection().add(actDoc1);
		 
		 ArrayList<Object> confirmationlist = new ArrayList<Object>();
		 ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
		 confirmationMethodDT.setConfirmationMethodCd("NA");
		 confirmationMethodDT.setConfirmationMethodTime(new Timestamp(System.currentTimeMillis()));
		 confirmationlist.add(confirmationMethodDT);
		 phcv.setTheConfirmationMethodDTCollection(confirmationlist);
		 ppvo.setPublicHealthCaseVO(phcv);
		 
		 PersonVO personVO = new PersonVO();
		 personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
	  	 personVO.setMPRUpdateValid(false);
 		 personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
		 personVO.getThePersonDT().setLastChgTime(new Timestamp(System.currentTimeMillis()));
		 personVO.getThePersonDT().setLastChgUserId(1000000L);
		 personVO.getThePersonDT().setFirstNm("bha");
		 personVO.getThePersonDT().setMiddleNm("bha1");
		 personVO.getThePersonDT().setLastNm("bha2");
		 Collection<Object> personColl = new ArrayList<Object>();
		 personColl.add(personVO);
		 ppvo.setThePersonVOCollection(personColl);
		 return ppvo;
	 }
	 
	private ProgramAreaVO getProgramAreaVO() {
		ProgramAreaVO pavo= new ProgramAreaVO();
		pavo.setInvestigationFormCd("1234INV");
		return pavo;
	}
	
	 private HashMap<Object, Object> getIgnoredQuestionMap() {
		 Map<Object, Object> questionMap= new HashMap<Object,Object>();
		 NbsQuestionMetadata nbsqMetaData1=new NbsQuestionMetadata();
		 nbsqMetaData1.setDataLocation("PERSON.person_uid");
		 questionMap.put("question1", nbsqMetaData1);
		 NbsQuestionMetadata nbsqMetaData2=new NbsQuestionMetadata();
		 nbsqMetaData2.setDataLocation("PUBLIC_HEALTH_CASE.public_health_case_uid");
		 questionMap.put("question2", nbsqMetaData2);
		 NbsQuestionMetadata nbsqMetaData3=new NbsQuestionMetadata();
		 nbsqMetaData3.setDataLocation("CASE_MANAGEMENT.public_health_case_uid");
		 questionMap.put("question3", nbsqMetaData3);
		 return (HashMap<Object, Object>) questionMap;
	 }
}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper.EdxPHCRUpdateHelper","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues","gov.cdc.nedss.util.NEDSSConstants"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({EdxPHCRUpdateHelper.class})
@PowerMockIgnore("javax.management.*")
public static class HandleUpdateIngoreScenarioForCoreObjects_test {
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	LogUtils logger;
	
	@Mock
	DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT;
	
    @Mock
    CachedDropDownValues cdv;
    
    @Mock
    NBSBeanUtils nUtils;
    
  
	@InjectMocks
	@Spy
	EdxPHCRUpdateHelper edxPHCRUpdateHelper=Mockito.spy(EdxPHCRUpdateHelper.class);
	
	public HandleUpdateIngoreScenarioForCoreObjects_test(){}
		
	 @Before
	 public void initMocks() throws Exception {
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(EdxPHCRUpdateHelper.class, "logger", logger);
	 }
		
	
	@SuppressWarnings("unchecked")
	@Test
	public void handleUpdateIngoreScenarioForCoreObjects_test() throws Exception{
		PowerMockito.mockStatic(EdxPHCRUpdateHelper.class);
		PowerMockito.whenNew(NBSBeanUtils.class).withNoArguments().thenReturn(nUtils);
		Mockito.doReturn(getIgnoredQuestionMap()).when(edxPHCRUpdateHelper).getIgnoreQuestionMap();
		Mockito.when(EdxPHCRUpdateHelper.safe(any(Collection.class))).thenReturn(getIgnoredQuestionMap().keySet());
		Mockito.doNothing().when(nUtils).copyNonNullPropertiesForNullDest(any(Object.class),any(Object.class),any(HashMap.class));
		Whitebox.invokeMethod(edxPHCRUpdateHelper, "handleUpdateIngoreScenarioForCoreObjects", getnewPhcVO(),getoldPhcVO());
		System.out.println("Class Name: EdxPHCRUpdateHelper.java, Method:handleUpdateIngoreScenarioForCoreObjects, Iteration:1, Result::PASSED");
	}
	

	 private String getUpdateIgnoreList() {
		 return "question1,question2,question3,question4";
	 }
	 
	 private HashMap<Object, Object> getQuestionMap() {
		 Map<Object, Object> questionMap= new HashMap<Object,Object>();
		 NbsQuestionMetadata nbsqMetaData0=new NbsQuestionMetadata();
		 nbsqMetaData0.setDataCd("1234");
		 questionMap.put("question1", nbsqMetaData0);
		 NbsQuestionMetadata nbsqMetaData1=new NbsQuestionMetadata();
		 nbsqMetaData1.setPartTypeCd("PART11");
		 questionMap.put("question4", nbsqMetaData1);
		 NbsQuestionMetadata nbsqMetaData2=new NbsQuestionMetadata();
		 nbsqMetaData2.setPartTypeCd("PART1");
		 questionMap.put("question2", nbsqMetaData2);
		 NbsQuestionMetadata nbsqMetaData3=new NbsQuestionMetadata();
		 nbsqMetaData3.setDataLocation("ACT_ID_Atlanta");
		 questionMap.put("question3", nbsqMetaData3);
		 return (HashMap<Object, Object>) questionMap;
	 }
	 
	 private HashMap<Object, Object> getIgnoreQuestionMap() {
		 Map<Object, Object> ignoreQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) ignoreQuestionMap;
	 }
	 
	 private HashMap<Object, Object> getIgnoreParticipantQuestionMap(){
		 Map<Object, Object> IgnoreParticipantQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) IgnoreParticipantQuestionMap; 
	 }
	 
	 private HashMap<Object, Object> getIgnoreActIDQuestionMap(){
		 Map<Object, Object> IgnoreActIDQuestionMap= new HashMap<Object,Object>();
		 return (HashMap<Object, Object>) IgnoreActIDQuestionMap; 
	 }
	
	 private  PageActProxyVO getoldPhcVO() {
		 PageActProxyVO ppvo= new PageActProxyVO();
		 PublicHealthCaseVO phcv = new PublicHealthCaseVO();
		 PublicHealthCaseDT phct = new PublicHealthCaseDT();
		 PersonVO personVO = new PersonVO();
		 phct.setProgAreaCd("HEP");
		 phct.setPublicHealthCaseUid(1234567L);
		 phct.setLocalId("USP0987");
		 phct.setAddUserId(1111111L);
		 phct.setSharedInd("Ind");
		 phct.setAddTime(new Timestamp(System.currentTimeMillis()));
		 phct.setVersionCtrlNbr(1);
		 phcv.setThePublicHealthCaseDT(phct);
		 ppvo.setPublicHealthCaseVO(phcv);
		 
		 personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
	  	 personVO.setMPRUpdateValid(false);
 		 personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
		 personVO.getThePersonDT().setLastChgTime(new Timestamp(System.currentTimeMillis()));
		 personVO.getThePersonDT().setLastChgUserId(1000000L);
		 personVO.getThePersonDT().setFirstNm("pra");
		 personVO.getThePersonDT().setMiddleNm("Pra1");
		 personVO.getThePersonDT().setLastNm("Prab2");
		 Collection<Object> personColl = new ArrayList<Object>();
		 personColl.add(personVO);
		 ppvo.setThePersonVOCollection(personColl);
		 
		 return ppvo;
	 }
	 
	 private  PageActProxyVO getnewPhcVO() {
		 PageActProxyVO ppvo= new PageActProxyVO();
		 PublicHealthCaseVO phcv = new PublicHealthCaseVO();
		 ActRelationshipDT actDoc = new ActRelationshipDT();
		 actDoc.setSourceActUid(12345L);
		 actDoc.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
		 actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		 actDoc.setTargetActUid(98765L);
		 actDoc.setTargetClassCd(NEDSSConstants.CASE);
		 actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
		 actDoc.setTypeCd("DocToPHC");

		 Collection<Object> coll = new ArrayList<Object>();
		 coll.add(actDoc);
		 phcv.setTheActRelationshipDTCollection(coll);
		 
		 ActRelationshipDT actDoc1 = new ActRelationshipDT();
		 actDoc1.setSourceActUid(123456L);
		 actDoc1.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
		 actDoc1.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		 actDoc1.setTargetActUid(987656L);
		 actDoc1.setTargetClassCd(NEDSSConstants.CASE);
		 actDoc1.setRecordStatusCd(NEDSSConstants.ACTIVE);
		 actDoc1.setTypeCd("DocToCON");
		 phcv.getTheActRelationshipDTCollection().add(actDoc1);
		 
		 ArrayList<Object> confirmationlist = new ArrayList<Object>();
		 ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
		 confirmationMethodDT.setConfirmationMethodCd("NA");
		 confirmationMethodDT.setConfirmationMethodTime(new Timestamp(System.currentTimeMillis()));
		 confirmationlist.add(confirmationMethodDT);
		 phcv.setTheConfirmationMethodDTCollection(confirmationlist);
		 ppvo.setPublicHealthCaseVO(phcv);
		 
		 PersonVO personVO = new PersonVO();
		 personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
	  	 personVO.setMPRUpdateValid(false);
 		 personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
		 personVO.getThePersonDT().setLastChgTime(new Timestamp(System.currentTimeMillis()));
		 personVO.getThePersonDT().setLastChgUserId(1000000L);
		 personVO.getThePersonDT().setFirstNm("bha");
		 personVO.getThePersonDT().setMiddleNm("bha1");
		 personVO.getThePersonDT().setLastNm("bha2");
		 Collection<Object> personColl = new ArrayList<Object>();
		 personColl.add(personVO);
		 ppvo.setThePersonVOCollection(personColl);
		 return ppvo;
	 }
	 
	private ProgramAreaVO getProgramAreaVO() {
		ProgramAreaVO pavo= new ProgramAreaVO();
		pavo.setInvestigationFormCd("1234INV");
		return pavo;
	}
	
	 private HashMap<Object, Object> getIgnoredQuestionMap() {
		 Map<Object, Object> questionMap= new HashMap<Object,Object>();
		 NbsQuestionMetadata nbsqMetaData1=new NbsQuestionMetadata();
		 nbsqMetaData1.setDataLocation("PERSON.person_uid");
		 questionMap.put("question1", nbsqMetaData1);
		 NbsQuestionMetadata nbsqMetaData2=new NbsQuestionMetadata();
		 nbsqMetaData2.setDataLocation("PUBLIC_HEALTH_CASE.public_health_case_uid");
		 questionMap.put("question2", nbsqMetaData2);
		 NbsQuestionMetadata nbsqMetaData3=new NbsQuestionMetadata();
		 nbsqMetaData3.setDataLocation("CASE_MANAGEMENT.public_health_case_uid");
		 questionMap.put("question3", nbsqMetaData3);
		 return (HashMap<Object, Object>) questionMap;
	 }
}


}
	
