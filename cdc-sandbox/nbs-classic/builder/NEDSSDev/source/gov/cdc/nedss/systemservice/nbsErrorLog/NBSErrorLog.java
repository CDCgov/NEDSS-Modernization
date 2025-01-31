/**
* Name:		NBSErrorLog
* Description:	Java class that builds ArrayList<Object> out of XML file using JAXP , xalan - Xpath for
* building a DOM Document out of xml, parse it and use xpath to filter data.
* and returns it to the calling Action Class
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	nmallela.
* @version	1.0
*/
package gov.cdc.nedss.systemservice.nbsErrorLog;

import gov.cdc.nedss.util.NEDSSConstants;

import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.apache.xpath.*;


/**
 *
 */
public final class NBSErrorLog {

 /**
  * A static Public method that builds an ArrayList<Object> from the passed XML file using JAXP, xalan - Xpath
  * and builds a DOM Document, parses it and uses xpath to filter data.
  * @param String xmlFile
  * @param String pageID
  * @return ArrayList<Object> of errorMessages specific to the page being loaded.
  * @throws ParserConfigurationException, IOException, SAXException, TransformerException
  */

  private static Document document;

  public static void loadErrorMessages(String xmlFile) throws ParserConfigurationException, IOException, SAXException {

      //System.out.println("Method called to initialize XML by NFC !!!!");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      document = dBuilder.parse(xmlFile);
  }

  public static final ArrayList<Object> subErrorMapMaker() throws ParserConfigurationException, IOException, SAXException, TransformerException {

    //DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    //DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    //document = dBuilder.parse(xmlFile);
    //System.out.println("xml file = " + xmlFile);
    //System.out.println("document = " + document);
    ArrayList<Object> errorList = new ArrayList<Object> ();
    ArrayList<Object> list;
    String xpathExpression = "ErrorLog";
    XPathAPI xpathApi = new XPathAPI();

    NodeList nodeList = xpathApi.selectNodeList(document, xpathExpression);

    //System.out.println("nodeList.getLength() = " + nodeList.getLength());
    //System.out.println("nodeList.toString() = "+ nodeList);

    for(int i=0; i < nodeList.getLength(); i++) {

      Node node = nodeList.item(i);

      Node childNode = node.getFirstChild();

      if(childNode == null) continue;

      NodeList childList = node.getChildNodes();

      for(int j=0; j < childList.getLength(); j++) {

        Node childListNode = childList.item(j);

        //System.out.println(" childListNode1type = " + childListNode1.getNodeType());

        if(childListNode.getNodeType() == Node.ELEMENT_NODE) {

          Element errorElement = (Element) childListNode;

          //System.out.println("errorElement1 = " + errorElement1);

          if(errorElement.getNodeName().equals("Error")) {

            //System.out.println("errorElement is Error = " + errorElement.getNodeName());

            String id = errorElement.getAttribute("id");
            String value = errorElement.getAttribute("value");
            //System.out.println("errorElement id1 = " + id1);
            //System.out.println("errorElement value1 = " + value1);

            list = new ArrayList<Object> ();

            list.add(id);
            list.add(value);

            errorList.add(list);
          }//if
        }//if
      }//for
    }//for


    return errorList;
  }


  public static final ArrayList<Object> errorMapMaker(String pageID) throws ParserConfigurationException, IOException, SAXException, TransformerException {

      ArrayList<Object> errorList = new ArrayList<Object> ();
      ArrayList<Object> list;

      String xpathExpression = "ErrorLog/NBSPage[@id = '" + pageID.toUpperCase() + "']/Errors";

      //System.out.println("xpathExpression = " + xpathExpression);

      XPathAPI xpathApi = new XPathAPI();

      NodeList nodeList = xpathApi.selectNodeList(document, xpathExpression);

      //System.out.println("nodeList.getLength() = " + nodeList.getLength());
      //System.out.println("nodeList.toString() = "+ nodeList);

      for(int i=0; i < nodeList.getLength(); i++) {

        Node node = nodeList.item(i);

        Node childNode = node.getFirstChild();

        if(childNode == null) continue;

        NodeList childList = node.getChildNodes();

        for(int j=0; j < childList.getLength(); j++) {

          Node childListNode = childList.item(j);

          //System.out.println(" childListNode1type = " + childListNode1.getNodeType());

          if(childListNode.getNodeType() == Node.ELEMENT_NODE) {

            Element errorElement = (Element) childListNode;

            //System.out.println("errorElement1 = " + errorElement1);

            if(errorElement.getNodeName().equals("Error")) {

              //System.out.println("errorElement is Error = " + errorElement.getNodeName());

              String id = errorElement.getAttribute("id");
              String value = errorElement.getAttribute("value");
              //System.out.println("errorElement id1 = " + id1);
              //System.out.println("errorElement value1 = " + value1);

              list = new ArrayList<Object> ();

              list.add(id);
              list.add(value);

              errorList.add(list);
            }//if
          }//if
        }//for
      }//for
      return errorList;

  }//errorMapMaker



  //for testing
/*
  public static void main(String args[])  {
    try {
      System.out.println("before calling method = ");
      ArrayList<Object> list1 = subErrorMapMaker(NEDSSConstants.NBS_ERRORLOG);
      System.out.println(list1.toString());
    } catch(ParserConfigurationException pce) {
      System.out.println("ParserConfigurationException = " +pce.toString() );
    } catch(IOException ioe) {
      System.out.println("IOException = " +ioe.toString() );
    } catch(SAXException se) {
      System.out.println("SAXException = " +se.toString() );
    } catch(TransformerException te) {
      System.out.println("TransformerException = " +te.toString() );
    }
  }
*/

}//ErrorHandler