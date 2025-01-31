package gov.cdc.nedss.alert.ejb.alertejb;


import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.alert.dt.AlertEmailMessageDT;
import gov.cdc.nedss.alert.dt.AlertLogDT;
import gov.cdc.nedss.alert.dt.AlertLogDetailDT;
import gov.cdc.nedss.alert.dt.AlertUserDT;
import gov.cdc.nedss.alert.dt.UserEmailDT;
import gov.cdc.nedss.alert.ejb.dao.AlertEmailMessageDAO;
import gov.cdc.nedss.alert.ejb.dao.AlertRootDAO;
import gov.cdc.nedss.alert.util.AlertStateConstant;
import gov.cdc.nedss.alert.vo.AlertEmailMessageVO;
import gov.cdc.nedss.alert.vo.AlertLogVO;
import gov.cdc.nedss.alert.vo.AlertVO;
import gov.cdc.nedss.alert.vo.UserEmailVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;



import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AlertEJB   implements javax.ejb.SessionBean {

	/**
	 *AlertEJB  is the  EJB to store and retrieve alerts.
	 *
	 * @author Pradeep Sharma
	 *
	 */

	static final LogUtils logger = new LogUtils(AlertEJB.class.getName());
	private static final long serialVersionUID = 1L;

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void setSessionContext(SessionContext arg0) throws EJBException,
	RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbCreate()
	{
	}

	public Collection<Object> getAlertVOCollection(String conditionCd,String jurisdictionCd,String recordStatusCd,String typeCode,NBSSecurityObj nbsSecurityObj)  throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		Collection<Object> coll =alertRootDAO.getAlertVOCollection(conditionCd, jurisdictionCd,recordStatusCd,  typeCode,true, true);
		return coll;
	}

	public void insertAlertVO(AlertVO alertVO,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		alertRootDAO.insertAlertVO(alertVO);

	}


	public void updateAlertVO(AlertVO alertVO, Long oldAlertUid,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		alertRootDAO.updateAlertVO(alertVO, oldAlertUid);
	}

	public void deleteAlertVO(Long alertUid,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		alertRootDAO.deleteAlertVO(alertUid);
	}


	public UserEmailVO getUserEmailVO( Long nedssEntryId,NBSSecurityObj nbsSecurityObj)  throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		UserEmailVO userEmailVO=alertRootDAO.getUserEmailVO(nedssEntryId);
		return userEmailVO;
	}

	public void insertUserEmailVO(UserEmailVO userEmailVO,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		alertRootDAO.insertUserEmailVO(userEmailVO);
	}

	public void updateUserEmailVO(UserEmailVO userEmailVO,  Long nedssEntryUid,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		alertRootDAO.updateUserEmailVO(userEmailVO,nedssEntryUid);
	}

	public Collection<Object> getAlertMessageCollection(NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException, RemoteException{
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		Collection<Object> alertEmailMessageColl = alertRootDAO.getAlertEmailMessageVOCollection();
		/*if(alertEmailMessageColl!=null){
			Iterator it = alertEmailMessageColl.iterator();
			while(it.hasNext()){
				AlertEmailMessageVO alertEmailMessageVO =(AlertEmailMessageVO)it.next();
				sendAndLogMessage(alertEmailMessageVO);
			
			}
		}*/
		return alertEmailMessageColl;
	}
	
	public void sendAndLogMessage(AlertEmailMessageVO alertEmailMessageVO, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSSystemException, RemoteException{
		Map<Object, Object> map = new HashMap<Object, Object>();
		AlertLogVO alertLogVO= new AlertLogVO();
		boolean errorCondition= false;
		AlertLogDT alertLogDT= new AlertLogDT();
		alertLogDT.setAlertUid(alertEmailMessageVO.getAlertEmailMessageDT().getAlertUid());
		alertLogDT.setEventLocalId(alertEmailMessageVO.getAlertEmailMessageDT().getEventLocalId());
		alertLogDT.setAddTime(new Timestamp(new Date().getTime()));
		alertLogVO.setAlertLogDT(alertLogDT);
		
		try{
			map = mailer(alertEmailMessageVO, false, nbsSecurityObj);

				AlertLogDetailDT alertLogDetailDT= new AlertLogDetailDT();
			alertLogDetailDT.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_SUCCESSFUL);
			alertEmailMessageVO.getAlertEmailMessageDT().setTransmissionStatus(AlertStateConstant.MESSAGE_SENT);
			Collection<Object> emailLogMessages = (Collection<Object>)map.get("ALERT_LOG_COLL");
			emailLogMessages.add(alertLogDetailDT);
			alertLogVO.setAlertLogDetailDTColl(emailLogMessages);
		}catch(Exception e){
			errorCondition= true;
			logger.fatal("AlertEJB.sendAndLogMessage:" + e.getMessage(), e);
			throw new javax.ejb.EJBException(e);
		}
		if(map.get("ERROR")!=null || errorCondition){
			alertEmailMessageVO.getAlertEmailMessageDT().setTransmissionStatus(AlertStateConstant.TRANSMISSION_ERROR);
			AlertLogDetailDT alertLogDetailDT= new AlertLogDetailDT();
			alertLogDetailDT.setAlertActivityDetailLog(AlertStateConstant.ALERT_EMAIL_MESSAGE_UNSUCCESSFUL);
			Collection<Object> emailLogMessages = (Collection<Object>)map.get("ALERT_LOG_COLL");
			emailLogMessages.add(alertLogDetailDT);
			alertLogVO.setAlertLogDetailDTColl(emailLogMessages);
			alertLogDT.setActivityLog(AlertStateConstant.ALERT_EMAIL_MESSAGE_UNSUCCESSFUL  );
			
		}else{
			Integer initSize= new Integer(0);
			if(map.get("ORIGINAL_SIZE")!=null)
				initSize= (Integer)map.get("ORIGINAL_SIZE");
			Integer finalSize= new Integer(0);
			if(map.get("FINAL_SIZE")!=null)
				finalSize= (Integer)map.get("FINAL_SIZE");
			alertLogDT.setActivityLog("Alert sent to "+ finalSize.intValue() +" out of "+ initSize.intValue()+ " users"  );
			alertEmailMessageVO.getAlertEmailMessageDT().setTransmissionStatus(AlertStateConstant.MESSAGE_SENT);
		}
		AlertRootDAO.insertEmailLogVO(alertLogVO);
		AlertRootDAO.updateAlertEmailMessageVO(alertEmailMessageVO);
		
	}

	public void insertEmailsWithLogDetail(AlertLogVO alertLogVO, Collection<Object> userEmailColl, NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException{
		AlertEmailMessageDAO alertEmailMessageDAO =new AlertEmailMessageDAO();
		alertEmailMessageDAO.insertEmailsWithLogDetail(alertLogVO,userEmailColl );
	}

	public void sendSimulatedMessage(String conditionCode, String JurisdictionCode, String LocalId, String typeCode, Timestamp lastChgTime, NBSSecurityObj nbsSecurityObj )throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException{
		ProgramAreaVO programAreaVO=CachedDropDowns.getProgramAreaForCondition(conditionCode);
		String programAreaCode=programAreaVO.getStateProgAreaCode();
		String programAreaDescription=programAreaVO.getStateProgAreaCdDesc();
		createMessage(conditionCode, JurisdictionCode, LocalId, lastChgTime,programAreaCode, programAreaDescription, typeCode, true, true, nbsSecurityObj);
	}

	private void createMessage(String conditionCode, String JurisdictionCode, String LocalId, Timestamp addTime, String progAreaCd, String progAreaDesc, String typeCode,boolean isAdminUser, boolean simulatedMessage, NBSSecurityObj nbsSecurityObj){
		AlertRootDAO alertRootDAO= new AlertRootDAO();
		
		try{
		if(JurisdictionCode==null)
			JurisdictionCode=AlertStateConstant.JURISDICTION_NOT_ASSIGNED;
		Collection<Object> alertCollection  =alertRootDAO.getAlertVOCollection(conditionCode, JurisdictionCode,NEDSSConstants.RECORD_STATUS_ACTIVE, typeCode,isAdminUser, false);
		if(alertCollection!=null && alertCollection.size()>0){
			Iterator<Object> iterator= alertCollection.iterator();
			while(iterator.hasNext()){
				AlertVO alertVO= (AlertVO)iterator.next();
				AlertEmailMessageVO alertEmailMessageVO = new AlertEmailMessageVO();
				AlertEmailMessageDT alertEmailMessageDT= new AlertEmailMessageDT();
				alertEmailMessageDT.setTypeCd(alertVO.getAlertDT().getTypeCd());
				alertEmailMessageDT.setType( CachedDropDowns.getCodeDescTxtForCd(alertVO.getAlertDT().getTypeCd(), "ALRT_PUBLIC_HEALTH_EVENT") );
				alertEmailMessageDT.setSeverityCd(alertVO.getAlertDT().getSeverityCd());
				alertEmailMessageDT.setSeverity( CachedDropDowns.getCodeDescTxtForCd(alertVO.getAlertDT().getSeverityCd(), "SEVERITY"));
				alertEmailMessageDT.setAlertMsgTxt(alertVO.getAlertDT().getAlertMsgTxt());
				if(simulatedMessage)
					alertEmailMessageDT.setSimulatedAlert("Y");
				else
					alertEmailMessageDT.setSimulatedAlert("N");
				alertEmailMessageDT.setJurisdictionCd(JurisdictionCode);
				if(simulatedMessage && alertVO.getAlertDT().getJurisdictionCd().equals(AlertStateConstant.JURISDICTION_ALL))
					alertEmailMessageDT.setJurisdictionDescription(AlertStateConstant.JURISDICTION_ALL);
				else if(alertEmailMessageDT.getJurisdictionCd().equals(AlertStateConstant.JURISDICTION_NOT_ASSIGNED))
					alertEmailMessageDT.setJurisdictionDescription(AlertStateConstant.JURISDICTION_NOT_ASSIGNED);
				else
					alertEmailMessageDT.setJurisdictionDescription(CachedDropDowns.getJurisdictionDesc(JurisdictionCode));
				alertEmailMessageDT.setAssociatedConditionCd(conditionCode);
				alertEmailMessageDT.setAssociatedConditionDesc(CachedDropDowns.getConditionDesc(alertVO.getAlertDT().getConditionCd()));
				alertEmailMessageDT.setEventAddTime(addTime);
				alertEmailMessageDT.setAddTime(alertVO.getAlertDT().getAddTime());
				alertEmailMessageDT.setAlertAddTime(alertVO.getAlertDT().getAddTime());
				alertEmailMessageDT.setEventLocalId(LocalId);
				alertEmailMessageDT.setTransmissionStatus("NOT SENT");
				alertEmailMessageDT.setAlertUid(alertVO.getAlertDT().getAlertUid());
				if(progAreaCd==null){
					alertEmailMessageDT.setProgAreaCd(AlertStateConstant.PROGRAM_AREA_NOT_ASSIGNED);
					alertEmailMessageDT.setProAreaDescription(AlertStateConstant.PROGRAM_AREA_NOT_ASSIGNED);
				}
				else{
					alertEmailMessageDT.setProgAreaCd(progAreaCd);
					alertEmailMessageDT.setProAreaDescription(progAreaDesc);
				}
				alertEmailMessageVO.setAlertEmailMessageDT(alertEmailMessageDT);
				AlertLogVO alertLogVO= new AlertLogVO();
				AlertLogDT alertLogDT= new AlertLogDT();
				alertLogDT.setAddTime(new Timestamp(new Date().getTime()));
				alertLogDT.setAlertUid(alertVO.getAlertDT().getAlertUid());
				alertLogDT.setEventLocalId(LocalId);
				alertLogVO.setAlertLogDT(alertLogDT);

				if(alertVO.getAlertUserDTCollection()!=null && alertVO.getAlertUserDTCollection().size()>0){
					Iterator<Object> ite = alertVO.getAlertUserDTCollection().iterator();
					while(ite.hasNext()){
						AlertUserDT alertUserDT = (AlertUserDT)ite.next();
						alertEmailMessageVO.getUserEmailDTCollection().addAll(alertUserDT.getUserEmailCollection());
					}
				}
				else
					logger.error("THERE IS NO USER LINKED TO THE ALERT");
				if(alertEmailMessageVO.getUserEmailDTCollection()!=null && alertEmailMessageVO.getUserEmailDTCollection().size()>0){
					AlertLogDetailDT alertLogDetailDT= new AlertLogDetailDT();
					alertLogDetailDT.setAlertActivityDetailLog("there are " + alertEmailMessageVO.getUserEmailDTCollection().size()+ " users with valid email ids for alert for event local id "+ LocalId);

				}
				else{
					AlertLogDetailDT alertLogDetailDT= new AlertLogDetailDT();
					alertLogDetailDT.setAlertActivityDetailLog("there is a alert message without any user email list for event local id"+ LocalId);
				}

				PropertyUtil propertyUtil = PropertyUtil.getInstance();
				String mailhost = propertyUtil.getAlertMailHost();
				Collection<Object> emailLogMessages = null;
				AlertLogDetailDT alertLogDetailDT= new AlertLogDetailDT();
				Map<Object,Object> map= new HashMap<Object, Object>();
				Integer initSize= new Integer(0);
				Integer finalSize=new Integer(0);;
				if(mailhost!=null && !mailhost.trim().equalsIgnoreCase("") && simulatedMessage){
		
					try {
						map = mailer(alertEmailMessageVO, simulatedMessage, nbsSecurityObj);
						emailLogMessages = (Collection<Object>)map.get("ALERT_LOG_COLL");
						alertLogDetailDT.setAlertActivityDetailLog(AlertStateConstant.ALERT_EMAIL_MESSAGE_SUCCESSFUL);
						alertEmailMessageDT.setTransmissionStatus(AlertStateConstant.MESSAGE_SENT);
						alertLogVO.setAlertLogDetailDTColl(emailLogMessages);

						if(map.get("ERROR")!=null){
							alertLogDT.setActivityLog(AlertStateConstant.ALERT_EMAIL_MESSAGE_UNSUCCESSFUL  );
						}else{
							if(map.get("ORIGINAL_SIZE")!=null)
								initSize= (Integer)map.get("ORIGINAL_SIZE");
							if(map.get("FINAL_SIZE")!=null)
								finalSize= (Integer)map.get("FINAL_SIZE");
							alertLogDT.setActivityLog("Alert sent to "+ finalSize.intValue() +" out of "+ initSize.intValue()+ " users"  );
						}
					} catch (Exception e) {
						alertLogDT.setActivityLog("Sending alertEmail Message failed: -"+trimToTwoKLength(e.toString()));
						alertLogDetailDT.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT_ERROR+"Error while creating and sending mail ");
						alertLogVO.setAlertLogDetailDTColl(emailLogMessages);
					}
					finally{
						AlertRootDAO.insertAlertEmailVO(alertEmailMessageVO);
						AlertRootDAO.insertEmailLogVO(alertLogVO);
					}
				}
				else
				{
					if(simulatedMessage)
							alertLogDT.setActivityLog("TEST: Simulated Alert Email Message Created for Condition "+alertEmailMessageDT.getAssociatedConditionDesc());
					else
						alertLogDT.setActivityLog("Alert Email Message Created for Event Local  id :"+LocalId );

					emailLogMessages = new ArrayList<Object> ();
					alertLogDetailDT.setAlertActivityDetailLog(AlertStateConstant.ALERT_EMAIL_MESSAGE_SUCCESSFUL);
					alertEmailMessageVO.getAlertEmailMessageDT().setTransmissionStatus(AlertStateConstant.MESSAGE_NOT_SENT);
					AlertRootDAO.insertAlertEmailVO(alertEmailMessageVO);
					emailLogMessages.add(alertLogDetailDT);
					alertLogVO.setAlertLogDetailDTColl(emailLogMessages);
					AlertRootDAO.insertEmailLogVO(alertLogVO);

				}
				logger.debug("Alert log and message inserted");
			}
		}
		}
		catch (Exception e)
		{
		logger.fatal("AlertEJB.createMessage:" + e.getMessage(), e);
		throw new javax.ejb.EJBException(e);
		}
	}
	public void alertLabsEmailMessage(LabResultProxyVO labResultProxyVO, String LocalId,NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException{
		AlertEmailMessageDAO alertMessageDAO= new AlertEmailMessageDAO();
		try{
		Collection<Object> conditionColl =alertMessageDAO.getAlertConditionList(labResultProxyVO, nbsSecurityObj);
		ObservationDT lab112 = null;
		if(conditionColl!=null && conditionColl.size()>0){
			Iterator<Object> it = conditionColl.iterator();
			Collection<ObservationVO> observationCollection  = labResultProxyVO.getTheObservationVOCollection();
			Iterator<ObservationVO> iter = observationCollection.iterator();
			while(iter.hasNext()){
				ObservationVO obsVO = (ObservationVO)iter.next();
				if(obsVO.getTheObservationDT().getObsDomainCdSt1().equalsIgnoreCase("Order")){
					lab112= obsVO.getTheObservationDT();
				}
			}
			while(it.hasNext()){
				String conditionCode =(String)it.next();
				createMessage(conditionCode,lab112.getJurisdictionCd(),  LocalId,lab112.getAddTime() , lab112.getProgAreaCd(), CachedDropDowns.getProgAreadDesc(lab112.getProgAreaCd()), NEDSSConstants.ADMIN_ALERT_LAB, false, false, nbsSecurityObj);
			}
		}else{
			logger.debug("Not Alert Mesaage Found");
		}
		}catch(Exception e){
			logger.fatal("Exception raised in alertLabsEmailMessage: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e);
		}
	}

	public void alertNBDDocumentEmailMessage(NBSDocumentVO nbsDocumentVO, NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException{
		try{
			createMessage(nbsDocumentVO.getNbsDocumentDT().getCd(),nbsDocumentVO.getNbsDocumentDT().getJurisdictionCd(),  nbsDocumentVO.getNbsDocumentDT().getLocalId(),nbsDocumentVO.getNbsDocumentDT().getAddTime(),
			nbsDocumentVO.getNbsDocumentDT().getProgAreaCd(), CachedDropDowns.getProgAreadDesc(nbsDocumentVO.getNbsDocumentDT().getProgAreaCd()), NEDSSConstants.PHC_236, false, false, nbsSecurityObj);
		}catch(Exception e){
			logger.fatal("Exception raised in AlertEJB.alertNBDDocumentEmailMessage: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e);
		}
	}
	private Map<Object,Object> mailer(AlertEmailMessageVO alertEmailMessageVO,boolean simulatedAlert, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,  NEDSSSystemException,java.rmi.RemoteException{
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		Collection<Object> alertDetailLog = new ArrayList<Object> ();
		Properties props = new Properties();
		PropertyUtil propertyUtil = PropertyUtil.getInstance();
		String mailhost = propertyUtil.getAlertMailHost();
		AlertLogDetailDT alertLogDetailDT = new AlertLogDetailDT();
		alertLogDetailDT.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_COMPOSE + "mail.host: "+ mailhost);
		alertDetailLog.add(alertLogDetailDT);
		props.put("mail.host", mailhost);
		AlertLogDetailDT alertLogDetailDT2 = new AlertLogDetailDT();
		alertLogDetailDT2.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_COMPOSE +"mail.transport.protocol: "+propertyUtil.getAlertSmtp());
		alertDetailLog.add(alertLogDetailDT2);
		props.put("mail.transport.protocol",propertyUtil.getAlertSmtp());
		AlertLogDetailDT alertLogDetailDT3 = new AlertLogDetailDT();
		alertLogDetailDT3.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_COMPOSE +"mail.smtp.host: "+mailhost);
		alertDetailLog.add(alertLogDetailDT3);
		props.put("mail.smtp.host",mailhost);
		Session session = Session.getDefaultInstance(props);
		Transport smtp_service;
		MimeMessage message = new MimeMessage(session);
		try {
			smtp_service = session.getTransport();
			try {
				InternetAddress sender;
				sender = new InternetAddress(propertyUtil.getAlertAdminEmail(),propertyUtil.getAlertAdminUser());
				AlertLogDetailDT alertLogDetailDT4= new AlertLogDetailDT();
				alertLogDetailDT4.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_COMPOSE +"AlertAdminEmail:"+propertyUtil.getAlertAdminEmail());
				alertLogDetailDT4.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_COMPOSE +"AlertAdminUser: "+propertyUtil.getAlertAdminUser());
				alertDetailLog.add(alertLogDetailDT4);

				message.setFrom(sender);
				Collection<Object> coll =alertEmailMessageVO.getUserEmailDTCollection();
				map.put("USER_EMAIL_COLL",coll);
				InternetAddress[]  to_list= new InternetAddress[coll.size()];

				if(coll!=null && coll.size()>0){

					Iterator<Object> it =coll.iterator();
					int i = 0;
					while(it.hasNext()){
						UserEmailDT userEmailDT=(UserEmailDT)it.next();
						if(userEmailDT.getEmailId()==null){
							AlertLogDetailDT alertLogDetailDT11= new AlertLogDetailDT();
							alertLogDetailDT11.setAlertActivityDetailLog(AlertStateConstant.ALERT_MESSAAGE_INVALID_EMAIL +"User Email id from User_email table:"+userEmailDT.getUserEmailUid());
							alertDetailLog.add(alertLogDetailDT11);
						}
						else
							to_list[i++]=new InternetAddress( userEmailDT.getEmailId());
					}
					message.setRecipients(Message.RecipientType.TO, to_list);

					AlertLogDetailDT alertLogDetailDT5= new AlertLogDetailDT();
					alertLogDetailDT5.setAlertActivityDetailLog(AlertStateConstant.ALERT_MESSAAGE_PREP +" Total number of Email Recipients:"+coll.size());
					map.put("ORIGINAL_SIZE",new Integer(coll.size()));
					alertDetailLog.add(alertLogDetailDT5);

				}
				else{
					AlertLogDetailDT alertLogDetailDT5= new AlertLogDetailDT();
					alertLogDetailDT5.setAlertActivityDetailLog(AlertStateConstant.ALERT_MESSAAGE_PREP_ERROR +" No Email Recipients for the alert");
					alertDetailLog.add(alertLogDetailDT5);
					map.put("ALERT_LOG_COLL",alertDetailLog);
					map.put("ERROR", "ERROR");
					return map;
				}

				if(simulatedAlert){
					message.setSubject(AlertStateConstant.SIMULATED_PART1+ alertEmailMessageVO.getAlertEmailMessageDT().getSeverity()+AlertStateConstant.SIMULATED_PART2+ alertEmailMessageVO.getAlertEmailMessageDT().getAssociatedConditionDesc() +AlertStateConstant.SIMULATED_PART3);
					message.setText(AlertStateConstant.SIMULATED_TEXT_PART1+ alertEmailMessageVO.getAlertEmailMessageDT().getType()+AlertStateConstant.SIMULATED_TEXT_PART2+ alertEmailMessageVO.getAlertEmailMessageDT().getAssociatedConditionDesc()+AlertStateConstant.SIMULATED_TEXT_PART3);
				}
				else
				{
					message.setSubject(AlertStateConstant.PART1+ alertEmailMessageVO.getAlertEmailMessageDT().getSeverity()+AlertStateConstant.PART2+ alertEmailMessageVO.getAlertEmailMessageDT().getAssociatedConditionDesc() );
					String emailBodyStr = getEmailAlertMessageBody(alertEmailMessageVO.getAlertEmailMessageDT(), nbsSecurityObj);
					//message.setText(emailBodyStr,"ISO-8859-1", "html");
					//message.setText(emailBodyStr);
					message.setContent(emailBodyStr, "text/html");
				}
				message.saveChanges();
				smtp_service.send(message);
				alertEmailMessageVO.getAlertEmailMessageDT().setEmailSentTime(new Timestamp(new Date().getTime()));
				AlertLogDetailDT alertLogDetailDTS= new AlertLogDetailDT();
				alertLogDetailDTS.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT +"email was successfully sent to " +coll.size() + " users");
				alertDetailLog.add(alertLogDetailDTS);
				map.put("FINAL_SIZE",new Integer(coll.size()));
				
			} catch (UnsupportedEncodingException e) {
				AlertLogDetailDT alertLogDetailDTE= new AlertLogDetailDT();
				alertLogDetailDTE.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT_ERROR +trimToTwoKLength( e.toString()));
				alertDetailLog.add(alertLogDetailDTE);	e.printStackTrace();
				map.put("ERROR", "ERROR");
				
			} catch (SendFailedException e) {
				AlertLogDetailDT alertLogDetailDTE= new AlertLogDetailDT();
				alertLogDetailDTE.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT_ERROR + trimToTwoKLength(e.toString()));
				alertDetailLog.add(alertLogDetailDTE);
					try {
							message.setRecipients(Message.RecipientType.TO, e.getValidUnsentAddresses());
							message.saveChanges();
							smtp_service.send(message);
							alertEmailMessageVO.getAlertEmailMessageDT().setEmailSentTime(new Timestamp(new Date().getTime()));
							map.put("FINAL_SIZE",new Integer(e.getValidUnsentAddresses().length));
							AlertLogDetailDT alertLogDetailDTS= new AlertLogDetailDT();
							alertLogDetailDTS.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT +"email was successfully sent to " + e.getValidUnsentAddresses().length + " users");
							alertDetailLog.add(alertLogDetailDTS);
			} catch (MessagingException e1) {
							AlertLogDetailDT alertLogDetailDTE1= new AlertLogDetailDT();
							alertLogDetailDTE1.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT_ERROR +trimToTwoKLength( e.toString()));
							alertDetailLog.add(alertLogDetailDTE1);
							map.put("ERROR", "ERROR");
						}
					}
			catch(Exception e){
				AlertLogDetailDT alertLogDetailDTE= new AlertLogDetailDT();
				alertLogDetailDTE.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT_ERROR +"Generic Exception:"+ trimToTwoKLength( e.toString()));
				alertDetailLog.add(alertLogDetailDTE);
				map.put("ERROR", "ERROR");
			}
			finally{
				map.put("ALERT_LOG_COLL",alertDetailLog);

			}
		} catch (NoSuchProviderException e2) {
			AlertLogDetailDT alertLogDetailDTSCPE= new AlertLogDetailDT();
			alertLogDetailDTSCPE.setAlertActivityDetailLog(AlertStateConstant.ALERT_MAIL_TRANSMIT_ERROR + trimToTwoKLength(e2.getMessage()));
			alertDetailLog.add(alertLogDetailDTSCPE);
			map.put("ERROR", "ERROR");
		}
		return map;

	}
    /*
     * The message differs depending on whether it is a lab or case report. 
     * See word document on: Release Task #4995 RA: Enhance the Language for Alert Emails
     */

	private String getEmailAlertMessageBody(
			AlertEmailMessageDT alertEmailMessageDT,
			NBSSecurityObj nbsSecurityObj) {
		String returnString;
		
		try{
		if ((alertEmailMessageDT.getTypeCd().equals(AlertStateConstant.PHCR_OR_LAB_CODE) &&
				alertEmailMessageDT.getEventLocalId().contains("OBS")) ||
				alertEmailMessageDT.getTypeCd().equals(AlertStateConstant.LAB_CODE)) {
			returnString = buildLabEmailBody(alertEmailMessageDT, nbsSecurityObj);
		} else {
			returnString = buildCaseEmailBody(alertEmailMessageDT, nbsSecurityObj);
		}
		return returnString;
	}
	catch (Exception e)
	{
		logger.fatal("AlertEJB.getEmailAlertMessageBody:" + e.getMessage(), e);
		throw new javax.ejb.EJBException(e);
	}
}

	/*
	 * Build body of email for a Lab Report Alert
	 */
	private String buildLabEmailBody(
			AlertEmailMessageDT alertEmailMessageDT,
			NBSSecurityObj nbsSecurityObj) {
		
		try{
		ArrayList<String> resultStrAry;
		resultStrAry = getReportingFacilityAndLabResultString(alertEmailMessageDT, nbsSecurityObj);
		String reportingFacility = resultStrAry.get(0);
		String labResult = resultStrAry.get(1);
		DateFormat formatter =  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,Locale.US);
		String dateTimeOut = formatter.format(alertEmailMessageDT.getEventAddTime());
		
		
		StringBuffer sb = new StringBuffer(AlertStateConstant.BODY_TEXT_PART1); //A
		sb.append(alertEmailMessageDT.getType()); //Laboratory Report
		sb.append(AlertStateConstant.BODY_TEXT_PART1b); //from
		sb.append(reportingFacility.isEmpty() ? AlertStateConstant.UNKNOWN_REPORTING_FACILITY : reportingFacility);
		sb.append(AlertStateConstant.BODY_TEXT_PART2); //indicating
		sb.append(alertEmailMessageDT.getAssociatedConditionDesc());
		sb.append(AlertStateConstant.BODY_TEXT_PART3); //was received on
		sb.append(dateTimeOut);
		sb.append(AlertStateConstant.BODY_TEXT_PART4a); //for
		sb.append(alertEmailMessageDT.getJurisdictionDescription());
		sb.append(AlertStateConstant.BODY_TEXT_PART4b); //Jurisdiction with an identifier of
		sb.append(alertEmailMessageDT.getEventLocalId());
		if (labResult != null && !labResult.isEmpty()) {
			sb.append(AlertStateConstant.BODY_TEXT_PART5); //with the following result(s):
			sb.append(labResult);
		}
		if (alertEmailMessageDT.getAlertMsgTxt() != null)  //text user can specify in when adding/updating the Alert
			sb.append("<br>" + alertEmailMessageDT.getAlertMsgTxt());
		
		logger.debug("Lab Report eMail body text = " + sb.toString());
		
		return sb.toString();
	}
	catch (Exception e)
	{
		logger.fatal("AlertEJB.buildLabEmailBody:" + e.getMessage(), e);
		throw new javax.ejb.EJBException(e);
	}
}
	
	/*
	 * Build body of email for a Case Report Alert
	 */
	private String buildCaseEmailBody(AlertEmailMessageDT alertEmailMessageDT,
		NBSSecurityObj nbsSecurityObj) {
		DateFormat formatter =  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,Locale.US);
		String dateTimeOut = formatter.format(alertEmailMessageDT.getEventAddTime());
		String sendingFacility = getSendingFacilityForCaseReport(alertEmailMessageDT.getEventLocalId());
		StringBuffer sb = new StringBuffer(AlertStateConstant.TEXT_PART1); //A
		sb.append(alertEmailMessageDT.getType()+AlertStateConstant.TEXT_PART1b); 
		sb.append(sendingFacility);
		sb.append(AlertStateConstant.TEXT_PART2+alertEmailMessageDT.getAssociatedConditionDesc()+AlertStateConstant.TEXT_PART3);
		sb.append(dateTimeOut+ " for "+ alertEmailMessageDT.getJurisdictionDescription());
		sb.append(AlertStateConstant.TEXT_PART4+ alertEmailMessageDT.getEventLocalId()+".");
		if (alertEmailMessageDT.getAlertMsgTxt() != null)  //text user can specify in the Alert
			sb.append("<br><br>" + alertEmailMessageDT.getAlertMsgTxt());
		logger.debug("PHCR eMail body text = " + sb.toString());
		return sb.toString();
	}
	
	
	public static String trimToTwoKLength(String s){
		if(s!=null && s.length()>1900){
			return s.substring(0, 1900);
		}
		else
			return s;
	}
	
	public void resetQueue(NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSSystemException, RemoteException{
		AlertRootDAO.resetQueue();
	}
	
	/*
	 * getReportingFacilityAndLabResultString - get the Reporting Facility and lab results associated with the Lab.
	 * This method is not efficient in that we are getting all the labs for the person and finding the one
	 * that matches so that we can reuse the current lab result parsing used by the front end in workup display.
	 * @param AlertEmailMessageDT 
	 * @param NBSSecurityObj 
	 * @return ArrayList with reporting facility followed by lab result
	 */
	private ArrayList<String> getReportingFacilityAndLabResultString(AlertEmailMessageDT alertEmailMessageDT, NBSSecurityObj nbsSecurityObj) {
		ArrayList<String> retStrAry = new ArrayList<String>();
		retStrAry.add("");retStrAry.add("");
		
		try{
		if (alertEmailMessageDT.getEventLocalId() == null ||
				alertEmailMessageDT.getEventLocalId().equalsIgnoreCase(AlertStateConstant.SIMULATE)) {
			    return retStrAry; //simulated
		}
		//get patent uid
		AlertEmailMessageDAO alertMessageDAO= new AlertEmailMessageDAO();
		Long patientMPR = alertMessageDAO.getMPRForLabReportLocalId(alertEmailMessageDT.getEventLocalId());
		if (patientMPR == null)
			return retStrAry;
        ArrayList<Object> uidList = new ArrayList<Object> ();
        uidList.add(patientMPR);
        
        //get all lab reports for patient
		ObservationProcessor observationProcessor = new ObservationProcessor();
		 HashMap<Object, Object> labReportMap = new HashMap<Object, Object>();
		 Collection<Object>  labReportSummaryVOCollection  = new ArrayList<Object> ();
		 String uidType = "PERSON_PARENT_UID";
		 try {
		 	labReportMap = observationProcessor.retrieveLabReportSummaryRevisited(uidList, false, nbsSecurityObj, uidType);
		    if(labReportMap.containsKey("labEventList"))
		         labReportSummaryVOCollection  = new ArrayList<Object> ((ArrayList<?> )labReportMap.get("labEventList"));
		 } 
		 catch(NEDSSSystemException ex)
		 {
		     logger.warn("Batch eMail User: Lab Report security settings doesn't allow lab collection to be returned");
		 } 
		 
	     if (labReportSummaryVOCollection == null || labReportSummaryVOCollection.size() == 0) {
	            logger.debug("Can't get the Lab Result for email alert purposes - Lab Report Summary Collection is null");
	            return retStrAry;
	     }
	     //find the one in the email message
	     Iterator<Object>  labIterator = labReportSummaryVOCollection.iterator();
	     while (labIterator.hasNext()) {
	                 LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO)labIterator.next();
	                 if (labReportSummaryVO.getLocalId() != null &&
	                		 labReportSummaryVO.getLocalId().equals(alertEmailMessageDT.getEventLocalId())) { 		
	                	 //Get the reported facility and the Lab Result display string 
	                	 String reportingFacility = labReportSummaryVO.getReportingFacility();
	                	 if (reportingFacility != null) {
	                		 logger.debug("Reporting Facility is " +reportingFacility);
	                		 retStrAry.add(0, reportingFacility);
	                	 }
	                	 //get the descriptive Lab Result in HTML format
	                	 String labResultStr = DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection());
	                	 if (labResultStr != null) {
	                		 logger.debug("Lab Result Display String = " +labResultStr);
	                		 retStrAry.add(1, labResultStr);
	                	 }
	                	 break;
	                 }
	      }
	     return retStrAry;
	}
	catch (Exception e)
	{
	logger.fatal("AlertEJB.getReportingFacilityAndLabResultString:" + e.getMessage(), e);
	throw new javax.ejb.EJBException(e);
	}
	}
	
	
	/*
	 * getSendingFacilityForCaseReport - get the Reporting Facility and Case Report document.
	 * @param AlertEmailMessageDT 
	 * @param NBSSecurityObj 
	 * @return sending facility name
	 */
	private String getSendingFacilityForCaseReport(String localId) {
		//get PHCR sending facility
		AlertEmailMessageDAO alertMessageDAO= new AlertEmailMessageDAO();
		return(alertMessageDAO.getDocumentSendingFacilityName(localId));
	}
	
}
