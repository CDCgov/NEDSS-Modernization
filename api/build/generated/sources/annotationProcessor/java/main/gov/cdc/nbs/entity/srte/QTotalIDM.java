package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTotalIDM is a Querydsl query type for TotalIDM
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTotalIDM extends EntityPathBase<TotalIDM> {

    private static final long serialVersionUID = 71891004L;

    public static final QTotalIDM totalIDM = new QTotalIDM("totalIDM");

    public final StringPath format = createString("format");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath label = createString("label");

    public final StringPath srtReference = createString("srtReference");

    public final StringPath uniqueCd = createString("uniqueCd");

    public QTotalIDM(String variable) {
        super(TotalIDM.class, forVariable(variable));
    }

    public QTotalIDM(Path<? extends TotalIDM> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTotalIDM(PathMetadata metadata) {
        super(TotalIDM.class, metadata);
    }

}

