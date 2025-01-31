package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.association.dao.ParticipationHistDAOImpl;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl;
import gov.cdc.nedss.localfields.ejb.dao.NBSUIMetaDataDAOImpl;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.rmi.PortableRemoteObject;

public class ReversePamConversionDAO  extends DAOBase{
    static final LogUtils logger = new LogUtils(ReversePamConversionDAO.class.getName());
	private static final String SELECT_FROM_PHC_TABLE_SQL = "Select TOP 1   public_health_case_uid \"publicHealthCaseUid\" from public_health_case where cd=? and public_health_case_uid not in   (select act_uid from NBS_conversion_master where process_type_ind='Production' and  status_cd  in( 'UpdatePass') ) and  case_type_cd <>'S' order by public_health_case_uid desc";
	
	private String COUNT_PHC_CASES= " Select count(*) from public_health_case where cd='10030' and public_health_case_uid not in ( select act_uid from nbs_conversion_master" +
			  		" where status_cd  in( 'UpdatePass')) and  case_type_cd <>'S'";
                            
	
	@SuppressWarnings("unchecked")
	public  Collection<Object>  gePamProxyVOColl(String cd, NBSSecurityObj nbsSecurityObj ){
		PublicHealthCaseDT publicHealthCaseDT  = new PublicHealthCaseDT();
		ArrayList<Object> publicHealthCaseDTCollection  = new ArrayList<Object> ();
		publicHealthCaseDTCollection.add(cd);
		Collection<Object>  pamProxyVOColl= new ArrayList<Object> ();
		String query=null;
		try
		{
			query=SELECT_FROM_PHC_TABLE_SQL;
			publicHealthCaseDTCollection  = (ArrayList<Object> )preparedStmtMethod(publicHealthCaseDT, publicHealthCaseDTCollection, query, NEDSSConstants.SELECT);
			Iterator it = publicHealthCaseDTCollection.iterator();
			PamProxy pamproxy = null;
	        NedssUtils nedssUtils = new NedssUtils();
	        Object object = nedssUtils.lookupBean(JNDINames.PAM_PROXY_EJB);
	        PamProxyHome home = (PamProxyHome) PortableRemoteObject.narrow(object, PamProxyHome.class);
	        pamproxy = home.create();	        
			while(it.hasNext()){
			PublicHealthCaseDT pubHCaseDT=(PublicHealthCaseDT)it.next();
				Long  publicHealthCaseUid = pubHCaseDT.getPublicHealthCaseUid();
			    PamProxyVO proxyVO = new PamProxyVO(); 
		        proxyVO =  pamproxy.getPamProxy(publicHealthCaseUid, nbsSecurityObj);
		        pamProxyVOColl.add(proxyVO);
		        
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in ReversePamConversionDAO.gePamProxyVOColl:  ERROR = " + ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString(), ex);
		}
		 return pamProxyVOColl;
	}

	
	public  int getPublicHealthDTReverseCount(String cd){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		 int numberOfCasesNeedsToBeTransferred = 0;
		ResultSet resultSet=null;
		logger.debug(" COUNT_PHC_CASES="+ COUNT_PHC_CASES);
		dbConnection = getConnection();
		try{
				preparedStmt = dbConnection.prepareStatement( COUNT_PHC_CASES);
				int i = 1;
			    //preparedStmt.setString(i++, cd); 
				
				 resultSet = preparedStmt.executeQuery();
				  if (resultSet.next())
		            {
					  numberOfCasesNeedsToBeTransferred = resultSet.getInt(1);
		            }
				logger.debug("resultCount is " +numberOfCasesNeedsToBeTransferred);
		}
		catch(SQLException sqlex)
		{
			    logger.fatal("SQLException while ReversePamConversionDAO.getPublicHealthDTReverseCount: cd :"+cd, sqlex);
			    throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
		    logger.fatal("Exception while ReversePamConversionDAO.getPublicHealthDTReverseCount: cd :"+cd+":newRecordStatusCode", ex);
				    throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			  closeStatement(preparedStmt);
			  releaseConnection(dbConnection);
		}
		return numberOfCasesNeedsToBeTransferred;
    }//end
	
	
	
	public  void sePamProxyVO(PamProxyVO pamProxyVO, NBSSecurityObj nbsSecurityObj ){
		try
		{
			PamProxy pamproxy = null;
			pamProxyVO.setItDirty(true);
			java.util.Date dateTime = new java.util.Date();
			Timestamp lastChgTime = new Timestamp(dateTime.getTime());
		

			pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setLastChgTime(lastChgTime);
			pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setRecordStatusTime(lastChgTime);
			pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setLastChgUserId(new Long(nbsSecurityObj
					.getEntryID()));
		
			pamProxyVO.getPublicHealthCaseVO().setItDirty(true);
			pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDirty(true);
			if(pamProxyVO.getThePersonVOCollection()!=null){
				Iterator<Object> it = pamProxyVO.getThePersonVOCollection().iterator();
				while(it.hasNext()){
					PersonVO personVO= (PersonVO)it.next();
					if(personVO.getThePersonDT().getCd().equals(
							NEDSSConstants.PAT)){
						personVO.setItDirty(true);
						personVO.getThePersonDT().setItDirty(true);
						personVO.getThePersonDT().setLastChgTime(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime());
						personVO.getThePersonDT().setLastChgUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId());
					}
				}
			}
			if(pamProxyVO.getTheParticipationDTCollection()!=null){
				Iterator<Object> it = pamProxyVO.getTheParticipationDTCollection().iterator();
				while(it.hasNext()){
					ParticipationDT participationDT= (ParticipationDT)it.next();
					participationDT.setLastChgTime(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime());
					participationDT.setLastChgUserId(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId());
					participationDT.setItDirty(true);
					participationDT.setItNew(false);
				}
			}
			
			  if(pamProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection() != null && pamProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection() != null)
		      {
		          ActIdDT actIdDT = null;
		         Iterator<Object>  itr = pamProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection().iterator();
		          while(itr.hasNext() )
		          {
		              actIdDT = (ActIdDT) itr.next();
		              actIdDT.setItDirty(true);
		              actIdDT.setItNew(false);
		          }
		      }
			
			 Collection<Object>  coll =pamProxyVO.getPamVO().getActEntityDTCollection();
			 if(coll!=null){
				Iterator<Object>  it = coll.iterator();
				 while(it.hasNext()){
					 NbsActEntityDT nbsCaseEntityDT =  (NbsActEntityDT)it.next();
					 if(!nbsCaseEntityDT.isItDelete()){
						 nbsCaseEntityDT.setItDirty(true);
						 nbsCaseEntityDT.setItNew(false);
						 nbsCaseEntityDT.setLastChgUserId(pamProxyVO
									.getPublicHealthCaseVO().getThePublicHealthCaseDT()
									.getLastChgUserId());
						 nbsCaseEntityDT.setRecordStatusCd(pamProxyVO.getPublicHealthCaseVO()
									.getThePublicHealthCaseDT()
									.getRecordStatusCd());
						 nbsCaseEntityDT.setRecordStatusTime(pamProxyVO.getPublicHealthCaseVO().
								 getThePublicHealthCaseDT().getRecordStatusTime());
						 
					 }
				 }
			 }
			 
			Set<Object> set =  pamProxyVO.getPamVO().getPamAnswerDTMap().keySet();
			if(set !=null){
				Iterator<Object> it = set.iterator();
				while(it.hasNext()){
					Long nbsQuestionUid= (Long)it.next();
					Object object = pamProxyVO.getPamVO().getPamAnswerDTMap().get(nbsQuestionUid);
					if(object instanceof NbsCaseAnswerDT){
						NbsCaseAnswerDT NbsCaseAnswerDT = (NbsCaseAnswerDT)object;
						 if(!NbsCaseAnswerDT.isItDelete()){
							 NbsCaseAnswerDT.setItDirty(true);
							 NbsCaseAnswerDT.setItNew(false);
						 }
					}
					else if(object instanceof ArrayList<?>){
						Collection<?>  innerCollection  = (ArrayList<?> )object;
						Iterator<?> iter = innerCollection.iterator();
				   		while(iter.hasNext()){
				   			NbsCaseAnswerDT NbsCaseAnswerDT = (NbsCaseAnswerDT)iter.next();
				   			if(!NbsCaseAnswerDT.isItDelete()){
									NbsCaseAnswerDT.setItDirty(true);
									NbsCaseAnswerDT.setItNew(false);
				   			}
				   		}//end iterator iter
					}//end else if
					
				}//end outer iterator
			}//end set is null check
			
			ParticipationHistDAOImpl participationHistDAOImpl = new ParticipationHistDAOImpl();
			participationHistDAOImpl.store(pamProxyVO.getTheParticipationDTCollection());
			
	        NedssUtils nedssUtils = new NedssUtils();
	        Object object = nedssUtils.lookupBean(JNDINames.PAM_PROXY_EJB);
	        PamProxyHome home = (PamProxyHome) PortableRemoteObject.narrow(object, PamProxyHome.class);
	        pamproxy = home.create();	        
	        pamproxy.setPamProxy(pamProxyVO, nbsSecurityObj);
		}
		 catch (Exception ex) {
			logger.fatal("Exception in ReversePamConversionDAO.sePamProxyVO:  ERROR = " + ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString(), ex);
		}
	}
	
