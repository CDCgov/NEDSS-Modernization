package gov.cdc.nedss.webapp.nbs.action.ldf.helper;

import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.* ;
import gov.cdc.nedss.ldf.dt.* ;
import gov.cdc.nedss.util.* ;
import gov.cdc.nedss.webapp.nbs.action.ldf.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.ldf.group.DefinedFieldSubformGroup;

import java.sql.* ;
import java.util.* ;

import javax.naming.* ;
import javax.rmi.* ;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>Title: LdfHelper.</p>
 * <p>Description: This class has mothods to resync extension xsp
 *    and database.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Shailesh Desai
 * @version 1.0
 */

public class LdfHelper extends BaseLdf{
	
 private static final String EMPTY_GROUP_TOKEN = "EMPTY_GROUP";
 private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
/* Synchronize Extension XSP with database.
  The function will read URl table (which represents page which can have LDF)
  and iterate through each page to get metadata for all LDF elements
  on that page. It than converts those to XSP and put in appropriate
  place .*/
  public void SyncXsp(){

    SRTMap srtm = null;
    Map<Object,Object> map = new TreeMap<Object, Object>();
    ArrayList<Object> pages = null;
    NEDSSConstants Constants = new NEDSSConstants();
    NedssUtils nu = new NedssUtils();
    try {

      Object objref = nu.lookupBean(JNDINames.SRT_CACHE_EJB);
      SRTMapHome home = (SRTMapHome) PortableRemoteObject.narrow(objref,
          SRTMapHome.class);
      srtm = home.create();
      pages = srtm.getLDFPageIDs();
     Iterator<Object>  iter = pages.iterator();
      while (iter.hasNext()) {
        LdfPageSetDT dt = (LdfPageSetDT) iter.next();
        String pageId = dt.getLdfPageId();
     //   Collection<Object>  col = getLDFMetaData(pageId);
      //  createXMLDocument(col, dt.getPageUrl());
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }


   }

	public static String getFromXSPPageCache(
		String code,
		HttpSession session) {

		if(!propertyUtil.isLDFXspCacheOn())
			return null;

		ServletContext context = session.getServletContext();
		Hashtable<Object, Object> xspPageCache = null;
		if (context.getAttribute("xSPPageCache") != null) {
			xspPageCache = (Hashtable<Object, Object>) context.getAttribute("xSPPageCache");
		} //xspPageCache can only be null when this cache is first built
		else {
			xspPageCache = new Hashtable<Object, Object>();
			context.setAttribute("xSPPageCache", xspPageCache);
			return null;
		}

		Object extXSP = xspPageCache.get(code);
		return (extXSP == null ? null : extXSP.toString());

	}

	public static void putInXSPPageCache(
		String code,
		String XSP,
		HttpSession session) {
		
		if(!propertyUtil.isLDFXspCacheOn()){
			ServletContext context = session.getServletContext();
			context.setAttribute("xSPPageCache", new Hashtable<Object, Object>());
			return;		
		}
		
		if (XSP == null
			|| XSP.trim().equals("")
			|| code == null
			|| code.trim().equals("")
			|| XSP.equalsIgnoreCase(NEDSSConstants.BLANK_XSPTAG_LDF))
			return;

		ServletContext context = session.getServletContext();
		Hashtable<Object, Object> xspPageCache =
			(Hashtable<Object, Object>) context.getAttribute("xSPPageCache");
		if (xspPageCache == null) {
			xspPageCache = new Hashtable<Object, Object>();
			context.setAttribute("xSPPageCache", xspPageCache);
		}
		xspPageCache.put(code, XSP);
	}

	public static void resetXSPPageCache(
			HttpSession session) {
		
		ServletContext context = session.getServletContext();
		context.setAttribute("xSPPageCache", new Hashtable<Object, Object>());
		return;	
	}

	public static String generateXSPPageCacheKey(
		String busObjNm,
		Long busObjUid,
		DefinedFieldSubformGroup group,
		String condition,
		HttpServletRequest request) {

		StringBuffer key = new StringBuffer();
		if (busObjNm != null) {
			key.append(busObjNm);
		}
		key.append("~");
		if (group != null) {
			if(group.getName() == null){
				// we need to find out whether we are at create pages or 
				// edit/view pages of business object without CDF/Subform
				if(busObjUid!=null && busObjUid.longValue()>0){
					// this is an edit/view 
					key.append(EMPTY_GROUP_TOKEN);
				}
			}
			else{
				key.append(group.getName());
			}
		}
		key.append("~");
		if (condition != null) {
			key.append(condition);
		}

		return key.toString();
	}
}