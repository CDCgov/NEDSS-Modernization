package gov.cdc.nedss.webapp.nbs.action.nbsDocument.util;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class NBSDocumentActionUtil {
	
	 static final LogUtils logger = new LogUtils(NBSDocumentActionUtil.class.getName());
	 
	 public NBSDocumentVO setNBSDocumentVOforUpdate(NBSDocumentVO nbsDocVO){
		 nbsDocVO.getNbsDocumentDT().setItDirty(true);
		 nbsDocVO.getNbsDocumentDT().setItNew(false);
		 nbsDocVO.getPatientVO().setItDirty(true);
		 nbsDocVO.getPatientVO().setItNew(false);
		 nbsDocVO.getPatientVO().getThePersonDT().setItDirty(true);
		 nbsDocVO.getPatientVO().getThePersonDT().setItNew(false);
		 nbsDocVO.getParticipationDT().setItDirty(true);
		 nbsDocVO.getParticipationDT().setItNew(false);
		 
		 return nbsDocVO;	 
		 
	 }
	 
	 public NBSDocumentVO setNBSDocumentVOforDelete(NBSDocumentVO nbsDocVO){

		 nbsDocVO.getNbsDocumentDT().setItDirty(false);
		 nbsDocVO.getNbsDocumentDT().setItNew(false);
		 nbsDocVO.getNbsDocumentDT().setItDelete(true);
		 
		 nbsDocVO.getPatientVO().setItDirty(true);
		 nbsDocVO.getPatientVO().setItNew(false);
		 nbsDocVO.getPatientVO().setItDelete(true);
		
		 nbsDocVO.getPatientVO().getThePersonDT().setItDirty(true);
		 nbsDocVO.getPatientVO().getThePersonDT().setItNew(false);
		 nbsDocVO.getPatientVO().getThePersonDT().setItDelete(true);
		 
		 nbsDocVO.getParticipationDT().setItDirty(false);
		 nbsDocVO.getParticipationDT().setItNew(false);
		 nbsDocVO.getParticipationDT().setItDelete(true);
		 
		 return nbsDocVO;	 
		 
	 }
	
	 public NBSDocumentVO getNBSDocument(HttpSession session, Long documentUid)throws Exception{
			MainSessionCommand msCommand = null;
			NBSDocumentVO nbsDocumentVO  = new NBSDocumentVO();
			try{
				String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
				String sMethod = "getNBSDocument";
				Object[] oParams =new Object[] {documentUid};				
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				
				ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
				   nbsDocumentVO = (NBSDocumentVO)aList.get(0);
				}catch (Exception ex) {
					logger.fatal("Error in getNBSDocument in Action Util: ", ex);
					throw new Exception(ex.toString());
				}
				 return nbsDocumentVO; 
	} 
	 
	 
	 
	 
	 public void setDocumentforUpdate(HttpSession session,NBSDocumentVO nbsDocVO)throws Exception{
					
				MainSessionCommand msCommand = null;
				try{
				
					String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
					String sMethod = "updateDocument";
					Object[] oParams =new Object[] {nbsDocVO};
					MainSessionHolder holder = new MainSessionHolder();
					msCommand = holder.getMainSessionCommand(session);
					ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
					}catch (Exception ex) {
						logger.fatal("Error in updateDocument in Action Util: ", ex);
						throw new Exception(ex.toString());
					}
		 }
	 
	 public void setDocumentforUpdateforTransferOwnership(HttpSession session,NBSDocumentVO nbsDocVO)throws Exception{
			
			MainSessionCommand msCommand = null;
			try{
				Long documentUID = nbsDocVO.getNbsDocumentDT().getNbsDocumentUid();
				String newProgramAreaCode = nbsDocVO.getNbsDocumentDT().getProgAreaCd();
				String newJurisdictionCode = nbsDocVO.getNbsDocumentDT().getJurisdictionCd();
				String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
				String sMethod = "transferOwnership";
				Object[] oParams =new Object[] {documentUID, newProgramAreaCode, newJurisdictionCode};
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				}catch (Exception ex) {
					logger.fatal("Error in updateDocument in Action Util: ", ex);
					throw new Exception(ex.toString());
				}
	 }
	 
	 public Boolean getInvestigationAssoWithDocumentColl(HttpSession session, Long nbsDocUid)throws Exception{
			
			MainSessionCommand msCommand = null;
			Boolean isInvAss ;
			try{
				String sBeanJndiName = JNDINames.NBS_DOCUMENT_EJB;
				String sMethod = "getInvestigationAssoWithDocumentColl";
				Object[] oParams =new Object[] {nbsDocUid};
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);				
				isInvAss = (Boolean)aList.get(0);
				}catch (Exception ex) {
					logger.fatal("Error in updateDocument in Action Util: ", ex);
					throw new Exception(ex.toString());
				}
				return isInvAss;
	 }

}
