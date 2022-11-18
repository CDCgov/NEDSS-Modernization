package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QElrXref is a Querydsl query type for ElrXref
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QElrXref extends EntityPathBase<ElrXref> {

    private static final long serialVersionUID = -1760256200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QElrXref elrXref = new QElrXref("elrXref");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QElrXrefId id;

    public final StringPath laboratoryId = createString("laboratoryId");

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QElrXref(String variable) {
        this(ElrXref.class, forVariable(variable), INITS);
    }

    public QElrXref(Path<? extends ElrXref> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QElrXref(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QElrXref(PathMetadata metadata, PathInits inits) {
        this(ElrXref.class, metadata, inits);
    }

    public QElrXref(Class<? extends ElrXref> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QElrXrefId(forProperty("id")) : null;
    }

}

