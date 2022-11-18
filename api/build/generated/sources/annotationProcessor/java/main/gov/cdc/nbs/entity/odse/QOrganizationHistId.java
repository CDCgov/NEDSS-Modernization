package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrganizationHistId is a Querydsl query type for OrganizationHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrganizationHistId extends BeanPath<OrganizationHistId> {

    private static final long serialVersionUID = 1884274087L;

    public static final QOrganizationHistId organizationHistId = new QOrganizationHistId("organizationHistId");

    public final NumberPath<Long> organizationUid = createNumber("organizationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QOrganizationHistId(String variable) {
        super(OrganizationHistId.class, forVariable(variable));
    }

    public QOrganizationHistId(Path<? extends OrganizationHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrganizationHistId(PathMetadata metadata) {
        super(OrganizationHistId.class, metadata);
    }

}

