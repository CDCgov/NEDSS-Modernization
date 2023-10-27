package gov.cdc.nbs.questionbank.page;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PageSummaryMapper {
  private static final QWaTemplate waTemplate = QWaTemplate.waTemplate;
  private static final QAuthUser authUser = QAuthUser.authUser;
  private static final QConditionCode conditionCode = QConditionCode.conditionCode;
  private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;


  public List<PageSummary> map(List<Tuple> tuples) {
    return tuples.stream()
        .map(this::toPageSummary)
        .collect(
            Collectors.groupingBy(PageSummary::id,
                LinkedHashMap::new, // preserves ordering
                Collectors.collectingAndThen(
                    Collectors.reducing(this::merge),
                    Optional::get
                )
            )
        )
        .values()
        .stream()
        .toList();
  }

  /**
   * The query contains a join on the page_cond_mapping table that causes each condition associated with a page to
   * generate an extra row. The merge function simply combines the condition lists and returns the 'left' entry
   */
  private PageSummary merge(final PageSummary left, final PageSummary right) {
    Collection<ConditionSummary> conditions = CollectionMerge.merged(left.conditions(), right.conditions());

    return new PageSummary(
        left.id(),
        left.eventType(),
        left.name(),
        left.description(),
        left.status(),
        left.messageMappingGuide(),
        conditions,
        left.lastUpdate(),
        left.lastUpdateBy()
    );
  }

  PageSummary toPageSummary(final Tuple tuple) {
    Long identifier = tuple.get(waTemplate.id);
    List<ConditionSummary> conditions = List.of(asCondition(tuple));
    PageSummary.EventType eventType = getEventType(tuple.get(waTemplate.busObjType));

    return new PageSummary(
        identifier,
        eventType,
        tuple.get(waTemplate.templateNm),
        tuple.get(waTemplate.descTxt),
        getStatus(tuple),
        new PageSummary.MessageMappingGuide(
            tuple.get(waTemplate.nndEntityIdentifier),
            tuple.get(codeValueGeneral.codeShortDescTxt)
        ),
        conditions,
        tuple.get(waTemplate.lastChgTime),
        getLastUpdatedBy(tuple));
  }

  private ConditionSummary asCondition(final Tuple tuple) {
    return new ConditionSummary(
        tuple.get(conditionCode.id),
        tuple.get(conditionCode.conditionShortNm)
    );
  }

  /**
   * Sets the page status as defined in legacy code PageManagementActionUtil line #1965-1970
   * <p>
   * If a page has status "Draft" and no publish version exists, then set to "Initial Draft", if a published version
   * does exist, then set to "Published with Draft"
   */
  private String getStatus(final Tuple tuple) {
    String templateType = tuple.get(waTemplate.templateType);
    Integer publishVersion = tuple.get(waTemplate.publishVersionNbr);
    if ("Draft".equalsIgnoreCase(templateType)) {
      if (publishVersion == null) {
        return "Initial Draft";
      } else {
        return "Published with Draft";
      }
    }
    return templateType;
  }

  private String getLastUpdatedBy(Tuple tuple) {
    return tuple.get(authUser.userFirstNm) + " " + tuple.get(authUser.userLastNm);
  }

  private PageSummary.EventType getEventType(final String type) {
    String display =
        switch (type) {
          case "INV" -> "Investigation";
          case "CON" -> "Contact";
          case "VAC" -> "Vaccination";
          case "IXS" -> "Interview";
          case "SUS" -> "Lab Susceptibility";
          case "LAB" -> "Lab Report";
          case "ISO" -> "Lab Isolate Tracking";
          default -> type;
        };
    return new PageSummary.EventType(type, display);
  }
}
