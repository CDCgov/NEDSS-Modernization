package gov.cdc.nedss.webapp.nbs.action.elr;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.elr.helper.*;

import java.io.*;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
 * Title:        ELRActivityLogSubmit
 * Description:  This is a submit action class for submmiting the conditions to generate ELR activity log report.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */

public class ELRActivityLogSubmit
    extends Action {

  //For logging
  static final LogUtils logger = new LogUtils(ELRActivityLogSubmit.class.
                                              getName());
  /**
   * This method submmits the conditions, such as time, success or failure conditions,
   * to generate ELR activity log report.
   * @param ActionMapping
   * @param ActionForm
   * @param HttpServletRequest
   * @param HttpServletResponse
   * @return ActionForward
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
	  boolean successSelected = false;
	  boolean bothFailPassSelected = false;
	  
    HttpSession session = request.getSession();

    if (session == null) {
      logger.fatal("error no session");

      throw new ServletException("error no session");
    }

    //String isToday = (String)request.getParameter("IsToday");
    String fromDate = (String) request.getParameter("strFromDate");
    String fromTime = (String) request.getParameter("strFromTime");
    String toDate = (String) request.getParameter("strToDate");
    String toTime = (String) request.getParameter("strToTime");
    String success = (String) request.getParameter("strSuccess");
    String successMigratedDetails = (String) request.getParameter(
        "strSuccessMigratedDetailsNode");
    String unsuccessfullyMigratedNode = (String) request.getParameter(
        "strUnsuccessfullyMigratedNode");
    String sortOrder = (String) request.getParameter("strSortResults");
    String patientName = (String) request.getParameter("strLastName");
    String msgId = (String) request.getParameter("strMsgId");
    String accessionNumber = (String) request.getParameter("strAccessionNumber");
    String observationID = (String) request.getParameter("strObservationID");
    String reportingFacility = (String) request.getParameter(
        "strReportingFacility");

    logger.info("fromDate = " + fromDate +
                       " | fromTime = " + fromTime +
                       " | toDate = " + toDate +
                       " | toTime = " + toTime +
                       " | success = " + success +
                       " | successMigratedDetails = " + successMigratedDetails +
                       " | unsuccessfullyMigratedNode = " +
                       unsuccessfullyMigratedNode +
                       " | sortResults = " + sortOrder +
                       " | lastName = " + patientName +
                       " | msgId = " + msgId +
                       " | accessionNumber = " + accessionNumber +
                       " | observationID = " + observationID +
                       " | reportingFacility = " + reportingFacility);


    ELRActivityLogSearchDT elrActivityLogSearchDT = new ELRActivityLogSearchDT();

    ArrayList<Object> statusList = new ArrayList<Object> ();
    ArrayList<Object> statusListForDisplay = new ArrayList<Object> ();

    //setting the statusCD
    if (success != null && success.equalsIgnoreCase("true")) {
      statusList.add("S");
      statusListForDisplay.add("Success");
      successSelected = true;
    }

    if (successMigratedDetails != null &&
        successMigratedDetails.equalsIgnoreCase("true")) {
      statusList.add("I");
      successSelected = true;

      statusListForDisplay.add("Informative");
    }
    if (unsuccessfullyMigratedNode != null &&
        unsuccessfullyMigratedNode.equalsIgnoreCase("true")) {
      statusList.add("F");
      statusListForDisplay.add("Failure");
      if(successSelected)
        bothFailPassSelected = true;
      successSelected = false;
    }

    elrActivityLogSearchDT.setStatusCd(statusList);

    if (sortOrder != null && sortOrder.length() > 0) {
      elrActivityLogSearchDT.setSortOrder(sortOrder.trim());
    }
    if (patientName != null && patientName.length() > 0) {
      elrActivityLogSearchDT.setSubjectNm(patientName.trim());
    }
    if (accessionNumber != null & accessionNumber.length() > 0) {
      elrActivityLogSearchDT.setFillerNbr(accessionNumber.trim());
    }
    if (reportingFacility != null && reportingFacility.length() > 0) {
      elrActivityLogSearchDT.setReportingFacNm(reportingFacility);
    }
    if (msgId != null && msgId.length() > 0) {
      elrActivityLogSearchDT.setMsgObservationUid(new Long(msgId.trim()));
    }
    if (observationID != null && observationID.length() > 0) {
      elrActivityLogSearchDT.setOdsObservationUid(observationID.trim());
    }

    //setting processStartTime
    String fromDateTime = "";
    String toDateTime = "";
    try {
      //set a default From Date & Time if no date or time entered
      if (fromDate == null || fromDate.length() == 0) {
        Date defaultDate = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDefaultDate = formatter.format(defaultDate);
        String time = " 00:00:00.000000000";
        formattedDefaultDate += time;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
        Date dbDate = df.parse(formattedDefaultDate);
        Timestamp ts = new java.sql.Timestamp(dbDate.getTime());
        elrActivityLogSearchDT.setProcessStartTime(ts);
        fromDateTime = formattedDefaultDate;
      }
      else {//convert the date retrieved to Timestamp
        String time = "";
        if (fromTime == null || fromTime.length() == 0) {
          time = "00:00:00.000000000";
        }
        else {
          time = fromTime.trim() + ":00.000000000";
        }

        String sDate = this.formatDate(fromDate.trim()) + " " + time;
        Timestamp ts = Timestamp.valueOf(sDate);
        elrActivityLogSearchDT.setProcessStartTime(ts);
        fromDateTime = sDate;
      }

      //setting processEndTime
      //set a default To Date & Time if no date or time entered to tomorrows date
      if (toDate == null || toDate.length() == 0) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,1);
        Date dateTomorrow = cal.getTime();
        StringBuffer tomorrow = new StringBuffer(formatter.format(dateTomorrow));
        tomorrow.append(" 00:00:00.000000000");
        Timestamp ts = Timestamp.valueOf(tomorrow.toString());
        elrActivityLogSearchDT.setProcessEndTime(ts);

      }
      else {//retrieve the ToDate & ToTime and convert to Timestamp
        String time = "";
        if (toTime == null || toTime.length() == 0) {
          Date defaultToTime = new Date(); //present day and time
          SimpleDateFormat formatter = new SimpleDateFormat(
              "HH:mm:ss.SSSSSSSSS");
          time = formatter.format(defaultToTime); ///present time
        }
        else {
          time = toTime.trim() + ":00.000000000";
        }
        String sDate = this.formatDate(toDate.trim()) + " " + time;
        Timestamp ts = Timestamp.valueOf(sDate);
        elrActivityLogSearchDT.setProcessEndTime(ts);
        toDateTime = sDate;
      }
    }
    catch (ParseException e) {
      logger.fatal("Error occured trying to parse string to Date!!!");
      e.printStackTrace();
      throw new ServletException("Error: ParseException while attempting to convert to and from time string to date objects \n");
    }

    ArrayList<?> reportList = (ArrayList<?> ) sendProxyToEJB(elrActivityLogSearchDT,
        session);
    //ArrayList<Object> countList = setResultList(resultList,request);
    setResultList((ArrayList<?> )reportList.get(0),request);
    setELRCounts((ArrayList<?> )reportList.get(1), request , successSelected , bothFailPassSelected);
    setDateRange(elrActivityLogSearchDT,request);
    setRecordsDisplayed(elrActivityLogSearchDT,request);

    return mapping.findForward("next");
  }

  /**
   * This is a private method to send conditions, such as fromDate, toDate and statusList to NotificationProxyEJB
   * to create a ELR activity log report.
   * This method calls getActivityLogReport() method in ObservationProxyEJB
   * through MainSessionCommandEJB
   * @param String fromDate
   * @param String toDate
   * @param ArrayList<Object> statusList
   * @param HttpSession session
   * @return Collection<Object>  a colletion of ElrActivityLog reports
   */
  private Collection<?>  sendProxyToEJB(ELRActivityLogSearchDT elrLogDT,
                                    HttpSession session) {

    MainSessionCommand msCommand = null;
    MainSessionHolder mainSessionHolder = new MainSessionHolder();
    logger.info("ELRActivityLogSubmit -- sendProxyToEJB");

    try {

      String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
      String sMethod = "getActivityLogReport";
      Object[] oParams = {
          elrLogDT};
      msCommand = mainSessionHolder.getMainSessionCommand(session);

      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
                                              oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {

        ArrayList<?> elrReport = (ArrayList<?> ) resultUIDArr.get(0);
        logger.info(
            "get elrReport in ELRActivityLogSubmit: " +
            elrReport.size());

        return elrReport;
      }
      else {

        return null;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal("ERROR in ElrActivityLogSummit", e);

      return null;
    }
  }


  /**
 * This method sets the ELR counts to the request object.
 * @param ArrayList
 * @param HttpServletRequest
 * */
  private void setELRCounts(ArrayList<?>  countList, HttpServletRequest request , boolean successSelected ,boolean bothFailPassSelected){

    Integer unsuccessfulELRs = (Integer)countList.get(0);
    Integer successfulELRs = (Integer)countList.get(1);
    Integer totalELRs = null;

    if(bothFailPassSelected)
      totalELRs = new Integer(successfulELRs.intValue() + unsuccessfulELRs.intValue());
    else if(successSelected)
      totalELRs = successfulELRs;
    else
      totalELRs = unsuccessfulELRs;
    //Integer totalELRs = new Integer(successfulELRs.intValue() + unsuccessfulELRs.intValue());
    //successfulELRs = totalELRs - unsuccessfulELRs;
    request.setAttribute("unsuccessfulELRs",unsuccessfulELRs.toString());
    logger.info("unsuccessfulELRs:"+unsuccessfulELRs.toString());

    request.setAttribute("successfulELRs",successfulELRs.toString());
    logger.info("successfulELRs:"+successfulELRs.toString());

    request.setAttribute("totalELRs",totalELRs.toString());
    logger.info("totalELRs:"+totalELRs.toString());
    //set all to the request object
    //set back to false
    bothFailPassSelected = false;
    successSelected = false;
  }



  /**
 * This method sets the result list to the request object.
 * @param ArrayList
 * @param HttpServletRequest
 */
  private void setResultList(ArrayList<?>  resultList,HttpServletRequest request){
	  //start of display
	  Iterator<?>  resultIter = resultList.iterator();
	  StringBuffer str = new StringBuffer();
	  ArrayList<Object> countList = new ArrayList<Object> ();
	  //int successCount = 0;
	  //int failerCount = 0;
	  //int unknownStatusCount = 0;
	  while(resultIter.hasNext()){
		  try {
			  ElrActivityLogDT alDT = (ElrActivityLogDT) resultIter.next();

			  //set status
			  if (alDT.getStatusCd().equalsIgnoreCase("S")||alDT.getStatusCd().equalsIgnoreCase("I")) {
				  str.append("Success");

			  } else if (alDT.getStatusCd().equalsIgnoreCase("F")) {
				  str.append("Failure");
				  //failerCount++;
			  } else {
				  str.append(alDT.getStatusCd());
				  //unknownStatusCount++;
			  }
			  str.append("$");
			  //set Migrated Date/Time
			  str.append(formatDate(alDT.getProcessTime()));
			  str.append("$");
			  //set Message ID
			  if (alDT.getMsgObservationUid() == null)
				  str.append("- - - - - - - - -");
			  else
				  str.append(alDT.getMsgObservationUid().toString());
			  str.append("$");
			  //set Patient Name
			  if (alDT.getSubjectNm() == null)
				  str.append("- - - - - - - - -");
			  else
				  str.append(alDT.getSubjectNm());
			  str.append("$");
			  //set Accesssion Number
			  if (alDT.getFillerNbr() == null)
				  str.append("- - - - - - - - -");
			  else
				  str.append(alDT.getFillerNbr());
			  str.append("$");
			  //set Observation ID
			  if (alDT.getOdsObservationUid() == null)
				  str.append("- - - - - - - - -");
			  else
				  str.append(alDT.getOdsObservationUid());
			  str.append("$");
			  //Set Reporting Facility
			  if (alDT.getReportingFacNm() == null)
				  str.append("- - - - - - - - -");
			  else
				  str.append(alDT.getReportingFacNm());
			  str.append("$");
			  //set end of row character

			  //now iterate through all processMessages
			  //if(alDT.getStatusCd().equalsIgnoreCase("S"))
			  //successCount++;
			  Collection<Object>  processMessageColl = alDT.getProcessMessageCollection();
			  if (!alDT.getStatusCd().equalsIgnoreCase("S")){

				  if (processMessageColl != null && !processMessageColl.isEmpty()) {

					  //write first double bar
					  str.append("[[");
					  str.append("~");

					  Iterator<Object>  procMsgIter = processMessageColl.iterator();
					  while (procMsgIter.hasNext()) {
						  String procMsg = (String) procMsgIter.next();
						  //write msg
						  str.append("-");
						  str.append(procMsg);
						  str.append("!");
					  } //end inner while
					  str.append("~$]]|");
				  }
				  else {
					  str.append("|");
				  } //end if
			  }
			  else{
				  str.append("|");
			  }
		  } catch (Exception ex) {
			  logger.error("Exception encountered in ELRActivityLogSubmit.setResultList() " + ex.getMessage());
		  }
	  } //end first while
	  //countList.add(0, new Integer(successCount));
	  //countList.add(1, new Integer(failerCount));
	  //countList.add(2, new Integer(unknownStatusCount));
	  request.setAttribute("resultList",str.toString());
	  //return countList;
  }

    /**
     * This method sets the recordsDisplayed to the request object.
     * @param ELRActivityLogSearchDT
     * @param HttpServletRequest
     */
    private void setRecordsDisplayed(ELRActivityLogSearchDT elrActivityLogSearchDT, HttpServletRequest request){
      Collection<Object>  statusList = elrActivityLogSearchDT.getStatusCd();
     Iterator<Object>  statusIter = statusList.iterator();
      StringBuffer str = new StringBuffer();
      while(statusIter.hasNext()){
        String status = (String)statusIter.next();
        if(status.equalsIgnoreCase("S"))
          str.append("Successful migrated");
        else if(status.equalsIgnoreCase("I"))
          str.append("Successful migrated with Details");
        else if(status.equalsIgnoreCase("F"))
          str.append("Unsuccessful migrated");
        if(statusIter.hasNext())
          str.append(",");

      }
      //write to request object
      request.setAttribute("recordsDisplayed",str.toString());
    }


    /**
     * This method sets the date to the request object.
     * @param ELRActivityLogSearchDT
     * @param HttpServletRequest
     */
    private void setDateRange(ELRActivityLogSearchDT elrActivityLogSearchDT, HttpServletRequest request){
      Timestamp startTime = elrActivityLogSearchDT.getProcessStartTime();
      Timestamp endTime = elrActivityLogSearchDT.getProcessEndTime();

      StringBuffer str = new StringBuffer();
      str.append(formatDate(startTime));
      str.append(" to ");
      str.append(formatDate(endTime));
      //write to request object
      request.setAttribute("dateRange",str.toString());
      request.setAttribute("groupName","ELR Activity Log for "+str.toString());
}

  /**
   * This is a private method to format a time which will be displayed on the page.
   * It transforms a timestamp to a formatted string time. The format time will be "MM/dd/yyyy HH:mm"
   * @param Timestamp timestamp
   * @return String formatted time
   */
  private String formatDate(java.sql.Timestamp timestamp) {

	  Date date = null;
	  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	  try {
		  if (timestamp != null) {
			  date = new Date(timestamp.getTime());

		  }
	  } catch (Exception ex) {
		  logger.error("Exception encountered in ELRActivityLogSubmit.formatDate() " + ex.getMessage());
		  ex.printStackTrace();
	  }
	  if (date == null) {

		  return "";
	  }
	  else {

		  return formatter.format(date);
	  }
  }

  /**
   * This method converts a String date in the following format: yyyy-MM-dd
   * @param String string date
   * @return String formatted date
   */
  private String formatDate(String date) {
	  String formattedDate = "";
	  try {
		  String year = date.substring(6, date.length());
		  String month = date.substring(0, 2);
		  String day = date.substring(3, 5);

		  formattedDate = year + "-" + month + "-" + day;
	  } catch (Exception ex) {
		  logger.error("Exception encountered in CompareMergeLoad.formatDate(" + date + ") " + ex.getMessage());
		  ex.printStackTrace();
	  }
	  return formattedDate;
  }
}