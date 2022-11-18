package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDfSfMetadataGroup is a Querydsl query type for DfSfMetadataGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDfSfMetadataGroup extends EntityPathBase<DfSfMetadataGroup> {

    private static final long serialVersionUID = -1141971324L;

    public static final QDfSfMetadataGroup dfSfMetadataGroup = new QDfSfMetadataGroup("dfSfMetadataGroup");

    public final StringPath groupName = createString("groupName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QDfSfMetadataGroup(String variable) {
        super(DfSfMetadataGroup.class, forVariable(variable));
    }

    public QDfSfMetadataGroup(Path<? extends DfSfMetadataGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDfSfMetadataGroup(PathMetadata metadata) {
        super(DfSfMetadataGroup.class, metadata);
    }

}

