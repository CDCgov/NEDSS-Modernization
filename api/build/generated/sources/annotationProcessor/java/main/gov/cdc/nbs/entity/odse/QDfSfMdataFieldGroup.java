package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDfSfMdataFieldGroup is a Querydsl query type for DfSfMdataFieldGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDfSfMdataFieldGroup extends EntityPathBase<DfSfMdataFieldGroup> {

    private static final long serialVersionUID = -1044646000L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDfSfMdataFieldGroup dfSfMdataFieldGroup = new QDfSfMdataFieldGroup("dfSfMdataFieldGroup");

    public final QDfSfMetadataGroup dfSfMetadataGroupUid;

    public final StringPath fieldType = createString("fieldType");

    public final QDfSfMdataFieldGroupId id;

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QDfSfMdataFieldGroup(String variable) {
        this(DfSfMdataFieldGroup.class, forVariable(variable), INITS);
    }

    public QDfSfMdataFieldGroup(Path<? extends DfSfMdataFieldGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDfSfMdataFieldGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDfSfMdataFieldGroup(PathMetadata metadata, PathInits inits) {
        this(DfSfMdataFieldGroup.class, metadata, inits);
    }

    public QDfSfMdataFieldGroup(Class<? extends DfSfMdataFieldGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dfSfMetadataGroupUid = inits.isInitialized("dfSfMetadataGroupUid") ? new QDfSfMetadataGroup(forProperty("dfSfMetadataGroupUid")) : null;
        this.id = inits.isInitialized("id") ? new QDfSfMdataFieldGroupId(forProperty("id")) : null;
    }

}

