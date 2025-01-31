package gov.cdc.nedss.nnd.util;


import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.PartnerServicesDAOV3;
import gov.cdc.nedss.nnd.helper.PSFIndexDT;
import gov.cdc.nedss.nnd.helper.PSFPartnerDT;
import gov.cdc.nedss.nnd.helper.PSFRiskDT;
import gov.cdc.nedss.nnd.helper.PSFSessionDT;
import gov.cdc.nedss.nnd.helper.PartnerServicesHelper;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;


/**
 * PartnerServicesUtil - Calls helper to build the document and then returns byte array of document for output by web tier.
 *  See PSData_v3.xsd.
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * August 8th, 2018
 * @version 1.0
 */

public class PartnerServicesUtil {

	static final LogUtils logger = new LogUtils(PartnerServicesUtil.class.getName());
	
	public byte[] processPartnerServicesFileRequest(String reportingMonth, String reportingYear, String contactPerson, String invFormCd, String ixsFormCd, String defaultStateCd, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		
		logger.debug("in PartnerServcesUtil.processPartnerServicesFileRequest()");

		XpemsPSDataDocument xpemsdoc = null;
		
		//Unusual logic around month and year
		//get the current year
		String currentYearStr = new SimpleDateFormat("yyyy").format(new Date());
		int curYear = Integer.parseInt(currentYearStr);
		int rptYear = Integer.parseInt(reportingYear);
		boolean priorYr = false;
		boolean presentYr = false;
		boolean futureYr = false;
		int reportYearToUse = rptYear;
		if (rptYear == curYear)
			presentYr = true;
		else if (rptYear < curYear)
			priorYr = true;
		else if (rptYear > curYear)
			futureYr = true;
		
		if (presentYr && reportingMonth.equals("3")) //march
			reportYearToUse = --reportYearToUse;
		else if (futureYr && reportingMonth.equals("3")) //march
			reportYearToUse = --reportYearToUse;
		else if (priorYr && reportingMonth.equals("3")) //march)
			reportYearToUse = --reportYearToUse;		
		
		String rptYearStr = String.valueOf(reportYearToUse);
		//reporting month/year indicate the date ranges
		String startingMonth = "";
		String endingMonth = "";
		String endingDay = "";
		if (reportingMonth.equals("3"))  {//march
			startingMonth = "07";
			endingMonth = "12";
			endingDay = "31";
		} else if (reportingMonth.equals("9")) { //sept
			startingMonth = "01";
			endingMonth = "06";
			endingDay = "30";
		}
		String startingTime = " 00:00:00";
		String endingTime = " 23:59:59";
		String startingDateStr = rptYearStr + "-" + startingMonth + "-" + "01" + startingTime;
		String endingDateStr = rptYearStr + "-" + endingMonth + "-" + endingDay + endingTime;
		String startingDateRange = startingMonth + "/01/" + rptYearStr;
		String endingDateRange = endingMonth + "/" + endingDay + "/" + rptYearStr;
		logger.info("Partner Services: Starting and ending date range are: " + startingDateRange + " " +endingDateRange);
		
		logger.info("Partner Services: Retrieving indexes");
		
		
		/*BEGIN: Read the data from the flat PSF tables in the NBS_MSGOUTE database and store it in objects to be able to generate the final XML*/
		
		logger.info("The process that reads the data from the flat PSF tables and store it in objects to generate the XML starts.");
		
//-------------------CLIENT DATA--------------------------
		//Step 1: Get Index objects within the date range (we need the localClientId and the caseNumberPS)
		
		logger.info("Reading Client Data");
		ArrayList<Object> indexColl = retrieveAndGatherIndexData(startingDateStr, endingDateStr, nbsSecurityObj);
		if (indexColl.isEmpty()) {
			throw new NEDSSAppException("No cases found to process between "+startingDateStr+" and "+endingDateStr, "ERR122");
		}
		
		//Step 2: Get localClientId from PSF_PARTNER using the caseNumberPS from PSF_INDEX
		String listOfCaseNumberPS = createListOfCaseNumberPSFromIndex(indexColl);//List of CaseNumberPS
		logger.info("List of CaseNumberPS from PSF_INDEX is: "+listOfCaseNumberPS);
		
		ArrayList<Object> localClientIdPartnerList = new ArrayList<Object>();
		
		if(listOfCaseNumberPS!=null && !listOfCaseNumberPS.isEmpty())	
			localClientIdPartnerList = getLocalClientIdFromPartner(listOfCaseNumberPS,nbsSecurityObj);
		logger.info("List of PSF_Partner by previous list has been created");
		//Step 3: Get localClientId from PSF_PARTNER OOJ
		ArrayList<Object> localClientIdPartnerListOOJ = getLocalClientIdFromPartnerOOJ(startingDateStr, endingDateStr, nbsSecurityObj);
		logger.info("List of PSF_Partner OOJ by previous list has been created");
		
		//Step 4: De-duplicate the three lists of localClientId (from psf_index, from psf_partner and from psf_partner OOJ)
		
		ArrayList<String> finalListLocalClientId = new ArrayList<String>();
		
		for (int i=0; i<indexColl.size(); i++){
			
			PSFIndexDT indexDT = (PSFIndexDT)indexColl.get(i);
			
			if(!finalListLocalClientId.contains(indexDT.getLocalClientId()))		
				finalListLocalClientId.add(indexDT.getLocalClientId());
			
		}
		
		for (int i=0; i<localClientIdPartnerList.size(); i++){
			
			PSFPartnerDT partnerDT = (PSFPartnerDT)localClientIdPartnerList.get(i);
			String localClientId = partnerDT.getLocalClientId();
			
			if(!finalListLocalClientId.contains(localClientId))
				finalListLocalClientId.add(localClientId);
			
		}
		
		for (int i=0; i<localClientIdPartnerListOOJ.size(); i++){
			
			PSFPartnerDT partnerDT = (PSFPartnerDT)localClientIdPartnerListOOJ.get(i);
			String localClientId = partnerDT.getLocalClientId();
			
			if(!finalListLocalClientId.contains(localClientId))
				finalListLocalClientId.add(localClientId);
		}
		
		//Step 5: get the list of PSFClientDT by the final list of localClientId. This final master list will be used by other processes below.
		
		String listOfLocalClientIdPSByComma = createListOfLocalClientIdFromArray(finalListLocalClientId);
		
		logger.info("List of LocalClientId after deduplicating the lists from index, partner and partner OOJ: "+listOfLocalClientIdPSByComma);
		ArrayList<Object> clientColl=new ArrayList<Object>();
		

		
		if(listOfLocalClientIdPSByComma!=null && !listOfLocalClientIdPSByComma.isEmpty())	
			clientColl.addAll(retrieveAndGatherClientData(listOfLocalClientIdPSByComma, nbsSecurityObj));
		
		
//-------------------RISK DATA--------------------------
		logger.info("Reading Risk Data");
		//Step 1: the final master list created before is used to get the risks where local client id is in that list.
		ArrayList<Object> riskColl = new ArrayList<Object> ();
		if(listOfLocalClientIdPSByComma!=null && !listOfLocalClientIdPSByComma.isEmpty())	
			riskColl = retrieveAndGatherRiskData(listOfLocalClientIdPSByComma, nbsSecurityObj);
		
		//Step2: Create a Map with key the localClientId and the value is an arrayList of risk objects.
		//This is useful in order to easily get the risk profile by client
		HashMap<String, ArrayList<Object>> riskMap = new HashMap<String, ArrayList<Object>>();
		populateRiskMap(riskMap,riskColl);
		
//-------------------ASANINDEX DATA--------------------------	
		
		logger.info("Reading AsAnIndex Data");
		//Step 1: get the list of Indexes where localClientId is in the final master client list
		ArrayList<Object> asAnIndexColl = new ArrayList<Object>();
		if(listOfLocalClientIdPSByComma!=null && !listOfLocalClientIdPSByComma.isEmpty())
			asAnIndexColl = retrieveAndGatherAsAnIndexData(listOfLocalClientIdPSByComma, startingDateStr, endingDateStr, nbsSecurityObj);
		
		//Step 2: create a Map with key the localClientId and the value is an arrayList of index objects. This will help to
		//easily get the index associated to the client while generating the XML
		HashMap<String, ArrayList<Object>> indexMap = new HashMap<String, ArrayList<Object>>();
		populateAsAnIndexMap(indexMap,asAnIndexColl);
		
		
//-------------------ASAPARTNER DATA--------------------------
		logger.info("Reading AsAPartner Data");
		//Step 1: get the list of partners where the localClientId is in the final master client list
		ArrayList<Object> asAPartnerColl = new ArrayList<Object> ();
		if(listOfLocalClientIdPSByComma!=null && !listOfLocalClientIdPSByComma.isEmpty())
			asAPartnerColl = retrieveAndGatherAsAPartnerDataByLocalClientIdList(listOfLocalClientIdPSByComma, nbsSecurityObj);
		
		//Step 2: create a Map with key the localClientId and the value is an arrayList of partner objects. This will help to
		//easily get the partner associated to the client while generating the XML
		
		HashMap<String, ArrayList<Object>> partnerMap = new HashMap<String, ArrayList<Object>>();
		populateAsAPartnerMap(partnerMap,asAPartnerColl);
		
//-------------------ASANINDEX SESSION DATA--------------------------
		
		logger.info("Reading AsAnIndex session Data");
		//Step 1: First create a list with the combination of PSF_INDEX.localClientId and PSF_INDEX.CaseNumberPS to be used in the where clause in the PSF_SESSION table.
		
		String listOfPairsLocalClienIdAndCaseNumberPS = createListOfPairsLocalClientIdAndCaseNumberPSFromIndex(indexColl);//List of localClientId and CaseNumberPS

		//Step 2: get the sessions that matches the localClientId and CaseNumberPS
		ArrayList<Object> asAnIndexSessionColl = new ArrayList<Object>();
		if(listOfPairsLocalClienIdAndCaseNumberPS!=null && !listOfPairsLocalClienIdAndCaseNumberPS.isEmpty())
			asAnIndexSessionColl = retrieveAndGatherAsAnIndexPartnerSessionData(listOfPairsLocalClienIdAndCaseNumberPS, nbsSecurityObj);
		
		//Step 3: create a Map with key the localClientId_caseNumberPS and the value is an arrayList of session objects. This will help to
		//easily get the asAnIndex sessions associated to the client while generating the XML
				
		HashMap<String, ArrayList<Object>> indexSessionMap = new HashMap<String, ArrayList<Object>>();
		populateAsAnIndexSessionMap(indexSessionMap,asAnIndexSessionColl);
		
//-------------------ASAPARTNER SESSION DATA--------------------------
		
		logger.info("Reading AsAPartner session Data");
		//Step 1: using the existing listOfCaseNumberPS, get the list of PSFPartnerDTs that are in that list
		
		ArrayList<Object> asAPartnerSessColl = retrieveAndGatherAsAPartnerDataByCaseNumberPSList(listOfCaseNumberPS, nbsSecurityObj);
		
		//Step 2: create a list with the pairs localClientId and CaseNumberPS from PSF_PARTNER which will be used in the where clause to get the as a partner sessions
		String listOfPairsLocalClienIdAndCaseNumberPSPartner = createListOfPairsLocalClientIdAndCaseNumberPSFromPartner(asAPartnerSessColl);//List of localClientId and CaseNumberPS
		//Step 3: using the previous list, get the PSFSessionDT that matches the previous list.
		ArrayList<Object> asAPartnerSessionColl = new ArrayList<Object>();
		
		if(listOfPairsLocalClienIdAndCaseNumberPSPartner!=null && !listOfPairsLocalClienIdAndCaseNumberPSPartner.isEmpty())
			asAPartnerSessionColl = retrieveAndGatherAsAnIndexPartnerSessionData(listOfPairsLocalClienIdAndCaseNumberPSPartner, nbsSecurityObj);
		
		//Step 4: using the existing localClientIdPartnerListOOJ, get the localClientId list from PSF_PARTNER
		String listOfLocalClientIdPSByCommaOOJ = createListOfLocalClientIdFromPartner(localClientIdPartnerListOOJ);
		//Step 5: using the previous list, get the PSFSessionDT object which localClientId is in that list and caseNumberPS is either Blank or NULL
		ArrayList<Object> asAPartnerSessionCollOOJ = new ArrayList<Object>();
		logger.info("The listOfLocalClientIdPSByCommaOOJ is:"+listOfLocalClientIdPSByCommaOOJ+".");
		if(listOfLocalClientIdPSByCommaOOJ!=null && !listOfLocalClientIdPSByCommaOOJ.trim().isEmpty() && !listOfLocalClientIdPSByCommaOOJ.trim().equalsIgnoreCase(""))	
			asAPartnerSessionCollOOJ =  retrieveAndGatherAsAnIndexPartnerSessionDataOOJ(listOfLocalClientIdPSByCommaOOJ,nbsSecurityObj);
		
		logger.info("Creating hashMaps");
		
		//Step 5: create a hashMap where the key is localClientId_CaseNumberPS and the value is the list of SessionDT from non OOJ partners and OOJ partners.
		//In case is an OOJ Partner, the caseNumberPS is NULL so we will append _NULL
		
		HashMap<String, ArrayList<Object>> partnerSessionMap = new HashMap<String, ArrayList<Object>>();
		populateAsAnIndexSessionMap(partnerSessionMap,asAPartnerSessionColl);
		populateAsAnIndexSessionMap(partnerSessionMap,asAPartnerSessionCollOOJ);
		
		
//-------------------SITES DATA--------------------------
		
		logger.info("Reading Sites Data");
		
		//Step 1: get the list of PSFSessionDT objects where the localClientId is in the final master client list
		//This list will be de-duplicated based on siteId and siteTypeValueCode before creating the XML
		ArrayList<Object> sessionColl = retrieveAndGatherSessionDataByLocalClientIdList(listOfLocalClientIdPSByComma, nbsSecurityObj);		
		
		logger.info("The process that reads the data from the flat PSF tables and store it in objects to generate the XML ends.");
		
		/*END: Read the necessary data from the database and store it in objects to be able to generate the final XML*/

		
	
		logger.info("Partner Services: Calling PartnerSericesHelper to create document");
		//create a new document
		xpemsdoc = XpemsPSDataDocument.Factory.newInstance();
		try {
			PartnerServicesHelper.buildPartnerServicesDocument( 
					sessionColl,
					indexMap,
					indexSessionMap,
					partnerMap,
					partnerSessionMap,
					clientColl,
					riskMap,
					null,
					xpemsdoc,
					startingDateRange,
					endingDateRange,
					contactPerson,
					invFormCd,
					ixsFormCd,
					defaultStateCd,
					nbsSecurityObj);
		} catch (RemoteException e1) {
				logger.error("Remote Exception in processPartnerServicesFileRequest " + e1);
				e1.printStackTrace();
		} catch (NEDSSAppException e1) {
			logger.error("NEDSS APP Exception in processPartnerServicesFileRequest " + e1);
			e1.printStackTrace();
			throw new NEDSSAppException("Partner Services File Request Processing Exception " +e1.getMessage());
		} catch (Exception e1) {
			logger.error("Exception in processPartnerServicesFileRequest " + e1);
			e1.printStackTrace();
			throw new NEDSSAppException("Partner Services File Request Exception Occurred " +e1.getMessage());
		}	
		
		//return a byte stream for the download
		 XmlOptions opts = new XmlOptions();
		 opts.setSavePrettyPrint();
		 opts.setSavePrettyPrintIndent(4);
		 opts.setCharacterEncoding("UTF-8");
		 //Luther wants the schema location in the header
		 XmlCursor cursor = xpemsdoc.newCursor();
		 if (cursor.toFirstChild())
		 {
		   cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance","schemaLocation"), "http://help.lutherconsulting.com/schemas/PSData_v3.xsd");
		 }
		// cursor.insertNamespace("nedss", "http://www.cdc.gov/NEDSS");
		 logger.info("PartnerServices: Opening output stream for XML document");
		 ByteArrayOutputStream baOut = new ByteArrayOutputStream();
		try {
			//psXmlBytes = xpemsdoc.xmlText(opts).getBytes("UTF-8");
			xpemsdoc.save(baOut, opts);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException for UTF-8?");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exception on xpemsdoc.save(baOut, opts);");
			e.printStackTrace();
		} 
	
