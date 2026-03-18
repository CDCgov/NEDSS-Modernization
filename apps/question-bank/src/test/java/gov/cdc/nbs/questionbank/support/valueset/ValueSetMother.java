package gov.cdc.nbs.questionbank.support.valueset;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralId;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ValueSetMother {
  private static final String CODE_SET_NAME = "TestCodeset";
  private int identifier = 9910;

  private final ValueSetRepository valueSetRepository;

  private final CodeValueGeneralRepository conceptRepository;

  private final CodesetGroupMetadatumRepository codeSetGrpMetaRepository;

  private final List<Codeset> allValueSets = new ArrayList<>();
  private final HashMap<String, List<CodeValueGeneral>> concepts = new HashMap<>();

  ValueSetMother(
      final ValueSetRepository valueSetRepository,
      final CodeValueGeneralRepository conceptRepository,
      final CodesetGroupMetadatumRepository codeSetGrpMetaRepository) {
    this.valueSetRepository = valueSetRepository;
    this.conceptRepository = conceptRepository;
    this.codeSetGrpMetaRepository = codeSetGrpMetaRepository;
  }

  public void clean() {
    valueSetRepository.deleteAll();
    codeSetGrpMetaRepository.deleteAll();
    conceptRepository.deleteAllByCodesetName(CODE_SET_NAME);
    concepts.clear();
    allValueSets.clear();
  }

  public Codeset valueSet() {
    return allValueSets.stream()
        .filter(Objects::nonNull)
        .findFirst()
        .orElseGet(this::valueSetWithConcepts);
  }

  public Codeset valueSet(String name) {
    return allValueSets.stream()
        .filter(v -> name.equals(v.getValueSetNm()))
        .findFirst()
        .orElseGet(() -> valueset(name));
  }

  public Codeset one() {
    return allValueSets.stream()
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No value sets are available"));
  }

  public List<CodeValueGeneral> concept(String codeSetName) {
    return concepts.get(codeSetName);
  }

  public Codeset setActive(Codeset valueset, boolean active) {
    valueset.setStatusCd(active ? "A" : "I");
    return valueSetRepository.save(valueset);
  }

  private Codeset valueset(String name) {
    Instant now = Instant.now();
    Codeset codeset = new Codeset();
    codeset.setId(new CodesetId("code_value_general", name));
    codeset.setValueSetNm(name);
    codeset.setStatusCd("A");
    codeset.setLdfPicklistIndCd('Y');
    codeset.setStatusToTime(now);
    codeset.setCodeSetDescTxt(name);
    codeset.setAssigningAuthorityCd("L");
    codeset.setValueSetTypeCd("LOCAL");
    codeset.setAddTime(now);
    codeset.setAddUserId(99999999L);
    codeset.setEffectiveFromTime(now);
    codeset = valueSetRepository.save(codeset);
    codeset = createMetadataEntry(codeset);
    allValueSets.add(codeset);
    return codeset;
  }

  private Codeset valueSetWithConcepts() {
    Instant now = Instant.now();
    Codeset codeset = new Codeset();
    codeset.setId(new CodesetId("code_value_general", CODE_SET_NAME));
    codeset.setStatusCd("A");
    codeset.setStatusToTime(now);
    codeset.setCodeSetDescTxt(CODE_SET_NAME);
    codeset.setAssigningAuthorityCd("L");
    codeset.setValueSetTypeCd("LOCAL");
    codeset.setAddTime(now);
    codeset.setAddUserId(99999999L);
    codeset.setEffectiveFromTime(now);
    codeset = valueSetRepository.save(codeset);

    CodeValueGeneral yes = new CodeValueGeneral();
    yes.setId(new CodeValueGeneralId(CODE_SET_NAME, "Yes"));
    yes.setCodeDescTxt("Yes");
    yes.setCodeShortDescTxt("Yes");
    yes.setCodeSystemCd("2.16.840.1.113883.12.136");
    yes.setCodeSystemDescTxt("Yes/No Indicator (HL7)");
    yes.setEffectiveFromTime(now);
    yes.setIsModifiableInd('Y');
    yes.setStatusCd('A');
    yes.setStatusTime(now);
    yes.setConceptTypeCd("PHIN");
    yes.setConceptPreferredNm("Yes");
    yes.setConceptStatusCd("Active");
    yes.setConceptStatusTime(now);
    yes.setAddTime(now);
    yes.setAddUserId(99999999L);
    yes = conceptRepository.save(yes);

    CodeValueGeneral no = new CodeValueGeneral();
    no.setId(new CodeValueGeneralId(CODE_SET_NAME, "No"));
    no.setCodeDescTxt("No");
    no.setCodeShortDescTxt("No");
    no.setCodeSystemCd("2.16.840.1.113883.12.136");
    no.setCodeSystemDescTxt("Yes/No Indicator (HL7)");
    no.setEffectiveFromTime(now);
    no.setIsModifiableInd('Y');
    no.setStatusCd('A');
    no.setStatusTime(now);
    no.setConceptTypeCd("PHIN");
    no.setConceptPreferredNm("No");
    no.setConceptStatusCd("Active");
    no.setConceptStatusTime(now);
    no.setAddTime(now);
    no.setAddUserId(99999999L);
    no = conceptRepository.save(no);

    List<CodeValueGeneral> conceptList = new ArrayList<>();
    conceptList.add(yes);
    conceptList.add(no);

    concepts.put(CODE_SET_NAME, conceptList);
    allValueSets.add(codeset);
    return codeset;
  }

  public Codeset createMetadataEntry(Codeset valueset) {
    CodeSetGroupMetadatum codeGrp =
        new CodeSetGroupMetadatum(
            getCodeSetGroupID(),
            valueset.getCodeSetDescTxt(),
            valueset.getValueSetNm(),
            valueset.getValueSetNm());

    codeGrp.setLdfPicklistIndCd(valueset.getLdfPicklistIndCd());
    codeGrp = codeSetGrpMetaRepository.save(codeGrp);
    valueset.setCodeSetGroup(codeGrp);
    valueset = valueSetRepository.save(valueset);
    return valueset;
  }

  private long getCodeSetGroupID() {
    return identifier += 10;
  }

  public void addConcept(Codeset codeset, String name, String value) {
    Instant now = Instant.now();
    CodeValueGeneral concept = new CodeValueGeneral();
    concept.setId(new CodeValueGeneralId(codeset.getId().getCodeSetNm(), value));
    concept.setCodeDescTxt(name);
    concept.setCodeShortDescTxt(name);
    concept.setCodeSystemCd(codeset.getAssigningAuthorityCd());
    concept.setCodeSystemDescTxt(codeset.getAssigningAuthorityDescTxt());
    concept.setEffectiveFromTime(now);
    concept.setIsModifiableInd('Y');
    concept.setStatusCd('A');
    concept.setStatusTime(now);
    concept.setConceptTypeCd("LOCAL");
    concept.setConceptPreferredNm(name);
    concept.setConceptStatusCd("Active");
    concept.setConceptStatusTime(now);
    concept.setAddTime(now);
    concept.setAddUserId(99999999L);
    conceptRepository.save(concept);
  }
}
