package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the Filter_value table.
 * @author Ed Jenkins
 */
public class FilterValueDT extends AbstractVO implements RootDTInterface
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(FilterValueDT.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    private Long value_uid;
    private Long report_filter_uid;
    private Integer sequence_nbr;
    private String value_type;
    private Long column_uid;
    private String operator;
    private String value_txt;

    /**
     * Constructor.
     */
    public FilterValueDT()
    {
    }

    public long getValueUID()
    {
        return (value_uid == null) ? 0L : value_uid.longValue();
    }

    public String getValueUID_s()
    {
        return (value_uid == null) ? "" : value_uid.toString();
    }

    public void setValueUID(long l)
    {
        value_uid = new Long(l);
    }

    public void setValueUID(String s)
    {
        if(s == null)
        {
            value_uid = null;
            return;
        }
        try
        {
            value_uid = Long.valueOf(s);
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

    public int getSequenceNumber()
    {
        return (sequence_nbr == null) ? 0 : sequence_nbr.intValue();
    }

    public String getSequenceNumber_s()
    {
        return (sequence_nbr == null) ? "" : sequence_nbr.toString();
    }

    public void setSequenceNumber(int i)
    {
        sequence_nbr = new Integer(i);
    }

    public void setSequenceNumber(String s)
    {
        if(s == null)
        {
            sequence_nbr = null;
            return;
        }
        try
        {
            sequence_nbr = Integer.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getValueType()
    {
        return (value_type == null) ? "" : value_type;
    }

    public void setValueType(String s)
    {
        value_type = s;
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

    public String getOperator()
    {
        return (operator == null) ? "" : operator;
    }

    public void setOperator(String s)
    {
        operator = s;
    }

    public String getValueTxt()
    {
        return (value_txt == null) ? "" : value_txt;
    }

    public void setValueTxt(String s)
    {
        value_txt = s;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer(STRING_BUFFER_SIZE);
        if(column_uid != null)
        {
            sb.append(column_uid);
        }
        if(operator != null)
        {
            sb.append(operator);
        }
        if(value_txt != null)
        {
            sb.append(value_txt);
        }
        String s = sb.toString();
        return s;
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(this.getValueUID() == 0L)
        {
            throw new IllegalStateException("Please specify a value uid.");
        }
        if(this.getReportFilterUID() == 0L)
        {
            throw new IllegalStateException("Please specify a report filter uid.");
        }
        if(this.getValueType().equalsIgnoreCase(""))
        {
            throw new IllegalStateException("Please select a value type.");
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
