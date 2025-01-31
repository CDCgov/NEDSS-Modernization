package gov.cdc.nedss.report.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.*;

public class DisplayColumnDT  extends AbstractVO implements RootDTInterface
{

    private Long displayColumnUid;
    private Long columnUid;
    private Long dataSourceUid;
    private Long reportUid;
    private Integer sequenceNbr;
    private String statusCd;
    private DataSourceColumnDT theDataSourceColumnDT;
    private ReportSortColumnDT theReportSortColumnDT;

    /**
    * @roseuid 3C17F6EE0288
    */
    public DisplayColumnDT()
    {
    }

    public DisplayColumnDT(DisplayColumnDT dDT)
    {
        this.displayColumnUid = dDT.displayColumnUid;
        this.columnUid = dDT.columnUid;
        this.dataSourceUid = dDT.dataSourceUid;
        this.reportUid = dDT.reportUid;
        this.sequenceNbr = dDT.sequenceNbr;
        this.statusCd = dDT.statusCd;
        this.theDataSourceColumnDT = new DataSourceColumnDT(dDT.theDataSourceColumnDT);
        if(dDT.theReportSortColumnDT!=null)
        	this.theReportSortColumnDT = new ReportSortColumnDT(dDT.theReportSortColumnDT);
        //setItNew(true);
    }

    /**
    * Sets the value of the columnUid property.
    *
    * @param aColumnUid the new value of the columnUid property
    */
    public void setColumnUid(Long aColumnUid)
    {
        columnUid = aColumnUid;
        setItDirty(true);
    }

    /**
    * Access method for the columnUid property.
    *
    * @return   the current value of the columnUid property
    */
    public Long getColumnUid()
    {
        return columnUid;
    }

    /**
    * Sets the value of the dataSourceUid property.
    *
    * @param aDataSourceUid the new value of the dataSourceUid property
    */
    public void setDataSourceUid(Long aDataSourceUid)
    {
        dataSourceUid = aDataSourceUid;
        setItDirty(true);
    }

    /**
    * Access method for the dataSourceUid property.
    *
    * @return   the current value of the dataSourceUid property
    */
    public Long getDataSourceUid()
    {
        return dataSourceUid;
    }

    /**
    * Sets the value of the displayColumnUid property.
    *
    * @param aDisplayColumnUid the new value of the displayColumnUid property
    */
    public void setDisplayColumnUid(Long aDisplayColumnUid)
    {
        displayColumnUid = aDisplayColumnUid;
        setItDirty(true);
    }

    /**
    * Access method for the displayColumnUid property.
    *
    * @return   the current value of the displayColumnUid property
    */
    public Long getDisplayColumnUid()
    {
        return displayColumnUid;
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

    /**
    * Sets the value of the reportUid property.
    *
    * @param aReportUid the new value of the reportUid property
    */
    public void setReportUid(Long aReportUid)
    {
        reportUid = aReportUid;
        setItDirty(true);
    }

    /**
    * Access method for the reportUid property.
    *
    * @return   the current value of the reportUid property
    */
    public Long getReportUid()
    {
        return reportUid;
    }

    /**
    * Sets the value of the sequenceNbr property.
    *
    * @param aSequenceNbr the new value of the sequenceNbr property
    */
    public void setSequenceNbr(Integer aSequenceNbr)
    {
        sequenceNbr = aSequenceNbr;
        setItDirty(true);
    }

    /**
    * Access method for the sequenceNbr property.
    *
    * @return   the current value of the sequenceNbr property
    */
    public Integer getSequenceNbr()
    {
        return sequenceNbr;
    }

    /**
    * Sets the value of the statusCd property.
    *
    * @param aStatusCd the new value of the statusCd property
    */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
    * Access method for the statusCd property.
    *
    * @return   the current value of the statusCd property
    */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
    * theDataSourceColumnDT is made read only because the report is only referencing
    * the DataSourceColumn information.  When the report is changed or deleted, the
    * DataSourceColumn information should remain uneffected.
    */
    public void setTheDataSourceColumnDT(DataSourceColumnDT aDataSourceColumnDT)
    {
        theDataSourceColumnDT = aDataSourceColumnDT;
        setItDirty(true);
    }
    /**
     * Access method for the theDataSourceColumnDT property.
     *
     * @return   the current value of the theDataSourceColumnDT property
     */
     public DataSourceColumnDT getTheDataSourceColumnDT()
     {
         return theDataSourceColumnDT;
     }

    /**
    * Access method for the theReportSortColumnDT property.
    *
    * @return   the current value of the theReportSortColumnDT property
    */
    public ReportSortColumnDT getTheReportSortColumnDT()
    {
        return theReportSortColumnDT;
    }
    
     public void setTheReportSortColumnDT(ReportSortColumnDT aReportSortColumnDT)
     {
         theReportSortColumnDT = aReportSortColumnDT;
         setItDirty(true);
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

     

}
