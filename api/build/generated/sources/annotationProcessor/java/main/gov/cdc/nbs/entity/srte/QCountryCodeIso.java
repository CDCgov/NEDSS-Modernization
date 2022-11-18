package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCountryCodeIso is a Querydsl query type for CountryCodeIso
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCountryCodeIso extends EntityPathBase<CountryCodeIso> {

    private static final long serialVersionUID = 1244962768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCountryCodeIso countryCodeIso = new QCountryCodeIso("countryCodeIso");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeDescTxt = createString("codeDescTxt");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final SetPath<CountryXref, QCountryXref> countryXrefs = this.<CountryXref, QCountryXref>createSet("countryXrefs", CountryXref.class, QCountryXref.class, PathInits.DIRECT2);

    public final QCountryCodeIsoId id;

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QCountryCodeIso(String variable) {
        this(CountryCodeIso.class, forVariable(variable), INITS);
    }

    public QCountryCodeIso(Path<? extends CountryCodeIso> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCountryCodeIso(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCountryCodeIso(PathMetadata metadata, PathInits inits) {
        this(CountryCodeIso.class, metadata, inits);
    }

    public QCountryCodeIso(Class<? extends CountryCodeIso> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCountryCodeIsoId(forProperty("id")) : null;
    }

}

