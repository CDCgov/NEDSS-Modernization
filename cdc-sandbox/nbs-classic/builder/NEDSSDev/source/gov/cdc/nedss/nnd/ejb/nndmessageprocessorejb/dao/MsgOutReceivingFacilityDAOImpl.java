package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.nnd.helper.MsgOutReceivingFacilityDT;
import gov.cdc.nedss.nnd.helper.MsgOutUidGeneratorHelper;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class MsgOutReceivingFacilityDAOImpl
    extends BMPBase
{

    static final LogUtils logger = new LogUtils(MsgOutReceivingFacilityDAOImpl.class.getName());
    private static final String INSERT =
        "INSERT INTO MsgOut_Receiving_facility ( " +
         "receiving_facility_entity_uid, " + "nm_use_cd, " +
         "assigning_authority_cd, " + "assigning_authority_desc_txt, " +
         "receiving_facility_nm, " + "record_status_cd, " +
         "record_status_time, " + "root_extension_txt, " + "status_cd, " +
         "status_time, " + "type_cd " +
         " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

    public MsgOutReceivingFacilityDAOImpl()
    {
    }

    public long create(Object obj)
                throws NEDSSDAOSysException
    {
    	try{
	        MsgOutReceivingFacilityDT dt = (MsgOutReceivingFacilityDT)obj;
	
	        if (dt == null)
	            throw new NEDSSDAOSysException("Error: try to create null MsgOutReceivingFacilityDT object.");
	
	        long uid = insertMsgOutReceivingFacility(
	                           (MsgOutReceivingFacilityDT)obj);
	
	        return uid;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}
    }

    private long insertMsgOutReceivingFacility(MsgOutReceivingFacilityDT dt)
                                        throws NEDSSDAOSysException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        long uid = 0;
        MsgOutUidGeneratorHelper uidGen = null;

        try
        {
            dbConnection = getConnection(NEDSSConstants.MSGOUT);

            /**
            * get Uid from MsgOutUidGeneratorHelper and update table
            */
            uidGen = new MsgOutUidGeneratorHelper();
            uid = uidGen.getMsgOutIDLong(NNDConstantUtil.MSGOUT_MESSAGE).longValue();
            preparedStmt = dbConnection.prepareStatement(INSERT);

            int i = 1;
            preparedStmt.setLong(i++, uid); // set receiving_facility_entity_uid
            preparedStmt.setString(i++, dt.getNmUseCd());
            preparedStmt.setString(i++, dt.getAssigningAuthorityCd());
            preparedStmt.setString(i++, dt.getAssigningAuthorityDescTxt());
            preparedStmt.setString(i++, dt.getReceivingFacilityNm());
            preparedStmt.setString(i++, dt.getRecordStatusCd());

            if (dt.getRecordStatusTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());

            preparedStmt.setString(i++, dt.getRootExtensionTxt());
            preparedStmt.setString(i++, dt.getStatusCd());

            if (dt.getStatusTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getStatusTime());

            preparedStmt.setString(i++, dt.getTypeCd());
            preparedStmt.executeUpdate();

            return uid;
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException while inserting"+sqlex.getMessage(), sqlex);
            throw new NEDSSDAOSysException("Insert table failed");
        }
        catch (Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException("Insert into table Failed");
        }
        finally
        {

            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    } //end of insert
}