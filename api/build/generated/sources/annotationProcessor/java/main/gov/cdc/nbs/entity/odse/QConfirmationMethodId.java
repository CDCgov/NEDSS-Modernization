package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConfirmationMethodId is a Querydsl query type for ConfirmationMethodId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QConfirmationMethodId extends BeanPath<ConfirmationMethodId> {

    private static final long serialVersionUID = -946573784L;

    public static final QConfirmationMethodId confirmationMethodId = new QConfirmationMethodId("confirmationMethodId");

    public final StringPath confirmationMethodCd = createString("confirmationMethodCd");

    public final NumberPath<Long> publicHealthCaseUid = createNumber("publicHealthCaseUid", Long.class);

    public QConfirmationMethodId(String variable) {
        super(ConfirmationMethodId.class, forVariable(variable));
    }

    public QConfirmationMethodId(Path<? extends ConfirmationMethodId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConfirmationMethodId(PathMetadata metadata) {
        super(ConfirmationMethodId.class, metadata);
    }

}

