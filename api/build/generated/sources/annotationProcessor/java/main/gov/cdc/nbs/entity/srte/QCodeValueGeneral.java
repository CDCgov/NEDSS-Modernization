package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeValueGeneral is a Querydsl query type for CodeValueGeneral
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeValueGeneral extends EntityPathBase<CodeValueGeneral> {

    private static final long serialVersionUID = -436558286L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeValueGeneral codeValueGeneral = new QCodeValueGeneral("codeValueGeneral");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath adminComments = createString("adminComments");

    public final StringPath codeDescTxt = createString("codeDescTxt");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codeSystemVersionNbr = createString("codeSystemVersionNbr");

    public final StringPath conceptCode = createString("conceptCode");

    public final StringPath conceptNm = createString("conceptNm");

    public final NumberPath<Integer> conceptOrderNbr = createNumber("conceptOrderNbr", Integer.class);

    public final StringPath conceptPreferredNm = createString("conceptPreferredNm");

    public final StringPath conceptStatusCd = createString("conceptStatusCd");

    public final DateTimePath<java.time.Instant> conceptStatusTime = createDateTime("conceptStatusTime", java.time.Instant.class);

    public final StringPath conceptTypeCd = createString("conceptTypeCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QCodeValueGeneralId id;

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath parentIsCd = createString("parentIsCd");

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath superCode = createString("superCode");

    public final StringPath superCodeSetNm = createString("superCodeSetNm");

    public QCodeValueGeneral(String variable) {
        this(CodeValueGeneral.class, forVariable(variable), INITS);
    }

    public QCodeValueGeneral(Path<? extends CodeValueGeneral> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeValueGeneral(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeValueGeneral(PathMetadata metadata, PathInits inits) {
        this(CodeValueGeneral.class, metadata, inits);
    }

    public QCodeValueGeneral(Class<? extends CodeValueGeneral> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCodeValueGeneralId(forProperty("id")) : null;
    }

}

