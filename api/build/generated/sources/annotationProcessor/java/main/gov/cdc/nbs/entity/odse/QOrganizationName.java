package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrganizationName is a Querydsl query type for OrganizationName
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrganizationName extends EntityPathBase<OrganizationName> {

    private static final long serialVersionUID = 457997013L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrganizationName organizationName = new QOrganizationName("organizationName");

    public final ComparablePath<Character> defaultNmInd = createComparable("defaultNmInd", Character.class);

    public final QOrganizationNameId id;

    public final StringPath nmTxt = createString("nmTxt");

    public final StringPath nmUseCd = createString("nmUseCd");

    public final QOrganization organizationUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public QOrganizationName(String variable) {
        this(OrganizationName.class, forVariable(variable), INITS);
    }

    public QOrganizationName(Path<? extends OrganizationName> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrganizationName(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrganizationName(PathMetadata metadata, PathInits inits) {
        this(OrganizationName.class, metadata, inits);
    }

    public QOrganizationName(Class<? extends OrganizationName> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QOrganizationNameId(forProperty("id")) : null;
        this.organizationUid = inits.isInitialized("organizationUid") ? new QOrganization(forProperty("organizationUid"), inits.get("organizationUid")) : null;
    }

}

