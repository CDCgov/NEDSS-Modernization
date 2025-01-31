package test.gov.cdc.nedss.act.observation.helper;

import static org.mockito.Matchers.any;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationNameDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ProviderDataForPrintVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.UidSummaryVO;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;



@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil",
    "gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl",
    "gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO",
    "gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues",
    "gov.cdc.nedss.act.observation.helper.ObservationProcessor",
     "gov.cdc.nedss.util.NEDSSConstants"
  })
@RunWith(Enclosed.class)
@PrepareForTest({ObservationProcessor.class,WumSqlQuery.class})
@PowerMockIgnore("javax.management.*")
public class ObservationProcessor_tests {
	
	
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.LogUtils","gov.cdc.nedss.util.PropertyUtil",
	                                "gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl",
	                                "gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO",
	                                "gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues",
	                                "gov.cdc.nedss.act.observation.helper.ObservationProcessor",
	                                 "gov.cdc.nedss.util.NEDSSConstants"
	                              })
@RunWith(PowerMockRunner.class)
@PrepareForTest({ObservationProcessor.class,WumSqlQuery.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class GetObservationSummaryListForWorkupRevisited_test {
	String uidType;
	Long uid;
	String type;
    boolean isCDCFormPrintCase;
    int expectedValue;
    int iteration;
    
    public GetObservationSummaryListForWorkupRevisited_test(String uidType,  String type,Long uid, boolean isCDCFormPrintCase, int expectedValue, int iteration) {
		this.uidType = uidType;
		this.uid=uid;
		this.type=type;
		this.isCDCFormPrintCase = isCDCFormPrintCase;
		this.expectedValue=expectedValue;
		this.iteration = iteration;
	}
    
	@Mock
	NBSSecurityObj secObj;
	
	@Mock
	ObservationSummaryDAOImpl osd;
	
	@Mock
	OrganizationNameDAOImpl organizationDao;
	
	@Mock
	RetrieveSummaryVO rSummaryVO;
	
	@Mock
	LabReportSummaryVO labRepEvent;
	
	@Mock
	CachedDropDownValues cddv;
	
	@Mock
	LogUtils logger;
	
	@InjectMocks
	ObservationProcessor observationProcessor;

	
	@Before
	public void initMocks() throws Exception {
		logger=Mockito.mock(LogUtils.class);
		Whitebox.setInternalState(ObservationProcessor.class,"logger", logger);
		Whitebox.setInternalState(WumSqlQuery.class, "SELECT_LABSUMMARY_FORWORKUPNEW", "select");
		Whitebox.setInternalState(WumSqlQuery.class, "SELECT_LABSUMMARY_FORWORKUP", "select");
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getObservationSummaryListForWorkupRevisited_test() throws Exception{
		observationProcessor = PowerMockito.spy(new ObservationProcessor());
		PowerMockito.whenNew(ObservationSummaryDAOImpl.class).withNoArguments().thenReturn(osd);
		PowerMockito.whenNew(RetrieveSummaryVO.class).withNoArguments().thenReturn(rSummaryVO);
		PowerMockito.whenNew(CachedDropDownValues.class).withNoArguments().thenReturn(cddv);
		PowerMockito.whenNew(OrganizationNameDAOImpl.class).withNoArguments().thenReturn(organizationDao);
		Mockito.when(secObj.getDataAccessWhereClause(any(String.class), any(String.class))).thenReturn("whereClause");
		Mockito.doReturn(getLabVoList()).when(observationProcessor).preparedStmtMethod(any(LabReportSummaryVO.class),any(ArrayList.class),any(String.class),any(String.class));
		Mockito.when(osd.getLabParticipations(any(Long.class))).thenReturn(getLabParticipations());
		Mockito.when(osd.getPatientPersonInfo(any(Long.class))).thenReturn(getValList());
		Mockito.when(osd.getProviderInfo(any(Long.class),any(String.class))).thenReturn(getProviderInfo());
		Mockito.when(osd.getActIdDetails(any(Long.class))).thenReturn(getActIdDetails());
		Mockito.when(rSummaryVO.getAssociatedInvList(any(Long.class),any(NBSSecurityObj.class),any(String.class))).thenReturn(getAssociationInvMap());
		Mockito.when(osd.getReportingFacilityName(any(Long.class))).thenReturn("ORG");
		Mockito.when(osd.getSpecimanSource(any(Long.class))).thenReturn("BLDV");
		Mockito.when(cddv.getDescForCode(any(String.class),any(String.class))).thenReturn("Speciman");
		Mockito.when(organizationDao.load(any(Long.class))).thenReturn(getOrganizatonInfo());
		Mockito.when(osd.getOrderingFacilityAddress(any(ProviderDataForPrintVO.class),any(Long.class))).thenReturn(new ProviderDataForPrintVO());
		Mockito.when(osd.getOrderingFacilityPhone(any(ProviderDataForPrintVO.class),any(Long.class))).thenReturn(new ProviderDataForPrintVO());
		Mockito.when(osd.getOrderingPersonAddress(any(ProviderDataForPrintVO.class),any(Long.class))).thenReturn(new ProviderDataForPrintVO());
		Mockito.when(osd.getOrderingPersonPhone(any(ProviderDataForPrintVO.class),any(Long.class))).thenReturn(new ProviderDataForPrintVO());
		PowerMockito.doReturn(12345L).when(observationProcessor, "getProviderInformation", any(ArrayList.class),any(LabReportSummaryVO.class));
		PowerMockito.doNothing().when(observationProcessor, "getTestAndSusceptibilities", any(ArrayList.class),any(LabReportSummaryVO.class),any(LabReportSummaryVO.class));
		PowerMockito.doNothing().when(observationProcessor, "populateDescTxtFromCachedValues", any(ArrayList.class));
		PowerMockito.doNothing().when(observationProcessor, "populateDescTxtFromCachedValues", any(ArrayList.class));
		Object[] oParams=new Object[] {getLabReportUids(),this.isCDCFormPrintCase,secObj,this.uidType};
		HashMap<Object, Object> osMap=Whitebox.invokeMethod(observationProcessor,"getObservationSummaryListForWorkupRevisited", oParams);
        ArrayList<Object>  labSummList=(ArrayList<Object>) osMap.get("labSummList");
		ArrayList<Object>  labEventList=(ArrayList<Object>) osMap.get("labEventList");
		Assert.assertEquals(this.expectedValue,labSummList.size());
		Assert.assertEquals(this.expectedValue,labEventList.size());
		System.out.println("Method:getObservationSummaryListForWorkupRevisited, iteration::::"+this.iteration+", Expected Result:"+this.expectedValue+", Actual Result:"+labSummList.size()+", RESULT::::PASSED");
	}
	
	
	 @Parameterized.Parameters
	   public static Collection input() {
	      return Arrays.asList(new Object[][]{
	    		  {"PERSON_PARENT_UID","PATSBJ",10009286L,true,1,1},
	    		  {"PERSON_PARENT_UID","PATSBJ,SPC",10009287L,true,2,2},
	    		  {"PERSON_PARENT_UID","PATSBJ,ORD",10009288L,true,3,3},
	    		  {"PERSON_PARENT_UID","PATSBJ,AUT",10009289L,true,4,4},
	    		  {"LABORATORY_UID","PATSBJ",10009286L,true,1,5},
	    		  {"LABORATORY_UID","PATSBJ,SPC",10009287L,true,2,6},
	    		  {"LABORATORY_UID","PATSBJ,SPC",10009288L,true,3,7},
	    		  {"LABORATORY_UID","PATSBJ,AUT",10009289L,true,3,8},
	    		  {"PERSON_PARENT_UID","SPC",10009289L,true,0,9},
	    		  {"LABORATORY_UID","SPC",10009289L,true,0,10},
	       });
	   }
	 
	 
	 private ArrayList<Object> getLabVoList(){
		 ArrayList<Object> labVoList= new ArrayList<Object>();
		 LabReportSummaryVO labSummary1= new LabReportSummaryVO();
		 labSummary1.setObservationUid(this.uid);
		 labSummary1.setRecordStatusCd("UNPROCESSED");
		 labVoList.add(labSummary1);

		 return labVoList;
		 
	 }
	 
	 private ArrayList<Object> getValList(){
		 ArrayList<Object> valList= new ArrayList<Object>();
		 valList.add(0,"First Name");
		 valList.add(1,"Last Name");
		 valList.add(2,"LabReports");
		 valList.add(3,"P012345678");
		 return valList;
	 }
	 
	 private ArrayList<Object> getProviderInfo(){
		 ArrayList<Object> orderProviderInfo= new ArrayList<Object> ();
	     orderProviderInfo.add(0, "LastName");
         orderProviderInfo.add(1, "First Name");
         orderProviderInfo.add(2, "Mr.");
         orderProviderInfo.add(3, "Sr.");
         orderProviderInfo.add(4, "None");
         orderProviderInfo.add(5, 0123456L);
         return orderProviderInfo;
	 }
	 
	 private ArrayList<Object> getActIdDetails(){
		 ArrayList<Object> actIdDetails= new ArrayList<Object> ();
		 actIdDetails.add(0, "rootExtTxtDisplay");
		 return actIdDetails;
	 }
	 
	 private Map<Object,Object> getAssociationInvMap(){
		 Map<Object,Object> assocoiatedInvMap= new HashMap<Object,Object> ();
		 assocoiatedInvMap.put("OBS10001002GA01","MRB100");
		 assocoiatedInvMap.put("OBS10001003GA01","MRB161");
		 return assocoiatedInvMap;
	 }
	 
	 private Map<Object,Object> getLabParticipations(){
		 Map<Object,Object> vals = new HashMap<Object,Object>();
		 /*vals.put("PATSBJ", 10009286L);
		 vals.put("SPC", 10009287L);
		 vals.put("ORD", 10009288L);
		 vals.put("AUT", 10009289L);*/
		 StringTokenizer st1 = new StringTokenizer(this.type, ",");
		 while (st1.hasMoreTokens()) {
			 vals.put(st1.nextToken(),this.uid);
		 }
				 
		 return vals;
	 }
	 
	 private ArrayList<Object> getLabReportUids(){
		 ArrayList<Object> uids= new ArrayList<Object> ();
		 if("PERSON_PARENT_UID".equals(this.uidType)){
			 if(this.iteration == 1) {
				  uids.add(0, 10791283L);
			 }else if(this.iteration == 2) {
				 uids.add(0, 10791283L);	 
				 uids.add(1, 10790285L);
			 }else if(this.iteration == 3) {
				 uids.add(0, 10791283L);	 
				 uids.add(1, 10790285L);
				 uids.add(2, 10790283L);
			 }else if(this.iteration == 4) {
				 uids.add(0, 10791283L);	 
				 uids.add(1, 10790285L);
				 uids.add(2, 10790283L);
				 uids.add(3, 10790284L);
			 }
		 } else if("LABORATORY_UID".equals(this.uidType)) {
			 Timestamp ts=new Timestamp(System.currentTimeMillis());
			 if(this.iteration == 5) {
				 UidSummaryVO vo= new UidSummaryVO();
				 vo.setUid(10791283L); 
				 vo.setAddTime(ts);
				 vo.setStatusTime(ts);
				 uids.add(vo);
			 }else if(this.iteration == 6) {
				 UidSummaryVO vo= new UidSummaryVO();
				 vo.setUid(10791283L); 
				 vo.setAddTime(ts);
				 vo.setStatusTime(ts);
				 uids.add(vo);
				 UidSummaryVO vo1= new UidSummaryVO();
				 vo1.setUid(10790285L); 
				 vo1.setAddTime(ts);
				 vo1.setStatusTime(ts);
				 uids.add(vo1);
			 }else if(this.iteration == 7 || this.iteration == 8) {
				 UidSummaryVO vo= new UidSummaryVO();
				 vo.setUid(10791283L); 
				 vo.setAddTime(ts);
				 vo.setStatusTime(ts);
				 uids.add(vo);
				 UidSummaryVO vo1= new UidSummaryVO();
				 vo1.setUid(10790285L); 
				 vo1.setAddTime(ts);
				 vo1.setStatusTime(ts);
				 uids.add(vo1);
				 UidSummaryVO vo2= new UidSummaryVO();
				 vo2.setUid(10790283L); 
				 vo2.setAddTime(ts);
				 vo2.setStatusTime(ts);
				 uids.add(vo2);
			}
	 }
         return uids;
	 }
	 
	 
	 private ArrayList<Object> getOrganizatonInfo(){
		 ArrayList<Object> arrayList = new ArrayList<Object> ();
		 OrganizationNameDT organizationName = new OrganizationNameDT();
		 organizationName.setOrganizationUid(10032447L);
		 organizationName.setOrganizationNameSeq(1);
		 organizationName.setNmTxt("LABCORP");
		 organizationName.setNmUseCd("L");
		 arrayList.add(organizationName);
		 return arrayList;
	 }
	
}

}
	
