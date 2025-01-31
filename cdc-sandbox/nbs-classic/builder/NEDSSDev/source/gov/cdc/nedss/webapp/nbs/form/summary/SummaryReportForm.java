package gov.cdc.nedss.webapp.nbs.form.summary;


import java.util.*;
import org.apache.struts.action.*;
import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.webapp.nbs.util.*;


/**
 *  Struts action form for summary reports.
 *  @author Ed Jenkins
 */
public class SummaryReportForm extends ActionForm
{
  private SummaryReportProxyVO proxy;
  private Collection<Object>  reportCollection  = null;
  private java.util.Collection<Object>  summaryProxyCollection;
    public void reset()
    {
        proxy = null;
    }



    public boolean hasProxy()
    {
        if(proxy == null)
        {
            return false;
        }
        return true;
    }

    public SummaryReportProxyVO getProxy()
    {
        if(proxy == null)
        {
            proxy = new SummaryReportProxyVO();
        }
        return proxy;
    }

    public Collection<Object>  getReportCollection()
    {
      return this.reportCollection;
    }

    public void setReportCollection(Collection<Object> reportCollection)
    {
      this.reportCollection  = reportCollection;
    }

    public void setProxy(SummaryReportProxyVO proxy)
    {
        this.proxy = proxy;
    }
 /************************************************
     * BATCH entry for observations
     */
    public BatchEntryHelper getReport(int index)
    {

	// this should really be in the constructor
	if (this.reportCollection  == null)
	    this.reportCollection  = new ArrayList<Object> ();

	int currentSize = this.reportCollection.size();

	// check if we have a this many personNameDTs
	if (index < currentSize)
	{

	    try
	    {

		Object[] tempArray = this.reportCollection.toArray();
		Object tempObj = tempArray[index];
		BatchEntryHelper temp = (BatchEntryHelper)tempObj;

		return temp;
	    }
	    catch (Exception e)
	    {
		//##!! System.out.println(e); // do nothing just continue
	    }
	}

	BatchEntryHelper temp = null;

	for (int i = currentSize; i < index + 1; i++)
	{
	    temp = new BatchEntryHelper();
	    this.reportCollection.add(temp);
	}

	return temp;
    }


  public void setSummaryProxyCollection(java.util.Collection<Object>  summaryProxyCollection) {
    this.summaryProxyCollection  = summaryProxyCollection;
  }
  public java.util.Collection<Object>  getSummaryProxyCollection() {
    return summaryProxyCollection;
  }


}

