package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the Report table.
 * @author Ed Jenkins
 */
public class ReportDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportDT.class);

    private Long report_uid;
    private Long data_source_uid;
    private String data_source_uid2;
    
    private String add_reason_cd;
    private Timestamp add_time;
    private Long add_user_uid;
    private String desc_txt;
    private Timestamp effective_from_time;
    private Timestamp effective_to_time;
    private String filter_mode;
    private String is_modifiable_ind;
    private String location;
    private Long owner_uid;
    private String org_access_permis;
    private String prog_area_access_permis;
    private String report_title;
    private String report_type_code;
    private String shared;
    private String shared2;


	private String status_cd;
    private Timestamp status_time;
    private String category;
    private Long report_section_cd;
    private String report_section_desc_txt;

    public long getReportUID()
    {
        return (report_uid == null) ? 0L : report_uid.longValue();
    }

    public String getReportUID_s()
    {
        return (report_uid == null) ? "" : report_uid.toString();
    }

    public void setReportUID(long l)
    {
        report_uid = new Long(l);
    }

    public void setReportUID(String s)
    {
        if(s == null)
        {
            report_uid = null;
            return;
        }
        try
        {
            report_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public long getDataSourceUID()
    {
        return (data_source_uid == null) ? 0L : data_source_uid.longValue();
    }

    public String getDataSourceUID_s()
    {
        return (data_source_uid == null) ? "" : data_source_uid.toString();
    }
    public String getDataSourceUID_s2()
    {
        return (data_source_uid2 == null) ? "" : data_source_uid2.toString();
    }
    public void setDataSourceUID_s2(String dataSource)
    {
    	data_source_uid2=dataSource;
    	
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

    public String getLocation()
    {
        return (location == null) ? "" : location;
    }

    public void setLocation(String s)
    {
        /*
            The database has inconsistient case for filenames for standard reports.
            The XSL does a case-sensitive comparison between SRT values and field values.
            Therefore, to make the drop-down listboxes work correctly, we must
            convert the filenames to upper-case.
            Also in NewReport.java and ViewReport.java
        */
        if(s == null)
        {
            location = null;
            return;
        }
        location = s.toUpperCase();
    }

    public long getOwnerUID()
    {
        return (owner_uid == null) ? 0L : owner_uid.longValue();
    }

    public String getOwnerUID_s()
    {
        return (owner_uid == null) ? "0" : owner_uid.toString();
    }

    public void setOwnerUID(long l)
    {
        owner_uid = new Long(l);
    }

    public void setOwnerUID(String s)
    {
        if(s == null)
        {
            owner_uid = null;
            return;
        }
        if(s.equalsIgnoreCase(""))
        {
            owner_uid = null;
            return;
        }
        try
        {
            owner_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getReportTitle()
    {
        return (report_title == null) ? "" : report_title;
    }

    public void setReportTitle(String s)
    {
        report_title = s;
    }

    public String getReportTypeCode()
    {
        return (report_type_code == null) ? "" : report_type_code;
    }

    public void setReportTypeCode(String s)
    {
        report_type_code = s;
    }

    public String getShared()
    {
        return (shared == null) ? "" : shared;
    }

    public void setShared(String s)
    {
        shared = s;
    }

    public String toString()
    {
        String s = new String(report_title);
        return s;
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getReportUID() == 0L)
        {
            throw new IllegalStateException("Please specify a report uid.");
        }
        if(this.getReportTitle().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a name.");
        }
        if(this.getDescTxt().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please enter a description.");
        }
        if(this.getDataSourceUID() == 0L)
        {
            throw new IllegalStateException("Please select a data source.");
        }
        if(!this.getReportTypeCode().equals("SAS_CUSTOM"))
        {
            if(!this.getReportTypeCode().equals("SAS_GRAPH"))
            {
                if(!this.getReportTypeCode().equals("SAS_ODS_HTML"))
                {
                    throw new IllegalStateException("Please select a type.");
                }
            }
        }
        if(this.getLocation().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please select a SAS program.");
        }
        if(!this.getShared().equals("P"))
        {
            if(!this.getShared().equals("S"))
            {
                if(!this.getShared().equals("T"))
                {
                	if(!this.getShared().equals("R")) {
                		throw new IllegalStateException("Please select a group.");	
                	}                    
                }
            }
        }
        if(this.owner_uid == null)
        {
            throw new IllegalStateException("Please select an owner.");
        }
        add_reason_cd = null;
        add_user_uid = null;
        effective_from_time = null;
        effective_to_time = null;
        filter_mode = "B";
        is_modifiable_ind = "N";
        org_access_permis = null;
        prog_area_access_permis = null;
        status_cd = "A";
        category = null;
    }

	public Timestamp getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}

	public long getReportSectionCode()
    {
        return (report_section_cd.longValue());
    }

    public void setReportSectionCode(long s)
    {
        report_section_cd = new Long(s);
    }
    public String getReportSectionCode_s()
    {
        return (report_section_cd == null) ? "" : report_section_cd.toString();
    }
    public void setReportSectionCode(String s)
    {
        if(s == null)
        {
        	report_section_cd = null;
            return;
        }
        try
        {
        	report_section_cd = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }
   

    
	public String getReportSectionDescTxt()
    {
        return (report_section_desc_txt);
    }

    public void setReportSectionDescTxt(String s)
    {
        report_section_desc_txt = s;
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
	
    public String getShared2() {
		return shared2;
	}

	public void setShared2(String shared2) {
		this.shared2 = shared2;
	}
	
}