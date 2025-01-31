package gov.cdc.nedss.webapp.nbs.action.person;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;

/**
 * Title:        PersonHistoryDetailLoad
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Ning Peng
 * @version 1.0
 */
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class PersonHistoryDetailLoad
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(PersonHistoryDetailLoad.class.getName());
    boolean bReports = false;

    /**
     * Creates a new PersonHistoryDetailLoad object.
     */
    public PersonHistoryDetailLoad()
    {
    }

    /**
     * Struts execute(), loading a person history detail
     * @param mapping an ActionMapping object
     * @param form an ActionForm object
     * @param request a HttpServletRequest object
     * @param response a HttpServletResponse object
     * @return an ActionForward object
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {
        logger.debug("inside the PersonHistoryDetailLoad ");

        CompleteDemographicForm personForm = (CompleteDemographicForm)form;
        HttpSession session = request.getSession(false);

        if (session == null)
        {
            logger.debug("error no session");

            throw new ServletException("Error no session");
        }

        NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
                                        "NBSSecurityObject");
        String personUID = request.getParameter("personUID");

        if (personUID == null)
            personUID = (String)request.getAttribute("personUID");

        String seqNumber = request.getParameter("seqNumber");

        if (seqNumber == null)
            seqNumber = (String)request.getAttribute("seqNumber");

        //If it is a current view,seqNumber == -1, go to current view
        if (seqNumber.compareTo("-1") == 0)

            return mapping.findForward("viewCurrent");

        //  VIEW action
        if (!personUID.equals(""))
        {

            PersonVO person = getOldPersonObject(personUID, seqNumber,
                                                 personForm, session);

            if (bReports)

                //VOTester.createReport(person, "obsgen-view-load");
                convertPersonToRequestObj(person, request);
        }
        else
        {
            logger.error("!!!!!!!!!!!!    no person UID");

            throw new ServletException("!!!!!!!!!!!!    no person UID");
        }

        return mapping.findForward("view");
    }

    private String formatDate(java.sql.Timestamp timestamp)
    {

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        logger.info(
                "InvestigationMeaslesPreAction.formatDate: date: " +
                timestamp);

        if (timestamp != null)
            date = new Date(timestamp.getTime());

        logger.info("InvestigationMeaslesPreAction.formatDate: date: " +
                    date);

        if (date == null)

            return "";
        else

            return formatter.format(date);
    }

    private PersonVO getOldPersonObject(String uid, String histSeqNumber,
                                        CompleteDemographicForm form,
                                        HttpSession session)
    {

        PersonVO person = null;
        MainSessionCommand msCommand = null;

        if (uid != null && histSeqNumber != null)
        {

            try
            {

                Long personUID = new Long(uid.trim());
                Integer seqNumber = new Integer(histSeqNumber.trim());
                String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
                String sMethod = "getPersonHist";
                Object[] oParams = new Object[] { personUID, seqNumber };
                MainSessionHolder holder = new MainSessionHolder();
                msCommand = holder.getMainSessionCommand(session);

                ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
                                                         sMethod, oParams);
                person = (PersonVO)arr.get(0);
                form.setPerson(person);
            }
            catch (NumberFormatException e)
            {
                logger.error("Error: no person UID");
            }
            catch (Exception ex)
            {

                if (session == null)
                {
                    logger.error("Error: no session, please login");
                }

                logger.fatal("getOldPersonObject: ", ex);
            }
        }
        else
        { // if the request didn't povide the PersonUid, look in form
            person = form.getPerson();
        }

        return person;
    }

    private void convertPersonToRequestObj(PersonVO personVO,
                                           HttpServletRequest request)
    {

        //       logger.info("personVO class is - " + personVO);
        if (personVO != null)
        {

            //logger.debug("personVO class is - " + personVO);
            PersonDT person = personVO.getThePersonDT();

            //for the top bar
            request.setAttribute("personLocalID", person.getLocalId());

            //to persist this information for query string or input element
            request.setAttribute("personUID",
                                 String.valueOf(person.getPersonUid()));
            request.setAttribute("lastChgTime",
                                 formatDate(person.getLastChgTime()));
            request.setAttribute("lastChgUserId",
                                 String.valueOf(person.getLastChgUserId()));
            request.setAttribute("lastChgReasonCd",
                                 person.getLastChgReasonCd());
            request.setAttribute("recordStatusCd", person.getRecordStatusCd());
            request.setAttribute("description", person.getDescription());

            // create the person name parsed string for the batch entry javascript
            Collection<Object>  names = personVO.getThePersonNameDTCollection();

            if (names != null)
            {

               Iterator<Object>  iter = names.iterator();
                StringBuffer sNamesCombined = new StringBuffer("");

                while (iter.hasNext())
                {

                    PersonNameDT name = (PersonNameDT)iter.next();

                    if (name != null)
                    {

                        if (name.getStatusCd().equals("A"))
                        {
                            sNamesCombined.append(
                                    "person.personNameDT_s[i].nmUseCd=");

                            if (name.getNmUseCd() != null)
                                sNamesCombined.append(name.getNmUseCd());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].statusCd=");

                            if (name.getStatusCd() != null)
                                sNamesCombined.append(name.getStatusCd());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].nmPrefix=");

                            if (name.getNmPrefix() != null)
                                sNamesCombined.append(name.getNmPrefix());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].nmSuffix=");

                            if (name.getNmSuffix() != null)
                                sNamesCombined.append(name.getNmSuffix());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].nmDegree=");

                            if (name.getNmDegree() != null)
                                sNamesCombined.append(name.getNmDegree());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].durationUnitCd=");

                            if (name.getDurationUnitCd() != null)
                                sNamesCombined.append(name.getNmDegree());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].lastNm=");

                            if (name.getLastNm() != null)
                                sNamesCombined.append(name.getLastNm());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].lastNm2=");

                            if (name.getLastNm2() != null)
                                sNamesCombined.append(name.getLastNm2());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].firstNm=");

                            if (name.getFirstNm() != null)
                                sNamesCombined.append(name.getFirstNm());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].middleNm=");

                            if (name.getMiddleNm() != null)
                                sNamesCombined.append(name.getMiddleNm());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].middleNm2=");

                            if (name.getMiddleNm2() != null)
                                sNamesCombined.append(name.getMiddleNm2());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].fromTime_s=");

                            if (name.getFromTime() != null)
                                sNamesCombined.append(formatDate(name.getFromTime()));

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].toTime_s=");

                            if (name.getToTime() != null)
                                sNamesCombined.append(formatDate(name.getToTime()));

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].durationAmt=");

                            if (name.getDurationAmt() != null)
                                sNamesCombined.append(name.getDurationAmt());

                            sNamesCombined.append(
                                    "&person.personNameDT_s[i].personNameSeq=");

                            if (name.getPersonNameSeq() != null)
                                sNamesCombined.append(String.valueOf(name.getPersonNameSeq()));

                            sNamesCombined.append("&|");
                        }
                    }
                }

                request.setAttribute("names", sNamesCombined.toString());
            }

            // create the entity id parsed string for the batch entry javascript
            Collection<Object>  ids = personVO.getTheEntityIdDTCollection();

            if (ids != null)
            {

               Iterator<Object>  iter = ids.iterator();
                StringBuffer combinedIds = new StringBuffer("");

                while (iter.hasNext())
                {

                    EntityIdDT id = (EntityIdDT)iter.next();

                    if (id != null)
                    {

                        if (id.getStatusCd().equals("A"))
                        {
                            combinedIds.append(
                                    "person.entityIdDT_s[i].typeCd=");

                            if (id.getTypeCd() != null)
                                combinedIds.append(id.getTypeCd());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].typeDescTxt=");

                            if (id.getTypeDescTxt() != null)
                                combinedIds.append(id.getTypeDescTxt());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].assigningAuthorityCd=");

                            if (id.getAssigningAuthorityCd() != null)
                                combinedIds.append(id.getAssigningAuthorityCd());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].assigningAuthorityDescTxt=");

                            if (id.getAssigningAuthorityDescTxt() != null)
                                combinedIds.append(id.getAssigningAuthorityDescTxt());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].statusCd=");

                            if (id.getStatusCd() != null)
                                combinedIds.append(id.getStatusCd());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].rootExtensionTxt=");

                            if (id.getRootExtensionTxt() != null)
                                combinedIds.append(id.getRootExtensionTxt());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].validFromTime_s=");

                            if (id.getValidFromTime() != null)
                                combinedIds.append(formatDate(id.getValidFromTime()));

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].validToTime_s=");

                            if (id.getValidToTime() != null)
                                combinedIds.append(formatDate(id.getValidToTime()));

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].durationAmt=");

                            if (id.getDurationAmt() != null)
                                combinedIds.append(id.getDurationAmt());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].durationUnitCd=");

                            if (id.getDurationUnitCd() != null)
                                combinedIds.append(id.getDurationUnitCd());

                            combinedIds.append(
                                    "&person.entityIdDT_s[i].entityIdSeq=");

                            if (id.getEntityIdSeq() != null)
                                combinedIds.append(id.getEntityIdSeq());

                            combinedIds.append("&|");
                        }
                    }
                }

                request.setAttribute("ids", combinedIds.toString());
            }

            //  create the race parsed string
            StringBuffer sAmericanIndian = new StringBuffer("");
            String sAmericanIndianController = "";
            StringBuffer sWhite = new StringBuffer("");
            String sWhiteController = "";
            StringBuffer sAfricanAmerican = new StringBuffer("");
            String sAfricanAmericanController = "";
            StringBuffer sAsian = new StringBuffer("");
            String sAsianController = "";
            StringBuffer sHawaiian = new StringBuffer("");
            String sHawaiianController = "";
            String sOtherRace = "";
            String sOtherRaceController = "";
            String sOtherRaceDescText = "";
            Collection<Object>  races = personVO.getThePersonRaceDTCollection();

            if (races != null)
            {

               Iterator<Object>  iter = races.iterator();

                while (iter.hasNext())
                {

                    PersonRaceDT race = (PersonRaceDT)iter.next();

                    if (race != null)
                    {

                        if (race.getRecordStatusCd().equals("A") &&
                            race.getRaceCategoryCd().equals("U"))
                        {
                            request.setAttribute("unknownRace", "U");
                        } // american indian
                        else if (race.getRecordStatusCd().equals("A") &&
                                 race.getRaceCategoryCd().equals("1002-5"))
                        {
                            sAmericanIndianController = "y";
                            sAmericanIndian.append(race.getRaceCd()).append(
                                    "|");
                        }
                        else if (race.getRecordStatusCd().equals("A") &&
                                 race.getRaceCategoryCd().equals("2106-3"))
                        {
                            sWhiteController = "y";
                            sWhite.append(race.getRaceCd()).append("|");
                        }
                        else if (race.getRecordStatusCd().equals("A") &&
                                 race.getRaceCategoryCd().equals("2054-5"))
                        {
                            sAfricanAmericanController = "y";
                            sAfricanAmerican.append(race.getRaceCd()).append(
                                    "|");
                        }
                        else if (race.getRecordStatusCd().equals("A") &&
                                 race.getRaceCategoryCd().equals("2028-9"))
                        {
                            sAsianController = "y";
                            sAsian.append(race.getRaceCd()).append("|");
                        }
                        else if (race.getRecordStatusCd().equals("A") &&
                                 race.getRaceCategoryCd().equals("2076-8"))
                        {
                            sHawaiianController = "y";
                            sHawaiian.append(race.getRaceCd()).append("|");
                        }
                        else if (race.getRecordStatusCd().equals("A") &&
                                 race.getRaceCategoryCd().equals("O"))
                        {
                            sOtherRace = "true";
                            sOtherRaceDescText = race.getRaceDescTxt();
                        }
                    }
                }

                request.setAttribute("americanIndian",
                                     sAmericanIndian.toString());
                request.setAttribute("white", sWhite.toString());
                request.setAttribute("africanAmerican",
                                     sAfricanAmerican.toString());
                request.setAttribute("asian", sAsian.toString());
                request.setAttribute("hawaiian", sHawaiian.toString());
                request.setAttribute("americanIndianController",
                                     sAmericanIndianController);
                request.setAttribute("whiteController", sWhiteController);
                request.setAttribute("africanAmericanController",
                                     sAfricanAmericanController);
                request.setAttribute("asianController", sAsianController);
                request.setAttribute("hawaiianController", sHawaiianController);
                request.setAttribute("OtherRace", sOtherRace);
                request.setAttribute("OtherRaceDescText", sOtherRaceDescText);
            }

            // sets up the ethinicity parsed strings
            StringBuffer parsedEthnicity = new StringBuffer("");
            String parsedEthnicityController = "";
            String otherEthnic = "";
            String otherEthnicController = "";
            Collection<Object>  ethnicities = personVO.getThePersonEthnicGroupDTCollection();

            if (ethnicities != null)
            {

               Iterator<Object>  iter = ethnicities.iterator();

                if (iter != null)
                {

                    while (iter.hasNext())
                    {

                        PersonEthnicGroupDT ethnic = (PersonEthnicGroupDT)iter.next();

                        if (ethnic.getRecordStatusCd().equals("A") &&
                            ethnic.getEthnicGroupCd().equals("O"))
                        {
                            otherEthnicController = "y";
                            otherEthnic = ethnic.getEthnicGroupDescTxt();

                            if (otherEthnic.equals(""))
                                otherEthnic = "no desc put in";
                        }
                        else if (ethnic.getRecordStatusCd().equals("A"))
                        {
                            parsedEthnicityController = "y";
                            parsedEthnicity.append(ethnic.getEthnicGroupCd()).append(
                                    "|");
                        }
                    }
                }

                request.setAttribute("parsedEthnicity",
                                     parsedEthnicity.toString());
                request.setAttribute("parsedEthnicityController",
                                     parsedEthnicityController);
                request.setAttribute("otherEthnic", otherEthnic);
                request.setAttribute("otherEthnicController",
                                     otherEthnicController);
            }

            // sex and birth info
            request.setAttribute("birthTime",
                                 formatDate(person.getBirthTime()));
            request.setAttribute("ageCalc",
                                 String.valueOf(person.getAgeCalc()));
            request.setAttribute("ssn", person.getSSN());
            request.setAttribute("currSexCd", person.getCurrSexCd());
            request.setAttribute("birthGenderCd", person.getBirthGenderCd());
            request.setAttribute("multipleBirthInd",
                                 person.getMultipleBirthInd());
            request.setAttribute("birthOrderNbr",
                                 String.valueOf(person.getBirthOrderNbr()));
            request.setAttribute("ageReported", person.getAgeReported());
            request.setAttribute("ageReportedUnitCd",
                                 person.getAgeReportedUnitCd());
            request.setAttribute("ageReportedTime",
                                 formatDate(person.getAgeReportedTime()));
            request.setAttribute("birthTimeCalc",
                                 formatDate(person.getBirthTimeCalc()));
            request.setAttribute("ageCategoryCd", person.getAgeCategoryCd());
            request.setAttribute("ageCategoryCd", person.getAgeCategoryCd());
            request.setAttribute("deceasedIndCd", person.getDeceasedIndCd());
            request.setAttribute("deceasedDate",
                                 formatDate(person.getDeceasedTime()));

            //  general information
            request.setAttribute("ssn", person.getSSN());
            request.setAttribute("mothersMaidenNm",
                                 person.getMothersMaidenNm());
            request.setAttribute("adultsInHouseNbr",
                                 String.valueOf(person.getAdultsInHouseNbr()));
            request.setAttribute("childrenInHouseNbr",
                                 String.valueOf(person.getChildrenInHouseNbr()));
            request.setAttribute("educationLevelCd",
                                 person.getEducationLevelCd());
            request.setAttribute("educationLevelDescTxt",
                                 person.getEducationLevelDescTxt());
            request.setAttribute("occupationCd", person.getOccupationCd());
            request.setAttribute("maritalStatusCd",
                                 person.getMaritalStatusCd());
            request.setAttribute("maritalStatusDescTxt",
                                 person.getMaritalStatusDescTxt());
            request.setAttribute("primLangCd", person.getPrimLangCd());
            request.setAttribute("primLangDescTxt",
                                 person.getPrimLangDescTxt());

            StringBuffer sParsedAddresses = new StringBuffer("");
            StringBuffer sParsedTeles = new StringBuffer("");
            StringBuffer sParsedLocators = new StringBuffer("");
            Collection<Object>  addresses = personVO.getTheEntityLocatorParticipationDTCollection();

            if (addresses != null)
            {

               Iterator<Object>  iter = addresses.iterator();

                while (iter.hasNext())
                {

                    EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)iter.next();

                    if (elp != null)
                    {

                        if (elp.getStatusCd() != null &&
                            elp.getClassCd() != null &&
                            elp.getUseCd() != null &&
                            elp.getStatusCd().equals("A") &&
                            elp.getClassCd().equals("PST") &&
                            elp.getCd().equals("BDL") &&
                            elp.getUseCd().equals("BIR"))
                        {

                            PostalLocatorDT postal = elp.getThePostalLocatorDT();
                            request.setAttribute("birthCity",
                                                 postal.getCityCd());
                            request.setAttribute("birthState",
                                                 postal.getStateCd());
                            request.setAttribute("birthCountry",
                                                 postal.getCntryCd());
                            request.setAttribute("birthCounty",
                                                 postal.getCntyCd());
                        }
                        else if (elp.getStatusCd() != null &&
                                 elp.getClassCd() != null &&
                                 elp.getUseCd() != null &&
                                 elp.getStatusCd().equals("A") &&
                                 elp.getClassCd().equals("PST") &&
                                 elp.getUseCd().equals("DTH"))
                        {

                            PostalLocatorDT postal = elp.getThePostalLocatorDT();
                            request.setAttribute("deathCity",
                                                 postal.getCityCd());
                            request.setAttribute("deathState",
                                                 postal.getStateCd());
                            request.setAttribute("deathCountry",
                                                 postal.getCntryCd());
                            request.setAttribute("deathCounty",
                                                 postal.getCntyCd());
                        } //   sets up the addresses parsed string
                        else if (elp.getStatusCd() != null &&
                                 elp.getClassCd() != null &&
                                 elp.getStatusCd().equals("A") &&
                                 elp.getClassCd().equals("PST"))
                        {

                            PostalLocatorDT postal = elp.getThePostalLocatorDT();
                            sParsedAddresses.append("address[i].cd=");

                            if (elp.getCd() != null)
                                sParsedAddresses.append(elp.getCd());

                            sParsedAddresses.append("&address[i].useCd=");

                            if (elp.getUseCd() != null)
                                sParsedAddresses.append(elp.getUseCd());

                            sParsedAddresses.append(
                                    "&address[i].durationUnitCd=");

                            if (elp.getDurationUnitCd() != null)
                                sParsedAddresses.append(elp.getDurationUnitCd());

                            sParsedAddresses.append("&address[i].cdDescTxt=");

                            if (elp.getCdDescTxt() != null)
                                sParsedAddresses.append(elp.getCdDescTxt());

                            sParsedAddresses.append(
                                    "&address[i].thePostalLocatorDT_s.streetAddr1=");

                            if (postal.getStreetAddr1() != null)
                                sParsedAddresses.append(postal.getStreetAddr1());

                            sParsedAddresses.append(
                                    "&address[i].thePostalLocatorDT_s.streetAddr2=");

                            if (postal.getStreetAddr2() != null)
                                sParsedAddresses.append(postal.getStreetAddr2());

                            sParsedAddresses.append(
                                    "&address[i].thePostalLocatorDT_s.cityCd=");

                            if (postal.getCityCd() != null)
                                sParsedAddresses.append(postal.getCityCd());

                            sParsedAddresses.append(
                                    "&address[i].thePostalLocatorDT_s.stateCd=");

                            if (postal.getStateCd() != null)
                                sParsedAddresses.append(postal.getStateCd());

                            sParsedAddresses.append(
                                    "&address[i].thePostalLocatorDT_s.zipCd=");

                            if (postal.getZipCd() != null)
                                sParsedAddresses.append(postal.getZipCd());

                            sParsedAddresses.append(
                                    "&address[i].thePostalLocatorDT_s.cntyCd=");

                            if (postal.getCntyCd() != null)
                                sParsedAddresses.append(postal.getCntyCd());

                            sParsedAddresses.append(
                                    "&address[i].thePostalLocatorDT_s.cntryCd=");

                            if (postal.getCntryCd() != null)
                                sParsedAddresses.append(postal.getCntryCd());

                            sParsedAddresses.append("&address[i].fromTime_s=");

                            if (elp.getFromTime() != null)
                                sParsedAddresses.append(formatDate(elp.getFromTime()));

                            sParsedAddresses.append("&address[i].toTime_s=");

                            if (elp.getToTime() != null)
                                sParsedAddresses.append(formatDate(elp.getToTime()));

                            sParsedAddresses.append("&address[i].durationAmt=");

                            if (elp.getDurationAmt() != null)
                                sParsedAddresses.append(elp.getDurationAmt());

                            sParsedAddresses.append(
                                    "&address[i].validTimeTxt=");

                            if (elp.getValidTimeTxt() != null)
                                sParsedAddresses.append(elp.getValidTimeTxt());

                            sParsedAddresses.append(
                                    "&address[i].locatorDescTxt=");

                            if (elp.getLocatorDescTxt() != null)
                                sParsedAddresses.append(elp.getLocatorDescTxt());

                            sParsedAddresses.append(
                                    "&address[i].postalLocatorUid=");

                            if (postal.getPostalLocatorUid() != null)
                                sParsedAddresses.append(postal.getPostalLocatorUid());

                            sParsedAddresses.append("&address[i].locatorUid=");

                            if (elp.getLocatorUid() != null)
                                sParsedAddresses.append(elp.getLocatorUid());

                            sParsedAddresses.append("&|");
                        } //  create the telephone parsed string
                        else if (elp.getStatusCd() != null &&
                                 elp.getClassCd() != null &&
                                 elp.getStatusCd().equals("A") &&
                                 elp.getClassCd().equals("TELE"))
                        {

                            TeleLocatorDT tele = elp.getTheTeleLocatorDT();
                            sParsedTeles.append(
                                    "person.entityLocatorParticipationDT_s[i].cd=");

                            if (elp.getCd() != null)
                                sParsedTeles.append(elp.getCd());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].useCd=");

                            if (elp.getUseCd() != null)
                                sParsedTeles.append(elp.getUseCd());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].durationUnitCd=");

                            if (elp.getDurationUnitCd() != null)
                                sParsedTeles.append(elp.getDurationUnitCd());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].cdDescTxt=");

                            if (elp.getCdDescTxt() != null)
                                sParsedTeles.append(elp.getCdDescTxt());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].theTeleLocatorDT_s.cntryCd=");

                            if (tele.getCntryCd() != null)
                                sParsedTeles.append(tele.getCntryCd());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].theTeleLocatorDT_s.phoneNbrTxt=");

                            if (tele.getPhoneNbrTxt() != null)
                                sParsedTeles.append(tele.getPhoneNbrTxt());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].theTeleLocatorDT_s.extensionTxt=");

                            if (tele.getExtensionTxt() != null)
                                sParsedTeles.append(tele.getExtensionTxt());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].theTeleLocatorDT_s.emailAddress=");

                            if (tele.getEmailAddress() != null)
                                sParsedTeles.append(tele.getEmailAddress());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].theTeleLocatorDT_s.urlAddress=");

                            if (tele.getUrlAddress() != null)
                                sParsedTeles.append(tele.getUrlAddress());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].fromTime_s=");

                            if (elp.getFromTime() != null)
                                sParsedTeles.append(formatDate(elp.getFromTime()));

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].toTime_s=");

                            if (elp.getToTime() != null)
                                sParsedTeles.append(formatDate(elp.getToTime()));

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].durationAmt=");

                            if (elp.getDurationAmt() != null)
                                sParsedTeles.append(elp.getDurationAmt());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].validTimeTxt=");

                            if (elp.getValidTimeTxt() != null)
                                sParsedTeles.append(elp.getValidTimeTxt());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].locatorDescTxt=");

                            if (elp.getLocatorDescTxt() != null)
                                sParsedTeles.append(elp.getLocatorDescTxt());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].theTeleLocatorDT_s.teleLocatorUid=");

                            if (tele.getTeleLocatorUid() != null)
                                sParsedTeles.append(tele.getTeleLocatorUid());

                            sParsedTeles.append(
                                    "&person.entityLocatorParticipationDT_s[i].locatorUid=");

                            if (elp.getLocatorUid() != null)
                                sParsedTeles.append(elp.getLocatorUid());

                            sParsedTeles.append("&|");
                        }
                        else if (elp.getStatusCd() != null &&
                                 elp.getClassCd() != null &&
                                 elp.getStatusCd().equals("A") &&
                                 elp.getClassCd().equals("PHYS"))
                        {

                            PhysicalLocatorDT physical = elp.getThePhysicalLocatorDT();
                            sParsedLocators.append(
                                    "person.entityLocatorParticipationDT_s[i].cd=");

                            if (elp.getCd() != null)
                                sParsedLocators.append(elp.getCd());

                            sParsedLocators.append(
                                    "&person.entityLocatorParticipationDT_s[i].useCd=");

                            if (elp.getUseCd() != null)
                                sParsedLocators.append(elp.getUseCd());

                            sParsedLocators.append(
                                    "&person.entityLocatorParticipationDT_s[i].cdDescTxt=");

                            if (elp.getCdDescTxt() != null)
                                sParsedLocators.append(elp.getUseCd());

                            sParsedLocators.append(
                                    "&person.entityLocatorParticipationDT_s[i].thePhysicalLocatorDT_s.name=not implemented");
                            sParsedLocators.append(
                                    "&person.entityLocatorParticipationDT_s[i].thePhysicalLocatorDT_s.locatorTxt=");

                            if (physical.getLocatorTxt() != null)
                                sParsedLocators.append(physical.getLocatorTxt());

                            sParsedLocators.append(
                                    "&person.entityLocatorParticipationDT_s[i].thePhysicalLocatorDT_s.physicalLocatorUid=");

                            if (physical.getPhysicalLocatorUid() != null)
                                sParsedLocators.append(physical.getPhysicalLocatorUid());

                            sParsedLocators.append(
                                    "&person.entityLocatorParticipationDT_s[i].locatorUid=");

                            if (elp.getLocatorUid() != null)
                                sParsedLocators.append(elp.getLocatorUid());

                            sParsedLocators.append("&|");
                        }
                    }
                }

                request.setAttribute("addresses", sParsedAddresses.toString());
                request.setAttribute("parsedTelephoneString",
                                     sParsedTeles.toString());
                request.setAttribute("parsedLocatorsString",
                                     sParsedLocators.toString());
            }
        }
    }
}