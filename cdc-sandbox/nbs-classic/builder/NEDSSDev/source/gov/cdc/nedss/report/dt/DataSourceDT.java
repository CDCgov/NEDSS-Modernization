package gov.cdc.nedss.report.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NedssUtils;

import java.sql.Timestamp;

public class DataSourceDT extends AbstractVO implements RootDTInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dataSourceUid;
    private String dataSourceName;
    private String dataSourceTitle;
    private String dataSourceTypeCode;
    private String descTxt;
    private String conditionSecurity;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String jurisdictionSecurity;
    private String orgAccessPermis;
    private String progAreaAccessPermis;
    private String statusCd;
    private Timestamp statueTime;
    private String reportingFacilitySecurity;

    /**
    * @roseuid 3C17F72001C1
    */
    public DataSourceDT()
    {
    }

    public DataSourceDT(DataSourceDT dsDT)
    {
        this.dataSourceUid = dsDT.dataSourceUid;
        this.dataSourceName = dsDT.dataSourceName;
        this.dataSourceTitle = dsDT.dataSourceTitle;
        this.dataSourceTypeCode = dsDT.dataSourceTypeCode;
        this.descTxt = dsDT.descTxt;
        this.conditionSecurity = dsDT.conditionSecurity;
        this.effectiveFromTime = dsDT.effectiveFromTime;
        this.effectiveToTime = dsDT.effectiveToTime;
        this.jurisdictionSecurity = dsDT.jurisdictionSecurity;
        this.orgAccessPermis = dsDT.orgAccessPermis;
        this.progAreaAccessPermis = dsDT.progAreaAccessPermis;
        this.statusCd = dsDT.statusCd;
        this.statueTime = dsDT.statueTime;
        //setItNew(true);
    }

    /**
    * Sets the value of the conditionSecurity property.
    *
    * @param aConditionSecurity the new value of the conditionSecurity property
    */
    public void setConditionSecurity(String aConditionSecurity)
    {
        conditionSecurity = aConditionSecurity;
        setItDirty(true);
    }

    /**
    * Access method for the conditionSecurity property.
    *
    * @return   the current value of the conditionSecurity property
    */
    public String getConditionSecurity()
    {
        return conditionSecurity;
    }

    /**
    * Sets the value of the dataSourceName property.
    *
    * @param aDataSourceName the new value of the dataSourceName property
    */
    public void setDataSourceName(String aDataSourceName)
    {
        dataSourceName = aDataSourceName;
        setItDirty(true);
    }

    /**
    * Access method for the dataSourceName property.
    *
    * @return   the current value of the dataSourceName property
    */
    public String getDataSourceName()
    {
        return dataSourceName;
    }

    /**
    * Sets the value of the dataSourceTitle property.
    *
    * @param aDataSourceTitle the new value of the dataSourceTitle property
    */
    public void setDataSourceTitle(String aDataSourceTitle)
    {
        dataSourceTitle = aDataSourceTitle;
        setItDirty(true);
    }

    /**
    * Access method for the dataSourceTitle property.
    *
    * @return   the current value of the dataSourceTitle property
    */
    public String getDataSourceTitle()
    {
        return dataSourceTitle;
    }

    /**
    * Sets the value of the dataSourceTypeCode property.
    *
    * @param aDataSourceTypeCode the new value of the dataSourceTypeCode property
    */
    public void setDataSourceTypeCode(String aDataSourceTypeCode)
    {
        dataSourceTypeCode = aDataSourceTypeCode;
        setItDirty(true);
    }

    /**
    * Access method for the dataSourceTypeCode property.
    *
    * @return   the current value of the dataSourceTypeCode property
    */
    public String getDataSourceTypeCode()
    {
        return dataSourceTypeCode;
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
    * @roseuid 3C17F72001F3
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((DataSourceDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
    * @param itDelete
    * @roseuid 3C17F72003DE
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7210050
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F72002C6
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7200320
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F720035C
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F72003B6
    */
    public boolean isItNew()
    {
        return itNew;
    }

    /**
    * Sets the value of the jurisdictionSecurity property.
    *
    * @param aJurisdictionSecurity the new value of the jurisdictionSecurity property
    */
    public void setJurisdictionSecurity(String aJurisdictionSecurity)
    {
        jurisdictionSecurity = aJurisdictionSecurity;
        setItDirty(true);
    }

    /**
    * Access method for the jurisdictionSecurity property.
    *
    * @return   the current value of the jurisdictionSecurity property
    */
    public String getJurisdictionSecurity()
    {
        return jurisdictionSecurity;
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
    * Sets the value of the statueTime property.
    *
    * @param aStatueTime the new value of the statueTime property
    */
    public void setStatueTime(Timestamp aStatueTime)
    {
        statueTime = aStatueTime;
        setItDirty(true);
    }

    /**
    * Access method for the statueTime property.
    *
    * @return   the current value of the statueTime property
    */
    public Timestamp getStatueTime()
    {
        return statueTime;
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
    
	public String getReportingFacilitySecurity() {
		 return (reportingFacilitySecurity == null) ? "N" : reportingFacilitySecurity;
   	}

	public void setReportingFacilitySecurity(String reportingFacilitySecurity) {
		this.reportingFacilitySecurity = reportingFacilitySecurity;
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
