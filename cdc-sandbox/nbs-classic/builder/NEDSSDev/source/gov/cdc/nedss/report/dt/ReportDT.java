package gov.cdc.nedss.report.dt;

import gov.cdc.nedss.util.*;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

public class ReportDT extends AbstractVO
{
	private static final Logger logger = Logger.getLogger(ReportDT.class);
    private Long reportUid;
    private Long dataSourceUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserUid;
    private String descTxt;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String filterMode;
    private String isModifiableInd;
    private String location;
    private Long ownerUid;
    private String orgAccessPermis;
    private String progAreaAccessPermis;
    private String reportTitle;
    private String reportTypeCode;
    private String shared;
    private String statusCd;
    private Timestamp statusTime;
    private String category;
    private Long sectionCd;
    private String sectionDescTxt;
    private boolean runPermission = false;
    private boolean deletePermission = false;
    private String reportActionRun;
    private String reportActionDelete;
    private String reportActionDetail;

    public String getReportActionRun() {
    	return reportActionRun;
	}

	public void setReportActionRun(String reportActionRun) {
		this.reportActionRun = reportActionRun;
	}
	public String getReportActionDelete() {
		return reportActionDelete;
	}

	public void setReportActionDelete(String reportActionDelete) {
		this.reportActionDelete = reportActionDelete;
	}

	public String getReportActionDetail() {
		return reportActionDetail;
	}

	public void setReportActionDetail(String reportActionDetail) {
		this.reportActionDetail = reportActionDetail;
	}

	public Long getSectionCd() {
		return sectionCd;
	}

	public void setSectionCd(Long sectionCd) {
		this.sectionCd = sectionCd;
	}

	public String getSectionCd_s()
    {
        return (sectionCd == null) ? "" : String.valueOf(sectionCd);
    }
	
	 public void setSectionCd(String s)
	    {
	        if(s == null)
	        {
	        	sectionCd = null;
	            return;
	        }
	        try
	        {
	        	sectionCd = Long.valueOf(s);
	        }
	        catch(Exception ex)
	        {
	            logger.error(ex, ex);
	        }
	    }
	   
	
	public String getSectionDescTxt() {
		  return sectionDescTxt;
	}

	public void setSectionDescTxt(String sectionDescTxt) {
		this.sectionDescTxt = sectionDescTxt;
	}

	/**
    * @roseuid 3C17F72403B2
    */
    public ReportDT()
    {
    }

    public ReportDT(ReportDT rDT)
    {
        this.reportUid = rDT.reportUid;
        this.dataSourceUid = rDT.dataSourceUid;
        this.addReasonCd = rDT.addReasonCd;
        this.addTime = rDT.addTime;
        this.addUserUid = rDT.addUserUid;
        this.descTxt = rDT.descTxt;
        this.effectiveFromTime = rDT.effectiveFromTime;
        this.effectiveToTime = rDT.effectiveToTime;
        this.filterMode = rDT.filterMode;
        this.isModifiableInd = rDT.isModifiableInd;
        this.location = rDT.location;
        this.ownerUid = rDT.ownerUid;
        this.orgAccessPermis = rDT.orgAccessPermis;
        this.progAreaAccessPermis = rDT.progAreaAccessPermis;
        this.reportTitle = rDT.reportTitle;
        this.reportTypeCode = rDT.reportTypeCode;
        this.shared = rDT.shared;
        this.statusCd = rDT.statusCd;
        this.statusTime = rDT.statusTime;
        this.category = rDT.category;
        //setItNew(true);
    }

    /**
    * Sets the value of the addReasonCd property.
    *
    * @param aAddReasonCd the new value of the addReasonCd property
    */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
    * Access method for the addReasonCd property.
    *
    * @return   the current value of the addReasonCd property
    */
    public String getAddReasonCd()
    {
    	 return addReasonCd;
    }

    /**
    * Sets the value of the addTime property.
    *
    * @param aAddTime the new value of the addTime property
    */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
    * Access method for the addTime property.
    *
    * @return   the current value of the addTime property
    */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
    * Sets the value of the addUserUid property.
    *
    * @param aAddUserUid the new value of the addUserUid property
    */
    public void setAddUserUid(Long aAddUserUid)
    {
        addUserUid = aAddUserUid;
        setItDirty(true);
    }

    /**
    * Access method for the addUserUid property.
    *
    * @return   the current value of the addUserUid property
    */
    public Long getAddUserUid()
    {
        return addUserUid;
    }

    /**
    * Sets the value of the category property.
    *
    * @param aCategory the new value of the category property
    */
    public void setCategory(String aCategory)
    {
        category = aCategory;
        setItDirty(true);
    }

