package gov.cdc.nbs.questionbank.entity.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.WaTemplate;

public interface WaTemplateRepository extends JpaRepository<WaTemplate, Long> {

    public Optional<WaTemplate> findByIdAndTemplateTypeIn(Long id, List<String> templateTypes);
}
