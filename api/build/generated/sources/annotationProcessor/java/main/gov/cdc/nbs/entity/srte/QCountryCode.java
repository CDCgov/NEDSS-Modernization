package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCountryCode is a Querydsl query type for CountryCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCountryCode extends EntityPathBase<CountryCode> {

    private static final long serialVersionUID = -1893486475L;

    public static final QCountryCode countryCode = new QCountryCode("countryCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeDescTxt = createString("codeDescTxt");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath excludedTxt = createString("excludedTxt");

    public final StringPath id = createString("id");

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final StringPath keyInfoTxt = createString("keyInfoTxt");

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath parentIsCd = createString("parentIsCd");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QCountryCode(String variable) {
        super(CountryCode.class, forVariable(variable));
    }

    public QCountryCode(Path<? extends CountryCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCountryCode(PathMetadata metadata) {
        super(CountryCode.class, metadata);
    }

}

