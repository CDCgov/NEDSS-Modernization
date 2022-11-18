package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPageCondMappingHist is a Querydsl query type for PageCondMappingHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPageCondMappingHist extends EntityPathBase<PageCondMappingHist> {

    private static final long serialVersionUID = -393244120L;

    public static final QPageCondMappingHist pageCondMappingHist = new QPageCondMappingHist("pageCondMappingHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath conditionCd = createString("conditionCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> pageCondMappingUid = createNumber("pageCondMappingUid", Long.class);

    public final NumberPath<Long> waTemplateUid = createNumber("waTemplateUid", Long.class);

    public QPageCondMappingHist(String variable) {
        super(PageCondMappingHist.class, forVariable(variable));
    }

    public QPageCondMappingHist(Path<? extends PageCondMappingHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPageCondMappingHist(PathMetadata metadata) {
        super(PageCondMappingHist.class, metadata);
    }

}

