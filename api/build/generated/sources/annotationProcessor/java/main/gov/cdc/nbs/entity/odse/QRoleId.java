package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoleId is a Querydsl query type for RoleId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRoleId extends BeanPath<RoleId> {

    private static final long serialVersionUID = -1513190040L;

    public static final QRoleId roleId = new QRoleId("roleId");

    public final StringPath cd = createString("cd");

    public final NumberPath<Long> roleSeq = createNumber("roleSeq", Long.class);

    public final NumberPath<Long> subjectEntityUid = createNumber("subjectEntityUid", Long.class);

    public QRoleId(String variable) {
        super(RoleId.class, forVariable(variable));
    }

    public QRoleId(Path<? extends RoleId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoleId(PathMetadata metadata) {
        super(RoleId.class, metadata);
    }

}

