package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubstanceAdministrationHistId is a Querydsl query type for SubstanceAdministrationHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSubstanceAdministrationHistId extends BeanPath<SubstanceAdministrationHistId> {

    private static final long serialVersionUID = -1731395500L;

    public static final QSubstanceAdministrationHistId substanceAdministrationHistId = new QSubstanceAdministrationHistId("substanceAdministrationHistId");

    public final NumberPath<Long> interventionUid = createNumber("interventionUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QSubstanceAdministrationHistId(String variable) {
        super(SubstanceAdministrationHistId.class, forVariable(variable));
    }

    public QSubstanceAdministrationHistId(Path<? extends SubstanceAdministrationHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubstanceAdministrationHistId(PathMetadata metadata) {
        super(SubstanceAdministrationHistId.class, metadata);
    }

}

