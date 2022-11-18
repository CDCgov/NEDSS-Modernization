package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonMerge is a Querydsl query type for PersonMerge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonMerge extends EntityPathBase<PersonMerge> {

    private static final long serialVersionUID = 2057750284L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonMerge personMerge = new QPersonMerge("personMerge");

    public final QPersonMergeId id;

    public final DateTimePath<java.time.Instant> mergeTime = createDateTime("mergeTime", java.time.Instant.class);

    public final StringPath mergeUserId = createString("mergeUserId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Long> supercededParentUid = createNumber("supercededParentUid", Long.class);

    public final NumberPath<Long> survivingParentUid = createNumber("survivingParentUid", Long.class);

    public final QPerson survivingPersonUid;

    public final NumberPath<Short> survivingVersionCtrlNbr = createNumber("survivingVersionCtrlNbr", Short.class);

    public QPersonMerge(String variable) {
        this(PersonMerge.class, forVariable(variable), INITS);
    }

    public QPersonMerge(Path<? extends PersonMerge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonMerge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonMerge(PathMetadata metadata, PathInits inits) {
        this(PersonMerge.class, metadata, inits);
    }

    public QPersonMerge(Class<? extends PersonMerge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonMergeId(forProperty("id")) : null;
        this.survivingPersonUid = inits.isInitialized("survivingPersonUid") ? new QPerson(forProperty("survivingPersonUid"), inits.get("survivingPersonUid")) : null;
    }

}

