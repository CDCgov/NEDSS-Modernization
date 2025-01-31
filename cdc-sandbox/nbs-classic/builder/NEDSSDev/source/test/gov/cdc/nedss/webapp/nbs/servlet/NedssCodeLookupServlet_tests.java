package test.gov.cdc.nedss.webapp.nbs.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSrchResultVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.ProviderSrchResultVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.servlet","gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns"})
@RunWith(Enclosed.class)
@PrepareForTest({NedssCodeLookupServlet.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public class NedssCodeLookupServlet_tests {
	
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.servlet","gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns"," gov.cdc.nedss.util.HTMLEncoder"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({NedssCodeLookupServlet.class,NBSContext.class, HTMLEncoder.class})
@PowerMockIgnore("javax.management.*")
public static class EntitySearchRoute_test {
	
	@Mock
    private PrintWriter mockPrintWriter;
	@org.mockito.Mock
	PropertyUtil propertyUtil;
	@Mock
	CachedDropDowns cachedDropDowns;

	@org.mockito.Mock
	HttpServletRequest request;
	NedssCodeLookupServlet nedssCodeLookupServlet;
	@Mock
	 HttpServletResponse response;
	@Mock
	WebContext wcontext;
	@Mock
	HttpSession httpSession;

	@Mock
	MainSessionCommand msCommand;

	@Mock
	MainSessionHolder holder;

	@Mock
	NBSSecurityObj secObjMock;

	@Mock
	LogUtils logger;
	
	
	 @Before
	    public void initMocks() throws Exception {
		 propertyUtil = PowerMockito.mock(PropertyUtil.class);
		 nedssCodeLookupServlet = new NedssCodeLookupServlet();
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "propUtil", propertyUtil);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "logger", logger); 	
		 PowerMockito.mockStatic(NBSContext.class);
	    }
	   
	 @Test
	 public void entitySearchRoute_test() throws Exception{
		 PowerMockito.when(response.getWriter()).thenReturn(mockPrintWriter);
		 PowerMockito.mockStatic(PropertyUtil.class);
		 PowerMockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
	 	 msCommand = PowerMockito.mock(MainSessionCommand.class);
	 	 when(request.getSession()).thenReturn(httpSession);
	 	 when(holder.getMainSessionCommand(any(HttpSession.class))).thenReturn(msCommand);
	 	 when((MainSessionCommand)httpSession.getAttribute("msCommand")).thenReturn(msCommand);
	 	 ArrayList<ArrayList<Object>> ar=retriveDisplayOrganizationList();
	 	 Mockito. doReturn(ar).when(msCommand).processRequest(any(String.class),any(String.class),any(Object[].class));
	 	 Object[] oParams1 = new Object[] {"1","1","1","1","1","1",false,request,response}; 
	 	 

		    PowerMockito.mockStatic(HTMLEncoder.class);
				 PowerMockito.doReturn("test").when(HTMLEncoder.class, "sanitizeHtml",any(String.class));

				 
				 
	 	 Whitebox.invokeMethod(new NedssCodeLookupServlet(), "entitySearchRoute", oParams1);
	 }






public ArrayList<ArrayList<Object>> retriveDisplayPersonList( ) throws NEDSSSystemException
{
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayPersonList displayPersonList = null;
	ArrayList<Object> searchResult = new ArrayList<Object>();
	int listCount = 0; 
	

	ProviderSrchResultVO srchResultVO = new ProviderSrchResultVO();
	ArrayList<Object> nameList = new ArrayList<Object>();
	PersonNameDT nameDT = new PersonNameDT();
	nameDT.setPersonUid(123L);
	nameDT.setFirstNm("FirstNm");
	nameDT.setLastNm("LastNm");
	nameDT.setNmSuffix("nmSuffix");
	nameDT.setNmDegree("nmDegree"); 
	nameDT.setNmUseCd("nmUseCd");
	nameList.add(nameDT);
	srchResultVO.setPersonDOB("personDOB");
	srchResultVO.setCurrentSex("currentSex");
	srchResultVO.setPersonUID(123L);
	srchResultVO.setPersonLocalID("personLocalId");
	srchResultVO.setVersionCtrlNbr(1);
	srchResultVO.setPersonNameColl(nameList);
	ArrayList<Object> locatorList = new ArrayList<Object>();

	

	srchResultVO.setPersonUID(125L);
	srchResultVO.setVersionCtrlNbr(14);
	EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
	entityLocatorDT.setCd("2");
	entityLocatorDT.setCdDescTxt("");
	entityLocatorDT.setClassCd("");
	entityLocatorDT.setEntityUid(123L);
	entityLocatorDT.setLocatorUid(345L);
	entityLocatorDT.setUseCd("3");
	entityLocatorDT.setCd("4");
	PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
	postalLocatorDT.setStateCd("037");
	postalLocatorDT.setCityCd("171");
	postalLocatorDT.setCityDescTxt("text");
	postalLocatorDT.setStreetAddr1("1011 st, Atlanta");
	postalLocatorDT.setStreetAddr2("1022 st, City");
	postalLocatorDT.setPostalLocatorUid(12345L);
	postalLocatorDT.setZipCd("11234");
	entityLocatorDT
			.setThePostalLocatorDT(postalLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added address locator: ");


	TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
	teleLocatorDT.setPhoneNbrTxt("5017458795");
	teleLocatorDT.setExtensionTxt("2351");
	teleLocatorDT.setEmailAddress("Email");
	entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added tele locator: ");


	srchResultVO.setPersonLocatorsColl(locatorList);
	// For ID
	ArrayList<Object> entityIdList = new ArrayList<Object>();

	EntityIdDT entityIdDT = new EntityIdDT();
	 
	entityIdDT.setEntityUid(123L );
	srchResultVO.setVersionCtrlNbr(123 );
	entityIdDT.setTypeCd("typeCd"); 
	entityIdDT.setTypeDescTxt("typeDesc");
	entityIdDT.setAssigningAuthorityCd("AssigningAuthorityCd");
	entityIdDT.setAssigningAuthorityDescTxt("AssigningAuthorityDescTxt");
	entityIdDT.setRootExtensionTxt("RootExtensionTxt");
	entityIdList.add(entityIdDT);
	srchResultVO.setPersonIdColl(entityIdList);
	searchResult.add(srchResultVO);
	

	ArrayList<Object> cacheList = new ArrayList<Object>();
	for (int j = 0; j < searchResult.size(); j++) {
		cacheList.add(searchResult.get(j));
		listCount++;
	}
	ArrayList<Object> displayList = new ArrayList<Object>();
	displayPersonList = new DisplayPersonList(1, cacheList, 0, listCount);
	displayList.add(displayPersonList);
	
	listOfArrays.add(displayList);
	return listOfArrays;
	
}
public ArrayList<ArrayList<Object>> retriveDisplayOrganizationList( ) throws NEDSSSystemException
{
	
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayOrganizationList displayOrganizationList = null;

	int listCount = 0;
	try {

		ArrayList<Object> searchResult = new ArrayList<Object>();

		OrganizationSrchResultVO srchResultVO = new OrganizationSrchResultVO();
		ArrayList<Object> nameList = new ArrayList<Object>();
		Long organizationUid = 123L;
		OrganizationNameDT nameDT = new OrganizationNameDT();
		nameDT.setOrganizationUid(123L);
		nameDT.setNmTxt("nmTxt");
		nameDT.setNmUseCd("nmUseCd");
		nameList.add(nameDT);

		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setOrganizationId("1234");
		srchResultVO.setVersionCtrlNbr(123);
		srchResultVO.setOrganizationNameColl(nameList);

		ArrayList<Object> locatorList = new ArrayList<Object>();

		EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setVersionCtrlNbr(1);
		entityLocatorDT.setCd("123");
		entityLocatorDT.setCdDescTxt("cdDescTxt");
		entityLocatorDT.setClassCd("classCd");
		entityLocatorDT.setEntityUid(123L);
		entityLocatorDT.setLocatorUid(123L);
		entityLocatorDT.setUseCd("useCd");

		entityLocatorDT.setCd("cd");
		PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
		postalLocatorDT.setStateCd("stateCd");
		postalLocatorDT.setCityCd("cityCd");
		postalLocatorDT.setCityDescTxt("cityDescTxt");
		postalLocatorDT.setStreetAddr1("streetAddr1");
		postalLocatorDT.setStreetAddr2("streetAddr2");
		postalLocatorDT.setPostalLocatorUid(123L);
		postalLocatorDT.setZipCd("zipCd");
		postalLocatorDT.setCntyCd("cntyCd");
		entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
		locatorList.add(entityLocatorDT);


		srchResultVO.setOrganizationLocatorsColl(locatorList);
		ArrayList<Object> entityIdList = new ArrayList<Object>();
		EntityIdDT entityIdDT = new EntityIdDT();
		srchResultVO.setOrganizationUID(organizationUid);
		entityIdDT.setEntityUid(organizationUid);
		srchResultVO.setVersionCtrlNbr(123);
		entityIdDT.setTypeCd("typeCd");

		entityIdDT.setTypeDescTxt("typeDescTxt");
		entityIdDT.setAssigningAuthorityCd("assigningAuthorityCd");
		entityIdDT.setAssigningAuthorityDescTxt("assigningAuthorityDescTxt");
		entityIdDT.setRootExtensionTxt("rootExtensionTxt");
		entityIdList.add(entityIdDT);

		srchResultVO.setOrganizationIdColl(entityIdList);
		searchResult.add(srchResultVO);
		

		ArrayList<Object> cacheList = new ArrayList<Object>();
		for (int j = 0; j < searchResult.size(); j++) {
			cacheList.add(searchResult.get(j));
			listCount++;
		}
		ArrayList<Object> displayList = new ArrayList<Object>();
		displayOrganizationList = new DisplayOrganizationList(1, cacheList, 0, listCount);
		displayList.add(displayOrganizationList);
		
		listOfArrays.add(displayList);
		return listOfArrays;
	} catch (Exception ex) {
		throw new NEDSSSystemException(ex.toString());
	}
}






}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.servlet","gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({NedssCodeLookupServlet.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class GetOrgValue_test {
	
	@Mock
    private PrintWriter mockPrintWriter;
	@org.mockito.Mock
	PropertyUtil propertyUtil;
	@Mock
	CachedDropDowns cachedDropDowns;

	@org.mockito.Mock
	HttpServletRequest request;
	NedssCodeLookupServlet nedssCodeLookupServlet;
	@Mock
	 HttpServletResponse response;
	@Mock
	WebContext wcontext;
	@Mock
	HttpSession httpSession;

	@Mock
	MainSessionCommand msCommand;

	@Mock
	MainSessionHolder holder;

	@Mock
	NBSSecurityObj secObjMock;

	@Mock
	LogUtils logger;
	
	
	 @Before
	    public void initMocks() throws Exception {
		 propertyUtil = PowerMockito.mock(PropertyUtil.class);
		 nedssCodeLookupServlet = new NedssCodeLookupServlet();
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "propUtil", propertyUtil);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "logger", logger); 	
		 PowerMockito.mockStatic(NBSContext.class);
	    }
	   


@Test
public void getOrgValue_test() throws Exception{
	
	 msCommand = PowerMockito.mock(MainSessionCommand.class);
	 when(request.getSession()).thenReturn(httpSession);
	 when(holder.getMainSessionCommand(any(HttpSession.class))).thenReturn(msCommand);
	 when((MainSessionCommand)httpSession.getAttribute("msCommand")).thenReturn(msCommand);
	 
	 PowerMockito.mockStatic(CachedDropDowns.class);
	 PowerMockito.when(CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WORK_PHONE, "EL_USE_PST_ORG")).thenReturn("10");
	 ArrayList<ArrayList<Object>> ar=retriveDisplayOrganizationList();
	 Mockito.doReturn(ar).when(msCommand).processRequest(any(String.class),any(String.class),any(Object[].class));
	 Object[] oParams1 = new Object[] {"1",httpSession};
	 Map<Object,Object> returnMap =  Whitebox.invokeMethod(new NedssCodeLookupServlet(), "getOrgValue", oParams1);
	 
	 assertEquals(123L, returnMap.get("UID"));
	 assertEquals("123", returnMap.get("versionCtrlNbr"));
	 assertEquals("nmTxt<br>", returnMap.get("result"));
	
}






public ArrayList<ArrayList<Object>> retriveDisplayPersonList( ) throws NEDSSSystemException
{
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayPersonList displayPersonList = null;
	ArrayList<Object> searchResult = new ArrayList<Object>();
	int listCount = 0; 
	

	ProviderSrchResultVO srchResultVO = new ProviderSrchResultVO();
	ArrayList<Object> nameList = new ArrayList<Object>();
	PersonNameDT nameDT = new PersonNameDT();
	nameDT.setPersonUid(123L);
	nameDT.setFirstNm("FirstNm");
	nameDT.setLastNm("LastNm");
	nameDT.setNmSuffix("nmSuffix");
	nameDT.setNmDegree("nmDegree"); 
	nameDT.setNmUseCd("nmUseCd");
	nameList.add(nameDT);
	srchResultVO.setPersonDOB("personDOB");
	srchResultVO.setCurrentSex("currentSex");
	srchResultVO.setPersonUID(123L);
	srchResultVO.setPersonLocalID("personLocalId");
	srchResultVO.setVersionCtrlNbr(1);
	srchResultVO.setPersonNameColl(nameList);
	ArrayList<Object> locatorList = new ArrayList<Object>();

	

	srchResultVO.setPersonUID(125L);
	srchResultVO.setVersionCtrlNbr(14);
	EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
	entityLocatorDT.setCd("2");
	entityLocatorDT.setCdDescTxt("");
	entityLocatorDT.setClassCd("");
	entityLocatorDT.setEntityUid(123L);
	entityLocatorDT.setLocatorUid(345L);
	entityLocatorDT.setUseCd("3");
	entityLocatorDT.setCd("4");
	PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
	postalLocatorDT.setStateCd("037");
	postalLocatorDT.setCityCd("171");
	postalLocatorDT.setCityDescTxt("text");
	postalLocatorDT.setStreetAddr1("1011 st, Atlanta");
	postalLocatorDT.setStreetAddr2("1022 st, City");
	postalLocatorDT.setPostalLocatorUid(12345L);
	postalLocatorDT.setZipCd("11234");
	entityLocatorDT
			.setThePostalLocatorDT(postalLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added address locator: ");


	TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
	teleLocatorDT.setPhoneNbrTxt("5017458795");
	teleLocatorDT.setExtensionTxt("2351");
	teleLocatorDT.setEmailAddress("Email");
	entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added tele locator: ");


	srchResultVO.setPersonLocatorsColl(locatorList);
	// For ID
	ArrayList<Object> entityIdList = new ArrayList<Object>();

	EntityIdDT entityIdDT = new EntityIdDT();
	 
	entityIdDT.setEntityUid(123L );
	srchResultVO.setVersionCtrlNbr(123 );
	entityIdDT.setTypeCd("typeCd"); 
	entityIdDT.setTypeDescTxt("typeDesc");
	entityIdDT.setAssigningAuthorityCd("AssigningAuthorityCd");
	entityIdDT.setAssigningAuthorityDescTxt("AssigningAuthorityDescTxt");
	entityIdDT.setRootExtensionTxt("RootExtensionTxt");
	entityIdList.add(entityIdDT);
	srchResultVO.setPersonIdColl(entityIdList);
	searchResult.add(srchResultVO);
	

	ArrayList<Object> cacheList = new ArrayList<Object>();
	for (int j = 0; j < searchResult.size(); j++) {
		cacheList.add(searchResult.get(j));
		listCount++;
	}
	ArrayList<Object> displayList = new ArrayList<Object>();
	displayPersonList = new DisplayPersonList(1, cacheList, 0, listCount);
	displayList.add(displayPersonList);
	
	listOfArrays.add(displayList);
	return listOfArrays;
	
}
public ArrayList<ArrayList<Object>> retriveDisplayOrganizationList( ) throws NEDSSSystemException
{
	
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayOrganizationList displayOrganizationList = null;

	int listCount = 0;
	try {

		ArrayList<Object> searchResult = new ArrayList<Object>();

		OrganizationSrchResultVO srchResultVO = new OrganizationSrchResultVO();
		ArrayList<Object> nameList = new ArrayList<Object>();
		Long organizationUid = 123L;
		OrganizationNameDT nameDT = new OrganizationNameDT();
		nameDT.setOrganizationUid(123L);
		nameDT.setNmTxt("nmTxt");
		nameDT.setNmUseCd("nmUseCd");
		nameList.add(nameDT);

		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setOrganizationId("1234");
		srchResultVO.setVersionCtrlNbr(123);
		srchResultVO.setOrganizationNameColl(nameList);

		ArrayList<Object> locatorList = new ArrayList<Object>();

		EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setVersionCtrlNbr(1);
		entityLocatorDT.setCd("123");
		entityLocatorDT.setCdDescTxt("cdDescTxt");
		entityLocatorDT.setClassCd("classCd");
		entityLocatorDT.setEntityUid(123L);
		entityLocatorDT.setLocatorUid(123L);
		entityLocatorDT.setUseCd("useCd");

		entityLocatorDT.setCd("cd");
		PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
		postalLocatorDT.setStateCd("stateCd");
		postalLocatorDT.setCityCd("cityCd");
		postalLocatorDT.setCityDescTxt("cityDescTxt");
		postalLocatorDT.setStreetAddr1("streetAddr1");
		postalLocatorDT.setStreetAddr2("streetAddr2");
		postalLocatorDT.setPostalLocatorUid(123L);
		postalLocatorDT.setZipCd("zipCd");
		postalLocatorDT.setCntyCd("cntyCd");
		entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
		locatorList.add(entityLocatorDT);


		srchResultVO.setOrganizationLocatorsColl(locatorList);
		ArrayList<Object> entityIdList = new ArrayList<Object>();
		EntityIdDT entityIdDT = new EntityIdDT();
		srchResultVO.setOrganizationUID(organizationUid);
		entityIdDT.setEntityUid(organizationUid);
		srchResultVO.setVersionCtrlNbr(123);
		entityIdDT.setTypeCd("typeCd");

		entityIdDT.setTypeDescTxt("typeDescTxt");
		entityIdDT.setAssigningAuthorityCd("assigningAuthorityCd");
		entityIdDT.setAssigningAuthorityDescTxt("assigningAuthorityDescTxt");
		entityIdDT.setRootExtensionTxt("rootExtensionTxt");
		entityIdList.add(entityIdDT);

		srchResultVO.setOrganizationIdColl(entityIdList);
		searchResult.add(srchResultVO);
		

		ArrayList<Object> cacheList = new ArrayList<Object>();
		for (int j = 0; j < searchResult.size(); j++) {
			cacheList.add(searchResult.get(j));
			listCount++;
		}
		ArrayList<Object> displayList = new ArrayList<Object>();
		displayOrganizationList = new DisplayOrganizationList(1, cacheList, 0, listCount);
		displayList.add(displayOrganizationList);
		
		listOfArrays.add(displayList);
		return listOfArrays;
	} catch (Exception ex) {
		throw new NEDSSSystemException(ex.toString());
	}
}






}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.servlet","gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({NedssCodeLookupServlet.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class GetPersonValue_test {
	
	@Mock
    private PrintWriter mockPrintWriter;
	@org.mockito.Mock
	PropertyUtil propertyUtil;
	@Mock
	CachedDropDowns cachedDropDowns;

	@org.mockito.Mock
	HttpServletRequest request;
	NedssCodeLookupServlet nedssCodeLookupServlet;
	@Mock
	 HttpServletResponse response;
	@Mock
	WebContext wcontext;
	@Mock
	HttpSession httpSession;

	@Mock
	MainSessionCommand msCommand;

	@Mock
	MainSessionHolder holder;

	@Mock
	NBSSecurityObj secObjMock;

	@Mock
	LogUtils logger;
	
	
	 @Before
	    public void initMocks() throws Exception {
		 propertyUtil = PowerMockito.mock(PropertyUtil.class);
		 nedssCodeLookupServlet = new NedssCodeLookupServlet();
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "propUtil", propertyUtil);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "logger", logger); 	
		 PowerMockito.mockStatic(NBSContext.class);
	    }
	   

@Test
public void getPersonValue_test() throws Exception{
	PowerMockito.mockStatic(PropertyUtil.class);
	PowerMockito.when(PropertyUtil.getInstance()).thenReturn(propertyUtil);
	
	 msCommand = PowerMockito.mock(MainSessionCommand.class);
	 when(request.getSession()).thenReturn(httpSession);
	 when(holder.getMainSessionCommand(any(HttpSession.class))).thenReturn(msCommand);
	 when((MainSessionCommand)httpSession.getAttribute("msCommand")).thenReturn(msCommand);
	 
	 PowerMockito.mockStatic(CachedDropDowns.class);
	 PowerMockito.when(CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WORK_PHONE, "EL_USE_PST_ORG")).thenReturn("10");
	 ArrayList<ArrayList<Object>> ar=retriveDisplayPersonList();
	 Mockito.doReturn(ar).when(msCommand).processRequest(any(String.class),any(String.class),any(Object[].class));
	 
	 
	 
	 
	 
	 Object[] oParams1 = new Object[] {"","1",httpSession};
	 Map<Object,Object> returnMap =  Whitebox.invokeMethod(new NedssCodeLookupServlet(), "getPersonValue", oParams1);
	 
	
	 assertEquals(125L, returnMap.get("UID"));
	 assertEquals("123", returnMap.get("versionCtrlNbr"));
	 assertEquals("FirstNm LastNm, nmSuffix, nmDegree", returnMap.get("result"));
	 
}






public ArrayList<ArrayList<Object>> retriveDisplayPersonList( ) throws NEDSSSystemException
{
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayPersonList displayPersonList = null;
	ArrayList<Object> searchResult = new ArrayList<Object>();
	int listCount = 0; 
	

	ProviderSrchResultVO srchResultVO = new ProviderSrchResultVO();
	ArrayList<Object> nameList = new ArrayList<Object>();
	PersonNameDT nameDT = new PersonNameDT();
	nameDT.setPersonUid(123L);
	nameDT.setFirstNm("FirstNm");
	nameDT.setLastNm("LastNm");
	nameDT.setNmSuffix("nmSuffix");
	nameDT.setNmDegree("nmDegree"); 
	nameDT.setNmUseCd("nmUseCd");
	nameList.add(nameDT);
	srchResultVO.setPersonDOB("personDOB");
	srchResultVO.setCurrentSex("currentSex");
	srchResultVO.setPersonUID(123L);
	srchResultVO.setPersonLocalID("personLocalId");
	srchResultVO.setVersionCtrlNbr(1);
	srchResultVO.setPersonNameColl(nameList);
	ArrayList<Object> locatorList = new ArrayList<Object>();

	

	srchResultVO.setPersonUID(125L);
	srchResultVO.setVersionCtrlNbr(14);
	EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
	entityLocatorDT.setCd("2");
	entityLocatorDT.setCdDescTxt("");
	entityLocatorDT.setClassCd("");
	entityLocatorDT.setEntityUid(123L);
	entityLocatorDT.setLocatorUid(345L);
	entityLocatorDT.setUseCd("3");
	entityLocatorDT.setCd("4");
	PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
	postalLocatorDT.setStateCd("037");
	postalLocatorDT.setCityCd("171");
	postalLocatorDT.setCityDescTxt("text");
	postalLocatorDT.setStreetAddr1("1011 st, Atlanta");
	postalLocatorDT.setStreetAddr2("1022 st, City");
	postalLocatorDT.setPostalLocatorUid(12345L);
	postalLocatorDT.setZipCd("11234");
	entityLocatorDT
			.setThePostalLocatorDT(postalLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added address locator: ");


	TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
	teleLocatorDT.setPhoneNbrTxt("5017458795");
	teleLocatorDT.setExtensionTxt("2351");
	teleLocatorDT.setEmailAddress("Email");
	entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added tele locator: ");


	srchResultVO.setPersonLocatorsColl(locatorList);
	// For ID
	ArrayList<Object> entityIdList = new ArrayList<Object>();

	EntityIdDT entityIdDT = new EntityIdDT();
	 
	entityIdDT.setEntityUid(123L );
	srchResultVO.setVersionCtrlNbr(123 );
	entityIdDT.setTypeCd("typeCd"); 
	entityIdDT.setTypeDescTxt("typeDesc");
	entityIdDT.setAssigningAuthorityCd("AssigningAuthorityCd");
	entityIdDT.setAssigningAuthorityDescTxt("AssigningAuthorityDescTxt");
	entityIdDT.setRootExtensionTxt("RootExtensionTxt");
	entityIdList.add(entityIdDT);
	srchResultVO.setPersonIdColl(entityIdList);
	searchResult.add(srchResultVO);
	

	ArrayList<Object> cacheList = new ArrayList<Object>();
	for (int j = 0; j < searchResult.size(); j++) {
		cacheList.add(searchResult.get(j));
		listCount++;
	}
	ArrayList<Object> displayList = new ArrayList<Object>();
	displayPersonList = new DisplayPersonList(1, cacheList, 0, listCount);
	displayList.add(displayPersonList);
	
	listOfArrays.add(displayList);
	return listOfArrays;
	
}
public ArrayList<ArrayList<Object>> retriveDisplayOrganizationList( ) throws NEDSSSystemException
{
	
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayOrganizationList displayOrganizationList = null;

	int listCount = 0;
	try {

		ArrayList<Object> searchResult = new ArrayList<Object>();

		OrganizationSrchResultVO srchResultVO = new OrganizationSrchResultVO();
		ArrayList<Object> nameList = new ArrayList<Object>();
		Long organizationUid = 123L;
		OrganizationNameDT nameDT = new OrganizationNameDT();
		nameDT.setOrganizationUid(123L);
		nameDT.setNmTxt("nmTxt");
		nameDT.setNmUseCd("nmUseCd");
		nameList.add(nameDT);

		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setOrganizationId("1234");
		srchResultVO.setVersionCtrlNbr(123);
		srchResultVO.setOrganizationNameColl(nameList);

		ArrayList<Object> locatorList = new ArrayList<Object>();

		EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setVersionCtrlNbr(1);
		entityLocatorDT.setCd("123");
		entityLocatorDT.setCdDescTxt("cdDescTxt");
		entityLocatorDT.setClassCd("classCd");
		entityLocatorDT.setEntityUid(123L);
		entityLocatorDT.setLocatorUid(123L);
		entityLocatorDT.setUseCd("useCd");

		entityLocatorDT.setCd("cd");
		PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
		postalLocatorDT.setStateCd("stateCd");
		postalLocatorDT.setCityCd("cityCd");
		postalLocatorDT.setCityDescTxt("cityDescTxt");
		postalLocatorDT.setStreetAddr1("streetAddr1");
		postalLocatorDT.setStreetAddr2("streetAddr2");
		postalLocatorDT.setPostalLocatorUid(123L);
		postalLocatorDT.setZipCd("zipCd");
		postalLocatorDT.setCntyCd("cntyCd");
		entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
		locatorList.add(entityLocatorDT);


		srchResultVO.setOrganizationLocatorsColl(locatorList);
		ArrayList<Object> entityIdList = new ArrayList<Object>();
		EntityIdDT entityIdDT = new EntityIdDT();
		srchResultVO.setOrganizationUID(organizationUid);
		entityIdDT.setEntityUid(organizationUid);
		srchResultVO.setVersionCtrlNbr(123);
		entityIdDT.setTypeCd("typeCd");

		entityIdDT.setTypeDescTxt("typeDescTxt");
		entityIdDT.setAssigningAuthorityCd("assigningAuthorityCd");
		entityIdDT.setAssigningAuthorityDescTxt("assigningAuthorityDescTxt");
		entityIdDT.setRootExtensionTxt("rootExtensionTxt");
		entityIdList.add(entityIdDT);

		srchResultVO.setOrganizationIdColl(entityIdList);
		searchResult.add(srchResultVO);
		

		ArrayList<Object> cacheList = new ArrayList<Object>();
		for (int j = 0; j < searchResult.size(); j++) {
			cacheList.add(searchResult.get(j));
			listCount++;
		}
		ArrayList<Object> displayList = new ArrayList<Object>();
		displayOrganizationList = new DisplayOrganizationList(1, cacheList, 0, listCount);
		displayList.add(displayOrganizationList);
		
		listOfArrays.add(displayList);
		return listOfArrays;
	} catch (Exception ex) {
		throw new NEDSSSystemException(ex.toString());
	}
}






}




@SuppressStaticInitializationFor ({"gov.cdc.nedss.webapp.nbs.servlet","gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet",
	"gov.cdc.nedss.util.NEDSSConstants","gov.cdc.nedss.util.PropertyUtil","gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns"})
@RunWith(PowerMockRunner.class)//powermock-module-junit4.jar\
@PrepareForTest({NedssCodeLookupServlet.class,NBSContext.class})
@PowerMockIgnore("javax.management.*")
public static class GetCorrectParameter_test {
	
	@Mock
    private PrintWriter mockPrintWriter;
	@org.mockito.Mock
	PropertyUtil propertyUtil;
	@Mock
	CachedDropDowns cachedDropDowns;

	@org.mockito.Mock
	HttpServletRequest request;
	NedssCodeLookupServlet nedssCodeLookupServlet;
	@Mock
	 HttpServletResponse response;
	@Mock
	WebContext wcontext;
	@Mock
	HttpSession httpSession;

	@Mock
	MainSessionCommand msCommand;

	@Mock
	MainSessionHolder holder;

	@Mock
	NBSSecurityObj secObjMock;

	@Mock
	LogUtils logger;
	
	
	 @Before
	    public void initMocks() throws Exception {
		 propertyUtil = PowerMockito.mock(PropertyUtil.class);
		 nedssCodeLookupServlet = new NedssCodeLookupServlet();
		 logger = Mockito.mock(LogUtils.class);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "propUtil", propertyUtil);
		 Whitebox.setInternalState(NedssCodeLookupServlet.class, "logger", logger); 	
		 PowerMockito.mockStatic(NBSContext.class);
	    }
	   




public ArrayList<ArrayList<Object>> retriveDisplayPersonList( ) throws NEDSSSystemException
{
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayPersonList displayPersonList = null;
	ArrayList<Object> searchResult = new ArrayList<Object>();
	int listCount = 0; 
	

	ProviderSrchResultVO srchResultVO = new ProviderSrchResultVO();
	ArrayList<Object> nameList = new ArrayList<Object>();
	PersonNameDT nameDT = new PersonNameDT();
	nameDT.setPersonUid(123L);
	nameDT.setFirstNm("FirstNm");
	nameDT.setLastNm("LastNm");
	nameDT.setNmSuffix("nmSuffix");
	nameDT.setNmDegree("nmDegree"); 
	nameDT.setNmUseCd("nmUseCd");
	nameList.add(nameDT);
	srchResultVO.setPersonDOB("personDOB");
	srchResultVO.setCurrentSex("currentSex");
	srchResultVO.setPersonUID(123L);
	srchResultVO.setPersonLocalID("personLocalId");
	srchResultVO.setVersionCtrlNbr(1);
	srchResultVO.setPersonNameColl(nameList);
	ArrayList<Object> locatorList = new ArrayList<Object>();

	

	srchResultVO.setPersonUID(125L);
	srchResultVO.setVersionCtrlNbr(14);
	EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
	entityLocatorDT.setCd("2");
	entityLocatorDT.setCdDescTxt("");
	entityLocatorDT.setClassCd("");
	entityLocatorDT.setEntityUid(123L);
	entityLocatorDT.setLocatorUid(345L);
	entityLocatorDT.setUseCd("3");
	entityLocatorDT.setCd("4");
	PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
	postalLocatorDT.setStateCd("037");
	postalLocatorDT.setCityCd("171");
	postalLocatorDT.setCityDescTxt("text");
	postalLocatorDT.setStreetAddr1("1011 st, Atlanta");
	postalLocatorDT.setStreetAddr2("1022 st, City");
	postalLocatorDT.setPostalLocatorUid(12345L);
	postalLocatorDT.setZipCd("11234");
	entityLocatorDT
			.setThePostalLocatorDT(postalLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added address locator: ");


	TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
	teleLocatorDT.setPhoneNbrTxt("5017458795");
	teleLocatorDT.setExtensionTxt("2351");
	teleLocatorDT.setEmailAddress("Email");
	entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
	locatorList.add(entityLocatorDT);
	logger.debug("Added tele locator: ");


	srchResultVO.setPersonLocatorsColl(locatorList);
	// For ID
	ArrayList<Object> entityIdList = new ArrayList<Object>();

	EntityIdDT entityIdDT = new EntityIdDT();
	 
	entityIdDT.setEntityUid(123L );
	srchResultVO.setVersionCtrlNbr(123 );
	entityIdDT.setTypeCd("typeCd"); 
	entityIdDT.setTypeDescTxt("typeDesc");
	entityIdDT.setAssigningAuthorityCd("AssigningAuthorityCd");
	entityIdDT.setAssigningAuthorityDescTxt("AssigningAuthorityDescTxt");
	entityIdDT.setRootExtensionTxt("RootExtensionTxt");
	entityIdList.add(entityIdDT);
	srchResultVO.setPersonIdColl(entityIdList);
	searchResult.add(srchResultVO);
	

	ArrayList<Object> cacheList = new ArrayList<Object>();
	for (int j = 0; j < searchResult.size(); j++) {
		cacheList.add(searchResult.get(j));
		listCount++;
	}
	ArrayList<Object> displayList = new ArrayList<Object>();
	displayPersonList = new DisplayPersonList(1, cacheList, 0, listCount);
	displayList.add(displayPersonList);
	
	listOfArrays.add(displayList);
	return listOfArrays;
	
}
public ArrayList<ArrayList<Object>> retriveDisplayOrganizationList( ) throws NEDSSSystemException
{
	
	ArrayList<ArrayList<Object>> listOfArrays = new ArrayList<>();
	DisplayOrganizationList displayOrganizationList = null;

	int listCount = 0;
	try {

		ArrayList<Object> searchResult = new ArrayList<Object>();

		OrganizationSrchResultVO srchResultVO = new OrganizationSrchResultVO();
		ArrayList<Object> nameList = new ArrayList<Object>();
		Long organizationUid = 123L;
		OrganizationNameDT nameDT = new OrganizationNameDT();
		nameDT.setOrganizationUid(123L);
		nameDT.setNmTxt("nmTxt");
		nameDT.setNmUseCd("nmUseCd");
		nameList.add(nameDT);

		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setOrganizationId("1234");
		srchResultVO.setVersionCtrlNbr(123);
		srchResultVO.setOrganizationNameColl(nameList);

		ArrayList<Object> locatorList = new ArrayList<Object>();

		EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
		srchResultVO.setOrganizationUID(organizationUid);
		srchResultVO.setVersionCtrlNbr(1);
		entityLocatorDT.setCd("123");
		entityLocatorDT.setCdDescTxt("cdDescTxt");
		entityLocatorDT.setClassCd("classCd");
		entityLocatorDT.setEntityUid(123L);
		entityLocatorDT.setLocatorUid(123L);
		entityLocatorDT.setUseCd("useCd");

		entityLocatorDT.setCd("cd");
		PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
		postalLocatorDT.setStateCd("stateCd");
		postalLocatorDT.setCityCd("cityCd");
		postalLocatorDT.setCityDescTxt("cityDescTxt");
		postalLocatorDT.setStreetAddr1("streetAddr1");
		postalLocatorDT.setStreetAddr2("streetAddr2");
		postalLocatorDT.setPostalLocatorUid(123L);
		postalLocatorDT.setZipCd("zipCd");
		postalLocatorDT.setCntyCd("cntyCd");
		entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
		locatorList.add(entityLocatorDT);


		srchResultVO.setOrganizationLocatorsColl(locatorList);
		ArrayList<Object> entityIdList = new ArrayList<Object>();
		EntityIdDT entityIdDT = new EntityIdDT();
		srchResultVO.setOrganizationUID(organizationUid);
		entityIdDT.setEntityUid(organizationUid);
		srchResultVO.setVersionCtrlNbr(123);
		entityIdDT.setTypeCd("typeCd");

		entityIdDT.setTypeDescTxt("typeDescTxt");
		entityIdDT.setAssigningAuthorityCd("assigningAuthorityCd");
		entityIdDT.setAssigningAuthorityDescTxt("assigningAuthorityDescTxt");
		entityIdDT.setRootExtensionTxt("rootExtensionTxt");
		entityIdList.add(entityIdDT);

		srchResultVO.setOrganizationIdColl(entityIdList);
		searchResult.add(srchResultVO);
		

		ArrayList<Object> cacheList = new ArrayList<Object>();
		for (int j = 0; j < searchResult.size(); j++) {
			cacheList.add(searchResult.get(j));
			listCount++;
		}
		ArrayList<Object> displayList = new ArrayList<Object>();
		displayOrganizationList = new DisplayOrganizationList(1, cacheList, 0, listCount);
		displayList.add(displayOrganizationList);
		
		listOfArrays.add(displayList);
		return listOfArrays;
	} catch (Exception ex) {
		throw new NEDSSSystemException(ex.toString());
	}
}




@Test
public void getCorrectParameter_test() throws Exception{
	//PowerMockito.when(propertyUtilMocked.getHTMLEncodingEnabled()).thenReturn(NEDSSConstants.YES);
	PowerMockito.when(request.getParameter("null")).thenReturn("null");;
	PowerMockito.when(request.getParameter(null)).thenReturn(null);
	String out = nedssCodeLookupServlet.getCorrectParameter(request, "null");
	String out1 = nedssCodeLookupServlet.getCorrectParameter(request, null);
	
	PowerMockito.when(request.getParameter("3")).thenReturn("3");;
	String out2 = nedssCodeLookupServlet.getCorrectParameter(request, "3");
	
	Assert.assertEquals("null", out);
	Assert.assertEquals(null, out1);
	Assert.assertEquals("3", out2);

	}

}
}
