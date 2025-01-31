//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\entity\\person\\ejb\\dao\\PersonMergeDAOImpl.java

package gov.cdc.nedss.entity.person.ejb.dao;
import gov.cdc.nedss.entity.person.dt.PersonMergeDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PersonMergeDAOImpl extends DAOBase
{
	static final LogUtils logger = new LogUtils(PersonMergeDAOImpl.class.getName());
  private final String INSERT_MERGE_DT = "INSERT INTO PERSON_MERGE ("+
   "SUPERCED_PERSON_UID, SUPERCEDED_VERSION_CTRL_NBR, SUPERCEDED_PARENT_UID, "+
   "SURVIVING_PERSON_UID, SURVIVING_VERSION_CTRL_NBR, SURVIVING_PARENT_UID, "+
   "RECORD_STATUS_CD, RECORD_STATUS_TIME, MERGE_USER_ID, "+
   "MERGE_TIME) "+
   "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

   /**
    * @roseuid 3EA803BF036B
    */
   public PersonMergeDAOImpl()
   {

   }

   /**
    * @param obj
    * @throws NEDSSDAOSysException
    * @throws gov.cdc.nedss.exception.NEDSSSystemException
    * @roseuid 3EA59E150167
    */
   public void create(Collection<Object> coll) throws NEDSSSystemException
   {
	   try{
	     Iterator<Object>  it = coll.iterator();
	     while(it.hasNext()) {
	       PersonMergeDT dt = (PersonMergeDT)it.next();
	       ArrayList<Object> list = new ArrayList<Object> ();
	       list.add(dt.getSupercedPersonUid());
	       list.add(dt.getSupercededVersionCtrlNbr());
	       list.add(dt.getSupercededParentUid());
	       list.add(dt.getSurvivingPersonUid());
	       list.add(dt.getSurvivingVersionCtrlNbr());
	       list.add(dt.getSurvivingParentUid());
	       list.add(dt.getRecordStatusCd());
	       list.add(dt.getRecordStatusTime());
	       list.add(dt.getMergeUserId());
	       list.add(dt.getMergeTime());
	
	       preparedStmtMethod(null, list, INSERT_MERGE_DT,NEDSSConstants.UPDATE);
	
	     }//end of while
	   }catch(Exception ex){
		   logger.fatal("Exception  = "+ex.getMessage(), ex);
		   throw new NEDSSSystemException(ex.toString());
	   }
   }
}
