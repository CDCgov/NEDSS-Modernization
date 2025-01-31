package gov.cdc.nedss.nnd.util;


import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nnd.helper.PartnerServicesHelperOld;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxy;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.PartnerServicesDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.PartnerServicesLookupDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.rmi.RemoteException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ejb.CreateException;
import javax.xml.namespace.QName;

import noNamespace.XpemsPSDataDocument;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;


/**
 * PartnerServicesUtil - Calls helper to build the document and then returns byte array of document for output by web tier.
 *  See PSData_v2.1.xsd.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA for CDC</p>
 * Dec 22nd, 2016
 * @version 0.9
 */

public class PartnerServicesUtilOld {

	static final LogUtils logger = new LogUtils(PartnerServicesUtil.class.getName());
	
	public byte[] processPartnerServicesFileRequest(String reportingMonth, String reportingYear, String contactPerson, String invFormCd, String ixsFormCd, String defaultStateCd, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		
		logger.debug("in PartnerServcesUtil.processPartnerServicesFileRequest()");
		byte[] psXmlBytes = null;
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
		//get Cases within the time frame
		ArrayList<Object> partnerServicesColl = retrieveAndGatherPartnerServicesData(startingDateStr, endingDateStr, nbsSecurityObj);
		if (partnerServicesColl.isEmpty()) {
			throw new NEDSSAppException("No cases found to process between "+startingDateStr+" and "+endingDateStr, "ERR122");
		}
		logger.info("Partner Services: Retrieving partners that have no disposition");
		//Bring in partners that have no disposition
		ArrayList<Object> associatedPartnersColl = getPartnersOfIndexPatientsWithInvestigations(partnerServicesColl, nbsSecurityObj);
		if (associatedPartnersColl != null)
			logger.info("Partner Services: Completed retrieving partners. "+associatedPartnersColl.size()+" retreived.");
		//Also bring in partners that are contacts only with no public health case
		
		logger.info("Partner Services: Getting contacts that have no cases. ");
		ArrayList<Object> contactOnlyPartnersColl = getPartnersOfIndexPatientsWithContactOnly(partnerServicesColl, nbsSecurityObj);
		if (contactOnlyPartnersColl != null)
			logger.info("Partner Services: Completed getting" +contactOnlyPartnersColl.size() + " contacts that have no cases. ");
		
		//Add the partners and contacts into the main collection
		partnerServicesColl.addAll(associatedPartnersColl);
		partnerServicesColl.addAll(contactOnlyPartnersColl);
		
		//get all 900 Cases in the system
		logger.info("Partner Services: Getting all 900 cases in the system");
		ArrayList<Object> all900CaseColl = retrieveAll900Data(nbsSecurityObj);
		if (all900CaseColl != null)
			logger.info("Partner Services: Retrieved " +all900CaseColl.size() +" 900 cases in the system. Populating Case Maps.");
		if (all900CaseColl != null && !all900CaseColl.isEmpty())
			PartnerServicesHelperOld.populateTheCaseMaps(all900CaseColl);
		
		logger.info("Partner Services: Getting migrated Legacy cases in the system");
		ArrayList<Object> migratedCaseColl = retrieveAllLegacy900Data(nbsSecurityObj);
		if (migratedCaseColl != null)
			logger.info("Partner Services: Retrieved " +migratedCaseColl.size() +" migrated cases in the system. Populated migrated maps");
		if (migratedCaseColl != null && !migratedCaseColl.isEmpty())
			PartnerServicesHelperOld.populateTheMigratedMaps(migratedCaseColl);
		logger.info("Partner Services: Calling PartnerSericesHelper to create document");
		//create a new document
		xpemsdoc = XpemsPSDataDocument.Factory.newInstance();
		try {
			PartnerServicesHelperOld.buildPartnerServicesDocument( 
					partnerServicesColl,
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
		   cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance","schemaLocation"), "http://schemas.lutherconsulting.com/PSData_v2.1.xsd");
		 }
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


	/*
	 * Get all 900 (AIDS/HIV) cases in the system.
	 */
	private ArrayList<Object>  retrieveAll900Data(NBSSecurityObj nbsSecurityObj) {
		
		//retrieve a list of phc uids that meet the criteria
		PartnerServicesDAO partnerServicesDAO = new PartnerServicesDAO();
		ArrayList<Object> all900CaseColl = partnerServicesDAO.getAll900CasesInOurSystem();

		return all900CaseColl;
	}
	
	/*
	 * Get all migrated (Legacy) 900 (AIDS/HIV) cases in the system.
	 */
	private ArrayList<Object>  retrieveAllLegacy900Data(NBSSecurityObj nbsSecurityObj) {
		
		//retrieve a list of phc uids that meet the criteria
		PartnerServicesDAO partnerServicesDAO = new PartnerServicesDAO();
		ArrayList<Object> allMigratedCaseColl = partnerServicesDAO.getAllMigrated900Cases();

		return allMigratedCaseColl;
	}
	
	
	/*
	 * retrieve cases that fall in the date range
	 */
	private ArrayList<Object>  retrieveAndGatherPartnerServicesData(String startingDateStr,
			String endingDateStr, NBSSecurityObj nbsSecurityObj) {
		
		//retrieve a list of phc uids that meet the criteria
		PartnerServicesDAO partnerServicesDAO = new PartnerServicesDAO();
		ArrayList<Object> partnerServicesColl = partnerServicesDAO.getHivPartnerServicesMatchColl(startingDateStr, endingDateStr);
		logger.info("==================================================================");
		logger.info("PartnerServices: Retrieved "+partnerServicesColl.size() + " matching cases in time period.");
		logger.info("==================================================================");
		logger.info("==================================================================");
		logger.info("PartnerServices:Starting getting contact info for matching cases.");
		logger.info("==================================================================");
		//fill in the associated contacts for each investigation
		partnerServicesDAO.getNamedAndNamedBy(partnerServicesColl, nbsSecurityObj);
		logger.info("==================================================================");
		logger.info("PartnerServices:Completed getting contact info for matching cases.");
		logger.info("==================================================================");

		return partnerServicesColl;
	}
	
	/**
	 * We need to pull the associated partners into our list to be made into Clients.
	 * @param partnerServicesColl
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object> getPartnersOfIndexPatientsWithInvestigations(
			ArrayList<Object> partnerServicesColl, NBSSecurityObj nbsSecurityObj) {
		logger.debug("in PartnerServicesUtil.getPartnersOfIndexPatientsWithInvestigations()");
		ArrayList<Object> associatedPartnersColl = new ArrayList<Object>();
		Map<Long, String> casesInScopeUidToLocalIdMap = new HashMap<Long,String>();
		Iterator psCollIter = partnerServicesColl.iterator();
		while (psCollIter.hasNext()) {
			PartnerServicesLookupDT caseLookupDT = (PartnerServicesLookupDT) psCollIter.next();
			if (caseLookupDT.getPublicHealthCaseUid()!=null && caseLookupDT.getLocalId()!=null)
				casesInScopeUidToLocalIdMap.put(caseLookupDT.getPublicHealthCaseUid(), caseLookupDT.getLocalId());
		}
		//walk threw the list again and pull out any contacts that we don't already have
		PartnerServicesDAO partnerServicesDAO = new PartnerServicesDAO();
		psCollIter = partnerServicesColl.iterator();
		while (psCollIter.hasNext()) {
			PartnerServicesLookupDT caseLookupDT = (PartnerServicesLookupDT) psCollIter.next();
			if (caseLookupDT.getContactSummaryColl() != null && !caseLookupDT.getContactSummaryColl().isEmpty()) {
				Collection<Object> contactSummaryCollection = caseLookupDT.getContactSummaryColl();
				Iterator itr = contactSummaryCollection.iterator();
				while (itr.hasNext()) {
					CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) itr
							.next();
					if (contactSummaryDT.isContactNamedByPatient() && 
							contactSummaryDT.getContactReferralBasisCd() != null &&
									contactSummaryDT.getContactReferralBasisCd().startsWith("P")) {
						if (contactSummaryDT.getContactEntityPhcUid() != null) {
							if (casesInScopeUidToLocalIdMap.containsKey(contactSummaryDT.getContactEntityPhcUid()))
								continue; //already in list
							//need to add this one to the list..
							casesInScopeUidToLocalIdMap.put(caseLookupDT.getPublicHealthCaseUid(), caseLookupDT.getLocalId());
							ArrayList<Object> thisHivCase = partnerServicesDAO.getHivPartnerServicesCase(contactSummaryDT.getContactEntityPhcUid());
							
							if (thisHivCase != null) {
								PartnerServicesLookupDT psLookupDT = (PartnerServicesLookupDT) thisHivCase.get(0);
								psLookupDT.setIndexCaseLocalId(contactSummaryDT.getSubjectPhcLocalId()); //not getContactPhcLocalId()
								if (psLookupDT.getFldFollUpDispo() != null && (psLookupDT.getFldFollUpDispo().equals("1") || psLookupDT.getFldFollUpDispo().equals("2") || psLookupDT.getFldFollUpDispo().equals("5")))
									psLookupDT.setIsIndex(true);
								else
									psLookupDT.setIsIndex(false);
								psLookupDT.setIsPartner(true);
								psLookupDT.setIsContactRcd(false);
								psLookupDT.setContactSummaryColl(new ArrayList<Object>()); //we're not going to cascade out on this release
								associatedPartnersColl.add((Object)psLookupDT);
							}
						}
					}
				}
			}
		}
		logger.debug("leaving PartnerServicesUtil.getPartnersOfIndexPatientsWithInvestigations()");
		logger.debug("   associatedPartnersColl.size is "+associatedPartnersColl.size());
		return associatedPartnersColl;
	}
	/**
	 * We decided to also get the contacts without cases and process them as Clients with AsAPartner.
	 * @param partnerServicesColl
	 * @param nbsSecurityObj
	 * @return
	 */
	private ArrayList<Object> getPartnersOfIndexPatientsWithContactOnly(
			ArrayList<Object> partnerServicesColl, NBSSecurityObj nbsSecurityObj) {
		ArrayList<Object> contactsOnlyColl = new ArrayList<Object>();

		logger.debug("in PartnerServicesUtil.getPartnersOfIndexPatientsWithContactOnly()");
		//walk threw the list  and pull out any contacts without cases
		PartnerServicesDAO partnerServicesDAO = new PartnerServicesDAO();
		Iterator psCollIter = partnerServicesColl.iterator();
		while (psCollIter.hasNext()) {
			PartnerServicesLookupDT caseLookupDT = (PartnerServicesLookupDT) psCollIter.next();
			if (caseLookupDT.getContactSummaryColl() != null && !caseLookupDT.getContactSummaryColl().isEmpty()) {
				Collection<Object> contactSummaryCollection = caseLookupDT.getContactSummaryColl();
				Iterator itr = contactSummaryCollection.iterator();
				while (itr.hasNext()) {
					CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) itr.next();
					if (contactSummaryDT.isContactNamedByPatient() && 
							contactSummaryDT.getContactReferralBasisCd() != null &&
									contactSummaryDT.getContactReferralBasisCd().startsWith("P")) {
						if (contactSummaryDT.getContactEntityPhcUid() == null) { //This is a contact only with no case
							PartnerServicesLookupDT psLookupDT = new PartnerServicesLookupDT();
							//add this contact only to the return list..
							Long ctContactUid = contactSummaryDT.getCtContactUid();
							if (ctContactUid != null) {
								psLookupDT.setCtContactUid(ctContactUid);
								CTContactProxyVO ctContactProxyVO = PartnerServicesHelperOld.getContactVO(ctContactUid , nbsSecurityObj);
								if (ctContactProxyVO != null) {
									psLookupDT.setCtContactProxyVO(ctContactProxyVO);
									psLookupDT.setPersonLocalId(ctContactProxyVO.getContactPersonVO().getThePersonDT().getLocalId());
									psLookupDT.setProgAreaCd(ctContactProxyVO.getcTContactVO().getcTContactDT().getProgAreaCd());
									if (ctContactProxyVO.getcTContactVO().getcTContactDT().getContactReferralBasisCd() != null)
										psLookupDT.setReferralBasisCd(ctContactProxyVO.getcTContactVO().getcTContactDT().getContactReferralBasisCd());
									if (ctContactProxyVO.getcTContactVO().getcTContactDT().getDispositionCd() != null)
										psLookupDT.setFldFollUpDispo(ctContactProxyVO.getcTContactVO().getcTContactDT().getDispositionCd());
								}
								psLookupDT.setCtContactUid(ctContactUid);
								if (contactSummaryDT.getInterviewDate() != null)
									psLookupDT.setContactInterviewDate(contactSummaryDT.getInterviewDate());
								psLookupDT.setActivityFromTime(contactSummaryDT.getAddTime());
								psLookupDT.setIndexCaseLocalId(contactSummaryDT.getSubjectPhcLocalId());
								psLookupDT.setIsIndex(false);
								psLookupDT.setIsPartner(true);
								psLookupDT.setIsContactRcd(true);
								psLookupDT.setContactSummaryColl(new ArrayList<Object>()); //we're not going to cascade out on this release
								contactsOnlyColl.add((Object)psLookupDT);
							}
						}
					}
				}
			}
		}
		logger.debug("leaving PartnerServicesUtil.getPartnersOfIndexPatientsWithContactOnly()");
		logger.debug("   contactsOnlyColl.size is "+contactsOnlyColl.size());
		return contactsOnlyColl;
		
	}
		
}

