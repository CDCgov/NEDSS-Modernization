
package gov.cdc.nedss.systemservice.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.PrepareVOUtilsHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PrepareVOUtilsDAOImpl extends DAOBase
{
   public PrepareVOUtilsDAOImpl()
    {
    }

    private static final LogUtils logger = new LogUtils(PrepareVOUtilsDAOImpl.class.getName());


    /**
     * This method will run a stored procedure(GetNextState_sp) on database and return the
	* RecordStatusState and ObjectStatusState for the Object depending on the passed parameters
	* @param businessTriggerCd -- it indicated the Bussiness Trigger,it maybe Delete,Edit or Create
	* @param moduleCd -- it tells the Module Code eg. Person,Organization,PublicHealthCase etc
	* @param uid -- represents the UID for Module
	* @param tableName -- Table name corrosponding to the Module
	* @return PrepareVOUtilsHelper -- helper class for prepareVOUtil
     */
    public PrepareVOUtilsHelper callableStatement(String businessTriggerCd, String moduleCd, Long uid, String tableName)
    {
        logger.debug("\n\n this is from callableStatement method \n\n" );
		PrepareVOUtilsHelper prepareVOUtilsHelper = new PrepareVOUtilsHelper();
		String input = "callableStatement : " + businessTriggerCd + ", " + moduleCd + ", " + uid + ", " + tableName;
		logger.debug(input);

        ArrayList<Object> inArrayList= new ArrayList<Object> ();
        ArrayList<Object>  outArrayList= new ArrayList<Object> ();
        ArrayList<Object> arrayList = new ArrayList<Object> ();
		try
		{
		    logger.debug("About to call stored procedure");
		    String sQuery  = "{call GetNextState_sp(?,?,?,?,?,?,?,?,?)}";
		    logger.debug("sQuery = " + sQuery);
		    logger.debug("after prepareCall");
	
	            inArrayList.add(businessTriggerCd);
	            inArrayList.add(moduleCd);
	            inArrayList.add(uid);
	            inArrayList.add(tableName);
	            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
	            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
	            outArrayList.add(new Integer(java.sql.Types.TIMESTAMP));
	            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
	            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
	
	          arrayList = (ArrayList<Object> )callStoredProcedureMethod(sQuery,inArrayList,outArrayList);
	
	         // if(arrayList == null)
	         //       logger.debug("THE ARRAY LIST IS NULL ***************************" );
	
	        int count = 0;
	
	          if(arrayList.get(count)!=null)
	                prepareVOUtilsHelper.setLocalId(arrayList.get(count++).toString());
	          else
	          {
	                prepareVOUtilsHelper.setLocalId(null);
	                count++;
	           }
	
	        if(arrayList.get(count)!=null)
	             prepareVOUtilsHelper.setAddUserId(new Long(arrayList.get(count++).toString().trim()));
	        else
	        {
	            prepareVOUtilsHelper.setAddUserId(null);
	            count++;
	        }
	
	         prepareVOUtilsHelper.setAddUserTime((Timestamp)arrayList.get(count++));
	         Object recStatusState = arrayList.get(count++);
	         Object objStatusState = arrayList.get(count++);
	         prepareVOUtilsHelper.setRecordStatusState(recStatusState == null ? null : recStatusState.toString());
	         prepareVOUtilsHelper.setObjectStatusState(objStatusState == null ? null : objStatusState.toString());
	
		     return prepareVOUtilsHelper;
		}
		catch(Exception e)
		{
			logger.fatal("Exception  = "+e.getMessage(), e);
		    throw new NEDSSSystemException("exception = " + e.toString(), e);
		}
    }

     /**
    * This method is used for unit testing
    *
    * @param String[]
    * @param securityObj
    * commented out main method by Prab for fortify
    */

    /* 
    public static void main (String [] args)
    {
       Connection conn = null;
       ResultSet rs = null;
       CallableStatement sProc = null;
       String businessTriggerCd ="INT_VAC_EDIT" ;
       String moduleCd= "BASE";
       Long uid = new Long(605336758);
       String tableName = "Intervention";
	    try{
	        Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
	        conn = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", NEDSSConstants.UODS, NEDSSConstants.PODS);
	
	      	    logger.debug("About to call stored procedure");
		    String sQuery  = "{call GetNextState_sp(?,?,?,?,?,?,?,?,?)}";
	
		    sProc = conn.prepareCall(sQuery);
		    logger.debug("after prepareCall");
	
		    sProc.setString(1,businessTriggerCd);  //1
		    sProc.setString(2,moduleCd);  //2
		    sProc.setString(3,uid.toString());  //3
		    sProc.setString(4,tableName);  //4
	
		    sProc.registerOutParameter(5, java.sql.Types.VARCHAR);//@localId
		    sProc.registerOutParameter(6, java.sql.Types.VARCHAR);//@addUserId
		    sProc.registerOutParameter(7, java.sql.Types.TIMESTAMP);//@addUserTime
		    sProc.registerOutParameter(8, java.sql.Types.VARCHAR);//@recordStatusState
		    sProc.registerOutParameter(9, java.sql.Types.VARCHAR);//@objectStatusState
	
		    logger.debug("before execute");
		    sProc.execute();
		    logger.debug("after execute");
	
	    }
		catch(Exception se)
		{
	            logger.error("Exception ="+se.getMessage(), se);
		}

    }
   */    
}