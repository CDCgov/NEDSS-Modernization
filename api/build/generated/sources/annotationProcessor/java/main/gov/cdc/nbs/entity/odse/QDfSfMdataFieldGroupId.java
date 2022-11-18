package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDfSfMdataFieldGroupId is a Querydsl query type for DfSfMdataFieldGroupId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDfSfMdataFieldGroupId extends BeanPath<DfSfMdataFieldGroupId> {

    private static final long serialVersionUID = 1117543627L;

    public static final QDfSfMdataFieldGroupId dfSfMdataFieldGroupId = new QDfSfMdataFieldGroupId("dfSfMdataFieldGroupId");

    public final NumberPath<Long> dfSfMetadataGroupUid = createNumber("dfSfMetadataGroupUid", Long.class);

    public final NumberPath<Long> fieldUid = createNumber("fieldUid", Long.class);

    public QDfSfMdataFieldGroupId(String variable) {
        super(DfSfMdataFieldGroupId.class, forVariable(variable));
    }

    public QDfSfMdataFieldGroupId(Path<? extends DfSfMdataFieldGroupId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDfSfMdataFieldGroupId(PathMetadata metadata) {
        super(DfSfMdataFieldGroupId.class, metadata);
    }

}

