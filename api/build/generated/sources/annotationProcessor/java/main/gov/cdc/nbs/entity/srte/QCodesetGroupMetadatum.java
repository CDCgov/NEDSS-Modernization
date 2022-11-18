package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodesetGroupMetadatum is a Querydsl query type for CodesetGroupMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodesetGroupMetadatum extends EntityPathBase<CodesetGroupMetadatum> {

    private static final long serialVersionUID = 2097399762L;

    public static final QCodesetGroupMetadatum codesetGroupMetadatum = new QCodesetGroupMetadatum("codesetGroupMetadatum");

    public final StringPath codeSetDescTxt = createString("codeSetDescTxt");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final SetPath<Codeset, QCodeset> codesets = this.<Codeset, QCodeset>createSet("codesets", Codeset.class, QCodeset.class, PathInits.DIRECT2);

    public final StringPath codeSetShortDescTxt = createString("codeSetShortDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> ldfPicklistIndCd = createComparable("ldfPicklistIndCd", Character.class);

    public final ComparablePath<Character> phinStdValInd = createComparable("phinStdValInd", Character.class);

    public final StringPath vadsValueSetCode = createString("vadsValueSetCode");

    public QCodesetGroupMetadatum(String variable) {
        super(CodesetGroupMetadatum.class, forVariable(variable));
    }

    public QCodesetGroupMetadatum(Path<? extends CodesetGroupMetadatum> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodesetGroupMetadatum(PathMetadata metadata) {
        super(CodesetGroupMetadatum.class, metadata);
    }

}

