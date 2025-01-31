package gov.cdc.nedss.systemservice.ejb.questionmapejb.bean;

import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceValueDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetValueDT;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJBObject;

public interface QuestionMap extends EJBObject {

	public Collection<Object>  getPamQuestions() throws RemoteException, Exception;
	/*
	 * To get the Rules for caching 
	 */
	public Collection<Object>  getRuleCollection() throws RemoteException, Exception;
	
	/* Methods added for rule Admin Tool */
	public ArrayList<Object> getRuleList() throws RemoteException, Exception;
	
	public ArrayList<Object> findRuleInstances(String whereclause) throws RemoteException, Exception;
	
	public ArrayList<Object> findSourceFields(String whereclause) throws RemoteException, Exception;
	
	public ArrayList<Object> findTargetFields(String whereclause) throws RemoteException, Exception;
	
    public ArrayList<Object> findSourceValues(String whereclause) throws RemoteException, Exception;
	
	public ArrayList<Object> findTargetValues(String whereclause) throws RemoteException, Exception;
	
	public ArrayList<Object> getConseqInd() throws RemoteException, Exception;
	
	public ArrayList<Object> getErrorMessages() throws RemoteException, Exception;
	
    public ArrayList<Object> getOperatorTypes() throws RemoteException, Exception;
	
	public ArrayList<Object> getPAMLabels() throws RemoteException, Exception;
	
	public String deleteRuleIns(String strRuleInsUid) throws RemoteException, Exception;
	
	public String addRuleInstance(RulesDT rulesDT) throws RemoteException, Exception;
	
	public String addSourceField(SourceFieldDT srcFieldDT) throws RemoteException, Exception;
	
	public String addTargetField(TargetFieldDT tarFieldDT) throws RemoteException, Exception;
	
	public String addSourceValue(SourceValueDT srcValueDT) throws RemoteException, Exception;
	
	public String addTargetValue(TargetValueDT tarValueDT) throws RemoteException, Exception;
	
	public ArrayList<Object> getFormCode(Collection<Object> questionIdentColl) throws RemoteException, Exception;	
	
	public boolean updateSourceValue(SourceValueDT srcValueDT) throws RemoteException, Exception;
	
	public boolean updateTargetValue(TargetValueDT tarValueDT) throws RemoteException, Exception;
	
   public boolean deleteSourceField(String srcFieldUID) throws RemoteException, Exception;
	
	public boolean deleteTargetField(String tarFieldUID) throws RemoteException, Exception;

	public boolean associateFormCode(ArrayList<Object>  rulesDT) throws RemoteException, Exception;
	
	public ArrayList<Object> findfrmCodes(String strRuleInsUid) throws RemoteException, Exception;
	
	public Collection<Object>  retrieveQuestionRequiredNnd(String formCd) throws RemoteException, Exception;
	
	public Collection<Object>  retrieveAggregateData() throws RemoteException, Exception;
	
	public Collection<Object>  retrieveNBSDocumentMetadata() throws RemoteException, Exception;
	
	public Collection<Object>  getContactQuestions() throws RemoteException, Exception;
	
	public Collection<Object>  getDMBQuestions() throws RemoteException, Exception;
	
	public Collection<Object>  getPrePopMapping() throws RemoteException, Exception;
}