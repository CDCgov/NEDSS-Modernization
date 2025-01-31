//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\deduplication\\ejb\\deduplicationprocessorejb\\dao\\DeDuplicationDAO.java

package gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.sql.*;
import java.math.BigDecimal;

public class DeDuplicationDAO extends DAOBase
{
    private static final LogUtils logger = new LogUtils(PrepareVOUtils.class.getName());
    //Records removed from merge won't be reset
    private static final String RESET_PATIENT_REGISTRY_FOR_DEDUP = "update person set group_nbr = ?, group_time = ?, dedup_match_ind = ? where group_nbr is not null and dedup_match_ind <> 'R' and person.cd = 'PAT'";
    //Updating patient table for those records removed from merge
    private static final String REMOVE_FROM_MERGE_FOR_DEDUP = "update person set group_nbr = NULL, group_time = NULL, dedup_match_ind = 'R' where person_uid = ? and person.cd = 'PAT'";

    private static final String SELECT_GROUP_NUMBER = "select group_nbr groupNbr from person where person.PERSON_UID = person.PERSON_PARENT_UID AND person.GROUP_NBR IS NOT NULL";

	private static final String SELECT_ENTITY_UID = "select entity_uid \"personUid\" FROM " + DataTables.ENTITY_ID_TABLE + " with(nolock) WHERE entity_UID = ? group by entity_uid";

	private static final String SELECT_POSTAL_LOCATOR_UID = "select elp.entity_uid \"personUid\" FROM "
															+ DataTables.ENTITY_LOCATOR_PARTICIPATION_TABLE + " elp  with(nolock), "
															+ DataTables.POSTAL_LOCATOR_TABLE + " pl  with(nolock)"
															+ " WHERE elp.LOCATOR_UID = pl.POSTAL_LOCATOR_UID and entity_UID = ?  group by entity_uid";

    /**
    * @roseuid 3E6F33AB00F5
    */
    public DeDuplicationDAO()
    {
    }

    /**
    * PSEUDO CODE
    * -------------------------------------------------
    * SELECT version_ctrl_nbr, person_uid from Person Where person_parent_uid =
    * <PersonVO.PersonDT.personUid from Superceded>
    *
    * Return Key = person_uid, Value = version_ctrl_nbr
    *
    * @param personUid
    * @return java.util.Hashtable
    * @roseuid 3E6E4A2401F4
    */
    @SuppressWarnings("unchecked")
	public HashMap<Object,Object> getPersonRevisions(Long personUid)
    {
        HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
        PersonDT personDT = new PersonDT();
        logger.debug("personUid ---------"+personUid);
        try{
	        String preparedStatement = "SELECT version_ctrl_nbr VersionCtrlNbr, person_uid PersonUid from Person Where person_parent_uid = " + personUid;
	
	        Collection<Object> collection = (ArrayList<Object> ) preparedStmtMethod(personDT, null, preparedStatement, NEDSSConstants.SELECT);
	
	        Iterator<Object> iterator = collection.iterator();
	
	        while(iterator.hasNext())
	        {
	            hashMap.put(((PersonDT)iterator.next()).getVersionCtrlNbr(),((PersonDT)iterator.next()).getPersonUid());
	        }
	        logger.debug("preparedStatement---------"+preparedStatement);
        }catch(Exception ex){
        	logger.error("Exception occured for personUid ----- "+personUid+" ----- "+ex.getMessage(),ex);
        }
        return hashMap;
    }

