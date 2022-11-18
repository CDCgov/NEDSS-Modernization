package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoleHistId is a Querydsl query type for RoleHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRoleHistId extends BeanPath<RoleHistId> {

    private static final long serialVersionUID = -700368470L;

    public static final QRoleHistId roleHistId = new QRoleHistId("roleHistId");

    public final StringPath cd = createString("cd");

    public final NumberPath<Long> roleSeq = createNumber("roleSeq", Long.class);

    public final NumberPath<Long> subjectEntityUid = createNumber("subjectEntityUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QRoleHistId(String variable) {
        super(RoleHistId.class, forVariable(variable));
    }

    public QRoleHistId(Path<? extends RoleHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoleHistId(PathMetadata metadata) {
        super(RoleHistId.class, metadata);
    }

}

