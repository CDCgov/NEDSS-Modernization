package gov.cdc.nedss.systemservice.nbscontext.contextgenerator;

import gov.cdc.nedss.util.LogUtils;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;

public class SimpleCSVProcessor
{

    static final LogUtils logger = new LogUtils(SimpleCSVProcessor.class.getName());

    public static void main(String[] args) throws Exception
    {

        String inputFileName = null;
        
        if(args.length < 1 || args.length > 2){
        	System.out.println("ContextMapCsvToXmlConverter.bat datafile.csv");
        	System.exit(-1);
        } else {
        	inputFileName = args[0];
        }
        
        TransformerFactory transFact = TransformerFactory.newInstance();
        if (transFact.getFeature(SAXTransformerFactory.FEATURE))
        {

            SAXTransformerFactory saxTransFact = (SAXTransformerFactory)transFact;
            TransformerHandler transHand = null;


			String outputFileName = null;

			transHand = saxTransFact.newTransformerHandler();
			outputFileName = "Consolidated.xml";

            File f = new File(outputFileName);
            transHand.setResult(new StreamResult(f));
            CSVXMLReader csvReader = new CSVXMLReader();
            InputSource csvInputSrc = null;

            try
            {
                csvInputSrc = new InputSource(new FileReader(inputFileName));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            // attach the XSLT processor to the CSVXMLReader
            csvReader.setContentHandler(transHand);
            csvReader.parse(csvInputSrc);
        }
        else
        {
            System.err.println("SAXTransformerFactory is not supported.");
            System.exit(1);
        }
    }
}