		return baOut.toByteArray();  
	}

	
	/**
	 * createListOfCaseNumberPS: returns a list of all the caseNumberPS between single quotes and separated by ,
	 * */
	 public String createListOfCaseNumberPSFromIndex(ArrayList<Object> indexColl){
		 
		 String caseNumberPS = "";
		 
		 for (int i=0; i<indexColl.size(); i++){
			 PSFIndexDT indexDT = (PSFIndexDT)indexColl.get(i);
			 caseNumberPS += "'"+indexDT.getCaseNumberPS()+"',";
		 }
		 
		 if(caseNumberPS.length()!=0)
			 caseNumberPS=caseNumberPS.substring(0,caseNumberPS.length()-1); 
			 
		 logger.info("PartnerServicesUtil.createListOfCaseNumberPSFromIndex: the list of caseNumberPS is: "+caseNumberPS+".");

		 return caseNumberPS;
	 }
	 
	 
	 /**
	  * createListOfCaseNumberPS: returns a list of caseNumberPS between single quotes and separated by ,
	  * @param localIdList
	  * @return
	  */
	public  String createListOfLocalClientIdFromArray(ArrayList<String> localIdList){
			 
			 String localClientId = "";
			 
			 for (int i=0; i<localIdList.size(); i++){
				 
				 String localCId = (String)localIdList.get(i);
				 localClientId += "'"+localCId+"',";

			 }
			 
			 if(localClientId.length()!=0)
				 localClientId=localClientId.substring(0,localClientId.length()-1); 
				 
			 logger.info("PartnerServicesUtil.createListOfLocalClientIdFromArray: the list of localClientId is: "+localClientId+".");

			 return localClientId;
		 }
	
	/**
	 * createListOfLocalClientIDFromObjects: returns a list of localClientId from the Array of PSF_PARTNER objects
	 * @param partnerList
	 * @return
	 */
	public  String createListOfLocalClientIdFromPartner(ArrayList<Object> partnerList){
			 
			 String localClientId = "";
			 
			 for (int i=0; i<partnerList.size(); i++){
				 PSFPartnerDT partnerDT = (PSFPartnerDT)partnerList.get(i);
				 localClientId += "'"+partnerDT.getLocalClientId()+"',";
			 }
			 
			 if(localClientId.length()!=0)
				 localClientId=localClientId.substring(0,localClientId.length()-1); 
			
			 logger.info("PartnerServicesUtil.createListOfLocalClientIdFromPartner: the list of localClientId is: "+localClientId+".");

			 return localClientId;
		 }
	
	
	 /**
	  * createListOfPairsLocalClienIdAndCaseNumberPS: returns a string of pairs LocalClientId - CaseNumberPS in order to get
	  * the information from PSF_SESSIONS based on localClientId and CaseNumberPS
	  * @param indexColl
	  * @return
	  */
	 public String createListOfPairsLocalClientIdAndCaseNumberPSFromIndex(ArrayList<Object> indexColl){
		 
		 String listPairs = "";
		 
		 String localClientId="";
		 String caseNumberPS = "";
		 
		 for (int i=0; i<indexColl.size(); i++){
			 PSFIndexDT indexDT = (PSFIndexDT)indexColl.get(i);

			 localClientId = "(localClientId = '"+indexDT.getLocalClientId()+"'";
			 caseNumberPS = "and caseNumberPS = '"+indexDT.getCaseNumberPS()+"') OR";
			 listPairs += localClientId + caseNumberPS;
		 } 
		 
		 if(listPairs.length()!=0){
			 listPairs=listPairs.substring(0,listPairs.length()-2); 
			 listPairs="("+listPairs+")";
		 }
			 
		 logger.info("PartnerServicesUtil.createListOfPairsLocalClientIdAndCaseNumberPSFromIndex: the list of pairs localClientId and caseNumberPS is: "+listPairs+".");

		 return listPairs;
	 }
	 
	 /**
	  * createListOfPairsLocalClientIdAndCaseNumberPSPartner: creates a string with the pairs LocalClientId - CaseNumberPS in order to get
	  * the information from PSF_PARTNER based on localClientId and CaseNumberPS
	  * @param partnerColl
	  * @return
	  */
	 public String createListOfPairsLocalClientIdAndCaseNumberPSFromPartner(ArrayList<Object> partnerColl){
		 
		 String listPairs = "";
		 
		 String localClientId="";
		 String caseNumberPS = "";
		 
		 for (int i=0; i<partnerColl.size(); i++){
			 PSFPartnerDT partnerDT = (PSFPartnerDT)partnerColl.get(i);

			 localClientId = "(localClientId = '"+partnerDT.getLocalClientId()+"'";
			 caseNumberPS = "and caseNumberPS = '"+partnerDT.getCaseNumberPS()+"') OR";
			 listPairs += localClientId + caseNumberPS;
		 } 
		 
		 if(listPairs.length()!=0){
			 listPairs=listPairs.substring(0,listPairs.length()-2); 
			 listPairs="("+listPairs+")";
		 }
			
		 logger.info("PartnerServicesUtil.createListOfPairsLocalClientIdAndCaseNumberPSFromPartner: the list of pairs localClientId and caseNumberPS is: "+listPairs+".");

		 return listPairs;
	 }
	 
	 
		 
	/**
	 * populateRiskMap: populates the risk map with patient local id and the psfRiskDT. This will help to locate the risk while creating
	 * the client information in the XML.
	 * @param riskMap
	 * @param riskColl
	 * @return
	 */
	
	private void populateRiskMap(HashMap<String, ArrayList<Object>> riskMap,ArrayList<Object> riskColl){
		
		for(int i=0; i<riskColl.size(); i++){
			
			PSFRiskDT riskDT = (PSFRiskDT)riskColl.get(i);
			String localId = riskDT.getLocalClientId();
			if(riskMap.get(localId)!=null){
				ArrayList<Object> risks = riskMap.get(localId);
				risks.add(riskDT);
				
			}
			else{
				ArrayList<Object> risks = new ArrayList<Object>();
				risks.add(riskDT);
				riskMap.put(localId, risks);
			}
		}
		
		logger.info("PartnerServicesUtil.populateRiskMap: asAnIndexMap contains a total of: "+riskMap.size()+" keys.");


	}
	
	
	/**
	 * populateAsAnIndexMap: creates a Map which key is the localId and the value is the indexDT.
	 * @param asAnIndexMap
	 * @param asAnIndexColl
	 */
	private void populateAsAnIndexMap(HashMap<String, ArrayList<Object>> asAnIndexMap,ArrayList<Object> asAnIndexColl){
		
		for(int i=0; i<asAnIndexColl.size(); i++){
			
			PSFIndexDT indexDT = (PSFIndexDT)asAnIndexColl.get(i);
			String localId = indexDT.getLocalClientId();
			if(asAnIndexMap.get(localId)!=null){
				ArrayList<Object> asAnIndex = asAnIndexMap.get(localId);
				asAnIndex.add(indexDT);
				
			}
			else{
				ArrayList<Object> risks = new ArrayList<Object>();
				risks.add(indexDT);
				asAnIndexMap.put(localId, risks);
			}
		}
		

		logger.info("PartnerServicesUtil.populateAsAnIndexMap: asAnIndexMap contains a total of: "+asAnIndexMap.size()+" keys.");

	}
	
	/**
	 * populateAsAnIndexSessionMap: populates the map with the key localId_caseNumberPS and the value is the PSFSessionDT
	 * @param asAnIndexSessionMap
	 * @param asAnIndexColl
	 */
	
	private void populateAsAnIndexSessionMap(HashMap<String, ArrayList<Object>> asAnIndexSessionMap,ArrayList<Object> asAnIndexColl){
		
		for(int i=0; i<asAnIndexColl.size(); i++){
			
			PSFSessionDT sessionDT = (PSFSessionDT)asAnIndexColl.get(i);
			String localId = sessionDT.getLocalClientId();
			String caseNumberPS = sessionDT.getCaseNumberPS();
			
			if(caseNumberPS==null || caseNumberPS.equalsIgnoreCase(""))
				caseNumberPS=sessionDT.getInvLocalId();
			
			String keys = localId+"_"+caseNumberPS;
			
			if(asAnIndexSessionMap.get(keys)!=null){
				ArrayList<Object> asAnIndex = asAnIndexSessionMap.get(keys);
				asAnIndex.add(sessionDT);
				
			}
			else{
				ArrayList<Object> index = new ArrayList<Object>();
				index.add(sessionDT);
				asAnIndexSessionMap.put(keys, index);
			}
		}
		
		logger.info("PartnerServicesUtil.populateAsAnIndexSessionMap: asAnIndexSessionMap contains a total of: "+asAnIndexSessionMap.size()+" keys.");


	}
	
	/**
	 * populateAsAPartnerMap: populates the asAPartnerMap map with key the localClienId and value the PSFPartnerDT object
	 * @param asAPartnerMap
	 * @param asAPartnerColl
	 */
	private void populateAsAPartnerMap(HashMap<String, ArrayList<Object>> asAPartnerMap,ArrayList<Object> asAPartnerColl){
		
		for(int i=0; i<asAPartnerColl.size(); i++){
			
			PSFPartnerDT partnerDT = (PSFPartnerDT)asAPartnerColl.get(i);
			String localId = partnerDT.getLocalClientId();
			if(asAPartnerMap.get(localId)!=null){
				ArrayList<Object> asAPartner = asAPartnerMap.get(localId);
				asAPartner.add(partnerDT);
				
			}
			else{
				ArrayList<Object> risks = new ArrayList<Object>();
				risks.add(partnerDT);
				asAPartnerMap.put(localId, risks);
			}
		}
		
		logger.info("PartnerServicesUtil.populateAsAPartnerMap: asAPartnerMap contains a total of: "+asAPartnerMap.size()+" keys.");

	}
	
	/**
	 * getLocalClientIdFromPartnerOOJ: returns a ArrayList of localClientId from PSF_PARTNER which are OOJ and are within the date range
	 * @param startingDateStr
	 * @param endingDateStr
	 * @param nbsSecurityObj
	 * @return
	 */
	
	private ArrayList<Object> getLocalClientIdFromPartnerOOJ(String startingDateStr, String endingDateStr, NBSSecurityObj nbsSecurityObj){
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getLocalClientIdFromPartnerOOJ(startingDateStr, endingDateStr);
		
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.getLocalClientIdFromPartnerOOJ: Retrieved "+partnerServicesColl.size() + " matching OOJ partners in time period.");
		logger.info("==================================================================");
		logger.info("==================================================================");
		
		return partnerServicesColl;
	}
	
	/**
	 * getLocalClientIdFromPartner: returns a list of localClientId from PSF_PARTNER table which caseNumberPS is in the listCaseNumberPS
	 * @param listCaseNumberPS
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object> getLocalClientIdFromPartner(String listCaseNumberPS, NBSSecurityObj nbsSecurityObj){
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getLocalClientIdFromPartner(listCaseNumberPS);
		
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.getLocalClientIdFromPartner: Retrieved "+partnerServicesColl.size() + " matching list of caseNumberPS.");
		logger.info("==================================================================");
		logger.info("==================================================================");
		
		return partnerServicesColl;
	}
	
	/**
	 * retrieveAndGatherIndexData: get the index objects which date is within that range
	 * @param startingDateStr
	 * @param endingDateStr
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object>  retrieveAndGatherIndexData(String startingDateStr,
			String endingDateStr, NBSSecurityObj nbsSecurityObj) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFIndexData(startingDateStr, endingDateStr);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherIndexData: Retrieved "+partnerServicesColl.size() + " matching indexes in time period.");
		logger.info("==================================================================");
		logger.info("==================================================================");
		
		return partnerServicesColl;
	}
	
	
	/**
	 * retrieveAndGatherClientData: gets a list of PSFClientDT which localClientIds are in the localClientIdList list
	 * @param localClientIdList
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object>  retrieveAndGatherClientData(String localClientIdList, NBSSecurityObj nbsSecurityObj) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFClientData(localClientIdList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherClientData: Retrieved "+partnerServicesColl.size() + " matching list of localClientId.");
		logger.info("==================================================================");
		logger.info("==================================================================");
		
		return partnerServicesColl;
	}
	
	/**
	 * retrieveAndGatherAsAnIndexData: returns PSFIndexDT objects which localClientId is in the localClientIdList list
	 * @param listClientLocalId
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object> retrieveAndGatherAsAnIndexData(String localClientIdList, String startingDateStr, String endingDateStr, NBSSecurityObj nbsSecurityObj) {
			
			PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
			ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFAsAnIndexData(localClientIdList, startingDateStr, endingDateStr);
			logger.info("==================================================================");
			logger.info("PartnerServicesUtil.retrieveAndGatherAsAnIndexData: Retrieved "+partnerServicesColl.size() + " matching list of localClientId.");
			logger.info("==================================================================");
			logger.info("==================================================================");
	
			return partnerServicesColl;
	}
	
	public void writeXMLLog(String actualDate, String successFailure, String errorMessage, String mode, String startDate, String endDate){
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		partnerServicesDAO.writeXMLLog(actualDate, successFailure, errorMessage, mode, startDate, endDate);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.writeXMLLogIndex: written in activity_log table");
		logger.info("==================================================================");
		logger.info("==================================================================");

	}
	
	
	
	public void writeXMLLogDetail(ArrayList<String> detailInformation){
		
		if(detailInformation!=null && detailInformation.size()==5){
			String recordType=detailInformation.get(0);
			String successFailure=detailInformation.get(1);
			String errorMessage=detailInformation.get(2); 
			String startDate=detailInformation.get(3);
			String endDate=detailInformation.get(4);
			

			PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
			partnerServicesDAO.writeXMLLogDetail(recordType, successFailure, errorMessage, startDate, endDate);
			logger.info("==================================================================");
			logger.info("PartnerServicesUtil.writeXMLLogDetail: written in activity_log_detail table");
			logger.info("==================================================================");
			logger.info("==================================================================");
		
		}

	}
	
	
	
	/**
	 * retrieveAndGatherAsAnIndexSessionData: returns the PSF_SESSION objects where the localClientId and the CaseNumberPS match the list
	 * @param localClientIdList
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object> retrieveAndGatherAsAnIndexPartnerSessionData(String localClientIdCaseNumberPSList, NBSSecurityObj nbsSecurityObj) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFSessionDataByLocalClientIdAndCaseNumberPS(localClientIdCaseNumberPSList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherAsAnIndexPartnerSessionData: Retrieved "+partnerServicesColl.size() + " matching list of pairs localClientId and caseNumberPS.");
		logger.info("==================================================================");
		logger.info("==================================================================");

		return partnerServicesColl;
}

	
	private ArrayList<Object> retrieveAndGatherSessionDataByLocalClientIdList(String localClientIdCaseNumberPSList, NBSSecurityObj nbsSecurityObj) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFSessionDataByLocalClientIdList(localClientIdCaseNumberPSList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherSessionDataByLocalClientIdList: Retrieved "+partnerServicesColl.size() + " matching list of pairs of localClientId and caseNumberPS.");
		logger.info("==================================================================");
		logger.info("==================================================================");

		return partnerServicesColl;
}
	
	
	
	private ArrayList<Object> retrieveAndGatherAsAnIndexPartnerSessionDataOOJ(String localClientIdCaseNumberPSList, NBSSecurityObj nbsSecurityObj) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFSessionDataByLocalClientIdOOJ(localClientIdCaseNumberPSList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherAsAnIndexPartnerSessionDataOOJ: Retrieved "+partnerServicesColl.size() + " matching list of pairs of localClientId and CaseNumberPS.");
		logger.info("==================================================================");
		logger.info("==================================================================");

		return partnerServicesColl;
}
	
	/**
	 * retrieveAndGatherAsAPartnerData: returns all the PSFPartnerDT object which localClientId is in the localClientIdList list
	 * @param listClientLocalId
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object> retrieveAndGatherAsAPartnerDataByLocalClientIdList(String localClientIdList, NBSSecurityObj nbsSecurityObj) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFAsAPartnerData(localClientIdList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherAsAPartnerDataByLocalClientIdList: Retrieved "+partnerServicesColl.size() + " matching list of localClientId.");
		logger.info("==================================================================");
		logger.info("==================================================================");

		return partnerServicesColl;
}

	/**
	 * getCollectionOfPublicHealthCaseDTFromPHCLocalIdList: calls the PartnerServicesDAOV3 to retrieve
	 * the collection of publicHealthCaseDT which local_id is in the localClientIdList parameter
	 * @param localClientIdList
	 * @return
	 */
	public Collection<Object> getCollectionOfPublicHealthCaseDTFromPHCLocalIdList(String localClientIdList) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		Collection<Object> partnerServicesColl = partnerServicesDAO.getPublicHealthCaseDTFromListOfPHCLocalId(localClientIdList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.getCollectionOfPublicHealthCaseDTFromPHCLocalIdList: Retrieved "+partnerServicesColl.size() + " matching list of localClientId.");
		logger.info("==================================================================");
		logger.info("==================================================================");

		return partnerServicesColl;
}
	
