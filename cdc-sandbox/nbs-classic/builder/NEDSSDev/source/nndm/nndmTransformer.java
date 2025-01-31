/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright © 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *-------------------------------------------------------------------------------------------------------
 */
package nndm;

import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;


/**
 * This class exposes methods to perform XSL transformations.
 */
public class nndmTransformer
{
   /**
    * Denotes the IO buffer size to use - value is 8 KB.
    */
   private static final int BUFFER_SIZE = 8192;

   /**
    * Internal flag to determine if XML validation is turned on.
    * @see nndm.nndmConfig#XML_VALIDATION_PROPERTY_NAME
    */
   private boolean isXmlValidated = nndmConfig.DEFAULT_XML_VALIDATION;


   /**
    * Constructs this nndmTransformer object.
    */
   public nndmTransformer()
	{
		super();
      String xmlValidationValue = nndmConfig.getNndmConfigurations(nndmConfig.XML_VALIDATION_PROPERTY_NAME);
      if (!(xmlValidationValue.trim().length() == 0)){
         isXmlValidated = new Boolean(xmlValidationValue).booleanValue();
      }
	}


    /** This performs a simple XSL transformations on the XML that is passed in the
      * first parameter. The second parameter is the URL to the stylesheet to transform.
      * Exceptions are trapped by the caller.
      * <p>
      * Note: Wether or not the xml will be validated against a schema depends on the
      * configuration value the nndmConfig.XML_VALIDATION_PROPERTY_NAME property.
      * </p>
      *  @param xml String that contains the xml document to be transformed.
      *  @param xsl String that contains the URL for the stylesheet to use for the transformation
      *  @return String that contains the transformed XML
      *  @exception Exception If error while parsing the xml, instantiating a transformer, or XSLT's errors during tranformation.
      *
      * @see nndm.nndmConfig#XML_VALIDATION_PROPERTY_NAME
      * @see #transform(String xml, String xsl, boolean isValidating)
     */
	public final String transform(String xml, String xsl) throws Exception
	{
      return transform(xml, xsl, isXmlValidated);
   }

    /** This performs a simple XSL transformations on the XML that is passed in the
      * first parameter. The second parameter is the URL to the stylesheet to transform.
      * Exceptions are trapped by the caller.
      *  @param xml String that contains the xml document to be transformed.
      *  @param xsl String that contains the URL for the stylesheet to use for the transformation
      *  @param isValidating If <code> true </code> then validate the XML content during parse, otherwise
      *  perform a non-validating transformation.   Validation implies that the XML string in
      *  the first argument will be parsed and compared to it defining schema following the
      *  implementation of the <a href="http://www.w3.org/TR/xmlschema-1/">W3C XML Schema Language </a>.
      *  If the XML is to be validated, then it is assumed that it uses XML Schema grammars and it must either
      *  <ul>
      *  <li> specify the location of the grammars it uses by using an <code> xsi:schemaLocation </code> attribute
      *     if they use namespaces</li>
      *  <li> otherwise an <code> xsi:noNamespaceSchemaLocation </code> attribute
      * </ul>
      *  in the <code> root </code> element of the XML .
      *  @return String that contains the transformed XML
      *  @exception Exception If error while parsing the xml, instantiating a transformer, or XSLT's errors during tranformation.
     */
   synchronized protected final String transform(String xml, String xsl, boolean isValidating) throws Exception
	{
      //sanity check
      if (((xml == null) || (xml.length() == 0)) || ((xsl == null) || (xsl.length() == 0))){
         throw new Exception("Unable to perform transform(String xml, String xsl).  The given xml or xsl is null or empty.");
      }
      Source xmlSource = null;
      String transformationResult = "";
      InputStream xmlDataStream = null;
      InputStream xslStream = null;
      StringWriter resultWriter = null;
      nndmTransformerHandler handler = null;
      try{
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         // set up anomyous class using our own URI resolver class.  Xalan will call this
         // class when needing to resolve relative URL's or URI's in stylsheets.
         transformerFactory.setURIResolver(new URIResolver() {
            public Source resolve(String href, String base)
               throws TransformerException{
                  Source resolvedSource = null;
                  InputStream in = null;
                  try{
                     in = nndmTransformer.getSourceAsInputStream(href);
                     resolvedSource = new StreamSource(in);
                  } catch (IOException ioe){
                     //have to catch for possible IOExceptions from resolving the URI
                     // and have to throw it as a TransformerException.
                     throw new TransformerException("Error getting source stream: " + ioe);
                  } finally {
                     if(resolvedSource == null){
                        //attempt to get stream source from the href and given base.
                        resolvedSource = new StreamSource(base + href);
                     }
                  }
                  return resolvedSource;
            }
         });
         xmlDataStream = getSourceAsInputStream(xml);
         xslStream = getSourceAsInputStream(xsl);
         if (isValidating){
            SAXParserFactory parserfactory= SAXParserFactory.newInstance();
            parserfactory.setNamespaceAware(true);
            // Turn on validation.
            parserfactory.setValidating(true);
            // Schema validation feature id (http://apache.org/xml/features/validation/schema).
            parserfactory.setFeature("http://apache.org/xml/features/validation/schema",true);
            // Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking).
            parserfactory.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
            // Get an XMLReader for reading the schema.
            XMLReader reader = parserfactory.newSAXParser().getXMLReader();
            // Instantiate an error handler (see the nndmTransformerHandler inner class below) that will report any
            // errors or warnings that occur as the XMLReader is parsing the XML input.
            handler = new nndmTransformerHandler();
            reader.setErrorHandler(handler);
            xmlSource = new SAXSource(reader,new InputSource(xmlDataStream));
         } else {
            xmlSource = new StreamSource(xmlDataStream);
         }
         // setup the stylesheet
         Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslStream));
         // Transform the source XML to a string
         resultWriter = new StringWriter(BUFFER_SIZE);
         transformer.transform( xmlSource,new StreamResult(resultWriter));
         transformationResult = resultWriter.toString();
      } catch (Exception e){
         throw e;
      } finally {
         if (xmlDataStream != null){
            try{xmlDataStream.close();} catch (IOException ioe){}
         }
         if (xslStream != null){
            try{xslStream.close();} catch (IOException ioe){}
         }
         if (resultWriter != null){
            try{resultWriter.close();} catch (IOException ioe){}
         }
      }
      return transformationResult;
	}

   /**
    * Obtain an input stream from a given data source.
    * @param source the source of the data in the form of
    * <ul>
    * <li>File Name , Examples: sample\test\my.txt ; Relative
    * file name to the XSLT Stylesheet base path entry in the Config.properties.</li>
    * <li>A string containing the form of an XML Document or XML fragment , Example:
    * <pre>
    *    &lt;?xml  version="1.0"&gt;
    *    &lt;root_element&gt;
    *        &lt;child_element/&gt;
    *    &lt;/root_element&gt;
    * </pre>
    * </li>
    * </ul>
    * @return InputStream of the data source encoded by the Default Encoding,
    * otherwise <code> null </code> if IOException.
    *
    * @exception IOException if unable to create an encoded input stream for the given <i>source</i> or
    * the method was passed a <i>source</i> that was <code> null </code> or empty.
    * @see nndm.nndmConfig#STYLES_PROPERTY_NAME
    */
   protected static InputStream getSourceAsInputStream(String source) throws IOException {
      if ((source == null) || (source.length() == 0)) {
         throw new IOException(" *** getSourceAsInputStream(String source) cannot process an empty 'source' parameter. ");
      }
      InputStream dataStream = null;
      if (source.trim().startsWith("<")) {
         //assume a string representing an XML document
         // using Eight-bit Unicode Transformation Format for special characters. Need to revisit this.
         dataStream = new BufferedInputStream(new ByteArrayInputStream(source.getBytes("utf-8")),BUFFER_SIZE);
      } else {
         //assume local file
         dataStream = new BufferedInputStream(new FileInputStream(nndmConfig.getNndmConfigurations(nndmConfig.STYLES_PROPERTY_NAME)+source),BUFFER_SIZE);
      }
      return dataStream;
   }

}
/* END CLASS DEFINITION nndmTransformer */


