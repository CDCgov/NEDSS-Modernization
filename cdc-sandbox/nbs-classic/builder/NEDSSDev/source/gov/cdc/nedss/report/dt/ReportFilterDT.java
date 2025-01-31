package gov.cdc.nedss.report.dt;

import gov.cdc.nedss.util.*;

import java.util.*;

public class ReportFilterDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;
    private Long reportFilterUid;
    private Long reportUid;
    private Long dataSourceUid;
    private Long filterUid;
    private String statusCd;
    private Integer maxValueCnt;
    private Integer minValueCnt;
    private Long columnUid;
    private String reportFilterInd;
    
    /**
    * theFilterCodeDT is made read only because the report is only referencing the
    * FilterCode information.  When the report is changed or deleted, the FilterCode
    * information should remain uneffected.
    */
    public FilterCodeDT theFilterCodeDT;
	public Collection<Object> theFilterValueDTCollection;

    /**
    * @roseuid 3C17F7260166
    */
    public ReportFilterDT()
    {
    }

    public ReportFilterDT(ReportFilterDT rfDT)
    {
        this.theFilterCodeDT = new FilterCodeDT(rfDT.theFilterCodeDT);
        this.theFilterValueDTCollection  = new ArrayList<Object> ();
        Iterator<Object> it = null;
        for(it = rfDT.theFilterValueDTCollection.iterator(); it.hasNext();)
        {
            this.theFilterValueDTCollection.add(new FilterValueDT((FilterValueDT)it.next()));
        }
        this.reportFilterUid = rfDT.reportFilterUid;
        this.reportUid = rfDT.reportFilterUid;
        this.dataSourceUid = rfDT.dataSourceUid;
        this.filterUid = rfDT.filterUid;
        this.statusCd = rfDT.statusCd;
        this.maxValueCnt = rfDT.maxValueCnt;
        this.minValueCnt = rfDT.minValueCnt;
        this.columnUid = rfDT.columnUid;
        this.reportFilterInd = rfDT.reportFilterInd;
        //setItNew(true);
    }

    /**
    * Sets the value of the theColumnUid property.
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
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C17F7260198
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((ReportFilterDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    public void setFilterCodeDT(FilterCodeDT aFilterCodeDT)
    {
        theFilterCodeDT = aFilterCodeDT;
        setItDirty(true);
    }

    /**
    * Sets the value of the filterUid property.
    *
    * @param aFilterUid the new value of the filterUid property
    */
    public void setFilterUid(Long aFilterUid)
    {
        filterUid = aFilterUid;
        setItDirty(true);
    }

    /**
    * Access method for the filterUid property.
    *
    * @return   the current value of the filterUid property
    */
    public Long getFilterUid()
    {
        return filterUid;
    }

    /**
    * @param itDelete
    * @roseuid 3C17F7260397
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7270013
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F7260274
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F72602E2
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F726030A
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7260379
    */
    public boolean isItNew()
    {
        return itNew;
    }

    /**
    * Sets the value of the max_value_cnt property.
    *
    * @param aMax_value_cnt the new value of the max_value_cnt property
    */
    public void setMaxValueCnt(Integer aMaxValueCnt)
    {
        maxValueCnt = aMaxValueCnt;
        setItDirty(true);
    }

    /**
    * Access method for the max_value_cnt property.
    *
    * @return   the current value of the max_value_cnt property
    */
    public Integer getMaxValueCnt()
    {
        return maxValueCnt;
    }

    /**
    * Sets the value of the min_value_cnt property.
    *
    * @param aMin_value_cnt the new value of the min_value_cnt property
    */
    public void setMinValueCnt(Integer aMinValueCnt)
    {
        minValueCnt = aMinValueCnt;
        setItDirty(true);
    }

    /**
    * Access method for the min_value_cnt property.
    *
    * @return   the current value of the min_value_cnt property
    */
    public Integer getMinValueCnt()
    {
        return minValueCnt;
    }

    /**
    * Sets the value of the reportFilterUid property.
    *
    * @param aReportFilterUid the new value of the reportFilterUid property
    */
    public void setReportFilterUid(Long aReportFilterUid)
    {
        reportFilterUid = aReportFilterUid;
    }

    /**
    * Access method for the reportFilterUid property.
    *
    * @return   the current value of the reportFilterUid property
    */
    public Long getReportFilterUid()
    {
        return reportFilterUid;
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
    * Access method for the theFilterCodeDT property.
    *
    * @return   the current value of the theFilterCodeDT property
    */
    public FilterCodeDT getTheFilterCodeDT()
    {
        return theFilterCodeDT;
    }

    /**
    * Sets the value of the theFilterValueDTCollection  property.
    *
    * @param aTheFilterValueDTCollection  the new value of the theFilterValueDTCollection  property
    */
    public void setTheFilterValueDTCollection(Collection<Object> aTheFilterValueDTCollection)
    {
        theFilterValueDTCollection  = aTheFilterValueDTCollection;
        setItDirty(true);
    }

    /**
    * Access method for the theFilterValueDTCollection  property.
    *
    * @return   the current value of the theFilterValueDTCollection  property
    */
    public Collection<Object> getTheFilterValueDTCollection()
    {
        return theFilterValueDTCollection;
    }

    /**
     * 
     * @return
     */
	public String getReportFilterInd() {
		return reportFilterInd;
	}

	/**
	 * 
	 * @param reportFilterInd
	 */
	public void setReportFilterInd(String reportFilterInd) {
		this.reportFilterInd = reportFilterInd;
	}

}
