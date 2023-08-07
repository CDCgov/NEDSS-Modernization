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

    
    @Query("SELECT v from WaTemplate v WHERE v.id=:id OR v.templateNm LIKE %:templateNm% OR v.conditionCd LIKE %:conditionCd% OR v.datamartNm LIKE %:dataMartNm% OR v.recordStatusCd LIKE %:recordStatusCd% OR v.templateType IN :templateType")
    Page<WaTemplate> searchTemplate(@Param("id") Long id, @Param("templateNm") String templateNm, @Param("conditionCd") String conditionCd , @Param("dataMartNm") String dataMartNm, @Param("recordStatusCd") String recordStatusCd,  @Param("templateType") List<String> templateType, Pageable pageable);
    
    @Query("SELECT MAX(id) from WaTemplate")
    Long getMaxTemplateID();
}
