package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QLanguageCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
class PrimaryLanguageValueSetFinder {

  private final JPAQueryFactory factory;
  private final QLanguageCode values;
  private final String primaryLanguage;

  PrimaryLanguageValueSetFinder(final JPAQueryFactory factory,
      @Value("${nbs.primary-language:English}") final String primaryLanguage) {
    this.factory = factory;
    this.values = QLanguageCode.languageCode;
    this.primaryLanguage = primaryLanguage;
  }

  Collection<CodedValue> all() {
    List<CodedValue> recs = new java.util.ArrayList<>(this.factory.select(
        values.id,
        values.codeShortDescTxt).from(values)
        .orderBy(new OrderSpecifier<>(Order.ASC, values.codeShortDescTxt))
        .fetch()
        .stream()
        .map(this::map)
        .toList());
    List<CodedValue> result = new ArrayList<>();
    Optional<CodedValue> primaryLanguageCodededValue =
        recs.stream().filter(item -> item.name().equals(primaryLanguage)).findFirst();
    if (primaryLanguageCodededValue.isPresent()) {
      result.add(primaryLanguageCodededValue.get());
      recs.removeIf(item -> item.name().equals(primaryLanguage));
    }
    result.addAll(recs);
    return result;
  }

  private CodedValue map(final Tuple tuple) {
    String value = tuple.get(values.id);
    String name = StandardNameFormatter.formatted(tuple.get(values.codeShortDescTxt));
    return new CodedValue(value, name);
  }
}
