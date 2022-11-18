package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeValueClinical is a Querydsl query type for CodeValueClinical
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeValueClinical extends EntityPathBase<CodeValueClinical> {

    private static final long serialVersionUID = -1542221047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeValueClinical codeValueClinical = new QCodeValueClinical("codeValueClinical");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeDescTxt = createString("codeDescTxt");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final StringPath codeSystemCode = createString("codeSystemCode");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath commonName = createString("commonName");

    public final QCodeValueClinicalId id;

    public final NumberPath<Short> orderNumber = createNumber("orderNumber", Short.class);

    public final StringPath otherNames = createString("otherNames");

    public final QSnomedCode snomedCd;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QCodeValueClinical(String variable) {
        this(CodeValueClinical.class, forVariable(variable), INITS);
    }

    public QCodeValueClinical(Path<? extends CodeValueClinical> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeValueClinical(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeValueClinical(PathMetadata metadata, PathInits inits) {
        this(CodeValueClinical.class, metadata, inits);
    }

    public QCodeValueClinical(Class<? extends CodeValueClinical> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCodeValueClinicalId(forProperty("id")) : null;
        this.snomedCd = inits.isInitialized("snomedCd") ? new QSnomedCode(forProperty("snomedCd")) : null;
    }

}

