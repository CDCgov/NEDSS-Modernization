package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJurisdictionCode is a Querydsl query type for JurisdictionCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJurisdictionCode extends EntityPathBase<JurisdictionCode> {

    private static final long serialVersionUID = -1036943748L;

    public static final QJurisdictionCode jurisdictionCode = new QJurisdictionCode("jurisdictionCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeDescTxt = createString("codeDescTxt");

    public final NumberPath<Short> codeSeqNum = createNumber("codeSeqNum", Short.class);

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final ComparablePath<Character> exportInd = createComparable("exportInd", Character.class);

    public final StringPath id = createString("id");

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final SetPath<JurisdictionParticipation, QJurisdictionParticipation> jurisdictionParticipations = this.<JurisdictionParticipation, QJurisdictionParticipation>createSet("jurisdictionParticipations", JurisdictionParticipation.class, QJurisdictionParticipation.class, PathInits.DIRECT2);

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath parentIsCd = createString("parentIsCd");

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath stateDomainCd = createString("stateDomainCd");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath typeCd = createString("typeCd");

    public QJurisdictionCode(String variable) {
        super(JurisdictionCode.class, forVariable(variable));
    }

    public QJurisdictionCode(Path<? extends JurisdictionCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJurisdictionCode(PathMetadata metadata) {
        super(JurisdictionCode.class, metadata);
    }

}

