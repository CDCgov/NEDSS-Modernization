package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStateModel is a Querydsl query type for StateModel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStateModel extends EntityPathBase<StateModel> {

    private static final long serialVersionUID = 850593030L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStateModel stateModel = new QStateModel("stateModel");

    public final QStateModelId id;

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath objectStatusCodeSetNm = createString("objectStatusCodeSetNm");

    public final StringPath objectStatusFromCode = createString("objectStatusFromCode");

    public final NumberPath<Short> objectStatusSeqNm = createNumber("objectStatusSeqNm", Short.class);

    public final StringPath objectStatusToCode = createString("objectStatusToCode");

    public final StringPath recordStatusCodeSetNm = createString("recordStatusCodeSetNm");

    public final NumberPath<Short> recordStatusSeqNm = createNumber("recordStatusSeqNm", Short.class);

    public final StringPath recordStatusToCode = createString("recordStatusToCode");

    public QStateModel(String variable) {
        this(StateModel.class, forVariable(variable), INITS);
    }

    public QStateModel(Path<? extends StateModel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStateModel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStateModel(PathMetadata metadata, PathInits inits) {
        this(StateModel.class, metadata, inits);
    }

    public QStateModel(Class<? extends StateModel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QStateModelId(forProperty("id")) : null;
    }

}

