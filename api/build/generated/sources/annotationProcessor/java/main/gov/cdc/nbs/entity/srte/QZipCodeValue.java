package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZipCodeValue is a Querydsl query type for ZipCodeValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QZipCodeValue extends EntityPathBase<ZipCodeValue> {

    private static final long serialVersionUID = -823810607L;

    public static final QZipCodeValue zipCodeValue = new QZipCodeValue("zipCodeValue");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

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

    public final SetPath<ZipcntyCodeValue, QZipcntyCodeValue> zipcntyCodeValues = this.<ZipcntyCodeValue, QZipcntyCodeValue>createSet("zipcntyCodeValues", ZipcntyCodeValue.class, QZipcntyCodeValue.class, PathInits.DIRECT2);

    public QZipCodeValue(String variable) {
        super(ZipCodeValue.class, forVariable(variable));
    }

    public QZipCodeValue(Path<? extends ZipCodeValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QZipCodeValue(PathMetadata metadata) {
        super(ZipCodeValue.class, metadata);
    }

}

