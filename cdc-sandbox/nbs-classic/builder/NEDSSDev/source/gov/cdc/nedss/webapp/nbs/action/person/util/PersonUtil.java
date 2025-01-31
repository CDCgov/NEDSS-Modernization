package gov.cdc.nedss.webapp.nbs.action.person.util;

import java.util.*;
import java.util.Date;

import javax.servlet.http.*;

import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationSRTCodeLookupTranslationDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.homepage.HomePageForm;
import gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * <p>Title: </p>
 * <p>Description: Utility class for Patient Module</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class PersonUtil {

   private static GregorianCalendar currentDate;
   static final LogUtils logger = new LogUtils(PersonUtil.class.getName());
   private static PropertyUtil propUtil = PropertyUtil.getInstance();

   
   
   
   public static String displayAge(String birthAge) {
	   
	      int years = 0;
	      int months = 0;
	      int days = 0;
	      
	      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	      Date birthDate = null ;
		
	      try {
			birthDate = sdf.parse(birthAge);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      //create calendar object for birth days
	      Calendar birthDay = Calendar.getInstance();
	      birthDay.setTimeInMillis(birthDate.getTime());
	      //create calendar object for current days
	      long currentTime = System.currentTimeMillis();
	      Calendar now = Calendar.getInstance();
	      now.setTimeInMillis(currentTime);
	      //Get difference between years
	      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
	      int currMonth = now.get(Calendar.MONTH) + 1;
	      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
	      //Get difference between months
	      months = currMonth - birthMonth;
	      //if months difference is in negative then reduce years by one and calculate the number of months.
	      
	      
	     boolean flag = true;
	      
	      if(months==1){
	    	  if(now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)){
	    		  int today = now.get(Calendar.DAY_OF_MONTH);
	 	         now.add(Calendar.MONTH, -1);
	 	        days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
	    		  if(days <= 28){
	  	        	  months = 0;
	  	        	  flag  = false;
	  	       } 
	    	  }
	    	  
	      }
	      if(flag){
	      if (months < 0)
	      {
	         years--;
	         months = 12 - birthMonth + currMonth;
	         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	            months--;
	      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         years--;
	         months = 11;
	      }
	      //Calculate the days
	      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
	         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
	      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         int today = now.get(Calendar.DAY_OF_MONTH);
	         now.add(Calendar.MONTH, -1);
	         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
	        
	        
	      } else
	      {
	         days = 0;
	         if (months == 12)
	         {
	            years++;
	            months = 0;
	         }
	      }
	      
	      if(days >=  30){
	        	months = months +1;
	       }
	      }
	    
	      String calcAge ="";
	      if (years != 0 && months != 0 && days != 0) {

	            calcAge = years + "|Y";

	         }
	         else if (years != 0 && months == 0 && days != 0) {

	            calcAge = years + "|Y";

	         }

	         else if (years == 0 && months != 0 && days != 0) {

	            calcAge = months + "|M";

	         }
	         else if (years != 0 && months != 0 && days == 0) {

	            calcAge = years + "|Y";

	         }
	         else if (years != 0 && months == 0 && days == 0) {

	            calcAge = years + "|Y";

	         }
	         else if (years == 0 && months == 0 && days != 0) {

	            calcAge = days + "|D";

	         }
	         else if (years != 0 && months == 0 && days == 0) {

	            calcAge = years + "|Y";

	         }
	         else if (years == 0 && months != 0 && days == 0) {

	            calcAge = months + "|M";

	         }
	         else {
	            //calcAge = years + " years " + months + " months and " + days + " days";
	            calcAge = days + "|D";
	         }
	      
	      
	       return calcAge;
	   }//displayAge

   public static String formatCurrentDate() {

      currentDate = new GregorianCalendar();

      int varM = currentDate.get(Calendar.MONTH) + 1;
      int varY = currentDate.get(Calendar.YEAR);
      int varD = currentDate.get(Calendar.DATE);

      String varMM = "" + varM;
      String varDD = "" + varD;
      String varYY = "" + varY;

      if (varM < 10) {
         varMM = "0" + varMM;
      }
      if (varD < 10) {
         varDD = "0" + varDD;
      }
      if (varY < 10) {
         varYY = "0" + varYY;
      }
      if (varY < 100) {
         varYY = "0" + varYY;
      }
      if (varY < 1000) {
         varYY = "0" + varYY;

      }
      String formatDate = varMM + "/" + varDD + "/" + varYY;

      return formatDate;
   }

   public static String displayAgeForPatientResults(String birthAge) {

      int birthYear = 0, birthMonth = 0, birthDay = 0;
      int currentYear = 0, currentMonth = 0, currentDay = 0;
      int year = 0, month = 0, day = 0;
      int x = 0, y = 0; // For Placement of Age
      String calcAge=""; // Calculated age String

      if (birthAge != null && !birthAge.equals("")) {
         StringTokenizer stAge = new StringTokenizer(birthAge, "/");
         try {
            if (stAge != null) {
               birthMonth = Integer.parseInt(stAge.nextToken());
               birthDay = Integer.parseInt(stAge.nextToken());
               birthYear = Integer.parseInt(stAge.nextToken());
            }
         }
         catch (Exception e) {
            e.printStackTrace();
         }
      }
      
      if (birthAge != null && !birthAge.equals("")) {
      GregorianCalendar birthDate = new GregorianCalendar(birthYear, birthMonth,
          birthDay);

      currentDate = new GregorianCalendar();

      currentYear = currentDate.get(Calendar.YEAR);
      currentMonth = currentDate.get(Calendar.MONTH) + 1;
      currentDay = currentDate.get(Calendar.DATE);

      year = currentYear - birthYear;
      month = currentMonth - birthMonth;

      if (month <= 0) {
         year--;
         if(month == 0 && currentDay - birthDay >= 0){
        	 year++;
         }
         else
        	 month = month + 12;
        
      }
      day = currentDay - birthDay;

      if (year == 0 && month == 0 && day == 0) {

         calcAge = "0 Days ";

      }
      if (day < 0) {
         month--;
         switch (currentMonth) {
            case 1:
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
               day = day + 31;
               break;
            case 5:
            case 7:
            case 10:
            case 12:
               day = day + 30;
               break;
            case 3:
               if (currentDate.isLeapYear(currentYear)) {
                  day = day + 29;
               }
               else {
                  day = day + 28;
               }
         }
      }

      if (year != 0 && month != 0 && day != 0) {

         calcAge = year + " Years ";

      }
      else if (year == 0 && month != 0 && day != 0) {

         calcAge = month + " Months ";

      }
      else if (year != 0 && month != 0 && day == 0) {

         calcAge = year + " Years ";

      }
      else if (year != 0 && month == 0 && day == 0) {

         calcAge = year + " Years ";

      }
      else if (year == 0 && month == 0 && day != 0) {

//if client wants age to be displayed in weeks also(as units - drop-down), uncomment the below code
         /*
               if(day >= 7 && day <= 30) {
                 int weeks = day/7;
                 psVO.setAgeReported(String.valueOf(weeks));
                 psVO.setAgeReportedUnitCd("W");
               } else {
               }
          */

         calcAge = day + " Days ";

      }
      else if (year != 0 && month == 0 && day == 0) {

         calcAge = year + " Years ";

      }
      else if (year == 0 && month != 0 && day == 0) {

         calcAge = month + " Months ";

      }
      else {
         //calcAge = year + " year " + month + " month and " + day + " days";
         calcAge = year + " Years ";
         if (year == 0 && month == 0 && day == 0) {
            calcAge = "0 Days ";
         }

      }
      }//if ends

      return calcAge;

   } //displayAgeForPatientResults
   
   /**
    * This method is used by the Person-Add Basic page. 
    * LDFs can be present on the bottom of the page of the type Patient LDF.
    * Note that the LDFs are stored on the form.getPamClientVO().getAnswerMap()
    *
    * @param CompleteDemographicForm personForm
    */
	public static Collection<Object>  extractPatientLDFs(CompleteDemographicForm form) {
		String actionMode = form.getActionMode();
		ArrayList<Object> returnList = new ArrayList<Object> ();
		ArrayList<Object> toRemove = new ArrayList<Object> ();
		Map<Object,Object> answerMap = form.getPamClientVO().getAnswerMap();
		Iterator<Object> iter = answerMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			if(key != null && key.startsWith("PATLDF_")) {
				String value = answerMap.get(key) == null ? "" : (String) answerMap.get(key);
				if(value.equals(""))
					continue;
				StateDefinedFieldDataDT  stateDT = new StateDefinedFieldDataDT();
				Long ldfUid = Long.valueOf(key.substring(key.indexOf("_")+1));
				stateDT.setLdfUid(ldfUid);
				stateDT.setBusinessObjNm("PAT");
				stateDT.setLdfValue(value);
				if(actionMode != null && actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
					stateDT.setItDirty(true);
				returnList.add(stateDT);
				toRemove.add(key);
			}
		}
		Map<Object,Object> answerArrayMap = form.getPamClientVO().getArrayAnswerMap();
		Iterator<Object> it = answerArrayMap.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			if(key.startsWith("PATLDF_")) {
				String[] valueList = (String[])answerArrayMap.get(key);
				  if(valueList != null && valueList.length > 0) {
						StateDefinedFieldDataDT  stateDT = new StateDefinedFieldDataDT();
						Long ldfUid = Long.valueOf(key.substring(key.indexOf("_")+1));
						stateDT.setLdfUid(ldfUid);
						stateDT.setBusinessObjNm("PAT");
						stateDT.setLdfValue(makeLDFAnswerString(valueList));
						if(actionMode != null && actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
							stateDT.setItDirty(true);
						returnList.add(stateDT);
						toRemove.add(key);
				  }
			}
		}
		//Remove LDFs from NBSAnswers
		for (int i=0; i< toRemove.size(); i++) {
			String key = (String) toRemove.get(i);
			answerMap.remove(key);
		}
		return returnList;
	}
	private static String makeLDFAnswerString(String[] valueList) {
		StringBuffer sb = new StringBuffer();
		  for (int i = 0; i < valueList.length; i++) {
				String tkn = valueList[i];
				sb.append(tkn).append("|");
		  }
		return sb.toString();
	}

   public static void setPatientForEventCreate(PersonVO personVO,
                                            HttpServletRequest request)
   {
     setNames(personVO, request);
     setAddressesForCreate(personVO, request);
     setEthnicityForCreate(personVO, request);
     setRaceForCreate(personVO, request);
     setTelephones(personVO, request);
     setIds(personVO, request);
   }

   public static void setPatientForEventEdit(PersonVO personVO,
                                            HttpServletRequest request)
   {
     setNamesForEdit(personVO, request);
     setEntityLocatorParticipationsForEdit(personVO, request);
     setEthnicityForEdit(personVO, request);
     setRaceForEdit(personVO, request);
     setIds(personVO, request);
   }

   public static void setAddressesForCreate(PersonVO personVO,
                                            HttpServletRequest request) {

      String city = request.getParameter("cityDescTxt");
      String street1 = request.getParameter("streetAddr1");
      String street2 = request.getParameter("streetAddr2");
      String zip = request.getParameter("zipCd");
      String state = request.getParameter("stateCd");
      String county = request.getParameter("cntyCd");
      String country = request.getParameter("countryCd");
      if(country==null)
      {
        country = request.getParameter("hiddenCountryCd");
      }
      String defaultStateFlag = request.getParameter("defaultStateFlag");

      if ( (city != null && !city.equals("")) ||
          (street1 != null && !street1.equals("")) ||
          (street2 != null && !street2.equals("")) ||
          (zip != null && !zip.equals("")) ||
          (county != null && !county.equals("")) ||
         (state != null && !state.equals("")) && (defaultStateFlag!=null && defaultStateFlag.equals("false")) )
         {

         Long personUID = personVO.getThePersonDT().getPersonUid();
         Collection<Object>  arrELP = personVO.
                             getTheEntityLocatorParticipationDTCollection();
         if (arrELP == null) {
            arrELP = new ArrayList<Object> ();

         }
         EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
         PostalLocatorDT pl = new PostalLocatorDT();
         pl.setCityDescTxt(city);
         pl.setStreetAddr1(street1);
         pl.setStreetAddr2(street2);

         elp.setItNew(true);
         elp.setItDirty(false);
         elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elp.setEntityUid(personUID);
         elp.setCd(NEDSSConstants.HOME);
         elp.setClassCd(NEDSSConstants.POSTAL);
         elp.setUseCd(NEDSSConstants.HOME);
         elp.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                           request.getParameter("addressAsOfDate") :
                           (String) request.getParameter("generalAsOfDate"));

         pl.setItNew(true);
         pl.setItDirty(false);
         pl.setAddTime(new Timestamp(new Date().getTime()));
         pl.setCityDescTxt(city);
         pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
         pl.setStreetAddr1(street1);
         pl.setStreetAddr2(street2);
         pl.setZipCd(zip);
         pl.setStateCd(state);
         pl.setCntyCd(county);
         pl.setCntryCd(country);
         pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

         elp.setThePostalLocatorDT(pl);
         arrELP.add(elp);
         logger.info("Number of address in setBasicAddresses: " + arrELP.size());
         personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
      }
   }

   public static void setTelephones(PersonVO personVO,
                                    HttpServletRequest request) {

      logger.info("Inside setBasicTelephones");
      Long personUID = personVO.getThePersonDT().getPersonUid();
      Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();

      if (arrELP == null) {
         arrELP = new ArrayList<Object> ();

      }
      EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
      TeleLocatorDT teleDTHome = new TeleLocatorDT();
      EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
      TeleLocatorDT teleDTWork = new TeleLocatorDT();
      String homePhone = request.getParameter("homePhoneNbrTxt");
      String workPhone = request.getParameter("workPhoneNbrTxt");
      String homeExt = request.getParameter("homeExtensionTxt");
      String workExt = request.getParameter("workExtensionTxt");
      logger.info("homePHone: " + homeExt + "   :workPhone: " + workExt);

      //Home Phone
      if (homePhone != null && !homePhone.equals("")) {
         elpHome.setItNew(true);
         elpHome.setItDirty(false);
         elpHome.setEntityUid(personUID);
         elpHome.setClassCd(NEDSSConstants.TELE);
         elpHome.setCd(NEDSSConstants.PHONE);
         elpHome.setUseCd(NEDSSConstants.HOME);
         elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         elpHome.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                               request.getParameter("telephoneAsOfDate") :
                               (String) request.getParameter("generalAsOfDate"));
         teleDTHome.setPhoneNbrTxt(homePhone);
         teleDTHome.setExtensionTxt(homeExt);
         teleDTHome.setItNew(true);
         teleDTHome.setItDirty(false);
         teleDTHome.setAddTime(new Timestamp(new Date().getTime()));
         teleDTHome.setExtensionTxt(homeExt);
         teleDTHome.setPhoneNbrTxt(homePhone);
         teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpHome.setTheTeleLocatorDT(teleDTHome);

         arrELP.add(elpHome);
      }

      //Work Phone
      if (workPhone != null && !workPhone.equals("")) {
         elpWork.setItNew(true);
         elpWork.setItDirty(false);
         elpWork.setEntityUid(personUID);
         elpWork.setClassCd(NEDSSConstants.TELE);
         elpWork.setCd(NEDSSConstants.PHONE);
         elpWork.setUseCd(NEDSSConstants.WORK_PHONE);
         elpWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         elpWork.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                               request.getParameter("telephoneAsOfDate") :
                               (String) request.getParameter("generalAsOfDate"));
         teleDTWork.setPhoneNbrTxt(homePhone);
         teleDTWork.setExtensionTxt(homeExt);
         teleDTWork.setItNew(true);
         teleDTWork.setItDirty(false);
         teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
         teleDTWork.setExtensionTxt(workExt);
         teleDTWork.setPhoneNbrTxt(workPhone);
         teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpWork.setTheTeleLocatorDT(teleDTWork);
         arrELP.add(elpWork);
      }

      personVO.setTheEntityLocatorParticipationDTCollection(arrELP);

   }

   public static void setIds(PersonVO personVO, HttpServletRequest request) {
      Collection<Object>  ids = personVO.getTheEntityIdDTCollection();

      Long personUID = personVO.getThePersonDT().getPersonUid();
      String patientSSNAsOfDate = request.getParameter("patientSSNAsOfDate");
      String patientSSN = request.getParameter("patientSSN");

      boolean isThereSSN = false;
      if (ids != null) {
        Iterator<Object>  itrCount = ids.iterator();
         //need to find the max seq nbr for existing names
         Integer maxSeqNbr = new Integer(0);
         while (itrCount.hasNext()) {
            EntityIdDT idDT = (EntityIdDT) itrCount.next();
            if (idDT.getEntityIdSeq() != null) {
               if (idDT.getEntityIdSeq().compareTo(maxSeqNbr) > 0) { // update the maxSeqNbr when you find a bigger one
                  maxSeqNbr = idDT.getEntityIdSeq();
               }
            }
         }
        Iterator<Object>  itrIds = ids.iterator();

         while (itrIds.hasNext()) {
            EntityIdDT id = (EntityIdDT) itrIds.next();

            if (id.getEntityUid() == null || id.getEntityUid().longValue()==0) { // this is a new one

               // tem fix for PHIN id.getEntityUid() == null || id.getEntityUid().longValue() < 0
               maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
               id.setEntityIdSeq(maxSeqNbr);

               if (id.getStatusCd() != null &&
                   id.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  //System.out.println(" this is a new one@@@@@@@@@@@@@@@@@@@@@@@@@");
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  id.setItNew(true);
                  id.setItDirty(false);
                  id.setAddTime(new Timestamp(new Date().getTime()));
                  id.setLastChgTime(new Timestamp(new Date().getTime()));
                  id.setRecordStatusTime(new Timestamp(new Date().getTime()));
                  id.setStatusTime(new Timestamp(new Date().getTime()));

                  if (id.getAsOfDate() == null) {
                     id.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                      "" :
                                      (String) request.getParameter("generalAsOfDate"));

                  }
                  id.setEntityUid(personUID);

               }
               else {
                  //if inactive from batch , just remove from collection
                  //System.out.println(" remove from collection");
                  itrIds.remove();
               }

            }
            else { //this is old one
               //System.out.println(" this is a OLD one");
               //check if ssn exists in the collection already
               if (id.getTypeCd() != null && id.getAssigningAuthorityCd() != null &&
                   id.getTypeCd().equals("SS") &&
                   id.getAssigningAuthorityCd().equals("SSA")) {
                  //System.out.println(" found SSN");
                  isThereSSN = true;
                  id.setRootExtensionTxt(patientSSN);
                  id.setAsOfDate_s(patientSSNAsOfDate);
                  id.setTypeDescTxt("Social Security Number");
                  id.setAssigningAuthorityDescTxt("Social Security Administration");
                  id.setItNew(false);
                  id.setItDirty(true);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  id.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               }
               else if (id.getStatusCd() != null &&
                        id.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  id.setItNew(false);
                  id.setItDirty(true);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

               }
               else {
                  id.setItNew(false);
                  id.setItDelete(true);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
               }

            }
            id.setLastChgTime(new Timestamp(new Date().getTime()));
         }
         //don't have ssn in batch but do have one outside batch
         if (isThereSSN == false && patientSSN != null && !patientSSN.equals("")) {
            //System.out.println(" creating ssn with batch");
            EntityIdDT iddt = null;
            iddt = new EntityIdDT();
            maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
            iddt.setEntityIdSeq(maxSeqNbr);
            iddt.setAddTime(new Timestamp(new Date().getTime()));
            iddt.setLastChgTime(new Timestamp(new Date().getTime()));
            iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
            iddt.setStatusTime(new Timestamp(new Date().getTime()));
            iddt.setEntityUid(personUID);
            iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            iddt.setTypeCd("SS");
            iddt.setTypeDescTxt("Social Security Number");
            iddt.setAssigningAuthorityCd("SSA");
            iddt.setAssigningAuthorityDescTxt("Social Security Administration");
            iddt.setRootExtensionTxt(patientSSN);
            iddt.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                               patientSSNAsOfDate :
                               (String) request.getParameter("generalAsOfDate"));

            ids.add(iddt);
         }
      } //don't have any batch id's but do have a social security number
      else if (patientSSN != null && !patientSSN.equals("")) {
         //System.out.println(" creating ssn without batch");
         EntityIdDT iddt = null;
         iddt = new EntityIdDT();
         iddt.setEntityIdSeq(new Integer(0));
         iddt.setAddTime(new Timestamp(new Date().getTime()));
         iddt.setLastChgTime(new Timestamp(new Date().getTime()));
         iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
         iddt.setStatusTime(new Timestamp(new Date().getTime()));
         iddt.setEntityUid(personUID);
         iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         iddt.setTypeCd("SS");
         iddt.setTypeDescTxt("Social Security Number");
         iddt.setAssigningAuthorityCd("SSA");
         iddt.setAssigningAuthorityDescTxt("Social Security Administration");
         iddt.setRootExtensionTxt(patientSSN);
         iddt.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                            patientSSNAsOfDate :
                            (String) request.getParameter("generalAsOfDate"));

         ArrayList<Object> idList = new ArrayList<Object> ();
         idList.add(iddt);
         personVO.setTheEntityIdDTCollection(idList);
      }

   }

	public static void setAssigningAuthorityforIds(Collection<Object> entityIds, String codesetNm) {
		if (entityIds == null)
			return;
		Iterator<Object> ite = entityIds.iterator();
		while (ite.hasNext()) {
			EntityIdDT idDT = (EntityIdDT)ite.next();
			if (!idDT.getRecordStatusCd().equals(
					NEDSSConstants.RECORD_STATUS_INACTIVE)
					&& idDT.getAssigningAuthorityCd() != null
					&& (idDT.getAssigningAuthorityDescTxt() == null || idDT
							.getAssigningAuthorityIdType() == null)) {
				try {
					Coded coded = new Coded();
					coded.setCode(idDT.getAssigningAuthorityCd());
					coded.setCodesetName(codesetNm);
					coded.setCodesetTableName(DataTables.CODE_VALUE_GENERAL);
					NotificationSRTCodeLookupTranslationDAOImpl lookupDAO = new NotificationSRTCodeLookupTranslationDAOImpl();
					lookupDAO.retrieveSRTCodeInfo(coded);
					if (coded.getCode() != null
							&& coded.getCodeDescription() != null
							&& coded.getCodeSystemCd() != null) {
						
						//Fatima: added this if statement otherwise the description for Assigning Authority = Other will be always Other
						//Instead of the one entered from Provider/Person screen. This is related to ND-23426
						if(idDT.getAssigningAuthorityDescTxt()==null || idDT.getAssigningAuthorityDescTxt().isEmpty())
							idDT.setAssigningAuthorityDescTxt(coded.getCodeDescription());
						idDT.setAssigningAuthorityIdType(coded
								.getCodeSystemCd());
					}
				} catch (NEDSSSystemException ex) {
					String errorMessage = "The assigning authority "
							+ idDT.getAssigningAuthorityCd()
							+ " does not exists in the system for codesetNm = "  + codesetNm ;
					logger.debug(ex.getMessage() + errorMessage);
				}
			}
		}
	}

   /**
    * This method is used by the Person-Add Basic page.  It
    * set the value of the Ethnicity chosen, to the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public static void setEthnicityForCreate(PersonVO personVO,
                                            HttpServletRequest request) {

      logger.info("Inside setBasicEthnicity");
      Long personUID = personVO.getThePersonDT().getPersonUid();
      String[] arrEthnicity = request.getParameterValues("ethnicitySelected");
      String ethnicityCategory = personVO.getThePersonDT().getEthnicGroupInd();
      logger.info("Value of ethnicityGroupInd: " + ethnicityCategory);

      if (personVO.getThePersonEthnicGroupDTCollection() == null) { // null if this is new

         ArrayList<Object> ethnicityList = new ArrayList<Object> ();

         if (arrEthnicity != null) {

            for (int i = 0, len = arrEthnicity.length; i < len; i++) {

               String strVal = arrEthnicity[i];
               PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
               ethnicDT.setItNew(true);
               ethnicDT.setItDirty(false);
               ethnicDT.setPersonUid(personUID);
               ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               ethnicDT.setEthnicGroupCd(strVal.trim());
               ethnicityList.add(ethnicDT);
            }
         } //selected the main category but not the root
         else if (ethnicityList == null && ethnicityCategory != null) {

            PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
            ethnicDT.setItNew(true);
            ethnicDT.setItDirty(false);
            ethnicDT.setPersonUid(personUID);
            ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            ethnicDT.setEthnicGroupCd("U");
            ethnicityList.add(ethnicDT);
         }

         String sEthnicGroupDescTxt = request.getParameter("ethnicGroupDescTxt");

         if (sEthnicGroupDescTxt != null &&
             ! (sEthnicGroupDescTxt.trim().equals(""))) {

            PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
            ethnicDT.setItNew(true);
            ethnicDT.setItDelete(false);
            ethnicDT.setItDirty(false);
            ethnicDT.setPersonUid(personUID);
            ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            ethnicDT.setEthnicGroupCd("O");
            ethnicDT.setEthnicGroupDescTxt(sEthnicGroupDescTxt);
            ethnicityList.add(ethnicDT);
         }

         personVO.setThePersonEthnicGroupDTCollection(ethnicityList);
      }
      else { // we have previously stored ethnicities

        Iterator<Object>  iter = personVO.getThePersonEthnicGroupDTCollection().
                         iterator();

         if (iter != null) {

            HashMap<Object,Object> hm = new HashMap<Object,Object>();

            while (iter.hasNext()) {

               PersonEthnicGroupDT ethnicDT = (PersonEthnicGroupDT) iter.next();
               ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
               ethnicDT.setItDelete(true);

               //put them in a hashmap
               hm.put(ethnicDT.getEthnicGroupCd(), ethnicDT);
            }

            if (arrEthnicity != null) {

               for (int i = 0, len = arrEthnicity.length; i < len; i++) {

                  String strVal = arrEthnicity[i];
                  PersonEthnicGroupDT ethnicDT = (PersonEthnicGroupDT) hm.get(
                      strVal.trim());

                  if (ethnicDT != null) { //true if exists already in collection
                     ethnicDT.setRecordStatusCd(NEDSSConstants.
                                                RECORD_STATUS_ACTIVE);
                     ethnicDT.setItNew(false);
                     ethnicDT.setItDirty(true);
                     ethnicDT.setItDelete(false);
                  } //false if doesn't exist in collection, a new one
                  else {

                     PersonEthnicGroupDT newEthnicDT = new PersonEthnicGroupDT();
                     newEthnicDT.setItNew(true);
                     newEthnicDT.setItDirty(false);
                     newEthnicDT.setPersonUid(personUID);
                     newEthnicDT.setRecordStatusCd(NEDSSConstants.
                         RECORD_STATUS_ACTIVE);
                     newEthnicDT.setEthnicGroupCd(strVal.trim());
                     personVO.getThePersonEthnicGroupDTCollection().add(
                         newEthnicDT);
                  }
               }
            }

            //other ethnicity goes away I think
         }
      }
   } //end

   /**
    * This method is used by the Person-Add Basic page.  It
    * set the value of the race chosen, to the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public static void setRaceForCreate(PersonVO personVO,
                                       HttpServletRequest request) {

      Long personUID = personVO.getThePersonDT().getPersonUid();
      ArrayList<Object> raceList = new ArrayList<Object> ();
      CachedDropDownValues cachedDDV = new CachedDropDownValues();

      if (personVO.getThePersonRaceDTCollection() == null) { // null if this is new

         String sUnknownRace = request.getParameter("unknownRace");

         if (sUnknownRace != null) {

            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
            raceDT.setRaceCd(NEDSSConstants.UNKNOWN);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setRaceDescTxt("Unknown");
            raceDT.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("generalAsOfDate"));
            raceList.add(raceDT);
         }

         String americanIndianCategory = request.getParameter(NEDSSConstants.
             AMERICAN_INDIAN_OR_ALASKAN_NATIVE);

         if (americanIndianCategory != null) {

            logger.info("Selected American Indian");
            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setRaceCategoryCd(NEDSSConstants.
                                     AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
            raceDT.setRaceCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("generalAsOfDate"));
            raceList.add(raceDT);
         }

         String whiteCategory = request.getParameter(NEDSSConstants.WHITE);

         if (whiteCategory != null) {

            logger.info("Selected White");
            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
            raceDT.setRaceCd(NEDSSConstants.WHITE);
            raceDT.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("generalAsOfDate"));
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceList.add(raceDT);
         }

         String africanCategory = request.getParameter(NEDSSConstants.
             AFRICAN_AMERICAN);

         if (africanCategory != null) {

            logger.info("Selected African American");
            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
            raceDT.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("generalAsOfDate"));
            raceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceList.add(raceDT);
         }

         String asianCategory = request.getParameter(NEDSSConstants.ASIAN);

         if (asianCategory != null) {

            logger.info("Selected Asian");
            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
            raceDT.setRaceCd(NEDSSConstants.ASIAN);
            raceDT.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("generalAsOfDate"));
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceList.add(raceDT);
         }

         String hawaiianCategory = request.getParameter(NEDSSConstants.
             NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);

         if (hawaiianCategory != null) {
            logger.info("Selected Hawiian");
            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setRaceCategoryCd(NEDSSConstants.
                                     NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
            raceDT.setRaceCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("generalAsOfDate"));
            raceList.add(raceDT);
         }

         String sOtherRaceDescTxt = request.getParameter("raceDescTxt");
         String sOtherRace = request.getParameter("otherRaceCd");

         if (sOtherRace != null) {

            logger.info("Selected Other");
            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
            raceDT.setRaceCd(NEDSSConstants.OTHER_RACE);
            raceDT.setRaceDescTxt(sOtherRaceDescTxt);
            raceDT.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("generalAsOfDate"));
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

            raceList.add(raceDT);
         }

         // prepare the VO
         personVO.setThePersonRaceDTCollection(raceList);
      } // end of the new one
   }

   public static PersonVO updatePatientFlags(PersonVO pvo) {
      pvo.setItDirty(false);
      pvo.setItNew(false);
      pvo.getThePersonDT().setItDirty(false);
      pvo.getThePersonDT().setItNew(false);
      if (pvo.getTheEntityIdDTCollection() != null) {
         Collection<Object>  col = new ArrayList<Object> ();
        Iterator<Object>  ite = pvo.getTheEntityIdDTCollection().iterator();
         while (ite.hasNext()) {
            EntityIdDT eiDT = (EntityIdDT) ite.next();
            eiDT.setItDirty(false);
            eiDT.setItNew(false);
            col.add(eiDT);
         }
         pvo.setTheEntityIdDTCollection(col);
      }
      if (pvo.getThePersonNameDTCollection() != null) {
         Collection<Object>  col = new ArrayList<Object> ();
        Iterator<Object>  ite = pvo.getThePersonNameDTCollection().iterator();
         while (ite.hasNext()) {
            PersonNameDT pnDT = (PersonNameDT) ite.next();
            pnDT.setItDirty(false);
            pnDT.setItNew(false);
            col.add(pnDT);
         }
         pvo.setThePersonNameDTCollection(col);
      }
      if (pvo.getTheEntityLocatorParticipationDTCollection() != null) {
         Collection<Object>  col = new ArrayList<Object> ();
        Iterator<Object>  ite = pvo.getTheEntityLocatorParticipationDTCollection().
                        iterator();
         while (ite.hasNext()) {
            EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT)
                                                 ite.next();
            elpDT.setItDirty(false);
            elpDT.setItNew(false);
            if (elpDT.getTheLocatorDT() != null) {
               elpDT.getTheLocatorDT().setItDirty(false);
               elpDT.getTheLocatorDT().setItNew(false);
            }
            if (elpDT.getThePhysicalLocatorDT() != null) {
               elpDT.setItDirty(false);
               elpDT.setItNew(false);
            }
            col.add(elpDT);
         }
         pvo.setTheEntityLocatorParticipationDTCollection(col);
      }
      if (pvo.getThePersonRaceDTCollection() != null) {
         Collection<Object>  col = new ArrayList<Object> ();
        Iterator<Object>  ite = pvo.getThePersonRaceDTCollection().iterator();
         while (ite.hasNext()) {
            PersonRaceDT prDT = (PersonRaceDT) ite.next();
            prDT.setItDirty(false);
            prDT.setItNew(false);
            col.add(prDT);
         }
         pvo.setThePersonRaceDTCollection(col);
      }
      if (pvo.getThePersonEthnicGroupDTCollection() != null) {
         Collection<Object>  col = new ArrayList<Object> ();
        Iterator<Object>  ite = pvo.getThePersonEthnicGroupDTCollection().iterator();
         while (ite.hasNext()) {
            PersonEthnicGroupDT pegDT = (PersonEthnicGroupDT) ite.next();
            pegDT.setItDirty(false);
            pegDT.setItNew(false);
            col.add(pegDT);
         }
         pvo.setThePersonEthnicGroupDTCollection(col);
      }
      return pvo;
   }

   public static void setNames(PersonVO personVO, HttpServletRequest request) {

      //Collection<Object>  names = personVO.getThePersonNameDTCollection();
      Long personUID = personVO.getThePersonDT().getPersonUid();
      //HttpSession session = request.getSession();

      logger.info(
          "Setting Names because currentTask starts with AddPatientBasic");
      String lastNm = request.getParameter("patient.lastNm");
      String firstNm = request.getParameter("patient.firstNm");
      String middleNm = request.getParameter(
          "patient.middleNm");
      String suffix = request.getParameter("patient.nmSuffix");
      String asOfDate = request.getParameter(
          "person.asOfDate_s");
      if ( (lastNm != null && !lastNm.trim().equals("")) ||
          (firstNm != null && !firstNm.trim().equals("")) ||
          (middleNm != null && !middleNm.trim().equals("")) ||
          (suffix != null && !suffix.trim().equals(""))) {
         PersonNameDT pdt = new PersonNameDT();
         pdt.setItNew(true);
         pdt.setItDirty(false);
         pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
         pdt.setPersonNameSeq(new Integer(1));
         pdt.setStatusTime(new Timestamp(new Date().getTime()));
         pdt.setAddTime(new Timestamp(new Date().getTime()));
         pdt.setRecordStatusTime(new Timestamp(new Date().getTime()));
         pdt.setPersonUid(personUID);
         pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
         pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         pdt.setLastNm(lastNm);
         pdt.setFirstNm(firstNm);
         pdt.setMiddleNm(middleNm);
         if (asOfDate == null) {
            pdt.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                              request.getParameter(
                "patient.asOfDate_s") :
                              (String) request.getParameter("generalAsOfDate"));
         }
         else {
            pdt.setAsOfDate_s(asOfDate);
         }
         pdt.setNmSuffix(suffix);
         Collection<Object>  pdts = new ArrayList<Object> ();
         pdts.add(pdt);
         personVO.setThePersonNameDTCollection(pdts);
      }
   }

   /**
    * This method will set the Ethnicity data onto
    * the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public static void setEthnicityForEdit(PersonVO personVO,
                                          HttpServletRequest request) {

      Long personUID = personVO.getThePersonDT().getPersonUid();
      String[] arrEthnicity = request.getParameterValues("ethnicitySelected");
      String ethnicityCategory = personVO.getThePersonDT().getEthnicGroupInd();

      if (personVO.getThePersonEthnicGroupDTCollection() == null) { // null if this is new

         ArrayList<Object> ethnicityList = new ArrayList<Object> ();

         if (arrEthnicity != null) {

            for (int i = 0, len = arrEthnicity.length; i < len; i++) {

               String strVal = arrEthnicity[i];
               if (strVal == null || strVal.equals("")) {
                  continue;
               }
               PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
               ethnicDT.setItNew(true);
               ethnicDT.setItDirty(false);
               ethnicDT.setPersonUid(personUID);
               ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               ethnicDT.setEthnicGroupCd(strVal.trim());
               ethnicityList.add(ethnicDT);
            }
         } //selected the main category but not the root
         else if (ethnicityList == null && ethnicityCategory != null) {

            PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
            ethnicDT.setItNew(true);
            ethnicDT.setItDirty(false);
            ethnicDT.setPersonUid(personUID);
            ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            ethnicDT.setEthnicGroupCd("U");
            ethnicityList.add(ethnicDT);
         }

         String sEthnicGroupDescTxt = request.getParameter("ethnicGroupDescTxt");

         if (sEthnicGroupDescTxt != null &&
             ! (sEthnicGroupDescTxt.trim().equals(""))) {

            PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
            ethnicDT.setItNew(true);
            ethnicDT.setItDelete(false);
            ethnicDT.setItDirty(false);
            ethnicDT.setPersonUid(personUID);
            ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            ethnicDT.setEthnicGroupCd("O");
            ethnicDT.setEthnicGroupDescTxt(sEthnicGroupDescTxt);
            ethnicityList.add(ethnicDT);
         }

         personVO.setThePersonEthnicGroupDTCollection(ethnicityList);
      }
      else { // we have previously stored ethnicities

        Iterator<Object>  iter = personVO.getThePersonEthnicGroupDTCollection().
                         iterator();

         if (iter != null) {

            HashMap<Object,Object> hm = new HashMap<Object,Object>();

            while (iter.hasNext()) {

               PersonEthnicGroupDT ethnicDT = (PersonEthnicGroupDT) iter.next();
               ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
               ethnicDT.setItDelete(true);

               //put them in a hashmap
               hm.put(ethnicDT.getEthnicGroupCd(), ethnicDT);
            }

            if (arrEthnicity != null) {

               for (int i = 0, len = arrEthnicity.length; i < len; i++) {

                  String strVal = arrEthnicity[i];
                  PersonEthnicGroupDT ethnicDT = (PersonEthnicGroupDT) hm.get(
                      strVal.trim());

                  if (ethnicDT != null) { //true if exists already in collection
                     ethnicDT.setRecordStatusCd(NEDSSConstants.
                                                RECORD_STATUS_ACTIVE);
                     ethnicDT.setItNew(false);
                     ethnicDT.setItDirty(true);
                     ethnicDT.setItDelete(false);
                  } //false if doesn't exist in collection, a new one
                  else {

                     if (strVal == null || strVal.equals("")) {
                        continue;
                     }
                     PersonEthnicGroupDT newEthnicDT = new PersonEthnicGroupDT();
                     newEthnicDT.setItNew(true);
                     newEthnicDT.setItDirty(false);
                     newEthnicDT.setPersonUid(personUID);
                     newEthnicDT.setRecordStatusCd(NEDSSConstants.
                         RECORD_STATUS_ACTIVE);
                     newEthnicDT.setEthnicGroupCd(strVal.trim());
                     personVO.getThePersonEthnicGroupDTCollection().add(
                         newEthnicDT);
                  }
               }
            }

            //other ethnicity goes away I think
         }
      }
   } //end

   /**
    * This method is used in setting the race(s) onto
    * the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public static void setRaceForEdit(PersonVO personVO,
                                     HttpServletRequest request) {

      Long personUID = personVO.getThePersonDT().getPersonUid();
      ArrayList<Object> raceList = new ArrayList<Object> ();
      CachedDropDownValues cachedDDV = new CachedDropDownValues();

      if (personVO.getThePersonRaceDTCollection() == null ||
          personVO.getThePersonRaceDTCollection().size() == 0) {
// null if this is new

         String sUnknownRace = request.getParameter("unknownRace");

         if (sUnknownRace != null) {

            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
            raceDT.setRaceCd(NEDSSConstants.UNKNOWN);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setRaceDescTxt("Unknown");
            raceDT.setPersonUid(personUID);
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);
            raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("raceAsOfDate"));

            raceList.add(raceDT);
         }

         String americanIndianCategory = request.getParameter(NEDSSConstants.
             AMERICAN_INDIAN_OR_ALASKAN_NATIVE);

         if (americanIndianCategory != null) {

            logger.info("ARRAY IS NULL FOR AM. IND");
            PersonRaceDT raceDT = new PersonRaceDT();

            raceDT.setRaceCategoryCd(NEDSSConstants.
                                     AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
            raceDT.setRaceCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("raceAsOfDate"));

            raceDT.setPersonUid(personUID);
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);

            raceList.add(raceDT);
         }

         String whiteCategory = request.getParameter(NEDSSConstants.WHITE);

         if (whiteCategory != null) {

            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
            raceDT.setRaceCd(NEDSSConstants.WHITE);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("raceAsOfDate"));

            raceDT.setPersonUid(personUID);
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);

            raceList.add(raceDT);
         }

         String africanCategory = request.getParameter(NEDSSConstants.
             AFRICAN_AMERICAN);

         if (africanCategory != null) {

            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
            raceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("raceAsOfDate"));

            raceDT.setPersonUid(personUID);
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);

            raceList.add(raceDT);
         }

         String asianCategory = request.getParameter(NEDSSConstants.ASIAN);

         if (asianCategory != null) {

            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
            raceDT.setRaceCd(NEDSSConstants.ASIAN);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("raceAsOfDate"));

            raceDT.setPersonUid(personUID);
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);

            raceList.add(raceDT);
         }

         String hawaiianCategory = request.getParameter(NEDSSConstants.
             NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);

         if (hawaiianCategory != null) {

            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setRaceCategoryCd(NEDSSConstants.
                                     NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
            raceDT.setRaceCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("raceAsOfDate"));

            raceDT.setPersonUid(personUID);
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);

            raceList.add(raceDT);
         }

         String sOtherRaceDescTxt = request.getParameter("raceDescTxt");
         logger.info("Other txt: " + sOtherRaceDescTxt);
         String sOtherRace = request.getParameter("otherRaceCd");

         if (sOtherRace != null) {

            PersonRaceDT raceDT = new PersonRaceDT();
            raceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
            raceDT.setRaceCd(NEDSSConstants.OTHER_RACE);
            raceDT.setRaceDescTxt(sOtherRaceDescTxt);
            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            raceDT.setRaceDescTxt("Other");
            if (sOtherRaceDescTxt != null) {
               raceDT.setRaceDescTxt(sOtherRaceDescTxt);
            }
            raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                 "" :
                                 (String) request.getParameter("raceAsOfDate"));

            raceDT.setPersonUid(personUID);
            raceDT.setItNew(true);
            raceDT.setItDelete(false);
            raceDT.setItDirty(false);

            raceList.add(raceDT);
         }

         // prepare the VO
         personVO.setThePersonRaceDTCollection(raceList);
      } // end of the new one
      //else we have old ones !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      else {

        Iterator<Object>  iter = personVO.getThePersonRaceDTCollection().iterator();

         if (iter != null) {

            HashMap<Object,Object> hm = new HashMap<Object,Object>();

            while (iter.hasNext()) {

               PersonRaceDT raceDT = (PersonRaceDT) iter.next();
               raceDT.setItDelete(true);
               //raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

               //put them in a hashmap
               hm.put(raceDT.getRaceCd(), raceDT);
            }

            //  UNKNOWN RACE
            String sUnknownRace = request.getParameter("unknownRace");

            if (sUnknownRace != null && sUnknownRace.equals("y")) {

               String strVal = NEDSSConstants.UNKNOWN;
               PersonRaceDT raceDT = (PersonRaceDT) hm.get(strVal.trim());

               if (raceDT != null) { //true if exists already in collection
                  raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  raceDT.setPersonUid(personUID);
                  raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                       "" :
                                       (String) request.getParameter("raceAsOfDate"));

                  raceDT.setItNew(false);
                  raceDT.setItDirty(true);
                  raceDT.setItDelete(false);
                  raceDT.setPersonUid(personUID);

               } //false if doesn't exist in collection, a new one
               else {

                  PersonRaceDT newRaceDT = new PersonRaceDT();
                  newRaceDT.setItNew(true);
                  newRaceDT.setItDirty(false);
                  newRaceDT.setPersonUid(personUID);
                  newRaceDT.setRecordStatusCd(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
                  newRaceDT.setRaceCd(strVal.trim());
                  newRaceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
                  newRaceDT.setRaceDescTxt("Unknown");
                  newRaceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                          "" :
                                          (String) request.getParameter("raceAsOfDate"));

                  personVO.getThePersonRaceDTCollection().add(newRaceDT);
               }
            }

            //  AMERICAN INDIAN

            String americanIndianCategory = request.getParameter(NEDSSConstants.
                AMERICAN_INDIAN_OR_ALASKAN_NATIVE);

            if (americanIndianCategory != null &&
                !americanIndianCategory.equals("")) {

               PersonRaceDT raceDT = (PersonRaceDT) hm.get(NEDSSConstants.
                   AMERICAN_INDIAN_OR_ALASKAN_NATIVE);

               if (raceDT != null) { //true if exists already in collection
                  raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  raceDT.setItNew(false);
                  raceDT.setItDirty(true);
                  raceDT.setItDelete(false);
                  raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                       "" :
                                       (String) request.getParameter("raceAsOfDate"));

               } //false if doesn't exist in collection, a new one
               else {
                  PersonRaceDT newRaceDT = new PersonRaceDT();
                  newRaceDT.setItNew(true);
                  newRaceDT.setItDirty(false);
                  newRaceDT.setPersonUid(personUID);
                  newRaceDT.setRecordStatusCd(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
                  newRaceDT.setRaceCd(NEDSSConstants.
                                      AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
                  newRaceDT.setRaceCategoryCd(NEDSSConstants.
                                              AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
                  newRaceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                          "" :
                                          (String) request.getParameter("raceAsOfDate"));

                  personVO.getThePersonRaceDTCollection().add(newRaceDT);
               }
            }

            //  WHITE

            String whiteCategory = request.getParameter(NEDSSConstants.WHITE);

            if (whiteCategory != null && !whiteCategory.equals("")) {

               PersonRaceDT raceDT = (PersonRaceDT) hm.get(NEDSSConstants.WHITE);

               if (raceDT != null) { //true if exists already in collection
                  raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  raceDT.setItNew(false);
                  raceDT.setItDirty(true);
                  raceDT.setItDelete(false);
                  raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                       "" :
                                       (String) request.getParameter("raceAsOfDate"));

               } //false if doesn't exist in collection, a new one
               else {

                  PersonRaceDT newRaceDT = new PersonRaceDT();
                  newRaceDT.setItNew(true);
                  newRaceDT.setItDirty(false);
                  newRaceDT.setPersonUid(personUID);
                  newRaceDT.setRecordStatusCd(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
                  newRaceDT.setRaceCd(NEDSSConstants.WHITE);
                  newRaceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
                  newRaceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                          "" :
                                          (String) request.getParameter("raceAsOfDate"));

                  personVO.getThePersonRaceDTCollection().add(newRaceDT);
               }
            }

            //  AFRICAN AMERICAN

            String africanCategory = request.getParameter(NEDSSConstants.
                AFRICAN_AMERICAN);

            if (africanCategory != null && !africanCategory.equals("")) {

               PersonRaceDT raceDT = (PersonRaceDT) hm.get(NEDSSConstants.
                   AFRICAN_AMERICAN);

               if (raceDT != null) { //true if exists already in collection
                  raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  raceDT.setItNew(false);
                  raceDT.setItDirty(true);
                  raceDT.setItDelete(false);
                  raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                       "" :
                                       (String) request.getParameter("raceAsOfDate"));

               } //false if doesn't exist in collection, a new one
               else {

                  PersonRaceDT newRaceDT = new PersonRaceDT();
                  newRaceDT.setItNew(true);
                  newRaceDT.setItDirty(false);
                  newRaceDT.setPersonUid(personUID);
                  newRaceDT.setRecordStatusCd(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
                  newRaceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
                  newRaceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
                  newRaceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                          "" :
                                          (String) request.getParameter("raceAsOfDate"));

                  personVO.getThePersonRaceDTCollection().add(newRaceDT);
               }
            }

            //  ASIAN

            String asianCategory = request.getParameter(NEDSSConstants.ASIAN);

            if (asianCategory != null && !asianCategory.equals("")) {

               PersonRaceDT raceDT = (PersonRaceDT) hm.get(NEDSSConstants.ASIAN);

               if (raceDT != null) { //true if exists already in collection
                  raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  raceDT.setItNew(false);
                  raceDT.setItDirty(true);
                  raceDT.setItDelete(false);
                  raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                       "" :
                                       (String) request.getParameter("raceAsOfDate"));

               } //false if doesn't exist in collection, a new one
               else {

                  PersonRaceDT newRaceDT = new PersonRaceDT();
                  newRaceDT.setItNew(true);
                  newRaceDT.setItDirty(false);
                  newRaceDT.setPersonUid(personUID);
                  newRaceDT.setRecordStatusCd(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
                  newRaceDT.setRaceCd(NEDSSConstants.ASIAN);
                  newRaceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
                  newRaceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                          "" :
                                          (String) request.getParameter("raceAsOfDate"));

                  personVO.getThePersonRaceDTCollection().add(newRaceDT);
               }
            }

            //  HAWAIIAN

            String hawaiianCategory = request.getParameter(NEDSSConstants.
                NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);

            if (hawaiianCategory != null && !hawaiianCategory.equals("")) {

               PersonRaceDT raceDT = (PersonRaceDT) hm.get(NEDSSConstants.
                   NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);

               if (raceDT != null) { //true if exists already in collection
                  raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  raceDT.setItNew(false);
                  raceDT.setItDirty(true);
                  raceDT.setItDelete(false);
                  raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                       "" :
                                       (String) request.getParameter("raceAsOfDate"));

               } //false if doesn't exist in collection, a new one
               else {

                  PersonRaceDT newRaceDT = new PersonRaceDT();
                  newRaceDT.setItNew(true);
                  newRaceDT.setItDirty(false);
                  newRaceDT.setPersonUid(personUID);
                  newRaceDT.setRecordStatusCd(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
                  newRaceDT.setRaceCd(NEDSSConstants.
                                      NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
                  newRaceDT.setRaceCategoryCd(NEDSSConstants.
                      NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
                  newRaceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                          "" :
                                          (String) request.getParameter("raceAsOfDate"));

                  personVO.getThePersonRaceDTCollection().add(newRaceDT);
               }
            }

            //  race OTHER
            String sOtherRaceDescTxt = request.getParameter("raceDescTxt");
            String sOtherRace = request.getParameter("otherRaceCd");

            if (sOtherRace != null && !sOtherRace.equals("")) {

               String strVal = NEDSSConstants.OTHER_RACE;
               PersonRaceDT raceDT = (PersonRaceDT) hm.get(strVal.trim());

               if (raceDT != null) { //true if exists already in collection
                  raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  raceDT.setRaceDescTxt(sOtherRaceDescTxt);
                  raceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                       "" :
                                       (String) request.getParameter("raceAsOfDate"));

                  raceDT.setItNew(false);
                  raceDT.setItDirty(true);
                  raceDT.setItDelete(false);
               } //false if doesn't exist in collection, a new one
               else {

                  PersonRaceDT newRaceDT = new PersonRaceDT();
                  newRaceDT.setItNew(true);
                  newRaceDT.setItDirty(false);
                  newRaceDT.setPersonUid(personUID);
                  newRaceDT.setRecordStatusCd(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
                  newRaceDT.setRaceCd(strVal.trim());
                  newRaceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
                  newRaceDT.setRaceDescTxt(sOtherRaceDescTxt);
                  newRaceDT.setRaceDescTxt("Other");
                  if (sOtherRaceDescTxt != null) {
                     newRaceDT.setRaceDescTxt(sOtherRaceDescTxt);
                  }
                  newRaceDT.setAsOfDate_s(request.getParameter("raceAsOfDate") == null ?
                                          "" :
                                          (String) request.getParameter("raceAsOfDate"));

                  personVO.getThePersonRaceDTCollection().add(newRaceDT);
               }
            }
         }
      }

   }

   public static void setPhysicalLocations(PersonVO personVO) {
      Long personUID = personVO.getThePersonDT().getPersonUid();
      Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();

      EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
      PhysicalLocatorDT plDT = new PhysicalLocatorDT();
      plDT.setItNew(true);
      plDT.setItDirty(false);
      plDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      elp.setItNew(true);
      elp.setItDirty(false);
      elp.setEntityUid(personUID);
      elp.setClassCd(NEDSSConstants.PHYSICAL);
      elp.setCd("A");
      elp.setUseCd("WP");

      elp.setThePhysicalLocatorDT(plDT);
      elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      arrELP.add(elp);

      personVO.setTheEntityLocatorParticipationDTCollection(arrELP);

   }

   public static void setNamesForEdit(PersonVO personVO,
                                      HttpServletRequest request) {

      Collection<Object>  names = personVO.getThePersonNameDTCollection();
      HttpSession session = request.getSession();
      Collection<Object>  pdts = new ArrayList<Object> ();
      String lastNm = request.getParameter("patient.lastNm");
      String firstNm = request.getParameter("patient.firstNm");
      String middleNm = request.getParameter(
          "patient.middleNm");
      String suffix = request.getParameter("patient.nmSuffix");
      String asOfDate = request.getParameter(
          "patient.asOfDate_s");

      if (names != null && names.size() > 0) {
        Iterator<Object>  ite = names.iterator();
         while (ite.hasNext()) {
            PersonNameDT pdt = (PersonNameDT) ite.next();
            if((lastNm == null || lastNm.trim().equals("")) &&
             (firstNm == null || firstNm.trim().equals("")) &&
             (middleNm == null || middleNm.trim().equals("")) &&
             (suffix == null || suffix.trim().equals("")))
            {
              pdt.setItNew(false);
              pdt.setItDirty(false);
              pdt.setItDelete(true);
            }
            else
            {
              pdt.setItNew(false);
              pdt.setItDirty(true);
              pdt.setLastNm(lastNm);
              pdt.setFirstNm(firstNm);
              pdt.setMiddleNm(middleNm);
              pdt.setNmSuffix(suffix);
              pdt.setStatusCd("A");
              pdt.setLastChgTime(new Timestamp(new Date().getTime()));
              pdt.setAsOfDate_s(asOfDate);
            }
            pdts.add(pdt);
         }
         personVO.setThePersonNameDTCollection(pdts);
      }
      else {
         setNames(personVO, request);
      }
   }

   public static void setEntityLocatorParticipationsForEdit(PersonVO personVO,
       HttpServletRequest request) {

      Long personUID = personVO.getThePersonDT().getPersonUid();
      Collection<Object>  arrELP = (ArrayList<Object> ) personVO.
                          getTheEntityLocatorParticipationDTCollection();

      String homePhone = request.getParameter("homePhoneNbrTxt");
      String workPhone = request.getParameter("workPhoneNbrTxt");
      String homeExt = request.getParameter("homeExtensionTxt");
      String workExt = request.getParameter("workExtensionTxt");

      String city = request.getParameter("cityDescTxt");
      String street1 = request.getParameter("streetAddr1");
      String street2 = request.getParameter("streetAddr2");
      String zip = request.getParameter("zipCd");
      String state = request.getParameter("stateCd");
      String county = request.getParameter("cntyCd");
      String country = request.getParameter("countryCd");
      if(country==null)
      {
        country = request.getParameter("hiddenCountryCd");
      }
      String defaultStateFlag = request.getParameter("defaultStateFlag");

      if (arrELP == null) {
            arrELP = new ArrayList<Object> ();
        }
      // home address
      EntityLocatorParticipationDT elp = getEntityLocatorParticipation(personVO,NEDSSConstants.POSTAL, NEDSSConstants.HOME);
      String addressAsOfDate = request.getParameter("addressAsOfDate");
      if(elp != null)
      {
        if((city == null || city.equals("")) &&
          (street1 == null || street1.equals("")) &&
          (street2 == null || street2.equals("")) &&
          (zip == null || zip.equals("")) &&
          (county == null || county.equals("")) &&
          (state == null || state.equals(""))
          )
        {
         //for edit when addressAsOfDate = null means blank address, then delete address
          elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
          elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
          elp.setItNew(false);
          elp.setItDirty(false);
          elp.setItDelete(true);
        }
        else
        {    //addressAsOfDate is not null

          elp.setCd(NEDSSConstants.HOME);
          elp.setClassCd(NEDSSConstants.POSTAL);
          elp.setUseCd(NEDSSConstants.HOME);
          elp.getThePostalLocatorDT().setCityDescTxt(city);
          elp.getThePostalLocatorDT().setStreetAddr1(street1);
          elp.getThePostalLocatorDT().setStreetAddr2(street2);
          elp.getThePostalLocatorDT().setZipCd(zip);
          elp.getThePostalLocatorDT().setStateCd(state);
          elp.getThePostalLocatorDT().setCntyCd(county);
          elp.getThePostalLocatorDT().setCntryCd(country);
          elp.getThePostalLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
          elp.setAsOfDate_s((String) request.getParameter("addressAsOfDate"));
          elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          elp.setItNew(false);
          elp.setItDirty(true);
          elp.getThePostalLocatorDT().setItNew(false);
          elp.getThePostalLocatorDT().setItDirty(true);

        }
      }
      else
      { //new address need to be created
        if ( (addressAsOfDate != null && !addressAsOfDate.equals("")) &&
             ((city != null && !city.equals("")) ||
            (street1 != null && !street1.equals("")) ||
            (street2 != null && !street2.equals("")) ||
            (zip != null && !zip.equals("")) ||
            (county != null && !county.equals("")) ||
            ((state != null && !state.equals("")) && (defaultStateFlag!=null && defaultStateFlag.equals("false")) ))
            ) {

           EntityLocatorParticipationDT elpAddress = new EntityLocatorParticipationDT();
           PostalLocatorDT pl = new PostalLocatorDT();


           elpAddress.setItNew(true);
           elpAddress.setItDirty(false);
           elpAddress.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
           elpAddress.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
           elpAddress.setEntityUid(personUID);
           elpAddress.setCd(NEDSSConstants.HOME);
           elpAddress.setClassCd(NEDSSConstants.POSTAL);
           elpAddress.setUseCd(NEDSSConstants.HOME);
           elpAddress.setAsOfDate_s(addressAsOfDate);

           pl.setCityDescTxt(city);
           pl.setStreetAddr1(street1);
           pl.setStreetAddr2(street2);
           pl.setZipCd(zip);
           pl.setStateCd(state);
           pl.setCntyCd(county);

           pl.setAddTime(new Timestamp(new Date().getTime()));
           pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
           pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
           pl.setItNew(true);
           pl.setItDirty(false);
           elpAddress.setThePostalLocatorDT(pl);
           arrELP.add(elpAddress);
       }
      }

      //home telephone
      elp = getEntityLocatorParticipation(personVO,NEDSSConstants.TELE, NEDSSConstants.HOME);
      String telephoneAsOfDate = request.getParameter("telephoneAsOfDate");
      if(elp != null)
      {

        if((homePhone == null || homePhone.equals("")) &&
           (homeExt == null || homeExt.equals("")))
        {
          //for edit when telephoneAsOfDate = null means blank telephone, then delete telephone
          elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
          elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
          elp.setItNew(false);
          elp.setItDirty(false);
          elp.setItDelete(true);
        }
        else
        {    //telephoneAsOfDate is not null

          elp.setCd(NEDSSConstants.PHONE);
          elp.setClassCd(NEDSSConstants.TELE);
          elp.setUseCd(NEDSSConstants.HOME);
          elp.getTheTeleLocatorDT().setPhoneNbrTxt(homePhone);
          elp.getTheTeleLocatorDT().setExtensionTxt(homeExt);
          elp.getTheTeleLocatorDT().setAddTime(new Timestamp(new Date().getTime()));
          elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
          elp.setAsOfDate_s(telephoneAsOfDate);
          elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          elp.setItNew(false);
          elp.setItDirty(true);
          elp.getTheTeleLocatorDT().setItNew(false);
          elp.getTheTeleLocatorDT().setItDirty(true);

        }
      }
      else
      { //new home telephone need to be created
        EntityLocatorParticipationDT elpTeleHome = new EntityLocatorParticipationDT();
        TeleLocatorDT teleDTHome = new TeleLocatorDT();
        if (telephoneAsOfDate != null && !telephoneAsOfDate.equals("") &&
            ((homePhone != null && !homePhone.equals("")) || (homeExt != null && !homeExt.equals("")))) {
           elpTeleHome.setItNew(true);
           elpTeleHome.setItDirty(false);
           elpTeleHome.setEntityUid(personUID);
           elpTeleHome.setClassCd(NEDSSConstants.TELE);
           elpTeleHome.setCd(NEDSSConstants.PHONE);
           elpTeleHome.setUseCd(NEDSSConstants.HOME);
           elpTeleHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
           elpTeleHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
           elpTeleHome.setAsOfDate_s(telephoneAsOfDate);

           teleDTHome.setPhoneNbrTxt(homePhone);
           teleDTHome.setExtensionTxt(homeExt);
           teleDTHome.setItNew(true);
           teleDTHome.setItDirty(false);
           teleDTHome.setAddTime(new Timestamp(new Date().getTime()));
           teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

           elpTeleHome.setTheTeleLocatorDT(teleDTHome);

           arrELP.add(elpTeleHome);
        }

      }
      //work telephone
      elp = getEntityLocatorParticipation(personVO,NEDSSConstants.TELE, NEDSSConstants.WORK_PHONE);
      String worktelephoneAsOfDate = request.getParameter("telephoneAsOfDate");
      if(elp != null)
      {
        if((workPhone == null || workPhone.equals("")) &&
           (workExt == null || workExt.equals("")))
        {
          //for edit when worktelephoneAsOfDate = null means blank telephone, then delete telephone
          elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
          elp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
          elp.setItNew(false);
          elp.setItDirty(false);
          elp.setItDelete(true);
        }
        else
        {    //worktelephoneAsOfDate is not null

          elp.setCd(NEDSSConstants.PHONE);
          elp.setClassCd(NEDSSConstants.TELE);
          elp.setUseCd(NEDSSConstants.WORK_PHONE);
          elp.getTheTeleLocatorDT().setPhoneNbrTxt(workPhone);
          elp.getTheTeleLocatorDT().setExtensionTxt(workExt);
          elp.getTheTeleLocatorDT().setAddTime(new Timestamp(new Date().getTime()));
          elp.getTheTeleLocatorDT().setLastChgTime(new Timestamp(new Date().getTime()));
          elp.setAsOfDate_s(worktelephoneAsOfDate);
          elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          elp.setItNew(false);
          elp.setItDirty(true);
          elp.getTheTeleLocatorDT().setItNew(false);
          elp.getTheTeleLocatorDT().setItDirty(true);

        }
      }
      else
      { //new home telephone need to be created
        EntityLocatorParticipationDT elpTeleWork = new EntityLocatorParticipationDT();
        TeleLocatorDT teleDTWork = new TeleLocatorDT();
        if (worktelephoneAsOfDate != null && !worktelephoneAsOfDate.equals("") &&
            ((workPhone != null && !workPhone.equals("")) || (workExt != null && !workExt.equals("")))) {
           elpTeleWork.setItNew(true);
           elpTeleWork.setItDirty(false);
           elpTeleWork.setEntityUid(personUID);
           elpTeleWork.setClassCd(NEDSSConstants.TELE);
           elpTeleWork.setCd(NEDSSConstants.PHONE);
           elpTeleWork.setUseCd(NEDSSConstants.WORK_PHONE);
           elpTeleWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
           elpTeleWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
           elpTeleWork.setAsOfDate_s(worktelephoneAsOfDate);

           teleDTWork.setPhoneNbrTxt(workPhone);
           teleDTWork.setExtensionTxt(workExt);
           teleDTWork.setItNew(true);
           teleDTWork.setItDirty(false);
           teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
           teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

           elpTeleWork.setTheTeleLocatorDT(teleDTWork);

           arrELP.add(elpTeleWork);
        }
      }

         personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
  }



   public static String getCountiesByState(String stateCd) {

      StringBuffer parsedCodes = new StringBuffer("");

      if (stateCd != null) {

         //SRTValues srtValues = new SRTValues();
         CachedDropDownValues srtValues = new CachedDropDownValues();
         TreeMap<?,?> treemap = null;
         treemap = srtValues.getCountyCodes(stateCd);

         if (treemap != null) {

            Set<?> set = treemap.keySet();
           Iterator<?>  itr = set.iterator();

            while (itr.hasNext()) {

               String key = (String) itr.next();
               String value = (String) treemap.get(key);
               parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).
                   append(value.trim()).append(NEDSSConstants.SRT_LINE);
            }
         }
      }

      return parsedCodes.toString();
   }

   /**
    * This method sets the RaceCD attribute
    *
    * @param request HttpServletRequest
    * @param findRace String
    */
   public static void setAttributeRaceCD(HttpServletRequest request,
                                         String findRace) {

      if (findRace.equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE)) {
         request.setAttribute("americanIndianController", "y");
      }
      else if (findRace.equals(NEDSSConstants.ASIAN)) {
         request.setAttribute("asianController", "y");
      }
      else if (findRace.equals(NEDSSConstants.AFRICAN_AMERICAN)) {
         request.setAttribute("africanAmericanController", "y");
      }
      else if (findRace.equals(NEDSSConstants.
                               NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER)) {
         request.setAttribute("hawaiianController", "y");
      }
      else if (findRace.equals(NEDSSConstants.WHITE)) {
         request.setAttribute("whiteController", "y");
      }
      else if (findRace.equals(NEDSSConstants.OTHER_RACE)) {
         request.setAttribute("OtherRace", "true");
      }
      else if (findRace.equals(NEDSSConstants.REFUSED_TO_ANSWER)) {
          request.setAttribute("refusedToAnswer", "true");
       }
      else if (findRace.equals("O")) {
         request.setAttribute("OtherRace", "true");

      }
      else if (findRace.equals(NEDSSConstants.UNKNOWN)) {
         request.setAttribute("unknownRace", "y");
      }
   }

   /**
    * This method is to convert the PersonVO object
    * into Request attributes
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    * @param action String
    * @param stateList ArrayList
    */
   public static void convertPersonToRequestObj(PersonVO personVO,
                                                HttpServletRequest request,
                                                String action,
                                                ArrayList<Object> stateList) throws NEDSSAppException{

      Long personUID = null;

      //ROLES
      convertRolesToRequestObj(personVO, request);
      //NAMES COLLECTION
       convertNamesCollectionToRequestObj(personVO, request);
      //IDS COLLECTION
        convertIdsCollectionToRequestObj(personVO, request, action);
      //RACE AND ETHNICITY
       convertRaceAndEthnicityToRequestObj(personVO, request);
      //ENTITY LOCATOR COLLECTION
        convertEntityLocatorCollectionToRequestObj(personVO, request, action, stateList);

      if (personVO != null && personVO.getThePersonDT() != null) {
         PersonDT person = personVO.getThePersonDT();

         //for the top bar
         String displayLocalID = getDisplayLocalID(person.getLocalId());
         request.setAttribute("personLocalID", displayLocalID);
         request.setAttribute("patientDisplayLocalId", displayLocalID);
         request.setAttribute("patientLocalId", person.getLocalId());
         request.setAttribute("defaultStateCd", getDefaultStateCd());
         request.setAttribute("defaultStateFlagEx", "true");

         //to persist this information for query string or input element
         //request.setAttribute("personUID", new String(person.getPersonUid().toString()));
         request.setAttribute("description", person.getDescription());
         request.setAttribute("defaultStateFlag", "true");
         request.setAttribute("CDM-spanishOrigin", person.getEthnicGroupInd());

         request.setAttribute("asOfDateAdmin",
                              StringUtils.formatDate(person.getAsOfDateAdmin()));
         request.setAttribute("asOfDateSex",
                              StringUtils.formatDate(person.getAsOfDateSex()));
         request.setAttribute("asOfDateMorbidity",
                              StringUtils.formatDate(person.
             getAsOfDateMorbidity()));
         request.setAttribute("asOfDateGeneral",
                              StringUtils.formatDate(person.getAsOfDateGeneral()));
         request.setAttribute("asOfDateEthnicity",
                              StringUtils.formatDate(person.
             getAsOfDateEthnicity()));


         request.setAttribute("birthTime",StringUtils.formatDate(person.getBirthTime()));
         if(DateUtil.lessThanSixDaysAge(person.getBirthTime()))
          {
              request.setAttribute("DOBValidation", "T");
          }
          else
          {
              request.setAttribute("DOBValidation", "F");
          }

         request.setAttribute("birthTimeCalc",
                              StringUtils.formatDate(person.getBirthTimeCalc()));

         if (action.endsWith("PatientFromEvent")) {
            request.setAttribute("ageReported", (String) person.getAgeReported());
            request.setAttribute("ageReportedUnitCd",
                                 person.getAgeReportedUnitCd());
            CachedDropDownValues cdv = new CachedDropDownValues();
            String ageUnits = cdv.getDescForCode("P_AGE_UNIT", person.getAgeReportedUnitCd());
            request.setAttribute("printAge", person.getAgeReported());
            request.setAttribute("printAgeUnits", ageUnits);

         }
         request.setAttribute("ageCalc", String.valueOf(person.getAgeCalc()));
         request.setAttribute("currSexCd", person.getCurrSexCd());
         request.setAttribute("birthGenderCd", person.getBirthGenderCd());
         request.setAttribute("multipleBirthInd", person.getMultipleBirthInd());
         request.setAttribute("birthOrderNbr",person.getBirthOrderNbr()==null?"":
                              String.valueOf(person.getBirthOrderNbr()));
         //set current age
         setCurrentAgeToRequest(request, person);
         request.setAttribute("ageReportedTime",
                              StringUtils.formatDate(person.getAgeReportedTime()));


         request.setAttribute("ageCategoryCd", person.getAgeCategoryCd());
         request.setAttribute("deceasedIndCd", person.getDeceasedIndCd());
         request.setAttribute("deceasedDate",
                              StringUtils.formatDate(person.getDeceasedTime()));
         request.setAttribute("maritalStatus", person.getMaritalStatusCd());
         request.setAttribute("maritalStatusDescTxt",
                              person.getMaritalStatusDescTxt());
         request.setAttribute("mothersMaidenNm", person.getMothersMaidenNm());
         request.setAttribute("adultsInHouseNbr",person.getAdultsInHouseNbr()==null?"":
                              String.valueOf(person.getAdultsInHouseNbr()));
         request.setAttribute("childrenInHouseNbr",person.getChildrenInHouseNbr()==null?"":
                              String.valueOf(person.getChildrenInHouseNbr()));
         request.setAttribute("educationLevelCd", person.getEducationLevelCd());
         request.setAttribute("educationLevelDescTxt",
                              person.getEducationLevelDescTxt());
         request.setAttribute("occupationCd", person.getOccupationCd());


         request.setAttribute("primLangCd", person.getPrimLangCd());
         request.setAttribute("primLangDescTxt", person.getPrimLangDescTxt());

      }
   }

     public static void convertEntityLocatorCollectionToRequestObj(PersonVO
       personVO, HttpServletRequest request, String action, ArrayList<Object> stateList) throws NEDSSAppException {
      StringBuffer sParsedAddresses = new StringBuffer("");
      StringBuffer sParsedTeles = new StringBuffer("");
      StringBuffer sParsedLocators = new StringBuffer("");
      Collection<Object>  addresses = personVO.
                             getTheEntityLocatorParticipationDTCollection();
      try {
      logger.info("About to create address parseString");
      if (addresses != null) {
         logger.info("Number of addresses: " + addresses.size());
        Iterator<Object>  iter = addresses.iterator();
         Timestamp mostRecentAddressAOD = null;
         Timestamp mostRecentHomeTeleAOD = null;
         Timestamp mostRecentWorkTeleAOD = null;
         while (iter.hasNext()) {

            EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                               iter.next();

            if (elp != null) {

               if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                   elp.getUseCd() != null &&
                   elp.getStatusCd() != null &&
                   elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                   elp.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE) &&
                   elp.getClassCd().equals("PST") && elp.getCd().equals("F") &&
                   elp.getUseCd().equals("BIR")) {

                  PostalLocatorDT postal = elp.getThePostalLocatorDT();
                  request.setAttribute("birthCity", postal.getCityDescTxt());
                  request.setAttribute("birthState", postal.getStateCd());
                  if (postal.getCntryCd() != null) {
                     request.setAttribute("birthCountry", postal.getCntryCd());
                  }
                  else if (postal.getCntryDescTxt() != null) {
                     String desc = postal.getCntryDescTxt();
                     String birthCtry = getCountryCodeByDesc(desc);
                     request.setAttribute("birthCountry", birthCtry);
                  }
                  request.setAttribute("birthCounty", postal.getCntyCd());
                  request.setAttribute("birthCountiesInState",
                                       PersonUtil.
                                       getCountiesByState(postal.getStateCd()));
                  request.setAttribute("birthPostalLocatorUid",
                                       postal.getPostalLocatorUid().toString());
                  request.setAttribute("birthLocatorUid",
                                       elp.getLocatorUid().toString());
                  request.setAttribute("birthEntityUid",
                                       elp.getEntityUid().toString());
                  request.setAttribute("birthStatusCd", elp.getStatusCd());
                  request.setAttribute("birthRecordStatusCd",
                                       elp.getRecordStatusCd());

               }
               else if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                        elp.getUseCd() != null &&
                        elp.getStatusCd() != null &&
                        elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                        elp.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE) &&
                        elp.getClassCd().equals("PST") &&
                        elp.getUseCd().equals("DTH")) {

                  PostalLocatorDT postal = elp.getThePostalLocatorDT();
                  request.setAttribute("deathCity", postal.getCityDescTxt());
                  request.setAttribute("deathState", postal.getStateCd());
                  if (postal.getCntryCd() != null) {
                     request.setAttribute("deathCountry", postal.getCntryCd());
                  }
                  else if (postal.getCntryDescTxt() != null) {
                     String desc = postal.getCntryDescTxt();
                     String deathCtry = getCountryCodeByDesc(desc);
                     request.setAttribute("deathCountry", deathCtry);
                  }
                  request.setAttribute("deathCounty", postal.getCntyCd());
                  request.setAttribute("deathCountiesInState",
                                       PersonUtil.
                                       getCountiesByState(postal.getStateCd()));
                  request.setAttribute("deathPostalLocatorUid",
                                       postal.getPostalLocatorUid().toString());
                  request.setAttribute("deathLocatorUid",
                                       elp.getLocatorUid().toString());
                  request.setAttribute("deathEntityUid",
                                       elp.getEntityUid().toString());
                  request.setAttribute("deathStatusCd", elp.getStatusCd());
                  request.setAttribute("deathRecordStatusCd",
                                       elp.getRecordStatusCd());

               } //   sets up the addresses parsed string
               else if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                        elp.getClassCd().equals("PST")) {
                  PostalLocatorDT postal = elp.getThePostalLocatorDT();
                  //this is for file summary tab
                  if (elp.getCd() != null && elp.getUseCd() != null &&
                      elp.getCd().equals(NEDSSConstants.HOME) &&
                      elp.getUseCd().equals(NEDSSConstants.HOME) &&
                      elp.getStatusCd() != null &&
                      elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                      elp.getRecordStatusCd() != null &&
                      elp.getRecordStatusCd().
                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                    if (mostRecentAddressAOD == null ||
                        (elp.getAsOfDate() != null &&
                     !elp.getAsOfDate().before(mostRecentAddressAOD))) {
                   mostRecentAddressAOD = elp.getAsOfDate();

                   request.setAttribute("fileSummaryAddress1",
                                        postal.getStreetAddr1());
                   request.setAttribute("fileSummaryAddress2",
                                        postal.getStreetAddr2());
                   request.setAttribute("fileSummaryCity",
                                        postal.getCityDescTxt());

                   request.setAttribute("fileSummaryState", postal.getStateCd());

                   request.setAttribute("fileSummaryZip", postal.getZipCd());
                   request.setAttribute("fileSummaryCounty", postal.getCntyCd());
                   request.setAttribute("fileSummaryCountry",
                                        postal.getCntryCd());
                   request.setAttribute("addressCounties",
                                        PersonUtil.
                                        getCountiesByState(postal.getStateCd()));
                   request.setAttribute("addressAsOfDate",
                                        StringUtils.
                                        formatDate(elp.getAsOfDate()));
                 }
                }

                  logger.info("Creating parsed string for addresses");
                  //create an array of states
                  if (action.startsWith("View") && postal.getStateCd() != null &&
                      stateList != null) {
                     stateList.add(postal.getStateCd());
                  }

                  sParsedAddresses.append("address[i].cd").append(
                      NEDSSConstants.BATCH_PART);

                  if (elp.getCd() != null) {
                     sParsedAddresses.append(elp.getCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].asOfDate_s").append(NEDSSConstants.BATCH_PART);

                  if (elp.getAsOfDate() != null) {
                     sParsedAddresses.append(StringUtils.formatDate(elp.
                         getAsOfDate()));

                  }

                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].useCd").append(NEDSSConstants.BATCH_PART);

                  if (elp.getUseCd() != null) {
                     sParsedAddresses.append(elp.getUseCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].statusCd").append(NEDSSConstants.BATCH_PART);

                  if (elp.getStatusCd() != null) {
                     sParsedAddresses.append(elp.getStatusCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].durationUnitCd").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getDurationUnitCd() != null) {
                     sParsedAddresses.append(elp.getDurationUnitCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].cdDescTxt").append(NEDSSConstants.BATCH_PART);

                  if (elp.getCdDescTxt() != null) {
                     sParsedAddresses.append(elp.getCdDescTxt());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.streetAddr1").append(
                      NEDSSConstants.BATCH_PART);

                  if (postal.getStreetAddr1() != null) {
                     sParsedAddresses.append(postal.getStreetAddr1());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.streetAddr2").append(
                      NEDSSConstants.BATCH_PART);

                  if (postal.getStreetAddr2() != null) {
                     sParsedAddresses.append(postal.getStreetAddr2());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.cityDescTxt").append(
                      NEDSSConstants.BATCH_PART);

                  if (postal.getCityDescTxt() != null) {
                     sParsedAddresses.append(postal.getCityDescTxt());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.stateCd").append(
                      NEDSSConstants.BATCH_PART);

                  if (postal.getStateCd() != null) {
                     sParsedAddresses.append(postal.getStateCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.zipCd").append(
                      NEDSSConstants.BATCH_PART);

                  if (postal.getZipCd() != null) {
                     sParsedAddresses.append(postal.getZipCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.cntyCd").append(
                      NEDSSConstants.BATCH_PART);

                  if (postal.getCntyCd() != null) {
                     sParsedAddresses.append(postal.getCntyCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.cntryCd").append(
                      NEDSSConstants.BATCH_PART);

                  if (postal.getCntryCd() != null) {
                     sParsedAddresses.append(postal.getCntryCd());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].fromTime_s").append(NEDSSConstants.BATCH_PART);

                  if (elp.getFromTime() != null) {
                     sParsedAddresses.append(StringUtils.formatDate(elp.
                         getFromTime()));

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].toTime_s").append(NEDSSConstants.BATCH_PART);

                  if (elp.getToTime() != null) {
                     sParsedAddresses.append(StringUtils.formatDate(elp.
                         getToTime()));

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].durationAmt").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getDurationAmt() != null) {
                     sParsedAddresses.append(elp.getDurationAmt());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].validTimeTxt").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getValidTimeTxt() != null) {
                     sParsedAddresses.append(elp.getValidTimeTxt());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].locatorDescTxt").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getLocatorDescTxt() != null) {
                     sParsedAddresses.append(elp.getLocatorDescTxt());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].thePostalLocatorDT_s.postalLocatorUid").
                      append(NEDSSConstants.BATCH_PART);

                  if (postal.getPostalLocatorUid() != null) {
                     sParsedAddresses.append(postal.getPostalLocatorUid().
                                             toString());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].entityUid").append(NEDSSConstants.BATCH_PART);

                  if (elp.getEntityUid() != null) {
                     sParsedAddresses.append(elp.getEntityUid().toString());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      "address[i].locatorUid").append(NEDSSConstants.BATCH_PART);

                  if (elp.getLocatorUid() != null) {
                     sParsedAddresses.append(elp.getLocatorUid().toString());

                  }
                  sParsedAddresses.append(NEDSSConstants.BATCH_SECT).append(
                      NEDSSConstants.BATCH_LINE);
               } //  create the telephone parsed string
               else if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                        elp.getClassCd().equals("TELE")) {

                  //for entity search
                  TeleLocatorDT tele = elp.getTheTeleLocatorDT();

                  //need this for the summary tab on view file
                  if (elp.getCd() != null && elp.getUseCd() != null &&
                      elp.getCd().equals(NEDSSConstants.PHONE) &&
                      elp.getUseCd().equals(NEDSSConstants.HOME) &&
                      elp.getStatusCd() != null &&
                      elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                      elp.getRecordStatusCd() != null &&
                      elp.getRecordStatusCd().
                      equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                  if (mostRecentHomeTeleAOD == null ||
                      (elp.getAsOfDate() != null &&
                      !elp.getAsOfDate().before(mostRecentHomeTeleAOD))) {
                    mostRecentHomeTeleAOD = elp.getAsOfDate();

                    //request.setAttribute("phone", tele.getPhoneNbrTxt());

                    request.setAttribute("fileSummaryHomePhone",
                                         (tele.getPhoneNbrTxt()!= null && tele.getPhoneNbrTxt().length()!=12)?"":tele.getPhoneNbrTxt());
                    request.setAttribute("fileSummaryHomePhoneExt",
                                         tele.getExtensionTxt());
                    request.setAttribute("telephoneAsOfDate",
                                         StringUtils.
                                         formatDate(elp.getAsOfDate()));
                   }
                  }
                  else if (elp.getCd() != null && elp.getUseCd() != null &&
                           elp.getCd().equals(NEDSSConstants.PHONE) &&
                           elp.getUseCd().equals(NEDSSConstants.WORK_PHONE) &&
                           elp.getStatusCd() != null &&
                           elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                           elp.getRecordStatusCd() != null &&
                           elp.
                           getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                    if (mostRecentWorkTeleAOD == null ||
                        (elp.getAsOfDate() != null &&
                        !elp.getAsOfDate().before(mostRecentWorkTeleAOD))) {
                      mostRecentWorkTeleAOD = elp.getAsOfDate();

                      request.setAttribute("fileSummaryWorkPhone",
                                           (tele.getPhoneNbrTxt()!= null && tele.getPhoneNbrTxt().length()!=12)?"":tele.getPhoneNbrTxt());
                      request.setAttribute("fileSummaryWorkPhoneExt",
                                           tele.getExtensionTxt());
                      request.setAttribute("telephoneAsOfDate",
                                           StringUtils.
                                           formatDate(elp.getAsOfDate()));
                    }
                  }

                  if (elp.getCd().trim().equals("L") && tele != null) {
                     request.setAttribute("entity_email", tele.getEmailAddress());
                     request.setAttribute("entity_telephone",
                                          tele.getPhoneNbrTxt());
                     request.setAttribute("entity_ext", tele.getExtensionTxt());
                  }

                  sParsedTeles.append("telephone[i].asOfDate_s").append(NEDSSConstants.
	                  BATCH_PART);
	
	              if (elp.getAsOfDate() != null) {
	                 sParsedTeles.append(StringUtils.formatDate(elp.getAsOfDate()));
	
	              }
                  
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].useCd").append(NEDSSConstants.BATCH_PART);

                  if (elp.getUseCd() != null) {
                     sParsedTeles.append(elp.getUseCd());

                  }
                  
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                		  "telephone[i].cd").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getCd() != null) {
                     sParsedTeles.append(elp.getCd());

                  }
                      
                      
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].statusCd").append(NEDSSConstants.BATCH_PART);

                  if (elp.getStatusCd() != null) {
                     sParsedTeles.append(elp.getStatusCd());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].durationUnitCd").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getDurationUnitCd() != null) {
                     sParsedTeles.append(elp.getDurationUnitCd());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].cdDescTxt").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getCdDescTxt() != null) {
                     sParsedTeles.append(elp.getCdDescTxt());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].theTeleLocatorDT_s.cntryCd").append(
                      NEDSSConstants.BATCH_PART);

                  if (tele.getCntryCd() != null) {
                     sParsedTeles.append(tele.getCntryCd());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].theTeleLocatorDT_s.phoneNbrTxt").append(
                      NEDSSConstants.BATCH_PART);

                  if (tele.getPhoneNbrTxt() != null) {
                     sParsedTeles.append(tele.getPhoneNbrTxt());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].theTeleLocatorDT_s.extensionTxt").append(
                      NEDSSConstants.BATCH_PART);

                  if (tele.getExtensionTxt() != null) {
                     sParsedTeles.append(tele.getExtensionTxt());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].theTeleLocatorDT_s.emailAddress").append(
                      NEDSSConstants.BATCH_PART);

                  if (tele.getEmailAddress() != null) {
                     sParsedTeles.append(tele.getEmailAddress());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].theTeleLocatorDT_s.urlAddress").append(
                      NEDSSConstants.BATCH_PART);

                  if (tele.getUrlAddress() != null) {
                     sParsedTeles.append(tele.getUrlAddress());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].fromTime_s").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getFromTime() != null) {
                     sParsedTeles.append(StringUtils.formatDate(elp.getFromTime()));

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].toTime_s").append(NEDSSConstants.BATCH_PART);

                  if (elp.getToTime() != null) {
                     sParsedTeles.append(StringUtils.formatDate(elp.getToTime()));

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].durationAmt").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getDurationAmt() != null) {
                     sParsedTeles.append(elp.getDurationAmt());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].validTimeTxt").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getValidTimeTxt() != null) {
                     sParsedTeles.append(elp.getValidTimeTxt());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].locatorDescTxt").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getLocatorDescTxt() != null) {
                     sParsedTeles.append(elp.getLocatorDescTxt());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].theTeleLocatorDT_s.teleLocatorUid").append(
                      NEDSSConstants.BATCH_PART);

                  if (tele.getTeleLocatorUid() != null) {
                     sParsedTeles.append(tele.getTeleLocatorUid().toString());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].locatorUid").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getLocatorUid() != null) {
                     sParsedTeles.append(elp.getLocatorUid().toString());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      "telephone[i].entityUid").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getEntityUid() != null) {
                     sParsedTeles.append(elp.getEntityUid().toString());

                  }
                  sParsedTeles.append(NEDSSConstants.BATCH_SECT).append(
                      NEDSSConstants.BATCH_LINE);
               }
               else if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                        elp.getClassCd().equals("PHYS")) {

                  PhysicalLocatorDT physical = elp.getThePhysicalLocatorDT();

                  sParsedLocators.append("physical[i].cd").append(
                      NEDSSConstants.BATCH_PART);

                  if (elp.getCd() != null) {
                     sParsedLocators.append(elp.getCd());

                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      "physical[i].useCd").append(NEDSSConstants.BATCH_PART);

                  if (elp.getUseCd() != null) {
                     sParsedLocators.append(elp.getUseCd());

                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      "physical[i].statusCd").append(NEDSSConstants.BATCH_PART);

                  if (elp.getStatusCd() != null) {
                     sParsedLocators.append(elp.getStatusCd());

                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      "physical[i].cdDescTxt").append(NEDSSConstants.BATCH_PART);

                  if (elp.getCdDescTxt() != null) {
                     sParsedLocators.append(elp.getCdDescTxt());

                     //sParsedLocators.append(NEDSSConstants.BATCH_SECT).append("physical[i].thePhysicalLocatorDT_s.name=not implemented");
                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      "physical[i].thePhysicalLocatorDT_s.locatorTxt").append(
                      NEDSSConstants.BATCH_PART);

                  if (physical.getLocatorTxt() != null) {
                     sParsedLocators.append(physical.getLocatorTxt());

                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      "physical[i].thePhysicalLocatorDT_s.physicalLocatorUid").
                      append(NEDSSConstants.BATCH_PART);

                  if (physical.getPhysicalLocatorUid() != null) {
                     sParsedLocators.append(physical.getPhysicalLocatorUid().
                                            toString());

                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      "physical[i].locatorUid").append(NEDSSConstants.
                      BATCH_PART);

                  if (elp.getLocatorUid() != null) {
                     sParsedLocators.append(elp.getLocatorUid().toString());

                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      "physical[i].entityUid").append(NEDSSConstants.BATCH_PART);

                  if (elp.getEntityUid() != null) {
                     sParsedLocators.append(elp.getEntityUid().toString());

                  }
                  sParsedLocators.append(NEDSSConstants.BATCH_SECT).append(
                      NEDSSConstants.BATCH_LINE);
               }
            }
         }

         request.setAttribute("addresses", sParsedAddresses.toString());
         request.setAttribute("parsedTelephoneString", sParsedTeles.toString());
         request.setAttribute("parsedLocatorsString", sParsedLocators.toString());
      }
      }catch(Exception ex) {
		   String error = "Exception while setting entity locator participation to request";
		   logger.error(error);
		   throw new NEDSSAppException (ex.getMessage()+ ": "+error, ex);
	}

   }

   public static void convertRaceAndEthnicityToRequestObj(PersonVO personVO,
       HttpServletRequest request) throws NEDSSAppException {
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
      //for view file summary tab
      StringBuffer fileSummaryRace = new StringBuffer("");
try {
      if (races != null) {

        Iterator<Object>  iter = races.iterator();

         while (iter.hasNext()) {

            PersonRaceDT race = (PersonRaceDT) iter.next();
            if (race != null) {
               //RACE AS OF DATE
               request.setAttribute("raceAsOfDate",
                                    StringUtils.formatDate(race.getAsOfDate()));
               if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) &&
                   race.getRaceCategoryCd().trim().
                   equals(NEDSSConstants.UNKNOWN)) {
                  request.setAttribute("unknownRace", HTMLEncoder.encodeHtml("y"));
                  //only get the as of date for level one race
                  if (race.getRaceCd().trim().equals(NEDSSConstants.UNKNOWN))
                     request.setAttribute("unknownRaceDate",
                                       StringUtils.formatDate(race.getAsOfDate()));

                  //for view file summary tab
                  if(!fileSummaryRace.toString().contains("Unknown"))
                  fileSummaryRace.append("Unknown, ");
               } // american indian
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) &&
                        race.getRaceCategoryCd().trim().
                        equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE)) {
                  sAmericanIndianController = "y";
                  sAmericanIndian.append(race.getRaceCd()).append(
                      NEDSSConstants.BATCH_LINE);
                  //only get the as of date for level one race
                  if (race.getRaceCd().trim().equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE))
                     request.setAttribute("americanIndianAsOfDate",
                                       StringUtils.formatDate(race.getAsOfDate()));

                  if (fileSummaryRace.toString().indexOf(
                      "American Indian or Alaska Native,") == -1) {
                     fileSummaryRace.append(
                         "American Indian or Alaska Native, ");
                  }

               }
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) &&
                        race.getRaceCategoryCd().trim().
                        equals(NEDSSConstants.WHITE)) {
                  sWhiteController = "y";
                  sWhite.append(race.getRaceCd()).append(NEDSSConstants.
                      BATCH_LINE);
                  //only get the as of date for level one race
                  if (race.getRaceCd().trim().equals(NEDSSConstants.WHITE))
                     request.setAttribute("whiteAsOfDate",
                                       StringUtils.formatDate(race.getAsOfDate()));
                  if (fileSummaryRace.toString().indexOf("White,") == -1) {
                     fileSummaryRace.append("White, ");
                  }

               }
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) &&
                        race.getRaceCategoryCd().trim().
                        equals(NEDSSConstants.AFRICAN_AMERICAN)) {
                  sAfricanAmericanController = "y";
                  sAfricanAmerican.append(race.getRaceCd()).append(
                      NEDSSConstants.BATCH_LINE);
                  //only get the as of date for level one race
                  if (race.getRaceCd().trim().equals(NEDSSConstants.AFRICAN_AMERICAN))
                     request.setAttribute("blackAfricanAmericanAsOfDate",
                                       StringUtils.formatDate(race.getAsOfDate()));

                  if (fileSummaryRace.toString().indexOf(
                      "Black or African American,") == -1) {
                     fileSummaryRace.append("Black or African American, ");
                  }

               }
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) &&
                        race.getRaceCategoryCd().trim().
                        equals(NEDSSConstants.ASIAN)) {
                  sAsianController = "y";
                  sAsian.append(race.getRaceCd()).append(NEDSSConstants.
                      BATCH_LINE);
                  //only get the as of date for level one race
                  if (race.getRaceCd().trim().equals(NEDSSConstants.ASIAN))
                     request.setAttribute("asianAsOfDate",
                                       StringUtils.formatDate(race.getAsOfDate()));
                  if (fileSummaryRace.toString().indexOf("Asian,") == -1) {
                     fileSummaryRace.append("Asian, ");
                  }

               }
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) &&
                        race.getRaceCategoryCd().trim().
                        equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER)) {
                  sHawaiianController = "y";
                  sHawaiian.append(race.getRaceCd()).append(NEDSSConstants.
                      BATCH_LINE);
                  //only get the as of date for level one race
                  if (race.getRaceCd().trim().equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER))
                     request.setAttribute("nativeHawaiianAsOfDate",
                                       StringUtils.formatDate(race.getAsOfDate()));

                  if (fileSummaryRace.toString().indexOf(
                      "Native Hawaiian or Other Pacific Islander,") == -1) {
                     fileSummaryRace.append(
                         "Native Hawaiian or Other Pacific Islander, ");
                  }

               }
              
                //  else if (race.getRecordStatusCd().trim().equals(
                 // NEDSSConstants.RECORD_STATUS_ACTIVE) &&
                   //    race.getRaceCategoryCd().trim().equals("O")) {
                // sOtherRace = "true";
               //  sOtherRaceDescText = race.getRaceDescTxt();
                 //for view file summary tab
               //  fileSummaryRace.append("Other, ");
                       //           }
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) &&
                        race.getRaceCategoryCd().trim().
                        equals(NEDSSConstants.OTHER_RACE)) {
                  sOtherRace = "true";
                  sOtherRaceDescText = race.getRaceDescTxt();
                  request.setAttribute("otherAsOfDate",
                                       StringUtils.formatDate(race.getAsOfDate()));
                  //for view file summary tab

                  fileSummaryRace.append("Other").append(", ");
               }
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                       RECORD_STATUS_ACTIVE) &&
                            race.getRaceCategoryCd().trim().
                            equals(NEDSSConstants.REFUSED_TO_ANSWER)) {
            	   if(!fileSummaryRace.toString().contains("Unknown"))
                       fileSummaryRace.append("Unknown").append(", ");
                   }
               else if (race.getRecordStatusCd().trim().equals(NEDSSConstants.
                       RECORD_STATUS_ACTIVE) &&
                            race.getRaceCategoryCd().trim().
                            equals(NEDSSConstants.NOT_ASKED)) {
            	   if(!fileSummaryRace.toString().contains("Unknown"))
                       fileSummaryRace.append("Unknown").append(", ");
                   }
            }
         }
         
         //Remove Null Flavor Race(Unknown) if the race also has a Known race
         String[] knownRaces={CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE, "RACE_CALCULATED"),CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.ASIAN, "RACE_CALCULATED"),CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.AFRICAN_AMERICAN, "RACE_CALCULATED"),CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER, "RACE_CALCULATED"),CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.WHITE, "RACE_CALCULATED"),CachedDropDowns.getCodeDescTxtForCd(NEDSSConstants.OTHER_RACE, "RACE_CALCULATED")};
         StringBuffer finalFileSummaryRace = new StringBuffer("");
         List<String> knownRacesList= new ArrayList<String>();
		 Collections.addAll(knownRacesList, knownRaces);	
		 Iterator<String> iter2=knownRacesList.iterator();
	  		while(iter2.hasNext()){
	  			String iteratorKnownRaceValue=(String) iter2.next();
	  			 if(fileSummaryRace.toString().contains(iteratorKnownRaceValue))
	  	         {
	  				if(fileSummaryRace.toString().contains("Unknown"))
		  	         {
	  					String tempFileSummaryRace="";
	  					tempFileSummaryRace=fileSummaryRace.toString().replace("Unknown, ", "");
	  					finalFileSummaryRace.append(tempFileSummaryRace);
	  					fileSummaryRace=finalFileSummaryRace;
		  	         }
	  	         }
	  		}
	  		
         request.setAttribute("americanIndian", HTMLEncoder.encodeHtml(sAmericanIndian.toString()));
         request.setAttribute("white", HTMLEncoder.encodeHtml(sWhite.toString()));
         request.setAttribute("africanAmerican", HTMLEncoder.encodeHtml(sAfricanAmerican.toString()));
         request.setAttribute("asian", HTMLEncoder.encodeHtml(sAsian.toString()));
         request.setAttribute("hawaiian", HTMLEncoder.encodeHtml(sHawaiian.toString()));
         request.setAttribute("americanIndianController",
        		 HTMLEncoder.encodeHtml(sAmericanIndianController));
         request.setAttribute("whiteController", HTMLEncoder.encodeHtml(sWhiteController));
         request.setAttribute("africanAmericanController",
        		 HTMLEncoder.encodeHtml(sAfricanAmericanController));
         request.setAttribute("asianController", HTMLEncoder.encodeHtml(sAsianController));
         request.setAttribute("hawaiianController", HTMLEncoder.encodeHtml(sHawaiianController));
         request.setAttribute("OtherRace", sOtherRace);
         request.setAttribute("OtherRaceDescText", HTMLEncoder.encodeHtml(sOtherRaceDescText));
         //for view file summary tab
         request.setAttribute("fileSummaryRaceCategory", fileSummaryRace);
      }

      // sets up the ethnicity parsed strings
      StringBuffer parsedEthnicity = new StringBuffer("");
      String parsedEthnicityController = "";
      String otherEthnic = "";
      String otherEthnicController = "";
      Collection<Object>  ethnicities = personVO.getThePersonEthnicGroupDTCollection();

      if (ethnicities != null) {

        Iterator<Object>  iter = ethnicities.iterator();

         if (iter != null) {

            while (iter.hasNext()) {

               PersonEthnicGroupDT ethnic = (PersonEthnicGroupDT) iter.next();
               if (ethnic.getRecordStatusCd().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) && ethnic.getEthnicGroupCd()!=null &&
                   ethnic.getEthnicGroupCd().equals("O")) {
                  otherEthnicController = "y";
                  otherEthnic = ethnic.getEthnicGroupDescTxt();

                  if (otherEthnic.equals("")) {
                     otherEthnic = "no desc put in";
                  }
               }
               else if (ethnic.getRecordStatusCd().equals(NEDSSConstants.
                   RECORD_STATUS_ACTIVE) && ethnic.getEthnicGroupCd()!=null) {
                  parsedEthnicityController = "2135-2";
                  parsedEthnicity.append(ethnic.getEthnicGroupCd()).append(
                      NEDSSConstants.BATCH_LINE);

               }
            }
         }

         request.setAttribute("parsedEthnicity", parsedEthnicity.toString());
         request.setAttribute("parsedEthnicityController",
                              parsedEthnicityController);
         request.setAttribute("otherEthnic", otherEthnic);
         request.setAttribute("otherEthnicController", otherEthnicController);
      }
		}catch(Exception ex) {
			   String error = "Exception while setting race and ethnicity to request";
			   logger.error(error);
			   throw new NEDSSAppException (ex.getMessage()+ ": "+error, ex);
		}

   }

    public static void convertIdsCollectionToRequestObj(PersonVO personVO,
       HttpServletRequest request, String action) throws NEDSSAppException{
      // create the entity id parsed string for the batch entry javascript
    	try {
      Collection<Object>  ids = personVO.getTheEntityIdDTCollection();
      logger.info("IDs: " + ids);

      if (ids != null) {

        Iterator<Object>  iter = ids.iterator();
         StringBuffer combinedIds = new StringBuffer("");
         String entity = null;
         //the struts naming is patient for person from event
         if (action.endsWith("PatientFromEvent")) {
            entity = "patient";
         }
         else {
            entity = "person";

         }
         while (iter.hasNext()) {

            EntityIdDT id = (EntityIdDT) iter.next();
            if (id != null) {
          
               if (id.getAssigningAuthorityCd() != null &&
                   id.getAssigningAuthorityCd().equals("SSA") && id.getTypeCd() != null &&
                   id.getTypeCd().equals("SS")
                   ) {

                  request.setAttribute("ssn", id.getRootExtensionTxt());
                  request.setAttribute("ssnAsOfDate",
                                       StringUtils.formatDate(id.getAsOfDate()));

               }

               // as of date
               combinedIds.append(entity).append(".entityIdDT_s[i].asOfDate_s").
                   append(NEDSSConstants.BATCH_PART);

               if (id.getAsOfDate() != null) {
                  combinedIds.append(StringUtils.formatDate(id.getAsOfDate()));

               }

               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].typeCd").append(NEDSSConstants.
                   BATCH_PART);

               if (id.getTypeCd() != null) {
                  combinedIds.append(id.getTypeCd());

               }

               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].typeDescTxt").append(NEDSSConstants.
                   BATCH_PART);

               if (id.getTypeDescTxt() != null) {
                  combinedIds.append(id.getTypeDescTxt());

               }
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].assigningAuthorityCd").append(
                   NEDSSConstants.BATCH_PART);

               if (id.getAssigningAuthorityCd() != null) {
                  combinedIds.append(id.getAssigningAuthorityCd());

               }
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].assigningAuthorityDescTxt").append(
                   NEDSSConstants.BATCH_PART);

               if (id.getAssigningAuthorityDescTxt() != null) {
                  combinedIds.append(id.getAssigningAuthorityDescTxt());

               }

               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].rootExtensionTxt").append(
                   NEDSSConstants.BATCH_PART);

               if (id.getRootExtensionTxt() != null && !id.getRootExtensionTxt().trim().equals("")) {
                  if ( (id.getAssigningAuthorityCd() != null) &&
                      (id.getAssigningAuthorityCd().equalsIgnoreCase("SSA"))) {
                     if ( (id.getRootExtensionTxt()).indexOf("-") != -1) {
                        combinedIds.append(id.getRootExtensionTxt());
                     }
                     else {
                        StringBuffer SSN = new StringBuffer(id.
                            getRootExtensionTxt());
                        if(SSN != null && SSN.length()>3)
                        SSN.insert(3, "-");
                        if(SSN != null && SSN.length()>6)
                        SSN.insert(6, "-");
                        combinedIds.append(SSN);
                     }
                  }
                  else {
                     combinedIds.append(id.getRootExtensionTxt());
                  }
               }

               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].validFromTime_s").append(
                   NEDSSConstants.BATCH_PART);

               if (id.getValidFromTime() != null) {
                  combinedIds.append(StringUtils.formatDate(id.getValidFromTime()));

               }
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].validToTime_s").append(
                   NEDSSConstants.BATCH_PART);

               if (id.getValidToTime() != null) {
                  combinedIds.append(StringUtils.formatDate(id.getValidToTime()));

               }
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].durationAmt").append(NEDSSConstants.
                   BATCH_PART);

               if (id.getDurationAmt() != null) {
                  combinedIds.append(id.getDurationAmt());

               }
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].durationUnitCd").append(
                   NEDSSConstants.BATCH_PART);

               if (id.getDurationUnitCd() != null) {
                  combinedIds.append(id.getDurationUnitCd());

               }
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].entityIdSeq").append(NEDSSConstants.
                   BATCH_PART);

               if (id.getEntityIdSeq() != null) {
                  combinedIds.append(id.getEntityIdSeq());

               }
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].entityUid").append(NEDSSConstants.
                   BATCH_PART);

               if (id.getEntityUid() != null) {
                  combinedIds.append(id.getEntityUid().toString());
               }
               //need to set ssn to status cd inactive
               combinedIds.append(NEDSSConstants.BATCH_SECT).append(entity).
                   append(".entityIdDT_s[i].statusCd").append(NEDSSConstants.
                   BATCH_PART);

               if (id.getAssigningAuthorityCd() != null &&
                   id.getAssigningAuthorityCd().equals("SSA") && id.getTypeCd() != null &&
                   id.getTypeCd().equals("SS")) {
                  combinedIds.append("I");
               }
               else {
                  combinedIds.append(id.getStatusCd());
                  ///////////////////////////////////////////

               }

               combinedIds.append(NEDSSConstants.BATCH_SECT).append(
                   NEDSSConstants.BATCH_LINE);
            }
         }

 
         request.setAttribute("ids", combinedIds.toString());

      }
    	}catch(Exception ex) {
    		   String error = "Exception while setting entity Ids to request";
    		   logger.error(error);
    		   throw new NEDSSAppException (ex.getMessage()+ ": "+error, ex);
    	}

   }

   public static void convertRolesToRequestObj(PersonVO personVO,
                                               HttpServletRequest request) {
      // set up the roles information
      Collection<Object>  roles = personVO.getTheRoleDTCollection();

      if (roles != null) {

         StringBuffer rolesBuffer = new StringBuffer("");
        Iterator<Object>  iter = roles.iterator();

         if (iter != null) {

            while (iter.hasNext()) {

               RoleDT roleDT = (RoleDT) iter.next();

               if (roleDT != null) /*&& roleDT.getRecordStatusCd()!=null && roleDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)&& roleDT.getCd()!=null*/ {
                  rolesBuffer.append(roleDT.getCd()).append(NEDSSConstants.
                      BATCH_LINE);
               }
            }
         }
         request.setAttribute("roleList", rolesBuffer.toString());
      }
   }

   public static void convertNamesCollectionToRequestObj(PersonVO personVO,
       HttpServletRequest request) throws NEDSSAppException{
      // create the person name parsed string for the batch entry javascript
      Collection<Object>  names = personVO.getThePersonNameDTCollection();
try {
      if (names != null) {
         logger.info("NAMES COLLECTION WAS NOT EMPTY");
        Iterator<Object>  iter = names.iterator();
         StringBuffer sNamesCombined = new StringBuffer("");
         Timestamp mostRecentNameAOD = null;

         while (iter.hasNext()) {

            PersonNameDT name = (PersonNameDT) iter.next();

            if (name != null) {

               // for personInfo
               if (name != null && name.getNmUseCd() != null &&
                   name.getNmUseCd().equals(NEDSSConstants.LEGAL) &&
                   name.getStatusCd() != null &&
                   name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                   name.getRecordStatusCd() != null &&
                   name.getRecordStatusCd().
                   equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                 if (mostRecentNameAOD == null ||
                     (name.getAsOfDate() != null &&
                     !name.getAsOfDate().before(mostRecentNameAOD))) {
                   mostRecentNameAOD = name.getAsOfDate();
                   request.setAttribute("entity_last_name", name.getLastNm());
                   request.setAttribute("entity_first_name", name.getFirstNm());
                   //for view file summary tab
                   request.setAttribute("legalLastName", name.getLastNm());
                   request.setAttribute("legalFirstName", name.getFirstNm());
                   request.setAttribute("legalMiddleName", name.getMiddleNm());
                   request.setAttribute("legalSuffixName", name.getNmSuffix());
                  String	strPName = ( (name.getFirstNm() == null) ? "" :
                      name.getFirstNm()) +" "+ ( (name.getMiddleNm() == null) ? "" :
                    	  name.getMiddleNm())+ " "+( (name.getLastNm() == null) ? "" :
                    		  name.getLastNm());
    			  if(null == strPName || strPName.equalsIgnoreCase("null")){
    				 strPName ="";
    			   }
    			  request.setAttribute("patientFullLegalName",strPName);
    			  request.setAttribute("patientSuffixName", ( (name.getNmSuffix() == null) ? " " : CachedDropDowns.getCodeDescTxtForCd(name.getNmSuffix(), "P_NM_SFX")));
                   request.setAttribute("patientName",
                                        ( (name.getFirstNm() == null) ? " " :
                                         name.getFirstNm()) + " " +
                                        ( (name.getLastNm() == null) ? " " :
                                         name.getLastNm()));
                   request.setAttribute("nameAsOfDate",
                                        StringUtils.formatDate(name.getAsOfDate()));
                 }
               }

               // need all the records

               sNamesCombined.append("person.personNameDT_s[i].asOfDate_s").
                   append(NEDSSConstants.BATCH_PART);

               if (name.getAsOfDate() != null) {
                  sNamesCombined.append(StringUtils.formatDate(name.getAsOfDate()));

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].nmUseCd").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getNmUseCd() != null) {
                  sNamesCombined.append(name.getNmUseCd());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].statusCd").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getStatusCd() != null) {
                  sNamesCombined.append(name.getStatusCd());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].nmPrefix").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getNmPrefix() != null) {
                  sNamesCombined.append(name.getNmPrefix());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].nmSuffix").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getNmSuffix() != null) {
                  sNamesCombined.append(name.getNmSuffix());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].nmDegree").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getNmDegree() != null) {
                  sNamesCombined.append(name.getNmDegree());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].durationUnitCd").append(
                   NEDSSConstants.BATCH_PART);

               if (name.getDurationUnitCd() != null) {
                  sNamesCombined.append(name.getDurationUnitCd());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].lastNm").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getLastNm() != null) {
                  sNamesCombined.append(name.getLastNm());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].lastNm2").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getLastNm2() != null) {
                  sNamesCombined.append(name.getLastNm2());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].firstNm").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getFirstNm() != null) {
                  sNamesCombined.append(name.getFirstNm());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].middleNm").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getMiddleNm() != null) {
                  sNamesCombined.append(name.getMiddleNm());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].middleNm2").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getMiddleNm2() != null) {
                  sNamesCombined.append(name.getMiddleNm2());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].fromTime_s").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getFromTime() != null) {
                  sNamesCombined.append(StringUtils.formatDate(name.getFromTime()));

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].toTime_s").append(NEDSSConstants.
                   BATCH_PART);

               if (name.getToTime() != null) {
                  sNamesCombined.append(StringUtils.formatDate(name.getToTime()));

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].durationAmt").append(
                   NEDSSConstants.BATCH_PART);

               if (name.getDurationAmt() != null) {
                  sNamesCombined.append(name.getDurationAmt());

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   "person.personNameDT_s[i].personNameSeq").append(
                   NEDSSConstants.BATCH_PART);

               if (name.getPersonNameSeq() != null) {
                  sNamesCombined.append(String.valueOf(name.getPersonNameSeq()));

               }
               sNamesCombined.append(NEDSSConstants.BATCH_SECT).append(
                   NEDSSConstants.BATCH_LINE);
            }
         }
         logger.info("names StringAppended: " + sNamesCombined.toString());
         request.setAttribute("names", sNamesCombined.toString());
      }
		}catch(Exception ex) {
			   String error = "Exception while setting patient name to request";
			   logger.error(error);
			   throw new NEDSSAppException (ex.getMessage()+ ": "+error, ex);
		}

   }

   public static void setCurrentAgeToRequest(HttpServletRequest request,
                                             PersonDT person) {

		String currentAgeAndUnits = null;

		if (person.getBirthTimeCalc() != null) {
			currentAgeAndUnits = PersonUtil.displayAge(StringUtils
					.formatDate(person.getBirthTimeCalc()));
		} else if (person.getBirthTime() != null) {
			currentAgeAndUnits = PersonUtil.displayAge(StringUtils
					.formatDate(person.getBirthTime()));
		}
		// display MPR's current Age as Current Date - birth date (not aod !!)
		if (currentAgeAndUnits != null
				&& currentAgeAndUnits.trim().length() > 0) {
			int pipe = currentAgeAndUnits.indexOf('|');
			request.setAttribute("currentAge", currentAgeAndUnits.substring(0,
					pipe));
			request.setAttribute("currentAgeUnitCd", currentAgeAndUnits
					.substring(pipe + 1));
		}

   }

   /**
    * setCurrentAgeToRequest2: used from right investigation on compare/merge investigations screen
    * @param request
    * @param person
    */
   public static void setCurrentAgeToRequest2(HttpServletRequest request,
           PersonDT person) {

	String currentAgeAndUnits = null;
	
	if (person.getBirthTimeCalc() != null) {
	currentAgeAndUnits = PersonUtil.displayAge(StringUtils
	.formatDate(person.getBirthTimeCalc()));
	} else if (person.getBirthTime() != null) {
	currentAgeAndUnits = PersonUtil.displayAge(StringUtils
	.formatDate(person.getBirthTime()));
	}
	// display MPR's current Age as Current Date - birth date (not aod !!)
	if (currentAgeAndUnits != null
	&& currentAgeAndUnits.trim().length() > 0) {
	int pipe = currentAgeAndUnits.indexOf('|');
	request.setAttribute("currentAge2", currentAgeAndUnits.substring(0,
	pipe));
	request.setAttribute("currentAgeUnitCd2", currentAgeAndUnits
	.substring(pipe + 1));
	}

}
   
   /**
    * This method will return the code for a country given
    * its description.
    *
    * @param desc String
    * @return String
    */
   public static String getCountryCodeByDesc(String desc) {
      String ctryDesc = "";
      CachedDropDownValues srtValues = new CachedDropDownValues();
      TreeMap<Object,Object> tMap = srtValues.getCountryCodesAsTreeMap();
      if (tMap != null) {
         TreeMap<Object,Object> zMap = srtValues.reverseMap(tMap);
         if (zMap != null) {
            Set<Object> set = zMap.keySet();
           Iterator<Object>  itr = set.iterator();
            while (itr.hasNext()) {
               String key = (String) itr.next();
               if (key.equalsIgnoreCase(desc)) {
                  ctryDesc = (String) zMap.get(key);
               }
            }
         }
      }
      return ctryDesc;
   }

   /**
    * This method creates a request attribute of the counties for a state.
    *
    * @param request HttpServletRequest
    * @param stateList ArrayList
    */
   public static void prepareAddressCounties(HttpServletRequest request,
                                             ArrayList<Object> stateList) {

      if (stateList != null) {

         StringBuffer totalCounties = new StringBuffer();
         List<Object> unique = new ArrayList<Object> ();
        Iterator<Object>  i = stateList.iterator();

         while (i.hasNext()) {

            Object current = i.next();

            if (!unique.contains(current)) {
               unique.add(current);
            }
         }

        Iterator<Object>  states = unique.iterator();

         while (states.hasNext()) {
            totalCounties.append(getCountiesByState( (String) states.next()));
         }

         request.setAttribute("addressCounties", totalCounties.toString());
      }
      
   



   }

   public static EntityLocatorParticipationDT getEntityLocatorParticipation(PersonVO personVO, String classCd, String useCd)
   {
     Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();
     if (arrELP == null || arrELP.size() == 0) {
       return null;
     }
     else {
      Iterator<Object>  itrAddress = arrELP.iterator();
       while (itrAddress.hasNext()) {

         EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
             itrAddress.next();
         if (elp.getClassCd() != null &&
             elp.getClassCd().equals(classCd) &&
             elp.getUseCd() != null &&
             elp.getUseCd().equals(useCd) &&
             elp.getLocatorUid() != null) {
           return elp;
         }
       }
     }
     return null;
   }
   public static String getDisplayLocalID(String localID)
   {
    if(localID==null || localID.trim().equals(""))
     {
       return null;
     }
       String displayLocalID = null;
       String prefix = NEDSSConstants.PERSON;
       String seedValue = propUtil.getSeedValue();
       String sufix = propUtil.getUidSufixCode();

       if (seedValue != null && seedValue.trim().length() > 0 && sufix != null &&
           sufix.trim().length() > 0)
       {
         displayLocalID = localID.substring(prefix.length());
         displayLocalID = displayLocalID.substring(0,
                                                   displayLocalID.length() -
                                                   sufix.length());
         try
         {
           displayLocalID = String.valueOf( (new Long(displayLocalID).longValue()) -
                                           (new Long(seedValue).longValue()));
         }
         catch(NumberFormatException ne)
         {
           logger.error("Can not able to convert " +displayLocalID +" to long value");
           ne.printStackTrace();
         }
       }
       else
       {
         logger.error("Can not able to read the Seed value and sufix from properties file to derive DisplayLocalID");
       }

     return displayLocalID;
   }
   public static String getDefaultStateCd()
   {
     String defaultStateCd = propUtil.getNBS_STATE_CODE();
     return defaultStateCd;
   }
   
   public static String getDefaultCountryCd()
   {
     //String defaultStateCd = propUtil.getNBS_COUNTRY_CODE();
     
     String defaultStateCd = "840";//Fatima: there's no country code in the property table like we have for state, should we add it to the database?
     return defaultStateCd;
   }
   
   
   public static String getBirthDate(String month, String day, String year)
   {
     StringBuffer birthDate = new StringBuffer("");
     if (month != null && month.trim().length() != 0)
     {
       birthDate.append(month).append("/");
     }
     else
       birthDate.append("../");
     if (day != null && day.trim().length() != 0)
     {
       birthDate.append(day).append("/");
     }
     else
       birthDate.append("../");
     if (year != null && year.trim().length() != 0)
     {
       birthDate.append(year);
     }
     else
       birthDate.append("....");

     return birthDate.toString();
   }
   
   public void getBirthAddress(PersonVO personVO,CompleteDemographicForm pform){
	   Collection<Object>  addresses = personVO.
       getTheEntityLocatorParticipationDTCollection();
	   boolean exists= false;
	   if (addresses != null) {
       logger.info("Number of addresses: " + addresses.size());
      Iterator<Object>  iter = addresses.iterator();
       Timestamp mostRecentAddressAOD = null;
       Timestamp mostRecentHomeTeleAOD = null;
       Timestamp mostRecentWorkTeleAOD = null;
       while (iter.hasNext()) {

          EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                             iter.next();

          if (elp != null) {

             if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                 elp.getUseCd() != null &&
                 elp.getStatusCd() != null &&
                 elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                 elp.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE) &&
                 elp.getClassCd().equals("PST") && elp.getCd().equals("F") &&
                 elp.getUseCd().equals("BIR")) {

                PostalLocatorDT postal = elp.getThePostalLocatorDT();
                pform.setBirthAddress(elp);
                exists = true;
                break;
               // request.setAttribute("birthCity", postal.getCityDescTxt());
                
                
               

             }
             
          }
       }
       if(!exists){
    	   EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
    	   PostalLocatorDT dt = new PostalLocatorDT();
    	   dt.setCityDescTxt(null);
    	   dt.setStateCd("");
    	   
    	   dt.setCntyCd("");
    	   dt.setCntryCd("");   
    	   elp.setThePostalLocatorDT(dt);
          pform.setBirthAddress(elp);
       }
     }
	   else{
			   EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
			   PostalLocatorDT dt = new PostalLocatorDT();
			   dt.setCityDescTxt(null);
			   dt.setStateCd("");
			   dt.setCntyCd("");
			   dt.setCntryCd("");   
			   elp.setThePostalLocatorDT(dt);
			   pform.setBirthAddress(elp);
	   }
   }
   
   public void getDeceasedAddress(PersonVO personVO,CompleteDemographicForm pform){
	   Collection<Object>  addresses = personVO.
       getTheEntityLocatorParticipationDTCollection();
	   boolean exists= false;
	   if (addresses != null) {
       logger.info("Number of addresses: " + addresses.size());
      Iterator<Object>  iter = addresses.iterator();
       Timestamp mostRecentAddressAOD = null;
       Timestamp mostRecentHomeTeleAOD = null;
       Timestamp mostRecentWorkTeleAOD = null;
       while (iter.hasNext()) {

          EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                             iter.next();

          if (elp != null) {

             if (elp.getStatusCd() != null && elp.getClassCd() != null &&
                     elp.getUseCd() != null &&
                     elp.getStatusCd() != null &&
                     elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                     elp.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE) &&
                     elp.getClassCd().equals("PST") &&
                     elp.getUseCd().equals("DTH")) {

                PostalLocatorDT postal = elp.getThePostalLocatorDT();
               
                pform.setDeceasedAddress(elp);
                exists = true;
                break;
               
               // request.setAttribute("birthCity", postal.getCityDescTxt());     

             }
             
          }
       }
       if(!exists){
    	   EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
    	   PostalLocatorDT dt = new PostalLocatorDT();
    	   dt.setCityDescTxt(null);
    	   dt.setStateCd("");
    	   
    	   dt.setCntyCd("");
    	   dt.setCntryCd("");  
    	   elp.setThePostalLocatorDT(dt);
           pform.setDeceasedAddress(elp);
       }
       }
	   else{
    	   EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
    	   PostalLocatorDT dt = new PostalLocatorDT();
    	   dt.setCityDescTxt(null);
    	   dt.setStateCd("");
      	   dt.setCntyCd("");
    	   dt.setCntryCd("");  
    	   elp.setThePostalLocatorDT(dt);
           pform.setDeceasedAddress(elp);
	   }
   }
   
   public void setToNamesCollection(CompleteDemographicForm personForm){
	   BatchEntry be = new BatchEntry();
	   ArrayList<BatchEntry> list = new ArrayList<BatchEntry>();
	   Map<String,String> map = new HashMap<String,String>();
	   list = personForm.getAllBatchAnswer4Table("nameTable");
	   PersonVO personVO = personForm.getPerson();
	   //Collection<Object>  names = personVO.getThePersonNameDTCollection();
	   Collection<Object>  names = new ArrayList<Object> ();
	   ArrayList<Object>  addressCollection = new ArrayList<Object>();
	   for(int i=0;i<list.size();i++){
		   be = list.get(i);
		   if(be != null){
			   map = be.getAnswerMap();
			   if(map != null){
				   PersonNameDT name = new  PersonNameDT();
				   if(map.get("person_uid") != null ){
				   if(!map.get("person_uid").trim().equals(""))	   
				   name.setPersonUid(Long.valueOf(map.get("person_uid"))); 
				   name.setPersonNameSeq(be.getId());
				   }
				   name.setAsOfDate_s(map.get("nameDate"));
				   name.setNmUseCd(map.get("nameType"));
				   name.setNmPrefix(map.get("namePrefix"));
				   name.setLastNm(map.get("nameLast"));
				   name.setLastNm2(map.get("nameSecLast"));
				   name.setFirstNm(map.get("nameFirst"));
				   name.setMiddleNm(map.get("nameMiddle"));
				   name.setMiddleNm2(map.get("nameSecMiddle"));
				   name.setNmSuffix(map.get("nameSuffix"));
				   name.setNmDegree(map.get("nameDegree")); 
				   name.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				  // if(be.getExisting()==true)					   
				  // name.setPersonNameSeq(be.getId());
				   names.add(name);   
			    }
		   }
	     }
	   personVO.setThePersonNameDTCollection(names);
   }

   public void setToIdsCollection(CompleteDemographicForm personForm){
	   BatchEntry be = new BatchEntry();
	   ArrayList<BatchEntry> list = new ArrayList<BatchEntry>();
	   Map<String,String> map = new HashMap<String,String>();
	   list = personForm.getAllBatchAnswer4Table("idenTable");
	   PersonVO personVO = personForm.getPerson();
	   //Collection<Object>  names = personVO.getThePersonNameDTCollection();
	   Collection<Object>  ids = new ArrayList<Object> ();
	   ArrayList<Object>  idsCollection = new ArrayList<Object>();
	   for(int i=0;i<list.size();i++){
		   be = list.get(i);
		   if(be != null){
			   map = be.getAnswerMap();
			   if(map != null){
				   EntityIdDT idDt = new  EntityIdDT();
				   if(map.get("entity_uid") != null && !map.get("entity_uid").trim().equals("")){
				   idDt.setEntityUid(Long.valueOf(map.get("entity_uid"))); 				  
				   }
				   if(map.get("entityIdSeq") != null && !map.get("entityIdSeq").trim().equals("")){					  
					  idDt.setEntityIdSeq(Integer.valueOf(map.get("entityIdSeq")));
					  }
	               idDt.setAsOfDate_s(map.get("idDate"));
	               String idType = map.get("idType");
	               
	               idDt.setTypeCd(idType);
	               
	               if(idType!=null && idType.equalsIgnoreCase("OTH"))
	            	   idDt.setTypeDescTxt(map.get("IdTypeOTH"));//ND-23426
	               else
	            	   idDt.setTypeDescTxt(map.get("idTypeDesc"));//ND-23701
	               
	               String idAssgn = map.get("idAssgn");
	               
	               idDt.setAssigningAuthorityCd(idAssgn);
	               
	               if(idAssgn!=null && idAssgn.equalsIgnoreCase("OTH"))
	            	   idDt.setAssigningAuthorityDescTxt(map.get("idAssgnOTH"));//ND-23426
	              
	               idDt.setRootExtensionTxt(map.get("idValue"));
	               idDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				  // if(be.getExisting()==true)					   
				  // name.setPersonNameSeq(be.getId());
				   ids.add(idDt);   
			    }
		   }
	     }
	   personVO.setTheEntityIdDTCollection(ids);
   }
   public void setToAddressCollection(PersonVO personVO,CompleteDemographicForm personForm,String userId){
	   BatchEntry be = new BatchEntry();
	   ArrayList<BatchEntry> list = new ArrayList<BatchEntry>();
	   Map<String,String> map = new HashMap<String,String>();
	   list = personForm.getAllBatchAnswer4Table("addrTable");
	   ArrayList<Object>  addressCollection = new ArrayList<Object>();
	  // ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();
	   for(int i=0;i<list.size();i++){
		   be = list.get(i);
		   map = be.getAnswerMap();
		   if(map != null){
			   EntityLocatorParticipationDT elp = new  EntityLocatorParticipationDT();
			   PostalLocatorDT dt = new PostalLocatorDT();
			   if(map.get("locator_uid") != null ){	
				  if(!map.get("locator_uid").trim().equals("")) 
				  elp.setLocatorUid(Long.valueOf(map.get("locator_uid")));	
				  //dt.setAddTime(new Timestamp(new Date().getTime()));
	                dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	               // dt.setAddUserId(Long.valueOf(userId));
	                dt.setLastChgUserId(Long.valueOf(userId));
	                dt.setLastChgTime(new Timestamp(new Date().getTime()));
			   }else{
				    dt.setAddTime(new Timestamp(new Date().getTime()));
	                dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	                dt.setAddUserId(Long.valueOf(userId));
	                dt.setLastChgUserId(Long.valueOf(userId));
	                dt.setLastChgTime(new Timestamp(new Date().getTime()));
			   }
			   if(map.get("addrNameDate") != null) 
			   elp.setAsOfDate_s(map.get("addrNameDate"));
			   if(map.get("addrType") != null) 
			   elp.setCd(map.get("addrType"));
			   if(map.get("addrUse") != null) 
			   elp.setUseCd(map.get("addrUse")); 
			   if(map.get("addrStr1") != null && !map.get("addrStr1").equals("")) 
			   dt.setStreetAddr1(map.get("addrStr1")); 
			   if(map.get("addrStr2") != null && !map.get("addrStr2").equals("")) 
				dt.setStreetAddr2(map.get("addrStr2"));
			   if(map.get("addrCity") != null && !map.get("addrCity").equals("")) 
			    dt.setCityDescTxt(map.get("addrCity"));
			   if(map.get("addrZip") != null && !map.get("addrZip").equals("")) 
			    dt.setZipCd(map.get("addrZip"));
			   if(map.get("addrCounty") != null && !map.get("addrCounty").equals("")) 
			   dt.setCntyCd(map.get("addrCounty"));
			   //Census Tract
			   if(map.get("addrCensusTract") != null && !map.get("addrCensusTract").equals("")) 
			   dt.setCensusTract(map.get("addrCensusTract"));
			   if(map.get("addrCountry") != null && !map.get("addrCountry").equals("")) 
			   dt.setCntryCd(map.get("addrCountry"));
			   if(map.get("addrState") != null && !map.get("addrState").equals("")) 
			   dt.setStateCd(map.get("addrState"));
			   if(map.get("addrComments") != null && !map.get("addrComments").equals("")) 
			   elp.setLocatorDescTxt(map.get("addrComments"));  
			   dt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			   elp.setThePostalLocatorDT(dt);
			   elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			   elp.setClassCd(NEDSSConstants.POSTAL);
			   addressCollection.add(elp);		   
		   }	 
	     }
	   personForm.setAddressCollection(addressCollection);
   }
   
   public void setToPhoneCollection(PersonVO personVO,CompleteDemographicForm personForm,String userId){
	   BatchEntry be = new BatchEntry();
	   ArrayList<BatchEntry> list = new ArrayList<BatchEntry>();
	   Map<String,String> map = new HashMap<String,String>();
	   list = personForm.getAllBatchAnswer4Table("phoneTable");
	   ArrayList<Object>  teleCollection = new ArrayList<Object>();
	  // ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();
	   for(int i=0;i<list.size();i++){
		   be = list.get(i);
		   map = be.getAnswerMap();
		   if(map != null){
			   EntityLocatorParticipationDT elp = new  EntityLocatorParticipationDT();
			   TeleLocatorDT dt = new TeleLocatorDT();
			   if(map.get("locator_uid") != null ){	
				   if(!map.get("locator_uid").trim().equals(""))
				    elp.setLocatorUid(Long.valueOf(map.get("locator_uid")));				  
	                //dt.setAddTime(new Timestamp(new Date().getTime()));
	                dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	               // dt.setAddUserId(Long.valueOf(userId));
	                dt.setLastChgUserId(Long.valueOf(userId));
	                dt.setLastChgTime(new Timestamp(new Date().getTime()));
			   }else{
				    dt.setAddTime(new Timestamp(new Date().getTime()));
	                dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	                dt.setAddUserId(Long.valueOf(userId));
	                dt.setLastChgUserId(Long.valueOf(userId));
	                dt.setLastChgTime(new Timestamp(new Date().getTime()));
			   }
			   if(map.get("phDate") != null) 
			   elp.setAsOfDate_s(map.get("phDate"));
			   if(map.get("phType") != null) 
			   elp.setCd(map.get("phType"));
			   if(map.get("phUse") != null) 
			   elp.setUseCd(map.get("phUse"));			   
			   if(map.get("phCntryCd") != null && !map.get("phCntryCd").equals("")) 
			   dt.setCntryCd(map.get("phCntryCd"));
			   if(map.get("phNum") != null && !map.get("phNum").equals("")) 
			   dt.setPhoneNbrTxt(map.get("phNum"));
			   if(map.get("phExt") != null && !map.get("phExt").equals("")) 
			   dt.setExtensionTxt(map.get("phExt"));
			   if(map.get("phEmail") != null && !map.get("phEmail").equals("")) 
			   dt.setEmailAddress(map.get("phEmail"));
			   if(map.get("phUrl") != null && !map.get("phUrl").equals("")) 
			   dt.setUrlAddress(map.get("phUrl"));
			   if(map.get("phComments") != null && !map.get("phComments").equals("")) 
			   elp.setLocatorDescTxt(map.get("phComments"));  
			   dt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			   elp.setTheTeleLocatorDT(dt);
			   elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			   elp.setClassCd(NEDSSConstants.TELE);
			   teleCollection.add(elp);		   
		   }	 
	     }
	   personForm.setTelephoneCollection(teleCollection);
   }
  

  
   /**
    * This method is used in setting the race(s) onto
    * the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public void setRace(PersonVO personVO, CompleteDemographicForm personForm,String userId) {

      Long personUID = personVO.getThePersonDT().getPersonUid();
      ArrayList<Object> raceList = new ArrayList<Object> ();
      CachedDropDownValues cachedDDV = new CachedDropDownValues();
      
      BatchEntry be = new BatchEntry();
	   ArrayList<BatchEntry> list = new ArrayList<BatchEntry>();
	   Map<String,String> map = new HashMap<String,String>();
	   list = personForm.getAllBatchAnswer4Table("raceTable");
	   Collection<Object>  raceCollection = personVO.getThePersonRaceDTCollection();  
	   if(raceCollection != null){
	   Iterator<Object>  itrRaceOld = raceCollection.iterator();
	      //Iterator<Object>  itrAddress = addressList.iterator();
    
       while (itrRaceOld.hasNext()) {     	
	    	  PersonRaceDT raceOld = (PersonRaceDT)itrRaceOld.next();	    	
	    		 raceOld.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);	
	    		 raceOld.setItNew(false);
	    		 raceOld.setItDirty(false);
	    		 raceOld.setItDelete(true);
	    	  
	      }
	   }
	   
	   
	  // ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();
	   if(list != null){
	   for(int i=0;i<list.size();i++){
		   be = list.get(i);
		   map = be.getAnswerMap();
		   if(map != null){
			
				    PersonRaceDT raceDT = new PersonRaceDT();
				    String raceexists= raceExistsInOldCollection(map.get("raceType").trim(),"RaceCategoryCd",raceCollection);
				    if(!raceexists.equals("")){
				   
				    	//its an edit of race category
				    	  raceDT.setItNew(false);
			                raceDT.setItDirty(true);
			                raceDT.setItDelete(false);
			               
			               
			                raceDT.setStatusTime(new Timestamp(new Date().getTime()));
			                // raceDT.setAddTime(new Timestamp(new Date().getTime()));
			                raceDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
			                //raceDT.setAddUserId(Long.valueOf(userId));
			                raceDT.setLastChgUserId(Long.valueOf(userId));
			                raceDT.setLastChgTime(new Timestamp(new Date().getTime()));
			                
			                Iterator<Object> itrRaceOld1 = raceCollection.iterator(); 
			                while (itrRaceOld1.hasNext()) {    	    	
			                	PersonRaceDT raceOld1 = (PersonRaceDT)itrRaceOld1.next();
			      	    		if(raceOld1.getRaceCategoryCd().equals(map.get("raceType").trim()) && 
			        			     raceOld1.getRaceCd().equals(map.get("raceType").trim())){
			      	    			itrRaceOld1.remove();
			      	    			break;
			      	    		}
			      	    		 //itrRaceOld.remove(); 
			      	    		// raceCollection.add(raceOld);   	
			      	    	  
			      	      }    
			                
				    	
				  
				  
				    }else if(raceexists.equals("")){
			    	   raceDT.setItNew(true);
			            raceDT.setItDelete(false);
			            raceDT.setItDirty(false);
			            
			            raceDT.setStatusTime(new Timestamp(new Date().getTime()));
		                raceDT.setAddTime(new Timestamp(new Date().getTime()));
		                raceDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
		                raceDT.setAddUserId(Long.valueOf(userId));
		                raceDT.setLastChgUserId(Long.valueOf(userId));
		                raceDT.setLastChgTime(new Timestamp(new Date().getTime()));
				    }
	                raceDT.setPersonUid(personUID);
		            raceDT.setRaceCategoryCd(map.get("raceType").trim());
		            raceDT.setRaceCd(map.get("raceType").trim());
		            raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		            /*if(map.get("raceType").trim().equals(NEDSSConstants.UNKNOWN)){
		              raceDT.setRaceDescTxt("Unknown");
		            }else  if(map.get("raceType").trim().equals(NEDSSConstants.OTHER_RACE)){
		              raceDT.setRaceDescTxt("Other");
		            }else  if(map.get("raceType").trim().equals(NEDSSConstants.MULTI_RACE)){
		              raceDT.setRaceDescTxt("Other");
		            }*/
		            raceDT.setAsOfDate_s(map.get("raceDate").trim());
		          
		            if(map.get("raceDetailCat") != null && !map.get("raceDetailCat").equals("")){
		            	String detailedRace = map.get("raceDetailCat");
		            	while(detailedRace.indexOf("|")!= -1){
		                 String strVal = detailedRace.substring(0,detailedRace.indexOf("|")).trim();         
		                 String exists = raceExistsInOldCollection(strVal,"RaceCd",raceCollection);
		                 detailedRace = detailedRace.substring(detailedRace.indexOf("|")+1, detailedRace.length());
		                 PersonRaceDT detailedraceDT = new PersonRaceDT();
		                 if(exists.equals("")){
		                	 detailedraceDT.setItNew(true);
		                	 detailedraceDT.setItDelete(false);
		                	 detailedraceDT.setItDirty(false);
		                	 
		                	 detailedraceDT.setStatusTime(new Timestamp(new Date().getTime()));
		                	 detailedraceDT.setAddTime(new Timestamp(new Date().getTime()));
		                	 detailedraceDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
		                	 detailedraceDT.setAddUserId(Long.valueOf(userId));
		                	 detailedraceDT.setLastChgUserId(Long.valueOf(userId));
		                	 detailedraceDT.setLastChgTime(new Timestamp(new Date().getTime()));
		                 }else{
		                	 detailedraceDT.setItNew(false);
			                 detailedraceDT.setItDirty(true);
			                 detailedraceDT.setItDelete(false);
			                 detailedraceDT.setStatusTime(new Timestamp(new Date().getTime()));
		                	 //detailedraceDT.setAddTime(new Timestamp(new Date().getTime()));
		                	 detailedraceDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
		                	 //detailedraceDT.setAddUserId(Long.valueOf(userId));
		                	 detailedraceDT.setLastChgUserId(Long.valueOf(userId));
		                	 detailedraceDT.setLastChgTime(new Timestamp(new Date().getTime()));
			                 
			                 Iterator<Object> itrRaceOld2 = raceCollection.iterator();
					 	      //Iterator<Object>  itrAddress = addressList.iterator();
					 	      while (itrRaceOld2.hasNext()) {
					 	    	  PersonRaceDT raceOld2 = (PersonRaceDT)itrRaceOld2.next();
					 	    	  if(raceOld2.getRaceCategoryCd().equals(map.get("raceType").trim()) && 
					 	    			 raceOld2.getRaceCd().equals(strVal)){					 	    		 
					 	    		 itrRaceOld2.remove(); 
					 	    		 break;
					 	    	  }
					 	    	  
					 	      }
		                	    
		                 }
		                 detailedraceDT.setPersonUid(personUID);
		                 detailedraceDT.setRaceCategoryCd(map.get("raceType").trim());
		                 detailedraceDT.setRaceCd(strVal.trim());
		                 detailedraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		                 // get the race desc from cached drop down values from cached hashed map of codes and descriptions
		                 detailedraceDT.setRaceDescTxt(cachedDDV.getDescForCode(map.get("raceType").trim(), strVal.trim()));
		                 detailedraceDT.setAsOfDate_s(map.get("raceDate").trim());
		                 raceList.add(detailedraceDT);
		                 
		            	} 
		            	if(detailedRace != null && !detailedRace.trim().equals("") && !detailedRace.trim().equals("|")){
		            		
		            		 String strVal = detailedRace.trim();         
			                 String exists = raceExistsInOldCollection(strVal,"RaceCd",raceCollection);			                
			                 PersonRaceDT detailedraceDT = new PersonRaceDT();
			                 if(exists.equals("")){
			                	 detailedraceDT.setItNew(true);
			                	 detailedraceDT.setItDelete(false);
			                	 detailedraceDT.setItDirty(false);  
			                	 detailedraceDT.setStatusTime(new Timestamp(new Date().getTime()));
			                	 detailedraceDT.setAddTime(new Timestamp(new Date().getTime()));
			                	 detailedraceDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
			                	 detailedraceDT.setAddUserId(Long.valueOf(userId));
			                	 detailedraceDT.setLastChgUserId(Long.valueOf(userId));
			                	 detailedraceDT.setLastChgTime(new Timestamp(new Date().getTime()));
			                 }else{
			                	 detailedraceDT.setItNew(false);
				                 detailedraceDT.setItDirty(true);
				                 detailedraceDT.setItDelete(false);
				                 detailedraceDT.setStatusTime(new Timestamp(new Date().getTime()));
			                	 //detailedraceDT.setAddTime(new Timestamp(new Date().getTime()));
			                	 detailedraceDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
			                	 //detailedraceDT.setAddUserId(Long.valueOf(userId));
			                	 detailedraceDT.setLastChgUserId(Long.valueOf(userId));
			                	 detailedraceDT.setLastChgTime(new Timestamp(new Date().getTime()));
				                 Iterator<Object> itrRaceOld2 = raceCollection.iterator();
						 	      //Iterator<Object>  itrAddress = addressList.iterator();
						 	      while (itrRaceOld2.hasNext()) {
						 	    	  PersonRaceDT raceOld2 = (PersonRaceDT)itrRaceOld2.next();
						 	    	  if(raceOld2.getRaceCategoryCd().equals(map.get("raceType").trim()) && 
						 	    			 raceOld2.getRaceCd().equals(strVal)){					 	    		 
						 	    		 itrRaceOld2.remove(); 
						 	    		 break;
						 	    	  }
						 	    	  
						 	      }
			                	    
			                 }
			                 detailedraceDT.setPersonUid(personUID);
			                 detailedraceDT.setRaceCategoryCd(map.get("raceType").trim());
			                 detailedraceDT.setRaceCd(strVal);
			                 detailedraceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			                 // get the race desc from cached drop down values from cached hashed map of codes and descriptions
			                 detailedraceDT.setRaceDescTxt(cachedDDV.getDescForCode(map.get("raceType").trim(), strVal));
			                 detailedraceDT.setAsOfDate_s(map.get("raceDate"));
			                 raceList.add(detailedraceDT);
			                 detailedRace ="";
		            	}
		            	
		            }else{			            	
		            	raceDT.setRaceDescTxt(CachedDropDowns.getCodeDescTxtForCd(map.get("raceType"), "RACE_CALCULATED"));
		            }
		            raceList.add(raceDT);  
				   
			   
			   
			   
		   }
	   }
	   }
	   if(raceCollection != null)
	   raceList.addAll(raceCollection);
	  
	      personVO.setThePersonRaceDTCollection(raceList);
    
   }
   
   private String raceExistsInOldCollection(String val,String Type, Collection<Object>  raceCollection ){
	   String ret = "";	
	   if(raceCollection != null){
	   Iterator<Object>  itrRaceOld = raceCollection.iterator();
	      //Iterator<Object>  itrAddress = addressList.iterator();
	      while (itrRaceOld.hasNext()) {
	    	  PersonRaceDT raceOld = (PersonRaceDT)itrRaceOld.next();	         
	              if(Type.equals("RaceCd") && raceOld.getRaceCd() != null && raceOld.getRaceCd().equals(val)){
	            	return ("exists");
	              }else if(Type.equals("RaceCategoryCd") && raceOld.getRaceCategoryCd() != null && raceOld.getRaceCategoryCd().equals(val)){
	            	  return ("exists");    
		              } 
	      }   
	   }

	   return ret;	   
   }

   /**
    * This method will set the Ethnicity data onto
    * the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public void setEthnicity(PersonVO personVO,CompleteDemographicForm personForm, HttpServletRequest request,String userId ) {

      logger.info("Inside setEthnicity");
      
      Long personUID = personVO.getThePersonDT().getPersonUid();
      String[] arrEthnicity = personForm.getSpanOrigin();

      if (personVO.getThePersonEthnicGroupDTCollection() == null) { // null if this is new

         ArrayList<Object> ethnicityList = new ArrayList<Object> ();

         if (arrEthnicity != null) {

            for (int i = 0, len = arrEthnicity.length; i < len; i++) {

               String strVal = arrEthnicity[i];
               if (strVal == null || strVal.equals("")) {
                  continue;
               }
               PersonEthnicGroupDT ethnicDT = new PersonEthnicGroupDT();
               ethnicDT.setItNew(true);
               ethnicDT.setItDirty(false);
               ethnicDT.setPersonUid(personUID);
               ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               ethnicDT.setEthnicGroupCd(strVal.trim());
              
               ethnicDT.setAddTime(new Timestamp(new Date().getTime()));
               ethnicDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
               ethnicDT.setAddUserId(Long.valueOf(userId));
               ethnicDT.setLastChgUserId(Long.valueOf(userId));
               ethnicDT.setLastChgTime(new Timestamp(new Date().getTime()));
               ethnicityList.add(ethnicDT);
            }
         } 
         personVO.setThePersonEthnicGroupDTCollection(ethnicityList);
      }
      else { // we have previously stored ethnicities

        Iterator<Object>  iter = personVO.getThePersonEthnicGroupDTCollection().
                         iterator();

         if (iter != null) {

            HashMap<Object,Object> hm = new HashMap<Object,Object>();

            while (iter.hasNext()) {

               PersonEthnicGroupDT ethnicDT = (PersonEthnicGroupDT) iter.next();
               ethnicDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
               ethnicDT.setItDelete(true);

               //put them in a hashmap
               hm.put(ethnicDT.getEthnicGroupCd(), ethnicDT);
            }

            if (arrEthnicity != null) {

               for (int i = 0, len = arrEthnicity.length; i < len; i++) {

                  String strVal = arrEthnicity[i];
                  if (strVal == null || strVal.equals("")) {
                      continue;
                   }
                  PersonEthnicGroupDT ethnicDT = (PersonEthnicGroupDT) hm.get(
                      strVal.trim());

                  if (ethnicDT != null) { //true if exists already in collection
                     ethnicDT.setRecordStatusCd(NEDSSConstants.
                                                RECORD_STATUS_ACTIVE);
                     ethnicDT.setItNew(false);
                     ethnicDT.setItDirty(true);
                     ethnicDT.setItDelete(false);
                     ethnicDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
                     ethnicDT.setLastChgUserId(Long.valueOf(userId));
                     ethnicDT.setLastChgTime(new Timestamp(new Date().getTime()));
                  } //false if doesn't exist in collection, a new one
                  else {
                     PersonEthnicGroupDT newEthnicDT = new PersonEthnicGroupDT();
                     newEthnicDT.setItNew(true);
                     newEthnicDT.setItDirty(false);
                     newEthnicDT.setPersonUid(personUID);
                     newEthnicDT.setRecordStatusCd(NEDSSConstants.
                         RECORD_STATUS_ACTIVE);
                     newEthnicDT.setEthnicGroupCd(strVal.trim());
                     newEthnicDT.setAddTime(new Timestamp(new Date().getTime()));
                     newEthnicDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
                     newEthnicDT.setAddUserId(Long.valueOf(userId));
                     newEthnicDT.setLastChgUserId(Long.valueOf(userId));
                     newEthnicDT.setLastChgTime(new Timestamp(new Date().getTime()));
                     personVO.getThePersonEthnicGroupDTCollection().add(
                         newEthnicDT);
                  }
                  
               }

            }

         }
      }
   } //end

	 /**
	 * @param personVO
	 * @return
	 */
	   
   public static Map<Object, Object> setPatientDetailsForIIS(PersonVO personVO){
	   Map map = new HashMap<Object, Object>();
	   try{
		   PersonDT personDT = personVO.getThePersonDT();
		   
		   map.put("FirstName", personDT.getFirstNm());
		   map.put("LastName", personDT.getLastNm());
		   map.put("DOB", personDT.getBirthTime());
		   map.put("Sex", personDT.getCurrSexCd());
		   
		   Collection<Object>  locatorsColl = personVO.getTheEntityLocatorParticipationDTCollection();
		   if (locatorsColl != null) {
					Iterator<Object> locatorIter = locatorsColl.iterator();
					while(locatorIter.hasNext()) {
						EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) locatorIter.next();
						//get Home Address if there
						if("PST".equalsIgnoreCase(elpDT.getClassCd()) && "H".equalsIgnoreCase(elpDT.getUseCd())) {
							PostalLocatorDT postal = elpDT.getThePostalLocatorDT();
							map.put("StreetAddress1", postal.getStreetAddr1());
							map.put("City", postal.getCityDescTxt());
							map.put("State", postal.getStateCd());
							map.put("Zip", postal.getZipCd());
						}
					}
		   }
		   
		   return map;
	   }catch(Exception ex){
		   logger.error("Exception :"+ex.getMessage(), ex);
		   ex.printStackTrace();
	   }
	   return map;
   }
   
   /**
     * Calculate age based on asOfDate.
	 * @param birthAge
	 * @param ageAsOfDateStr
	 * @return
	 * @throws NEDSSAppException
	 */
   
	public static Map calculateAge(String birthAge, String ageAsOfDateStr) throws NEDSSAppException{
		   Map ageMap = new HashMap<String, String>();
		   try{
			   	  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			      Date birthDate = null ;
				  Date ageAsOfDate = null;
			      try {
			    	  birthDate = sdf.parse(birthAge);
			    	  ageAsOfDate =  sdf.parse(ageAsOfDateStr);
			      } catch (ParseException e) {
			    	  logger.debug("Date parsing exception :"+e.getMessage());
			      }
			      
			      
			      long reportedAgeMilliSec = ageAsOfDate.getTime() - birthDate.getTime();
		        
		          long reportedAgeSeconds = reportedAgeMilliSec/1000;
		          long reportedAgeMinutes = reportedAgeSeconds/60;
		          long reportedAgeHours = reportedAgeMinutes/60;
		          long reportedAgeDays = reportedAgeHours/24;
		          long reportedAgeMonths = (long) (reportedAgeDays/30.41);
		          long reportedAgeYears = reportedAgeMonths/12;
	
		          int age;
	
		          if(Math.ceil(reportedAgeDays)<=28){
		        	  age = (int) Math.floor(reportedAgeDays);
		        	  ageMap.put("age", age);
		        	  ageMap.put("unit", "D");
		          } else if(Math.ceil(reportedAgeDays)>28 && reportedAgeYears<1)  {
		            	if(Math.ceil(reportedAgeDays) > 28 && Math.ceil(reportedAgeDays) < 31)
		        			reportedAgeMonths = reportedAgeMonths + 1;
		            	
		            	age = (int) Math.floor(reportedAgeMonths);
			        	ageMap.put("age", age);		
			        	ageMap.put("unit", "M");
		          } else  {
		        	  	age = (int) Math.floor(reportedAgeYears);
		        	  	ageMap.put("age", age);
			        	ageMap.put("unit", "Y");
		          }
	
		   }catch(Exception ex){
			   	logger.error("Exception : "+ ex.getMessage(), ex);
				throw new NEDSSAppException(ex.getMessage(),ex);
		   }
		   return ageMap;
	   }
	
	   public static String buildSearchCriteriaString(PersonSearchVO psVO) {

		      //  build the criteria string
		      StringBuffer sQuery = new StringBuffer("");
		      CachedDropDownValues cache = new CachedDropDownValues();

		      if (psVO.getLastName() != null && !psVO.getLastName().equals("")) {
		         sQuery.append("Last Name").append(" " +
		             cache.getDescForCode("SEARCH_SNDX", psVO.getLastNameOperator()) +
		             " ").append("'" + psVO.getLastName() + "'").append(", ");

		      }
		      if (psVO.getFirstName() != null && !psVO.getFirstName().equals("")) {
		         sQuery.append("First Name").append(" " +
		             cache.getDescForCode("SEARCH_SNDX", psVO.getFirstNameOperator()) +
		             " ").append("'" + psVO.getFirstName() + "'").append(", ");

		      }
		      if ( (psVO.getBirthTimeMonth() != null &&
		            !psVO.getBirthTimeMonth().equals("")) ||
		          (psVO.getBirthTimeDay() != null && !psVO.getBirthTimeDay().equals("")) ||
		          (psVO.getBirthTimeYear() != null && !psVO.getBirthTimeYear().equals("")))
		      {
		        sQuery.append("DOB").append(" " +
		                                    cache.getDescForCode("SEARCH_NUM",
		            psVO.getBirthTimeOperator()) +
		                                    " ").append("'" +
		                                                PersonUtil.getBirthDate(psVO.getBirthTimeMonth(),
		            psVO.getBirthTimeDay(), psVO.getBirthTimeYear()) + "'").append(", ");

		      }
		      else if ( (psVO.getAfterBirthTime() != null &&
		                 psVO.getAfterBirthTime().trim().length() != 0) ||
		               (psVO.getBeforeBirthTime() != null &&
		                psVO.getBeforeBirthTime().trim().length() != 0))
		      {
		        sQuery.append("DOB").append(" Between ").append("'" +
		            psVO.getBeforeBirthTime() + "' and '").append(
		            psVO.getAfterBirthTime() + "'").append(", ");

		      }
		      if (psVO.getCurrentSex() != null && !psVO.getCurrentSex().equals("")) {
		         sQuery.append("Current Sex Equal ").append("'" +
		             cache.getDescForCode("SEX", psVO.getCurrentSex()) +
		             "'").append(", ");

		      }
		      if (psVO.getStreetAddr1() != null && !psVO.getStreetAddr1().equals("")) {
		         sQuery.append("Street Address").append(" " +
		             cache.getDescForCode("SEARCH_SNDX", psVO.getStreetAddr1Operator()) +
		             " ").append("'" + psVO.getStreetAddr1() + "'").append(", ");

		      }
		      if (psVO.getCityDescTxt() != null && !psVO.getCityDescTxt().equals("")) {
		         sQuery.append("City").append(" " +
		             cache.getDescForCode("SEARCH_SNDX", psVO.getCityDescTxtOperator()) +
		             " ").append("'" + psVO.getCityDescTxt() + "'").append(", ");

		      }
		      if (psVO.getState() != null && !psVO.getState().equals("")) {
		         sQuery.append("State Equal ").append("'" +
		             getStateDescTxt(psVO.getState()) + "'").append(", ");

		      }
		      if (psVO.getZipCd() != null && !psVO.getZipCd().equals("")) {
		         sQuery.append("Zip").append(" Equal ").append("'" + psVO.getZipCd() +
		             "' ").append(",");

		      }
		      String codeDesc = cache.getDescForCode("SEARCH_ALPHA", psVO.getPatientIDOperator());
		      if (codeDesc == null || codeDesc.equals("")) {
		    	  codeDesc = "Equal";
		      }
		      if (psVO.getOldLocalID() != null && !psVO.getOldLocalID().equals("")) {
		        sQuery.append("Patient ID ").append(" " +
		            codeDesc +
		            " ").append("'" + psVO.getOldLocalID() +"'").append(", ");
		      }
		      if (psVO.getRootExtensionTxt() != null &&
		          !psVO.getRootExtensionTxt().equals("")) {

		         if (psVO.getTypeCd() != null && !psVO.getTypeCd().equals("")) {
		            sQuery.append(cache.getDescForCode("EI_TYPE_PAT", psVO.getTypeCd())).
		                append(" Equal ").append("'" + psVO.getRootExtensionTxt() + "'").
		                append(", ");
		         }
		      }

		      if (psVO.getSsn() != null && !psVO.getSsn().trim().equals("")) {
		         sQuery.append(NEDSSConstants.SNUMBER_LABEL).append(" Equal ").append("'" + psVO.getSsn() +
		             "' ").append(",");

		      }

		      if (psVO.getPhoneNbrTxt() != null && !psVO.getPhoneNbrTxt().equals("")) {
		         sQuery.append("Telephone").append(" Contains ").append("'" +
		             psVO.getPhoneNbrTxt() + "' ").append(",");

		      }
		      String findRace = getRaceDescTxt(psVO.getRaceCd(),
		                                       cache.getRaceCodes("ROOT"));

		      if (psVO.getRaceCd() != null && !psVO.getRaceCd().equals("")) {
		         sQuery.append("Race Equal ").append("'" +
		             cache.getDescForCode("ROOT", psVO.getRaceCd()) + "'").append(", ");

		      }
		      if (psVO.getEthnicGroupInd() != null &&
		          !psVO.getEthnicGroupInd().equals("")) {
		         sQuery.append("Ethnicity Equal ").append("'" +
		             cache.getDescForCode("P_ETHN_GRP", psVO.getEthnicGroupInd()) + "'").
		             append(", ");

		      }
		      String findRole = getRoleDescTxt(psVO.getRole(),
		                                       cache.getCodedValues("RL_TYPE_PAT"));

		      if (psVO.getRole() != null && !psVO.getRole().equals("")) {
		         sQuery.append("Role Equal ").append("'" +
		             cache.getDescForCode("RL_TYPE_PAT", psVO.getRole()) + "'").append(", ");
		      }
		      
		  	if (psVO.getReportType() != null && psVO.getReportType().trim().length() != 0 && !psVO.getReportType().equals("N")) {
		  		
		  		if(psVO.getReportType().equals(PersonSearchVO.LAB_REPORT)){
		        	
		  			sQuery.append("Event Type Equals ").append("'Lab report'").append(", ");
		          	
		          } else if(psVO.getReportType().equals(PersonSearchVO.MORBIDITY_REPORT)){
		          	
		        	  sQuery.append("Event Type Equals ").append("'Morbidity Report'").append(", ");
		          	
		          } else if(psVO.getReportType().equals(PersonSearchVO.CASE_REPORT)){
		          	
		        	  sQuery.append("Event Type Equals ").append("'Case Report'").append(", ");
		          	
		          } else if(psVO.getReportType().equals(PersonSearchVO.INVESTIGATION_REPORT)){
		          	
		        	  sQuery.append("Event Type Equals ").append("'Investigation'").append(", ");
		          	
		          }
		  		
			if (psVO.getConditionSelected() != null && psVO.getConditionSelected().length > 1
					|| (psVO.getConditionSelected() != null && psVO.getConditionSelected().length == 1
							&& !psVO.getConditionSelected()[0].trim().equals(""))) {

				String[] cArray = psVO.getConditionSelected();
				String prefix = "";
				sQuery.append("Condition in ").append("(");
				for (int i = 0; i < (cArray.length); i++) {
					if(cArray[i].equals(""))
						continue;
					sQuery.append(prefix);
					prefix = ", ";
					sQuery.append("'" + cache.getConditionDesc(cArray[i]) + "'");
				}
				sQuery.append(")").append(", ");
			}
			
			String[] pa = psVO.getProgramAreaInvestigationSelected();
			if(pa != null
					&& pa.length == 1 && pa[0].trim().equals(""))
			pa = psVO.getProgramAreaLabSelected();
			if(pa != null
					&& pa.length == 1 && pa[0].trim().equals(""))
				pa = psVO.getProgramAreaMorbSelected();
			
			if (pa != null && pa.length > 1 || (pa != null
					&& pa.length == 1 && !pa[0].trim().equals(""))) {

				String prefix = "";
				sQuery.append("Program Area in ").append("(");
				for (int i = 0; i < (pa.length); i++) {
					if(pa[i].equals(""))
						continue;
					sQuery.append(prefix);
					prefix = ", ";
					sQuery.append("'" + pa[i] + "'");
				}
				sQuery.append(")").append(", ");
			}
	
			if (psVO.getJurisdictionSelected() != null && psVO.getJurisdictionSelected().length > 1
					|| (psVO.getJurisdictionSelected() != null && psVO.getJurisdictionSelected().length == 1
							&& !psVO.getJurisdictionSelected()[0].trim().equals(""))) {

				String[] cArray = psVO.getJurisdictionSelected();
				
				String prefix = "";
				sQuery.append("Jurisdiction in ").append("(");
				for (int i = 0; i < (cArray.length); i++) {
					if(cArray[i].equals(""))
						continue;
					sQuery.append(prefix);
					prefix = ", ";
					sQuery.append("'" + cache.getJurisdictionDesc(cArray[i]) + "'");
				}
				sQuery.append(")").append(", ");

			}
			if (psVO.getPregnantSelected() != null && !psVO.getPregnantSelected().equals("")) {
				sQuery.append("Pregnancy Status Equals ")
						.append("'" + cache.getDescForCode("YNU", psVO.getPregnantSelected()) + "'").append(", ");
			}
		  	
		  	//process check

			if( (null != psVO.getProcessedState() && "true".equalsIgnoreCase(psVO.getProcessedState())) && (null == psVO.getUnProcessedState() || "false".equalsIgnoreCase(psVO.getUnProcessedState()))){
				sQuery.append("Processing Status Equals ").append("PROCESSED").append(",");
			} else if( (null == psVO.getProcessedState() || "false".equalsIgnoreCase(psVO.getProcessedState())) && ( null != psVO.getUnProcessedState() && "true".equalsIgnoreCase(psVO.getUnProcessedState()))){
				sQuery.append("Processing Status Equals ").append("UNPROCESSED").append(",");
			}
		  /*	 
		  	if (psVO.getDateType() != null && !psVO.getDateType().equals("")){
		 		 sQuery.append("Document Updated By User Equal ").append("'" + psVO.getDocumentUpdateFullNameSelected() + "'").append(", ");
		 	  }
		 	 */
		  	 if(psVO.getActType() != null && psVO.getActType().trim().length() != 0 
		   		  && psVO.getDocOperator() != null && psVO.getDocOperator().trim().length() != 0
		   		  && psVO.getActId() != null && psVO.getActId().trim().length() != 0
		   		  ){
		   	  String codeDescDoc = cache.getDescForCode("PHVS_EVN_SEARCH_ABC", psVO.getActType());
		   	  sQuery.append(codeDescDoc);
		   	  
		   	  if(psVO.getDocOperator().equals("=")){
		   		  sQuery.append(" equals ").append("'" + psVO.getActId() + "'").append(", ");
		   	  }
		   	  else if(psVO.getDocOperator().equals("CT")){
		   		sQuery.append(" contains ").append("'" + psVO.getActId() + "'").append(", ");
		   	  }
		   	  else if(psVO.getDocOperator().equals("!=")){
		   		sQuery.append(" not equals ").append("'" + psVO.getActId() + "'").append(", ");
		   	  }
		     }
		      if(psVO.getDateType() != null && psVO.getDateType().trim().length() != 0 
		    		  && psVO.getDateOperator() != null && psVO.getDateOperator().trim().length() != 0
		    		  && ((psVO.getDateFrom() != null && psVO.getDateFrom().trim().length() != 0) 
		    		      || psVO.getDateOperator().equals(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS)
		    		      || psVO.getDateOperator().equals(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS)
		    		      || psVO.getDateOperator().equals(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS))
		    		      
		    		  ){
		    	  String codeDescDate = cache.getDescForCode("NBS_EVENT_SEARCH_DATES", psVO.getDateType());
		    	  sQuery.append(codeDescDate);
		    	  
		    	  if(psVO.getDateOperator().equals("=")){
		    		  sQuery.append(" equals ").append("'" + psVO.getDateFrom() + "'").append(", ");
		    	  }
		    	  else if(psVO.getDateOperator().equals("BET") && psVO.getDateTo() != null && psVO.getDateTo().trim().length() != 0){
		    		  sQuery.append(" between ").append("'" + psVO.getDateFrom() + "'").append(" and ").append("'" + psVO.getDateTo() + "'").append(", ");
		    	  } else if(psVO.getDateOperator().equals(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS)) {
		    		  sQuery.append(" By ").append("'Last 6 Months'").append(", ");
		    	  } else if(psVO.getDateOperator().equals(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS)) {
		    		  sQuery.append(" By ").append("'Last 30 Days'").append(", ");
		    	  } else if(psVO.getDateOperator().equals(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS)) {
		    		  sQuery.append(" By ").append("'Last 7 Days'").append(", "); 
		    	  }
		      }
		      //Electronic and Manual check-box
		      if (psVO.getReportType()!=null && !psVO.getReportType().equals("I") && psVO.getElectronicValueSelected().trim().equalsIgnoreCase("true") && psVO.getManualValueSelected().trim().equalsIgnoreCase("false")){
		 		 sQuery.append("Entry Method equals ").append("'Electronic'").append(", ");
		 	  }
		      else if (psVO.getReportType()!=null && !psVO.getReportType().equals("I") && psVO.getElectronicValueSelected().trim().equalsIgnoreCase("false") && psVO.getManualValueSelected().trim().equalsIgnoreCase("true")){
				 sQuery.append("Entry Method equals ").append("'Manual'").append(", ");
			  }
		      
		    //Entered by check-box
		      if (psVO.getInternalValueSelected().trim().equalsIgnoreCase("true") && psVO.getExternalValueSelected().trim().equalsIgnoreCase("false")){
		 		 sQuery.append("Entered By equals ").append("'Internal User'").append(", ");
		 	  }
		      else if (psVO.getInternalValueSelected().trim().equalsIgnoreCase("false") && psVO.getExternalValueSelected().trim().equalsIgnoreCase("true")){
				 sQuery.append("Entered By equals ").append("'External User'").append(", ");
			  }
		      
		      //Event Status new/initial or update
		      if (psVO.getEventStatusInitialSelected().trim().equalsIgnoreCase("true") && psVO.getEventStatusUpdateSelected().trim().equalsIgnoreCase("false")){
		    		 sQuery.append("Document State equals ").append("'New/Initial'").append(", ");
		    	  }
		      else if (psVO.getEventStatusInitialSelected().trim().equalsIgnoreCase("false") && psVO.getEventStatusUpdateSelected().trim().equalsIgnoreCase("true")){
		 		 sQuery.append("Document State equals ").append("'Update'").append(", ");
		 	  }
		      
		      if (psVO.getDocumentCreateSelected() != null && !psVO.getDocumentCreateSelected().equals("")){
		   		 sQuery.append("Event Created By User equals ").append("'" + psVO.getDocumentCreateFullNameSelected() + "'").append(", ");
		   	  }
		   	 if (psVO.getDocumentUpdateSelected() != null && !psVO.getDocumentUpdateSelected().equals("")){
		   		 sQuery.append("Event Last Updated By User equals ").append("'" + psVO.getDocumentUpdateFullNameSelected() + "'").append(", ");
		   	  }
		   	 
		   	if(psVO.getProviderFacilitySelected() != null && psVO.getProviderFacilitySelected().trim().length() != 0){
		   	 String codeDescPF = cache.getDescForCode("NBS_REP_ORD_TYPE", psVO.getProviderFacilitySelected());
		   	sQuery.append(codeDescPF).append(" equals ");
		   	 if(psVO.getProviderFacilitySelected().equals(PersonSearchVO.ORDERING_FACILITY)){
		   	   String desc = psVO.getOrderingFacilityDescSelected();
		   		
		   		String[] dArray = desc.split("<br>");
				
				if(dArray.length>0)
					sQuery.append("'" + dArray[0] + "'").append(", ");
		          } 
		     else if(psVO.getProviderFacilitySelected().equals(PersonSearchVO.ORDERING_PROVIDER)){
		    	 String desc = psVO.getOrderingProviderDescSelected();
		    		
		    		String[] dArray = desc.split("<br>");
		 		
		 		if(dArray.length>0)
		 			sQuery.append("'" + dArray[0]+ "'").append(", ");
		           } 
		           
		     else if(psVO.getProviderFacilitySelected().equals(PersonSearchVO.REPORTING_FACILITY)){
		    	 String desc = psVO.getReportingFacilityDescSelected();
		    		
		    		String[] dArray = desc.split("<br>");
		 		
		 		if(dArray.length>0)
		 			sQuery.append("'" + dArray[0]+ "'").append(", ");
		           } 
		      
		     else if(psVO.getProviderFacilitySelected().equals(PersonSearchVO.REPORTING_PROVIDER)){
		    	 String desc = psVO.getReportingProviderDescSelected();
		    		
		    		String[] dArray = desc.split("<br>");
		 		
		 		if(dArray.length>0)
		 			sQuery.append("'" + dArray[0] + "'").append(", ");
		           } 
		   	}
		 	if(psVO.getInvestigatorSelected() != null && psVO.getInvestigatorDescSelected() != null && psVO.getInvestigatorDescSelected().trim().length() != 0){
		 		 String desc = psVO.getInvestigatorDescSelected();


		 		String[] dArray = desc.split("<br>");
				
				if(dArray.length>0)
					sQuery.append("Investigator equals ").append("'" + dArray[0] + "'").append(", ");
		 	}
		 	if(psVO.getInvestigationStatusSelected() != null && psVO.getInvestigationStatusSelected().trim().length() != 0){
		 		//PHC_IN_STS
		 		sQuery.append("Investigation Status equals ").append("'" +	cache.getDescForCode("PHC_IN_STS", psVO.getInvestigationStatusSelected())+ "'").append(", ");
		 	}
			if (psVO.getOutbreakNameSelected() != null && psVO.getOutbreakNameSelected().length > 1
					|| (psVO.getOutbreakNameSelected() != null && psVO.getOutbreakNameSelected().length == 1
							&& !psVO.getOutbreakNameSelected()[0].trim().equals(""))) {
				String[] cArray = psVO.getOutbreakNameSelected();
				String prefix = "";
				sQuery.append("Outbreak Name in ").append("(");
				for (int i = 0; i < (cArray.length); i++) {
					if(cArray[i].equals(""))
						continue;
					sQuery.append(prefix);
					prefix = ", ";
					sQuery.append("'" + cache.getDescForCode("OUTBREAK_NM", cArray[i]) + "'");
				}
				sQuery.append(")").append(", ");
			}
		 	if(psVO.getCaseStatusListValuesSelected() != null && psVO.getCaseStatusListValuesSelected().trim().length() != 0){
		 		//PHC_IN_STS
		 		String caseStatus = psVO.getCaseStatusListValuesSelected().trim();
		 		if(psVO.getCaseStatusListValuesSelected().contains("ed Values:")){
		 			caseStatus = caseStatus.replace("ed Values:", "");
		 		}
				if(psVO.getCaseStatusListValuesSelected().contains("UNASSIGNED") && 
						!((psVO.getCaseStatusCodedValuesSelected() != null)
								&& (psVO.getCaseStatusCodedValuesSelected().trim().length() != 0)
								)){
					caseStatus = caseStatus.replace(", UNASSIGNED", "Unassigned");
					sQuery.append("Case Status equals ").append("'" +	caseStatus.trim() + "'").append(", ");
				}
					
				else
					if(psVO.getCaseStatusListValuesSelected().contains("UNASSIGNED"))
					{
						caseStatus = caseStatus.replace(", UNASSIGNED", ", Unassigned");
						String[] cArray = caseStatus.split(",");
						String prefix = "";
						sQuery.append("Case Status in ").append("(");
						for(int i = 0; i< (cArray.length);i++){
							sQuery.append(prefix);
							  prefix = ", ";
							sQuery.append("'" +	cArray[i].trim() + "'");
						}
						sQuery.append(")").append(", ");
					}
					else if(caseStatus.length() != 0)
					{
						String[] cArray = caseStatus.split(",");
						String prefix = "";
						sQuery.append("Case Status in ").append("(");
						for(int i = 0; i< (cArray.length);i++){
							sQuery.append(prefix);
							  prefix = ", ";
							sQuery.append("'" +	cArray[i].trim() + "'");
						}
						sQuery.append(")").append(", ");
					}
		 		
		 	}
		 	
		 	if(psVO.getCurrentProcessStateValuesSelected() != null && psVO.getCurrentProcessStateValuesSelected().trim().length() != 0){
		 		//PHC_IN_STS
		 		String currentStatus = psVO.getCurrentProcessStateValuesSelected().trim();
		 		if(psVO.getCurrentProcessStateValuesSelected().contains("ed Values:")){
		 			currentStatus = currentStatus.replace("ed Values:", "");
		 		}
				if(psVO.getCurrentProcessStateValuesSelected().contains("UNASSIGNED") && 
						!((psVO.getCurrentProcessCodedValuesSelected() != null)
								&& (psVO.getCurrentProcessCodedValuesSelected().trim().length() != 0)
								)){
					currentStatus = currentStatus.replace(", UNASSIGNED", "Unassigned");
					sQuery.append("Current Processing Status equals ").append("'" +	currentStatus.trim() + "'").append(", ");
				}
					
				else
					if(psVO.getCurrentProcessStateValuesSelected().contains("UNASSIGNED"))
					{
						currentStatus = currentStatus.replace(", UNASSIGNED", ", Unassigned");
						String[] cArray = currentStatus.split(",");
						String prefix = "";
						sQuery.append("Current Processing Status in ").append("(");
						for(int i = 0; i< (cArray.length);i++){
							sQuery.append(prefix);
							  prefix = ", ";
							sQuery.append("'" +	cArray[i].trim() + "'");
						}
						sQuery.append(")").append(", ");
					}
					else if(currentStatus.length() != 0)
					{
						String[] cArray = currentStatus.split(",");
						String prefix = "";
						sQuery.append("Current Processing Status in ").append("(");
						for(int i = 0; i< (cArray.length);i++){
							sQuery.append(prefix);
							  prefix = ", ";
							sQuery.append("'" +	cArray[i].trim() + "'");
						}
						sQuery.append(")").append(", ");
					}
		 		
		 	}
		 	if(psVO.getNotificationValuesSelected() != null && psVO.getNotificationValuesSelected().trim().length() != 0){
		 		//PHC_IN_STS
		 		String NotiStatus = psVO.getNotificationValuesSelected().trim();
		 		if(psVO.getNotificationValuesSelected().contains("ed Values:")){
		 			NotiStatus = NotiStatus.replace("ed Values:", "");
		 		}
				if(psVO.getNotificationValuesSelected().contains("UNASSIGNED") && 
						!((psVO.getNotificationCodedValuesSelected() != null)
								&& (psVO.getNotificationCodedValuesSelected().trim().length() != 0)
								)){
					NotiStatus = NotiStatus.replace(", UNASSIGNED", "Unassigned");
					sQuery.append("Notification Status equals ").append("'" +	NotiStatus.trim() + "'").append(", ");
				}
					
				else
					if(psVO.getNotificationValuesSelected().contains("UNASSIGNED"))
					{
						NotiStatus = NotiStatus.replace(", UNASSIGNED", ", Unassigned");
						String[] cArray = NotiStatus.split(",");
						String prefix = "";
						sQuery.append("Notification Status in ").append("(");
						for(int i = 0; i< (cArray.length);i++){
							sQuery.append(prefix);
							  prefix = ", ";
							sQuery.append("'" +	cArray[i].trim() + "'");
						}
						sQuery.append(")");
					}
					else if(NotiStatus.length() != 0)
					{
						String[] cArray = NotiStatus.split(",");
						String prefix = "";
						sQuery.append("Notification Status in ").append("(");
						for(int i = 0; i< (cArray.length);i++){
							sQuery.append(prefix);
							  prefix = ", ";
							sQuery.append("'" +	cArray[i].trim() + "'");
						}
						sQuery.append(")");
					}
		 		
		 	}
		 	
		 	//Resulted Test
			if (!psVO.getResultedTestDescriptionWithCodeValue().isEmpty() || !psVO.getTestDescription().isEmpty()) {
				

				String resultedTestDescriptionWithCode = psVO.getResultedTestDescriptionWithCodeValue().trim();
				String specialCharacter;

				if (resultedTestDescriptionWithCode.indexOf("'") > 0) {
					specialCharacter = "'";
					resultedTestDescriptionWithCode = replaceCharacters(resultedTestDescriptionWithCode,
							specialCharacter, "''");
				}
				
				String testDescription = psVO.getTestDescription().trim();

				if (testDescription.indexOf("'") > 0) {
					specialCharacter = "'";
					testDescription = replaceCharacters(testDescription,
							specialCharacter, "''");
				}
				String description="";
				
				if(!testDescription.isEmpty()){
					//int index = testDescription.lastIndexOf("(");
					description=testDescription.trim();
				
				}else
					description=resultedTestDescriptionWithCode;
				
				if(description != null && psVO.getResultedTestCodeDropdown() != null){
				sQuery.append("Resulted Test ");
				String codeDescDropdown = cache.getDescForCode("SEARCH_TEXT", psVO.getResultedTestCodeDropdown());
					sQuery.append(codeDescDropdown.toLowerCase());
				
				sQuery.append(" '"+description+"'").append(", ");
				}
			}
			
			//Codeed Result/Organism
				if (!psVO.getCodeResultOrganismDescriptionValue().isEmpty() || !psVO.getResultDescriptionValue().isEmpty()) {
					

					String codeResultOrganismDescriptionValue = psVO.getCodeResultOrganismDescriptionValue().trim();
					String specialCharacter;

					if (codeResultOrganismDescriptionValue.indexOf("'") > 0) {
						specialCharacter = "'";
						codeResultOrganismDescriptionValue = replaceCharacters(codeResultOrganismDescriptionValue,
								specialCharacter, "''");
					}
					
					String resultDescriptionValue = psVO.getResultDescriptionValue().trim();

					if (resultDescriptionValue.indexOf("'") > 0) {
						specialCharacter = "'";
						resultDescriptionValue = replaceCharacters(resultDescriptionValue,
								specialCharacter, "''");
					}
					String description="";
					
					if(!resultDescriptionValue.isEmpty()){
						
						description=resultDescriptionValue.trim();
					
					}else
						description=codeResultOrganismDescriptionValue;
					
					if(description != null && psVO.getCodeResultOrganismDropdown() != null){
					sQuery.append("Coded Result/Organism ");
					String codeDescDropdown = cache.getDescForCode("SEARCH_TEXT", psVO.getCodeResultOrganismDropdown());
						sQuery.append(codeDescDropdown.toLowerCase());
					
					sQuery.append(" '"+description+"'").append(", ");
					}
				}
		  	}
	   
      
		        return sQuery.toString().replace("Program Area in (),", "");
		    } //buildSearchCriteriaString

		   /**
		      * Formats a String by removing specified characters with another set of specified characters
		      * @param   toBeRepaced  is the characters to be replaced
		      * @param   specialCharacter
		      * @param   replacement  are the characters to replace the characters being removed
		      * @return  String with characters replaced.
		      */
		     private static String replaceCharacters(String toBeRepaced, String specialCharacter, String replacement) {
		     	int s = 0;
		         int e = 0;
		         StringBuffer result = new StringBuffer();
		     	try{
		 	      
		 	
		 	      while ((e = toBeRepaced.indexOf(specialCharacter, s)) >= 0) {
		 	          result.append(toBeRepaced.substring(s, e));
		 	          result.append(replacement);
		 	          s = e+specialCharacter.length();
		 	      }
		 	      result.append(toBeRepaced.substring(s));
		     	}catch(Exception ex){
		     		logger.error("Exception ="+ex.getMessage(), ex);
		     		throw new NEDSSSystemException(ex.toString(), ex);
		     	}
		 	      return result.toString();
		     }
		   private static String getStateDescTxt(String sStateCd) {

		      CachedDropDownValues srtValues = new CachedDropDownValues();
		      TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
		      String desc = "";

		      if (sStateCd != null && treemap.get(sStateCd) != null) {
		         desc = (String) treemap.get(sStateCd);

		      }
		      return desc;
		   }

		   private static String getRaceDescTxt(String raceCode, String descTxt) {

		      String returnStr = "";
		      if (raceCode != null) {
		         if (!raceCode.equals("") && descTxt != null) {
		            String findRace = descTxt.substring(descTxt.indexOf(raceCode));
		            String s = findRace.substring(findRace.indexOf("$") + 1);
		            return s.substring(0, s.indexOf("|") + 1);
		         }
		      }
		      return returnStr;
		   }

		   private static String getRoleDescTxt(String role, String descTxt) {

		      String returnStr = "";
		      if (role != null) {
		         if (!role.equals("") && descTxt != null) {
		            String findRole = descTxt.substring(descTxt.indexOf(role));
		            String s = findRole.substring(findRole.indexOf("$") + 1);
		            return s.substring(0, s.indexOf("|") + 1);
		         }
		      }
		      return returnStr;
		   }
		   
		    public static void findPeople(PersonSearchForm psForm, HttpSession session,
				    HttpServletRequest request, boolean homePageSrch, boolean bEntitySearch)
	    {
	    	ArrayList<?> personList = new ArrayList<Object> ();
	    	PatientSearchVO psVO = null;
	    	Boolean patientSearch = false;
	    	int totalCountPersonList = 0;
	    	String reportT = (String)psForm.getAttributeMap().get("reportType");
			if(reportT == null || reportT.trim().length() == 0 || reportT.equalsIgnoreCase("N"))
				psForm.clearAll();
			if(reportT != null && reportT.equalsIgnoreCase("N")) 
				patientSearch = true;
			
	    	SearchResultPersonUtil srpUtil = new SearchResultPersonUtil();
	    	
	        //civil00018034 To Implement PatientSearch Functionality from HomePage (SimpleSearch), handle here
	    	if(homePageSrch) {
	    		HomePageForm hForm = (HomePageForm)request.getSession().getAttribute("homepageForm");
	    		if(hForm!=null)
	    			psVO = (PatientSearchVO) hForm.getPatientSearchVO();
	    		//If BirthTime entered as part of search, Split it to 3 different Fields (to fit old screen format)
	    		if(psVO!=null)
	    			srpUtil._populateBirthTime(psVO);


	    	} else {
	    		psVO = (PatientSearchVO)psForm.getPersonSearch();//From the UI
	    		psForm.setOldPersonSearch(psVO);

	    	}
	    	
			if(patientSearch && psVO != null){
				psVO.setReportType("N");;
			}
			
	    	if (psVO != null && (psVO.getBirthTimeDay() != null && psVO.getBirthTimeDay().trim().length() != 0) &&
	    		 (psVO.getBirthTimeMonth() != null && psVO.getBirthTimeMonth().trim().length() != 0) &&
	    		 (psVO.getBirthTimeYear() != null && psVO.getBirthTimeYear().trim().length() != 0))
		     {
	    		psVO.setBirthTime(PersonUtil.getBirthDate(psVO.getBirthTimeMonth(),
		                                                 psVO.getBirthTimeDay(),
		                                                 psVO.getBirthTimeYear()));
	    		srpUtil.setAgeAndUnits(psVO);
		     }
		     if (psVO != null && psVO.getLocalID() != null && !psVO.getLocalID().trim().equals(""))
		     {
		    	 psVO.setOldLocalID(psVO.getLocalID());
		    	 psVO.setPatientIDOperator("IN");
		    	 srpUtil.replaceDisplayLocalIDWithActualLocalID(psVO);
		     }
		     //Trim the Act ID for empty spaces

		     if(psVO != null && psVO.getActId() != null && !psVO.getActId().trim().equals("")) {
		    	 srpUtil.trimEventID(psVO);
		     }

		     String contextAction = request.getParameter("ContextAction");

		     if (contextAction == null){
		    	 contextAction = (String) request.getAttribute("ContextAction");
		     }



		       //Set temperory for mergeperson
		     if (request.getParameter("Mode1") != null &&
		         request.getParameter("Mode1").equals("ManualMerge"))
		     {
		    	 psVO.setActive(true);
		    	 psVO.setLogicalDeleted(false);
		    	 psVO.setSuperceded(false);
		     }
		     if(homePageSrch) {
		     	session.setAttribute("DSSearchCriteria", psVO);
		     }

		     NBSContext.store(session, "DSSearchCriteria", psVO);

			if (psVO != null)
			{
				String existing = request.getParameter("ContextAction") == null ? (String)request.getParameter("ContextAction") : null;
		        boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
                 ArrayList list = new ArrayList();
		        if("ReturnToSearchResults".equalsIgnoreCase(existing)|| initLoad){
		           	list = (ArrayList)session.getAttribute("personList");
		           	NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		           	boolean checkPermission = secObj.getPermission(NBSBOLookup.PATIENT,NBSOperationLookup.VIEWWORKUP);
		           	//boolean checkPermissionInvestigation = secObj.getPermission(NBSBOLookup.INVESTIGATION,NEDSSConstants.VIEW, programArea, jurisdiction, sharedInd);
		           	boolean checkPermissionInvestigation =true;

		           	if(psVO.getReportType()!=null && psVO.getReportType().equalsIgnoreCase("I")){
		           		srpUtil.setDisplayInfoInvestigation(list,checkPermissionInvestigation);
		           		try {
							srpUtil.sortInvs(psForm, list, false, request);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		           	}
		           	else
		           		if(psVO.getReportType()!=null && psVO.getReportType().equalsIgnoreCase("LR")){
			           		srpUtil.setDisplayInfoLaboratoryReport(list,checkPermissionInvestigation, request);
			           		try {
								srpUtil.sortObservations(psForm, list, false, request);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			           		}
			           	else{
			           		srpUtil.setDisplayInfo(list,checkPermission, bEntitySearch, request);
					           	try{
					           		srpUtil.sortPatientLibarary(psForm, list, false, request);
					           	}catch(Exception e){
				
					           	}
			           	}
		        }else{
				    try
				    {
				    	NedssUtils nedssUtils = null;
					    MainSessionCommand msCommand = null;
					    String sBeanJndiName = "";
					    String sMethod = "";
					    Object[] oParams = null;
					    sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
					    sMethod = "findPatient";
					    oParams = new Object[]{
					    		psVO, new Integer(propUtil.getNumberOfRows()),
					    		new Integer(0) };

						MainSessionHolder holder = new MainSessionHolder();
						msCommand = holder.getMainSessionCommand(session);
				        ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName,  sMethod, oParams);
				        personList = (ArrayList<?> )arrList.get(0);

			        	if(personList.size()>1){//finalQuery was saved in position 1
				        	
				        	String finalQuery = (String)personList.get(1);
				        	//Storing the query in the session son after the event search results are shown
				        	//the query is available in case the user decides to save the queue
				        	request.getSession().setAttribute("finalQueryString", finalQuery);
				        	personList.remove(1);
				        }
				    }
				    catch (Exception e)
				    {
			    		logger.warn("Exception in PersonSearchSubmit: " + e.getMessage());
			    		e.printStackTrace();

				    }
				    if( personList != null && personList.size() > 0  &&  personList.get(0)!=null){
				    	DisplayPersonList displayPersonList = (DisplayPersonList) personList.get(0);
				    	totalCountPersonList = displayPersonList.getTotalCounts();
			        	list = displayPersonList.getList();
						try{
						      NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
						          "NBSSecurityObject");
							boolean checkSummaryPermission = secObj.getPermission(NBSBOLookup.PATIENT,
							          NBSOperationLookup.VIEWWORKUP);
							
							if(psVO.getReportType()!=null && psVO.getReportType().equalsIgnoreCase("I")){
								
								srpUtil.setDisplayInfoInvestigation(list,checkSummaryPermission);
								srpUtil.sortInvs(psForm, list, false, request);
							}else
								if(psVO.getReportType()!=null && psVO.getReportType().equalsIgnoreCase("LR")){
									psForm.getPersonSearch().setReportType("LR");
									psForm.getAttributeMap().put("reportType","LR");
									srpUtil.setDisplayInfoLaboratoryReport(list,checkSummaryPermission, request);
									srpUtil.sortObservations(psForm, list, false, request);
								}
								else{
								
									srpUtil.setDisplayInfo(list,checkSummaryPermission, bEntitySearch, request);
									srpUtil.sortPatientLibarary(psForm, list, false, request);
								}
							
							psForm.setIntqueueCount(displayPersonList.getTotalCounts());
							//psForm.setPatientVoCollection(list);
							
							NBSContext.store(request.getSession() ,NBSConstantUtil.DSPersonList,list);
							NBSContext.store(request.getSession() ,NBSConstantUtil.DSPersonListFull,list);
													
							psForm.initializeDropDowns(list);
						
						/*	psVO.setJurisdictionsDD(psForm.getJurisdictionsDD());
							psVO.setNotifications(psForm.getNotifications());
							psVO.setCaseStatuses(psForm.getCaseStatusesDD());
							
							psVO.setConditions(psForm.getConditions());
							psVO.setDateFilterList(psForm.getStartDateDropDowns());*/
							psForm.getAttributeMap().put("notificationDDList", psForm.getNotifications());
							psForm.getAttributeMap().put("investigatorDDList", psForm.getInvestigators());
							psForm.getAttributeMap().put("CaseStatusDDList", psForm.getCaseStatusesDD());
							psForm.getAttributeMap().put("jurisdictionDDList", psForm.getJurisdictionsDD());
							psForm.getAttributeMap().put("startDateDDList", psForm.getStartDateDropDowns());
							psForm.getAttributeMap().put("conditionDDList", psForm.getConditions());
							
							psForm.getAttributeMap().put("observationTypeDDList", psForm.getObservationTypesDD());
							psForm.getAttributeMap().put("descriptionDDList", psForm.getDescriptionDD());
							psForm.getAttributeMap().put("conditionDD", psForm.getConditionDD());
							
							psForm.getAttributeMap().put("InvestigatorsCount",new Integer(psForm.getInvestigators().size()));
							psForm.getAttributeMap().put("JurisdictionsCount",new Integer(psForm.getJurisdictionsDD().size()));
							psForm.getAttributeMap().put("ConditionsCount",new Integer(psForm.getConditions().size()));
							psForm.getAttributeMap().put("caseStatusCount",new Integer(psForm.getCaseStatusesDD().size()));
							psForm.getAttributeMap().put("dateFilterListCount",new Integer(psForm.getStartDateDropDowns().size()));
							psForm.getAttributeMap().put("notificationsCount",new Integer(psForm.getNotifications().size()));
							
							psForm.getAttributeMap().put("DescriptionCount",new Integer(psForm.getDescriptionDD().size()));
							psForm.getAttributeMap().put("observationTypeCount",new Integer(psForm.getObservationTypesDD().size()));
							psForm.getAttributeMap().put("ConditionsDDCount",new Integer(psForm.getConditionDD().size()));
							
							psForm.getAttributeMap().put("TOTALCOUNT", psForm.getIntqueueCount());
							//session.setAttribute("notificationDDList", psForm.getNotifications());
							NBSContext.store(session, NBSConstantUtil.DSAttributeMap, psForm.getAttributeMap());
							//session.setAttribute("attributeMap", psForm.getAttributeMap());
						}catch(Exception e){

				        }
				    }

		        }

		        //set temperory for merge person
		        session.setAttribute("PerMerge",personList);
		        psForm.getAttributeMap().put("queueCount", String.valueOf(list.size()));
		        String reportType = psForm.getPersonSearch().getReportType();
		        
		        if(reportType!=null && (reportType.equalsIgnoreCase("LR") || reportType.equalsIgnoreCase("I")))
		        	request.setAttribute("PageTitle", "Event Search Results ");
		        else
		        	request.setAttribute("PageTitle", "Search Results ");
		        request.setAttribute("personList", list);
		        //session.setAttribute("personList", list);
		        //psForm.getAttributeMap().get("TOTALCOUNT");
		        String error = null;
		        int totalAllowedRecords = 0;
		        int totalNumberOfRecords = 0;
		        if (propUtil.getNumberOfRows() != 0) {
		        	totalAllowedRecords =  propUtil.getNumberOfRows();
	    		} else
	    			throw new NEDSSSystemException(
	    					"Expected the number of patient search "
	    							+ "results returned to the caller defined in the property file.");
	    		if(reportType!=null && reportType.equalsIgnoreCase("LR"))
	    			if (propUtil.getLabNumberOfRows() != 0) {
			        	totalAllowedRecords =  propUtil.getLabNumberOfRows();
		    		}
				if(psForm.getAttributeMap().get("TOTALCOUNT")!= null)
		        	 totalNumberOfRecords = (int)psForm.getAttributeMap().get("TOTALCOUNT");
				else
					totalNumberOfRecords = totalCountPersonList;
				if(totalNumberOfRecords > totalAllowedRecords )
		        	error = "error";
				else 
					error = "N";
				psForm.getAttributeMap().put("totalRecords",error);
		        request.setAttribute("totalRecords",error);
		        
		        request.setAttribute("queueCount", list.size());
		        //request.setAttribute("personSearchForm", psForm);
		    	//To make sure SelectAll is checked, see if no criteria is applied
				if(psForm.getSearchCriteriaArrayMap().size() == 0)
					request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));		
		        
		        //No event search
		        if(reportType==null || reportType.isEmpty())
		        	psForm.getAttributeMap().put("reportType","N");
		        else//Lab report or Morbidity report or Case report
		        	 if(reportType.equalsIgnoreCase("MR") || reportType.equalsIgnoreCase("CR"))
		 	        	psForm.getAttributeMap().put("reportType","LMC");
		        	 else if (reportType.equalsIgnoreCase("LR")) psForm.getAttributeMap().put("reportType","LR");
		 	         else//Investigation
		 	        	psForm.getAttributeMap().put("reportType", psForm.getPersonSearch().getReportType());
		        
		        if(homePageSrch) {
		        	session.setAttribute("DSSearchResults", personList);
		        	request.setAttribute("homePageSrch", "true");
		        }
		     //   NBSContext.store(session, "DSSearchCriteria", personList);
			    NBSContext.store(session, "DSSearchResults", personList);
			}
	    }	   


}