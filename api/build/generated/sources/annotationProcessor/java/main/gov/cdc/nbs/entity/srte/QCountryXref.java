package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCountryXref is a Querydsl query type for CountryXref
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCountryXref extends EntityPathBase<CountryXref> {

    private static final long serialVersionUID = -1892857949L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCountryXref countryXref = new QCountryXref("countryXref");

    public final StringPath alpha2ToCode = createString("alpha2ToCode");

    public final QCountryCodeIso countryCodeIso;

    public final StringPath fromCode = createString("fromCode");

    public final StringPath fromCodeDescTxt = createString("fromCodeDescTxt");

    public final StringPath fromCodeSetNm = createString("fromCodeSetNm");

    public final NumberPath<Short> fromSeqNum = createNumber("fromSeqNum", Short.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath toCodeDescTxt = createString("toCodeDescTxt");

    public final StringPath toCodeSystemCd = createString("toCodeSystemCd");

    public QCountryXref(String variable) {
        this(CountryXref.class, forVariable(variable), INITS);
    }

    public QCountryXref(Path<? extends CountryXref> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCountryXref(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCountryXref(PathMetadata metadata, PathInits inits) {
        this(CountryXref.class, metadata, inits);
    }

    public QCountryXref(Class<? extends CountryXref> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.countryCodeIso = inits.isInitialized("countryCodeIso") ? new QCountryCodeIso(forProperty("countryCodeIso"), inits.get("countryCodeIso")) : null;
    }

}

