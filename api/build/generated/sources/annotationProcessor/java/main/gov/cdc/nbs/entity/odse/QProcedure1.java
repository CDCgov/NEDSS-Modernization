package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProcedure1 is a Querydsl query type for Procedure1
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProcedure1 extends EntityPathBase<Procedure1> {

    private static final long serialVersionUID = -1450594955L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProcedure1 procedure1 = new QProcedure1("procedure1");

    public final StringPath approachSiteCd = createString("approachSiteCd");

    public final StringPath approachSiteDescTxt = createString("approachSiteDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QIntervention intervention;

    public QProcedure1(String variable) {
        this(Procedure1.class, forVariable(variable), INITS);
    }

    public QProcedure1(Path<? extends Procedure1> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProcedure1(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProcedure1(PathMetadata metadata, PathInits inits) {
        this(Procedure1.class, metadata, inits);
    }

    public QProcedure1(Class<? extends Procedure1> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.intervention = inits.isInitialized("intervention") ? new QIntervention(forProperty("intervention"), inits.get("intervention")) : null;
    }

}

