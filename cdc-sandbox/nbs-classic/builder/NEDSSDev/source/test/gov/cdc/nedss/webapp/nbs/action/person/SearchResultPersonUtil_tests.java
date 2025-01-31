
package test.gov.cdc.nedss.webapp.nbs.action.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.junit.Assert;
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

import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil; 



@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.servlet"," gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.LogUtils",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj"})
@RunWith(Enclosed.class)
@PrepareForTest({SearchResultPersonUtil.class,LogUtils.class,PropertyUtil.class, NEDSSConstants.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public class SearchResultPersonUtil_tests {
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.servlet","gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.LogUtils",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({SearchResultPersonUtil.class,LogUtils.class,PropertyUtil.class, NEDSSConstants.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class SetDisplayInfo_test {
	
	
	@Mock
	PropertyUtil propertyUtil;
	@org.mockito.Mock
	LogUtils loggerMock;

	@org.mockito.Mock
	HttpServletRequest request;
	
	

	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	@Mock
	HttpSession httpSession;
	
	@Mock
	MockHttpServletRequest mockRequest;
	
	@Mock
	MockHttpSession mockHttpSession;
	

	@InjectMocks
	@Spy
	SearchResultPersonUtil searchResultPersonUtil = PowerMockito.spy(new SearchResultPersonUtil());
	
	
	 @Before
	    public void initMocks() throws Exception {
		 
		 propertyUtil= Mockito.mock(PropertyUtil.class);
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		//searchResultPersonUtil = PowerMockito.spy(new SearchResultPersonUtil());
	//     searchResultPersonUtil = new SearchResultPersonUtil();
	     request = Mockito.mock(HttpServletRequest.class);
		 loggerMock = Mockito.mock(LogUtils.class);
		 httpSession = Mockito.mock(HttpSession.class);
		 Whitebox.setInternalState(SearchResultPersonUtil.class, "logger", loggerMock);
		 Whitebox.setInternalState(SearchResultPersonUtil.class, "propertyUtil", propertyUtil);
		 
		 Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		 mockRequest = Mockito.mock(MockHttpServletRequest.class);
		 mockHttpSession = Mockito.mock(MockHttpSession.class); 
		 
		 nbsSecurityObjMock = Mockito.mock(NBSSecurityObj.class);
		 //Whitebox.setInternalState(SearchResultPersonUtil.class, "instance", nbsSecurityObjMock);
		 
	    }
	 

@SuppressWarnings("rawtypes") 
@Test
public void setDisplayInfo_test() throws Exception{
	PowerMockito.when(propertyUtil.getHTMLEncodingEnabled()).thenReturn(NEDSSConstants.YES);
	PowerMockito.when(request.getParameter("Mode1")).thenReturn("null");;
	PowerMockito.when(request.getAttribute("Mode1")).thenReturn("null");;
	PowerMockito.when(propertyUtil.getSeedValue()).thenReturn("1");
	PowerMockito.when(propertyUtil.getUidSufixCode()).thenReturn("4");
	ArrayList  revisionList =populateData();
	searchResultPersonUtil.setDisplayInfo(revisionList, false, true, request);
	Assert.assertEquals("<a href=\"javascript:selectPatient('10009465');\">8</a>", ((PatientSrchResultVO)revisionList.get(0)).getLink());
	}
	

@SuppressWarnings({ "rawtypes", "unchecked" })
private ArrayList  populateData( ) {
	ArrayList  revisionList = new ArrayList<PatientSrchResultVO>();

		PatientSrchResultVO patientSrchResultVO = new PatientSrchResultVO();
		patientSrchResultVO.setAgeReported("28");
		patientSrchResultVO.setAgeUnit(null);
		patientSrchResultVO.setAsOfDateAdmin(null);
		patientSrchResultVO.setAssociatedWith(null);
		patientSrchResultVO.setBirthTime(null);
		patientSrchResultVO.setCaseStatus(null);
		patientSrchResultVO.setCaseStatusCd(null);
		patientSrchResultVO.setCd("PAT");
		patientSrchResultVO.setCondition(null);
		patientSrchResultVO.setConditionCdColl(null);
		patientSrchResultVO.setConditionLink(null);
		patientSrchResultVO.setCurrentSex("F");
		patientSrchResultVO.setDateReceived(null);
		patientSrchResultVO.setDeceasedTime(null);
		patientSrchResultVO.setDegree(null);
		patientSrchResultVO.setDescription(null);
		patientSrchResultVO.setDescriptionPrint(null);
		patientSrchResultVO.setDescriptions(null);
		patientSrchResultVO.setDisplayRevision(null);
		patientSrchResultVO.setDocumentType(null);
		patientSrchResultVO.setDocumentTypeNoLnk(null);
		patientSrchResultVO.setElectronicInd(null);
		patientSrchResultVO.setEthnicGroupInd("2135-2");
		patientSrchResultVO.setInvestigator(null);
		patientSrchResultVO.setInvestigatorFirstName(null);
		patientSrchResultVO.setInvestigatorLastName(null);
		patientSrchResultVO.setInvSummaryVOs(null);
		patientSrchResultVO.setJurisdiction(null);
		patientSrchResultVO.setJurisdictionCd(null);
		patientSrchResultVO.setLink(null);
		patientSrchResultVO.setLocalId("PSN10067029GA01");
		patientSrchResultVO.setMaritalStatusCd(null);
		patientSrchResultVO.setMPRUid(null);
		patientSrchResultVO.setNonStdHivProgramAreaCode(false);
		patientSrchResultVO.setNotification(null);
		patientSrchResultVO.setNotificationCd(null);
		patientSrchResultVO.setObservationUid(null);
		patientSrchResultVO.setPersonAddressProfile(null);
		patientSrchResultVO.setPersonAddressProfileNoLink(null);
		patientSrchResultVO.setPersonDOB(null);
		patientSrchResultVO.setPersonFirstName("Lucy");
		patientSrchResultVO.setPersonFullName(null);
		patientSrchResultVO.setPersonFullNameNoLink(null);
		patientSrchResultVO.setPersonIdColl(null);
		patientSrchResultVO.setPersonIds(null);
		patientSrchResultVO.setPersonIdsNoLink(null);
		patientSrchResultVO.setPersonLastName("Williams");
		patientSrchResultVO.setPersonLocalID("10009438");
		patientSrchResultVO.setPersonLocatorsColl(null);
		patientSrchResultVO.setPersonNameColl(null);
		patientSrchResultVO.setPersonParentUid(null);
		patientSrchResultVO.setPersonPhoneprofile(null);
		patientSrchResultVO.setPersonPhoneprofileNoLink(null);
		patientSrchResultVO.setPersonUID(10009465L);
		patientSrchResultVO.setProfile(null);
		patientSrchResultVO.setProfileNoLink(null);
		patientSrchResultVO.setProgramAreaCode(null);
		patientSrchResultVO.setProviderFirstName(null);
		patientSrchResultVO.setProviderLastName(null);
		patientSrchResultVO.setProviderPrefix(null);
		patientSrchResultVO.setProviderSuffix(null);
		patientSrchResultVO.setProviderUid(null);
		patientSrchResultVO.setPublicHealthCaseUid(null);
		patientSrchResultVO.setRaceCdColl(null);
		patientSrchResultVO.setRecordStatusCd("ACTIVE");
		patientSrchResultVO.setReportingFacility(null);
		patientSrchResultVO.setReportingFacilityProvider(null);
		patientSrchResultVO.setReportingFacilityProviderPrint(null);
		patientSrchResultVO.setRevisionColl(null);
		patientSrchResultVO.setSex(null);
		patientSrchResultVO.setSsn(null);
		patientSrchResultVO.setStartDate(null);
		patientSrchResultVO.setStartDate_s(null);
		patientSrchResultVO.setSummaryVOColl(null);
		patientSrchResultVO.setTestsString(null);
		patientSrchResultVO.setTestsStringNoLnk(null);
		patientSrchResultVO.setTestsStringPrint(null);
		patientSrchResultVO.setTheResultedSummaryTestVOCollection(null);
		patientSrchResultVO.setTotalCount(null);
		patientSrchResultVO.setType(null);
		patientSrchResultVO.setVersionCtrlNbr(null);
		patientSrchResultVO.setView(null);
		patientSrchResultVO.setViewFile(null);
		patientSrchResultVO.setViewFileWithoutLink(null);
		
		revisionList.add(patientSrchResultVO);
	 return revisionList;
}


	

}



@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.servlet"," gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.LogUtils",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({SearchResultPersonUtil.class,LogUtils.class,PropertyUtil.class, NEDSSConstants.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class ShowButton_test {
	
	
	@Mock
	PropertyUtil propertyUtil;
	@org.mockito.Mock
	LogUtils loggerMock;

	@org.mockito.Mock
	HttpServletRequest request;
	
	
	//@InjectMocks
	SearchResultPersonUtil searchResultPersonUtil;
	
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	@Mock
	HttpSession httpSession;
	
	MockHttpServletRequest mockRequest = new MockHttpServletRequest();
	MockHttpSession mockHttpSession = new MockHttpSession();
	
	
	 @Before
	    public void initMocks() throws Exception {
		 
		 propertyUtil= Mockito.mock(PropertyUtil.class);
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		//searchResultPersonUtil = PowerMockito.spy(new SearchResultPersonUtil());
	     searchResultPersonUtil = new SearchResultPersonUtil();
		 loggerMock = Mockito.mock(LogUtils.class);
		 
		 Whitebox.setInternalState(SearchResultPersonUtil.class, "logger", loggerMock);
		 Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		 
		 nbsSecurityObjMock = Mockito.mock(NBSSecurityObj.class);
		 //Whitebox.setInternalState(SearchResultPersonUtil.class, "instance", nbsSecurityObjMock);
		 
	    }
	 

@SuppressWarnings({ "rawtypes", "unchecked" })
private ArrayList  populateData( ) {
	ArrayList  revisionList = new ArrayList<PatientSrchResultVO>();

		PatientSrchResultVO patientSrchResultVO = new PatientSrchResultVO();
		patientSrchResultVO.setAgeReported("28");
		patientSrchResultVO.setAgeUnit(null);
		patientSrchResultVO.setAsOfDateAdmin(null);
		patientSrchResultVO.setAssociatedWith(null);
		patientSrchResultVO.setBirthTime(null);
		patientSrchResultVO.setCaseStatus(null);
		patientSrchResultVO.setCaseStatusCd(null);
		patientSrchResultVO.setCd("PAT");
		patientSrchResultVO.setCondition(null);
		patientSrchResultVO.setConditionCdColl(null);
		patientSrchResultVO.setConditionLink(null);
		patientSrchResultVO.setCurrentSex("F");
		patientSrchResultVO.setDateReceived(null);
		patientSrchResultVO.setDeceasedTime(null);
		patientSrchResultVO.setDegree(null);
		patientSrchResultVO.setDescription(null);
		patientSrchResultVO.setDescriptionPrint(null);
		patientSrchResultVO.setDescriptions(null);
		patientSrchResultVO.setDisplayRevision(null);
		patientSrchResultVO.setDocumentType(null);
		patientSrchResultVO.setDocumentTypeNoLnk(null);
		patientSrchResultVO.setElectronicInd(null);
		patientSrchResultVO.setEthnicGroupInd("2135-2");
		patientSrchResultVO.setInvestigator(null);
		patientSrchResultVO.setInvestigatorFirstName(null);
		patientSrchResultVO.setInvestigatorLastName(null);
		patientSrchResultVO.setInvSummaryVOs(null);
		patientSrchResultVO.setJurisdiction(null);
		patientSrchResultVO.setJurisdictionCd(null);
		patientSrchResultVO.setLink(null);
		patientSrchResultVO.setLocalId("PSN10067029GA01");
		patientSrchResultVO.setMaritalStatusCd(null);
		patientSrchResultVO.setMPRUid(null);
		patientSrchResultVO.setNonStdHivProgramAreaCode(false);
		patientSrchResultVO.setNotification(null);
		patientSrchResultVO.setNotificationCd(null);
		patientSrchResultVO.setObservationUid(null);
		patientSrchResultVO.setPersonAddressProfile(null);
		patientSrchResultVO.setPersonAddressProfileNoLink(null);
		patientSrchResultVO.setPersonDOB(null);
		patientSrchResultVO.setPersonFirstName("Lucy");
		patientSrchResultVO.setPersonFullName(null);
		patientSrchResultVO.setPersonFullNameNoLink(null);
		patientSrchResultVO.setPersonIdColl(null);
		patientSrchResultVO.setPersonIds(null);
		patientSrchResultVO.setPersonIdsNoLink(null);
		patientSrchResultVO.setPersonLastName("Williams");
		patientSrchResultVO.setPersonLocalID("10009438");
		patientSrchResultVO.setPersonLocatorsColl(null);
		patientSrchResultVO.setPersonNameColl(null);
		patientSrchResultVO.setPersonParentUid(null);
		patientSrchResultVO.setPersonPhoneprofile(null);
		patientSrchResultVO.setPersonPhoneprofileNoLink(null);
		patientSrchResultVO.setPersonUID(10009465L);
		patientSrchResultVO.setProfile(null);
		patientSrchResultVO.setProfileNoLink(null);
		patientSrchResultVO.setProgramAreaCode(null);
		patientSrchResultVO.setProviderFirstName(null);
		patientSrchResultVO.setProviderLastName(null);
		patientSrchResultVO.setProviderPrefix(null);
		patientSrchResultVO.setProviderSuffix(null);
		patientSrchResultVO.setProviderUid(null);
		patientSrchResultVO.setPublicHealthCaseUid(null);
		patientSrchResultVO.setRaceCdColl(null);
		patientSrchResultVO.setRecordStatusCd("ACTIVE");
		patientSrchResultVO.setReportingFacility(null);
		patientSrchResultVO.setReportingFacilityProvider(null);
		patientSrchResultVO.setReportingFacilityProviderPrint(null);
		patientSrchResultVO.setRevisionColl(null);
		patientSrchResultVO.setSex(null);
		patientSrchResultVO.setSsn(null);
		patientSrchResultVO.setStartDate(null);
		patientSrchResultVO.setStartDate_s(null);
		patientSrchResultVO.setSummaryVOColl(null);
		patientSrchResultVO.setTestsString(null);
		patientSrchResultVO.setTestsStringNoLnk(null);
		patientSrchResultVO.setTestsStringPrint(null);
		patientSrchResultVO.setTheResultedSummaryTestVOCollection(null);
		patientSrchResultVO.setTotalCount(null);
		patientSrchResultVO.setType(null);
		patientSrchResultVO.setVersionCtrlNbr(null);
		patientSrchResultVO.setView(null);
		patientSrchResultVO.setViewFile(null);
		patientSrchResultVO.setViewFileWithoutLink(null);
		
		revisionList.add(patientSrchResultVO);
	 return revisionList;
}


@Test
public void showButton_test() throws Exception{
	when(propertyUtil.getHTMLEncodingEnabled()).thenReturn(NEDSSConstants.YES);
	PowerMockito.when(nbsSecurityObjMock.getPermission(NBSBOLookup.PATIENT,
            NBSOperationLookup.ADD)).thenReturn(true);
	mockHttpSession.setAttribute("NBSSecurityObject", nbsSecurityObjMock);
	mockRequest.setHttpSession(mockHttpSession);
	PowerMockito.mockStatic(NBSContext.class);
	NBSContext.store(any(HttpSession.class), any(String.class), any(ArrayList.class));
	when(NBSContext.getCurrentTask(mockRequest.getSession())).thenReturn("TASK");
	searchResultPersonUtil.showButton(mockRequest,"");
	assertEquals("/nbs/TASK.do?ContextAction=Add",mockRequest.getAttribute("addPatHref"));
}
	


}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.servlet"," gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil","gov.cdc.nedss.util.LogUtils",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({SearchResultPersonUtil.class,LogUtils.class,PropertyUtil.class, NEDSSConstants.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class ReplaceDecoration_test {
	
	
	@Mock
	PropertyUtil propertyUtil;
	@org.mockito.Mock
	LogUtils loggerMock;

	@org.mockito.Mock
	HttpServletRequest request;
	
	
	//@InjectMocks
	SearchResultPersonUtil searchResultPersonUtil;
	
	@Mock
	 NBSSecurityObj nbsSecurityObjMock;
	@Mock
	HttpSession httpSession;
	
	MockHttpServletRequest mockRequest = new MockHttpServletRequest();
	MockHttpSession mockHttpSession = new MockHttpSession();
	
	
	 @Before
	    public void initMocks() throws Exception {
		 
		 propertyUtil= Mockito.mock(PropertyUtil.class);
	     Whitebox.setInternalState(PropertyUtil.class,"instance", propertyUtil);
		//searchResultPersonUtil = PowerMockito.spy(new SearchResultPersonUtil());
	     searchResultPersonUtil = new SearchResultPersonUtil();
		 loggerMock = Mockito.mock(LogUtils.class);
		 
		 Whitebox.setInternalState(SearchResultPersonUtil.class, "logger", loggerMock);
		 Whitebox.setInternalState(NEDSSConstants.class, "PERSON", "PSN");
		 
		 nbsSecurityObjMock = Mockito.mock(NBSSecurityObj.class);
		 //Whitebox.setInternalState(SearchResultPersonUtil.class, "instance", nbsSecurityObjMock);
		 
	    }
	 

	

@SuppressWarnings({ "rawtypes", "unchecked" })
private ArrayList  populateData( ) {
	ArrayList  revisionList = new ArrayList<PatientSrchResultVO>();

		PatientSrchResultVO patientSrchResultVO = new PatientSrchResultVO();
		patientSrchResultVO.setAgeReported("28");
		patientSrchResultVO.setAgeUnit(null);
		patientSrchResultVO.setAsOfDateAdmin(null);
		patientSrchResultVO.setAssociatedWith(null);
		patientSrchResultVO.setBirthTime(null);
		patientSrchResultVO.setCaseStatus(null);
		patientSrchResultVO.setCaseStatusCd(null);
		patientSrchResultVO.setCd("PAT");
		patientSrchResultVO.setCondition(null);
		patientSrchResultVO.setConditionCdColl(null);
		patientSrchResultVO.setConditionLink(null);
		patientSrchResultVO.setCurrentSex("F");
		patientSrchResultVO.setDateReceived(null);
		patientSrchResultVO.setDeceasedTime(null);
		patientSrchResultVO.setDegree(null);
		patientSrchResultVO.setDescription(null);
		patientSrchResultVO.setDescriptionPrint(null);
		patientSrchResultVO.setDescriptions(null);
		patientSrchResultVO.setDisplayRevision(null);
		patientSrchResultVO.setDocumentType(null);
		patientSrchResultVO.setDocumentTypeNoLnk(null);
		patientSrchResultVO.setElectronicInd(null);
		patientSrchResultVO.setEthnicGroupInd("2135-2");
		patientSrchResultVO.setInvestigator(null);
		patientSrchResultVO.setInvestigatorFirstName(null);
		patientSrchResultVO.setInvestigatorLastName(null);
		patientSrchResultVO.setInvSummaryVOs(null);
		patientSrchResultVO.setJurisdiction(null);
		patientSrchResultVO.setJurisdictionCd(null);
		patientSrchResultVO.setLink(null);
		patientSrchResultVO.setLocalId("PSN10067029GA01");
		patientSrchResultVO.setMaritalStatusCd(null);
		patientSrchResultVO.setMPRUid(null);
		patientSrchResultVO.setNonStdHivProgramAreaCode(false);
		patientSrchResultVO.setNotification(null);
		patientSrchResultVO.setNotificationCd(null);
		patientSrchResultVO.setObservationUid(null);
		patientSrchResultVO.setPersonAddressProfile(null);
		patientSrchResultVO.setPersonAddressProfileNoLink(null);
		patientSrchResultVO.setPersonDOB(null);
		patientSrchResultVO.setPersonFirstName("Lucy");
		patientSrchResultVO.setPersonFullName(null);
		patientSrchResultVO.setPersonFullNameNoLink(null);
		patientSrchResultVO.setPersonIdColl(null);
		patientSrchResultVO.setPersonIds(null);
		patientSrchResultVO.setPersonIdsNoLink(null);
		patientSrchResultVO.setPersonLastName("Williams");
		patientSrchResultVO.setPersonLocalID("10009438");
		patientSrchResultVO.setPersonLocatorsColl(null);
		patientSrchResultVO.setPersonNameColl(null);
		patientSrchResultVO.setPersonParentUid(null);
		patientSrchResultVO.setPersonPhoneprofile(null);
		patientSrchResultVO.setPersonPhoneprofileNoLink(null);
		patientSrchResultVO.setPersonUID(10009465L);
		patientSrchResultVO.setProfile(null);
		patientSrchResultVO.setProfileNoLink(null);
		patientSrchResultVO.setProgramAreaCode(null);
		patientSrchResultVO.setProviderFirstName(null);
		patientSrchResultVO.setProviderLastName(null);
		patientSrchResultVO.setProviderPrefix(null);
		patientSrchResultVO.setProviderSuffix(null);
		patientSrchResultVO.setProviderUid(null);
		patientSrchResultVO.setPublicHealthCaseUid(null);
		patientSrchResultVO.setRaceCdColl(null);
		patientSrchResultVO.setRecordStatusCd("ACTIVE");
		patientSrchResultVO.setReportingFacility(null);
		patientSrchResultVO.setReportingFacilityProvider(null);
		patientSrchResultVO.setReportingFacilityProviderPrint(null);
		patientSrchResultVO.setRevisionColl(null);
		patientSrchResultVO.setSex(null);
		patientSrchResultVO.setSsn(null);
		patientSrchResultVO.setStartDate(null);
		patientSrchResultVO.setStartDate_s(null);
		patientSrchResultVO.setSummaryVOColl(null);
		patientSrchResultVO.setTestsString(null);
		patientSrchResultVO.setTestsStringNoLnk(null);
		patientSrchResultVO.setTestsStringPrint(null);
		patientSrchResultVO.setTheResultedSummaryTestVOCollection(null);
		patientSrchResultVO.setTotalCount(null);
		patientSrchResultVO.setType(null);
		patientSrchResultVO.setVersionCtrlNbr(null);
		patientSrchResultVO.setView(null);
		patientSrchResultVO.setViewFile(null);
		patientSrchResultVO.setViewFileWithoutLink(null);
		
		revisionList.add(patientSrchResultVO);
	 return revisionList;
}



@Test
public void replaceDecoration_test() throws Exception{
	String oldValue =	"<b>"+ "Cell" +"</b><br>"+"5014582569";
	String expectedValue=searchResultPersonUtil.replaceDecoration(oldValue);
	assertEquals("Cell 5014582569",expectedValue);
	
}
	

}
}