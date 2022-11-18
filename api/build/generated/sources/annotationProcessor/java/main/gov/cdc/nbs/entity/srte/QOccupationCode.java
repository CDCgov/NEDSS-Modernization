package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOccupationCode is a Querydsl query type for OccupationCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOccupationCode extends EntityPathBase<OccupationCode> {

    private static final long serialVersionUID = 1674367686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOccupationCode occupationCode = new QOccupationCode("occupationCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeDescTxt = createString("codeDescTxt");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath id = createString("id");

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final StringPath keyInfoTxt = createString("keyInfoTxt");

    public final QNaicsIndustryCode naicsIndustryCd;

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath parentIsCd = createString("parentIsCd");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QOccupationCode(String variable) {
        this(OccupationCode.class, forVariable(variable), INITS);
    }

    public QOccupationCode(Path<? extends OccupationCode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOccupationCode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOccupationCode(PathMetadata metadata, PathInits inits) {
        this(OccupationCode.class, metadata, inits);
    }

    public QOccupationCode(Class<? extends OccupationCode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.naicsIndustryCd = inits.isInitialized("naicsIndustryCd") ? new QNaicsIndustryCode(forProperty("naicsIndustryCd")) : null;
    }

}

