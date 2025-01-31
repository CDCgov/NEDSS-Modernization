package gov.cdc.nedss.report.vo;

import gov.cdc.nedss.util.*;

import java.util.*;

import gov.cdc.nedss.report.dt.*;

public class ReportVO extends AbstractVO
{

	private static final long serialVersionUID = 1L;
    public ReportDT theReportDT;
    public List<Object> theDisplayColumnDTList;
	public Collection<Object> theReportFilterDTCollection;
    Map<Object, Object> reportFilterMap = new HashMap<Object, Object>();

    public Map<Object, Object> getReportFilterMap() {
		return reportFilterMap;
	}

	public void setReportFilterMap(Map<Object, Object> reportFilterMap) {
		this.reportFilterMap = reportFilterMap;
	}

	/**
    * @roseuid 3C17F7270225
    */
    public ReportVO()
    {
    }

    public ReportVO(ReportVO rVO)
    {
        this.theReportDT = new ReportDT(rVO.getTheReportDT());
        this.theReportFilterDTCollection  = new ArrayList<Object> ();
        ArrayList<Object>  reportFilterDTList = (ArrayList<Object> )rVO.getTheReportFilterDTCollection();
        Iterator<Object> fIterator = null;
        for(fIterator = reportFilterDTList.iterator(); fIterator.hasNext();)
        {
            ReportFilterDT reportFilterDT = (ReportFilterDT)fIterator.next();
            this.theReportFilterDTCollection.add(new ReportFilterDT(reportFilterDT));
        }
        this.theDisplayColumnDTList = new ArrayList<Object> ();
        ArrayList<Object>  displayColumnDTList = (ArrayList<Object> )rVO.getTheDisplayColumnDTList();
        Iterator<Object> dIterator = null;
        for(dIterator = displayColumnDTList.iterator(); dIterator.hasNext();)
        {
            DisplayColumnDT displayColumnDT = (DisplayColumnDT)dIterator.next();
            this.theDisplayColumnDTList.add(new DisplayColumnDT(displayColumnDT));
        }
    }  //ReportVO(rVO)

    /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C17F727024E
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        return true;
    }

    /**
    * @param itDelete
    * @roseuid 3C17F7280028
    */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
    * @return boolean
    * @roseuid 3C17F7280082
    */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
    * @param itDirty
    * @roseuid 3C17F727030C
    */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
    * @return boolean
    * @roseuid 3C17F727037A
    */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
    * @param itNew
    * @roseuid 3C17F7270398
    */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
    * @return boolean
    * @roseuid 3C17F728000A
    */
    public boolean isItNew()
    {
        return itNew;
    }

    /**
    * Sets the value of the theDisplayColumnDTList property.
    *
    * @param aTheDisplayColumnDTList the new value of the theDisplayColumnDTList property
    */
    public void setTheDisplayColumnDTList(List<Object> aTheDisplayColumnDTList)
    {
        theDisplayColumnDTList = aTheDisplayColumnDTList;
        setItDirty(true);
    }

    /**
    * Access method for the theDisplayColumnDTList property.
    *
    * @return   the current value of the theDisplayColumnDTList property
    */
    public List<Object> getTheDisplayColumnDTList()
    {
        return theDisplayColumnDTList;
    }

    /**
    * Sets the value of the theReportDT property.
    *
    * @param aTheReportDT the new value of the theReportDT property
    */
    public void setTheReportDT(ReportDT aTheReportDT)
    {
        theReportDT = aTheReportDT;
        setItDirty(true);
    }

    /**
    * Access method for the theReportDT property.
    *
    * @return   the current value of the theReportDT property
    */
    public ReportDT getTheReportDT()
    {
        return theReportDT;
    }

    /**
    * Sets the value of the theReportFilterDTCollection  property.
    *
    * @param aTheReportFilterDTCollection  the new value of the theReportFilterDTCollection  property
    */
    public void setTheReportFilterDTCollection(Collection<Object> aTheReportFilterDTCollection)
    {
        theReportFilterDTCollection  = aTheReportFilterDTCollection;
        setItDirty(true);
    }

    /**
    * Access method for the theReportFilterDTCollection  property.
    *
    * @return   the current value of the theReportFilterDTCollection  property
    */
    public Collection<Object> getTheReportFilterDTCollection()
    {
        return theReportFilterDTCollection;
    }

}