    /**
    * Access method for the category property.
    *
    * @return   the current value of the category property
    */
    public String getCategory()
    {
    	 return category;

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
    * @roseuid 3C17F7250010
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((ReportDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
    * Sets the value of the filterMode property.
    *
    * @param aFilterMode the new value of the filterMode property
    */
    public void setFilterMode(String aFilterMode)
    {
        filterMode = aFilterMode;
        setItDirty(true);
    }

    /**
    * Access method for the filterMode property.
    *
    * @return   the current value of the filterMode property
    */
    public String getFilterMode()
    {
    	 return filterMode;
    }

    /**
    * Sets the value of the isModifiableInd property.
    *
    * @param aIsModifiableInd the new value of the isModifiableInd property
    */
    public void setIsModifiableInd(String aIsModifiableInd)
    {
        isModifiableInd = aIsModifiableInd;
        setItDirty(true);
    }

    /**
    * Access method for the isModifiableInd property.
    *
    * @return   the current value of the isModifiableInd property
    */
    public String getIsModifiableInd()
    {
    	return isModifiableInd;
    }

    /**
    * @param itDelete
    * @roseuid 3C17F7250269
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F72502D7
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F7250114
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7250182
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F72501B4
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F725022D
    */
    public boolean isItNew()
    {
        return itNew;
    }

    /**
    * Sets the value of the location property.
    *
    * @param aLocation the new value of the location property
    */
    public void setLocation(String aLocation)
    {
        location = aLocation;
        setItDirty(true);
    }

    /**
    * Access method for the location property.
    *
    * @return   the current value of the location property
    */
    public String getLocation()
    {
    	return location;
    }

    /**
    * Sets the value of the orgAccessPermis property.
    *
    * @param aOrgAccessPermis the new value of the orgAccessPermis property
    */
    public void setOrgAccessPermis(String aOrgAccessPermis)
    {
        orgAccessPermis = aOrgAccessPermis;
        setItDirty(true);
    }

    /**
    * Access method for the orgAccessPermis property.
    *
    * @return   the current value of the orgAccessPermis property
    */
    public String getOrgAccessPermis()
    {
    	return orgAccessPermis;
    }

    /**
    * Sets the value of the ownerUid property.
    *
    * @param aOwnerUid the new value of the ownerUid property
    */
    public void setOwnerUid(Long aOwnerUid)
    {
        ownerUid = aOwnerUid;
        setItDirty(true);
    }

    /**
    * Access method for the ownerUid property.
    *
    * @return   the current value of the ownerUid property
    */
    public Long getOwnerUid()
    {
        return ownerUid;
    }

    /**
    * Sets the value of the progAreaAccessPermis property.
    *
    * @param aProgAreaAccessPermis the new value of the progAreaAccessPermis property
    */
    public void setProgAreaAccessPermis(String aProgAreaAccessPermis)
    {
        progAreaAccessPermis = aProgAreaAccessPermis;
        setItDirty(true);
    }

    /**
    * Access method for the progAreaAccessPermis property.
    *
    * @return   the current value of the progAreaAccessPermis property
    */
    public String getProgAreaAccessPermis()
    {
    	return progAreaAccessPermis;
    }

    /**
    * Sets the value of the reportTitle property.
    *
    * @param aReportTitle the new value of the reportTitle property
    */
    public void setReportTitle(String aReportTitle)
    {
        reportTitle = aReportTitle;
        setItDirty(true);
    }

    /**
    * Access method for the reportTitle property.
    *
    * @return   the current value of the reportTitle property
    */
    public String getReportTitle()
    {
    	return reportTitle;

    }

    /**
    * Sets the value of the reportTypeCode property.
    *
    * @param aReportTypeCode the new value of the reportTypeCode property
    */
    public void setReportTypeCode(String aReportTypeCode)
    {
        reportTypeCode = aReportTypeCode;
        setItDirty(true);
    }

    /**
    * Access method for the reportTypeCode property.
    *
    * @return   the current value of the reportTypeCode property
    */
    public String getReportTypeCode()
    {
    	return reportTypeCode;
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
    * Sets the value of the shared property.
    *
    * @param aShared the new value of the shared property
    */
    public void setShared(String aShared)
    {
        shared = aShared;
        setItDirty(true);
    }

    /**
    * Access method for the shared property.
    *
    * @return   the current value of the shared property
    */
    public String getShared()
    {
    	return shared;

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

	public boolean isRunPermission() {
		return runPermission;
	}

	public void setRunPermission(boolean runPermission) {
		this.runPermission = runPermission;
	}

	public boolean isDeletePermission() {
		return deletePermission;
	}

	public void setDeletePermission(boolean deletePermission) {
		this.deletePermission = deletePermission;
	}

}
