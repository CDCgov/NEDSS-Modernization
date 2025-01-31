package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonRaceVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.MessageBuilderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.phdc.CodedType;
import gov.cdc.nedss.phdc.HeaderType;
import gov.cdc.nedss.phdc.IdentifierType;
import gov.cdc.nedss.phdc.IdentifiersType;
import gov.cdc.nedss.phdc.NameType;
import gov.cdc.nedss.phdc.NumericType;
import gov.cdc.nedss.phdc.OrganizationParticipantType;
import gov.cdc.nedss.phdc.ParticipantsType;
import gov.cdc.nedss.phdc.PatientType;
import gov.cdc.nedss.phdc.PostalAddressType;
import gov.cdc.nedss.phdc.ProviderNameType;
import gov.cdc.nedss.phdc.ProviderParticipantType;
import gov.cdc.nedss.phdc.TelephoneType;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTGenericCodeDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJBException;
/**
 * Utility Class to parse person Specific information.
 * @author Pradeep Kumar Sharma
 * @release 4.3
 */
public class EdxPersonEntityProcessor {
	static final LogUtils logger = new LogUtils(EdxPersonEntityProcessor.class.getName());
	public static CachedDropDownValues cdv = new CachedDropDownValues();

	public static PublicHealthCaseVO setParticipant(ParticipantsType participantType, HeaderType headerType, String typeCd, PageActProxyVO pageActProxyVO, PamProxyVO pamProxyVO,NBSSecurityObj nbsSecurityObj ) {
		PublicHealthCaseVO publicHealthCaseVO= null;
		if(pageActProxyVO!=null)
			publicHealthCaseVO= pageActProxyVO.getPublicHealthCaseVO();
		else
			publicHealthCaseVO= pamProxyVO.getPublicHealthCaseVO();

		ProviderParticipantType[] providerParticipantTypeArray =participantType.getProviderArray();
		ProviderParticipantType providerParticipantType=null;
		boolean isPHC_INVESTIGATOR=false;
		boolean isPHC_REPORTER=false;
		boolean isPHC_PHYSICIAN=false;
		for(int num=0; num<providerParticipantTypeArray.length; num++){
			providerParticipantType=providerParticipantTypeArray[num];

			if(publicHealthCaseVO.getEdxPHCRLogDetailDTCollection()==null)
				publicHealthCaseVO.setEdxPHCRLogDetailDTCollection(new ArrayList<EDXActivityDetailLogDT>());
			else{
				if(providerParticipantType.getTypeCd()!=null && providerParticipantType.getTypeCd().getCode()!=null){
					if(providerParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_INVESTIGATOR)
							|| providerParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_REPORTER)
							|| providerParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_PHYSICIAN))
					{
						if(providerParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_INVESTIGATOR) && isPHC_INVESTIGATOR) {
							if(providerParticipantType.getName()!=null){
								logger.warn(" EdxPersonEntityProcessor.setParticipant duplicate InvestgrOfPHC found. Duplicate Provider "+providerParticipantType.getName().getLast() +", "+providerParticipantType.getName().getFirst()
										+" could not be created");
							}else{
								logger.warn("EdxPersonEntityProcessor.setParticipant duplicate InvestgrOfPHC found.");
									
							}
							continue;
						} else {
							isPHC_INVESTIGATOR= true;
						} 
						if(providerParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_REPORTER) && isPHC_REPORTER) {
							if(providerParticipantType.getName()!=null){
								logger.warn(" EdxPersonEntityProcessor.setParticipant duplicate PerAsReporterOfPHC found. Duplicate Provider "+providerParticipantType.getName().getLast() +", "+providerParticipantType.getName().getFirst()
										+" could not be created");
							}else{
								logger.warn("EdxPersonEntityProcessor.setParticipant duplicate PerAsReporterOfPHC found.");
									
							}
							continue;
						} else {
							isPHC_REPORTER=true;
						}
						if(providerParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_PHYSICIAN) && isPHC_PHYSICIAN) {
							if(providerParticipantType.getName()!=null){
								logger.warn(" EdxPersonEntityProcessor.setParticipant duplicate PhysicianOfPHC found. Duplicate Provider "+providerParticipantType.getName().getLast() +", "+providerParticipantType.getName().getFirst()
										+" could not be created");
							}else{
								logger.warn("EdxPersonEntityProcessor.setParticipant duplicate PhysicianOfPHC found.");
									
							}
							continue;
						} else {
							isPHC_PHYSICIAN= true;
						}
						
						EDXActivityDetailLogDT eDXActivityDetailLogDT = setProviderParticipationType(providerParticipantType,headerType, null,pageActProxyVO,pamProxyVO, nbsSecurityObj);
						publicHealthCaseVO.getEdxPHCRLogDetailDTCollection().add(eDXActivityDetailLogDT);
					}
				}
				
			}
		}
		OrganizationParticipantType[] organizationParticipantTypeArray =participantType.getOrganizationArray();
		OrganizationParticipantType organizationParticipantType=null;
		boolean isHOSPITAL_NAME_ADMITTED=false;
		boolean isPHC_REPORTING_SOURCE=false;
		for(int num=0; num<organizationParticipantTypeArray.length; num++){
			organizationParticipantType=organizationParticipantTypeArray[num];
			if(publicHealthCaseVO.getEdxPHCRLogDetailDTCollection()==null)
				publicHealthCaseVO.setEdxPHCRLogDetailDTCollection(new ArrayList<EDXActivityDetailLogDT>());
			else{
				
				if(organizationParticipantType.getTypeCd()!=null && organizationParticipantType.getTypeCd().getCode()!=null){
					if( organizationParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.HOSPITAL_NAME_ADMITTED)									
						|| organizationParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_REPORTING_SOURCE))
					{
						if( organizationParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.HOSPITAL_NAME_ADMITTED)&& isHOSPITAL_NAME_ADMITTED){
							if(organizationParticipantType.getName()!=null){
								logger.warn(" EdxPersonEntityProcessor.setParticipant duplicate HospOfADT found. Duplicate Organization "+organizationParticipantType.getName()
										+" could not be created");
							}else{
								logger.warn("EdxPersonEntityProcessor.setParticipant duplicate HospOfADT found.");
									
							}
							
							continue;
						}else{
							isHOSPITAL_NAME_ADMITTED = true;
						}
						if( organizationParticipantType.getTypeCd().getCode().equalsIgnoreCase(NEDSSConstants.PHC_REPORTING_SOURCE)&& isPHC_REPORTING_SOURCE){
							if(organizationParticipantType.getName()!=null){
								logger.warn(" EdxPersonEntityProcessor.setParticipant duplicate OrgAsReporterOfPHC found. Duplicate Organization "+organizationParticipantType.getName()
										+" could not be created");
							}else{
								logger.warn("EdxPersonEntityProcessor.setParticipant duplicate OrgAsReporterOfPHC found.");
									
							}
							
							continue;
						}else{
							isPHC_REPORTING_SOURCE = true;
						}
						
						EDXActivityDetailLogDT eDXActivityDetailLogDT = setOrganizationParticipationType(organizationParticipantType,headerType, null,pageActProxyVO,pamProxyVO,nbsSecurityObj);
						publicHealthCaseVO.getEdxPHCRLogDetailDTCollection().add(eDXActivityDetailLogDT);
					}
				}
			}
		}
		return publicHealthCaseVO;
	}

	public static EDXActivityDetailLogDT setProviderParticipationType(ProviderParticipantType providerParticipantType, HeaderType headerType,String typeCode, PageActProxyVO pageActProxyVO,PamProxyVO pamProxyVO, NBSSecurityObj nbsSecurityObj){
		PersonVO providerVO = new PersonVO();
		PublicHealthCaseVO publicHealthCaseVO= null;
		EDXActivityDetailLogDT eDXActivityDetailLogDT =null;

		if(pageActProxyVO!=null)
			publicHealthCaseVO= pageActProxyVO.getPublicHealthCaseVO();
		else
			publicHealthCaseVO= pamProxyVO.getPublicHealthCaseVO();

		int j = 0;

		Collection<Object> coll = new ArrayList<Object>();
		if(providerParticipantType.getIdentifiers()!=null){
			IdentifiersType identifiersType = providerParticipantType.getIdentifiers();
			IdentifierType[] identifierArray =identifiersType.getIdentifierArray();
	
			for(int i=0;i< identifierArray.length;i++){
				EntityIdDT entityIdDT  = new EntityIdDT();
				IdentifierType identifierType = identifierArray[i];
	
				if(identifierType!=null && identifierType.getIDTypeCode()!=null && identifierType.getIDTypeCode().equalsIgnoreCase(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID)){
	
					if(identifierType.getIDNumber()!=null && identifierType.getIDNumber().trim().length()>0 &&
							identifierType.getAssigningFacility()!=null && identifierType.getAssigningFacility().getNamespaceID()!=null && identifierType.getAssigningFacility().getNamespaceID().trim().length()>0 &&
							identifierType.getAssigningFacility()!=null && identifierType.getAssigningFacility().getUniversalID()!=null && identifierType.getAssigningFacility().getUniversalID().trim().length()>0 &&
							identifierType.getAssigningFacility()!=null && identifierType.getAssigningFacility().getUniversalIDType()!=null && identifierType.getAssigningFacility().getUniversalIDType().trim().length()>0 &&
							identifierType.getAssigningAuthority()!=null && identifierType.getAssigningAuthority().getNamespaceID()!=null && identifierType.getAssigningAuthority().getNamespaceID().trim().length()>0 &&
							identifierType.getAssigningAuthority()!=null && identifierType.getAssigningAuthority().getUniversalID()!=null && identifierType.getAssigningAuthority().getUniversalID().trim().length()>0 &&
							identifierType.getAssigningAuthority()!=null && identifierType.getAssigningAuthority().getUniversalIDType()!=null && identifierType.getAssigningAuthority().getUniversalIDType().trim().length()>0){
	
						StringBuffer buffer = new StringBuffer();
						buffer.append(identifierType.getIDTypeCode());
						buffer.append("^").append(identifierType.getIDNumber());
						buffer.append("^").append(identifierType.getAssigningFacility().getNamespaceID());
						buffer.append("^").append(identifierType.getAssigningFacility().getUniversalID());
						buffer.append("^").append(identifierType.getAssigningFacility().getUniversalIDType());
						buffer.append("^").append(identifierType.getAssigningAuthority().getNamespaceID());
						buffer.append("^").append(identifierType.getAssigningAuthority().getUniversalID());
						buffer.append("^").append(identifierType.getAssigningAuthority().getUniversalIDType());
						providerVO.setLocalIdentifier(buffer.toString());
					}
				}
				else{
					entityIdDT.setEntityIdSeq(new Integer(j++));
					entityIdDT.setAssigningAuthorityCd(identifierType.getAssigningAuthority().getUniversalID());
					entityIdDT.setAssigningAuthorityDescTxt(identifierType.getAssigningAuthority().getNamespaceID());
					entityIdDT.setAssigningAuthorityIdType(identifierType.getAssigningAuthority().getUniversalIDType());
					entityIdDT.setRootExtensionTxt(identifierType.getIDNumber());
					entityIdDT.setTypeCd(identifierType.getIDTypeCode());
					entityIdDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					entityIdDT.setStatusTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
					entityIdDT.setRecordStatusTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
					entityIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
					entityIdDT.setAsOfDate(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime());
					entityIdDT.setItNew(true);
					entityIdDT.setItDirty(false);
					coll.add(entityIdDT);
				}
			}
		providerVO.setTheEntityIdDTCollection(coll);
		}
		PersonDT personDT = new PersonDT();
		ProviderNameType providerNameType =providerParticipantType.getName();

		personDT.setAddTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
		personDT.setAddTime(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime());
		personDT.setAddUserId(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId());
		personDT.setCd(NEDSSConstants.PRV);
		personDT.setLastChgTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
		personDT.setLastChgUserId(publicHealthCaseVO.getThePublicHealthCaseDT().getLastChgUserId());
		personDT.setFirstNm(providerNameType.getFirst());
		personDT.setLastNm(providerNameType.getLast());
		personDT.setMiddleNm(providerNameType.getMiddle());
		personDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND_ELR);
		personDT.setEdxInd(NEDSSConstants.EDX_IND);

		personDT.setPersonUid(new Long(-1));
		personDT.setItNew(true);
		personDT.setItDirty(false);
		providerVO.setThePersonDT(personDT);
		PersonNameDT pdt = new PersonNameDT();
		pdt.setFirstNm(providerNameType.getFirst());
		if(providerNameType.getDegree()!=null && providerNameType.getDegree().getCode()!=null)
			pdt.setNmDegree(providerNameType.getDegree().getCode());
		pdt.setLastNm(providerNameType.getLast());
		pdt.setMiddleNm(providerNameType.getMiddle());
		pdt.setPersonUid(personDT.getPersonUid());
		pdt.setAddTime(new Timestamp(new Date().getTime()));
		pdt.setAddUserId(Long.valueOf(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId()));
		pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
		pdt.setPersonNameSeq(new Integer(1));
		pdt.setStatusTime(new Timestamp(new Date().getTime()));
		pdt.setRecordStatusTime(new Timestamp(new Date().getTime()));
		pdt.setItNew(true);
		pdt.setItDirty(false);
		pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
		pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		Collection<Object>  pdts = new ArrayList<Object> ();
		pdts.add(pdt);
		providerVO.setThePersonNameDTCollection(pdts);
		Collection<Object> entityLocatorColl= new ArrayList<Object>();
		if(providerParticipantType.getPostalAddress()!=null){
			entityLocatorColl=setPostalAddress(providerParticipantType.getPostalAddress(),publicHealthCaseVO,
					providerVO.getThePersonDT().getPersonUid());
		}
		if(providerParticipantType.getTelephone()!=null){
			Collection<Object> teleColl=setTelephone(providerParticipantType.getTelephone(),publicHealthCaseVO, providerVO.getThePersonDT().getPersonUid());
			entityLocatorColl.addAll(teleColl);
		}
		if(entityLocatorColl!=null && entityLocatorColl.size()>0)
			providerVO.setTheEntityLocatorParticipationDTCollection(entityLocatorColl);

		if(typeCode==null){
			CodedType codedType=providerParticipantType.getTypeCd();
			codedType.getCode();
			codedType.getCodeDescTxt();
		}

		EdxMatchingCriteriaUtil util =  new EdxMatchingCriteriaUtil();
		if(pageActProxyVO!=null)
			setAaOfDate(providerVO, pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime());
		else
			setAaOfDate(providerVO, pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime());

		try {
			providerVO.getThePersonDT().setCaseInd(true);
			eDXActivityDetailLogDT = util.getMatchingProvider(providerVO,nbsSecurityObj);
			Long personUid=Long.parseLong(eDXActivityDetailLogDT.getRecordId());
			providerVO.getThePersonDT().setPersonUid(personUid);
			if(publicHealthCaseVO.getEdxPHCRLogDetailDTCollection()==null)
				publicHealthCaseVO.setEdxPHCRLogDetailDTCollection(new ArrayList<EDXActivityDetailLogDT>());
			publicHealthCaseVO.getEdxPHCRLogDetailDTCollection().add(eDXActivityDetailLogDT);
		} 
		catch (Exception e) {
			logger.error("Error getting a match" +e);
			throw new EJBException(e);
		}
		setActEntityForCreate(pageActProxyVO,pamProxyVO, typeCode, providerVO.getThePersonDT().getPersonUid(),   NEDSSConstants.PERSON);
		return eDXActivityDetailLogDT;

	}


	public static EDXActivityDetailLogDT setOrganizationParticipationType(
			OrganizationParticipantType organizationParticipantType,HeaderType headerType,
			String typeCode, PageActProxyVO pageActProxyVO,PamProxyVO pamProxyVO, NBSSecurityObj nbsSecurityObj) {
		PublicHealthCaseVO publicHealthCaseVO= null;
		EDXActivityDetailLogDT eDXActivityDetailLogDT = null;
		if(pageActProxyVO!=null)
			publicHealthCaseVO= pageActProxyVO.getPublicHealthCaseVO();
		else
			publicHealthCaseVO= pamProxyVO.getPublicHealthCaseVO();
		OrganizationVO organizationVO= new OrganizationVO();

		organizationVO.setSendingFacility(headerType.getReceivingFacility().getNamespaceID());
		organizationVO.setSendingSystem(headerType.getSendingApplication().getNamespaceID());

		int j = 0;
		Collection<Object> coll = new ArrayList<Object>();
		if(organizationParticipantType.getIdentifiers()!=null){
			IdentifiersType identifiersType = organizationParticipantType.getIdentifiers();
			IdentifierType[] identifierArray =identifiersType.getIdentifierArray();
			for(int i=0;i< identifierArray.length;i++){
				EntityIdDT entityIdDT  = new EntityIdDT();
				entityIdDT.setEntityIdSeq(new Integer(j++));
				IdentifierType identifierType = identifierArray[i];
	
				if(identifierType!=null && identifierType.getIDTypeCode()!=null && identifierType.getIDTypeCode().equalsIgnoreCase(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID)){
					if(identifierType.getIDNumber()!=null && identifierType.getIDNumber().trim().length()>0 &&
							identifierType.getAssigningFacility()!=null && identifierType.getAssigningFacility().getNamespaceID()!=null && identifierType.getAssigningFacility().getNamespaceID().trim().length()>0 &&
							identifierType.getAssigningFacility()!=null && identifierType.getAssigningFacility().getUniversalID()!=null && identifierType.getAssigningFacility().getUniversalID().trim().length()>0 &&
							identifierType.getAssigningFacility()!=null && identifierType.getAssigningFacility().getUniversalIDType()!=null && identifierType.getAssigningFacility().getUniversalIDType().trim().length()>0 &&
							identifierType.getAssigningAuthority()!=null && identifierType.getAssigningAuthority().getNamespaceID()!=null && identifierType.getAssigningAuthority().getNamespaceID().trim().length()>0 &&
							identifierType.getAssigningAuthority()!=null && identifierType.getAssigningAuthority().getUniversalID()!=null && identifierType.getAssigningAuthority().getUniversalID().trim().length()>0 &&
							identifierType.getAssigningAuthority()!=null && identifierType.getAssigningAuthority().getUniversalIDType()!=null && identifierType.getAssigningAuthority().getUniversalIDType().trim().length()>0){
	
						StringBuffer buffer = new StringBuffer();
						buffer.append(identifierType.getIDTypeCode());
						buffer.append("^").append(identifierType.getIDNumber());
						buffer.append("^").append(identifierType.getAssigningFacility().getNamespaceID());
						buffer.append("^").append(identifierType.getAssigningFacility().getUniversalID());
						buffer.append("^").append(identifierType.getAssigningFacility().getUniversalIDType());
						buffer.append("^").append(identifierType.getAssigningAuthority().getNamespaceID());
						buffer.append("^").append(identifierType.getAssigningAuthority().getUniversalID());
						buffer.append("^").append(identifierType.getAssigningAuthority().getUniversalIDType());
						organizationVO.setLocalIdentifier(buffer.toString());
					}
				}else{
					entityIdDT.setAssigningAuthorityCd(identifierType.getAssigningAuthority().getUniversalID());
					entityIdDT.setAssigningAuthorityDescTxt(identifierType.getAssigningAuthority().getNamespaceID());
					entityIdDT.setRootExtensionTxt(identifierType.getIDNumber());
					entityIdDT.setAssigningAuthorityIdType(identifierType.getAssigningAuthority().getUniversalIDType());
					entityIdDT.setTypeCd(identifierType.getIDTypeCode());
					entityIdDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					entityIdDT.setStatusTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
					entityIdDT.setRecordStatusTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
					entityIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
					entityIdDT.setAsOfDate(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime());
					entityIdDT.setItNew(true);
					entityIdDT.setItDirty(false);
					coll.add(entityIdDT);
				}
			}
			organizationVO.setTheEntityIdDTCollection(coll);
		}
		OrganizationDT organizationDT = new OrganizationDT();
		organizationDT.setAddTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
		organizationDT.setAddUserId(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId());
		organizationDT.setLastChgTime_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());
		organizationDT.setLastChgUserId(publicHealthCaseVO.getThePublicHealthCaseDT().getLastChgUserId());
		organizationDT.setAddTime(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime());
		organizationDT.setDisplayNm(organizationParticipantType.getName());
		organizationDT.setOrganizationUid(new Long(-1));
		organizationDT.setItNew(true);
		organizationDT.setItDirty(false);
		organizationDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND_ELR);
		organizationDT.setEdxInd(NEDSSConstants.EDX_IND);
		organizationVO.setTheOrganizationDT(organizationDT);
		OrganizationNameDT orgNameDt= new OrganizationNameDT();
		orgNameDt.setNmTxt(organizationParticipantType.getName());
		orgNameDt.setAddTime(new Timestamp(new Date().getTime()));
		orgNameDt.setAddUserId(Long.valueOf(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId()));
		orgNameDt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
		orgNameDt.setOrganizationNameSeq(new Integer(1));
		orgNameDt.setStatusTime(new Timestamp(new Date().getTime()));
		orgNameDt.setRecordStatusTime(new Timestamp(new Date().getTime()));
		orgNameDt.setOrganizationUid(organizationDT.getOrganizationUid());
		orgNameDt.setItNew(true);
		orgNameDt.setItDirty(false);
		orgNameDt.setRecordStatusCd(NEDSSConstants.ACTIVE);
		orgNameDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		Collection<Object>  pdts = new ArrayList<Object> ();
		pdts.add(orgNameDt);
		organizationVO.setTheOrganizationNameDTCollection(pdts);

		Collection<Object> entityLocatorColl = new ArrayList<Object>();
		if(organizationParticipantType.getPostalAddress()!=null){
			Collection<Object> postalColl=setPostalAddress(organizationParticipantType.getPostalAddress(), publicHealthCaseVO,
					organizationVO.getTheOrganizationDT().getOrganizationUid());
			entityLocatorColl.addAll(postalColl);
		}

		if(organizationParticipantType.getTelephone()!=null){
			Collection<Object> teleColl=setTelephone(organizationParticipantType.getTelephone(),publicHealthCaseVO, organizationVO.getTheOrganizationDT().getOrganizationUid());
			entityLocatorColl.addAll(teleColl);

		}
		if(entityLocatorColl!=null && entityLocatorColl.size()>0)
			organizationVO.setTheEntityLocatorParticipationDTCollection(entityLocatorColl);


		if(typeCode==null){
			CodedType codedType=organizationParticipantType.getTypeCd();
			typeCode=codedType.getCode();
			codedType.getCodeDescTxt();
		}
		EdxMatchingCriteriaUtil util =  new EdxMatchingCriteriaUtil();
		Long orgUid;
		try {
			eDXActivityDetailLogDT = util.getMatchingOrganization(organizationVO,nbsSecurityObj);
			orgUid=Long.parseLong(eDXActivityDetailLogDT.getRecordId());
			organizationVO.getTheOrganizationDT().setOrganizationUid(orgUid);
			if(publicHealthCaseVO.getEdxPHCRLogDetailDTCollection()==null)
				publicHealthCaseVO.setEdxPHCRLogDetailDTCollection(new ArrayList<EDXActivityDetailLogDT>());
			publicHealthCaseVO.getEdxPHCRLogDetailDTCollection().add(eDXActivityDetailLogDT);
		} 
		catch (Exception e) {
			logger.error("Error getting a match for the organization" +e);
			throw new EJBException(e);
		}
		setActEntityForCreate(pageActProxyVO,pamProxyVO, typeCode, organizationVO.getTheOrganizationDT().getOrganizationUid(),  NEDSSConstants.ORGANIZATION);
		return eDXActivityDetailLogDT;
	}

	public static void setActEntityForCreate(PageActProxyVO proxyVO,
			PamProxyVO pamProxyVO, String entityType, Long subjectEntityUID,
			String entityClassCd) {

		Long addUserId = null;
		Long actUid = null;
		if (proxyVO != null) {
			if (proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getPublicHealthCaseUid() != null) {
				actUid = proxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT().getPublicHealthCaseUid();
				addUserId = proxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT().getAddUserId();
			} else if (proxyVO.getInterviewVO().getTheInterviewDT()
					.getInterviewUid() != null) {
				actUid = proxyVO.getInterviewVO().getTheInterviewDT()
						.getInterviewUid();
				addUserId = proxyVO.getInterviewVO().getTheInterviewDT()
						.getAddUserId();
			}
		} else {
			actUid = pamProxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT().getPublicHealthCaseUid();
			addUserId = pamProxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT().getAddUserId();
		}
		int vcNum = 1;

		NbsActEntityDT nbsActEntityDT = new NbsActEntityDT();
		nbsActEntityDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		nbsActEntityDT.setAddUserId(addUserId);
		nbsActEntityDT.setEntityUid(subjectEntityUID);
		nbsActEntityDT.setEntityVersionCtrlNbr(new Integer(vcNum));
		nbsActEntityDT.setActUid(actUid);
		nbsActEntityDT.setTypeCd(entityType);
		createParticipation(proxyVO, pamProxyVO, subjectEntityUID,
				entityClassCd, entityType, actUid);
		if (proxyVO != null) {
			if (proxyVO.getPageVO().getActEntityDTCollection().size() == 0) {
				Collection<Object> coll = new ArrayList<Object>();
				coll.add(nbsActEntityDT);
				proxyVO.getPageVO().setActEntityDTCollection(coll);
			} else {
				proxyVO.getPageVO().getActEntityDTCollection()
						.add(nbsActEntityDT);
			}
		} else {
			if (pamProxyVO.getPamVO().getActEntityDTCollection().size() == 0) {
				Collection<Object> coll = new ArrayList<Object>();
				coll.add(nbsActEntityDT);
				pamProxyVO.getPamVO().setActEntityDTCollection(coll);
			} else {
				pamProxyVO.getPamVO().getActEntityDTCollection()
						.add(nbsActEntityDT);
			}
		}

	}
	
	public static void setActEntityForContactRecord(
			CTContactProxyVO proxyVO, String entityType, Long subjectEntityUID) {
		try{
		Long addUserId = proxyVO.getcTContactVO().getcTContactDT()
				.getAddUserId();
		Long actUid = proxyVO.getcTContactVO().getcTContactDT()
				.getCtContactUid();
		int vcNum = 1;
		NbsActEntityDT nbsActEntityDT = new NbsActEntityDT();
		nbsActEntityDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		nbsActEntityDT.setAddUserId(addUserId);
		nbsActEntityDT.setEntityUid(subjectEntityUID);
		nbsActEntityDT.setEntityVersionCtrlNbr(new Integer(vcNum));
		nbsActEntityDT.setActUid(actUid);
		nbsActEntityDT.setTypeCd(entityType);
		if (proxyVO != null) {
			if (proxyVO.getcTContactVO().getActEntityDTCollection() == null) {
				Collection<Object> coll = new ArrayList<Object>();
				coll.add(nbsActEntityDT);
				proxyVO.getcTContactVO().setActEntityDTCollection(coll);
			} else {
				proxyVO.getcTContactVO().getActEntityDTCollection()
						.add(nbsActEntityDT);
			}
		}
		}catch( Exception ex){
			String errorTxt = "error while creating act entity relationship for contact record: "+ex.getMessage();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}



	public static ParticipationDT createParticipation(PageActProxyVO pageActProxyVO,PamProxyVO pamProxyVO, Long subjectEntityUid, String subjectClassCd, String typeCd, Long actUid) {

		ParticipationDT participationDT = new ParticipationDT();
		participationDT.setActClassCd(NEDSSConstants.CLASS_CD_CASE);
		participationDT.setActUid(actUid);
		participationDT.setSubjectClassCd(subjectClassCd);
		participationDT.setSubjectEntityUid(subjectEntityUid);
		participationDT.setTypeCd(typeCd.trim());
		participationDT.setTypeDescTxt(cdv.getDescForCode("PAR_TYPE", typeCd.trim()));
		participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		participationDT.setItNew(true);
		participationDT.setItDirty(false);
		if(pamProxyVO!=null){
			if(pamProxyVO.getTheParticipationDTCollection()==null)
				pamProxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
			pamProxyVO.getTheParticipationDTCollection().add(participationDT);
		}

		if(pageActProxyVO!=null){
			if(pageActProxyVO.getTheParticipationDTCollection()==null)
				pageActProxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
			pageActProxyVO.getTheParticipationDTCollection().add(participationDT);
		}
		return participationDT;
	}
	private static Collection<Object> setTelephone(TelephoneType telephoneType, PublicHealthCaseVO publicHealthCaseVO, Long entityUid) {

		Collection<Object>  arrELP = new ArrayList<Object> ();
		String type=PageConstants.W_PHONE;
		EntityLocatorParticipationDT elpTele = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDT = new TeleLocatorDT();

		elpTele.setItNew(true);
		elpTele.setItDirty(false);
		elpTele.setAddTime(new Timestamp(new Date().getTime()));
		elpTele.setAddUserId(Long.valueOf(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId()));
		elpTele.setEntityUid(entityUid);
		if(type.equalsIgnoreCase(PageConstants.H_PHONE)){
			elpTele.setClassCd(NEDSSConstants.TELE);
			elpTele.setCd(NEDSSConstants.PHONE);
			elpTele.setUseCd(NEDSSConstants.HOME);
		}
		if(type.equalsIgnoreCase(PageConstants.W_PHONE)){
			elpTele.setClassCd(NEDSSConstants.TELE);
			elpTele.setCd(NEDSSConstants.PHONE);
			elpTele.setUseCd(NEDSSConstants.WORK_PHONE);
		}
		if(type.equalsIgnoreCase(PageConstants.EMAIL)){
			elpTele.setClassCd(NEDSSConstants.TELE);
			elpTele.setCd(NEDSSConstants.NET);
			elpTele.setUseCd(NEDSSConstants.HOME);
		}
		if(type.equalsIgnoreCase(PageConstants.CELL_PHONE)){
			elpTele.setClassCd(NEDSSConstants.TELE);
			elpTele.setCd(NEDSSConstants.CELL);
			elpTele.setUseCd(NEDSSConstants.MOBILE);
		}
		elpTele.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elpTele.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		teleDT.setPhoneNbrTxt(telephoneType.getNumber());
		teleDT.setExtensionTxt(telephoneType.getExtension());
		teleDT.setItNew(true);
		teleDT.setItDirty(false);
		teleDT.setAddTime(new Timestamp(new Date().getTime()));
		teleDT.setAddUserId(Long.valueOf(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId()));
		teleDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elpTele.setTheTeleLocatorDT(teleDT);
		arrELP.add(elpTele);

		return arrELP;
	}

	private static Collection<Object> setPostalAddress(PostalAddressType postalAddressType, PublicHealthCaseVO publicHealthCaseVO, Long entityUid){
		ArrayList<Object> arrayOfELP= new ArrayList<Object>();
		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
		elp.setItNew(true);
		elp.setItDirty(false);
		elp.setAddTime(new Timestamp(new Date().getTime()));
		elp.setAddUserId(Long.valueOf(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId()));
		elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setEntityUid(entityUid);
		elp.setCd(NEDSSConstants.OFFICE_CD);
		elp.setClassCd(NEDSSConstants.POSTAL);
		elp.setUseCd(NEDSSConstants.WORK_PLACE);
		elp.setAsOfDate_s(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime().toString());

		PostalLocatorDT pl = new PostalLocatorDT();
		pl.setItNew(true);
		pl.setItDirty(false);
		pl.setAddTime(publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime());
		pl.setAddUserId(Long.valueOf(publicHealthCaseVO.getThePublicHealthCaseDT().getAddUserId()));
		pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
		pl.setStreetAddr1(postalAddressType .getStreetAddressOne());
		pl.setStreetAddr2(postalAddressType .getStreetAddressTwo());
		pl.setZipCd(postalAddressType.getZipCode());
		if(postalAddressType.getState()!=null && postalAddressType.getState().getCode()!=null)
			pl.setStateCd(postalAddressType.getState().getCode());
		pl.setCityDescTxt(postalAddressType.getCity());
		if(postalAddressType.getCounty()!=null && postalAddressType.getCounty().getCode()!=null)
			pl.setCntyCd(postalAddressType.getCounty().getCode());
		pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

		elp.setThePostalLocatorDT(pl);
		arrayOfELP.add(elp);
		logger.info("Number of address in setBasicAddresses: " + arrayOfELP.size());
		return arrayOfELP;
	}


	public static PersonVO setPatientVO( PageActProxyVO pageActProxyVO,PamProxyVO pamProxyVO, PatientType patientType, Timestamp time, boolean isForCreate) throws NEDSSAppException{
		MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();
		PersonVO personVO = new PersonVO();
		try {
			NameType nameType = patientType.getName();
			//PersonVO
			personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
			personVO.setItDirty(false);
			personVO.getThePersonDT().setItNew(true);
			personVO.getThePersonDT().setItDirty(false);
			personVO.setItNew(true);
			personVO.setMPRUpdateValid(false);

			//PersonDT
			personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
			personVO.getThePersonDT().setItNew(true);
			personVO.getThePersonDT().setLastChgTime(time);
			if(pageActProxyVO!=null)
				personVO.getThePersonDT().setLastChgUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
			else
				personVO.getThePersonDT().setLastChgUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());

			if(nameType!=null) {
				personVO.getThePersonDT().setFirstNm(nameType.getFirst());
				personVO.getThePersonDT().setMiddleNm(nameType.getMiddle());
				personVO.getThePersonDT().setLastNm(nameType.getLast());
			}

			if(isForCreate)
				personVO.getThePersonDT().setPersonUid(new Long(-2));

			Long longVal = null;

			if(patientType.getDateOfBirth() != null) {
				longVal=patientType.getDateOfBirth().getTimeInMillis();
			}

			if (longVal != null) {
				Timestamp dobTimestamp = new Timestamp(longVal);
				personVO.getThePersonDT().setBirthTime(dobTimestamp);
			}

			NumericType patientAge = patientType.getReportedAge();

			if( patientAge!=null && patientType.getReportedAge()!=null && patientAge.getValue1() > -1 ){
				int age=(int)patientAge.getValue1();
				personVO.getThePersonDT().setAgeReported(age+"");
			}
			if ( patientAge!=null && patientAge.getUnit()!=null ) {
				try {
					SRTGenericCodeDT srtAgeCode = messageBuilderHelper.reversePHINToNBSCodeTranslation("PHVS_AGE_UNIT", patientAge.getUnit().getCode(), "Standard_XREF");
					if(srtAgeCode!=null)
						personVO.getThePersonDT().setAgeReportedUnitCd(srtAgeCode.getCode());
				}
				catch(Exception e) {
					logger.error("NbsPHCRDocumentUtil-getPatientVO: there exists no reported age code associated with the message in Standard_XREF table for code ste nm PHVS_AGE_UNIT "+e);
				}
			}
			
			if(patientType.getDeceasedIndicator()!=null && patientType.getDeceasedIndicator().getCode()!=null)
				personVO.getThePersonDT().setDeceasedIndCd(patientType.getDeceasedIndicator().getCode());
			if(patientType.getDeceasedDate()!=null)
				personVO.getThePersonDT().setDeceasedTime(new Timestamp(patientType.getDeceasedDate().getTimeInMillis()));
			if((patientType.getDeceasedIndicator()!=null && patientType.getDeceasedIndicator().getCode()!=null) 
					|| patientType.getDeceasedDate()!=null)
				personVO.getThePersonDT().setAsOfDateMorbidity(time);
			
			if (patientType.getSex() != null) {
				personVO.getThePersonDT().setCurrSexCd(patientType.getSex().getCode());
			}

			//Person Race	
			/*At max we can parse only one race code at a time and as per Christi we will get only one top level race category group*/
			ArrayList<Object> thePersonRaceDTCollection  = new ArrayList<Object> ();
			if( patientType.getRaceArray() != null ) {
				for ( int i = 0; i <  patientType.getRaceArray().length; i++ ) {
					PersonRaceDT personRaceDT = new PersonRaceDT();
					CodedType codedType = patientType.getRaceArray()[i];
					personRaceDT.setRaceCd(	codedType.getCode());
					personRaceDT.setAddTime(time);
					if(pageActProxyVO!=null)
						personRaceDT.setAddUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
					else
						personRaceDT.setAddUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());

					personRaceDT.setAsOfDate(time);
					personRaceDT.setItNew(true);
					personRaceDT.setItDirty(false);
					personRaceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					PersonRaceVO raceVO = CachedDropDowns.getRaceAndCategoryCode(codedType.getCode());
					personRaceDT.setRaceCategoryCd(raceVO.getRaceCategoryCd());
					if ( isForCreate &&raceVO.getRaceCd()!= null &&raceVO.getRaceCategoryCd() != null ) {
						personRaceDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
						thePersonRaceDTCollection.add(personRaceDT);
					}
				}	
			}

			if (thePersonRaceDTCollection!=null && thePersonRaceDTCollection.size()>0) {
				personVO.setThePersonRaceDTCollection(thePersonRaceDTCollection);
			}

			ArrayList<Object> thePersonEthnicGroupDTCollection  = new ArrayList<Object> ();

			PersonEthnicGroupDT personEthnicGroupDT= new PersonEthnicGroupDT();
			CodedType codedType=  patientType.getEthnicity();
			if(codedType!=null){
				personEthnicGroupDT.setEthnicGroupCd(codedType.getCode());
				personEthnicGroupDT.setEthnicGroupDescTxt(codedType.getCodeDescTxt());
				personEthnicGroupDT.setAddTime(time);
				if(pageActProxyVO!=null)
					personEthnicGroupDT.setAddUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
				else
					personEthnicGroupDT.setAddUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());

				if(isForCreate)
					personEthnicGroupDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
				thePersonEthnicGroupDTCollection.add(personEthnicGroupDT);
			}
			if(thePersonEthnicGroupDTCollection!=null && thePersonEthnicGroupDTCollection.size()>0){
				personVO.setThePersonEthnicGroupDTCollection(thePersonEthnicGroupDTCollection);
			}

			CodedType ethnicCodedType = patientType.getEthnicity();

			if ( ethnicCodedType != null ) {
				personVO.getThePersonDT().setEthnicGroupInd(ethnicCodedType.getCode());
				personVO.getThePersonDT().setAsOfDateEthnicity(time);
			}

			//Person Name
			PersonNameDT personNameDT = new PersonNameDT();
			personNameDT.setItNew(true);
			personNameDT.setItDirty(false);
			personNameDT.setAddTime(new Timestamp(new Date().getTime()));
			if(pageActProxyVO!=null)
				personNameDT.setAddUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
			else
				personNameDT.setAddUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());

			personNameDT.setAsOfDate(time);
			personNameDT.setNmUseCd(NEDSSConstants.LEGAL_NAME);
			personNameDT.setPersonNameSeq(new Integer(1));
			personNameDT.setStatusTime(new Timestamp(new Date().getTime()));
			personNameDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
			personNameDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
			personNameDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			personNameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

			if (nameType != null) {
				personNameDT.setFirstNm(nameType.getFirst());
				personNameDT.setMiddleNm(nameType.getMiddle());
				personNameDT.setLastNm(nameType.getLast());
				personNameDT.setAsOfDate(time);
			}

			Collection<Object>  thePersonNameDTCollection  = new ArrayList<Object> ();
			thePersonNameDTCollection.add(personNameDT);

			if (isForCreate)
				personNameDT.setPersonUid(personVO.getThePersonDT().getPersonUid());

			personNameDT.setAsOfDate(time);

			if (thePersonNameDTCollection  != null && thePersonNameDTCollection.size() > 0) {
				personVO.setThePersonNameDTCollection(thePersonNameDTCollection);
			}

			Collection<Object>  elPColl = new ArrayList<Object> ();

			//Postal LocatorDT
			PostalAddressType postalLocatorType = patientType.getPostalAddress();

			if (postalLocatorType != null) {
				PostalLocatorDT thePostalLocatorDT = new PostalLocatorDT();
				thePostalLocatorDT.setStreetAddr1(postalLocatorType.getStreetAddressOne());
				thePostalLocatorDT.setStreetAddr2(postalLocatorType.getStreetAddressTwo());
				thePostalLocatorDT.setCityDescTxt(postalLocatorType.getCity());
				if(postalLocatorType.getCounty() != null)
					thePostalLocatorDT.setCntyCd(postalLocatorType.getCounty().getCode());
				if(postalLocatorType.getState() != null)
					thePostalLocatorDT.setStateCd(postalLocatorType.getState().getCode());
				if (postalLocatorType.getCountry() != null){
					thePostalLocatorDT.setCntryCd(XMLTypeToNBSObject.getNbsCode("DEM167",postalLocatorType.getCountry(),	new Long(3560)));
				}

				thePostalLocatorDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				thePostalLocatorDT.setRecordStatusTime(time);
				thePostalLocatorDT.setZipCd(postalLocatorType.getZipCode());
				EntityLocatorParticipationDT entityPostalLocatorDT = new EntityLocatorParticipationDT();
				entityPostalLocatorDT.setItNew(true);
				entityPostalLocatorDT.setItDirty(false);
				entityPostalLocatorDT.setAddTime(time);
				if(pageActProxyVO!=null)
					entityPostalLocatorDT.setAddUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
				else
					entityPostalLocatorDT.setAddUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());

				entityPostalLocatorDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				entityPostalLocatorDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				entityPostalLocatorDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
				entityPostalLocatorDT.setCd(NEDSSConstants.HOME);
				entityPostalLocatorDT.setClassCd(NEDSSConstants.POSTAL);
				entityPostalLocatorDT.setUseCd(NEDSSConstants.HOME);
				entityPostalLocatorDT.setThePostalLocatorDT(thePostalLocatorDT);
				entityPostalLocatorDT.setAsOfDate(time);
				elPColl.add(entityPostalLocatorDT);
			}

			//TeleLoctorDT
			TelephoneType telephoneLocatorType = patientType.getTelephone();

			if (telephoneLocatorType != null) {
				EntityLocatorParticipationDT entityTeleLocatorDT = new EntityLocatorParticipationDT();
				entityTeleLocatorDT.setItNew(true);
				entityTeleLocatorDT.setItDirty(false);
				entityTeleLocatorDT.setAddTime(new Timestamp(new Date().getTime()));
				if(pageActProxyVO!=null)
					entityTeleLocatorDT.setAddUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
				else
					entityTeleLocatorDT.setAddUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());

				entityTeleLocatorDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
				entityTeleLocatorDT.setClassCd(NEDSSConstants.TELE);
				entityTeleLocatorDT.setCd(NEDSSConstants.PHONE);
				entityTeleLocatorDT.setUseCd(NEDSSConstants.HOME);
				entityTeleLocatorDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				entityTeleLocatorDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				entityTeleLocatorDT.setAsOfDate(time);
				TeleLocatorDT homeTeleLocatorDT = new TeleLocatorDT();
				homeTeleLocatorDT.setPhoneNbrTxt(telephoneLocatorType.getNumber());
				homeTeleLocatorDT.setExtensionTxt(telephoneLocatorType.getExtension());
				homeTeleLocatorDT.setItNew(false);
				homeTeleLocatorDT.setItDirty(false);
				homeTeleLocatorDT.setAddTime(time);



				if(pageActProxyVO!=null)
					homeTeleLocatorDT.setAddUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
				else
					homeTeleLocatorDT.setAddUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());

				homeTeleLocatorDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				homeTeleLocatorDT.setRecordStatusTime(time);
				homeTeleLocatorDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				entityTeleLocatorDT.setTheTeleLocatorDT(homeTeleLocatorDT);
				elPColl.add(entityTeleLocatorDT);
			}
			String typeCode="SubjOfPHC";
			if(elPColl!=null && elPColl.size()>0)
				personVO.setTheEntityLocatorParticipationDTCollection(elPColl);
			else
				personVO.setTheEntityLocatorParticipationDTCollection(null);
			setActEntityForCreate(pageActProxyVO, pamProxyVO,typeCode, personVO.getThePersonDT().getPersonUid(),   NEDSSConstants.PERSON);

			setAaOfDate(personVO, time);
		} catch (Exception e) {
			logger.error("NbsPHCRDocumentUtil getPatientVO NEDSSAppException:"+e.getCause());
			logger.error("NbsPHCRDocumentUtil getPatientVO NEDSSAppException:"+e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
		return personVO;
	}

	private static void setAaOfDate(PersonVO pvo, Timestamp aod) {
		try {
			PersonDT dt = pvo.getThePersonDT();

			dt.setAsOfDateAdmin(aod);

			if (dt.getEthnicGroupInd() != null)
				dt.setAsOfDateEthnicity(aod);

			if (pvo.getThePersonDT().getBirthTime() != null 
					|| pvo.getThePersonDT().getAgeReported() != null
					|| pvo.getThePersonDT().getCurrSexCd() != null
					|| pvo.getThePersonDT().getBirthGenderCd() != null) {
				pvo.getThePersonDT().setAsOfDateSex(aod);
			} else {
				pvo.getThePersonDT().setAsOfDateSex(null);
			}

			if (dt.getMothersMaidenNm() != null || dt.getAdultsInHouseNbr() != null ||dt.getChildrenInHouseNbr() != null || 
					dt.getEducationLevelCd() != null || dt.getOccupationCd() != null || dt.getMaritalStatusCd() != null || 
					dt.getPrimLangCd() != null) {
				dt.setAsOfDateGeneral(aod);
			}

			if (pvo.getTheEntityIdDTCollection()!=null) {
				Iterator<Object> eIdIt = pvo.getTheEntityIdDTCollection().iterator();

				while (eIdIt.hasNext()) {
					EntityIdDT eIddt = (EntityIdDT)eIdIt.next();
					eIddt.setAsOfDate(aod);
					eIddt.setRecordStatusTime(aod);
					eIddt.setStatusTime(aod);
				}
			}

			if (pvo.getTheEntityLocatorParticipationDTCollection()!=null) {
				Iterator<Object> elpIt = pvo.getTheEntityLocatorParticipationDTCollection().iterator();

				while (elpIt.hasNext()) {
					EntityLocatorParticipationDT elpDt = (EntityLocatorParticipationDT)elpIt.next();
					elpDt.setAsOfDate(aod);
				}
			}

			if( pvo.getThePersonRaceDTCollection()!=null){
				Iterator<Object> pRaceIt = pvo.getThePersonRaceDTCollection().iterator();

				while (pRaceIt.hasNext()) {
					PersonRaceDT pRaceDt = (PersonRaceDT)pRaceIt.next();
					pRaceDt.setAsOfDate(aod);
				}

			}

			if( pvo.getThePersonNameDTCollection()!=null){
				Iterator<Object> pNIt = pvo.getThePersonNameDTCollection().iterator();

				while (pNIt.hasNext()) {
					PersonNameDT pnDt = (PersonNameDT)pNIt.next();
					pnDt.setAsOfDate(aod);
				}

			}
		} catch (Exception e) {
			logger.error("NbsPHCRDocumentUtil setAaOfDate NEDSSAppException:"+e.getCause());
			logger.error("NbsPHCRDocumentUtil setAaOfDate NEDSSAppException:"+e);
		}
	}

}
