package gov.cdc.nedss.ldf.importer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.w3c.tidy.DOMAttrMapImpl;
import org.w3c.tidy.DOMDocumentImpl;
import org.w3c.tidy.DOMNodeImpl;
import org.w3c.tidy.DOMNodeListImpl;
import org.w3c.tidy.Tidy;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

/**
 *
 * <p>Title: SubformDataParser</p>
 * <p>Description: Uses JTidy and parses xhtml to java objects </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC </p>
 * @author nmallela
 * @version 1.0
 */
public class SubformDataParser {

	//For logging
	static final LogUtils logger =
		new LogUtils(SubformDataParser.class.getName());
	/**
	 * Class variables
	 */
	final static String[] elementsToBeExtracted =
		{
			ImportConstants.INPUT,
			ImportConstants.TEXTAREA,
			ImportConstants.SELECT,
			ImportConstants.SPAN };
	ArrayList<Object> fields = new ArrayList<Object> ();
	String formName;
	String formVersion;

	/**
	 * Default constructor.
	 * @return
	 */
	public Collection<Object>  extractFields(InputStream in) throws NEDSSAppException {

		//Check input stream
		if (in == null) {
			return null;
		}

		//Create instance of JTidy
		Tidy tidy = new Tidy();
		tidy.setXmlTags(true);

		//Set error writer
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		tidy.setErrout(printWriter);
		processDOM((DOMNodeImpl)tidy.parseDOM(in, null));

		//Check for errors, if errors throw exception
		String errors = stringWriter.toString();

		if (tidy.getParseErrors()>0) {
			logger.error(ImportConstants.HTML_PARSE_ERROR_TEXT + errors);
			throw new NEDSSAppException(
				ImportConstants.HTML_PARSE_ERROR_TEXT + errors);
		}
		return fields;
	}

	/**
	 *
	 * @param node
	 */
	private void processDOM(DOMNodeImpl node) {

		/**
		 * Check null for node.
		 */
		if (node == null) {
			return;
		}

		//Get node ttype
		int type = node.getNodeType();
		switch (type) {

			case DOMNodeImpl.DOCUMENT_NODE :
				processDOM((DOMNodeImpl)((DOMDocumentImpl) node).getDocumentElement());
				break;

			case DOMNodeImpl.ELEMENT_NODE :

				String nodeName = node.getNodeName();

				if (nodeName != null && isElementToBeExtracted(nodeName)) {
					//Create instance of SubformElement class
					SubformElement fieldInfo = new SubformElement();
					//Get element attributes
					DOMAttrMapImpl attrs = (DOMAttrMapImpl)node.getAttributes();
					String elementType = "";

					for (int i = 0; i < attrs.getLength(); i++) {

						String attrName = attrs.item(i).getNodeName();
						//set id
						if (attrName != null
							&& (ImportConstants.ID).equalsIgnoreCase(attrName)) {
							fieldInfo.setId(attrs.item(i).getNodeValue());
						}
						//set name
						if (attrName != null
							&& (ImportConstants.NAME).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setName(attrs.item(i).getNodeValue());
						}
						//set data type
						if (attrName != null
							&& (ImportConstants.DATATYPE).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setDataType(attrs.item(i).getNodeValue());
						}
						//set object id
						if (attrName != null
							&& (ImportConstants.OBJECTID).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setObjectId(attrs.item(i).getNodeValue());
						}
						//set comment
						if (attrName != null
							&& (ImportConstants.COMMENT).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setComment(attrs.item(i).getNodeValue());
						}
						//set nnd indicator
						if (attrName != null
							&& (ImportConstants.NND_IND).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setNndInd(attrs.item(i).getNodeValue());
						}
						//set label text
						if (attrName != null
							&& (ImportConstants.LABELTEXT).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setLabelText(
								attrs.item(i).getNodeValue());
						}
						//set codesets
						if (attrName != null
							&& (ImportConstants.CODESETNAME).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setCodeSetName(
								attrs.item(i).getNodeValue());
						}

						//set source
						if (attrName != null
							&& (ImportConstants.CLASS_CD).equalsIgnoreCase(
								attrName)) {
							fieldInfo.setSource(attrs.item(i).getNodeValue());
						}

						//set CDCNATIONALID
						if (attrName != null
							&& (
								ImportConstants
									.NATIONALIDENTIFIER)
									.equalsIgnoreCase(
								attrName)) {
							fieldInfo.setCdcnationalId(
								attrs.item(i).getNodeValue());
						}
						//Collect type information
						if (attrName != null
							&& (ImportConstants.TYPE).equalsIgnoreCase(
								attrName)) {
							elementType = attrs.item(i).getNodeValue();
						}

						//Collect form name information
						if (attrName != null
							&& (ImportConstants.FORMNAME).equalsIgnoreCase(
								attrName)
							&& (ImportConstants.CDCFORM).equalsIgnoreCase(
								nodeName)) {
							this.setFormName(attrs.item(i).getNodeValue());
						}

						//Collect form name information
						if (attrName != null
							&& (ImportConstants.FORMVERSION).equalsIgnoreCase(
								attrName)
							&& (ImportConstants.CDCFORM).equalsIgnoreCase(
								nodeName)) {
							this.setFormVersion(attrs.item(i).getNodeValue());
						}

					} //end for loop
					//add to list
					/**
					     * For input type radio button, on form id may be diffrent but name attribute is
					 * kept same. The data will be collected on name attribute, hence name and attribute
					     * must be same so that there should not be duplicate field in database.
					 */
					if (fieldInfo != null
						&& (ImportConstants.RADIO).equalsIgnoreCase(
							elementType)) {
						fieldInfo.setId(fieldInfo.getName());
					}

					//Check for duplicate and unwanted input type
					if (addField(fieldInfo, elementType)) {
						fields.add(fieldInfo);
					}

				} //
				DOMNodeListImpl children = (DOMNodeListImpl) node.getChildNodes();
				if (children != null) {
					int len = children.getLength();
					for (int i = 0; i < len; i++) {
						processDOM((DOMNodeImpl)children.item(i));
					}
				}
				break;
		}
	}

