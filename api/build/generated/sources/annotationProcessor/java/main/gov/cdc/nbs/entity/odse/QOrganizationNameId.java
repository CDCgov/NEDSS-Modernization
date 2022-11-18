package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrganizationNameId is a Querydsl query type for OrganizationNameId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrganizationNameId extends BeanPath<OrganizationNameId> {

    private static final long serialVersionUID = 2048467664L;

    public static final QOrganizationNameId organizationNameId = new QOrganizationNameId("organizationNameId");

    public final NumberPath<Short> organizationNameSeq = createNumber("organizationNameSeq", Short.class);

    public final NumberPath<Long> organizationUid = createNumber("organizationUid", Long.class);

    public QOrganizationNameId(String variable) {
        super(OrganizationNameId.class, forVariable(variable));
    }

    public QOrganizationNameId(Path<? extends OrganizationNameId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrganizationNameId(PathMetadata metadata) {
        super(OrganizationNameId.class, metadata);
    }

}

