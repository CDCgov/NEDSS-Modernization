package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCdfSubformImportDataLogId is a Querydsl query type for CdfSubformImportDataLogId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCdfSubformImportDataLogId extends BeanPath<CdfSubformImportDataLogId> {

    private static final long serialVersionUID = -1947477990L;

    public static final QCdfSubformImportDataLogId cdfSubformImportDataLogId = new QCdfSubformImportDataLogId("cdfSubformImportDataLogId");

    public final StringPath dataOid = createString("dataOid");

    public final StringPath dataType = createString("dataType");

    public final NumberPath<Long> importLogUid = createNumber("importLogUid", Long.class);

    public QCdfSubformImportDataLogId(String variable) {
        super(CdfSubformImportDataLogId.class, forVariable(variable));
    }

    public QCdfSubformImportDataLogId(Path<? extends CdfSubformImportDataLogId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCdfSubformImportDataLogId(PathMetadata metadata) {
        super(CdfSubformImportDataLogId.class, metadata);
    }

}