	/**
	 * Determins if element is to be extracted.
	 * @param elementName
	 * @return true if element name matches list of to exteacted
	 * element list.
	 */

	private boolean isElementToBeExtracted(String elementName) {
		if (elementsToBeExtracted == null
			|| elementName == null
			|| elementName.length() <= 0
			|| elementsToBeExtracted.length <= 0) {
			return false;
		}

		for (int i = 0; i < elementsToBeExtracted.length; i++) {
			if (elementsToBeExtracted[i].equalsIgnoreCase(elementName)) {
				return true;
			}
		}

		return false;
	}

	private boolean addField(SubformElement fieldInfo, String elementType) {

		if ((ImportConstants.BUTTON).equalsIgnoreCase(elementType)
			|| (ImportConstants.SUBMIT).equalsIgnoreCase(elementType)) {
			return false;
		}

		if (fieldInfo.getId() == null && fieldInfo.getName() == null) {
			return false;
		}
		//check for duplicate id
		Iterator iterator = fields.iterator();
		while (iterator.hasNext()) {
			SubformElement field = (SubformElement) iterator.next();
			if (field.getId() != null
				&& (field.getId().equalsIgnoreCase(fieldInfo.getId()))) {
				return false;
			}
		}

		return true;
	}

	public static void main(String args[]) throws Exception {
		SubformDataParser parser = new SubformDataParser();
		FileInputStream in =
			new FileInputStream(
				PropertyUtil.getInstance().getProperty(
					ImportConstants.SF_XHTML_DIRECTORY_KEY,
					ImportConstants.DEFAULT_SF_XHTML_DIRECTORY)
					+ "SampleXHMLForm1.xml");
		Collection<Object>  fields = parser.extractFields(in);
		Iterator iterator = fields.iterator();
		while (iterator.hasNext()) {
			SubformElement field = (SubformElement) iterator.next();
			System.out.println(
				"Field info:"
					+ field.getId()
					+ "/"
					+ field.getName()
					+ "/"
					+ field.getDataType()
					+ "/"
					+ field.getLabelText()
					+ "/"
					+ field.getCdcnationalId()
					+ "/"
					+ field.getSource()
					+ "/"
					+ field.getCodeSetName());
		}
		//Form info
		System.out.println(
			"Form:" + parser.getFormName() + "//" + parser.getFormVersion());

	}

	/**
	 * @return
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * @return
	 */
	public String getFormVersion() {
		return formVersion;
	}

	/**
	 * @param string
	 */
	public void setFormName(String string) {
		formName = string;
	}

	/**
	 * @param string
	 */
	public void setFormVersion(String string) {
		formVersion = string;
	}

}
