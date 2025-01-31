/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.helper;

import java.util.*;

import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.ldf.dt.LdfPageSetDT;
import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.systemservice.util.ValidationResult;
import gov.cdc.nedss.util.*;
import java.io.*;
import java.util.SortedSet;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PageSets {

	//For logging
	static final LogUtils logger = new LogUtils(PageSets.class.getName());
	private Map<Object,Object> pageSets;

	public PageSets() throws NEDSSAppException {
		NedssUtils nedssUtils = new NedssUtils();
		try {
			Object obj = nedssUtils.lookupBean(JNDINames.SRT_CACHE_EJB);
			SRTMapHome home =
				(SRTMapHome) PortableRemoteObject.narrow(obj, SRTMapHome.class);
			SRTMap srtMap = home.create();
			ArrayList<Object> aList = srtMap.getLDFPageIDs();
			pageSets = new HashMap<Object,Object>();
			for (Iterator<Object> iter = aList.iterator(); iter.hasNext();) {
				LdfPageSetDT element = (LdfPageSetDT) iter.next();
				pageSets.put(new Integer(element.getLdfPageId()), element);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}
	}
	/**
	 * @return
	 */
	public Map<Object,Object> getPageSets() {
		return pageSets;
	}

	public LdfPageSetDT getPageSetDT(String pageSetId) {
		return (LdfPageSetDT) pageSets.get(new Integer(pageSetId));
	}
	public List<Object> validate(ImportData data) {

		if (data instanceof DefinedFieldImportData) {
			return validate(((DefinedFieldImportData) data).getPageSetID());
		} else if (data instanceof SubformImportData) {
			return validate(((SubformImportData) data).getPageSetID());
		}
		return new ArrayList<Object> ();
	}

	public List<Object> validate(String type) {
		List<Object> retVal = new ArrayList<Object> ();
		if (type == null || type.trim().length() == 0) {
			retVal.add(
				new ValidationResult(
					false,
					ImportConstants.NULL_DF_PAGE_SET_TEXT));
		} else if (getPageSetDT(type) == null) {
			retVal.add(
				new ValidationResult(
					false,
					ImportConstants.INVALID_DF_PAGE_SET_TEXT + type));
		}

		return retVal;
	}
        public StringBuffer toHTML()
        {
        StringBuffer html = new StringBuffer();
        if(pageSets != null && pageSets.size() > 0)
          {
            html.append("<br><b>Pagesets Table</b></br>");
            html.append("<TABLE border=1><TR><TH>Pageset<TH>Description<TH>Business Object Name<TH>Condition Code</TR>");

            TreeMap<Object,Object> keyMap = new TreeMap<Object,Object> (pageSets);
            Collection<Object>  keys = keyMap.keySet();
           Iterator<Object>  keyIter = keys.iterator();
            while (keyIter.hasNext()) {
              Integer key = (Integer) keyIter.next();
              LdfPageSetDT valueDT = (LdfPageSetDT) pageSets.get(key);
              String value = valueDT.getCodeShortDescTxt();
              String businessObjName = valueDT.getBusinessObjNm();
              String conditionCd = valueDT.getConditionCd();
              html.append("<TR><TD>" + key.toString() + "</TD><TD>" + value + "</TD><TD>"+businessObjName+"</TD><TD>"+conditionCd+"</TD></TR>");
            }
            html.append("</TABLE>");
          }
        return html;
      }

      public static void main(String[] args) throws Exception {
        PageSets pageSet = new PageSets();
        StringBuffer html = new StringBuffer();
        html.append("<HTML>");
        html.append(pageSet.toHTML());
        html.append("</HTML>");

        File file = new File("pagesets.html");
        FileWriter fw = new FileWriter(file);
        PrintWriter out = new PrintWriter(new BufferedWriter(fw));
        out.write(html.toString());
        out.flush();

      }


}
