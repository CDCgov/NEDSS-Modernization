package gov.cdc.nedss.webapp.nbs.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMap;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;

/**
 * This class is for get the value list for the different questions, 
 * This value list is not stored in the CachedDropDowns. Because those list
 * may not be used by application often, we do want to put them into Cache to 
 * enlarge the size of the CachedDropDowns object.
 *
 */
public class QuestionDropDownCreator {
	
private static QuestionDropDownCreator creator = new QuestionDropDownCreator();    
    
    public static QuestionDropDownCreator getInstance() { return creator; }   
    
	public static final LogUtils logger = new LogUtils(QuestionDropDownCreator.class.getName());
	private static SRTMap srtm = null;
	
	private  static SRTMap getSRTMapEJBRef() throws Exception {

		if (srtm == null) {
			NedssUtils nu = new NedssUtils();
			Object objref = nu.lookupBean(JNDINames.SRT_CACHE_EJB);
			if (objref != null) {
				SRTMapHome home = (SRTMapHome) PortableRemoteObject.narrow(objref, SRTMapHome.class);
				srtm = home.create();
			}
		}
		return srtm;
	}
	
	public ArrayList<Object> getQuestionDropDownList(String valueSet)
	{
		if(NEDSSConstants.CODE_SET_JURISDICTION.equals(valueSet))
			return CachedDropDowns.getJurisdictionList();
		
		else if(NEDSSConstants.CODE_SET_COUNTY_CCD.equals(valueSet))
		{
			ArrayList<Object> list = new ArrayList<Object>();
			String stateCD = PropertyUtil.getInstance().getNBS_STATE_CODE();
			if(stateCD != null && stateCD !="")
				list =  CachedDropDowns.getCountyCodes(stateCD);
			return list;
		}
		else
			return CachedDropDowns.getCodedValueForType(valueSet);
	}
	
	public ArrayList<Object> getJurisdictionCodedValues()
	{
		ArrayList<Object> list = null;
		try {
				list = new ArrayList<Object> ();
				if (getSRTMapEJBRef() != null) {
					TreeMap<Object,Object> juris = getSRTMapEJBRef()
							.getJurisdictionCodedValues();
					if (juris != null && juris.size() > 0) {
						TreeSet<Object>set = new TreeSet<Object>(new Comparator<Object>() {
				            public int compare(Object obj, Object obj1) {
				                return ((Comparable<Object>) ((Map.Entry) obj).getValue()).compareTo(((Map.Entry) obj1).getValue());
				            }
				        });
				        
				        set.addAll(juris.entrySet());
				        DropDownCodeDT dDownDT = new DropDownCodeDT();
						dDownDT.setKey(""); dDownDT.setValue("");
						list.add(dDownDT);
				        for (Iterator<Object> i = set.iterator(); i.hasNext();) {
				            Map.Entry entry = (Map.Entry) i.next();
				            dDownDT = new DropDownCodeDT();
							dDownDT.setKey((String) entry.getKey());
							dDownDT.setValue((String) entry.getValue());
							list.add(dDownDT);
				        }
					}
				}
		} catch (Exception ex) {
			logger.error("Error while loading jurisdictions in getJurisdictionList: CachedDropDowns. ");
		}
		return list;
	}
}
