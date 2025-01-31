package gov.cdc.nedss.report.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

public class FilterCodeDT extends AbstractVO implements RootDTInterface
{

    private Long filterUid;
    private String codeTable;
    private String descTxt;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String filterCode;
    private String filterCodeSetNm;
    private String filterType;
    private String filterName;
    private String statusCd;
    private Timestamp statusTime;

    /**
    * @roseuid 3C17F72201BA
    */
    public FilterCodeDT()
    {
    }

    public FilterCodeDT(FilterCodeDT fcDT)
    {
        this.filterUid = fcDT.filterUid;
        this.codeTable = fcDT.codeTable;
        this.descTxt = fcDT.descTxt;
        this.effectiveFromTime = fcDT.effectiveFromTime;
        this.effectiveToTime = fcDT.effectiveToTime;
        this.filterCode = fcDT.filterCode;
        this.filterCodeSetNm = fcDT.filterCodeSetNm;
        this.filterType = fcDT.filterType;
        this.filterName = fcDT.filterName;
        this.statusCd = fcDT.statusCd;
        this.statusTime = fcDT.statusTime;
        //setItNew(true);
    }

    /**
    * Sets the value of the codeTable property.
    *
    * @param aCodeTable the new value of the codeTable property
    */
    public void setCodeTable(String aCodeTable)
    {
        codeTable = aCodeTable;
        setItDirty(true);
    }

    /**
    * Access method for the codeTable property.
    *
    * @return   the current value of the codeTable property
    */
    public String getCodeTable()
    {
        return codeTable;
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
    * @roseuid 3C17F72201F6
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((FilterCodeDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
    * Sets the value of the filterCode property.
    *
    * @param aFilterCode the new value of the filterCode property
    */
    public void setFilterCode(String aFilterCode)
    {
        filterCode = aFilterCode;
        setItDirty(true);
    }

    /**
    * Access method for the filterCode property.
    *
    * @return   the current value of the filterCode property
    */
    public String getFilterCode()
    {
        return filterCode;
    }

    /**
    * Sets the value of the filterCodeSetNm property.
    *
    * @param aFilterCodeSetNm the new value of the filterCodeSetNm property
    */
    public void setFilterCodeSetNm(String aFilterCodeSetNm)
    {
        filterCodeSetNm = aFilterCodeSetNm;
        setItDirty(true);
    }

    /**
    * Access method for the filterCodeSetNm property.
    *
    * @return   the current value of the filterCodeSetNm property
    */
    public String getFilterCodeSetNm()
    {
        return filterCodeSetNm;
    }

    /**
    * Sets the value of the filterName property.
    *
    * @param aFilterName the new value of the filterName property
    */
    public void setFilterName(String aFilterName)
    {
        filterName = aFilterName;
        setItDirty(true);
    }

    /**
    * Access method for the filterName property.
    *
    * @return   the current value of the filterName property
    */
    public String getFilterName()
    {
        return filterName;
    }

    /**
    * Sets the value of the filterType property.
    *
    * @param aFilterType the new value of the filterType property
    */
    public void setFilterType(String aFilterType)
    {
        filterType = aFilterType;
        setItDirty(true);
    }

    /**
    * Access method for the filterType property.
    *
    * @return   the current value of the filterType property
    */
    public String getFilterType()
    {
        return filterType;
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
    * @roseuid 3C17F7230021
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F72300A3
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F72202E7
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7220355
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F722037D
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F72203E1
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
