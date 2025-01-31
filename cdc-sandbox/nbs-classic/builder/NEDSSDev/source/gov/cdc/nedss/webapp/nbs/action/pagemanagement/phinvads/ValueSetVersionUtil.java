package gov.cdc.nedss.webapp.nbs.action.pagemanagement.phinvads;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PhinVadsSystemVO;
import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.srtadmin.dt.CodeSetGpMetaDataDT;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.managerules.ManageRulesAction;
import gov.cdc.vocab.service.VocabService;
import gov.cdc.vocab.service.bean.CodeSystem;
import gov.cdc.vocab.service.bean.ValueSet;
import gov.cdc.vocab.service.bean.ValueSetConcept;
import gov.cdc.vocab.service.bean.ValueSetVersion;
import gov.cdc.vocab.service.dto.input.ValueSetVersionSearchCriteriaDto;
import gov.cdc.vocab.service.dto.output.ValueSetConceptResultDto;
import gov.cdc.vocab.service.dto.output.ValueSetVersionResultDto;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.*;

import javax.servlet.http.HttpServletRequest;



import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;

/**
 * Sample class to demonstrate 1) finding a Value Set and 2) finding the
 * Concepts for the Value Set. Methods are based on the use case associated with
 * the NBS DMB.
 *
 */
public class ValueSetVersionUtil {

	/**
	 * log4j Logger.
	 */
	static final LogUtils log = new LogUtils(ValueSetVersionUtil.class.getName());
	/**
	 * URL of PHIN VADS.
	 *
	 */
	final String SERVICEURL = PropertyUtil.getInstance().getServiceUrlPHINVADSWebService();

	/**
	 * VocabService.
	 */
	private VocabService service;

	/**
	 * Properties of ValueSet.
	 */
	private String vsCode;
	private String vsOID;
	private String vsDefinitionText;
	private String vsName;
	private String vsStatus;
	private String vsScopeNoteText;
	private String vsAssigningAuthorityGUID;
	private Date vsLastRevisionDate;
	private int vsVersion;
	private String vsVersionGUID;
	private Date vsEffectiveDate;

	/**
	 * Properties of a Concept.
	 */
	String cCode;
	String cName;
	String cOID;
	String cPreferredName;
	String cDefinitionText;
	String cStatus;
	Date cStatusDateTime;

	/**
	 * Properties of a Code System.
	 */
	String csCodeSystem;
	String csCodeSystemName;
	String csHL7Table;
	String csCodeOID;
	String csURL;

	/**
	 * Default Constructor. Provides VADS service connection.
	 */
	public ValueSetVersionUtil() {
		service = this.getVADServiceConnection();
	}

	/***************************************************************************
	 * Test Harness.
	 *
	 * @param args
	 */
	public static PhinVadsSystemVO findPhinVads(String[] args, HttpServletRequest request, String isMoreAllowed) {
	//public static void main(String[] args) {
		ValueSetVersionUtil application = new ValueSetVersionUtil();
		PhinVadsSystemVO phinVadsVo = new PhinVadsSystemVO();
		String vsVersionGUID = application
				.findValueSet(args[0], phinVadsVo);

		if (vsVersionGUID != null) {
			phinVadsVo = application.findConcepts(vsVersionGUID, phinVadsVo, request, isMoreAllowed);
		} else {
			log.debug("No Value Set found!");
			//System.out.println("No Value Set found!");
			phinVadsVo = null;
		}   
		return phinVadsVo;
	}

	/**
	 * Added this code to Disable Certificate Validation
	 */
	
	/*TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
		}
	} };*/
	
	/**
	 * <p>
	 * Returns a instance of a connection to the PHIN VADS Web Service
	 * </p>
	 *
	 * @return VocabService or null if a connection could not be established
	 */

