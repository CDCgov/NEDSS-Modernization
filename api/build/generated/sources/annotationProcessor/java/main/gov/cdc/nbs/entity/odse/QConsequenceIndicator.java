package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConsequenceIndicator is a Querydsl query type for ConsequenceIndicator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsequenceIndicator extends EntityPathBase<ConsequenceIndicator> {

    private static final long serialVersionUID = 1087528707L;

    public static final QConsequenceIndicator consequenceIndicator = new QConsequenceIndicator("consequenceIndicator");

    public final StringPath conseqIndCode = createString("conseqIndCode");

    public final StringPath conseqIndDescTxt = createString("conseqIndDescTxt");

    public final StringPath conseqIndType = createString("conseqIndType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QConsequenceIndicator(String variable) {
        super(ConsequenceIndicator.class, forVariable(variable));
    }

    public QConsequenceIndicator(Path<? extends ConsequenceIndicator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConsequenceIndicator(PathMetadata metadata) {
        super(ConsequenceIndicator.class, metadata);
    }

}

