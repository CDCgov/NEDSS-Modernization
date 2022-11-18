package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPublicHealthCaseHistId is a Querydsl query type for PublicHealthCaseHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPublicHealthCaseHistId extends BeanPath<PublicHealthCaseHistId> {

    private static final long serialVersionUID = -355039639L;

    public static final QPublicHealthCaseHistId publicHealthCaseHistId = new QPublicHealthCaseHistId("publicHealthCaseHistId");

    public final NumberPath<Long> publicHealthCaseUid = createNumber("publicHealthCaseUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPublicHealthCaseHistId(String variable) {
        super(PublicHealthCaseHistId.class, forVariable(variable));
    }

    public QPublicHealthCaseHistId(Path<? extends PublicHealthCaseHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPublicHealthCaseHistId(PathMetadata metadata) {
        super(PublicHealthCaseHistId.class, metadata);
    }

}

