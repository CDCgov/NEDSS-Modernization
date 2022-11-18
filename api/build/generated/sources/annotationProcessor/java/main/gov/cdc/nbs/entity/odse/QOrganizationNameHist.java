package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrganizationNameHist is a Querydsl query type for OrganizationNameHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrganizationNameHist extends EntityPathBase<OrganizationNameHist> {

    private static final long serialVersionUID = 1482382231L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrganizationNameHist organizationNameHist = new QOrganizationNameHist("organizationNameHist");

    public final ComparablePath<Character> defaultNmInd = createComparable("defaultNmInd", Character.class);

    public final QOrganizationNameHistId id;

    public final StringPath nmTxt = createString("nmTxt");

    public final StringPath nmUseCd = createString("nmUseCd");

    public final QOrganizationName organizationName;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public QOrganizationNameHist(String variable) {
        this(OrganizationNameHist.class, forVariable(variable), INITS);
    }

    public QOrganizationNameHist(Path<? extends OrganizationNameHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrganizationNameHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrganizationNameHist(PathMetadata metadata, PathInits inits) {
        this(OrganizationNameHist.class, metadata, inits);
    }

    public QOrganizationNameHist(Class<? extends OrganizationNameHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QOrganizationNameHistId(forProperty("id")) : null;
        this.organizationName = inits.isInitialized("organizationName") ? new QOrganizationName(forProperty("organizationName"), inits.get("organizationName")) : null;
    }

}