/**
 *  Catch any errors or warnings from the XMLReader that gets created if Schema Validation is turned on.
 */
class nndmTransformerHandler extends DefaultHandler
{
   /**
    * Contains the details of the attempted validation.
    */
   private StringBuffer result = new StringBuffer("");

   /**
    * Gets the location of the warning/error as a string.
    * Returns a string of the location of the problem.
    */
   private String getLocationString(SAXParseException ex) {
      StringBuffer sb = new StringBuffer();
      String systemId = ex.getSystemId();
      if (systemId != null) {
         int index = systemId.lastIndexOf('/');
         if (index != -1)
             systemId = systemId.substring(index + 1);
         sb.append(systemId);
      }
      sb.append(" Line:");
      sb.append(ex.getLineNumber());
      sb.append(" Column:");
      sb.append(ex.getColumnNumber());

      return sb.toString();
   }

   /**
    * Sets the details of the result of the attempted validation.
    * @param resultType description of type of message.  Examples, Warning, Error, etc.
    * @param ex the exception that occurred while validating.
    */
   private void setResult(String resultType , SAXParseException ex){
      result.append("[ ");
      result.append(resultType);
      result.append(" ] ");
      result.append(getLocationString(ex));
      result.append(" ; ");
      result.append(ex.getMessage());
      result.append(" ");
      result.append(System.getProperty("line.separator"));
   }

   /**
    * Gets the result of the validation.
    * <p>
    * This will contain one of the following:
    * <ul>
    * <li>WARNING message. </li>
    * <li>ERROR message. </li>
    * <li>FATAL ERROR message. </li>
    * </ul>
    * </p>
    * @return String containing the results of the validated
    * parse,  otherwise <code> null </code> or empty ("") implies
    * that the XML validated <i> successfully </i> against its schema definition.
    */
   public String getResult(){
      return this.result.toString().trim();
   }

   /**
    * Logs the details of the parser's <i> Warning </i> while validating the xml document.
    * @param spe the SAX Parser Exception containing the warning details.
    */
   public void warning (SAXParseException spe) throws SAXException
   {
      this.setResult("Schema Validation WARNING",spe);
      //System.out.println("WARNING: " + spe.getMessage());
      throw new SAXException(this.getResult());
   }

   /**
    * Logs the details of the parser's <i> Error </i> while validating the xml document.
    * @param spe the SAX Parser Exception containing the error details.
    */
   public void error (SAXParseException spe) throws SAXException
   {
      this.setResult("Schema Validation ERROR",spe);
      //System.out.println("ERROR: " + spe.getMessage());
      throw new SAXException(this.getResult());
   }

   /**
    * Logs the details of the parser's <i> Fatal Error </i> while validating the xml document.
    * @param spe the SAX Parser Exception containing the fatal error details.
    */
   public void fatalError(SAXParseException spe) throws SAXException {
      this.setResult("Schema Validation FATAL ERROR",spe);
      throw new SAXException(this.getResult());
   }

}

