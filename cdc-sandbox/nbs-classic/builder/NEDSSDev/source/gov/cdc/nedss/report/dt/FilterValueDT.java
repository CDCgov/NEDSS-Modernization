package gov.cdc.nedss.report.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.*;

public class FilterValueDT  extends AbstractVO implements RootDTInterface
{

    private Long reportFilterUid;
    private Long valueUid;
    private Integer sequenceNbr;
    private String valueType;
    private Long columnUid;
    private String valueTxt;
    private String filterValueOperator;

    /**
    * @roseuid 3C17F7230284
    */
    public FilterValueDT()
    {
    }

    public FilterValueDT(FilterValueDT fvDT)
    {
        this.reportFilterUid = fvDT.reportFilterUid;
        this.valueUid = fvDT.valueUid;
        this.sequenceNbr = fvDT.sequenceNbr;
        this.valueType = fvDT.valueType;
        this.columnUid = fvDT.columnUid;
        this.valueTxt = fvDT.valueTxt;
        this.filterValueOperator = fvDT.filterValueOperator;
        //setItNew(true);
    }

    public void setColumnUid(Long aColumnUid)
    {
        columnUid = aColumnUid;
        setItDirty(true);
    }

    public Long getColumnUid()
    {
        return columnUid;
    }

    /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C17F72302AC
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((FilterValueDT)objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    public void setFilterValueOperator(String aFilterValueOperator)
    {
        filterValueOperator = aFilterValueOperator;
        setItDirty(true);
    }

    public String getFilterValueOperator()
    {
        return filterValueOperator;
    }

    /**
    * @param itDelete
    * @roseuid 3C17F72400A5
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7240109
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F7230392
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7240004
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F724002C
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7240087
    */
    public boolean isItNew()
    {
        return itNew;
    }

    /**
    * Sets the value of the reportFilterUid property.
    *
    * @param aReportFilterUid the new value of the reportFilterUid property
    */
    public void setReportFilterUid(Long aReportFilterUid)
    {
        reportFilterUid = aReportFilterUid;
        setItDirty(true);
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

    public void setSequenceNbr(Integer aSequenceNbr)
    {
        sequenceNbr = aSequenceNbr;
        setItDirty(true);
    }

    public Integer getSequenceNbr()
    {
        return sequenceNbr;
    }

    public void setValueTxt(String aValueTxt)
    {
        valueTxt = aValueTxt;
        setItDirty(true);
    }

    public String getValueTxt()
    {
        return valueTxt;
    }

    public void setValueType(String aValueType)
    {
        valueType = aValueType;
        setItDirty(true);
    }

    public String getValueType()
    {
        return valueType;
    }

    /**
    * Sets the value of the value_uid property.
    *
    * @param aValue_uid the new value of the value_uid property
    */
    public void setValueUid(Long aValueUid)
    {
        valueUid = aValueUid;
        setItDirty(true);
    }

    /**
    * Access method for the value_uid property.
    *
    * @return   the current value of the value_uid property
    */
    public Long getValueUid()
    {
        return valueUid;
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
