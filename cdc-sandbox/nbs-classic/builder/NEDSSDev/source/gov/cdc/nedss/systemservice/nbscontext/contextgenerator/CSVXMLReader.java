package gov.cdc.nedss.systemservice.nbscontext.contextgenerator;

import java.io.*;

import java.net.URL;

import org.xml.sax.*;
import org.xml.sax.helpers.*;


/**
 * A utility class that parses a Comma Separated Values (CSV) file
 * and outputs its contents using SAX2 events. The format of CSV that
 * this class reads is identical to the export format for Microsoft
 * Excel. For simple values, the CSV file may look like this:
 * <pre>
 * a,b,c
 * d,e,f
 * </pre>
 * Quotes are used as delimiters when the values contain commas:
 * <pre>
 * a,"b,c",d
 * e,"f,g","h,i"
 * </pre>
 * And double quotes are used when the values contain quotes. This parser
 * is smart enough to trim spaces around commas, as well.
 *
 * @author NEDSS Development Team
 */
public class CSVXMLReader
    extends AbstractXMLReader
{

    // an empty attribute for use with SAX
    private static final Attributes EMPTY_ATTR = new AttributesImpl();

    /**
     * Parse a CSV file. SAX events are delivered to the ContentHandler
     * that was registered via <code>setContentHandler</code>.
     *
     * @param input the comma separated values file to parse.
     */
    public void parse(InputSource input)
               throws IOException, SAXException
    {

        // if no handler is registered to receive events, don't bother
        // to parse the CSV file
        ContentHandler ch = getContentHandler();

        if (ch == null)
        {

            return;
        }

        // convert the InputSource into a BufferedReader
        BufferedReader br = null;

        if (input.getCharacterStream() != null)
        {
            br = new BufferedReader(input.getCharacterStream());
        }
        else if (input.getByteStream() != null)
        {
            br = new BufferedReader(new InputStreamReader(input.getByteStream()));
        }
        else if (input.getSystemId() != null)
        {

            java.net.URL url = new URL(input.getSystemId());
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        }
        else
        {
            throw new SAXException("Invalid InputSource object");
        }

        ch.startDocument();

        // emit <csvFile>
        ch.startElement("", "", "csvFile", EMPTY_ATTR);

        // read each line of the file until EOF is reached
        String curLine = null;

        while ((curLine = br.readLine()) != null)
        {
            curLine = curLine.trim();

            if (curLine.length() > 0)
            {

                // create the <line> element
                ch.startElement("", "", "line", EMPTY_ATTR);

                // output data from this line
                parseLine(curLine, ch);

                // close the </line> element
                ch.endElement("", "", "line");
            }
        }

        // emit </csvFile>
        ch.endElement("", "", "csvFile");
        ch.endDocument();
    }

    // Break an individual line into tokens. This is a recursive function
    // that extracts the first token, then recursively parses the
    // remainder of the line.
    private void parseLine(String curLine, ContentHandler ch)
                    throws IOException, SAXException
    {

        String firstToken = null;
        String remainderOfLine = null;
        int commaIndex = locateFirstDelimiter(curLine);

        if (commaIndex > -1)
        {
            firstToken = curLine.substring(0, commaIndex).trim();
            remainderOfLine = curLine.substring(commaIndex + 1).trim();
        }
        else
        {

            // no commas, so the entire line is the token
            firstToken = curLine;
        }

        // remove redundant quotes
        firstToken = cleanupQuotes(firstToken);

        // emit the <value> element
        ch.startElement("", "", "value", EMPTY_ATTR);
        ch.characters(firstToken.toCharArray(), 0, firstToken.length());
        ch.endElement("", "", "value");

        // recursively process the remainder of the line
        if (remainderOfLine != null)
        {
            parseLine(remainderOfLine, ch);
        }
    }

    // locate the position of the comma, taking into account that
    // a quoted token may contain ignorable commas.
    private int locateFirstDelimiter(String curLine)
    {

        if (curLine.startsWith("\""))
        {

            boolean inQuote = true;
            int numChars = curLine.length();

            for (int i = 1; i < numChars; i++)
            {

                char curChar = curLine.charAt(i);

                if (curChar == '"')
                {
                    inQuote = !inQuote;
                }
                else if (curChar == ',' && !inQuote)
                {

                    return i;
                }
            }

            return -1;
        }
        else
        {

            return curLine.indexOf(',');
        }
    }

    // remove quotes around a token, as well as pairs of quotes
    // within a token.
    private String cleanupQuotes(String token)
    {

        StringBuffer buf = new StringBuffer();
        int length = token.length();
        int curIndex = 0;

        if (token.startsWith("\"") && token.endsWith("\""))
        {
            curIndex = 1;
            length--;
        }

        boolean oneQuoteFound = false;
        boolean twoQuotesFound = false;

        while (curIndex < length)
        {

            char curChar = token.charAt(curIndex);

            if (curChar == '"')
            {
                twoQuotesFound = (oneQuoteFound) ? true : false;
                oneQuoteFound = true;
            }
            else
            {
                oneQuoteFound = false;
                twoQuotesFound = false;
            }

            if (twoQuotesFound)
            {
                twoQuotesFound = false;
                oneQuoteFound = false;
                curIndex++;

                continue;
            }

            buf.append(curChar);
            curIndex++;
        }

        return buf.toString();
    }
}