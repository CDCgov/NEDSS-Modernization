package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the Data_Source_Codeset table.
 * @author Ed Jenkins
 */
public class DataSourceCodesetDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DataSourceCodesetDT.class);

    private Long data_source_codeset_uid;
    private Long column_uid;
    private String code_desc_cd;
    private String codeset_nm;
    private String status_cd;
    private Timestamp status_time;

    /**
     * Constructor.
     */
    public DataSourceCodesetDT()
    {
    }

    public long getDataSourceCodesetUID()
    {
        return (data_source_codeset_uid == null) ? 0L : data_source_codeset_uid.longValue();
    }

    public String getDataSourceCodesetUID_s()
    {
        return (data_source_codeset_uid == null) ? "" : data_source_codeset_uid.toString();
    }

    public void setDataSourceCodesetUID(long l)
    {
        data_source_codeset_uid = new Long(l);
    }

    public void setDataSourceCodesetUID(String s)
    {
        if(s == null)
        {
            data_source_codeset_uid = null;
            return;
        }
        try
        {
            data_source_codeset_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
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

    public String getCodeDescCD()
    {
        return (code_desc_cd == null) ? "" : code_desc_cd;
    }

    public void setCodeDescCD(String s)
    {
        code_desc_cd = s;
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
        if(this.getDataSourceCodesetUID() == 0L)
        {
            throw new IllegalStateException("Please specify a data source codeset uid.");
        }
        if(this.getColumnUID() == 0L)
        {
            throw new IllegalStateException("Please specify a column uid.");
        }
        if(this.getCodeDescCD().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please select a type.");
        }
        if( (!code_desc_cd.equalsIgnoreCase("C")) && (!code_desc_cd.equalsIgnoreCase("D")) )
        {
            throw new IllegalStateException("Please select a type.");
        }
        if(this.getCodesetName().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please specify a codeset name.");
        }
        status_cd = "A";
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
