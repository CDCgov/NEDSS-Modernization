package gov.cdc.nbs.questionbank.valueset.concept;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QCodeset;
import gov.cdc.nbs.questionbank.entity.QWaUiMetadata;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class ConceptFinder {
  private static final QWaUiMetadata uiMetadata = QWaUiMetadata.waUiMetadata;
  private static final QCodeset codeset = QCodeset.codeset;
  private static final QCodeValueGeneral codeValueGeneralTable = QCodeValueGeneral.codeValueGeneral;
  private static final QCodeValueGeneral cvg2 = new QCodeValueGeneral("cvg2");

  private final JPAQueryFactory factory;

  public ConceptFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  public List<Concept> findByQuestionIdentifier(String questionIdentifier, long page) {
    String codeSetName =
        factory
            .select(codeset.id.codeSetNm)
            .from(codeset)
            .where(
                codeset.codeSetGroup.id.eq(
                    JPAExpressions.select(uiMetadata.codeSetGroupId)
                        .from(uiMetadata)
                        .where(
                            uiMetadata
                                .questionIdentifier
                                .eq(questionIdentifier)
                                .and(uiMetadata.waTemplateUid.id.eq(page)))))
            .fetchFirst();
    if (codeSetName == null) {
      return new ArrayList<>();
    }
    return find(codeSetName);
  }

  public List<Concept> find(String codeSetNm) {
    return select()
        .where(codeValueGeneralTable.id.codeSetNm.equalsIgnoreCase(codeSetNm))
        .orderBy(codeValueGeneralTable.codeShortDescTxt.asc())
        .fetch()
        .stream()
        .map(this::toConcept)
        .toList();
  }

  public Optional<Concept> find(String codeset, String code) {
    Tuple row =
        select()
            .where(
                codeValueGeneralTable
                    .id
                    .codeSetNm
                    .equalsIgnoreCase(codeset)
                    .and(codeValueGeneralTable.id.code.eq(code)))
            .orderBy(codeValueGeneralTable.codeShortDescTxt.asc())
            .fetchOne();
    return Optional.ofNullable(this.toConcept(row));
  }

  public Page<Concept> find(String codeSetNm, Pageable pageable) {
    List<String> found =
        select()
            .where(codeValueGeneralTable.id.codeSetNm.equalsIgnoreCase(codeSetNm))
            .orderBy(resolveOrderBy(pageable.getSort()))
            .fetch()
            .stream()
            .map(r -> r.get(codeValueGeneralTable.id.code))
            .toList();

    int total = found.size();

    List<String> ids =
        found.subList(
            (int) pageable.getOffset(),
            Math.min(total, (int) pageable.getOffset() + pageable.getPageSize()));

    List<Concept> concepts =
        select()
            .where(
                codeValueGeneralTable
                    .id
                    .code
                    .in(ids)
                    .and(codeValueGeneralTable.id.codeSetNm.eq(codeSetNm)))
            .orderBy(resolveOrderBy(pageable.getSort()))
            .fetch()
            .stream()
            .map(this::toConcept)
            .toList();

    return new PageImpl<>(concepts, pageable, total);
  }

  private OrderSpecifier<?> resolveOrderBy(Sort sort) {
    if (sort == null || sort.isEmpty() || sort.isUnsorted()) {
      return codeValueGeneralTable.id.codeSetNm.asc();
    } else {
      Order order = sort.toList().get(0);
      var path =
          switch (order.getProperty()) {
            case "code" -> codeValueGeneralTable.id.code;
            case "display" -> codeValueGeneralTable.codeShortDescTxt;
            case "conceptCode" -> codeValueGeneralTable.conceptCode;
            case "effectiveDate" -> codeValueGeneralTable.effectiveFromTime;
            default -> codeValueGeneralTable.id.codeSetNm;
          };
      return order.isAscending() ? path.asc() : path.desc();
    }
  }

  private JPAQuery<Tuple> select() {
    return factory
        .select(
            codeValueGeneralTable.id.codeSetNm, // valueset
            codeValueGeneralTable.id.code, // local code
            codeValueGeneralTable.codeDescTxt, // long name
            codeValueGeneralTable.codeShortDescTxt, // display
            codeValueGeneralTable.effectiveFromTime,
            codeValueGeneralTable.effectiveToTime,
            codeValueGeneralTable.statusCd, // A or I
            codeValueGeneralTable.adminComments,
            // messaging fields
            codeValueGeneralTable.conceptCode,
            codeValueGeneralTable.conceptNm,
            codeValueGeneralTable.conceptPreferredNm,
            JPAExpressions.select(cvg2.id.code) // Get the code for the associated codeSystem
                .from(cvg2)
                .where(
                    cvg2.id
                        .codeSetNm
                        .eq("CODE_SYSTEM")
                        .and(cvg2.codeDescTxt.eq(codeValueGeneralTable.codeSystemCd))))
        .from(codeValueGeneralTable);
  }

  private Concept toConcept(Tuple row) {
    if (row == null) {
      return null;
    }
    return new Concept(
        row.get(codeValueGeneralTable.id.codeSetNm),
        row.get(codeValueGeneralTable.id.code),
        row.get(codeValueGeneralTable.codeDescTxt),
        row.get(codeValueGeneralTable.codeShortDescTxt),
        row.get(codeValueGeneralTable.effectiveFromTime),
        row.get(codeValueGeneralTable.effectiveToTime),
        Character.valueOf('A').equals(row.get(codeValueGeneralTable.statusCd))
            ? Status.ACTIVE
            : Status.INACTIVE,
        row.get(codeValueGeneralTable.adminComments),
        row.get(codeValueGeneralTable.conceptCode),
        row.get(codeValueGeneralTable.conceptNm),
        row.get(codeValueGeneralTable.conceptPreferredNm),
        row.get(11, String.class));
  }
}
