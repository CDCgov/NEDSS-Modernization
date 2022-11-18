package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDataMigrationDetailId is a Querydsl query type for DataMigrationDetailId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDataMigrationDetailId extends BeanPath<DataMigrationDetailId> {

    private static final long serialVersionUID = 1989793433L;

    public static final QDataMigrationDetailId dataMigrationDetailId = new QDataMigrationDetailId("dataMigrationDetailId");

    public final NumberPath<Long> dataMigrationBatchUid = createNumber("dataMigrationBatchUid", Long.class);

    public final NumberPath<Long> dataMigrationDetailUid = createNumber("dataMigrationDetailUid", Long.class);

    public final NumberPath<Long> dataMigrationRecordUid = createNumber("dataMigrationRecordUid", Long.class);

    public QDataMigrationDetailId(String variable) {
        super(DataMigrationDetailId.class, forVariable(variable));
    }

    public QDataMigrationDetailId(Path<? extends DataMigrationDetailId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataMigrationDetailId(PathMetadata metadata) {
        super(DataMigrationDetailId.class, metadata);
    }

}

