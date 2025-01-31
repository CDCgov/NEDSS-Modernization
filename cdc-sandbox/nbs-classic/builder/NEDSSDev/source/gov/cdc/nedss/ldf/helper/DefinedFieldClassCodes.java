/*
 * Created on Feb 6, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.helper;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.exception.NEDSSAppException;

import java.util.*;
import java.io.*;

import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.systemservice.util.ValidationResult;
import gov.cdc.nedss.util.*;
import java.util.TreeMap;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedFieldClassCodes {
	//For logging
	static final LogUtils logger =
		new LogUtils(DefinedFieldClassCodes.class.getName());
	private Map<Object,Object> classCodes = new HashMap<Object,Object>();

	public DefinedFieldClassCodes() throws NEDSSAppException {
		NedssUtils nedssUtils = new NedssUtils();
		try {
			Object obj = nedssUtils.lookupBean(JNDINames.SRT_CACHE_EJB);
			SRTMapHome home =
				(SRTMapHome) PortableRemoteObject.narrow(obj, SRTMapHome.class);
			SRTMap srtMap = home.create();
			classCodes = srtMap.getLDFValues(NEDSSConstants.LDF_SOURCE);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}

	}

	/**
	 * @return
	 */
	public Map<Object,Object> getClassCodes() {
		return classCodes;
	}

	public String getDescription(String key) {
		return (String) classCodes.get(key);
	}
	public List<Object> validate(ImportData data) {
		List<Object> retVal = new ArrayList<Object> ();
		if (data instanceof DefinedFieldImportData) {
			retVal.addAll(
				validate(((DefinedFieldImportData) data).getClassCode()));
			if (!((DefinedFieldImportData) data)
				.getClassCode()
				.equalsIgnoreCase(ImportConstants.CLASS_CODE_CDC)) {
				retVal.add(
					new ValidationResult(
						false,
						ImportConstants.INVALID_DF_CLASS_CODE_TEXT
							+ ((DefinedFieldImportData) data).getClassCode()));
			}
		} else if (data instanceof SubformElement) {
			retVal.addAll(validate(((SubformElement) data).getSource()));
			if (((SubformElement) data)
				.getSource()
				.equalsIgnoreCase(ImportConstants.CLASS_CODE_DM)) {
				retVal.add(
					new ValidationResult(
						false,
						ImportConstants.INVALID_DF_CLASS_CODE_TEXT
							+ ((SubformElement) data).getSource()));
			}
		} else if (data instanceof SubformImportData) {
			retVal.addAll(validate(((SubformImportData) data).getClassCode()));
			if (((SubformImportData) data)
				.getClassCode()
				.equalsIgnoreCase(ImportConstants.CLASS_CODE_DM)) {
				retVal.add(
					new ValidationResult(
						false,
						ImportConstants.INVALID_DF_CLASS_CODE_TEXT
							+ ((SubformImportData) data).getClassCode()));
			}
		}
		return retVal;
	}

	public List<Object> validate(String type) {
		List<Object> retVal = new ArrayList<Object> ();
		if (type == null || type.trim().length() == 0) {
			retVal.add(
				new ValidationResult(
					false,
					ImportConstants.NULL_DF_CLASS_CODE_TEXT));
		} else if (getDescription(type) == null) {
			retVal.add(
				new ValidationResult(
					false,
					ImportConstants.INVALID_DF_CLASS_CODE_TEXT + type));
		}

		return retVal;
	}
	public StringBuffer toHTML() {
		StringBuffer html = new StringBuffer();
		if (classCodes != null && classCodes.size() > 0) {
			html.append("<br><b>Class Codes Table</b></br>");
			html.append(
				"<TABLE border=1><TR><TH>CodeSet Name<TH>Description</TD></TR>");

			TreeMap<Object, Object> keyMap = new TreeMap<Object, Object> (classCodes);
			Collection<Object>  keys = keyMap.keySet();
			Iterator<Object>  keyIter = keys.iterator();
			while (keyIter.hasNext()) {
				String key = (String) keyIter.next();
				String value = (String) classCodes.get(key);
				html.append(
					"<TR><TD>" + key + "</TD><TD>" + value + "</TD></TR>");
			}
			html.append("</TABLE>");
		}
		return html;
	}
	public static void main(String[] args) throws Exception {
		DefinedFieldClassCodes classCodes = new DefinedFieldClassCodes();
		StringBuffer html = new StringBuffer();
		html.append("<HTML>");
		html.append(classCodes.toHTML());
		html.append("</HTML>");

		File file = new File("classcodes.html");
		FileWriter fw = new FileWriter(file);
		PrintWriter out = new PrintWriter(new BufferedWriter(fw));
		out.write(html.toString());
		out.flush();

	}

}
