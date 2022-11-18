package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeset is a Querydsl query type for Codeset
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeset extends EntityPathBase<Codeset> {

    private static final long serialVersionUID = 833050151L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeset codeset = new QCodeset("codeset");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath adminComments = createString("adminComments");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeSetDescTxt = createString("codeSetDescTxt");

    public final QCodesetGroupMetadatum codeSetGroup;

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QCodesetId id;

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final ComparablePath<Character> ldfPicklistIndCd = createComparable("ldfPicklistIndCd", Character.class);

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final NumberPath<Long> parentIsCd = createNumber("parentIsCd", Long.class);

    public final StringPath sourceDomainNm = createString("sourceDomainNm");

    public final StringPath sourceVersionTxt = createString("sourceVersionTxt");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusToTime = createDateTime("statusToTime", java.time.Instant.class);

    public final StringPath valueSetCode = createString("valueSetCode");

    public final StringPath valueSetNm = createString("valueSetNm");

    public final StringPath valueSetOid = createString("valueSetOid");

    public final StringPath valueSetStatusCd = createString("valueSetStatusCd");

    public final DateTimePath<java.time.Instant> valueSetStatusTime = createDateTime("valueSetStatusTime", java.time.Instant.class);

    public final StringPath valueSetTypeCd = createString("valueSetTypeCd");

    public QCodeset(String variable) {
        this(Codeset.class, forVariable(variable), INITS);
    }

    public QCodeset(Path<? extends Codeset> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeset(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeset(PathMetadata metadata, PathInits inits) {
        this(Codeset.class, metadata, inits);
    }

    public QCodeset(Class<? extends Codeset> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeSetGroup = inits.isInitialized("codeSetGroup") ? new QCodesetGroupMetadatum(forProperty("codeSetGroup")) : null;
        this.id = inits.isInitialized("id") ? new QCodesetId(forProperty("id")) : null;
    }

}