    /**
     * Finds all the active groups and returns the count of the same...
     * @return Integer
     * @throws NEDSSDAOSysException
     */
    @SuppressWarnings("unchecked")
	public Integer getActiveGroupNumberCount() throws NEDSSDAOSysException
    {
    	PersonDT personDT = new PersonDT();
    	Collection<Object> collection = null;
    	try{
	        collection = (ArrayList<Object> ) preparedStmtMethod(personDT, null, SELECT_GROUP_NUMBER,  NEDSSConstants.SELECT);        
    	}catch(Exception ex){
    		logger.fatal("Exception = "+ex.getMessage(),ex);
    		throw new NEDSSDAOSysException(ex.toString(), ex);
    	}
    	return new Integer(collection.size());
    }
    /**
     * Returns the current pivots in the DataBase...
     * @return Collection<Object>
     * @throws NEDSSDAOSysException
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> getPivotList() throws NEDSSDAOSysException {
		PersonNameDT personNameDT = new PersonNameDT();

		ArrayList<Object> arrayList = null;
		String sQuery = "{call PERSON_DEDUP_SELECT_PIVOTS_SP()}";
		try {
			arrayList = callStoredProcedureMethodWithResultSet(personNameDT, null, sQuery);
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return arrayList;
	}

    /**
     * Invokes a stored procedure in the database to create patients who are similar wrt to business logic
     * and returns the number of the groups just created.
     * @return Integer
     * @throws NEDSSSystemException
     */
    public BigDecimal createSimilarGroups() throws NEDSSSystemException
    {
        ArrayList<Object>  outArrayList= new ArrayList<Object> ();

        try
        {
            logger.debug("About to call stored procedure");

            outArrayList.add(new Integer(java.sql.Types.INTEGER));

            String sQuery = "{call PERSON_GROUP_SP(?)}";

            ArrayList<Object>  returnArrayList= callStoredProcedureMethod(sQuery, new ArrayList<Object> (), outArrayList);

            if(returnArrayList!= null && returnArrayList.size() > 0)
            {
                return new BigDecimal(returnArrayList.get(0).toString());
            }

            return null;
        }
        catch (Exception se)
        {
        	logger.fatal("Exception = "+se.getMessage(), se);
            throw new NEDSSSystemException("Error: SQLException while obtaining database connection.\n" + se.getMessage());
        }

    }

