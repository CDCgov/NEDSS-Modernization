package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCountryCodeIsoId is a Querydsl query type for CountryCodeIsoId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCountryCodeIsoId extends BeanPath<CountryCodeIsoId> {

    private static final long serialVersionUID = -1886653173L;

    public static final QCountryCodeIsoId countryCodeIsoId = new QCountryCodeIsoId("countryCodeIsoId");

    public final StringPath code = createString("code");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public QCountryCodeIsoId(String variable) {
        super(CountryCodeIsoId.class, forVariable(variable));
    }

    public QCountryCodeIsoId(Path<? extends CountryCodeIsoId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCountryCodeIsoId(PathMetadata metadata) {
        super(CountryCodeIsoId.class, metadata);
    }

}

