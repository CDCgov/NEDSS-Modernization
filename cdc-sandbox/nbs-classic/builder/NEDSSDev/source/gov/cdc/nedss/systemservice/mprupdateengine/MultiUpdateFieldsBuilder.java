package gov.cdc.nedss.systemservice.mprupdateengine;

import org.xml.sax.helpers.DefaultHandler;
import gov.cdc.nedss.systemservice.util.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * This is the helper class that parses the configuration file for the MPR
 * multi-value field update action.  It builds a collection of the
 * MultiUpdateField objects for the action.
 */
class MultiUpdateFieldsBuilder extends DefaultHandler {

	private List<Object> updateFields = null;

	// working variables
	String startElementName = null;
	String sourceID;
	String AODFieldName;
	MultiUpdateFieldElement fieldElement= null;
	MultiUpdateField field = null;

	MultiUpdateFieldsBuilder(String filename) {

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

	public void startElement(
		String ns,
		String lName,
		String qName,
		Attributes a)
		throws SAXException {

		startElementName = lName != "" ? lName : qName;
		if(startElementName.equals(MPRUpdateEngineConstants.MPRMF_FIELD_ELEMENT)) {
			field = new MultiUpdateField();
			field.setElements(new ArrayList<Object>());
		}
		if(startElementName.equals(MPRUpdateEngineConstants.MPRMF_SUB_FIELD_ELEMENT)) {
			fieldElement = new MultiUpdateFieldElement();
			field.getElements().add(fieldElement);
		}
		emit("START ELEMENT " + startElementName);

		// go through attribute
		if (a != null) {
			for (int j = 0; j < a.getLength(); j++) {
				String attrName = a.getLocalName(j) != "" ? a.getLocalName(j) : a.getQName(j);

				// deal with attributes
				if(startElementName.equals(MPRUpdateEngineConstants.MPRMF_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRMF_FIELD_SOURCE_ID_ATTRIBUTE)){
						field.setSoruceID(a.getValue(j));
					}
				}
				if(startElementName.equals(MPRUpdateEngineConstants.MPRMF_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRMF_FIELD_COLLECTION_NAME_ATTRIBUTE)){
						field.setCollectionName(a.getValue(j));
					}
				}
				if(startElementName.equals(MPRUpdateEngineConstants.MPRMF_SUB_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRMF_SUB_FIELD_NAME_ATTRIBUTE)){
						fieldElement.setFieldName(a.getValue(j));
					}
				}
				if(startElementName.equals(MPRUpdateEngineConstants.MPRMF_SUB_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRMF_SUB_FIELD_CONTAINER_NAME_ATTRIBUTE)){
						fieldElement.setContainerFieldName(a.getValue(j));
					}
				}
				if(startElementName.equals(MPRUpdateEngineConstants.MPRMF_AOD_FIELD_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.MPRMF_AOD_FIELD_NAME_ATTRIBUTE)){
						field.setAODFieldName(a.getValue(j));
					}
				}

				emit("ATTR(" + j + "):");
				emit(attrName);
				emit("=\"" + a.getValue(j) + "\"");
			}
		}
	}

	public void characters(char[] buf, int offset, int length)
		throws SAXException {

		try {
			emit("CHARS(" + length + ")|" + new String(buf, offset, length) + "|");
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}

	public void endElement(String ns, String lName, String qName)
		throws SAXException {
		String endElementName = lName != "" ? lName : qName;
		if(endElementName.equals(MPRUpdateEngineConstants.MPRMF_FIELD_ELEMENT)) {
			updateFields.add(field);
		}
		emit("END   ELEMENT " + endElementName);
	}

	private void emit(String s) throws SAXException {
//		System.out.println(s);
	}

	/**
	Returns the collection of MultiUpdateField objects.
	 */
	public List<Object> getUpdateFields() {
		return updateFields;
	}

}
