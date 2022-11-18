package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProcedure1Hist is a Querydsl query type for Procedure1Hist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProcedure1Hist extends EntityPathBase<Procedure1Hist> {

    private static final long serialVersionUID = 1233010231L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProcedure1Hist procedure1Hist = new QProcedure1Hist("procedure1Hist");

    public final StringPath approachSiteCd = createString("approachSiteCd");

    public final StringPath approachSiteDescTxt = createString("approachSiteDescTxt");

    public final QProcedure1HistId id;

    public final QProcedure1 interventionUid;

    public QProcedure1Hist(String variable) {
        this(Procedure1Hist.class, forVariable(variable), INITS);
    }

    public QProcedure1Hist(Path<? extends Procedure1Hist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProcedure1Hist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProcedure1Hist(PathMetadata metadata, PathInits inits) {
        this(Procedure1Hist.class, metadata, inits);
    }

    public QProcedure1Hist(Class<? extends Procedure1Hist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QProcedure1HistId(forProperty("id")) : null;
        this.interventionUid = inits.isInitialized("interventionUid") ? new QProcedure1(forProperty("interventionUid"), inits.get("interventionUid")) : null;
    }

}

