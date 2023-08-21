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

import gov.cdc.nbs.entity.odse.UserProfile;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.UserProfileRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;

//

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
				List<String> data = Arrays.asList(page.getBusObjType(), page.getTemplateNm(), page.getTemplateType(),
						formatttedRelatedConditions(page), page.getLastChgTime().toString(),
						getLastUpdatedUser(page.getLastChgUserId()));
				csvPrinter.printRecord(data);

			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());

		} catch (IOException e) {
			throw new RuntimeException("Error downloading Page Library: " + e.getMessage());
		}

	}

	public String formatttedRelatedConditions(WaTemplate page) {
		StringBuffer data = new StringBuffer();
		List<PageCondMapping> mappings = pageConMappingRepository.findByWaTemplate(page);
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

}
