package gov.cdc.nedss.util;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *  XMLRequestHelper.
 *  Utility functions for XSP files.
 *  @author Ed Jenkins.
 */
public class XMLRequestHelper
{

    /**
     *  Private default constructor.
     */
    private XMLRequestHelper()
    {
    }

    /**
     *  Parses an XML string and returns a table model to be used in XSP.
     *  The return value is an ArrayList<Object> that contains other ArrayList<Object> objects.
     *  The inner ArrayList<Object> objects are collections of strings.
     *  @param pXML a string containing an XML document.
     *  The structure of pXML is like this:
        <table>
            <record>
                <field>value</field>
                <field>value</field>
                <field>value</field>
            </record>
            <record>
                <field>value</field>
                <field>value</field>
                <field>value</field>
            </record>
        </table>
     *  @return a table model.
     *  The table model will be an empty collection if
     *  there are no fields,
     *  there are no records,
     *  or if pXML is null.
     */
    public static ArrayList<Object> getTable(String pXML)
    {
        //  Create return value.
        ArrayList<Object> alTable = new ArrayList<Object> ();
        //  Verify parameters.
        if(pXML == null)
        {
            return alTable;
        }
        if(pXML.trim().equals(""))
        {
            return alTable;
        }
        try
        {
            //  Parse the string.
            StringReader sr = new StringReader(pXML);
            InputSource is = new InputSource(sr);
            DOMParser parser = new DOMParser();
            parser.parse(is);
            //  Get the root element.
            Document doc = parser.getDocument();
            Element root = doc.getDocumentElement();
            //  Loop through all of the <record> elements.
            NodeList nlRecords = root.getElementsByTagName("record");
            int intRecords = nlRecords.getLength();
            for(int intRecord=0; intRecord<intRecords; intRecord++)
            {
                //  Loop through all of the <field> elements.
                ArrayList<Object> alRecord = new ArrayList<Object> ();
                Element eRecord = (Element)nlRecords.item(intRecord);
                NodeList nlFields = eRecord.getElementsByTagName("field");
                int intFields = nlFields.getLength();
                for(int intField=0; intField<intFields; intField++)
                {
                    //  Get the field's value.
                    Element eField = (Element)nlFields.item(intField);
                    Node n = eField.getFirstChild();
                    String strField = null;
                    if(n == null)
                    {
                        strField = "";
                    }
                    else
                    {
                        strField = n.getNodeValue();
                        strField = urlDecode(strField);
                    }
                    //  Add the field to the record.
                    alRecord.add(strField);
                }
                //  Add the record to the table.
                alTable.add(alRecord);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //  Return the output.
        return alTable;
    }

    /**
     *  Copies ArrayList<Object> data into a JavaScript String.
     *  Assumes that pAL is a collection of ArrayLists, which are collections of Strings.
     *  Assumes that there are 0 or more records and each record has 1 or more fields.
     *  Assumes that a variable by the name of pName has already been declared in the JavaScript.
     *  @param pAL the ArrayList<Object> to copy.
     *  @param pName the name of the array in the JavaScript.
     *  @return the JavaScript equivalient of the ArrayList.
     */
    public static String xmlEncodeArrayListToJavaScript(ArrayList<Object>  pAL, String pName)
    {
        //  Verify parameters.
        if(pAL == null)
        {
            return "";
        }
        if(pName == null)
        {
            return "";
        }
        //  Create temp variables.
        int intRecord = 0;
        int intRecords = 0;
        int intField = 0;
        int intFields = 0;
        StringBuffer js = new StringBuffer();
        //  Begin the outer array.
        js.append(pName + " = [");
        //  Loop through the records.
        intRecords = pAL.size();
        for(intRecord=0; intRecord<intRecords; intRecord++)
        {
            //  Get a record.
            ArrayList<Object> al = (ArrayList<Object> )pAL.get(intRecord);
            //  Begin the inner array.
            js.append("[");
            //  Loop through the fields.
            intFields = al.size();
            for(intField=0; intField<intFields; intField++)
            {
                //  Quote, value, and quote.
                js.append("\"");
                js.append(XMLRequestHelper.xmlEncodeToJavaScript((String)al.get(intField)));
                js.append("\"");
                //  Separate each element with a comma.
                if(intField < (intFields-1))
                {
                    js.append(",");
                }
            }
            //  Close the inner array.
            js.append("]");
            //  Separate each element with a comma.
            if(intRecord < (intRecords-1))
            {
                //js.append(",");
                 js.append(",\n");
            }
        }
        //  Close the outer array.
        js.append("];");
        //  Convert to a string.
        String s = js.toString();
        //  Return the result.
        return s;
    }

    /**
     *  Gets the constants defined by a class or interface
     *  and puts them into a string for use in JavaScript.
     *  Only String and primitive types are used.
     *  Only public static final fields are used.
     *  @param pClass the class or interface to read.
     *  @return a string containing JavaScript commands
     *  that redefine the constants or an empty string
     *  if pClass is null.
     */
    public static String getConstants(Class<?> pClass)
    {
        //  Create return value.
        String s = "";
        //  Allocate output buffer.
        StringBuffer sb = new StringBuffer();
        //  Verify parameters.
        if(pClass == null)
        {
            return s;
        }
        //  Get all fields declared in the specified class or interface.
        Field[] fields = pClass.getDeclaredFields();
        //  Allocate temp storage.
        Field f = null;
        Class<?> c = null;
        String strClassName = null;
        String strValue = null;
        int m = 0;
        //  Loop through all fields.
        int x = 0;
        int y = fields.length;
        for(x=0; x<y; x++)
        {
            f = fields[x];
            c = f.getType();
            //  If the field is an interface, do not process it.
            if(c.isInterface())
            {
                continue;
            }
            //  Do not process arrays.
            if(c.isArray())
            {
                continue;
            }
            //  Only process fields that are public, static, and final.
            m = f.getModifiers();
            if(!Modifier.isPublic(m))
            {
                continue;
            }
            if(!Modifier.isStatic(m))
            {
                continue;
            }
            if(!Modifier.isFinal(m))
            {
                continue;
            }
            //  Process strings.
            strClassName = c.getName();
            if(strClassName.equals("java.lang.String"))
            {
                try
                {
                    strValue = xmlEncodeToJavaScript((String)f.get(null));
                    sb.append("var " + f.getName() + " = \"" + strValue + "\"; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            //  Do not process anything else unless it is a primitive type.
            if(!c.isPrimitive())
            {
                continue;
            }
            if(c == Boolean.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getBoolean(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            if(c == Byte.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getByte(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            if(c == Character.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getChar(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            if(c == Double.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getDouble(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            if(c == Float.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getFloat(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            if(c == Integer.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getInt(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            if(c == Long.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getLong(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
            if(c == Short.TYPE)
            {
                try
                {
                    sb.append("var " + f.getName() + " = " + f.getShort(null) + "; ");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                continue;
            }
        }
        //  Return the output.
        s = sb.toString();
        return s;
    }

    /**
     *  Encodes a string that may contain special characters that are reserved in XML.
     *  The JavaScript escape() function does the same job, but it is deprecated.
     *  The JavaScript encodeURI() function replaces escape(), but it does not handle
     *  all of the characters that escape() did.
     *  Only characters that affect XML well-formedness will be encoded.
     *  The percent sign is encoded, too, because it is part of the encoding scheme.
     *  @param pString the string to encode.
     *  @return an encoded version of the string.
     */
    public static String xmlEncode(String pString)
    {
        //  Verify parameters.
        if(pString == null)
        {
            return "";
        }
        //  Create temp variables.
        StringBuffer sb = new StringBuffer();
        char c = '?';
        //  Create variables for looping.
        int x = 0;
        int y = pString.length();
        //  Loop through all of the characters in the string.
        for(x=0; x<y; x++)
        {
            //  Get a character.
            c = pString.charAt(x);
            switch(c)
            {
                //  Translate it if it is a reserved character.
                case 0x22:  sb.append("%22");   break;  //  "   double quote
                case 0x25:  sb.append("%25");   break;  //  %   percent sign
                case 0x26:  sb.append("%26");   break;  //  &   ampersand
                case 0x27:  sb.append("%27");   break;  //  '   single quote
                case 0x3C:  sb.append("%3C");   break;  //  <   left angle bracket
                case 0x3E:  sb.append("%3E");   break;  //  >   right angle bracket
                //  Otherwise, pass it on as-is.
                default:    sb.append(c);       break;  //  all other characters
            }
        }
        //  Return the result.
        String s = sb.toString();
        return s;
    }

    /**
     *  Decodes a string that may contain special characters that are reserved in XML.
     *  The JavaScript unescape() function does the same job, but it is deprecated.
     *  The JavaScript decodeURI() function replaces unescape(), but it does not handle
     *  all of the characters that unescape() did.
     *  Only character references that affect XML well-formedness will be decoded.
     *  The percent sign is decoded, too, because it is part of the encoding scheme.
     *  @param pString the string to decode.
     *  @return the decoded version of the string.
     */
    public static String xmlDecode(String pString)
    {
        //  Verify parameters.
        if(pString == null)
        {
            return "";
        }
        //  Create temp variables.
        StringBuffer sb = new StringBuffer();
        String ss = null;
        char c = '?';
        int i = 0;
        //  Create variables for looping.
        int x = 0;
        int y = pString.length();
        //  Loop through all of the characters in the string.
        for(x=0; x<y;)
        {
            //  Get a character.
            c = pString.charAt(x);
            if(c == 37)
            {
                //  If it is the percent sign,
                //  decode the following two characters at once.
                ss = pString.substring(x+1, x+3);
                try
                {
                    //  Convert them to an integer and append the character code equivalent.
                    i = Integer.parseInt(ss, 16);
                    c = (char)(i & 0xFFFF);
                    sb.append(c);
                    //  Skip the sign and the 2-digit code.
                    x += 3;
                }
                catch(Exception ex)
                {
                    //  If the format is incorrect, stop processing and keep what you have.
                    break;
                }
            }
            else
            {
                //  If it is not the percent sign, then pass it on as-is.
                sb.append(c);
                //  Skip past the character.
                x += 1;
            }
        }
        //  Return the result.
        String s = sb.toString();
        return s;
    }

    /**
     *  Encodes a string that may contain special characters that are reserved in XML.
     *  Encodes into a format suitable for writing into an attribute.
     *  Only characters that affect XML well-formedness will be encoded.
     *  @param pString the string to encode.
     *  @return an encoded version of the string.
     */
    public static String xmlEncodeToXHTML(String pString)
    {
        //  Verify parameters.
        if(pString == null)
        {
            return "";
        }
        //  Create temp variables.
        StringBuffer sb = new StringBuffer();
        char c = '?';
        //  Create variables for looping.
        int x = 0;
        int y = pString.length();
        //  Loop through all of the characters in the string.
        for(x=0; x<y; x++)
        {
            //  Get a character.
            c = pString.charAt(x);
            switch(c)
            {
                //  Translate it if it is a reserved character.
                case 0x22:  sb.append("&quot;");    break;  //  "   double quote
                case 0x26:  sb.append("&amp;");     break;  //  &   ampersand
                case 0x27:  sb.append("&apos;");    break;  //  '   single quote
                case 0x3C:  sb.append("&lt;");      break;  //  <   left angle bracket
                case 0x3E:  sb.append("&gt;");      break;  //  >   right angle bracket
                //  Otherwise, pass it on as-is.
                default:    sb.append(c);           break;  //  all other characters
            }
        }
        //  Return the result.
        String s = sb.toString();
        return s;
    }

    /**
     *  Encodes a string that may contain special characters that are reserved in XML.
     *  Encodes into a format suitable for writing into a JavaScript String.
     *  Only characters that affect XML well-formedness will be encoded.
     *  @param pString the string to encode.
     *  @return an encoded version of the string.
     */
    public static String xmlEncodeToJavaScript(String pString)
    {
        //  Verify parameters.
        if(pString == null)
        {
            return "";
        }
        //  Create temp variables.
        StringBuffer sb = new StringBuffer();
        char c = '?';
        //  Create variables for looping.
        int x = 0;
        int y = pString.length();
        //  Loop through all of the characters in the string.
        for(x=0; x<y; x++)
        {
            //  Get a character.
            c = pString.charAt(x);
            switch(c)
            {
                //  Translate it if it is a reserved character.
                case 0x22:  sb.append("\\" + "u0022");  break;  //  "   double quote
                case 0x26:  sb.append("\\" + "u0026");  break;  //  &   ampersand
                case 0x27:  sb.append("\\" + "u0027");  break;  //  '   single quote
                case 0x3C:  sb.append("\\" + "u003C");  break;  //  <   left angle bracket
                case 0x3E:  sb.append("\\" + "u003E");  break;  //  >   right angle bracket
                case 0x0A:	sb.append("\\" + "u0020");  break;  // 	Replace Carriage Return with a space
                //  Otherwise, pass it on as-is.
                default:    sb.append(c);               break;  //  all other characters
            }
        }
        //  Return the result.
        String s = sb.toString();
        return s;
    }

    /**
     *  Encodes a string for use in a URL.
     *  Similar to java.net.URLEncoder.encode(String)
     *  and XMLRequestHelper.xmlEncode(String)
     *  but this one encodes spaces and question marks too.
     *  @param pString the string to encode.
     *  @return an encoded version of the string.
     */
    public static String urlEncode(String pString)
    {
        //  Verify parameters.
        if(pString == null)
        {
            return "";
        }
        //  Create temp variables.
        StringBuffer sb = new StringBuffer();
        char c = '?';
        //  Create variables for looping.
        int x = 0;
        int y = pString.length();
        //  Loop through all of the characters in the string.
        for(x=0; x<y; x++)
        {
            //  Get a character.
            c = pString.charAt(x);
            switch(c)
            {
                //  Translate it if it is a reserved character.
                case 0x20:  sb.append("%20");   break;  //      space
                case 0x22:  sb.append("%22");   break;  //  "   double quote
                case 0x25:  sb.append("%25");   break;  //  %   percent sign
                case 0x26:  sb.append("%26");   break;  //  &   ampersand
                case 0x27:  sb.append("%27");   break;  //  '   single quote
                case 0x3C:  sb.append("%3C");   break;  //  <   left angle bracket
                case 0x3E:  sb.append("%3E");   break;  //  >   right angle bracket
                case 0x3F:  sb.append("%3F");   break;  //  ?   question mark
                //  Otherwise, pass it on as-is.
                default:    sb.append(c);       break;  //  all other characters
            }
        }
        //  Return the result.
        String s = sb.toString();
        return s;
    }

    /**
     *  Decodes a string from a URL.
     *  Just a thing wrapper for xmlDecode(String).
     *  @param pString the string to decode.
     *  @return the decoded version of the string.
     */
    public static String urlDecode(String pString)
    {
        return xmlDecode(pString);
    }

    /**
     *  Gets the mode.
     *  Normalizes the given string to either "add", "edit", "view", or "print".
     *  @param pMode the mode obtained from a request parameter.
     *  @return the normalized mode.
     *  The default value is "view".
     */
    public static String getMode(String pMode)
    {
        //  Verify parameters.
        if(pMode == null)
        {
            return "view";
        }
        //  Normalize the mode.
        if(pMode.equalsIgnoreCase("add"))
        {
            return "add";
        }
        if(pMode.equalsIgnoreCase("edit"))
        {
            return "edit";
        }
        if(pMode.equalsIgnoreCase("print"))
        {
            return "print";
        }
        //  Return default value.
        return "view";
    }

    /**
     *  Gets the title.
     *  Used for screens that can have add, edit, view, and print modes.
     *  @param pMode the mode obtained from a request parameter.
     *  Note that "add" is converted to "Create".
     *  @param pBase the base string to be appended to the mode.
     *  @return the full title.
     *  The default value is "[null]".
     */
    public static String getTitle(String pMode, String pBase)
    {
        //  Create temp variables.
        String strMode = getMode(pMode);
        //  Create return variable.
        String strTitle = null;
        //  Verify parameters.
        if(pBase == null)
        {
            return "[null]";
        }
        //  Assemble the result.
        if(strMode.equalsIgnoreCase("add"))
        {
            strTitle = "Create " + pBase;
        }
        if(strMode.equalsIgnoreCase("edit"))
        {
            strTitle = "Edit " + pBase;
        }
        if(strMode.equalsIgnoreCase("print"))
        {
            strTitle = "View " + pBase;
        }
        if(strTitle == null)
        {
            strTitle = "View " + pBase;
        }
        //  Return result.
        return strTitle;
    }

    public static String updateDOMTable(String pXML, String value) {
		//  Verify parameters.
		if (pXML == null) {
			return null;
		}
		if (pXML.trim().equals("")) {
			return null;
		}
		StringBuffer values = new StringBuffer();
		try {
			//  Parse the string.
			values.append("<table>");
			StringReader sr = new StringReader(pXML);
			InputSource is = new InputSource(sr);
			DOMParser parser = new DOMParser();
			parser.parse(is);
			//  Get the root element.
			Document doc = parser.getDocument();
			Element root = doc.getDocumentElement();
			//  Loop through all of the <record> elements.
			NodeList nlRecords = root.getElementsByTagName("record");
			int intRecords = nlRecords.getLength();
			for (int intRecord = 0; intRecord < intRecords; intRecord++) {
				//  Loop through all of the <field> elements.
				values.append("<record>");
				Element eRecord = (Element) nlRecords.item(intRecord);
				NodeList nlFields = eRecord.getElementsByTagName("field");
				//for(int intField=0; intField<intFields; intField++)
				//  {
				//  Get the field's value.
				values.append("<field>");
				Element eField = (Element) nlFields.item(0);
				Node n = eField.getFirstChild();
				String strField = n.getNodeValue();
				values.append(strField);
				values.append("</field>");
				values.append("<field>");
				Element eField1 = (Element) nlFields.item(1);
				Node n1 = eField1.getFirstChild();
				String strField1 = n1.getNodeValue();
				values.append(strField1);
				values.append("</field>");
				if (strField.equalsIgnoreCase(value)) {
					values.append("<field>");
					values.append("true");
					values.append("</field>");
				} else {
					values.append("<field>");
					values.append("false");
					values.append("</field>");
				}
				values.append("</record>");
			}
			values.append("</table>");
			// }

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//  Return the output.
		return values.toString();
	}


}
