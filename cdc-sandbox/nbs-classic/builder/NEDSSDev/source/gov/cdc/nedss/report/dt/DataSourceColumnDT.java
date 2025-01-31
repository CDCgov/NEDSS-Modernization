package gov.cdc.nedss.report.dt;

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

public class DataSourceColumnDT extends AbstractVO
{

    private Long columnUid;
    private String columnName;
    private String columnTitle;
    private String columnTypeCode;
    private Long dataSourceUid;
    private String descTxt;
    private String displayable;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String filterable;
    private String statusCd;
    private Timestamp statusTime;
    private Integer columnMaxLen;
    private String codeDescCd;
    private String codesetNm;
  

    /**
    * @roseuid 3C17F71E0178
    */
    public DataSourceColumnDT(DataSourceColumnDT dscDT)
    {
        this.columnUid = dscDT.columnUid;
        this.columnName = dscDT.columnName;
        this.columnTitle = dscDT.columnTitle;
        this.columnTypeCode = dscDT.columnTypeCode;
        this.dataSourceUid = dscDT.dataSourceUid;
        this.descTxt = dscDT.descTxt;
        this.displayable = dscDT.displayable;
        this.effectiveFromTime = dscDT.effectiveFromTime;
        this.effectiveToTime = dscDT.effectiveToTime;
        this.filterable = dscDT.filterable;
        this.statusCd = dscDT.statusCd;
        this.statusTime = dscDT.statusTime;
        this.columnMaxLen = dscDT.columnMaxLen;
        this.codeDescCd = dscDT.codeDescCd;
        this.codesetNm = dscDT.codesetNm;
        //setItNew(true);
    }

    public DataSourceColumnDT()
    {
    }

    /**
    * Sets the value of the columnMaxLen property.
    *
    * @param aColumnMaxLen the new value of the columnMaxLen property
    */
    public void setColumnMaxLen(Integer aColumnMaxLen)
    {
        columnMaxLen = aColumnMaxLen;
        setItDirty(true);
    }

    /**
    * Access method for the columnMaxLen property.
    *
    * @return   the current value of the columnMaxLen property
    */
    public Integer getColumnMaxLen()
    {
        return columnMaxLen;
    }

    /**
    * Sets the value of the columnName property.
    *
    * @param aColumnName the new value of the columnName property
    */
    public void setColumnName(String aColumnName)
    {
        columnName = aColumnName;
        setItDirty(true);
    }

    /**
    * Access method for the columnName property.
    *
    * @return   the current value of the columnName property
    */
    public String getColumnName()
    {
        return columnName;
    }

    /**
    * Sets the value of the columnTitle property.
    *
    * @param aColumnTitle the new value of the columnTitle property
    */
    public void setColumnTitle(String aColumnTitle)
    {
        columnTitle = aColumnTitle;
        setItDirty(true);
    }

    /**
    * Access method for the columnTitle property.
    *
    * @return   the current value of the columnTitle property
    */
    public String getColumnTitle()
    {
        return columnTitle;
    }

    /**
    * Sets the value of the columnTypeCode property.
    *
    * @param aColumnTypeCode the new value of the columnTypeCode property
    */
    public void setColumnTypeCode(String aColumnTypeCode)
    {
        columnTypeCode = aColumnTypeCode;
        setItDirty(true);
    }

    /**
    * Access method for the columnTypeCode property.
    *
    * @return   the current value of the columnTypeCode property
    */
    public String getColumnTypeCode()
    {
        return columnTypeCode;
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
    * Sets the value of the descTxt property.
    *
    * @param aDescTxt the new value of the descTxt property
    */
    public void setDescTxt(String aDescTxt)
    {
        descTxt = aDescTxt;
        setItDirty(true);
    }

    /**
    * Access method for the descTxt property.
    *
    * @return   the current value of the descTxt property
    */
    public String getDescTxt()
    {
        return descTxt;
    }

    /**
    * Sets the value of the displayable property.
    *
    * @param aDisplayable the new value of the displayable property
    */
    public void setDisplayable(String aDisplayable)
    {
        displayable = aDisplayable;
        setItDirty(true);
    }

    /**
    * Access method for the displayable property.
    *
    * @return   the current value of the displayable property
    */
    public String getDisplayable()
    {
        return displayable;
    }

    /**
    * Sets the value of the effectiveFromTime property.
    *
    * @param aEffectiveFromTime the new value of the effectiveFromTime property
    */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

    /**
    * Access method for the effectiveFromTime property.
    *
    * @return   the current value of the effectiveFromTime property
    */
    public Timestamp getEffectiveFromTime()
    {
        return effectiveFromTime;
    }

    /**
    * Sets the value of the effectiveToTime property.
    *
    * @param aEffectiveToTime the new value of the effectiveToTime property
    */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

    /**
    * Access method for the effectiveToTime property.
    *
    * @return   the current value of the effectiveToTime property
    */
    public Timestamp getEffectiveToTime()
    {
        return effectiveToTime;
    }

    /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C17F71E01C8
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((DataSourceColumnDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
    * Sets the value of the filterable property.
    *
    * @param aFilterable the new value of the filterable property
    */
    public void setFilterable(String aFilterable)
    {
        filterable = aFilterable;
        setItDirty(true);
    }

    /**
    * Access method for the filterable property.
    *
    * @return   the current value of the filterable property
    */
    public String getFilterable()
    {
        return filterable;
    }

    /**
    * @param itDelete
    * @roseuid 3C17F71F01FC
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F71F02CE
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F71E03B3
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F71F00A7
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F71F00EE
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F71F01B6
    */
    public boolean isItNew()
    {
        return itNew;
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
    * Sets the value of the statusTime property.
    *
    * @param aStatusTime the new value of the statusTime property
    */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
    * Access method for the statusTime property.
    *
    * @return   the current value of the statusTime property
    */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

	public String getCodeDescCd() {
		return codeDescCd;
	}

	public void setCodeDescCd(String codeDescCd) {
		this.codeDescCd = codeDescCd;
		setItDirty(true);
	}

	public String getCodesetNm() {
		return codesetNm;
	}

	public void setCodesetNm(String codesetNm) {
		this.codesetNm = codesetNm;
		setItDirty(true);
	}

}
