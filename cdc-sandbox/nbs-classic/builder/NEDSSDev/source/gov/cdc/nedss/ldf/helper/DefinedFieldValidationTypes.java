/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.helper;

import gov.cdc.nedss.util.LogUtils;

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
public class DefinedFieldValidationTypes {

	//For logging
	static final LogUtils logger =
		new LogUtils(DefinedFieldValidationTypes.class.getName());
	private Map<Object,Object> validationTypes = new HashMap<Object,Object>();

	public DefinedFieldValidationTypes() throws NEDSSAppException {
		NedssUtils nedssUtils = new NedssUtils();
		try {
			Object obj = nedssUtils.lookupBean(JNDINames.SRT_CACHE_EJB);
			SRTMapHome home =
				(SRTMapHome) PortableRemoteObject.narrow(obj, SRTMapHome.class);
			SRTMap srtMap = home.create();
			validationTypes =
				srtMap.getLDFValues(NEDSSConstants.LDF_VALIDATION_TYPE);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}

	}

	/**
	 * @return
	 */
	public Map<Object,Object> getValidationTypes() {
		return validationTypes;
	}

	public String getDescription(String key) {
		return (String) validationTypes.get(key);
	}

	public List<Object> validate(ImportData data) {

		if (data instanceof DefinedFieldImportData) {
			return validate(
				((DefinedFieldImportData) data).getValidationText());
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
					ImportConstants.INVALID_DF_VALIDATION_TYPE_TEXT + type));
		}

		return retVal;
	}
        public StringBuffer toHTML()
       {
         StringBuffer html = new StringBuffer();
         if(validationTypes != null && validationTypes.size() > 0)
          {
            html.append("<br><b>Validation Types Table</b></br>");
            html.append(
                "<TABLE border=1><TR><TH>Validation Type Name<TH>Description</TR>");
            TreeMap<Object,Object> keyMap = new TreeMap<Object,Object>(validationTypes);
            Collection<Object>  keys = keyMap.keySet();
           Iterator<Object>  keyIter = keys.iterator();
            while (keyIter.hasNext()) {
              String key = (String) keyIter.next();
              String value = (String) validationTypes.get(key);
              html.append("<TR><TD>" + key + "</TD><TD>" + value + "</TD></TR>");
            }
            html.append("</TABLE>");
          }
         return html;
       }
       public static void main(String[] args) throws Exception {
        DefinedFieldValidationTypes validationType = new DefinedFieldValidationTypes();
        StringBuffer html = new StringBuffer();
        html.append("<HTML>");
        html.append(validationType.toHTML());
        html.append("</HTML>");

        File file = new File("validations.html");
        FileWriter fw = new FileWriter(file);
        PrintWriter out = new PrintWriter(new BufferedWriter(fw));
        out.write(html.toString());
        out.flush();

      }



}
