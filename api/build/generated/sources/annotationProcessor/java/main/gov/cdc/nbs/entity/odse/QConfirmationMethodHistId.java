package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConfirmationMethodHistId is a Querydsl query type for ConfirmationMethodHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QConfirmationMethodHistId extends BeanPath<ConfirmationMethodHistId> {

    private static final long serialVersionUID = -324486550L;

    public static final QConfirmationMethodHistId confirmationMethodHistId = new QConfirmationMethodHistId("confirmationMethodHistId");

    public final StringPath confirmationMethodCd = createString("confirmationMethodCd");

    public final NumberPath<Long> publicHealthCaseUid = createNumber("publicHealthCaseUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QConfirmationMethodHistId(String variable) {
        super(ConfirmationMethodHistId.class, forVariable(variable));
    }

    public QConfirmationMethodHistId(Path<? extends ConfirmationMethodHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConfirmationMethodHistId(PathMetadata metadata) {
        super(ConfirmationMethodHistId.class, metadata);
    }

}