	  @SuppressWarnings("unchecked")
	public void logicallyDeleteMetada() throws NEDSSDAOSysException, NEDSSSystemException
	    {
		  String UPDATE= "";
	        int resultCount = 0;
			java.util.Date dateTime = new java.util.Date();
			Timestamp chgTme = new Timestamp(dateTime.getTime());
	        ArrayList<Object> nbsUIMetadataColl= new ArrayList<Object> ();
	        NbsUiMetadataDT nbsUiMetadataDT = new NbsUiMetadataDT();
	        try
	        {

	            nbsUIMetadataColl = (ArrayList<Object> )preparedStmtMethod(nbsUiMetadataDT, nbsUIMetadataColl, FIND_NBS_UI_METADATA, NEDSSConstants.SELECT);
	            if (nbsUIMetadataColl.size()==0)
	            {
	                logger.info("ReversePamConversionDAO.logDelNBSQuestionDTColl: there exisits no UI Metadata DT that needs to be updated nbsUIMetadataColl= " +
	                		nbsUIMetadataColl.size());
	            }
	           Iterator<Object>  it = nbsUIMetadataColl.iterator();
	            while(it.hasNext()){
	            	NbsUiMetadataDT nbsuiMetadataDT =(NbsUiMetadataDT)it.next();
	            	nbsuiMetadataDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
	            	nbsuiMetadataDT.setRecordStatusTime(chgTme);
	        		deleteNBSUiMetadata(nbsuiMetadataDT); 
	            	
	        		NBSQuestionDAOImpl nBSQuestionDAOImpl =  new NBSQuestionDAOImpl();
	            	NbsQuestionDT nbsQuestionDT=nBSQuestionDAOImpl.findNBSQuestion(nbsuiMetadataDT.getNbsQuestionUid());
	            	int qversionCtrlNbr=0;
	            	if(nbsQuestionDT.getVersionCtrlNbr()!=null){
	            		qversionCtrlNbr=nbsQuestionDT.getVersionCtrlNbr().intValue()+1;
	            	}
	            	nbsQuestionDT.setVersionCtrlNbr(new Integer(qversionCtrlNbr));
	            	nbsQuestionDT.setRecordStatusTime(chgTme);
	        		nBSQuestionDAOImpl.updateNBSQuestion(nbsQuestionDT);
	           
	            }
	        }
	        catch (Exception se)
	        {
	            logger.fatal(
	                    "ReversePamConversionDAO.logDelNBSQuestionDTColl: SQLException while updating"+se.getMessage(), se);
	            throw new NEDSSDAOSysException("Error: SQLException while updating\n" +
	                                           se.getMessage(), se);
	        }
	    }
	  private static final String GET_TO_QUESTION_ID="" +
		" select nbs_question_uid from NBS_QUESTION where question_identifier in ( "
		+" select to_question_id "
		+" from nbs_conversion_mapping where from_question_id in "
		+" ('INV112','INV138','INV139','INV140','INV148','INV149','INV152','INV153','INV154','INV155','INV156','INV157','INV159','INV161','INV162') )";
	  
	   
	  private static final String FIND_NBS_UI_METADATA = "SELECT num.nbs_ui_metadata_uid \"nbsUiMetadataUid\", "
			+"num.nbs_ui_component_uid \"nbsUiComponentUid\", " 
			+"num.nbs_question_uid \"nbsQuestionUid\", " 
			+"num.parent_uid \"parentUid\", " 
			+"num.question_label \"questionLabel\", " 
			+"num.question_tool_tip \"questionToolTip\", " 
			+"num.investigation_form_cd \"investigationFormCd\", " 
			+"num.enable_ind \"enableInd\", " 
			+"num.default_value \"defaultValue\", " 
			+"num.display_ind \"displayInd\", " 
			+"num.order_nbr \"orderNbr\", " 
			+"num.required_ind \"requiredInd\", " 
			+"num.tab_order_id \"tabOrderId\", " 
			+"num.tab_name \"tabName\", " 
			+"num.add_time \"addTime\", " 
			+"num.add_user_id \"addUserId\", " 
			+"num.last_chg_time \"lastChgTime\", " 
			+"num.last_chg_user_id \"lastChgUserId\", " 
			+"num.record_status_cd \"recordStatusCd\", " 
			+"num.record_status_time \"recordStatusTime\", " 
			+"num.max_length \"maxLength\", " 
			+"num.ldf_position \"ldfPosition\", " 
			+"num.css_style \"cssStyle\", " 
			+"num.ldf_page_id \"ldfPageId\", "
			+"num.ldf_status_cd \"ldfStatusCd\", "
			+"num.admin_comment \"adminComment\", "
			+"num.ldf_status_time \"ldfStatusTime\", "
			+"num.version_ctrl_nbr \"versionCtrlNbr\", "
			+"num.field_size \"fieldSize\", "
			+"nq.datamart_column_nm \"datamartColumnNm\" "
			+"FROM NBS_UI_Metadata num "
			+"LEFT OUTER JOIN "
			+"NBS_Question nq ON num.nbs_question_uid = nq.nbs_question_uid "
			+" where nq.nbs_question_uid in ("+GET_TO_QUESTION_ID+")";

private static final String DELETE_NBS_UI_METADATA = "UPDATE NBS_UI_Metadata SET record_status_cd='LOG_DEL', record_status_time=? ,ldf_status_cd='LDF_UPDATE', ldf_status_time=?, version_ctrl_nbr=?,last_chg_time=?,last_chg_user_id=? WHERE nbs_ui_metadata_uid=? ";

/**
 * Delete NBSQuestion by metadata Uid
 * @param nbsUiMetadataUid
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public void deleteNBSUiMetadata(NbsUiMetadataDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
	NBSUIMetaDataDAOImpl nBSUIMetaDataDAOImpl  = new NBSUIMetaDataDAOImpl();
	//History
	try {
		nBSUIMetaDataDAOImpl.updateNBSUiMetadataHistory(dt.getNbsUiMetadataUid());
		
		int versionctrlNbr=0;
		if(dt.getVersionCtrlNbr()!=null){
			versionctrlNbr=dt.getVersionCtrlNbr().intValue()+1;
		}
		
		dt.setVersionCtrlNbr(new Integer(versionctrlNbr));
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(dt.getRecordStatusTime());
		paramList.add(dt.getLdfStatusTime());
		paramList.add(dt.getVersionCtrlNbr());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		//where param
		paramList.add(dt.getNbsUiMetadataUid());
				
		preparedStmtMethod(null, paramList, DELETE_NBS_UI_METADATA, NEDSSConstants.UPDATE);
		
	}catch (NEDSSDAOSysException ex) {
		logger.fatal("NEDSSDAOSysException in deleteNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSDAOSysException(ex.toString(), ex);
	}catch (Exception ex) {
		logger.fatal("Exception in deleteNBSUiMetadata: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString(), ex);
	}			
	
}
}