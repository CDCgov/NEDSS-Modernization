package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.util.Iterator;
import java.util.TreeMap;

public class QuickEntryEventHelper {

	static final LogUtils logger = new LogUtils(QuickEntryEventHelper.class
			.getName());

	static CachedDropDownValues cachedValues = new CachedDropDownValues();
	
	public String makePRVDisplayString(PersonVO proVO) {
	return makePRVDisplayString(proVO, false);
	}

	public String makePRVDisplayString(PersonVO proVO, boolean includeIDs) {
		PersonDT personDT = null;
		PostalLocatorDT postalDT = null;
		TeleLocatorDT teleDT = null;

		StringBuffer stBuff = new StringBuffer("");
		
		try {
		
		personDT = proVO.getThePersonDT();

		if (proVO.getThePersonNameDTCollection() != null) {

			Iterator personNameIt = proVO.getThePersonNameDTCollection()
					.iterator();

			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {
                    if(includeIDs)
	                	stBuff.append((personNameDT.getNmPrefix() == null) ? ""
								: (personNameDT.getNmPrefix() + " "));
					stBuff.append((personNameDT.getFirstNm() == null) ? ""
							: (personNameDT.getFirstNm() + " "));
					if(includeIDs)
	                	stBuff.append((personNameDT.getMiddleNm() == null) ? ""
								: (personNameDT.getMiddleNm() + " "));
					stBuff.append((personNameDT.getLastNm() == null) ? ""
							: (personNameDT.getLastNm()));
					stBuff.append((personNameDT.getNmSuffix() == null) ? ""
							: (", " + personNameDT.getNmSuffix()));
					stBuff.append(
							(personNameDT.getNmDegree() == null) ? ""
									: (", " + personNameDT.getNmDegree()));
				}
			}
		}

		if (proVO.getTheEntityLocatorParticipationDTCollection() != null) {
			Iterator entIt = proVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (entIt.hasNext()) {
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt
						.next();
				if (entityDT != null) {
					if (entityDT.getCd() != null
							&& entityDT.getCd().equalsIgnoreCase("O")
							&& entityDT.getClassCd() != null
							&& entityDT.getClassCd().equalsIgnoreCase("PST")
							&& entityDT.getRecordStatusCd() != null
							&& entityDT.getRecordStatusCd().equalsIgnoreCase(
									"ACTIVE")
							&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
						postalDT = entityDT.getThePostalLocatorDT();
						stBuff.append((postalDT.getStreetAddr1() == null) ? ""
								: ("<br/>" + postalDT.getStreetAddr1()));
						stBuff.append((postalDT.getStreetAddr2() == null) ? ""
								: ("<br/>" + postalDT.getStreetAddr2()));
						stBuff.append((postalDT.getCityDescTxt() == null) ? ""
								: ("<br/>" + postalDT.getCityDescTxt()));
						stBuff
								.append((postalDT.getCityDescTxt() == null || postalDT
										.getStateCd() == null) ? " " : (", "));
						stBuff.append((postalDT.getStateCd() == null) ? ""
								: (getStateDescTxt(postalDT.getStateCd()))
										+ " ");
						stBuff.append((postalDT.getZipCd() == null) ? ""
								: (postalDT.getZipCd() + " "));

					}

					if (entityDT.getClassCd() != null) {
						if (entityDT.getClassCd() != null
								&& entityDT.getClassCd().equalsIgnoreCase(
										"TELE")
								&& entityDT.getRecordStatusCd() != null
								&& entityDT.getRecordStatusCd()
										.equalsIgnoreCase("ACTIVE")
								&& entityDT.getCd() != null
								&& entityDT.getCd().equalsIgnoreCase("PH")
								&& entityDT.getUseCd() != null
								&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
							teleDT = entityDT.getTheTeleLocatorDT();

							stBuff
									.append((teleDT.getEmailAddress() == null) ? ""
											: ("<br/>" + teleDT.getEmailAddress()));

							stBuff
									.append((teleDT.getPhoneNbrTxt() == null) ? ""
											: ("<br/>" + teleDT.getPhoneNbrTxt() + " "));
							String ext = "";
							if(teleDT.getExtensionTxt()!=null && !teleDT.getExtensionTxt().equals("0.0")){
									ext = teleDT.getExtensionTxt().replace(".0", "");
									stBuff
									.append("<b>Ext. </b>" + ext);
							}
							
						}
					}
				}
			}
		}
		if(includeIDs && proVO.getTheEntityIdDTCollection()!=null && proVO.getTheEntityIdDTCollection().size()>0){
      	  Iterator<Object> ite = proVO.getTheEntityIdDTCollection().iterator();
      	  while(ite.hasNext()){
      		  EntityIdDT entityId = (EntityIdDT)ite.next();
      		stBuff.append("<br><b>").append(entityId.getTypeCd()).append(":</b> ");
      		  if(entityId.getRootExtensionTxt()!=null && entityId.getRootExtensionTxt().trim().length()>0)
      			stBuff.append(entityId.getRootExtensionTxt()).append(" ");
      		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
      			stBuff.append("(").append(entityId.getAssigningAuthorityDescTxt()).append(" ");
      		  if(entityId.getAssigningAuthorityCd() !=null && entityId.getAssigningAuthorityCd().trim().length()>0)
      			stBuff.append("(").append(entityId.getAssigningAuthorityCd()).append(")");
      		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
      			stBuff.append(")");
      	  }
        }
		} catch (Exception ex) {
			logger.error("Error in makePRVDisplayString: "+ex.getMessage());
			ex.printStackTrace();
		}
		return stBuff.toString();
	}

	public String makePatientDisplayString(PersonVO patVO) {

		String cellPhone = "";
		String homePhone = "";
		String workPhone = "";
		String emailAddress = "";
		StringBuffer sbName = new StringBuffer("");
		StringBuffer sbAddress = new StringBuffer("");
		StringBuffer sbDisplay = new StringBuffer("");
		
		try {

		if (patVO.getThePersonNameDTCollection() != null) {

			Iterator personNameIt = patVO.getThePersonNameDTCollection()
					.iterator();

			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {

					sbName.append((personNameDT.getFirstNm() == null) ? ""
							: (personNameDT.getFirstNm() + " "));
					sbName.append((personNameDT.getLastNm() == null) ? ""
							: (personNameDT.getLastNm()));
					sbName.append((personNameDT.getNmSuffix() == null) ? ""
							: (", " + personNameDT.getNmSuffix()));
					sbName.append(
							(personNameDT.getNmDegree() == null) ? ""
									: (", " + personNameDT.getNmDegree()));
				}
			}
		}

		if (patVO.getTheEntityLocatorParticipationDTCollection() != null) {
			Iterator locatorIter = patVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (locatorIter.hasNext()) {
				EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) locatorIter.next();
					//get Home Address if there
					if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("PST") 
							&& elpDT.getUseCd().equalsIgnoreCase("H") ) {
							PostalLocatorDT postal = elpDT.getThePostalLocatorDT();
								if (postal != null && (postal.getStreetAddr1() != null || postal.getCityCd() != null || postal.getZipCd() != null)) {
									String streetAdd1 = postal.getStreetAddr1();
									String streetAdd2 = postal.getStreetAddr2();
									String cityCd = postal.getCityCd();
									String stateDesc = getStateDescTxt(postal.getStateCd());
									String zipCd = postal.getZipCd();
									sbAddress = new StringBuffer("");
									if(streetAdd1 != null && streetAdd1.trim().length() > 0)
										sbAddress.append(streetAdd1);
									if(streetAdd2!= null && streetAdd2.trim().length() > 0)
										sbAddress.append(streetAdd2).append("</br>");
									else
										sbAddress.append("</br>");
									if(cityCd != null && cityCd.trim().length() > 0)
										sbAddress.append(cityCd).append(", ");
									if(stateDesc != null && stateDesc.trim().length() > 0)
										sbAddress.append(stateDesc).append(" ");
									if(zipCd != null && zipCd.trim().length() > 0)	
										sbAddress.append(zipCd);
								} //if address data present
					} //if home adr
			  		//get Tele Locator
			  		if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("TELE")) {
			  			TeleLocatorDT tele = elpDT.getTheTeleLocatorDT();
			  			if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("CP" ) && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
				  			cellPhone = tele.getPhoneNbrTxt();
			  			} else if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("PH" ) &&  elpDT.getUseCd().equalsIgnoreCase("H") && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
					  		homePhone = tele.getPhoneNbrTxt();
			  			} else if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("PH" ) &&  elpDT.getUseCd().equalsIgnoreCase("WK") && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
				  			workPhone = tele.getPhoneNbrTxt();					  			
			  			} else if(elpDT.getCd() != null && elpDT.getCd().equalsIgnoreCase("NET" ) && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
				  			emailAddress = tele.getEmailAddress();						  					  			
			  			}
			  		}
				} //locator
			}
		
		if (sbName.length() == 0)
			sbDisplay.append("-No Name-").append("</br>");
		else
			sbDisplay.append(sbName).append("</br>");
		if (sbAddress.length() > 0)
			sbDisplay.append(sbAddress).append("</br>");
		if (!homePhone.isEmpty())
			sbDisplay.append("hm:").append(homePhone).append("</br>");
		if (!cellPhone.isEmpty())
			sbDisplay.append("cell:").append(cellPhone).append("</br>");
		if (!workPhone.isEmpty())
			sbDisplay.append("wk:").append(workPhone).append("</br>");;
		if (!emailAddress.isEmpty())		
			sbDisplay.append(emailAddress);
		} catch (Exception ex) {
			logger.error("Error in makePatientDisplayString: "+ex.getMessage());
			ex.printStackTrace();
		}
		return sbDisplay.toString();
	}
	
	public String makeORGDisplayString(OrganizationVO orgVO) {
		
		return makeORGDisplayString(orgVO, false);
	}
	
	
	public String makeORGDisplayString(OrganizationVO orgVO, boolean includeIDs) {

		OrganizationDT orgDT = null;
		PostalLocatorDT postalDT = null;
		TeleLocatorDT teleDT = null;
		StringBuffer stBuff = null;

		stBuff = new StringBuffer("");
		
		try {
		orgDT = orgVO.getTheOrganizationDT();
		if (orgVO.getTheOrganizationNameDTCollection() != null) {
			Iterator orgNameIt = orgVO.getTheOrganizationNameDTCollection()
					.iterator();
			while (orgNameIt.hasNext()) {
				OrganizationNameDT orgNmDT = (OrganizationNameDT) orgNameIt
						.next();
				if (orgNmDT.getNmUseCd() != "L") {
					stBuff.append((orgNmDT.getNmTxt() == null) ? "" : (orgNmDT
							.getNmTxt()));
				}

				if (orgVO.getTheEntityLocatorParticipationDTCollection() != null) {
					Iterator entIt = orgVO
							.getTheEntityLocatorParticipationDTCollection()
							.iterator();
					while (entIt.hasNext()) {
						EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt
								.next();
						if (entityDT != null) {
							if (entityDT.getClassCd().equalsIgnoreCase("PST")
									&& entityDT.getRecordStatusCd()
											.equalsIgnoreCase("ACTIVE")
									&& entityDT.getStatusCd().equalsIgnoreCase(
											"A")
									&& entityDT.getUseCd().equalsIgnoreCase(
											"WP")) {
								postalDT = entityDT.getThePostalLocatorDT();
								stBuff
										.append((postalDT.getStreetAddr1() == null) ? ""
												: ("<br/>" + postalDT.getStreetAddr1()));
								stBuff
										.append((postalDT.getStreetAddr2() == null) ? ""
												: ("<br/>" + postalDT.getStreetAddr2()));
								stBuff
										.append((postalDT.getCityDescTxt() == null) ? ""
												: ("<br/>" + postalDT.getCityDescTxt()));
								stBuff
										.append((postalDT.getCityDescTxt() == null && postalDT
												.getStateCd() == null) ? ""
												: (", "));
								if (postalDT.getStateCd() != null) {
									stBuff.append(getStateDescTxt(postalDT
											.getStateCd()));

								}
								stBuff
										.append((postalDT.getZipCd() == null) ? ""
												: (" " + postalDT.getZipCd() + " "));
							}
							if (entityDT.getClassCd().equalsIgnoreCase("TELE")
									&& entityDT.getRecordStatusCd()
											.equalsIgnoreCase("ACTIVE")
									&& entityDT.getStatusCd().equalsIgnoreCase(
											"A")
									&& entityDT.getUseCd().equalsIgnoreCase(
											"WP")) {
								teleDT = entityDT.getTheTeleLocatorDT();
								stBuff
										.append((teleDT.getPhoneNbrTxt() == null) ? "<br/>"
												: ("<br/>"
														+ teleDT
																.getPhoneNbrTxt() + " "));
								String ext = "";
								if(teleDT.getExtensionTxt()!=null && !teleDT.getExtensionTxt().equals("0.0")){
										ext = teleDT.getExtensionTxt().replace(".0", "");
										stBuff.append("<b>Ext. </b>" + ext);
							
										
								}
							}
						}
					}
				}
				if(includeIDs && orgVO.getTheEntityIdDTCollection()!=null && orgVO.getTheEntityIdDTCollection().size()>0){
			      	  Iterator<Object> ite = orgVO.getTheEntityIdDTCollection().iterator();
			      	  while(ite.hasNext()){
			      		  EntityIdDT entityId = (EntityIdDT)ite.next();
			      		stBuff.append("<br><b>").append(entityId.getTypeCd()).append(":</b> ");
			      		  if(entityId.getRootExtensionTxt()!=null && entityId.getRootExtensionTxt().trim().length()>0)
			      			stBuff.append(entityId.getRootExtensionTxt()).append(" ");
			      		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
			      			stBuff.append("(").append(entityId.getAssigningAuthorityDescTxt()).append(" ");
			      		  if(entityId.getAssigningAuthorityCd() !=null && entityId.getAssigningAuthorityCd().trim().length()>0)
			      			stBuff.append("(").append(entityId.getAssigningAuthorityCd()).append(")");
			      		  if(entityId.getAssigningAuthorityDescTxt() !=null && entityId.getAssigningAuthorityDescTxt().trim().length()>0)
			      			stBuff.append(")");
			      	  }
			        }
			}
		}
		} catch (Exception ex) {
			logger.error("Error in makeOrgDisplayString: "+ex.getMessage());
			ex.printStackTrace();
		}

		return stBuff.toString();
	}

	public String getStateDescTxt(String sStateCd) {

		String desc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap treemap = srtValues.getStateCodes2("USA");
			if (treemap != null) {
				if (sStateCd != null && treemap.get(sStateCd) != null) {
					desc = (String) treemap.get(sStateCd);
				}
			}
		}

		return desc;
	}
}