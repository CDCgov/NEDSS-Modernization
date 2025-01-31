package gov.cdc.nedss.reportadmin.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import org.apache.log4j.Logger;

/**
 * Represents the Report_Filter table.
 * @author Ed Jenkins
 */
public class ReportFilterDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportFilterDT.class);

    private Long report_filter_uid;
    private Long report_uid;
    private Long data_source_uid;
    private Long filter_uid;
    private String status_cd;
    private Integer max_value_cnt;
    private Integer min_value_cnt;
    private Long column_uid;
    private String reportFilterInd;
    private String reportFilterValidationUid;

    public long getReportFilterUID()
    {
        return (report_filter_uid == null) ? 0L : report_filter_uid.longValue();
    }

    public String getReportFilterUID_s()
    {
        return (report_filter_uid == null) ? "" : report_filter_uid.toString();
    }

    public void setReportFilterUID(long l)
    {
        report_filter_uid = new Long(l);
    }

    public void setReportFilterUID(String s)
    {
        if(s == null)
        {
            report_filter_uid = null;
            return;
        }
        try
        {
            report_filter_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

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

    public int getMaxValueCount()
    {
        return (max_value_cnt == null) ? 0 : max_value_cnt.intValue();
    }

    public String getMaxValueCount_s()
    {
        return (max_value_cnt == null) ? "" : max_value_cnt.toString();
    }

    public void setMaxValueCount(int i)
    {
        max_value_cnt = new Integer(i);
    }

    public void setMaxValueCount(String s)
    {
        if(s == null)
        {
            max_value_cnt = null;
            return;
        }
        try
        {
            max_value_cnt = Integer.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public int getMinValueCount()
    {
        return (min_value_cnt == null) ? 0 : min_value_cnt.intValue();
    }

    public String getMinValueCount_s()
    {
        return (min_value_cnt == null) ? "" : min_value_cnt.toString();
    }

    public void setMinValueCount(int i)
    {
        min_value_cnt = new Integer(i);
    }

    public void setMinValueCount(String s)
    {
        if(s == null)
        {
            min_value_cnt = null;
            return;
        }
        try
        {
            min_value_cnt = Integer.valueOf(s);
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

    public String toString()
    {
        String s = null;
        if(report_filter_uid != null)
        {
            s = report_filter_uid.toString();
        }
        return s;
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getReportFilterUID() == 0L)
        {
            throw new IllegalStateException("Please specify a report filter uid.");
        }
        if(this.getReportUID() == 0L)
        {
            throw new IllegalStateException("Please specify a report uid.");
        }
        if(this.getDataSourceUID() == 0L)
        {
            throw new IllegalStateException("Please specify a data source uid.");
        }
        if(this.getFilterUID() == 0L)
        {
            throw new IllegalStateException("Please specify a filter uid.");
        }
        status_cd = "A";
        if(max_value_cnt == null)
        {
            throw new IllegalStateException("Please select a max value.");
        }
        int intMax = this.getMaxValueCount();
        if( (intMax != -1) && (intMax != 1) && (intMax != 2) )
        {
            throw new IllegalStateException("Please select a max value.");
        }
        if(min_value_cnt == null)
        {
            throw new IllegalStateException("Please select a min value.");
        }
        int intMin = this.getMinValueCount();
        if( (intMin != 0) && (intMin != 1) )
        {
            throw new IllegalStateException("Please select a min value.");
        }
    }

	public String getReportFilterInd() {
		if(reportFilterInd == null)
			return "N";
		return reportFilterInd;
	}

	public void setReportFilterInd(String reportFilterInd) {
		this.reportFilterInd = reportFilterInd;
	}

	public String getReportFilterValidationUid() {
		return reportFilterValidationUid;
	}

	public void setReportFilterValidationUid(String reportFilterValidationUid) {
		this.reportFilterValidationUid = reportFilterValidationUid;
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
