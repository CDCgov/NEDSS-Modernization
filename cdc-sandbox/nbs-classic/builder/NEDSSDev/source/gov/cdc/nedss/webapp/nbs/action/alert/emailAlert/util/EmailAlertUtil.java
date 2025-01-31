package gov.cdc.nedss.webapp.nbs.action.alert.emailAlert.util;

import gov.cdc.nedss.alert.dt.UserEmailDT;
import gov.cdc.nedss.alert.vo.UserEmailVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.alert.emailAlert.clientVO.EmailAlertClientVO;
import gov.cdc.nedss.webapp.nbs.form.alert.EmailAlert.EmailAlertUserForm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EmailAlertUtil {
	static final LogUtils logger = new LogUtils(EmailAlertUtil.class.getName());
	
public static void handleEmail(EmailAlertUserForm userform, String userAction, HttpServletRequest req) throws Exception 
	{
		HttpSession session = req.getSession();
		if(userform.getUsersList()!=null || userform.getUsersList().size()==0){
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			UserEmailVO userEmailVO = new UserEmailVO();
			
			Collection<Object>  coll = new ArrayList<Object> ();
			
			int seqNbr = 0;
			if((userform.getEmailAlertClientVO().getEmailId1())!=null && !userform.getEmailAlertClientVO().getEmailId1().trim().equals("")){
				UserEmailDT userEmailDT = new UserEmailDT();
				userEmailDT.setEmailId(userform.getEmailAlertClientVO().getEmailId1());
				userEmailDT.setSeqNbr(new Integer(++seqNbr));
				if(userform.getEmailAlertClientVO().getAddTime()!=null)
					userEmailDT.setAddTime(userform.getEmailAlertClientVO().getAddTime());
				else
					userEmailDT.setAddTime(new Timestamp(System.currentTimeMillis()));
				if(userform.getEmailAlertClientVO().getAddUserId()!=null)
					userEmailDT.setAddUserId(userform.getEmailAlertClientVO().getAddUserId());
				else
					userEmailDT.setAddUserId(Long.valueOf(userId));
					
				userEmailDT.setLastChgTime(new Timestamp(System.currentTimeMillis()));
				userEmailDT.setLastChgUserId(Long.valueOf(userId));
				userEmailDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				if(userform.getEmailAlertClientVO().getNedssEntryId()!=0)
					userEmailDT.setNedssEntryId(new Long(userform.getEmailAlertClientVO().getNedssEntryId()));
				coll.add(userEmailDT);
			}
			
			if((userform.getEmailAlertClientVO().getEmailId2()!=null && !userform.getEmailAlertClientVO().getEmailId2().trim().equals("")) ){
				UserEmailDT userEmailDT1 = new UserEmailDT();
				userEmailDT1.setEmailId(userform.getEmailAlertClientVO().getEmailId2());
				userEmailDT1.setSeqNbr(new Integer(++seqNbr));
				if(userform.getEmailAlertClientVO().getAddTime()!=null)
					userEmailDT1.setAddTime(userform.getEmailAlertClientVO().getAddTime());
				else
					userEmailDT1.setAddTime(new Timestamp(System.currentTimeMillis()));
				if(userform.getEmailAlertClientVO().getAddUserId()!=null)
					userEmailDT1.setAddUserId(userform.getEmailAlertClientVO().getAddUserId());
				else
					userEmailDT1.setAddUserId(Long.valueOf(userId));
				userEmailDT1.setLastChgTime(new Timestamp(System.currentTimeMillis()));
				userEmailDT1.setLastChgUserId(Long.valueOf(userId));
				userEmailDT1.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				//userEmailDT1.setNedssEntryId(userform.getEmailAlertClientVO().getNedssEntryId());
				if(userform.getEmailAlertClientVO().getNedssEntryId()!=0)
					userEmailDT1.setNedssEntryId(new Long(userform.getEmailAlertClientVO().getNedssEntryId()));

				coll.add(userEmailDT1);
			}
			if((userform.getEmailAlertClientVO().getEmailId3())!=null  && !userform.getEmailAlertClientVO().getEmailId3().trim().equals("")){
				UserEmailDT userEmailDT2 = new UserEmailDT();
				userEmailDT2.setEmailId(userform.getEmailAlertClientVO().getEmailId3());
				userEmailDT2.setSeqNbr(new Integer(++seqNbr));
				if(userform.getEmailAlertClientVO().getAddTime()!=null)
					userEmailDT2.setAddTime(userform.getEmailAlertClientVO().getAddTime());
				else
					userEmailDT2.setAddTime(new Timestamp(System.currentTimeMillis()));
				if(userform.getEmailAlertClientVO().getAddUserId()!=null)
					userEmailDT2.setAddUserId(userform.getEmailAlertClientVO().getAddUserId());
				else
					userEmailDT2.setAddUserId(Long.valueOf(userId));
				userEmailDT2.setLastChgTime(new Timestamp(System.currentTimeMillis()));
				userEmailDT2.setLastChgUserId(Long.valueOf(userId));
				userEmailDT2.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				if(userform.getEmailAlertClientVO().getNedssEntryId()!=0)
					userEmailDT2.setNedssEntryId(new Long(userform.getEmailAlertClientVO().getNedssEntryId()));
				coll.add(userEmailDT2);
			}
			userEmailVO.setUserEmailDTCollection(coll);
			Long nedssEntryId=new Long(userform.getEmailAlertClientVO().getNedssEntryId());
			if(userAction.equals(NEDSSConstants.EDIT_LOAD_ACTION)){
			 sendProxyToAlertEJB(userEmailVO,  nedssEntryId, userAction, req,session);
			NBSContext.store(session,  NEDSSConstants.Nedss_Entry_Id, nedssEntryId.toString());
			}
			if(userAction.equals(NEDSSConstants.VIEW_LOAD_ACTION)){
			UserEmailVO userEmailVO2 =emailAlertEJB(userEmailVO,  nedssEntryId, userAction, req,session);
			ArrayList<Object> uiVOList = new ArrayList<Object> ();
			uiVOList = new ArrayList<Object>(userEmailVO2.getUserEmailDTCollection());
			 EmailAlertClientVO emailAlertClientVO  = new EmailAlertClientVO();
			 if(!uiVOList.isEmpty()){
				Iterator<Object>  it = uiVOList.iterator();
				 int i = 0;
				 while(it.hasNext()){
					 UserEmailDT userEmailDT = (UserEmailDT)it.next();	
					 if(i==0){
							emailAlertClientVO.setEmailId1(userEmailDT.getEmailId());
							emailAlertClientVO.setAddUserId(userEmailDT.getAddUserId());
							emailAlertClientVO.setAddTime(userEmailDT.getAddTime());
					 }
					 if(i==1){
						emailAlertClientVO.setEmailId2(userEmailDT.getEmailId());
					 }if(i==2){
						emailAlertClientVO.setEmailId3(userEmailDT.getEmailId());
					 }
					 ++i;
				 }
			 }
			NBSContext.store(session, NEDSSConstants.Nedss_Entry_Id, emailAlertClientVO);
			emailAlertClientVO.setNedssEntryId(nedssEntryId.longValue());
			
			if(new Long(emailAlertClientVO.getNedssEntryId())==null){
				userform.getEmailAlertClientVO().setEmailId1("");
				userform.getEmailAlertClientVO().setEmailId2("");
				userform.getEmailAlertClientVO().setEmailId3("");
				}
			userform.setEmailAlertClientVO(emailAlertClientVO);
						
			}
			}		
			
		}
public static void sendProxyToAlertEJB(UserEmailVO userEmailVO,  Long nedssEntryId, String userAction, 
			HttpServletRequest request,HttpSession session) throws NEDSSAppConcurrentDataException, Exception 
	{
		MainSessionCommand msCommand = null;
		Long nedss_EntryId = null;
		String sBeanJndiName = JNDINames.ALERT_EJB;
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
		String sMethod = "updateUserEmailVO";
		Object[]  oParams = {userEmailVO,nedssEntryId, };
	    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    nedss_EntryId = (Long) resultUIDArr.get(0);
		
}
public static UserEmailVO emailAlertEJB(UserEmailVO userEmailVO, Long nedssEntryId, String userAction, 
			HttpServletRequest request,HttpSession session) throws NEDSSAppConcurrentDataException, Exception {
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.ALERT_EJB;
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);
		ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			String sMethod = "getUserEmailVO";
			Object[]  oParams = {nedssEntryId};
		    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		    userEmailVO= (UserEmailVO) resultUIDArr.get(0);
		
		return userEmailVO;
	}
}
