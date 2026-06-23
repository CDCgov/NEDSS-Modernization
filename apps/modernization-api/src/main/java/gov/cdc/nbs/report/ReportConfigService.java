package gov.cdc.nbs.report;

import gov.cdc.nbs.repository.NbsConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportConfigService {

    private final NbsConfigurationRepository nbsConfigurationRepository;

    public String getConfigValue(String key) {
        return nbsConfigurationRepository
                .findById(key)
                .map(config -> config.getConfigValue() != null ? config.getConfigValue() : config.getDefaultValue())
                .orElse("");
    }
}
