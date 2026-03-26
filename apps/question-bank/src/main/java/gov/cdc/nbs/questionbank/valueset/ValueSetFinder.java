package gov.cdc.nbs.questionbank.valueset;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.QCodeset;
import gov.cdc.nbs.questionbank.valueset.exception.ValuesetNotFoundException;
import gov.cdc.nbs.questionbank.valueset.model.Valueset;
import org.springframework.stereotype.Component;

@Component
public class ValueSetFinder {
  private static final QCodeset codesetTable = QCodeset.codeset;
  private static final QCodeSetGroupMetadatum metadataTable =
      QCodeSetGroupMetadatum.codeSetGroupMetadatum;

  private final JPAQueryFactory factory;

  public ValueSetFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  public Valueset find(String codesetNm) {
    Tuple row =
        factory
            .select(
                metadataTable.id,
                codesetTable.valueSetTypeCd,
                codesetTable.id.codeSetNm,
                codesetTable.valueSetNm,
                codesetTable.codeSetDescTxt)
            .from(codesetTable)
            .join(metadataTable)
            .on(metadataTable.id.eq(codesetTable.codeSetGroup.id))
            .where(
                codesetTable
                    .id
                    .classCd
                    .eq("code_value_general")
                    .and(codesetTable.id.codeSetNm.eq(codesetNm)))
            .fetchOne();
    if (row == null) {
      throw new ValuesetNotFoundException(codesetNm);
    }
    return this.toValueset(row);
  }

  private Valueset toValueset(Tuple row) {
    return new Valueset(
        row.get(metadataTable.id),
        row.get(codesetTable.valueSetTypeCd),
        row.get(codesetTable.id.codeSetNm),
        row.get(codesetTable.valueSetNm),
        row.get(codesetTable.codeSetDescTxt));
  }
}
