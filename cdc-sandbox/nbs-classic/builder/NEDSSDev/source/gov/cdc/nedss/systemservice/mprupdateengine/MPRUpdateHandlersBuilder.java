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
import java.util.Map;
import java.util.HashMap;
import gov.cdc.nedss.util.LogUtils;

/**
 * <p>Title: MPRUpdateHandlersBuilder</p>
 * <p>Description: This is the helper class that parses the configuration file
 * for MPR update handlers.  It builds update handler objects for the MPR
 * update handler factory.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender
 * @version 1.0
 */
class MPRUpdateHandlersBuilder extends DefaultHandler {

        static final LogUtils logger = new LogUtils(MPRUpdateHandlersBuilder.class.getName());

	private Map<Object,Object> updateHandlers = null;

	// working variables
	String startElementName = null;
	MPRUpdateHandler updateHandler = null;
	String updateHandlerID = null;
	ActionComposite actions = null;
	AbstractActionImpl action = null;

	MPRUpdateHandlersBuilder(String filename) {

		SAXParserFactory f = SAXParserFactory.newInstance();
		SAXParser p = null;
		DefaultHandler h = this;
		updateHandlers = new HashMap<Object,Object>();

		try {
			p = f.newSAXParser();
			p.parse(new File(filename), h);
		} catch (Throwable x) {
			updateHandlers = null;
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
		if(startElementName.equals(MPRUpdateEngineConstants.HANDLER_ELEMENT)) {
			// create a new update handler
			updateHandler = new MPRUpdateHandlerDefaultImpl();
		}
		if(startElementName.equals(MPRUpdateEngineConstants.ACTIONS_ELEMENT)) {
			// create a new action composite
			actions = new ActionCompositeImpl();
		}
		emit("START ELEMENT " + startElementName);

		// go through attribute
		if (a != null) {
			for (int j = 0; j < a.getLength(); j++) {
				String attrName = a.getLocalName(j) != "" ? a.getLocalName(j) : a.getQName(j);

				// deal with attributes
				if(startElementName.equals(MPRUpdateEngineConstants.HANDLER_ELEMENT)) {
					if(attrName.equals(MPRUpdateEngineConstants.HANDLER_ID_ATTRIBUTE)){
						updateHandlerID = a.getValue(j);
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
			// read the value
			if(startElementName.equals(MPRUpdateEngineConstants.ACTION_CLASS_ELEMENT)) {
				// create a new action object
				String classname = new String(buf, offset, length);
				action = (AbstractActionImpl)Class.forName(classname).newInstance();
			}
			if(startElementName.equals(MPRUpdateEngineConstants.ACTION_INIT_ID_ELEMENT)) {
				// init the new action object
				String initID = new String(buf, offset, length);
				action.init(MPRUpdateEnigneUtil.getActionInitFileName(initID));
			}

			emit("CHARS(" + length + ")|" + new String(buf, offset, length) + "|");
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}

	public void endElement(String ns, String lName, String qName)
		throws SAXException {
		String endElementName = lName != "" ? lName : qName;
		if(endElementName.equals(MPRUpdateEngineConstants.HANDLER_ELEMENT)) {
			// init update handler and add the handler to the handlers collection
			updateHandler.init(actions);
			updateHandlers.put(updateHandlerID, updateHandler);
		}
		if(endElementName.equals(MPRUpdateEngineConstants.ACTION_ELEMENT)) {
			// add action to actions;
			actions.add(action);
		}
		emit("END   ELEMENT " + endElementName);
	}

	private void emit(String s) throws SAXException {
//		System.out.println(s);
	}

	/**
	 * Returns the update handlers available to the MPR update handler factory.
	 * @return Map
	 */
	public Map<Object,Object> getUpdateHandlers() {
		return updateHandlers;
	}

	public static void main(String[] args) {
		new MPRUpdateHandlersBuilder(MPRUpdateEngineConstants.HANDLERS_CONFIG_FILENAME);
	}
}
