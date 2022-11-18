package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClinicalDocumentHistId is a Querydsl query type for ClinicalDocumentHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QClinicalDocumentHistId extends BeanPath<ClinicalDocumentHistId> {

    private static final long serialVersionUID = -887368190L;

    public static final QClinicalDocumentHistId clinicalDocumentHistId = new QClinicalDocumentHistId("clinicalDocumentHistId");

    public final NumberPath<Long> clinicalDocumentUid = createNumber("clinicalDocumentUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QClinicalDocumentHistId(String variable) {
        super(ClinicalDocumentHistId.class, forVariable(variable));
    }

    public QClinicalDocumentHistId(Path<? extends ClinicalDocumentHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClinicalDocumentHistId(PathMetadata metadata) {
        super(ClinicalDocumentHistId.class, metadata);
    }

}