public ArrayList<Object> getCollectionOfActRelationShipFromPHCLocalId(String localClientIdList) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getActRelationshipDTFromListOfPHCLocalId(localClientIdList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherAsAPartnerDataByLocalClientIdList: Retrieved "+partnerServicesColl.size() + " matching list of localClientId.");
		logger.info("==================================================================");
		logger.info("==================================================================");

		return partnerServicesColl;
}
	
	

	
	
	
	private ArrayList<Object> retrieveAndGatherAsAPartnerDataByCaseNumberPSList(String localClientIdList, NBSSecurityObj nbsSecurityObj) {
			
			PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
			ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFAsAPartnerDataByCaseNumberPS(localClientIdList);
			logger.info("==================================================================");
			logger.info("PartnerServicesUtil.retrieveAndGatherAsAPartnerDataByCaseNumberPSList: Retrieved "+partnerServicesColl.size() + " matching list of localClientId.");
			logger.info("==================================================================");
			logger.info("==================================================================");
	
			return partnerServicesColl;
	}

	
	
	
	/**
	 * retrieveAndGatherRiskData: returns PSFRiskDT objects which localClientIds are within the listClientLocalIdList list
	 * @param listClientLocalId
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object> retrieveAndGatherRiskData(String localClientIdList, NBSSecurityObj nbsSecurityObj) {
		
		PartnerServicesDAOV3 partnerServicesDAO = new PartnerServicesDAOV3();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getPSFRiskData(localClientIdList);
		logger.info("==================================================================");
		logger.info("PartnerServicesUtil.retrieveAndGatherRiskData: Retrieved "+partnerServicesColl.size() + " matching list of localClientId.");
		logger.info("==================================================================");
		logger.info("==================================================================");
		
		return partnerServicesColl;
	}
	
}

