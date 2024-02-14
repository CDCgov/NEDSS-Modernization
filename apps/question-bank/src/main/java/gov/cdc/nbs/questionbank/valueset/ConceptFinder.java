package gov.cdc.nbs.questionbank.valueset;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;

@Component
public class ConceptFinder {
  private static final QCodeValueGeneral codeValueGeneralTable = QCodeValueGeneral.codeValueGeneral;
  private static final QCodeValueGeneral cvg2 = new QCodeValueGeneral("cvg2");

  private final JPAQueryFactory factory;

  public ConceptFinder(final JPAQueryFactory factory) {
    this.factory = factory;
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
    Tuple row = select()
        .where(codeValueGeneralTable.id.codeSetNm.equalsIgnoreCase(codeset).and(codeValueGeneralTable.id.code.eq(code)))
        .orderBy(codeValueGeneralTable.codeShortDescTxt.asc())
        .fetchOne();
    return Optional.ofNullable(this.toConcept(row));
  }

  private JPAQuery<Tuple> select() {
    return factory.select(
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
            .where(cvg2.id.codeSetNm.eq("CODE_SYSTEM")
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
        Character.valueOf('A').equals(row.get(codeValueGeneralTable.statusCd)) ? Status.ACTIVE : Status.INACTIVE,
        row.get(codeValueGeneralTable.adminComments),
        row.get(codeValueGeneralTable.conceptCode),
        row.get(codeValueGeneralTable.conceptNm),
        row.get(codeValueGeneralTable.conceptPreferredNm),
        row.get(11, String.class));
  }
}
