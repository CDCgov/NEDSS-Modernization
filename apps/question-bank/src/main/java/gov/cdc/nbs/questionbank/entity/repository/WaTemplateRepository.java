package gov.cdc.nbs.questionbank.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.WaTemplate;

public interface WaTemplateRepository extends JpaRepository<WaTemplate, Long> {

    public Optional<WaTemplate> findByIdAndTemplateTypeIn(Long id, List<String> templateTypes);

    public Boolean existsByDatamartNmAndIdNot(String dataMartName, Long id);

    public Boolean existsByTemplateNmAndIdNot(String templateNm, Long id);

    @Query("SELECT MAX(id) from WaTemplate")
    Long getMaxTemplateID();
}
