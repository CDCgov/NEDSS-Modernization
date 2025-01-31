/*
 * Created on Feb 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.helper;

import java.util.*;
import java.io.*;

import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.systemservice.util.ValidationResult;
import gov.cdc.nedss.util.*;
/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedFieldSRTCodeSets {
	//For logging
	static final LogUtils logger =
		new LogUtils(DefinedFieldSRTCodeSets.class.getName());
	private Map<Object,Object> srtCodeSets = new HashMap<Object,Object>();

	public DefinedFieldSRTCodeSets() throws NEDSSAppException {
		NedssUtils nedssUtils = new NedssUtils();
		try {
			Object obj = nedssUtils.lookupBean(JNDINames.SRT_CACHE_EJB);
			SRTMapHome home =
				(SRTMapHome) PortableRemoteObject.narrow(obj, SRTMapHome.class);
			SRTMap srtMap = home.create();
			srtCodeSets = srtMap.getLDFDropdowns();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}

	}

	/**
	 * @return
	 */
	public Map<Object,Object> getSrtCodeSets() {
		return srtCodeSets;
	}

	public String getDescription(String key) {
		return (String) srtCodeSets.get(key);
	}

	/**
	 * @param data
	 * @return
	 */
	public List<Object> validate(ImportData data) {

		if (data instanceof DefinedFieldImportData) {
			return validate(((DefinedFieldImportData) data).getCodeSetName());
		} else if (data instanceof SubformElement) {
			return validate(((SubformElement) data).getCodeSetName());
		}
		return new ArrayList<Object> ();
	}

	public List<Object> validate(String type) {
		List<Object> retVal = new ArrayList<Object> ();
		if (type != null
			&& type.trim().length() != 0
			&& getDescription(type) == null) {
			retVal.add(
				new ValidationResult(
					false,
					ImportConstants.INVALID_DF_CODE_SET_TEXT + type));
		}

		return retVal;
	}
        public StringBuffer toHTML()
       {
         StringBuffer html = new StringBuffer();
         if(srtCodeSets != null && srtCodeSets.size() > 0)
          {
            html.append("<br><b>Code Sets Table</b></br>");
            html.append("<TABLE border=1><TR><TH>Codeset Name<TH>Description</TR>");
            TreeMap<Object,Object> keyMap = new TreeMap<Object, Object>(srtCodeSets);
            Collection<Object>  keys = keyMap.keySet();
           Iterator<Object>  keyIter = keys.iterator();
            while (keyIter.hasNext()) {
              String key = (String) keyIter.next();
              String value = (String) srtCodeSets.get(key);
              html.append("<TR><TD>" + key + "</TD><TD>" + value + "</TD></TR>");
            }
            html.append("</TABLE>");
          }
         return html;
       }
       public static void main(String[] args) throws Exception {
         DefinedFieldSRTCodeSets codeSets = new DefinedFieldSRTCodeSets();
         StringBuffer html = new StringBuffer();
         html.append("<HTML>");
         html.append(codeSets.toHTML());
         html.append("</HTML>");

         File file = new File("codesets.html");
         FileWriter fw = new FileWriter(file);
         PrintWriter out = new PrintWriter(new BufferedWriter(fw));
         out.write(html.toString());
         out.flush();

       }



}
