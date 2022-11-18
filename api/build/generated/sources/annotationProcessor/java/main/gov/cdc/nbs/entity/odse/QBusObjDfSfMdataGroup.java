package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBusObjDfSfMdataGroup is a Querydsl query type for BusObjDfSfMdataGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusObjDfSfMdataGroup extends EntityPathBase<BusObjDfSfMdataGroup> {

    private static final long serialVersionUID = -409963861L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBusObjDfSfMdataGroup busObjDfSfMdataGroup = new QBusObjDfSfMdataGroup("busObjDfSfMdataGroup");

    public final QDfSfMetadataGroup dfSfMetadataGroupUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QBusObjDfSfMdataGroup(String variable) {
        this(BusObjDfSfMdataGroup.class, forVariable(variable), INITS);
    }

    public QBusObjDfSfMdataGroup(Path<? extends BusObjDfSfMdataGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBusObjDfSfMdataGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBusObjDfSfMdataGroup(PathMetadata metadata, PathInits inits) {
        this(BusObjDfSfMdataGroup.class, metadata, inits);
    }

    public QBusObjDfSfMdataGroup(Class<? extends BusObjDfSfMdataGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dfSfMetadataGroupUid = inits.isInitialized("dfSfMetadataGroupUid") ? new QDfSfMetadataGroup(forProperty("dfSfMetadataGroupUid")) : null;
    }

}

