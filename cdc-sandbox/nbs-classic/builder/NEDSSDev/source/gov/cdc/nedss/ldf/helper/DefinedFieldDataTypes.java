/*
 * Created on Feb 5, 2004
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

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedFieldDataTypes {
	//For logging
	static final LogUtils logger =
		new LogUtils(DefinedFieldDataTypes.class.getName());
	private Map<Object,Object> dataTypes = new HashMap<Object,Object>();

	public DefinedFieldDataTypes() throws NEDSSAppException {
		NedssUtils nedssUtils = new NedssUtils();
		try {
			Object obj = nedssUtils.lookupBean(JNDINames.SRT_CACHE_EJB);
			SRTMapHome home =
				(SRTMapHome) PortableRemoteObject.narrow(obj, SRTMapHome.class);
			SRTMap srtMap = home.create();
			dataTypes = srtMap.getLDFValues(NEDSSConstants.LDF_DATA_TYPE);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}

	}

	/**
	 * @return
	 */
	public Map<Object,Object> getDataTypes() {
		return dataTypes;
	}

	public String getDescription(String key) {
		return (String) dataTypes.get(key);
	}

	/**
	 * @param data
	 * @return
	 */
	public List<Object> validate(ImportData data) {

		if (data instanceof DefinedFieldImportData) {
			return validate(((DefinedFieldImportData) data).getDataType());
		} else if (data instanceof SubformElement) {
			// for now only support CV and ST
			List<Object> retVal = new ArrayList<Object> ();
			retVal.addAll(validate(((SubformElement) data).getDataType()));
			if (!((SubformElement) data)
				.getDataType()
				.equalsIgnoreCase(ImportConstants.DATA_TYPE_CODED_VALUE_CODE)
				&& !((SubformElement) data).getDataType().equalsIgnoreCase(
					ImportConstants.DATA_TYPE_ST_CODE)) {
				retVal.add(
					new ValidationResult(
						false,
						ImportConstants.INVALID_DF_DATA_TYPE_TEXT
							+ ((SubformElement) data).getDataType()));
			}
			return retVal;
		}
		return new ArrayList<Object> ();
	}

	public List<Object> validate(String type) {
		List<Object> retVal = new ArrayList<Object> ();
		if (type == null || type.trim().length() == 0) {
			retVal.add(
				new ValidationResult(
					false,
					ImportConstants.NULL_DF_DATA_TYPE_TEXT));
		} else if (getDescription(type) == null) {
			retVal.add(
				new ValidationResult(
					false,
					ImportConstants.INVALID_DF_DATA_TYPE_TEXT + type));
		}

		return retVal;
	}
	public StringBuffer toHTML() {
		StringBuffer html = new StringBuffer();
		if (dataTypes != null && dataTypes.size() > 0) {
			html.append("<br><b>Data Types Table</b></br>");
			html.append(
				"<TABLE border=1><TR><TH>Data Type Name<TH>Description</TR>");
			TreeMap<Object, Object> keyMap = new TreeMap<Object, Object>(dataTypes);
			Collection<Object>  keys = keyMap.keySet();
			Iterator<Object> keyIter = keys.iterator();
			while (keyIter.hasNext()) {
				String key = (String) keyIter.next();
				String value = (String) dataTypes.get(key);
				html.append(
					"<TR><TD>" + key + "</TD><TD>" + value + "</TD></TR>");
			}
			html.append("</TABLE>");
		}
		return html;
	}
	public static void main(String[] args) throws Exception {
		DefinedFieldDataTypes dataType = new DefinedFieldDataTypes();
		StringBuffer html = new StringBuffer();
		html.append("<HTML>");
		html.append(dataType.toHTML());
		html.append("</HTML>");

		File file = new File("datatypes.html");
		FileWriter fw = new FileWriter(file);
		PrintWriter out = new PrintWriter(new BufferedWriter(fw));
		out.write(html.toString());
		out.flush();

	}

}
