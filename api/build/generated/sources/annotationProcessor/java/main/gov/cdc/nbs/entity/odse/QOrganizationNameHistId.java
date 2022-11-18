package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrganizationNameHistId is a Querydsl query type for OrganizationNameHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrganizationNameHistId extends BeanPath<OrganizationNameHistId> {

    private static final long serialVersionUID = -1359815918L;

    public static final QOrganizationNameHistId organizationNameHistId = new QOrganizationNameHistId("organizationNameHistId");

    public final NumberPath<Short> organizationNameSeq = createNumber("organizationNameSeq", Short.class);

    public final NumberPath<Long> organizationUid = createNumber("organizationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QOrganizationNameHistId(String variable) {
        super(OrganizationNameHistId.class, forVariable(variable));
    }

    public QOrganizationNameHistId(Path<? extends OrganizationNameHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrganizationNameHistId(PathMetadata metadata) {
        super(OrganizationNameHistId.class, metadata);
    }

}

