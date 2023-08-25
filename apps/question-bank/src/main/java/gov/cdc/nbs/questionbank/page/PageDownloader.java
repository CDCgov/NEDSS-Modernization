package gov.cdc.nbs.questionbank.page;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	@SuppressWarnings("deprecation")
	public ByteArrayInputStream downloadLibrary() {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL).withHeader("Event Type",
				"Page Name", "Page State", "Related Conditions(s)", "Last Udated", "Last Udated By ");

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
			List<WaTemplate> pages = templateRepository.getAllPagesOrderedByName();
			for (WaTemplate page : pages) {
				List<String> data = Arrays.asList(getEventType(page.getBusObjType()), page.getTemplateNm(), page.getTemplateType(),
						formatttedRelatedConditions(page), page.getLastChgTime().toString(),
						getLastUpdatedUser(page.getLastChgUserId()));
				csvPrinter.printRecord(data);

			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());

		} catch (IOException e) {
			throw new IOException("Error downloading Page Library: " + e.getMessage());
		}

	}

	public String formatttedRelatedConditions(WaTemplate page) {
		StringBuilder data = new StringBuilder();
		List<PageCondMapping> mappings = pageConMappingRepository.findByWaTemplateUid(page);
		for (PageCondMapping conMap : mappings) {
			String conditionId = conMap.getConditionCd();
			Optional<ConditionCode> condition = conditionCodeRepository.findById(conditionId);
			if (condition.isPresent()) {
				data.append(condition.get().getConditionDescTxt() + "(" + conditionId + "),");
			}

		}
		return data.toString();
	}

	public String getLastUpdatedUser(Long lastChgUserId) {
		Optional<UserProfile> user = userProfileRepository.findById(lastChgUserId);
		if (user.isPresent()) {
			return user.get().getFirstNm() + " " + user.get().getLastNm();
		}
		return " ";
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

}
