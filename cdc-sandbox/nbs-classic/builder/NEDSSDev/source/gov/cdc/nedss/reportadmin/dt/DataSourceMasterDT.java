package gov.cdc.nedss.reportadmin.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import org.apache.log4j.Logger;

/**
 * Represents the DATA_SOURCE_MASTER table.
 * @author Ed Jenkins
 */
public class DataSourceMasterDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DataSourceMasterDT.class);

    private Long column_uid;
    private String DataSourceName;
    private String column_name;
    private String code_desc;
    private String codeset_nm;

    /**
     * Constructor.
     */
    public DataSourceMasterDT()
    {
    }

    public long getColumnUID()
    {
        return (column_uid == null) ? 0L : column_uid.longValue();
    }

    public String getColumnUID_s()
    {
        return (column_uid == null) ? "" : column_uid.toString();
    }

    public void setColumnUID(long l)
    {
        column_uid = new Long(l);
    }

    public void setColumnUID(String s)
    {
        if(s == null)
        {
            column_uid = null;
            return;
        }
        try
        {
            column_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getDataSourceName()
    {
        return (DataSourceName == null) ? "" : DataSourceName;
    }

    public void setDataSourceName(String s)
    {
        DataSourceName = s;
    }

    public String getColumnName()
    {
        return (column_name == null) ? "" : column_name;
    }

    public void setColumnName(String s)
    {
        column_name = s;
    }

    public String getCodeDesc()
    {
        return (code_desc == null) ? "" : code_desc;
    }

    public void setCodeDesc(String s)
    {
        code_desc = s;
    }

    public String getCodesetName()
    {
        return (codeset_nm == null) ? "" : codeset_nm;
    }

    public void setCodesetName(String s)
    {
        codeset_nm = s;
    }

    public String toString()
    {
        if(codeset_nm == null)
        {
            return "";
        }
        return codeset_nm;
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getColumnUID() == 0L)
        {
            throw new IllegalStateException("Please specify a column uid.");
        }
        if(this.getDataSourceName().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please specify a data source name.");
        }
        if(this.getColumnName().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please specify a column name.");
        }
        if(this.getCodeDesc().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please specify a code description.");
        }
        if(this.getCodesetName().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please specify a codeset name.");
        }
    }

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
