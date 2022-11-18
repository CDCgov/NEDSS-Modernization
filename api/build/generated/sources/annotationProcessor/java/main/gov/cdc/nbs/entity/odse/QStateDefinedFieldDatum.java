package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStateDefinedFieldDatum is a Querydsl query type for StateDefinedFieldDatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStateDefinedFieldDatum extends EntityPathBase<StateDefinedFieldDatum> {

    private static final long serialVersionUID = -1649274588L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStateDefinedFieldDatum stateDefinedFieldDatum = new QStateDefinedFieldDatum("stateDefinedFieldDatum");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final StringPath businessObjectNm = createString("businessObjectNm");

    public final QStateDefinedFieldDatumId id;

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final QStateDefinedFieldMetadatum ldfUid;

    public final StringPath ldfValue = createString("ldfValue");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QStateDefinedFieldDatum(String variable) {
        this(StateDefinedFieldDatum.class, forVariable(variable), INITS);
    }

    public QStateDefinedFieldDatum(Path<? extends StateDefinedFieldDatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStateDefinedFieldDatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStateDefinedFieldDatum(PathMetadata metadata, PathInits inits) {
        this(StateDefinedFieldDatum.class, metadata, inits);
    }

    public QStateDefinedFieldDatum(Class<? extends StateDefinedFieldDatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QStateDefinedFieldDatumId(forProperty("id")) : null;
        this.ldfUid = inits.isInitialized("ldfUid") ? new QStateDefinedFieldMetadatum(forProperty("ldfUid"), inits.get("ldfUid")) : null;
    }

}

