package gov.cdc.nbs.id;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalUidGeneratorRepository extends JpaRepository<LocalUidGenerator, String> {

  public Optional<LocalUidGenerator> findByTypeCd(String typeCd);
}
