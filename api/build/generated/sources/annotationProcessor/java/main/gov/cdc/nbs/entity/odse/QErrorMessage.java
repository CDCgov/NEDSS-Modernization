package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorMessage is a Querydsl query type for ErrorMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorMessage extends EntityPathBase<ErrorMessage> {

    private static final long serialVersionUID = 1405825238L;

    public static final QErrorMessage errorMessage = new QErrorMessage("errorMessage");

    public final StringPath errorCd = createString("errorCd");

    public final StringPath errorDescTxt = createString("errorDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QErrorMessage(String variable) {
        super(ErrorMessage.class, forVariable(variable));
    }

    public QErrorMessage(Path<? extends ErrorMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorMessage(PathMetadata metadata) {
        super(ErrorMessage.class, metadata);
    }

}

