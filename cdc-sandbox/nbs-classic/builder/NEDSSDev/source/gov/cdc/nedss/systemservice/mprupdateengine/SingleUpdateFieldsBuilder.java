//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\updatehandler\\SingleUpdateFieldsBuilder.java

package gov.cdc.nedss.systemservice.mprupdateengine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import gov.cdc.nedss.systemservice.util.MPRUpdateEngineConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
This is the helper class that parses the configuration file for the MPR
single-value field update action.  It builds a collection of the
SingleUpdateField objects for the action.
 */
class SingleUpdateFieldsBuilder extends DefaultHandler
{
   /**
   working variables
    */
   String startElementName = null;
   private List<Object> updateFields = null;
   SingleUpdateField updateFiled = null;

   SingleUpdateFieldsBuilder(String filename)
   {

		SAXParserFactory f = SAXParserFactory.newInstance();
		SAXParser p = null;
		DefaultHandler h = this;
		updateFields = new ArrayList<Object> ();

		try {
			p = f.newSAXParser();
			p.parse(new File(filename), h);
		} catch (Throwable x) {
			updateFields = null;
			x.printStackTrace();
		}
   }

   public void startElement(String ns, String lName, String qName, Attributes a) throws org.xml.sax.SAXException
   {

		startElementName = lName != "" ? lName : qName;
		if(startElementName.equals(MPRUpdateEngineConstants.MPRSF_FIELD_ELEMENT)) {
			// create a new field accessor
			updateFiled = new SingleUpdateField();
		}
		emit("START ELEMENT " + startElementName);

		// go through attribute
		if (a != null) {
			for (int j = 0; j < a.getLength(); j++) {
				String attrName = a.getLocalName(j) != "" ? a.getLocalName(j) : a.getQName(j);

				if(startElementName.equals(MPRUpdateEngineConstants.MPRSF_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRSF_FIELD_SOURCE_ID_ATTRIBUTE)){
						updateFiled.setSourceID(a.getValue(j));
					}
				}
				if(startElementName.equals(MPRUpdateEngineConstants.MPRSF_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRSF_FIELD_NAME_ATTRIBUTE)){
						updateFiled.setFieldName(a.getValue(j));
                                                //System.out.println("updateField: " + updateFiled.getFieldName());
					}
				}
				if(startElementName.equals(MPRUpdateEngineConstants.MPRSF_AOD_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRSF_AOD_FIELD_NAME_ATTRIBUTE)){
						updateFiled.setAODFieldName(a.getValue(j));
					}
				}
				if(startElementName.equals(MPRUpdateEngineConstants.MPRSF_AOD_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRSF_AOD_FIELD_SOURCE_ID_ATTRIBUTE)){
						updateFiled.setAODSourceID(a.getValue(j));
					}
				}

				emit("ATTR(" + j + "):");
				emit(attrName);
				emit("=\"" + a.getValue(j) + "\"");
			}
		}
   }

   public void characters(char[] buf, int offset, int length) throws org.xml.sax.SAXException
   {

		try {
			emit("CHARS(" + length + ")|" + new String(buf, offset, length) + "|");
		} catch (Exception e) {
			throw new SAXException(e);
		}
   }

   public void endElement(String ns, String lName, String qName) throws org.xml.sax.SAXException
   {
		String endElementName = lName != "" ? lName : qName;
		if(endElementName.equals(MPRUpdateEngineConstants.MPRSF_FIELD_ELEMENT)) {
			// add the field accessors to the list
			updateFields.add(updateFiled);
		}
		emit("END   ELEMENT " + endElementName);
   }

   private void emit(String s) throws org.xml.sax.SAXException
   {
//		System.out.println(s);
   }

   /**
   Returns the collection of SingleUpdateField objects.
    */
   public List<Object> getUpdateFields()
   {
             //System.out.println("in SingleBuilder.init");
     return updateFields;
   }
}
