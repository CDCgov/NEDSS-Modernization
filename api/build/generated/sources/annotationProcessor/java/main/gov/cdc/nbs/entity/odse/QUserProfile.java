package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserProfile is a Querydsl query type for UserProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserProfile extends EntityPathBase<UserProfile> {

    private static final long serialVersionUID = 1889094791L;

    public static final QUserProfile userProfile = new QUserProfile("userProfile");

    public final StringPath firstNm = createString("firstNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastNm = createString("lastNm");

    public final DateTimePath<java.time.Instant> lastUpdTime = createDateTime("lastUpdTime", java.time.Instant.class);

    public QUserProfile(String variable) {
        super(UserProfile.class, forVariable(variable));
    }

    public QUserProfile(Path<? extends UserProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserProfile(PathMetadata metadata) {
        super(UserProfile.class, metadata);
    }

}