	public VocabService getVADServiceConnection() {
		VocabService service = null;
		HessianProxyFactory factory = new HessianProxyFactory();
		try {

			/*SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());*/
			service = (VocabService) factory.create(VocabService.class, SERVICEURL);
		} catch (MalformedURLException e) {
			log.error("Exception in getVADServiceConnection(): ERROR = " + e);
			throw new NEDSSSystemException("The VADS web service is down:" + e.toString());
		} catch (HessianRuntimeException hre) {
			log.error("Exception in getVADServiceConnection(): ERROR = " + hre);
			throw new HessianRuntimeException("The VADS web service is down:" + hre.toString());
		} /*catch (NoSuchAlgorithmException hre) {
			log.error("Exception in getVADServiceConnection(): ERROR = " + hre);
			throw new HessianRuntimeException("The VADS web service is down:" + hre.toString());
		} catch (KeyManagementException hre) {
			log.error("Exception in getVADServiceConnection(): ERROR = " + hre);
			throw new HessianRuntimeException("The VADS web service is down:" + hre.toString());
		}*/
		return service;
	}

	/**
	 * Finds the most recent version of the specified ValueSet name using an
	 * Exact search of all Views and Groups.
	 *
	 * @param searchtext,
	 *            name of ValueSet
	 * @return String, representing Value Set Version GUID
	 */
	public String findValueSet(String searchtext, PhinVadsSystemVO phinVadsVo) {// Search for ValueSet
		ValueSetVersionSearchCriteriaDto vsSearchCrit = new ValueSetVersionSearchCriteriaDto();
		vsSearchCrit.setFilterByViews(false);
		vsSearchCrit.setFilterByGroups(false);
		vsSearchCrit.setCodeSearch(true);
		vsSearchCrit.setNameSearch(false);
		vsSearchCrit.setOidSearch(false);
		vsSearchCrit.setDefinitionSearch(false);
		vsSearchCrit.setSearchText(searchtext);
		// Exact search
		vsSearchCrit.setSearchType(1);
		// Most recent version
		vsSearchCrit.setVersionOption(3);

		// Execute the search
		ValueSetVersionResultDto theVSSearchResult = service
				.findValueSetVersions(vsSearchCrit, 1, 5);

		// Display the results of the search (e.g., was something returned)
		log.debug("Total Results from Search is: "
				+ theVSSearchResult.getTotalResults());

		// Move forward if the search returned 1 and only 1
		if (theVSSearchResult.getTotalResults() == 1) {

			// Extract the ValueSetVersion from the results
			ValueSetVersion theValueSetVersion = theVSSearchResult
					.getValueSetVersion();

			// Get the ValueSet Result using the ValueSetOid from the
			// ValueSetVersion
			ValueSet theValueSet = service.getValueSetByOid(
					theValueSetVersion.getValueSetOid()).getValueSet();
			CodeSetDT codeSetDT = new CodeSetDT();
			vsCode = theValueSet.getCode();
			vsOID = theValueSet.getOid();
			vsDefinitionText = theValueSet.getDefinitionText();
			vsName = theValueSet.getName();
			vsStatus = theValueSet.getStatus();
			vsScopeNoteText = theValueSet.getScopeNoteText();
			vsAssigningAuthorityGUID = theValueSet.getAssigningAuthorityId();
			codeSetDT.setCodeSetNm(vsCode);
			codeSetDT.setAssigningAuthorityCd(vsAssigningAuthorityGUID);
			codeSetDT.setCodeSetDescTxt(vsDefinitionText);
			codeSetDT.setValueSetOid(vsOID);
			codeSetDT.setValueSetNm(vsName);
			codeSetDT.setValueSetCode(vsCode);
			codeSetDT.setValueSetStatusCd(vsStatus);
			//codeSetDT.setSourceVersionTxt(vsName);
			
			CodeSetGpMetaDataDT codeSetGpMetaDataDT = new CodeSetGpMetaDataDT();
			codeSetGpMetaDataDT.setCodeSetNm(vsCode);
			codeSetGpMetaDataDT.setVads_value_set_code(vsCode);
			codeSetGpMetaDataDT.setCodeSetDescTxt(vsDefinitionText);
			codeSetGpMetaDataDT.setCodeSetShortDescTxt(vsName);
			
			phinVadsVo.setCodeSetDT(codeSetDT);
			phinVadsVo.setCodeSetGpMetaDataDT(codeSetGpMetaDataDT);
			
			vsLastRevisionDate = theValueSet.getValueSetLastRevisionDate();
			// Get the Version Number from the ValueSetVersion
			vsVersion = theValueSetVersion.getVersionNumber();
			// Get the Value Set Version GUID
			vsVersionGUID = theValueSetVersion.getId();
			vsEffectiveDate = theValueSetVersion.getEffectiveDate();
			
			codeSetDT.setSourceVersionTxt(String.valueOf(vsVersion));
			
			log.debug("VS Version Number is: " + vsVersion);
			log.debug("VS Version Number GUID is: " + vsVersionGUID);
			log.debug("VS Code: " + vsCode);
			log.debug("VS OID is: " + vsOID);
			log.debug("VS Definition Text: " + vsDefinitionText);
			log.debug("VS Name: " + vsName);
			log.debug("VS Status: " + vsStatus);
			log.debug("VS Scope Note Text: " + vsScopeNoteText);
			log.debug("VS Assigning Authority GUID: "
					+ vsAssigningAuthorityGUID);
			log.debug("VS Last Revision Date: " + vsLastRevisionDate);
			log.debug("VS Effective Date: " + vsEffectiveDate);

		}
		return vsVersionGUID;
	}
	
	
	/***************************************************************************
	 * Finds concepts based on specified Value Set Version GUID.
	 *
	 * @param vsVersionGUID,
	 *            Value Set Version GUID
	 */
	public PhinVadsSystemVO findConcepts(String vsVersionGUID, PhinVadsSystemVO phinVadsVo, HttpServletRequest request, String isMoreAllowed) {
		// Need to loop through the page
		// While the first page will usually have the results, larger results
		// sets will span pages
		boolean flag = true;
		int page = 1;
		ArrayList<Object> newCodeValueGeneralDTCollection  = new ArrayList<Object> ();

		int code = 1;
		while (flag) {

			// Get the Concepts associated to the Value Set using the GUID of
			// the Value Set Version
			ValueSetConceptResultDto theValueSetConceptDto = service
					.getValueSetConceptsByValueSetVersionId(vsVersionGUID,
							page, 500);

			//Check the size of the concepts contained in the Value Set
			//This will return the total size regardless of the pages
			log.debug("Total Concepts returned is: " + theValueSetConceptDto.getTotalResults());
			if(isMoreAllowed != null && isMoreAllowed.equals("false")){
				if (theValueSetConceptDto.getTotalResults() > PropertyUtil.getInstance().getPhinVadsMaxCount()){
					log.debug("Warning: Over" +PropertyUtil.getInstance().getPhinVadsMaxCount()+ " concepts returned!");
					request.setAttribute("ConceptCount", theValueSetConceptDto.getTotalResults());
					break;
				}
			}

			//Get the concepts from the ValueSetConceptResultDto
			List<ValueSetConcept> theValueSetConcepts = theValueSetConceptDto.getValueSetConcepts();

			
			

			log.debug("Page: " + page);

			if (theValueSetConcepts != null && !theValueSetConcepts.isEmpty()) {

				// Get the individual concepts
				for (ValueSetConcept concept : theValueSetConcepts) {

					CodeValueGeneralDT codeValueGeneralDT = new CodeValueGeneralDT();
					
					

					cCode = concept.getConceptCode();
					cName = concept.getCodeSystemConceptName();
					cOID = concept.getCodeSystemOid();

					cPreferredName = concept.getCdcPreferredDesignation();
					cDefinitionText = concept.getDefinitionText();
					cStatus = concept.getStatus();
					cStatusDateTime = concept.getStatusDate();
					
					codeValueGeneralDT.setCodeSetNm(phinVadsVo.getCodeSetDT().getCodeSetNm());
					if(cCode !=null && cCode.length()>20){
						codeValueGeneralDT.setCode(Integer.toString(code));
						code = code+1;
					}
					else 
						codeValueGeneralDT.setCode(cCode);
					codeValueGeneralDT.setCodeDescTxt(cName);
					// truncate the length of cPreferredName to 100 before inserting into codeShortDescTxt
					String cPreferredNameSub = null;
					if(cPreferredName!= null && cPreferredName.length()>100){
						cPreferredNameSub = cPreferredName.substring(0, 99);
						codeValueGeneralDT.setCodeShortDescTxt(cPreferredNameSub);
					}else
						codeValueGeneralDT.setCodeShortDescTxt(cPreferredName);
					codeValueGeneralDT.setCodeSystemCd(cOID);
					//codeValueGeneralDT.setCodeSystemDescTxt(codeSystemDescTxt)
					
					codeValueGeneralDT.setEffectiveFromTime(cStatusDateTime);
					codeValueGeneralDT.setConceptCode(cCode);
					codeValueGeneralDT.setConceptNm(cName);
					codeValueGeneralDT.setConceptPreferredNm(cPreferredName);
					codeValueGeneralDT.setConceptStatusCd(cStatus);
					Timestamp aupdateTime = new Timestamp(cStatusDateTime.getTime());
					codeValueGeneralDT.setConceptStatusTime(aupdateTime);
					//codeValueGeneralDT.setCodeSystemVersionNbr(codeSystemVersionNbr)
					codeValueGeneralDT.setAdminComments(cDefinitionText);

					newCodeValueGeneralDTCollection.add(codeValueGeneralDT);

					log.debug("Concept Code: " + cCode);
					log.debug("Concept Name: " + cName);
					log.debug("Concept OID" + cOID);
					log.debug("Concept Preferred Name: " + cPreferredName);
					log.debug("Concept Definition Text: " + cDefinitionText);
					log.debug("Concept Status: " + cStatus);
					log.debug("Concept Status Date: " + cStatusDateTime);
					log.debug("");
					System.out.println("Concept Code: " + cCode);
					System.out.println("Concept Name: " + cName);
					System.out.println("Concept OID" + cOID);
					System.out.println("Concept Preferred Name: " + cPreferredName);
					System.out.println("Concept Definition Text: " + cDefinitionText);
					System.out.println("Concept Status: " + cStatus);
					System.out.println("Concept Status Date: " + cStatusDateTime);
					System.out.println("");

					// Get the Coding System information for the concept
					// Remember, the Coding Systems can be different amongst
					// concepts in a Value Set.
					CodeSystem cs = service.getCodeSystemByOid(
							concept.getCodeSystemOid()).getCodeSystem();

					csCodeSystem = cs.getCodeSystemCode();
					csCodeSystemName = cs.getName();
					csHL7Table = cs.getHl70396Identifier();
					csCodeOID = cs.getOid();
					csURL = cs.getSourceUrl();
					
					codeValueGeneralDT.setCodeSystemDescTxt(csCodeSystemName);
					codeValueGeneralDT.setCodeSystemCd(csCodeOID);

					log.debug("Code System Code: " + csCodeSystem);
					log.debug("Code System Name: " + csCodeSystemName);
					log.debug("Code System HL7 Table: " + csHL7Table);
					log.debug("Code System OID: " + csCodeOID);
					log.debug("Code System URL: " + csURL);
					log.debug("");


				}
				// Increment the page
				page++;
				
				phinVadsVo.setTheCodeValueGenaralDtCollection(newCodeValueGeneralDTCollection);

			}
			// Break out of looping through pages
			else {
				flag = false;
			}
		}
		return phinVadsVo;

	}
}