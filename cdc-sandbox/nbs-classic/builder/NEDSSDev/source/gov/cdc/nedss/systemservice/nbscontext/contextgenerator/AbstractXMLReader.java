package gov.cdc.nedss.systemservice.nbscontext.contextgenerator;

import java.io.IOException;

import java.util.*;

import org.xml.sax.*;


/**
 * An abstract class that implements the SAX2 XMLReader interface. The
 * intent of this class is to make it easy for subclasses to act as
 * SAX2 XMLReader implementations. This makes it possible, for example, for
 * them to emit SAX2 events that can be fed into an XSLT processor for
 * transformation.
 *
 * @author NEDSS Development Team
 */
public abstract class AbstractXMLReader
    implements org.xml.sax.XMLReader
{

    private Map<Object,Object> featureMap = new HashMap<Object,Object>();
    private Map<Object,Object> propertyMap = new HashMap<Object,Object>();
    private EntityResolver entityResolver;
    private DTDHandler dtdHandler;
    private ContentHandler contentHandler;
    private ErrorHandler errorHandler;

    /**
     * The only abstract method in this class. Derived classes can parse
     * any source of data and emit SAX2 events to the ContentHandler.
     */
    public abstract void parse(InputSource input)
                        throws IOException, SAXException;

    public boolean getFeature(String name)
                       throws SAXNotRecognizedException,
                              SAXNotSupportedException
    {

        Boolean featureValue = (Boolean)this.featureMap.get(name);

        return (featureValue == null) ? false : featureValue.booleanValue();
    }

    public void setFeature(String name, boolean value)
                    throws SAXNotRecognizedException, SAXNotSupportedException
    {
        this.featureMap.put(name, new Boolean(value));
    }

    public Object getProperty(String name)
                       throws SAXNotRecognizedException,
                              SAXNotSupportedException
    {

        return this.propertyMap.get(name);
    }

    public void setProperty(String name, Object value)
                     throws SAXNotRecognizedException,
                            SAXNotSupportedException
    {
        this.propertyMap.put(name, value);
    }

    public void setEntityResolver(EntityResolver entityResolver)
    {
        this.entityResolver = entityResolver;
    }

    public EntityResolver getEntityResolver()
    {

        return this.entityResolver;
    }

    public void setDTDHandler(DTDHandler dtdHandler)
    {
        this.dtdHandler = dtdHandler;
    }

    public DTDHandler getDTDHandler()
    {

        return this.dtdHandler;
    }

    public void setContentHandler(ContentHandler contentHandler)
    {
        this.contentHandler = contentHandler;
    }

    public ContentHandler getContentHandler()
    {

        return this.contentHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler)
    {
        this.errorHandler = errorHandler;
    }

    public ErrorHandler getErrorHandler()
    {

        return this.errorHandler;
    }

    public void parse(String systemId)
               throws IOException, SAXException
    {
        parse(new InputSource(systemId));
    }
}