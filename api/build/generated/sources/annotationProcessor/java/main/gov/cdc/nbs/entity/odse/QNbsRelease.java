package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsRelease is a Querydsl query type for NbsRelease
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsRelease extends EntityPathBase<NbsRelease> {

    private static final long serialVersionUID = -1722558593L;

    public static final QNbsRelease nbsRelease = new QNbsRelease("nbsRelease");

    public final DateTimePath<java.time.Instant> deploymentDate = createDateTime("deploymentDate", java.time.Instant.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.Instant> publishDate = createDateTime("publishDate", java.time.Instant.class);

    public final StringPath version = createString("version");

    public QNbsRelease(String variable) {
        super(NbsRelease.class, forVariable(variable));
    }

    public QNbsRelease(Path<? extends NbsRelease> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsRelease(PathMetadata metadata) {
        super(NbsRelease.class, metadata);
    }

}

