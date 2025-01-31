
package gov.cdc.nedss.systemservice.util;

import java.util.*;
import java.sql.*;

public class PrepareVOUtilsHelper
{
    public PrepareVOUtilsHelper()
    {
    }
    private String localId = null;
    private Long addUserId = null;
    private Timestamp addUserTime = null;
    private String recordStatusState = null;
    private String objectStatusState = null;

    /**
     *setLocalId
	*@param localId -- Local ID to be set
	*/
    public void setLocalId(String localId)
    {
        this.localId = localId;
    }
    /**
     *getLocalIds
	*@return localId
     */
    public String getLocalIds()
    {
        return localId;
    }
    /**
     *setAddUserId
	*param addUserId -- The user ID
	*/
    public void setAddUserId(Long addUserId)
    {
        this.addUserId = addUserId;
    }
    /**
     *getAddUserId
	*@return addUserId -- The user ID
     */
    public Long getAddUserId()
    {
        return addUserId;
    }
    /**
     *setAddUserTime
	*@param addUserTime -- TimeStamp
     */
    public void setAddUserTime(Timestamp addUserTime)
    {
        this.addUserTime = addUserTime;
    }
    /**
     *getAddUserTime
	*@return addUserTime
     */
    public Timestamp getAddUserTime()
    {
        return addUserTime;
    }
    /**
     *setRecordStatusState
	*@param recordStatusState
	*/
    public void setRecordStatusState(String recordStatusState)
    {
        this.recordStatusState = recordStatusState;
    }
    /**
     *getRecordStatusState
	*@return recordStatusState
     */
    public String getRecordStatusState()
    {
        return recordStatusState;
    }
    /**
     *setObjectStatusState
	*@param objectStatusState
     */
    public void setObjectStatusState(String objectStatusState)
    {
        this.objectStatusState = objectStatusState;
    }
    /**
     *getObjectStatusState
	*return objectStatusState
     */
    public String getObjectStatusState()
    {
        return objectStatusState;
    }

}