package gov.cdc.nbs.questionbank.valueset;

import java.util.List;
import org.springframework.stereotype.Component;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

@Component
public class ConceptFinder {
  private static final QCodeValueGeneral codeValueGeneralTable = QCodeValueGeneral.codeValueGeneral;

  private final JPAQueryFactory factory;

  public ConceptFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  public List<Concept> find(String codeSetNm) {
    return factory.select(
        codeValueGeneralTable.id.code,
        codeValueGeneralTable.id.codeSetNm,
        codeValueGeneralTable.codeShortDescTxt,
        codeValueGeneralTable.codeDescTxt,
        codeValueGeneralTable.conceptCode,
        codeValueGeneralTable.conceptPreferredNm,
        codeValueGeneralTable.codeSystemDescTxt,
        codeValueGeneralTable.conceptStatusCd,
        codeValueGeneralTable.effectiveFromTime,
        codeValueGeneralTable.effectiveToTime)
        .from(codeValueGeneralTable)
        .where(codeValueGeneralTable.id.codeSetNm.equalsIgnoreCase(codeSetNm))
        .orderBy(codeValueGeneralTable.codeShortDescTxt.asc())
        .fetch()
        .stream()
        .map(this::toConcept)
        .toList();
  }

  private Concept toConcept(Tuple row) {
    return new Concept(
        row.get(codeValueGeneralTable.id.code),
        row.get(codeValueGeneralTable.id.codeSetNm),
        row.get(codeValueGeneralTable.codeShortDescTxt),
        row.get(codeValueGeneralTable.codeDescTxt),
        row.get(codeValueGeneralTable.conceptCode),
        row.get(codeValueGeneralTable.conceptPreferredNm),
        row.get(codeValueGeneralTable.codeSystemDescTxt),
        row.get(codeValueGeneralTable.conceptStatusCd),
        row.get(codeValueGeneralTable.effectiveFromTime),
        row.get(codeValueGeneralTable.effectiveToTime));
  }
}
