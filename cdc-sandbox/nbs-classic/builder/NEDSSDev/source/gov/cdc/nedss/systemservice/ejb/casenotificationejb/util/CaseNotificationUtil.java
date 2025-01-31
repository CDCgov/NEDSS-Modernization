package gov.cdc.nedss.systemservice.ejb.casenotificationejb.util;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.util.ArrayList;

import javax.rmi.PortableRemoteObject;


public class CaseNotificationUtil {
	static final LogUtils logger = new LogUtils(CaseNotificationUtil.class
			.getName());
	/*public void getActiveExportAlgorithm(HttpServletRequest request, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSAppConcurrentDataException, Exception{
		CaseNotificationDAOImpl caseNotificationDAOImpl= new CaseNotificationDAOImpl();
		Collection<Object>  activeAlgorithmColl=caseNotificationDAOImpl.getExportAlgorithmrCollection();
		NbsQuestionDT nbsQuestionDT =null;
		TreeSet<Object>set  = new TreeSet<Object>();
		NBSQuestionDAOImpl nBSQuestionDAOImpl = new NBSQuestionDAOImpl();
		if(activeAlgorithmColl!=null && activeAlgorithmColl.size()>0){
			java.util.Iterator it = activeAlgorithmColl.iterator();
			StringBuffer generateSelectClause= new StringBuffer();
			StringBuffer generateWhereClause= new StringBuffer();
			generateSelectClause.append("Select public_health_case_uid, ");
			while(it.hasNext()){
				ExportAlgorithmDT exportAlgorithmDT =(ExportAlgorithmDT)it.next();
				Collection<Object>  exportTriggerColl = caseNotificationDAOImpl.getExportTrigggerCollection(exportAlgorithmDT.getExportAlgorithmUid());
				if (exportTriggerColl!=null){
					java.util.Iterator iter = exportTriggerColl.iterator();
					while(iter.hasNext()){
						ExportTriggerDT exportTriggerDT=(ExportTriggerDT)iter.next();

						nbsQuestionDT = nBSQuestionDAOImpl.findNBSQuestion(exportTriggerDT.getNbsQuestionUid());
						set.add(nbsQuestionDT.getDataLocation());
						if(iter.hasNext()){
							generateSelectClause.append(nbsQuestionDT.getDataLocation()+",");
							generateWhereClause.append(nbsQuestionDT.getDataLocation() + exportTriggerDT.getTriggerLogic()+ exportTriggerDT.getTriggerFilter() +" and ");

						}
						else{
							generateSelectClause.append(nbsQuestionDT.getDataLocation());
							generateWhereClause.append(nbsQuestionDT.getDataLocation() + exportTriggerDT.getTriggerLogic()+ exportTriggerDT.getTriggerFilter());

						}

					}
				}
				String dynamicWhenClauseBuilder= dynamicWhereCluaseBuilder(set);
				String FullSQLQuery= generateSelectClause+dynamicWhenClauseBuilder+generateWhereClause;
				Collection<Object>  phcColl= caseNotificationDAOImpl.getExportTrigggerPHCCollection(FullSQLQuery);
				if(phcColl!=null && phcColl.size()==0){
					logger.debug("There exists no phc case for export trigger"+FullSQLQuery );
				}
				if(phcColl!=null && phcColl.size()>0){

					logger.debug("There exists phc cases  for export trigger"+FullSQLQuery );
					java.util.Iterator iterator = phcColl.iterator();
					while(iterator.hasNext()){
						Long publicHealthCaseUID =(Long) it.next();
						NedssUtils nedssUtils = new NedssUtils();
						Object theLookedUpObject;
						theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
						ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
						narrow(theLookedUpObject, ActControllerHome.class);
						ActController actController = actHome.create();
						PublicHealthCaseVO  thePublicHealthCaseVO = actController.getPublicHealthCase(
								publicHealthCaseUID, nbsSecurityObj);

						NotificationProxyVO notificationProxyVO =setShareNotificationProxyVO( thePublicHealthCaseVO,  exportAlgorithmDT.getExportReceivingFacilityUid(),  nbsSecurityObj);
						setShareCase(notificationProxyVO, request);

					}

				}

			}

		}

	}

	private static Long setShareCase(NotificationProxyVO notProxyVO, HttpServletRequest request)
	throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSAppConcurrentDataException, Exception {
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
		String sMethod = "shareCase";
		ArrayList<Object> resultUIDArr = new ArrayList<Object> ();

		if (msCommand == null) {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
		}
		Object[] oParams = { notProxyVO};
		resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);        	
		if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			Long result = (Long) resultUIDArr.get(0);
			return result;
		} else
			return null;
	}


	private String dynamicWhereCluaseBuilder(TreeSet<Object>set){
		java.util.Iterator it = set.iterator();
		StringBuffer buff = new StringBuffer();
		StringBuffer whereClause= new StringBuffer();
		while(it.hasNext()){
			String dataLocation = (String)it.next();
			String Table_Name = dataLocation.substring(0,dataLocation.indexOf(".") );
			String Column_Name = dataLocation.substring(dataLocation.lastIndexOf((".") , dataLocation.length()));
			logger.debug("Table_Name is "+ Table_Name);
			logger.debug("Column_Name is "+ Column_Name);
			if(dataLocation.equalsIgnoreCase("PUBLIC_HEALTH_CASE")){
				buff.append("FROM " +Table_Name );
			}
			if(dataLocation.equalsIgnoreCase("PERSON")){
				buff.append(", " +Table_Name );
				whereClause.append("PUBLIC_HEALTH_CASE.PUBLIC_HEALTH_CASE_UID=PARTICIPATION.ACT_UID AND PARTICIPATION.SUBJECT_ENTITY_UID=PERSON.PERSON_UID AND " +
				"PUBLIC_HEALTH_CASE.PUBLIC_HEALTH_CASE_UID NOT IN (SELECT ACT_UID FROM NBS_Master_Log)");
			}
			
			if(whereClause.length()>0){
				buff.append(whereClause);
			}

		}
		return buff.toString();
	}

	public  static NotificationProxyVO setShareNotificationProxyVO(PublicHealthCaseVO publicHealthCaseVO, Long exportReceivingFacilityUid,
			NBSSecurityObj nbsSecurityObj)
	throws java.rmi.RemoteException, javax.ejb.EJBException,
	NEDSSAppConcurrentDataException, Exception {
		NotificationDT notificationDT = new NotificationDT();
		java.util.Date startDate= new java.util.Date();
		Timestamp aAddTime = new Timestamp(startDate.getTime());
		notificationDT.setAddTime(aAddTime); 
		notificationDT.setLastChgTime(aAddTime);
		notificationDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
		notificationDT.setExportReceivingFacilityUid(exportReceivingFacilityUid);
		notificationDT.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
		notificationDT.setCaseClassCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd());
		notificationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		notificationDT.setItNew(false);
		notificationDT.setItDirty(true);
		notificationDT.setRecordStatusCd(NEDSSConstants.PENDING_APPROVAL_STATUS);
		notificationDT.setAutoResendInd(NEDSSConstants.NOTIFICATION_AUTO_RESEND_OFF);
		notificationDT.setStatusTime(aAddTime);
		notificationDT.setProgramJurisdictionOid(publicHealthCaseVO.getThePublicHealthCaseDT().getProgramJurisdictionOid());
		notificationDT.setRecordStatusTime(aAddTime);
		notificationDT.setJurisdictionCd(publicHealthCaseVO.getThePublicHealthCaseDT().getJurisdictionCd());
		notificationDT.setProgAreaCd(publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd());
		notificationDT.setVersionCtrlNbr(new Integer(1));
		notificationDT.setCaseConditionCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCd());
		notificationDT.setNotificationUid(new Long(-1));
		notificationDT.setCd("EXP_NOTF_BATCH");
		NotificationVO notVO = new NotificationVO();
		notVO.setTheNotificationDT(notificationDT);
		notVO.setItNew(true);

		ActRelationshipDT actDT1 = new ActRelationshipDT();
		actDT1.setItNew(true);
		actDT1.setTargetActUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
		actDT1.setSourceActUid(notificationDT.getNotificationUid());
		actDT1.setAddTime(aAddTime);
		actDT1.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
		actDT1.setSequenceNbr(new Integer(1));
		actDT1.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		actDT1.setStatusTime(aAddTime);
		actDT1.setTypeCd(NEDSSConstants.ACT106_TYP_CD);
		actDT1.setSourceClassCd(NEDSSConstants.ACT106_SRC_CLASS_CD);
		actDT1.setTargetClassCd(NEDSSConstants.ACT106_TAR_CLASS_CD);
		NotificationProxyVO  notProxyVO = new NotificationProxyVO();
		notProxyVO.setItNew(true);
		notProxyVO.setThePublicHealthCaseVO(publicHealthCaseVO);
		notProxyVO.setTheNotificationVO(notVO);
		ArrayList<Object> actRelColl = new ArrayList<Object> ();
		actRelColl.add(0, actDT1);
		notProxyVO.setTheActRelationshipDTCollection(actRelColl);
		return notProxyVO;

	}

*/
	
