package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProcedure1HistId is a Querydsl query type for Procedure1HistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProcedure1HistId extends BeanPath<Procedure1HistId> {

    private static final long serialVersionUID = -488139342L;

    public static final QProcedure1HistId procedure1HistId = new QProcedure1HistId("procedure1HistId");

    public final NumberPath<Long> interventionUid = createNumber("interventionUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QProcedure1HistId(String variable) {
        super(Procedure1HistId.class, forVariable(variable));
    }

    public QProcedure1HistId(Path<? extends Procedure1HistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProcedure1HistId(PathMetadata metadata) {
        super(Procedure1HistId.class, metadata);
    }

}

