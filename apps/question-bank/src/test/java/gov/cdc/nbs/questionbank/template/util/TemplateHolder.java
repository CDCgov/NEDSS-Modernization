package gov.cdc.nbs.questionbank.template.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.template.response.Template;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class TemplateHolder {

	Page<Template> templateResults;
	
	 WaTemplate template;
}
