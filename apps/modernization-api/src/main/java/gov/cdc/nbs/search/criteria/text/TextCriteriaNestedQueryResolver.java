package gov.cdc.nbs.search.criteria.text;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import gov.cdc.nbs.search.WildCards;
import org.apache.commons.codec.language.Soundex;

public class TextCriteriaNestedQueryResolver {

  public static  BoolQuery equalTo(final String path, final String name, final String value) {
    return BoolQuery.of(
        bool -> bool.must(
            should -> should.nested(
                nested -> nested.path(path)
                    .query(
                        query -> query.bool(
                            field -> field.must(
                                must -> must.match(
                                    match -> match
                                        .field(name)
                                        .query(value)
                                )
                            )
                        )
                    )
            )
        )
    );
  }

  public static BoolQuery notEquals(final String path, final String name, final String value) {
    return BoolQuery.of(
        bool -> bool.mustNot(
            mustNot -> mustNot.nested(
                nested -> nested.path(path)
                    .query(
                        query -> query.bool(
                            field -> field.must(
                                must -> must.match(
                                    match -> match
                                        .field(name)
                                        .query(value)
                                )
                            )
                        )
                    )
            )
        )
    );
  }

  public static BoolQuery contains(final String path, final String name, final String value) {
    String adjusted = WildCards.contains(value);
    return BoolQuery.of(
        bool -> bool.should(
            should -> should.nested(
                nested -> nested.path(path)
                    .query(
                        query -> query.bool(
                            field -> field.must(
                                must -> must.wildcard(
                                    match -> match
                                        .field(name)
                                        .value(adjusted)
                                )
                            )
                        )
                    )
            )
        )
    );
  }

  public static BoolQuery startsWith(final String path, final String name, final String value) {
    String adjusted = WildCards.startsWith(value);
    return BoolQuery.of(
        bool -> bool.should(
            should -> should.nested(
                nested -> nested.path(path)
                    .query(
                        query -> query.bool(
                            legal -> legal.must(
                                must -> must.wildcard(
                                    match -> match
                                        .field(name)
                                        .value(adjusted)
                                )
                            )
                        )
                    )
            )
        )
    );
  }

  public static BoolQuery soundLike(final String path, final String name, final String value) {
    String adjusted = Holder.soundex.encode(value);
    return BoolQuery.of(
        bool -> bool.should(
            should -> should.nested(
                nested -> nested.path(path)
                    .query(
                        query -> query.bool(
                            legal -> legal.must(
                                must -> must.term(
                                    term -> term
                                        .field(name)
                                        .value(adjusted)
                                )
                            )
                        )
                    )
            )
        )
    );
  }

  private TextCriteriaNestedQueryResolver() {
    //  static
  }

  private static class Holder {
    private static final Soundex soundex = new Soundex();
  }

}
