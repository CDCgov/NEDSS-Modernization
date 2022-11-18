package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNonPersonLivingSubjectHistId is a Querydsl query type for NonPersonLivingSubjectHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QNonPersonLivingSubjectHistId extends BeanPath<NonPersonLivingSubjectHistId> {

    private static final long serialVersionUID = 555226645L;

    public static final QNonPersonLivingSubjectHistId nonPersonLivingSubjectHistId = new QNonPersonLivingSubjectHistId("nonPersonLivingSubjectHistId");

    public final NumberPath<Long> nonPersonUid = createNumber("nonPersonUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QNonPersonLivingSubjectHistId(String variable) {
        super(NonPersonLivingSubjectHistId.class, forVariable(variable));
    }

    public QNonPersonLivingSubjectHistId(Path<? extends NonPersonLivingSubjectHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNonPersonLivingSubjectHistId(PathMetadata metadata) {
        super(NonPersonLivingSubjectHistId.class, metadata);
    }

}

