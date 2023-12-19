package gov.cdc.nbs.questionbank.page;

import java.io.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.UserProfile;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.UserProfileRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import lombok.RequiredArgsConstructor;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
@RequiredArgsConstructor
public class PageDownloader {

	@Autowired
	private WaTemplateRepository templateRepository;

	@Autowired
	private PageCondMappingRepository pageConMappingRepository;

	@Autowired
	private ConditionCodeRepository conditionCodeRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	private static final List<String> PAGE_LIBRARY_HEADERS = Arrays.asList("Event Type",
			"Page Name", "Page State", "Related Conditions(s)", "Last Updated", "Last Updated By ");
	private static final Font helvetica = new Font(FontFamily.HELVETICA, 7, Font.NORMAL);
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter
			.ofPattern("MM/dd/yyyy")
			.withZone(ZoneId.of("UTC"));

	
	public ByteArrayInputStream downloadLibrary()  throws IOException {
		final CSVFormat format = CSVFormat.Builder.create().setQuoteMode(QuoteMode.MINIMAL).setHeader("Event Type",
				"Page Name", "Page State", "Related Conditions(s)", "Last Udated", "Last Udated By ").build();
		

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
			
			List<WaTemplate> pages = templateRepository.getAllPagesOrderedByName();
			List<PageCondMapping> mappings = pageConMappingRepository.findByWaTemplateUidIn(pages);
			List<ConditionCode> conditionCodes = conditionCodeRepository.findByIdIn(conditionIds(mappings));

			for (WaTemplate page : pages) {
				List<ConditionCode> pageConditions = new ArrayList<>();

				mappings.stream()
                .filter(p -> p.getWaTemplateUid().equals(page))
                .forEach(v -> 
                    conditionCodes.stream()
                            .filter(c -> c.getId().equals(v.getConditionCd()))
                            .forEach(pageConditions::add)
                );
				
				List<String> data = Arrays.asList(getEventType(page.getBusObjType()), page.getTemplateNm(), page.getTemplateType(),
						formatttedRelatedConditions(pageConditions), null != page.getLastChgTime()? dateFormatter.format(page.getLastChgTime()):"",
						getLastUpdatedUser(page.getLastChgUserId()));
				csvPrinter.printRecord(data);

			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());

		} catch (IOException e) {
			throw new IOException("Error downloading Page Library: " + e.getMessage());
		}

	}

	public String formatttedRelatedConditions(List<ConditionCode> conditions) {
        return conditions.stream()
                .map(c -> c.getConditionDescTxt() + "(" + c.getId() + ")")
                .collect(Collectors.joining(","));
	}

	public String getLastUpdatedUser(Long lastChgUserId) {
		Optional<UserProfile> user = userProfileRepository.findById(lastChgUserId);
		if (user.isPresent()) {
			return user.get().getFirstNm() + " " + user.get().getLastNm();
		}
		return " ";
	}
	private List<String> conditionIds(List<PageCondMapping> mappings) {
		List<String> conditionIds = new ArrayList<>();
		for(PageCondMapping map : mappings) {
			conditionIds.add(map.getConditionCd());
		}
		return conditionIds;
	}
	
	private String getEventType(String type) {
	if (type == null || type.length() < 1)
	 return " ";
	
	  switch (type) {
		case "INV":
			return  "Investigation";
		case "CON":
		    return "Contact";
		case "VAC":
			return "Vaccination";
		case "IXS":
			return "Interview";
		case "SUS":
			return "Lab Susceptibility";
		case "LAB":
			return "Lab Report";
		case "ISO":
			return "Lab Isolate Tracking";
		default:
			return type;

	}
	  
	}

	public byte[] downloadLibraryPDF() throws DocumentException, IOException {
		var outputStream = new ByteArrayOutputStream();
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		PdfWriter.getInstance(document, outputStream);

		document.open();
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(95);

		addPageLibraryTableHeader(table);
		List<WaTemplate> pages = templateRepository.getAllPagesOrderedByName();
		List<PageCondMapping> mappings = pageConMappingRepository.findByWaTemplateUidIn(pages);
		List<ConditionCode> conditionCodes = conditionCodeRepository.findByIdIn(conditionIds(mappings));

		for (WaTemplate page : pages) {
			List<ConditionCode> pageConditions = new ArrayList<>();

			mappings.stream().filter(p -> p.getWaTemplateUid().equals(page)).toList()
					.forEach(v ->
							conditionCodes.stream()
									.filter(c -> c.getId().equals(v.getConditionCd()))
									.forEach(pageConditions::add)
					);

			List<String> data = Arrays.asList(getEventType(page.getBusObjType()), page.getTemplateNm(), page.getTemplateType(),
					formatttedRelatedConditions(pageConditions), null != page.getLastChgTime()? dateFormatter.format(page.getLastChgTime()):"",
					getLastUpdatedUser(page.getLastChgUserId()));
			addRows(table,data);

		}
		
		document.add(table);
		document.close();
		outputStream.close();
		return outputStream.toByteArray();
	}

	private void addRows(PdfPTable table, List<String> pagelibrarydata) {
		 for(String data: pagelibrarydata){
			 table.addCell(createStringCell(data, helvetica));
		 }
	}
	private PdfPCell createStringCell(String content, Font font) {
		if (content == null) {
			return new PdfPCell();
		}
		return new PdfPCell(new Phrase(content, font));
	}
	private void addPageLibraryTableHeader(PdfPTable table) {
		PAGE_LIBRARY_HEADERS
				.forEach(columnTitle -> {
					PdfPCell header = new PdfPCell();
					header.setBackgroundColor(new BaseColor(233, 233, 233));
					header.setBorderWidth(1);
					header.setPhrase(new Phrase(columnTitle, helvetica));
					table.addCell(header);
				});
	}
}
