package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the Report_Sort_Column table.
 * @author Ed Jenkins
 */
public class ReportSortColumnDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportSortColumnDT.class);

    private Long report_sort_column_uid;
    private String report_sort_order_code;
    private Integer report_sort_sequence_num;
    private Long data_source_uid;
    private Long report_uid;
    private Long column_uid;
    private String status_cd;
    private Timestamp status_time;

    /**
     * Constructor.
     */
    public ReportSortColumnDT()
    {
    }

    public long getReportSortColumnUID()
    {
        return (report_sort_column_uid == null) ? 0L : report_sort_column_uid.longValue();
    }

    public String getReportSortColumnUID_s()
    {
        return (report_sort_column_uid == null) ? "" : report_sort_column_uid.toString();
    }

    public void setReportSortColumnUID(long l)
    {
        report_sort_column_uid = new Long(l);
    }

    public void setReportSortColumnUID(String s)
    {
        if(s == null)
        {
            report_sort_column_uid = null;
            return;
        }
        try
        {
            report_sort_column_uid = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getReportSortOrderCode()
    {
        return (report_sort_order_code == null) ? "" : report_sort_order_code;
    }

    public void setReportSortOrderCode(String s)
    {
        report_sort_order_code = s;
    }

    public int getReportSortSequenceNumber()
    {
        return (report_sort_sequence_num == null) ? 0 : report_sort_sequence_num.intValue();
    }

    public String getReportSortSequenceNumber_s()
    {
        return (report_sort_sequence_num == null) ? "" : report_sort_sequence_num.toString();
    }

    public void setReportSortSequenceNumber(int i)
    {
        report_sort_sequence_num = new Integer(i);
    }

    public void setReportSortSequenceNumber(String s)
    {
        if(s == null)
        {
            report_sort_sequence_num = null;
            return;
        }
        try
        {
            report_sort_sequence_num = Integer.valueOf(s);
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
        if(report_sort_column_uid == null)
        {
            return "0";
        }
        return report_sort_column_uid.toString();
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getReportSortColumnUID() == 0L)
        {
            throw new IllegalStateException("Please specify a report sort column uid.");
        }
        if(this.getReportSortOrderCode().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please specify a sort order.");
        }
        if(this.getReportSortSequenceNumber() == 0)
        {
            throw new IllegalStateException("Please specify a sequence number.");
        }
        if(this.getDataSourceUID() == 0L)
        {
            throw new IllegalStateException("Please select a data source.");
        }
        if(this.getReportUID() == 0L)
        {
            throw new IllegalStateException("Please select a report.");
        }
        if(this.getColumnUID() == 0L)
        {
            throw new IllegalStateException("Please select a column.");
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
