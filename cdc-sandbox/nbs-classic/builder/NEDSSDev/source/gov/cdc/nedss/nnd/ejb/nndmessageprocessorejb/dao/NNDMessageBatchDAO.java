package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NNDMessageBatchDAO extends BMPBase
{
    // logging
    static final LogUtils logger                   = new LogUtils(NNDMessageBatchDAO.class.getName());
    static final String   SELECT_APPR_NOTIFICATION = " SELECT " + "notification_uid \"NotificationUid\", cd \"cd\"  "
                                                           + " from notification " + " where ( record_status_cd = '"
                                                           + NEDSSConstants.NOTIFICATION_APPROVED_CODE + "' or "
                                                           + " record_status_cd = '"
                                                           + NEDSSConstants.NOTIFICATION_PEND_DEL + "' ) and "
                                                           + "status_cd = 'A' "  
                                                           + "AND cd not in ('EXP_NOTF_PHDC','SHARE_NOTF_PHDC')"//NBSCentral #13446 //Jira ND-25487	
                                                           + " order by last_chg_time ";
    static final List<String> NND_CD = Arrays.asList( 
            new String[]{ NEDSSConstants.NOTIFICATION_CD, 
              NEDSSConstants.CLASS_CD_NOTF, 
              NEDSSConstants.AGGREGATE_NOTIFICATION_CD} );

    public NNDMessageBatchDAO()
    {
    }
    
    public Map<Object, Object> getApprovedAndPendDelNotificationUid(int maxRows) throws NEDSSSystemException
    {
        Map<Object, Object> notificationMap = new HashMap<Object, Object>();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ArrayList<Object> uidList = new ArrayList<Object>();
        Collection<Object> APPROVED_SHARED_NOTIFICATION_UID_LIST = new ArrayList<Object>();
        Collection<Object> APPROVED_TRANFERRED_NOTIFICATION_UID_LIST = new ArrayList<Object>();
        Collection<Object> APPROVED_NND_NOTIFICATION_UID_LIST = new ArrayList<Object>();

        try
        {
            dbConnection = getConnection(NEDSSConstants.ODS);
            preparedStmt = dbConnection.prepareStatement(SELECT_APPR_NOTIFICATION); 
            resultSet = preparedStmt.executeQuery();
            int i = 1;
            while (resultSet.next())
            {
                Long uid = new Long(resultSet.getLong(1));
                String cd = resultSet.getString(2);
                if ((maxRows <= 0) || (i <= maxRows))
                {
                    if ( NND_CD.contains(cd) )
                    {
                        APPROVED_NND_NOTIFICATION_UID_LIST.add(uid);
                    }
                    if (cd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF))// || cd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)) -- CLASS_CD_EXP_NOTF_PHDC notification are processed by SAS process PHDCMessage.bat
                    {
                        APPROVED_TRANFERRED_NOTIFICATION_UID_LIST.add(uid);
                    }
                    if (cd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF))// || cd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC)) -- CLASS_CD_SHARE_NOTF_PHDC notification are processed by SAS process PHDCMessage.bat
                    {
                        APPROVED_SHARED_NOTIFICATION_UID_LIST.add(uid);
                    }
                    i++;
                }
                else
                {
                    logger.debug("Break at maxRow =" + uidList.size());
                    break;
                }
            }
        }
        catch (SQLException sqx)
        {
            sqx.printStackTrace();
            logger.fatal("SQLException while selecting approved Notification"+sqx.getMessage(), sqx);
            throw new NEDSSSystemException(sqx.getMessage());
        }
        catch (Exception ex)
        {
            logger.fatal("SQLException while selecting approved Notification"+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        notificationMap.put(NEDSSConstants.APPROVED_NND_NOTIFICATION_UID_LIST, APPROVED_NND_NOTIFICATION_UID_LIST);
        notificationMap.put(NEDSSConstants.APPROVED_TRANFERRED_NOTIFICATION_UID_LIST,
                APPROVED_TRANFERRED_NOTIFICATION_UID_LIST);
        notificationMap
                .put(NEDSSConstants.APPROVED_SHARED_NOTIFICATION_UID_LIST, APPROVED_SHARED_NOTIFICATION_UID_LIST);
        logger.debug("returning APPROVED_NND_NOTIFICATION_UID_LISTt, size = "
                + APPROVED_NND_NOTIFICATION_UID_LIST.size());
        logger.debug("returning APPROVED_TRANFERRED_NOTIFICATION_UID_LIST, size = "
                + APPROVED_TRANFERRED_NOTIFICATION_UID_LIST.size());
        logger.debug("returning APPROVED_SHARED_NOTIFICATION_UID_LIST, size = "
                + APPROVED_SHARED_NOTIFICATION_UID_LIST.size());

        return notificationMap;
    }
}