package gov.cdc.nedss.webapp.nbs.action.pagemanagement.phinvads;

import gov.cdc.vocab.service.VocabService;
import gov.cdc.vocab.service.bean.Authority;
import gov.cdc.vocab.service.bean.CodeSystem;
import gov.cdc.vocab.service.bean.CodeSystemConcept;
import gov.cdc.vocab.service.bean.CodeSystemConceptAltDesignation;
import gov.cdc.vocab.service.bean.CodeSystemConceptPropertyValue;
import gov.cdc.vocab.service.bean.CodeSystemPropertyDefinition;
import gov.cdc.vocab.service.bean.Group;
import gov.cdc.vocab.service.bean.Source;
import gov.cdc.vocab.service.bean.ValueSet;
import gov.cdc.vocab.service.bean.ValueSetConcept;
import gov.cdc.vocab.service.bean.ValueSetVersion;
import gov.cdc.vocab.service.bean.View;
import gov.cdc.vocab.service.bean.ViewVersion;
import gov.cdc.vocab.service.dto.input.CodeSystemConceptSearchCriteriaDto;
import gov.cdc.vocab.service.dto.input.CodeSystemSearchCriteriaDto;
import gov.cdc.vocab.service.dto.input.GroupSearchCriteriaDto;
import gov.cdc.vocab.service.dto.input.ValueSetConceptSearchCriteriaDto;
import gov.cdc.vocab.service.dto.input.ValueSetSearchCriteriaDto;
import gov.cdc.vocab.service.dto.input.ValueSetVersionSearchCriteriaDto;
import gov.cdc.vocab.service.dto.input.ViewVersionSearchCriteriaDto;
import gov.cdc.vocab.service.dto.output.CodeSystemConceptResultDto;
import gov.cdc.vocab.service.dto.output.CodeSystemPropertyDefinitionResultDto;
import gov.cdc.vocab.service.dto.output.CodeSystemResultDto;
import gov.cdc.vocab.service.dto.output.FileImageResultDto;
import gov.cdc.vocab.service.dto.output.GroupResultDto;
import gov.cdc.vocab.service.dto.output.IdResultDto;
import gov.cdc.vocab.service.dto.output.ServiceInfoResultDto;
import gov.cdc.vocab.service.dto.output.ValueSetConceptResultDto;
import gov.cdc.vocab.service.dto.output.ValueSetResultDto;
import gov.cdc.vocab.service.dto.output.ValueSetVersionResultDto;
import gov.cdc.vocab.service.dto.output.ViewVersionResultDto;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * Test Client - Connects to the VADS Web Service and Unit Tests the Service methods
 * @author Eady
 */
public class ClientTest {

	/**
	 * log4j Logger.
	 */
	static Logger log = Logger.getLogger(ClientTest.class);

 /**
   * Client Instance of the Web Service
   */
  private VocabService service;

