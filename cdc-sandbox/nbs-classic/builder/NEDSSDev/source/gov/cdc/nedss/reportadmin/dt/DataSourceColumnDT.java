package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Represents the Data_source_column table.
 * @author Ed Jenkins
 */
public class DataSourceColumnDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DataSourceColumnDT.class);

    private Long column_uid;
    private Integer column_max_len;
    private String column_name;
    private String column_title;
    private String column_type_code;
    private Long data_source_uid;
    private String desc_txt;
    private String displayable;
    private Timestamp effective_from_time;
    private Timestamp effective_to_time;
    private String filterable;
    private String status_cd;
    private Timestamp status_time;
    private String codesetNm;
    private String dataSourceCodesetUid;
    private String codeDescCd;

    /**
     * Constructor.
     */
    public DataSourceColumnDT()
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

    public int getColumnMaxLen()
    {
        return (column_max_len == null) ? 0 : column_max_len.intValue();
    }

    public String getColumnMaxLen_s()
    {
        return (column_max_len == null) ? "" : column_max_len.toString();
    }

    public void setColumnMaxLen(int i)
    {
        column_max_len = new Integer(i);
    }

    public void setColumnMaxLen(String s)
    {
        if(s == null)
        {
            column_max_len = null;
            return;
        }
        try
        {
            column_max_len = Integer.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getColumnName()
    {
        return (column_name == null) ? "" : column_name;
    }

    public void setColumnName(String s)
    {
        column_name = s;
    }

    public String getColumnTitle()
    {
        return (column_title == null) ? "" : column_title;
    }

    public void setColumnTitle(String s)
    {
        column_title = s;
    }

    public String getColumnTypeCode()
    {
        return (column_type_code == null) ? "STRING" : column_type_code;
    }

    public void setColumnTypeCode(String s)
    {
        column_type_code = s;
    }

    public long getDataSourceUID()
    {
        return (data_source_uid == null) ? 0L : data_source_uid.longValue();
    }

    public String getDataSourceUID_s()
    {
        return (data_source_uid == null) ? "" : data_source_uid.toString();
    }

    public void setDataSourceUID(long l)
    {
        data_source_uid = new Long(l);
    }

    public void setDataSourceUID(String s)
    {
        if(s == null)
        {
            data_source_uid = null;
            return;
        }
        try
        {
            data_source_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getDescTxt()
    {
        return (desc_txt == null) ? "" : desc_txt;
    }

    public void setDescTxt(String s)
    {
        desc_txt = s;
    }

    public String getDisplayable()
    {
        return (displayable == null) ? "N" : displayable;
    }

    public void setDisplayable(String s)
    {
        displayable = s;
    }

    public String getFilterable()
    {
        return (filterable == null) ? "N" : filterable;
    }

    public void setFilterable(String s)
    {
        filterable = s;
    }

    public String toString()
    {
        String s = new String(column_name);
        return s;
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
        if(this.getColumnTypeCode().equalsIgnoreCase("STRING"))
        {
            if(this.getColumnMaxLen_s().equalsIgnoreCase(""))
            {
                throw new IllegalStateException("Please enter a maximum length.");
            }
        }
        else
        {
            column_max_len = null;
        }
        if(this.getColumnName().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a name.");
        }
        if(this.getColumnTitle().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a title.");
        }
        if(!this.getColumnTypeCode().equals("STRING"))
        {
            if(!this.getColumnTypeCode().equals("INTEGER"))
            {
                if(!this.getColumnTypeCode().equals("DATETIME"))
                {
                    throw new IllegalStateException("Please select a type.");
                }
            }
        }
        if(this.getDataSourceUID() == 0L)
        {
            throw new IllegalStateException("Please enter a data source uid.");
        }
        if(this.getDescTxt().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a description.");
        }
        if(!this.getDisplayable().equals("Y"))
        {
            displayable = "N";
        }
        effective_from_time = null;
        effective_to_time = null;
        if(!this.getFilterable().equals("Y"))
        {
            filterable = "N";
        }
        status_cd = "A";

    }

	public String getCodesetNm() {
		return codesetNm;
	}

	public void setCodesetNm(String codesetNm) {
		/*
		if(codesetNm != null && codesetNm.trim().length() > 0) {
		
			if(codesetNm.indexOf(".") != -1) {
				this.codesetNm = codesetNm.substring(codesetNm.indexOf(".")+1 );
			} else
				this.codesetNm = codesetNm;				
		}
		*/
		this.codesetNm = codesetNm;
	}

	public String getCodeDescCd() {
		return codeDescCd;
	}

	public void setCodeDescCd(String codeDescCd) {
		this.codeDescCd = codeDescCd;
	}

	public String getDataSourceCodesetUid() {
		return dataSourceCodesetUid;
	}

	public void setDataSourceCodesetUid(String dataSourceCodesetUid) {
		this.dataSourceCodesetUid = dataSourceCodesetUid;
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
