package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the Report_Filter_Validation table.
 * @author Ed Jenkins
 */
public class ReportFilterValidationDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportFilterValidationDT.class);

    private Long report_filter_validation_uid;
    private Long report_filter_uid;
    private String report_filter_ind;
    private String status_cd;
    private Timestamp status_time;

    /**
     * Constructor.
     */
    public ReportFilterValidationDT()
    {
    }

    public long getReportFilterValidationUID()
    {
        return (report_filter_validation_uid == null) ? 0L : report_filter_validation_uid.longValue();
    }

    public String getReportFilterValidationUID_s()
    {
        return (report_filter_validation_uid == null) ? "" : report_filter_validation_uid.toString();
    }

    public void setReportFilterValidationUID(long l)
    {
        report_filter_validation_uid = new Long(l);
    }

    public void setReportFilterValidationUID(String s)
    {
        if(s == null)
        {
            report_filter_validation_uid = null;
            return;
        }
        try
        {
            report_filter_validation_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

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

    public String getReportFilterIndicator()
    {
        return (report_filter_ind == null) ? "" : report_filter_ind;
    }

    public void setReportFilterIndicator(String s)
    {
        report_filter_ind = s;
    }

    public String toString()
    {
        if(report_filter_validation_uid == null)
        {
            return "0";
        }
        return report_filter_validation_uid.toString();
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getReportFilterValidationUID() == 0L)
        {
            throw new IllegalStateException("Please specify a report filter validation uid.");
        }
        if(this.getReportFilterUID() == 0L)
        {
            throw new IllegalStateException("Please specify a report filter uid.");
        }
        if(this.getReportFilterIndicator().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please select a type.");
        }
        if( (!report_filter_ind.equalsIgnoreCase("Y")) && (!report_filter_ind.equalsIgnoreCase("N")) )
        {
            throw new IllegalStateException("Please select a type.");
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
