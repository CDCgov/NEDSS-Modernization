package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.ValidateSubsectionException;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SubSectionValidator {

  private final EntityManager entityManager;

  private static final long TAB = 1010l;
  private static final long SECTION = 1015l;
  private static final long SUB_SECTION = 1016l;
  public static final long ROLLINGNOTE = 1019l;

  public SubSectionValidator(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void validateIfCanBeGrouped(long pageId, long subsectionId) {
    List<WaUiMetadata> subsectionElements = getSubsectionElements(pageId, subsectionId);

    for (WaUiMetadata element : subsectionElements) {
      if (element.getDataLocation() == null || !element.getDataLocation().contains("ANSWER_TXT"))
        throw new ValidateSubsectionException(
            "Subsection includes questions that are considered 'core'");
      if (element.getPublishIndCd() != null && element.getPublishIndCd() == 'T')
        throw new ValidateSubsectionException(
            "Subsection includes a question(s) that has already been published.");
      if (element.getNbsUiComponentUid().equals(ROLLINGNOTE) && subsectionElements.size() > 1)
        throw new ValidateSubsectionException(
            """
            Subsection can only have the Repeating Note field \
            and no other fields in the repeating block subsection.\
            """);
    }
  }

  List<WaUiMetadata> getSubsectionElements(long pageId, long subsectionId) {
    List<WaUiMetadata> subsectionElements = new ArrayList<>();
    List<Long> containers = Arrays.asList(SUB_SECTION, SECTION, TAB);
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) throw new PageNotFoundException(pageId);

    WaUiMetadata subsection =
        page.getUiMetadata().stream()
            .filter(w -> w.getId() == subsectionId && w.getNbsUiComponentUid().equals(SUB_SECTION))
            .findFirst()
            .orElseThrow(
                () ->
                    new ValidateSubsectionException(
                        "Failed to find subsection with id: " + subsectionId));

    for (WaUiMetadata w : page.getUiMetadata()) {
      if (w.getOrderNbr() > subsection.getOrderNbr()
          && containers.contains(w.getNbsUiComponentUid())) break;
      if (w.getOrderNbr() > subsection.getOrderNbr()) subsectionElements.add(w);
    }
    return subsectionElements;
  }
}
