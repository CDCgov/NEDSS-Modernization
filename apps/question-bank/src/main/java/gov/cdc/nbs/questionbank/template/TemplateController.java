package gov.cdc.nbs.questionbank.template;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.questionbank.template.request.TemplateSearchRequest;
import gov.cdc.nbs.questionbank.template.response.Template;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/template/")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class TemplateController {

	private TemplateReader templateReader;

	@GetMapping
	@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	public Page<Template> findAllTemplates(@PageableDefault(size = 25) Pageable pageable) {
		return templateReader.findAllTemplates(pageable);

	}

	@PostMapping("search")
	@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	public Page<Template> searchTemplate(@RequestBody TemplateSearchRequest search,
			@PageableDefault(size = 25) Pageable pageable) {
		return templateReader.searchTemplate(search, pageable);
	}

}
