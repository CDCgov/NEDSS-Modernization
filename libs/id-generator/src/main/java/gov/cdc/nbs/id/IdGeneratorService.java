package gov.cdc.nbs.id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IdGeneratorService {

  private final LocalUidGeneratorRepository localUidGeneratorRepository;

  public IdGeneratorService(final LocalUidGeneratorRepository localUidGeneratorRepository) {
    this.localUidGeneratorRepository = localUidGeneratorRepository;
  }

  /**
   * Returns the next valid Id for the given EntityType. Increments the Id seed for the EntityType
   * in the Local_UID_generator table
   *
   * @param type
   * @return
   */
  @Transactional
  public GeneratedId getNextValidId(EntityType type) {
    if (type == null) {
      throw new IllegalArgumentException("EntityType must not be null");
    }

    LocalUidGenerator uidEntry;
    if (type.equals(EntityType.NBS)) {
      // The 'NBS' type has a varying class_name_cd depending on the jurisdiction,
      // but it has a consistent and unique type_cd
      uidEntry =
          localUidGeneratorRepository
              .findByTypeCd(type.toString())
              .orElseThrow(() -> new IdGenerationException(type));
    } else {
      uidEntry =
          localUidGeneratorRepository
              .findById(type.toString())
              .orElseThrow(() -> new IdGenerationException(type));
    }
    var id =
        GeneratedId.builder()
            .id(uidEntry.getSeedValueNbr())
            .prefix(uidEntry.getUidPrefixCd())
            .suffix(uidEntry.getUidSuffixCd())
            .build();

    // Increment the Id in the database
    uidEntry.setSeedValueNbr(uidEntry.getSeedValueNbr() + 1);
    localUidGeneratorRepository.save(uidEntry);

    return id;
  }

  @Builder
  @Data
  @AllArgsConstructor
  public static class GeneratedId {
    private Long id;
    private String prefix;
    private String suffix;

    public String toLocalId() {
      return prefix + id.toString() + suffix;
    }

    public GeneratedId(Long id) {
      this.id = id;
    }
  }

  /**
   * Matches the class_name_cd column of the Local_UID_generator table, other than the NBS entry.
   * Which references the type_cd column as the class_name_cd for type NBS is dynamic based on the
   * jurisdiction
   */
  public enum EntityType {
    NBS,
    CLINICAL_DOCUMENT,
    COINFECTION_GROUP,
    CS_REPORT,
    CT_CONTACT,
    DEDUPLICATION_LOG,
    EPILINK,
    GEOCODING,
    GEOCODING_LOG,
    GROUP,
    INTERVENTION,
    INTERVIEW,
    MATERIAL,
    NBS_DOCUMENT,
    NBS_QUESTION_ID_LDF,
    NBS_QUESTION_LDF,
    NBS_UIMETEDATA_LDF,
    NND_METADATA,
    NON_LIVING_SUBJECT,
    NOTIFICATION,
    OBSERVATION,
    ORGANIZATION,
    PAGE,
    PATIENT_ENCOUNTER,
    PERSON,
    PERSON_GROUP,
    PLACE,
    PUBLIC_HEALTH_CASE,
    RDB_METADATA,
    REFERRAL,
    REPORT,
    REPORTDATASOURCE,
    REPORTDATASOURCECOLUMN,
    REPORTDISPLAYCOLUMN,
    REPORTFILTER,
    REPORTFILTERCODE,
    REPORTFILTERVALUE,
    SECURITY_LOG,
    TREATMENT,
    WORKUP
  }
}
