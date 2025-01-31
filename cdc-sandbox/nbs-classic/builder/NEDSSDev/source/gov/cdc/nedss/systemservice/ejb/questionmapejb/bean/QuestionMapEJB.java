package gov.cdc.nedss.systemservice.ejb.questionmapejb.bean;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dao.NBSDocumentMetadataDAOImpl;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dao.NbsQuestionDAOImpl;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceValueDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetValueDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class QuestionMapEJB implements SessionBean {

	static final LogUtils logger = new LogUtils(QuestionMapEJB.class.getName());

	boolean verbose = false;

	private static PropertyUtil propUtil = PropertyUtil.getInstance();

	public Collection<Object>  getPamQuestions() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			Collection<Object>  questions = dao.retrievePamOuestions();

			return questions;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	/*
	 * Get the Rules for caching
	 */
	public Collection<Object>  getRuleCollection() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			Collection<Object>  ruleColl = dao.getRuleCollection();

			return ruleColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);

		}
	}

/************************** Start of methods added for Rule Engine UI Tool **********************************/
	/*
	 * Gets the Rules for caching
	 */
	public ArrayList<Object> getRuleList() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> ruleListColl = dao.getRuleList();

			return ruleListColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);

		}
	}

	/*
	 * Gets the Rules Instances for a particular Rule
	 */
	public ArrayList<Object> findRuleInstances(String whereclause) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> ruleInsColl = dao.findRuleInstances(whereclause);

			return ruleInsColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while finding rule instances with where clause: " + whereclause + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);

		}
	}
	
	/*
	 * Gets the Source Fields for a particular Rule Instance
	 */
	public ArrayList<Object> findSourceFields(String whereclause) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> srcField = dao.findSourceFields(whereclause);

			return srcField;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while finding souce fields with where clause: " + whereclause + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/*
	 * Gets the Target Fields for a particular Rule Instance
	 */
	public ArrayList<Object> findTargetFields(String whereclause) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> tarField = dao.findTargetFields(whereclause);

			return tarField;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while finding target fields with where clause: " + whereclause + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/*
	 * Gets the Source Values for a particular Source Field
	 */
	public ArrayList<Object> findSourceValues(String whereclause) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> srcVal = dao.findSourceValues(whereclause);

			return srcVal;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while finding souce values with where clause: " + whereclause + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/*
	 * Gets the Target Value for a particular Target Field
	 */
	public ArrayList<Object> findTargetValues(String whereclause) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> tarVal = dao.findTargetValues(whereclause);

			return tarVal;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while finding target values with where clause: " + whereclause + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/*
	 * Deletes  a particular Rule Instance
	 */
	public String deleteRuleIns(String strRuleInsUid) throws RemoteException, NEDSSSystemException, Exception {
       try {
		String status="";
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			status = dao.deleteRuleIns(strRuleInsUid);

			return status;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("Exception while deleting rule instance with Rule Instance Uid: " + strRuleInsUid + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
	}

	/*
	 * Gets the Conseq indicators
	 */
	public ArrayList<Object> getConseqInd() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> conseqColl = dao.getConseqInd();

			return conseqColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting Conseq indicators" + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/*
	 * Gets the Error Messages
	 */
	public ArrayList<Object> getErrorMessages() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> errorMColl = dao.getErrorMessages();

			return errorMColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting error messages" + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/*
	 * Gets the Operator types
	 */
	public ArrayList<Object> getOperatorTypes() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> opType = dao.getOperatorTypes();

			return opType;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting operator types" + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/*
	 * Gets all the Pam Questions in the NBS.
	 */
	public ArrayList<Object> getPAMLabels() throws RemoteException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> pamLColl = dao.getPAMLabels();

			return pamLColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting the Pam Questions" + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Adds a new  Rule Instances for a particular Rule
	 */
	public String addRuleInstance(RulesDT rulesDT) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			String ruleInsUid = dao.addRuleInstance(rulesDT);

			return ruleInsUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while adding a new  Rule Instances for a particular Rule with Uid: " + rulesDT.getRuleUid() + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Adds a new Source Field to a existing Rules Instance.
	 */
	public String addSourceField(SourceFieldDT srcFieldDT) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			String ruleInsUid = dao.addSourceField(srcFieldDT);

			return ruleInsUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while adding a new source field to an existing rule instance Rule with Uid: " + srcFieldDT.getRuleInstanceUid() + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Adds a new Target Field to a existing Rules Instance.
	 */
	public String addTargetField(TargetFieldDT tarFieldDT) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			String ruleInsUid = dao.addTargetField(tarFieldDT);

			return ruleInsUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while adding a new target field to an existing rule instance Rule with Uid: " + tarFieldDT.getRuleInstanceUid() + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Adds a new Source Value to a existing Source Field.
	 */
	public String addSourceValue(SourceValueDT srcValueDT) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			String ruleInsUid = dao.addSourceValue(srcValueDT);

			return ruleInsUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while adding a new source value to an existing source field with Uid: " + srcValueDT.getSourceFieldUid() + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Adds a new Target Value to a existing Target Field.
	 */
	public String addTargetValue(TargetValueDT tarValueDT) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			String ruleInsUid = dao.addTargetValue(tarValueDT);

			return ruleInsUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while adding a new target value to an existing target field with Uid: " + tarValueDT.getTargetFieldUid() + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Updates an existing Source Value
	 */
	public boolean updateSourceValue(SourceValueDT srcValueDT) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			boolean updatestatus = dao.updateSourceValue(srcValueDT);

			return updatestatus;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while updating an existing source value with Uid: " + srcValueDT.getSourceValueUid() + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Updates an existing Target Value
	 */
	public boolean updateTargetValue(TargetValueDT tarValueDT) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			boolean updatestatus = dao.updateTargetValue(tarValueDT);

			return updatestatus;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while updating an existing target value with Uid: " + tarValueDT.getTargetValueUid() + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Deletes an existing Source Field
	 */
	public boolean deleteSourceField(String srcFieldUID) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			boolean updatestatus = dao.deleteSourceField(srcFieldUID);

			return updatestatus;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while deleting an existing source field with Uid: " + srcFieldUID + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Deletes an existing Target Field
	 */
	public boolean deleteTargetField(String tarFieldUID) throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			boolean updatestatus = dao.deleteTargetField(tarFieldUID);

			return updatestatus;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while deleting an existing target field with Uid: " + tarFieldUID + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/*
	 * Gets all the form codes related to the collections of the questions identifier
	 */
	public ArrayList<Object> getFormCode(Collection<Object> questionIdentColl) throws RemoteException, NEDSSSystemException, Exception {
		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> rulesColl = dao.getFormCode(questionIdentColl);
			return rulesColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting the form codes related to the collections of the questions identifier "+ e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
	}
	
	/*
	 * Associates  Form Codes to a existing Rule Instances
	 */
	public boolean associateFormCode(ArrayList<Object>  rulesDT) throws RemoteException, NEDSSSystemException, Exception {
		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			boolean associatestatus = dao.associateFormCode(rulesDT);
			return associatestatus;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while associating form Codes to an existing Rule Instance" +e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
	}
	/*
	 * Gets  all Form Codes associated to a existing Rule Instances
	 */
	public ArrayList<Object> findfrmCodes(String strRuleInsUid) throws RemoteException, NEDSSSystemException, Exception {
		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			ArrayList<Object> frmList = dao.findfrmCodes(strRuleInsUid);
			return frmList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting all the form Codes associated to an existing Rule Instance with Uid: " + strRuleInsUid + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
	}
	
	/**
	 * Method to retrieve the required questions to create a Notification
	 * @param formCd
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Collection<Object>  retrieveQuestionRequiredNnd(String formCd) throws RemoteException, NEDSSSystemException, Exception {
		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			if(formCd !=null && (formCd.equals(NBSConstantUtil.INV_FORM_RVCT) || formCd.equals(NBSConstantUtil.INV_FORM_VAR)))
				return dao.retrieveQuestionRequiredNnd(formCd);
			else
				return dao.retrieveQuestionRequiredNndForDMB(formCd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while retrieving the required questions to create a notification with form code: " + formCd + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
	}

	/**
	 * Method to retrieve Collection<Object>  of Aggregate Summary Data
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
	public Collection<Object>  retrieveAggregateData() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			Collection<Object>  summaryColl = dao.retrieveAggregateData();

			return summaryColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while retrieving the Aggregate Summary Data" +  e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public Collection<Object>  retrieveNBSDocumentMetadata() throws RemoteException, NEDSSSystemException, Exception {
		try {
			NBSDocumentMetadataDAOImpl dao = new NBSDocumentMetadataDAOImpl();
			return dao.getNBSDocumentMetadata();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while retrieving the NBS Document Metadata" +  e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
	}
	public Collection<Object>  getContactQuestions() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			Collection<Object>  questions = dao.retrieveContactOuestions();

			return questions;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting the contact questions" +  e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public Collection<Object>  getDMBQuestions() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			Collection<Object>  questions = dao.retrieveDMBOuestions();
			Collection<Object> templateQuestions = dao.retrieveGenericTemplateOuestions();
			questions.addAll(templateQuestions);
			return questions;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception while getting the DMB questions" +  e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public Collection<Object>  getPrePopMapping() throws RemoteException, NEDSSSystemException, Exception {

		try {
			NbsQuestionDAOImpl dao = new NbsQuestionDAOImpl();
			Collection<Object>  prepopMapping = dao.retrievePrePopMapping();
			return prepopMapping;
		} catch (Exception e) {
			logger.fatal("Exception while getting PrePop Mapping" +  e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	
	/************************** End of methods added for Rule Engine UI Tool **********************************/	


	public QuestionMapEJB() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
	}
}
