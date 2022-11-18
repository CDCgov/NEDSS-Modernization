package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNaicsIndustryCode is a Querydsl query type for NaicsIndustryCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNaicsIndustryCode extends EntityPathBase<NaicsIndustryCode> {

    private static final long serialVersionUID = 1973638243L;

    public static final QNaicsIndustryCode naicsIndustryCode = new QNaicsIndustryCode("naicsIndustryCode");

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

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final SetPath<OccupationCode, QOccupationCode> occupationCodes = this.<OccupationCode, QOccupationCode>createSet("occupationCodes", OccupationCode.class, QOccupationCode.class, PathInits.DIRECT2);

    public final StringPath parentIsCd = createString("parentIsCd");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QNaicsIndustryCode(String variable) {
        super(NaicsIndustryCode.class, forVariable(variable));
    }

    public QNaicsIndustryCode(Path<? extends NaicsIndustryCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNaicsIndustryCode(PathMetadata metadata) {
        super(NaicsIndustryCode.class, metadata);
    }

}

