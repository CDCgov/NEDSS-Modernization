package gov.cdc.nedss.webapp.nbs.action.summary;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.form.summary.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.FormQACode;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*;
import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.webapp.nbs.util.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;




public class SummaryReportSubmit extends Action {

 //For logging
  static final LogUtils logger = new LogUtils(SummaryReportSubmit.class.getName());

  public SummaryReportSubmit() {
  }

  /**
 * The method do the actions based on the Context and redirect to the appropriate page
 * and binds appropriate variables to request object.
 * @param mapping    the ActionMapping
 * @param form   the ActionForm
 * @param request  the HttpServletRequest
 * @param response  the HttpServletResponse
 * @return  ActionForward
 * @throws IOException
 * @throws ServletException
 */
  public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {
      HttpSession session = request.getSession(false);
      NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

      String strContextAction = request.getParameter("ContextAction");

      if (strContextAction == null)
      {
      	 strContextAction = (String) request.getAttribute("ContextAction");
       }
       else if(strContextAction.equalsIgnoreCase("Submit"))
       {

          logger.debug("before createHandler in submit action  ");
          try{
              this.editHandler(mapping,form,request,session,secObj);
             }
          catch (NEDSSAppConcurrentDataException ex)
          {
            logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ");
            return mapping.findForward("dataerror");
          }
       }
       else if(strContextAction.equalsIgnoreCase("SubmitAndSendNotification")){
        logger.debug("before createHandler in submit Notification action  ");

          try{
              this.editHandler(mapping,form,request,session,secObj);
              SummaryReportForm summaryReportForm = (SummaryReportForm)form;
              SummaryReportProxyVO summaryReportProxyVO = summaryReportForm.getProxy();
              NotificationProxyVO notificationProxyVO = this.setActrelationshipForNotification(summaryReportProxyVO);
              Long notificationUid = this.sendProxyToNotificationEJB(notificationProxyVO,session,request);
             }
          catch (NEDSSConcurrentDataException ex)
          {
            logger.fatal("ERROR - NEDSSConcurrentDataException, The data has been modified by another user, please recheck! ");
            return mapping.findForward("dataerror");
          }
          catch (Exception ex){
      		logger.error("Exception insummary Report Submit: " + ex.getMessage());
      		ex.printStackTrace();
          }
       }

      //need to process any parameters that come inside here
      Enumeration<?> params = request.getParameterNames();
      while(params.hasMoreElements())
      {
	String sParamName = (String)params.nextElement();
	request.setAttribute(sParamName,request.getParameter(sParamName));

      }


      return mapping.findForward(strContextAction);

  }
/**
 * This method edits the SummaryReportProxyVO objects and
 * call the method to send the object to the EJB
 * @param mapping   the ActionMapping
 * @param form   the ActionForm
 * @param request  the HttpServletRequest
 * @param session   the HttpSession
 * @param nbsSecurityObj   the NBSSecurityObj
 * @throws ServletException
 * @throws NEDSSAppConcurrentDataException
 */
 private void editHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			       HttpSession session, NBSSecurityObj nbsSecurityObj)
			throws ServletException,NEDSSAppConcurrentDataException
    {

        SummaryReportForm summaryReportForm = (SummaryReportForm)form;

        int tempID = -1;
        int totalCount = 0;

  try{

      // Select the proxy here
      SummaryReportProxyVO summaryReportProxyVO = summaryReportForm.getProxy();
      logger.info("before update summaryReportUid: "+ summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
      
      if(summaryReportProxyVO != null)
      {
         Collection<Object>  mainObservationCollection  = summaryReportProxyVO.getTheObservationVOCollection();
         logger.info("The size of the mainObservatioCollection  from the previous page.." +mainObservationCollection);
         Collection<Object>  OldActRelationshipCollection   = summaryReportProxyVO.getTheActRelationshipDTCollection();
         ////##!! System.out.println("The size of the old act relationship : "+OldActRelationshipCollection.size());
         Collection<Object>  helperColl = summaryReportForm.getReportCollection();
         logger.debug("The size of the helper collection...... " + helperColl.size());
         Collection<Object>  actRelationshipCollection   = new ArrayList<Object> (); // For the actrelashionship

      if (helperColl != null)
	{

            // Getting the SummaryReportForm ObservationVO from the SummaryReportProxyVO which is set while AddSummaryData action
            ObservationVO formSumRepObsVO =  getObservationVO(summaryReportProxyVO,"Summary_Report_Form");
            
            for (Iterator<Object> iter = helperColl.iterator(); iter.hasNext();)
	    {

                BatchEntryHelper bachentry = (BatchEntryHelper)iter.next();
                if(bachentry.getUid() != null && bachentry.getUid().longValue() > 0)
                {
                  
                  Collection<ObservationVO>  oldItemObservations = bachentry.getObservationVOCollection();
                 Iterator<ObservationVO>  oldObsIter =  oldItemObservations.iterator();
                  while(oldObsIter.hasNext())
                  {
                    ObservationVO oldObservationVO = (ObservationVO)oldObsIter.next();
                          if(bachentry.getStatusCd() == null)
                          {
                          oldObservationVO.getTheObservationDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
                          oldObservationVO.setItDirty(true);
                          oldObservationVO.getTheObservationDT().setItDirty(true);
                          }
                    if( bachentry.getStatusCd() != null)
                    {

                         if(bachentry.getStatusCd().equalsIgnoreCase("I")){
                          oldObservationVO.getTheObservationDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
                          oldObservationVO.setItDirty(true);
                          oldObservationVO.getTheObservationDT().setItDirty(true);

                         }
                         else{
                         oldObservationVO.getTheObservationDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                         oldObservationVO.getTheObservationDT().setItDirty(true);
                         oldObservationVO.setItDirty(true);
                         oldObservationVO.setItNew(false);
                         }
                      }
                    }
                }
                else if(bachentry.getUid() == null)
                {
                    
                    ObservationVO rowObservationVO = rowObservation("SOURCESROW");
                    rowObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
                    mainObservationCollection.add(rowObservationVO);

                   
                    // Act relashionship from row to SummaryForm ObservationVO from the previous page
                    ActRelationshipDT preActRel = new ActRelationshipDT();
                    preActRel.setSourceActUid(rowObservationVO.getTheObservationDT().getObservationUid());
                    preActRel.setSourceClassCd("OBS");
                    preActRel.setTargetActUid(formSumRepObsVO.getTheObservationDT().getObservationUid());
                    preActRel.setTargetClassCd("OBS");
                    preActRel.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                    preActRel.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                    preActRel.setTypeCd("SummaryFrmQ");
                    preActRel.setItNew(true);
                    preActRel.setItDirty(false);
                    actRelationshipCollection.add(preActRel);
                   

                    tempID = setObservationForCreate(bachentry,  mainObservationCollection, tempID, rowObservationVO, formSumRepObsVO, actRelationshipCollection);

                }


                BigDecimal dCount = bachentry.getObservationVO_s(1).getObsValueNumericDT_s(0).getNumericValue1();
                
                if(dCount != null)
                {
                    if (bachentry.getStatusCd()!= null)
                    {
                      if(bachentry.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                         totalCount = totalCount + dCount.intValue();
                     }
                   
                }

          }// for iter for loop

      }// for  helper if

      // For setting the total value to Sum107
       ObservationVO observationTotalCount = this.getObservationVO(summaryReportProxyVO,NEDSSConstants.SUM107);
       if(observationTotalCount != null)
       {
          
           observationTotalCount.getObsValueNumericDT_s(0).setNumericValue1(new BigDecimal(totalCount ));
           observationTotalCount.setItDirty(true);
           observationTotalCount.setItNew(false);
       }

      // Setting the values to proxy

       PublicHealthCaseVO  phcVO = summaryReportProxyVO.getThePublicHealthCaseVO();
       PublicHealthCaseDT phcDT  = phcVO.getThePublicHealthCaseDT();
       Long phcUid = phcDT.getPublicHealthCaseUid();

       phcDT.setItDirty(true);
       phcVO.setThePublicHealthCaseDT(phcDT);
       summaryReportProxyVO.setThePublicHealthCaseVO(phcVO);
       summaryReportProxyVO.setTheObservationVOCollection(mainObservationCollection);
       summaryReportProxyVO.setTheActRelationshipDTCollection(actRelationshipCollection);
       summaryReportProxyVO.setItDirty(true);
       summaryReportProxyVO.setItNew(false);
       phcUid = sendProxyToInvestigationEJB(summaryReportProxyVO,session,request);
       
       }

      }

      catch (NEDSSAppConcurrentDataException ex)
          {
            logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ");
            throw new NEDSSAppConcurrentDataException();
          }
      catch(Exception e)
      {
  		logger.error("Exception in Summary Report Submit Edit Handler: " + e.getMessage());
  		e.printStackTrace();
      }
  }
 /**
   * The method creates a new instance of ObservationVO and
   * the values are set as to represent a Row Observation
   * @param cntrlCdDisplyForm     the String
   * @return  ObservationVO     the object
   */

    private ObservationVO  rowObservation(String cntrlCdDisplyForm)
    {
         ObservationVO rowObservationVO = new ObservationVO();

         ObservationDT rowObservationDT = new ObservationDT();

         rowObservationDT.setCd("ItemToRow");
         rowObservationDT.setCdSystemCd("NBS");
         rowObservationDT.setCtrlCdDisplayForm(cntrlCdDisplyForm.trim());
         rowObservationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         rowObservationDT.setItNew(true);
         rowObservationDT.setItDirty(false);

         rowObservationVO.setTheObservationDT(rowObservationDT);
         rowObservationVO.setItNew(true);
         rowObservationVO.setItDirty(false);
         return rowObservationVO;
    }


 /**
   * Selects the ObservationVO from SummaryReportProxyVO based on the Cd value
   * @param summaryReportProxyVO    the SummaryReportProxyVO
   * @param SummaryReportFormCd     the String value as the Cd for the observation
   * @return  ObservationVO object
   */
    private ObservationVO getObservationVO(SummaryReportProxyVO summaryReportProxyVO, String SummaryReportFormCd)
    {
	//##!! System.out.println("Hi we are in the method of getting the observation from summaryVO");
        String observationCode = "";
	ObservationVO observationVO = null;
	Iterator<Object> anIterator = null;
	Collection<Object>  obsCollection  = summaryReportProxyVO.getTheObservationVOCollection();
        //##!! System.out.println("Hi .. The size of obsvocoll in the method"+obsCollection);
	if(obsCollection  != null)
	{
	    anIterator = obsCollection.iterator();
	    while(anIterator.hasNext())
	    {
		 observationVO = (ObservationVO)anIterator.next();
		 if(observationVO.getTheObservationDT().getCd() != null && observationVO.getTheObservationDT().getCd().trim().equals(SummaryReportFormCd.trim()))
		 {
		   //##!! System.out.println("getObservation Cd from  getObservationVO method =======: " + observationVO.getTheObservationDT().getCd());
		    return observationVO;
		 }
	    }
	}
	return null;
    }
   /**
     * Select the ObservationVO from a collection of ObservationVOs
     * based ont he Cd value which we are passing
     * @param obsCollection    the Collection<Object>  of ObservationVOs
     * @param SummaryReportFormCd   the String which represent the Cd of the Observation
     * @return   ObservationVO object
     */
    private ObservationVO getObservationVOfromObsColl(Collection<Object> obsCollection  , String SummaryReportFormCd)
    {
	String observationCode = "";
        ObservationVO observationVO = null;
       Iterator<Object>  anIterator = null;
	if(obsCollection  != null)
	{
	    anIterator = obsCollection.iterator();
	    while(anIterator.hasNext())
	    {
		 observationVO = (ObservationVO)anIterator.next();
		 if(observationVO.getTheObservationDT().getCd() != null && observationVO.getTheObservationDT().getCd().equals(SummaryReportFormCd))
		 {
		   // logger.debug("getObservation: " + observationVO.getTheObservationDT().getCd());
		    return observationVO;
		 }
	    }
	}
	return null;
    }

    /**
    * set negative tempId and newFlag to true for all the observations in the batchentry
    * @param bachentry   the BatchEntryHelper class
    * @param mainObservatioCollection    the Collection<Object>  of Observations in the SummaryReportProxyVO
    * @param tempID   the negative int
    * @param rowObservation   the ObservationVO which represents the row
    * @param formObservation   the form ObservationVO
    * @param actRelationshipCollection    the collection act relationShips
    * @return  tempID   the int
    */
    private int setObservationForCreate(BatchEntryHelper bachentry, Collection<Object>  mainObservationCollection, int tempID,
                                         ObservationVO rowObservation, ObservationVO formObservation,
                                         Collection<Object>  actRelationshipCollection)
    {

	        Collection<ObservationVO>  obsVoColl =  bachentry.getObservationVOCollection();
               Iterator<ObservationVO>  itor1 = obsVoColl.iterator();
                while (itor1.hasNext())
	        {
                ObservationVO obsVO = (ObservationVO)itor1.next();
		ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
                ObservationDT obsDT = obsVO.getTheObservationDT();
                FormQACode.putQuestionCode(obsDT);
                if(bachentry.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                     obsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                else if(bachentry.getStatusCd().equals("I"))
                     obsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

		if (obsVO.getTheObsValueCodedDTCollection() != null)
		{
		   Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();
		    while (it.hasNext())
		    {

			ObsValueCodedDT obsValDT = (ObsValueCodedDT)it.next();
			obsValDT.setItNew(true);
			obsValDT.setItDirty(false);
		    }
		}

		if (obsVO.getTheObsValueNumericDTCollection() != null)
		{

		   Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();

		    while (it.hasNext())
		    {

			ObsValueNumericDT obsValDT = (ObsValueNumericDT)it.next();
                        obsValDT.setItNew(true);
			obsValDT.setItDirty(false);
		    }
                }
		if (obsVO.getTheObsValueTxtDTCollection() != null)
		{

		   Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();

		    while (it.hasNext())
		    {

			ObsValueTxtDT obsValDT = (ObsValueTxtDT)it.next();
			obsValDT.setItNew(true);
			obsValDT.setItDirty(false);
		    }
		}

		obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		obsDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

		String strCode = obsDT.getCd();

		if ((strCode == null) || (strCode.trim().length() == 0))
		    logger.fatal("Page submitted an observation where obsDT.Cd was not set.");

		obsDT.setObservationUid(new Long(tempID--));
		obsDT.setItNew(true);
		obsDT.setItDirty(false);
		obsVO.setItNew(true);
		obsVO.setItDirty(false);
		ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
                // adding 2 actRelationship for each item
                  this.setActrelationshipForItem(obsVO, rowObservation, formObservation, actRelationshipCollection);
		mainObservationCollection.add(obsVO);
                //##!! System.out.println(" size of mainObservationCollection: " + mainObservationCollection.size());

	 } // end of while

	return tempID;
    }

   /**
    * send the SummaryReportProxyVO to the database through
    * InvestigationProxyEJB - setSummaryReportProxy method
    * @param summaryReportProxyVO    the  SummaryReportProxyVO
    * @param session     the  HttpSession
    * @param request     the HttpServletRequest
    * @return      the Long
    * @throws Exception
    */

      private Long sendProxyToInvestigationEJB(SummaryReportProxyVO summaryReportProxyVO,
					     HttpSession session, HttpServletRequest request)
				      throws Exception
    {

	MainSessionCommand msCommand = null;
	Long publicHealthCaseUID = null;

	try
	{

	    String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
	    String sMethod = "setSummaryReportProxy";
            //##!! System.out.println("Tis is from sendProxytoEJB"+summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd());
	    logger.debug("sending SummaryReportProxyVO to investigationproxyejb, via mainsession");

	    Object[] oParams = { summaryReportProxyVO };
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    logger.info("mscommand in InvestigationStore is: " + msCommand);

	    ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

	    if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
	    {
		logger.info("Create summaryReport worked!!! publicHealthCaseUID = " +
			resultUIDArr.get(0));
		publicHealthCaseUID = (Long)resultUIDArr.get(0);
	    }

	    //context store

	     NBSContext.store(session,NBSConstantUtil.DSSummaryReportUID,publicHealthCaseUID );
	}
	catch (Exception e)
	{
	    logger.fatal("ERROR calling mainsession control", e);
            e.printStackTrace();
	    throw e;
	}

	return publicHealthCaseUID;
    }

  /**
   * creats the new ActRelationshipDT instances between each item ObservationVO of the row
   * and the RowObservationVO,  and each item ObservationVO of the row and the form ObervationVO
   * @param itemObservationVO   the ObservationVO instance
   * @param rowObservationVO    the ObservationVO instance
   * @param formObservationVO   the ObservationVO instance
   * @param actRelationshipCollection     the Collection
   */
   private void setActrelationshipForItem(ObservationVO itemObservationVO, ObservationVO rowObservationVO, ObservationVO formObservationVO, Collection<Object>  actRelationshipCollection)
   {
          ActRelationshipDT itemToRowAR = new ActRelationshipDT();
          itemToRowAR.setSourceActUid(itemObservationVO.getTheObservationDT().getObservationUid());
          itemToRowAR.setSourceClassCd(NEDSSConstants.SUMMARY_OBS);
          itemToRowAR.setTargetActUid(rowObservationVO.getTheObservationDT().getObservationUid());
          itemToRowAR.setTargetClassCd(NEDSSConstants.SUMMARY_OBS);
          itemToRowAR.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          itemToRowAR.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          itemToRowAR.setTypeCd(NEDSSConstants.SummaryRowItem);
          itemToRowAR.setItNew(true);
          itemToRowAR.setItDirty(false);
          actRelationshipCollection.add(itemToRowAR);
          //##!! System.out.println(" Set the first act relashionship  from the item to row *******************: " );

          ActRelationshipDT itemToFormAR = new ActRelationshipDT();
          itemToFormAR.setSourceActUid(itemObservationVO.getTheObservationDT().getObservationUid());
          itemToFormAR.setSourceClassCd(NEDSSConstants.SUMMARY_OBS);
          itemToFormAR.setTargetActUid(formObservationVO.getTheObservationDT().getObservationUid());
          itemToFormAR.setTargetClassCd(NEDSSConstants.SUMMARY_OBS);
          itemToFormAR.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          itemToFormAR.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          itemToFormAR.setTypeCd(NEDSSConstants.SUMMARY_FORM_Q);
          itemToFormAR.setItNew(true);
          itemToFormAR.setItDirty(false);
          actRelationshipCollection.add(itemToFormAR);
          //##!! System.out.println(" Set the second act relashionship  from the item to form *******************: " );

   }
  /**
   * Creates an instance of the NotificationProxyVO
   * @param summaryReportProxyVO    the SummaryReportProxyVO
   * @return  notiProxyVO  NotificationProxyVO
   */

  private NotificationProxyVO setActrelationshipForNotification(SummaryReportProxyVO summaryReportProxyVO)
   {
          int tempID = -1;

          Collection<Object>  actRelashonshipDTColl =  new ArrayList<Object> ();

          PublicHealthCaseVO  phcVO = summaryReportProxyVO.getThePublicHealthCaseVO();

          NotificationProxyVO notiProxyVO = new NotificationProxyVO();
          NotificationVO notificationVO = new NotificationVO();
          NotificationDT notificationDT = new NotificationDT();
          notificationDT.setNotificationUid(new Long(tempID--));
          notificationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          //notificationDT.setLastChgTime(new java.sql.Timestamp(new Date().getTime()));
          notificationDT.setItNew(true);
          notificationVO.setTheNotificationDT(notificationDT);
          notificationVO.setItNew(true);
          notiProxyVO.setTheNotificationVO(notificationVO);

          PublicHealthCaseDT publicHealthcaseDT = phcVO.getThePublicHealthCaseDT();
          notiProxyVO.setThePublicHealthCaseVO(phcVO);

          ActRelationshipDT actNotification = new ActRelationshipDT();
          actNotification.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          actNotification.setSourceActUid(notificationVO.getTheNotificationDT().getNotificationUid());
          actNotification.setSourceClassCd(NEDSSConstants.CLASS_CD_NOTF);
          actNotification.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          actNotification.setTargetActUid(phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
          actNotification.setTargetClassCd(NEDSSConstants.SUMMARY_CLASS_CD);
          actNotification.setTypeCd(NEDSSConstants.SUMMARY_NOTIFICATION);
          actNotification.setItNew(true);
          actNotification.setItDirty(false);

          actRelashonshipDTColl.add(actNotification);
          notiProxyVO.setTheActRelationshipDTCollection(actRelashonshipDTColl);
// System.out.println(" Set the first act relashionship  from the Notification *******************: " );

          return notiProxyVO;

   }
   /**
     * Sends the NotificationProxyVO to the NotificationProxyEJB
     * through setSummaryNotificationProxy method
     * @param notificationProxyVO   the NotificationProxyVO
     * @param session    the HttpSession
     * @param request    the HttpServletRequest
     * @return  Long
     * @throws Exception
     */

    private Long sendProxyToNotificationEJB(NotificationProxyVO notificationProxyVO,
					     HttpSession session, HttpServletRequest request)
				      throws Exception
    {

	MainSessionCommand msCommand = null;
	Long notificationUid = null;

	try
	{
	    String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
	    String sMethod = "setSummaryNotificationProxy";
	    logger.debug("sending setSummaryNotificationProxy to NotificationProxyEJBRef, via mainsession");

	    Object[] oParams = { notificationProxyVO };
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    logger.info("mscommand in NotificationProxyEJBRef is: " + msCommand);

	    ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

	    if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
	    {
		logger.info("Create summaryReport worked!!! notificationUid = " +
			resultUIDArr.get(0));
		notificationUid = (Long)resultUIDArr.get(0);
	    }

	}
	catch (Exception e)
	{
	    e.printStackTrace();
            logger.error("ERROR calling mainsession control", e);
	    throw e;
	}

	return notificationUid;
    }
}