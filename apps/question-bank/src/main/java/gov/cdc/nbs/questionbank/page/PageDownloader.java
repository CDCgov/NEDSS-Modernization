package gov.cdc.nbs.questionbank.page;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;


//

public class PageDownloader {
	
	 @Autowired
	 private WaTemplateRepository templateRepository;
	 
	 @Autowired
	 private PageCondMappingRepository pageConMappingRepository;
	 
	public ByteArrayInputStream downloadLibrary() {
	final CSVFormat format = CSVFormat.DEFAULT
			                 .withQuoteMode(QuoteMode.MINIMAL)
			                 .withHeader();
	
	try (ByteArrayOutputStream out = new ByteArrayOutputStream() ;
		CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
		
	} catch(IOException e) {
		
	}
	
	}

}
