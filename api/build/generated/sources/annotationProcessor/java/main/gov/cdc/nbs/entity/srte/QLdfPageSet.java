package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLdfPageSet is a Querydsl query type for LdfPageSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLdfPageSet extends EntityPathBase<LdfPageSet> {

    private static final long serialVersionUID = -771375757L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLdfPageSet ldfPageSet = new QLdfPageSet("ldfPageSet");

    public final StringPath businessObjectNm = createString("businessObjectNm");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final StringPath codeVersion = createString("codeVersion");

    public final QConditionCode conditionCd;

    public final NumberPath<Short> displayColumn = createNumber("displayColumn", Short.class);

    public final NumberPath<Short> displayRow = createNumber("displayRow", Short.class);

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath id = createString("id");

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath parentIsCd = createString("parentIsCd");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final StringPath uiDisplay = createString("uiDisplay");

    public QLdfPageSet(String variable) {
        this(LdfPageSet.class, forVariable(variable), INITS);
    }

    public QLdfPageSet(Path<? extends LdfPageSet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLdfPageSet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLdfPageSet(PathMetadata metadata, PathInits inits) {
        this(LdfPageSet.class, metadata, inits);
    }

    public QLdfPageSet(Class<? extends LdfPageSet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conditionCd = inits.isInitialized("conditionCd") ? new QConditionCode(forProperty("conditionCd"), inits.get("conditionCd")) : null;
    }

}

