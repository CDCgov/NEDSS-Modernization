package gov.cdc.nbs.questionbank.valueset;

import java.util.List;
import org.springframework.stereotype.Component;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.QCodeset;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetOption;

@Component
public class ValueSetOptionFinder {
  private static final QCodeset valuesetTable = QCodeset.codeset;
  private static final QCodeSetGroupMetadatum metadataTable = QCodeSetGroupMetadatum.codeSetGroupMetadatum;
  final JPAQueryFactory factory;

  public ValueSetOptionFinder(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  public List<ValueSetOption> findValueSetOptions() {
    return factory.select(
        metadataTable.id,
        metadataTable.codeSetShortDescTxt,
        metadataTable.codeSetNm)
        .from(metadataTable)
        .where(metadataTable.ldfPicklistIndCd.eq('Y')
            .and(metadataTable.id.in(
                JPAExpressions.select(valuesetTable.codeSetGroup.id)
                    .from(valuesetTable)
                    .where(valuesetTable.id.classCd.eq("code_value_general").and(valuesetTable.statusCd.eq("A"))))))
        .orderBy(metadataTable.codeSetShortDescTxt.asc())
        .fetch()
        .stream()
        .map(this::toOption)
        .toList();
  }

  private ValueSetOption toOption(Tuple row) {
    return new ValueSetOption(
        row.get(metadataTable.codeSetShortDescTxt),
        row.get(metadataTable.id).toString(),
        row.get(metadataTable.codeSetNm));
  }

}
