
package gov.cdc.nedss.report.dt;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NedssUtils;
import java.sql.Timestamp;

public class ReportSortColumnDT extends AbstractVO
{
  
    private Long reportSortColumnUid;
    private String reportSortOrderCode;
    private Integer reportSortSequenceNum;
    private Long dataSourceUid;
    private Long reportUid;
    private Long columnUid;    
    private String statusCd;
    private Timestamp statusTime;

    /**
    * @roseuid 3C17F6EE0288
    */
    public ReportSortColumnDT()
    {
    }

    public ReportSortColumnDT(ReportSortColumnDT sDT)
    {
        this.reportSortOrderCode = sDT.reportSortOrderCode;
        this.reportSortColumnUid = sDT.reportSortColumnUid;
        this.dataSourceUid = sDT.dataSourceUid;
        this.reportUid = sDT.reportUid;
        this.reportSortSequenceNum=sDT.reportSortSequenceNum;
        this.columnUid = sDT.columnUid;
        this.statusCd = sDT.statusCd;
        this.statusTime = sDT.statusTime;
        
        //setItNew(true);
    }

    /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C17F6EE02BA
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((DisplayColumnDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
    * @param itDelete
    * @roseuid 3C17F6EF0103
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F6EF0171
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F6EE03C8
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F6EF004E
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F6EF006C
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F6EF00DA
    */
    public boolean isItNew()
    {
        return itNew;
    }

	public Long getColumnUid() {
		return columnUid;
	}

	public void setColumnUid(Long columnUid) {
		this.columnUid = columnUid;
	}

	public Long getDataSourceUid() {
		return dataSourceUid;
	}

	public void setDataSourceUid(Long dataSourceUid) {
		this.dataSourceUid = dataSourceUid;
	}

	public Long getReportSortColumnUid() {
		return reportSortColumnUid;
	}

	public void setReportSortColumnUid(Long reportSortColumnUid) {
		this.reportSortColumnUid = reportSortColumnUid;
	}

	public String getReportSortOrderCode() {
		return reportSortOrderCode;
	}

	public void setReportSortOrderCode(String reportSortOrderCode) {
		this.reportSortOrderCode = reportSortOrderCode;
	}

	public Integer getReportSortSequenceNum() {
		return reportSortSequenceNum;
	}

	public void setReportSortSequenceNum(Integer reportSortSequenceNum) {
		this.reportSortSequenceNum = reportSortSequenceNum;
	}

	public Long getReportUid() {
		return reportUid;
	}

	public void setReportUid(Long reportUid) {
		this.reportUid = reportUid;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}


}
