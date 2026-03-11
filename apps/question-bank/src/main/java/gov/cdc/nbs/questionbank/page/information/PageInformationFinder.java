package gov.cdc.nbs.questionbank.page.information;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.page.SelectableCondition;
import gov.cdc.nbs.questionbank.page.SelectableEventType;
import gov.cdc.nbs.questionbank.page.SelectableMessageMappingGuide;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class PageInformationFinder {
  private static final QWaTemplate page = QWaTemplate.waTemplate;
  private static final QCodeValueGeneral eventType = new QCodeValueGeneral("event_type");
  private static final QCodeValueGeneral mmg = QCodeValueGeneral.codeValueGeneral;
  private static final QPageCondMapping conditionMapping = QPageCondMapping.pageCondMapping;
  private static final QConditionCode condition = QConditionCode.conditionCode;
  private static final SelectableConditionMerger merger = new SelectableConditionMerger();

  private final JPAQueryFactory factory;

  public PageInformationFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  Optional<PageInformation> find(final long id) {
    // Find page
    PageInformation pageInfo =
        toPageInfo(
            factory
                .select(
                    page.id,
                    eventType.id.code,
                    eventType.codeShortDescTxt,
                    mmg.id.code,
                    mmg.codeShortDescTxt,
                    page.templateNm,
                    page.datamartNm,
                    page.descTxt)
                .from(page)
                .join(eventType)
                .on(
                    eventType
                        .id
                        .codeSetNm
                        .eq("BUS_OBJ_TYPE")
                        .and(eventType.id.code.eq(page.busObjType)))
                .join(mmg)
                .on(
                    mmg.id
                        .codeSetNm
                        .eq("NBS_MSG_PROFILE")
                        .and(mmg.id.code.eq(page.nndEntityIdentifier)))
                .where(page.id.eq(id))
                .fetchFirst());

    if (pageInfo != null) {
      pageInfo.conditions().addAll(getConditions(id));
    }
    return Optional.ofNullable(pageInfo);
  }

  private List<SelectableCondition> getConditions(Long id) {
    return factory
        .select(condition.id, condition.conditionShortNm, page.publishIndCd)
        .from(page)
        .leftJoin(conditionMapping)
        .on(conditionMapping.waTemplateUid.id.eq(page.id))
        .leftJoin(condition)
        .on(condition.id.eq(conditionMapping.conditionCd))
        .where(page.formCd.eq(JPAExpressions.select(page.formCd).from(page).where(page.id.eq(id))))
        .fetch()
        .stream()
        .map(this::toSelectableCondition)
        .collect(Accumulator.collecting(SelectableCondition::value, merger::merge));
  }

  private SelectableCondition toSelectableCondition(Tuple row) {
    return new SelectableCondition(
        row.get(condition.id),
        row.get(condition.conditionShortNm),
        Character.valueOf('T').equals(row.get(page.publishIndCd)));
  }

  private PageInformation toPageInfo(Tuple row) {
    if (row == null) {
      return null;
    }
    return new PageInformation(
        row.get(page.id),
        new SelectableEventType(row.get(eventType.id.code), row.get(eventType.codeShortDescTxt)),
        new SelectableMessageMappingGuide(row.get(mmg.id.code), row.get(mmg.codeShortDescTxt)),
        row.get(page.templateNm),
        row.get(page.datamartNm),
        row.get(page.descTxt),
        new ArrayList<>());
  }
}
