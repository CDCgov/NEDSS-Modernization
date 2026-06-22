package gov.cdc.nbs.report;

import gov.cdc.nbs.questionbank.entity.NbsConfiguration;
import gov.cdc.nbs.repository.NbsConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportConfigService {

    private final NbsConfigurationRepository configRepository;

    public String getConfigValue(String key, String defaultValue) {
        return configRepository.findById(key)
                .map(NbsConfiguration::getConfigValue)
                .orElse(defaultValue);
    }

    public int getConfigInt(String key, int defaultValue) {
        String val = getConfigValue(key, null);
        if (val == null) return defaultValue;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getDataSourceMapping(String key) {
        // e.g., key = "nbs_ods"
        String fullKey = "report.datasource.mapping." + key;
        // Providing fallback matching your current application.yml defaults
        String defaultMapping = switch (key) {
            case "nbs_ods" -> "NBS_ODSE";
            case "nbs_rdb" -> "RDB";
            case "nbs_srt" -> "NBS_SRTE";
            case "nbs_msg" -> "NBS_MSGOUTE";
            default -> throw new IllegalArgumentException("Unknown datasource key: " + key);
        };
        return getConfigValue(fullKey, defaultMapping);
    }
}
