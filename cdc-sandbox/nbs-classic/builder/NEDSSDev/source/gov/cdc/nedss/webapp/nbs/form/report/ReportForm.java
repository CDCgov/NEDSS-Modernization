package gov.cdc.nedss.webapp.nbs.form.report;

import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.report.dt.ReportFilterDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.report.ReportAction;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.util.*;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.*;

/**
 *  Struts action form for reports.
 *  @author Ed Jenkins
 */
public class ReportForm extends ActionForm
{

    private SummaryReportProxyVO proxy;
    private Collection<Object>  reportCollection  = null;
    private java.util.Collection<Object>  summaryProxyCollection;
    private ReportFilterDT rdDT = new ReportFilterDT();
    private Map<Object, Object> searchAttributeMap  = new HashMap<Object, Object>();
    private Map<Object, Object> filterColumnMap  = new HashMap<Object, Object>();
    
    
    public Map<Object, Object> getFilterColumnMap() {
		return filterColumnMap;
	}

	public void setFilterColumnMap(Map<Object, Object> filterColumnMap) {
		this.filterColumnMap = filterColumnMap;
	}

	static final LogUtils logger = new LogUtils(ReportForm.class.getName());
    public Map<Object, Object> getSearchAttributeMap() {
		return searchAttributeMap;
	}

	public void setSearchAttributeMap(Map<Object, Object> searchAttributeMap) {
		this.searchAttributeMap = searchAttributeMap;
	}

	public ReportFilterDT getRdDT() {
		return rdDT;
	}

	public void setRdDT(ReportFilterDT rdDT) {
		this.rdDT = rdDT;
	}

	public ReportForm()
    {
    }
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = new ArrayList<Object>();
		try {
			if(key!=null && key.equals("PLACE_LIST"))
				aList = CachedDropDowns.getPlaceList();
			else
				aList = CachedDropDowns.getCodedValueForType(key);
		} catch (Exception e) {
				logger.error("getCodedValue error for " + key + "/n " + e);
		} 
		return aList;
	}
    public void setProxy(SummaryReportProxyVO proxy)
    {
        this.proxy = proxy;
    }

    public SummaryReportProxyVO getProxy()
    {
        if(proxy == null)
        {
            proxy = new SummaryReportProxyVO();
        }
        return proxy;
    }

    public BatchEntryHelper getReport(int index)
    {
        if(this.reportCollection  == null)
        {
            this.reportCollection  = new ArrayList<Object> ();
        }
        int currentSize = this.reportCollection.size();
        if(index < currentSize)
        {
            try
            {
                Object[] tempArray = this.reportCollection.toArray();
                Object tempObj = tempArray[index];
                BatchEntryHelper temp = (BatchEntryHelper)tempObj;
                return temp;
            }
            catch(Exception e)
            {
            }
        }
        BatchEntryHelper temp = null;
        for(int i = currentSize; i < index + 1; i++)
        {
            temp = new BatchEntryHelper();
            this.reportCollection.add(temp);
        }
        return temp;
    }

    public void setReportCollection(Collection<Object> reportCollection)
    {
        this.reportCollection  = reportCollection;
    }

    public Collection<Object>  getReportCollection()
    {
        return this.reportCollection;
    }

    public void setSummaryProxyCollection(java.util.Collection<Object>  summaryProxyCollection)
    {
        this.summaryProxyCollection  = summaryProxyCollection;
    }

    public java.util.Collection<Object>  getSummaryProxyCollection()
    {
        return summaryProxyCollection;
    }

    public boolean hasProxy()
    {
        if(proxy == null)
        {
            return false;
        }
        return true;
    }

    public void reset()
    {
        proxy = null;
    }
    public void resetMaps()
    {
    	searchAttributeMap.clear();
        filterColumnMap.clear();
    }
    
	public void setAnswerArray(String key, String[] answer) {
		if (answer.length > 0) {
			String[] answerList = new String[answer.length];
			boolean selected = false;
			for (int i = 1; i <= answer.length; i++) {
				String answerTxt = answer[i - 1];
				if (!answerTxt.equals("")) {
					selected = true;
					answerList[i - 1] = answerTxt;
				}
			}
			if (selected)
				searchAttributeMap.put(key, answerList);
		}
	}

	public String[] getAnswerArray(String key) {
		return (String[]) searchAttributeMap.get(key);
	}
    
}
