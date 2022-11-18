package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsDocumentMetadatum is a Querydsl query type for NbsDocumentMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsDocumentMetadatum extends EntityPathBase<NbsDocumentMetadatum> {

    private static final long serialVersionUID = -2140601305L;

    public static final QNbsDocumentMetadatum nbsDocumentMetadatum = new QNbsDocumentMetadatum("nbsDocumentMetadatum");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath description = createString("description");

    public final StringPath docTypeCd = createString("docTypeCd");

    public final StringPath docTypeVersionTxt = createString("docTypeVersionTxt");

    public final StringPath documentViewCdaXsl = createString("documentViewCdaXsl");

    public final StringPath documentViewXsl = createString("documentViewXsl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parserClassNm = createString("parserClassNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath xmlbeanFactoryClassNm = createString("xmlbeanFactoryClassNm");

    public final StringPath xmlSchemaLocation = createString("xmlSchemaLocation");

    public QNbsDocumentMetadatum(String variable) {
        super(NbsDocumentMetadatum.class, forVariable(variable));
    }

    public QNbsDocumentMetadatum(Path<? extends NbsDocumentMetadatum> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsDocumentMetadatum(PathMetadata metadata) {
        super(NbsDocumentMetadatum.class, metadata);
    }

}

