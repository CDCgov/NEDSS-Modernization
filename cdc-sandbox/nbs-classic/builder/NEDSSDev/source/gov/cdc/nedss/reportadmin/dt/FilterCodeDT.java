package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the Filter_code table.
 * @author Ed Jenkins
 */
public class FilterCodeDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(FilterCodeDT.class);

    private Long filter_uid;
    private String code_table;
    private String desc_txt;
    private Timestamp effective_from_time;
    private Timestamp effective_to_time;
    private String filter_code;
    private String filter_code_set_nm;
    private String filter_type;
    private String filter_name;
    private String status_cd;
    private Timestamp status_time;

    /**
     * Constructor.
     */
    public FilterCodeDT()
    {
    }

    public long getFilterUID()
    {
        return (filter_uid == null) ? 0L : filter_uid.longValue();
    }

    public String getFilterUID_s()
    {
        return (filter_uid == null) ? "" : filter_uid.toString();
    }

    public void setFilterUID(long l)
    {
        filter_uid = new Long(l);
    }

    public void setFilterUID(String s)
    {
        if(s == null)
        {
            filter_uid = null;
            return;
        }
        try
        {
            filter_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getCodeTable()
    {
        return (code_table == null) ? "" : code_table;
    }

    public void setCodeTable(String s)
    {
        code_table = s;
    }

    public String getDescTxt()
    {
        return (desc_txt == null) ? "" : desc_txt;
    }

    public void setDescTxt(String s)
    {
        desc_txt = s;
    }

    public String getFilterCode()
    {
        return (filter_code == null) ? "" : filter_code;
    }

    public void setFilterCode(String s)
    {
        filter_code = s;
    }

    public String getFilterCodeSetName()
    {
        return (filter_code_set_nm == null) ? "" : filter_code_set_nm;
    }

    public void setFilterCodeSetName(String s)
    {
        filter_code_set_nm = s;
    }

    public String getFilterType()
    {
        return (filter_type == null) ? "" : filter_type;
    }

    public void setFilterType(String s)
    {
        filter_type = s;
    }

    public String getFilterName()
    {
        return (filter_name == null) ? "" : filter_name;
    }

    public void setFilterName(String s)
    {
        filter_name = s;
    }

    public String toString()
    {
        String s = new String(filter_name);
        return s;
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getFilterUID() == 0L)
        {
            throw new IllegalStateException("Please specify a filter uid.");
        }
        if(this.getCodeTable().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please specify a code table.");
        }
        if(this.getDescTxt().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a description.");
        }
        effective_from_time = null;
        effective_to_time = null;
        if(this.getFilterCode().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a filter code.");
        }
        if(this.getFilterType().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a filter type.");
        }
        if(this.getFilterName().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a filter name.");
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
