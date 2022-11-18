package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSpecimenSourceCode is a Querydsl query type for SpecimenSourceCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpecimenSourceCode extends EntityPathBase<SpecimenSourceCode> {

    private static final long serialVersionUID = 235894718L;

    public static final QSpecimenSourceCode specimenSourceCode = new QSpecimenSourceCode("specimenSourceCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath code = createString("code");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final StringPath specimenSourceDescTxt = createString("specimenSourceDescTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QSpecimenSourceCode(String variable) {
        super(SpecimenSourceCode.class, forVariable(variable));
    }

    public QSpecimenSourceCode(Path<? extends SpecimenSourceCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpecimenSourceCode(PathMetadata metadata) {
        super(SpecimenSourceCode.class, metadata);
    }

}

