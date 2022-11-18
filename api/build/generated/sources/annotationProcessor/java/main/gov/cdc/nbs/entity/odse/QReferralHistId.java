package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReferralHistId is a Querydsl query type for ReferralHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReferralHistId extends BeanPath<ReferralHistId> {

    private static final long serialVersionUID = -817543151L;

    public static final QReferralHistId referralHistId = new QReferralHistId("referralHistId");

    public final NumberPath<Long> referralUid = createNumber("referralUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QReferralHistId(String variable) {
        super(ReferralHistId.class, forVariable(variable));
    }

    public QReferralHistId(Path<? extends ReferralHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReferralHistId(PathMetadata metadata) {
        super(ReferralHistId.class, metadata);
    }

}

