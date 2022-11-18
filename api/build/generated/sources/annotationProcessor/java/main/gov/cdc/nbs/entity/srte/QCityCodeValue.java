package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCityCodeValue is a Querydsl query type for CityCodeValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCityCodeValue extends EntityPathBase<CityCodeValue> {

    private static final long serialVersionUID = 1511412651L;

    public static final QCityCodeValue cityCodeValue = new QCityCodeValue("cityCodeValue");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final SetPath<CntycityCodeValue, QCntycityCodeValue> cntycityCodeValues = this.<CntycityCodeValue, QCntycityCodeValue>createSet("cntycityCodeValues", CntycityCodeValue.class, QCntycityCodeValue.class, PathInits.DIRECT2);

    public final StringPath codeDescTxt = createString("codeDescTxt");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeShortDescTxt = createString("codeShortDescTxt");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath excludedTxt = createString("excludedTxt");

    public final StringPath id = createString("id");

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath parentIsCd = createString("parentIsCd");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QCityCodeValue(String variable) {
        super(CityCodeValue.class, forVariable(variable));
    }

    public QCityCodeValue(Path<? extends CityCodeValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCityCodeValue(PathMetadata metadata) {
        super(CityCodeValue.class, metadata);
    }

}

