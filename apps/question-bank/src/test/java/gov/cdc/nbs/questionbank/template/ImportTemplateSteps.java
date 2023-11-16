package gov.cdc.nbs.questionbank.template;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.ResultActions;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ImportTemplateSteps {

  record TemplateXml(String name, String xml) {
  };

  @Autowired
  Active<TemplateXml> template = new Active<>();

  @Autowired
  @Qualifier("classic")
  MockRestServiceServer server;

  @Value("${nbs.wildfly.url:http://wildfly:7001}")
  String classicUrl;

  @Autowired
  TemplateImportRequest request;

  @Autowired
  PageMother mother;

  private final Active<ResultActions> response = new Active<>();

  private static final String LOCATION =
      "http://localhost:8080/nbs/PreviewTemplate.do?method=viewTemplate&srcTemplateNm=TEMPLATE_NAME&src=Import&templateUid=TEMPLATE_UID";


  private static final String XML =
      """
          <xml-fragment>
            <ExportHeader>
              <TemplateNm>TEMPLATE_NAME</TemplateNm>
              <ExportedFromState>SRA</ExportedFromState>
              <ExportedDateTime>2023-11-15T21:13:26.955Z</ExportedDateTime>
            </ExportHeader>
            <TemplateInsert>
              <TemplateName>TEMPLATE_NAME</TemplateName>
              <InsertStatement>
                INSERT INTO wa_template
                (template_type,publish_version_nbr,form_cd,condition_cd,bus_obj_type,datamart_nm,record_status_cd,record_status_time,last_chg_time,last_chg_user_id,local_id,desc_txt,template_nm,publish_ind_cd,add_time,add_user_id,nnd_entity_identifier,parent_template_uid,source_nm,template_version_nbr,version_note)
                values
                ('TEMPLATE',null,null,null,'INV',null,'Active',ReplaceWithCurrentDate,ReplaceWithCurrentDate,ReplaceCurrentUserId,null,'This
                arboviral investigation template was created with NBS 5.4 based on the CDC Arboviral MMG
                version 1.3 dated 9/19/2016. Jurisdictions should inactivate any existing arboviral templates
                after upgrading to NBS 5.4 and use this version going forward. This template is compatible
                with NBS 5.4 and
                up.','TEMPLATE_NAME',SetPublishIndCdtoF,ReplaceWithCurrentDate,ReplaceCurrentUserId,'Arbo_Case_Map_v1.0',null,ReplaceSrcName,null,null)</InsertStatement>
            </TemplateInsert>
          </xml-fragment>
                  """;


  @Given("I have template to import")
  public void i_have_a_template_to_import() {
    WaTemplate page = mother.one();
    template.active(new TemplateXml(
        page.getTemplateNm(),
        XML.replaceAll(
            "TEMPLATE_NAME",
            page.getTemplateNm())));
  }

  @When("I import a template")
  public void i_import_a_template() throws Exception {
    WaTemplate page = mother.one();
    String location = LOCATION.replace(
        "TEMPLATE_UID",
        page.getId().toString())
        .replace("TEMPLATE_NAME", page.getTemplateNm());
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(new URI(location));
    server.expect(requestTo(classicUrl + "/nbs/ManageTemplates.do?method=importTemplate&type=Import"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
        .andRespond(withStatus(HttpStatus.FOUND).headers(headers));

    response.active(request.send(template.active()));
  }

  @Then("the template is imported")
  public void the_template_is_imported() throws Exception {
    WaTemplate page = mother.one();
    server.verify();

    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(page.getId()))
        .andExpect(jsonPath("$.templateNm").value(page.getTemplateNm()));
  }

}
