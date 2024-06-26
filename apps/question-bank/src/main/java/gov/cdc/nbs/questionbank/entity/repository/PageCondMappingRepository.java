package gov.cdc.nbs.questionbank.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;

public interface PageCondMappingRepository extends JpaRepository<PageCondMapping, Long> {

    List<PageCondMapping> findByWaTemplateUidIn(List<WaTemplate> pages);

    public void deleteAllByWaTemplateUid(WaTemplate template);
}