    /**
     * Given a personUID as input param gets the same patients from Person...
     * @param inPersonUID
     * @return Collection<Object>
     * @throws NEDSSDAOSysException
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> getSamePatientsFromPerson(Long inPersonUID) throws NEDSSDAOSysException
    {
    	try{
	    	PersonDT personDT = new PersonDT();
	
	        ArrayList<Object>  paramterArrayList= new ArrayList<Object> ();
	
	        paramterArrayList.add(inPersonUID);
	        
	        String sQuery = "{call PERSON_DEDUP_SELECT_SAME_FRM_PSN_SP(?)}";
	        
	        ArrayList<Object>  arrayList= callStoredProcedureMethodWithResultSet(personDT, paramterArrayList,
	    			sQuery);
	
	        //ArrayList<Object>  arrayList = (ArrayList<Object> ) preparedStmtMethod(personDT, paramterArrayList, SELECT_SAME_PATIENTS_FROM_PERSON, NEDSSConstants.SELECT);
	
	        buildArrayListOfPersonUIDsGivenPersonDTCollection(arrayList);
	
	        return arrayList;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}
    }

    /**
     * Given a personUID as input param gets the same patients from RaceAndEthnic...
     * @param inPersonUID
     * @return Collection<Object>
     * @throws NEDSSDAOSysException
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> getSamePatientsFromRaceAndEthnic(Long inPersonUID, BinarySearchTree inBinarySearchTree) throws NEDSSDAOSysException
    {
    	try{
	        PersonRaceDT personRaceDT = new PersonRaceDT();
	
	        ArrayList<Object>  paramterArrayList= new ArrayList<Object> ();
	
	        paramterArrayList.add(inPersonUID);
	
	        String sQuery = "{call PERSON_DEDUP_SELECT_SAME_FRM_RACE_SP(?,?)}";
	        
	        Collection<Object> collection = inBinarySearchTree.toCollection();

			String dynamicString = setDynamicCollectionString(collection);

			paramterArrayList.add(dynamicString);
	        
	        ArrayList<Object>  arrayList= callStoredProcedureMethodWithResultSet(personRaceDT, paramterArrayList,
	    			sQuery);
	
	        buildArrayListOfPersonUIDsGivenPersonRaceDTCollection(arrayList);
	
	        return arrayList;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}
    }

    /**
     * Given a personUID as input param gets the same patients from Entity...
     * @param inPersonUID
     * @return Collection<Object>
     * @throws NEDSSDAOSysException
     */
	@SuppressWarnings("unchecked")
	public Collection<Object> getEntitysFromEntity(Long inPersonUID, BinarySearchTree inBinarySearchTree) throws NEDSSDAOSysException {
		PersonDT entityDT = new PersonDT();

		ArrayList<Object> parameterArrayList = new ArrayList<Object>();

		parameterArrayList.add(inPersonUID);

		ArrayList<Object> arrayList;

		try {
			String sQuery = "{call PERSON_DEDUP_SELECT_SAME_FRM_ENTITY_SP(?,?)}";
			
			Collection<Object> collection = inBinarySearchTree.toCollection();

			String dynamicString = setDynamicCollectionString(collection);

			parameterArrayList.add(dynamicString);

			arrayList = callStoredProcedureMethodWithResultSet(entityDT, parameterArrayList, sQuery);

			buildArrayListOfPersonUIDsGivenPersonDTCollection(arrayList);
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
		return arrayList;

	}

    /**
     * Given a personUID and AsOfDate as input params gets the same patients from PersonName...
     * @param inPersonUID
     * @param inTimeStamp
     * @param inBinarySearchTree
     * @return Collection<Object>
     * @throws NEDSSDAOSysException
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> getSamePatientsFromPersonName(Long inPersonUID, Timestamp inTimeStamp,
			BinarySearchTree inBinarySearchTree) throws NEDSSDAOSysException {
		try {
			PersonNameDT personNameDT = new PersonNameDT();

			ArrayList<Object> parameterArrayList = new ArrayList<Object>();

			parameterArrayList.add(inPersonUID);

			ArrayList<Object> arrayList;

			String sQuery = "{call PERSON_DEDUP_SELECT_SAME_FRM_NAME_SP(?,?)}";

			Collection<Object> collection = inBinarySearchTree.toCollection();

			String dynamicString = setDynamicCollectionString(collection);

			parameterArrayList.add(dynamicString);

			arrayList = callStoredProcedureMethodWithResultSet(personNameDT, parameterArrayList, sQuery);

			return buildMostRecentAsOfDateEntrysGivenAPersonNameOrELPDTCollection(arrayList);
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
	}

    /**
     * Given a personUID as input param gets the same patients from EntityLocatorParticipation...
     * @param inPersonUID
     * @param inBinarySearchTree
     * @return Collection<Object>
     * @throws NEDSSDAOSysException
     */
	public Collection<Object> getSamePatientsFromPostalLocator(Long inPersonUID, BinarySearchTree inBinarySearchTree)
			throws NEDSSDAOSysException {
		try {
			Collection<Object> collection = inBinarySearchTree.toCollection();

			collection = buildELPDTCollection(inPersonUID, collection);

			return buildMostRecentAsOfDateEntrysGivenAPersonNameOrELPDTCollection(collection);
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
	}

    /**
     * Given a ArrayList<Object>  builds a PersonDT Collection<Object> and puts it back in the same object...
     * @param inArrayList
     */
    private void buildArrayListOfPersonUIDsGivenPersonDTCollection(ArrayList<Object>  inArrayList)
    {
        PersonDT personDT=null;
        try{
	        int size = inArrayList.size();
	
	        if(size > 0)
	        {
	            for(int i = 0; i < size; i++)
	            {
	            	try{
		                personDT = (PersonDT)inArrayList.get(i);
		                inArrayList.set(i, personDT.getPersonUid());
	            	}catch(Exception e){
	            		logger.error("Exception while iterating through personUid ----- "+personDT.getPersonUid());
	            		logger.fatal("Exception = "+e.getMessage(), e);
	            		throw new NEDSSSystemException(e.toString(), e);
	            	}
	            }
	        }
        }catch(Exception ex){
        	logger.fatal("Exception = "+ex.getMessage(),ex);
        	throw new NEDSSSystemException(ex.toString(), ex);
        }
    }

    /**
     * Given a ArrayList<Object>  builds a PersonRaceDT Collection<Object> and puts it back in the same object...
     * @param inArrayList
     */
    private void buildArrayListOfPersonUIDsGivenPersonRaceDTCollection(ArrayList<Object>  inArrayList)
    {
        PersonRaceDT personRaceDT=null;
        try{
	        int size = inArrayList.size();
	
	        if(size > 0)
	        {
	
	            for(int i = 0; i < size; i++)
	            {
	            	try{
		                personRaceDT = (PersonRaceDT)inArrayList.get(i);
		                inArrayList.set(i, personRaceDT.getPersonUid());
	            	}catch(Exception e){
	            		logger.error("Exception while iterating through personUid ----- "+personRaceDT.getPersonUid());
	            		logger.fatal("Exception = "+e.getMessage(), e);
	            		throw new NEDSSSystemException(e.toString(), e);
	            	}
	            }
	        }
        }catch(Exception ex){
        	logger.fatal("Exception = "+ex.getMessage(),ex);
        	throw new NEDSSSystemException(ex.toString(), ex);
        }
    }
    /**
     * Given the Collection<Object> (from which to extract the content for
     * the Collection<Object> String) the Dynamic Collection<Object> String is built
     * @param inCollection
     */
    private String setDynamicCollectionString(Collection<Object> inCollection)
    {
    	try{
	        StringBuffer stringBuffer = new StringBuffer();
	        
	        Iterator<Object> iterator = inCollection.iterator();
	    	
	        while(iterator.hasNext())
	        {
	        	stringBuffer.append(String.valueOf(((Long)iterator.next()).longValue())).append(",");
	        }
	        String collectionString = stringBuffer.toString();
	        logger.debug("DYNAMIC_COLLECTION_STRING ------ "+collectionString);
	        return collectionString.substring(0, (collectionString.length()-1));

    	}catch(Exception ex){
    		logger.fatal("Exception = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}

    }

    /**
     * Given a Collection<Object> builds a collection object with Most Recent AsOfDates...
     * @param inCollection
     * @return Collection<Object>
     */
    private Collection<Object> buildMostRecentAsOfDateEntrysGivenAPersonNameOrELPDTCollection(Collection<Object> inCollection)
    {
        HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
        Object dtObject = null;
        Long personUID = null;
        Timestamp timeStamp = null;
        try{
	        Iterator<Object> iterator = inCollection.iterator();
	        Object object;
while(iterator.hasNext())
	        {
	            dtObject = iterator.next();
	
	            if(dtObject instanceof PersonNameDT)
	            {
	                timeStamp = ((PersonNameDT)dtObject).getAsOfDate();
	                personUID = ((PersonNameDT)dtObject).getUid();
	            }
	            else
	            {
	                timeStamp = ((EntityLocatorParticipationDT)dtObject).getAsOfDate();
	                personUID = ((EntityLocatorParticipationDT)dtObject).getEntityUid();
	            }
	
	            //Use Synchronize because of HashMap<Object,Object>..
	            synchronized(this)
	            {
	                object = hashMap.get(personUID);
	
	                if(object == null)
	                {   //The key never was in the HashMap<Object,Object>, put it in...
	                    hashMap.put(personUID, timeStamp);
	                }
	                else
	                {
	                    //The key was there in the HashMap<Object,Object> on a earlier insert, now we need to compare the time
	                    // stamp on the same to see if it is older / newer than the one we have, if newer no action
	                    // otherwise update the same's value with our value meaning our timestamp.
	                    if(timeStamp.after((Timestamp)object))
	                    {
	                        hashMap.put(personUID, timeStamp);
	                    }
	                }
	            }
	        }
        }catch(Exception ex){
        	logger.fatal("Exception = "+ex.getMessage(),ex);
        	throw new NEDSSSystemException(ex.toString(), ex);
        }
        return hashMap.keySet();
    }
    
    
    /**
     * Given a personUID and AsOfDate as input params gets the same patients from PersonName...
     * @param inPersonUID
     * @param inTimeStamp
     * @param inBinarySearchTree
     * @return Collection<Object>
     * @throws NEDSSDAOSysException
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> buildELPDTCollection(Long inPersonUID, Collection<Object> inCollection)
			throws NEDSSDAOSysException {
		try {
			PersonNameDT pesonNameDT = new PersonNameDT();

			ArrayList<Object> parameterArrayList = new ArrayList<Object>();

			parameterArrayList.add(inPersonUID);
			
			String dynamicString = setDynamicCollectionString(inCollection);

			parameterArrayList.add(dynamicString);

			ArrayList<Object> arrayList;

			String sQuery = "{call PERSON_DEDUP_SELECT_SAME_FRM_ELP_SP(?,?)}";

			// Collection<Object> collection = inBinarySearchTree.toCollection();

			arrayList = callStoredProcedureMethodWithResultSet(pesonNameDT, parameterArrayList, sQuery);

			Collection<Object> elpDTCollection = new ArrayList<Object>();
			if (arrayList != null) {
				Iterator<Object> ite = arrayList.iterator();
				while (ite.hasNext()) {
					PersonNameDT pNameDT = (PersonNameDT) ite.next();
					EntityLocatorParticipationDT elpDT = new EntityLocatorParticipationDT();

					elpDT.setEntityUid(pNameDT.getPersonUid());
					elpDT.setAsOfDate(pNameDT.getAsOfDate());
					elpDTCollection.add(elpDT);
				}
			}

			return elpDTCollection;
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}
	}

    /**
     * The method resets the Patient Registry
     */
    public void resetPatientRegistryForDedup() {
      Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;

        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for resetPatientRegistryForDedup ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        
        try {
          int i = 1;
          preparedStmt = dbConnection.prepareStatement(RESET_PATIENT_REGISTRY_FOR_DEDUP);
          preparedStmt.setNull(i++, Types.NUMERIC);
          preparedStmt.setNull(i++, Types.DATE);
          preparedStmt.setNull(i++, Types.CHAR);
          preparedStmt.executeUpdate();
        }catch (SQLException e)
        {
        	logger.fatal("SQLException  = "+e.getMessage(), e);
            throw new NEDSSDAOSysException(e.toString());
        }
        finally
        {
              closeResultSet(resultSet);
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
        }

    }
    /**
     * removePatientFromMergeForDedup: this method set the patient as non available from merge, once Remove from Merge option has been used
     */
    public void removePatientFromMergeForDedup(String personUid) {
      Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;

        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for removePatientFromMergeForDedup ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        
        try {
          int i = 1;
          preparedStmt = dbConnection.prepareStatement(REMOVE_FROM_MERGE_FOR_DEDUP);
          preparedStmt.setString(1,personUid);
          preparedStmt.executeUpdate();
        }catch (SQLException e)
        {
        	logger.fatal("SQLException  = "+e.getMessage(), e);
            throw new NEDSSDAOSysException(e.toString());
        }
        finally
        {
              closeResultSet(resultSet);
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
        }

    }
    

    @SuppressWarnings("unchecked")
	public boolean hasEntityIds(Long personUid){

    	PersonDT entityDT = new PersonDT();

    	try{
	        ArrayList<Object>  parameterArrayList= new ArrayList<Object> ();
	
	        parameterArrayList.add(personUid);
	
	        ArrayList<Object>  arrayList = (ArrayList<Object> ) preparedStmtMethod(entityDT, parameterArrayList, SELECT_ENTITY_UID, NEDSSConstants.SELECT);
	
	        if(arrayList != null && arrayList.size()==1){
	        	return true;
	        }
    	}catch(Exception ex){
    		logger.fatal("Exception = "+ex.getMessage(),ex);
    		throw new NEDSSSystemException(ex.toString(), ex);
    	}
    	
        return false;
	}

    @SuppressWarnings("unchecked")
	public boolean hasPostalLocators(Long personUid){

    	PersonDT entityDT = new PersonDT();
    	try{
	        ArrayList<Object>  parameterArrayList= new ArrayList<Object> ();
	
	        parameterArrayList.add(personUid);
	
	        ArrayList<Object>  arrayList = (ArrayList<Object> ) preparedStmtMethod(entityDT, parameterArrayList, SELECT_POSTAL_LOCATOR_UID, NEDSSConstants.SELECT);
	
	        if(arrayList != null && arrayList.size()==1){
	        	return true;
	        }
    	}catch(Exception ex){
    		logger.fatal("Exception = "+ex.getMessage(),ex);
    		throw new NEDSSSystemException(ex.toString(), ex);
    	}
    	
        return false;
	}
}