	public static void main(String[] args)
	{
		System.out.println("CaseNotificationUtil batch process  started.  Please wait");
		try
		{
			String runProcess = null;
			String userName = null;
			if(args.length == 2){
				userName = args[0];
				runProcess = args[1];
				System.out.println(userName);
			} else {
				System.out.println("Usage :  CaseNotificationUtil.bat [CASE)NOTIFICATION_USER_ID] [run]"); 
				System.exit(-1); 
			}
			
			generateCasesForExportAlgorithm(userName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fatal("Error: " + e.getMessage());
		}

		System.out.println("CaseNotificationUtil Complete!!!");
	} 
	
	private static int generateCasesForExportAlgorithm(String userName){
		int totalNumberOfCases=0;
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);		
		try {
			
				MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(objref, MainSessionCommandHome.class);
				MainSessionCommand msCommand = home.create();

				logger.info("msCommand = " + msCommand.getClass());

				logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
				
				
				nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

				logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
				String sBeanJndiName = JNDINames.CASE_NOTIFICATION_EJB;
				String sMethod = "getActiveExportAlgorithm";
				Object[] oParams = new Object[] {};
			

			ArrayList<?>  arr  =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			  Integer totalCases= (Integer)arr.get(0);
			  if(totalCases != null)
				  totalNumberOfCases=totalCases.intValue();
			  System.out.println("Total Number of cases shared : +totalNumberOfCases "+totalNumberOfCases);
			  return totalNumberOfCases;
		} catch (Exception  e) {
			e.printStackTrace();
			System.out.println("Total Number of cases shared : +totalNumberOfCases"+e.getMessage() );
		}
		  return totalNumberOfCases;
	}


}
