package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUnitCode is a Querydsl query type for UnitCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUnitCode extends EntityPathBase<UnitCode> {

    private static final long serialVersionUID = 628639999L;

    public static final QUnitCode unitCode = new QUnitCode("unitCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath code = createString("code");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final NumberPath<Long> nbsUid = createNumber("nbsUid", Long.class);

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath unitDescTxt = createString("unitDescTxt");

    public QUnitCode(String variable) {
        super(UnitCode.class, forVariable(variable));
    }

    public QUnitCode(Path<? extends UnitCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUnitCode(PathMetadata metadata) {
        super(UnitCode.class, metadata);
    }

}

