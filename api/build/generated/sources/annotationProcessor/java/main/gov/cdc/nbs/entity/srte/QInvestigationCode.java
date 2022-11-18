package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInvestigationCode is a Querydsl query type for InvestigationCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvestigationCode extends EntityPathBase<InvestigationCode> {

    private static final long serialVersionUID = 1891647585L;

    public static final QInvestigationCode investigationCode = new QInvestigationCode("investigationCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codeVersion = createString("codeVersion");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath id = createString("id");

    public final StringPath investigationDescTxt = createString("investigationDescTxt");

    public final NumberPath<Short> nbsUid = createNumber("nbsUid", Short.class);

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QInvestigationCode(String variable) {
        super(InvestigationCode.class, forVariable(variable));
    }

    public QInvestigationCode(Path<? extends InvestigationCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInvestigationCode(PathMetadata metadata) {
        super(InvestigationCode.class, metadata);
    }

}

