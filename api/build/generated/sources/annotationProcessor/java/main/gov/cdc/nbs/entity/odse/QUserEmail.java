package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEmail is a Querydsl query type for UserEmail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEmail extends EntityPathBase<UserEmail> {

    private static final long serialVersionUID = 532426234L;

    public static final QUserEmail userEmail = new QUserEmail("userEmail");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath emailId = createString("emailId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> nedssEntryId = createNumber("nedssEntryId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final NumberPath<Integer> seqNbr = createNumber("seqNbr", Integer.class);

    public QUserEmail(String variable) {
        super(UserEmail.class, forVariable(variable));
    }

    public QUserEmail(Path<? extends UserEmail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEmail(PathMetadata metadata) {
        super(UserEmail.class, metadata);
    }

}