  /**
   * Constructs the Client instance of the Web Service
   */
  public ClientTest(){

    String serviceUrl = "http://phinvads.cdc.gov/vocabService/v2";

    HessianProxyFactory factory = new HessianProxyFactory();
    try {
      service = (VocabService) factory.create(VocabService.class, serviceUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the web service client
   */
  public VocabService getService(){
    return this.service;
  }

  /**
   * Unit tests the VADS Web Service.
   * @param args no arguments are needed.
   * @throws MalformedURLException
   */
  public static void main(String[] args) throws MalformedURLException {

    ClientTest client = new ClientTest();

    // Call getReleaseNotes()
    log.debug("Calling getReleaseNotes()");
    FileImageResultDto fileResultDto = client.getService().getReleaseNotes();
    log.debug("File length: " + fileResultDto.getFileImage().length);

    // Call getCodeSystemRepresentation()
    log.debug("Calling getCodeSystemRepresentation()");
    fileResultDto = client.getService().getCodeSystemRepresentation();
    log.debug("File length: " + fileResultDto.getFileImage().length);

    /*
     * getServiceInfo()
     */
    log.debug("Calling getAllGroups()");
    ServiceInfoResultDto siResult = client.getService().getServiceInfo();
    log.debug("Content Version/Date:" + siResult.getContentVersion() + "/" + siResult.getContentVersionDate());

    /*
     * Search for value set concepts
     */
    ValueSetConceptSearchCriteriaDto searchCrit = new ValueSetConceptSearchCriteriaDto();
    searchCrit.setConceptCodeSearch(true);
    searchCrit.setPreferredNameSearch(true);
    searchCrit.setConceptNameSearch(true);
    searchCrit.setAlternateNameSearch(false);
    searchCrit.setFilterByCodeSystems(false);
    searchCrit.setFilterByGroups(false);
    searchCrit.setFilterByViews(false);
    searchCrit.setFilterByValueSets(false);
    searchCrit.setSearchText("New"); // Should return new york, new jersey, etc.
    searchCrit.setSearchType(1);

    ValueSetConceptResultDto searchResult = null;

    searchResult = client.getService().findValueSetConcepts(searchCrit, 1, 5);
    log.debug("Count is " + searchResult.getTotalResults());

    List<ValueSetConcept> valueSetConcepts = searchResult.getValueSetConcepts();

//    for (ValueSetConcept e : valueSetConcepts) {
//      log.debug("CodeSystemOid is " + e.getCodeSystemOid());
//      log.debug("Concept Code is " + e.getConceptCode());
//      log.debug("Statuscode is " + e.getStatus());
//      log.debug("Concept Id is " + e.getId());
//    }

    /*
     * Get All Value Sets
     */
    ValueSetResultDto vsResultDto = client.getService().getAllValueSets();
    List<ValueSet> valueSets = vsResultDto.getValueSets();
    log.debug("result set size is " + valueSets.size());
    for (ValueSet e : valueSets) {
      log.debug("ValueSet Name is " + e.getName());
    }

    /*
     * Get All Value Set Versions
     */
    ValueSetVersionResultDto vsvResultDto = client.getService().getAllValueSetVersions();
    List<ValueSetVersion> valueSetVersions = vsvResultDto.getValueSetVersions();
    log.debug("result set size is " + valueSetVersions.size());
    for (ValueSetVersion e : valueSetVersions) {
      log.debug("ValueSetVersion id and oid and status are " + e.getId() + " : " + e.getValueSetOid() + ":"
          + e.getStatus());
    }

    /*
     * Get All Views
     */
    List<View> views = client.getService().getAllViews().getViews();

    log.debug("result set size is " + views.size());
    for (View e : views) {

      log.debug("VocabularyView Name: " + e.getName() + " id: " + e.getId());
    }

    /*
     * Get All View Versions
     */
    List<ViewVersion> viewVersions = client.getService().getAllViewVersions().getViewVersions();

    log.debug("result set size is " + viewVersions.size());
    for (ViewVersion e : viewVersions) {
      log.debug("ViewVersion [Id=" + e.getId() + "][ViewId=" + e.getViewId() + "][Version="
          + e.getVersionNumber() + "][Reason=" + e.getReason() + "]");
    }

    /*
     * Get All Groups
     */
    log.debug("Calling getAllGroups()");
    List<Group> groups = client.getService().getAllGroups().getGroups();
    for (Group e : groups) {
      log.debug("Group Id is " + e.getId());
      log.debug("Group Name is " + e.getName());
    }

    /*
     * Get All Code Systems
     */
    List<CodeSystem> codeSystems = client.getService().getAllCodeSystems().getCodeSystems();

    for (CodeSystem e : codeSystems) {

      log.debug("Code System name is " + e.getName());
    }

    // GetValueSetConcept
    ValueSetConcept vsc = client.getService().getValueSetConceptById("BE673BB3-D280-DD11-B38D-00188B398520").getValueSetConcept();
    log.debug("Get ValueSetConcept");
    log.debug(vsc.getId());

    // GetValueSetVersion
    ValueSetVersion vsv = client.getService().getValueSetVersionById("80D34BBC-617F-DD11-B38D-00188B398520").getValueSetVersion();
    log.debug("Get ValueSetVersion");
    log.debug(vsv.getId());

    // GetCodeSystemConcept
    CodeSystemConcept csc = client.getService().getCodeSystemConceptByOidAndCode("2.16.840.1.113883.6.92", "16").getCodeSystemConcept();
    log.debug("Get CodeSystemConcept");
    log.debug(csc.getName());

    // GetCodeSystemConcept
    csc = client.getService().getCodeSystemConceptById("D6B69C5D-4D7F-DD11-B38D-00188B398520").getCodeSystemConcept();
    log.debug("Get CodeSystemConceptById");
    log.debug(csc.getName());

    // Find ValueSetVersion
    ValueSetVersionSearchCriteriaDto vsvSearchCrit = new ValueSetVersionSearchCriteriaDto();
    vsvSearchCrit.setFilterByViews(false);
    vsvSearchCrit.setFilterByGroups(false);
    vsvSearchCrit.setCodeSearch(true);
    vsvSearchCrit.setNameSearch(true);
    vsvSearchCrit.setOidSearch(false);
    vsvSearchCrit.setDefinitionSearch(false);
    vsvSearchCrit.setSearchText("State");
    vsvSearchCrit.setSearchType(1);
    vsvSearchCrit.setVersionOption(1);

    ValueSetVersionResultDto vsvSearchResult = null;

    vsvSearchResult = client.getService().findValueSetVersions(vsvSearchCrit, 1, 5);
    log.debug("VSV Search Count is " + vsvSearchResult.getTotalResults());

    valueSetVersions = vsvSearchResult.getValueSetVersions();

    for (ValueSetVersion e : valueSetVersions) {
      log.debug("VSV OID is: " + e.getValueSetOid());
      log.debug("VSV description is: " + e.getDescription());
      log.debug("VSV vNumber is: " + e.getVersionNumber());
    }

    // Find ValueSet
    ValueSetSearchCriteriaDto vsSearchCrit = new ValueSetSearchCriteriaDto();
    vsSearchCrit.setFilterByViews(false);
    vsSearchCrit.setFilterByGroups(false);
    vsSearchCrit.setCodeSearch(true);
    vsSearchCrit.setNameSearch(true);
    vsSearchCrit.setOidSearch(false);
    vsSearchCrit.setDefinitionSearch(false);
    vsSearchCrit.setSearchText("State");
    vsSearchCrit.setSearchType(1);

    ValueSetResultDto vsSearchResult = null;

    IdResultDto vsIds = client.getService().findValueSetIds(vsSearchCrit, 1, 5);
    log.debug("VSID:" + vsIds.getId());
    vsSearchResult = client.getService().findValueSets(vsSearchCrit, 1, 5);
    log.debug("VS Search Count is " + vsvSearchResult.getTotalResults());

    valueSets = vsSearchResult.getValueSets();

    for (ValueSet e : valueSets) {
      log.debug("VS OID is: " + e.getOid());
      log.debug("VS description is: " + e.getDefinitionText());
      log.debug("VS name is: " + e.getName());
    }


    log.debug("");
    log.debug("Related Object Retrieval:");
    log.debug("");

    // Call getGroupIdsByValueSetOid()
    List<String> groupIdsByVSOid = client.getService().getGroupIdsByValueSetOid("2.16.840.1.114222.4.11.830").getIds();
    log.debug("Calling getGroupIdsByValueSetOid() returned " + groupIdsByVSOid.size() + " row(s):");
    for (String e : groupIdsByVSOid) {
      Group group = client.getService().getGroupById(e).getGroup();
      log.debug("Group [Id=" + group.getId() + "][Name=" + group.getName() + "][Description="
          + group.getDescriptionText() + "]");
      // log.debug("Group [Id=" + e + "]");
    }
    log.debug("");

    // Call getViewVersionIdsByValueSetVersionId()
    List<String> viewVersionIdsByVSVId = client.getService().getViewVersionIdsByValueSetVersionId(
        "80D34BBC-617F-DD11-B38D-00188B398520").getIds();
    log.debug("Calling getViewVersionIdsByValueSetVersionId() returned " + viewVersionIdsByVSVId.size()
        + " row(s):");
    for (String e : viewVersionIdsByVSVId) {
      ViewVersion vv = client.getService().getViewVersionById(e).getViewVersion();
      View view = client.getService().getViewById(vv.getViewId()).getView();
      log.debug("ViewVersion [Id=" + vv.getId() + "][ViewId=" + vv.getViewId() + "][Name=" + view.getName()
          + "][Description=" + view.getDescriptionText() + "][Version=" + vv.getVersionNumber() + "][Reason="
          + vv.getReason() + "]");
      // log.debug("ViewVersion [Id=" + e + "]");
    }
    log.debug("");

    // Call getValueSetVersionsByValueSetOid
    List<ValueSetVersion> vsvByVSOid = client.getService().getValueSetVersionsByValueSetOid("2.16.840.1.114222.4.11.830")
        .getValueSetVersions();
    log.debug("Calling getValueSetVersionsByValueSetOid() returned " + vsvByVSOid.size() + " row(s):");
    for (ValueSetVersion e : vsvByVSOid) {
      log.debug("VSVByVSOID [Id=" + e.getId() + "][VersionNumber=" + e.getVersionNumber() + "]");
    }
    log.debug("");



    // Call getValueSetConceptsByValueSetVersionId

    Calendar cal1 = Calendar.getInstance();

    @SuppressWarnings("unused")
    ValueSetConceptResultDto vscByVSVid = client.getService().getValueSetConceptsByValueSetVersionId(
        "B8D34BBC-617F-DD11-B38D-00188B398520", 1, 100);

    Calendar cal2 = Calendar.getInstance();

    log.debug("Call to method took " + getElapsedMiliSeconds(cal1, cal2) + " miliseconds.");



    // Call getCodeSystemConceptAltDesignationByCodeSystemOidAndConceptCode()
    List<CodeSystemConceptAltDesignation> cscAltDes = client.getService().getCodeSystemConceptAltDesignationByOidAndCode(
        "2.16.840.1.113883.6.93", "17045").getCodeSystemConceptAltDesignations();
    log.debug("Calling getCodeSystemConceptAltDesignationByCodeSystemOidAndConceptCode() returned "
        + cscAltDes.size() + " row(s):");
    for (CodeSystemConceptAltDesignation e : cscAltDes) {
      log.debug("CSC Alternate Designation [Id=" + e.getId() + "][CSOid=" + e.getCodeSystemOid()
          + "][ConceptCode=" + e.getConceptCode() + "][SDODesignationId=" + e.getSdoDesignationId()
          + "][PhinPreferredTerm=" + e.isPhinPreferredTerm() + "][Code=" + e.isCode() + "][ConceptDesignationText="
          + e.getConceptDesignationText() + "]");
    }
    log.debug("");

    // Call findCodeSystems
    CodeSystemSearchCriteriaDto csSearchCritDto = new CodeSystemSearchCriteriaDto();
    csSearchCritDto.setCodeSearch(true);
    csSearchCritDto.setNameSearch(true);
    csSearchCritDto.setOidSearch(false);
    csSearchCritDto.setDefinitionSearch(false);
    csSearchCritDto.setAssigningAuthoritySearch(false);
    csSearchCritDto.setTable396Search(false);
    csSearchCritDto.setSearchType(1);
    csSearchCritDto.setSearchText("\"Country\"");

    CodeSystemResultDto csSearchResultDto = client.getService().findCodeSystems(csSearchCritDto, 1, 5);
    log.debug("Calling findCodeSystems() returned " + csSearchResultDto.getTotalResults() + " matches");
    for (CodeSystem e : csSearchResultDto.getCodeSystems()) {
      log.debug("code system id:" + e.getId());
      log.debug("code system name:" + e.getName());
    }
    log.debug("");

    // Call findCodeSystemConcepts

    CodeSystemConceptSearchCriteriaDto cscSearchCritDto = new CodeSystemConceptSearchCriteriaDto();
    cscSearchCritDto.setCodeSearch(true);
    cscSearchCritDto.setNameSearch(true);
    cscSearchCritDto.setPreferredNameSearch(false);
    cscSearchCritDto.setAlternateNameSearch(false);
    cscSearchCritDto.setDefinitionSearch(false);
    cscSearchCritDto.setSearchType(1);
    cscSearchCritDto.setSearchText("Texas");
    CodeSystemConceptResultDto cscSearchResultDto = client.getService().findCodeSystemConcepts(cscSearchCritDto, 1, 5);
    System.out
        .println("Calling findCodeSystemConcepts() returned " + cscSearchResultDto.getTotalResults() + " matches");
    for (CodeSystemConcept e : cscSearchResultDto.getCodeSystemConcepts()) {
      log.debug("code system concept id:" + e.getConceptCode());
      log.debug("code system concept name:" + e.getName());
      log.debug("code system oid:" + e.getCodeSystemOid());
    }
    log.debug("");

    // GetCodeSystem
    CodeSystem cs = client.getService().getCodeSystemByOid("2.16.840.1.113883.6.92").getCodeSystem();
    log.debug("Call Get CodeSystem");
    log.debug(cs.getOid());
    log.debug(cs.getName());
    log.debug("");

    // GetCodeSystemPropertyDefinitionsByCodeSystemOid
    log.debug("Call Get GetCodeSystemPropertyDefinitionsByCodeSystemOid");
    List<CodeSystemPropertyDefinition> cspdList = client.getService().getCodeSystemPropertyDefinitionsByCodeSystemOid(
        "2.16.840.1.113883.6.93").getCodeSystemPropertyDefinitions();
    log.debug("Number of results: " + cspdList.size());
    for (CodeSystemPropertyDefinition e : cspdList) {
      log.debug("name: " + e.getName());
      log.debug("dataTypeCode: " + e.getDataType());
    }
    log.debug("");

    // GetAllSources
    log.debug("Call getAllSources()");
    List<Source> sources = client.getService().getAllSources().getSources();
    log.debug("allSources length: " + sources.size());
    log.debug("name: " + sources.get(1).getName());
    log.debug("description: " + sources.get(1).getDescription());
    log.debug("");

    // GetAllSources
    log.debug("Call getAllAuthorities()");
    List<Authority> authorities = client.getService().getAllAuthorities().getAuthoritys();
    log.debug("allAuthorities length: " + authorities.size());
    log.debug("name: " + authorities.get(0).getName());
    log.debug("description: " + authorities.get(0).getDescription());
    log.debug("");

    // GetParentCodeSystemConcepts
    log.debug("Call GetParentCodeSystemConcepts()");
    List<CodeSystemConcept> parentConcepts = client.getService().getParentCodeSystemConceptsByOidAndCode("2.16.840.1.113883.6.238",
        "1000-9").getCodeSystemConcepts();
    log.debug("parentConcepts length: " + parentConcepts.size());
    for (CodeSystemConcept e : parentConcepts) {
      log.debug("Concept Name: " + e.getName());
      log.debug("Concept Code: " + e.getConceptCode());
    }
    log.debug("");

    // GetChildCodeSystemConcepts
    log.debug("Call GetChildCodeSystemConcepts()");
    List<CodeSystemConcept> childConcepts = client.getService().getChildCodeSystemConceptsByOidAndCode("2.16.840.1.113883.6.238",
        "1000-9").getCodeSystemConcepts();
    log.debug("childConcepts length: " + childConcepts.size());
    for (CodeSystemConcept e : childConcepts) {
      log.debug("Concept Name: " + e.getName());
      log.debug("Concept Code: " + e.getConceptCode());
    }
    log.debug("");

    // Call getValueSetVersionIdsByCodeSystemConceptOidAndCode()
    List<String> vsvIds = client.getService().getValueSetVersionIdsByCodeSystemConceptOidAndCode("2.16.840.1.113883.6.93", "17045")
        .getIds();
    log.debug("Calling getValueSetVersionIdsByCodeSystemConceptOidAndCode() returned " + vsvIds.size()
        + " row(s):");
    for (String e : vsvIds) {
      log.debug("Id: " + e);
    }
    log.debug("");

    // Call getValueSetsByCodeSystemConceptOidAndCode()
    List<ValueSet> vsObjs = client.getService().getValueSetsByCodeSystemConceptOidAndCode("2.16.840.1.113883.6.93", "17045").getValueSets();
    log.debug("Calling getValueSetsByCodeSystemConceptOidAndCode() returned " + vsObjs.size()
        + " row(s):");
    for (ValueSet e : vsObjs) {
      log.debug("Id: " + e.getOid() + " - " + e.getName());
    }
    log.debug("");

    // Call getCodeSystemConceptPropertyValuesByOidAndCode()
    List<CodeSystemConceptPropertyValue> cscPropVals = client.getService().getCodeSystemConceptPropertyValuesByOidAndCode(
        "2.16.840.1.113883.6.93", "13257").getCodeSystemConceptPropertyValues();
    log.debug("Calling getCodeSystemConceptPropertyValuesByOidAndCode() returned " + cscPropVals.size()
        + " row(s):");
    for (CodeSystemConceptPropertyValue e : cscPropVals) {
      log.debug("PropName: " + e.getPropertyName());
      log.debug("PropValue: " + e.getStringValue() + e.getDateValue() + e.getNumericValue()
          + e.isBooleanValue());
      log.debug("ValueType: " + e.getValueType());
    }
    log.debug("");

    // Call getCodeSystemConceptsByCodeSystemOid
    // cscSearchResultDto = client.getService().getCodeSystemConceptsByCodeSystemOid("2.16.840.1.113883.6.92", 1, 5);
    cscSearchResultDto = client.getService().getCodeSystemConceptsByCodeSystemOid("2.16.2", 1, 500);
    log.debug("Calling getCodeSystemConceptsByCodeSystemOid() returned "
        + cscSearchResultDto.getTotalResults() + " matches");
    for (CodeSystemConcept e : cscSearchResultDto.getCodeSystemConcepts()) {
      log.debug("code system concept id:" + e.getConceptCode());
      log.debug("code system concept name:" + e.getName());
      log.debug("code system oid:" + e.getCodeSystemOid());
    }
    log.debug("");

    // Call findGroups
    GroupSearchCriteriaDto gSearchCritDto = new GroupSearchCriteriaDto();
    gSearchCritDto.setNameSearch(true);
    gSearchCritDto.setDefinitionSearch(false);
    gSearchCritDto.setSearchType(1);
    gSearchCritDto.setSearchText("Organism");

    GroupResultDto gSearchResultDto = client.getService().findGroups(gSearchCritDto, 1, 5);
    log.debug("Calling findGroups() returned " + gSearchResultDto.getTotalResults() + " matches");
    for (Group e : gSearchResultDto.getGroups()) {
      log.debug("group name:" + e.getName());
    }
    log.debug("");

    // Call getValueSetOidsByGroupId

    for (Group group : groups) {
      List<String> vsOids = client.getService().getValueSetOidsByGroupId(group.getId()).getIds();
      log.debug("Calling getValueSetOidsByGroupId() for " + group.getName() + " returned " + vsOids.size()
          + " row(s):");
      for (String e : vsOids) {
        log.debug("Id: " + e);
      }
    }
    log.debug("");

    // Call getValueSetVersionIdsByViewVersionId

    vsvIds = client.getService().getValueSetVersionIdsByViewVersionId("42ECC799-1284-DD11-B2C6-00188B398520").getIds();
    System.out
        .println("Calling getValueSetVersionIdsByViewVersionId() for 42ECC799-1284-DD11-B2C6-00188B398520 returned "
            + vsvIds.size() + " row(s):");
    log.debug("");

    // Call getViewVersionsByViewId

    viewVersions = client.getService().getViewVersionsByViewId("26ECC799-1284-DD11-B2C6-00188B398520").getViewVersions();
    log.debug("Calling getViewVersionsByViewId() for 26ECC799-1284-DD11-B2C6-00188B398520 returned "
        + viewVersions.size() + " row(s):");
    log.debug("");

    // Call findViewVersions
    log.debug("Calling findViewVersions()");
    ViewVersionSearchCriteriaDto vvSearchCrit = new ViewVersionSearchCriteriaDto();
    vvSearchCrit.setSearchText("CRA");
    vvSearchCrit.setVersionOption(3);
    vvSearchCrit.setSearchType(1);
    ViewVersionResultDto vvResultDto = client.getService().findViewVersions(vvSearchCrit, 1, 5);
    for (ViewVersion vv : vvResultDto.getViewVersions()) {
      log.debug("View Version.  ID: " + vv.getId());
    }

    // Call getAllCodeSystemPropertyDefinitions()
    log.debug("Calling getAllCodeSystemPropertyDefinitions()");
    CodeSystemPropertyDefinitionResultDto cspdResultDto = client.getService().getAllCodeSystemPropertyDefinitions();
    log.debug("Total Results:" + cspdResultDto.getTotalResults());
    log.debug("");


    //Validate Stuff
    log.debug("Calling validateMethods()");
    log.debug("True:" + client.getService().validateCodeSystem("2.16.840.1.113883.12.78").isValid());
    log.debug("False:" + client.getService().validateCodeSystem("2.16.840.1.113883.12.78.007.007").isValid());
    log.debug("True:" + client.getService().validateConceptCodeSystemMembership("2.16.840.1.113883.12.78", "HH").isValid());
    log.debug("False:" + client.getService().validateConceptCodeSystemMembership("2.16.840.1.113883.12.78.007.007", "HH").isValid());
    log.debug("False:" + client.getService().validateConceptCodeSystemMembership("2.16.840.1.113883.12.78", "XXXXXX1337").isValid());
    log.debug("True:" + client.getService().validateConceptValueSetMembership("2.16.840.1.113883.12.78", "HH", "2.16.840.1.114222.4.11.800", 1).isValid());
    log.debug("True:" + client.getService().validateConceptValueSetMembership("2.16.840.1.113883.12.78", "HH", "2.16.840.1.114222.4.11.800", null).isValid());
    log.debug("False:" + client.getService().validateConceptValueSetMembership("2.16.840.1.113883.12.78", "HHXL3TT", "2.16.840.1.114222.4.11.800", 1).isValid());
    log.debug("False:" + client.getService().validateConceptValueSetMembership("2.16.840.1.113883.12.78.007.007", "HH", "2.16.840.1.114222.4.11.800", 1).isValid());
    log.debug("False:" + client.getService().validateConceptValueSetMembership("2.16.840.1.113883.12.78", "HH", "2.16.840.1.114222.4.11.800.007.007", 1).isValid());
    log.debug("True:" + client.getService().validateValueSet("2.16.840.1.114222.4.11.800").isValid());
    log.debug("False:" + client.getService().validateValueSet("2.16.840.1.114222.4.11.800.007.007").isValid());
    log.debug("");

    log.debug("Calling getValueSetVersionByOidAndNumber");
    log.debug("1:" + client.getService().getValueSetVersionByValueSetOidAndVersionNumber("2.16.840.1.114222.4.11.834", 1).getValueSetVersion().getVersionNumber());
    log.debug("!1:" + client.getService().getValueSetVersionByValueSetOidAndVersionNumber("2.16.840.1.114222.4.11.834", null).getValueSetVersion().getVersionNumber());
    log.debug("");

    log.debug("Calling getViewVersionByViewNameAndVersionNumber");
    log.debug("1:" + client.getService().getViewVersionByViewNameAndVersionNumber("CRA Referral Request", 1).getViewVersion().getVersionNumber());
    log.debug("!1:" + client.getService().getViewVersionByViewNameAndVersionNumber("CRA Referral Request", null).getViewVersion().getVersionNumber());
    log.debug("");

    log.debug("Calling getViewByName");
    log.debug("CRA Referral Request:" + client.getService().getViewByName("CRA Referral Request").getView().getName());
    log.debug("");

    log.debug("Calling getViewVersionsByViewName");
    vvResultDto = client.getService().getViewVersionsByViewName("CRA Referral Request");
    log.debug("size > 0:" + vvResultDto.getViewVersions().size());
    log.debug("");


    //Test get group by name

    log.debug("*******END*********");

  }

  private static String getElapsedMiliSeconds(Calendar cal1, Calendar cal2){
    long cal1Time = cal1.getTimeInMillis();
    long cal2Time = cal2.getTimeInMillis();
    long diffTime = cal2Time - cal1Time;
    return "" + diffTime;
  }


}
