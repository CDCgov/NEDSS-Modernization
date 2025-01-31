package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;


/**
 * Represents the Data_Source table.
 * @author Ed Jenkins
 */
public class DataSourceDT extends AbstractVO
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8527055250937737497L;

	/**
     * Logger.
     */
//    private static final Logger logger = Logger.getLogger(DataSourceDT.class);

    private Long data_source_uid;
    private Integer column_max_len;
    private String condition_security;
    private String data_source_name;
    private String data_source_title;
    private String data_source_type_code;
    private String desc_txt;
    private Timestamp effective_from_time;
    private Timestamp effective_to_time;
    private String jurisdiction_security;
    private String org_access_permis;
    private String prog_area_access_permis;
    private String status_cd;
    private Timestamp status_time;
    private String reporting_facility_security;

    

	/**
     * Constructor.
     */
    public DataSourceDT()
    {
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
            //logger.error(ex, ex);
        }
    }

    public String getDataSourceName()
    {
        return (data_source_name == null) ? "" : data_source_name;
    }

    public void setDataSourceName(String s)
    {
        data_source_name = s;
    }

    public String getDataSourceTitle()
    {
        return (data_source_title == null) ? "" : data_source_title;
    }

    public void setDataSourceTitle(String s)
    {
        data_source_title = s;
    }

    public String getDescTxt()
    {
        return (desc_txt == null) ? "" : desc_txt;
    }

    public void setDescTxt(String s)
    {
        desc_txt = s;
    }

    public String getJurisdictionSecurity()
    {
        return (jurisdiction_security == null) ? "N" : jurisdiction_security;
    }

    public void setJurisdictionSecurity(String s)
    {
        jurisdiction_security = s;
    }

    public String toString()
    {
        return this.getDataSourceName();
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getDataSourceUID() == 0L)
        {
            throw new IllegalStateException("Please specify a data source uid.");
        }
        column_max_len = null;
        condition_security = "N";
        if(this.getDataSourceName().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a name.");
        }
        String data_source_name_v = this.getDataSourceName().toLowerCase();
		if (data_source_name_v != null
				&& (data_source_name_v.startsWith("nbs_ods.")
						|| data_source_name_v.startsWith("nbs_rdb.")
						|| data_source_name_v.startsWith("nbs_msg.") || data_source_name_v
							.startsWith("nbs_srt."))) {
			//do nothing, continue.
		}
		else
			throw new IllegalStateException(
					"Please enter a name that starts with 'nbs_ods.', 'nbs_rdb.', 'nbs_msg.', or 'nbs_srt.'");

        String[] arrDSN = data_source_name_v.split("\\x2E");
        if(arrDSN.length != 2)
        {
            throw new IllegalStateException("Please enter a valid name.");
        }
        if(this.getDataSourceTitle().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a title.");
        }
        data_source_type_code = null;
        if(this.getDescTxt().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a description.");
        }
        effective_from_time = null;
        effective_to_time = null;
        if(!this.getJurisdictionSecurity().equals("Y"))
        {
            jurisdiction_security = "N";
        }
        org_access_permis = null;
        prog_area_access_permis = null;
        status_cd = "A";
    }

	public Timestamp getStatus_time() {
		return status_time;
	}

	public void setStatus_time(Timestamp status_time) {
		this.status_time = status_time;
	}
	
	public String getReporting_facility_security() {
   	 return (reporting_facility_security == null) ? "N" : reporting_facility_security;
	}

	public void setReporting_facility_security(String reporting_facility_security) {
		this.reporting_facility_security = reporting_facility_security;
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
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
