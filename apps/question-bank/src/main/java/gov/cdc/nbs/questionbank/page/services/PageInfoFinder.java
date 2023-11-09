package gov.cdc.nbs.questionbank.page.services;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.page.PageInfoMapper;
import gov.cdc.nbs.questionbank.page.model.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageInfoFinder {
    private static final QWaTemplate waTemplate = QWaTemplate.waTemplate;
    private static final QConditionCode conditionCode = QConditionCode.conditionCode;
    private static final QPageCondMapping conditionMapping = QPageCondMapping.pageCondMapping;
    private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;
    private final PageInfoMapper mapper;
    private final JPAQueryFactory factory;

    public PageInfoFinder(
            final JPAQueryFactory factory,
            final PageInfoMapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    public PageInfo find(Long id) {
        JPAQuery<Tuple> query = factory.select(
                        waTemplate.templateNm,
                        waTemplate.descTxt,
                        waTemplate.busObjType,
                        waTemplate.datamartNm,
                        waTemplate.nndEntityIdentifier,
                        codeValueGeneral.codeShortDescTxt,
                        conditionCode.id,
                        conditionCode.conditionShortNm
                )
                .from(waTemplate)
                .leftJoin(conditionMapping).on(waTemplate.id.eq(conditionMapping.waTemplateUid.id))
                .leftJoin(conditionCode).on(conditionMapping.conditionCd.eq(conditionCode.id))
                .leftJoin(codeValueGeneral).on(waTemplate.nndEntityIdentifier.eq(codeValueGeneral.id.code))
                .where(waTemplate.id.eq(id));
        return mapper.map(query.fetchOne());
    }

}

